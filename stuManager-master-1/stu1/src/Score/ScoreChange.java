package Score;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
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
public class ScoreChange extends JFrame {
    JPanel contentPane;
    XYLayout xYLayout1 = new XYLayout();
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JTextField jTextField1 = new JTextField();
    JTextField jTextField2 = new JTextField();
    JComboBox jComboBox1 =new JComboBox();
    JOptionPane jOptionPane1 = new JOptionPane();
    dbConn conn = new dbConn();
    String college,special;
    int find;

    public ScoreChange(int find) {
        this.find = find;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception{
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(xYLayout1);
        xYLayout1.setHeight(350);
        xYLayout1.setWidth(500);
        setTitle("成绩修改");

        jLabel1.setFont(new java.awt.Font("Dialog", Font.BOLD, 20));
        jLabel1.setText("成 绩 修 改");
        jLabel2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jLabel2.setText("学生学号:");
        jLabel3.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jLabel3.setText("课程名称:");
        jLabel4.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jLabel4.setText("学  分:");
        jTextField1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jTextField2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jButton1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jButton1.setText("修  改");
        jButton1.addActionListener(new ScoreChange_jButton1_actionAdapter(this));
        jButton2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jButton2.setText("退  出");
        jButton2.addActionListener(new ScoreChange_jButton2_actionAdapter(this));
        jComboBox1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jOptionPane1.setLayout(null);

        jComboBox1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jComboBox1.setEditable(true);

        contentPane.add(jLabel1, new XYConstraints(158, 21, -1, 49));
        contentPane.add(jLabel2, new XYConstraints(85, 80, 80, 30));
        contentPane.add(jLabel3, new XYConstraints(85, 130, 80, 30));
        contentPane.add(jLabel4, new XYConstraints(85, 180, 80, 30));
        contentPane.add(jButton1, new XYConstraints(130, 230, 90, 30));
        contentPane.add(jButton2, new XYConstraints(295, 230, 90, 30));
        contentPane.add(jComboBox1, new XYConstraints(200, 130, 140, 30));
        contentPane.add(jTextField1, new XYConstraints(200, 80, 140, 30));
        contentPane.add(jTextField2, new XYConstraints(200, 180, 140, 30));
        jComboBox1.addItem("请选择课程名称");
        //选择专业
        try {
            ResultSet rs = conn.getRs("select * from tb_cource;");
            while(rs.next()){
                jComboBox1.addItem(rs.getString("courceName"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //显示所选数据
        show_();

    }

    public void show_(){
        if(find < 0){
            return;
        }else{
            try {
                ResultSet rs = conn.getRs("select * from tb_score where scoreId = " + Integer.valueOf(find) + ";");
                while(rs.next()){
                    jTextField1.setText(rs.getString("stuNumber"));
                    jComboBox1.addItem(rs.getString("courceName"));
                    jTextField2.setText(rs.getString("score"));
                }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //提交
    public void jButton1_actionPerformed(ActionEvent e){
        int i = conn.getUpdate("Update tb_score set courceName = '" + jComboBox1.getSelectedItem() + "',stuNumber = " + jTextField1.getText().trim() + ",score = " + Float.valueOf(jTextField2.getText().trim()) + " where scoreId = " + find + ";");
        if(i == -1){
            jOptionPane1.showMessageDialog(this, "修改失败", "提示", JOptionPane.INFORMATION_MESSAGE, null);
        }else{
            jOptionPane1.showMessageDialog(this, "修改成功", "提示", JOptionPane.INFORMATION_MESSAGE, null);
        }
    }

    //退出
    public void jButton2_actionPerformed(ActionEvent e){
        this.dispose();
    }
}

class ScoreChange_jButton1_actionAdapter implements ActionListener {
    private ScoreChange scoreChange;
    public ScoreChange_jButton1_actionAdapter(ScoreChange scoreChange){
        this.scoreChange = scoreChange;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        scoreChange.jButton1_actionPerformed(e);
    }
}

class ScoreChange_jButton2_actionAdapter implements ActionListener {
    private ScoreChange scoreChange;
    public ScoreChange_jButton2_actionAdapter(ScoreChange scoreChange){
        this.scoreChange = scoreChange;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        scoreChange.jButton2_actionPerformed(e);
    }
}



