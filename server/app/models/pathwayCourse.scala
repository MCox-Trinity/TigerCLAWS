package models


final case class pathwayCourse(id:Int, name:String, acceptFun: ((String,String)) => Boolean)