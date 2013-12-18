package workers.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import workers.akka.messages.*;

/**
 * author aybek.bukabayev
 */
public class Master extends UntypedActor {
    private int likes = 0;
    private int clientId;
    private String name;

    @Override
    public void preStart(){
        System.out.println("Master preStart");
        final ActorRef child = getContext().actorOf(Child.mkProps("child"));
        child.tell(new ConfigMessage("Start requests",this.clientId),getSelf());
    }

    public static Props mkProps(int clientId,String name) {
        return Props.create(Master.class, clientId, name);
    }

    public Master(int clientId,String name){
        this.clientId = clientId;
        this.name = name;
        System.out.println("Master constructor executed "+name);
    }


    @Override
    public void postStop() throws Exception {
        super.postStop();
        System.out.println("Master Killed");
    }

    @Override
    public void onReceive(Object message) throws Exception {
       if (message instanceof StatusUpdate) {
            System.out.println("Got Status Update "+ this.name);
            likes = ((StatusUpdate) message).getLikes();
            System.out.println(likes);
        } else
       if (message instanceof StatusMessage) {
            getSender().tell(new ResultMessage("" + likes), getSelf());
        }
       else
       if (message instanceof DeathMessage) {
               //kill this actor, we're done!
               //could also call stop...
               this.getSelf().tell(akka.actor.PoisonPill.getInstance(),getSelf());
       }else {
            System.out.println("unhandled message"+message.toString());
            unhandled(message);
        }
    }
}
