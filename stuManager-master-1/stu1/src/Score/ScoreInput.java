package Score;

import db.dbConn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by dell on 2020/11/4.
 */
public class ScoreInput extends JFrame {
    JPanel contentPane;
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JTextField jTextField1 = new JTextField();
    JTextField jTextField2 = new JTextField();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JComboBox jComboBox1 = new JComboBox();
    JOptionPane jOptionPane1 = new JOptionPane();
    dbConn conn = new dbConn();

    public ScoreInput(){
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception{
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(null);
        setSize(new Dimension(482, 300));
        setTitle("成绩录入");

        jLabel1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 20));
        jLabel1.setText("成 绩 录 入 ");
        jLabel1.setBounds(new Rectangle(178, 17, 126, 25));
        jLabel2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jLabel2.setText("学生学号:");
        jLabel2.setBounds(new Rectangle(75, 70, 81, 21));
        jLabel3.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jLabel3.setText("课  程:");
        jLabel3.setBounds(new Rectangle(75, 120, 82, 21));
        jLabel4.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jLabel4.setText("成  绩:");
        jLabel4.setBounds(new Rectangle(80, 170, 77, 21));

        jTextField1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jTextField1.setBorder(BorderFactory.createLoweredBevelBorder());
        jTextField1.setText("");
        jTextField1.setBounds(new Rectangle(190, 70, 184, 26));
        jTextField2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jTextField2.setBorder(BorderFactory.createLoweredBevelBorder());
        jTextField2.setText("");
        jTextField2.setBounds(new Rectangle(190, 170, 94, 26));
        jComboBox1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jComboBox1.setBounds(new Rectangle(190, 120, 160, 25));

        jButton1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jButton1.setBounds(new Rectangle(103, 240, 90, 29));
        jButton1.setBorder(BorderFactory.createRaisedBevelBorder());
        jButton1.setText("提  交");
        jButton1.addActionListener(new ScoreInput_jButton1_actionAdapter(this));
        jButton2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jButton2.setBounds(new Rectangle(277, 240, 90, 29));
        jButton2.setBorder(BorderFactory.createRaisedBevelBorder());
        jButton2.setText("退  出");
        jButton2.addActionListener(new ScoreInput_jButton2_actionAdapter(this));

        jOptionPane1.setBounds(new Rectangle(75, 261, 262, 90));
        jOptionPane1.setLayout(null);

        contentPane.add(jLabel1);
        contentPane.add(jLabel2);
        contentPane.add(jLabel3);
        contentPane.add(jLabel4);
        contentPane.add(jTextField1);
        contentPane.add(jTextField2);
        contentPane.add(jComboBox1);
        contentPane.add(jButton1);
        contentPane.add(jButton2);
        contentPane.add(jOptionPane1);
        jComboBox1.addItem("请选择");

        //选择课程
        try {
            ResultSet rs = conn.getRs("select * from tb_cource;");
            while(rs.next()){
                jComboBox1.addItem(rs.getString("courceName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //提交
    public void jButton1_actionPerformed(ActionEvent e){
        insertScore();
    }

    //课程录入
    public void insertScore(){
        //异常判断
        if(jTextField1.getText().length() == 0){
            jOptionPane1.showMessageDialog(this, "学生学号不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
        }else if(jComboBox1.getSelectedIndex() == 0){
            jOptionPane1.showMessageDialog(this, "请选择课程！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
        }else if(jTextField2.getText().length() == 0){
            jOptionPane1.showMessageDialog(this, "成绩不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
        }else{
            try {
                boolean name = false;
                ResultSet rs = conn.getRs("select stuNumber from tb_score where courceName = '" + String.valueOf(jComboBox1.getSelectedItem()) + "';");
                while(rs.next()){
                    if(jTextField1.getText().trim().equals(rs.getString("stuNumber"))){
                        name = true;
                        break;
                    }
                }
                if(name){
                    jOptionPane1.showMessageDialog(this, "成绩已存在！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
                }else{
                    int i = conn.getUpdate("insert into tb_score(stuNumber,score,courceName) values('" + jTextField1.getText().trim() + "'," + jTextField2.getText().trim() + ",'" + jComboBox1.getSelectedItem() + "');");
                    if(i == -1){
                        jOptionPane1.showMessageDialog(this, "提交失败！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
                    }else{
                        jOptionPane1.showMessageDialog(this, "提交成功！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
                    }
                }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //退出
    public void jButton2_actionPerformed(ActionEvent e){
        this.dispose();
    }


}

class ScoreInput_jButton1_actionAdapter implements ActionListener{
    private ScoreInput scoreInput;
    public ScoreInput_jButton1_actionAdapter(ScoreInput scoreInput){
        this.scoreInput = scoreInput;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.scoreInput.jButton1_actionPerformed(e);
    }
}

class ScoreInput_jButton2_actionAdapter implements ActionListener {
    private ScoreInput adaptee;

    ScoreInput_jButton2_actionAdapter(ScoreInput adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.jButton2_actionPerformed(e);
    }
}
