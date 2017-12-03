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
		BufferedInputStream errorStream = null;
		try {
			try {
				Process makeProcess = Runtime.getRuntime().exec(Utils.makeCommand + " -C " + project.getFolder());
				errorStream = new BufferedInputStream(makeProcess.getErrorStream());
				returnCode = makeProcess.waitFor();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (returnCode != 0) {
				StringBuilder sb = new StringBuilder();
				byte[] buff = new byte[512];
				try {
					Utils.appendAllToStringBuilder(sb, errorStream);
				} catch (IOException e) {
					throw new MakeErrorException("Make not successfull: returnCode=" + returnCode);
				}
				throw new MakeErrorException(
						"Make not successfull: returnCode=" + returnCode + " errorOutput:" + sb.toString());
			}
		} finally {
			if (errorStream != null) {
				try {
					errorStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return 0;
	}
}
