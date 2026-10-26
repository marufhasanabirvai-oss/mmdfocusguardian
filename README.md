# Focus Guardian - Native Android Self-Control & Wellbeing Blocker

Built with **Kotlin**, **Jetpack Compose (Material 3)**, **Room Database**, **DataStore Preferences**, and clean architecture.

## Architecture Overview
- **VpnService**: Intercepts DNS port 53 locally via 127.0.0.1 to sinkhole configured domains and adult categories.
- **AccessibilityService**: Recursively monitors visible screen nodes for blocked words using Unicode-aware regex boundaries.
- **Visual Pattern Matcher**: MediaProjection service computes 64-bit dHash and compares Hamming distance on-device.
- **Device Administration**: Prevents casual uninstall. Includes Device Owner ADB provision for permanent enrollment.
- **Protection Lock**: Enforces unmodifiable rule locks (1 to 30 days) to prevent hasty disabling during impulsive moments.
- **Penalty Engine**: Dispatches `ACTION_MAIN` (`CATEGORY_HOME`) and displays non-dismissible lockout countdown screen.

## Permissions Required
1. `BIND_ACCESSIBILITY_SERVICE`: Inspects on-screen text in real-time.
2. `BIND_VPN_SERVICE`: Local DNS sinkhole for website & adult blocking.
3. `BIND_DEVICE_ADMIN`: Self-control uninstall prevention.
4. `POST_NOTIFICATIONS`: Android 13+ penalty warnings.
5. `RECEIVE_BOOT_COMPLETED`: Re-engages active protection locks on restart.

## How to Build in Android Studio
1. Open Android Studio (Ladybug / Koala or newer).
2. Click **File -> Open** and select this directory.
3. Allow Gradle to sync.
4. Click **Build -> Build Bundle(s) / APK(s) -> Build APK(s)** or click the Green **Run** button to install on emulator/device.
