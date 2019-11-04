// Deletes empty lines from the input.

%%
%include Jflex.include
%caseless
%%

//VoidLine = \n\n
//BlankLine = \n\s+\n
//LineEndSpace = \s+\n
//EmptySpace = {VoidLine} | {BlankLine} | {LineEndSpace}

//%%

(\R?\s*\R)++ {
	System.out.println();
}

[^]+ {
	ECHO();
}
