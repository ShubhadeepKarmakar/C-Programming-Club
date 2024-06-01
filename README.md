
# [**C Programming Club**](https://www.cprogrammingclub.com/): [**Appwrite**](https://appwrite.io) [**Hashnode**](https://hashnode.com) Hackathon

![appwritehackathonnnnnnnnnnnn](https://github.com/ShubhadeepKarmakar/cprogrammingclub/assets/99060332/a3406666-d976-4ae7-baa5-f2c5d1ea676b)


I thought of making this app a few months back but didn't get the right time to do it. When I reached out about the **Appwrite X Hashnode Hackathon**, I got out of my comfort zone and started building it. After working day and night for a whole month, I finally made it.

## Team Details
I made this project single-handedly.
- Shubhadeep Karmakar - @shubhadeep


## Description
**C-Programming-Club** is a **C** language learning app for beginners, who have just started their programming journey or just want to learn **C programming**.

**C** is an easy programming language to learn and if one has a thorough understanding of **C** then one can easily learn any other programming language easily.

After installing this **App**, you don't need to go anywhere to learn about **C**, because in this **App** you will get a **chapter-wise reading** with **video lectures**. If you get stuck somewhere, you can ask for help in the **In-app Community** section, where your fellow learners or **C-Programming-Club** experts will help you out.
Also, there are chapter-wise **problems** and **quizzes** to test your preparation.

Since your reading and quiz participation is tracked, after completing the entire reading and quizzes, you will be able to **claim your certificate**. 
### Some key features of this app:
* **Video Lecture with Proper Reading Content:**
Each chapter has its video lecture for proper understanding of the respective chapter, and proper reading material if anything is left.
* **Chapter Quizzes:**
Each chapter has a separate quiz section where you can test your command of the respective chapters.
* **In-App Comunity:**
This community feature is built from scratch with ***Appwrite Realtime***, not inherited from other community providers or any API connections.
Here you can connect with your classmates and ask your programming doubts.
* **Notes Taking:**
You may want to take notes while studying, this **note-taking** feature is directly connected to the ***Appwrite database*** so you can retrieve full notes anytime by logging into this app.
* **Become Interview Ready:**
Here are **100+** interview questions to help you prepare for your upcoming coding interview.
* **Completion certificate:**
Once your progress is 100%, you will be eligible to claim your certificate.

## Tech Stack

- **Android Kotlin**
  - MVVM Architecture
  - Dependency Injection(Hilt)
- **Markdown Viewer**
  - [https://github.com/tiagohm/MarkdownView](https://github.com/tiagohm/MarkdownView)
- **Animation**
   - [Lottie animation](https://lottiefiles.com/)

- [**Appwrite Cloud**](https://cloud.appwrite.io/)
    - Authentication
      - To authenticate the user with an Email Id or various OAuth providers(Google, GitHub, Linkedin, etc.)
    - Database
      - As there are lots of separate parts in this app, the database is used in various sections, to store chapter content, quizzes,  study notes, user data, community chats, and so on.
    - Realtime
      - This feature is used in the community section of the app to make real-time changes, when a user sends a message, all users will receive the update simultaneously. 
    - Storage 
      - To store markdown files and some images.

## Challenges I Faced
- Implementing Authentication 
 - Appwrite documentation is very easy to understand but unfortunately, I faced some problems while implementing **OAuth**, but the community support is very good. After dropping the issue on the Appwrite Discord server, I got the solution within an hour.
- Implementing Video Player
 - Since the YouTube Android Player API has been deprecated, I had to look for an alternative. And then I came up with the IFrame library.
- Handling ANR issues with Coroutines due to many database connections working simultaneously.

## Public Code Repo
**GitHub Repo Link**- [https://github.com/ShubhadeepKarmakar/cprogrammingclub](https://github.com/ShubhadeepKarmakar/cprogrammingclub)
## Demo Link
[YouTube Video Link](https://www.youtube.com/watch?v=cKV2ZTdN7Hk&ab_channel=ShubhadeepKarmakar)
<!--- Add a link to the demo recording of your project in this section -->
## App Download Link
[Download Apk](https://drive.google.com/file/d/1KbDdLB1EzAx4h5TpB9RjpuXdsuj0wJME/view?usp=sharing)

You can sign up with your own credentials or use one of the demo credentials below

**Note:** These credentials are for testing purposes only.

| email    | Password |
| -------- | ------- |
| a@gmail.com  | 12345678  |
| b@gmail.com | 12345678  |


#Appwrite #AppwriteHackathon
