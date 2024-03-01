plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.firebasefirestoredatabase"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.firebasefirestoredatabase"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //LOTTIEFILES
    implementation("com.airbnb.android:lottie:6.0.1")

    //FIREBASE
    // Import the BoM for the Firebase platform
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))

    //FIRESTORE
    implementation("com.google.firebase:firebase-firestore")
    implementation ("com.firebaseui:firebase-ui-firestore:8.0.2")

    //firebase analytics
    implementation("com.google.firebase:firebase-analytics")

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation("com.google.firebase:firebase-auth")


//    //PIN VIEW
    implementation ("io.github.chaosleung:pinview:1.4.4")

    
    implementation("com.google.android.gms:play-services-auth:20.7.0")

    //COUNTRY CODE PICKER
    implementation ("com.hbb20:ccp:2.5.0")

    //Navigation Controller  //TESTING //NOT USED
    implementation ("androidx.navigation:navigation-fragment:2.5.2")
    implementation ("androidx.navigation:navigation-ui:2.5.2")
}