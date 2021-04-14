package spike;
import java.util.HashMap;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class TextEmojis extends ListenerAdapter{
	HashMap<String, String> textEmotes = new HashMap<String, String>();
	HashMap<String, String> premiumEmotes = new HashMap<String, String>();
	
	public TextEmojis() {
		this.textEmotes.put("/lenny", "( ͡° ͜ʖ ͡°)");
		this.textEmotes.put("/lennyshrug", "¯\\\\_( ͡° ͜ʖ ͡°)\\_/¯");
		this.textEmotes.put("/fightme", "(ง'̀-'́)ง");
		this.textEmotes.put("/eyefallsout", "( ಠ ͜ʖರೃ)");
		this.textEmotes.put("/owo", "(´・ω・`)");
		this.textEmotes.put("/highfive", "( ⌒o⌒)人(⌒-⌒ )v");
		this.textEmotes.put("/noyou", "(☞ﾟヮﾟ)☞ ☜(ﾟヮﾟ☜) ");
		this.textEmotes.put("/excuse", "ಠ_ಠ");
		this.textEmotes.put("/upsetti", "ಠ╭╮ಠ");
		this.textEmotes.put("/hugpls", "༼ つ ◕_◕ ༽つ");
		this.textEmotes.put("/canthearyou", "ᕦ(ò_óˇ)ᕤ");
		this.textEmotes.put("/frickyou", "╭∩╮(ಠ۝ಠ)╭∩╮");
		this.textEmotes.put("/listening", "ʕ ͠° ʖ̫ °͠ ʔ");
		
		this.textEmotes.put("/list", "```" +
				"/canthearyou, ᕦ(ò_óˇ)ᕤ\n" +
				"/excuse, ಠ_ಠ\n" + 
				"/eyefallsout, ( ಠ ͜ʖರೃ)\n" +
				"/fightme, (ง'̀-'́)ง\n" + 
				"/frickyou, ╭∩╮(ಠ۝ಠ)╭∩╮\n" +
				"/highfive,( ⌒o⌒)人(⌒-⌒ )v\n" + 
				"/hugpls, ༼ つ ◕_◕ ༽つ\n" +
				"/lenny, ( ͡° ͜ʖ ͡°)\n" + 
				"/lennyshrug, ¯\\_( ͡° ͜ʖ ͡°)_/¯\n" + 
				"/listening, ʕ ͠° ʖ̫ °͠ ʔ\n" +
				"/noyou, (☞ﾟヮﾟ)☞ ☜(ﾟヮﾟ☜)\n" + 	
				"/owo, (´・ω・`)\n" + 
				"/upsetti, ಠ╭╮ಠ\n" +
				"```");
		
		//premium emojis:
		this.premiumEmotes.put("/cri", "(ಥ﹏ಥ)");
		this.premiumEmotes.put("/sad", "ಥ_ಥ");
		this.premiumEmotes.put("/smooch", "( ˘ ³˘)❤");
		this.premiumEmotes.put("/uhohlenny", "( ͡ʘ ͜ʖ ͡ʘ)");
		this.premiumEmotes.put("/unsettlinglenny", "( ͡👁️ ͜ʖ ͡👁️)");
		this.premiumEmotes.put("/happy", "( ﾟヮﾟ)");
		this.premiumEmotes.put("/depressed", "（ ´,_ゝ`)");
		this.premiumEmotes.put("/no", "(╬ ಠ益ಠ)");
		this.premiumEmotes.put("/intensifies", "(≧ロ≦)");
		this.premiumEmotes.put("/yay", "ヽ(´▽`)/");
		this.premiumEmotes.put("/ayy", "(☞ﾟヮﾟ)☞");
		this.premiumEmotes.put("/stare", "(*′☉.̫☉)");
		this.premiumEmotes.put("/smug", "( ‾ʖ̫‾)");
		this.premiumEmotes.put("/imfine", "(¬‿¬)");
		this.premiumEmotes.put("/sopretty", "(•‾̑⌣‾̑•)");
		this.premiumEmotes.put("/imthebest", "☆(◒‿◒)☆");
		this.premiumEmotes.put("/catdance", "⊂ヽ \n" + 
		    "　 ＼＼ Λ＿Λ\n" + 
		    "　　 ＼( 'ㅅ' )\n" + 
		    "　　　 >　⌒ヽ\n" + 
		    "　　　/ 　 へ  ＼\n" + 
		    "　　 /　　/　＼＼\n" + 
		    "　　 ﾚ　ノ　　 ヽつ\n" + 
		    "　　/　/ \n" + 
		    "　 /　/| \n" + 
		    "　(　(ヽ \n" + 
		    "　|　|、＼ \n" + 
		    "　| 丿 ＼ ⌒) \n" + 
		    "　| |　　)");
		
		this.premiumEmotes.put("/list", "Premium emotes:\n```"
				+ "/ayy, (☞ﾟヮﾟ)☞\n"
				+ "/cri, (ಥ﹏ಥ)\n"
				+ "/depressed, （ ´,_ゝ`)\n"
				+ "/happy, ( ﾟヮﾟ)\n"
				+ "/imfine, (¬‿¬)\n"
				+ "/imthebest, ☆(◒‿◒)☆\n"
				+ "/intensifies, (≧ロ≦)\n"
				+ "/no, (╬ ಠ益ಠ)\n"
				+ "/sad, ಥ_ಥ\n"
				+ "/smooch, ( ˘ ³˘)❤\n"
				+ "/smug, ( ‾ʖ̫‾)\n"
				+ "/sopretty, (•‾̑⌣‾̑•)\n"
				+ "/stare. (*′☉.̫☉)\n"
				+ "/uhohlenny, ( ͡ʘ ͜ʖ ͡ʘ)\n"
				+ "/unsettlinglenny, ( ͡👁️ ͜ʖ ͡👁️)\n"
				+ "/yay, ヽ(´▽`)/\n"
				+ "/catdance,\n ⊂ヽ \n" + 
				"　 ＼＼ Λ＿Λ\n" + 
				"　　 ＼( 'ㅅ' )\n" + 
				"　　　 >　⌒ヽ\n" + 
				"　　　/ 　 へ  ＼\n" + 
				"　　 /　　/　＼＼\n" + 
				"　　 ﾚ　ノ　　 ヽつ\n" + 
				"　　/　/ \n" + 
				"　 /　/| \n" + 
				"　(　(ヽ \n" + 
				"　|　|、＼ \n" + 
				"　| 丿 ＼ ⌒) \n" + 
				"　| |　　)"
				+ "```");
		
	}
	
	public void onGuildMessageReceived(GuildMessageReceivedEvent e) {
		String[] args = e.getMessage().getContentRaw().split("\\s+");
		String inpt = args[0].toLowerCase();
		if(this.textEmotes.containsKey(inpt)) {
			e.getChannel().deleteMessageById(e.getMessageIdLong()).complete();
			Tools.type(e, e.getMember().getAsMention() + ";\n" + this.textEmotes.get(inpt));
		}
		if(e.getMember().getRoles().contains(e.getGuild().getRoleById(Shop.EMOJIS_ROLE))
				&& this.premiumEmotes.containsKey(inpt)) {
			if(!inpt.equals("/list"))
				e.getChannel().deleteMessageById(e.getMessageIdLong()).complete();
			Tools.type(e, e.getMember().getAsMention() + ";\n" + this.premiumEmotes.get(inpt));
		}
	}
}
