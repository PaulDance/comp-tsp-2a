package syntax;
%% 
%include Jflex.include
%include JflexCup.include

// Macros
WS         = [ \t\f] | \R
EOLComment = "//" .*
C89Comment = "/*" [^*]* ("*" ([^*/] [^*]*)?)* "*/"
Ignore     = {WS}+ | {EOLComment} | {C89Comment}
Integer    = 0 | [1-9] [0-9]*
Identifier = [:jletter:] [:jletterdigit:]*
Println    = "System" {WS}* "." {WS}* "out" {WS}* "." {WS}* "println"

%%
// Mots Clés
"class"      { return TOKEN(CLASS);   }
"main"       { return TOKEN(MAIN);    }
{Println}    { return TOKEN(PRINTLN); }
"public"     { return TOKEN(PUBLIC);  }
"static"     { return TOKEN(STATIC);  }
"String"     { return TOKEN(STRING);  }
"void"       { return TOKEN(VOID);    }
// Operateurs

// Ponctuations 
";"          { return TOKEN(SEP);     }
"{"          { return TOKEN(LC);      }
"}"          { return TOKEN(RC);      }
"("          { return TOKEN(LP);      }
")"          { return TOKEN(RP);      }
"["          { return TOKEN(LB);      }
"]"          { return TOKEN(RB);      }
// literals
{Integer}    { return TOKEN(LIT_INT,  Integer.parseInt(yytext()));     }  
{Identifier} { return TOKEN(IDENT,    new String(yytext())) ;          }
// Ignore 
{Ignore}     {}
// Ramasse Miette
[^]          { WARN("Unknown char '"+yytext()+"' "); return TOKEN(error); }
