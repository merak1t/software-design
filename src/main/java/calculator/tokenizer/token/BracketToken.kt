package tokenizer.token

import tokenizer.TokenType
import visitor.TokenVisitor

class BracketToken(override val type: TokenType) : Token {

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }

    override fun toString(): String {
        return type.toString()
    }
}
