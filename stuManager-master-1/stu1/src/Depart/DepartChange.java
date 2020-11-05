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
public class DepartChange extends JFrame {
    JPanel contentPane;
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JComboBox jComboBox1 =new JComboBox();
    JComboBox jComboBox2 =new JComboBox();
    JOptionPane jOptionPane1 = new JOptionPane();
    dbConn conn = new dbConn();
    String college,special;
    String find;

    public DepartChange() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception{
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(null);
        setSize(new Dimension(465, 280));
        setTitle("院系修改");

        jLabel1.setFont(new java.awt.Font("Dialog", Font.BOLD, 20));
        jLabel1.setText("院 系 信 息 修 改");
        jLabel1.setBounds(new Rectangle(136, 20, 212, 25));
        jLabel2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jLabel2.setText("所属学院:");
        jLabel2.setBounds(new Rectangle(80, 100, 90, 20));
        jLabel3.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jLabel3.setText("所属专业:");
        jLabel3.setBounds(new Rectangle(80, 150, 90, 20));
        jButton1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jButton1.setBounds(new Rectangle(102, 223, 96, 29));
        jButton1.setBorder(BorderFactory.createRaisedBevelBorder());
        jButton1.setText("修  改");
        jButton1.addActionListener(new DepartChange_jButton1_actionAdapter(this));
        jButton2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jButton2.setBounds(new Rectangle(265, 221, 96, 31));
        jButton2.setBorder(BorderFactory.createRaisedBevelBorder());
        jButton2.setText("退  出");
        jButton2.addActionListener(new DepartChange_jButton2_actionAdapter(this));
        jOptionPane1.setBounds(new Rectangle(106, 258, 262, 90));
        jOptionPane1.setLayout(null);

        jComboBox1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jComboBox1.setEditable(true);
        jComboBox1.setBounds(new Rectangle(180, 100, 180, 25));
        jComboBox1.addActionListener(new DepartChange_jComboBox1_actionAdapter(this));
        jComboBox2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jComboBox2.setEditable(true);
        jComboBox2.setBounds(new Rectangle(180, 150, 180, 25));

        contentPane.add(jLabel1);
        contentPane.add(jLabel2);
        contentPane.add(jLabel3);
        contentPane.add(jButton1);
        contentPane.add(jButton2);
        contentPane.add(jComboBox1);
        contentPane.add(jComboBox2);
        jComboBox1.addItem("请选择学院");

        try {
            ResultSet rs = conn.getRs("select * from tb_depart;");
            while(rs.next()){
                jComboBox1.addItem(rs.getString("departName"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jComboBox2.setEnabled(false);
        //显示
        show_();

    }

    //显示
    public void show_(){
        if(find == null){
            return ;
        }else{
            jComboBox2.setEnabled(true);
            try {
                ResultSet rs = conn.getRs("select * from tb_spec where specId = '" + String.valueOf(find) + "';");
                while(rs.next()){
                    jComboBox1.addItem(String.valueOf(rs.getString("departName")));
                    jComboBox2.addItem(String.valueOf(rs.getString("spectName")));
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

    //修改
    public void jButton1_actionPerformed(ActionEvent e){
        //判断异常
        if(jComboBox1.getSelectedIndex() == 0){
            jOptionPane1.showMessageDialog(this, "请选择要修改的院系！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }else if(jComboBox2.getSelectedIndex() == 0){
            jOptionPane1.showMessageDialog(this, "请选择要修改的专业！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }else {
            collegeSave();
            specialSave();

            boolean departExist = false, specExist = false;
            try {
                ResultSet rs1 = conn.getRs("select * from tb_spec where departName = '" + String.valueOf(jComboBox1.getSelectedItem()) + "';");
                while(rs1.next()){
                    departExist = true;
                }

                ResultSet rs2 = conn.getRs("select * from tb_spec where specName = '" + String.valueOf(jComboBox2.getSelectedItem()) + "';");
                while(rs2.next()){
                    departExist = true;
                }

                if(departExist  && specExist){
                    jOptionPane1.showMessageDialog(this, "学院和专业已存在！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
                }else if(!departExist && specExist){
                    jOptionPane1.showMessageDialog(this, "专业已存在！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
                }else if(departExist && !specExist){
                    conn.getUpdate("delete from tb_spec where specId = '" + String.valueOf(find) + "';");
                    int i = conn.getUpdate("insert into tb_spec(departName,specName) values('" + String.valueOf(jComboBox1.getSelectedItem()) + "','" + String.valueOf(jComboBox2.getSelectedItem()) + "');");
                    if(i == -1){
                        jOptionPane1.showMessageDialog(this, "修改失败！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
                    }else{
                        jOptionPane1.showMessageDialog(this, "修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
                    }
                }else if(!departExist && !specExist){
                    conn.getUpdate("delete  from tb_spec where specId = '" + Integer.valueOf(find) + "';");
                    int j = conn.getUpdate("insert into tb_spec(departName,specName) values('" + String.valueOf(jComboBox1.getSelectedItem()) + "','" + String.valueOf(jComboBox2.getSelectedItem()) + "');");
                    int k = conn.getUpdate("insert into tb_depart(departName) values('" + String.valueOf(jComboBox1.getSelectedItem()) + "';)");
                    if(j != -1 && k != -1){
                        jOptionPane1.showMessageDialog(this, "修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
                    }else{
                        jOptionPane1.showMessageDialog(this, "修改失败！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
                    }
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    //存储college
    public void collegeSave(){
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

    //存储special
    public void specialSave(){
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
    //
    public void jComboBox1_actionPerformed(ActionEvent e){
        special();
        jComboBox2.setEnabled(true);
    }

    //
    public void special(){
        jComboBox2.removeAllItems();
        jComboBox2.addItem("请选择专业");
        try {
            ResultSet rs = conn.getRs("select * from tb_spec where departName = '" + String.valueOf(jComboBox1.getSelectedItem()) + "';");
            while(rs.next()){
                jComboBox2.addItem(rs.getString("specName"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

class DepartChange_jButton1_actionAdapter implements ActionListener {
    private DepartChange departChange;
    public DepartChange_jButton1_actionAdapter(DepartChange departChange){
        this.departChange = departChange;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        departChange.jButton1_actionPerformed(e);
    }
}

class DepartChange_jButton2_actionAdapter implements ActionListener {
    private DepartChange departChange;
    public DepartChange_jButton2_actionAdapter(DepartChange departChange){
        this.departChange = departChange;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        departChange.jButton2_actionPerformed(e);
    }
}

class DepartChange_jComboBox1_actionAdapter implements ActionListener {
    private DepartChange departChange;
    public DepartChange_jComboBox1_actionAdapter(DepartChange departChange){
        this.departChange = departChange;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        departChange.jComboBox1_actionPerformed(e);
    }
}
