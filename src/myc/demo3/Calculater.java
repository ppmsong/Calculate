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
 * 2019.10.27���£�
 * �޸�bug:
 * 1.����˳�����жϲ�������ʾ
 * 2.�����˿�ѧ�����������ġ�E��ʹ�������
 * 3.����˱��ʽ��������������ƥ�䣨��3-��
 *   ���жϣ�ͨ��ջ�պ�ջʣ����жϣ���
 *
 */


/**
 * @�汾��V2.0
 * @���ߣ�pms
 * @ʱ�䣺 2019.10.27
 *
 * @����:
 * ���׼�������ʵ�ִ��룬�ܹ����㿪�����ٷ���������
 * ���򵥵Ĵ���С���ŵ���׺���ʽ��
 *
 * @˵��:
 * ����ȫ��Ϊ�Լ���д������ʱ���������㣬�������к�
 * �������δʵ�ֵĹ��ܣ������𽥸��º����ơ�����
 * ��bug��������ı��ʽ����ӭ˽�ң�һ������Դ���
 *
 * @��ʵ�ֵĹ��ܣ�
 * 1.�򵥵ı��ʽ���㣬���� + - * /������������ţ�
 *   ��ƽ�����������ٷ��������Ź��ܣ��Լ��˸���ա�
 * 2.������ʽʱ�Զ������������ı���(��Windows��
 *   ������������)������������������ʾ���·��ı�
 *   �򣬽����ʽ��ʾ���Ϸ��ı���
 * 3.�򵥵ı��ʽ��鹦�ܣ��ܹ�������Ų�ƥ�䡢�����㡢
 *   �Ȳ��ֱ��ʽ����
 * 4.������������ƺ͸������������������ʱ����������
 *   ����ʽ���������������ǽ������������(�����
 *   һ����������������)��
 * 5.С�������ƣ���һ����������������С������С���㰴
 *   ����ʱʧЧ���Ա�֤ÿ��������ֻ��һ��С���㣻���⣬
 *   ������С����ʱ�����·��ı�����û�����ݻ��߽�����
 *   ��������Զ���С����ǰ�������0��
 * 6.���ּ�С�����������ƺ͸���������������֮�����ּ�
 *   ��С�������ʱʧЧ�����������֮�����¼��
 * 7.�����������ƺ͸������޷��ڱ��ʽΪ��ʱ����ٷֺš�
 *   ��������ǰ���㣬����ʱ�Զ�ȥ�����������������ţ�
 *   ���������ȼ����м��㡣
 * 8.֧�ֿ�ƽ��������sqrt���Ķ��Ƕ�ף�����ƽ��������
 *   ��С����ʱ����ʾ���ʽ����Bad str����
 * 9.���ݱ��ʽ���ȶ�̬�ı������ı��������С���Ա�֤
 *   �����ı������������ı�����������ʾ��
 * 10.��������ʽ�����Զ�ȷ���Ƿ����С��λ����������
 *    ���ְ�����λ�ּ�����ʾ����ÿ��λ������һ�����Ÿ�
 *    ����
 *
 *
 *
 * @��ʵ�ֵĹ��ܣ�
 * 1.���ఴ��
 * 2.���������
 * 3.���ʽ��鹦��
 * 4.���ʽ������
 * 5.���̵ļ�������
 * 6.֧�ֶ�㿪ƽ��Ƕ�ף��ݹ�ʵ�֣�
 * 6.��̬�ı�ı������С�����洰�ڣ�
 * 7.һ�μ�����ɺ���������ŻὫ�ϴ�����������
 *   ��������ε�������ʽ��
 * 8.�˵����ĸ��๦�ܣ�����ת����������������ʷ��¼
 * 9.���༰��ǿ����������Ƽ���������
 * 10.�߾��ȼ��������ʹ�ã���ʵ�ָ�ǿ�ļ���������
 * 11.�ٷֺ�ǰ������������������ı��ʽ
 * 12.�����Ű������ܵ��Ż�����ǰ����ÿ�ΰ���������
 *    �ͻ���ӡ�(-��������������Ϊ�ж���������Ϊ��
 *    ��ȥ�����ţ�������Ӹ���
 * 13.֧����������С�������ʾ��λ��
 *
 */

/**
 * @��˵����
 * �������Ľ������ɣ��Լ��¼�������Ӧ���ܡ�
 *
 * @��ṹ��
 * public class Calculater extends JFrame {
 * 	public static void main(String[] args){}
 * 	public Calculater(){}					//���캯��������������
 * 	class btnActionListener implements ActionListener{}	//���������¼���Ӧ�ڲ���
 * 	class btnKeyListener implements KeyListener{}		//���̼�����δʵ�֣�
 * }
 */

public class Calculater extends JFrame {
    private static final long serialVersionUID = 1L;
    /** @flag
     * ���ڱ���Ƿ������һ�����㣬ÿ�μ������֮��
     * ��ÿ�ε��'='֮����ֵ����Ϊ�棬֮���ٴε��
     * �κΰ���������ı��򣬲�����ֵ����Ϊfalse��
     *
     ** @point
     * ���������������������������������point��Ч��
     * ���ڱ�ǵ�ǰ�������Ƿ��Ѿ�����С���㣬�Ա�֤
     * ÿ��������ֻ����һ��С���㡣
     */
    private boolean flag = false;
    private boolean point = false;
    private JPanel contentPane;	//������
    private JTextField txtUnder;	//�ϲ��ı���,������ʾ����ı��ʽ
    private JTextField txtUpper;	//�²��ı���,������ʾ��ǰ������
    private String strUpper = "";	//�ϲ��ı�
    private String strUnder = "";	//�²��ı�


    public static void main(String[] args) {
        Calculater frame = new Calculater();
        frame.setVisible(true);
        //frame.setResizable(false);	//���ɸ��Ĵ��ڴ�С
    }

    public Calculater() {
        setTitle("���׼�����-pms");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 416, 620);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        /**
         * �����ı����񣬴�������㡣���������ʽʱ���
         * ʽ��ʾ���²㡣��ʼʱ��Ϊ�ա����������ʽʱ��
         * ���²��ı���ʵʱ���¡�������ں�֮���²��ı���
         * ��ʾ���������ϲ���ʾ���ʽ���ٴε���κΰ���
         * ���ü���������ʾ�ü���
         */
        JPanel paneText = new JPanel();		//����ı�����
        paneText.setBounds(0, 0, 400, 170);
        contentPane.add(paneText);
        paneText.setLayout(null);

        txtUpper = new JTextField(strUpper);	//����Ϸ��ı���
        txtUpper.setHorizontalAlignment(JTextField.RIGHT);	//�����ı��Ҷ���
        txtUpper.setFont(new Font("Consolas", Font.PLAIN, 30));
        txtUpper.setEditable(false);
        txtUpper.setBounds(0, 0, 400, 65);
        paneText.add(txtUpper);

        txtUnder = new JTextField(strUnder);	//����·��ı���
        txtUnder.setHorizontalAlignment(JTextField.RIGHT);	//�����ı��Ҷ���
        txtUnder.setFont(new Font("Consolas", Font.PLAIN, 50));
        txtUnder.setEditable(false);
        txtUnder.setBounds(0, 65, 400, 105);
        paneText.add(txtUnder);

        /**
         * ���ɰ������񣬲����ַ���������ѭ����ʽ���ɰ�����
         */
        JPanel PaneButton = new JPanel();		//��Ӱ�������
        PaneButton.setBounds(0, 170, 400, 412);
        contentPane.add(PaneButton);
        PaneButton.setLayout(new GridLayout(6, 4, 0, 0));

        String[] btn = {"(",")","CE","Del","%","sqrt","1/X","*",	//���ð�������
                "7","8","9","/","4","5","6","+",
                "1","2","3","-","��","0",".","="};

        for(int i=0;i<btn.length;i++) {			//��Ӱ���
            Button button = new Button(btn[i]);
            button.setFont(new Font("Consolas", Font.PLAIN, 25));
            button.addActionListener(new btnActionListener()); 	//��Ӽ�����
            //button.addKeyListener(new btnKeyListener());
            PaneButton.add(button);
        }


    }

    /**
     * �ڲ��࣬ʵ�ְ����ļ������ܣ���ʵʱ��������
     * �����ı��������ʾ���ݡ�
     */
    class btnActionListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {

            String cmd = e.getActionCommand();

            /**
             * ������flag����flagΪ�棬��������ı���
             * @flag
             * ���ڱ���Ƿ������һ�����㣬ÿ�μ������֮��
             * ��ÿ�ε��'='֮����ֵ����Ϊ�棬֮���ٴε��
             * �κΰ���������ı��򣬲�����ֵ����Ϊfalse��
             */
            if(flag) {
                strUnder = "";
                strUpper = "";
                flag = false;
            }
            /**
             * ������point����pointΪ�棬С���㰴��ʧЧ��
             * ��pointΪ���ҵ�ǰ��Ӧ����Ϊ���������point
             * ��Ϊ�٣���ʱС������Ч��
             * @point
             * ���������������������������������point��Ч��
             * ���ڱ�ǵ�ǰ�������Ƿ��Ѿ�����С���㣬�Ա�֤
             * ÿ��������ֻ����һ��С���㡣
             */
            char a = cmd.charAt(0);
            if(point && cmd.length()==1 && (a<'0'||a>'9') && a!='.') {
                point = false;
            }


            //��ռ����������ı�����գ��ȴ��´����롣
            if(cmd.equals("CE")) {
                strUpper = "";
                strUnder = "";
            }
            //ɾ���������·��ı����е����һ���ַ��������ڣ�
            //ɾ�������޷�ɾ���Ϸ��ı�����ַ���
            else if (cmd.equals("Del")) {
                if(strUnder.length()>0) {
                    strUnder = strUnder.substring(0,strUnder.length()-1);
                }
            }
            //��������������·��ı�������������Ϸ��ı���
            //���·��ı������ַ�Ϊ�����ţ����ӳ����Ʋ�����
            //�´�����������ʱ���˴����Ż������ܽ���Ϊ������
            //��������ƥ��ʱ�ٽ������Ʋ�����
            else if(cmd.equals("+")||cmd.equals("-")||cmd.equals("*")||cmd.equals("/")) {
                char s = ' ';
                if(!strUnder.isEmpty()) {		//��ȡ�·��ı�������ַ�
                    s = strUnder.charAt(strUnder.length()-1);
                }

                if(strUpper.isEmpty()&&strUnder.isEmpty()) {}	//���¾���ʱ���������ʧЧ
                else if(!strUnder.isEmpty()&&(s=='+'||s=='-'||s=='*'||s=='/')) { //���Ѿ�����һ�������ʱ���ٴ�������Ϊ���������
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
            //�����Ű���
            else if(cmd.equals("(")) {
                strUpper += strUnder;
                strUnder = cmd;
            }
            //�����Ű��������·��ı�����������
            else if(cmd.equals(")")) {
                strUpper = strUpper + strUnder + ")";
                strUnder = "";
            }
            //�ٷֺŰ��������²��ʿ�ʱ�ٷֺż�ʧЧ
            else if (cmd.equals("%")) {
                if(strUpper.isEmpty()&&strUnder.isEmpty()) {}
                else {
                    strUpper += strUnder;
                    strUnder = cmd;
                }
            }
            //��ƽ������
            else if (cmd.equals("sqrt")) {
                strUpper += strUnder;
                strUnder = "sqrt(";
            }
            //��������
            //�������²��ı����������ʱ�����1/X��������֡�1/(+�������⡣
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
            //�����Ű��������·��ı����е��ı�ǰ���ϡ�(-�����˴����Ż���
            else if (cmd.equals("��")) {
                strUnder = strUnder + "(-";
            }
            //С���㰴������pointΪ�棬����ʧЧ�������·��ı������
            //С���㲢point��ֵ��Ϊ�棬��ʼ��Ч�����⣬������С����ʱ
            //���ʽΪ�ջ�ǰһλ�����֣����Զ���С����ǰ�������0��
            //�²�Ϊ���ϲ�ǿ�ʱС���㰴��ʧЧ��(����������֮����������)
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
            //���ںŰ��������¼�����������ʽ������flag��Ϊ�棬�´ε�
            //���κΰ�������������ı��򣨴˴����Ż�����
            else if (cmd.equals("=")) {
                if(strUnder.isEmpty()&&strUpper.isEmpty()) {
                    return;
                }
                flag = true;
                strUpper += strUnder;
                strUnder = Calculate.calc(strUpper);
            }
            //���ְ�������֮ǰ�·��ı����л���������������ơ�
            //�²�Ϊ���ϲ�ǿ�ʱ���ּ�ʧЧ
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
            txtUpper.setText(strUpper);	//�����Ϸ��ı�����ı�
            if(strUpper.length()>23) {
                txtUpper.setFont(new Font("Consolas", Font.PLAIN, 690/strUpper.length()));
            }
            else {
                txtUpper.setFont(new Font("Consolas", Font.PLAIN, 30));
            }

            txtUnder.setText(strUnder);	//�����·��ı�����ı�
            if(strUnder.length()>15) {
                txtUnder.setFont(new Font("Consolas", Font.PLAIN, 700/strUnder.length()));
            }
            else {
                txtUnder.setFont(new Font("Consolas", Font.PLAIN, 50));
            }
        }
    }



}
