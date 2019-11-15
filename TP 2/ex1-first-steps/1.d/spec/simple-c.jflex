%%
%include Jflex.include
%include JflexCup.include

Type = "void" | "int" | "long" | "char" | "float" | "double"
Identifier = [_a-zA-Z] [_a-zA-Z0-9]*

Ignored = \R | \s | {Comment}
Comment = {SimpleComment} | {MultiLineComment}
SimpleComment = "//".*
MultiLineComment = "/*" (\*+[^/*] | [^*])* \*+\/

Integer = -?[0-9]+
Decimal = {Integer}?\.[0-9]+
String = \".*\"

%%


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

{String} {
	return TOKEN(String);
}

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
