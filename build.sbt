name := "ilike"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  javaJdbc,
  javaEbean,
  cache ,
  "org.json"%"org.json"%"chargebee-1.0",
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "be.objectify" %% "deadbolt-java" % "2.2-RC1",
  "commons-io" % "commons-io" % "2.3"
)     

resolvers ++= Seq(
        Resolver.url("Objectify Play Repository", url("http://schaloner.github.com/releases/"))(Resolver.ivyStylePatterns),
        Resolver.url("Objectify Play Repository - snapshots", url("http://schaloner.github.com/snapshots/"))(Resolver.ivyStylePatterns)
)

play.Project.playJavaSettings
