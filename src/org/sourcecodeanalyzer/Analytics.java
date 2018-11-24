 /*
 * Source Code Analyzer is part of the larger Source Code Analyzer repository on https://github.com/Hayawi/SourceCodeAnalyzer
 * Author: Yahya Ismail
 * This project is under the MIT License so go wild
 * 
 * Analytics contains the methods that perform string analytics on the contents of the files to output data
 */

package org.sourcecodeanalyzer;

public class Analytics {

	//returns total lines in of content
	public static int totalFileLines(String[] contents){
		return contents.length;
	}
	
	//aggregates the results from single comment and block comment to get total number of comments
	public static int totalComments(String[] contents, String[] commentCharacter){
		
		return totalSingleComments(contents, commentCharacter) + totalBlockComments(contents, commentCharacter)[0];
	}
	
	//returns the lowest valid non-negative number, or -1 if none exists
	private static int lowestIndex(int a, int b){
		if (a < 0 && b < 0)
			return -1;
		else if (b < 0)
			return a;
		else if (a < 0)
			return b;
		else if (a <= b)
			return a;
		else if (b < a)
			return b;
		else
			return -1;
	}
	
	//validates if the comment character is actually a valid comment starter or contained in a string
	private static boolean isComment(String toCheck){
		int doubleQuotes = 0;
		int singleQuotes = 0;
		for (int i = 0; i < toCheck.length(); i++){
			if (toCheck.charAt(i) == '\"' && singleQuotes % 2 == 0)
				doubleQuotes++;
			else if (toCheck.charAt(i) == '\'' && doubleQuotes % 2 == 0)
				singleQuotes++;
		}
		if (doubleQuotes % 2 == 0 && singleQuotes % 2 == 0)
			return true;
		return false;
	}
	
	private static int indexOfComment(String toCheck, String[] commentCharacter){
		int singleComment = toCheck.indexOf(commentCharacter[0]);
		int beginBlockComment = -1;
		int endBlockComment = -1;
		if (commentCharacter.length > 1)
		{
			beginBlockComment = toCheck.indexOf(commentCharacter[1]);
			endBlockComment = toCheck.indexOf(commentCharacter[2]);
		}
		int firstComment = lowestIndex(singleComment, beginBlockComment);
		
		while (firstComment > -1 && !isComment(toCheck.substring(0, firstComment))){
			toCheck = toCheck.substring(0, firstComment) + toCheck.substring(firstComment + 1);
			firstComment = indexOfComment(toCheck, commentCharacter);
		}
		
		if (firstComment > -1)
			return firstComment;
		else
			return endBlockComment;
		
	}
	
	//Tallies up all the single comments by just looking through each line and doing some string logic on it
	//There exists one bug, and that is that if the comment is a trailing comment and there is a " or ' captured inside a string, 
	//the comment wont be counted
	// eg. String foo = "hello\"" // sample comment
	//TODO: Fix the trailing string bug
	public static int totalSingleComments(String[] contents, String[] commentCharacter){
		if (contents.length == 0 || commentCharacter.length == 0)
			return 0;
		int commentCount = 0;
		boolean previousComment = false;
		boolean previousIsfirstComment = false;
		boolean isBlock = false;
		
		for (int i  = 0; i < contents.length; i++){
			int activeCommentIndex = indexOfComment(contents[i], commentCharacter);
			System.out.println(activeCommentIndex);
			String stringContainingComment = "";
			if (activeCommentIndex > -1)
				stringContainingComment = contents[i].substring(activeCommentIndex);
			
			if (!isBlock && activeCommentIndex > -1) {
				if (stringContainingComment.contains(commentCharacter[0])) {
					if (!previousComment) {
						commentCount++;
						previousIsfirstComment = true;
					} else if (previousComment && previousIsfirstComment) {
						commentCount--;
						previousIsfirstComment = false;
					}
					previousComment = true;
				} else if (stringContainingComment.contains(commentCharacter[1])) {
					if (stringContainingComment.contains(commentCharacter[2])) {
						if (!previousComment) {
							commentCount++;
							previousIsfirstComment = true;
						} else if (previousComment && previousIsfirstComment) {
							commentCount--;
							previousIsfirstComment = false;
						}
						previousComment = true;
					} else {
						isBlock = true;
						previousComment = true;
					}
				}
			} else if (contents[i].contains(commentCharacter[2]) && isBlock) {
					isBlock = false;
					if (!stringContainingComment.contains(commentCharacter[2]) && stringContainingComment.contains(commentCharacter[1]))
						isBlock = true;
			} else if (!isBlock)
				previousComment = false;
		}
		return commentCount;		
	}
	
	//Tallies up all the block comments by just looking through each line and doing some string logic on it
	//There exists one bug, and that is that if the comment is a trailing comment and there is a " or ' captured inside a string, 
	//the comment wont be counted
	// eg. String foo = "hello\"" // sample comment
	//TODO: Fix the trailing string bug
	//return[0] is comment lines, return[1] is number of blocks
	public static int[] totalBlockComments(String[] contents, String[] commentCharacter){
		int commentCount = 0;
		int blockCount = 0;
		boolean previousComment = false;
		boolean previousIsFirstOfBlock = false;
		boolean blockActive = false;
		boolean blockJustEnded = false;
		if (contents.length == 0 || commentCharacter.length == 0)
			return new int[]{0,0};
		for (int i = 0; i < contents.length; i++){
			int indexOfFirst = contents[i].indexOf(commentCharacter[0]);
			int indexOfSecond = -1;
			int indexOfThird = -1;
			if (commentCharacter.length > 1) {
				indexOfSecond = contents[i].indexOf(commentCharacter[1]);
				indexOfThird = contents[i].indexOf(commentCharacter[2]);
			}
			if (!blockActive){
				if (indexOfFirst > -1 && indexOfSecond <= -1 && isComment(contents[i].substring(0, indexOfFirst)))
				{
					if (!previousComment) {
						previousComment = true;
						previousIsFirstOfBlock = true;
					} else if (previousComment && previousIsFirstOfBlock && contents[i].substring(0, indexOfFirst).trim().isEmpty()){
						commentCount++;
						blockCount++;
						previousIsFirstOfBlock = false;
					} else if (previousComment && contents[i].substring(0, indexOfFirst).trim().isEmpty())
						commentCount++;
				} else if (indexOfFirst > -1 && indexOfSecond > -1){
					if ((indexOfFirst < indexOfSecond && isComment(contents[i].substring(0, indexOfFirst))) || 
						(indexOfFirst > indexOfSecond && !isComment(contents[i].substring(0, indexOfSecond)) && 
						isComment(contents[i].substring(0, indexOfFirst)))){
						if (!previousComment) {
							previousComment = true;
							previousIsFirstOfBlock = true;
						} else if (previousComment && previousIsFirstOfBlock && contents[i].substring(0, indexOfFirst).trim().isEmpty()){
							commentCount++;
							blockCount++;
							previousIsFirstOfBlock = false;
						} else if (previousComment && contents[i].substring(0, indexOfFirst).trim().isEmpty())
							commentCount++;
					} else if (isComment(contents[i].substring(0, indexOfSecond))){
						if (indexOfThird > -1 && indexOfThird > indexOfSecond){
							if (!previousComment) {
								previousComment = true;
								previousIsFirstOfBlock = true;
							} else if (previousComment && previousIsFirstOfBlock && contents[i].substring(0, indexOfSecond).trim().isEmpty()){
								commentCount++;
								blockCount++;
								previousIsFirstOfBlock = false;
							} else if (previousComment && contents[i].substring(0, indexOfSecond).trim().isEmpty())
								commentCount++;
						} else{
							blockActive = true;
							previousComment = true;
							if (!previousIsFirstOfBlock)
								previousIsFirstOfBlock = true;
						}
					}
				} else if (indexOfSecond > -1 && isComment(contents[i].substring(0, indexOfSecond))){
					if (indexOfThird > -1 && indexOfThird > indexOfSecond){
						if (!previousComment) {
							previousComment = true;
							previousIsFirstOfBlock = true;
						} else if (previousComment && previousIsFirstOfBlock && contents[i].substring(0, indexOfSecond).trim().isEmpty()){
							commentCount++;
							blockCount++;
							previousIsFirstOfBlock = false;
						} else if (previousComment && contents[i].substring(0, indexOfSecond).trim().isEmpty())
							commentCount++;
					} else {
						blockActive = true;
						previousComment = true;
						if (!previousIsFirstOfBlock)
							previousIsFirstOfBlock = true;
					}
				} else {
					if (previousComment && !previousIsFirstOfBlock && !blockJustEnded)
						commentCount++;
					blockJustEnded=false;
					previousComment = false;
				}
			} else {
				commentCount++;
				if (previousIsFirstOfBlock) {
					blockCount++;
					commentCount++;
					previousIsFirstOfBlock = false;
				}
				if (indexOfThird > -1){
					blockActive = false;
					blockJustEnded = true;
				}
			}
		}
		return new int[]{commentCount,blockCount};
	}
	
	//Tallies up the total todos in each comment based on whether the todo is encapsulated in a comment of not
	//Currently there is a bug where, if the comment is a trailing comment and there is a " or ' 
	//captured in a string, the comment isn't counted
	// eg. String foo = "hello\"" // sample comment
	//TODO: Fix the trailing string bug
	public static int totalTodos(String[] contents, String[] commentCharacter){
		int todos = 0;
		if (contents.length == 0 || commentCharacter.length == 0)
			return 0;
		boolean activeBlock = false;
		for (int i = 0; i < contents.length; i++)
		{
			int indexOfFirst = contents[i].indexOf(commentCharacter[0]);
			int indexOfSecond = -1;
			int indexOfThird = -1;
			if (commentCharacter.length > 1) {
				indexOfSecond = contents[i].indexOf(commentCharacter[1]);
				indexOfThird = contents[i].indexOf(commentCharacter[2]);
			}
			
			int todoLocation = contents[i].indexOf("TODO");
			int lowIndex = lowestIndex(indexOfFirst, indexOfSecond);
			if (lowIndex == indexOfSecond)
			{
				if (indexOfThird < indexOfSecond)
					activeBlock = true;
			}
			if (todoLocation > -1){
				if (lowIndex > -1){
					if (lowIndex < todoLocation && isComment(contents[i].substring(0, lowIndex)))
						todos++;
				} else if (indexOfThird > todoLocation){
					todos++;
					if (activeBlock)
						activeBlock = false;
				} else if (activeBlock)
					todos++;
			}
		}
		return todos;
	}
	
	

}