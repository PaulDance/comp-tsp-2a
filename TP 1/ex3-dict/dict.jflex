// Different types of dictionnary searches
// A dictionnary here is expected to have one word per line.

%%
%include Jflex.include
%caseless
%%


.*q[^u\n\r].* {										// q but not u.
	ECHOLN("Q^U");
}

[^zq\n\r]*(z[^z\n\r]*q | q[^q\n\r]*z)[^zq\n\r]* {	// Exactly one z and one q.
	ECHOLN("TopScrabble");
}

.*(z.*q.*x | z.*x.*q | q.*z.*x
		| q.*x.*z | x.*z.*q | x.*q.*z).* {			// At least one z, one q and one x.
	ECHOLN("SuperTopScrabble");
}

.*(z.*q | q.*z).* {									// At least one z and one q.
	ECHOLN("Scrabble");
}

^[a-f]{4}$ {										// 4-letter hex word.
	ECHOLN("4 Hexspeak");
}

^[a-fOIZSG]{4}$ {									// 4-letter "extended" hex word.
	ECHOLN("4 Extended Hexspeak");
}

^[a-f]+$ {											// Any length hex word.
	ECHOLN("Hexspeak");
}

^[a-fOIZSG]+$ {										// Any length "extended" hex word.
	ECHOLN("Extended Hexspeak");
}

[^] {												// The rest is passed.
	/* Ignored */
}
