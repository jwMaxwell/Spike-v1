package spike;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.LinkedList;
import java.util.Queue;
import javax.security.auth.login.LoginException;
import javax.swing.Timer;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

public class Bot extends ListenerAdapter
{
	public static JDA jda;
	private static final String BOTTOKEN = null; 
	public static final String PREFIX = "$";
	public static final String ACTIVITYDESC = "students cry";
	public static Activity botActivity = Activity.watching(ACTIVITYDESC);
	public static OnlineStatus botOnlineStatus = OnlineStatus.DO_NOT_DISTURB;
	public static Queue<Message> cache = new LinkedList<Message>();
	public static Timer time = null;
	
	@SuppressWarnings({ "deprecation", "static-access" })
	public static void main(String[] args)
	{	
		Gamble.setup();
		try {
			jda = new JDABuilder(AccountType.BOT).createDefault(BOTTOKEN)
					.setMemberCachePolicy(MemberCachePolicy.ALL)
					.enableIntents(GatewayIntent.GUILD_MEMBERS).build();
		} catch(LoginException e) {
			System.out.println("Failed to login - Reenter your bot token and try again");
		}
		
		jda.getPresence().setStatus(botOnlineStatus);
		jda.getPresence().setActivity(botActivity);
		jda.addEventListener(new Gamble());
		jda.addEventListener(new NameVerification());
		jda.addEventListener(new Bet());
		jda.addEventListener(new ScriptChain());
		jda.addEventListener(new TextEmojis());
		jda.addEventListener(new Shop());
		jda.addEventListener(new PrivateChat());
		//changes text every update
		ActionListener timeListener = new ActionListener() 
		{	
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				Date today = new Date(); //gets date
				Tools.time = today.toString();
			}
		};		
		
		final int SECONDS = 1000; //milliseconds per second
		time = new Timer(SECONDS, timeListener); //updates the time every second
		time.start();
		
		//new ITSUpdates();
	}
	
	@Override
	 public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		 cache.add(e.getMessage());
		 if(cache.size() > 10)
			 cache.poll();
	 }
}


