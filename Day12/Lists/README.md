# Android Development Journey 🚀

## 📅 Day 12 - Lists with LazyColumn & LazyRow

## ✅ What I Did

* Created a new project **Lists**
* Practiced building scrollable lists in Jetpack Compose
* Implemented vertical, horizontal, and nested scrolling layouts

## 📚 What I Learned

### 🔹 LazyColumn

* Used `LazyColumn()` to create vertically scrollable lists
* Displayed multiple items efficiently
* Added spacing using `Arrangement.spacedBy()`

### 🔹 LazyRow

* Used `LazyRow()` to create horizontally scrollable lists
* Built side-scrolling item layouts

### 🔹 Nested Lists

* Combined `LazyColumn` with `LazyRow`
* Created multiple horizontal rows inside a vertical list
* Learned layouts used in apps like Netflix / shopping apps

### 🔹 Types of List Builders

#### `item {}`

* Used for single custom item like header/footer

#### `items(count)`

* Generated list items using count and index

#### `items(list)`

* Displayed data from a list of strings

#### `itemsIndexed(list)`

* Accessed both index and item value together

### 🔹 UI Customization

* Added:

  * `padding`
  * `spacing`
  * `RoundedCornerShape`
  * `background`
  * `Box` alignment

### 🔹 Performance Benefit

* Learned Lazy components render visible items only
* Better performance than regular Column/Row for large lists

## 📌 Outcome

* Built efficient scrollable list UI
* Learned RecyclerView alternative in Compose
* Practiced real-world list layouts
* Improved UI design skills
  
