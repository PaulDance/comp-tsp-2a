// JSON parser's lexical definition in JFlex syntax.

%%
%include Jflex.include
%include JflexCup.include

Number = -? {Integer} {FractionalPart}? {ExpNotation}?
Integer = 0 | [1-9][0-9]*
FractionalPart = \.[0-9]+
ExpNotation = [eE] [+-]? [0-9]+

String = \"{Char}*\"
Char = {UnescapedChar} | {EscapedChar}
UnescapedChar = [\x20-\x21\x23-\x5B\x5D-\uFFFF]
EscapedChar = \\ ([\"\\/bfnrt] | u[:xdigit:]{4})

// Things to ignore completely from the syntax. Although comments don't exist in JSON,
// they are implemented here for debugging purposes only. Consider them not part of
// the hereby described lexicon meant for the JSON syntax.
Ignored = {Whitespace} | {Comment}
Whitespace = (\R | \s)+
Comment = {SimpleComment} | {MultiLineComment}
SimpleComment = "//".*
MultiLineComment = "/*" (\*+[^/*] | [^*])* \*+\/

%%

// Literals

"null" {
	return TOKEN(Null);
}

"true" {
	return TOKEN(True);
}

"false" {
	return TOKEN(False);
}

{Number} {
	return TOKEN(Number);
}

{String} {
	return TOKEN(String);
}


// Small static sequences.

"," {
	return TOKEN(Comma);
}

":" {
	return TOKEN(Colon);
}

"[" {
	return TOKEN(OpenBracket);
}

"]" {
	return TOKEN(CloseBracket);
}

"{" {
	return TOKEN(OpenBrace);
}

"}" {
	return TOKEN(CloseBrace);
}


{Ignored} {
	// Ignored.
}

[^] {
	WARN("Unknown character: " + yytext());
}