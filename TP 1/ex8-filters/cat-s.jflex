// Mimics the `cat -s` Unix comand : deletes consecutive void and blank lines
// from the input while keeping at least one.

%%
%include Jflex.include
%caseless
%%


^(\s*\R)+ {						// For consecutive void or blank lines,
	System.out.println();		// just let an empty one pass.
}

[^] {
	ECHO();
}
