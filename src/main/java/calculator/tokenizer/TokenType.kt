package tokenizer


enum class TokenType constructor(val priority: Int) {
    LPAREN(0),
    RPAREN(0),
    PLUS(1),
    MINUS(1),
    MUL(2),
    DIV(2),
    NUMBER(3);
}