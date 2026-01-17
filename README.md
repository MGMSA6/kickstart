# 🚀 Kickstart – Android Project Initializer

Kickstart is an **Android Studio / IntelliJ plugin** that bootstraps Android projects the same way **Spring Initializr** does for backend development.

It generates **project structure, core classes, and dependencies** so developers can focus on **building business logic and screens**, not configuring projects.

---

## 🎯 Why Kickstart?

Starting a new Android project usually means:
- Creating folders manually
- Deciding an architecture
- Adding dependencies
- Configuring Gradle, KSP, DI, networking, and persistence
- Writing repetitive boilerplate code

**Kickstart automates all of this.**

> One click → Clean setup → Start coding 🚀

---

## ✨ Features

### 🧱 Architecture-Based Setup
Choose an architecture and Kickstart generates a clean, scalable structure:

- **MVVM**
- **MVP**
- **MVI** (coming soon)

Each architecture follows **industry best practices**.

---

### 📁 Automatic Project Structure Generation

Example (MVVM):

```
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
```

---

### 🧩 Core Class Generation

Kickstart creates essential base classes to get started quickly:

- Base ViewModel / Presenter
- Repository interfaces
- UseCase templates
- Application class
- Dependency Injection modules
- Network & database setup classes

---

### 📦 Automatic Dependency Configuration

Kickstart configures **latest stable dependencies** automatically:

- Lifecycle / ViewModel
- Kotlin Coroutines
- Room (KSP)
- Retrofit & OkHttp
- Hilt (KSP)
- Testing libraries

✔ Uses **Gradle Version Catalog** when available  
✔ Falls back gracefully for older projects  
✔ Enables **KSP automatically**

---

### ⚙️ Smart Project Detection

Kickstart:
- Works only for **Android projects**
- Detects base package automatically
- Validates existing setup before injecting
- Avoids duplicate dependencies
- Keeps Gradle configuration clean

---

## ▶️ How to Use

1. Open an **Android project**
2. Go to **Tools → Kickstart**
3. Select architecture (**MVVM / MVP / MVI**)
4. Click **Generate**
5. Start building features 🚀

---

## 🧠 Kickstart vs Manual Setup

| Manual Setup | Kickstart |
|-------------|-----------|
| Create folders by hand | Auto-generated |
| Search dependencies | Auto-configured |
| Copy-paste boilerplate | Generated |
| Inconsistent structure | Standardized |
| Time-consuming | Instant |

---

## 🤝 Contributing

Contributions, ideas, and suggestions are welcome.

Kickstart aims to become the **default project initializer for Android developers**.

---

## 📄 License

MIT License
