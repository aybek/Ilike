import models.Wall;
import models.utils.WallUtils;
import play.Application;
import play.GlobalSettings;
import play.libs.Akka;
import workers.akka.Master;

import java.util.List;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application application) {
        System.out.println("Global executed");

        List<Wall> walls = WallUtils.getAll();

        for(Wall w:walls){
            Akka.system().actorOf(Master.mkProps(w.wallId,""+w.name),w.name);
        }

        for (int i=1;i<=10;i++){
            Akka.system().actorOf(Master.mkProps(48760195,i+""),i+"");
        }


    }

}