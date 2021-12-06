# Deobfuscated crash reports
-keepattributes SourceFile,LineNumberTable          # Keep file names/lines numbers
-keep public class * extends java.lang.Exception    # Keep custom exceptions

-keep class org.threeten.bp.** { *; }

#network
-keep class dev.jakal.codechallenge.infrastructure.common.network.model.** { *; }
