package com.ljm.verification.util;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RandomValidateCode {
    private static int width = 135;// 图片宽
    private static int height = 28;// 图片高
    private static int lineSize = 40;// 干扰线数量
    private static Random random = new Random();

    /**
     * 设置验证码字体
     *
     * @return 字体
     */
    private static Font getFont() {
        return new Font("Fixedsys", Font.CENTER_BASELINE, 18);
    }

    /**
     * 设置验证码颜色
     *
     * @param fc
     * @param bc
     * @return rgb值
     */
    private static Color getRandColor(int fc, int bc) {
        if (fc > 255)
            fc = 255;
        if (bc > 255)
            bc = 255;
        int r = fc + random.nextInt(bc - fc - 16);
        int g = fc + random.nextInt(bc - fc - 14);
        int b = fc + random.nextInt(bc - fc - 18);
        return new Color(r, g, b);
    }

    /**
     * 生成随机验证码，绘制成图片，并且写入结果
     *
     * @param request
     * @param response
     */
    public static void getRandcode(HttpServletRequest request,
                                   HttpServletResponse response) {
        HttpSession session = request.getSession();
        // BufferedImage类是具有缓冲区的Image类,Image类是用于描述图像信息的类
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics g = image.getGraphics();// 产生Image对象的Graphics对象,改对象可以在图像上进行各种绘制操作
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ROMAN_BASELINE, 45)); //图片上字的大小
        g.setColor(getRandColor(110, 133));
        // 绘制干扰线
        for (int i = 0; i <= lineSize; i++) {
            drowLine(g);
        }
        // 绘制随机验证吗
        String randomString = getValidate();
        //获得生成的验证码，然后跟用户输入的进行比较
        session.setAttribute("verification", result);
        session.setMaxInactiveInterval(30);
        String[] arr = randomString.toString().split("");
        for (int i = 1; i <= arr.length; i++) {
            drowString(g, arr[i - 1], i);
        }
        g.dispose();
        try {
            // 将内存中的图片通过流动形式输出到客户端
            ImageIO.write(image, "JPEG", response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 绘制图片
     */
    private static String drowString(Graphics g, String randomString, int i) {
        int j = 15;
        if (result instanceof Integer) {
            j = 11;
        }
        g.setFont(getFont());
        g.setColor(new Color(random.nextInt(101), random.nextInt(111), random
                .nextInt(121)));
        g.translate(random.nextInt(3), random.nextInt(3));
        g.translate(random.nextInt(3), random.nextInt(3));
        g.drawString(randomString, j * i, 12);
        return randomString;
    }

    /**
     * 绘制干扰线
     */
    private static void drowLine(Graphics g) {
        int x = random.nextInt(width);
        int y = random.nextInt(height);
        int xl = random.nextInt(13);
        int yl = random.nextInt(15);
        g.drawLine(x, y, x + xl, y + yl);
    }

    public static Object result;

    /**
     * 生成字母验证码
     * @return 验证码的内容
     */
    public static String createAB() {
        String[] abc = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "q", "r", "s", "t", "u", "v", "o", "p", "w", "x", "z", "y", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "Q", "R", "S", "T", "U", "V", "O", "P", "W", "X", "Y", "Z"};
        Random rd = new Random();
        int index = rd.nextInt(abc.length);
        String one = abc[index];
        index = rd.nextInt(abc.length);
        String two = abc[index];
        index = rd.nextInt(abc.length);
        String three = abc[index];
        index = rd.nextInt(abc.length);
        String four = abc[index];
        result = one + two + three + four;
        String str = one + two + three + four;
        return str;
    }

    /**
     * 生成加减乘除算法验证码
     * 由于2位数的 乘除对某些人算起来麻烦
     * 有兴趣的可以扩展乘法和除法
     * @return 验证码的内容
     */
    public static String createNumber() {
        String fu[] = new String[]{"+", "-", "*", "/"};
        Random rd = new Random();
        int i = rd.nextInt(2);
        int num1 = rd.nextInt(100);
        int num2 = rd.nextInt(100);
        if (i == 0) {
            result = num1 + num2;
        } else {
            //循环保证永远不会出现减法结果是负数
            while (num1 < num2) {
                num1 = rd.nextInt(100);
            }
            result = num1 - num2;
        }
        String str = num1 + fu[i] + num2 + "=";
        return str;
    }

    /**
     * 生产汉字验证吗
     * @return 验证码的内容
     */
    public static String createChinese() {
        String chinese[] = new String[]{
                "勿", "忘", "初", "心", "方", "得", "始", "终", "认", "真", "切", "怂", "从", "一", "而", "终", "英", "雄", "联", "盟"
        };
        Random rd = new Random();
        int index = rd.nextInt(chinese.length);
        String one = chinese[index];
        index = rd.nextInt(chinese.length);
        String two = chinese[index];
        index = rd.nextInt(chinese.length);
        String three = chinese[index];
        index = rd.nextInt(chinese.length);
        String four = chinese[index];
        result = one + two + three + four;
        String str = one + two + three + four;
        return str;
    }

    /**
     * 随机调用验证码
     */
    public static String getValidate() {
        Random rd = new Random();
        int i = rd.nextInt(4);
        if (i == 1) {
            return createAB();
        } else if (i == 2) {
            return createChinese();
        }
        return createNumber();
    }
}
