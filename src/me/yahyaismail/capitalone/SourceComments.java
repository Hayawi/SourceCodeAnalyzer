package me.yahyaismail.capitalone;

public enum SourceComments {
	//all the supported languages thus far
	//if a new language is to be added, it can be added by doing the following:
	// Language(file extension, singular comment, block comment[])
	// If a block comment is the same for starting and ending, keep it as a 2 element array
	// so if the starting and ending symbols are #, keep the array as {"#","#"} even if it looks silly
	Java(".java", "//", new String[]{"/*", "*/"}),
	C(".c", "//", new String[]{"/*", "*/"}),
	Python(".py", "#", new String[]{}),
	Cpp(".cpp", "//", new String[]{"/*", "*/"}),
	JavaScript(".js", "//", new String[]{"/*", "*/"}),
	Cs(".cs", "//", new String[]{"/*", "*/"});
	
	final String extension;
	final String singleComment;
	final String[] blockComment;
	
	SourceComments(String extension, String singleComment, String[] blockComment){
		this.extension = extension;
		this.singleComment = singleComment;
		this.blockComment = blockComment;
	}
	
	//returns the extension
	public String getExtension() {
		return extension;
	}

	//returns the character for a single comment
	public String getSingleComment() {
		return singleComment;
	}

	//returns the block comment array
	public String[] getBlockComment() {
		return blockComment;
	}
}
