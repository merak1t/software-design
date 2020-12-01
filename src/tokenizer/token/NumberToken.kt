package tokenizer.token

import tokenizer.TokenType
import visitor.TokenVisitor


class NumberToken(private val num: Long) : Token {

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }

    override val type: TokenType
        get() = TokenType.NUMBER

    override fun toString(): String {
        return "NUMBER($num)"
    }
}