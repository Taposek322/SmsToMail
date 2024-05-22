// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrains.kotlin) apply false
    kotlin("kapt") version "1.9.22" apply false
    alias(libs.plugins.develops.ksp) apply false
    alias(libs.plugins.dagger.hilt) apply false
}