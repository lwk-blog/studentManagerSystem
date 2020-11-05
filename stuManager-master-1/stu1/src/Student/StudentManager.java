package Student;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import db.dbConn;
import stuManager.MainFrame;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 * Created by dell on 2020/10/23.
 */
public class StudentManager extends JFrame {
    String sql, zhy;
    JLabel jLabel1 = new JLabel();
    JLabel jLabel2 = new JLabel();
    JLabel jLabel3 = new JLabel();
    JLabel jLabel4 = new JLabel();
    JLabel jLabel6 = new JLabel();
    XYLayout xYLayout1 = new XYLayout();
    XYLayout xYLayout2 = new XYLayout();
    XYLayout xYLayout4 = new XYLayout();
    XYLayout xYLayout5 = new XYLayout();
    JPanel jPanel1 = new JPanel();
    JPanel jPanel2 = new JPanel();
    JPanel jPanel4 = new JPanel();
    JTextField jTextField1 = new JTextField();
    JTextField jTextField2 = new JTextField();
    JTextField jTextField3 = new JTextField();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JButton jButton3 = new JButton();
    JButton jButton4 = new JButton();
    JButton jButton5 = new JButton();
    JButton jButton6 = new JButton();
    JButton jButton7 = new JButton();

    ButtonGroup buttonGroup1 = new ButtonGroup();
    JComboBox jComboBox1 = new JComboBox();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTable jTable1 = new JTable();
    int intRow;

    dbConn conn = new dbConn();
    String[] arrField = {"学号", "姓名", "学院", "专业", "班级"};
    DefaultTableModel model = new DefaultTableModel();
    Object[][] arr = {};
    static int find;
    JOptionPane jOptionPane_LoginFeedback = new JOptionPane();

    public StudentManager(){
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception{
        getContentPane().setLayout(xYLayout1);
        setTitle("学生信息管理");
        jLabel1.setFont(new java.awt.Font("新宋体", Font.BOLD, 27));
        jLabel1.setText("学生信息管理");
        jLabel2.setFont(new java.awt.Font("Dialog", Font.BOLD, 20));
        jLabel2.setText("请选择查询方式:");
        jLabel3.setFont(new java.awt.Font("Dialog", Font.BOLD, 20));
        jLabel3.setText("请输入学号:");
        jLabel4.setFont(new java.awt.Font("Dialog", Font.BOLD, 20));
        jLabel4.setText("请输入学生姓名:");
        jLabel6.setFont(new java.awt.Font("Dialog", Font.BOLD, 20));
        jLabel6.setText("请输入要查询的班级:");

        jButton1.setFont(new java.awt.Font("Dialog", Font.BOLD, 20));
        jButton1.setText("查询");
        jButton1.addActionListener(new StudentC_jButton1_actionAdpter(this));
        jButton2.setFont(new java.awt.Font("Dialog", Font.BOLD, 20));
        jButton2.setText("查询");
        jButton2.addActionListener(new StudentC_jButton2_actionAdpter(this));
        jButton4.setFont(new java.awt.Font("Dialog", Font.BOLD, 20));
        jButton4.setText("查询");
        jButton4.addActionListener(new StudentC_jButton4_actionAdpter(this));
        jButton5.setFont(new java.awt.Font("Dialog", Font.BOLD, 20));
        jButton5.setText("修改");
        jButton5.addActionListener(new StudentC_jButton5_actionAdapter(this));
        jButton6.setFont(new java.awt.Font("Dialog", Font.BOLD, 20));
        jButton6.setText("返回");
        jButton6.addActionListener(new StudentC_jButton6_actionAdapter(this));
        jButton7.setFont(new java.awt.Font("Dialog", Font.BOLD, 20));
        jButton7.setText("删除");
        jButton7.addActionListener(new StudentC_jButton7_actionAdapter(this));
        jComboBox1.addActionListener(new StudentC_jComboBox1_actionAdapter(this));
        jComboBox1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 20));

        jPanel1.setBorder(BorderFactory.createEtchedBorder());
        jPanel1.setLayout(xYLayout5);
        jPanel2.setBorder(BorderFactory.createEtchedBorder());
        jPanel2.setLayout(xYLayout2);
        jPanel4.setBorder(BorderFactory.createEtchedBorder());
        jPanel4.setLayout(xYLayout4);

        jScrollPane1.setBorder(BorderFactory.createEtchedBorder());
        jTable1.setCellSelectionEnabled(true);
        jPanel2.add(jButton2, new XYConstraints(381, 8, 85, 27));
        jPanel4.add(jButton4, new XYConstraints(388, 8, 89, 26));
        this.getContentPane().add(jPanel2, new XYConstraints(150, 125, 510, 50));
        this.getContentPane().add(jPanel4, new XYConstraints(150, 125, 510, 50));
        ShowPanel();
        jComboBox1.addItem("请选择");
        jComboBox1.addItem("按学号查询");
        jComboBox1.addItem("按姓名查询");
        jComboBox1.addItem("按班级查询");

        jPanel1.add(jTextField1, new XYConstraints(164, 8, 149, 25));
        jPanel1.add(jButton1, new XYConstraints(360, 8, 80, 29));
        jPanel1.add(jLabel3, new XYConstraints(29, 8, 125, 26));
        jPanel2.add(jLabel4, new XYConstraints(21, 8, 164, 31));
        jPanel2.add(jTextField2, new XYConstraints(206, 8, 132, 27));
        jPanel4.add(jLabel6, new XYConstraints(16, 8, 201, 30));
        jPanel4.add(jTextField3, new XYConstraints(212, 8, 152, 28));

        jScrollPane1.getViewport().add(jTable1, null);
        this.getContentPane().add(jLabel1, new XYConstraints(321, 12, 180, 38));
        this.getContentPane().add(jLabel2, new XYConstraints(169, 74, 161, 32));
        this.getContentPane().add(jComboBox1, new XYConstraints(370, 76, -1, 30));
        this.getContentPane().add(jPanel1, new XYConstraints(150, 125, 510, 50));
        this.getContentPane().add(jPanel2, new XYConstraints(150, 125, 510, 50));
        this.getContentPane().add(jPanel4, new XYConstraints(150, 125, 510, 50));
        this.getContentPane().add(jButton5, new XYConstraints(225, 545, 95, 34));
        this.getContentPane().add(jButton6, new XYConstraints(525, 545, 95, 34));
        this.getContentPane().add(jButton7, new XYConstraints(375, 545, 95, 34));
        this.getContentPane().add(jScrollPane1, new XYConstraints(20, 193, 759, 325));

        //判断用户等级
        /*if(MainFrame.level.equals("1")){

        }else{

        }*/

        sql = "select * from tb_student;";
        //更新显示
        UpdateRecord();
    }

    //查询方式
    public void ShowPanel(){
        jPanel1.setVisible(false);
        jPanel2.setVisible(false);
        jPanel4.setVisible(false);
        if(jComboBox1.getSelectedIndex() == 1){
            jPanel1.setVisible(true);
        }else if(jComboBox1.getSelectedIndex() == 2){
            jPanel2.setVisible(true);
        }else if(jComboBox1.getSelectedIndex() == 3){
            jPanel4.setVisible(true);
        }
    }

    //更新显示
    public void UpdateRecord(){
        Object[][] arrTmp = {};//设定表格的字段
        Vector vec;
        model = new DefaultTableModel(arrTmp, arrField);
        jTable1 =new JTable(model);
        jScrollPane1.getViewport().add(jTable1, null);

        try {
            ResultSet rs =conn.getRs(sql);
            while(rs.next()){
                vec = new Vector();
                vec.add(rs.getString("stuNumber").trim());
                vec.add(rs.getString("stuName").trim());
                vec.add(rs.getString("stuDepart").trim());
                vec.add(rs.getString("stuSpec").trim());
                vec.add(rs.getString("stuClass").trim());
                model.addRow(vec);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jScrollPane1.getHorizontalScrollBar();
        jTable1.setGridColor(Color.BLUE);
        jTable1.setEnabled(true);
        jTable1.setSelectionForeground(Color.RED);
        jTable1.setSelectionBackground(Color.GREEN);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.setRowSelectionAllowed(true);
        jTable1.setShowVerticalLines(true);
    }

    public void jComboBox1_actionPerformed(ActionEvent e){
        ShowPanel();
    }

    //按学号查询
    public void jButton1_actionPerformed(ActionEvent e){
        if(!jTextField1.getText().trim().equals("")){
            sql = "select * from tb_student where stuNumber = '" + jTextField1.getText().trim() + "';";
            UpdateRecord();
        }else{
            jOptionPane_LoginFeedback.showMessageDialog(this, "请输入要查询的学生学号！", "提示", jOptionPane_LoginFeedback.INFORMATION_MESSAGE);
        }
    }

    //按姓名查询
    public void jButton2_actionPerformed(ActionEvent e){
        if(!jTextField1.getText().trim().equals("")){
            sql = "select * from tb_student where stuName = '" + jTextField2.getText().trim() + "';";
            UpdateRecord();
        }else{
            jOptionPane_LoginFeedback.showMessageDialog(this, "请输入要查询的学生姓名！", "提示", jOptionPane_LoginFeedback.INFORMATION_MESSAGE);
        }
    }

    //按班级查询
    public void jButton4_actionPerformed(ActionEvent e){
        if(!jTextField1.getText().trim().equals("")){
            sql = "select * from tb_student where stuClass = '" + jTextField3.getText().trim() + "';";
            UpdateRecord();
        }else{
            jOptionPane_LoginFeedback.showMessageDialog(this, "请输入要查询的学生班级！", "提示", jOptionPane_LoginFeedback.INFORMATION_MESSAGE);
        }
    }

    //修改
    public void jButton5_actionPerformed(ActionEvent e){
        getM();
        if(intRow != -1){
            StudentChange siadd = new StudentChange(find);
            siadd.setLocation(400, 200);
            siadd.setSize(592, 350);
            siadd.setVisible(true);
            siadd.validate();
            this.dispose();
        }else{
            jOptionPane_LoginFeedback.showMessageDialog(this, "请选择要修改的信息！", "提示", jOptionPane_LoginFeedback.INFORMATION_MESSAGE);

        }
    }

    //返回
    public void jButton6_actionPerformed(ActionEvent e){
        this.dispose();
    }

    //触发删除
    public void jButton7_actionPerformed(ActionEvent e){
        System.out.println("删除功能！");
        getM();
        if(intRow != -1){
            delstu();
        }else{
            jOptionPane_LoginFeedback.showMessageDialog(this, "请选择要删除的信息！", "提示", jOptionPane_LoginFeedback.INFORMATION_MESSAGE);

        }
    }

    //删除
    public void delstu(){
        try {
            if(0 < conn.getUpdate("delete from tb_student where stuNumber = '" + find +  "';")){
                jOptionPane_LoginFeedback.showMessageDialog(this, "学生信息删除成功！", "提示！", jOptionPane_LoginFeedback.INFORMATION_MESSAGE);
                UpdateRecord();
            }else{
                System.err.printf("删除tb_student表中stuNumber = %d的记录失败\n", find);
                jOptionPane_LoginFeedback.showMessageDialog(this, "学生信息删除失败！", "提示！", jOptionPane_LoginFeedback.INFORMATION_MESSAGE);
            }
        } catch (HeadlessException e) {
            e.printStackTrace();
        }
    }
    //获取选定的行
    public void getM(){
        intRow = jTable1.getSelectedRow();
        if(intRow == -1){
            return;
        }
        try {
            find = Integer.parseInt(model.getValueAt(intRow, 0).toString());
            System.err.println(find);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}

class StudentC_jButton1_actionAdpter implements ActionListener {
    private StudentManager studentManager;
    public StudentC_jButton1_actionAdpter(StudentManager studentManager){
        this.studentManager = studentManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        studentManager.jButton1_actionPerformed(e);
    }
}

class StudentC_jButton2_actionAdpter implements ActionListener {
    private StudentManager studentManager;
    public StudentC_jButton2_actionAdpter(StudentManager studentManager){
        this.studentManager = studentManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        studentManager.jButton2_actionPerformed(e);
    }
}

class StudentC_jButton4_actionAdpter implements ActionListener {
    private StudentManager studentManager;
    public StudentC_jButton4_actionAdpter(StudentManager studentManager){
        this.studentManager = studentManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        studentManager.jButton4_actionPerformed(e);
    }
}

class StudentC_jButton5_actionAdapter implements ActionListener {
    private StudentManager studentManager;
    public StudentC_jButton5_actionAdapter(StudentManager studentManager){
        this.studentManager = studentManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        studentManager.jButton5_actionPerformed(e);
    }
}

class StudentC_jButton6_actionAdapter implements ActionListener {
    private StudentManager studentManager;
    public StudentC_jButton6_actionAdapter(StudentManager studentManager){
        this.studentManager = studentManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        studentManager.jButton6_actionPerformed(e);
    }
}

class StudentC_jButton7_actionAdapter implements ActionListener {
    private StudentManager studentManager;
    public StudentC_jButton7_actionAdapter(StudentManager studentManager){
        this.studentManager = studentManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        studentManager.jButton7_actionPerformed(e);
    }
}

class StudentC_jComboBox1_actionAdapter implements ActionListener{
    private StudentManager studentManager;
    public StudentC_jComboBox1_actionAdapter(StudentManager studentManager){
        this.studentManager = studentManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        studentManager.jComboBox1_actionPerformed(e);
    }
}
