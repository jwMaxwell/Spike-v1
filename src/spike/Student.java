package spike;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Student implements Comparable<Student> {
	public static ArrayList<Student> students = new ArrayList<Student>();
	public String name;
	public long id;
	public long wallet;
		
	public Student(String name, long id, long wallet) {
		this.name = name;
		this.id = id;
		this.wallet = wallet;
	}
	
	public static Student get(long id) {
		for(Student t : students)
			if(t.id == id)
				return t;
		
		return null;
	}
	
	public static boolean add(Student s) {
		for(Student t : students)
			if(t.equals(s))
				return false;
		
		students.add(s);
		return true;
	}
	
	public static String[] getAllNames() {
		ArrayList<String> temp = new ArrayList<String>();
		for(Student t : students)
			temp.add(t.name);
		
		String[] result = Arrays.stream(temp.toArray()).toArray(String[]::new);
		return result;
	}
	
	public static long[] getAllIds() {
		ArrayList<Long> result = new ArrayList<Long>();
		for(Student t : students)
			result.add(t.id);
		return result.stream().mapToLong(l -> l).toArray();
	}
	
	public static long[] getAllWallets() {
		ArrayList<Long> result = new ArrayList<Long>();
		for(Student t : students)
			result.add(t.wallet);
		return result.stream().mapToLong(l -> l).toArray();
	}
	
	public static Student[] getSorted() {
		List<Student> temp = students;
		Collections.sort(temp);
		return Arrays.stream(temp.toArray()).toArray(Student[]::new);
	}

	public boolean equals(Student s) {
		if(this.id == s.id)
			return true;
		return false;
	}
	
	@Override
	public int compareTo(Student o) {
		return Long.compare(this.wallet, o.wallet);
	}
}
