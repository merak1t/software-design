package visitor

import tokenizer.token.BracketToken
import tokenizer.token.NumberToken
import tokenizer.token.OperationToken

interface TokenVisitor {

    fun visit(token: NumberToken)

    fun visit(token: BracketToken)

    fun visit(token: OperationToken)
}