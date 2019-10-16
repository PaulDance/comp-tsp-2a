%%
%standalone
%%

// Entoure les identifiants par des crochets.
[a-zA-Z][a-zA-Z0-9]*  { System.out.print("["+yytext()+"]"); }
