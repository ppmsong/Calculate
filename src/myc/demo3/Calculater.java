package myc.demo3;

//@Calculater.java
import java.awt.Font;
import java.awt.Button;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * 2019.10.27更新：
 * 修复bug:
 * 1.添加了除零的判断并给出提示
 * 2.避免了科学记数法产生的‘E’使程序崩溃
 * 3.添加了表达式符号运与算数不匹配（如3-）
 *   的判断（通过栈空和栈剩余的判断）。
 *
 */


/**
 * @版本：V2.0
 * @作者：pms
 * @时间： 2019.10.27
 *
 * @描述:
 * 简易计算器的实现代码，能够计算开方，百分数，倒数
 * 及简单的带有小括号的中缀表达式。
 *
 * @说明:
 * 代码全部为自己所写，由于时间能力不足，代码仍有很
 * 多问题和未实现的功能，今后会逐渐更新和完善。若发
 * 现bug或计算错误的表达式，欢迎私我，一定认真对待。
 *
 * @已实现的功能：
 * 1.简单的表达式计算，包括 + - * /运算符，正负号，
 *   开平方，求倒数，百分数及括号功能，以及退格，清空。
 * 2.输入表达式时自动更新上下两文本框(仿Windows自
 *   带计算器界面)，并将最后的运算结果显示在下方文本
 *   框，将表达式显示在上方文本框。
 * 3.简单的表达式检查功能，能够检查括号不匹配、除以零、
 *   等部分表达式错误。
 * 4.运算符输入限制和辅助：连续输入运算符时，不会连续
 *   向表达式中添加运算符，而是进行运算符更新(将最近
 *   一次输入的运算符更新)。
 * 5.小数点限制：在一个运算数中若输入小数点则小数点按
 *   键暂时失效，以保证每个运算数只有一个小数点；另外，
 *   当按下小数点时，若下方文本框中没有内容或者仅有运
 *   算符，则自动在小数点前添加数字0。
 * 6.数字及小数点输入限制和辅助：输入右括号之后数字键
 *   及小数点键暂时失效，输入运算符之后重新激活。
 * 7.其他输入限制和辅助：无法在表达式为空时输入百分号。
 *   允许输入前导零，计算时自动去除，允许输入多层括号，
 *   将按照优先级进行计算。
 * 8.支持开平方函数（sqrt）的多层嵌套，当开平方的运算
 *   数小于零时将显示表达式错误（Bad str）。
 * 9.根据表达式长度动态改变上下文本框字体大小，以保证
 *   上下文本框内容能在文本框中完整显示。
 * 10.计算结果格式化，自动确定是否输出小数位，并将整数
 *    部分按照四位分级法显示，即每三位数字用一个逗号隔
 *    开。
 *
 *
 *
 * @待实现的功能：
 * 1.更多按键
 * 2.界面的美化
 * 3.表达式检查功能
 * 4.表达式纠错功能
 * 5.键盘的监听功能
 * 6.支持多层开平方嵌套（递归实现）
 * 6.动态改变改变组件大小（跟随窗口）
 * 7.一次计算完成后点击运算符号会将上次运算结果保留
 *   并加入这次的运算表达式中
 * 8.菜单栏的更多功能：进制转换计算器、运算历史记录
 * 9.更多及更强大的输入限制及辅助功能
 * 10.高精度及大数类的使用，以实现更强的计算器功能
 * 11.百分号前可以添加括号括起来的表达式
 * 12.正负号按键功能的优化：当前仅是每次按下正负号
 *    就会添加“(-”，后续将更改为判断正负，若为负
 *    则去掉负号，否则添加负号
 * 13.支持自行设置小数点后显示的位数
 *
 */

/**
 * @类说明：
 * 计算器的界面生成，以及事件监听响应功能。
 *
 * @类结构：
 * public class Calculater extends JFrame {
 * 	public static void main(String[] args){}
 * 	public Calculater(){}					//构造函数，生成主界面
 * 	class btnActionListener implements ActionListener{}	//按键监听事件响应内部类
 * 	class btnKeyListener implements KeyListener{}		//键盘监听（未实现）
 * }
 */

public class Calculater extends JFrame {
    private static final long serialVersionUID = 1L;
    /** @flag
     * 用于标记是否完成了一次运算，每次计算完成之后
     * 即每次点击'='之后其值更新为真，之后再次点击
     * 任何按键将清空文本框，并将其值更新为false。
     *
     ** @point
     * 若正在输入运算数（即非运算符），则point生效，
     * 用于标记当前运算数是否已经输入小数点，以保证
     * 每个运算数只能有一个小数点。
     */
    private boolean flag = false;
    private boolean point = false;
    private JPanel contentPane;	//主窗格
    private JTextField txtUnder;	//上层文本框,用于显示输入的表达式
    private JTextField txtUpper;	//下层文本框,用于显示当前的输入
    private String strUpper = "";	//上层文本
    private String strUnder = "";	//下层文本


    public static void main(String[] args) {
        Calculater frame = new Calculater();
        frame.setVisible(true);
        //frame.setResizable(false);	//不可更改窗口大小
    }

    public Calculater() {
        setTitle("简易计算器-pms");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 416, 620);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        /**
         * 生成文本窗格，窗格分两层。输入计算表达式时表达
         * 式显示在下层。开始时均为空。输入计算表达式时，
         * 上下层文本框实时更新。点击等于号之后下层文本框
         * 显示运算结果，上层显示表达式。再次点击任何按键
         * 重置计算器并显示该键。
         */
        JPanel paneText = new JPanel();		//添加文本窗格
        paneText.setBounds(0, 0, 400, 170);
        contentPane.add(paneText);
        paneText.setLayout(null);

        txtUpper = new JTextField(strUpper);	//添加上方文本框
        txtUpper.setHorizontalAlignment(JTextField.RIGHT);	//设置文本右对齐
        txtUpper.setFont(new Font("Consolas", Font.PLAIN, 30));
        txtUpper.setEditable(false);
        txtUpper.setBounds(0, 0, 400, 65);
        paneText.add(txtUpper);

        txtUnder = new JTextField(strUnder);	//添加下方文本框
        txtUnder.setHorizontalAlignment(JTextField.RIGHT);	//设置文本右对齐
        txtUnder.setFont(new Font("Consolas", Font.PLAIN, 50));
        txtUnder.setEditable(false);
        txtUnder.setBounds(0, 65, 400, 105);
        paneText.add(txtUnder);

        /**
         * 生成按键窗格，采用字符串数组以循环形式生成按键。
         */
        JPanel PaneButton = new JPanel();		//添加按键窗格
        PaneButton.setBounds(0, 170, 400, 412);
        contentPane.add(PaneButton);
        PaneButton.setLayout(new GridLayout(6, 4, 0, 0));

        String[] btn = {"(",")","CE","Del","%","sqrt","1/X","*",	//设置按键文字
                "7","8","9","/","4","5","6","+",
                "1","2","3","-","±","0",".","="};

        for(int i=0;i<btn.length;i++) {			//添加按键
            Button button = new Button(btn[i]);
            button.setFont(new Font("Consolas", Font.PLAIN, 25));
            button.addActionListener(new btnActionListener()); 	//添加监听器
            //button.addKeyListener(new btnKeyListener());
            PaneButton.add(button);
        }


    }

    /**
     * 内部类，实现按键的监听功能，并实时更新上下
     * 两个文本窗格的显示内容。
     */
    class btnActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {

            String cmd = e.getActionCommand();

            /**
             * 检查更新flag，若flag为真，清空上下文本框。
             * @flag
             * 用于标记是否完成了一次运算，每次计算完成之后
             * 即每次点击'='之后其值更新为真，之后再次点击
             * 任何按键将清空文本框，并将其值更新为false。
             */
            if(flag) {
                strUnder = "";
                strUpper = "";
                flag = false;
            }
            /**
             * 检查更新point，（point为真，小数点按键失效）
             * 若point为真且当前响应按键为运算符，则将point
             * 置为假，此时小数点生效。
             * @point
             * 若正在输入运算数（即非运算符），则point生效，
             * 用于标记当前运算数是否已经输入小数点，以保证
             * 每个运算数只能有一个小数点。
             */
            char a = cmd.charAt(0);
            if(point && cmd.length()==1 && (a<'0'||a>'9') && a!='.') {
                point = false;
            }


            //清空键，将上下文本框清空，等待下次输入。
            if(cmd.equals("CE")) {
                strUpper = "";
                strUnder = "";
            }
            //删除键，将下方文本框中的最后一个字符（若存在）
            //删掉，但无法删除上方文本框的字符。
            else if (cmd.equals("Del")) {
                if(strUnder.length()>0) {
                    strUnder = strUnder.substring(0,strUnder.length()-1);
                }
            }
            //基本运算符，将下方文本框的内容移至上方文本框，
            //若下方文本框首字符为左括号，则延迟上移操作至
            //下次输入右括号时（此处待优化，功能将改为当左右
            //括号数量匹配时再进行上移操作）
            else if(cmd.equals("+")||cmd.equals("-")||cmd.equals("*")||cmd.equals("/")) {
                char s = ' ';
                if(!strUnder.isEmpty()) {		//获取下方文本框最后字符
                    s = strUnder.charAt(strUnder.length()-1);
                }

                if(strUpper.isEmpty()&&strUnder.isEmpty()) {}	//上下均空时运算符按键失效
                else if(!strUnder.isEmpty()&&(s=='+'||s=='-'||s=='*'||s=='/')) { //当已经输入一个运算符时，再次输入视为运算符更新
                    strUnder = strUnder.substring(0,strUnder.length()-1)+cmd;
                }

                else if(!strUnder.isEmpty()&&strUnder.charAt(0)!='(') {
                    strUpper += strUnder;
                    strUnder = cmd;
                }
                else {
                    strUnder += cmd;
                }

            }
            //左括号按键
            else if(cmd.equals("(")) {
                strUpper += strUnder;
                strUnder = cmd;
            }
            //右括号按键，将下方文本框内容上移
            else if(cmd.equals(")")) {
                strUpper = strUpper + strUnder + ")";
                strUnder = "";
            }
            //百分号按键，当下层问空时百分号键失效
            else if (cmd.equals("%")) {
                if(strUpper.isEmpty()&&strUnder.isEmpty()) {}
                else {
                    strUpper += strUnder;
                    strUnder = cmd;
                }
            }
            //开平方按键
            else if (cmd.equals("sqrt")) {
                strUpper += strUnder;
                strUnder = "sqrt(";
            }
            //求倒数按键
            //避免了下层文本框有运算符时点击“1/X”键会出现“1/(+”的问题。
            else if (cmd.equals("1/X")) {
                if(!strUnder.isEmpty()) {
                    char t = strUnder.charAt(0);
                    if(t=='+'||t=='-'||t=='*'||t=='/') {
                        strUpper += t;
                        strUnder = strUnder.substring(1,strUnder.length());
                    }
                }
                strUnder = "1/(" + strUnder;
            }
            //正负号按键，在下方文本框中的文本前加上“(-”（此处待优化）
            else if (cmd.equals("±")) {
                strUnder = strUnder + "(-";
            }
            //小数点按键。若point为真，按键失效。否则，下方文本框添加
            //小数点并point的值赋为真，开始生效。另外，若按下小数点时
            //表达式为空或前一位非数字，则自动在小数点前添加数字0。
            //下层为空上层非空时小数点按键失效。(避免右括号之后输入数字)
            else if(cmd.equals(".")) {
                if(point) {return;}
                else if(strUnder.isEmpty()&&!strUpper.isEmpty()) {}
                else if(strUnder.isEmpty()) {
                    strUnder += "0.";
                    point = true;
                }
                else {
                    char t = strUnder.charAt(strUnder.length()-1);
                    if(t<'0'||t>'9') {
                        strUpper += strUnder;
                        strUnder = "0.";
                    }
                    else strUnder += ".";
                    point = true;
                }
            }
            //等于号按键，按下即触发计算表达式，并将flag赋为真，下次点
            //击任何按键将清空上下文本框（此处待优化）。
            else if (cmd.equals("=")) {
                if(strUnder.isEmpty()&&strUpper.isEmpty()) {
                    return;
                }
                flag = true;
                strUpper += strUnder;
                strUnder = Calculate.calc(strUpper);
            }
            //数字按键，若之前下方文本框有基本运算符则将其上移。
            //下层为空上层非空时数字键失效
            else {
                if(strUnder.isEmpty()&&!strUpper.isEmpty()) {}
                else if(strUnder.isEmpty()) {
                    strUnder += cmd;
                }
                else {
                    char t = strUnder.charAt(strUnder.length()-1);
                    if((t=='+'||t=='-'||t=='*'||t=='/') && (strUnder.charAt(0)!='(')) {
                        strUpper += strUnder;
                        strUnder = cmd;
                    }
                    else {
                        strUnder += cmd;
                    }
                }
            }
            txtUpper.setText(strUpper);	//更新上方文本框的文本
            if(strUpper.length()>23) {
                txtUpper.setFont(new Font("Consolas", Font.PLAIN, 690/strUpper.length()));
            }
            else {
                txtUpper.setFont(new Font("Consolas", Font.PLAIN, 30));
            }

            txtUnder.setText(strUnder);	//更新下方文本框的文本
            if(strUnder.length()>15) {
                txtUnder.setFont(new Font("Consolas", Font.PLAIN, 700/strUnder.length()));
            }
            else {
                txtUnder.setFont(new Font("Consolas", Font.PLAIN, 50));
            }
        }
    }



}
