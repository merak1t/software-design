import tokenizer.token.Token
import tokenizer.Tokenizer
import visitor.CalcRPNVisitor
import visitor.RPNVisitor
import visitor.PrintTokensVisitor
import java.nio.file.Files
import java.nio.file.Paths


fun main(args: Array<String>) {
    val file = "test"
    //BufferedReader(InputStreamReader(System.`in`)).use { fin ->
    Files.newBufferedReader(Paths.get("$file.txt")).use { fin ->
        while (true) {
            val time1 = { System.currentTimeMillis().toDouble() }
            val st1 = time1()
            val deltaTime1 = { (time1() - st1) / 1000.0 }
            val input = fin.readLine() ?: break
            val tokens: List<Token> = Tokenizer.parse(input)
            val RPNTokens = RPNVisitor.toRPN(tokens)
            println("input  : $input")
            println("tokens : " + PrintTokensVisitor.print(tokens))
            println("RPN    : " + PrintTokensVisitor.print(RPNTokens))
            println("answer : " + CalcRPNVisitor.calculate(RPNTokens))
            println("time   : ${deltaTime1()}")
            println()
        }
    }
}

