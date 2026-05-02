# 📱 Day 02 — Android ViewModel

> **Learning Android Development — Day by Day**  

---

## 🤔 What Problem Does ViewModel Solve?

Before learning ViewModel, you need to understand **the problem it fixes**.

### The Screen Rotation Problem

Imagine you're building a counter app. The user clicks `+` 10 times — the score is now **10**. Then they rotate their phone.

**What happens?**

Android **destroys** the Activity and **recreates** it from scratch. Your score? **Gone. Reset to 0.**

This happens not just for rotation — but also for:
- Changing phone language
- Switching dark/light mode
- Low memory situations

This is called a **configuration change**, and it's one of the most common beginner frustrations in Android. ViewModel is the fix.

---

## 💡 What is ViewModel?

A **ViewModel** is a special class that stores your screen's data **outside** the Activity.

Think of it like this:

> 🎒 **Activity** = You (the person walking around)  
> 📦 **ViewModel** = Your locker at school

When you go home and come back (Activity destroyed and recreated), **your locker is still there** with all your stuff inside.

The ViewModel lives **longer** than the Activity. When the Activity is recreated after rotation, it reconnects to the same ViewModel and gets the data back.

---

## 🔄 The Lifecycle Comparison

```
WITHOUT ViewModel:
──────────────────
User taps + (score = 10)
    │
    ▼
Screen rotates → Activity DESTROYED → score LOST
    │
    ▼
Activity RECREATED → score = 0 😭


WITH ViewModel:
───────────────
User taps + (score = 10)  →  ViewModel stores score = 10
    │                              │
    ▼                              │ (ViewModel survives!)
Screen rotates → Activity DESTROYED │
    │                              │
    ▼                              │
Activity RECREATED ────────────────┘
    │
    ▼
Activity asks ViewModel → "score is 10" ✅
```

---

## 🛠️ How to Use ViewModel — Step by Step

### Step 1: Add the dependency

In your `build.gradle (Module)` file, add:

```gradle
dependencies {
    implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0"
}
```

---

### Step 2: Create your ViewModel class

```kotlin
import androidx.lifecycle.ViewModel

class CounterViewModel : ViewModel() {

    // This is your data — stored safely inside ViewModel
    var score = 0
        private set  // Only ViewModel can change it directly

    fun increment() {
        score++
    }

    fun reset() {
        score = 0
    }
}
```

> 💡 Notice: `CounterViewModel` extends `ViewModel`. That's all you need to get started!

---

### Step 3: Use the ViewModel in your Activity

```kotlin
import androidx.activity.viewModels

class MainActivity : AppCompatActivity() {

    // This is how you get (or create) the ViewModel
    private val viewModel: CounterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Display the current score (restored after rotation!)
        updateUI()

        // When + button is clicked
        binding.btnIncrement.setOnClickListener {
            viewModel.increment()
            updateUI()
        }
    }

    private fun updateUI() {
        binding.tvScore.text = viewModel.score.toString()
    }
}
```

> 💡 `by viewModels()` is Kotlin magic — it creates the ViewModel the first time and returns the **same one** every time after (even after rotation).

---

## 🔗 ViewModel + LiveData (The Power Combo)

The example above works, but there's a better pattern: use **LiveData** with your ViewModel.

**LiveData** is an observable data holder — when the value changes, it automatically updates the UI. You don't even need to call `updateUI()` manually!

```kotlin
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CounterViewModel : ViewModel() {

    // MutableLiveData = can be changed (private to ViewModel)
    private val _score = MutableLiveData(0)

    // LiveData = read-only (exposed to Activity)
    val score: LiveData<Int> = _score

    fun increment() {
        _score.value = (_score.value ?: 0) + 1
    }

    fun reset() {
        _score.value = 0
    }
}
```

```kotlin
class MainActivity : AppCompatActivity() {

    private val viewModel: CounterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Observe the score — UI updates AUTOMATICALLY when score changes!
        viewModel.score.observe(this) { newScore ->
            binding.tvScore.text = newScore.toString()
        }

        binding.btnIncrement.setOnClickListener {
            viewModel.increment()
            // No need to call updateUI() — LiveData does it for us!
        }
    }
}
```

> 💡 `observe(this) { ... }` means: "whenever `score` changes, run this block."

---

## ✅ What TO Store in ViewModel

| Store this | Example |
|---|---|
| UI data / state | counter value, form inputs |
| Search results | list of items from API |
| User selections | which tab is selected |
| Loading state | `isLoading = true/false` |
| Error messages | "Network failed" string |

## ❌ What NOT to Store in ViewModel

| Don't store this | Why |
|---|---|
| `Context` or `Activity` | ViewModel lives longer — causes memory leak |
| `View` references | Same reason — causes memory leak |
| `Fragment` references | Will crash when Fragment is destroyed |

> ⚠️ **Golden Rule:** Never hold a reference to the UI layer inside your ViewModel.

---

## 📏 ViewModel Lifetime (vs Activity)

```
Timeline:
─────────────────────────────────────────────────────►

Activity:  [Created]──[Running]──[Destroyed]  [Created]──[Running]──[Destroyed (user leaves)]
                                                    ↑
                                               Rotation happened

ViewModel: [Created]────────────────────────────────────────────────[Destroyed]
              │                                                           │
           1st launch                                         User presses Back / calls finish()
```

The ViewModel is **only destroyed** when the user actually finishes the screen — not during rotation.

---

## 🧠 Quick Summary

| Concept | Simple Explanation |
|---|---|
| **ViewModel** | A safe locker for your screen's data |
| **Why we need it** | Activity gets destroyed on rotation — ViewModel doesn't |
| **`by viewModels()`** | Gets or creates the ViewModel for you |
| **LiveData** | Observable data that auto-updates the UI |
| **When ViewModel is destroyed** | Only when user leaves the screen for good |

---

## 📌 Key Rules to Remember

1. **Never pass `Context` or `View` into ViewModel** — you'll create memory leaks.
2. **Always use `by viewModels()`** — don't use `ViewModelProvider` manually (it's more verbose and error-prone for beginners).
3. **ViewModel + LiveData = the standard beginner pattern** — use them together.
4. **One ViewModel per screen** is usually the right structure.

---

*📚 Learning Android step by step. Follow along if you're learning too!*
