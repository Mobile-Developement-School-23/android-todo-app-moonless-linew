package ru.linew.plugin

import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class UploadPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val androidComponents =
            project.extensions.findByType(AndroidComponentsExtension::class.java)
                ?: throw IllegalStateException("plugin not found!")

        androidComponents.onVariants { variant ->
            val variantName = variant.name.replaceFirstChar { it.uppercaseChar() }
            val apkFileDirectory = variant.artifacts.get(SingleArtifact.APK)
            project.tasks.register("uploadApkFor$variantName", UploadTask::class.java){
                apkDirectory.set(apkFileDirectory)
            }

        }
    }
}