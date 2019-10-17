// WIP
%%
%include Jflex.include
%{
	int nbOK = 0, nbNOT = 0, nbUNK = 0;
	
	void OUT(String s) {
		System.out.print(s + ": " + yytext());
	}
	
	void OUTLN(String s) {
		System.out.println(s + ": " + yytext());
	}
%}
%eof{
	System.out.println("OK = " + nbOK + ", NOT = " + nbNOT + ", UNK = " + nbUNK);
%eof}

OK	= (a[^a] | b[^b])*
NOT	= .*(aa | bb).*

%%


^ {OK} \R {
	OUT("OK");
	nbOK++;
}

^ {NOT} \R {
	OUT("NOT");
	nbNOT++;
}

^ [ab]* \R {		// Balai sur Sigma*
	OUT("UNK");
	nbUNK++;
}

[^] {				// Autre que a,b ou NL
	WARN("Invalid Char");
}
