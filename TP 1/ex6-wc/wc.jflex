// JFlex version of unix program `wc`.

%%
%include Jflex.include

%{
	int charCount = 0, wordCount= 0, lineCount = 0;
%}

%eof{
	System.out.println("Lines:\t\t" + lineCount);
	System.out.println("Words:\t\t" + wordCount);
	System.out.println("Characters:\t" + charCount);
%eof}

%%


[^\s]+ {							// A word is considered a non-whitespace sequence.
	wordCount++;
	charCount += yylength();
}

\R {
	lineCount++;
	charCount++;
}

[^] {
	charCount++;
}

