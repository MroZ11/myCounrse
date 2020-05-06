package mediator;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by cloud on 2019/12/20.
 */
public class ChatRoom {
    String roomId;

    Set<User> userSet = new LinkedHashSet<>();

    public ChatRoom(String roomId) {
        this.roomId = roomId;
    }

    public void connect(User user) {
        System.out.println(String.format(" %s 登陆", user.name));
        userSet.add(user);
    }

    public void showMessage(User fromUser, User toUser, String message) {
        if(userSet.contains(toUser)){
            System.out.println(String.format(" %s 对 %s 说 %s", fromUser.name, toUser.name, message));
        }else{
            System.out.println(String.format(" %s 对 %s 说 %s [发送失败,用户不在线]", fromUser.name, toUser.name, message));
        }
    }

}
