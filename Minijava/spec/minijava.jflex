package syntax;
%% 
%include Jflex.include
%include JflexCup.include

// Macros
WS = [ \t\f] | \R
EOLComment = "//" .*
C89Comment = "/*" [^*]* ("*" ([^*/] [^*]*)?)* "*/"
Ignore = {WS}+ | {EOLComment} | {C89Comment}
Integer = 0 | [1-9] [0-9]*
Identifier = [:jletter:] [:jletterdigit:]*
Println = "System" {WS}* "." {WS}* "out" {WS}* "." {WS}* "println"

%%
// Keywords
"class"				{ return TOKEN(CLASS); }
"extends"			{ return TOKEN(EXTENDS); }
"main"				{ return TOKEN(MAIN); }
{Println}			{ return TOKEN(PRINTLN); }
"public"			{ return TOKEN(PUBLIC); }
"static"			{ return TOKEN(STATIC); }
"String"			{ return TOKEN(STRING); }
"void"				{ return TOKEN(VOID); }
"boolean"			{ return TOKEN(BOOL); }
"int"				{ return TOKEN(INT); }
"this"				{ return TOKEN(THIS); }
"new"				{ return TOKEN(NEW); }
"if"				{ return TOKEN(IF); }
"else"				{ return TOKEN(ELSE); }
"while"				{ return TOKEN(WHILE); }
"return"			{ return TOKEN(RETURN); }
"length"			{ return TOKEN(LENGTH); }

// Binary operators
"+"					{ return TOKEN(PLUS); }
"-"					{ return TOKEN(MINUS); }
"*"					{ return TOKEN(TIMES); }
"&&"				{ return TOKEN(AND); }
"<"					{ return TOKEN(LESS); }

// Unary operators
"!"					{ return TOKEN(NOT); }
"="					{ return TOKEN(EQUALS); }

// Ponctuations
","					{ return TOKEN(COMMA); }
"." 				{ return TOKEN(DOT); }
";" 				{ return TOKEN(SEP); }
"{" 				{ return TOKEN(LC); }
"}" 				{ return TOKEN(RC); }
"(" 				{ return TOKEN(LP); }
")" 				{ return TOKEN(RP); }
"[" 				{ return TOKEN(LB); }
"]" 				{ return TOKEN(RB); }

// literals
{Integer}			{ return TOKEN(LIT_INT,	Integer.parseInt(yytext())); }
"true" | "false"	{ return TOKEN(LIT_BOOL, Boolean.parseBoolean(yytext())); }
{Identifier}		{ return TOKEN(IDENT,	new String(yytext())); }


// Ignore
{Ignore}			{}

// Ramasse Miette
[^]					{ WARN("Unknown char '" + yytext() + "' "); return TOKEN(error); }
