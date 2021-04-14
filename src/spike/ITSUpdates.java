package spike;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonParser;

import net.dv8tion.jda.api.EmbedBuilder;

public class ITSUpdates {
  private static final long WAIT_TIME = /*30 **/ 20000;
  
  public ITSUpdates() {
    Timer timer = new Timer("Timer");
    TimerTask task = new TimerTask() {
      @Override
      public void run() {
        getUpdate();
      }
    };
    timer.schedule(task, WAIT_TIME);
  }
  
  private void getUpdate() {
    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = 
        HttpRequest.newBuilder(URI.create("http://api.truman.edu/its/service-notes/4265"))
        .GET()
        .setHeader("accept", "application/vnd.its.v1+json")
        .build();
    
    HttpResponse<String> response = null;
    try {
      response = client.send(request, BodyHandlers.ofString(StandardCharsets.UTF_8));
    } catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    DeconstructJSON djson = new DeconstructJSON(response.body());
    
    String content = 
          "**" + djson.title + "**\n"
        + "Entry date: " + djson.entryDate + "\n\n"
        + djson.body;
    
    EmbedBuilder eb = new EmbedBuilder();
    eb.setTitle("ITS Service Announcement");
    eb.setDescription(content);
    
    Bot.jda
       .getGuildById("689243509466726412")
       .getTextChannelById("748358241276264629")
       .sendMessage(eb.build())
       .queue();
  }
  
  class DeconstructJSON {
    int id;
    String entryDate;
    String title;
    String body;
    
    public DeconstructJSON(String dat) {
      //gets service notes id
      int i = dat.indexOf("AutoID") + 9;
      int k;
      for(k = i; dat.charAt(k) != '"'; k++);
      this.id = Integer.parseInt(dat.substring(i, k));
      
      i = dat.indexOf("EntryDate") + "EntryDate\":\"".length();
      for(k = i; dat.charAt(k) != '"'; k++);
      this.entryDate = dat.substring(i, k);
      
      i = dat.indexOf("ShortDesc") + "ShortDesc\":\"".length();
      for(k = i; dat.charAt(k) != '"'; k++);
      this.title = dat.substring(i, k);
      
      i = dat.indexOf("LongDesc") + "LongDesc\":\"".length();
      for(k = i; dat.charAt(k) != '"'; k++);
      this.body = dat.substring(i, k);//this.escape(dat.substring(i, k));
      
    }
    
    private String escape(String raw) {
      String line = raw;
      String pattern = "\\\\u([0-9A-F]{4})"; // Match unicode, which is \\u followed by 4 hex digits
      Pattern r = Pattern.compile(pattern);
      Matcher m = r.matcher(line);
      while(m.find()){
        char theChar = ((char)Integer.parseInt(m.group(1), 16));
        line = line.replaceAll("\\"+m.group(0), ""+theChar);
      }

      line = line.replaceAll("\\\\/", "/");
      line = line.replaceAll("&nbsp;", " ");
      line = line.replaceAll("\\\\r\\\\n\\\\r\\\\n", " ");
      line = line.replaceAll("\\\\r\\\\n", " ");
      line = line.replaceAll("</?p>", "");
      return line;
    }
}
  /*
   * {
"_links":{
"self":{"href":"http:\/\/api.truman.edu\/its\/service-notes?current=yes\u0026page=1"},
"first":{"href":"http:\/\/api.truman.edu\/its\/service-notes?current=yes"},
"last":{"href":"http:\/\/api.truman.edu\/its\/service-notes?current=yes\u0026page=1"}},

"_embedded":{"service_notes":[{
"AutoID":"4265",
"EntryDate":"2021-03-22 13:26:48",
"ShortDesc":"TruView service outage",
"LongDesc":"\u003Cp\u003ETruView services experienced multiple outages this morning starting approximately at 6:58am. Services have been restarted and should be available.\u003C\/p\u003E\r\n\r\n\u003Cp\u003ENote: If you are trying to register and TruView is unavailable, remember you can use the direct access URL for registration: http:\/\/registration.truman.edu\u003C\/p\u003E\r\n\r\n\u003Cp\u003E\u0026nbsp;\u003C\/p\u003E",
"EndDate":"2021-03-22 20:25:00",
"timedown":"2021-03-22 06:58:00",
"timeup":"2021-03-22 08:24:00",
"scheduled":"0",
"degraded":"0",

"_links":{"self":{"href":"http:\/\/api.truman.edu\/its\/service-notes\/4265"}}}]},"page_count":1,"page_size":25,"total_items":1,"page":1}
   */
  
}
