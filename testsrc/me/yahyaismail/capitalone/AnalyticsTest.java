package me.yahyaismail.capitalone;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class AnalyticsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	//tests if the total lines returned for an empty file is 0
	public void testEmptyTotalFileLines() {
		int fileLines = Analytics.totalFileLines(new String[]{});
		assertEquals(fileLines, 0);
	}
	
	@Test
	//tests if the total lines returned is correct
	public void testNonEmptyTotalFileLines() {
		int fileLines = Analytics.totalFileLines(new String[]{"hello", "is", "it", "me", "you're", "looking", "for"});
		assertEquals(fileLines, 7);
	}
	
	
	
	@Test
	public void testEmptyTotalComments() {
		int commentLines = Analytics.totalComments(new String[]{}, new String[]{"//"});
		assertEquals(commentLines, 0);
	}
	
	@Test
	public void testSingleTotalComments() {
		int commentLines = Analytics.totalComments(new String[]{"//hello", "is", "it", "me", "you're", "looking", "//for"}, new String[]{"//", "/*", "*/"});
		assertEquals(commentLines, 2);
	}
	
	@Test
	public void testEmptyCommentArrayTotalComments() {
		int commentLines = Analytics.totalComments(new String[]{"//hello", "is", "it", "me", "you're", "looking", "//for"}, new String[]{});
		assertEquals(commentLines, 0);
	}
	
	@Test
	public void testSingleAndBlockTotalComments() {
		int commentLines = Analytics.totalComments(new String[]{"//hello", "is", "/*it", "me", "you're*/", "looking", "//for"}, new String[]{"//", "/*", "*/"});
		assertEquals(commentLines, 5);
	}
	
	@Test
	public void testMalformedTotalComments() {
		int commentLines = Analytics.totalComments(new String[]{"/ /hello", "is", "it", "me", "you're", "looking", "/ /for"}, new String[]{"//", "/*", "*/"});
		assertEquals(commentLines, 0);
	}
	
	
	
	@Test
	public void testEmptyTotalSingleComments() {
		int commentLines = Analytics.totalComments(new String[]{}, new String[]{"//", "/*", "*/"});
		assertEquals(commentLines, 0);
	}
	
	@Test
	public void testEmptyArrayTotalSingleComments() {
		int commentLines = Analytics.totalComments(new String[]{"//hello", "is", "it", "me", "you're", "looking", "//for"}, new String[]{});
		assertEquals(commentLines, 0);
	}
	
	@Test
	public void testTotalSingleComments() {
		int commentLines = Analytics.totalComments(new String[]{"//hello", "is", "/*it", "me", "you're*/", "looking", "//for"}, new String[]{"//", "/*", "*/"});
		assertEquals(commentLines, 2);
	}
	
	@Test
	public void testMultiLineTotalSingleComments() {
		int commentLines = Analytics.totalComments(new String[]{"//hello", "is", "/*it*/", "me", "/*you're*/", "looking", "//for"}, new String[]{"//", "/*", "*/"});
		assertEquals(commentLines, 4);
	}
	
	@Test
	public void testBlockTotalSingleComments() {
		int commentLines = Analytics.totalComments(new String[]{"//hello", "//is", "/*it*/", "me", "/*you're*/", "looking", "//for"}, new String[]{"//", "/*", "*/"});
		assertEquals(commentLines, 2);
	}

	
	
	@Test
	public void testNoTotalTodos() {
		int commentLines = Analytics.totalComments(new String[]{"//hello", "//is", "/*it*/", "me", "/*you're*/", "looking", "//for"}, new String[]{"//", "/*", "*/"});
		assertEquals(commentLines, 0);
	}
	
	@Test
	public void testOutOfCommentTotalTodos() {
		int commentLines = Analytics.totalComments(new String[]{"TODO", "//is", "/*it*/", "me", "/*you're*/", "looking", "//for"}, new String[]{"//", "/*", "*/"});
		assertEquals(commentLines, 0);
	}
	
	@Test
	public void testEmptyCommentArrayTotalTodos() {
		int commentLines = Analytics.totalComments(new String[]{"//TODO", "//is", "/*it*/", "me", "/*you're*/", "looking", "//for"}, new String[]{});
		assertEquals(commentLines, 0);
	}

	@Test
	public void testTotalTodos() {
		int commentLines = Analytics.totalComments(new String[]{"TODO", "//is", "/*TODO*/", "me", "/*you're", "TODO", "for*/"}, new String[]{"//", "/*", "*/"});
		assertEquals(commentLines, 2);
	}
}
