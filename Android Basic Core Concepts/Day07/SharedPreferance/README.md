# 📱 Day 07 — SharedPreferences in Android
Learning Android Development — Day by Day

---

## 🤔 What is SharedPreferences?

SharedPreferences is Android's built-in way to **save small pieces of data permanently on the device**. The data stays safe even when the user closes the app, restarts the phone, or rotates the screen.

📱 Open any app → Type your name → Close the app → Reopen it → Your name is still there. That saved name = SharedPreferences at work.

Today we learned four core operations:

| Operation | What it does |
|-----------|-------------|
| Save data | Write key-value pairs to a file on the device |
| Display saved data | Read and show the saved values on screen |
| Hide data | Toggle visibility of sensitive values like passwords |
| Clear data | Remove one key or wipe everything at once |

---

## 🗂️ How SharedPreferences Works Under the Hood

Android stores your data as a simple **XML file** inside your app's private folder. No database, no internet, no complex setup — just key → value pairs.

```
/data/data/com.yourapp/shared_prefs/MyPrefs.xml
```

The file looks like this after saving some data:

```xml
<?xml version='1.0' encoding='utf-8' standalone='yes' ?>
<map>
    <string name="username">Ravi</string>
    <int name="age" value="22" />
    <boolean name="isLoggedIn" value="true" />
</map>
```

**Real-life analogy**
Think of SharedPreferences like a **small notebook** that your app keeps on the phone. You write things in it, read things from it, and erase things from it. The notebook stays safe even when you close the app.

---

## 🧰 Setup — Get a Reference to SharedPreferences

Before saving or reading anything, you need a **SharedPreferences object** — like opening the notebook before writing in it.

```kotlin
val sharedPref = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
```

| Parameter | What it means |
|-----------|--------------|
| `"MyPrefs"` | The name of your preferences file — you choose this name |
| `Context.MODE_PRIVATE` | Only your app can read/write this file |

> 💡 Call `getSharedPreferences()` inside your Activity. If you only have one Activity, you can also use `getPreferences(Context.MODE_PRIVATE)` — it auto-names the file after your Activity.

---

## 💾 Operation 1 — Save Data

To save data, you need an **Editor** — think of it as picking up a pen before writing in the notebook.

```kotlin
// Step 1: Get the editor (pick up the pen)
val editor = sharedPref.edit()

// Step 2: Put your data (write in the notebook)
editor.putString("username", "Ravi")      // save text
editor.putInt("age", 22)                   // save a whole number
editor.putBoolean("isLoggedIn", true)      // save true/false

// Step 3: Apply — this actually saves everything
editor.apply()
```

> ⚠️ **Always call `apply()`** after putting data — without it nothing gets written to the file!

### What data types can you save?

| Method | Saves | Example use |
|--------|-------|------------|
| `putString()` | Text | Name, email, city |
| `putInt()` | Whole number | Age, score, count |
| `putFloat()` | Decimal number | Height, weight |
| `putBoolean()` | True / False | isLoggedIn, isDarkMode |
| `putLong()` | Large number | Timestamp, file size |

### `apply()` vs `commit()` — What is the difference?

| | `apply()` | `commit()` |
|---|---|---|
| **Speed** | Fast — runs in background | Slow — blocks the main thread |
| **Return value** | Nothing | Returns `true` or `false` |
| **When to use** | Almost always | Only if you need confirmation |

> 💡 Rule of thumb: Always use `apply()` unless you have a very specific reason to use `commit()`.

---

## 👀 Operation 2 — Display Saved Data

Reading data is straightforward — call `get` methods directly on the SharedPreferences object.

```kotlin
// Read saved values (always provide a default value as the second argument)
val username  = sharedPref.getString("username", "Guest")   // default = "Guest"
val age       = sharedPref.getInt("age", 0)                 // default = 0
val isLogged  = sharedPref.getBoolean("isLoggedIn", false)  // default = false

// Show on screen
binding.tvName.text = "Hello, $username!"
binding.tvAge.text  = "Age: $age"
```

> 💡 The **second parameter** is the **default value** — what gets returned if that key has never been saved. Always provide a sensible default.

### Check if a key exists before reading

```kotlin
if (sharedPref.contains("username")) {
    val name = sharedPref.getString("username", "")
    binding.tvName.text = name
} else {
    binding.tvName.text = "No data saved yet"
}
```

---

## 🔒 Operation 3 — Hide / Show Data (Toggle Visibility)

For passwords or sensitive fields, you can toggle between showing and hiding the value using `inputType`.

```kotlin
var isPasswordVisible = false

binding.btnToggleVisibility.setOnClickListener {

    // Flip the toggle
    isPasswordVisible = !isPasswordVisible

    // Change inputType based on the toggle state
    binding.etPassword.inputType =
        if (isPasswordVisible)
            InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD   // show text
        else
            InputType.TYPE_CLASS_TEXT or
            InputType.TYPE_TEXT_VARIATION_PASSWORD           // hide with dots

    // Move cursor to end so it does not jump to the beginning
    binding.etPassword.setSelection(binding.etPassword.text.length)
}
```

> ⚠️ **Important:** Do NOT store real passwords in SharedPreferences — it is not encrypted. Use **EncryptedSharedPreferences** or **Android Keystore** for anything sensitive.

---

## 🗑️ Operation 4 — Clear Data

You can remove **one specific key** or **wipe everything** at once.

```kotlin
val editor = sharedPref.edit()

// Remove just one item
editor.remove("username")
editor.apply()

// Remove ALL saved data at once
editor.clear()
editor.apply()
```

You can also **read all saved data at once** — useful for debugging:

```kotlin
val allData = sharedPref.all
Log.d("SharedPrefs", allData.toString())
// Output: {username=Ravi, age=22, isLoggedIn=true}
```

---

## 🔄 Full Flow — How SharedPreferences Works

```
User types data and taps Save
        │
        ▼
sharedPref.edit() → get the editor
        │
        ▼
editor.putString / putInt / putBoolean → stage the changes
        │
        ▼
editor.apply() → write to XML file on device
        │
        ▼
Data is now saved permanently on the device
        │
        ▼
User closes app / restarts phone → data still there
        │
        ▼
sharedPref.getString / getInt / getBoolean → read it back
        │
        ▼
Show on screen
```


## 📊 SharedPreferences vs Room Database

| Feature | SharedPreferences | Room Database |
|---------|------------------|---------------|
| **Best for** | Simple key-value data | Complex, relational data |
| **Data size** | Small | Large |
| **Example** | Username, settings, theme | Product list, chat messages |
| **Setup effort** | Very easy | Requires some setup |
| **SQL queries** | Not supported | Fully supported |
| **Multiple rows** | No | Yes |

> 💡 Rule of thumb: If your data fits as "key = value", use SharedPreferences. If you need a list of many items or a table, use Room.

---

## 📌 Key Rules to Remember

- Always call `apply()` after putting data — without it nothing gets saved
- Always provide a **default value** when reading — the key might not exist yet
- Use `MODE_PRIVATE` always — never share your preferences with other apps
- Do NOT store real passwords — use **EncryptedSharedPreferences** for sensitive data
- `apply()` is async and fast — prefer it over `commit()` in almost all cases
- Call `editor.clear().apply()` to wipe everything, not just `clear()` alone

---

## 🧠 Quick Memory Tricks

| Concept | Remember As |
|---------|------------|
| `getSharedPreferences()` | Opening the notebook |
| `sharedPref.edit()` | Picking up the pen |
| `editor.putString()` | Writing in the notebook |
| `editor.apply()` | Closing and saving the notebook |
| `sharedPref.getString()` | Reading from the notebook |
| `editor.remove("key")` | Erasing one line |
| `editor.clear()` | Tearing out all pages |
| Default value in `get` | "Show this if nothing is written yet" |

---

📚 Learning Android step by step. Follow along if you're learning too!
