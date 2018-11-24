 /*
 * Source Code Analyzer is part of the larger Source Code Analyzer repository on https://github.com/Hayawi/SourceCodeAnalyzer
 * Author: Yahya Ismail
 * This project is under the MIT License so go wild
 */

package org.sourcecodeanalyzer;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;

import org.junit.Test;
import org.sourcecodeanalyzer.CodeAnalyzer;

public class CodeAnalyzerTest {

	@Test
	public void testJavaFile() {
		robotFileChooser("testfiles\\Application.java");
		CodeAnalyzer.main(null);
		File file = new File("testfiles\\application Results.txt");
		assert(file.exists());
	}
	
	@Test
	public void testJSFile() {
		robotFileChooser("testfiles\\Student.js");
		CodeAnalyzer.main(null);
		File file = new File("testfiles\\student Results.txt");
		assert(file.exists());
	}
	
	@Test
	public void testPythonFile() {
		robotFileChooser("testfiles\\time.py");
		CodeAnalyzer.main(null);
		File file = new File("testfiles\\time Results.txt");
		assert(file.exists());
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
	
}
