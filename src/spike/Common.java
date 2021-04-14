package spike;

import java.awt.Color;
import java.util.Timer;
import java.util.TimerTask;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Common {
  public static void updates(GuildMessageReceivedEvent e) {
    String contents = "";
    
    Tools.basicEmbed("Update log", contents, e);
  }
  
  public static void remindme(GuildMessageReceivedEvent e, String[] args) {
    final long HOUR = 3600000;
    final long MIN = 60000;
    final long SEC = 1000;
    String[] timeSegs = args[1].split(":");
    long time = (Long.parseLong(timeSegs[0]) * HOUR) 
        + (Long.parseLong(timeSegs[1]) * MIN) 
        + (Long.parseLong(timeSegs[2]) * SEC);
    
    String temp = "";
    for (int i = 2; i < args.length; i++) {
      temp += args[i] + " ";
    }
    String str = temp;
    
    TimerTask task = new TimerTask() {
        public void run() {
          EmbedBuilder eb = new EmbedBuilder();
          eb.setTitle("Reminder :bell:");
          eb.setDescription(str);
          eb.setColor(0xcc00ff);
          eb.setFooter(e.getMember().getNickname() != null ? e.getMember().getNickname() : e.getAuthor().getAsTag()
                  , e.getAuthor().getAvatarUrl());
          e.getChannel().sendMessage(eb.build()).append(e.getMember().getAsMention()).queue();
            
        }
    };
    Timer timer = new Timer("Timer");
        
    timer.schedule(task, time);
    String contents = "Reminder set for " + timeSegs[0] 
            + "hours, " + timeSegs[1] 
            + "minutes, and " + timeSegs[2] 
            + "seconds from now.\n"
            + "Current time: " + Tools.time;
    Tools.basicEmbed("Reminder", contents, e);
  }
  
  public static void test(GuildMessageReceivedEvent e) {
    EmbedBuilder embed = new EmbedBuilder();
    embed.setTitle("Test");

    embed.setDescription(tablify("Testing 1 2 3"));
  
    e.getChannel().sendMessage(embed.build()).queue();
    embed.clear();
  }
  
  public static String tablify(String str) {
    String[] lines = str.split("\n");
    
    //finds maximum line length
    int max = 0;
    for( String t : lines) {
      if (t.length() > max) max = t.length();
    }
    max += 4;
    
    //makes top of table
    String result = "```yaml\n╔"; 
    for (int i = 0; i < max; i++) {
      result += "═";
    }
    result += "╗\n";
    
    //makes content
    for (String t : lines) {
      result += "║ ";
      result += t;
      
      for (int i = 0; i < max - t.length() - 1; i++) {
        result += " ";
      }
      
      result += "║\n";
      if (!t.equals(lines[lines.length - 1])) result += "╠";
      else result += "╚";
      
      for (int i = 0; i < max; i++) {
        result += "═";
      }
      if (!t.equals(lines[lines.length - 1])) result += "╣\n";
      else result += "╝\n```";
    }
    
    return result;
    
    /*
     * ╔═══════╦════╗
     * ║ salty ║  0 ║
     * ╠═══════╬════╣
     * ║ Eddie ║ 20 ║
     * ╚═══════╩════╝
     */
    
  }
  
  public static void embedify(GuildMessageReceivedEvent e, String args[]) {
    EmbedBuilder embed = new EmbedBuilder();
    embed.setTitle(Tools.spaceifier(args[1]));
    
    String temp = "";
    for (int i = 2; i < args.length; i++)
    {
      temp += args[i] + " ";
    }

    embed.setDescription(temp);
    embed.setFooter(e.getMember().getNickname() != null ? e.getMember().getNickname() : e.getAuthor().getAsTag(),
        e.getAuthor().getAvatarUrl());

    embed.setColor(0xcc00ff);
    
    e.getChannel().sendMessage(embed.build()).queue();
    embed.clear();
    
    e.getChannel().deleteMessageById(e.getMessageIdLong()).complete();
  }
  
  public static void clear(GuildMessageReceivedEvent e, String args[]) {
    EmbedBuilder error = new EmbedBuilder();
    error.setColor(Color.RED);
    if (Integer.parseInt(args[1]) < 2 || Integer.parseInt(args[1]) > 100) {
      error.setTitle("❌ Invalid Arguements");
      error.setDescription("A minimum of 2 messages can be selected at one time. "
                 + "A maximum of 100 messages can be selected at one time.");
      e.getChannel().sendMessage(error.build()).queue();
      error.clear();
    } 
    else {
      try {
        e.getChannel().deleteMessages(e.getChannel().getHistory()
            .retrievePast(Integer.parseInt(args[1])).complete()).queue();
      } catch(Exception ex) {
        error.setTitle("❌ Some messages could not be cleared");
        e.getChannel().sendMessage(error.build()).queue();
        error.clear();
      }
    }
  }
  
  public static void echo(GuildMessageReceivedEvent e, String args[]) {
    e.getChannel().deleteMessageById(e.getMessageIdLong()).complete();
    
    String str = "";
    for (int i = 1; i < args.length; i++) {
      str += args[i] + " ";
    }
    Tools.type(e, str);
  }
}
