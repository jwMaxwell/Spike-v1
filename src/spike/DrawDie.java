package spike;
public class DrawDie {
	static String d10 =  "  ____    \n" + 
									  	" /\\ . \\ \n" + 
										  "/: \\___\\\n" + 
										  "\\' /'.'/ \n" + 
										  " \\/'_'/  \n" + 
										  "          \n";
	
	static String d20 =  "  ____    \n" + 
						  	" /\\' .\\ \n" + 
							  "/: \\___\\\n" + 
							  "\\' / . / \n" + 
							  " \\/___/  \n" + 
							  "          \n";
	
	static String d30 =  "  ____    \n" + 
						  	" /\\'. \\ \n" + 
							  "/.:\\_'_\\\n" + 
							  "\\.'/ . / \n" + 
							  " \\/___/  \n" + 
							  "          \n";
	
	static String d40 =  "  ____    \n" + 
						  	" /\\' '\\ \n" + 
							  "/ .\\'_'\\\n" + 
							  "\\  /'.'/ \n" + 
							  " \\/'_'/  \n" + 
							  "          \n";
	
	static String d50 =  "  ____    \n" + 
						  	" /\\'.'\\ \n" + 
							  "/: \\'_'\\\n" + 
							  "\\' /: :/ \n" + 
							  " \\/'_'/  \n" + 
							  "          \n";
	
	static String d60 =  "  ____    \n" + 
						  	" /\\: :\\ \n" + 
							  "/: \\'_'\\\n" + 
							  "\\' /.  / \n" + 
							  " \\/__'/  \n" + 
							  "          \n";
	
	static String d01 = "           \r\n"
						 + "   _____   \r\n"
						 + "  / .  /\\ \r\n"
						 + "/____/..\\ \r\n"
						 + "\\'  '\\  /\r\n"
						 + "\\'__'\\/  \r\n";
	
	static String d02 = "           \r\n"
						 + "   _____   \r\n"
						 + "  /  . /\\ \r\n"
						 + "/_'__/..\\ \r\n"
						 + "\\'  '\\::/\r\n"
						 + "\\'__'\\/  \r\n";
	
	static String d03 = "           \r\n"
						 + "   _____   \r\n"
						 + "  / .' /\\ \r\n"
						 + "/_'__/  \\ \r\n"
						 + "\\   '\\' /\r\n"
						 + "\\'___\\/  \r\n";
	
	static String d04 = "           \r\n"
						 + "   _____   \r\n"
						 + "  /'  '/\\ \r\n"
						 + "/_'_'/..\\ \r\n"
						 + "\\   '\\  /\r\n"
						 + "\\'___\\/  \r\n";
	
	static String d05 = "           \r\n"
						 + "   _____   \r\n"
						 + "  /'. '/\\ \r\n"
						 + "/_'_'/ .\\ \r\n"
						 + "\\'  '\\  /\r\n"
						 + "\\'__'\\/  \r\n";
	
	static String d06 = "           \r\n"
						 + "   _____   \r\n"
						 + "  /:  :/\\ \r\n"
						 + "/_'_'/.'\\ \r\n"
						 + "\\'  '\\.:/\r\n"
						 + "\\'__'\\/  \r\n";
	
	
	//		 4					4			 		 2					6					 4					4
	// 6 2 1 5		5 6 2 1    4 6 3 1		3 2 4 5    2 1 5 6		1 5 6 2
	//		 3					3					 5					1					 3					3
	
	
	public static String draw(int dice1, int dice2) {
		String t1 = "";
		String t2 = "";
		
		switch(dice1) {
			case 1:
				t1 = d10;
				break;
			case 2:
				t1 = d20;
				break;
			case 3:
				t1 = d30;
				break;
			case 4:
				t1 = d40;
				break;
			case 5:
				t1 = d50;
				break;
			case 6:
				t1 = d60;
				break;
		}
		
		switch(dice2) {
			case 1:
				t2 = d01;
				break;
			case 2:
				t2 = d02;
				break;
			case 3:
				t2 = d03;
				break;
			case 4:
				t2 = d04;
				break;
			case 5:
				t2 = d05;
				break;
			case 6:
				t2 = d06;
				break;
		}
		
		return stitch(t1, t2);
	}
	
	private static String stitch(String a, String b) {
		String[] temp1 = a.split("\n");
		String[] temp2 = b.split("\n");
		String result = "";
		for(int i = 0; i < temp1.length; i++) {
			result += temp1[i] + temp2[i] + "\n";
		}
		
		return result;
	}
}
