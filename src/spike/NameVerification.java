package spike;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class NameVerification extends ListenerAdapter {
	
	private static final long VERIFIED = 827972362326376489l;
	private static final long MUTED_CHANNEL = 689298597837144146l;
	private static final long ADMIN_CHANNEL = 748357727872614411l;
	private static final String MUTED_MSG = "You have been marked as unverified since you are either "
	                                      + "new here or you nickname does not meet the server's guidelines. "
	                                      + "Please read the rules and change your discord nickname to "
	                                      + "include your first and last name. **After you have done this, "
	                                      + "type `$verify` to request an unmute!**";
	private static final String NOTIFY_MSG = " has requested to be unmuted";
	private static final String NOTIFIED_MSG = "The admins have been notified about your unmute request!";
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		if(e.getGuild().getIdLong() == 689243509466726412l)
		if(!e.getMember().getRoles().contains(e.getGuild().getRoleById(VERIFIED))) {
			e.getChannel().deleteMessageById(e.getMessageIdLong()).complete();
			
			EmbedBuilder eb = new EmbedBuilder();
			eb.setAuthor(e.getMember().getNickname());
			
			if(e.getMessage().getContentDisplay().equals(Bot.PREFIX + "verify")) {
				EmbedBuilder adminEb = new EmbedBuilder();
				adminEb.setTitle("User \"" + e.getMember().getEffectiveName() + "\" unmute request");
				adminEb.setAuthor(e.getMember().getNickname());
				adminEb.setDescription(e.getMember().getAsMention() + NOTIFY_MSG);
				
				eb.setTitle("Request sent!");
				eb.setAuthor(e.getMember().getNickname());
				eb.setDescription(NOTIFIED_MSG);
				
				final long ADMIN_TAG = 689246362495352928l;
				e.getGuild().getTextChannelById(ADMIN_CHANNEL).sendMessage(e.getGuild().getRoleById(ADMIN_TAG).getAsMention()).queue();
				e.getGuild().getTextChannelById(ADMIN_CHANNEL).sendMessage(adminEb.build()).queue();
				e.getGuild().getTextChannelById(MUTED_CHANNEL).sendMessage(eb.build()).queue();
				adminEb.clear();
			}
			else {
				eb.setTitle("Unverified");
				eb.setAuthor(e.getMember().getNickname());
				eb.setDescription(MUTED_MSG);
				
				e.getGuild().getTextChannelById(MUTED_CHANNEL).sendMessage(e.getMember().getAsMention()).queue();
				e.getGuild().getTextChannelById(MUTED_CHANNEL).sendMessage(eb.build()).queue();
			}
			eb.clear();
		}
	}
}
