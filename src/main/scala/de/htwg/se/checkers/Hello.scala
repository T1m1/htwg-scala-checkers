package de.htwg.se.checkers

import de.htwg.se.checkers.model.Student

object Hello {
  def main(args: Array[String]): Unit = {
    val student = Student("Timi")

    println("Hello, " + student.name)
    println(s"Hello, ${student.name}") // string interpolation: http://docs.scala-lang.org/overviews/core/string-interpolation.html
  }
}
