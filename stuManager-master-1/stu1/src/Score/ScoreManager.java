package Score;

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
 * Created by dell on 2020/11/4.
 */
public class ScoreManager extends JFrame {
    JPanel contentPane = (JPanel)getContentPane();
    XYLayout xYLayout1 = new XYLayout();
    XYLayout xYLayout2 = new XYLayout();
    JPanel jPanel1 = new JPanel();
    JLabel jLabel1 = new JLabel();
    JScrollPane jScrollPane1 = new JScrollPane();
    JTable jTable1 = new JTable();
    JButton jButton1 = new JButton();
    JButton jButton2 = new JButton();
    JButton jButton3 = new JButton();

    JButton jButton_print = new JButton();
    JButton jButton_sort = new JButton();
    JButton jButton_average = new JButton();
    JButton jButton_failure = new JButton();
    JOptionPane jOptionPane1 = new JOptionPane();
    DefaultTableModel model = new DefaultTableModel();
    dbConn conn = new dbConn();
    String[] arrField = {"成绩编号", "学生学号", "姓名", "课程", "分数"};
    int i_print = 0, i_sortDesc = 0;
    String sql;
    int find;
    int intRow;

    public ScoreManager(){
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void jbInit() throws Exception{
        setTitle("成绩管理");
        contentPane.setLayout(xYLayout1);
        xYLayout1.setWidth(550);
        xYLayout1.setHeight(560);

        jLabel1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 20));
        jLabel1.setText("成 绩 管 理");
        jPanel1.setLayout(xYLayout2);
        jPanel1.setBorder(BorderFactory.createEtchedBorder());
        jScrollPane1.setBorder(BorderFactory.createEtchedBorder());
        jButton1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jButton1.setText("修  改");
        jButton1.addActionListener(new ScoreManager_jButton1_actionAdapter(this));
        jButton2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jButton2.setText("删  除");
        jButton2.addActionListener(new ScoreManager_jButton2_actionAdapter(this));
        jButton3.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jButton3.setText("返  回");
        jButton3.addActionListener(new ScoreManager_jButton3_actionAdapter(this));

        jButton_print.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jButton_print.setText("打  印");
        jButton_print.addActionListener(new ScoreManagere_jButton_print_actionAdapter(this));
        jButton_sort.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jButton_sort.setText("排  序");
        jButton_sort.addActionListener(new ScoreManagere_jButton_sort_actionAdapter(this));
        jButton_average.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jButton_average.setText("平  均");
        jButton_average.addActionListener(new ScoreManagere_jButton_average_actionAdapter(this));
        jButton_failure.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
        jButton_failure.setText("不及格");
        jButton_failure.addActionListener(new ScoreManagere_jButton_failure_actionAdapter(this));

        jScrollPane1.getViewport().add(jTable1);
        contentPane.add(jLabel1, new XYConstraints(208, 13, 135, 43));
        contentPane.add(jPanel1, new XYConstraints(17, 68, 515, 63));
        contentPane.add(jScrollPane1, new XYConstraints(18, 150, 515, 300));
        contentPane.add(jButton1, new XYConstraints(74, 470, 100, -1));
        contentPane.add(jButton2, new XYConstraints(224, 470, 90, -1));
        contentPane.add(jButton3, new XYConstraints(374, 470, 90, -1));

        jPanel1.add(jButton_print, new XYConstraints(10, 15, 80, 25));
        jPanel1.add(jButton_sort, new XYConstraints(147, 15, 80, 25));
        jPanel1.add(jButton_average, new XYConstraints(287, 15, 80, 25));
        jPanel1.add(jButton_failure, new XYConstraints(419, 15, 80, 25));

        //用户等级判断
        if(MainFrame.level.equals("1")){

        }else if(MainFrame.level.equals("2")){
            jButton2.setVisible(false);
            jButton1.setVisible(false);
        }

        //选择专业表
        sql = "select * from tb_score;";
        //刷新
        UpdateRecord();
    }

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
                vec.add(String.valueOf(rs.getInt("scoreId")));
                vec.add(rs.getString("stuNumber"));
                ResultSet rsTmp = conn.getRs("select stuName from tb_student where stuNumber = " + String.valueOf(rs.getString("stuNumber") + ";"));
                while(rsTmp.next()){
                    vec.add(rsTmp.getString("stuName"));
                }
                vec.add(rs.getString("courceName"));
                vec.add(rs.getString("score"));
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
            ScoreChange siadd = new ScoreChange(find);
            siadd.setLocation(400, 200);
            siadd.setSize(465, 410);
            siadd.setVisible(true);
            siadd.setResizable(false);
            siadd.validate();
            this.dispose();
        }
    }

    //获取选定的行
    public void getM(){
        intRow = jTable1.getSelectedRow();
        if(intRow == -1){
            return;
        }
        find = Integer.parseInt(model.getValueAt(intRow, 0).toString());
    }

    //删除
    public void jButton2_actionPerformed(ActionEvent e){
        getM();
        if(intRow == -1){
            jOptionPane1.showMessageDialog(this, "请选择要删除的成绩！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
            return;
        }
        int i = conn.getUpdate("delete from tb_score where scoreId = '" + Integer.valueOf(find) + "';");
        if(i == -1){
            jOptionPane1.showMessageDialog(this, "删除失败！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
        }else{
            jOptionPane1.showMessageDialog(this, "删除成功！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
        }
        UpdateRecord();
    }

    //返回
    public void jButton3_actionPerformed(ActionEvent e){
        this.dispose();
    }

    //打印
    public void jButton_print_actionPerformed(ActionEvent e){
        i_print ++;
        if(i_print % 2 == 1){
            sql = "select * from tb_score order by scoreId desc,stuNumber;";
        }else if(i_print % 2 == 0){
            sql = "select * from tb_score order by score, stuNumber;";
        }
        UpdateRecord();
    }

    //排序
    public void jButton_sort_actionPerformed(ActionEvent e){
        i_sortDesc ++;
        if(i_sortDesc % 2 == 1){
            sql = "select * from tb_score order by score desc, stuNumber;";
        }else if(i_sortDesc % 2 == 0){
            sql = "select * from tb_score order by score, stuNumber;";
        }
        UpdateRecord();
    }

    //平均
    public void jButton_average_actionPerformed(ActionEvent e){
        float f_average = 0;
        int averageBig = 0, averageSmall = 0;
        try {
            //获取平均分
            ResultSet rs_average = conn.getRs("select avg(score) as scoreAverage from tb_score;");
            while(rs_average.next()){
                f_average =Float.valueOf(rs_average.getString("scoreAverage"));
            }

            //获取大于等于平均分的人数
            ResultSet rs_averageBig = conn.getRs("select * from tb_score  where score >= " + f_average + ";");
            while(rs_averageBig.next()){
                averageBig++;
            }

            //获取小于平均分的人数
            ResultSet rs_averageSmall = conn.getRs("select * from tb_score where score < " + f_average + ";");
            while(rs_averageSmall.next()){
                averageSmall++;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        jOptionPane1.showMessageDialog(this,"平均分 = " + f_average + "\n大于等于平均分的人数 = " + averageBig + "\n低于平均分的人数 = " + averageSmall, "提示", JOptionPane.INFORMATION_MESSAGE, null);
    }

    //不及格
    public void jButton_failure_actionPerformed(ActionEvent e){
        int totalNumber = 0, failureNumber = 0;
        try {
            //获取总人数
            ResultSet rs_total = conn.getRs("select * from tb_score;");
            while(rs_total.next()){
                totalNumber ++;
            }
            //获取不及格人数
            ResultSet rs_failure = conn.getRs("select * from tb_score where  score < " + 60 + ";");
            while(rs_failure.next()){
                failureNumber ++;
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        //显示不及格率
        jOptionPane1.showMessageDialog(this, "不及格率 = " + (float) failureNumber / totalNumber * 100 + "%\n不及格人数 =" + failureNumber + "\n总人数= " + totalNumber, "提示", JOptionPane.INFORMATION_MESSAGE,null);
    }
}

class ScoreManager_jButton1_actionAdapter implements ActionListener {
    private ScoreManager scoreManager;
    public ScoreManager_jButton1_actionAdapter(ScoreManager scoreManager){
        this.scoreManager = scoreManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        scoreManager.jButton1_actionPerformed(e);
    }
}

class ScoreManager_jButton2_actionAdapter implements ActionListener {
    private ScoreManager scoreManager;
    public ScoreManager_jButton2_actionAdapter(ScoreManager scoreManager){
        this.scoreManager = scoreManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        scoreManager.jButton2_actionPerformed(e);
    }
}

class ScoreManager_jButton3_actionAdapter implements ActionListener {
    private ScoreManager scoreManager;
    public ScoreManager_jButton3_actionAdapter(ScoreManager scoreManager){
        this.scoreManager = scoreManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        scoreManager.jButton3_actionPerformed(e);
    }
}

class ScoreManagere_jButton_print_actionAdapter implements ActionListener{
    private ScoreManager scoreManager;
    public ScoreManagere_jButton_print_actionAdapter(ScoreManager scoreManager){
        this.scoreManager = scoreManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        scoreManager.jButton_print_actionPerformed(e);
    }
}

class ScoreManagere_jButton_sort_actionAdapter implements ActionListener{
    private ScoreManager scoreManager;
    public ScoreManagere_jButton_sort_actionAdapter(ScoreManager scoreManager){
        this.scoreManager = scoreManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        scoreManager.jButton_sort_actionPerformed(e);
    }
}

class ScoreManagere_jButton_average_actionAdapter implements ActionListener{
    private ScoreManager scoreManager;
    public ScoreManagere_jButton_average_actionAdapter(ScoreManager scoreManager){
        this.scoreManager = scoreManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        scoreManager.jButton_average_actionPerformed(e);
    }
}

class ScoreManagere_jButton_failure_actionAdapter implements ActionListener{
    private ScoreManager scoreManager;
    public ScoreManagere_jButton_failure_actionAdapter(ScoreManager scoreManager){
        this.scoreManager = scoreManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        scoreManager.jButton_failure_actionPerformed(e);
    }
}

