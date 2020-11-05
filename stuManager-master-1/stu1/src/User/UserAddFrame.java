package User;

import db.dbConn;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by dell on 2020/10/20.
 */
public class UserAddFrame extends JFrame {
    JPanel contentPane;
    String level;
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JTextField jTextField1 = new JTextField();
    JPasswordField jPasswordField1 = new JPasswordField();
    JPasswordField jPasswordField2 = new JPasswordField();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JOptionPane jOptionPane1 = new JOptionPane();

    public UserAddFrame() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception{
        contentPane = (JPanel)getContentPane();
        contentPane.setLayout(null);
        setTitle("添加用户");
        setSize(new Dimension(469, 315));

        jLabel1.setFont(new java.awt.Font("Dialog", Font.BOLD, 23));
        jLabel1.setText("添 加 用 户");
        jLabel1.setBounds(new Rectangle(134, 12, 198, 27));
        jLabel2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jLabel2.setText("用户名:");
        jLabel2.setBounds(new Rectangle(90, 66, 68, 22));
        jLabel3.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jLabel3.setText("密  码:");
        jLabel3.setBounds(new Rectangle(90, 118, 75, 27));
        jLabel4.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jLabel4.setText("确认密码:");
        jLabel4.setBounds(new Rectangle(84, 167, 89, 29));

        jTextField1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 13));
        jTextField1.setBorder(BorderFactory.createLoweredBevelBorder());
        jTextField1.setBounds(new Rectangle(191, 65, 155, 24));
        jPasswordField1.setBorder(BorderFactory.createLoweredBevelBorder());
        jPasswordField1.setBounds(new Rectangle(191, 119, 155, 24));
        jPasswordField2.setBorder(BorderFactory.createLoweredBevelBorder());
        jPasswordField2.setBounds(new Rectangle(191, 169, 155, 24));

        jButton1.setBounds(new Rectangle(99, 225, 89, 25));
        jButton1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 13));
        jButton1.setBorder(BorderFactory.createLoweredBevelBorder());
        jButton1.setText("提  交");
        jButton1.addActionListener(new AddAdminFrame_jButton1_actionAdapter(this));
        jButton2.setBounds(new Rectangle(267, 225, 89, 25));
        jButton2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 13));
        jButton2.setBorder(BorderFactory.createLoweredBevelBorder());
        jButton2.setText("退  出");
        jButton2.addActionListener(new AddAdminFrame_jButton2_actionAdapter(this));
        jOptionPane1.setBounds(new Rectangle(0, 233, 262, 90));
        jOptionPane1.setLayout(null);

        contentPane.add(jLabel1);
        contentPane.add(jLabel2);
        contentPane.add(jLabel3);
        contentPane.add(jLabel4);
        contentPane.add(jTextField1);
        contentPane.add(jPasswordField1);
        contentPane.add(jPasswordField2);
        contentPane.add(jButton1);
        contentPane.add(jButton2);
        contentPane.add(jOptionPane1);

    }

    //退出
    public void jButton2_actionPerformed(){
        this.dispose();
    }

    //添加
    public void jButton1_actionPerformed(){

        System.out.println("jButton1_actionPerformed方法被调用！");
        boolean flag = false;
        //异常判断
        if(jTextField1.getText().length() == 0){
            jOptionPane1.showMessageDialog(this, "用户名不能为空！", "提示" , JOptionPane.INFORMATION_MESSAGE);
        }else if(String.valueOf(jPasswordField1.getPassword()).equals(String.valueOf(jPasswordField2.getPassword()))){
            flag = true;
        }else{
            jOptionPane1.showMessageDialog(this, "密码确认错误！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
        if(flag){
            try {
                //查询用户名是否存在
                boolean name = false;
                dbConn conn = new dbConn();
                ResultSet rs = conn.getRs("select * from tb_user");
                while(rs.next()){
                    if(jTextField1.getText().trim().equals(rs.getString("userName").trim())){
                        name = true;
                    }
                }
                if(name){
                    jOptionPane1.showMessageDialog(this, "用户名已存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    conn.getUpdate("insert into tb_user(userName, userPwd, userType) values('" + jTextField1.getText().trim() + "','" +  String.valueOf(jPasswordField1.getPassword())+ "','2');");
                    jOptionPane1.showMessageDialog(this, "添加用户成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }
}

class AddAdminFrame_jButton1_actionAdapter implements ActionListener{
    private UserAddFrame userAddFrame;

    public AddAdminFrame_jButton1_actionAdapter(UserAddFrame userAddFrame) {
        this.userAddFrame = userAddFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.userAddFrame.jButton1_actionPerformed();
    }
}

class AddAdminFrame_jButton2_actionAdapter implements ActionListener{
    private UserAddFrame userAddFrame;

    public AddAdminFrame_jButton2_actionAdapter(UserAddFrame userAddFrame) {
        this.userAddFrame = userAddFrame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.userAddFrame.jButton2_actionPerformed();
    }
}
