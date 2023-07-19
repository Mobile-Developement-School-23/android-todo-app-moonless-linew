pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        maven ("https://jitpack.io")
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("libs"){
            from(files("libs.toml"))
        }
    }
}
rootProject.name = "ToDoApp"
include(":app")
