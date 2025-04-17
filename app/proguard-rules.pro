# ========== GraphHopper Core ==========
-keep class com.graphhopper.** { *; }
-dontwarn com.graphhopper.**

# ========== JTS Geometry Engine ==========
-keep class org.locationtech.jts.** { *; }
-dontwarn org.locationtech.jts.**

# ========== Room Persistence Library ==========
# Keep annotations
-keepattributes *Annotation*

# Room database and DAO keep rules
-keep class androidx.room.** { *; }
-dontwarn androidx.room.**

# - Keep Entity, Dao, and Database classes
-keep class ** extends androidx.room.RoomDatabase
-keep class ** implements androidx.room.RoomDatabase
-keep class ** implements androidx.room.Dao

# ========== Gson (if using JSON anywhere, esp. in GraphHopperConfig) ==========
-keep class com.google.gson.** { *; }
-dontwarn com.google.gson.**

# Gson model rule
-keep class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

# ========== Retrofit / OkHttp (if you ever use HTTP in the app) ==========
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.Nullable
-dontwarn javax.annotation.ParametersAreNonnullByDefault

# ========== General Java Reflection Support ==========
-keepclassmembers class * {
    ** MODULE$;
}
-keepclassmembers class * {
    public <init>(...);
}

# Optional: Don't strip logging (for debug in release)
-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int d(...);
    public static int i(...);
    public static int w(...);
    public static int e(...);
}
