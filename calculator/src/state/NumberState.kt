package state

import tokenizer.token.NumberToken
import tokenizer.token.Token
import tokenizer.Tokenizer

class NumberState(tokenizer: Tokenizer) : State(tokenizer) {
    override fun getToken(): Token {
        var currCh = tokenizer.current()
        val builder = StringBuilder()
        while (Character.isDigit(currCh)) {
            builder.append(currCh)
            currCh = tokenizer.next()
        }
        return NumberToken(builder.toString().toLong())
    }
}