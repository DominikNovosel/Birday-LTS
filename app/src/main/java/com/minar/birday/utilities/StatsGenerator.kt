package com.minar.birday.utilities

import android.content.Context
import android.os.Build
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.BulletSpan
import androidx.annotation.ColorInt
import com.minar.birday.R
import com.minar.birday.model.EventResult
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import kotlin.math.truncate
import kotlin.random.Random

class StatsGenerator(eventList: List<EventResult>, context: Context?) {
    private val events: List<EventResult> = eventList
    private val applicationContext = context

    // Generate a random stat choosing randomly between one of the available functions
    fun generateRandomStat(): String {
        // Use a response string to re-execute the stats calculation if a stat cannot be computed correctly
        var response: String? = null
        val randomPerson = events.random()
        while (response.isNullOrBlank()) {
            response = when (Random.nextInt(0, 12)) {
                1 -> ageAverage()
                2 -> mostCommonMonth()
                3 -> mostCommonDecade()
                4 -> mostCommonAgeRange()
                5 -> specialAges()
                6 -> leapYearTotal()
                7 -> mostCommonZodiacSign()
                8 -> mostCommonDayOfWeek()
                9 -> dayOfWeek(randomPerson)
                10 -> zodiacSign(randomPerson)
                11 -> chineseSign(randomPerson)
                else -> ageAverage()
            }
        }
        return response
    }

    // Generate a summary of the stats
    fun generateFullStats(): SpannableStringBuilder {
        val sb = SpannableStringBuilder()
        val stats = mutableListOf<String>()
        stats.add(ageAverage())
        stats.add(oldestPerson())
        stats.add(youngestPerson())
        stats.add(mostCommonAgeRange())
        stats.add(mostCommonDayOfWeek())
        stats.add(mostCommonDecade())
        stats.add(mostCommonMonth())
        stats.add(mostCommonZodiacSign())
        stats.add(leapYearTotal())
        stats.removeIf { it.isBlank() }
        sb.appendBulletSpans(stats, 16, applicationContext!!.getColor(R.color.goodGray))
        return sb
    }

    // The average age
    private fun ageAverage(): String {
        val average = truncate(getAges().values.average()).toInt()
        return String.format(
            applicationContext?.getString(R.string.age_average)!!,
            applicationContext.resources?.getQuantityString(R.plurals.years, average, average),
        )
    }

    // The oldest person, taking into account months and days
    private fun oldestPerson(): String {
        var oldestDate = LocalDate.now()
        var oldestName = ""
        var oldestAge = 0
        events.forEach {
            if (oldestDate.isAfter(it.originalDate) && it.yearMatter!!) {
                oldestName = it.name
                oldestDate = it.originalDate
                oldestAge = getAge(it)
            }
        }
        return String.format(
            applicationContext?.getString(R.string.oldest_person)!!,
            oldestName,
        ) + ", " + applicationContext.resources?.getQuantityString(
            R.plurals.years,
            oldestAge,
            oldestAge
        ).toString()
    }

    // The youngest person, taking into account months and days
    private fun youngestPerson(): String {
        var youngestDate = LocalDate.of(1500, 1, 1)
        var youngestName = ""
        var youngestAge = 0
        events.forEach {
            if (youngestDate.isBefore(it.originalDate) && it.yearMatter!!) {
                youngestName = it.name
                youngestDate = it.originalDate
                youngestAge = getAge(it)
            }
        }
        val commonPart = String.format(
            applicationContext?.getString(R.string.youngest_person)!!,
            youngestName,
        )
        // If the youngest person is a baby, return the age in months
        return if (youngestAge == 0) {
            val months = getAgeMonths(youngestDate)
            "$commonPart, " + applicationContext.resources?.getQuantityString(
                R.plurals.months,
                months,
                months
            )
        } else {
            "$commonPart, " + applicationContext.resources?.getQuantityString(
                R.plurals.years,
                youngestAge,
                youngestAge
            )
        }
    }

    // The most common month. When there's no common month, return a blank string
    private fun mostCommonMonth(): String {
        val months = mutableMapOf<String, Int>()
        events.forEach {
            val month = it.originalDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())
            if (months[month] == null) months[month] = 1
            else months[month] = months[month]!!.plus(1)
        }
        val commonMonth: String = evaluateResult(months)
        if (commonMonth.isBlank()) return commonMonth
        return String.format(
            applicationContext?.getString(R.string.most_common_month)!!,
            commonMonth,
        )
    }

    // The most common age range (decade). When there's no common range, return a blank string
    private fun mostCommonAgeRange(): String {
        val ageRanges = mutableMapOf<String, Int>()
        events.forEach {
            // Quite unnecessary both here and in other functions, but it's for extra safety
            if (it.yearMatter!!) {
                if (ageRanges[getAgeRange(it.originalDate)] == null) ageRanges[getAgeRange(it.originalDate)] =
                    1
                else ageRanges[getAgeRange(it.originalDate)] =
                    ageRanges[getAgeRange(it.originalDate)]!!.plus(1)
            }
        }
        val commonRange: String = evaluateResult(ageRanges)
        if (commonRange.isBlank()) return commonRange
        return String.format(
            applicationContext?.getString(R.string.most_common_age_range)!!,
            commonRange,
            commonRange.toInt() + 10,
        )
    }

    // The most common decade (80s, 90s..). When there's no common decade, return a blank string
    private fun mostCommonDecade(): String {
        val decades = mutableMapOf<String, Int>()
        events.forEach {
            // Quite unnecessary both here and in other functions, but it's for extra safety
            if (it.yearMatter!!) {
                if (decades[getDecade(it.originalDate)] == null) decades[getDecade(it.originalDate)] =
                    1
                else decades[getDecade(it.originalDate)] =
                    decades[getDecade(it.originalDate)]!!.plus(1)
            }
        }
        val commonDecade: String = evaluateResult(decades)
        if (commonDecade.isBlank()) return commonDecade
        return String.format(
            applicationContext?.getString(R.string.most_common_decade)!!,
            commonDecade,
        )
    }

    // Get a random "special age" person. Special age means 1, 10, 18, 20, 30, 40, and so on
    private fun specialAges(): String {
        val specialAges = arrayOf(1, 10, 18, 20, 30, 40, 50, 60, 70, 80, 90, 100, 110, 120, 130)
        val specialPersons = mutableMapOf<String, Int>()
        events.forEach {
            // Quite unnecessary both here and in other functions, but it's for extra safety
            if (it.yearMatter!!) {
                val nextAge = getNextAge(it)
                if (nextAge in specialAges) specialPersons[it.name] = nextAge
            }
        }
        return if (specialPersons.isEmpty()) ""
        else {
            val chosen = specialPersons.keys.random()
            val years = specialPersons[chosen]!!
            // Format the first half of the sentence
            return String.format(
                applicationContext?.getString(R.string.special_ages)!!,
                chosen,
            ) + ", " + applicationContext.resources?.getQuantityString(
                R.plurals.years,
                years,
                years
            ).toString()
        }
    }

    // Get the zodiac sign for a random person
    private fun zodiacSign(person: EventResult): String {
        return String.format(
            applicationContext?.getString(R.string.random_zodiac_sign)!!,
            person.name,
            getZodiacSign(person),
        )
    }

    // The most common zodiac sign. When there's no common zodiac sign, return a blank string
    private fun mostCommonZodiacSign(): String {
        val zodiacSigns = mutableMapOf<String, Int>()
        events.forEach {
            if (zodiacSigns[getZodiacSign(it)] == null) zodiacSigns[getZodiacSign(it)] = 1
            else zodiacSigns[getZodiacSign(it)] = zodiacSigns[getZodiacSign(it)]!!.plus(1)
        }
        val commonZodiacSign: String = evaluateResult(zodiacSigns)
        if (commonZodiacSign.isBlank()) return commonZodiacSign
        return String.format(
            applicationContext?.getString(R.string.most_common_zodiac_sign)!!,
            commonZodiacSign,
        )
    }

    // Get the day of the week of birth for a random person
    private fun dayOfWeek(person: EventResult): String {
        return if (!person.yearMatter!!) ""
        else String.format(
            applicationContext?.getString(R.string.random_day_of_week)!!,
            person.name,
            person.originalDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault()),
        )
    }

    // The most common day of the week of birth. When there's no common day of the week, return a blank string
    private fun mostCommonDayOfWeek(): String {
        val weekDays = mutableMapOf<String, Int>()
        events.forEach {
            if (it.yearMatter!!) {
                val weekDay =
                    it.originalDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
                if (weekDays[weekDay] == null) weekDays[weekDay] = 1
                else weekDays[weekDay] = weekDays[weekDay]!!.plus(1)
            }
        }
        val commonWeekDay: String = evaluateResult(weekDays)
        if (commonWeekDay.isBlank()) return commonWeekDay
        return String.format(
            applicationContext?.getString(R.string.most_common_day_of_week)!!,
            commonWeekDay
        )
    }

    // Get the number of persons born in a leap year. Even 0 is an acceptable result
    private fun leapYearTotal(): String {
        var leapTotal = 0
        events.forEach {
            if (it.yearMatter!!) if (it.originalDate.isLeapYear) leapTotal++
        }
        return applicationContext?.resources?.getQuantityString(
            R.plurals.leap_year_total,
            leapTotal,
            leapTotal
        ).toString()
    }

    // Get the chinese year of a random person
    private fun chineseSign(person: EventResult): String {
        return if (!person.yearMatter!!) ""
        else String.format(
            applicationContext?.getString(R.string.random_chinese_year)!!,
            person.name,
            getChineseSign(person),
        )
    }

    // Get a list containing the names and an int containing the age
    private fun getAges(): Map<String, Int> {
        val ages = mutableMapOf<String, Int>()
        events.forEach {
            if (it.yearMatter!!) {
                val age = getAge(it)
                ages[it.name] = age
            }
        }
        return ages
    }

    fun getChineseSign(person: EventResult): String {
        return when (chineseAnimal(person.originalDate)) {
            0 -> applicationContext!!.getString(R.string.chinese_zodiac_rat)
            1 -> applicationContext!!.getString(R.string.chinese_zodiac_ox)
            2 -> applicationContext!!.getString(R.string.chinese_zodiac_tiger)
            3 -> applicationContext!!.getString(R.string.chinese_zodiac_rabbit)
            4 -> applicationContext!!.getString(R.string.chinese_zodiac_dragon)
            5 -> applicationContext!!.getString(R.string.chinese_zodiac_snake)
            6 -> applicationContext!!.getString(R.string.chinese_zodiac_horse)
            7 -> applicationContext!!.getString(R.string.chinese_zodiac_goat)
            8 -> applicationContext!!.getString(R.string.chinese_zodiac_monkey)
            9 -> applicationContext!!.getString(R.string.chinese_zodiac_rooster)
            10 -> applicationContext!!.getString(R.string.chinese_zodiac_dog)
            11 -> applicationContext!!.getString(R.string.chinese_zodiac_pig)
            else -> throw Exception("Unexpected Chinese animal index")
        }
    }

    // Get the zodiac sign
    fun getZodiacSign(person: EventResult): String {
        val day = person.originalDate.dayOfMonth
        val month = person.originalDate.month.value
        var sign = ""
        var signNumber = 0
        when (month) {
            12 -> signNumber = if (day < 22) 0 else 1
            1 -> signNumber = if (day < 20) 1 else 2
            2 -> signNumber = if (day < 19) 2 else 3
            3 -> signNumber = if (day < 21) 3 else 4
            4 -> signNumber = if (day < 20) 4 else 5
            5 -> signNumber = if (day < 21) 5 else 6
            6 -> signNumber = if (day < 21) 6 else 7
            7 -> signNumber = if (day < 23) 7 else 8
            8 -> signNumber = if (day < 23) 8 else 9
            9 -> signNumber = if (day < 23) 9 else 10
            10 -> signNumber = if (day < 23) 10 else 11
            11 -> signNumber = if (day < 22) 11 else 0
        }
        when (signNumber) {
            0 -> sign = applicationContext?.getString(R.string.zodiac_sagittarius).toString()
            1 -> sign = applicationContext?.getString(R.string.zodiac_capricorn).toString()
            2 -> sign = applicationContext?.getString(R.string.zodiac_aquarius).toString()
            3 -> sign = applicationContext?.getString(R.string.zodiac_pisces).toString()
            4 -> sign = applicationContext?.getString(R.string.zodiac_aries).toString()
            5 -> sign = applicationContext?.getString(R.string.zodiac_taurus).toString()
            6 -> sign = applicationContext?.getString(R.string.zodiac_gemini).toString()
            7 -> sign = applicationContext?.getString(R.string.zodiac_cancer).toString()
            8 -> sign = applicationContext?.getString(R.string.zodiac_leo).toString()
            9 -> sign = applicationContext?.getString(R.string.zodiac_virgo).toString()
            10 -> sign = applicationContext?.getString(R.string.zodiac_libra).toString()
            11 -> sign = applicationContext?.getString(R.string.zodiac_scorpio).toString()
        }
        return sign
    }

    // Only return the number of the sign
    fun getZodiacSignNumber(person: EventResult): Int {
        val day = person.originalDate.dayOfMonth
        val month = person.originalDate.month.value
        var signNumber = 0
        when (month) {
            12 -> signNumber = if (day < 22) 0 else 1
            1 -> signNumber = if (day < 20) 1 else 2
            2 -> signNumber = if (day < 19) 2 else 3
            3 -> signNumber = if (day < 21) 3 else 4
            4 -> signNumber = if (day < 20) 4 else 5
            5 -> signNumber = if (day < 21) 5 else 6
            6 -> signNumber = if (day < 21) 6 else 7
            7 -> signNumber = if (day < 23) 7 else 8
            8 -> signNumber = if (day < 23) 8 else 9
            9 -> signNumber = if (day < 23) 9 else 10
            10 -> signNumber = if (day < 23) 10 else 11
            11 -> signNumber = if (day < 22) 11 else 0
        }
        return signNumber
    }

    // Evaluate the result, differently from maxBy. If there's a tie, return an empty string
    private fun evaluateResult(map: Map<String, Int>): String {
        var maxValue = 0
        var result = ""
        map.forEach {
            if (it.value > maxValue) {
                maxValue = it.value
                result = it.key
            }
        }
        return if (map.values.count { it == maxValue } > 1) ""
        else result
    }

    // Functions to build the statistics in a bullet list
    private fun SpannableStringBuilder.appendBulletSpans(
        paragraphs: List<String>,
        margin: Int,
        @ColorInt color: Int
    ): SpannableStringBuilder {
        for (paragraph in paragraphs) {
            if (paragraphs.indexOf(paragraph) == 0) appendBulletSpan(paragraph, margin, color, true)
            else appendBulletSpan(paragraph, margin, color)
        }
        return this
    }

    private fun SpannableStringBuilder.appendBulletSpan(
        paragraph: String,
        margin: Int,
        @ColorInt color: Int,
        first: Boolean = false
    ): SpannableStringBuilder {
        if (!first) append("\n")
        val bulletSpan =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) BulletSpan(margin, color, 10)
            else BulletSpan(margin, color)
        val spaceBefore = length
        append(paragraph)
        val spaceAfter = length
        append("\n")
        setSpan(bulletSpan, spaceBefore, spaceAfter, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        return this
    }
}
