package myc.demo3;

//@Calculate.java
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * @��˵����
 * ���ʽ���㾲̬�����࣬�������ʽ���㡢���ʽ����
 * ���ʽԤ����������̬�����������ʽ���󷵻ذ�������
 * ˵�����ַ��������򷵻ؼ��������ַ�����ʽ��
 *
 * @��ṹ��
 * public class Calculate {
 * 	public static String calc(String str) {}		//���ʽ���㣨���ø���������
 * 	private static boolean checkString(String str) {}	//���ʽ��飨�������ƥ�䣩
 * 	private static String Preprocess(String str) {}		//���ʽԤ��������sqrt��%��
 * 	private static String getcalc(String str) {}		//���ȱ��ʽ���㣨��׺ת��׺��
 * }
 */
public class Calculate {
    /**
     * ���ʽ���㷽�������ȵ��þ�������Ԥ���������г�������
     * ��ͨ��ջ������׺���ʽת��׺���ʽ����󷵻ؼ��������ַ�
     * �����Ƿ񷵻�С��λ��ȡ����С�������Ƿ�Ϊ0��
     */
    public static String calc(String str) {
        if (checkString(str) == false) { //�������ƥ��
            return "Bad str";
        }

        str = Preprocess(str);
        if(str.equals("Bad sqrt")||str.equals("Devide 0")) {	//��鿪ƽ�����Ƿ�Ϊ��
            return str;
        }

        str = getcalc(str);		//���м��㣨��ʱ�����������������С���ţ�
        if(str.equals("Bad str")||str.equals("Devide 0")) {
            return str;
        }

        java.text.DecimalFormat format = new java.text.DecimalFormat("#,###.######");//��ֵ��ʽ��

        return format.format(Double.parseDouble(str));
    }

    /**
     * ���ʽ��������Ŀǰ���ܽ����ڼ������С���ŵ�ƥ�䣬
     * ����׼���������ľ����ܣ������������������ƥ�䡣
     */
    private static boolean checkString(String str) {
        // �����������ƥ��
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
     * ���ʽԤ��������������ʽ�е�һԪ�������
     * ���֣�sqrt��%�������������������Ա���㡣
     */
    private static String Preprocess(String str) {
        for (int i = 0; i < str.length()-1; i++) {
            if (str.charAt(i) == '(' && str.charAt(i + 1) == '-') { //����(-��ת��Ϊ��(0-��
                str = str.substring(0, i + 1) + "0" + str.substring(i + 1, str.length());
            }
        }

        for (int i = str.length()-1; i>=0; i--) {//�Ӻ���ǰ��������֧��sqrt�Ķ��Ƕ��
            if (str.charAt(i) == 's') { //���㿪ƽ��
                int cnt = 1;
                for (int j = i + 5; j < str.length(); j++) {
                    if(str.charAt(j)=='(') {
                        cnt++;
                    }
                    else if(str.charAt(j)==')') {
                        cnt--;
                    }
                    if (cnt==0) {//�������Ŵﵽƥ�䣬���ɻ�ø����µ��������ʽ
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


        for (int i = 0; i<str.length(); i++) {//�Ӻ���ǰ��������֧��sqrt�Ķ��Ƕ��
            if (str.charAt(i) == '%') { //����ٷֱ�
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
         * ����׺���ʽת��Ϊ��׺���ʽ�������������������֮��
         * ��ӷָ�����$���������к�׺���ʽ�ļ��㡣��׺���ʽ
         * ������str�У���׺���ʽ������s�С�
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
         * ���к�׺���ʽ�ļ���
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
