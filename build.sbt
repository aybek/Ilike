name := "ilike"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache ,
  "org.json"%"org.json"%"chargebee-1.0",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "be.objectify" %% "deadbolt-java" % "2.2-RC1"
)     

play.Project.playJavaSettings
