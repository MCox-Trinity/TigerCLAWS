package models
object CodeGen extends App {
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.PostgresProfile", 
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost/tigerclaws?user=tigerclaws&password=password",
    "/Users/zhangchonghao/cs/TigerCLAWS/server/app/", 
    "models", None, None, true, false
  )
}
