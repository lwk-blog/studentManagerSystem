package Depart;

import db.dbConn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by dell on 2020/11/2.
 */
public class DepartAddFrame extends JFrame {
    JPanel contentPane;
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JComboBox jComboBox1 = new JComboBox();
    JComboBox jComboBox2 = new JComboBox();
    JOptionPane jOptionPane1 = new JOptionPane();
    dbConn conn = new dbConn();
    String college,special;

    public DepartAddFrame(){
        jbInit();
    }

    private void jbInit(){
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(null);
        setSize(new Dimension(465, 280));
        setTitle("院系录入");

        jLabel1.setFont(new java.awt.Font("Dialog", Font.BOLD, 20));
        jLabel1.setText(" 院 系 信 息 录 入 ");
        jLabel1.setBounds(new Rectangle(136, 20, 212, 25));
        jLabel2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jLabel2.setText("所属学院:");
        jLabel2.setBounds(new Rectangle(80, 80, 90, 20));
        jLabel3.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jLabel3.setText("所属专业:");
        jLabel3.setBounds(new Rectangle(80, 130, 90, 20));

        jComboBox1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jComboBox1.setEditable(true);
        jComboBox1.setBounds(new Rectangle(180, 80, 180, 25));
        jComboBox1.addItem("请选择学院");
        jComboBox1.addActionListener(new DepartAddFrame_jComboBox1_actionAdapter(this));
        jComboBox2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jComboBox2.setEditable(true);
        jComboBox2.setBounds(new Rectangle(180, 130, 180, 25));

        jButton1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jButton1.setBounds(new Rectangle(102, 180, 96, 29));
        jButton1.setBorder(BorderFactory.createRaisedBevelBorder());
        jButton1.setText("提  交");
        jButton1.addActionListener(new DepartAddFrame_jButton1_actionAdapter(this));
        jButton2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jButton2.setBounds(new Rectangle(265, 180, 96, 31));
        jButton2.setBorder(BorderFactory.createRaisedBevelBorder());
        jButton2.setText("退  出");
        jButton2.addActionListener(new DepartAddFrame_jButton2_actionAdapter(this));

        jOptionPane1.setBounds(new Rectangle(106, 258, 262, 90));
        jOptionPane1.setLayout(null);

        contentPane.add(jLabel1);
        contentPane.add(jLabel2);
        contentPane.add(jLabel3);
        contentPane.add(jComboBox1);
        contentPane.add(jComboBox2);
        contentPane.add(jButton1);
        contentPane.add(jButton2);
        contentPane.add(jOptionPane1);

        //选择学院
        try {
            ResultSet rs = conn.getRs("select * from tb_depart;");
            while(rs.next()){
                jComboBox1.addItem(rs.getString("departName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jComboBox2.setEnabled(false);
    }

    //退出
    public void jButton2_actionPerformed(ActionEvent e){
        this.dispose();
    }

    //
    public void jComboBox1_actionPerformed(ActionEvent e){
        showSpecial();
        jComboBox2.setEnabled(true);
    }

    //显示专业
    public void showSpecial(){
        jComboBox2.removeAllItems();
        jComboBox2.addItem("请选择专业");
        ResultSet rs = conn.getRs("select * from tb_spec where departName = '" + String.valueOf(jComboBox1.getSelectedItem()) + "';");
        try {
            while(rs.next()){
                jComboBox2.addItem(rs.getString("specName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //修改
    public void jButton1_actionPerformed(ActionEvent e){
        //异常判断
        if(jComboBox1.getSelectedIndex() == 0){
            jOptionPane1.showMessageDialog(this, "所在学院不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
        }else if(jComboBox2.getSelectedIndex() == 0){
            jOptionPane1.showMessageDialog(this, "所在专业不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
        }else{
            college_Save();
            special_Save();

            boolean departExist = false, specExist = false;
            try {
                ResultSet rs_departExist = conn.getRs("select * from tb_spec where departName = '" + String.valueOf(jComboBox1.getSelectedItem()) + "';");
                while(rs_departExist.next()) {
                    departExist = true;
                }

                ResultSet rs_specExist = conn.getRs("select * from tb_spec where specName = '" + String.valueOf(jComboBox2.getSelectedItem()) + "';");
                while(rs_specExist.next()){
                    specExist = true;
                }

                if(departExist  && specExist){
                    jOptionPane1.showMessageDialog(this, "学院和专业已存在！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
                }else if(!departExist && specExist){
                    jOptionPane1.showMessageDialog(this, "专业已存在！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
                }else if(departExist && !specExist){
                    int i = conn.getUpdate("insert into tb_spec(departName,specName) values('" + String.valueOf(jComboBox1.getSelectedItem()) + "','" + String.valueOf(jComboBox2.getSelectedItem()) + "');");
                    if(i == -1){
                        jOptionPane1.showMessageDialog(this, "提交失败！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
                    }else{
                        jOptionPane1.showMessageDialog(this, "提交成功！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
                    }
                }else if(!departExist && !specExist){
                    int j = conn.getUpdate("insert into tb_spec(departName,specName) values('" + String.valueOf(jComboBox1.getSelectedItem()) + "','" + String.valueOf(jComboBox2.getSelectedItem()) + "');");
                    int k = conn.getUpdate("insert into tb_depart(departName) values('" + String.valueOf(jComboBox1.getSelectedItem()) + "';)");
                    if(j != -1 && k != -1){
                        jOptionPane1.showMessageDialog(this, "提交成功！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
                    }else{
                        jOptionPane1.showMessageDialog(this, "提交失败！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
                    }
                }

            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    //存储special
    public void special_Save(){
        String sel = String.valueOf(jComboBox2.getSelectedItem());
        ResultSet rs = conn.getRs("select * from tb_spec where specName = '" + sel + "';");
        try {
            while(rs.next()){
                college = rs.getString("specName");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //存储college
    public void college_Save(){
        String sel = String.valueOf(jComboBox1.getSelectedItem());
        ResultSet rs = conn.getRs("select * from tb_depart where departName = '" + sel + "';");
        try {
            while(rs.next()){
                college = rs.getString("departName");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class DepartAddFrame_jComboBox1_actionAdapter implements ActionListener {
    private DepartAddFrame departAddFrame;
    DepartAddFrame_jComboBox1_actionAdapter(DepartAddFrame departAddFrame){
        this.departAddFrame = departAddFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        departAddFrame.jComboBox1_actionPerformed(e);
    }
}

class DepartAddFrame_jButton1_actionAdapter implements ActionListener {
    private DepartAddFrame departAddFrame;
    public DepartAddFrame_jButton1_actionAdapter(DepartAddFrame departAddFrame){
        this.departAddFrame = departAddFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        departAddFrame.jButton1_actionPerformed(e);
    }
}

class DepartAddFrame_jButton2_actionAdapter implements ActionListener {
    private DepartAddFrame departAddFrame;
    public DepartAddFrame_jButton2_actionAdapter(DepartAddFrame departAddFrame){
        this.departAddFrame = departAddFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        departAddFrame.jButton2_actionPerformed(e);
    }
}
