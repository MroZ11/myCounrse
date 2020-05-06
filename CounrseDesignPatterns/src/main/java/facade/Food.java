package facade;

/**
 * Created by cloud on 2019/12/20.
 */
public class Food {
    String name;
    Foodstuff foodstuff;

    public Food(String name,  Foodstuff foodstuff) {
        this.name = name;
        this.foodstuff = foodstuff;
    }
}
