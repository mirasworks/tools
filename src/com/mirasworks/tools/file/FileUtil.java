package com.mirasworks.tools.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileUtil {

	public String readFile(String filename) {
		BufferedReader reader = null;
		StringBuilder strb = new StringBuilder();
		try {
			reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {

				strb.append(line);
				// line;
			}
			reader.close();

		} catch (Exception e) {
			System.err.format("Exception occurred trying to read '%s'.", filename);
			e.printStackTrace();
			return null;
		} finally {
			if (reader != null) {

				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return strb.toString();
	}

	/*
	 * @param n character count
	 * 
	 * @param path of the file
	 * 
	 * @param charsetIsoCode
	 * 
	 * @return
	 */
	public String head(int n, String path, String charsetIsoCode) {
		if (n == 0) {
			return "";
		}

		File file = new File(path);

		FileInputStream fileInputStream = null;
		FileChannel fileChannel = null;

		// dirty way of finding a string in the header of an huge file
		try {
			fileInputStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			System.err.println("file not found in path " + path);

			return "";
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
				}
			}

		}

		final int allocationSize = 4096;
		ByteBuffer buf = ByteBuffer.allocateDirect(allocationSize);
		StringBuffer strBuff = new StringBuffer(allocationSize);

		fileChannel = fileInputStream.getChannel();

		if (charsetIsoCode == null) {
			charsetIsoCode = "UTF8";
		}
		Charset cs = Charset.forName(charsetIsoCode);

		// known issue : it read one block more than expected
		try {
			long count = 0;
			CharBuffer charBuffer = null;
			while (count >= 0 && n > 0) {
				count = fileChannel.read(buf);
				buf.clear();
				buf.rewind();
				charBuffer = cs.decode(buf);
				int lenght = charBuffer.length();
				for (int i = 0; i < lenght && n > 0; i++) {
					char ch = charBuffer.get();
					strBuff.append(ch);
					n--;
				}
				buf.clear();
			}

		} catch (IOException e) {
			System.err.println("fileChannel can't be read");
		} finally {
			
			if (fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (IOException e) {
					System.err.println("Error while closing file input stream");
				}
			}
			if (fileChannel != null) {
				try {
					fileChannel.close();
				} catch (IOException e) {
					System.err.println("Error while closing file channel");
				}
			}

		}

		return strBuff.toString();
	}

	/**
	 * create the directory if not exists
	 * 
	 * @param folder
	 * @return boolean true if ok
	 * @throws SecurityException
	 */
	public boolean mkdir(String folder) throws SecurityException {
		File f = new File(folder);

		if (!f.exists()) {
			return f.mkdir();
		} else {
			return false;
		}
	}

	public boolean folderExists(String folder) {
		if (folder == null || folder.isEmpty()) {
			return false;
		}
		File f = new File(folder);
		return f.exists();
	}
	
	void write(List<String> aLines, String filePath, Charset encoding ) {
		Path path = Paths.get(filePath);
		try (BufferedWriter writer = Files.newBufferedWriter(path, encoding)) {
			for (String line : aLines) {
				writer.write(line);
				writer.newLine();
			}
		} catch (IOException e) {
			System.err.println("Error while reading file : \r\n" + e);
		}
	}
}
