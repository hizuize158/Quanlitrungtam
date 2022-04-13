package UI.ChuongTrinh;

import BUS.bus_ChungChi;
import BUS.bus_ChuongTrinh;
import DTO.dto_ChungChi;
import DTO.dto_ChuongTrinh;
import UI.ChungChi.UI_ChungChi;
import UI.FormXacMinhNguoiDung;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class UI_ChuongTrinh extends javax.swing.JPanel {

    /**
     * Creates new form ChuongTrinhUI
     */
    public UI_ChuongTrinh() {
        initComponents();
        setGiaoDienBanDau();
        reloadDuLieu();
    }
    public UI_ChuongTrinh(boolean isNotQuanLy){
        initComponents();
        setGiaoDienBanDau();
        reloadDuLieu();    
        hideBtnCt();
    }

    public void hideBtnCt() {
        btnCapNhatChuongTrinh.setVisible(false);
        btnThemChuongTrinh.setVisible(false);
        btnXoaChuongTrinh.setVisible(false);
        btnChungChi.setVisible(false);
        btnXacNhan.setVisible(false);
    }

    // LẤY TRẠNG THÁI CỦA checkbox hiển thị trương trình đóng
    public boolean isCkChuongTrinhDong() {
        return ckCtDong.isSelected();
    }

    //Biến tự định nghĩa  
    private ArrayList<dto_ChuongTrinh> dsChuongTrinh;
    private ArrayList<dto_ChungChi> dsChungChi;
    private DefaultTableModel dtmChuongTrinh = new DefaultTableModel();
    private dto_ChuongTrinh ctDuocChon;
    private dto_ChungChi ccDuocChon;
    boolean isCapNhat;

    
    // HÀM TÌM CHƯƠNG TRÌNH
    public void timChuongTrinh(String text){
        ArrayList<dto_ChuongTrinh> dsChuongTrinhTim = new bus_ChuongTrinh().layDsTimKiem(text);
        
        reloadTable(dsChuongTrinhTim);
    }
    // HÀM XÓA CHƯƠNG TRÌNH
    public void xoaChuongTrinh() {

        dto_ChuongTrinh ctSelected = new dto_ChuongTrinh();

        ctSelected = layChuongTrinhDuocChon();

        if (ctSelected != null) {

            boolean ketQua = new FormXacMinhNguoiDung().kiemTraMatKhau();

            if (ketQua == true) {

                int rs = new bus_ChuongTrinh().xoaChuongTrinh(ctSelected.getMaCt());

                if (rs == 0) {
                    JOptionPane.showMessageDialog(null, "Lỗi");
                } else {
                    JOptionPane.showMessageDialog(null, "Đã Xóa");
                    reloadDuLieu();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Mật Khẩu Không Chính Xác");
            }
        }
    }

    
    // HÀM CẬP NHẬT CHƯƠNG TRÌNH
    public void capNhatChuongTrinh(){
        dto_ChuongTrinh ct = new dto_ChuongTrinh();
        ct = layThongTinNhap();
        
        if (ct != null) {
             ct.setMaCt(this.ctDuocChon.getMaCt());
            
            int rs = new bus_ChuongTrinh().capNhatChuongTrinh(ct);

            if (rs == 0) {

                JOptionPane.showMessageDialog(null, "Lỗi");
            } 
            else {
                giaoDienThem();
                JOptionPane.showMessageDialog(null, "Đã cập nhật chương trình");
                reloadDuLieu();
            }
        }
    }
    // HÀM THÊM CHƯƠNG TRÌNH
    public void themChuongTrinh(){
        dto_ChuongTrinh ct = new dto_ChuongTrinh();
        ct = layThongTinNhap();

        if (ct != null) {
            int rs = new bus_ChuongTrinh().themChuongTrinh(ct);

            if (rs == 0) {

                JOptionPane.showMessageDialog(null, "Lỗi");
            } 
            else {
                giaoDienThem();
                JOptionPane.showMessageDialog(null, "Đã tạo chương trình mới");
                reloadDuLieu();
            }
        }
    }
    // HÀM LẤY DỮ LIỆU NHẬP VÀO
    public dto_ChuongTrinh layThongTinNhap() {

        dto_ChuongTrinh ct = null;

        int row = tbChuongTrinh.getSelectedRow();

        layChungChiDuocChon();

        String strTen = txtTenCt.getText();
        String strDiemDauVao = txtDiemDauVao.getText();
        String strDiemDauRa = txtDiemDauRa.getText();

        boolean isCkNghe = ckNghe.isSelected();
        boolean isCkNoi = ckNoi.isSelected();
        boolean isCkDoc = ckDoc.isSelected();
        boolean isCkViet = ckViet.isSelected();

        boolean isCkTinhTong = radCong.isSelected();
        boolean isCkTinhTrungBinhCong = radTrungBinhCong.isSelected();

        boolean isCkDong = radDong.isSelected();
        boolean isCkMo = radMo.isSelected();
        
        dto_ChungChi cc = this.dsChungChi.get(cbTenCc.getSelectedIndex());
        String strNoiDung = txtNoiDung.getText();
        
        
        int kqkt = kiemTraThongTinNhap(cc,strTen, strDiemDauVao, strDiemDauRa, isCkNghe, isCkNoi, isCkDoc, isCkViet, isCkTinhTong, isCkTinhTrungBinhCong, strNoiDung, isCkDong, isCkMo);

        if (kqkt == 0) {
            JOptionPane.showMessageDialog(null, "Chưa nhập đủ thông tin");
        } else if (kqkt == 1) {
            JOptionPane.showMessageDialog(null, "Điểm nhập vào không hợp lệ");

        } else if (kqkt == 2) {

            float diemDauRa = 0;
            float diemDauVao = 0;

            try {

                diemDauVao = Float.parseFloat(strDiemDauVao);
                diemDauRa = Float.parseFloat(strDiemDauRa);
            } catch (Exception ex) {

                ex.printStackTrace();
            }

            ct = new dto_ChuongTrinh();
            ct.setMaCc(this.ccDuocChon.getMaCc());
            ct.setTenCt(strTen);
            ct.setDiemDauVao(diemDauVao);
            ct.setDiemDauRa(diemDauRa);
            ct.setNoiDung(strNoiDung);

            if (isCkNghe == true) {
                ct.setTinhNghe(1);
            } else {
                ct.setTinhNghe(0);
            }

            if (isCkNoi == true) {
                ct.setTinhNoi(1);
            } else {
                ct.setTinhNoi(0);
            }

            if (isCkDoc == true) {
                ct.setTinhDoc(1);
            } else {
                ct.setTinhDoc(0);
            }

            if (isCkViet == true) {
                ct.setTinhViet(1);
            } else {
                ct.setTinhViet(0);
            }

            if (isCkTinhTong == true) {
                ct.setCachTinhDiem(1);
            } else {
                ct.setCachTinhDiem(2);
            }

            if (isCkDong == true) {
                ct.setTrangThai(0);
            } else {
                ct.setTrangThai(1);
            }
            
            if(radCong.isSelected())
                ct.setCachTinhDiem(1);
            else
                ct.setCachTinhDiem(2);
            
            if(radMo.isSelected())
                 ct.setTrangThai(1);
            else
                ct.setTrangThai(0);
        }
        return ct;

    }

    // HÀM KIỂM TRA THÔNG TIN NHẬP VÀO: 0 là thông tin nhập chưa đủ, 1-chuyển đổi số thất bại, 2-Thông tin nhập không hợp lệ, 3-thành công
    public int kiemTraThongTinNhap(dto_ChungChi cc, String strTen, String diemDauVao, String diemDauRa, boolean ckNghe, boolean ckNoi, boolean ckDoc, boolean ckViet, boolean ckTong, boolean ckTrungBinhCong, String noiDung, boolean ckDong, boolean ckMo) {

        if (cc == null) {
            return 0;
        }

        if (ckNghe == false && ckNoi == false && ckDoc == false && ckViet == false && ckTong == false && ckTrungBinhCong == false && ckDong == false && ckMo == false) {
            return 0;
        }

        if (strTen.isEmpty() || diemDauVao.isEmpty() || diemDauRa.isEmpty() || noiDung.isEmpty()) {
            return 0;
        } else {

            float dauRa = 0;
            float dauVao = 0;

            try {
                dauVao = Float.parseFloat(diemDauVao);
                dauRa = Float.parseFloat(diemDauRa);
            } catch (Exception ex) {
                return 1; // Thông tin nhập không hợp lệ.
            }

            if (dauRa <= dauVao || dauRa > cc.getDiemToiDa()) {
                return 1; // thông tin nhập không hợp lệ
            }

        }

        return 2;//thanh cong

    }
    
    
    // HÀM LẤY CHỨNG CHỈ ĐƯỢC CHỌN
    public void layChungChiDuocChon(){
        int row = cbTenCc.getSelectedIndex();   
        this.ccDuocChon = new dto_ChungChi();
        
        if(row >= 0 ){
            this.ccDuocChon = this.dsChungChi.get(row);
            txtMaCc.setText(this.ccDuocChon.getMaCc()+"");
            txtDiemToiDa.setText(this.ccDuocChon.getDiemToiDa()+"");
            lblLogo.setIcon(new ImageIcon(this.ccDuocChon.getSrcImg()));
        }
        
    }
    
    
    // HÀM LẤY CHƯƠNG TRÌNH ĐƯỢC CHỌN
    public dto_ChuongTrinh layChuongTrinhDuocChon() {
        int row = tbChuongTrinh.getSelectedRow();

        if (row >= 0) {
            this.ctDuocChon = this.dsChuongTrinh.get(row);
            this.ctDuocChon.setChungChi(new bus_ChuongTrinh().layMotChungChi(this.ctDuocChon.getMaCc()));

        } else {

            JOptionPane.showMessageDialog(null, "Chưa chọn dòng dữ liệu");
        }
        return this.ctDuocChon;
    }

    // HÀM RELOAD DỮ LIỆU
    public void reloadDuLieu() {

        dsChuongTrinh = new ArrayList<dto_ChuongTrinh>();
        dsChuongTrinh = new bus_ChuongTrinh().layDsChuongTrinh(this.ckCtDong.isSelected());

        dsChungChi = new ArrayList<dto_ChungChi>();
        dsChungChi = new bus_ChungChi().layDsChungChi();

        reloadTable(this.dsChuongTrinh);

        cbTenCc.removeAllItems();
        for (dto_ChungChi cc : this.dsChungChi) {
            cbTenCc.addItem(cc.getTenCc());
        }
    }

    // HÀM SET GIAO DIỆN BAN ĐẦU
    public void setGiaoDienBanDau() {
        setupTable();
        giaoDienThem();

        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(radCong);
        btnGroup.add(radTrungBinhCong);

        ButtonGroup btnGroupTrangThai = new ButtonGroup();
        btnGroupTrangThai.add(radDong);
        btnGroupTrangThai.add(radMo);
    }

    // HÀM SET GIAO DIỆN THÊM MỚI
    public void giaoDienThem() {
        
        isCapNhat = false;
        lblChuongTrinh.setText("Tạo Mới");
        btnXacNhan.setVisible(true);
        
        txtMaCt.setText("");
        txtTenCt.setText("");
        txtDiemDauVao.setText("");
        txtDiemDauRa.setText("");
        txtDiemToiDa.setText("");
        txtMaCc.setText("");
        txtNoiDung.setText("");
        ckNghe.setSelected(false);
        ckNoi.setSelected(false);
        ckDoc.setSelected(false);
        ckViet.setSelected(false);
        radCong.setSelected(false);
        radTrungBinhCong.setSelected(false);
        radDong.setSelected(false);
        radMo.setSelected(false);
    }
    
    
    // HÀM SET GIAO DIỆN CẬP NHẬT
    public void giaoDienCapNhat(){
        giaoDienChiTiet();
        isCapNhat = true;
        btnXacNhan.setVisible(true);
        
    }
    // HÀM SET GIAO DIỆN CHI TIẾT
    public void giaoDienChiTiet(){
        lblChuongTrinh.setText("Thông Tin");
        btnXacNhan.setVisible(false);

        layChuongTrinhDuocChon();
        
        txtMaCt.setText(this.ctDuocChon.getMaCt()+"");
        cbTenCc.setSelectedItem(this.ctDuocChon.getChungChi().getTenCc());
        txtTenCt.setText(this.ctDuocChon.getTenCt());
        txtDiemDauVao.setText(this.ctDuocChon.getDiemDauVao() + "");
        txtDiemDauRa.setText(this.ctDuocChon.getDiemDauRa() + "");
        txtNoiDung.setText(this.ctDuocChon.getNoiDung());

        if (this.ctDuocChon.getTinhNghe() == 1) {
            ckNghe.setSelected(true);
        }
        else
            ckNghe.setSelected(false);

        if (this.ctDuocChon.getTinhNoi() == 1) {
            ckNoi.setSelected(true);
        }
        else
            ckNoi.setSelected(false);

        if (this.ctDuocChon.getTinhDoc() == 1) {
            ckDoc.setSelected(true);
        }
        else
            ckDoc.setSelected(false);

        if (this.ctDuocChon.getTinhViet() == 1) {
            ckViet.setSelected(true);
        }
        else
            ckViet.setSelected(false);
        
        
        if (this.ctDuocChon.getCachTinhDiem() == 1) {
            
            radCong.setSelected(true);  
        } else {
            
            radTrungBinhCong.setSelected(true);
        }

        if (this.ctDuocChon.getTrangThai() == 1) {
            radMo.setSelected(true);
        } else {
            radDong.setSelected(true);
        }

        txtMaCc.setText(this.ctDuocChon.getMaCc() + "");
        txtDiemToiDa.setText(this.ctDuocChon.getChungChi().getDiemToiDa() + "");
        lblLogo.setIcon(new ImageIcon(this.ctDuocChon.getChungChi().getSrcImg()));
    }
    // HÀM LOAD DỮ LIỆU LÊN BẢNG
    public void reloadTable(ArrayList<dto_ChuongTrinh> dsChuongTrinh) {

        this.dsChuongTrinh = new ArrayList<dto_ChuongTrinh>();
        this.dsChuongTrinh = dsChuongTrinh;

        int stt = 0;

        dtmChuongTrinh.setRowCount(0);

        for (dto_ChuongTrinh ct : dsChuongTrinh) {

            stt++;
            Vector<Object> vc = new Vector<Object>();

            vc.add(stt);
            vc.add(ct.getMaCt());
            vc.add(ct.getTenCt());
            vc.add(ct.getChungChi().getTenCc());
            vc.add(ct.getDiemDauVao());
            vc.add(ct.getDiemDauRa());

            if (ct.getTrangThai() == 0) {
                vc.add("Đóng");
            } else {
                vc.add("Mở");
            }

            dtmChuongTrinh.addRow(vc);
        }
    }

    // HÀM TẠO BẢNG
    public void setupTable() {
        this.dtmChuongTrinh = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        this.dtmChuongTrinh.addColumn("STT");
        this.dtmChuongTrinh.addColumn("Mã CT");
        this.dtmChuongTrinh.addColumn("Tên Chương Trình");
        this.dtmChuongTrinh.addColumn("Chứng Chỉ");
        this.dtmChuongTrinh.addColumn("Đầu Vào");
        this.dtmChuongTrinh.addColumn("Đầu Ra");
        this.dtmChuongTrinh.addColumn("Trạng Thái");

        tbChuongTrinh.setModel(this.dtmChuongTrinh);

        tbChuongTrinh.getColumnModel().getColumn(0).setMaxWidth(50);
        tbChuongTrinh.getColumnModel().getColumn(1).setMinWidth(80);
        tbChuongTrinh.getColumnModel().getColumn(1).setMaxWidth(80);
        tbChuongTrinh.getColumnModel().getColumn(2).setMinWidth(150);
        tbChuongTrinh.getColumnModel().getColumn(2).setMaxWidth(150);
        tbChuongTrinh.getColumnModel().getColumn(3).setMinWidth(100);
        tbChuongTrinh.getColumnModel().getColumn(3).setMaxWidth(80);
        tbChuongTrinh.getColumnModel().getColumn(4).setMaxWidth(80);
        tbChuongTrinh.getColumnModel().getColumn(4).setMinWidth(80);
        tbChuongTrinh.getColumnModel().getColumn(5).setMaxWidth(80);
        tbChuongTrinh.getColumnModel().getColumn(5).setMinWidth(80);

        tbChuongTrinh.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        tbChuongTrinh.getTableHeader().setOpaque(false);
        tbChuongTrinh.getTableHeader().setForeground(new Color(0, 0, 0));
        tbChuongTrinh.setSelectionBackground(new Color(0, 64, 128));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnChuongTrinh = new javax.swing.JPanel();
        lblTimLop = new javax.swing.JLabel();
        txtTimChuongTrinh = new javax.swing.JTextField();
        btnThemChuongTrinh = new javax.swing.JButton();
        btnXoaChuongTrinh = new javax.swing.JButton();
        btnCapNhatChuongTrinh = new javax.swing.JButton();
        ckCtDong = new javax.swing.JCheckBox();
        btnChungChi = new javax.swing.JButton();
        scChuongTrinh = new javax.swing.JScrollPane();
        tbChuongTrinh = new javax.swing.JTable();
        pnThem = new javax.swing.JPanel();
        lblChuongTrinh = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtTenCt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtNoiDung = new javax.swing.JTextArea();
        btnXacNhan = new javax.swing.JButton();
        txtDiemDauVao = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDiemDauRa = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cbTenCc = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        ckNghe = new javax.swing.JCheckBox();
        ckNoi = new javax.swing.JCheckBox();
        ckDoc = new javax.swing.JCheckBox();
        ckViet = new javax.swing.JCheckBox();
        radCong = new javax.swing.JRadioButton();
        radTrungBinhCong = new javax.swing.JRadioButton();
        jLabel9 = new javax.swing.JLabel();
        txtMaCc = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtDiemToiDa = new javax.swing.JTextField();
        lblLogo = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        radDong = new javax.swing.JRadioButton();
        radMo = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        txtMaCt = new javax.swing.JTextField();

        setBackground(new java.awt.Color(230, 245, 255));
        setMinimumSize(new java.awt.Dimension(1350, 634));
        setPreferredSize(new java.awt.Dimension(1350, 634));

        pnChuongTrinh.setBackground(new java.awt.Color(230, 245, 255));
        pnChuongTrinh.setMinimumSize(new java.awt.Dimension(1350, 634));
        pnChuongTrinh.setPreferredSize(new java.awt.Dimension(1350, 634));

        lblTimLop.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTimLop.setText("Tìm Kiếm");

        txtTimChuongTrinh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTimChuongTrinh.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));
        txtTimChuongTrinh.setMargin(new java.awt.Insets(10, 10, 10, 10));
        txtTimChuongTrinh.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimChuongTrinhKeyReleased(evt);
            }
        });

        btnThemChuongTrinh.setBackground(new java.awt.Color(230, 245, 255));
        btnThemChuongTrinh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemChuongTrinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/taomoi.png"))); // NOI18N
        btnThemChuongTrinh.setToolTipText("Tạo Chương Trình Mới");
        btnThemChuongTrinh.setContentAreaFilled(false);
        btnThemChuongTrinh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThemChuongTrinh.setFocusable(false);
        btnThemChuongTrinh.setOpaque(true);
        btnThemChuongTrinh.setPreferredSize(new java.awt.Dimension(183, 40));
        btnThemChuongTrinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemChuongTrinhActionPerformed(evt);
            }
        });

        btnXoaChuongTrinh.setBackground(new java.awt.Color(230, 245, 255));
        btnXoaChuongTrinh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoaChuongTrinh.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaChuongTrinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/xoa.png"))); // NOI18N
        btnXoaChuongTrinh.setToolTipText("Xóa Chương Trình");
        btnXoaChuongTrinh.setContentAreaFilled(false);
        btnXoaChuongTrinh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoaChuongTrinh.setFocusable(false);
        btnXoaChuongTrinh.setMaximumSize(new java.awt.Dimension(129, 49));
        btnXoaChuongTrinh.setMinimumSize(new java.awt.Dimension(129, 49));
        btnXoaChuongTrinh.setOpaque(true);
        btnXoaChuongTrinh.setPreferredSize(new java.awt.Dimension(129, 49));
        btnXoaChuongTrinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaChuongTrinhActionPerformed(evt);
            }
        });

        btnCapNhatChuongTrinh.setBackground(new java.awt.Color(230, 245, 255));
        btnCapNhatChuongTrinh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCapNhatChuongTrinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/capnhat.png"))); // NOI18N
        btnCapNhatChuongTrinh.setToolTipText("Cập Nhật Chương Trình");
        btnCapNhatChuongTrinh.setContentAreaFilled(false);
        btnCapNhatChuongTrinh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCapNhatChuongTrinh.setFocusable(false);
        btnCapNhatChuongTrinh.setMaximumSize(new java.awt.Dimension(129, 49));
        btnCapNhatChuongTrinh.setMinimumSize(new java.awt.Dimension(129, 49));
        btnCapNhatChuongTrinh.setOpaque(true);
        btnCapNhatChuongTrinh.setPreferredSize(new java.awt.Dimension(129, 49));
        btnCapNhatChuongTrinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatChuongTrinhActionPerformed(evt);
            }
        });

        ckCtDong.setBackground(new java.awt.Color(230, 245, 255));
        ckCtDong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        ckCtDong.setText("Chương trình đóng");
        ckCtDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckCtDongActionPerformed(evt);
            }
        });

        btnChungChi.setBackground(new java.awt.Color(230, 245, 255));
        btnChungChi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/chungchi.png"))); // NOI18N
        btnChungChi.setToolTipText("Quản Lý Chứng Chỉ");
        btnChungChi.setBorderPainted(false);
        btnChungChi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnChungChi.setFocusPainted(false);
        btnChungChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChungChiActionPerformed(evt);
            }
        });

        scChuongTrinh.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbChuongTrinh.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbChuongTrinh.setRowHeight(30);
        tbChuongTrinh.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbChuongTrinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbChuongTrinhMousePressed(evt);
            }
        });
        scChuongTrinh.setViewportView(tbChuongTrinh);

        pnThem.setBackground(new java.awt.Color(250, 250, 250));
        pnThem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(51, 0, 51)));
        pnThem.setPreferredSize(new java.awt.Dimension(652, 500));

        lblChuongTrinh.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblChuongTrinh.setForeground(new java.awt.Color(51, 0, 102));
        lblChuongTrinh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblChuongTrinh.setText("Tạo Mới");
        lblChuongTrinh.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(51, 0, 51)));

        jLabel2.setForeground(new java.awt.Color(51, 0, 102));
        jLabel2.setText("Tên Chương Trình");

        jLabel3.setForeground(new java.awt.Color(51, 0, 102));
        jLabel3.setText("Điểm Đầu Vào");

        txtTenCt.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setForeground(new java.awt.Color(51, 0, 102));
        jLabel4.setText("Nội Dung");

        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        txtNoiDung.setColumns(20);
        txtNoiDung.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        txtNoiDung.setRows(5);
        jScrollPane2.setViewportView(txtNoiDung);

        btnXacNhan.setBackground(new java.awt.Color(0, 102, 153));
        btnXacNhan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnXacNhan.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhan.setText("XÁC NHẬN");
        btnXacNhan.setContentAreaFilled(false);
        btnXacNhan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXacNhan.setFocusable(false);
        btnXacNhan.setOpaque(true);
        btnXacNhan.setPreferredSize(new java.awt.Dimension(209, 40));
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });

        txtDiemDauVao.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDiemDauVao.setForeground(new java.awt.Color(0, 102, 153));
        txtDiemDauVao.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel5.setForeground(new java.awt.Color(51, 0, 102));
        jLabel5.setText("Điểm Đầu Ra");

        txtDiemDauRa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDiemDauRa.setForeground(new java.awt.Color(0, 102, 153));
        txtDiemDauRa.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel6.setForeground(new java.awt.Color(51, 0, 102));
        jLabel6.setText("Chứng Chỉ");

        cbTenCc.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        cbTenCc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbTenCc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTenCcActionPerformed(evt);
            }
        });

        jLabel7.setForeground(new java.awt.Color(51, 0, 102));
        jLabel7.setText("Tính Điểm");

        jLabel8.setForeground(new java.awt.Color(51, 0, 102));
        jLabel8.setText("Điểm Thành Phần");

        ckNghe.setBackground(new java.awt.Color(250, 250, 250));
        ckNghe.setText("Nghe");

        ckNoi.setBackground(new java.awt.Color(250, 250, 250));
        ckNoi.setText("Nói");

        ckDoc.setBackground(new java.awt.Color(250, 250, 250));
        ckDoc.setText("Đọc");

        ckViet.setBackground(new java.awt.Color(250, 250, 250));
        ckViet.setText("Viết");

        radCong.setBackground(new java.awt.Color(250, 250, 250));
        radCong.setText("Cộng Tất Cả");

        radTrungBinhCong.setBackground(new java.awt.Color(250, 250, 250));
        radTrungBinhCong.setText("Trung Bình Cộng");

        jLabel9.setForeground(new java.awt.Color(51, 0, 102));
        jLabel9.setText("Mã Chứng Chỉ");

        txtMaCc.setEditable(false);
        txtMaCc.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel10.setForeground(new java.awt.Color(51, 0, 102));
        jLabel10.setText("Điểm Tối Đa");

        txtDiemToiDa.setEditable(false);
        txtDiemToiDa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDiemToiDa.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/ielts.png"))); // NOI18N

        jLabel11.setForeground(new java.awt.Color(51, 0, 102));
        jLabel11.setText("Trạng Thái");

        radDong.setBackground(new java.awt.Color(250, 250, 250));
        radDong.setText("Đóng ");

        radMo.setBackground(new java.awt.Color(250, 250, 250));
        radMo.setText("Mở");

        jLabel1.setForeground(new java.awt.Color(51, 0, 102));
        jLabel1.setText("Mã CT");

        txtMaCt.setEditable(false);

        javax.swing.GroupLayout pnThemLayout = new javax.swing.GroupLayout(pnThem);
        pnThem.setLayout(pnThemLayout);
        pnThemLayout.setHorizontalGroup(
            pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThemLayout.createSequentialGroup()
                .addComponent(lblChuongTrinh, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThemLayout.createSequentialGroup()
                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnThemLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2))
                    .addGroup(pnThemLayout.createSequentialGroup()
                        .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnThemLayout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addGroup(pnThemLayout.createSequentialGroup()
                                        .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(txtMaCc, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel4))
                                        .addGap(24, 24, 24)
                                        .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel10)
                                            .addComponent(txtDiemToiDa, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(cbTenCc, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pnThemLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(29, 29, 29)
                        .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnThemLayout.createSequentialGroup()
                                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtDiemDauVao, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(71, 71, 71)
                                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDiemDauRa, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThemLayout.createSequentialGroup()
                                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(radDong)
                                    .addComponent(radCong))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(radTrungBinhCong)
                                    .addComponent(radMo, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(31, 31, 31))
                            .addGroup(pnThemLayout.createSequentialGroup()
                                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8)
                                    .addGroup(pnThemLayout.createSequentialGroup()
                                        .addComponent(ckNghe)
                                        .addGap(18, 18, 18)
                                        .addComponent(ckNoi)
                                        .addGap(18, 18, 18)
                                        .addComponent(ckDoc)
                                        .addGap(18, 18, 18)
                                        .addComponent(ckViet))
                                    .addComponent(jLabel7))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThemLayout.createSequentialGroup()
                                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaCt, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(txtTenCt, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addGap(22, 22, 22))
        );
        pnThemLayout.setVerticalGroup(
            pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThemLayout.createSequentialGroup()
                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnThemLayout.createSequentialGroup()
                        .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblChuongTrinh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnThemLayout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTenCt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaCt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDiemDauVao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDiemDauRa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnThemLayout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ckNghe)
                            .addComponent(ckNoi)
                            .addComponent(ckDoc)
                            .addComponent(ckViet))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(radCong)
                            .addComponent(radTrungBinhCong))
                        .addGap(16, 16, 16)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(radDong)
                            .addComponent(radMo)))
                    .addGroup(pnThemLayout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbTenCc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDiemToiDa, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaCc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnChuongTrinhLayout = new javax.swing.GroupLayout(pnChuongTrinh);
        pnChuongTrinh.setLayout(pnChuongTrinhLayout);
        pnChuongTrinhLayout.setHorizontalGroup(
            pnChuongTrinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnChuongTrinhLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnChuongTrinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnChuongTrinhLayout.createSequentialGroup()
                        .addComponent(lblTimLop)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimChuongTrinh, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ckCtDong)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnChungChi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnThemChuongTrinh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCapNhatChuongTrinh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaChuongTrinh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(scChuongTrinh, javax.swing.GroupLayout.PREFERRED_SIZE, 658, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(pnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 623, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
        pnChuongTrinhLayout.setVerticalGroup(
            pnChuongTrinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnChuongTrinhLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnChuongTrinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnChungChi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnChuongTrinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTimLop, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTimChuongTrinh, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ckCtDong))
                    .addComponent(btnThemChuongTrinh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhatChuongTrinh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaChuongTrinh, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnChuongTrinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 577, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(scChuongTrinh))
                .addContainerGap(5, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnChuongTrinh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnChuongTrinh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemChuongTrinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemChuongTrinhActionPerformed
        giaoDienThem();
    }//GEN-LAST:event_btnThemChuongTrinhActionPerformed

    private void btnXoaChuongTrinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaChuongTrinhActionPerformed
        xoaChuongTrinh();

    }//GEN-LAST:event_btnXoaChuongTrinhActionPerformed

    private void btnCapNhatChuongTrinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatChuongTrinhActionPerformed
        giaoDienCapNhat();

    }//GEN-LAST:event_btnCapNhatChuongTrinhActionPerformed

    private void ckCtDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckCtDongActionPerformed
        reloadDuLieu();
    }//GEN-LAST:event_ckCtDongActionPerformed

    private void txtTimChuongTrinhKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimChuongTrinhKeyReleased

        String text = txtTimChuongTrinh.getText();
        if(text.isEmpty())
            reloadDuLieu();
        else
            timChuongTrinh(text);
    }//GEN-LAST:event_txtTimChuongTrinhKeyReleased

    private void btnChungChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChungChiActionPerformed
        new UI_ChungChi().show();
    }//GEN-LAST:event_btnChungChiActionPerformed

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        
        if(isCapNhat == false)
            themChuongTrinh();
        else
            capNhatChuongTrinh();
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void cbTenCcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTenCcActionPerformed
        layChungChiDuocChon();
    }//GEN-LAST:event_cbTenCcActionPerformed

    private void tbChuongTrinhMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbChuongTrinhMousePressed
        giaoDienChiTiet();
    }//GEN-LAST:event_tbChuongTrinhMousePressed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhatChuongTrinh;
    private javax.swing.JButton btnChungChi;
    private javax.swing.JButton btnThemChuongTrinh;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JButton btnXoaChuongTrinh;
    private javax.swing.JComboBox<String> cbTenCc;
    private javax.swing.JCheckBox ckCtDong;
    private javax.swing.JCheckBox ckDoc;
    private javax.swing.JCheckBox ckNghe;
    private javax.swing.JCheckBox ckNoi;
    private javax.swing.JCheckBox ckViet;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblChuongTrinh;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblTimLop;
    private javax.swing.JPanel pnChuongTrinh;
    private javax.swing.JPanel pnThem;
    private javax.swing.JRadioButton radCong;
    private javax.swing.JRadioButton radDong;
    private javax.swing.JRadioButton radMo;
    private javax.swing.JRadioButton radTrungBinhCong;
    private javax.swing.JScrollPane scChuongTrinh;
    private javax.swing.JTable tbChuongTrinh;
    private javax.swing.JTextField txtDiemDauRa;
    private javax.swing.JTextField txtDiemDauVao;
    private javax.swing.JTextField txtDiemToiDa;
    private javax.swing.JTextField txtMaCc;
    private javax.swing.JTextField txtMaCt;
    private javax.swing.JTextArea txtNoiDung;
    private javax.swing.JTextField txtTenCt;
    private javax.swing.JTextField txtTimChuongTrinh;
    // End of variables declaration//GEN-END:variables
}
