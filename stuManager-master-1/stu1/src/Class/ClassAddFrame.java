package Class;

import db.dbConn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by dell on 2020/10/27.
 */
public class ClassAddFrame extends JFrame {
    JPanel contentPane;
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JTextField jTextField1 = new JTextField();
    JComboBox jComboBox1 = new JComboBox();
    JComboBox jComboBox2 = new JComboBox();
    JOptionPane jOptionPane1 = new JOptionPane();
    dbConn conn = new dbConn();
    String college,special;

    public ClassAddFrame(){
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception{
        contentPane = (JPanel)getContentPane();
        contentPane.setLayout(null);
        setSize(new Dimension(465, 280));
        setTitle("班级录入");

        jTextField1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jTextField1.setBorder(BorderFactory.createLoweredBevelBorder());
        jTextField1.setBounds(new Rectangle(180, 80, 180, 25));
        jLabel1.setFont(new java.awt.Font("Dialog", Font.BOLD, 20));
        jLabel1.setText("班 级 信 息 录 入");
        jLabel1.setBounds(new Rectangle(136, 20, 212, 25));
        jLabel2.setFont(new java.awt.Font("Dialog", Font.BOLD, 18));
        jLabel2.setText("班级名称:");
        jLabel2.setBounds(new Rectangle(80, 80, 90, 20));
        jLabel3.setFont(new java.awt.Font("Dialog", Font.BOLD, 18));
        jLabel3.setText("所属学院:");
        jLabel3.setBounds(new Rectangle(80, 130, 90, 20));
        jLabel4.setFont(new java.awt.Font("Dialog", Font.BOLD, 18));
        jLabel4.setText("所属专业:");
        jLabel4.setBounds(new Rectangle(80, 180, 90, 20));

        jButton1.setText("提   交");
        jButton1.setFont(new java.awt.Font("Dialog", Font.BOLD, 16));
        jButton1.setBounds(new Rectangle(102, 223, 96, 29));
        jButton1.setBorder(BorderFactory.createRaisedBevelBorder());
        jButton1.addActionListener(new AddClassFrame_jButton1_actionAdapter(this));
        jButton2.setText("退   出");
        jButton2.setFont(new java.awt.Font("Dialog", Font.BOLD, 16));
        jButton2.setBounds(new Rectangle(265, 221, 96, 31));
        jButton2.setBorder(BorderFactory.createRaisedBevelBorder());
        jButton2.addActionListener(new AddClassFrame_jButton2_actionAdapter(this));

        jComboBox1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jComboBox1.setEditable(true);
        jComboBox1.setBounds(new Rectangle(180, 130, 180, 25));
        jComboBox1.addActionListener(new AddClassFrame_jComboBox1_actionAdapter(this));
        jComboBox2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jComboBox2.setEditable(true);
        jComboBox2.setBounds(new Rectangle(180, 180, 180, 25));
        //jComboBox2.addActionListener(new AddClassFrame_jComboBox2_actionAdapter(this));

        jOptionPane1.setBounds(new Rectangle(106, 258, 262, 90));
        jOptionPane1.setLayout(null);

        contentPane.add(jLabel1);
        contentPane.add(jLabel2);
        contentPane.add(jLabel3);
        contentPane.add(jLabel4);
        contentPane.add(jTextField1);
        contentPane.add(jButton1);
        contentPane.add(jButton2);
        contentPane.add(jComboBox1);
        contentPane.add(jComboBox2);
        jComboBox1.addItem("请选择学院");

        //选择学院
        try {
            ResultSet rs = conn.getRs("select * from tb_depart;");
            while(rs.next()){
                String college =rs.getString("departName");
                jComboBox1.addItem(college);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jComboBox2.setEnabled(false);
    }

    //选择学院后触发专业可选
    public void jComboBox1_actionPerformed(ActionEvent e){
        //选择专业
        special();
        jComboBox2.setEnabled(true);
    }

    //退出
    public void jButton2_actionPerformed(ActionEvent e){
        this.dispose();
    }

    //提交
    public void jButton1_actionPerformed(ActionEvent e){
        //判断异常
        if(jTextField1.getText().trim().length() == 0){
            jOptionPane1.showMessageDialog(this, "班级名称不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
        }else if(jComboBox1.getSelectedIndex() == 0){
            jOptionPane1.showMessageDialog(this, "学院名称不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
        }else if(jComboBox2.getSelectedIndex() == 0){
            jOptionPane1.showMessageDialog(this, "专业名称不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
        }else{
            college_Save();
            special_Save();
            boolean classname = false;
            try {
                ResultSet rs = conn.getRs(
                        "select className from tb_class where specName = '" + String.valueOf(jComboBox2.getSelectedItem()) + "';");
                while(rs.next()){
                    if(jTextField1.getText().trim().equals(rs.getString("className").trim())){
                        classname = true;
                    }
                }
                if(classname){
                    jOptionPane1.showMessageDialog(this, "班级名称已存在！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
                }else{
                    conn.getUpdate(
                            "insert into tb_class(className, specName, departName) " + " values('"
                                    + jTextField1.getText().trim() + "','" + special + "','" + college + "');");
                    jOptionPane1.showMessageDialog(this, "班级信息录入成功！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    //选择专业
    public void special(){
        jComboBox2.removeAllItems();
        jComboBox2.addItem("请选择专业");
        try {
            ResultSet rs = conn.getRs(
                    "select * from tb_spec where departName = '" + String.valueOf(jComboBox1.getSelectedItem()) + "';");
            while(rs.next()){
                jComboBox2.addItem(rs.getString("specName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //存下所选专业
    public void special_Save(){
        String sel = String.valueOf(jComboBox2.getSelectedItem());
        try {
            ResultSet rs = conn.getRs("select * from  tb_spec where specName = '" + sel + "';");
            while(rs.next()){
                special = rs.getString("specName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //存下所选学院
    public void college_Save(){
        String sel = String.valueOf(jComboBox1.getSelectedItem());
        try {
            ResultSet rs = conn.getRs("select * from  tb_depart where departName = '" + sel + "';");
            while(rs.next()){
                college = rs.getString("departName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class AddClassFrame_jComboBox1_actionAdapter implements ActionListener {
    ClassAddFrame classAddFrame;
    public AddClassFrame_jComboBox1_actionAdapter( ClassAddFrame classAddFrame){
        this.classAddFrame = classAddFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        classAddFrame.jComboBox1_actionPerformed(e);
    }
}

class AddClassFrame_jButton1_actionAdapter implements ActionListener {
    ClassAddFrame classAddFrame;
    public AddClassFrame_jButton1_actionAdapter( ClassAddFrame classAddFrame){
        this.classAddFrame = classAddFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        classAddFrame.jButton1_actionPerformed(e);
    }
}

class AddClassFrame_jButton2_actionAdapter implements ActionListener {
    ClassAddFrame classAddFrame;
    public AddClassFrame_jButton2_actionAdapter( ClassAddFrame classAddFrame){
        this.classAddFrame = classAddFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        classAddFrame.jButton2_actionPerformed(e);
    }
}
