package vitHack;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Scanner;

public class logToText {
	public String convertLogToText() throws FileNotFoundException, IOException {
		Date date = new Date();
		long time = date.getTime();
		String fileName = "out" + time + ".txt";
		File fout = new File(fileName);
		FileOutputStream fos = new FileOutputStream(fout);
		File file = new File("System.log");
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		Scanner sc = new Scanner(file);
		sc.useDelimiter(",|\r\n");
		while (sc.hasNext()) {
			bw.write(sc.next());
		}
		// closing the scanner stream
		sc.close();
		bw.close();

		return fileName;

	}
}