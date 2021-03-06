jar@http://repo1.maven.org/maven2/com/cloudant/cloudant-client/2.5.0/cloudant-client-2.5.0.jar;http://repo1.maven.org/maven2/com/cloudant/cloudant-http/2.5.0/cloudant-http-2.5.0.jar  

import java.net.MalformedURLException;
import java.net.URL;

import com.cloudant.client.api.ClientBuilder;
import com.cloudant.client.api.CloudantClient;
import com.cloudant.client.org.lightcouch.PreconditionFailedException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;  

public class CreateDB { 	 	
  	  	 
    public String testparams = "{\"dbname\":\"person_MYNAME\"," //Replace MYNAME with your name to make it unique 	 		
            + "\"username\":\"\"," 	 		
            + "\"password\":\"\"," 	 		
            + "\"proxy\":\"http://cloudant-proxy.mybluemix.net/\"}"; 	  	 
    
    public static void main(String[] args) { 	   
        CreateDB hello = new CreateDB(); 	   
        System.out.println(main(new JsonParser().parse(hello.testparams).getAsJsonObject()));     
    } 	  	 
    
    public static JsonObject main(JsonObject args) { 	   
        
        JsonObject output = new JsonObject(); 		     	 
        String username = args.getAsJsonPrimitive("username").getAsString();   	 
        String password = args.getAsJsonPrimitive("password").getAsString();
        String url = args.getAsJsonPrimitive("proxy").getAsString().isEmpty()?("https://" + username + ".cloudant.com"):args.getAsJsonPrimitive("proxy").getAsString();
        try { 			 
            CloudantClient client = ClientBuilder.url(new URL(url)) 	        
            .username(username) 	        
            .password(password) 	        
            .build();; 	  	 				
            
            client.createDB(args.getAsJsonPrimitive("dbname").getAsString()); 				
            output.addProperty("result", "Success created database."); 		 
            
        } catch(PreconditionFailedException ex) { 				
            output.addProperty("err", ex.getReason()); 		          
        } catch (MalformedURLException e) { 		
            e.printStackTrace(); 		
        } 
        
        return output; 	 
    } 	
}