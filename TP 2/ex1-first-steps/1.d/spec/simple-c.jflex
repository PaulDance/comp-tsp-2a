%%
%include Jflex.include
%include JflexCup.include

Type = "int" | "long" | "char" | "float" | "double"
Identifier = [_a-zA-Z] [_a-zA-Z0-9]*

Ignored = \R | \s | {Comment}
Comment = {SimpleComment} | {MultiLineComment}
SimpleComment = "//".*
MultiLineComment = "/*" (\*+[^/*] | [^*])* \*+\/

Integer = [0-9]+
Decimal = {Integer}?\.{Integer}
String = \".*\"

BinaryOperator = [-+*/]

%%


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

"=" {
	return TOKEN(Equals);
}

{BinaryOperator} {
	return TOKEN(BinaryOperator);
}

/*
	"+" {
		return TOKEN(Plus);
	}
	
	"-" {
		return TOKEN(Minus);
	}
	
	"*" {
		return TOKEN(Star);
	}
	
	"/" {
		return TOKEN(Slash);
	}
*/

{Ignored} {
	// Ignored.
}

[^] {
	WARN("Unknown character : " + yytext());
	return TOKEN(Unknown);
}
