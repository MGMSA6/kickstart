# 🚀 Kickstart – Android Architecture Starter

Kickstart is an **Android Studio / IntelliJ plugin** that helps you start Android projects quickly by generating a **clean architecture structure**, **core base classes**, and **essential dependencies**.

The goal is simple:  
👉 **Skip configuration and boilerplate, start building features directly.**

---

## 🎯 Purpose

When starting a new Android project, developers usually spend time on:
- Creating architecture folders
- Adding base contracts and core classes
- Setting up networking and DI
- Configuring dependencies

**Kickstart automates this initial setup**, so you can focus on **business logic and UI** instead of project configuration.

---

## 🧱 Generated Project Structure

Kickstart generates a structured package layout under your base package:

```
core/
 ├─ base/
 │   ├─ BasePresenter
 │   └─ BaseView
 ├─ common/
 │   └─ Resource
 ├─ mvi/
 │   ├─ BaseMviViewModel
 │   └─ MviInterfaces

data/
 ├─ mapper/
 ├─ remote/
 │   ├─ api/
 │   │   └─ ApiService
 │   ├─ dto/
 │   └─ interceptor/
 │       └─ LoggingInterceptor
 ├─ repository/
 └─ source/
     └─ local/

domain/
 ├─ model/
 ├─ repository/
 └─ usecase/

presentation/
 └─ feature/
     └─ FeatureContract

di/
 ├─ AppModule
 └─ NetworkModule

ui/
 └─ theme/
     ├─ Color.kt
     ├─ Theme.kt
     └─ Type.kt

util/
 ├─ NetworkResult
 ├─ DispatcherProvider
 ├─ ApiConstants
 ├─ ErrorHandler
 └─ HeaderProvider

App.kt  
MainActivity.kt
```

---

## 🧩 What Kickstart Sets Up

### ✅ Core Architecture Support
- Base contracts (Presenter / View)
- MVI base ViewModel and interfaces
- Common resource wrapper

### 🌐 Networking Foundation
- Retrofit API service
- OkHttp client
- Logging interceptor
- Network constants and headers
- Centralized error handling

### 💉 Dependency Injection
- Application-level module
- Network module

### 🗂 Domain & Data Layers
- Clear separation of:
  - Data
  - Domain
  - Presentation
- Repository and use-case placeholders

### 🎨 UI Setup
- Jetpack Compose theme files
- Ready-to-use `MainActivity`

---

## 📦 Dependency Configuration

Kickstart configures essential dependencies such as:
- Lifecycle / ViewModel
- Coroutines
- Retrofit & OkHttp
- Dependency Injection
- KSP (for code generation)

Dependencies are added in a **clean and maintainable way**, using modern Gradle practices.

---

## ▶️ How to Use

1. Open an Android project
2. Go to **Tools → Kickstart**
3. Run the generator
4. Start adding screens and business logic 🚀

---

## 🧠 Why Kickstart?

| Without Kickstart | With Kickstart |
|------------------|---------------|
| Manual setup | Ready structure |
| Boilerplate code | Base classes provided |
| Network & DI setup | Pre-configured |
| Slow start | Instant productivity |

Kickstart helps you start projects **cleanly and consistently**.

---

## 📦 Installation

> Currently used as a local plugin  
> Marketplace publishing planned

Run locally with:
```
./gradlew runIde
```

---

## 📄 License

MIT License
