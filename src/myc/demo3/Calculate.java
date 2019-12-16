package myc.demo3;

//@Calculate.java
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * @类说明：
 * 表达式计算静态方法类，包括表达式计算、表达式纠错、
 * 表达式预处理三个静态方法。若表达式有误返回包含错误
 * 说明的字符串，否则返回计算结果的字符串形式。
 *
 * @类结构：
 * public class Calculate {
 * 	public static String calc(String str) {}		//表达式计算（调用各个方法）
 * 	private static boolean checkString(String str) {}	//表达式检查（检查括号匹配）
 * 	private static String Preprocess(String str) {}		//表达式预处理（计算sqrt及%）
 * 	private static String getcalc(String str) {}		//初等表达式计算（中缀转后缀）
 * }
 */
public class Calculate {
    /**
     * 表达式计算方法，首先调用纠错方法及预处理方法进行初步处理，
     * 再通过栈进行中缀表达式转后缀表达式，最后返回计算结果的字符
     * 串，是否返回小数位具取决于小数部分是否为0。
     */
    public static String calc(String str) {
        if (checkString(str) == false) { //检查括号匹配
            return "Bad str";
        }

        str = Preprocess(str);
        if(str.equals("Bad sqrt")||str.equals("Devide 0")) {	//检查开平方数是否为正
            return str;
        }

        str = getcalc(str);		//进行计算（此时仅包含基本运算符和小括号）
        if(str.equals("Bad str")||str.equals("Devide 0")) {
            return str;
        }

        java.text.DecimalFormat format = new java.text.DecimalFormat("#,###.######");//数值格式化

        return format.format(Double.parseDouble(str));
    }

    /**
     * 表达式纠错方法，目前功能仅限于检查左右小括号的匹配，
     * 后续准备加入更多的纠错功能，如运算符与运算数的匹配。
     */
    private static boolean checkString(String str) {
        // 检查括号括号匹配
        Stack<Character> sta = new Stack<Character>();
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '(') {
                sta.push(str.charAt(i));
            } else if (str.charAt(i) == ')') {
                if (sta.empty() || sta.peek() != '(') {
                    return false;
                } else {
                    sta.pop();
                }
            }
        }
        if (sta.size() > 0)
            return false;
        return true;
    }

    /**
     * 表达式预处理方法，计算表达式中的一元运算符的
     * 部分（sqrt及%）。并将负号做处理以便计算。
     */
    private static String Preprocess(String str) {
        for (int i = 0; i < str.length()-1; i++) {
            if (str.charAt(i) == '(' && str.charAt(i + 1) == '-') { //将“(-”转化为“(0-”
                str = str.substring(0, i + 1) + "0" + str.substring(i + 1, str.length());
            }
        }

        for (int i = str.length()-1; i>=0; i--) {//从后向前遍历，以支持sqrt的多层嵌套
            if (str.charAt(i) == 's') { //计算开平方
                int cnt = 1;
                for (int j = i + 5; j < str.length(); j++) {
                    if(str.charAt(j)=='(') {
                        cnt++;
                    }
                    else if(str.charAt(j)==')') {
                        cnt--;
                    }
                    if (cnt==0) {//左右括号达到匹配，即可获得根号下的完整表达式
                        String s = getcalc(str.substring(i + 5, j));
                        if(s.equals("Devide 0")) {
                            return s;
                        }
                        double sqrt = Double.parseDouble(s);
                        if(sqrt<0) {
                            return "Bad sqrt";
                        }
                        str = str.substring(0, i)
                                + String.valueOf(Math.sqrt(sqrt))
                                + str.substring(j + 1, str.length());
                        break;
                    }
                }
            }
        }


        for (int i = 0; i<str.length(); i++) {//从后向前遍历，以支持sqrt的多层嵌套
            if (str.charAt(i) == '%') { //计算百分比
                for (int j = i - 1; j >= 0; j--) {
                    if (j == 0) {
                        if (i < str.length() - 1) {
                            str = String.valueOf(Double.parseDouble(str.substring(0, i)) / 100)
                                    + str.substring(i + 1, str.length());
                        } else {
                            str = String.valueOf(Double.parseDouble(str.substring(0, i)) / 100);
                        }
                        break;
                    }
                    char t = str.charAt(j);
                    if ((t > '9' || t < '0') && t != '.') {
                        if (i < str.length() - 1) {
                            str = str.substring(0, j + 1)
                                    + String.valueOf(Double.parseDouble(str.substring(j + 1, i)) / 100)
                                    + str.substring(i + 1, str.length());
                        } else {
                            str = str.substring(0, j + 1)
                                    + String.valueOf(Double.parseDouble(str.substring(j + 1, i)) / 100);
                        }
                        break;
                    }
                }
            }
        }

        return str;
    }

    private static String getcalc(String str) {
        if (str.isEmpty()) {
            return str;
        }
        /**
         * 将中缀表达式转换为后缀表达式，并在运算数与运算符之间
         * 添加分隔符‘$’，最后进行后缀表达式的计算。中缀表达式
         * 储存在str中，后缀表达式储存在s中。
         */
        Stack<Character> sta = new Stack<Character>();
        String s = "$";
        int i = 0;
        if (str.charAt(0) == '(') {
            sta.push('(');
            i++;
        }
        boolean flag = false;
        for (; i < str.length(); i++) {
            if (str.charAt(i) == '+' && (i == 0 || (str.charAt(i - 1) < '0' && str.charAt(i - 1) != ')')))
                i++;
            if (str.charAt(i) == '-') {
                flag = true;
                i++;
            }
            while (i < (int) str.length() && ((str.charAt(i) >= '0' && str.charAt(i) <= '9') || str.charAt(i) == '.' || str.charAt(i)=='E')) {
                if (flag) {
                    s += "-$";
                    flag = false;
                }
                s += str.charAt(i);
                i++;
            }
            s += "$";
            if (i < (int) str.length()) {
                if (sta.empty() || str.charAt(i) == '(')
                    sta.push(str.charAt(i));
                else if (str.charAt(i) == '+' || str.charAt(i) == '-') {
                    while (sta.size() > 0 && sta.peek() != '(') {
                        s += sta.pop() + "$";
                    }
                    sta.push(str.charAt(i));
                } else if (str.charAt(i) == '*' || str.charAt(i) == '/') {
                    while (sta.size() > 0 && (sta.peek() == '*' || sta.peek() == '/')) {
                        s += sta.pop() + "$";
                    }
                    sta.push(str.charAt(i));
                } else {
                    while (sta.size() > 0 && sta.peek() != '(') {
                        s += sta.pop() + "$";
                    }
                    sta.pop();
                }
            }
        }

        while (sta.size() > 0) {
            s += sta.pop() + "$";
        }

        /**
         * 进行后缀表达式的计算
         */
        Stack<Double> stack = new Stack<Double>();
        StringTokenizer token = new StringTokenizer(s, "$", false);
        while (token.hasMoreTokens()) {
            String t = token.nextToken();
            if((t.equals("+")||t.equals("-")||t.equals("*")||t.equals("/"))&&stack.size()<2) {
                return "Bad str";
            }
            if (t.equals("+")) {
                stack.push(stack.pop() + stack.pop());
            } else if (t.equals("-")) {
                double a = stack.pop();
                stack.push(stack.pop() - a);
            } else if (t.equals("*")) {
                stack.push(stack.pop() * stack.pop());
            } else if (t.equals("/")) {
                double a = stack.pop();
                if(Math.abs(a-0)<1E-6) {
                    return "Devide 0";
                }
                stack.push(stack.pop() / a);
            } else {
                stack.push(Double.parseDouble(t));
            }
        }

        double c = stack.pop();
        if(sta.size()>0) {
            return "Bad Str";
        }

        return String.valueOf(c);
    }

}
