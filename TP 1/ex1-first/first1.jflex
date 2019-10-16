%%
%include Jflex.include
%%

[a-zA-Z][a-zA-Z0-9]*	{ ECHO("ID"); }		// Detects identificators and marks them.
[^]						{ ECHO(); }			// The rest always passes.
