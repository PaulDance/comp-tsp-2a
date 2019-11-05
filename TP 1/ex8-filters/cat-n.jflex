// Mimics the `cat -n` Unix comand : numbers input lines.
// The lines are not aligned though.

%%
%include Jflex.include
%caseless

%{
	int lineCount = 1;
%}
%%


^.*\R {										// For each line,
	System.out.print(lineCount + "\t");		// print the line number and some space,
	ECHO();									// print the actual line
	lineCount++;							// and increment the line counter.
}

[^] {
	ECHO();
}
