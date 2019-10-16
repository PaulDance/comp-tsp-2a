%%
%standalone
%%

// Surrounds identificators with brackets.
[a-zA-Z][a-zA-Z0-9]*  { System.out.print("["+yytext()+"]"); }
