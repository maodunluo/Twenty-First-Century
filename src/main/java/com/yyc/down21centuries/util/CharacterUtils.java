package com.yyc.down21centuries.util;

import java.util.Scanner;

/**
 * @author yuechao
 */
public class CharacterUtils {

    private CharacterUtils() {
    }

    /**
     * 返回字符串是否全是数字
     *
     * @param s 字符串
     * @return 是否是数字
     */
    public static boolean numeric(String s) {
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获取输入的数字
     *
     * @return 输入的数字
     */
    public static int getInputNumber() {
        Scanner in = new Scanner(System.in);
        String s = "1";
        int number = Integer.parseInt(s);
        if (in.hasNextLine()) {
            s = in.nextLine();
        }
        if (CharacterUtils.numeric(s)) {
            number = Integer.parseInt(s);
        }
        return number;
    }
}
