import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import com.ibm.watson.developer_cloud.natural_language_classifier.v1.NaturalLanguageClassifier;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.Classification;
import com.ibm.watson.developer_cloud.natural_language_classifier.v1.model.ClassifiedClass;

public class LanguageClassifier {

  private static String data =
    "{\"textToClassify\" : \"Will it rain tomorrow?\","  +
    " \"contextId\"      : \"ff18c7x157-nlc-2810\"," +
    " \"username\"       : \"1e2a85d3-f9f3-4d77-9b44-d0d56c93a028\"," +
    " \"password\"       : \"EjhIkxAUWWVQ\"}";

  public static void main(String[] args) {
    JsonParser parser = new JsonParser();
    JsonObject jsonArgs = parser.parse(data).getAsJsonObject();
    main(jsonArgs);
  }

  public static JsonObject main(JsonObject args) {
    JsonParser parser = new JsonParser();
    boolean noArgs = (args == null);
    boolean badArgs = (args.entrySet().size() != 7);
    if (noArgs || badArgs)
      args = parser.parse(data).getAsJsonObject();

    NaturalLanguageClassifier service = new NaturalLanguageClassifier();

    service.setUsernameAndPassword
        (args.get("username").getAsString(),
         args.get("password").getAsString());

    Classification result = service.classify
        (args.get("contextId").getAsString(),
         args.get("textToClassify").getAsString()).
        execute();

    System.out.println("The most likely classification is " +
                       result.getTopClass() + "\n");
    System.out.println("The complete list is:");
    List<ClassifiedClass> classifications = result.getClasses();
    for (ClassifiedClass nextClassification : classifications) {
      System.out.println("  Classification: " + nextClassification.getName() +
                         " (confidence: " +
                         (int)(nextClassification.getConfidence() * 100) +
                         "%)");
    }

    JsonObject returnObject = parser.parse(result.toString()).getAsJsonObject();

    if (noArgs || badArgs)
      returnObject.
      	addProperty("Note", "Either no arguments or the wrong number " +
                    "of arguments were passed in to this service, so " +
                    "default values were used. To POST data to this " +
                    "service, set the HTTP Content-Type header to " +
                    "'application/json' and pass a JSON object " +
                    "containing the fields 'textToClassify', 'contextId', "+
                    "'username', and 'password'.");

    return returnObject;
  }
}