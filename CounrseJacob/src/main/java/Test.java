import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

/**
 * Created by cloud on 2020/11/5.
 */
public class Test {

    public static void main(String[] args) {
        try {
            String wordFile = "C:\\Users\\cloud\\Desktop\\下半年考核\\2.doc";
            ActiveXComponent app = new ActiveXComponent("Word.Application");
            app.setProperty("Visible", true);
            Dispatch documents = app.getProperty("Documents").toDispatch();
            System.out.println("打开文件: " + wordFile);
            // 打开文档
            Dispatch document = Dispatch.call(documents, "Open").toDispatch();
            System.out.println("打开文件: "+Dispatch.get(document,"FullName"));
        } catch (Exception e) {
            e.printStackTrace();

        } finally {

        }


    }
}
