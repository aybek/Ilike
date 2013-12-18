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

    @Override
    public void preStart(){
        System.out.println("Master preStart");
        final ActorRef child = getContext().actorOf(Child.mkProps("child"));
        child.tell(new ConfigMessage("Start requests",this.clientId),getSelf());
    }

    public static Props mkProps(int clientId) {
        return Props.create(Master.class, clientId);
    }

    public Master(int clientId){
        this.clientId = clientId;
        System.out.println("Master constructor executed");
    }


    @Override
    public void postStop() throws Exception {
        super.postStop();
        System.out.println("Master Killed");
    }

    @Override
    public void onReceive(Object message) throws Exception {
       if (message instanceof StatusUpdate) {
            System.out.println("Got Status Update");
            likes = ((StatusUpdate) message).getLikes();

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
