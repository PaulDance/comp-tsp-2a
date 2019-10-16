// Deletes the r and R letters from the input text.

%%
%include Jflex.include
%%


r | R		{ /* Ignored */ }		// Rs are to be deleted,
[^]			{ ECHO(); }				// the rest passes.
