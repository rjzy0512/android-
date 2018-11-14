package com.example.car43;

import java.util.ArrayList;
import java.util.List;

public class Zfcjs {
    private static BjypcStack ms1 = new BjypcStack();
    private static BjypcStack ms2 = new BjypcStack();

    public static String cl(String s){
		String s1="",s2,s3,bl1,bl2;
		int An;
		An=s.indexOf(" ");
		s1=s.substring(2, An);
		bl1=s.substring(An+1,An+2);
		s=s.substring(An+1, s.length());
		An=s.indexOf(" ");
		bl2=s.substring(An+1,+An+2);
		s2=s.substring(2,An);
		s3=s.substring(An+1+2, s.length());
		
		An=s1.indexOf(bl1);
		s1=s1.substring(0,An)+"("+s2+")"+s1.substring(An+1,s1.length());
		
		An=s1.indexOf(bl2);
		s1=s1.substring(0,An)+"("+s3+")"+s1.substring(An+1,s1.length());

    	return s1;
    }
    
    public static List<String> zb(String s) {
        List<String> ls = new ArrayList<String>();
        int i = 0;
        String str;
        char c;
        do {
            if ((c = s.charAt(i)) < 48 || (c = s.charAt(i)) > 57) {
                ls.add("" + c);
                i++;
            } else {
                str = "";
                while (i < s.length() && (c = s.charAt(i)) >= 48
                        && (c = s.charAt(i)) <= 57) {
                    str += c;
                    i++;
                }
                ls.add(str);
            }

        } while (i < s.length());
        return ls;
    }

    public static List<String> parse(List<String> ls) {
        List<String> lss = new ArrayList<String>();
        for (String ss : ls) {
            if (ss.matches("\\d+")) {
                lss.add(ss);
            } else if (ss.equals("(")) {
                ms1.push(ss);
            } else if (ss.equals(")")) {

                while (!ms1.top.equals("(")) {
                    lss.add(ms1.pop());
                }
                ms1.pop();
            } else {
                while (ms1.size() != 0 && getValue(ms1.top) >= getValue(ss)) {
                    lss.add(ms1.pop());
                }
                ms1.push(ss);
            }
        }
        while (ms1.size() != 0) {
            lss.add(ms1.pop());
        }
        return lss;
    }
  
    public static int jisuan(List<String> ls) {
        for (String s : ls) {
            if (s.matches("\\d+")) {
                ms2.push(s);
            } else {
                int b = Integer.parseInt(ms2.pop());
                int a = Integer.parseInt(ms2.pop());
                if (s.equals("+")) {
                    a = a + b;
                } else if (s.equals("-")) {
                    a = a - b;
                } else if (s.equals("*")) {
                    a = a * b;
                } else if (s.equals("/")) {
                    a = a / b;
                }else if (s.equals("%")) {
                    a = a % b;
                }else if(s.equals("^"))
                {                
                    a = Integer.parseInt(""+Math.pow(a, b));  
                }
                ms2.push("" + a);
            }
        }
        return Integer.parseInt(ms2.pop());
    }
    /**
     * 获取运算符优先级
     * +,-为1 *,/为2 ()为0
     */
    public static int getValue(String s) {
        if (s.equals("+")) {
            return 1;
        } else if (s.equals("-")) {
            return 1;
        } else if (s.equals("*")) {
            return 2;
        } else if (s.equals("/")) {
            return 2;
        }else if(s.equals("%"))
        {
        	return 2;
        }
        return 0;
    }


}