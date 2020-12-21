package state

import tokenizer.token.Token
import tokenizer.Tokenizer

abstract class State(protected val tokenizer: Tokenizer) {
    abstract fun getToken(): Token
}