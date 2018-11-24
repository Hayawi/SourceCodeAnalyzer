 /*
 * Source Code Analyzer is part of the larger Source Code Analyzer repository on https://github.com/Hayawi/SourceCodeAnalyzer
 * Author: Yahya Ismail
 * This project is under the MIT License so go wild
 * 
 * CodeAnalyzer is the top level main class that aggregates all the helper classes and methods and outputs the results to console
 * and to file
 */

package org.sourcecodeanalyzer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class CodeAnalyzer {
	
	//main method that invokes all methods in other classes to aggregate data
	public static void main(String[] args){
		String fileURL = FileSystem.chooseFile();
		String[] contents = FileSystem.fileContents(fileURL);
		SourceComments sourceType = FileSystem.getSourceCommentType(fileURL);
		String[] commentCharacters = new String[1 + sourceType.blockComment.length];
		commentCharacters[0] = sourceType.singleComment;
		if (commentCharacters.length > 1)
		{
			commentCharacters[1] = sourceType.blockComment[0];
			commentCharacters[2] = sourceType.blockComment[1];
		}
		
		int numLines = Analytics.totalFileLines(contents);
		int totalComments = Analytics.totalComments(contents, commentCharacters);
		int singleComments = Analytics.totalSingleComments(contents, commentCharacters);
		int blockComments = Analytics.totalBlockComments(contents, commentCharacters)[0];
		int numBlocks = Analytics.totalBlockComments(contents, commentCharacters)[1];
		int todos = Analytics.totalTodos(contents, commentCharacters);
		output(fileURL, numLines, totalComments, singleComments, blockComments, numBlocks, todos);
	}
	
	//outputs the result to console and to a file
	private static void output(String fileURL, int numLines, int totalComments, int singleComments, int blockComments, int numBlocks, int todos){
		Charset charset = Charset.forName("utf-8");
		int fileNameIndex = fileURL.lastIndexOf("\\");
		fileURL = fileURL.substring(0, fileNameIndex) + fileURL.substring(fileNameIndex, fileURL.lastIndexOf(".")) + " Results.txt";
		File file = new File(fileURL);
		try {
			BufferedWriter writer = Files.newBufferedWriter(file.toPath(), charset);
			writer.write(fileURL.substring(fileNameIndex + 1, fileURL.lastIndexOf(".")) + "\n");
			writer.write("Total # of lines: " + numLines + "\n");
			writer.write("Total # of comment lines: " + totalComments + "\n");
			writer.write("Total # of single line comments: " + singleComments + "\n");
			writer.write("Total # of comment lines within block comments: " + blockComments + "\n");
			writer.write("Total # of block lines comments: " + numBlocks + "\n");
			writer.write("Total # of TODO's: " + todos + "\n");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.out.println("Could not access " + file.getName() + " or do not have sufficient write access");
			e.printStackTrace();
		}
		System.out.println(fileURL.substring(fileNameIndex + 1, fileURL.lastIndexOf(".")));
		System.out.println("Total # of lines: " + numLines);
		System.out.println("Total # of comment lines: " + totalComments);
		System.out.println("Total # of single line comments: " + singleComments);
		System.out.println("Total # of comment lines within block comments: " + blockComments);
		System.out.println("Total # of block lines comments: " + numBlocks);
		System.out.println("Total # of TODO's: " + todos);
	}
	
}
