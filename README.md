# Etymo — Android App

A gamified Indian regional language learning app built with Jetpack Compose.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin 2.0.21 |
| UI Framework | Jetpack Compose (BOM 2024.09.00) |
| Build System | Gradle 8.14 (wrapper, `-all`) |
| Android Gradle Plugin | 8.13.2 |
| Min SDK | 36 (Android 16) |
| Target SDK | 36 |
| Compile SDK | 36 |
| Java Compatibility | Java 17 |
| JVM Target | 17 |

---

## App Config

```
applicationId  : com.example.etymo
versionCode    : 1
versionName    : 1.0
namespace      : com.example.etymo
```

---

## Dependencies

### Core
| Artifact | Version |
|---|---|
| `androidx.core:core-ktx` | 1.17.0 |
| `androidx.lifecycle:lifecycle-runtime-ktx` | 2.9.4 |
| `androidx.activity:activity-compose` | 1.11.0 |

### Jetpack Compose (via BOM `2024.09.00`)
| Artifact | Notes |
|---|---|
| `androidx.compose.ui:ui` | Core UI |
| `androidx.compose.ui:ui-graphics` | Graphics |
| `androidx.compose.ui:ui-tooling-preview` | Preview support |
| `androidx.compose.foundation:foundation` | Layouts, lazy grids |
| `androidx.compose.material3:material3` | Material 3 components |
| `androidx.compose.material3:material3-adaptive-navigation-suite` | Navigation scaffold |
| `androidx.compose.material:material-icons-extended` | Extended icon set |

### Testing
| Artifact | Version |
|---|---|
| `junit:junit` | 4.13.2 |
| `androidx.test.ext:junit` | 1.3.0 |
| `androidx.test.espresso:espresso-core` | 3.7.0 |
| `androidx.compose.ui:ui-test-junit4` | via BOM |

### Debug Only
| Artifact | Notes |
|---|---|
| `androidx.compose.ui:ui-tooling` | Layout Inspector |
| `androidx.compose.ui:ui-test-manifest` | Test manifest |

---

## Gradle Wrapper

```properties
distributionUrl=https://services.gradle.org/distributions/gradle-8.14-all.zip
```

---

## Project Structure

```
app/src/main/java/com/example/etymo/
├── MainActivity.kt          # Entry point, app shell, bottom nav
├── screens/
│   ├── LearnScreen.kt       # Tab 1 — Learn
│   ├── ScriptScreen.kt      # Tab 2 — Script
│   ├── EtymoScreen.kt       # Tab 3 — Etymo
│   └── ProfileScreen.kt     # Tab 4 — Profile
└── ui/theme/
    ├── Color.kt             # Brand palette (yellow/dark/glass)
    ├── Theme.kt             # ETYMOTheme — dark, fixed color scheme
    └── Type.kt              # Typography
```

---

## Build & Run

```bash
# Debug build
./gradlew assembleDebug

# Install on connected device
./gradlew installDebug
```

Or hit **▶ Run** in Android Studio with the **app** configuration selected and your device plugged in with **USB Debugging** enabled.
