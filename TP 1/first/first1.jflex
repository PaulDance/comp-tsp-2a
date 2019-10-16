%%
%include Jflex.include
%%

[a-zA-Z][a-zA-Z0-9]*	{ ECHO("ID"); }		// Détecte les identifiants et les met en valeur.
[^]						{ ECHO(); }			// Le reste passe toujours.
