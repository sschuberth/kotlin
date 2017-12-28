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


apply { plugin("kotlin") }

dependencies {
    compile(project(":compiler:util"))
    compileOnly(intellijCoreDep()) { includeJars("intellij-core") }
    compileOnly(intellijDep()) { includeJars("guava-21.0", "android-base-common", rootProject = rootProject) }
    compileOnly(intellijPluginDep("gradle")) { includeJars("gradle-tooling-api", rootProject = rootProject) }
    compileOnly(intellijPluginDep("android")) { includeJars("android", "android-common", "sdk-common") }
}

sourceSets {
    "main" { projectDefault() }
    "test" {}
}

