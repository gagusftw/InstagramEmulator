# Project 3 - *Instagram Emulator*

**Instagram Emulator** is a photo sharing app similar to Instagram but using Parse as its backend.

Time spent: **8** hours spent in total

## User Stories

The following **required** functionality is completed:

- [x] User can sign up to create a new account using Parse authentication.
- [x] User can log in and log out of his or her account.
- [x] The current signed in user is persisted across app restarts.
- [x] User can take a photo, add a caption, and post it to "Instagram".

The following **optional** features are implemented:

- [x] User sees app icon in home screen and styled bottom navigation view
- [ ] Style the feed to look like the real Instagram feed.
- [ ] After the user submits a new post, show an indeterminate progress bar while the post is being uploaded to Parse.

The following **additional** features are implemented:

- [x] Implemented groundwork for switching tabs with BottomNavigationView

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='./walkthroughInstagramEmulator.gif' title='Video Walkthrough' width='400' alt='Video Walkthrough' />

## Notes

I attempted to write most of this with my own ideas and structure, and my setup of switching activities based on the bottom navigation may not be the best. I also struggled a lot to launch the camera and manage the Bitmap afterwards. A large amount of my time was spent setting up the layout and activity logic versus utilizing the Parse service. Additionally, I have not fixed the rotation of my photos in the application.

## Open-source libraries used

- [BitmapScaler.java](https://gist.github.com/nesquena/3885707fd3773c09f1bb) - Used to help scale Bitmap received from camera

## License

    Copyright 2021 Joseph Barshay

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
