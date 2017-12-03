package at.crimsonbit.cprogramtester;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import at.crimsonbit.cprogramtester.compiler.Compiler;
import at.crimsonbit.cprogramtester.programs.Project;
import at.crimsonbit.cprogramtester.utils.Utils;
import exceptions.MakeErrorException;

class TestCompiler {

	@Test
	void test() throws MakeErrorException {
		Project p = new Project("src/test/resources/testprograms/");
		Compiler c = new Compiler();
		int returnCode = c.make(p);
		assertEquals(0, returnCode);
	}

	@Test
	void testRun() throws IOException {
		Project p = new Project("src/test/resources/testprograms/");
		Process testProgram = Runtime.getRuntime().exec(p.getFolder().getAbsolutePath() + "/test.exe");
		BufferedInputStream bin = null;
		try {
			try {
				bin = new BufferedInputStream(testProgram.getInputStream());
				testProgram.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			StringBuilder sb = new StringBuilder();
			Utils.appendAllToStringBuilder(sb, bin);
			System.out.println(sb.toString());
		} finally {
			if (bin != null)
				bin.close();
		}

	}

}
