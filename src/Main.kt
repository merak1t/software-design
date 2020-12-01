import tokenizer.token.Token
import tokenizer.Tokenizer
import visitor.PrintVisitor
import java.nio.file.Files
import java.nio.file.Paths


val time1 = { System.currentTimeMillis().toDouble() }
val st1 = time1()
val deltaTime1 = { (time1() - st1) / 1000.0 }

fun main(args: Array<String>) {
    val file = "test"
    //BufferedReader(InputStreamReader(System.`in`)).use { fin ->
    Files.newBufferedReader(Paths.get("$file.txt")).use { fin ->
        while (true) {
            val input = fin.readLine()
            if (input == null || input.isEmpty()) {
                break
            }
            val tokens: List<Token> = Tokenizer.parse(input)
            println("input: " + PrintVisitor().walk(tokens))
        }
    }
    println("time : ${deltaTime1()}")
}

