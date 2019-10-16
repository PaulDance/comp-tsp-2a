// Deletes the n and N letters from the input text.

%%
%include Jflex.include
%%


n | N		{ /* Ignored */ }		// Ns are to be deleted,
[^]			{ ECHO(); }				// the rest passes.
