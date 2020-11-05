package Course;

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
 * Created by dell on 2020/11/3.
 */
public class CourseManager extends JFrame {
    JPanel contentPane = (JPanel)getContentPane();
    XYLayout xYLayout1 = new XYLayout();
    JLabel jLabel1 = new JLabel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTable jTable1 = new JTable();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JButton jButton3 = new JButton();
    JOptionPane jOptionPane1 = new JOptionPane();
    DefaultTableModel model = new DefaultTableModel();
    dbConn conn = new dbConn();
    String[] arrField = {"课程编号", "专业名称","课程名称", "课堂学分"};
    String sql;
    static int find;
    int intRow;

    public CourseManager(){
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception{
        contentPane.setLayout(xYLayout1);
        xYLayout1.setWidth(550);
        xYLayout1.setHeight(560);
        setTitle("课程管理");

        jLabel1.setFont(new java.awt.Font("黑体", Font.BOLD, 20));
        jLabel1.setText("课 程 管 理");
        jButton1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jButton1.setText("修  改");
        jButton1.addActionListener(new CourseManager_jButton1_actionAdapter(this));
        jButton2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jButton2.setText("删  除");
        jButton2.addActionListener(new CourseManager_jButton2_actionAdapter(this));
        jButton3.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jButton3.setText("返  回");
        jButton3.addActionListener(new CourseManager_jButton3_actionAdapter(this));
        jScrollPane1.setBorder(BorderFactory.createEtchedBorder());
        jScrollPane1.getViewport().add(jTable1);
        contentPane.add(jLabel1, new XYConstraints(208, 13, 135, 43));
        contentPane.add(jScrollPane1, new XYConstraints(18, 60, 490, 400));
        contentPane.add(jButton1, new XYConstraints(74, 480, 100, -1));
        contentPane.add(jButton3, new XYConstraints(374, 480, 100, -1));
        contentPane.add(jButton2, new XYConstraints(224, 480, 100, -1));

        //用户等级判断
        if(MainFrame.level.equals("1")){

        }else if(MainFrame.level.equals("2")){
            jButton1.setVisible(false);
            jButton2.setVisible(false);
        }

        //选择专业表
        sql = "select * from tb_cource;";
        //刷新
        UpdateRecord();
    }

    //刷新
    public void UpdateRecord(){
        Object[][] arrTmp = {};
        Vector vec;
        model = new DefaultTableModel(arrTmp, arrField);
        jTable1 = new JTable(model);
        jScrollPane1.getViewport().add(jTable1, null);
        ResultSet rs = conn.getRs(sql);
        try {
            while(rs.next()){
                vec = new Vector();
                vec.add(String.valueOf(rs.getInt("courceId")));
                vec.add(rs.getString("courceSpecName"));
                vec.add(rs.getString("courceName"));
                vec.add(rs.getString("courceHour"));
                model.addRow(vec);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        jScrollPane1.getHorizontalScrollBar();
        jTable1.setGridColor(Color.blue);
        jTable1.setDragEnabled(true);
        jTable1.setSelectionBackground(Color.green);
        jTable1.setSelectionForeground(Color.red);
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.setRowSelectionAllowed(true);
        jTable1.setShowVerticalLines(true);
    }

    //修改
    public void jButton1_actionPerformed(ActionEvent e){
        getM();
        if(find > 0){
            CourseChange siadd = new CourseChange(find);
            siadd.setLocation(400, 200);
            siadd.setSize(465, 310);
            siadd.setVisible(true);
            siadd.setResizable(false);
            siadd.validate();
            this.dispose();
        }
    }

    //删除
    public void jButton2_actionPerformed(ActionEvent e){
        getM();
        if(intRow == -1){
            jOptionPane1.showMessageDialog(this, "请选择要删除的课程！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
            return;
        }
        int i = conn.getUpdate("delete from tb_cource where courceId = " + Integer.valueOf(find) + ";");
        if(i == -1){
            jOptionPane1.showMessageDialog(this, "删除失败！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
        }else{
            jOptionPane1.showMessageDialog(this, "删除成功！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
        }
        UpdateRecord();
    }

    //获取选定的行
    public void getM(){
        intRow = jTable1.getSelectedRow();
        if(intRow == -1){
            return;
        }
        find = Integer.parseInt(model.getValueAt(intRow, 0).toString());
    }

    //返回
    public void jButton3_actionPerformed(ActionEvent e){
        this.dispose();
    }
}

class CourseManager_jButton1_actionAdapter implements ActionListener{
    private CourseManager courseManager;
    public CourseManager_jButton1_actionAdapter(CourseManager courseManager){
        this.courseManager = courseManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        courseManager.jButton1_actionPerformed(e);
    }
}

class CourseManager_jButton2_actionAdapter implements ActionListener{
    private CourseManager courseManager;
    public CourseManager_jButton2_actionAdapter(CourseManager courseManager){
        this.courseManager = courseManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        courseManager.jButton2_actionPerformed(e);
    }
}

class CourseManager_jButton3_actionAdapter implements ActionListener{
    private CourseManager courseManager;
    public CourseManager_jButton3_actionAdapter(CourseManager courseManager){
        this.courseManager = courseManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        courseManager.jButton3_actionPerformed(e);
    }
}