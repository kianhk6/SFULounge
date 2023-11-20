# SFULounge
With SFU Lounge, you can discover like-minded students who share your interests and academic goals, fostering meaningful connections within the SFU community. Stay informed about the most exciting events on campus, ensuring you never miss out on opportunities. Initiate conversations and meet new friends directly through the in-app chat feature.   

## Introduction
SFU Lounge is an innovative Android application designed specifically for Simon Fraser University (SFU) students. The app facilitates making new friends and connections within the SFU community, particularly addressing the challenge of socializing in a predominantly commuting school. SFU Lounge stands out with its unique features like blind profiles, interest-based matching, and icebreaker chats.

## Features

### User Profile
- **User Database Fields**: ID, First Name, Last Name (hidden), Gender, SFU Email, Password, Interests, Depth Questions (3), Photos (up to 5), Online Status.
- **Swipe Mechanism**: Users can swipe left or right on other user profiles. Swipe actions are stored with user IDs.
- **Chatrooms**: Each match creates a new chatroom with IDs for users and messages.
- **Message System**: Messages in chatrooms are identified by IDs and include timestamps and sender details.

### UI Pages
- **Login/Signup Page**: Start page with options to log in or sign up.
- **Photo Page**: Users upload 2 to 4 photos.
- **Personality Page**: Select up to 4 interests and answer up to 3 depth questions.
- **Swipe Page**: Displays user profiles for swiping.
- **Chat Rooms**: List view of all DMs.
- **Settings Page**: Options to edit user information, interests, depth questions, and photos.

### ViewModel Functions
- **UserViewModel**: Handles login, signup, and settings update functionalities.
- **MatchesViewModel**: Manages the swiping mechanism and user recommendations.
- **ChatViewModel**: Queries chat rooms and messages, handles chat functionalities.

## Implementation Details

### User Profile
- **Images**: 1 to 5 photos allowed.
- **Depth Questions**: Users answer selected questions to add depth to their profiles.
- **Interest Tags**: Users select from a list of interests.
- **Optional Fields**: Major, course list, age, pronouns, voice profiles, location, languages, zodiac sign, SFU club memberships.

### App Mechanics
- **User Recommendations**: Based on shared interests and preferences.
- **Blind Profiles**: Anonymous profiles shown during certain hours, focusing on character rather than appearance.
- **Matching Algorithm**: Matches users based on interests, values, and depth question answers.
- **Icebreaker Chat**: Facilitates easy conversation initiation.


## Show and Tell 1 

## Mock Ups

### The mvmm Model
You can view the document for the mvmm model here: [mvmm Model Document](https://docs.google.com/document/d/142Y2wBGEi41fotJXZjBQioxGrZqqjPv744CmfmjM3T8/edit?usp=sharing)

### Show and Tell Video
Click on the thumbnail below to watch the Show and Tell video:

[![Show and Tell Video](https://img.youtube.com/vi/wpXYgcXO-2Y/0.jpg)](https://youtu.be/wpXYgcXO-2Y)

## Work Completed to Date

- **Login:** Completed
- **Sign Up:** Completed
- **Chatting Ability**
  - Backend: Model
  - Model View
  - UI Prototype
- **Explore Page** (swipes, user recommendation)
  - Backend: Model
  - Model View
  - UI Prototype
- **Settings:** Backend Prototype Completed


## Team Contributions

### Kian Hosseinkhani
- **Management**: Led the team, assigned tasks and bug fixes via GitHub issues, and designed the MVVM diagram prototype.
- Worked on matchmaking features.
- Created database tables, repository functions, and viewmodels.
- Developed user recommendation algorithms.
- Participated in the development of chatting/messaging feature.
- Provided documentation.

### Mathew Wong
- Created database tables, repository functions, and viewmodels 
- Contributed to UI prototyping.
- Implemented email authentication for sign-up.
- Implemented login features.
- Implemented sign up back end and provided a UI prototype for it. 
- Implemented chatting/messaging feature.
- Provided documentation.
- Participated in Bug fixing tasks related to the front end wiring.


### Teeya Li
- Developed/Improved Interest and Deep Questions for the sign-up page.
- Created the Welcome page.
- Contributed to UI prototyping.
- Provided documentation.
- Participated in Bug fixing tasks related to the front end wiring.
  
### Nathalie Kaspar
- Developed images and basic information sign-up page.
- Contributed to UI prototyping.
- Provided documentation.
- Participated in Bug fixing tasks related to the front end wiring.

### Divij Gupta
- Participated in brainstorming meeting for backend database architecture.


## Challenges and Future Developments
- **Anticipated Challenges**: Implementing real-time chatting, email verification, complex UI designs, Firebase database integration, image uploads, and swiping mechanisms.
- **Skill Development**: The team will focus on learning and implementing the above-mentioned features.
- **Future Plans**: Post-semester deployment, advertisement through SFU clubs, and continuous app development for enhancing user experience.

## Getting Started

### Prerequisites
- Android Studio with Kotlin support.
- Firebase account for database and authentication services.

### Installation
1. Clone the repository.
2. Open the project in Android Studio.
3. Configure Firebase and other dependencies.
4. Build and run the application on an emulator or physical device.

## Support and Contact
For support, contact sfu_Lounge_support@gmail.com. Join our community and make your SFU experience unforgettable!
Developers: 
Divij Gupta
Kian Hosseinkhani
Nathalie Kaspar
Teeya Li
Mathew Wong
