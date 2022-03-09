# Archived!
This repo is archived and probably won't be updated anymore, the reason being that the main Birday repo has finally switched to keeping its master branch stable, and using a development branch for everything else. Soon, a GitHub Action should be configured to output an APK to https://github.com/m-i-n-a-r/birday/releases every so often, so you can install that with the same procedure described below.

---

Note: this document is still a work in progress.
# Birday LTS
This repository is based on the 2.1.0 release of https://github.com/m-i-n-a-r/birday. You can see the content of the original README at the bottom of this page.\
Why isn't it a direct fork? Because after the changes i made, I couldn't for the life of me find a way to have it marked as a fork in a way that wasn't excrutiatingly cumbersome. Heck, it took me long enough to even figure out how to upload everything. Thus, it'll probably be archived and replaced when the next stable version is released.

### Why does it exist?
The original author, @m-i-n-a-r is frequently very busy and rarely updates the app, which means users and some translators won't see it in their language for a very long time. This repo hopes to make Birday more accessible to users, easier to test for translators, and maybe help the original author keep it up to date by updating it with the latest translations from the upstream [master](https://github.com/m-i-n-a-r/birday).

# Installation
There are several ways to do this, let's start with the most convenient.<br> 
### F-Droid
As far as I can tell, @Poussinou took on the responsibility to maintain Birday on F-Droid. Considering how F-Droid works, updates are likely contingent on m-i-n-a-r's publishing of releases. We'd have to contact Poussinou directly to make this version available there, but i personally couldn't find any contact information.<br>
### Play Store
 As for Google Play, I'll try to convince m-i-n-a-r to periodically push out updates to the Play Store so you can: <br>
 [<img src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png"
     alt="Get it on Google Play"
     height="80">](https://play.google.com/store/apps/details?id=com.minar.birday)

 ### Sideloading   
If neither prove to be viable options, or the version you need isn't available yet, you can head over to the [Releases](https://github.com/DominikNovosel/Birday-LTS) and tap on the APK to download it to your phone. Navigate to it in your file manager and tap it. You may be prompted to allow installing apps from foreign sources and a see a security warning from Play Services (which you can safely ignore, it's just appearing because the app is unsigned).<br>
**Important!** <br>
If you already installed Birday from another source, you'll need to uninstall it first. Before doing so, make sure to make a backup from the app's settings menu and move it somewhere safe, also write down the values of any settings you wish to keep, because all of this data will be removed during the uninstallation.

# Contributing
The main objective here is to make sure all translation related changes in the original repository are also carried over to this one.
<br> 
You can help in several ways:
- If you are contributing translations to Birday, make sure you also open a pull request here, it would be particularly helpful to others if you also link to the upstream pull request.
- If someone doesn't do the above, you can do it for them.
- You can also help by just using the app in different languages and sending feedback.

Improving this documentation  would also be of great help!\
Depending on your level of expertise, you might need some help. Feel free to reach out in the Issues tab, or take a look at these resources:
- [Translation guide](https://github.com/m-i-n-a-r/birday/wiki/Translate-the-app) (If your language has special rules regarding plurals, (eg. in Croatian: 0-1 godin**a**, 2-4 godin**e**...) take a look at [this](https://developer.android.com/guide/topics/resources/string-resource#Plurals))
- [How to build the app](https://github.com/m-i-n-a-r/birday/wiki/Build-the-app-yourself) (Particularly useful if you're impatient for the next release!)
- [Pull requests](https://docs.github.com/en/pull-requests/collaborating-with-pull-requests/proposing-changes-to-your-work-with-pull-requests/about-pull-requests)

# Original README:

# Birday

An open source app to remember birthdays and events without having to open Facebook, set alarms or rely on Google Calendar.

<p align='center'>
  <a href='https://github.com/m-i-n-a-r/birday/blob/master/LICENSE.md'><img src='https://img.shields.io/badge/license-GPL 3-333333'/></a>
  <img src='https://img.shields.io/badge/version-2.1.X-blue'/>
	<img src='https://img.shields.io/badge/status-released-success'/>
	<img src='https://img.shields.io/badge/-translations%20needed!-yellow'/>
</p>

## Introduction
This is an open source app to remember **birthdays** in a fast, light and beautiful way. No less, no more. I had this idea considering that Google Calendar doesn't display an automatic notification the day of the birthday, and sometimes i need to remember a birthday for a person who is not in my Contacts. Also, adding too much birthdays to the calendar makes it messy and i prefer to manage them separately. The main focus of this app is being easy to use, clean and lightweight. I'm always glad to add features, but only important and really useful features!

## Translations (guide and info below)

| LANGUAGE                | STATUS       | SPECIAL THANKS | UPDATED BY                  |
|:------------------------|:------------:|:---------------|:----------------------------|
| **English**             | complete     | myself         | ar-maged, Commenter25       |
| **Spanish**             | complete     | myself         | Joseahfer                   |
| **Swedish**             | needs update | SlowNicoFish   |                             |
| **Dutch**               | needs update | stefanvi       |                             |
| **Italian**             | complete     | myself         | myself                      |
| **French**              | complete     | Mattis Biton   |                             |
| **Hungarian**           | needs update | Obi            |                             |
| **German**              | complete     | pizzapastamix  | WrstFngr, AlexanderRitter02 |
| **Vietnamese**          | needs update | Lee Huynh      |                             |
| **Russian**             | needs update | koterpillar    |                             |
| **Czech**               | needs update | Miloš Koliáš   |                             |
| **Polish**              | complete     | mateusz-bak    |                             |
| **Croatian**            | complete     | Dominik Novosel| Dominik Novosel             |
| **Portuguese**          | complete     | smarquespt     | smarquespt                  |
| **Brazilian**           | complete     | BadJuice67     | Paiusco                     |
| **Romanian**            | complete     | ygorigor       | ygorigor                    |
| **Traditional Chinese** | complete     | Still34        |                             |

## Features
- Notification the day of the event (with selectable time)
- Additional notification up to 7 days before the event
- Fetch the existing birthdays from the contacts app, images included!
- Easily backup and restore your saved events!
- insert an event manually, just specifying a name, an optional surname an image and a date (with optional year)
- Choose your favorite events to see detailed information about them and a countdown for each one
- Tap on an event to see every possible detail and the available actions
- The birthdays are also automagically backed up in cloud by Google Play Services when the app is installed from Play Store
- A set of stats (zodiac, average age and much more) when more than 5 events are inserted, plus an animated counter
- Easily delete, modify and share the saved events!
- Each favorite event has a note field
- Easily hide the top cards to display the list in fullscreen
- A nice searchbar, to quickly search a person in the saved events
- Quick apps launcher
- Light and dark themes (Android 10 dark mode supported)
- Selectable accent (no app restart needed, 12 choices)
- Shimmer effect (it can be disabled) and confetti, because why not
- Name first or surname first choice
- Notification only for favorite events
- Hide and show images
- Dark and light minimal widget
- Beautiful animations (and animated notification icon)
- Multiwindow / freeform full support
- Animated splashscreen and app intro, dynamic layouts and more
- Small and optimized apk size
- Many languages available!

## Screenshots
<p align='center'>
  <img src='https://i.imgur.com/nvZgkQl.png' width='18%'/>
  <img src='https://i.imgur.com/xmMf3YY.png' width='18%'/>
  <img src='https://i.imgur.com/czl7pse.png' width='18%'/>
  <img src='https://i.imgur.com/FHTriHF.png' width='18%'/>
  <img src='https://i.imgur.com/QCAAbfA.png' width='18%'/>

  <img src='https://i.imgur.com/HLWL68m.png' width='18%'/>
  <img src='https://i.imgur.com/DpKmkVl.png' width='18%'/>
  <img src='https://i.imgur.com/TdFaWJc.png' width='18%'/>
  <img src='https://i.imgur.com/PuEyDD1.png' width='18%'/>
  <img src='https://i.imgur.com/ghiz4Ap.png' width='18%'/>
</p>

## Download
The app is available through Google Play and F-Droid\
\
[<img src="https://fdroid.gitlab.io/artwork/badge/get-it-on.png"
     alt="Get it on F-Droid"
     height="80">](https://f-droid.org/packages/com.minar.birday/)
[<img src="https://play.google.com/intl/en_us/badges/images/generic/en-play-badge.png"
     alt="Get it on Google Play"
     height="80">](https://play.google.com/store/apps/details?id=com.minar.birday)
\
There is also a long term support version available, ready to be compiled and used when this repo is messed up by my partial updates. I'm planning to create a separate branch for development and keep the master branch clean and working, but that's a good solution too. Huge thanks to @DominikNovosel for the constant help in the maintenance of this project :) https://github.com/DominikNovosel/Birday-LTS <br><br> *(Dominik's note: Aawww... thanks! ❤️)*

## Credits and contributions
Birday uses some open source libraries, just a few:
- [Material Dialogs](https://github.com/afollestad/material-dialogs)
- [Konfetti](https://github.com/DanielMartinus/Konfetti)
- [App Intro](https://github.com/AppIntro/AppIntro)
- [Shimmer](https://github.com/facebook/shimmer-android)

Currently, Birday supports the languages in the above table. If you want to translate the app in any other language or update an existing translation, just contact me or send a pull request: you'll be quoted both on Github and in the Play Store description. For a detailed guide on how to translate the app, refer to the [Wiki](https://github.com/m-i-n-a-r/birday/wiki/Translate-the-app)

This app was written during my free time as a training. It was first published on May 1, 2020. Many good devs helped me understanding the best practices and they taught me a lot of useful tricks. A special thank to every contributor here and on Reddit. and God bless Stack Overflow.
