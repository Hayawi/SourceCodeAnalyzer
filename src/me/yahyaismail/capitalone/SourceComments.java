package me.yahyaismail.capitalone;

public enum SourceComments {
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
	
	public String getExtension() {
		return extension;
	}

	public String getSingleComment() {
		return singleComment;
	}

	public String[] getBlockComment() {
		return blockComment;
	}
}
