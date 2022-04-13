/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.LopHoc.ChiTietLopHoc;

import BUS.bus_Khachhang;
import BUS.bus_kqht;
import DTO.dto_KhachHang;
import DTO.dto_LopHoc;
import DTO.dto_kqht;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author USER
 */
public class FormThemKhachHang extends javax.swing.JDialog {

    /**
     * Creates new form FormThemKhachHang
     */
    
    private dto_LopHoc lopHoc;
    
    public FormThemKhachHang() {
        initComponents();
        giaoDienBanDau();
    }
    public FormThemKhachHang(dto_LopHoc lop){
        this.setModal(true);
        initComponents();
        this.lopHoc = new dto_LopHoc();
        this.lopHoc = lop;
        giaoDienBanDau();
        reloadDuLieuKhachHang();
    }
     
    // BIẾN TỰ ĐỊNH NGHĨA
    private DefaultTableModel dtmKhachHang;
    private ArrayList<dto_KhachHang> dsKhachHang;
    private dto_KhachHang khDuocChon;
    
    
    // HÀM THÊM KHÁCH HÀNG VÀO LỚP HỌC
    public void themKhachHangVaoLop(){
        boolean kiemTraDieuKien = kiemTraDuDieuKien();
        
        if(kiemTraDieuKien == true){
            
            dto_kqht kqht = new dto_kqht();
            kqht.setMaKh(khDuocChon.getMaKh());
            kqht.setMaLop(this.lopHoc.getMaLop());
            kqht.setNghe(-1);
            kqht.setNoi(-1);
            kqht.setDoc(-1);
            kqht.setViet(-1);
            kqht.setTong(-1);
            
            kqht.setKh(khDuocChon);
            kqht.getKh().setTenLop(this.lopHoc.getTenLop());
            
            int kq = new bus_kqht().themKhachHangVaoLop(kqht);
            
            if(kq > 0){
                JOptionPane.showMessageDialog(null, "Đã thêm khách hàng vào lớp");
                reloadDuLieuKhachHang();
            }
            else
                JOptionPane.showMessageDialog(null, "Lỗi");
        }
    }
    
    // HÀM KIỂM TRA KHÁCH HÀNG ĐƯỢC CHỌN CÓ ĐỦ YÊU CẦU VÀO LỚP HAY KHÔNG
    public boolean kiemTraDuDieuKien() {
        
        layKhachHangDuocChon();
        
        if (khDuocChon.getTenKh() == null) {
            JOptionPane.showMessageDialog(null, "Chưa chọn khách hàng");
            return false;
        } else {

            if (this.khDuocChon.getChungChiCanHoc().getMaCc() != this.lopHoc.getCt().getMaCc()) {
                JOptionPane.showMessageDialog(null, "Chứng chỉ cần học của khách hàng phải giống với chứng chỉ của lớp học");
                return false;
            }

            if (this.khDuocChon.getDiemDauVao() < this.lopHoc.getCt().getDiemDauVao()) {
                JOptionPane.showMessageDialog(null, "Khách hàng không đủ điểm đầu vào");
                return false;

            }
            
            if(this.khDuocChon.getTenLop() != null){
                JOptionPane.showMessageDialog(null, "Khách hàng đã có lớp");
                return false;
            }
            return true;
        }
    }
    
    // HÀM TÌM KHÁCH HÀNG
    public void timKhachHang(String text) {
        ArrayList<dto_KhachHang> dsKhachHangTim = new bus_Khachhang().layDsTimKiem(text);
        
        int size = dsKhachHangTim.size();
       for(int i = 0; i < size; i++){
            dsKhachHangTim.get(i).setChungChiCanHoc(new bus_Khachhang().layChungChi(dsKhachHangTim.get(i).getMaChungChi()));
        }
        reloadTableKhachHang(dsKhachHangTim);
    }
    
    // HÀM SETUP GIAO DIỆN BAN ĐẦU
    public void giaoDienBanDau() {

        setupTableKhachHang();
        reloadDuLieuKhachHang();
    }
    
    // HÀM SETUP DỮ LIỆU KHÁCH HÀNG
    public void reloadDuLieuKhachHang() {
        this.dsKhachHang = new ArrayList<dto_KhachHang>();
        this.dsKhachHang = new bus_Khachhang().layDsKhachHang();
        
        int size = this.dsKhachHang.size();
        for(int i = 0; i < size; i++){
            this.dsKhachHang.get(i).setChungChiCanHoc(new bus_Khachhang().layChungChi(this.dsKhachHang.get(i).getMaChungChi()));
        }
        reloadTableKhachHang(dsKhachHang);
    }
    
    
        // HÀM LẤY KHÁCH HÀNG ĐƯỢC CHỌN
    public void layKhachHangDuocChon() {

        int row = tbKhachHang.getSelectedRow();
        this.khDuocChon = new dto_KhachHang();
        if (row > -1) {
            this.khDuocChon = dsKhachHang.get(row);
                   this.khDuocChon.setDsLichSu(new bus_Khachhang().dsLichSu(this.khDuocChon.getMaKh()));
        this.khDuocChon.setChungChiCanHoc(new bus_Khachhang().layChungChi(this.khDuocChon.getMaChungChi()));
        }
    }
    
    // HÀM LOAD DỮ LIỆU LÊN BẢNG KHÁCH HÀNG
    public void reloadTableKhachHang(ArrayList<dto_KhachHang> dsKh) {

        int stt = 0;
        this.dsKhachHang = new ArrayList<dto_KhachHang>();
        this.dsKhachHang = dsKh;

        dtmKhachHang.setRowCount(0);

        for (dto_KhachHang kh : this.dsKhachHang) {

            stt++;
            Vector<Object> vc = new Vector<Object>();

            vc.add(stt);
            vc.add(kh.getMaKh());
            vc.add(kh.getTenKh());

            if (kh.getGioiTinh() == 1) {
                vc.add("Nam");
            } else {
                vc.add("Nữ");
            }

            vc.add(kh.layNgaySinh());
            vc.add(kh.getSdt());
            vc.add(kh.getChungChiCanHoc().getTenCc());
            vc.add(kh.getDiemDauVao()+"");
            vc.add(kh.getTenLop());

            dtmKhachHang.addRow(vc);
        }

    }
    // HÀM TẠO BẢNG KHÁCH HÀNG
    public void setupTableKhachHang() {
        dtmKhachHang = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        dtmKhachHang.addColumn("STT");
        dtmKhachHang.addColumn("Mã KH");
        dtmKhachHang.addColumn("Họ Tên");
        dtmKhachHang.addColumn("Giới Tính");
        dtmKhachHang.addColumn("Ngày Sinh");
        dtmKhachHang.addColumn("Điện Thoại");
        dtmKhachHang.addColumn("Chứng Chỉ Cần Học");
        dtmKhachHang.addColumn("Điểm Đầu Vào");
        dtmKhachHang.addColumn("Lớp Đang Học");

        tbKhachHang.setModel(dtmKhachHang);

        tbKhachHang.getColumnModel().getColumn(0).setMaxWidth(50);
        tbKhachHang.getColumnModel().getColumn(1).setMaxWidth(80);
        tbKhachHang.getColumnModel().getColumn(2).setMinWidth(150);
        tbKhachHang.getColumnModel().getColumn(2).setMaxWidth(200);
        tbKhachHang.getColumnModel().getColumn(2).setMaxWidth(80);
        tbKhachHang.getColumnModel().getColumn(3).setMaxWidth(80);
        tbKhachHang.getColumnModel().getColumn(3).setMinWidth(80);
        tbKhachHang.getColumnModel().getColumn(4).setMaxWidth(90);
        tbKhachHang.getColumnModel().getColumn(4).setMinWidth(90);
        tbKhachHang.getColumnModel().getColumn(4).setMinWidth(80);
        tbKhachHang.getColumnModel().getColumn(4).setMaxWidth(80);

        tbKhachHang.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        tbKhachHang.getTableHeader().setOpaque(false);
        tbKhachHang.getTableHeader().setForeground(new Color(0, 0, 0));
        tbKhachHang.setSelectionBackground(new Color(0, 64, 128));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtTimKhachHang = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbKhachHang = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(984, 461));

        jPanel1.setBackground(new java.awt.Color(230, 245, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(984, 461));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(51, 0, 102));
        jLabel1.setText("Nhập Thông Tin Khách Hàng");

        txtTimKhachHang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTimKhachHang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKhachHangKeyReleased(evt);
            }
        });

        jScrollPane1.setToolTipText("");
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã KH", "Tên Khách Hàng", "Giới Tính", "Ngày Sinh", "Điện Thoại", "Chứng Chỉ Cần Học", "Điểm Đầu Vào", "Lớp Đang Học", "Ghi Chú"
            }
        ));
        tbKhachHang.setRowHeight(25);
        tbKhachHang.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tbKhachHang);

        jButton1.setBackground(new java.awt.Color(0, 102, 153));
        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Thêm Vào Lớp");
        jButton1.setBorderPainted(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 964, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 309, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 579, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 642, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1000, 681));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        themKhachHangVaoLop();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtTimKhachHangKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKhachHangKeyReleased
        String text = txtTimKhachHang.getText();
        
        if(text.isEmpty())
            reloadDuLieuKhachHang();
        else
            timKhachHang(text);
    }//GEN-LAST:event_txtTimKhachHangKeyReleased


   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbKhachHang;
    private javax.swing.JTextField txtTimKhachHang;
    // End of variables declaration//GEN-END:variables
}
