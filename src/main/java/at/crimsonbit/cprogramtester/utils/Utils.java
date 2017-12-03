package at.crimsonbit.cprogramtester.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

public class Utils {
	public enum OSType {
		Windows,
		MacOS,
		Linux,
		Other
	};

	// cached result of OS detection
	protected static OSType detectedOS;
	protected static String makeCommand;

	/**
	 * detect the operating system from the os.name System property and cache the
	 * result
	 * 
	 * @returns - the operating system detected
	 */
	public static OSType getOperatingSystemType() {
		if (detectedOS == null) {
			String OS = System.getProperty("os.name", "generic").toLowerCase(Locale.ROOT);
			if ((OS.indexOf("mac") >= 0) || (OS.indexOf("darwin") >= 0)) {
				detectedOS = OSType.MacOS;
			} else if (OS.indexOf("win") >= 0) {
				detectedOS = OSType.Windows;
			} else if (OS.indexOf("nux") >= 0) {
				detectedOS = OSType.Linux;
			} else {
				detectedOS = OSType.Other;
			}
		}
		return detectedOS;
	}

	public static boolean isMakeInPath() {
		try {
			Process p = Runtime.getRuntime().exec("make -v");
			int returnCode = p.waitFor();
			if (returnCode != 0) {
				p = Runtime.getRuntime().exec("mingw32-make -v");
				returnCode = p.waitFor();
				if (returnCode == 0) {
					makeCommand = "mingw32-make";
					return true;
				}
			} else {
				makeCommand = "make";
				return true;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static void appendAllToStringBuilder(StringBuilder sb, InputStream stream) throws IOException {
		byte[] buff = new byte[512];
		while (stream.read(buff) > 0) {
			sb.append(new String(buff));
		}
	}
}
