package spike;
import java.util.ArrayList;
import java.util.Arrays;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Bet extends ListenerAdapter {
	
	ArrayList<ActiveBet> activeBets = new ArrayList<ActiveBet>();
	final static String MAKE_BET_STR = "||*~~0x54686973206973206120626574~~*||";
	final static String CLOSE_BET_STR = "||*~~0x4e6f7720656e64696e672074686520626574~~*||";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split("\\s+");
		if(args[0].equals("$bet") && Gamble.isValidWager(e, Long.parseLong(args[1])) && findBet(e) == null) 
			makeBet(e, args);
		else if(args[0].equals("$bet") && Gamble.isValidWager(e, Long.parseLong(args[1])) && findBet(e) != null)
			Tools.basicEmbed("Too many active bets!", "You currently have too many active bets. "
					+ "The most active bets you can have at one time is: 1", e);
		
		if(args[0].toUpperCase().equals("$ENDBET"))
			endBet(e);
		
		if(e.getAuthor().getIdLong() == 780512234606297118l &&
				e.getMessage().getContentRaw().contains(MAKE_BET_STR)) { //for bets
			for(ActiveBet t : activeBets)
				if(t.childMsg == null)
					t.childMsg = e.getMessage();
			addReactions(e);
		}
		if(e.getAuthor().getIdLong() == 780512234606297118l &&
				e.getMessage().getContentRaw().contains(CLOSE_BET_STR)) //for outcome
			addReactions(e);
	}
	
	private void makeBet(GuildMessageReceivedEvent e, String[] args) {		
		EmbedBuilder eb = new EmbedBuilder();
		
		eb.setTitle("Bet");
		eb.setDescription("```yaml\n" + buildMsg(Arrays.copyOfRange(args, 2, args.length)) + "\n```");
		eb.setFooter(e.getMember().getNickname() != null ? e.getMember().getNickname() : e.getAuthor().getAsTag(),
				e.getAuthor().getAvatarUrl());
		eb.setColor(0xcc00ff);
		
		e.getChannel().sendMessage(eb.build()).append(MAKE_BET_STR).queue();
		eb.clear();
		
		activeBets.add(new ActiveBet(e.getMessage(), Long.parseLong(args[1])));
	}
	
	private void endBet(GuildMessageReceivedEvent e) {
		Message bet = findBet(e).getBetMsg();
		if(bet != null) {
			EmbedBuilder eb = new EmbedBuilder();
			
			eb.setDescription("```yaml\n" + "Who won the bet?" + "\n```");
			eb.setFooter(e.getMember().getNickname() != null ? e.getMember().getNickname() : e.getAuthor().getAsTag(),
					e.getAuthor().getAvatarUrl());
			eb.setColor(0xcc00ff);
			
			e.getChannel().sendMessage(eb.build()).append(CLOSE_BET_STR).queue();
			eb.clear();
		}
		else {
			String contents = "You have no existing bets. For information about how to create a bet, "
											+ "type '$help bet'";
			Tools.basicEmbed("No bets found", contents, e);
		}
	}
	
	private void addReactions(GuildMessageReceivedEvent e) {
		e.getMessage().addReaction("👍").complete();
		e.getMessage().addReaction("👎").complete();
	}
	
	
	private ActiveBet findBet(GuildMessageReceivedEvent e) {
		for(ActiveBet t : activeBets)
			if(e.getAuthor().getIdLong() == t.getBetMsg().getAuthor().getIdLong())
				return t;
		return null;
	}
	
	@Override
	public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent e) {
		if(e.getUser().isBot())
			return;
	
		ActiveBet bet = null;
		boolean isGuestBet = false;
		for(ActiveBet t : activeBets)
			if(t.betMsg.getMember().equals(e.getMember()))
				bet = t;
		
		for(ActiveBet t : activeBets)
			if(t.childMsg.getIdLong() == e.getMessageIdLong()) {
				bet = t;
				isGuestBet = true;
			}
		
		if(bet == null)
			return;
		
		if(bet.childMsg.getContentRaw().contains(MAKE_BET_STR) && isGuestBet) {
			bet.addUser(e.getMember(), e, e.getReaction());
			return;
		}
		
		EmbedBuilder eb = new EmbedBuilder();
		eb.setTitle("Bet Outcome");
		eb.setDescription("```yaml\n" + bet.payout(e) + "\n```");
		eb.setFooter(e.getMember().getNickname(), e.getMember().getUser().getAvatarUrl());
		eb.setColor(0xcc00ff);
		e.getChannel().sendMessage(eb.build()).queue();
		activeBets.remove(activeBets.indexOf(bet));
	}
	
	public String buildMsg(String args[]) {
		String result = "";
		for(String t : args)
			result += t + " ";
		
		return result;
	}
	
	class ActiveBet {
		Message childMsg;
		Message betMsg;
		long wager;
		ArrayList<userChoice> users = new ArrayList<userChoice>();
		
		public ActiveBet(Message betMsg, long wager) {
			this.wager = wager;
			this.betMsg = betMsg;
		}
		
		public Message getBetMsg() {
			return this.betMsg;
		}
		
		public Member[] getUsers() {
			return (Member[])this.users.toArray();
		}
		
		public void addUser(Member m, GuildMessageReactionAddEvent e, MessageReaction vote) {
			users.add(new userChoice(m, vote));
			Gamble.addMoney(e.getUserIdLong(), -wager);
		}
		
		public String payout(GuildMessageReactionAddEvent e) {
			int losers = 0;
			
			String reactName = e.getReactionEmote().getName().equals("👍") ? "👍" : "👎";
			
			//counts losers
			for(int i = 0; i < users.size(); ++i) {
				if(!users.get(i).choice.getReactionEmote().getName().equals(reactName)) {
					++losers;
					users.remove(i);
				}
			}
			
			long winningPool = wager * users.size();
			
			long winningsPerPerson = users.size() - losers == 0 ? wager : winningPool / (users.size() - losers);
			if(losers == 0)
				winningsPerPerson = 0;
						
			for(userChoice t : users)
				if(t.choice.getReactionEmote().getName().equals(reactName))
					Gamble.addMoney(t.user.getIdLong(), wager + winningsPerPerson);
			
			String result = ""
					+ "Outcome: " + reactName + "\n"
					+ "Winner count: " + (users.size() - losers) + "\n"
					+ "Loser count: " + losers + "\n"
					+ "Winnings per person: " + winningsPerPerson;
			return result;
		}
		
		class userChoice {
			public Member user;
			public MessageReaction choice;
			
			public userChoice(Member m, MessageReaction r) {
				this.user = m;
				this.choice = r;
			}
		}
	}
	/*
	 * BET GRAMMAR
	 * 
	 * $bet [wager] text
	 */
}
