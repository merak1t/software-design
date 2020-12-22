package state

import tokenizer.token.OperationToken
import tokenizer.token.Token
import tokenizer.TokenType
import tokenizer.Tokenizer


class OperationState(tokenizer: Tokenizer) : State(tokenizer) {
    override fun getToken(): Token {
        val ch = tokenizer.current()
        tokenizer.next()
        return OperationToken(parseType(ch))
    }

    private fun parseType(ch: Char): TokenType {
        return (when (ch) {
            '+' -> TokenType.PLUS
            '-' -> TokenType.MINUS
            '*' -> TokenType.MUL
            '/' -> TokenType.DIV
            else -> throw IllegalArgumentException("Unsupported operation '$ch' found")
        })
    }
}