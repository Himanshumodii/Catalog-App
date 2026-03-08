Architecture

This project follows Clean Architecture with the MVVM pattern to maintain a clear separation of concerns and improve scalability, testability, and maintainability.

The architecture is divided into three main layers:
presentation → domain → data

Each layer has a specific responsibility and communicates only with its adjacent layer.

1. Presentation Layer
   
   The Presentation layer handles the UI and user interactions.
   
   Components included:
      Activities / Fragments
      ViewModels
      Adapters
      UI state handling
   Responsibilities:
      Display data to the user
      Observe data from ViewModel
      Handle user actions (clicks, search, filtering, etc.)

2. Domain Layer
   
   The Domain layer contains the core business logic of the application.
   It is completely independent of Android framework classes.
   
   Components included:
     Use Cases
     Repository Interfaces
     Domain Models
   
   Responsibilities:
     Define business rules
     Provide a clean API for the Presentation layer
     Remain independent from external frameworks

3. Data Layer
   
    The Data layer is responsible for handling data sources such as APIs, databases, or local storage.
   
    Components included:
       Repository implementations
       API services
       Retrofit client
   
    Responsibilities:
       Fetch data from remote APIs
       Provide data to the Domain layer


Setup Instructions

  Follow these steps to get the project up and running locally.
  
  1. Prerequisites
       Make sure you have the following installed:
       Android Studio (Arctic Fox or later recommended)
       JDK 11 or higher
       Gradle (usually bundled with Android Studio)
       Internet connection (to fetch dependencies)
 
  2. Open in Android Studio
       Launch Android Studio.
       Click on Open an existing project.
       Wait for Gradle to sync all dependencies.
     
  3. Configure API Keys 
       If your project uses APIs (like dummyjson.com):
       Check if there is a local.properties or config file.
       Add your API key in the format:

  4. Build and Run
       Connect an Android device or start an emulator.
       Click Run in Android Studio.
       The app should launch successfully on your device/emulator.

  5. Troubleshooting
       If Gradle fails to sync, try:
       Ensure your internet connection is active to fetch dependencies.
       Update Android Studio and SDK to the latest version if needed.



