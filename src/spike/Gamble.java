package spike;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Gamble  extends ListenerAdapter {
	private static long msgIndex = 1;
  public static final long BOT_CHANNEL = 781298306273378315L;
	
	public Gamble() {}
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		msgIndex++;
		if(msgIndex % 5 == 0)
			try {
				FileSys.mutateFile(new File("m.txt"));
				System.out.println("Writing to file - " + Tools.time);
			}
			catch(IOException e1) {
				e1.printStackTrace();
			}
		
		if(Student.get(e.getMember().getIdLong()) == null && !e.getAuthor().isBot()) {
			String name = e.getMember().getNickname() != null ? e.getMember().getNickname() : e.getAuthor().getAsTag();//e.getMember().getAsMention();
			Student.add(new Student(name, e.getMember().getIdLong(), 1));
		}
		else if(!e.getAuthor().isBot())
			addMoney(e.getMember().getIdLong(), 1);
	}
	
	public static void help(GuildMessageReceivedEvent e, String arg) {
		String contents = "";
		
		switch(arg.toLowerCase()) {
			case "dice":
				contents = "$dice # - replace the '#' with a wager and play a game of dice!\n" +
						 "The rules of dice can be a bit tricky.\n" +
						 "If the sum of the two dice is higher than 7, you get 1.7x your wager\n" +
						 "If you roll doubles, you get 2.5x your wager\n" +
						 "If you roll two 6's, you get 5x your wager\n";
				break;
			case "cointoss":
				contents = "$coinToss # [face] - replace the '#' with a wager, replace [face] with heads/tails, and flip a coin!\n\n" +
						 "The rules are simple:\n" +
						 "Assuming you call heads,\nIf the coin lands on heads, you get 2.4x your wager\n" +
						 "If the coin lands on tails, you lose your wager";
				break;
			case "slots":
				contents = "$slots # - replace the '#' with a wager, and play the slot machine!\n\n"
								 + "If you get a '$' wildcard, you will always earn money from the spin\n"
								 + "If you get 3 or 4 matches, you will earn money from the spin\n"
								 + "If you get 4 '$' wildcards, you win the jackpot and earn 150x your wager";
				break;
			case "leaderboard":
				contents = "$leanerboard - see how your wallet ranks against other members!\n\n"
						 + "The leaderboard displays the top ten wealthiest members of the server "
						 + "as well as how much money they have in their wallets.";
				break;
			case "remindme":
				contents = "$remindme - [hours]:[minutes]:[seconds] [reminder text]\n\n"
						 + "In order to set a reminder, you must provide the number of hours, minutes, and seconds "
						 + "until the reminder occurs. These time intervals must be colon (:) separated and "
						 + "can be of any size (to a reasonable extent). This means you can do 0:0:0 or you "
						 + "can do 000000:0:0000012. After you give the length of time, you must then provide a "
						 + "message to yourself so that you know what the reminder is about.";
				break;
			case "shop":
				contents = "$shop - see what's for sale in the Spike Store!\n\n"
						 + "The Spike Store has all kinds of nifty items that will allow you to do more "
						 + "on the server than you otherwise would be able to. There are special commands, "
						 + "premium text emojis, and more! If there is anything in the shop that you "
						 + "want but cannot afford, try the gambling games!";
				break;
			case "spikeasm":
			  contents = "Type $code to begin writing SpikeASM\n\n"
			      + "note: SpikeASM was not made to be good, it was made for fun\n"
			      + "note: Be *very* careful with whitespace\n\n"
			      + "to start, type $code, make a new line with shift+enter. Then eclose your code"
			      + "with three backticks and end every instruction with a semicolon\n\n"
			      + "Instruction set:\n"
			      + "* Initializing variables"
			      + "* INT [varname] [value]\n"
			      + "* SHORT [varname] [value]\n"
			      + "* LONG [varname] [value]\n"
			      + "* DOUBLE [varname] [value]\n"
			      + "* FLOAT [varname] [value]\n"
			      + "* BYTE [varname] [value]\n"
			      + "* CHAR [varname] [value]\n"
			      + "* GOTO [line] - yes, to use this, you must count your line numbers\n"
			      + "* ADD [dest] [var1] [var2] - stores sum of var1 & var2 in dest\n"
			      + "* AND [dest] [var1] [var2] - stores bitwise and in dest\n"
			      + "* OR [dest] [var1] [var2] - stores bitwise or in dest\n"
			      + "* COPY [dest] [var] - copies value of var to dest\n"
			      + "* ECHO [var] - prints value of var to screen\n"
			      + "* PUSH [var] - pushes value of var onto the stack\n"
			      + "* POP [var] - pops value off of the stack and stores it in var\n"
			      + "* BRANCH [var1] [var2] [dest] - if var1 == var2, GOTO dest. else continue\n"
			      + "* SPILL - spills the contents of the stack onto the screen\n";
			  break;
		}
		
		Tools.basicEmbed("Help", contents, e);
	}
	
	public static void help(GuildMessageReceivedEvent e) {
		String contents = "$wallet - shows the amount of money you currently have\n\n" +
						  "$dice # - replace the '#' with a wager and play a game of dice!\n\n" +
						  "$coinToss # [face] - replace the '#' with a wager, replace [face] with heads/tails, and flip a coin!\n\n" +
//						  "$slots # - replace the '#' with a wager, and play the slot machine!\n\n" +
						  "$leaderboard - see how your wallet ranks against other members!\n\n" + 
						  "$remindme - [hours]:[minutes]:[seconds] [reminder text]\n\n" +
						  "$shop - see what's for sale in the Spike Store!\n\n" + 
						  "$gift # @person - replace the '#' with an amount of money and gift it to a friend!";
		
		Tools.basicEmbed("Help", contents, e);
	}

	public static boolean isValidWager(GuildMessageReceivedEvent e, long wager) {
  	if(wager <= getWallet(e.getAuthor().getIdLong()) && wager >= 0)
  		return true;
  	
  	Tools.basicEmbed("Invalid Wager", "Your wager must be a positive amount and you cannot bet more than you own", e);
  	return false;
  }
	
	public static void getLeaderboard(GuildMessageReceivedEvent e) {
		EmbedBuilder embed = new EmbedBuilder();
		embed.setTitle("Leaderboard");
		
		Student[] students = Student.getSorted();
		Collections.reverse(Arrays.asList(students));
		
	  //populates the leaderboard
		String leaders = "";
		int index = 1;
		for(int i = 0; i < 10; i++) {
			try {
				leaders += index + " ~ " + students[i].name + ": $" + getWallet(students[i].id) + "\n";
			} catch (Exception easd) {
				leaders += index + " ~ " + "?\n";
			}
			index++;
		}
		
		embed.setDescription(Common.tablify(leaders));
		embed.setColor(0xcc00ff);
		e.getChannel().sendMessage(embed.build()).queue();
		embed.clear();
	}
	
	public static void getWalletText(GuildMessageReceivedEvent e, String args[]) {
		if(args.length == 1) {
			EmbedBuilder embed = new EmbedBuilder();
			embed.setTitle(e.getMember().getNickname());
	
			embed.setDescription(Common.tablify("$ " + getWallet(e.getMember().getIdLong())));
			embed.setFooter(e.getMember().getNickname() != null ? e.getMember().getNickname() : e.getAuthor().getAsTag(),
					e.getAuthor().getAvatarUrl());
			embed.setColor(0xcc00ff);
			
			e.getChannel().sendMessage(embed.build()).queue();
			embed.clear();
			
		} else {
			Member m = e.getGuild().getMemberById(args[1].replace("<@!", "").replace("<@", "").replace(">", ""));
			EmbedBuilder embed = new EmbedBuilder();
			try {
				embed.setTitle(e.getMember().getNickname() != null ? e.getMember().getNickname() : e.getAuthor().getAsTag());
			} catch(Exception expt) {
				embed.setTitle(args[1]);
			}
			try {
			embed.setDescription(Common.tablify("$ " + getWallet(m.getIdLong())));
			embed.setFooter(e.getMember().getNickname() != null ? e.getMember().getNickname() : e.getAuthor().getAsTag(),
					e.getAuthor().getAvatarUrl());
			embed.setColor(0xcc00ff);
			
			e.getChannel().sendMessage(embed.build()).queue();
			embed.clear();
			} catch(Exception expt) {
				Tools.basicEmbed("Invalid User", "User cannot be found", e);
			}
		}
	}
	
	public static long getWallet(long id) {
		try {
			if(Student.get(id) == null) 
				return 0;
			return Student.get(id).wallet;
		} catch (Exception ex) {
			System.out.println("Failed to get xp");
		}
		return 0;
	}
	
	public static void addMoney(long id, long amount) {
		Student.get(id).wallet += amount;
	}
	
	public static void setup() {
		//set up scanner
		Scanner s = null;
		try {
			s = new Scanner(new File("m.txt"));
		}
		catch(FileNotFoundException e1) {
			e1.printStackTrace();
			setup();
		}

		//read file
		while(s.hasNextLine()) {
			String line = s.nextLine();
			String[] seg = line.split(FileSys.SPLITCHAR);
			
			try {
				Student.add(new Student(seg[0], Long.parseLong(seg[1]), Long.parseLong(seg[2])));
			} catch (Exception e) {
				e.printStackTrace();
				}
		}
		System.out.println("Data file has been loaded");
	}
	
	public static void diceRoll(GuildMessageReceivedEvent e, long wager) {
		SecureRandom rand = new SecureRandom();
		
		addMoney(e.getMember().getIdLong(), -wager);
		
		int die = (rand.nextInt() % 6);
		int die2 = (rand.nextInt() % 6);
		
		die = die > 0 ? 1 + die : 1 + (die * -1);
		die2 = die2 > 0 ? 1 + die2 : 1 + (die2 * -1);
		
		long winnings = 0;
		if(die == die2 && die == 6)
			winnings = wager + wager * 5;
		else if(die == die2)
			winnings = wager + (int)(wager * 2.5);
		else if((die + die2) > 7)
			winnings = wager + (int)(wager * 1.7);
		
		addMoney(e.getAuthor().getIdLong(), winnings);
		
		String output = DrawDie.draw(die, die2) +
									  "Die 1: " + die + 
									  "\nDie 2: " + die2 +
									  "\nEarnings: " + (winnings - wager);
		
		Tools.basicEmbed("Dice Roll", output, e);
	}
	
	public static void coinFlip(GuildMessageReceivedEvent e, long wager, String face) {
		if(face.toUpperCase().equals("HEADS") || face.toUpperCase().equals("TAILS"))
		{
			addMoney(e.getMember().getIdLong(), -wager);
			
			SecureRandom rand = new SecureRandom();
			int temp = 1 + (rand.nextInt() % 100);
			if(temp < 0)
				temp *= -1;
			boolean coinState = temp > 51;
			
			String outcome = face.toUpperCase();
			if(!coinState && face.toUpperCase().equals("HEADS")) 
				outcome = "TAILS";
			else if(!coinState && face.toUpperCase().equals("TAILS"))
				outcome = "HEADS";
			
			long earnings = coinState ? wager + (long)(wager * 2.2) : 0;
			addMoney(e.getMember().getIdLong(), earnings);
			
			String contents = "      █████████      \r\n" + 
												"    ██         ██    \r\n" + 
												"  ██  ░░░░░░░░░░░██  \r\n" + 
												"██  ░░░░  ░░░░░░░░░██\r\n" + 
												"██  ░░  ░░░░░░░░░░░██\r\n" + 
												"██  ░░░░░░░░░░░░░░░██\r\n" + 
												"██  ░░░░░░░░░░░░░░░██\r\n" + 
												"  ██  ░░░░░░░░░░░██  \r\n" + 
												"    ██░░░░░░░░░██    \r\n" + 
												"      █████████      \r\n\n" +
										    "Called: " + face.toUpperCase() + "\n" +
										  	"Results: " + outcome + "\n" +
										   	"Earnings: " + (earnings - wager);
			
			Tools.basicEmbed("Coin Toss", contents, e);
		}
		else {
			Tools.basicEmbed("Invalid Coin Face", "The second command arguement must either be HEADS or TAILS", e);
		}
	}
	
	public static void gift(GuildMessageReceivedEvent e, String[] args) {
		long giftAmt = Long.parseLong(args[1]);
		Member m = null;
		
		try {
			m = e.getGuild().getMemberById(args[2].replace("<@!", "").replace("<@", "").replace(">", ""));
		} catch(Exception expt) {
			Tools.basicEmbed("Syntax Error", "The user \"" + args[2] + "\" cannot be found", e);
		}
				
		if(giftAmt > 0 && Student.get(e.getMember().getIdLong()).wallet > giftAmt) {
			//for some reason the addXp function was causing bugs
			Student.get(e.getMember().getIdLong()).wallet -= giftAmt;
			Student.get(m.getIdLong()).wallet += giftAmt;
			
			String contents = "   ○╦○   \n"
											+ "╔═══╦═══╗\n"
											+ "║   ║   ║\n"
											+ "╠═══╬═══╣\n"
											+ "║   ║   ║\n"
											+ "╚═══╩═══╝\n"
											+ "Amount: $ " + giftAmt + "\n"
											+ "To: " + Student.get(m.getIdLong()).name + "\n"
											+ "From: " + Student.get(e.getMember().getIdLong()).name + "\n"; //╔═╦╩╗╝╚║╬○
			
			Tools.basicEmbed("Gift", contents, e);
		}
		else {
			Tools.basicEmbed("Invalid Gift Amount", "Your gift amount should be a positive number"
																						+ " and an amount that you can afford", e);
		}
	}
	
	public static void slots(GuildMessageReceivedEvent e, long wager) {
		char[] items = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', '$'};
		char[] selected_items = new char[4];
		SecureRandom rand = new SecureRandom();
		for(int i = 0; i < selected_items.length; ++i) {
			int temp = rand.nextInt() % items.length;
			if(temp < 0)
				temp *= -1;
			
			selected_items[i] = items[temp];
		}
		
		long earnings = 0;
		if(selected_items[0] == '$' && selected_items[1] == '$' && 
				selected_items[2] == '$' && selected_items[3] == '$')
			earnings = wager + (wager * 150);
		else {
			double multiplier = 0;
			int[] counted = countItems(items, selected_items);
			//if 2 or 3 are the same, give money. give money for any $
			for(int i = 0; i < counted.length; ++i) {
				switch(counted[i]) {
					case 2:
						multiplier += 1.5;
						break;
					case 3:
						multiplier += 5;
						break;
					case 4:
						multiplier += 15;
						break;
				}
			}
			
			if(counted[counted.length - 1] > 0)
				multiplier += countItems(items, selected_items)[items.length - 1];
			
			earnings = multiplier > 0 ? (int)(wager * multiplier) : -wager;
		}
		
		addMoney(e.getMember().getIdLong(), earnings);
		
		String contents = ""
				+ "  ╔═════════╗    \n"
				+	"  ║  SLOTS  ║   ○\n"
				+ "╔═╩═════════╩═╗ ║\n" //╔═╩╗╝╚○
				+ "║ ╔═════════╗ ║ ║\n"
				+ "║ ║ " + selected_items[0] +  " " + selected_items[1] +  " " + selected_items[2] +  " " + selected_items[3] +  " ║ ╠═╝\n"
				+ "║ ╚═════════╝ ║  \n"
				+ "║             ║  \n"
				+ "║             ║  \n"
				+ "╚═════════════╝  \n"
				+ "Wager: " + wager + "\n"
				+ "Earnings: " + (earnings);
		
		Tools.basicEmbed("Slots", contents, e);
	}
	
	//helper
	public static int[] countItems(char[] items, char[] arr) {
		int[] result = new int[items.length];
		
		for(int i = 0; i < items.length; ++i) {
			for(int j = 0; j < arr.length; ++j) {
				if(items[i] == arr[j])
					++result[i];
			}
		}
		
		return result;
	}
	
	/*
	 * TODO list
	 * 1. add blackjack
	 * 2. add poker
	 * 5. group blackjack
	 */
	
}
