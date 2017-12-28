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

extra["versions.intellijSdk"] = "172.4343.14"
extra["versions.androidBuildTools"] = "r23.0.1"
extra["versions.idea.NodeJS"] = "172.3757.32"
//extra["versions.intellijSdk"] = "173.3942.27"
//extra["versions.androidStudioRelease"] = "3.1.0.5"
//extra["versions.androidStudioBuild"] = "173.4506631"

val gradleJars = listOf(
        "gradle-tooling-api", "gradle-base-services", "gradle-wrapper", "gradle-core", "gradle-base-services-groovy"
)

val platform = extra["versions.intellijSdk"].toString().substringBefore('.')
if (platform == "173") {
    extra["versions.jar.streamex"] = "0.6.5"
    extra["versions.jar.gson"] = "2.8.2"
    for (jar in gradleJars) {
        extra["versions.jar.$jar"] = "4.0"
    }
    extra["ignore.jar.lombok-ast-0.2.3"] = true
}
else {
    extra["versions.jar.streamex"] = "0.6.2"
    extra["versions.jar.gson"] = "2.5"
    for (jar in gradleJars) {
        extra["versions.jar.$jar"] = "3.5"
    }
}

if (!extra.has("versions.androidStudioRelease")) {
    extra["ignore.jar.android-base-common"] = true
}


