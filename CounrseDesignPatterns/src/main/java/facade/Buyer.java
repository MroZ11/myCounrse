package facade;

/**
 * Created by cloud on 2019/12/20.
 */
public class Buyer {

    public Foodstuff buyFoodstuff(String foodstuffName){
        System.out.println("采购去购买材料————>"+foodstuffName);
        Foodstuff foodstuff = new Foodstuff(foodstuffName);
        return foodstuff;
    }

}
