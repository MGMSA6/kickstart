🚀 Kickstart – Android Project Initializer

Kickstart is an Android Studio / IntelliJ plugin that bootstraps Android projects the same way Spring Initializr does for backend development.

It generates project structure, core classes, and dependencies so developers can start building features immediately instead of spending hours on setup and configuration.

🎯 What Problem Does Kickstart Solve?

Starting a new Android project usually means:

Creating folders manually

Deciding architecture

Adding dependencies

Configuring Gradle, KSP, DI, networking, persistence

Writing repetitive boilerplate

Kickstart automates all of this.

👉 You focus on business logic and screens, not project setup.

✨ Key Features
🧱 Architecture-First Project Setup

Choose an architecture and Kickstart generates a clean, scalable structure:

MVVM

MVP

MVI (coming soon)

Each architecture follows industry best practices.

📁 Automatic Project Structure Generation

Example (MVVM):

data/
 ├─ local/
 │   ├─ dao/
 │   └─ entity/
 ├─ remote/
 │   ├─ api/
 │   └─ dto/
 ├─ mapper/
 └─ repository/

domain/
 ├─ model/
 ├─ repository/
 └─ usecase/

ui/
 ├─ main/
 ├─ login/
 └─ dashboard/

di/
utils/

🧩 Core Class Generation

Kickstart creates essential base classes so you don’t start from scratch:

Base ViewModel / Presenter

Repository interfaces

UseCase templates

Application class

Dependency injection modules

Network & database setup classes

📦 Dependency Automation (Zero Guesswork)

Kickstart automatically configures latest stable dependencies, including:

Lifecycle / ViewModel

Coroutines

Room (KSP)

Retrofit & OkHttp

Hilt (KSP)

Testing libraries

✔ Uses Gradle Version Catalog when available
✔ Falls back gracefully for older projects
✔ Enables KSP automatically

⚙️ Smart Project Detection

Kickstart:

Works only on Android projects

Detects base package automatically

Validates existing setup before injecting

Avoids duplicate dependencies

Keeps configuration clean and readable

▶️ How It Works

Open an Android project

Go to Tools → Kickstart

Choose architecture (MVVM / MVP / MVI)

Click Generate

Start building features 🚀

🧠 Why Kickstart?
Without Kickstart	With Kickstart
Manual setup	One-click setup
Copy-paste configs	Auto-configured
Inconsistent structure	Standardized
Boilerplate fatigue	Feature-focused

Kickstart gives Android developers the same experience that Spring Initializr gives backend developers.

🛣️ Roadmap

Planned features:

Clean Architecture support

Feature-based modules

Compose / XML templates

API & repository generators

Architecture preview before apply

Undo / rollback support

Custom presets per team

📦 Installation

🚧 Coming soon to JetBrains Marketplace

For local development:

./gradlew runIde

🤝 Contributing

Ideas, issues, and PRs are welcome.
Kickstart aims to become the go-to initializer for Android projects.

📄 License

MIT License
