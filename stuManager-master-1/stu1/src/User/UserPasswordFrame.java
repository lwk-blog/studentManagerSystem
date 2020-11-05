package User;

import db.dbConn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by dell on 2020/11/3.
 */
public class UserPasswordFrame extends JFrame {
    JPanel contentPane;
    String level, name;
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel5 = new JLabel();
    JTextField jTextField1 = new JTextField();
    JPasswordField jPasswordField1 = new JPasswordField();
    JPasswordField jPasswordField2 = new JPasswordField();
    JPasswordField jPasswordField3 = new JPasswordField();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JOptionPane jOptionPane1 = new JOptionPane();
    dbConn conn = new dbConn();

    public UserPasswordFrame(String level, String name) {
        this.level = level;
        this.name = name;
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception{
        contentPane = (JPanel)getContentPane();
        contentPane.setLayout(null);
        setTitle("用户密码修改");
        setSize(new Dimension(444, 340));

        jLabel1.setFont(new java.awt.Font("Dialog", Font.BOLD, 20));
        jLabel1.setText(" 用 户 密 码 修 改 ");
        jLabel1.setBounds(new Rectangle(112, 15, 204, 24));
        jLabel2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jLabel2.setText("用户名:");
        jLabel2.setBounds(new Rectangle(80, 72, 74, 23));
        jLabel3.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jLabel3.setText("旧密码:");
        jLabel3.setBounds(new Rectangle(80, 115, 74, 23));
        jLabel4.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jLabel4.setText("新密码:");
        jLabel4.setBounds(new Rectangle(80, 159, 74, 23));
        jLabel5.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jLabel5.setText("确认新密码:");
        jLabel5.setBounds(new Rectangle(72, 203, 101, 23));

        jTextField1.setEnabled(false);
        jTextField1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 13));
        jTextField1.setBorder(BorderFactory.createLoweredBevelBorder());
        jTextField1.setBounds(new Rectangle(175, 72, 159, 23));
        jPasswordField1.setBorder(BorderFactory.createLoweredBevelBorder());
        jPasswordField1.setBounds(new Rectangle(175, 114, 160, 25));
        jPasswordField2.setBorder(BorderFactory.createLoweredBevelBorder());
        jPasswordField2.setBounds(new Rectangle(175, 158, 160, 25));
        jPasswordField2.setText("");
        jPasswordField3.setBorder(BorderFactory.createLoweredBevelBorder());
        jPasswordField3.setBounds(new Rectangle(175, 202, 160, 25));
        jPasswordField3.setText("");

        jButton1.setBounds(new Rectangle(87, 254, 86, 26));
        jButton1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 13));
        jButton1.setBorder(BorderFactory.createRaisedBevelBorder());
        jButton1.setText("提  交");
        jButton1.addActionListener(new UserPasswordFrame_jButton1_actionAdapter(this));
        jButton2.setBounds(new Rectangle(253, 254, 86, 26));
        jButton2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 13));
        jButton2.setBorder(BorderFactory.createLoweredBevelBorder());
        jButton2.setText("退  出");
        jButton2.addActionListener(new UserPasswordFrame_jButton2_actionAdapter(this));
        jOptionPane1.setBounds(new Rectangle(-8, 84, 262, 90));
        jOptionPane1.setLayout(null);

        contentPane.add(jLabel1);
        contentPane.add(jLabel2);
        contentPane.add(jLabel3);
        contentPane.add(jLabel4);
        contentPane.add(jLabel5);
        contentPane.add(jTextField1);
        contentPane.add(jPasswordField1);
        contentPane.add(jPasswordField2);
        contentPane.add(jPasswordField3);
        contentPane.add(jButton1);
        contentPane.add(jButton2);
        contentPane.add(jOptionPane1);

        this.jTextField1.setText(name);
        if(level.equals("1")){
            jTextField1.setEnabled(true);
        }

    }

    //提交
    public void jButton1_actionPerformed(){
        boolean a = false;
        boolean b = true;
        //异常判断
        if(jTextField1.getText().trim() == null || jTextField1.getText().trim().length() == 0 || jTextField1.getText().trim().length() > 20){
            jOptionPane1.showMessageDialog(this,"用户名不能为空且长度不能超过20！", "提示", JOptionPane.INFORMATION_MESSAGE,null);
        }else if(String.valueOf(jPasswordField2.getPassword()).trim().compareTo(String.valueOf(jPasswordField3.getPassword())) != 0){
            jOptionPane1.showMessageDialog(this,"新密码确认错误！", "提示", JOptionPane.INFORMATION_MESSAGE,null);
        } else {
            try {
                //对原密码进行验证
                ResultSet rs = conn.getRs("select userName,userPwd from tb_user;");
                while(rs.next()){
                    if(jTextField1.getText().trim().equals(rs.getString("userName").trim()) && String.valueOf(jPasswordField1.getPassword()).trim().equals(rs.getString("userPwd").trim())){
                        a = true;
                        b = false;
                        break;
                    }
                }
                if(b){
                    jOptionPane1.showMessageDialog(this,"用户名或密码错误！", "提示", JOptionPane.INFORMATION_MESSAGE,null);
                }
                if(a){
                    //修改密码
                    int i = conn.getUpdate("Update tb_user set userPwd = '" + String.valueOf(jPasswordField2.getPassword()).trim() + "' where userName = '" + jTextField1.getText().trim() +"'");
                    if(i == -1){
                        jOptionPane1.showMessageDialog(this,"修改失败！", "提示", JOptionPane.INFORMATION_MESSAGE,null);
                    }else{
                        jOptionPane1.showMessageDialog(this,"修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE,null);
                    }
                    jTextField1.setText("");
                    jPasswordField1.setText("");
                    jPasswordField2.setText("");
                    jPasswordField3.setText("");
                }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    //退出
    public void jButton2_actionPerformed(){
        this.dispose();
    }
}

class UserPasswordFrame_jButton1_actionAdapter implements ActionListener{
    private UserPasswordFrame userPasswordFrame;

    public UserPasswordFrame_jButton1_actionAdapter(UserPasswordFrame userPasswordFrame) {
        this.userPasswordFrame = userPasswordFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.userPasswordFrame.jButton1_actionPerformed();
    }
}

class UserPasswordFrame_jButton2_actionAdapter implements ActionListener{
    private UserPasswordFrame userPasswordFrame;

    public UserPasswordFrame_jButton2_actionAdapter(UserPasswordFrame userPasswordFrame) {
        this.userPasswordFrame = userPasswordFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.userPasswordFrame.jButton2_actionPerformed();
    }
}
