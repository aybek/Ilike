package workers.akka;

import akka.actor.Props;
import akka.actor.UntypedActor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import workers.akka.messages.ConfigMessage;
import workers.akka.messages.StatusUpdate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * author aybek.bukabayev
 */
public class Child extends UntypedActor {

    private int count=100;
    private int offset=0;
    private int postsCount=0;
    private JSONObject json;
    private StringBuffer response;
    private String url;
    private URL obj;
    private HttpURLConnection con;
    private int likesCount;
    private int postsNumber;
    private int clientId;

    @Override
    public void preStart(){
        System.out.println("Child preStart");
    }

    @Override
    public void postStop() throws Exception {
        super.postStop();
        System.out.println("Child Killed");
    }

    public static Props mkProps(String name) {
        return Props.create(Child.class, name);
    }

    final String name;

    public Child(String name){
      this.name = name;
        System.out.println("Created Child Worker");
    }


    @Override
    public void onReceive(Object message) throws Exception {

        if (message instanceof ConfigMessage) {
            System.out.println("Child Doing Work:");
            clientId = ((ConfigMessage) message).getClientId();
            while (true){
                sendGet();
                getSender().tell(new StatusUpdate(likesCount), getSelf());
            }

//            System.out.println("Child Done Work:");
        } else
            unhandled(message);
    }

    public void sendGet() throws IOException, JSONException {

        offset=0;
        postsNumber=1;
        count=100;
        likesCount=0;

        while(offset<postsNumber){

            url = "http://api.vk.com/method/wall.get?owner_id=-"+clientId+"&offset="+offset+"&count="+count+"";
            offset+=count;

            obj = new URL(url);

            con = (HttpURLConnection) obj.openConnection();

            System.out.println(con.getResponseCode());

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            json = new JSONObject(response.toString());
            JSONArray array = (JSONArray) json.get("response");

            postsNumber = array.getInt(0);

            JSONObject jsonInner;
            for (int i=1;i<array.length();i++){
                jsonInner = (JSONObject) array.get(i);
                likesCount+= Integer.parseInt(String.valueOf(((JSONObject) jsonInner.get("likes")).get("count")));
            }

//            System.out.println("OFFSET "+offset+" , likes: "+likesCount);
//            System.out.println(response.toString());

        }
        System.out.println();
        System.out.println(getSender().path()+"TOTAL: "+likesCount);
        //print result
//        System.out.println(response.toString());
    }
}
