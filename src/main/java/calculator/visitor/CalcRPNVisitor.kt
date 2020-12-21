package visitor

import tokenizer.token.BracketToken
import tokenizer.token.NumberToken
import tokenizer.token.OperationToken
import tokenizer.token.Token
import kotlin.collections.ArrayDeque
import kotlin.collections.List


class CalcRPNVisitor : TokenVisitor {
    private val stack = ArrayDeque<Long>()
    fun calc(tokens: List<Token>): Long {
        if (tokens.isEmpty()) {
            return 0L
        }
        tokens.forEach { token: Token -> token.accept(this) }
        val result = stack.removeLast()
        if (!stack.isEmpty()) {
            throw RuntimeException("Wrong input expression")
        }
        return result
    }

    override fun visit(token: NumberToken) {
        stack.add(token.getInt())
    }

    override fun visit(token: BracketToken) {
        throw UnsupportedOperationException("RPN unsupported brackets tokens")
    }

    override fun visit(token: OperationToken) {
        if (stack.size < 2) {
            throw UnsupportedOperationException("Unsupported binary operation ${token.type} with less than 2 argument")
        }
        val second = stack.removeLast()
        val first = stack.removeLast()
        stack.add(token.evaluate(first, second))
    }

    companion object {
        fun calculate(tokens: List<Token>): Long {
            return CalcRPNVisitor().calc(tokens)
        }
    }
}