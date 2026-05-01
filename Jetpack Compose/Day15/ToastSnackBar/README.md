# Android Development Journey 🚀

## 📅 Day 15 - Toast & Snackbar in Jetpack Compose

## ✅ What I Did

* Created a new project **ToastSnackbar**
* Practiced showing user feedback messages in Jetpack Compose
* Implemented Toast and Snackbar examples

## 📚 What I Learned

### 🔹 Toast Message

* Used `Toast.makeText()`
* Displayed short temporary messages on button click
* Used `LocalContext.current` to access Android context

### 🔹 Snackbar Message

* Used `SnackbarHostState()`
* Used `SnackbarHost()` inside `Scaffold()`
* Displayed Snackbar on button click

### 🔹 Snackbar Features

* Added message text
* Added action button:

  * `UNDO`
* Used duration:

  * `SnackbarDuration.Short`

### 🔹 Coroutine Scope

* Used `rememberCoroutineScope()`
* Used `launch {}` to show Snackbar asynchronously

### 🔹 Layout Components

* Used:

  * `Scaffold()`
  * `Column()`
  * `Button()`
  * `Text()`

### 🔹 UI Handling

* Managed padding and alignment
* Centered content on screen

## 📌 Outcome

* Built feedback UI using Toast and Snackbar
* Learned temporary message systems in Android apps
* Practiced Scaffold + Snackbar integration
* Improved user interaction knowledge
