name := "ilike"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache ,
  "org.json"%"org.json"%"chargebee-1.0"
)     

play.Project.playJavaSettings
