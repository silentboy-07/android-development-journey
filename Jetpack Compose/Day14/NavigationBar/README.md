# Android Development Journey 🚀

## 📅 Day 14 - Bottom Navigation Bar in Jetpack Compose

## ✅ What I Did

* Created a new project **BottomNavigationBar**
* Built a multi-screen app using Bottom Navigation in Jetpack Compose
* Connected screens using Navigation Compose

## 📚 What I Learned

### 🔹 Bottom Navigation Components

* Used `NavigationBar()`
* Used `NavigationBarItem()`
* Added labels and icons for each tab

### 🔹 Navigation Tabs Created

* Home
* Search
* Notifications
* Profile

### 🔹 Material Icons

* Used:

  * `Icons.Default.Home`
  * `Icons.Default.Search`
  * `Icons.Default.Notifications`
  * `Icons.Default.Person`

### 🔹 Navigation Setup

* Used `NavHost()`
* Used `NavController`
* Used `rememberNavController()`
* Defined `startDestination`

### 🔹 Routes Management

* Created sealed class `NavBarRoutes`
* Used type-safe routes for screen navigation

### 🔹 Navigation Actions

* Navigated between screens on tab click
* Used:

  * `popUpTo()`
  * `saveState = true`
  * `restoreState = true`
  * `launchSingleTop = true`

### 🔹 Selected Tab UI

* Managed selected item state
* Customized selected and unselected colors using:

  * `NavigationBarItemDefaults.colors()`

### 🔹 Screen Structure

Created separate screens:

* Home Screen
* Search Screen
* Notification Screen
* Profile Screen

Each screen used:

* `Scaffold()`
* Bottom Navigation Bar
* Center aligned content

## 📌 Outcome

* Built a professional bottom navigation app
* Learned real app screen switching pattern
* Improved navigation architecture skills
* Practiced reusable UI components
