package mediator;

/**
 * 中介者模式（Mediator Pattern）是用来降低多个对象和类之间的通信复杂性。
 * 这种模式提供了一个中介类，该类通常处理不同类之间的通信，并支持松耦合，使代码易于维护。中介者模式属于行为型模式。
 * <p>
 * 这里的聊天室即中介者，用户只需要存在一个聊天室的引用，即可和聊天室内的所有用户沟通，而不需要存每一个用户的引用
 */
public class Test {

    public static void main(String[] args) {
        ChatRoom chatRoom = new ChatRoom("qqq");
        User tom = new User("tom");
        User jack = new User("jack");
        tom.loginChatRoom(chatRoom);
        tom.talkTo(jack, "I love U");
    }

}
