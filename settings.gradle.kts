pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication {
                authentication.create<BasicAuthentication>("basic")
            }
            credentials {
                // Do not change the username below. It should always be "mapbox" (not your username).
                username = "mapbox"
                // Use the secret token stored in gradle.properties as the password
                password = "sk.eyJ1IjoiZGFkYWRkYS04OSIsImEiOiJjbHFqazNyNGMxbDN4MmlzNTc2NG9zNTEzIn0.yHOPyi--hIGT_Zc7AEZ7ZA"
            }
        }
    }
}

rootProject.name = "FieldPreview"
include(":app")
