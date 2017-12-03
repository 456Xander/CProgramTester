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
	public static String makeCommand;
	static {
		isMakeInPath();
	}

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
		Process p = null;
		try {
			p = Runtime.getRuntime().exec("make -v");
			makeCommand = "make";
		} catch (IOException e) {
			try {
				if (getOperatingSystemType() == OSType.Windows) {
					p = Runtime.getRuntime().exec(new String[] { "mingw32-make", "-v" });
					makeCommand = "mingw32-make";
					return true;
				}
			} catch (IOException e1) {
				try {
					p = Runtime.getRuntime().exec(new String[] { "C:/Program Files (x86)/CodeBlocks/MinGW/bin/mingw32-make", "-v" });
				} catch (IOException e2) {
					return false;
				}
				makeCommand = "C:/Program Files (x86)/CodeBlocks/MinGW/bin/mingw32-make";
				return true;
			}
			e.printStackTrace();
		}
		try {
			int returnCode = p.waitFor();
			return returnCode == 0;
		} catch (InterruptedException e) {
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
