package Class;

import db.dbConn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by dell on 2020/10/30.
 */
public class ClassChange extends JFrame {
    JPanel contentPane;
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JTextField jTextField1 = new JTextField();
    JOptionPane jOptionPane1 = new JOptionPane();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JComboBox jComboBox1 = new JComboBox();
    JComboBox jComboBox2 = new JComboBox();
    dbConn conn = new dbConn();
    String find;
    String special, college;

    public ClassChange(String find){
        this.find = find;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception{
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(null);
        setTitle("班级修改");
        setSize(new Dimension(465, 280));

        jLabel1.setFont(new java.awt.Font("Dialog", Font.BOLD, 20));
        jLabel1.setText("班 级 信 息 修 改");
        jLabel1.setBounds(new Rectangle(136, 20, 212, 25));
        jLabel2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jLabel2.setText("班级名称:");
        jLabel2.setBounds(new Rectangle(80, 80, 90, 20));
        jLabel3.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jLabel3.setText("所属学院:");
        jLabel3.setBounds(new Rectangle(80, 130, 90, 20));
        jLabel4.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jLabel4.setText("所属专业:");
        jLabel4.setBounds(new Rectangle(80, 180, 90, 20));
        jTextField1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jTextField1.setBorder(BorderFactory.createRaisedBevelBorder());
        jTextField1.setBounds(new Rectangle(180, 80, 180, 25));

        jButton1.setBounds(new Rectangle(102, 223, 96, 29));
        jButton1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jButton1.setBorder(BorderFactory.createRaisedBevelBorder());
        jButton1.setText("修   改");
        jButton1.addActionListener(new ClassU_jButton1_actionAdapter(this));
        jButton2.setBounds(new Rectangle(265, 221, 96, 31));
        jButton2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jButton2.setBorder(BorderFactory.createRaisedBevelBorder());
        jButton2.setToolTipText("");
        jButton2.setText("退   出");
        jButton2.addActionListener(new ClassU_jButton2_actionAdapter(this));
        jOptionPane1.setBounds(new Rectangle(106, 258, 262, 90));
        jOptionPane1.setLayout(null);

        jComboBox1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jComboBox1.setBounds(new Rectangle(180, 130, 180, 25));
        jComboBox1.addItem("请选择学院");
        jComboBox1.addActionListener(new ClassU_jComboBox1_actionAdapter(this));
        jComboBox2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jComboBox2.setBounds(new Rectangle(180, 180, 180, 25));
        jComboBox2.setEditable(true);

        contentPane.add(jLabel1);
        contentPane.add(jLabel2);
        contentPane.add(jLabel3);
        contentPane.add(jLabel4);
        contentPane.add(jOptionPane1);
        contentPane.add(jComboBox1);
        contentPane.add(jComboBox2);
        contentPane.add(jButton1);
        contentPane.add(jButton2);
        contentPane.add(jTextField1);

        //选择学院
        try {
            ResultSet rs = conn.getRs("select * from tb_depart;");
            while(rs.next()){
                String college = rs.getString("departName");
                jComboBox1.addItem(college);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jComboBox2.setEnabled(true);
        showNum();
    }

    //显示选定行在修改页面的值
    public void showNum(){
        if(find == null){
            return;
        }else{
            jComboBox2.setEnabled(true);
            try {
                ResultSet rs = conn.getRs("select * from tb_class where  classId = '" + Integer.valueOf(find) + "';");
                while(rs.next()){
                    jComboBox2.addItem(String.valueOf(rs.getString("specName")));
                    jTextField1.setText((rs.getString("className").trim()));
                }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //选择触发
    public void jComboBox1_actionPerformed(ActionEvent e){
        //选择专业
        selectSpec();
        jComboBox2.setEnabled(true);
    }

    //退出
    public void jButton2_actionPerformed(ActionEvent e){
        this.dispose();
    }

    //修改
    public void jButton1_actionPerformed(ActionEvent e){
        //异常判断
        if(this.jTextField1.getText().trim().length() == 0){
            jOptionPane1.showMessageDialog(this, "班级名称不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }else if(jComboBox1.getSelectedIndex() == 0){
            jOptionPane1.showMessageDialog(this, "班级选择所属学院！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }else if(jComboBox2.getSelectedIndex() == 0){
            jOptionPane1.showMessageDialog(this, "班级选择所属专业！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }else{
            college_Save();
            spec_Save();

            //不允许同一个专业有两个一样的班级
            boolean classname = false;
            try {
                ResultSet rs = conn.getRs("select className from tb_class where specName = '" + String.valueOf(jComboBox2.getSelectedItem()) + "';");
                while(rs.next()){
                    if(jTextField1.getText().trim().equals(rs.getString("className").trim())){
                        classname = true;
                    }
                }
                if(classname){
                    jOptionPane1.showMessageDialog(this, "班级名称已经存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    conn.getUpdate(
                            "update tb_class set className = '" + jTextField1.getText().trim() + "',specName = '" + special + "',departName = '" + college + "' where classId = " + String.valueOf(find) + ";"
                    );
                    jOptionPane1.showMessageDialog(this, "班级信息修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    //存下选定的学院
    public void college_Save(){
        String sel = String.valueOf(jComboBox1.getSelectedItem());
        try {
            ResultSet rs = conn.getRs("select * from tb_depart where departName = '" + sel + "';");
            while(rs.next()){
                college = rs.getString("departName");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //存下选定的专业
    public void spec_Save(){
        String sel = String.valueOf(jComboBox2.getSelectedItem());
        try {
            ResultSet rs = conn.getRs("select * from tb_spec where specName = '" + sel + "';");
            while(rs.next()){
                special = rs.getString("specName");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //选择专业
    public void selectSpec(){
        jComboBox2.removeAllItems();
        jComboBox2.addItem("请选择专业");
        try {
            ResultSet rs = conn.getRs("select * from tb_spec where departName = '" + String.valueOf(jComboBox1.getSelectedItem()) + "';");
            while(rs.next()){
                jComboBox2.addItem(rs.getString("specName"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class ClassU_jComboBox1_actionAdapter implements ActionListener{
    private ClassChange classChange;
    public ClassU_jComboBox1_actionAdapter(ClassChange classChange){
        this.classChange = classChange;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        classChange.jComboBox1_actionPerformed(e);
    }
}

class ClassU_jButton1_actionAdapter implements ActionListener{
    private ClassChange classChange;
    public ClassU_jButton1_actionAdapter(ClassChange classChange){
        this.classChange = classChange;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        classChange.jButton1_actionPerformed(e);
    }
}

class ClassU_jButton2_actionAdapter implements ActionListener{
    private ClassChange classChange;
    public ClassU_jButton2_actionAdapter(ClassChange classChange){
        this.classChange = classChange;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        classChange.jButton2_actionPerformed(e);
    }
}
