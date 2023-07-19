plugins {
    `kotlin-dsl`
}
gradlePlugin {
    plugins.register("upload-telegram-plugin") {
        id = "upload-telegram-plugin"
        implementationClass = "ru.linew.plugin.UploadPlugin"
    }
}
dependencies {
    implementation(libs.agp)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.bundles.ktor)
}