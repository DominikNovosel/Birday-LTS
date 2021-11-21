package com.minar.birday.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.minar.birday.R
import com.minar.birday.databinding.FavoriteRowBinding
import com.minar.birday.listeners.OnItemClickListener
import com.minar.birday.model.EventResult
import com.minar.birday.utilities.formatName
import com.minar.birday.utilities.getAge
import com.minar.birday.utilities.getRemainingDays
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class FavoritesAdapter internal constructor() :
    ListAdapter<EventResult, FavoritesAdapter.FavoriteViewHolder>(FavoritesDiffCallback()) {
    private lateinit var context: Context
    var itemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        context = parent.context
        val binding = FavoriteRowBinding
            .inflate(LayoutInflater.from(context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesAdapter.FavoriteViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // Can't use elsewhere without overriding as a public function
    public override fun getItem(position: Int): EventResult {
        return super.getItem(position)
    }

    inner class FavoriteViewHolder(binding: FavoriteRowBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnClickListener, View.OnLongClickListener {
        private val eventPerson = binding.eventPerson
        private val eventNote = binding.eventNote
        private val eventDate = binding.eventDate
        private val eventYears = binding.eventYears
        private val eventCountdown = binding.eventCountdown

        init {
            binding.root.setOnClickListener(this)
            binding.root.setOnLongClickListener(this)
        }

        // Set every necessary text and click action in each row
        fun bind(event: EventResult) {
            val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
            val formattedPersonName =
                formatName(event, sharedPrefs.getBoolean("surname_first", false))
            val formatter: DateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)
            val age = getAge(event)
            val daysRemaining = getRemainingDays(event.nextDate!!)
            val daysCountdown = if (daysRemaining > 0) "-$daysRemaining"
            else context.getString(R.string.exclamation)
            var nextDate = event.nextDate.format(formatter)

            if (event.yearMatter == false) nextDate = event.nextDate.format(formatter)
            val actualAge = context.getString(R.string.next_age_years) + ": " + age.toString() +
                    ", " + context.getString(R.string.born_in) + " " + event.originalDate.year
            eventPerson.text = formattedPersonName
            // Show an icon if there's a note
            if (!event.notes.isNullOrEmpty()) eventNote.visibility = View.VISIBLE
            else eventNote.visibility = View.GONE
            eventDate.text = nextDate
            eventCountdown.text = daysCountdown
            // Age -2 means that the year is not considered and the age is meaningless
            if (age != -2) {
                eventYears.visibility = View.VISIBLE
                eventYears.text = actualAge
            } else eventYears.visibility = View.GONE
        }

        override fun onClick(v: View?) {
            itemClickListener?.onItemClick(adapterPosition, v)
        }

        override fun onLongClick(v: View?): Boolean {
            itemClickListener?.onItemLongClick(adapterPosition, v)
            return true
        }
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
}

class FavoritesDiffCallback : DiffUtil.ItemCallback<EventResult>() {
    override fun areItemsTheSame(oldItem: EventResult, newItem: EventResult): Boolean {
        return oldItem.id == newItem.id
    }

    // Consider the notes, which is not in the equals method but is important to trigger the icon
    override fun areContentsTheSame(oldItem: EventResult, newItem: EventResult): Boolean {
        return oldItem == newItem && oldItem.notes == newItem.notes
    }
}