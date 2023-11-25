pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

        //для анімації
        maven {setUrl("https://oss.sonatype.org/content/repositories/snapshots")}
    }
}

rootProject.name = "Days counter"
include(":app")
 