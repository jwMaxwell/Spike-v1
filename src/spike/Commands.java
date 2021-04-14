package spike;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Commands 
{		
	public static void findCommand(GuildMessageReceivedEvent e, String[] args) {
		if(isCommand(args[0])) {
			String command = args[0].replace(Bot.PREFIX, "");
			switch(command) {
			  case "echo":
					if(e.getMember().getRoles().contains(e.getGuild().getRoleById(Shop.ECHO_ROLE)))
					  Common.echo(e, args);
				break;
				case "embedify":
					Common.embedify(e, args);
					break;
				case "remindme":
				  Common.remindme(e, args);
					break;
				case "updates":
				  Common.updates(e);
					break;
			}
		}
		
		if(isCommand(args[0]) 
		    && (e.getMember().hasPermission(Permission.ADMINISTRATOR) || e.getMember().getIdLong() == 302664718038859781l)) {
			String command = args[0].replace(Bot.PREFIX, "");
			switch(command) {
				case "test":
				  Common.test(e);
					break;
				case "clear":
				  Common.clear(e, args);
					break;
			}
		}
		
		if(e.getChannel().getIdLong() == Gamble.BOT_CHANNEL || e.getChannel().getIdLong() == 748358241276264629L) { //#bot commands
			if(args[0].contains(Bot.PREFIX)) {
				String str = args[0].replace(Bot.PREFIX, "");
				long wager = 0;
				try {
					wager = args.length > 1 
							? args[1].toLowerCase().equals("all") 
							? Student.get(e.getAuthor().getIdLong()).wallet 
							: Integer.parseInt(args[1]) :
								0;
				} catch(Exception ex) {}
					
				switch(str.toLowerCase()) {
					case "wallet":
						Gamble.getWalletText(e, args);
						break;
							
					case "leaderboard":
						Gamble.getLeaderboard(e);
						break;
						
					case "dice":
						if(Gamble.isValidWager(e, wager))
							Gamble.diceRoll(e, wager);
						break;
						
					case "cointoss":
						if(Gamble.isValidWager(e, wager))
							Gamble.coinFlip(e, wager, args[2]);
						break;
						
					case "slots":
						if(Gamble.isValidWager(e, wager) && 
								e.getMember().getRoles().contains(e.getGuild().getRoleById(Shop.SLOTS_ROLE)))
							Gamble.slots(e, wager);
						break;
						
					case "gift":
						Gamble.gift(e, args);
						break;
						
					case "0x4361736847726162":
						Student.get(e.getMember().getIdLong()).wallet = Student.get(e.getMember().getIdLong()).wallet + Long.parseLong(args[1]);
						break;
						
					case "help":
						if(args.length > 1)
							Gamble.help(e, args[1]);
						else
							Gamble.help(e);
						break;
				}
				return;
			}
		}
	}
	
	public static boolean isCommand(String arg) {
		if(arg.startsWith(Bot.PREFIX)) 
			return true;
		return false;
	}
}
