# 📱 Day 03 — Android Intents (Explicit, Implicit & Intent Filter)

> **Learning Android Development — Day by Day**

---

## 🤔 What is an Intent?

An **Intent** is a messaging object in Android. It is used to **request an action** from another component — either inside your own app, or from another app entirely.

Think of an Intent like a **letter** you send:
- The letter says **what you want done** (open a map, share a photo, go to next screen)
- Android reads the letter and decides **who should handle it**

There are **3 things** you learned today:

| Type | What it does | Real-life analogy |
|---|---|---|
| **Explicit Intent** | Opens a specific Activity you name | Calling someone by their direct number |
| **Implicit Intent** | Asks Android to find any app that can help | Broadcasting on a radio — anyone who can help, respond! |
| **Intent Filter** | Tells Android "my app can handle this!" | Putting up a sign: "We accept image deliveries here" |

---

## 1. Explicit Intent — "I know exactly where I want to go"

### What is it?

An Explicit Intent is when **you name the exact Activity** you want to open. Android goes directly to it — no questions asked.

Use this to navigate **within your own app** only.

### Real-life Example

> You want to go from the **Login screen** to the **Dashboard screen** in your app. You know the exact destination, so you say: "Open `DashboardActivity`." Android opens it immediately.

### Code

**Basic navigation:**
```kotlin
// In LoginActivity.kt
val intent = Intent(this, DashboardActivity::class.java)
startActivity(intent)
```

**Passing data (using `putExtra`):**
```kotlin
val intent = Intent(this, DashboardActivity::class.java)
intent.putExtra("username", "Ravi")   // key → value
intent.putExtra("userId", 42)
startActivity(intent)
```

**Receiving data in DashboardActivity:**
```kotlin
// In DashboardActivity.kt
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_dashboard)

    val username = intent.getStringExtra("username")  // "Ravi"
    val userId   = intent.getIntExtra("userId", 0)    // 42

    binding.tvWelcome.text = "Welcome, $username!"
}
```

### Key Rules
- Always use `this` (current context) as the first argument
- The second argument is the **class** of the Activity you want to open
- Use `putExtra("key", value)` to send data, `getStringExtra("key")` to receive it

---

## 2. Implicit Intent — "I know what I want done, but I don't care who does it"

### What is it?

An Implicit Intent describes **an action to perform**, without specifying which app should do it. Android looks at all installed apps and finds those that can handle your request.

If **multiple apps** can handle it → Android shows a **chooser dialog** so the user can pick.  
If **only one app** can handle it → Android opens it directly.  
If **no app** can handle it → your app crashes! (Always check first)

### Real-life Example

> You want to open a website. You don't care if Chrome, Firefox, or Brave opens it. You just say: "Open this URL." Android shows all the browsers and lets the user choose.

### Code

**Open a website:**
```kotlin
val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
startActivity(intent)
```

**Open Google Maps at a location:**
```kotlin
val location = Uri.parse("geo:28.6139,77.2090?q=New+Delhi")
val intent = Intent(Intent.ACTION_VIEW, location)
startActivity(intent)
```

**Share text with other apps:**
```kotlin
val shareIntent = Intent(Intent.ACTION_SEND).apply {
    type = "text/plain"
    putExtra(Intent.EXTRA_TEXT, "Check out this cool Android tutorial!")
}
// createChooser always shows the picker (even if one app matches)
startActivity(Intent.createChooser(shareIntent, "Share via"))
```

**Open the phone dialer:**
```kotlin
val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+911234567890"))
startActivity(dialIntent)
```

**Open camera to capture an image:**
```kotlin
val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE)
```

### Safety Check — What if no app can handle it?

```kotlin
val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://google.com"))

// Always check before launching to avoid crashes!
if (intent.resolveActivity(packageManager) != null) {
    startActivity(intent)
} else {
    Toast.makeText(this, "No app found to handle this!", Toast.LENGTH_SHORT).show()
}
```

### Common Implicit Intent Actions

| Action Constant | What it does |
|---|---|
| `Intent.ACTION_VIEW` | View data (URL, map, contact) |
| `Intent.ACTION_SEND` | Share data with another app |
| `Intent.ACTION_DIAL` | Open dialer with a number |
| `Intent.ACTION_CALL` | Call directly (needs CALL_PHONE permission) |
| `Intent.ACTION_PICK` | Pick a contact/image from gallery |
| `MediaStore.ACTION_IMAGE_CAPTURE` | Open camera |

---

## 3. Intent Filter — "My app can handle that!"

### What is it?

An Intent Filter is a declaration in your `AndroidManifest.xml` that tells Android:

> **"My app (specifically this Activity) is capable of handling this type of Intent."**

This is how your app appears in the **"Open with..."** or **"Share with..."** dialog shown by other apps.

### Real-life Example

> You're browsing in **Chrome** and long-press an image. You tap "Share image." Android looks at all installed apps and finds ones that declared they can receive images. **Your app** shows up in the list because you added an Intent Filter for it.

When the user selects your app → Android opens your Activity and passes the image URI directly to it.

---

### How to Set It Up — Step by Step

#### Step 1: Declare the Intent Filter in `AndroidManifest.xml`

```xml
<manifest ...>
    <application ...>

        <activity android:name=".ImageViewerActivity"
                  android:exported="true">

            <!-- This tells Android our app can receive images -->
            <intent-filter>
                <action android:name="android.intent.action.SEND" />
                <category android:name="android.intent.category.DEFAULT" />
                <data android:mimeType="image/*" />
            </intent-filter>

        </activity>

    </application>
</manifest>
```

> 💡 **Breaking down the 3 tags inside `<intent-filter>`:**
> - `<action>` — What the user wants to do (`SEND` = sharing something)
> - `<category>` — Always include `DEFAULT` so Android considers your app
> - `<data>` — What type of data you accept (`image/*` = any image format)

---

#### Step 2: Receive and display the image in your Activity

```kotlin
// ImageViewerActivity.kt
class ImageViewerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_viewer)

        // Check if we received an image via intent
        if (intent.action == Intent.ACTION_SEND &&
            intent.type?.startsWith("image/") == true) {

            // Get the image URI that was shared
            val imageUri = intent.getParcelableExtra<Uri>(Intent.EXTRA_STREAM)

            imageUri?.let { uri ->
                // Display it in an ImageView
                binding.imageView.setImageURI(uri)
            } ?: run {
                Toast.makeText(this, "No image received!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
```

---

### Accepting Multiple Types (Optional — for reference)

If you want your app to handle **both images and videos**:

```xml
<intent-filter>
    <action android:name="android.intent.action.SEND" />
    <category android:name="android.intent.category.DEFAULT" />
    <data android:mimeType="image/*" />
    <data android:mimeType="video/*" />
</intent-filter>
```

---

## 🔁 How All Three Work Together

```
YOUR APP                     ANDROID OS                    OTHER APPS
──────────                   ──────────                    ──────────

[Explicit Intent]
LoginActivity ──────────────► Opens DashboardActivity
   "Open DashboardActivity"    directly, no detour


[Implicit Intent]
MyApp fires ACTION_VIEW ────► Android scans all apps ────► Chrome  ✓
   "Open this URL"              for matching filters         Firefox ✓
                                                             Shows chooser


[Intent Filter]
Chrome fires ACTION_SEND ───► Android scans all apps ────► Gallery  ✓
   "Share image/*"              with image/* filter           Photos  ✓
                                                              MyApp   ✓ ← you!
                                                              Shows chooser
```

---

## 📌 Key Rules to Remember

1. **Explicit** → use inside your own app only (LoginActivity → DashboardActivity)
2. **Implicit** → use when you want system/other apps to help (browser, maps, share)
3. **Intent Filter** → declare in `AndroidManifest.xml`, not in Kotlin code
4. **Always safety-check implicit intents** with `resolveActivity()` before calling `startActivity()`
5. **`android:exported="true"`** is required in Android 12+ for activities with intent filters

---

## 🧠 Quick Summary

| Concept | Simple Memory Trick |
|---|---|
| **Explicit Intent** | Taxi with full address — goes directly |
| **Implicit Intent** | Radio broadcast — any listener can respond |
| **Intent Filter** | Shop sign — "We handle image deliveries!" |
| **putExtra / getExtra** | Attaching a note to your letter |
| **Chooser dialog** | Android asking "which app do you want?" |

---

*📚 Learning Android step by step. Follow along if you're learning too!*
