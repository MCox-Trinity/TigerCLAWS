package models

import utility.Course

final case class pathwayCourse(id:Int, name:String, acceptFun: ((String,String)) => Boolean)