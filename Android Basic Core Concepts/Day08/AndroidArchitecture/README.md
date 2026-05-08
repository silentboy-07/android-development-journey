# 📱 Day 08 — Android Architecture Patterns
Learning Android Development — Day by Day

---

## 🤔 What is an Architecture Pattern?

An Architecture Pattern is a **blueprint for how you organise your code**. It tells you where to put your logic, how your files should talk to each other, and how to keep things clean as your app grows.

📱 Imagine building a house — you need a plan before laying bricks. Without a plan, rooms end up in random places and nothing connects properly. Without an architecture pattern, your code ends up the same way — messy, hard to fix, and impossible to grow.

Today we learned five architecture concepts:

| Pattern / Concept | One Line Summary |
|-------------------|-----------------|
| MVVM | View asks ViewModel, ViewModel talks to data |
| MVP | Presenter is the middleman between View and data |
| MVI | One-way flow — User Intent → State → UI |
| Repository Pattern | A single place that manages all data sources |
| Clean Architecture | Layers — each layer has one job and does not cross |

---

## ❓ Why Do We Even Need Architecture?

Without architecture, most beginners write everything inside `MainActivity.kt` — network calls, UI logic, button clicks, database queries — all in one file.

**Real-life analogy**
It is like a restaurant where one person takes your order, cooks the food, washes the dishes, manages the billing, and also cleans the floor. It works for a very small café — but falls apart completely as the restaurant grows.

Architecture separates responsibilities:

| Without Architecture | With Architecture |
|----------------------|------------------|
| One giant file (God Activity) | Code split into focused layers |
| Hard to test | Easy to unit test each part |
| One change breaks everything | Changes are isolated |
| Can't work in a team | Multiple devs work on different layers |
| Hard to add new features | New features slot in cleanly |

---

## 🏛️ Pattern 1 — MVVM (Model — View — ViewModel)

### What is it?

MVVM splits your app into three parts:

| Layer | Job | Example |
|-------|-----|---------|
| **Model** | Holds data and business logic | User data, API calls, database |
| **View** | Shows UI and listens for user actions | Activity, Fragment, XML |
| **ViewModel** | Sits between View and Model, holds UI state | Fetches data, exposes LiveData |

**Real-life analogy**
Think of a **waiter, chef, and menu** in a restaurant. The **View** is the customer (sees the menu, places an order). The **ViewModel** is the waiter (takes the order, brings the food back). The **Model** is the chef and kitchen (actually prepares the data).

### How it works

```
User taps a button
        │
        ▼
View (Activity/Fragment)
        │  calls a function
        ▼
ViewModel
        │  fetches data
        ▼
Model (Repository / API / Database)
        │  returns data
        ▼
ViewModel (updates LiveData / StateFlow)
        │  UI observes the change
        ▼
View updates automatically
```

### Code Example

```kotlin
// Model — data class
data class User(val name: String, val age: Int)

// ViewModel — holds UI state
class UserViewModel : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun loadUser() {
        // In real apps, fetch from Repository
        _user.value = User("Ravi", 22)
    }
}

// View — Activity observes ViewModel
class MainActivity : AppCompatActivity() {

    private val viewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Observe LiveData — UI updates automatically
        viewModel.user.observe(this) { user ->
            binding.tvName.text = user.name
        }

        binding.btnLoad.setOnClickListener {
            viewModel.loadUser()
        }
    }
}
```

### Advantages & Disadvantages

| ✅ Advantages | ❌ Disadvantages |
|--------------|-----------------|
| Google's officially recommended pattern | ViewModel can become large ("fat ViewModel") |
| Works perfectly with Jetpack (LiveData, StateFlow) | Slight learning curve for beginners |
| Easy to unit test ViewModel independently | Overkill for very small apps |
| Survives screen rotation automatically | |

### 🏭 Industry Usage
MVVM is the **most widely used** Android architecture today. Used by Google, Uber, Airbnb, and most modern Android teams. If you learn one pattern — learn this one first.

---

## 🤝 Pattern 2 — MVP (Model — View — Presenter)

### What is it?

MVP is similar to MVVM but uses a **Presenter** instead of a ViewModel. The key difference — the View and Presenter know about each other through **interfaces**.

| Layer | Job |
|-------|-----|
| **Model** | Data and business logic |
| **View** | Shows UI — defined as an interface |
| **Presenter** | Handles all logic — talks to both View and Model |

**Real-life analogy**
Think of a **call centre**. The **View** is the customer on the phone. The **Presenter** is the customer service agent — they take the customer's request, go find the answer (Model), and report back directly. The agent and customer are always in direct two-way contact.

### Code Example

```kotlin
// View Interface — what the Presenter can tell the View to do
interface UserView {
    fun showUser(name: String)
    fun showError(message: String)
}

// Presenter — all logic lives here
class UserPresenter(private val view: UserView) {

    fun loadUser() {
        // Fetch data, then tell the View what to show
        val name = "Ravi"  // In real app: fetch from Model
        if (name.isNotEmpty()) {
            view.showUser(name)
        } else {
            view.showError("User not found")
        }
    }
}

// Activity implements the View Interface
class MainActivity : AppCompatActivity(), UserView {

    private lateinit var presenter: UserPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = UserPresenter(this)
        binding.btnLoad.setOnClickListener { presenter.loadUser() }
    }

    override fun showUser(name: String) {
        binding.tvName.text = name
    }

    override fun showError(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
```

### Advantages & Disadvantages

| ✅ Advantages | ❌ Disadvantages |
|--------------|-----------------|
| Very easy to unit test the Presenter | Presenter holds a reference to View — memory leak risk |
| Clean separation via interfaces | Does NOT survive screen rotation automatically |
| Great for teams who know Java/OOP well | More boilerplate than MVVM |
| Easier to understand than MVVM for some | Interfaces can feel like extra work |

### 🏭 Industry Usage
MVP was the **standard Android pattern before MVVM**. Still found in older codebases and legacy enterprise apps. Good to know when maintaining or reading older projects.

---

## 🔄 Pattern 3 — MVI (Model — View — Intent)

### What is it?

MVI is a **unidirectional (one-way) data flow** pattern. Everything moves in one direction — no two-way communication.

| Layer | Job |
|-------|-----|
| **Model** | Represents the complete UI state at any moment |
| **View** | Renders the state and sends user Intents |
| **Intent** | Describes what the user wants to do (not Android Intent!) |

> ⚠️ **Intent here does NOT mean Android's `Intent` class.** In MVI, Intent means "a user's intention" — like "user wants to load data" or "user clicked search".

**Real-life analogy**
Think of a **vending machine**. You press a button (Intent). The machine processes it and changes its internal state (Model). The display updates to show the new state (View). The flow only goes one way — you cannot reach inside the machine and change the state directly.

### How it works

```
User Action (e.g. tap Load)
        │
        ▼
Intent (LoadUserIntent)
        │
        ▼
Reducer / ViewModel processes the Intent
        │
        ▼
New State is produced (UserState.Loading → UserState.Success)
        │
        ▼
View renders the new State
        │
        ▼
(cycle repeats for every action)
```

### Code Example

```kotlin
// State — represents every possible screen condition
sealed class UserState {
    object Loading : UserState()
    data class Success(val name: String) : UserState()
    data class Error(val message: String) : UserState()
}

// Intent — every action the user can take
sealed class UserIntent {
    object LoadUser : UserIntent()
}

// ViewModel processes Intents and produces States
class UserViewModel : ViewModel() {

    private val _state = MutableStateFlow<UserState>(UserState.Loading)
    val state: StateFlow<UserState> = _state

    fun processIntent(intent: UserIntent) {
        when (intent) {
            is UserIntent.LoadUser -> loadUser()
        }
    }

    private fun loadUser() {
        _state.value = UserState.Loading
        // Fetch data...
        _state.value = UserState.Success("Ravi")
    }
}

// View renders state
lifecycleScope.launch {
    viewModel.state.collect { state ->
        when (state) {
            is UserState.Loading -> showProgress()
            is UserState.Success -> binding.tvName.text = state.name
            is UserState.Error   -> showError(state.message)
        }
    }
}
```

### Advantages & Disadvantages

| ✅ Advantages | ❌ Disadvantages |
|--------------|-----------------|
| Predictable — state is the single source of truth | Most complex to learn and set up |
| Easy to debug — every state change is traceable | A lot of boilerplate code |
| Perfect for complex UIs with many states | Overkill for simple screens |
| Great for Jetpack Compose | Intent/State classes can multiply quickly |

### 🏭 Industry Usage
MVI is gaining popularity with **Jetpack Compose** apps and large teams. Used heavily in companies like Spotify, Netflix (React-inspired thinking applied to Android). Ideal for complex screens where state management is difficult.

---

## 🗄️ Repository Pattern

### What is it?

The Repository Pattern is **not a full architecture** on its own — it is a design pattern used inside MVVM, MVP, and MVI. It creates a **single source of truth** for all your data.

**Real-life analogy**
Think of a **library librarian**. Whether you want a book from the shelf, from the storage room, or ordered from another library — you always ask the same librarian. You do not care where the book physically comes from. The librarian handles that. The Repository is that librarian.

### Without Repository vs With Repository

```
❌ Without Repository (ViewModel talks to everything directly):

ViewModel → Retrofit (API)
ViewModel → Room (Database)
ViewModel → SharedPreferences
ViewModel → Cache

✅ With Repository (ViewModel talks to one place):

ViewModel → Repository → Retrofit (API)
                       → Room (Database)
                       → SharedPreferences
                       → Cache
```

### Code Example

```kotlin
// Repository — single source of truth
class UserRepository(
    private val apiService: ApiService,       // remote data
    private val userDao: UserDao              // local database
) {
    suspend fun getUser(id: String): User {
        return try {
            val user = apiService.getUser(id)  // try network first
            userDao.insertUser(user)            // cache it locally
            user
        } catch (e: Exception) {
            userDao.getUser(id)                 // fallback to local
        }
    }
}

// ViewModel uses Repository — does not know WHERE data comes from
class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun loadUser(id: String) {
        viewModelScope.launch {
            _user.value = repository.getUser(id)
        }
    }
}
```

### Advantages & Disadvantages

| ✅ Advantages | ❌ Disadvantages |
|--------------|-----------------|
| ViewModel stays clean and simple | Extra class to write and maintain |
| Easy to switch data source (API → local DB) | Can feel like unnecessary abstraction for tiny apps |
| Easy to mock in unit tests | |
| Centralises all data logic | |

### 🏭 Industry Usage
The Repository Pattern is **used in virtually every professional Android app** today. It is a core part of Google's recommended architecture guide. You will see it in almost every job interview question too.

---

## 🏗️ Clean Architecture

### What is it?

Clean Architecture (introduced by Robert C. Martin / Uncle Bob) organises your entire app into **concentric layers**. The rule is simple: **outer layers depend on inner layers — never the other way around**.

```
┌─────────────────────────────────┐
│         Presentation Layer      │  ← UI, ViewModel, Activity
│  ┌───────────────────────────┐  │
│  │      Domain Layer         │  │  ← UseCases, Business Rules
│  │  ┌─────────────────────┐  │  │
│  │  │    Data Layer        │  │  │  ← Repository, API, Database
│  │  └─────────────────────┘  │  │
│  └───────────────────────────┘  │
└─────────────────────────────────┘
         ↑ dependencies flow inward only
```

### The Three Layers Explained

**1. Presentation Layer** — What the user sees
- Activities, Fragments, Composables
- ViewModels
- Only talks to the Domain layer

**2. Domain Layer** — The brain of the app
- UseCases (also called Interactors)
- Business rules — "what the app actually does"
- Pure Kotlin — NO Android imports here
- Completely independent — can be tested without Android

**3. Data Layer** — Where data lives
- Repositories (implementations)
- Retrofit (API)
- Room (Database)
- Only the Domain layer can talk to this layer

### Code Example

```kotlin
// DOMAIN LAYER — Pure Kotlin, zero Android dependencies

// Entity — core data model
data class User(val id: String, val name: String)

// Repository Interface — defined in Domain, implemented in Data
interface UserRepository {
    suspend fun getUser(id: String): User
}

// UseCase — one specific business action
class GetUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(id: String): User {
        return repository.getUser(id)
    }
}

// DATA LAYER — implements the repository interface
class UserRepositoryImpl(
    private val apiService: ApiService,
    private val userDao: UserDao
) : UserRepository {
    override suspend fun getUser(id: String): User {
        return apiService.getUser(id)
    }
}

// PRESENTATION LAYER — ViewModel uses UseCase
class UserViewModel(
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun loadUser(id: String) {
        viewModelScope.launch {
            _user.value = getUserUseCase(id)
        }
    }
}
```

### Advantages & Disadvantages

| ✅ Advantages | ❌ Disadvantages |
|--------------|-----------------|
| Maximum separation of concerns | Most complex setup of all patterns |
| Domain layer is 100% testable without Android | Lots of files and folders |
| Easy to swap UI or data source without touching business logic | Takes time to understand fully |
| Scales well to large teams and codebases | Overkill for small or personal projects |

### 🏭 Industry Usage
Clean Architecture is used in **large-scale production apps** at companies like Google, Grab, Mercado Libre, and most fintech companies. It is the gold standard for apps that will be maintained for years. You will see it heavily in senior Android developer interviews.

---

## 📊 All Patterns Side by Side

| | MVVM | MVP | MVI | Repository | Clean Architecture |
|--|------|-----|-----|------------|-------------------|
| **Complexity** | Medium | Medium | High | Low | Very High |
| **Beginner Friendly** | ✅ Yes | ✅ Yes | ❌ No | ✅ Yes | ❌ No |
| **Google Recommended** | ✅ Yes | ❌ No | ⚡ With Compose | ✅ Yes | ✅ Yes |
| **Survives Rotation** | ✅ Yes | ❌ No | ✅ Yes | — | ✅ Yes |
| **Testability** | High | High | Very High | High | Maximum |
| **Used In** | Most modern apps | Legacy apps | Complex UIs | All apps | Large apps |
| **Best For** | Most apps | Older codebases | Complex state | Data management | Enterprise/Scale |

---

## 🔄 How They All Fit Together

These patterns are **not competitors** — they work together:

```
A real production app typically uses:

Clean Architecture (overall structure)
        +
MVVM (presentation layer pattern)
        +
Repository Pattern (data layer pattern)
        +
MVI (for complex screens inside MVVM)
```

> 💡 Start with **MVVM + Repository**. That covers 80% of real Android projects. Add Clean Architecture layers when your app grows.

---

## 📌 Key Rules to Remember

- MVVM is Google's recommended pattern — learn this first
- The ViewModel in MVVM survives screen rotation — Activities do not
- In MVP, the Presenter holds a View reference — always detach it to avoid memory leaks
- In MVI, Intent does NOT mean Android's Intent class — it means "user's intention"
- The Repository is the single source of truth for data — ViewModel should never call API or DB directly
- In Clean Architecture, the Domain layer must have zero Android imports — pure Kotlin only
- Outer layers depend on inner layers — inner layers never depend on outer layers
- Start simple — add architecture complexity only when your app actually needs it

---

## 🧠 Quick Memory Tricks

| Concept | Remember As |
|---------|------------|
| MVVM | "View watches ViewModel like a TV watches a cable box" |
| MVP | "Presenter is the middleman — knows both sides" |
| MVI | "Vending machine — press button, get new state, no shortcuts" |
| Repository | "The librarian — you ask one person, they handle the rest" |
| Clean Architecture | "Onion layers — inner layers never know what is outside them" |
| Domain Layer | "The brain — pure logic, zero UI, zero database" |
| UseCase | "One class, one job — GetUser, SaveUser, DeleteUser" |
| Single Source of Truth | "One place stores the real data — everyone else reads from there" |

---

📚 Learning Android step by step. Follow along if you're learning too!
