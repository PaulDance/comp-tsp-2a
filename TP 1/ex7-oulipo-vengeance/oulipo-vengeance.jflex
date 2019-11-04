// JFlex augmented version of unix program `wc`, also counting
// words starting with the 'v' character.

%%
%include Jflex.include
%caseless

%{
	int charCount = 0, wordCount= 0, lineCount = 0,
		punctCount = 0, vWordCount = 0;
%}

%eof{
	System.out.println("Lines:\t\t\t" + lineCount);
	System.out.println("V Words:\t\t" + vWordCount);
	System.out.println("Words:\t\t\t" + wordCount);
	System.out.println("Punctuation Characters:\t" + punctCount);
	System.out.println("Characters:\t\t" + charCount);
%eof}

PunctChar = [,:;.?!()[]{}\']
Word = [^\s\R,:;.?!()[]{}\']+
Vword = v{Word}?

%%


{Vword} {
	vWordCount++;
	wordCount++;
	charCount += yylength();
}

{Word} {
	wordCount++;
	charCount += yylength();
}

{PunctChar} {
	punctCount++;
	charCount++;
}

\R {
	lineCount++;
	charCount++;
}

[^] {
	charCount++;
}

