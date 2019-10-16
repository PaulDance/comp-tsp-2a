// Basic non-typed C-like syntax specification.


%%
%include Jflex.include

COMMENT		= "//".*
WHITESPACE	= [\t ]
RETURN		= [\n\r\v]
REST		= [^]

%%


if | else | while | for 	{ ECHO("KW"); }					// Language keywords
[a-zA-Z_][a-zA-Z0-9_]*		{ ECHO("ID"); }					// Identificator
\+ | \- | \* | \/			{ ECHO("OP"); }					// Arithmetic operators
== | < | <= | > | >=		{ ECHO("CMP"); }				// Comparison operators
\= | \+\+ | \-\- | \+= | \-=
	| \*= | \/=				{ ECHO("AFF"); }				// Assignment operators
\( | \) | \[ | \] | \{ | \}
	| ; | \,				{ ECHO("SEP"); }				// Separators
[0-9]*\.?[0-9]*				{ ECHO("NUM"); }				// Integer and float numbers

{WHITESPACE}+ | {COMMENT}
	| {WHITESPACE}+{RETURN}	{ /* Ignored */ }
{RETURN}					{ System.out.println(); }
{REST}						{ WARN("Invalid character: '" + yytext() + "'"); }
