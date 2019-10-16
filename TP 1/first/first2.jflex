%%
%include Jflex.include

COMMENT		= "//".*
WHITESPACE	= [\t ]
RETURN		= [\n\r\v]
REST		= [^]

%%


if | else | while | for 	{ ECHO("KW"); }
[a-zA-Z_][a-zA-Z0-9_]*		{ ECHO("ID"); }
\+ | \- | \* | \/			{ ECHO("OP"); }
== | < | <= | > | >=		{ ECHO("CMP"); }
\= | \+\+ | \-\- | \+= | \-=
	| \*= | \/=				{ ECHO("AFF"); }
\( | \) | \[ | \] | \{ | \}
	| ; | \,				{ ECHO("SEP"); }
[0-9]*\.?[0-9]*				{ ECHO("NUM"); }

{WHITESPACE}+ | {COMMENT}
	| {WHITESPACE}+{RETURN}	{ /* Ignored */ }
{RETURN}					{ System.out.println(); }
{REST}						{ WARN("Invalid character: '" + yytext() + "'"); }
