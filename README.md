# MyShoe Collection

MyShoe Collection is an app designed to help users catalog and bookmark their favorite shoes, providing a simple and organized way to save and manage a personalized shoe collection. Managing personal shoe collections can be cumbersome, especially for shoe enthusiasts. MyShoe Collection simplifies this process by offering a centralized platform for managing and viewing shoes efficiently. This project is part of the **Udacity Android Kotlin Developer Nanodegree Program**.

**Note**: Offline functionality is not required for this particular project, but it is implemented in [Project02](https://github.com/AliElDerawi/KotlinSpaceRadar) and [Project04](https://github.com/AliElDerawi/GeoAssistant).

## Main Features of the Project

- **MVVM Design Pattern and Clean Architecture**: Ensures a scalable and testable codebase by separating concerns and using a reactive approach.
- **SharedViewModel for State Management**: Allows users to create their own bookmarked shoe list and saves data inside SharedViewModel.
- **Orientation Support**: Supports landscape and portrait modes without data loss or reloading, ensuring a seamless user experience.
- **Simplified Layouts**: Implements simplified layouts using ConstraintLayout, LinearLayout, or FrameLayout to maintain a consistent UI.
- **Reactive UI with DataBindingAdapter**: Integrates DataBindingAdapter for reactive and clean UI components.
- **Dependency Injection with Koin**: Implements Koin for effective dependency management, improving code maintainability.
- **Dynamic Forms using Kotlin Flow**: Uses Kotlin Flow for dynamic filling forms, enabling responsive and interactive UIs.
- **Single Activity Architecture**: Features a single activity with multiple fragments to ensure modular navigation and state retention.
- **Jetpack DataStore**: Implements Jetpack DataStore for storing and retrieving user preferences asynchronously, providing a modern alternative to SharedPreferences.
- **Onboarding Screen**: Includes an onboarding screen to enhance the initial user experience and guide users through the app's features.

## Useful Links

- [Starter Project Code](https://github.com/udacity/nd940-android-kotlin-course1-starter): Get the starter code for the project.
- [Project Rubric](https://docs.google.com/document/d/1n1vvMoQ_cv2E9NDcej7WDQMTqsY096dTPyh7Alkb1_0/edit?usp=sharing): View the project rubric.
- [Android Kotlin Developer Nanodegree Program](https://www.udacity.com/course/android-kotlin-developer-nanodegree--nd940): Learn more about the full program and its related projects.

#### **Note**: Many improvements and features in this project are not included in the Project Rubric as it was initially a project for the Udacity Nanodegree Program.

## Installation Guide

This project does not require any special installation setup. Follow these steps:

- **Clone the Repository**: Run the following command in your terminal:

  ```sh
  git clone https://github.com/AliElDerawi/MyShoeCollection.git

- **Open in Android Studio**: Open the project in Android Studio **Ladybug (2024.2.1 Patch2)**.
- **Build the Project**: Build the project using **Gradle Plugin v(8.7.2)**.

## Main Included External Libraries

- [**Koin v4**](https://github.com/InsertKoinIO/koin): Dependency injection framework, allowing modularity and making the project easier to test.
- [**Timber**](https://github.com/JakeWharton/timber): A lightweight logging utility for debugging purposes.
- [**Glide v4**](http://bumptech.github.io/glide/doc/getting-started.html): Efficient image loading and caching.
- [**DataStore**](https://developer.android.com/topic/libraries/architecture/datastore): Storing and retrieving user preferences asynchronously.
- [**Security Crypto**](https://developer.android.com/jetpack/androidx/releases/security): Encrypts user-sensitive data to ensure security.
- [**CircleIndicator**](https://github.com/ongakuer/CircleIndicator): Provides page indicators for ViewPager.
- [**CircularProgressBar**](https://github.com/lopspower/CircularProgressBar): Implements progress indication for the onboarding screen.
- [**KSP**](https://developer.android.com/build/migrate-to-ksp): Annotation processors plugin for improved build performance.

## Snapshots from the App

### Phone Screens (Portrait)

<div align="center">
<table style="width: 100%; table-layout: fixed;">
  <tr>
    <td align="center" style="width: 50%;">
      <img src="./images/login.jpg" height="666" alt="Login Screen"/>
      <p><strong>Login Screen</strong><br>Demonstrates the initial login functionality of the app.</p>
    </td>
    <td align="center" style="width: 50%;">
      <img src="./images/onboarding.jpg" height="666" alt="Onboarding Screen"/>
      <p><strong>Onboarding Screen</strong><br>Guides users through the key features of the app.</p>
    </td>
  </tr>
  <tr>
    <td align="center" style="width: 50%;">
      <img src="./images/add_shoe_to_bookmark.jpg" height="666" alt="Add Shoe to Bookmark Screen"/>
      <p><strong>Add Shoe to Bookmark Screen</strong><br>Allows users to add their favorite shoes to a bookmark list for easy reference.</p>
    </td>
    <td align="center" style="width: 50%;">
      <img src="./images/flow_form.gif" height="666" alt="Dynamic Filling Form Screen"/>
      <p><strong>Dynamic Filling Form Screen</strong><br>Uses Kotlin Flow to dynamically validate user input in real-time.</p>
    </td>
  </tr>
</table>
</div>

### Phone Screens (Landscape)

<div align="center">
  <img src="./images/bookmark_list_landscape.jpg" width="666" height="300" alt="Bookmark List Screen in Landscape Mode"/>
  <p><strong>Bookmark List Screen in Landscape Mode</strong><br>Displays the bookmarked shoes in an optimized layout for landscape orientation.</p>
</div>

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for improvements or bug fixes. Feel free to reach out if you'd like to add new features or expand on existing ones.

### How to Contribute

- **Fork the Repository**: Click the "Fork" button at the top.
- **Create a Feature Branch**: Branch off from `main` for any features (`git checkout -b feature/your-feature`).
- **Submit a Pull Request**: Submit a PR describing your changes.

## Contact

Feel free to reach out for any collaboration opportunities or if you have any questions. I'd love to hear your thoughts and contributions!
Check my **[GitHub profile](https://github.com/AliElDerawi)**.

## Future Improvements

- **Offline Functionality**: Implement offline capabilities similar to other projects to enhance usability.
- **User Authentication**: Enhance the login process with third-party authentication options (e.g., Google, Facebook).

## License

This project is open-source and licensed under the Apache 2.0 License. The LICENSE file in this repository provides more details.
