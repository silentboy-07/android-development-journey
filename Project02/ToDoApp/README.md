# 📱 ToDoApp Complete: UI, Jetpack Compose, Full MVVM App
 
> **Learning Android Development — Day by Day**
 
---
 
## 🎉 Project Complete!
 
Today we finished the **ToDoApp** — our first full Android project built from scratch using everything learned so far. The app is fully working with a beautiful UI, persistent local storage, and clean MVVM architecture.
 
---

## 📸 Screenshots

<p align="center">
  <img src="Project02/ToDoApp/Screenshorts/SplashScreen.png" width="180" style="margin: 8px;" />
  &nbsp;&nbsp;&nbsp;
  <img src="Project02/ToDoApp/Screenshorts/HomeScreen.png" width="180" style="margin: 8px;" />
  &nbsp;&nbsp;&nbsp;
  <img src="Project02/ToDoApp/Screenshorts/CreateTaskScreen.png" width="180" style="margin: 8px;" />
</p>

<p align="center">
  <img src="Project02/ToDoApp/Screenshorts/TaskScreen.png" width="180" style="margin: 8px;" />
  &nbsp;&nbsp;&nbsp;
  <img src="Project02/ToDoApp/Screenshorts/TaskCompleted.png" width="180" style="margin: 8px;" />
  &nbsp;&nbsp;&nbsp;
  <img src="Project02/ToDoApp/Screenshorts/UpdateTask.png" width="180" style="margin: 8px;" />
</p>

---

## ✨ Features

- ➕ **Add tasks** — bottom sheet with a clean text input
- ✅ **Mark complete** — tap checkbox → strikethrough + greyed card
- ✏️ **Edit tasks** — pre-filled bottom sheet to update any task
- 🗑️ **Delete tasks** — instantly removes from DB and UI
- 📊 **Live task counter** — "X remaining today" updates in real time
- 💾 **Persistent storage** — tasks survive app close and phone restart
- 🎨 **Warm earthy theme** — custom Material 3 colour palette
- 🚀 **Splash screen** — branded launch screen using Android 12+ API

---

## 🛠 Tech Stack

| Technology | Purpose |
|---|---|
| Jetpack Compose | Declarative UI |
| MVVM Architecture | Separation of concerns |
| Room Database | Local data persistence |
| Kotlin Coroutines | Async database operations |
| StateFlow | Reactive UI state |
| SplashScreen API | Animated launch screen |
| ViewModel | UI state holder |
| Material 3 | Design system |

---

## 🏗 Architecture — MVVM

This app follows the **Model–View–ViewModel (MVVM)** pattern, keeping UI, business logic, and data completely separated.

```
┌─────────────────────────────────────────────┐
│                    UI Layer                  │
│  ToDoListScreen  ·  ToDoItem  ·  TaskEditor  │
└─────────────────┬───────────────────────────┘
                  │ observes StateFlow
┌─────────────────▼───────────────────────────┐
│               ViewModel Layer                │
│              TaskViewModel                   │
│   addTask() · deleteTask() · updateTask()    │
└─────────────────┬───────────────────────────┘
                  │ calls suspend functions
┌─────────────────▼───────────────────────────┐
│             Repository Layer                 │
│              TaskRepository                  │
│        Abstracts data source access          │
└─────────────────┬───────────────────────────┘
                  │ queries
┌─────────────────▼───────────────────────────┐
│               Data Layer                     │
│   TaskDao  ·  TaskDatabase  ·  TaskItem      │
│          Room (SQLite under the hood)        │
└─────────────────────────────────────────────┘
```

### Data Flow

1. **User action** (tap, type) triggers a UI event in Compose
2. **Screen** calls a function on `TaskViewModel`
3. **ViewModel** delegates to `TaskRepository` via a coroutine
4. **Repository** calls `TaskDao` which reads/writes Room Database
5. **Room** emits a `Flow<List<TaskItem>>` back up the chain
6. **ViewModel** exposes it as `StateFlow`, Compose **recomposes** automatically

---

## 📁 Project Structure

```
com.example.todoapp
├── data
│   ├── room_database
│   │   ├── TaskDao.kt          # Database queries (insert, update, delete, getAll)
│   │   ├── TaskDatabase.kt     # Room database singleton
│   │   └── TaskItem.kt         # Entity data class
│   └── repository
│       └── TaskRepository.kt   # Single source of truth for data access
│
├── ui
│   ├── screens
│   │   ├── TaskEditor.kt       # ModalBottomSheet for create/edit
│   │   ├── TasksScreen.kt      # Main list screen with Scaffold + LazyColumn
│   │   └── ToDoItem.kt         # Individual task card composable
│   └── theme
│       ├── Color.kt            # App color palette
│       ├── Theme.kt            # MaterialTheme setup
│       └── Type.kt             # Typography
│
├── viewmodel
│   ├── TaskViewModel.kt        # Holds UI state, exposes StateFlow
│   └── TaskViewModelFactory.kt # Factory for ViewModel with Application context
│
└── MainActivity.kt             # Entry point, installs splash screen
```

---

## 🗄 Room Database

`TaskItem` is the database entity:

```kotlin
@Entity(tableName = "task_table")
data class TaskItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val taskName: String,
    val isDone: Boolean
)
```

`TaskDao` exposes suspend functions and a Flow for reactive updates:

```kotlin
@Dao
interface TaskDao {
    @Query("SELECT * FROM task_table ORDER BY id DESC")
    fun getAllTasks(): Flow<List<TaskItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: TaskItem)

    @Update
    suspend fun updateTask(task: TaskItem)

    @Delete
    suspend fun deleteTask(task: TaskItem)
}
```

---

## ⚙️ Dependencies

```kotlin
// Jetpack Compose BOM
implementation(platform(libs.androidx.compose.bom))
implementation(libs.androidx.compose.material3)

// Room Database
implementation("androidx.room:room-runtime:2.8.4")
ksp("androidx.room:room-compiler:2.8.4")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.2")

// Splash Screen
implementation("androidx.core:core-splashscreen:1.0.0")

// Extended Icons
implementation("androidx.compose.material:material-icons-extended")
```

---

## 🚀 Getting Started

1. Clone the repository
2. Open in Android Studio
3. Let Gradle sync
4. Run on an emulator or physical device (minSdk 24)

---

*📚 Learning Android step by step. Follow along if you're learning too!*
