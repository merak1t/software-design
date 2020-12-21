package visitor

import tokenizer.TokenType
import tokenizer.token.BracketToken
import tokenizer.token.NumberToken
import tokenizer.token.OperationToken
import tokenizer.token.Token
import kotlin.collections.ArrayDeque
import kotlin.collections.List
import kotlin.collections.forEach


class RPNVisitor private constructor() : TokenVisitor {
    private val tokens = ArrayDeque<Token>()
    private val stack = ArrayDeque<Token>()


    override fun visit(token: NumberToken) {
        tokens.add(token)
    }

    override fun visit(token: BracketToken) {
        if (token.type === TokenType.LPAREN) {
            stack.add(token)
        } else {
            var last = stack.removeLast()
            while (!stack.isEmpty() && last.type !== TokenType.LPAREN) {
                tokens.add(last)
                last = stack.removeLast()
            }
        }
    }

    override fun visit(token: OperationToken) {
        if (stack.isNotEmpty()) {
            var last: Token? = stack.lastOrNull()
            while (!stack.isEmpty() && token.type.priority <= last!!.type.priority) {
                tokens.add(stack.removeLast())
                last = stack.lastOrNull()
            }
        }
        stack.add(token)
    }


    private fun transform(input: List<Token>): List<Token> {
        input.forEach { token -> token.accept(this) }
        tokens.addAll(stack.reversed())
        stack.clear()
        return tokens.toList()
    }

    companion object {
        fun toRPN(tokens: List<Token>): List<Token> {
            return RPNVisitor().transform(tokens)
        }
    }
}