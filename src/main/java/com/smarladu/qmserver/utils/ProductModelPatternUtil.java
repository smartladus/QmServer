package com.smarladu.qmserver.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProductModelPatternUtil {

    public static boolean isModelMatch(String modelPattern, String model) {
        List<String> regexList = parsePattern(modelPattern);
        for (String regex : regexList) {
//            System.out.println("checking: " + regex);
            if (Pattern.matches(regex, model)) return true;
        }
        return false;
    }

    private static List<String> parsePattern (String modelPattern) {
        String[] patterns = modelPattern.split("\\|");
        List<String> regexList = new ArrayList<>();
        for (String str : patterns) {
            regexList.add(createRegex(str));
        }
        return regexList;
    }

    private static String createRegex(String model) {
        StringBuilder res = new StringBuilder(model);
        Matcher m = Pattern.compile("\\*+").matcher(model);
        while (m.find()) {
            res.replace(m.start(), m.end() , "[0-9A-Za-z\\s]{0," + (m.end()-m.start()) + "}");
        }
        return res.toString();
    }
}
