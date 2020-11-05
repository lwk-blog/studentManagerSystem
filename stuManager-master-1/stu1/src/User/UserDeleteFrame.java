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
public class UserDeleteFrame extends JFrame {
    JPanel contentPane;
    String level;
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JTextField jTextField1 = new JTextField();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JOptionPane jOptionPane1 = new JOptionPane();
    dbConn conn = new dbConn();

    public UserDeleteFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception{
        contentPane = (JPanel)getContentPane();
        contentPane.setLayout(null);
        setTitle("删除用户");
        setSize(new Dimension(469, 315));

        jLabel1.setFont(new java.awt.Font("Dialog", Font.BOLD, 23));
        jLabel1.setText("删 除 用 户");
        jLabel1.setBounds(new Rectangle(134, 12, 198, 27));
        jLabel2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jLabel2.setText("用户名:");
        jLabel2.setBounds(new Rectangle(90, 100, 68, 22));


        jTextField1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 13));
        jTextField1.setBorder(BorderFactory.createLoweredBevelBorder());
        jTextField1.setBounds(new Rectangle(191, 100, 155, 24));

        jButton1.setBounds(new Rectangle(99, 180, 89, 25));
        jButton1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 13));
        jButton1.setBorder(BorderFactory.createLoweredBevelBorder());
        jButton1.setText("提  交");
        jButton1.addActionListener(new UserDeleteFrame_jButton1_actionAdapter(this));
        jButton2.setBounds(new Rectangle(267, 180, 89, 25));
        jButton2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 13));
        jButton2.setBorder(BorderFactory.createLoweredBevelBorder());
        jButton2.setText("退  出");
        jButton2.addActionListener(new UserDeleteFrame_jButton2_actionAdapter(this));
        jOptionPane1.setBounds(new Rectangle(0, 233, 262, 90));
        jOptionPane1.setLayout(null);

        contentPane.add(jLabel1);
        contentPane.add(jLabel2);
        contentPane.add(jTextField1);
        contentPane.add(jButton1);
        contentPane.add(jButton2);
        contentPane.add(jOptionPane1);
    }

    //提交
    public void jButton1_actionPerformed(){
        boolean isAdmin = false,noUser =true;
        //异常判断
        if(jTextField1.getText().length() == 0){
            jOptionPane1.showMessageDialog(this, "用户名不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }else{
            try {
                ResultSet rs = conn.getRs("select * from tb_user where userName = '" + jTextField1.getText().trim() + "';");
                while(rs.next()){
                    noUser = false;
                    if(1 == Integer.valueOf(rs.getString("userType").trim())){
                        isAdmin = true;
                    }
                }
                if(noUser){
                    jOptionPane1.showMessageDialog(this, "用户名不存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
                if(isAdmin){
                    int i = conn.getUpdate("delete from tb_user where userName = '" + jTextField1.getText().trim() + "';");
                    if(i != -1){
                        jOptionPane1.showMessageDialog(this, "用户删除成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }else{
                        jOptionPane1.showMessageDialog(this, "用户删除失败！", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }
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

class UserDeleteFrame_jButton1_actionAdapter implements ActionListener{
    private UserDeleteFrame userDeleteFrame;

    public UserDeleteFrame_jButton1_actionAdapter(UserDeleteFrame userDeleteFrame) {
        this.userDeleteFrame = userDeleteFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.userDeleteFrame.jButton1_actionPerformed();
    }
}

class UserDeleteFrame_jButton2_actionAdapter implements ActionListener{
    private UserDeleteFrame userDeleteFrame;

    public UserDeleteFrame_jButton2_actionAdapter(UserDeleteFrame userDeleteFrame) {
        this.userDeleteFrame = userDeleteFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.userDeleteFrame.jButton2_actionPerformed();
    }
}