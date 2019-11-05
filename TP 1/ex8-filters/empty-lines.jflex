// Deletes void, blank lines and blank line ends from the input.

%%
%include Jflex.include
%caseless
%%


^(\s*\R)+ {					// Void or blank consecutive lines.
	// Deleted.
}

\s+$ {						// For line end blanks,
	System.out.println();	// it seems necessary to output an empty line still.
}

[^] {
	ECHO();
}
