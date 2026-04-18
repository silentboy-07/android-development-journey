# Android Development Journey 🚀

## 📅 Day 9 - State Management in Jetpack Compose

## ✅ What I Did

* Created a new project **StateManagement**
* Practiced managing UI state in Jetpack Compose
* Built counter examples using state variables and button interactions

## 📚 What I Learned

### 🔹 remember

* Used `remember` to store values during recomposition
* State remains available while composable is active

### 🔹 mutableStateOf

* Used `mutableStateOf()` to create changeable state values
* UI automatically updates when state changes

### 🔹 rememberSaveable

* Used `rememberSaveable` to preserve state during:

  * Screen rotation
  * Configuration changes
  * Process recreation (basic supported types)

### 🔹 Recomposition

* Learned that Compose redraws affected UI when state changes

### 🔹 Counter App Example

* Built a score counter with:

  * Increase button
  * Decrease button
* Used `enabled` property to disable decrease button when score = 0

### 🔹 State Hoisting

* Managed state in parent composable
* Passed data and actions to child composable

#### Parent Responsibilities

* Holds `score` state
* Handles increment / decrement logic

#### Child Responsibilities

* Displays UI
* Calls callbacks from parent

## 📌 Outcome

* Built interactive UI using state
* Understood reactive UI updates in Compose
* Learned parent-child state management pattern
* Improved understanding of clean architecture basics
