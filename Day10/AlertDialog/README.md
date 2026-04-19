# Android Development Journey 🚀

## 📅 Day 10 - AlertDialog in Jetpack Compose

## ✅ What I Did

* Created a new project **AlertDialog**
* Practiced creating confirmation dialogs in Jetpack Compose
* Built a delete confirmation UI with Card, IconButton, and AlertDialog

## 📚 What I Learned

### 🔹 AlertDialog Composable

* Used `AlertDialog()` to display popup dialogs
* Learned properties:

  * `onDismissRequest`
  * `title`
  * `text`
  * `confirmButton`
  * `dismissButton`

### 🔹 State Management

* Used `remember`
* Used `mutableStateOf(false)` to control dialog visibility

### 🔹 Dialog Actions

* Opened dialog when delete icon button is clicked
* Closed dialog when:

  * Confirm button clicked
  * Cancel button clicked
  * Outside dismiss action triggered

### 🔹 Buttons Inside Dialog

* Created custom:

  * Delete button
  * Cancel button
* Customized colors using `ButtonDefaults.buttonColors()`

### 🔹 Additional UI Components

* Used `Card()` for modern layout design
* Used `Row()` for content alignment
* Used `IconButton()` with delete icon

### 🔹 Toast Message

* Displayed success message using `Toast.makeText()` after delete confirmation

## 📌 Outcome

* Built a real-world delete confirmation dialog
* Learned popup interaction handling in Compose
* Improved state management understanding
* Practiced professional UI patterns used in apps
