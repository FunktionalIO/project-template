import xerial.sbt.Sonatype.GitHubHosting
import xerial.sbt.Sonatype.sonatypeCentralHost

ThisBuild / tlBaseVersion := "0.0"
val scala3Version = "3.5.2"

ThisBuild / name                   := "project-template"
ThisBuild / homepage               := Some(url(s"https://github.com/FunktionalIO/${(ThisBuild / name).value}"))
ThisBuild / description            := ""
ThisBuild / scalaVersion           := scala3Version
ThisBuild / organization           := "io.funktional"
ThisBuild / organizationName       := "Funktional"
ThisBuild / organizationHomepage   := Some(url("https://funktional.io"))
ThisBuild / startYear              := Some(2024)
ThisBuild / licenses               := Seq("EPL-2.0" -> url("https://opensource.org/licenses/EPL-2.0"))
ThisBuild / developers             := List(
  Developer(
    id = "rlemaitre",
    name = "Raphaël Lemaitre",
    email = "github.com.lushly070@passmail.net",
    url = url("https://rlemaitre.com")
  )
)
// Publication
ThisBuild / sonatypeCredentialHost := sonatypeCentralHost
ThisBuild / sonatypeProjectHosting := Some(GitHubHosting(
  "FunktionalIO",
  (ThisBuild / name).value,
  "github.com.lushly070@passmail.net"
))
ThisBuild / scmInfo                := Some(
  ScmInfo(
    url(s"https://github.com/FunktionalIO/${(ThisBuild / name).value}"),
    s"scm:git:git@github.com:FunktionalIO/${(ThisBuild / name).value}.git"
  )
)

// Github actions
ThisBuild / githubWorkflowOSes         := Seq("ubuntu-latest")
ThisBuild / githubWorkflowJavaVersions := Seq(JavaSpec.temurin("17"))

// Dependencies versions
val versions = new {
    val munit = "1.0.2"
}

val sharedSettings = Seq(
  organization   := "io.funktional",
  scalaVersion   := "3.3.4",
  libraryDependencies ++= Seq(
    "org.scalameta" %%% "munit" % "1.0.2" % Test
  ),
  // Headers
  headerMappings := headerMappings.value + (HeaderFileType.scala -> HeaderCommentStyle.cppStyleLineComment),
  headerLicense  := Some(HeaderLicense.Custom(
    """|Copyright (c) 2024-2024 by Raphaël Lemaitre and Contributors
           |This software is licensed under the Eclipse Public License v2.0 (EPL-2.0).
           |For more information see LICENSE or https://opensource.org/license/epl-2-0
           |""".stripMargin
  ))
)

lazy val core          = project
    .in(file("modules/core"))
    .settings(sharedSettings)
    .settings(
      name         := s"${(ThisBuild / name).value}-core",
      scalaVersion := scala3Version,
      libraryDependencies ++= Seq(
        "org.pkl-lang"   % "pkl-config-java-all" % versions.pkl,
        "org.scalameta" %% "munit"               % versions.munit % Test
      )
    )
lazy val root = project
    .in(file("."))
    .aggregate(core)
    .settings(sharedSettings)
    .settings(
      name           := "${(ThisBuild / name).value}",
      publish / skip := true
    )
