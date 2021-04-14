package spike;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Tools
{
	public static String time = "";
	
	public static boolean argEqualTo(String arg, String str)
	{
		if(arg.trim().equalsIgnoreCase(str)) return true;
		return false;
	}
	
	public static void type(GuildMessageReceivedEvent e, String s)
	{
		e.getChannel().sendTyping().queue();
		e.getChannel().sendMessage(s).queue();
	}
	
	public static String spaceifier(String s) {
		char[] sArr = s.toCharArray();
		for(int i = 0; i < sArr.length; i++) 
			sArr[i] = sArr[i] == '_' ? ' ' : sArr[i];
		
		return new String(sArr);
	}
	
	public static LinkedHashMap<Long, Long> sortHashMapByValues(
		  HashMap<Long, Long> passedMap) {
		  List<Long> mapKeys = new ArrayList<>(passedMap.keySet());
		  List<Long> mapValues = new ArrayList<>(passedMap.values());
		  Collections.sort(mapValues);
		  Collections.sort(mapKeys);
		
		  LinkedHashMap<Long, Long> sortedMap =
		      new LinkedHashMap<>();
		
		  Iterator<Long> valueIt = mapValues.iterator();
		  while (valueIt.hasNext()) {
		      Long val = valueIt.next();
		      Iterator<Long> keyIt = mapKeys.iterator();
		
		      while (keyIt.hasNext()) {
		          Long key = keyIt.next();
		          Long comp1 = passedMap.get(key);
		          Long comp2 = val;
		
		          if (comp1.equals(comp2)) {
		              keyIt.remove();
		              sortedMap.put(key, val);
		              break;
		          }
		      }
		  }
		  return sortedMap;
		}
	
	public static void basicEmbed(String title, String contents, GuildMessageReceivedEvent e) {
		EmbedBuilder eb = new EmbedBuilder();
		
		eb.setTitle(title);
		eb.setDescription("```yaml\n" + contents + "\n```");
		eb.setFooter(e.getMember().getNickname() != null ? e.getMember().getNickname() : e.getAuthor().getAsTag(),
				e.getAuthor().getAvatarUrl());
		eb.setColor(0xcc00ff);
		
		e.getChannel().sendMessage(eb.build()).queue();
		eb.clear();
	}
	
	public static void getTime() {

	}
	
	
}
