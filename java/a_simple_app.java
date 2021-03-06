import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class a_simple_app {

	public String testparams = "{\"Name\":\"John Doe\","
			+ "\"Email\":\"jdoe@xyz.com\","
			+ "\"Comment\":\"Hello World\"}";
	
	public static void main(String[] args) {
		a_simple_app hello = new a_simple_app();
        System.out.println(main(new JsonParser().parse(hello.testparams).getAsJsonObject()));
	}
	
	public static JsonObject main(JsonObject args) {
		JsonObject response = new JsonObject();
		
		JsonObject output = new JsonObject();
		output.addProperty("Name", args.getAsJsonPrimitive("Name").getAsString());
		output.addProperty("Email", args.getAsJsonPrimitive("Email").getAsString());
		output.addProperty("Comment", args.getAsJsonPrimitive("Comment").getAsString());
		
		response.add("output", output);
        
    return response;
	}
	
}
