%%
%include Jflex.include
%%
[a-zA-Z] [a-zA-Z0-9]*	{ ECHO("ID"); }
[^]						{ ECHO(); }
