package tokenizer.token

import tokenizer.TokenType
import visitor.TokenVisitor

class OperationToken(override val type: TokenType) : Token {

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }

    override fun toString(): String {
        return type.toString()
    }

    fun evaluate(a: Long, b: Long): Long {
        return when (type) {
            TokenType.PLUS -> a + b
            TokenType.MINUS -> a - b
            TokenType.MUL -> a * b
            TokenType.DIV -> a / b
            else -> throw RuntimeException("Unexpected type $type")
        }
    }
}