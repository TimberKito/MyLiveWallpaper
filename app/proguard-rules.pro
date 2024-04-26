# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keepattributes Signature
-keepattributes *Annotation*
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class * implements com.google.gson.TypeAdapter { *; }
-keep class com.google.gson.internal.LinkedTreeMap { *; }
-keep class com.timber.soft.mylivewallpaper.tools.AppTools
-keep class com.timber.soft.mylivewallpaper.data.** { *; }


-keepclassmembers class *{
  @androidx.room.Query <methods>;
}

-keep class com.timber.soft.mylivewallpaper.tools.AppFinalString {
    public static final java.lang.String DB_NAME;
    public static final int DB_VERSION;
}

-keepattributes Signature
-keepattributes *Annotation*