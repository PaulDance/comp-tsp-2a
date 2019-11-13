%%
%include Jflex.include
%include JflexCup.include

TYPE = "int" | "long" | "char"
IDENT = [a-zA-Z] [a-zA-Z0-9]*
IGNORE = \R | [ \t\f] | "//".*

%%


{TYPE} {
	return TOKEN(TYPE);
}

{IDENT} {
	return TOKEN(IDENT);
}

";" {
	return TOKEN(SEMI);
}

{IGNORE} {
	// Ignored.
}

[^] {
	WARN("Unknown character : " + yytext());
	return TOKEN(UNK);
}
