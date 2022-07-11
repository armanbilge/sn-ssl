ThisBuild / tlBaseVersion := "0.1"

ThisBuild / organization := "com.armanbilge"
ThisBuild / organizationName := "Arman Bilge"
ThisBuild / developers += tlGitHubDev("armanbilge", "Arman Bilge")
ThisBuild / startYear := Some(2022)
ThisBuild / tlSonatypeUseLegacyHost := false

ThisBuild / crossScalaVersions := Seq("3.1.3", "2.12.16", "2.13.8")

ThisBuild / githubWorkflowJavaVersions := Seq(JavaSpec.temurin("17"))

lazy val root = tlCrossRootProject.aggregate(ssl, tests)

lazy val ssl = project
  .in(file("ssl"))
  .enablePlugins(ScalaNativePlugin)
  .settings(
    name := "sn-ssl",
    scalacOptions -= "-source:3.0-migration"
  )

lazy val tests = crossProject(NativePlatform, JVMPlatform)
  .in(file("tests"))
  .enablePlugins(NoPublishPlugin)
  .nativeEnablePlugins(ScalaNativeJUnitPlugin)
  .settings(
    testOptions += Tests.Argument(TestFrameworks.JUnit, "-a", "-s", "-v")
  )
  .jvmSettings(
    fork := true,
    libraryDependencies ++= Seq(
      "junit" % "junit" % "4.13.2" % Test,
      "com.github.sbt" % "junit-interface" % "0.13.3" % Test
    )
  )
  .nativeConfigure(_.dependsOn(ssl))
