// Detects and deletes traditional C /*...*/ comments from the input.
// C++ end-of-line // comments are ignored.

%%
%include Jflex.include
%%

"/*" (\*+[^/*] | [^*])* \*+\/ {
	// Deleted.
}

[^] {
	ECHO();
}
