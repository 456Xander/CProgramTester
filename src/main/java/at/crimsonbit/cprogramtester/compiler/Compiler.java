package at.crimsonbit.cprogramtester.compiler;

import static at.crimsonbit.cprogramtester.api.ErrorCodes.ERROR_MAKE_NOT_FOUND;
import static at.crimsonbit.cprogramtester.api.ErrorCodes.ERROR_UNKNOWN_OS;

import java.io.BufferedInputStream;
import java.io.IOException;

import at.crimsonbit.cprogramtester.programs.Project;
import at.crimsonbit.cprogramtester.utils.Utils;
import exceptions.MakeErrorException;

public class Compiler {

	// TODO
	public int make(Project project) throws MakeErrorException {
		if (!Utils.isMakeInPath()) {
			switch (Utils.getOperatingSystemType()) {
			case Windows:
				System.err.println("The make command is not in your path, please install mingw or another program,"
						+ " which supplies c compiler tools."
						+ " If you already have mingw or similar installed, check if the bin folder "
						+ "(probably in C:\\MinGW\\bin) is in the PATH environment variable, if you do not know how to"
						+ "set environment variables, google it");
				System.exit(ERROR_MAKE_NOT_FOUND);
			case Linux:
				System.err.println("The program make is not installed, please install it and a c compiler."
						+ "On Ubuntu you need to do\n" + "sudo apt-get update && sudo apt-get install make gcc");
				System.exit(ERROR_MAKE_NOT_FOUND);
			case MacOS:
			case Other:
				System.err.println("The program make is not installed, please install make and a c compiler");
				System.exit(ERROR_MAKE_NOT_FOUND);
			default:
				System.err
						.println("The make program is not installed and your OS could not be detected, please report");
				System.exit(ERROR_UNKNOWN_OS);
			}
		}
		int returnCode = -1;
		StringBuffer error = new StringBuffer(), normal = new StringBuffer();

		try {
			Process makeProcess = Runtime.getRuntime()
					.exec(new String[] { Utils.makeCommand, "-C", project.getFolder().getAbsolutePath() });

			Thread a = new Thread(() -> {
				try (BufferedInputStream errorStream = new BufferedInputStream(makeProcess.getErrorStream())) {
					Utils.appendAllToStringBuffer(error, errorStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});;
			Thread b = new Thread(() -> {
				try (BufferedInputStream outStream = new BufferedInputStream(makeProcess.getInputStream())) {
					Utils.appendAllToStringBuffer(normal, outStream);
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			a.start();
			b.start();
			returnCode = makeProcess.waitFor();
			a.interrupt();
			b.interrupt();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (returnCode != 0) {
			throw new MakeErrorException(
					"Make not successfull: returnCode=" + returnCode + " errorOutput:" + error.toString());
		}

		return 0;
	}
}
