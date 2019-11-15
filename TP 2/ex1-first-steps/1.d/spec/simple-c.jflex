// Lexical definition for a C-like syntax meant to be analyzed with CUP.

%%
%include Jflex.include
%include JflexCup.include

// Only some types are supported through an enumeration.
Type = "void" | "int" | "long" | "char" | "float" | "double"
Identifier = [_a-zA-Z] [_a-zA-Z0-9]*

// Things to ignore completely from the syntax.
Ignored = \R | \s | {Comment}
Comment = {SimpleComment} | {MultiLineComment}
SimpleComment = "//".*
MultiLineComment = "/*" (\*+[^/*] | [^*])* \*+\/

// Literal expressions.
Integer = -?[0-9]+
Decimal = {Integer}?\.[0-9]+
Char = \'.\'
String = \".*\"

%%


// Reserved words.

"if" {
	return TOKEN(If);
}

"else" {
	return TOKEN(Else);
}

"while" {
	return TOKEN(While);
}

"for" {
	return TOKEN(For);
}


// Structures.

{Type} {
	return TOKEN(Type);
}

{Identifier} {
	return TOKEN(Identifier);
}

{Integer} {
	return TOKEN(Integer);
}

{Decimal} {
	return TOKEN(Decimal);
}

{Char} {
	return TOKEN(Char);
}

{String} {
	return TOKEN(String);
}


// Small static sequences.

"," {
	return TOKEN(Comma);
}

";" {
	return TOKEN(SemiColon);
}

"(" {
	return TOKEN(OpenParenthesis);
}

")" {
	return TOKEN(CloseParenthesis);
}

"{" {
	return TOKEN(OpenBrace);
}

"}" {
	return TOKEN(CloseBrace);
}

"!" {
	return TOKEN(Not);
}

"=" {
	return TOKEN(Equals);
}

"==" {
	return TOKEN(DoubleEquals);
}

"!=" {
	return TOKEN(NotEquals);
}

"+" {
	return TOKEN(Plus);
}

"++" {
	return TOKEN(DoublePlus);
}

"-" {
	return TOKEN(Minus);
}

"--" {
	return TOKEN(DoubleMinus);
}

"*" {
	return TOKEN(Star);
}

"/" {
	return TOKEN(Slash);
}

"<" {
	return TOKEN(LessThan);
}

"<=" {
	return TOKEN(LessThanOrEquals);
}

">" {
	return TOKEN(GreaterThan);
}

">=" {
	return TOKEN(GreaterThanOrEquals);
}

"&&" {
	return TOKEN(And);
}

"||" {
	return TOKEN(Or);
}


{Ignored} {
	// Ignored.
}

[^] {
	WARN("Unknown character : " + yytext());
}
