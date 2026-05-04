# 📱 Day 04 — Managing Tasks & Back Stack in Android

> **Learning Android Development — Day by Day**

---

## 🤔 What is a Task?

A **Task** is a collection of Activities that the user interacts with when doing a particular job in your app.

Think of it like this:

> 🛒 You open a **Shopping App**.
> You go: Home → Products → Product Detail → Cart → Checkout
>
> All of these screens together = **one Task**.

Every Task has a **Back Stack** — the ordered list of screens the user has visited.

---

## 📚 What is the Back Stack?

The **Back Stack** works exactly like a **stack of plates**.

- Every time you open a new screen → a new plate is placed **on top**
- The **top plate** is always what the user sees
- When the user presses the **Back button** → the top plate is removed
- When the stack is empty → the app exits (or goes to the Home screen)

```
User journey: Home → Products → Detail

Back Stack (top = visible on screen):
┌──────────────────────┐  ← TOP (visible)
│   DetailActivity     │
├──────────────────────┤
│  ProductsActivity    │
├──────────────────────┤
│    HomeActivity      │
└──────────────────────┘  ← BOTTOM

Press Back → DetailActivity is removed
Press Back again → ProductsActivity is removed
Press Back again → HomeActivity removed → app exits
```

---

## 🏗️ How Activities Enter and Leave the Stack

| User Action | What happens to the stack |
|---|---|
| Opens a new Activity | New Activity pushed **on top** |
| Presses **Back** button | Top Activity **popped off** (destroyed) |
| Presses **Home** button | Task goes to background (stack preserved!) |
| Returns to app from recent apps | Task comes to foreground (stack still there) |
| Calls `finish()` in code | Current Activity **popped off** manually |

> 💡 **Key insight:** Pressing `Home` does NOT destroy your stack. It just hides it. Your stack is preserved in the background and restored when the user returns.

---

## 🔄 Launch Modes — Controlling How Activities Stack

By default, every `startActivity()` call creates a **new instance** of that Activity and pushes it on the stack. But you can change this behaviour using **Launch Modes**.

Set a Launch Mode in `AndroidManifest.xml`:

```xml
<activity
    android:name=".HomeActivity"
    android:launchMode="singleTop" />
```

There are 4 Launch Modes:

---

### 1. `standard` (Default)

**Behaviour:** Always creates a brand new instance, even if one already exists in the stack.

```
Open A → Open B → Open A again:
Stack: [ A | B | A ]  ← two copies of A!
```

**Real-life analogy:** Like a Xerox machine — always makes a fresh copy.

**When to use:** Most screens — list screens, detail screens, forms, settings.

```xml
android:launchMode="standard"
```

---

### 2. `singleTop`

**Behaviour:** If the Activity is already at the **very top** of the stack, Android does NOT create a new instance. Instead it calls `onNewIntent()` on the existing one. If it's not on top, a new instance is created normally.

```
Open A → Open B → Open B again:
Stack: [ A | B ]  ← only one B, onNewIntent() called!

Open A → Open B → Open A again:
Stack: [ A | B | A ]  ← new A created because B was on top
```

**Real-life analogy:** If you're already at the front of the queue, don't join the back — just handle the new request in place.

**When to use:** Notification tap destinations — so tapping the same notification twice doesn't stack duplicate screens.

```xml
android:launchMode="singleTop"
```

**Handling the new intent in code:**
```kotlin
override fun onNewIntent(intent: Intent) {
    super.onNewIntent(intent)
    // Handle the new data sent via the intent
    val newData = intent.getStringExtra("data")
}
```

---

### 3. `singleTask`

**Behaviour:** Only **one instance** of this Activity can ever exist in a Task. If you try to open it again:
- If it's already in the stack → all Activities above it are **destroyed** and it comes to the front
- If it doesn't exist → it's created normally

```
Open Home → Open Products → Open Detail → Open Home again:
Stack before: [ Home | Products | Detail ]
Stack after:  [ Home ]  ← Products and Detail were destroyed!
```

**Real-life analogy:** There's only one King in a kingdom — if a new one arrives, everyone else above is dismissed and the original takes the throne.

**When to use:** Your app's main screen (HomeActivity) — so pressing "Go Home" from anywhere always clears the stack properly.

```xml
android:launchMode="singleTask"
```

---

### 4. `singleInstance`

**Behaviour:** Like `singleTask`, but this Activity gets its **own completely separate Task**. No other Activity can be in that Task with it.

```
Your app Task:    [ A | B ]
singleInstance Task: [ SpecialActivity ]  ← isolated, alone
```

**Real-life analogy:** A VIP room that only one person can occupy, in a completely separate building from everyone else.

**When to use:** Very rare. Typically for Activities that are shared between multiple apps (e.g., a map picker, a global settings screen).

```xml
android:launchMode="singleInstance"
```

---

### Launch Mode Comparison Table

| Mode | Multiple instances? | Clears activities above? | Own Task? |
|---|---|---|---|
| `standard` | Yes | No | No |
| `singleTop` | Yes (if not on top) | No | No |
| `singleTask` | No | Yes | No |
| `singleInstance` | No | Yes | Yes (own Task) |

---

## 🚩 Intent Flags — Runtime Stack Control

**Launch Modes** are set in the manifest (compile time). **Intent Flags** let you control the stack **at runtime in your Kotlin code** — more flexible!

---

### `FLAG_ACTIVITY_NEW_TASK`

Opens the Activity in a new Task. If a Task already exists for that Activity, it's brought to the front.

**When to use:** When launching an Activity from outside an Activity context (from a `Service`, `BroadcastReceiver`, or `Notification`).

```kotlin
val intent = Intent(context, MainActivity::class.java)
intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
context.startActivity(intent)
```

---

### `FLAG_ACTIVITY_CLEAR_TOP`

If the target Activity already exists in the stack, **all Activities above it are removed** and it comes to the top. Usually combined with `FLAG_ACTIVITY_SINGLE_TOP`.

**Real-life use:** A "Go to Home" button from a deeply nested screen.

```kotlin
// From any screen, go back to HomeActivity and clear everything above
val intent = Intent(this, HomeActivity::class.java)
intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or
               Intent.FLAG_ACTIVITY_SINGLE_TOP
startActivity(intent)
```

```
Stack before: [ Home | Products | Detail | Cart ]
User taps "Go to Home"
Stack after:  [ Home ]  ← Products, Detail, Cart all cleared!
```

---

### `FLAG_ACTIVITY_NO_HISTORY`

The Activity is **never added to the Back Stack**. Once the user leaves it, they cannot return with the Back button.

**Real-life use:** Splash screens, one-time intro screens, OTP verification screens.

```kotlin
val intent = Intent(this, SplashActivity::class.java)
intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
startActivity(intent)
// User can never press Back to return to SplashActivity
```

---

### `FLAG_ACTIVITY_CLEAR_TASK`

**Clears the entire Task** before starting the Activity. Always used together with `FLAG_ACTIVITY_NEW_TASK`.

**Real-life use:** After a user logs out — clear every screen and take them to Login so they cannot press Back and get back in.

```kotlin
// After logout:
val intent = Intent(this, LoginActivity::class.java)
intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or
               Intent.FLAG_ACTIVITY_CLEAR_TASK
startActivity(intent)
// The entire back stack is now cleared. LoginActivity is the only screen.
```

---

## 🔁 `finish()` — Manually Removing an Activity

You can remove the current Activity from the stack yourself by calling `finish()`.

```kotlin
// Example: a one-time screen like "Welcome" that removes itself
binding.btnGetStarted.setOnClickListener {
    startActivity(Intent(this, HomeActivity::class.java))
    finish()  // Remove WelcomeActivity from the stack
    // Now pressing Back on HomeActivity exits the app, not WelcomeActivity
}
```

---

## 🗺️ The Full Picture

```
App launches:
Stack: [ Splash ]

Splash calls finish() and starts Home:
Stack: [ Home ]

User opens Products:
Stack: [ Home | Products ]

User opens Detail:
Stack: [ Home | Products | Detail ]

User opens Cart:
Stack: [ Home | Products | Detail | Cart ]

User presses Back:
Stack: [ Home | Products | Detail ]  ← Cart popped

User taps "Go to Home" (FLAG_ACTIVITY_CLEAR_TOP):
Stack: [ Home ]  ← Products and Detail cleared

User presses Back:
App exits (stack is empty)
```

---

## 📌 Key Rules to Remember

1. **Back Stack = stack of plates** — new screens go on top, Back removes the top
2. **Home button hides the Task — it does NOT destroy the stack**
3. **`finish()`** removes the current Activity from the stack immediately
4. **`singleTask`** is commonly used for the app's main/home screen
5. **`FLAG_ACTIVITY_CLEAR_TASK or FLAG_ACTIVITY_NEW_TASK`** is the standard logout pattern
6. **`FLAG_ACTIVITY_NO_HISTORY`** is perfect for Splash screens
7. **Flags override Launch Modes** when both are set — flags win at runtime

---

## 🧠 Quick Memory Tricks

| Concept | Remember As |
|---|---|
| Back Stack | A stack of plates — newest on top |
| Task | The tray holding all your plates |
| `standard` | Xerox — always makes a copy |
| `singleTop` | Don't queue if you're already at the front |
| `singleTask` | Only one King — clear everyone above |
| `singleInstance` | VIP room in a separate building |
| `CLEAR_TOP` | "Go to Home" — bulldoze everything above |
| `CLEAR_TASK` | Logout — demolish the whole building |
| `NO_HISTORY` | Splash screen — leave no footprint |
| `finish()` | Remove yourself from the stack now |

---

*📚 Learning Android step by step. Follow along if you're learning too!*
