 /*
 * Source Code Analyzer is part of the larger Source Code Analyzer repository on https://github.com/Hayawi/SourceCodeAnalyzer
 * Author: Yahya Ismail
 * This project is under the MIT License so go wild
 */

package org.sourcecodeanalyzer;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.junit.Before;
import org.junit.Test;
import org.sourcecodeanalyzer.FileSystem;
import org.sourcecodeanalyzer.SourceComments;

public class FileSystemTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	// Testing if the file returns an empty string array for an empty java file
	public void testEmptyFileContents() {
		String[] emptyFile = FileSystem.fileContents("testfiles\\empty.java");
		assertNotNull(emptyFile);
		assertEquals(0, emptyFile.length);
	}

	@Test
	// Testing if the file returns 
	public void testNonEmptyFileContents() {
		String[] contents = FileSystem.fileContents("testfiles\\nonempty.java");
		assertNotNull(contents);
		assertEquals (5, contents.length);
		assertTrue( contents[0].equals("This") &&
				contents[1].equals("Is") &&
				contents[2].equals("A") &&
				contents[3].equals("Java") &&
				contents[4].equals("File"));
	}
	
	@Test
	// Testing if a non source file returns "Invalid Source File"
	public void testNonSourceChooseFile() {
		robotFileChooser("testfiles\\nonsource.txt");
		String fileURL = FileSystem.chooseFile();
		assertNotNull(fileURL);
		assertTrue(fileURL.equals("Invalid Source File"));
	}
	
	@Test
	// Testing if a non source file returns "Invalid Source File"
	public void testInvalidChooseFile() {
		robotFileChooser("testfiles\\.invalid.txt");		
		String fileURL = FileSystem.chooseFile();
		assertNotNull(fileURL);
		assertTrue(fileURL.equals("Invalid Source File"));
	}
	
	@Test
	// Testing if a source file returns its file destination
	public void testSourceChooseFile() {
		robotFileChooser("testfiles\\nonempty.java");		
		String fileURL = FileSystem.chooseFile();
		assertNotNull(fileURL);
		assertTrue(fileURL.equals(System.getProperty("user.dir") + "\\testfiles\\nonempty.java"));
	}
	
	private void robotFileChooser(String fileLocation){
		new Thread(new Runnable() {
		    @Override
		    public void run() {
		        Robot robot = null;
				try {
					robot = new Robot();
				} catch (AWTException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String file = fileLocation.toLowerCase();
		        robot.delay(1000);
		        for (int i = 0; i < file.length(); i++){
			        robot.delay(100);
			        robot.keyPress(KeyEvent.getExtendedKeyCodeForChar(file.charAt(i)));
			        robot.delay(100);
			        robot.keyRelease(KeyEvent.getExtendedKeyCodeForChar(file.charAt(i)));
		        }
		        robot.keyPress(KeyEvent.VK_ENTER);
		    }
		}).start();
	}
	
	@Test
	// Testing the enum returned using the file extension
	public void testJavaGetSourceCommentType(){
		SourceComments fileType = FileSystem.getSourceCommentType("testfiles\\example.java");
		assertNotNull(fileType);
		assertTrue(fileType == SourceComments.Java);
	}
	
	@Test
	// Testing the enum returned using the file extension
	public void testCGetSourceCommentType(){
		SourceComments fileType = FileSystem.getSourceCommentType("example.c");
		assertNotNull(fileType);
		assertTrue(fileType == SourceComments.C);
	}
	
	@Test
	// Testing the enum returned using the file extension
	public void testInvalidGetSourceCommentType(){
		SourceComments fileType = FileSystem.getSourceCommentType("testfiles\\example.garble");
		assertNull(fileType);
	}
	
	@Test
	// Testing the enum returned using the file extension
	public void testEmptyGetSourceCommentType(){
		SourceComments fileType = FileSystem.getSourceCommentType("");
		assertNull(fileType);
	}
}
