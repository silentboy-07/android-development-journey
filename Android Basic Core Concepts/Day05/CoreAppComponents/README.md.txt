# 📱 Day 05 — 4 Core Android App Components

> **Learning Android Development — Day by Day**
> ← [Day 04 — Tasks & Back Stack](./Day-04-Android-Tasks-BackStack.md)

---

## 🏗️ The Big Picture

Every Android app is built from 4 core building blocks. Think of these like the 4 roles in a restaurant:

| Component | Restaurant Analogy | What it does |
|---|---|---|
| **Activity** | Dining room | The screen the user sees and interacts with |
| **Service** | Kitchen | Work happening in the background, invisible to user |
| **Broadcast Receiver** | Intercom system | Listens for announcements and reacts to them |
| **Content Provider** | Waiter | Securely shares data between apps |

All 4 must be declared in `AndroidManifest.xml` (except dynamically registered Broadcast Receivers).

---

## 1. Activity — "The Screen"

### What is it?

An Activity represents **a single screen with a user interface**. Every screen the user sees is an Activity.

- Login screen → `LoginActivity`
- Home screen → `HomeActivity`
- Profile screen → `ProfileActivity`
- Settings screen → `SettingsActivity`

### Real-life Example

> Open **WhatsApp**. The chat list is one Activity. When you tap a chat, a new Activity (the chat screen) opens. When you tap the camera button, the camera Activity opens. Each screen = one Activity.

### Key Facts

- Has a visual UI with layouts (XML)
- Managed by the Back Stack (you learned this in Day 04!)
- Has a lifecycle: `onCreate()` → `onStart()` → `onResume()` → `onPause()` → `onStop()` → `onDestroy()` (Day 01!)
- Must be declared in `AndroidManifest.xml`

### Code

```kotlin
// Every Activity extends AppCompatActivity
class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding.btnLogin.setOnClickListener {
            // Navigate to HomeActivity
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}
```

**Declare in `AndroidManifest.xml`:**

```xml
<activity
    android:name=".LoginActivity"
    android:exported="true">
    <!-- LAUNCHER = this is the first screen shown when app opens -->
    <intent-filter>
        <action android:name="android.intent.action.MAIN" />
        <category android:name="android.intent.category.LAUNCHER" />
    </intent-filter>
</activity>

<!-- Other Activities (no intent-filter needed) -->
<activity android:name=".HomeActivity" />
<activity android:name=".ProfileActivity" />
```

---

## 2. Service — "The Background Worker"

### What is it?

A Service runs **in the background without any user interface**. It does long-running work even when the user is not looking at your app — or even when your app's screen is completely closed.

### Real-life Example

> Open **Spotify** and play a song. Now press the Home button and open Chrome. The music keeps playing! That's a **Foreground Service** — doing work (playing audio) even though you're looking at another app.

### The 3 Types of Services

| Type | Description | Example |
|---|---|---|
| **Started Service** | Runs until it stops itself or you stop it | Downloading a file |
| **Foreground Service** | Shows a notification, runs indefinitely | Playing music |
| **Bound Service** | Connected to a component, stops when component unbinds | Live GPS tracking |

### Key Facts

- Has **no UI** at all
- Runs on the **main thread by default** — use `Coroutines` or `WorkManager` for heavy tasks
- Must be declared in `AndroidManifest.xml`
- Foreground Services require a persistent notification (Android rules)

### Code

```kotlin
// Started Service — plays music in background
class MusicService : Service() {

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        // Do background work here
        val songName = intent?.getStringExtra("songName")
        playMusic(songName)

        // START_STICKY = if OS kills this service, restart it automatically
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        stopMusic() // Clean up
    }

    private fun playMusic(song: String?) { /* audio logic */ }
    private fun stopMusic() { /* stop audio */ }
}
```

**Start and stop the service from an Activity:**

```kotlin
// Start the service
val intent = Intent(this, MusicService::class.java)
intent.putExtra("songName", "Shape of You")
startService(intent)

// Stop the service
stopService(Intent(this, MusicService::class.java))
```

**Foreground Service with notification:**

```kotlin
class MusicService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = NotificationCompat.Builder(this, "music_channel")
            .setContentTitle("Now Playing")
            .setContentText("Shape of You - Ed Sheeran")
            .setSmallIcon(R.drawable.ic_music)
            .build()

        // This keeps the service alive and shows the notification
        startForeground(1, notification)
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
```

**Declare in `AndroidManifest.xml`:**

```xml
<service android:name=".MusicService" />

<!-- For Foreground Service, also add: -->
<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />
```

> ⚠️ **Modern Alternative:** For most background tasks today, Google recommends using **WorkManager** instead of Services. Services are still used for audio playback and real-time features.

---

## 3. Broadcast Receiver — "The Event Listener"

### What is it?

A Broadcast Receiver listens for **system-wide announcements (broadcasts)** and reacts to them. Android continuously sends broadcasts for important system events. Your app can tune in to any of them — or send its own.

### Real-life Example

> Your phone battery drops to 15%. Android sends a broadcast: `ACTION_BATTERY_LOW`. Every app that registered for this broadcast receives it and can react — maybe by pausing a sync, or showing a "Low battery" warning to the user.

### Common System Broadcasts

| Broadcast | When it fires |
|---|---|
| `ACTION_BATTERY_LOW` | Battery is critically low |
| `ACTION_BOOT_COMPLETED` | Phone finished booting |
| `CONNECTIVITY_CHANGE` | Wi-Fi or mobile data changed |
| `ACTION_AIRPLANE_MODE_CHANGED` | Airplane mode toggled |
| `ACTION_POWER_CONNECTED` | Charger plugged in |
| `SMS_RECEIVED` | SMS message arrived |

### Key Facts

- Has **no UI** (can show a Toast or Notification)
- Must be **very fast** — do minimal work in `onReceive()`. For heavy work, start a Service or WorkManager job.
- Can be registered **statically** (AndroidManifest.xml) or **dynamically** (in code)

### Code

```kotlin
// Step 1: Create the Receiver
class BatteryReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            Intent.ACTION_BATTERY_LOW -> {
                Toast.makeText(
                    context,
                    "Battery is low! Please charge your phone.",
                    Toast.LENGTH_LONG
                ).show()
                // Or: pause heavy background operations
            }
            Intent.ACTION_POWER_CONNECTED -> {
                Toast.makeText(context, "Charger connected!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
```

**Dynamic Registration (preferred — in Activity):**

```kotlin
class MainActivity : AppCompatActivity() {

    private val batteryReceiver = BatteryReceiver()

    override fun onStart() {
        super.onStart()
        // Register when Activity is visible
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_BATTERY_LOW)
            addAction(Intent.ACTION_POWER_CONNECTED)
        }
        registerReceiver(batteryReceiver, filter)
    }

    override fun onStop() {
        super.onStop()
        // ALWAYS unregister to prevent memory leaks!
        unregisterReceiver(batteryReceiver)
    }
}
```

**Static Registration (in Manifest — for BOOT_COMPLETED etc.):**

```xml
<receiver android:name=".BootReceiver"
          android:exported="false">
    <intent-filter>
        <action android:name="android.intent.action.BOOT_COMPLETED"/>
    </intent-filter>
</receiver>
```

**Sending your own custom broadcast:**

```kotlin
// Send a broadcast from anywhere in your app
val intent = Intent("com.myapp.DATA_REFRESHED")
intent.putExtra("timestamp", System.currentTimeMillis())
sendBroadcast(intent)

// Receive your custom broadcast
val filter = IntentFilter("com.myapp.DATA_REFRESHED")
registerReceiver(myReceiver, filter)
```

---

## 4. Content Provider — "The Data Gatekeeper"

### What is it?

A Content Provider manages access to a **shared data store** and lets other apps securely read or write that data. Android itself uses Content Providers for Contacts, Media Gallery, Calendar, and more.

### Real-life Example

> You open **WhatsApp** and want to send a contact. WhatsApp shows you your phone's contacts list. But WhatsApp does not own your contacts — it queries the **Android Contacts Content Provider** to fetch them. The Content Provider acts as a secure middleman, ensuring WhatsApp only gets what it is allowed to see.

### How it Works — URI Addressing

Content Providers use **URIs** (like web URLs) to address data:

```
content://com.android.contacts/contacts       → All contacts
content://com.android.contacts/contacts/42   → Contact with ID 42
content://media/external/images/media        → All images in gallery
```

- `content://` → the scheme (like `https://`)
- `com.android.contacts` → the authority (who owns this data)
- `/contacts` → the path (what data you want)

### CRUD Operations

| Operation | Method | SQL Equivalent |
|---|---|---|
| Read | `query()` | SELECT |
| Create | `insert()` | INSERT |
| Update | `update()` | UPDATE |
| Delete | `delete()` | DELETE |

### Code

**Reading contacts (querying the built-in Content Provider):**

```kotlin
// Add permission to AndroidManifest.xml first:
// <uses-permission android:name="android.permission.READ_CONTACTS"/>

fun readContacts() {
    val cursor = contentResolver.query(
        ContactsContract.Contacts.CONTENT_URI,  // What data (URI)
        arrayOf(                                 // Which columns to return
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME
        ),
        null,    // WHERE clause (null = all rows)
        null,    // WHERE arguments
        ContactsContract.Contacts.DISPLAY_NAME + " ASC"  // Sort order
    )

    cursor?.use {
        while (it.moveToNext()) {
            val id   = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts._ID))
            val name = it.getString(it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME))
            Log.d("Contact", "ID: $id, Name: $name")
        }
    }
}
```

**Reading images from the Media gallery:**

```kotlin
fun readImages() {
    val cursor = contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.DISPLAY_NAME),
        null, null,
        "${MediaStore.Images.Media.DATE_ADDED} DESC"
    )

    cursor?.use {
        while (it.moveToNext()) {
            val name = it.getString(
                it.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME)
            )
            Log.d("Image", name)
        }
    }
}
```

**Inserting a new contact:**

```kotlin
fun insertContact(name: String, phone: String) {
    val values = ContentValues().apply {
        put(ContactsContract.RawContacts.ACCOUNT_TYPE, null as String?)
        put(ContactsContract.RawContacts.ACCOUNT_NAME, null as String?)
    }
    contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, values)
}
```

**Declare your own Content Provider in `AndroidManifest.xml`:**

```xml
<provider
    android:name=".MyContentProvider"
    android:authorities="com.myapp.provider"
    android:exported="false" />
```

---

## 🗺️ How All 4 Work Together — A Real App Example

Imagine a **Music Streaming App**:

```
User taps "Play" on HomeActivity
        │
        ▼
HomeActivity (Activity)
Calls startService(MusicService)
        │
        ▼
MusicService (Service)
Plays music in background, shows notification
        │
        ▼
User gets a call → phone fires PHONE_STATE broadcast
        │
        ▼
PhoneReceiver (Broadcast Receiver)
onReceive() → tells MusicService to pause
        │
        ▼
User wants to save a song → app reads liked songs
from MediaStore via contentResolver.query()
        │
        ▼
Android Contacts ContentProvider
Returns media data securely
```

---

## 📌 Key Rules to Remember

1. **All 4 components** must be declared in `AndroidManifest.xml` (except dynamically registered Broadcast Receivers)
2. **Activity** = has UI + lifecycle. **Service** = no UI, background work
3. **Broadcast Receiver's `onReceive()` must be fast** — do not do network calls or heavy DB work directly in it. Start a Service or use WorkManager instead
4. **Content Provider uses URIs** to address data — like a URL for your database
5. **Always unregister** your Broadcast Receiver to avoid memory leaks
6. **Always request permissions** before accessing Content Providers (Contacts, Media, etc.)

---

## 🧠 Quick Memory Tricks

| Component | Remember As |
|---|---|
| Activity | Restaurant dining room — visible, interactive |
| Service | Restaurant kitchen — working, never seen |
| Broadcast Receiver | Intercom — listens and reacts to announcements |
| Content Provider | Waiter — fetches and serves data securely |

---

## 📊 Comparison Table

| Feature | Activity | Service | Broadcast Receiver | Content Provider |
|---|---|---|---|---|
| Has UI? | Yes | No | No (notification only) | No |
| Lifecycle? | Yes (complex) | Yes (simple) | Minimal | Yes |
| User interaction? | Yes | No | No | No |
| Runs how long? | While visible | Until stopped | Few seconds | On demand |
| Main purpose | Show screens | Background work | React to events | Share data |

---

## 🔗 What's Next?

- [ ] Day 06 — Fragments (mini-screens inside an Activity)
- [ ] Day 07 — RecyclerView (scrollable lists)
- [ ] Day 08 — Navigation Component (Jetpack navigation)
- [ ] Day 09 — Retrofit (networking / calling APIs)

---

> **Previous:** [Day 04 — Tasks & Back Stack](./Day-04-Android-Tasks-BackStack.md)

*📚 Learning Android step by step. Follow along if you're learning too!*