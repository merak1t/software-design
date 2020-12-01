package visitor

import tokenizer.token.BracketToken
import tokenizer.token.NumberToken
import tokenizer.token.OperationToken
import tokenizer.token.Token
import java.lang.StringBuilder

class PrintVisitor : TokenVisitor {
    private val stringBuilder = StringBuilder()

    fun walk(tokens: List<Token>): String {
        tokens.forEach { token -> token.accept(this) }
        return stringBuilder.toString()
    }

    override fun visit(token: NumberToken) {
        add(token)
    }

    override fun visit(token: BracketToken) {
        add(token)
    }

    override fun visit(token: OperationToken) {
        add(token)
    }

    private fun add(token: Token) {
        stringBuilder.append(token.toString()).append(' ')
    }

}