package spike;
import java.util.ArrayList;
import java.util.Arrays;

import interpreter.Run;
import lang.Lang;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ScriptChain extends ListenerAdapter {
	
	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
	  try {
  	  if (e.getMessage().getContentRaw().contains("$code")) {
  	    new Run().run(e, e.getMessage().getContentRaw());
  	    return;
  	  }
	  } catch (Exception ecxwasd) {}
	  
	  try {
      if (e.getMessage().getContentRaw().contains("$script")) {
        String code = e.getMessage().getContentRaw()
            .replace("$script", "");
        try {
          code = code.replace("```apache", "").replace("```", "");
        } catch(Exception asf) {
          code = code.replaceAll("```", "");
        }
        new Lang().run(e, code);
        return;
    }
    } catch (Exception ecxwasd) {}

	  
		String[] args = e.getMessage().getContentRaw().split("\\s+");
		if (args[0].equals("$&")) {
			chain(e, args);
		}
		else {
			Commands.findCommand(e, args);
		}
	}
	
	private void chain(GuildMessageReceivedEvent e, String[] args) {
		for (String[] t : command_split(args)) 
			Commands.findCommand(e, t);
	}
	
	private ArrayList<String[]> command_split(String args[]){
		ArrayList<String[]> commands = new ArrayList<String[]>();
		ArrayList<String> placeHolder = new ArrayList<String>();
		
		for (int i = 1; i < args.length; ++i) {
			String t = args[i];
			if (t.contains(";")) {
				placeHolder.add(t.substring(0, t.length() - 1));
				commands.add(Arrays.copyOf(placeHolder.toArray(), placeHolder.size(), String[].class));
				placeHolder.clear();
				continue;
			}
			placeHolder.add(t);
		}
		
		if (placeHolder.size() != 0)
			commands.add(Arrays.copyOf(placeHolder.toArray(), placeHolder.size(), String[].class));
		
		for (int i = 0; i < commands.size(); ++i) {
			commands.get(i)[0] = "$" + commands.get(i)[0];
		}
		
		return commands;
	}
	
}
