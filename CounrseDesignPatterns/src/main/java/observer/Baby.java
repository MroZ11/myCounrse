package observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cloud on 2019/12/23.
 */
public class Baby {

    public String name;

    List<Guardian> guardianList = new ArrayList<>();

    public Baby(String name) {
        this.name = name;
    }

    public Baby addGuardian(Guardian guardian){
        this.guardianList.add(guardian);
        return this;
    }

    public void cry(){
        System.out.println(String.format(" baby [%s] is crying ",this.name));
        notifyAllObservers();
    }

    public void notifyAllObservers(){
        for (Guardian guardian : guardianList) {
            guardian.careBaby();
        }
    }



}
