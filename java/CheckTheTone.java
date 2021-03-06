import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ElementTone;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneCategory;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneScore;

public class CheckTheTone {

  private static String textToAnalyze =
    "I know the times are difficult! Our sales have been "
    + "disappointing for the past three quarters for our data analytics "
    + "product suite. We have a competitive data analytics product "
    + "suite in the industry. But we need to do our job selling it! "
    + "We need to acknowledge and fix our sales challenges. "
    + "We can't blame the economy for our lack of execution! "
    + "We are missing critical sales opportunities. "
    + "Our product is in no way inferior to the competitor products. "
    + "Our clients are hungry for analytical tools to improve their "
    + "business outcomes. Economy has nothing to do with it.";

  private static String testparams =
    "{\"textToAnalyze\" : \"" + textToAnalyze + "\"," +
    " \"username\"       : \"\"," +
    " \"password\"       : \"\"," +
    " \"endpoint\"        : \"https://sandbox-watson-proxy.mybluemix.net/tone-analyzer/api\"," +
    " \"authentication\"  : \"true\"}";

  public static void main(String[] args) {
    JsonParser parser = new JsonParser();
    JsonObject jsonArgs = parser.parse(testparams).getAsJsonObject();
    main(jsonArgs);
  }

  public static JsonObject main(JsonObject args) {
    JsonParser parser = new JsonParser();
    boolean noArgs = (args == null);
    boolean badArgs = (args.get("textToAnalyze").getAsString().isEmpty() || args.get("username").getAsString().isEmpty() || args.get("password").getAsString().isEmpty());
    if (noArgs || badArgs)
      args = parser.parse(testparams).getAsJsonObject();

    ToneAnalyzer service = new ToneAnalyzer("2016-05-19");
    service.setUsernameAndPassword(args.get("username").getAsString(),
                                   args.get("password").getAsString());
   if (args.get("endpoint")!=null) 
      service.setEndPoint(args.get("endpoint").getAsString());
   
   if (args.get("authentication")!=null) service.setSkipAuthentication((args.get("authentication").getAsString()=="true")?true:false);

   ToneAnalysis result =
  		 service.getTone(args.get("textToAnalyze").getAsString(), null).
  		 execute();
  		 
	   ElementTone elementTone = result.getDocumentTone();
	   List<ToneCategory> toneCategories = elementTone.getTones();
	   for (ToneCategory nextCategory : toneCategories) {
	     System.out.println("Analysis for " + nextCategory.getName());
	 
	       List<ToneScore> toneScores = nextCategory.getTones();
	       for (ToneScore nextScore : toneScores) {
	         System.out.println("    " + nextScore.getName() + " = " +
	                          (int)(nextScore.getScore() * 100) + "%");
	     }
	     System.out.println();
	   }

    JsonObject returnObject = parser.parse(result.toString()).getAsJsonObject();

    if (noArgs || badArgs)
      returnObject.
       addProperty("Note", "Either no arguments or the wrong number " +
                    "of arguments were passed in to this service, so " +
                    "default values were used. To POST data to this " +
                    "service, set the HTTP Content-Type header to " +
                    "'application/json' and pass a JSON object " +
                    "containing the fields 'textToAnalyze', 'username', "+
                    " and 'password'.");

    return returnObject;
  }
}
