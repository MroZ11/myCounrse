package facade;

/**
 * 门面（Facade）
 * <p>
 * 假设 我需要做菜 但是我不会做 也不会买菜，这时候我要找人帮忙 就必须分别找到厨师和采购。
 * 假设有门面 即这里的餐厅 我只需要去找餐厅，我只需要告诉餐厅我要做的菜即可。
 * <p>
 * 与代理模式的区别：
 * <p>
 * 这个模式乍一看还真的很像代理模式，其实还是有很大的区别的。
 * 比如说他们都引入了中介，起到了代理的功能。但是代理模式只代理一个类，而且代理类与原类实现相同的抽象。门面类就不一样了，他代理的是一系列类，与子系统可以有不同的抽象
 * <p>
 * https://baijiahao.baidu.com/s?id=1636109114016642554&wfr=spider&for=pc
 */
public class Restaurant {

    Buyer buyer = new Buyer();
    Chef chef = new Chef();

    public static void main(String[] args) {
        Restaurant restaurant = new Restaurant();
        restaurant.getFood("回锅肉");
    }

    public Food getFood(String foodName) {
        String foodstuffName = chef.orderFoodStuff(foodName);
        return chef.cookFood(foodName, buyer.buyFoodstuff(foodstuffName));
    }

}
