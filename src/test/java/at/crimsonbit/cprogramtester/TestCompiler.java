package at.crimsonbit.cprogramtester;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import at.crimsonbit.cprogramtester.compiler.Compiler;
import at.crimsonbit.cprogramtester.programs.Project;
import exceptions.MakeErrorException;

class TestCompiler {

	@Test
	void test() throws MakeErrorException {
		Project p = new Project("src/test/resources/testprograms/");
		Compiler c = new Compiler();
		int returnCode = c.make(p);
		assertEquals(0, returnCode);
	}

}
