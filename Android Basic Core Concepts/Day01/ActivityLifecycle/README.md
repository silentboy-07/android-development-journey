# 📱 Day 01 — Android Activity Lifecycle

> **Learning Android Development — Day by Day**  
> This file is part of my daily Android learning journal. Anyone can follow along!

---

## 🤔 What is an Activity?

An **Activity** is a single screen in an Android app. For example:
- The **Login screen** → one Activity
- The **Home screen** → another Activity
- The **Profile screen** → yet another Activity

Every Activity has a **lifecycle** — a series of states it goes through from the moment it's created to when it's destroyed.

---

## 🔄 The 7 Lifecycle Methods

Think of an Activity like a **TV show episode**:
- It starts, plays, can be paused, stopped, and eventually ends.

Android calls specific methods (functions) automatically at each stage. You just need to *override* them and write your code inside.

---

### 1. `onCreate()` — 🏗️ Building the Stage

**What happens:** Called **once** when the Activity is first created.  
**What to do here:** Set up your UI layout, initialize variables, connect to the database.

**Real-life example:**  
> You open **WhatsApp** for the first time. The app sets up the chat list screen, connects to your database of messages, and prepares the toolbar. This all happens in `onCreate()`.

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main) // Load the UI layout
    // Initialize variables, set up RecyclerView, connect DB, etc.
}
```

> 💡 `savedInstanceState` helps restore data if the app was killed by the OS (e.g., low memory).

---

### 2. `onStart()` — 👀 Curtain Rises (Visible but not ready)

**What happens:** Activity becomes **visible** to the user, but not yet interactive.  
**What to do here:** Start things that should be visible (like loading a preview image).

**Real-life example:**  
> WhatsApp screen appears on your phone. You can *see* it, but it's not ready to respond to your taps yet.

```kotlin
override fun onStart() {
    super.onStart()
    // Activity is now visible to the user
}
```

---

### 3. `onResume()` — ▶️ Fully Active (User can interact)

**What happens:** Activity is in the **foreground** and fully interactive. This is the "live" state.  
**What to do here:** Start animations, resume video, activate the camera or sensors.

**Real-life example:**  
> WhatsApp is now fully open. You can tap chats, read messages, type replies. Everything works!

```kotlin
override fun onResume() {
    super.onResume()
    // Start camera, play animation, resume music player, etc.
}
```

> 💡 `onResume()` is called every time you return to this screen (not just the first time).

---

### 4. `onPause()` — ⏸️ Something Appeared on Top

**What happens:** Activity is **partially hidden** — another window appeared (like a dialog or incoming call UI).  
**What to do here:** Save *lightweight* data, pause animations. Don't do heavy work here — it's a quick call!

**Real-life example:**  
> You're using WhatsApp and a call notification pops up over it. WhatsApp is still visible in the background but paused.

```kotlin
override fun onPause() {
    super.onPause()
    // Pause video playback, save form data temporarily
    // ⚠️ DO NOT do heavy database/network operations here
}
```

---

### 5. `onStop()` — 🛑 Completely Hidden

**What happens:** Activity is **completely invisible** — user went home or opened another app.  
**What to do here:** Save important data to the database, release heavy resources (like maps or large bitmaps).

**Real-life example:**  
> You pressed the **Home button**. WhatsApp is no longer visible. It's like your TV on standby — still there, but the screen is off.

```kotlin
override fun onStop() {
    super.onStop()
    // Save data to database
    // Release heavy resources (camera, large images)
    // Unregister sensors
}
```

---

### 6. `onRestart()` — 🔁 Coming Back from Stopped

**What happens:** Called when a **stopped** Activity is about to become visible again (user returned to it).  
**What to do here:** Refresh data that might have changed while the app was hidden.

**Real-life example:**  
> You switch from another app back to WhatsApp. Before the screen shows again, `onRestart()` fires — like a "welcome back" signal.

```kotlin
override fun onRestart() {
    super.onRestart()
    // Refresh data, check for new messages, etc.
}
```

> 💡 After `onRestart()`, Android calls `onStart()` → `onResume()` again.

---

### 7. `onDestroy()` — 💀 The End

**What happens:** Activity is **removed from memory**. Either the user closed the app or the OS killed it.  
**What to do here:** Final cleanup — close database connections, cancel network requests, remove listeners.

**Real-life example:**  
> You swipe WhatsApp away from the Recent Apps screen. Or your phone is low on RAM and Android kills the app. Everything is cleaned up here.

```kotlin
override fun onDestroy() {
    super.onDestroy()
    // Close database, cancel Coroutines, remove listeners
    // Final cleanup — this is the last chance!
}
```

---

## 🗺️ The Full Lifecycle Flow

```
         App Launched
              │
              ▼
         onCreate()
              │
              ▼
          onStart()
              │
              ▼
         onResume()  ◄──────────────────────────┐
              │                                  │
     [User interacts]               [User comes back to app]
              │                                  │
    [Another window appears]           onRestart() ──► onStart()
              │                                  │
              ▼                                  │
          onPause()                              │
              │                                  │
     [App fully hidden]                          │
              │                                  │
              ▼                                  │
          onStop() ──────────────────────────────┘
              │
       [App closed / OS kills app]
              │
              ▼
         onDestroy()
```

---

## 🧠 Quick Memory Trick

| Method | Remember As |
|---|---|
| `onCreate()` | 🏗️ Build the room |
| `onStart()` | 🚪 Open the door (visible) |
| `onResume()` | 🙋 You walk in (interactive) |
| `onPause()` | 🤚 Someone knocks (distracted) |
| `onStop()` | 🚪 Door closes (hidden) |
| `onRestart()` | 👋 You return (coming back) |
| `onDestroy()` | 🏚️ Demolish the room |

---

## 📌 Key Rules to Remember

1. **Always call `super.methodName()`** — Android needs it for internal cleanup.
2. **`onPause()` must be fast** — don't do heavy work here.
3. **Save important data in `onStop()`** — not just `onPause()`.
4. **`onDestroy()` is not always called** — the OS can kill your app without calling it (e.g., force-stop).
5. **`onResume()` and `onPause()` are called the most** — every time you switch apps or get a notification.

---

*📚 Learning Android step by step. Follow along if you're learning too!*
