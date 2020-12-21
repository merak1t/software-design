package state

import tokenizer.token.BracketToken
import tokenizer.token.Token
import tokenizer.TokenType
import tokenizer.Tokenizer


class BracketState(tokenizer: Tokenizer) : State(tokenizer) {
    override fun getToken(): Token {
        val ch = tokenizer.current()
        tokenizer.next()
        return BracketToken(parseType(ch, tokenizer.getIndex()))
    }

    private fun parseType(ch: Char, index: Int): TokenType {
        return (when (ch) {
            '(' -> TokenType.LPAREN
            ')' -> TokenType.RPAREN
            else -> throw IllegalArgumentException("Unexpected '$ch' found at '$index'")
        })
    }
}