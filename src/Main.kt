import tokenizer.token.Token
import tokenizer.Tokenizer
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
            val input = fin.readLine()
            if (input == null || input.isEmpty()) {
                break
            }
            val tokens: List<Token> = Tokenizer.parse(input)
            println("input: " + PrintTokensVisitor.print(tokens))
            val RPNTokens = RPNVisitor.toRPN(tokens)
            println("RPN  : " + PrintTokensVisitor.print(RPNTokens))
            println("time : ${deltaTime1()}")
        }
    }
}

