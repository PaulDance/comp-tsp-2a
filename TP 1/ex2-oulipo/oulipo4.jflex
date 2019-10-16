// Gets the first letter from every word of the input data.

%%
%include Jflex.include

WORD 	= [^\s,;:;\.\?!\/\\\|\(\)\[\]\{\}&=\+\"\'\`~%]+

%%


{WORD}	{ System.out.print(yycharat(0)); }
[^]		{ /* Ignored */ }
