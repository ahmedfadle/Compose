# 🌆 City Search App

An Android app built with **Jetpack Compose**, **Kotlin**, **MVI**, **Hilt**, and **Clean Architecture** principles.  
It allows users to search and filter cities with a responsive UI and modern architecture practices.

---

## 🧱 Tech Stack

- **Kotlin** - Modern programming language for Android
- **Jetpack Compose** - Declarative UI toolkit
- **MVI (Model-View-Intent)** - Unidirectional data flow pattern
- **Hilt** - Dependency injection framework
- **Coroutines + Flow** - Asynchronous and reactive programming
- **Clean Architecture** - Separation of concerns, testability, scalability

---

## 📂 Project Structure

```plaintext
.
├── data/                # Data layer (DTOs, Retrofit API, Repositories)
├── domain/              # Domain layer (UseCases, Entities, Repositories Interfaces)
├── presentation/        # UI layer (ViewModels, Compose Screens, MVI Intents/States)
├── di/                  # Hilt Modules and Dependency Injection setup
├── utils/               # Utility and helper classes
└── MainApplication.kt   # Hilt Application class
