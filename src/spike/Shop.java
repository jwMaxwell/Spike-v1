package spike;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Shop extends ListenerAdapter {
	public static final long EMOJIS_ROLE = 692222442801987635l;
	public static final long ECHO_ROLE = 809151273554804806l;
	public static final long HIGHROLLER_ROLE = 809127293955211274l;
	public static final long SLOTS_ROLE = 810249172606910494l;
	
	private static final String COUPON_EMOJIS = "textemojis>reactions";
	private static final String COUPON_SLOTS = "ihaveanaddiction";
	private static final String COUPON_ECHO = "canyouhearmehearmehearme";
	private static final String COUPON_HIGHROLLER = "10%luck20%skill";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split("\\s+");
		if(Commands.isCommand(args[0]) && args[0].equals(Bot.PREFIX + "shop")) {
			String contents = ""
					+ "'$buy textemojis': $5000 - A whole new list of text emojis!\n\n"
					+ "'$buy slots': $10000 - Play the slot machine as much as you want and make that money!\n\n"
					+ "'$buy echo': $15000 - When you type $echo [text], you can make Spike say anything!\n\n"
					+ "'$buy highroller': $30000 - An exclusive role, only obtainable by the best gamblers!"
					+ "";
			Tools.basicEmbed("Spike Shop", contents, e);
		}
		else if(Commands.isCommand(args[0]) && args[0].equals(Bot.PREFIX + "buy")) {
			if(args.length == 2) buy(e, args[1]);
			else buy(e, args[1], args[2]);
		}
	}
	
	private void buy(GuildMessageReceivedEvent e, String arg) {
		Student s = Student.get(e.getMember().getIdLong());
		long roleID = 0;
		long price = 0;
		if(arg.equals("textemojis")) {
			roleID = EMOJIS_ROLE;
			price = 5000;
		}
		else if (arg.equals("slots")) {
			roleID = SLOTS_ROLE;
			price = 10000;
		}
		else if (arg.equals("echo")) {
			roleID = ECHO_ROLE;
			price = 15000;
		}
		else if (arg.equals("highroller")) {
			roleID = HIGHROLLER_ROLE;
			price = 30000;
		}
		else {
			Tools.basicEmbed("Error", "The item '" + arg + "' doesn't exist. To see what items are on sale, type '$shop'", e);
			return;
		}

		if(s.wallet >= price && !e.getMember().getRoles().contains(e.getGuild().getRoleById(roleID))) {
			s.wallet -= price;
			e.getGuild().addRoleToMember(s.id, e.getGuild().getRoleById(roleID)).complete();
			String content = "Item: " + arg + "\n" +
							 "Price: $" + price + "\n\n" +
							 "Thank you for your purchase, please come again!";
			Tools.basicEmbed("Purchase Confirmation", content, e);
		}
		else if(e.getMember().getRoles().contains(e.getGuild().getRoleById(roleID))) {
			Tools.basicEmbed("Error", "You already own this item, you silly goose!", e);
		}
		else {
			Tools.basicEmbed("Error", "Insufficient funds", e);
		}		
	}
	
	private void buy(GuildMessageReceivedEvent e, String arg, String coupon) {
		Student s = Student.get(e.getMember().getIdLong());
		long roleID = 0;
		long price = 0;
		if(arg.equals("textemojis")) {
			roleID = EMOJIS_ROLE;
			price = coupon.equals(COUPON_EMOJIS) ? 2500 : 5000;
		}
		else if (arg.equals("slots")) {
			roleID = SLOTS_ROLE;
			price = coupon.equals(COUPON_SLOTS) ? 5000 : 10000;
		}
		else if (arg.equals("echo")) {
			roleID = ECHO_ROLE;
			price = coupon.equals(COUPON_ECHO) ? 7500 : 15000;
		}
		else if (arg.equals("highroller")) {
			roleID = HIGHROLLER_ROLE;
			price = coupon.equals(COUPON_HIGHROLLER) ? 15000 : 30000;
		}
		else {
			Tools.basicEmbed("Error", "The item '" + arg + "' doesn't exist. To see what items are on sale, type '$shop'", e);
			return;
		}

		if(s.wallet >= price) {
			s.wallet -= price;
			e.getGuild().addRoleToMember(s.id, e.getGuild().getRoleById(roleID)).complete();
			e.getChannel().deleteMessageById(e.getMessageIdLong()).complete();
			String content = "Item: " + arg + "\n" +
							 "Price: $" + price + "\n\n" +
							 "Thank you for your purchase, please come again!";
			Tools.basicEmbed("Purchase Confirmation", content, e);
		}
		else {
			Tools.basicEmbed("Error", "Insufficient funds", e);
		}		
	}	
}
