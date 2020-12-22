package tokenizer.token

import tokenizer.TokenType
import visitor.TokenVisitor


interface Token {
    fun accept(visitor: TokenVisitor)
    val type: TokenType

    override fun toString(): String
}