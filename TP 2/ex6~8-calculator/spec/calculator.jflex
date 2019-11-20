%%
%include Jflex.include
%include JflexCup.include

Newline = \R

Ignored = {Whitespace} | {Comment}
Whitespace = \s
Comment = {LineEndComment}
LineEndComment =  "#" .*

Number = {PositiveInteger} | {Decimal}
Decimal = {PositiveInteger} {FractionalPart}
PositiveInteger = 0 | [1-9][0-9]*
FractionalPart = \.[0-9]+

%%


"+" {
	return TOKEN(Plus, yycharat(0));
}

"-" {
	return TOKEN(Minus, yycharat(0));
}

"*" {
	return TOKEN(Times, yycharat(0));
}

"/" {
	return TOKEN(Divide, yycharat(0));
}

"%" {
	return TOKEN(Modulo, yycharat(0));
}

"(" {
	return TOKEN(OpenParenthesis, yycharat(0));
}

")" {
	return TOKEN(CloseParenthesis, yycharat(0));
}

"," {
	return TOKEN(Comma, yycharat(0));
}

"min" {
	return TOKEN(MinFunction);
}

"max" {
	return TOKEN(MaxFunction);
}

{Decimal} {
	return TOKEN(Decimal, Double.parseDouble(yytext()));
}

{PositiveInteger} {
	return TOKEN(PositiveInteger, Integer.parseInt(yytext()));
}

{Newline} {
	return TOKEN(Newline);
}

{Ignored} {}

[^] {
	WARN("Unknown character: " + yytext());
	return TOKEN(error);
}
