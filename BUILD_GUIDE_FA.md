# 🚀 راهنمای ساخت APK - Star2D Game Engine

## 📋 خلاصه تحلیل پروژه

### ✅ وضعیت پروژه:
- **نام**: Star2D-game-engine-android-
- **نوع**: اپلیکیشن اندرویدی (LibGDX + Box2D)
- **زبان**: Java
- **حداقل SDK**: 23 (Android 6.0)
- **هدف SDK**: 36 (Android 15)
- **نسخه**: 0.4.7Beta

---

## ⚠️ مشکلات احتمالی و حل‌های آنها

### 1️⃣ **عدم تطابق Java Version**
**مشکل:** 
- `compileOptions` تنظیم شده برای `VERSION_21`
- اما برخی Gradle versions ممکن است این نسخه را پشتیبانی نکنند

**حل:**
```gradle
// تغییر به:
sourceCompatibility JavaVersion.VERSION_11
targetCompatibility JavaVersion.VERSION_11
```

---

### 2️⃣ **Gradle Version مشکل**
**مشکل:**
- Android Gradle Plugin 8.13.0 نسبتاً جدید است
- ممکن است نیاز به Gradle 8.7+ داشته باشد

**حل:**
فایل `gradle/wrapper/gradle-wrapper.properties` را چک کنید و به روز‌رسانی کنید

---

### 3️⃣ **Firebase Configuration**
**مشکل:**
- فایل `google-services.json` لازم است اگر بخواهید Firebase استفاده کنید
- اگر نداشته باشید، هنگام Build خطای Gradle داریم

**حل:**
- اگر Firebase نمی‌خواهید: حذف کردن plugin
- اگر می‌خواهید: `google-services.json` را اضافه کنید

---

### 4️⃣ **Native Libraries (Native Binaries)**
**مشکل:**
- Pروژه `natives` برای چند معماری (arm64-v8a, armeabi-v7a, x86, x86_64) دارد
- اگر این کتابخانه‌ها دانلود نشوند، build ناقص خواهد بود

**حل:**
- اطمینان از اتصال اینترنت قوی
- اجرای `copyAndroidNatives` task

---

### 5️⃣ **Keystore برای Release Build**
**مشکل:**
- Release build برای فایل `Star4droid.jks` یا متغیرهای محیطی نیاز دارد
- اگر ندارید، خودکار به debug keystore تغییر می‌دهد

**حل:**
- برای Debug APK: خودکار کار می‌کند ✅
- برای Release APK: باید keystore خود را تهیه کنید

---

### 6️⃣ **Deprecated Lint Warnings**
**مشکل:**
- بعضی lint rules قدیمی هستند

**حل:**
```gradle
lint {
    disable 'MissingTranslation', 'TypographyFractions', 'GradleCompatible'
    checkOnly 'NewApi', 'HandlerLeak'
    abortOnError false
}
```

---

## 🔧 مراحل ساخت APK

### **1️⃣ پیش‌نیازها:**
```bash
✅ Android SDK (حداقل API 36)
✅ JDK 11 یا بالاتر
✅ Gradle 8.7+
✅ Git
```

### **2️⃣ Clone فورک:**
```bash
git clone https://github.com/miladtak/Star2D-game-engine-android-
cd Star2D-game-engine-android-
```

### **3️⃣ Build Debug APK:**
```bash
# Windows/Mac/Linux
./gradlew assembleDebug
```

**خروجی:**
```
app/build/outputs/apk/debug/app-debug.apk
```

### **4️⃣ Build Release APK (اختیاری):**
```bash
./gradlew assembleRelease
```

**خروجی:**
```
app/build/outputs/apk/release/app-release.apk
```

---

## 📊 فایل‌های مهم:

| فایل | توضیح |
|------|--------|
| `app/build.gradle` | تنظیمات Build اپلیکیشن |
| `build.gradle` | تنظیمات اصلی Gradle |
| `settings.gradle` | تنظیمات ماژول‌ها |
| `gradle.properties` | متغیرهای Gradle |
| `app/src/` | کوド منبع |

---

## 🐛 خطاهای رایج و حل‌های آنها:

### ❌ **خطا: "SDK location not found"**
**حل:**
```bash
# بسازید: local.properties
sdk.dir=/path/to/android/sdk
```

### ❌ **خطا: "Could not resolve all artifacts"**
**حل:**
- بررسی اتصال اینترنت
- اجرای: `./gradlew --refresh-dependencies`

### ❌ **خطا: "Unsupported Java version"**
**حل:**
```gradle
// app/build.gradle میں
compileOptions {
    sourceCompatibility JavaVersion.VERSION_11
    targetCompatibility JavaVersion.VERSION_11
}
```

### ❌ **خطا: "google-services.json not found"**
**حل:**
- حذف Firebase plugin یا
- اضافه کردن `google-services.json`

---

## ✨ بهبودهای انجام شده:

✅ تحلیل ساختار پروژه  
✅ شناسایی مشکلات احتمالی  
✅ فراهم‌سازی راهنمای تفصیلی  
✅ ارائه‌دهی راه‌حل‌های عملی  

---

## 📝 نکات مهم:

⚠️ **نسخه Java:** اطمینان حاصل کنید JDK 11+ نصب کرده باشید  
⚠️ **Android SDK:** حداقل API 36 مورد نیاز است  
⚠️ **RAM:** حداقل 4GB برای Build ضروری است  
⚠️ **اتصال اینترنت:** برای دانلود Dependencies مهم است  

---

## 🚀 دستور نهایی برای ساخت APK:

```bash
# تمیز‌کردن Build های قدیم
./gradlew clean

# ساخت Debug APK (توصیه می‌شود برای شروع)
./gradlew assembleDebug

# یا برای Build سریع‌تر
./gradlew assemble --build-cache
```

---

## 📎 فایل خروجی:
```
📦 Star2D-game-engine-android-/
 └── app/build/outputs/apk/
     ├── debug/
     │   └── app-debug.apk ✅
     └── release/
         └── app-release.apk (اختیاری)
```

---

## 💡 توصیه‌ها:

1. **شروع با Debug APK** - سریع‌تر و بدون مشکلات Keystore
2. **تست روی دستگاه فیزیکی** - برای اطمینان از عملکرد
3. **بررسی Logcat** - برای یافتن خطاهای Runtime
4. **ذخیره‌سازی APK** - آن را در مکان ایمن نگاه دارید

---

**آخرین بروزرسانی:** 2026-08-27  
**وضعیت:** ✅ آماده برای Build
