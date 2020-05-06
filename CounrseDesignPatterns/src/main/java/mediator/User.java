package mediator;

/**
 * Created by cloud on 2019/12/20.
 */
public class User {

    String name;

    ChatRoom chatRoom;

    public User(String name) {
        this.name = name;
    }

    public void loginChatRoom(ChatRoom chatRoom){
        chatRoom.connect(this);
        this.chatRoom = chatRoom;
    }

    public void talkTo(User toUser,String msg){
        chatRoom.showMessage(this,toUser,msg);
    }

}
