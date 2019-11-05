// Mimics the `cat -b` Unix comand : numbers non empty input lines.
// The lines are not aligned though.

%%
%include Jflex.include
%caseless

%{
	int lineCount = 1;
%}
%%


^\s*\R {									// For a void or blank line,
	ECHO();									// let it pass without a number,
	lineCount++;							// but still increment the counter.
}

^.+$ {										// For the other lines,
	System.out.print(lineCount + "\t");		// print the line number and some space,
	ECHO();									// print the actual line
	lineCount++;							// and increment the line counter.
}

[^] {
	ECHO();
}
