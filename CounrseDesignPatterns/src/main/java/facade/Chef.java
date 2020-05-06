package facade;

/**
 * Created by cloud on 2019/12/20.
 */
public class Chef {

    public Food cookFood(String foodName, Foodstuff foodstuff) {
        Food food = new Food(foodName, foodstuff);
        System.out.println("厨师烹饪————>" + foodName);
        return food;
    }

    public String orderFoodStuff(String foodName) {
        String foodStuffName = foodName + "原料";

        System.out.println("厨师需要原料————>" + foodStuffName);
        return foodStuffName;
    }

}
