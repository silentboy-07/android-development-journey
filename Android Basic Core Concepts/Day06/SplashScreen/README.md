# 📱 Day 06 — Splash Screens in Android

> **Learning Android Development — Day by Day**

---

## 🤔 What is a Splash Screen?

A **Splash Screen** is the very first thing a user sees when they open your app — usually your app logo and brand colour shown for a brief moment while the app is loading.

> 📱 Open Spotify → You see a black screen with the Spotify logo → Then the home screen appears.
> That first moment = the splash screen.

Today we learned **two ways** to implement it:

| Approach | Android Version | Status |
|---|---|---|
| Activity-based splash | All versions (old way) | Discouraged — it's a hack |
| SplashScreen API | Android 12+ (with compat library for older) | The correct modern way |

---

## ❌ Method 1 — The Old Way (Activity-based Splash)

### What is it?

Before Android 12, developers created a fake "splash screen Activity". It would show your logo, wait 2–3 seconds using a timer, then launch the real Activity.

### Why it was a problem

Think of it like this:

> You ring a doorbell. The person inside knows you are there — but you still see a blank door for 2 seconds before they open it. That blank door moment = the **black screen flash** that happened before the fake splash Activity even appeared.

Android took a moment to start the process before the splash Activity could draw itself. So users would briefly see a black or white screen first — then the splash — then the real app. Three visual transitions instead of one.

### Real-life analogy

> It is like hiring an actor to stand at the entrance of a restaurant and pretend to welcome guests — when really the restaurant already has a proper entrance. The actor just adds delay and awkwardness.

### How it worked (the old code)

**Step 1 — Create a SplashActivity:**

```kotlin
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Wait 3 seconds, then open MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()  // Remove SplashActivity from back stack
        }, 3000)
    }
}
```

**Step 2 — Create layout file `activity_splash.xml`:**

```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/brand_blue">

    <ImageView
        android:layout_width="120dp"
        android:layout_height="120dp"
        android:layout_centerInParent="true"
        android:src="@mipmap/ic_launcher" />

</RelativeLayout>
```

**Step 3 — Make SplashActivity the launcher in `AndroidManifest.xml`:**

```xml
<!-- SplashActivity is the LAUNCHER (first screen) -->
<activity android:name=".SplashActivity"
          android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.MAIN"/>
        <category android:name="android.intent.category.LAUNCHER"/>
    </intent-filter>
</activity>

<!-- MainActivity is the real app — no intent-filter needed -->
<activity android:name=".MainActivity"/>
```

### Problems with this approach

1. **Black/white flash** — a blank screen appears before SplashActivity renders
2. **Wastes time** — the 3-second delay is fake; the app might already be ready
3. **Extra Activity** — unnecessary code and complexity in the back stack
4. **Google discourages it** — violates Android design guidelines
5. **Does not work nicely with Android 12+** — Android 12 adds its own splash on top, so users see TWO splash screens!

---

## ✅ Method 2 — The Modern Way (Android 12+ SplashScreen API)

### What is it?

Android 12 introduced a **built-in SplashScreen system**. The operating system itself shows a proper splash screen while your app is starting up — no fake Activity, no timers, no hacks.

It uses your **app icon on a background colour** and animates cleanly into your app. With the `androidx.core:core-splashscreen` library, this works all the way back to Android 6.0 (API 23).

### Real-life analogy

> Instead of hiring an actor to welcome guests, the restaurant building itself has a beautiful automatic entrance door that opens with a smooth animation. Built-in, professional, zero effort.

---

### How to implement it — Step by Step

#### Step 1 — Add the dependency

In `build.gradle (Module: app)`:

```gradle
dependencies {
    implementation "androidx.core:core-splashscreen:1.0.1"
}
```

Sync your project after adding this.

---

#### Step 2 — Add a colour for the splash background

In `res/values/colors.xml`:

```xml
<resources>
    <color name="splash_background">#1A1A2E</color>
</resources>
```

---

#### Step 3 — Create the splash theme

In `res/values/themes.xml`, add a new style:

```xml
<style name="Theme.App.SplashScreen" parent="Theme.SplashScreen">

    <!-- The background colour of the splash screen -->
    <item name="windowSplashScreenBackground">@color/splash_background</item>

    <!-- Your app icon — shown centered on the screen -->
    <item name="windowSplashScreenAnimatedIcon">@mipmap/ic_launcher_foreground</item>

    <!-- The theme to switch to AFTER the splash exits -->
    <!-- This must be your normal app theme -->
    <item name="postSplashScreenTheme">@style/Theme.MyApp</item>

</style>
```

> 💡 `postSplashScreenTheme` is very important — it tells Android which theme to apply after the splash finishes. Without it, your app will keep the splash theme and look wrong.

---

#### Step 4 — Apply the splash theme in AndroidManifest.xml

```xml
<activity
    android:name=".MainActivity"
    android:theme="@style/Theme.App.SplashScreen"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.MAIN"/>
        <category android:name="android.intent.category.LAUNCHER"/>
    </intent-filter>
</activity>
```

---

#### Step 5 — Call `installSplashScreen()` in MainActivity

This is the most important step. Call it **before** `setContentView()`:

```kotlin
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        // ✅ This one line sets everything up
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
```

That is it. Run the app — you will see your icon on the splash background, and it will smoothly transition to your app.

---

### Advanced — Keep the splash visible while loading data

By default, the splash exits as soon as your Activity draws its first frame. If you need to load data first (check login state, fetch remote config), you can tell the splash to wait:

```kotlin
class MainActivity : AppCompatActivity() {

    // False = still loading, True = ready to show the app
    private var isDataReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        // The splash stays visible as long as this returns true
        splashScreen.setKeepOnScreenCondition {
            !isDataReady
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Simulate loading: check login status, load user data, etc.
        lifecycleScope.launch {
            checkLoginStatus()    // your real async work
            isDataReady = true    // now the splash will exit
        }
    }

    private suspend fun checkLoginStatus() {
        delay(2000) // replace with your real async call
    }
}
```

---

### Advanced — Custom exit animation

You can hook into the moment the splash exits and animate the icon:

```kotlin
splashScreen.setOnExitAnimationListener { splashScreenView ->

    val iconView = splashScreenView.iconView

    // Scale the icon down as the splash exits
    val scaleX = ObjectAnimator.ofFloat(iconView, View.SCALE_X, 1f, 0f)
    val scaleY = ObjectAnimator.ofFloat(iconView, View.SCALE_Y, 1f, 0f)

    scaleX.duration = 500
    scaleY.duration = 500
    scaleY.interpolator = AccelerateDecelerateInterpolator()

    scaleY.doOnEnd {
        // IMPORTANT: Always call remove() when your animation is done
        // Without this, the splash will NEVER go away!
        splashScreenView.remove()
    }

    scaleX.start()
    scaleY.start()
}
```

> ⚠️ **Always call `splashScreenView.remove()`** at the end of your exit animation. If you forget, your splash will stay on screen permanently and your app will appear frozen.

---

### Advanced — Animated icon (Android 12+ only)

You can use an `AnimatedVectorDrawable` as your splash icon — it plays while the splash is showing:

```xml
<!-- In themes.xml -->
<item name="windowSplashScreenAnimatedIcon">
    @drawable/animated_logo
</item>

<!-- Duration must match the AnimatedVectorDrawable duration -->
<item name="windowSplashScreenAnimationDuration">
    1000
</item>
```

---

## 🔄 Full Flow — How the Modern Splash Works

```
User taps app icon
        │
        ▼
Android OS starts your app process
        │
        ▼
OS automatically shows the splash screen
(your icon + windowSplashScreenBackground colour)
        │
        ▼
Your MainActivity.onCreate() runs
installSplashScreen() is called
        │
        ▼
[optional] setKeepOnScreenCondition { !isReady }
        │
        ▼
Your app draws its first frame / isReady = true
        │
        ▼
[optional] setOnExitAnimationListener → plays exit animation
        │
        ▼
Splash exits → Your app is visible
```

---

## 📊 Old Way vs New Way — Comparison

| Feature | Old (Activity) | New (SplashScreen API) |
|---|---|---|
| Black flash on startup | Yes | No |
| Needs a separate Activity | Yes | No |
| Timer/delay required | Yes | No |
| Uses actual icon automatically | No (manual) | Yes |
| Exit animation | Manual | Built-in + customisable |
| Works on Android 12+ properly | No (double splash) | Yes |
| Compat library for older Android | Not needed (but broken) | `core-splashscreen:1.0.1` |
| Google recommended | No | Yes |

---

## 📌 Key Rules to Remember

1. **`installSplashScreen()` must be called BEFORE `super.onCreate()`** — this is critical. The order is: `installSplashScreen()` → `super.onCreate()` → `setContentView()`
2. **Always set `postSplashScreenTheme`** — without it your app will use the splash theme forever
3. **Never use a fake 3-second timer** — use `setKeepOnScreenCondition` for real loading delays
4. **Always call `splashScreenView.remove()`** when using a custom exit animation
5. **Remove your old SplashActivity completely** if you had one — do not mix the two approaches
6. **On Android 12+, the system always shows a splash** — if you had an old SplashActivity, users would see two splashes. The new API prevents this.

---

## 🧠 Quick Memory Tricks

| Concept | Remember As |
|---|---|
| Old Activity splash | A fake welcome actor — adds delay, looks bad |
| SplashScreen API | Built-in automatic door — smooth, no effort |
| `installSplashScreen()` | The one magic line — call it first |
| `setKeepOnScreenCondition` | "Stay visible until I say go" |
| `setOnExitAnimationListener` | "This is how I want to say goodbye" |
| `splashScreenView.remove()` | "I am done — now actually leave!" |
| `postSplashScreenTheme` | "After splash, switch to this theme" |

---

*📚 Learning Android step by step. Follow along if you're learning too!* 
