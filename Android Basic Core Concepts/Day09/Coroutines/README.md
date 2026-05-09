# 📱 Day 09 — Kotlin Coroutines in Android

> **Learning Android Development — Day by Day**

---

## 🤔 What Problem Do Coroutines Solve?

Android runs your entire app on a single thread called the **Main Thread** (also called the UI Thread). This thread is responsible for:

- Drawing every button, text view, and animation on screen
- Responding to user taps
- Running your code

If you run a slow task on this thread — like downloading data from the internet or reading a large file — the thread gets **blocked**. While it is blocked, nothing can be drawn and no taps can be registered. The screen freezes.

If the freeze lasts more than **5 seconds**, Android shows an **ANR (App Not Responding)** dialog and asks the user to force-close your app. This is one of the most common reasons users uninstall apps.

### Real-life analogy

> Imagine a single cashier at a supermarket (the Main Thread). If one customer asks the cashier to personally go to the warehouse and fetch a product (blocking network call), the entire queue freezes. Everyone else waits. The shop is broken.
>
> Coroutines are like giving the cashier a walkie-talkie — they can radio the warehouse (IO thread) to fetch the product while continuing to serve other customers. When the product arrives, they handle that customer too.

---

## 💡 What Are Coroutines?

Coroutines are Kotlin's way to write **asynchronous code that looks synchronous**. They let you run slow tasks on background threads and then come back to update the UI — all in clean, readable, straight-line code.

```kotlin
viewModelScope.launch {
    val user = fetchUser()             // runs on IO thread
    binding.tvName.text = user.name   // back on Main thread
}
```

That is it. No callbacks. No complex thread management. No `AsyncTask`. Just normal-looking code that works correctly.

---

## 🔑 Four Keywords You Must Know

### 1. `suspend` — "this function can pause"

The `suspend` keyword marks a function that can **pause its execution** without blocking the thread. While it is paused, the thread is free to do other work.

```kotlin
suspend fun fetchUser(id: Int): User {
    return withContext(Dispatchers.IO) {
        api.getUser(id)   // this pauses the coroutine while waiting for the network
    }
}
```

> A `suspend` function can only be called from inside another `suspend` function or from inside a coroutine.

---

### 2. `launch` — "start and forget"

`launch` starts a new coroutine. It does **not return a result**. Use it when you just want to run a task and do not need the answer back.

```kotlin
viewModelScope.launch {
    repository.saveUser(user)
    _message.value = "User saved!"
}
```

Think of `launch` like sending a text message — you send it and carry on. You do not wait for a reply.

---

### 3. `async` / `await` — "start and give me the result"

`async` starts a coroutine that **returns a value** wrapped in a `Deferred`. Call `.await()` to get the actual value when it is ready.

```kotlin
viewModelScope.launch {
    val result = async { fetchUser(id) }
    val user = result.await()          // waits here, but does NOT block the thread
    binding.tvName.text = user.name
}
```

The real power of `async` is running **multiple tasks in parallel**:

```kotlin
viewModelScope.launch {
    val userDeferred  = async { fetchUser(id) }   // starts immediately
    val postsDeferred = async { fetchPosts(id) }  // also starts immediately

    val user  = userDeferred.await()   // wait for both
    val posts = postsDeferred.await()

    // Total time = max(user fetch time, posts fetch time)
    // NOT user fetch time + posts fetch time
    _state.value = UserWithPosts(user, posts)
}
```

---

### 4. `withContext` — "switch thread for this block, then come back"

`withContext` switches the **Dispatcher** (thread) for a block of code, then automatically switches back when done. This is the most common coroutine pattern in Android.

```kotlin
viewModelScope.launch {
    // Currently on: Main thread (viewModelScope default)

    val user = withContext(Dispatchers.IO) {
        // Now on: IO thread — safe to do network/database work
        repository.getUser(id)
    }

    // Back on: Main thread — safe to update UI
    binding.tvName.text = user.name
}
```

---

## 📦 Step-by-Step Setup

### Step 1 — Add dependencies

In `build.gradle (Module: app)`:

```gradle
dependencies {
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3"
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0"
    implementation "androidx.lifecycle:lifecycle-runtime-ktx:2.7.0"
}
```

### Step 2 — Create a suspend function

```kotlin
class UserRepository {
    suspend fun getUser(id: Int): User {
        return withContext(Dispatchers.IO) {
            api.fetchUser(id)
        }
    }

    suspend fun saveUser(user: User) {
        withContext(Dispatchers.IO) {
            database.userDao().insert(user)
        }
    }
}
```

### Step 3 — Use it in a ViewModel

```kotlin
class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    fun loadUser(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true

            try {
                val user = repository.getUser(id)
                _user.value = user
            } catch (e: Exception) {
                Log.e("ViewModel", "Failed to load user", e)
            } finally {
                _isLoading.value = false
            }
        }
    }
}
```

### Step 4 — Observe in your Fragment

```kotlin
class UserFragment : Fragment() {

    private val viewModel: UserViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadUser(id = 42)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.user.collect { user ->
                    user?.let { binding.tvName.text = it.name }
                }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collect { loading ->
                    binding.progressBar.isVisible = loading
                }
            }
        }
    }
}
```

---

## 🌐 Coroutine Scopes

A **Scope** manages the lifetime of coroutines. When a scope is cancelled, every coroutine running inside it is cancelled automatically.

### `viewModelScope`

The most commonly used scope in Android. Tied to the ViewModel — cancelled automatically when the user leaves the screen (ViewModel is cleared).

```kotlin
class MyViewModel : ViewModel() {
    fun loadData() {
        viewModelScope.launch {
            val data = repository.getData()
            _state.value = data
        }
    }
    // When user leaves, viewModelScope is cancelled.
    // The coroutine stops even if the network call is still running.
}
```

### `lifecycleScope`

Tied to an Activity or Fragment. Cancelled when the Activity or Fragment is destroyed. Good for UI-related work that must stop when the screen disappears.

```kotlin
class MyFragment : Fragment() {
    override fun onViewCreated(view: View, ...) {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                binding.tvData.text = state.text
            }
        }
    }
}
```

### `coroutineScope { }` (inside suspend functions)

Creates a structured scope inside a `suspend` function. Waits for all children to finish before returning. If one child fails, all others are cancelled.

```kotlin
suspend fun loadDashboard(): Dashboard = coroutineScope {
    val users  = async { repository.getUsers() }
    val stats  = async { repository.getStats() }
    val news   = async { repository.getNews() }

    Dashboard(
        users  = users.await(),
        stats  = stats.await(),
        news   = news.await()
    )
}
```

### `GlobalScope` — avoid this

Lives for the entire app lifetime. Never cancelled. Can cause memory leaks. Almost never needed in app code.

```kotlin
// ❌ AVOID — leaks even when user leaves the screen
GlobalScope.launch {
    repository.syncData()
}

// ✅ USE THIS INSTEAD
viewModelScope.launch {
    repository.syncData()
}
```

---

## 🚦 Dispatchers — Choosing the Right Thread

A **Dispatcher** tells a coroutine which thread to run on.

### `Dispatchers.Main`

Runs on the Android Main Thread. The only dispatcher that can safely update the UI. This is the default for `viewModelScope`.

```kotlin
withContext(Dispatchers.Main) {
    binding.tvName.text = "Loaded!"
    binding.progressBar.isVisible = false
}
```

### `Dispatchers.IO`

Uses a pool of threads optimized for I/O waiting. Up to 64 threads by default. Use this for anything that waits — network calls, database, file reads.

```kotlin
withContext(Dispatchers.IO) {
    val response = api.getUsers()
    database.userDao().insertAll(response)
}
```

### `Dispatchers.Default`

Uses threads equal to the number of CPU cores. Optimized for CPU-intensive computation — not waiting.

```kotlin
withContext(Dispatchers.Default) {
    val sorted = hugeList.sortedBy { it.name }
    val result = computeHeavyMath()
}
```

### Dispatcher quick-pick table

| Task | Dispatcher |
|---|---|
| Update a TextView or Button | `Main` |
| Retrofit / OkHttp network call | `IO` |
| Room database query | `IO` |
| Read / write a file | `IO` |
| Sort a 100,000-item list | `Default` |
| Parse large JSON | `Default` |
| Image processing / compression | `Default` |

---

## ⚠️ Error Handling

Always wrap your coroutine body in `try / catch`. Unhandled exceptions in `launch` crash the app silently in some scopes.

```kotlin
viewModelScope.launch {
    try {
        val user = repository.getUser(id)
        _state.value = UiState.Success(user)
    } catch (e: IOException) {
        _state.value = UiState.Error("Network error — please try again")
    } catch (e: Exception) {
        _state.value = UiState.Error("Something went wrong")
    }
}
```

### Using `CoroutineExceptionHandler` for global errors

```kotlin
val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.e("Coroutine", "Unhandled exception", throwable)
    _errorMessage.value = throwable.message
}

viewModelScope.launch(exceptionHandler) {
    val user = repository.getUser(id)
    _user.value = user
}
```

---

## 🔄 The Complete Flow

```
User taps "Load Profile" button
        │
        ▼
Fragment calls viewModel.loadUser(id)
        │
        ▼
viewModelScope.launch {          ← runs on Main thread by default
        │
        ▼
    withContext(Dispatchers.IO) { ← switches to IO thread
        api.getUser(id)           ← network call (coroutine PAUSED here)
    }                             ← switches back to Main thread
        │
        ▼
    _user.value = user            ← updates StateFlow on Main thread
}
        │
        ▼
Fragment's collect { } runs       ← UI updates automatically
        │
        ▼
binding.tvName.text = user.name   ← user sees the name on screen
```

---

## 📌 Key Rules to Remember

1. **Never run slow work on the Main Thread** — always use `withContext(Dispatchers.IO)` or `withContext(Dispatchers.Default)`
2. **Always use `viewModelScope`** in ViewModels — it cancels automatically and prevents leaks
3. **`suspend` functions can only be called from coroutines** or other `suspend` functions
4. **Use `launch` when you do not need a result** — use `async/await` when you do
5. **Always handle exceptions** with `try/catch` inside your coroutine
6. **Never use `GlobalScope`** in app code — it never cancels and causes memory leaks
7. **`delay()` is not `Thread.sleep()`** — `delay()` suspends without blocking the thread; `Thread.sleep()` blocks it

---

## 🧠 Quick Memory Tricks

| Concept | Remember As |
|---|---|
| Main Thread | The single cashier — cannot be blocked |
| Coroutine | A task you can pause and resume freely |
| `suspend` | "Press pause" — thread stays free |
| `launch` | Send a text — fire and forget |
| `async/await` | Order food — get a number, collect when ready |
| `withContext` | Temporarily switch to another department |
| `viewModelScope` | Scope that dies with the screen |
| `Dispatchers.IO` | Waiting room — for network and database |
| `Dispatchers.Default` | Workshop — for CPU work |
| `Dispatchers.Main` | Reception desk — only place to touch UI |

---

## 🔗 What's Next?

- Project02 ( Implementing Room Database & MVVM in ToDoApp )

---

*📚 Learning Android step by step. Follow along if you're learning too!*
