package controllers;

import akka.actor.ActorRef;
import play.libs.Akka;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;
import scala.concurrent.Future;
import views.html.index;
import workers.akka.messages.ResultMessage;
import workers.akka.messages.StatusMessage;

import static akka.pattern.Patterns.ask;

public class Application extends Controller {

    public static Result index() {
//        ActorRef actor1 = Akka.system().actorOf(Master.mkProps(48760195),"OMTS1");
//        ActorRef actor2 = Akka.system().actorOf(Master.mkProps(48760195),"OMTS2");
//        for (int i=1;i<=100;i++){
//        uploadProgress("OMTS1");
//        }
        return ok(index.render("Your new application is ready."));
    }

    public static int uploadProgress(String actorName){
        ActorRef actor = Akka.system().actorFor("akka://application/user/"+actorName);
        if(actor.isTerminated())
        {
           return -1;
        }
        else
        {
            AsyncResult res = async(
                    Akka.asPromise(ask(actor, new StatusMessage("Ask status"), 3000)).map(
                            new F.Function<Object, Result>() {
                                public Result apply(Object response) {

                                    if (response instanceof ResultMessage) {

                                        return ok(((ResultMessage) response).getResult());
                                    }
                                    return ok(response.toString());
                                }
                            }
                    )
            );
            System.out.println("UPLOADPROGRESS:");
            System.out.println(res.getWrappedResult().toString());
            Future<Object> g = ask(actor, new StatusMessage("Ask status"), 3000);
            System.out.println(g.value());
         return 1;
        }
    }



}
