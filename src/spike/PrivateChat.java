package spike;

import java.awt.Color;
import java.security.SecureRandom;
import java.util.HashMap;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageType;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class PrivateChat extends ListenerAdapter {
  private static HashMap<String, PrivateMessageReceivedEvent> tickets =
      new HashMap<String, PrivateMessageReceivedEvent>();
  private static final long SERVER = 689243509466726412l;
  private static final long CHANNEL = 748357727872614411l;
  private static SecureRandom rand = new SecureRandom();
  private static final String intro = ""
      + "Your message has been sent anonymously to the admins\n"
      + "Someone will help you shortly. Thank you for your patience!\n\n"
      + "If you wish to mark your ticket as resolved, type $close";
  
  @Override
  public void onPrivateMessageReceived(PrivateMessageReceivedEvent e)
  {    
    if (e.getAuthor().isBot())
      return;
          
    
    boolean returningUser = false;
    for (PrivateMessageReceivedEvent t : tickets.values()) {
      if(e.getAuthor().equals(t.getAuthor())) {
        returningUser = true;
      }
    }
    
    if (!returningUser && e.getMessage().getContentRaw().equalsIgnoreCase("$close")) {
      EmbedBuilder eb = new EmbedBuilder();
      eb.setTitle("Anonymous report");
      eb.setDescription("You do not currently have an open ticket");
      eb.setColor(0xcc00ff);
      e.getChannel()
      .sendMessage(eb.build())
      .queue();
      return;
    }
    
    String ticketID = "";
    if (!returningUser) {
      EmbedBuilder eb = new EmbedBuilder();
      eb.setTitle("Anonymous report");
      eb.setDescription(intro);
      eb.setColor(0xcc00ff);
      e.getChannel()
      .sendMessage(eb.build())
      .queue();
      
      ticketID = generateTicket();
      tickets.put(ticketID, e);
    }
    else {
      for (String t : tickets.keySet()) {
        if (tickets.get(t).getAuthor().equals(e.getAuthor()))
         ticketID = t;
      }
    }
    
    if (e.getMessage().getContentRaw().equalsIgnoreCase("$close")) {
      tickets.remove(ticketID);
      
      EmbedBuilder eb = new EmbedBuilder();
      eb.clear();
      eb.setTitle("Ticket has been closed");
      eb.setColor(0xcc00ff);
      e.getChannel()
      .sendMessage(eb.build())
      .queue();
    }
    
    // creates message to send to administrator channel
    String url = "https://external-content.duckduckgo.com"
        + "/iu/?u=https%3A%2F%2Fs3.amazonaws.com%2Frapgen"
        + "ius%2FCoBaEZveTGqxqnOZ6sZp_never_gonna_give_yo"
        + "u_up.jpg&f=1&nofb=1";
    EmbedBuilder eb = new EmbedBuilder();
    eb.clear();
    if (e.getMessage().getContentRaw().equalsIgnoreCase("$close")) {
      eb.setDescription("***Ticket has been resolved***");
      eb.setFooter(ticketID, url);
      eb.setColor(Color.GREEN);
    }
    else {
      eb.setDescription(e.getMessage().getContentRaw());
      eb.setFooter(ticketID, url);
      eb.setColor(0xcc00ff);
    }
    
    // sends message to administrator channel
    e.getJDA()
    .getGuildById(SERVER)
    .getTextChannelById(CHANNEL)
    .sendMessage(eb.build())
    .append(e.getJDA().getGuildById(SERVER).getRoleById("689246362495352928").getAsMention())
    .queue();
    
    // adds user to list of cached students
    if (!Student.students.contains(
        new Student(e.getAuthor().getName(), e.getAuthor().getIdLong(), 0)))
    Student.add(new Student(e.getAuthor().getName(), e.getAuthor().getIdLong(), 0));
  }
  

  private static String generateTicket() {
    final char[] ALPHANUMERIC = 
        "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    String result = "";
    for (int i = 0; i < 5; ++i) {
      int indx = rand.nextInt() % ALPHANUMERIC.length;
      result += ALPHANUMERIC[indx > 0 ? indx : indx * -1];
    }
    
    while(tickets.containsKey(result)) {
      result = generateTicket();
    }
    return result;
  }
  
  // sends message to user
  @Override
  public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
    if (e.getChannel().getIdLong() == CHANNEL && !e.getAuthor().isBot())
      for (String t : tickets.keySet())
        if (e.getMessage().getContentRaw().contains(t.toString()))
            tickets.get(t).getChannel()
            .sendMessage(e.getMessage()
            .getContentRaw().replace(t, ""))
            .queue();
    
    if (e.getChannel().getIdLong() == CHANNEL && !e.getAuthor().isBot()
        && e.getMessage().getType().equals(MessageType.INLINE_REPLY))
      tickets.get(e.getMessage()
                   .getReferencedMessage()
                   .getEmbeds()
                   .get(0)
                   .getFooter()
                   .getText())
      .getChannel()
      .sendMessage(e.getMessage().getContentRaw())
      .queue();
  }
}
