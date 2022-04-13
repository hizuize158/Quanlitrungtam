package UI.GV_Phong;

import BUS.bus_GiaoVien;
import BUS.bus_Phong;
import DTO.dto_GiaoVien;
import DTO.dto_Phong;
import UI.FormXacMinhNguoiDung;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class UI_GV_Phong extends javax.swing.JPanel {

    /**
     * Creates new form TKB
     */
    public UI_GV_Phong() {
        initComponents();
        giaoDienPhongBanDau();
        giaoDienGiaoVienBanDau();
        
    }
    
    public UI_GV_Phong(boolean isQuanLy){
        initComponents();
        giaoDienPhongBanDau();
        giaoDienGiaoVienBanDau();
        hideBtn();
    }
    
    // BIẾN TỰ ĐỊNH NGHĨA
    private dto_Phong phongDuocChon;
    private dto_GiaoVien gvduocChon;
    private  boolean isCapNhatGv;
    private  boolean isCapNhatPhong;
    private DefaultTableModel dtmGiaoVien;
    private DefaultTableModel dtmPhong;
    private ArrayList<dto_GiaoVien> dsGiaoVien;
    private ArrayList<dto_Phong> dsPhong;
    
    // HÀM ẨN BTN
    public void hideBtn(){
        btnThemGv.setVisible(false);
        btnCapNhatGv.setVisible(false);
        btnXoaGv.setVisible(false);
        btnXoaPhong.setVisible(false);
        btnThemPhong.setVisible(false);
        btnCapNhatPhong.setVisible(false);
        pnThemGv.setVisible(false);
        pnThemPhong.setVisible(false);
    }
    // HÀM THIẾT LẬP GIAO DIỆN PHÒNG HỌC BAND DẦU
    public void giaoDienPhongBanDau(){
        setupTablePhong();
        giaoDienThemPhong();
        
        isCapNhatPhong = false;
        
        this.dsPhong = new ArrayList<dto_Phong>();
        this.dsPhong = new bus_Phong().layDsPhong();
        reloadTablePhong(this.dsPhong);
    }
    
    // HÀM THIẾT LẬP GIAO DIỆN GIÁO VIÊN BAN ĐẦU
    public void giaoDienGiaoVienBanDau(){
        setupTableGiaoVien();
        giaoDienThemGiaoVien();
        
        isCapNhatGv = false;
          
        this.dsGiaoVien = new ArrayList<dto_GiaoVien>();
        this.dsGiaoVien = new bus_GiaoVien().layDsGiaoVien();
        reloadTableGiaoVien(this.dsGiaoVien);    
    }
    
    // HÀM TÌM PHÒNG
    public void timPhong(String text){
        
        ArrayList<dto_Phong> dsPhongTimDuoc = new bus_Phong().timPhong(text);
        
        reloadTablePhong(dsPhongTimDuoc);
    }
    
    // HÀM TÌM GIÁO VIÊN
    public void timGiaoVien(String text){
        
        ArrayList<dto_GiaoVien> dsGiaoVienTimDuoc = new bus_GiaoVien().timGiaoVien(text);
        
        reloadTableGiaoVien(dsGiaoVienTimDuoc);
    }
    
    // HÀM XÓA PHÒNG
    public void xoaPhong(){
        
        FormXacMinhNguoiDung form = new FormXacMinhNguoiDung();
        
        if(form.getLuaChon() == 0){
            
            boolean xacMinh = form.kiemTraMatKhau();
            
            if(xacMinh == true){
                
                int kq = new bus_Phong().xoaPhong(this.phongDuocChon);
                
                if(kq > 0){

                    JOptionPane.showMessageDialog(null, "Đã xóa phòng học");
                    giaoDienPhongBanDau();
                }
                else
                    JOptionPane.showMessageDialog(null, "Lỗi");
                
            }
        }
    }
    // HÀM XÓA GIÁO VIÊN
    public void xoaGiaoVien(){
        
        FormXacMinhNguoiDung form = new FormXacMinhNguoiDung();
        
        if(form.getLuaChon() == 0){
            boolean xacMinh = form.kiemTraMatKhau();
            
            if(xacMinh == true){
                int kq = new bus_GiaoVien().xoaGiaoVien(this.giaoVienDuocChon());

                if(kq > 0){

                    JOptionPane.showMessageDialog(null, "Đã xóa giáo viên");
                    giaoDienGiaoVienBanDau();
                }
                else
                    JOptionPane.showMessageDialog(null, "Lỗi");
            }
            else
                JOptionPane.showMessageDialog(null, "Mật khẩu không chính xác");
        }
    }
    
    // HÀM CẬP NHẬT NHẬT PHÒNG HỌC
    public void capNhatPhong(){     
        
        dto_Phong phongNhap = layPhongNhap();

        if(phongNhap != null){
            
            phongNhap.setMaPhong(this.phongDuocChon.getMaPhong());
            int kq = new bus_Phong().capNhatPhong(phongNhap);
        
            if(kq > 0){
                
                JOptionPane.showMessageDialog(null, "Đã cập nhật phòng học");
                giaoDienPhongBanDau();
            }
            else
                JOptionPane.showMessageDialog(null, "Lỗi");
        }
    }
    //CẬP NHẬT GIÁO VIÊN
    public void capNhatGiaoVien(){
        
        dto_GiaoVien gvNhap = layGiaoVienNhap();
        
        if(gvNhap != null){
            
            gvNhap.setMaGv(this.gvduocChon.getMaGv());
            
            int kq = new bus_GiaoVien().capNhatGiaoVien(gvNhap);
            
            if(kq > 0){
                
                JOptionPane.showMessageDialog(null, "Đã cập nhật giáo viên");
                giaoDienGiaoVienBanDau();
            }
            else
                JOptionPane.showMessageDialog(null, "Lỗi");
        }
        
    }
    
    // THÊM PHÒNG HỌC
    public void themPhong(){
        
        dto_Phong phongNhapVao = layPhongNhap();
        
        if(phongNhapVao != null){
            
            int kq = new bus_Phong().themPhong(phongNhapVao);
            
            if(kq > 0){
                
                JOptionPane.showMessageDialog(null, "Đã tạo phòng học mới");
                giaoDienPhongBanDau();
            }
            else
                JOptionPane.showMessageDialog(null, "Lỗi");
        }
    }
    // THÊM GIÁO VIÊN
    public void themGiaoVien(){
        
        dto_GiaoVien gvNhapVao =layGiaoVienNhap();
        
        if(gvNhapVao != null){
            
            int kq = new bus_GiaoVien().themGiaoVien(gvNhapVao);
            
            if(kq > 0){
                
                JOptionPane.showMessageDialog(null, "Đã tạo giáo viên mới");
                giaoDienGiaoVienBanDau();
            }
            else
                JOptionPane.showMessageDialog(null, "Lỗi");
        }
        
    }
    
    // HÀM LẤY THÔNG TIN PHÒNG NHẬP VÀO
    public dto_Phong layPhongNhap(){
        
        dto_Phong phong = null;
        
        String tenPhong = txtTenPhong.getText();
        
        if(tenPhong.isEmpty()){
            JOptionPane.showMessageDialog(null, "Chưa nhập đủ thông tin Phòng Học");
            return null;
        }
        
        phong = new dto_Phong();
        phong.setTenPhong(tenPhong);
        
        return phong;
    }
    
    // HÀM LẤY THÔNG TIN GIÁO VIÊN NHẬP VÀO
    public dto_GiaoVien layGiaoVienNhap(){
        
        dto_GiaoVien gv = null;
        
        String tenGv = txtTenGv.getText();
        
        int gioiTinh = cbGioiTinhGv.getSelectedIndex() +1 ;
        
        String strSdt = txtDienThoaiGv.getText();
        int sdt;
        
        String quocTich = txtQuocTich.getText();
        
        if(tenGv.isEmpty() || strSdt.isEmpty() || quocTich.isEmpty()){
            JOptionPane.showMessageDialog(null, "Chưa nhập đủ thông tin Giáo Viên");
            return null;
        }
        
        try{
            sdt = Integer.parseInt(strSdt);
        }
        catch(Exception ex){
            ex.printStackTrace();
            return null;
        }
        
        gv = new dto_GiaoVien();
        
        gv.setTenGv(tenGv);
        gv.setGioiTinh(gioiTinh);
        gv.setSdt(strSdt);
        gv.setQuocTich(quocTich);
        
        return gv;
    }
    
    // HÀM SET GIAO DIỆN CẬP NHẬT PHÒNG
    public void giaoDienCapNhatPhong(dto_Phong phong){
        
        lblPhong.setText("Cập Nhật Phòng");
        
        if(phong != null){
            
            txtTenPhong.setText(phong.getTenPhong());
            txtMaPhong.setText(phong.getMaPhong()+"");
        }
        else
            JOptionPane.showMessageDialog(null, "Chưa chọn phòng học");
    }
    
    // HÀM SET GIAO DIỆN CẬP NHẬT GIÁO VIÊN
    public void giaoDienCapNhatGiaoVien(dto_GiaoVien gv){
        
        lblGv.setText("Cập Nhập ");
 
        if(gv != null){
            
            txtTenGv.setText(gv.getTenGv());
            txtMaGv.setText(gv.getMaGv()+"");
            cbGioiTinhGv.setSelectedIndex(gv.getGioiTinh()-1);
            txtDienThoaiGv.setText(gv.getSdt());
            txtQuocTich.setText(gv.getQuocTich());
        }
        else
            JOptionPane.showMessageDialog(null, "Chưa chọn giáo viên");
    }
    
    // HÀM SET GIAO DIỆN THÊM PHÒNG
    public void giaoDienThemPhong(){
        lblPhong.setText("Tạo Phòng Học Mới");
        txtTenPhong.setText("");
        txtMaPhong.setText("");
    }
    
    // HÀM SET GIAO DIỆN THÊM GIÁO VIÊN
    public void giaoDienThemGiaoVien(){
        lblGv.setText("Tạo Giáo Viên Mới");
        txtTenGv.setText("");
        txtDienThoaiGv.setText("");
        txtQuocTich.setText("");
        txtMaGv.setText("");
    }
    
    // HÀM LẤY PHÒNG ĐƯỢC CHỌN
    public dto_Phong phongDuocChon(){
        
        int row = tbPhong.getSelectedRow();
        dto_Phong phong = null;
        if(row > -1){
            phong = new dto_Phong();
            phong = dsPhong.get(row);
            
            this.phongDuocChon = new dto_Phong();
            this.phongDuocChon = phong;
        }
        
        return phong;
    }
    
    // HÀM LẤY GIÁO VIÊN ĐƯỢC CHỌN
    public dto_GiaoVien giaoVienDuocChon(){
        
        int row = tbGiaoVien.getSelectedRow();
        dto_GiaoVien gv = null;
        if(row > -1){
            gv = new dto_GiaoVien();
            gv = dsGiaoVien.get(row);
            
            this.gvduocChon = new dto_GiaoVien();
            this.gvduocChon = gv;
        }
        return gv;
    }
    
    // HÀM LOAD DỮ LIỆU LÊN BẢNG PHÒNG
    public void reloadTablePhong(ArrayList<dto_Phong> dsP) {
        int stt = 0;
        this.dsPhong = new ArrayList<dto_Phong>();
        this.dsPhong = dsP;
        
        dtmPhong.setRowCount(0);

        for (dto_Phong phong : this.dsPhong) {

            stt++;
            Vector<Object> vc = new Vector<Object>();
            
            vc.add(stt);
            vc.add(phong.getMaPhong());
            vc.add(phong.getTenPhong());

            dtmPhong.addRow(vc);
        }
    }
    
    // HÀM LOAD DỮ LIỆU LÊN BẢNG GIÁO VIÊN
    public void reloadTableGiaoVien(ArrayList<dto_GiaoVien> dsGv) {

        int stt = 0;
        this.dsGiaoVien = new ArrayList<dto_GiaoVien>();
        this.dsGiaoVien = dsGv;
        
        dtmGiaoVien.setRowCount(0);

        for (dto_GiaoVien gv : this.dsGiaoVien) {

            stt++;
            Vector<Object> vc = new Vector<Object>();
            
            vc.add(stt);
            vc.add(gv.getMaGv());
            vc.add(gv.getTenGv());
            
            if(gv.getGioiTinh() == 1)
                vc.add("Nam");
            else
                vc.add("Nữ");
            
            vc.add(gv.getSdt());
            vc.add(gv.getQuocTich());

            dtmGiaoVien.addRow(vc);
        }

    }
    
    // HÀM TẠO BẢNG PHÒNG
    public void setupTablePhong() {
        dtmPhong = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        dtmPhong.addColumn("STT");
        dtmPhong.addColumn("Mã Phòng");
        dtmPhong.addColumn("Tên Phòng");


        tbPhong.setModel(dtmPhong);

        tbPhong.getColumnModel().getColumn(0).setMaxWidth(50);

        tbPhong.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        tbPhong.getTableHeader().setOpaque(false);
        tbPhong.getTableHeader().setForeground(new Color(0, 0, 0));
        tbPhong.setSelectionBackground(new Color(0, 64, 128));

    }
    // HÀM TẠO BẢNG GIÁO VIÊN
    public void setupTableGiaoVien() {
        dtmGiaoVien = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        dtmGiaoVien.addColumn("STT");
        dtmGiaoVien.addColumn("Mã GV");
        dtmGiaoVien.addColumn("Tên Giáo Viên");
        dtmGiaoVien.addColumn("Giới Tính");
        dtmGiaoVien.addColumn("Điện Thoại");
        dtmGiaoVien.addColumn("Quốc Tịch");

        tbGiaoVien.setModel(dtmGiaoVien);

        tbGiaoVien.getColumnModel().getColumn(0).setMaxWidth(50);
        tbGiaoVien.getColumnModel().getColumn(1).setMinWidth(80);
        tbGiaoVien.getColumnModel().getColumn(1).setMaxWidth(80);
        tbGiaoVien.getColumnModel().getColumn(2).setMinWidth(150);
        tbGiaoVien.getColumnModel().getColumn(2).setMaxWidth(150);
        tbGiaoVien.getColumnModel().getColumn(3).setMinWidth(80);
        tbGiaoVien.getColumnModel().getColumn(3).setMaxWidth(80);


        tbGiaoVien.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        tbGiaoVien.getTableHeader().setOpaque(false);
        tbGiaoVien.getTableHeader().setForeground(new Color(0, 0, 0));
        tbGiaoVien.setSelectionBackground(new Color(0, 64, 128));

    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnGiaoVien = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbGiaoVien = new javax.swing.JTable();
        txtTimGv = new javax.swing.JTextField();
        pnThemGv = new javax.swing.JPanel();
        lblGv = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTenGv = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cbGioiTinhGv = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtDienThoaiGv = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnXacNhanGv = new javax.swing.JButton();
        txtQuocTich = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtMaGv = new javax.swing.JTextField();
        btnThemGv = new javax.swing.JButton();
        btnCapNhatGv = new javax.swing.JButton();
        btnXoaGv = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        pnPhong = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbPhong = new javax.swing.JTable();
        txtTimPhong = new javax.swing.JTextField();
        pnThemPhong = new javax.swing.JPanel();
        lblPhong = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtTenPhong = new javax.swing.JTextField();
        btnXacNhanPhong = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        txtMaPhong = new javax.swing.JTextField();
        btnThemPhong = new javax.swing.JButton();
        btnCapNhatPhong = new javax.swing.JButton();
        btnXoaPhong = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setBackground(new java.awt.Color(230, 245, 255));
        setMinimumSize(new java.awt.Dimension(1350, 634));
        setPreferredSize(new java.awt.Dimension(1350, 634));

        pnGiaoVien.setBackground(new java.awt.Color(230, 245, 255));
        pnGiaoVien.setMinimumSize(new java.awt.Dimension(800, 578));

        jLabel1.setText("Tìm Kiếm");

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(800, 23));

        tbGiaoVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã GV", "Tên Giáo Viên", "Giới Tính", "Điện Thoại", "Quốc Tịch"
            }
        ));
        tbGiaoVien.setRowHeight(30);
        tbGiaoVien.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbGiaoVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbGiaoVienMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbGiaoVien);
        if (tbGiaoVien.getColumnModel().getColumnCount() > 0) {
            tbGiaoVien.getColumnModel().getColumn(3).setHeaderValue("Giới Tính");
            tbGiaoVien.getColumnModel().getColumn(4).setHeaderValue("Điện Thoại");
            tbGiaoVien.getColumnModel().getColumn(5).setHeaderValue("Quốc Tịch");
        }

        txtTimGv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimGvActionPerformed(evt);
            }
        });
        txtTimGv.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimGvKeyReleased(evt);
            }
        });

        pnThemGv.setBackground(new java.awt.Color(248, 248, 248));
        pnThemGv.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)));

        lblGv.setBackground(new java.awt.Color(0, 0, 102));
        lblGv.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblGv.setForeground(new java.awt.Color(0, 0, 102));
        lblGv.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGv.setText("Tạo Giáo Viên Mới");
        lblGv.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 102)));

        jLabel3.setText("Họ Tên");

        jLabel4.setText("Giới Tính");

        cbGioiTinhGv.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));

        jLabel5.setText("Điện Thoại");

        jLabel6.setText("Quốc Tịch");

        btnXacNhanGv.setBackground(new java.awt.Color(0, 102, 153));
        btnXacNhanGv.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXacNhanGv.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhanGv.setText("Xác Nhận");
        btnXacNhanGv.setToolTipText("Xác Nhận");
        btnXacNhanGv.setBorder(null);
        btnXacNhanGv.setContentAreaFilled(false);
        btnXacNhanGv.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXacNhanGv.setFocusable(false);
        btnXacNhanGv.setOpaque(true);
        btnXacNhanGv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanGvActionPerformed(evt);
            }
        });

        jLabel2.setText("Mã GV");

        txtMaGv.setEditable(false);

        javax.swing.GroupLayout pnThemGvLayout = new javax.swing.GroupLayout(pnThemGv);
        pnThemGv.setLayout(pnThemGvLayout);
        pnThemGvLayout.setHorizontalGroup(
            pnThemGvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThemGvLayout.createSequentialGroup()
                .addComponent(lblGv, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXacNhanGv, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnThemGvLayout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(pnThemGvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaGv, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(pnThemGvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTenGv, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addGap(18, 18, 18)
                .addGroup(pnThemGvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbGioiTinhGv, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(pnThemGvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDienThoaiGv, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(pnThemGvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(txtQuocTich, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 110, Short.MAX_VALUE))
        );
        pnThemGvLayout.setVerticalGroup(
            pnThemGvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThemGvLayout.createSequentialGroup()
                .addGroup(pnThemGvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnThemGvLayout.createSequentialGroup()
                        .addGroup(pnThemGvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnXacNhanGv, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblGv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 37, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThemGvLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(pnThemGvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2))))
                .addGroup(pnThemGvLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenGv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbGioiTinhGv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDienThoaiGv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuocTich, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMaGv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnThemGv.setBackground(new java.awt.Color(230, 245, 255));
        btnThemGv.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemGv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/taomoi.png"))); // NOI18N
        btnThemGv.setToolTipText("Tạo Giáo Viên Mới");
        btnThemGv.setContentAreaFilled(false);
        btnThemGv.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThemGv.setFocusable(false);
        btnThemGv.setOpaque(true);
        btnThemGv.setPreferredSize(new java.awt.Dimension(183, 40));
        btnThemGv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemGvActionPerformed(evt);
            }
        });

        btnCapNhatGv.setBackground(new java.awt.Color(230, 245, 255));
        btnCapNhatGv.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCapNhatGv.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatGv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/capnhat.png"))); // NOI18N
        btnCapNhatGv.setToolTipText("Cập Nhật Thông Tin Giáo Viên");
        btnCapNhatGv.setContentAreaFilled(false);
        btnCapNhatGv.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCapNhatGv.setFocusable(false);
        btnCapNhatGv.setMaximumSize(new java.awt.Dimension(129, 49));
        btnCapNhatGv.setMinimumSize(new java.awt.Dimension(129, 49));
        btnCapNhatGv.setOpaque(true);
        btnCapNhatGv.setPreferredSize(new java.awt.Dimension(129, 49));
        btnCapNhatGv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatGvActionPerformed(evt);
            }
        });

        btnXoaGv.setBackground(new java.awt.Color(230, 245, 255));
        btnXoaGv.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoaGv.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaGv.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/xoa.png"))); // NOI18N
        btnXoaGv.setToolTipText("Xóa Giáo Viên");
        btnXoaGv.setContentAreaFilled(false);
        btnXoaGv.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoaGv.setFocusable(false);
        btnXoaGv.setMaximumSize(new java.awt.Dimension(129, 49));
        btnXoaGv.setMinimumSize(new java.awt.Dimension(129, 49));
        btnXoaGv.setOpaque(true);
        btnXoaGv.setPreferredSize(new java.awt.Dimension(129, 49));
        btnXoaGv.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaGvActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(230, 245, 255));
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/gv.png"))); // NOI18N
        jButton2.setBorderPainted(false);
        jButton2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout pnGiaoVienLayout = new javax.swing.GroupLayout(pnGiaoVien);
        pnGiaoVien.setLayout(pnGiaoVienLayout);
        pnGiaoVienLayout.setHorizontalGroup(
            pnGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnGiaoVienLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnGiaoVienLayout.createSequentialGroup()
                        .addGroup(pnGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnThemGv, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(pnGiaoVienLayout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimGv, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThemGv, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCapNhatGv, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaGv, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11))))
        );
        pnGiaoVienLayout.setVerticalGroup(
            pnGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnGiaoVienLayout.createSequentialGroup()
                .addGroup(pnGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pnGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnThemGv, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnCapNhatGv, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(btnXoaGv, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnGiaoVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTimGv, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(12, 12, 12)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnThemGv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnPhong.setBackground(new java.awt.Color(230, 245, 255));
        pnPhong.setMinimumSize(new java.awt.Dimension(451, 603));

        jLabel7.setText("Tìm Kiếm");

        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbPhong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "STT", "Mã Phòng", "Tên Phòng"
            }
        ));
        tbPhong.setRowHeight(30);
        tbPhong.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbPhongMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tbPhong);

        txtTimPhong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimPhongKeyReleased(evt);
            }
        });

        pnThemPhong.setBackground(new java.awt.Color(248, 248, 248));
        pnThemPhong.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)));

        lblPhong.setBackground(new java.awt.Color(0, 0, 102));
        lblPhong.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblPhong.setForeground(new java.awt.Color(0, 0, 102));
        lblPhong.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPhong.setText("Tạo Phòng Học Mới");
        lblPhong.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(0, 0, 102)));

        jLabel9.setText("Tên Phòng");

        btnXacNhanPhong.setBackground(new java.awt.Color(0, 102, 153));
        btnXacNhanPhong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXacNhanPhong.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhanPhong.setText("Xác Nhận");
        btnXacNhanPhong.setToolTipText("Xác Nhận");
        btnXacNhanPhong.setBorder(null);
        btnXacNhanPhong.setContentAreaFilled(false);
        btnXacNhanPhong.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXacNhanPhong.setFocusable(false);
        btnXacNhanPhong.setOpaque(true);
        btnXacNhanPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanPhongActionPerformed(evt);
            }
        });

        jLabel8.setText("Mã Phòng");

        txtMaPhong.setEditable(false);

        javax.swing.GroupLayout pnThemPhongLayout = new javax.swing.GroupLayout(pnThemPhong);
        pnThemPhong.setLayout(pnThemPhongLayout);
        pnThemPhongLayout.setHorizontalGroup(
            pnThemPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThemPhongLayout.createSequentialGroup()
                .addComponent(lblPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXacNhanPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnThemPhongLayout.createSequentialGroup()
                .addGap(67, 67, 67)
                .addGroup(pnThemPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtMaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(35, 35, 35)
                .addGroup(pnThemPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(txtTenPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnThemPhongLayout.setVerticalGroup(
            pnThemPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThemPhongLayout.createSequentialGroup()
                .addGroup(pnThemPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnXacNhanPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnThemPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnThemPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        btnThemPhong.setBackground(new java.awt.Color(230, 245, 255));
        btnThemPhong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/taomoi.png"))); // NOI18N
        btnThemPhong.setToolTipText("Tạo Phòng Học Mới");
        btnThemPhong.setContentAreaFilled(false);
        btnThemPhong.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThemPhong.setFocusable(false);
        btnThemPhong.setOpaque(true);
        btnThemPhong.setPreferredSize(new java.awt.Dimension(183, 40));
        btnThemPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemPhongActionPerformed(evt);
            }
        });

        btnCapNhatPhong.setBackground(new java.awt.Color(230, 245, 255));
        btnCapNhatPhong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCapNhatPhong.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/capnhat.png"))); // NOI18N
        btnCapNhatPhong.setToolTipText("Cập Nhật Phòng Học");
        btnCapNhatPhong.setContentAreaFilled(false);
        btnCapNhatPhong.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCapNhatPhong.setFocusable(false);
        btnCapNhatPhong.setMaximumSize(new java.awt.Dimension(129, 49));
        btnCapNhatPhong.setMinimumSize(new java.awt.Dimension(129, 49));
        btnCapNhatPhong.setOpaque(true);
        btnCapNhatPhong.setPreferredSize(new java.awt.Dimension(129, 49));
        btnCapNhatPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatPhongActionPerformed(evt);
            }
        });

        btnXoaPhong.setBackground(new java.awt.Color(230, 245, 255));
        btnXoaPhong.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoaPhong.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/xoa.png"))); // NOI18N
        btnXoaPhong.setToolTipText("Xóa Phòng Học");
        btnXoaPhong.setContentAreaFilled(false);
        btnXoaPhong.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoaPhong.setFocusable(false);
        btnXoaPhong.setMaximumSize(new java.awt.Dimension(129, 49));
        btnXoaPhong.setMinimumSize(new java.awt.Dimension(129, 49));
        btnXoaPhong.setOpaque(true);
        btnXoaPhong.setPreferredSize(new java.awt.Dimension(129, 49));
        btnXoaPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaPhongActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(230, 245, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/phong.png"))); // NOI18N
        jButton1.setBorderPainted(false);
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout pnPhongLayout = new javax.swing.GroupLayout(pnPhong);
        pnPhong.setLayout(pnPhongLayout);
        pnPhongLayout.setHorizontalGroup(
            pnPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnPhongLayout.createSequentialGroup()
                .addGroup(pnPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnPhongLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pnThemPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnPhongLayout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel7)
                                .addGap(18, 18, 18)
                                .addComponent(txtTimPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                                .addComponent(btnThemPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnCapNhatPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnXoaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap())
        );
        pnPhongLayout.setVerticalGroup(
            pnPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnPhongLayout.createSequentialGroup()
                .addGroup(pnPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXoaPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnPhongLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(pnPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnPhongLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnPhongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnThemPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCapNhatPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(pnThemPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(pnGiaoVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addComponent(pnPhong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnPhong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnGiaoVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCapNhatGvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatGvActionPerformed
        
        dto_GiaoVien gv = new dto_GiaoVien();
        gv = giaoVienDuocChon();
        
        if(gv != null){
            isCapNhatGv = true;
            giaoDienCapNhatGiaoVien(gv);       
        }
        else
            JOptionPane.showMessageDialog(null, "Chưa chọn giáo viên");
   
    }//GEN-LAST:event_btnCapNhatGvActionPerformed

    private void btnXoaGvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaGvActionPerformed
        
        dto_GiaoVien gv = new dto_GiaoVien();
        gv = giaoVienDuocChon();
        
        if(gv != null){
            isCapNhatGv = false;
            xoaGiaoVien();       
        }
        else
            JOptionPane.showMessageDialog(null, "Chưa chọn giáo viên");
    }//GEN-LAST:event_btnXoaGvActionPerformed

    private void btnXacNhanGvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanGvActionPerformed
        
        
        if(isCapNhatGv == false){
            themGiaoVien();
        }
        else
            capNhatGiaoVien();
    }//GEN-LAST:event_btnXacNhanGvActionPerformed

    private void btnXacNhanPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanPhongActionPerformed
        
        if(isCapNhatPhong == false){
            
            themPhong();
        }
        else{
            capNhatPhong();
        }
    }//GEN-LAST:event_btnXacNhanPhongActionPerformed

    private void btnCapNhatPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatPhongActionPerformed
        
        dto_Phong phong = new dto_Phong();
        phong = phongDuocChon();
        
        if(phong != null){
            isCapNhatPhong = true;
            giaoDienCapNhatPhong(phong);  
        }
        else
            JOptionPane.showMessageDialog(null, "Chưa chọn phòng");
    }//GEN-LAST:event_btnCapNhatPhongActionPerformed

    private void btnXoaPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaPhongActionPerformed
        
        dto_Phong phong = new dto_Phong();
        phong = phongDuocChon();
        
        if(phong != null){
            isCapNhatPhong = true;
            xoaPhong();  
        }
        else
            JOptionPane.showMessageDialog(null, "Chưa chọn phòng");
    }//GEN-LAST:event_btnXoaPhongActionPerformed

    private void btnThemPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemPhongActionPerformed
        isCapNhatPhong  = false;
        giaoDienThemPhong();
    }//GEN-LAST:event_btnThemPhongActionPerformed

    private void btnThemGvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemGvActionPerformed
        isCapNhatGv = false;
        giaoDienThemGiaoVien();
    }//GEN-LAST:event_btnThemGvActionPerformed

    private void tbGiaoVienMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbGiaoVienMousePressed
        giaoDienThemGiaoVien();
    }//GEN-LAST:event_tbGiaoVienMousePressed

    private void txtTimGvActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimGvActionPerformed

    }//GEN-LAST:event_txtTimGvActionPerformed

    private void txtTimGvKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimGvKeyReleased
        String text = txtTimGv.getText();
        
        timGiaoVien(text);

    }//GEN-LAST:event_txtTimGvKeyReleased

    private void txtTimPhongKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimPhongKeyReleased
       String text = txtTimPhong.getText();
       timPhong(text);
    }//GEN-LAST:event_txtTimPhongKeyReleased

    private void tbPhongMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPhongMousePressed
        giaoDienThemPhong();
    }//GEN-LAST:event_tbPhongMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhatGv;
    private javax.swing.JButton btnCapNhatPhong;
    private javax.swing.JButton btnThemGv;
    private javax.swing.JButton btnThemPhong;
    private javax.swing.JButton btnXacNhanGv;
    private javax.swing.JButton btnXacNhanPhong;
    private javax.swing.JButton btnXoaGv;
    private javax.swing.JButton btnXoaPhong;
    private javax.swing.JComboBox<String> cbGioiTinhGv;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblGv;
    private javax.swing.JLabel lblPhong;
    private javax.swing.JPanel pnGiaoVien;
    private javax.swing.JPanel pnPhong;
    private javax.swing.JPanel pnThemGv;
    private javax.swing.JPanel pnThemPhong;
    private javax.swing.JTable tbGiaoVien;
    private javax.swing.JTable tbPhong;
    private javax.swing.JTextField txtDienThoaiGv;
    private javax.swing.JTextField txtMaGv;
    private javax.swing.JTextField txtMaPhong;
    private javax.swing.JTextField txtQuocTich;
    private javax.swing.JTextField txtTenGv;
    private javax.swing.JTextField txtTenPhong;
    private javax.swing.JTextField txtTimGv;
    private javax.swing.JTextField txtTimPhong;
    // End of variables declaration//GEN-END:variables
}
