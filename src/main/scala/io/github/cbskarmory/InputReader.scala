package io.github.cbskarmory

import io.github.cbskarmory.Lexer.tokenize
import io.github.cbskarmory.Parser.parseExpr

import scala.annotation.tailrec
import scala.io.StdIn.readLine

// $COVERAGE-OFF$
object InputReader {
    def main(args: Array[String]): Unit = {

        println("Type 'exit' to exit or an expression to simplify. Ctrl-C to cancel")

        @tailrec
        def readInput(): Unit = {
            val ln = readLine().toLowerCase()
            if (ln == "exit") {
                return
            }
            try {
                println("--------")
                val exp = parseExpr(tokenize(ln.toList))
                println(s"Parsed: $exp")
                val simplifier = new Simplifier(exp)
                val path = simplifier.getWork match {
                    case None => "unknown"
                    case Some(p) => p.map(_.toString).reduce((a,b) => a + "\n" + b)
                }
                println(s"${simplifier.checks} nodes explored")
                println(s"${simplifier.getWork.getOrElse(Vector()).size} steps\n--------")
                println(path)

            } catch {
                case e: InterpreterError => println(e.toString + " | Please try again")
                case e: InterruptedException => println("<canceled>")
            }
            readInput()
        }
        readInput()
    }
}
// $COVERAGE_ON$