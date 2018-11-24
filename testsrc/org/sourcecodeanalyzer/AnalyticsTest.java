 /*
 * Source Code Analyzer is part of the larger Source Code Analyzer repository on https://github.com/Hayawi/SourceCodeAnalyzer
 * Author: Yahya Ismail
 * This project is under the MIT License so go wild
 */

package org.sourcecodeanalyzer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.sourcecodeanalyzer.Analytics;

public class AnalyticsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	//tests if the total lines returned for an empty file is 0
	public void testEmptyTotalFileLines() {
		int fileLines = Analytics.totalFileLines(new String[]{});
		assertEquals(0, fileLines);
	}
	
	@Test
	//tests if the total lines returned is correct
	public void testNonEmptyTotalFileLines() {
		int fileLines = Analytics.totalFileLines(new String[]{"hello", "is", "it", "me", "you're", "looking", "for"});
		assertEquals(7, fileLines);
	}
	
	
	
	@Test
	//tests totalComments if empty content is passed
	public void testEmptyTotalComments() {
		int commentLines = Analytics.totalComments(new String[]{}, new String[]{"//"});
		assertEquals(0, commentLines);
	}
	
	@Test
	//tests totalComments if there are only single comment characters used
	public void testSingleTotalComments() {
		int commentLines = Analytics.totalComments(new String[]{"//hello", "is", "it", "me", "you're", "looking", "//for"}, new String[]{"//", "/*", "*/"});
		assertEquals(2, commentLines);
	}
	
	@Test
	public void testEmptyCommentArrayTotalComments() {
		int commentLines = Analytics.totalComments(new String[]{"//hello", "is", "it", "me", "you're", "looking", "//for"}, new String[]{});
		assertEquals(0, commentLines);
	}
	
	@Test
	public void testSingleAndBlockTotalComments() {
		int commentLines = Analytics.totalComments(new String[]{"//hello", "is", "/*it", "me", "you're*/", "looking", "//for"}, new String[]{"//", "/*", "*/"});
		assertEquals(5, commentLines);
	}
	
	@Test
	public void testMalformedTotalComments() {
		int commentLines = Analytics.totalComments(new String[]{"/ /hello", "is", "it", "//me", "you're", "looking", "/ /for"}, new String[]{"//", "/*", "*/"});
		assertEquals(1, commentLines);
	}
	
	@Test
	public void testQuotedTotalComments(){
		int commentLines = Analytics.totalComments(new String[]{"/ /hello", "is", "\"//\"it", "//me", "you're", "looking", "/ /for"}, new String[]{"//", "/*", "*/"});
		assertEquals(1, commentLines);
	}
	
	@Test
	public void testCommentedBlockTotalComments(){
		int commentLines = Analytics.totalComments(new String[]{"/ /hello", "is", "// /*it", "me", "/* you're */", "looking", "/ /for"}, new String[]{"//", "/*", "*/"});
		assertEquals(2, commentLines);
	}
	
	@Test
	public void testTrailingTotalComments(){
		int commentLines = Analytics.totalComments(new String[]{"hello", "is//hi", "//it", "me", "/* you're */", "looking", "//for"}, new String[]{"//", "/*", "*/"});
		assertEquals(4, commentLines);
	}
	
	
	@Test
	public void testEmptyTotalSingleComments() {
		int commentLines = Analytics.totalSingleComments(new String[]{}, new String[]{"//", "/*", "*/"});
		assertEquals(0, commentLines);
	}
	
	@Test
	public void testEmptyArrayTotalSingleComments() {
		int commentLines = Analytics.totalSingleComments(new String[]{"//hello", "is", "it", "me", "you're", "looking", "//for"}, new String[]{});
		assertEquals(0, commentLines);
	}
	
	@Test
	public void testTotalSingleComments() {
		int commentLines = Analytics.totalSingleComments(new String[]{"//hello", "is", "/*it", "me", "you're*/", "looking", "//for"}, new String[]{"//", "/*", "*/"});
		assertEquals(2, commentLines);
	}
	
	@Test
	public void testMultiLineTotalSingleComments() {
		int commentLines = Analytics.totalSingleComments(new String[]{"//hello", "is", "/*it*/", "me", "/*you're*/", "looking", "//for"}, new String[]{"//", "/*", "*/"});
		assertEquals(4, commentLines);
	}
	
	@Test
	public void testBlockTotalSingleComments() {
		int commentLines = Analytics.totalSingleComments(new String[]{"//hello", "//is", "/*it*/", "me", "/*you're*/", "looking", "//for"}, new String[]{"//", "/*", "*/"});
		assertEquals(2, commentLines);
	}
	
	@Test
	public void testTrailingTotalSingleComments(){
		int commentLines = Analytics.totalSingleComments(new String[]{"hello", "/*yjjyjy", "it*/", "me//g", "j", "looking", "//for"}, new String[]{"//", "/*", "*/"});
		assertEquals(2, commentLines);
	}

	
	@Test
	public void testEmptyTotalBlockComments(){
		int[] commentLines = Analytics.totalBlockComments(new String[]{}, new String[]{"//", "/*", "*/"});
		assertEquals(0, commentLines[0]);
		assertEquals(0, commentLines[1]);
	}
	
	@Test
	public void testEmptyArrayTotalBlockComments(){
		int[] commentLines = Analytics.totalBlockComments(new String[]{"//hello", "//is", "/*it*/", "me", "/*you're*/", "looking", "//for"}, new String[]{});
		assertEquals(0, commentLines[0]);
		assertEquals(0, commentLines[1]);
	}
	
	@Test
	public void testTotalBlockComments(){
		int[] commentLines = Analytics.totalBlockComments(new String[]{"//hello", "//is", "/*it*/", "me", "/*you're*/", "looking", "//for"}, new String[]{"//", "/*", "*/"});
		assertEquals(3, commentLines[0]);
		assertEquals(1, commentLines[1]);
	}
	
	@Test
	public void testMultiLineTotalBlockComments(){
		int[] commentLines = Analytics.totalBlockComments(new String[]{"//hello", "is", "/*it", "me", "you're*/", "looking", "//for"}, new String[]{"//", "/*", "*/"});
		assertEquals(3, commentLines[0]);
		assertEquals(1, commentLines[1]);
	}
	
	@Test
	public void testTrailingTotalBlockComments(){
		int[] commentLines = Analytics.totalBlockComments(new String[]{"hello", "/*yjjyjy", "it*/", "me//g", "j", "looking", "//for"}, new String[]{"//", "/*", "*/"});
		assertEquals(2, commentLines[0]);
		assertEquals(1, commentLines[1]);
	}
	
	
	@Test
	public void testNoTotalTodos() {
		int commentLines = Analytics.totalTodos(new String[]{"//hello", "//is", "/*it*/", "me", "/*you're*/", "looking", "//for"}, new String[]{"//", "/*", "*/"});
		assertEquals(0, commentLines);
	}
	
	@Test
	public void testOutOfCommentTotalTodos() {
		int commentLines = Analytics.totalTodos(new String[]{"TODO", "//is", "/*it*/", "me", "/*you're*/", "looking", "//for"}, new String[]{"//", "/*", "*/"});
		assertEquals(0, commentLines);
	}
	
	@Test
	public void testEmptyCommentArrayTotalTodos() {
		int commentLines = Analytics.totalTodos(new String[]{"//TODO", "//is", "/*it*/", "me", "/*you're*/", "looking", "//for"}, new String[]{});
		assertEquals(0, commentLines);
	}

	@Test
	public void testTotalTodos() {
		int commentLines = Analytics.totalTodos(new String[]{"TODO", "//is", "/*TODO*/", "me", "/*you're", "TODO", "for*/"}, new String[]{"//", "/*", "*/"});
		assertEquals(2, commentLines);
	}
}
