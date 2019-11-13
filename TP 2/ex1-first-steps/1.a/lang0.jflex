import java_cup.runtime.Symbol;

%%
%cup
%extends sym

TYPE = "int" | "long" | "char"
IDENT = [a-zA-Z] [a-zA-Z0-9]*
IGNORE = \R | [ \t\f] | "//".*

%%


{TYPE} {
	return new Symbol(TYPE);
}

{IDENT} {
	return new Symbol(IDENT);
}

";" {
	return new Symbol(SEMI);
}

{IGNORE} {
	// Ignored.
}

[^] {
	return new Symbol(UNK);
}
