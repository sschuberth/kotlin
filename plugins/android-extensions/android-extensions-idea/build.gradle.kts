/*
 * Copyright 2010-2017 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


description = "Kotlin Android Extensions IDEA"

apply { plugin("kotlin") }

jvmTarget = "1.6"

val androidSdk by configurations.creating
val androidJar by configurations.creating

dependencies {
    testRuntime(intellijCoreDep()) { includeJars("intellij-core") }
    testRuntime(intellijDep())

    compile(project(":compiler:util"))
    compile(project(":compiler:light-classes"))
    compile(project(":idea:idea-core"))
    compile(project(":idea"))
    compile(project(":idea:idea-jvm"))
    compile(project(":idea:idea-gradle"))
    compile(project(":plugins:android-extensions-compiler"))
    compileOnly(project(":kotlin-android-extensions-runtime"))
    compileOnly(intellijPluginDep("android")) { includeJars("android.jar", "android-common.jar", "sdk-common.jar", "sdk-tools.jar") }
    compileOnly(intellijPluginDep("Groovy")) { includeJars("Groovy.jar") }
    compileOnly(intellijDep()) { includeJars("extensions.jar", "openapi.jar", "util.jar", "idea.jar", "android-base-common.jar", rootProject = rootProject) }

    testCompile(project(":compiler:tests-common"))
    testCompile(project(":compiler:cli"))
    testCompile(project(":compiler:frontend.java"))
    testCompile(project(":idea:idea-test-framework")) { isTransitive = false }
    testCompile(project(":plugins:kapt3-idea"))
    testCompile(projectTests(":compiler:tests-common"))
    testCompile(projectTests(":idea"))
    testCompile(projectTests(":idea:idea-android"))
    testCompile(projectDist(":kotlin-test:kotlin-test-jvm"))
    testCompile(commonDep("junit:junit"))
    testRuntime(projectDist(":kotlin-reflect"))
    testCompile(intellijPluginDep("android")) { includeJars("android", "android-common", "sdk-common", "sdk-tools") }
    testCompile(intellijPluginDep("Groovy")) { includeJars("Groovy") }
    testCompile(intellijDep()) { includeJars("extensions") }

    testRuntime(project(":idea:idea-jvm"))
    testRuntime(project(":plugins:android-extensions-jps"))
    testRuntime(project(":sam-with-receiver-ide-plugin"))
    testRuntime(project(":noarg-ide-plugin"))
    testRuntime(project(":allopen-ide-plugin"))
    testRuntime(project(":plugins:lint"))
    testRuntime(intellijPluginDep("junit"))
    testRuntime(intellijPluginDep("IntelliLang"))
    testRuntime(intellijPluginDep("properties"))
    testRuntime(intellijPluginDep("java-i18n"))
    testRuntime(intellijPluginDep("gradle"))
    testRuntime(intellijPluginDep("Groovy"))
    testRuntime(intellijPluginDep("java-decompiler"))
    testRuntime(intellijPluginDep("maven"))
    testRuntime(intellijPluginDep("android"))

    androidSdk(project(":custom-dependencies:android-sdk", configuration = "androidSdk"))
    androidJar(project(":custom-dependencies:android-sdk", configuration = "androidJar"))
}

sourceSets {
    "main" { projectDefault() }
    "test" { projectDefault() }
}

testsJar {}

projectTest {
    dependsOn(":kotlin-android-extensions-runtime:dist")
    dependsOn(androidSdk)
    workingDir = rootDir
    doFirst {
        systemProperty("android.sdk", androidSdk.singleFile.canonicalPath)
        systemProperty("android.jar", androidJar.singleFile.canonicalPath)
        systemProperty("idea.home.path", intellijRootDir().canonicalPath)
    }
}

runtimeJar()

ideaPlugin()
