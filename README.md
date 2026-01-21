# 🚀 Kickstart – Android Architecture Starter Plugin

Kickstart is an **Android Studio / IntelliJ plugin** that helps you bootstrap Android projects instantly by generating a **clean architecture structure**, **core base classes**, and **essential dependencies**.

Skip boilerplate. Start building features immediately.

---

# 🎯 Purpose

Kickstart removes repetitive setup work when starting a new Android project by automatically generating:
- Clean package structure
- Core base classes
- Networking and DI foundation
- Architecture-ready feature layers

---

# 🧱 Common Base Structure

This structure is generated **for every project**, regardless of the selected architecture.

core/
 ├─ base/
 │   ├─ BasePresenter
 │   └─ BaseView
 ├─ common/
 │   └─ Resource

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


# 🟦 MVVM (Model–View–ViewModel)

presentation/
 └─ feature/
     ├─ viewmodel/
     ├─ view/
     └─ state/

# 🟩 MVP (Model–View–Presenter)

presentation/
 └─ feature/
     ├─ FeatureContract.kt
     ├─ FeaturePresenter.kt
     └─ FeatureActivity.kt

# 🟨 MVI (Model–View–Intent)

presentation/
 └─ feature/
     ├─ FeatureContract.kt
     ├─ FeatureViewModel.kt
     └─ FeatureScreen.kt

core/
 └─ mvi/
     ├─ BaseMviViewModel
     └─ MviInterfaces

# 🌐 Networking

data/
 └─ remote/
     ├─ api/
     ├─ dto/
     └─ interceptor/


💉 Dependency Injection

di/
 ├─ AppModule
 └─ NetworkModule


# 📦 Dependency Configuration

Kickstart configures dependencies using **Gradle Version Catalogs**, including:

- Lifecycle & ViewModel
- Kotlin Coroutines
- Retrofit & OkHttp
- Dependency Injection
- KSP (Code Generation)

---

# ▶️ Usage

1. Open an Android project
2. Go to **Tools → Kickstart**
3. Select architecture (**MVVM / MVP / MVI**)
4. Confirm generation
5. Start building features 🚀
