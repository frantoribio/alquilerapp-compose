AlquilerApp - Android (Jetpack Compose)
=======================================

Mini project scaffold generated automatically.

- Package: com.example.alquilerapp
- UI: Jetpack Compose
- Network: Retrofit (points to http://10.0.2.2:8080/api/)
- Authentication: Real JWT token saved in DataStore preferences, and used in Authorization header for protected calls.

How to run:
1. Open in Android Studio (you may need to download Gradle wrapper or let Android Studio configure it).
2. Start your Spring backend locally on your host machine (default expected: http://localhost:8080).
3. Run the app on an Android emulator (emulator can access host localhost via 10.0.2.2).

Important files:
- app/src/main/java/com/example/alquilerapp/MainActivity.kt
- app/src/main/java/com/example/alquilerapp/data/network/ApiService.kt
- app/src/main/java/com/example/alquilerapp/data/network/RetrofitClient.kt
- app/src/main/java/com/example/alquilerapp/data/model/*.kt
- app/src/main/java/com/example/alquilerapp/repository/*.kt
- app/src/main/java/com/example/alquilerapp/viewmodel/*.kt
- app/src/main/java/com/example/alquilerapp/ui/screens/*.kt
