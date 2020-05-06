package com.zs.easypoitest;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateRange;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.swing.ClipboardUtil;
import cn.hutool.core.util.*;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.Header;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.ssl.SSLSocketFactoryBuilder;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.zs.easypoitest.basemodel.Human;
import sun.font.FontDesignMetrics;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.RoundingMode;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.Properties;

/**
 * hutool java工具类测试
 * Created by cloud on 2019/02/21.
 */
public class HutoolTest {


    public static void main(String[] args) {

        /*testHttp();*/

        /*testDate();*/

        /*testIo();*/

        /*testArray();*/

        /*testImg();*/

        /*testClipboard();*/

        /*testRuntime();*/

        /*testReflect();*/

        /*testNumber();*/

        /*testRegular();*/

        /*testCrypto();*/


    }


    /**
     * 测试http请求相关
     */
    public static void testHttp() {

        //get请求
        HttpResponse response = HttpRequest.get("http://www.baidu.com/").execute();

        System.out.println(response.getStatus());


        //post json请求
        HttpResponse response2 = HttpRequest.post("http://192.168.0.14:9085/atgas/account/login")
                .body(new JSONObject().put("loginId", 123).put("password", 123).put("validationCode", 123))
                .contentType("application/x-www-form-urlencoded")
                .header(Header.ACCEPT, "application/json") //头请求 json 返回json格式
                .execute();

        System.out.println(response2.getStatus() + response2.body());


        JSONUtil.isJson(response2.body());

        System.out.println(JSONUtil.isJson(response2.body()));

        System.out.println(JSONUtil.parseObj(response2.body()).get("message"));


    }


    /**
     * 时间工具相关
     */
    public static void testDate() {

        TimeInterval timer = DateUtil.timer();//计时器 用来计算代码执行时间等

        String nowStr = DateUtil.now();//获取当前时间字符串
        System.out.println(nowStr);


        Date nowDate = DateUtil.date();//获取当前时间
        System.out.println(nowDate);

        Date parsedDate = DateUtil.parse("2019/02/12 02:15:15");//自动识别日期格式
        System.out.println(DateUtil.beginOfDay(parsedDate));//获取一天开始的时间
        System.out.println(DateUtil.format(parsedDate, "yyyy MM dd HH:mm:ss"));//时间字符串转换

        System.out.println(DateUtil.ageOfNow(DateUtil.parse("1992-11-18")));//获取年龄

        DateRange dateRange = DateUtil.range(parsedDate, nowDate, DateField.DAY_OF_YEAR);//时间范围用于迭代遍历
        dateRange.forEach((s) -> {
            System.out.println(s);//返回DateTime类型（hutool定义的继承于java.util.Date）
        });
        System.out.println("本方法执行时间（毫秒）：" + timer.interval());//花费时间
    }


    /**
     * Io工具相关
     */
    public static void testIo() {
        String basePath = "E:\\IdeaProjects\\javaCourse\\CounrseEasyPoi\\src\\main\\resources\\static\\testFile\\";

        File file = FileUtil.file(basePath + "1.txt");
        System.out.println(StrUtil.format("文件名：{} 后缀 {}", FileUtil.getName(file), FileUtil.extName(file)));//获取文件后缀等


        cn.hutool.core.io.file.FileWriter writer = new cn.hutool.core.io.file.FileWriter(file);
        writer.write("这是一段1.txt中的文本");//写内容


        FileUtil.copy(file, FileUtil.newFile(basePath + "new.txt"), true);//文本复制

        //获取输入输出流
        BufferedInputStream in = FileUtil.getInputStream(basePath + "1.txt");
        BufferedOutputStream out = FileUtil.getOutputStream(basePath + "2.txt");

        //流复制
        long copySize = IoUtil.copy(in, out, IoUtil.DEFAULT_BUFFER_SIZE);

        System.out.println("复制大小：" + copySize);

        try (BufferedReader bfRead = IoUtil.getReader(in, CharsetUtil.UTF_8)) {//避免io关闭问题用这种方法
            System.out.println(bfRead.readLine());
        } catch (IOException ex) {

        }

        //读配置文件
        ClassPathResource resource = new ClassPathResource("hutool.properties");
        Properties properties = new Properties();
        try {
            properties.load(resource.getStream());
            System.out.println(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 数组相关
     */
    public static void testArray() {
        int[] intArray = null;
        System.out.println(StrUtil.format("数组不为空吗？{}", ArrayUtil.isNotEmpty(intArray)));

        int[] intArray2 = new int[10];
        for (int i = 0; i < intArray2.length; i++) {
            intArray2[i] = i * 100;
        }
        int[] removedIndexOneArray = ArrayUtil.remove(intArray2, 1);
        System.out.println(StrUtil.format("包含100吗？{}", ArrayUtil.contains(intArray2, 100)));
        System.out.println(StrUtil.format("包含100吗？{}", ArrayUtil.contains(removedIndexOneArray, 100)));

        /*以下是身份证相关*/
        String idCard = "510682199211185411";
        System.out.println(IdcardUtil.getProvinceByIdCard("510682199211185411"));//获取身份

    }

    /**
     * 图片相关
     */
    public static void testImg() {
        TimeInterval timer = DateUtil.timer();//计时器 用来计算代码执行时间等

        String bathPath = "E:\\IdeaProjects\\javaCourse\\CounrseEasyPoi\\src\\main\\resources\\static\\testFile\\";
        String imgPath = bathPath + "1.jpg";
        String pressText = "中国移动";
        float alpha = 0.8f;//透明度 1表示不透明 0 表示全透明

        BufferedImage bufferedImage = ImageUtil.read(URLUtil.url(imgPath));//读取图片
        Font font = new Font("微软雅黑", Font.BOLD, bufferedImage.getHeight() / 15);//字体大小取 1/15 图片高度

        FontMetrics fm = FontDesignMetrics.getMetrics(font);//获取字体度量 用来测量水印长度
        int textWidth = SwingUtilities.computeStringWidth(fm, pressText);//计算长度
        int textHeight = fm.getHeight();//计算高度

        BufferedImage bufferedImageHadText = ImageUtil.pressText(bufferedImage, pressText, Color.white,
                font, bufferedImage.getWidth() / 2 - textWidth / 2,
                bufferedImage.getHeight() / 2 - textHeight / 2, alpha);//pressText不修改原图 而是返回一个修改后的拷贝


        ImageUtil.write(bufferedImageHadText, FileUtil.newFile(bathPath + "2.jpg"));//保存图片

        System.out.println("本方法执行时间（毫秒）：" + timer.interval());//花费时间
    }


    /**
     * 剪切板
     */
    public static void testClipboard() {
        /*Clipboard clipboard =ClipboardUtil.getClipboard();//获取系统剪切板*/
        ClipboardUtil.setStr("21312我是你哥哥");//设置剪切板内容
        System.out.println(ClipboardUtil.getStr());//获取剪切板内容
    }

    /**
     * 命令行处理
     */
    public static void testRuntime() {
        String result = RuntimeUtil.execForStr("ipconfig");
        System.out.println(result);

        result = RuntimeUtil.execForStr("ping 192.168.0.14");
        System.out.println(result);
    }

    /**
     * 反射
     */
    public static void testReflect() {

        //获取单个方法
        Method method = ReflectUtil.getMethod(Human.class, "eat", String.class);
        Human human = new Human();
        human.setName("jack");


        //原生调用
        try {
            method.invoke(human, "apple");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        //直接调用
        ReflectUtil.invoke(human, "eat", "apple");


        for (Method methodOne : ReflectUtil.getMethods(Human.class)) {
            //获取所有方法
            System.out.println(methodOne.getName());
        }
    }

    /**
     * 数字
     */
    private static void testNumber() {
        System.out.println("精度问题计算0.05+0.01结果：" + (0.05 + 0.01));

        //NumberUtil封装BigDecimal 解决
        System.out.println("[采用NumberUtil方式]" + NumberUtil.add(0.05, 0.01));

        //小数处理 四舍五入保留两位小数
        System.out.println(NumberUtil.round(NumberUtil.div(1, 3), 2, RoundingMode.HALF_UP));

        System.out.println(NumberUtil.roundHalfEven(NumberUtil.div(1, 3), 2));

        //生成随机数数组 伪随机
        for (int i : NumberUtil.generateRandomNumber(0, 100, 10)) {
            System.out.print(i + " ");
        }


        //生成随机数 封装自Random 伪随机
        System.out.println("\n" + RandomUtil.randomString(10));

        //生成uuid 已废弃
        System.out.println(RandomUtil.randomUUID());
        //使用IdUtil
        System.out.println(IdUtil.randomUUID());
        //不带横线
        System.out.println(IdUtil.simpleUUID());

    }

    /**
     * 正则
     */
    public static void testRegular() {
        //匹配非负整数
        System.out.println(ReUtil.isMatch("^[1-9]\\d*|0$", "0"));

        //匹配正整数
        System.out.println(ReUtil.isMatch("^[1-9]\\d*$", "0"));

        //匹配验证也可以用Validator
        System.out.println(Validator.isMactchRegex("^[1-9]\\d*$", "0"));
    }

    /**
     * 加密
     */
    public static void testCrypto() {
        String password = "000000";

        System.out.println(StrUtil.format("MD5加密后{}", SecureUtil.md5(password)));

    }

    /**
     * https双向验证
     *
     * @throws Exception
     */
    public static void testHttpsP12() {

        //读取P12文件 P12Path填写P12文件路径，p12PWD填写P12文件密码
        String p12Path = "p12Path";
        String p12Pwd = "p12Pwd";

        File file = new File(p12Path);

        try (FileInputStream inputStream = new FileInputStream(file)) {
            //读取秘钥 　JKS和JCEKS是Java密钥库(KeyStore)的两种比较常见类型5种，JKS, JCEKS, PKCS12, BKS，UBER)。
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(inputStream, p12Pwd.toCharArray());

            //秘钥管理工厂用来生成 KeyManagers
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            //初始化（第二个参数为秘钥文件的密码）
            keyManagerFactory.init(keyStore, p12Pwd.toCharArray());
            //信任所有证书
            X509TrustManager tm = new X509TrustManager() {
                //信任处理 这里可以用来验证目标路径的证书等
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };

            /**
             * KeyManager 负责管理用于验证到同位体的本地 SSLSocket 的密钥内容。如果没有密钥内容可以使用，则套接字将不能提供身份验证凭据。
             *通过使用 KeyManagerFactory，或实现 KeyManager 子类之一来创建 KeyManager。
             */
            //配置SSL连接工厂
            SSLSocketFactory sslSocketFactory = SSLSocketFactoryBuilder.create().
                    setKeyManagers(keyManagerFactory.getKeyManagers())
                    .setTrustManagers(tm)
                    .build();

            HttpUtil.createPost("").setSSLSocketFactory(sslSocketFactory).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
