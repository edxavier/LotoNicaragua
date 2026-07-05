# AGENTS.md

## Repo at a glance
- Single-module Android app (no monorepo): `:app` only (`settings.gradle`).
- Build uses Groovy Gradle scripts plus version catalog (`build.gradle`, `app/build.gradle`, `gradle/libs.versions.toml`).
- UI is mixed ViewBinding + XML fragments + embedded Jetpack Compose `ComposeView` blocks (not a pure Compose app).

## Environment prerequisites
- Gradle wrapper is present (`gradlew.bat` / `gradlew`) and targets Gradle `8.14.4` (`gradle/wrapper/gradle-wrapper.properties`).
- Local runs require JDK configured via `JAVA_HOME`; wrapper fails immediately if missing (verified with `gradlew.bat -q projects`).
- Android SDK path is expected via untracked `local.properties` (`sdk.dir=...`).

## High-value commands
- Windows: `./gradlew.bat :app:assembleDebug`
- macOS/Linux: `./gradlew :app:assembleDebug`
- Unit tests: `./gradlew :app:testDebugUnitTest`
- Instrumented tests (device/emulator required): `./gradlew :app:connectedDebugAndroidTest`
- Lint: `./gradlew :app:lintDebug`

## Codebase wiring you should know
- App entrypoints: `app/src/main/AndroidManifest.xml`, `app/src/main/java/com/resultados/loto/lotonicaragua/LotoApplication.kt`, `app/src/main/java/com/resultados/loto/lotonicaragua/MainActivity.kt`.
- Navigation graph is XML with Safe Args (`app/src/main/res/navigation/mobile_navigation.xml`), but `MainActivity` also registers custom navigators (`DestinoCompartirApp`, `DestinoValorarApp`) before inflating the graph.
- Network layer is Retrofit + Moshi + coroutine `Deferred` adapter (`data/api/ResultsApiService.kt`, `data/api/ApiProvider.kt`), consumed by `data/repo/RepoResults.kt`.

## Repo-specific gotchas
- API base URL and auth token are currently hardcoded in `app/src/main/java/com/resultados/loto/lotonicaragua/data/api/ApiProvider.kt`; do not assume env-based config.
- Remote Config default URL is also hardcoded in `app/src/main/java/com/resultados/loto/lotonicaragua/MainActivity.kt`.
- App includes Firebase/Ads/Crashlytics and requires checked-in `app/google-services.json`; avoid removing or renaming it.
- `android:usesCleartextTraffic="true"` is enabled in manifest; HTTP endpoints are intentional in current setup.
- `build/` and `app/release/` artifacts are ignored by git (`.gitignore`); do not commit generated APK/AAB outputs.
