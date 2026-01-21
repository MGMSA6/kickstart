# 🚀 Kickstart – Android Architecture Starter Plugin

Kickstart is an **Android Studio / IntelliJ plugin** that helps you bootstrap Android projects instantly by generating a **clean architecture structure**, **core base classes**, and **essential dependencies**.

> Skip boilerplate. Start building features immediately.

---

## 🎯 Why Kickstart?

Starting a new Android project usually means repeating the same setup:
- Creating architecture folders
- Writing base classes and contracts
- Setting up networking and DI
- Managing Gradle dependencies

**Kickstart automates this entire setup**, so you can focus on **business logic and UI** instead of configuration.

---

## 🧱 Common Base Structure (Generated for All Architectures)

This structure is **shared across MVVM, MVP, and MVI** and contains only **architecture-agnostic foundations**.

```text
core/
 ├─ base/
 │   ├─ BasePresenter
 │   └─ BaseView
 ├─ common/
 │   └─ Resource

data/
 ├─ mapper/               # DTO ↔ Domain converters
 ├─ remote/
 │   ├─ api/
 │   │   └─ ApiService
 │   ├─ dto/
 │   └─ interceptor/
 │       └─ LoggingInterceptor
 ├─ repository/           # Repository implementations
 └─ source/
     └─ local/            # Room / local data source

domain/
 ├─ model/                # Pure domain models
 ├─ repository/           # Repository interfaces
 └─ usecase/              # Business logic

di/
 ├─ AppModule              # App-level DI
 └─ NetworkModule          # Retrofit / OkHttp setup

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


🧩 Architecture-Specific Structures

After generating the common base, Kickstart adds architecture-specific folders and classes based on your selection.

🟦 MVVM (Model–View–ViewModel)
presentation/
 └─ feature/
     ├─ viewmodel/        # ViewModels
     ├─ view/             # Activities / Fragments / Screens
     └─ state/            # UI state models

🟩 MVP (Model–View–Presenter)
presentation/
 └─ feature/
     ├─ FeatureContract.kt     # View & Presenter interfaces
     ├─ FeaturePresenter.kt    # Business logic
     └─ FeatureActivity.kt     # UI implementation

🟨 MVI (Model–View–Intent)
presentation/
 └─ feature/
     ├─ FeatureContract.kt     # Intent, State, SideEffect
     ├─ FeatureViewModel.kt    # Reducer & intent handler
     └─ FeatureScreen.kt       # Compose UI

core/
 └─ mvi/
     ├─ BaseMviViewModel
     └─ MviInterfaces

🌐 Networking Setup

Kickstart automatically configures:

Retrofit API service

OkHttp client

Logging interceptor

Centralized error handling

API constants and headers

💉 Dependency Injection

Application-level DI module

Network DI module

Clean and scalable DI setup


📦 Dependency Configuration

Kickstart adds essential dependencies using modern Gradle version catalogs:

Lifecycle & ViewModel

Coroutines

Retrofit & OkHttp

Dependency Injection

KSP (code generation)

▶️ How to Use

Open an Android project

Navigate to Tools → Kickstart

Select your architecture (MVVM / MVP / MVI)

Click OK

Start building features 🚀



