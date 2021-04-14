package spike;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Scanner;

public class FileSys {
	private static ArrayList<String> cache = new ArrayList<String>();
	private static File _file = null;
	private static ArrayList<String> result = new ArrayList<String>();
	public static final String SPLITCHAR = "\t";
	
	public static void mutateFile(File file) throws IOException {
		_file = file;
		
		read();
		interpret();
		write();
	}
	
	private static void read() throws FileNotFoundException {
		Scanner cin = new Scanner(_file);
		
		while(cin.hasNextLine()) {
			cache.add(cin.nextLine());
		}
		
		cin.close();
	}
	
	private static void interpret() {
		ArrayList<String> names = new ArrayList<String>();
		ArrayList<Long> ids = new ArrayList<Long>();
		ArrayList<Long> values = new ArrayList<Long>();
		
		for(Student t : Student.students) {
			names.add(t.name);
			ids.add(t.id);
			values.add(t.wallet);
		}
		
		for(String t : cache) {
			//this, in theory, should never happen. more of a fail safe
			if(!ids.contains(Long.parseLong(t.split(SPLITCHAR)[1]))) {
				result.add(t);
			}
		}
		
		for(int i = 0; i < names.size(); i++) {
			result.add(names.get(i) + SPLITCHAR + ids.get(i) + SPLITCHAR + values.get(i) + "\n");
		}
	}
	
	private static void write() throws IOException {
		wipe();
		
		FileWriter fw = new FileWriter(_file, false);
		fw.flush();
		
		String printMsg = "";
		for(String t : result) {
			printMsg += t;
		}
		fw.write(printMsg);
		fw.close();
		
		cache = new ArrayList<String>();
		result = new ArrayList<String>();
	}
	
	private static void wipe() throws IOException {
		//overwrites the file with cleaned data
		FileWriter fw = new FileWriter(_file, false);
		PrintWriter pw = new PrintWriter(fw, false);
		pw.flush();	
		pw.close();
		fw.close();
		
		OpenOption asdf = StandardOpenOption.TRUNCATE_EXISTING;
		Files.write(_file.toPath(), new byte[0], asdf);
	}
}
