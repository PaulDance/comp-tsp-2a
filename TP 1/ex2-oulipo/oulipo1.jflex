// Deletes the e and E letters from the input text.

%%
%include Jflex.include
%%


e | E		{ /* Ignored */ }		// Es are to be deleted,
[^]			{ ECHO(); }				// the rest passes.
