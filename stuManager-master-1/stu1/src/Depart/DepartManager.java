package Depart;

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
 * Created by dell on 2020/11/2.
 */
public class DepartManager extends JFrame {
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
    String[] arrField = {"专业编号", "学院名称", "专业名称"};
    String sql,find;
    int intRow;

    public DepartManager(){
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void jbInit() throws Exception{
        contentPane.setLayout(xYLayout1);
        xYLayout1.setWidth(530);
        xYLayout1.setHeight(540);

        jLabel1.setFont(new java.awt.Font("黑体", Font.PLAIN, 20));
        jLabel1.setText("院 系 信 息 管 理");
        jScrollPane1.setBorder(BorderFactory.createEtchedBorder());
        jButton1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jButton1.setText("修  改");
        jButton1.addActionListener(new DepartManager_jButton1_actionAdapter(this));
        jButton2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jButton2.setText("删  除");
        jButton2.addActionListener(new DepartManager_jButton2_actionAdapter(this));
        jButton3.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
        jButton3.setText("返  回");
        jButton3.addActionListener(new DepartManager_jButton3_actionAdapter(this));
        jScrollPane1.getViewport().add(jTable1);
        contentPane.add(jLabel1, new XYConstraints(177, 14, 177, 39));
        contentPane.add(jScrollPane1, new XYConstraints(10, 60, 510, 400));
        contentPane.add(jButton1, new XYConstraints(100, 480, 90, 35));
        contentPane.add(jButton2, new XYConstraints(220, 480, 90, 35));
        contentPane.add(jButton3, new XYConstraints(340, 480, 90, 35));

        //用户等级判断
        if(MainFrame.level.equals("1")){

        }else if(MainFrame.level.equals("2")){
            jButton2.setVisible(false);
            jButton3.setVisible(false);
        }

        //选择专业表
        sql = "select * from tb_spec;";
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
                vec.add(String.valueOf(rs.getInt("specId")));
                vec.add(rs.getString("departName"));
                vec.add(rs.getString("specName"));
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
        jTable1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jTable1.setRowSelectionAllowed(true);
        jTable1.setShowVerticalLines(true);
    }

    //存储所选数据
    public void getM(){
        intRow =jTable1.getSelectedRow();
        if(intRow == -1){
            return;
        }
        find = model.getValueAt(intRow, 0).toString().trim();
    }

    //修改
    public void jButton1_actionPerformed(ActionEvent e){
        getM();
        if(intRow == -1){
            jOptionPane1.showMessageDialog(this, "请选择要修改的院系！", "提示", JOptionPane.INFORMATION_MESSAGE);
        }else{
            DepartChange siadd = new DepartChange();
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
        //获取选定的行数据
        getM();
        if(intRow == -1){
            jOptionPane1.showMessageDialog(this, "请选择要删除的院系！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
        }
        int i = conn.getUpdate("delete from tb_spec where specId = '" + Integer.valueOf(find) + "';");
        if(i == -1){
            jOptionPane1.showMessageDialog(this, "删除院系失败！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
        }else{
            jOptionPane1.showMessageDialog(this, "删除院系成功！", "提示", JOptionPane.INFORMATION_MESSAGE, null);
        }
        UpdateRecord();
    }

    //返回
    public void jButton3_actionPerformed(ActionEvent e){
        this.dispose();
    }
}

class DepartManager_jButton1_actionAdapter implements ActionListener{
    private DepartManager departManager;
    public DepartManager_jButton1_actionAdapter(DepartManager departManager){
        this.departManager = departManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        departManager.jButton1_actionPerformed(e);
    }
}

class DepartManager_jButton2_actionAdapter implements ActionListener{
    private DepartManager departManager;
    public DepartManager_jButton2_actionAdapter(DepartManager departManager){
        this.departManager = departManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        departManager.jButton2_actionPerformed(e);
    }
}

class DepartManager_jButton3_actionAdapter implements ActionListener{
    private DepartManager departManager;
    public DepartManager_jButton3_actionAdapter(DepartManager departManager){
        this.departManager = departManager;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        departManager.jButton3_actionPerformed(e);
    }
}
