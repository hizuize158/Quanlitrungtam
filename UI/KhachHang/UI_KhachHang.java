
package UI.KhachHang;

import BUS.bus_ChungChi;
import BUS.bus_Khachhang;
import DTO.dto_ChungChi;
import DTO.dto_KhachHang;
import DTO.dto_LichSu;
import UI.FormXacMinhNguoiDung;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class UI_KhachHang extends javax.swing.JPanel {

    /**
     * Creates new form KhachHangUI
     */
    public UI_KhachHang() {
        initComponents();
        giaoDienBanDau();
        reloadDuLieu();
        reloadTableKhachHang(this.dsKhachHang);
    }
    
    public UI_KhachHang(boolean isHocVu){
        initComponents();
        giaoDienBanDau();
        reloadDuLieu();
        reloadTableKhachHang(this.dsKhachHang);
        hideBtnKh();
    }

    // BIẾN TỰ ĐỊNH NGHĨA
    private dto_KhachHang khDuocChon;
    private boolean isCapNhatKhachHang;
    private DefaultTableModel dtmKhachHang;
    private DefaultTableModel dtmLichSu;
    private ArrayList<dto_KhachHang> dsKhachHang;
    private ArrayList<dto_ChungChi> dsChungChi;

    // HÀM TÌM KIẾM
    public void timKhachHang(String text) {
        ArrayList<dto_KhachHang> dsKhachHangTim = new bus_Khachhang().layDsTimKiem(text);

        reloadTableKhachHang(dsKhachHangTim);
    }

    // HÀM XÓA KHÁCH HÀNG
    public void xoaKhachHang() {

        FormXacMinhNguoiDung form = new FormXacMinhNguoiDung();
        boolean ketQua = form.kiemTraMatKhau();

        if (form.getLuaChon() == 0) {

            if (ketQua == true) {

                int rs = new bus_Khachhang().xoaKhachHang(this.khDuocChon);

                if (rs == 0) {
                    JOptionPane.showMessageDialog(null, "Đã xóa khách hàng");
                } else {
                    JOptionPane.showMessageDialog(null, "Xóa Thành Công");
                    reloadDuLieu();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Mật Khẩu Không Chính Xác");
            }
        }
    }

    // HÀM CẬP NHẬT KHÁCH HÀNG
    public void capNhatKhachHang() {

        dto_KhachHang khNhap = layKhNhap();

        if (khNhap != null) {

            khNhap.setMaKh(this.khDuocChon.getMaKh());

            int kq = new bus_Khachhang().capNhatKhachHang(khNhap);

            if (kq > 0) {

                JOptionPane.showMessageDialog(null, "Đã cập nhật thay đổi");
                reloadDuLieu();
            } else {
                JOptionPane.showMessageDialog(null, "Lỗi");
            }

        }
    }

    // HÀM THÊM KHÁCH HÀNG
    public void themKhachHang() {

        dto_KhachHang kh = new dto_KhachHang();
        kh = layKhNhap();

        if (kh != null) {

            int kq = new bus_Khachhang().themKhachHang(kh);

            if (kq > 0) {
                JOptionPane.showMessageDialog(null, "Đã tạo khách hàng mới");

                reloadDuLieu();
            } else {
                JOptionPane.showMessageDialog(null, "Lỗi");
            }
        }
    }

    // HÀM LẤY THÔNG TIN NHẬP VÀO
    public dto_KhachHang layKhNhap() {

        dto_KhachHang kh = null;

        String ten = txtTen.getText();
        int gioiTinh;

        if (cbGioiTinh.getSelectedIndex() == 0) {
            gioiTinh = 1;
        } else {
            gioiTinh = 2;
        }

        Date ngaySinh = dcNgaySinh.getDate();

        String strDienThoai = txtSdt.getText();
        String diaChi = txtDiaChi.getText();
        String strDiemDauVao = txtDiemDauVao.getText();

        int indexChungChi = cbChungChi.getSelectedIndex();

        dto_ChungChi cc = null;
        if (indexChungChi > -1) {
            cc = new dto_ChungChi();
            cc = this.dsChungChi.get(indexChungChi);
        }

        if (ten.isEmpty() || ngaySinh == null || strDienThoai.isEmpty() || diaChi.isEmpty() || strDiemDauVao.isEmpty() || indexChungChi < 0) {
            JOptionPane.showMessageDialog(null, "Chưa nhập đủ thông tin");
            return null;
        }

        int sdt;

        try {
            sdt = Integer.parseInt(strDienThoai);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ");
            return null;
        }

        Float diemDauVao = 0F;

        try {

            diemDauVao = Float.parseFloat(strDiemDauVao);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Điểm đầu vào không hợp lệ");
        }

        if (diemDauVao > cc.getDiemToiDa()) {
            JOptionPane.showMessageDialog(null, "Điểm đầu vào không được lớn hơn điểm tối đa của chứng chỉ quy định");
            return null;
        }

        kh = new dto_KhachHang();
        kh.setTenKh(ten);
        kh.setGioiTinh(gioiTinh);
        kh.setNgaySinh(ngaySinh);
        kh.setSdt(strDienThoai);
        kh.setDiaChi(diaChi);
        kh.setDiemDauVao(diemDauVao);
        kh.setMaChungChi(cc.getMaCc());
        return kh;
    }

    // HÀM THIẾT LẬP GIAO DIỆN BAN ĐẦU
    public void giaoDienBanDau() {
        setupTableLichSu();
        setupTableKhachHang();
        giaoDienThem();
        lblKhachHang.setText("Thông Tin Chi Tiết");
        btnXacNhan.setVisible(false);
                
    }

    // HÀM SETUP DỮ LIỆU BAN ĐẦU
    public void reloadDuLieu() {
        setupDuLieuKhachHang();
        setupDuLieuChungChi();
        reloadTableKhachHang(this.dsKhachHang);
    }

    // HÀM SETUP DỮ LIỆU DANH SÁCH KHÁCH HÀNG
    public void setupDuLieuKhachHang() {
        this.dsKhachHang = new ArrayList<dto_KhachHang>();
        this.dsKhachHang = new bus_Khachhang().layDsKhachHang();
    }

    // HÀM SETUP DỮ LIỆU CHỨNG CHỈ
    public void setupDuLieuChungChi() {
        this.dsChungChi = new ArrayList<dto_ChungChi>();
        this.dsChungChi = new bus_ChungChi().layDsChungChi();

        cbChungChi.removeAllItems();

        for (dto_ChungChi cc : this.dsChungChi) {
            cbChungChi.addItem(cc.getTenCc());
        }
    }

    // HÀM SET GIAO DIỆN CẬP NHẬT
    public void giaoDienCapNhat() {

        lblKhachHang.setText("Cập Nhật");
        isCapNhatKhachHang = true;
        btnXacNhan.setVisible(true);
    }

    // HÀM HIỂN THỊ THÔNG TIN CHI TIẾT
    public void giaoDienChiTiet(dto_KhachHang kh) {

        reloadTableLichSu(kh.getDsLichSu());

        btnXacNhan.setVisible(false);
        lblKhachHang.setText("Thông Tin Chi Tiết");

        if (kh != null) {

            txtMa.setText(kh.getMaKh() + "");
            txtTen.setText(kh.getTenKh());
            cbGioiTinh.setSelectedIndex(kh.getGioiTinh() - 1);
            dcNgaySinh.setDate(kh.getNgaySinh());
            txtSdt.setText(kh.getSdt());
            txtDiaChi.setText(kh.getDiaChi());

            txtDiemDauVao.setText(kh.getDiemDauVao() + "");

            txtDiemToiDa.setText(kh.getChungChiCanHoc().getDiemToiDa() + "");

            txtLopDangHoc.setText(kh.getTenLop());

            cbChungChi.setSelectedItem(kh.getChungChiCanHoc().getTenCc());

            this.khDuocChon = new dto_KhachHang();
            this.khDuocChon = kh;
        }

    }

    // HÀM SET GIAO DIỆN THÊM KHÁCH HÀNG
    public void giaoDienThem() {

        isCapNhatKhachHang = false;
        btnXacNhan.setVisible(true);
        lblKhachHang.setText("Tạo Khách Hàng Mới");
        txtMa.setText("");
        txtTen.setText("");
        txtSdt.setText("");
        txtDiaChi.setText("");
        txtDiemDauVao.setText("");
        txtDiemToiDa.setText("");
        txtLopDangHoc.setText("");
        dcNgaySinh.setDate(null);
        tbKhachHang.clearSelection();

    }

    // HÀM LẤY KHÁCH HÀNG ĐƯỢC CHỌN
    public dto_KhachHang khachHangDuocChon() {

        int row = tbKhachHang.getSelectedRow();

        dto_KhachHang kh = null;

        if (row > -1) {

            kh = new dto_KhachHang();
            kh = dsKhachHang.get(row);

            khDuocChon = new dto_KhachHang();
            khDuocChon = kh;
        }
        kh.setDsLichSu(new bus_Khachhang().dsLichSu(kh.getMaKh()));
        kh.setChungChiCanHoc(new bus_Khachhang().layChungChi(kh.getMaChungChi()));

        return kh;
    }

    // HÀM LOAD DỮ LIỆU LÊN BẢNG LỊCH SỬ
    public void reloadTableLichSu(ArrayList<dto_LichSu> dsLichSu) {

        int stt = 0;

        dtmLichSu.setRowCount(0);

        for (dto_LichSu ls : dsLichSu) {

            stt++;

            Vector<Object> vc = new Vector<Object>();
            vc.add(stt);
            vc.add(ls.layNgayBd());
            vc.add(ls.layNgayKt());
            vc.add(ls.getLop().getTenLop());
            vc.add(ls.getNghe());
            vc.add(ls.getNoi());
            vc.add(ls.getDoc());
            vc.add(ls.getViet());
            vc.add(ls.getTong());

            dtmLichSu.addRow(vc);
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

            dtmKhachHang.addRow(vc);
        }

    }

    // HÀM TẠO BẢNG LỊCH SỬ
    public void setupTableLichSu() {
        dtmLichSu = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        dtmLichSu.addColumn("STT");
        dtmLichSu.addColumn("Từ");
        dtmLichSu.addColumn("Đến");
        dtmLichSu.addColumn("Tên Lớp");
        dtmLichSu.addColumn("Nghe");
        dtmLichSu.addColumn("Nói");
        dtmLichSu.addColumn("Đọc");
        dtmLichSu.addColumn("Viết");
        dtmLichSu.addColumn("Tổng");

        tbLichSu.setModel(dtmLichSu);

        tbLichSu.getColumnModel().getColumn(0).setMaxWidth(50);
        tbLichSu.getColumnModel().getColumn(1).setMinWidth(80);
        tbLichSu.getColumnModel().getColumn(2).setMinWidth(80);
        tbLichSu.getColumnModel().getColumn(3).setMinWidth(120);
        tbLichSu.getColumnModel().getColumn(3).setMaxWidth(120);

        tbLichSu.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        tbLichSu.getTableHeader().setOpaque(false);
        tbLichSu.getTableHeader().setForeground(new Color(0, 0, 0));
        tbLichSu.setSelectionBackground(new Color(0, 64, 128));

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

        tbKhachHang.setModel(dtmKhachHang);

        tbKhachHang.getColumnModel().getColumn(0).setMaxWidth(50);
        tbKhachHang.getColumnModel().getColumn(1).setMaxWidth(80);
        tbKhachHang.getColumnModel().getColumn(2).setMinWidth(150);
        tbKhachHang.getColumnModel().getColumn(2).setMaxWidth(200);
        tbKhachHang.getColumnModel().getColumn(2).setMaxWidth(80);

        tbKhachHang.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        tbKhachHang.getTableHeader().setOpaque(false);
        tbKhachHang.getTableHeader().setForeground(new Color(0, 0, 0));
        tbKhachHang.setSelectionBackground(new Color(0, 64, 128));

    }

    // ẨN MỘT SỐ NÚT ĐỂ PHẦN QUYỀN
    public void hideBtnKh() {
        btnCapNhatKH.setVisible(false);
        btnThemKH.setVisible(false);
        btnXoaKH.setVisible(false);
        
    }

    /*Hết khu vực của Tân*/
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jspKH = new javax.swing.JScrollPane();
        tbKhachHang = new javax.swing.JTable();
        pnThongTin = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbLichSu = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cbGioiTinh = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        dcNgaySinh = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtMa = new javax.swing.JTextField();
        txtSdt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtDiemDauVao = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        cbChungChi = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        txtLopDangHoc = new javax.swing.JTextField();
        lblKhachHang = new javax.swing.JLabel();
        btnXacNhan = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtDiemToiDa = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txtTimKH = new javax.swing.JTextField();
        btnXoaKH = new javax.swing.JButton();
        btnThemKH = new javax.swing.JButton();
        btnCapNhatKH = new javax.swing.JButton();

        setBackground(new java.awt.Color(230, 245, 255));
        setAlignmentX(0.0F);
        setAlignmentY(0.0F);
        setMinimumSize(new java.awt.Dimension(1350, 634));
        setPreferredSize(new java.awt.Dimension(1350, 634));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        jspKH.setBackground(new java.awt.Color(255, 255, 255));
        jspKH.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jspKH.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jspKH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jspKH.setViewportView(null);

        tbKhachHang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã KH", "Họ Tên", "Giới Tính", "Ngày Sinh", "Điện Thoại"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbKhachHang.setFocusable(false);
        tbKhachHang.setRowHeight(30);
        tbKhachHang.setRowMargin(5);
        tbKhachHang.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbKhachHang.setShowGrid(true);
        tbKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbKhachHangMousePressed(evt);
            }
        });
        jspKH.setViewportView(tbKhachHang);
        if (tbKhachHang.getColumnModel().getColumnCount() > 0) {
            tbKhachHang.getColumnModel().getColumn(0).setMinWidth(50);
            tbKhachHang.getColumnModel().getColumn(0).setMaxWidth(50);
            tbKhachHang.getColumnModel().getColumn(1).setMinWidth(100);
            tbKhachHang.getColumnModel().getColumn(1).setMaxWidth(100);
            tbKhachHang.getColumnModel().getColumn(2).setMinWidth(180);
            tbKhachHang.getColumnModel().getColumn(2).setPreferredWidth(200);
            tbKhachHang.getColumnModel().getColumn(2).setMaxWidth(180);
            tbKhachHang.getColumnModel().getColumn(4).setMinWidth(100);
            tbKhachHang.getColumnModel().getColumn(4).setPreferredWidth(50);
            tbKhachHang.getColumnModel().getColumn(4).setMaxWidth(100);
        }

        pnThongTin.setBackground(new java.awt.Color(250, 250, 250));
        pnThongTin.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)));
        pnThongTin.setMinimumSize(new java.awt.Dimension(613, 500));
        pnThongTin.setPreferredSize(new java.awt.Dimension(613, 500));
        pnThongTin.setRequestFocusEnabled(false);

        jLabel2.setText("Quá Trình Học Tập");

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbLichSu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Thời Gian", "Lớp", "Nghe", "Nói", "Đọc", "Viết", "Tổng"
            }
        ));
        tbLichSu.setRowHeight(30);
        jScrollPane1.setViewportView(tbLichSu);

        jLabel4.setText("Họ Tên");

        txtTen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenActionPerformed(evt);
            }
        });

        jLabel5.setText("Giới Tính");

        cbGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ" }));

        jLabel6.setText("Ngày Sinh");

        jLabel7.setText("Mã KH");

        txtMa.setEditable(false);

        txtSdt.setText("0392489615");

        jLabel3.setText("Điện Thoại");

        jLabel8.setText("Địa Chỉ");

        txtDiaChi.setText("103/6, Khu Phố 6, Phường Tân Thới Nhất, Quận 12, Thành PHố Hồ Chí Minh");

        jLabel9.setText("Điểm Đầu Vào");

        txtDiemDauVao.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtDiemDauVao.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtDiemDauVao.setText("999");

        jLabel10.setText("Chứng Chỉ Cần Học");

        cbChungChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbChungChiActionPerformed(evt);
            }
        });

        jLabel11.setText("Lớp Đang Học");

        txtLopDangHoc.setEditable(false);

        lblKhachHang.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblKhachHang.setForeground(new java.awt.Color(0, 0, 102));
        lblKhachHang.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblKhachHang.setText("Tạo Khách Hàng Mới");
        lblKhachHang.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(51, 0, 102)));

        btnXacNhan.setBackground(new java.awt.Color(0, 102, 153));
        btnXacNhan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXacNhan.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhan.setText("Xác Nhận");
        btnXacNhan.setToolTipText("Xác Nhận");
        btnXacNhan.setBorder(null);
        btnXacNhan.setContentAreaFilled(false);
        btnXacNhan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXacNhan.setFocusable(false);
        btnXacNhan.setOpaque(true);
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });

        jLabel12.setText("Điểm Tối Đa");

        txtDiemToiDa.setEditable(false);
        txtDiemToiDa.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout pnThongTinLayout = new javax.swing.GroupLayout(pnThongTin);
        pnThongTin.setLayout(pnThongTinLayout);
        pnThongTinLayout.setHorizontalGroup(
            pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThongTinLayout.createSequentialGroup()
                .addComponent(lblKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnThongTinLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnThongTinLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnThongTinLayout.createSequentialGroup()
                        .addGroup(pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(pnThongTinLayout.createSequentialGroup()
                                .addGroup(pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel3))
                                .addGap(498, 498, 498))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 571, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pnThongTinLayout.createSequentialGroup()
                                    .addGroup(pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(pnThongTinLayout.createSequentialGroup()
                                            .addGroup(pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(cbChungChi, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(14, 14, 14)))
                                    .addGroup(pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThongTinLayout.createSequentialGroup()
                                                .addGap(143, 143, 143)
                                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtLopDangHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel11)))
                                            .addGroup(pnThongTinLayout.createSequentialGroup()
                                                .addGap(198, 198, 198)
                                                .addGroup(pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(cbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel5))
                                                .addGap(34, 34, 34)
                                                .addGroup(pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(dcNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel6))))
                                        .addGroup(pnThongTinLayout.createSequentialGroup()
                                            .addGap(15, 15, 15)
                                            .addGroup(pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel12)
                                                .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 418, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel8)
                                                .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(pnThongTinLayout.createSequentialGroup()
                                                    .addComponent(txtDiemToiDa, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(48, 48, 48)
                                                    .addComponent(txtDiemDauVao, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(jLabel4)))))))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        pnThongTinLayout.setVerticalGroup(
            pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThongTinLayout.createSequentialGroup()
                .addGroup(pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtMa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(dcNgaySinh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(pnThongTinLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtSdt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel12)
                    .addComponent(jLabel9)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnThongTinLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDiemToiDa, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbChungChi, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtDiemDauVao, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtLopDangHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Tìm Kiếm");

        txtTimKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTimKH.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTimKH.setToolTipText("Tìm theo mã, họ tên, số điện thoại");
        txtTimKH.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));
        txtTimKH.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtTimKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimKHActionPerformed(evt);
            }
        });
        txtTimKH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimKHKeyReleased(evt);
            }
        });

        btnXoaKH.setBackground(new java.awt.Color(230, 245, 255));
        btnXoaKH.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoaKH.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/xoa.png"))); // NOI18N
        btnXoaKH.setToolTipText("Xóa Thông Tin Khách Hàng");
        btnXoaKH.setContentAreaFilled(false);
        btnXoaKH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoaKH.setFocusable(false);
        btnXoaKH.setMaximumSize(new java.awt.Dimension(129, 49));
        btnXoaKH.setMinimumSize(new java.awt.Dimension(129, 49));
        btnXoaKH.setOpaque(true);
        btnXoaKH.setPreferredSize(new java.awt.Dimension(129, 49));
        btnXoaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaKHActionPerformed(evt);
            }
        });

        btnThemKH.setBackground(new java.awt.Color(230, 245, 255));
        btnThemKH.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemKH.setForeground(new java.awt.Color(255, 255, 255));
        btnThemKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/taomoi.png"))); // NOI18N
        btnThemKH.setToolTipText("Tạo Khách Hàng Mới");
        btnThemKH.setContentAreaFilled(false);
        btnThemKH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThemKH.setFocusable(false);
        btnThemKH.setMaximumSize(new java.awt.Dimension(217, 60));
        btnThemKH.setMinimumSize(new java.awt.Dimension(217, 60));
        btnThemKH.setOpaque(true);
        btnThemKH.setPreferredSize(new java.awt.Dimension(209, 30));
        btnThemKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemKHActionPerformed(evt);
            }
        });

        btnCapNhatKH.setBackground(new java.awt.Color(230, 245, 255));
        btnCapNhatKH.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCapNhatKH.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/capnhat.png"))); // NOI18N
        btnCapNhatKH.setToolTipText("Cập Nhật Thông Tin Khách Hàng");
        btnCapNhatKH.setContentAreaFilled(false);
        btnCapNhatKH.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCapNhatKH.setFocusable(false);
        btnCapNhatKH.setMaximumSize(new java.awt.Dimension(129, 49));
        btnCapNhatKH.setMinimumSize(new java.awt.Dimension(129, 49));
        btnCapNhatKH.setOpaque(true);
        btnCapNhatKH.setPreferredSize(new java.awt.Dimension(129, 49));
        btnCapNhatKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTimKH, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(261, 261, 261)
                        .addComponent(btnThemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCapNhatKH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnXoaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jspKH, javax.swing.GroupLayout.PREFERRED_SIZE, 654, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(pnThongTin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTimKH, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(5, 5, 5))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnXoaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCapNhatKH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnThemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnThongTin, javax.swing.GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
                    .addComponent(jspKH))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

    }//GEN-LAST:event_formMouseClicked

    private void btnCapNhatKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatKHActionPerformed

        giaoDienCapNhat();

    }//GEN-LAST:event_btnCapNhatKHActionPerformed

    private void btnXoaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaKHActionPerformed
        dto_KhachHang kh = khachHangDuocChon();

        if (kh != null)
            xoaKhachHang();
        else
            JOptionPane.showMessageDialog(null, "Chưa chọn khách hàng");
    }//GEN-LAST:event_btnXoaKHActionPerformed

    private void btnThemKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemKHActionPerformed
        giaoDienThem();
    }//GEN-LAST:event_btnThemKHActionPerformed

    private void txtTimKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimKHActionPerformed

    }//GEN-LAST:event_txtTimKHActionPerformed

    private void txtTenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenActionPerformed

    private void tbKhachHangMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbKhachHangMousePressed
        giaoDienChiTiet(khachHangDuocChon());
    }//GEN-LAST:event_tbKhachHangMousePressed

    private void cbChungChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbChungChiActionPerformed
        int row = cbChungChi.getSelectedIndex();

        if (row > -1) {
            dto_ChungChi cc = this.dsChungChi.get(row);
            txtDiemToiDa.setText(cc.getDiemToiDa() + "");
        }
    }//GEN-LAST:event_cbChungChiActionPerformed

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed

        if (isCapNhatKhachHang == false)
            themKhachHang();

        else
            capNhatKhachHang();
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void txtTimKHKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKHKeyReleased
        String text = txtTimKH.getText();

        if (text.isEmpty())
            reloadDuLieu();
        else
            timKhachHang(text);
    }//GEN-LAST:event_txtTimKHKeyReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhatKH;
    private javax.swing.JButton btnThemKH;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JButton btnXoaKH;
    private javax.swing.JComboBox<String> cbChungChi;
    private javax.swing.JComboBox<String> cbGioiTinh;
    private com.toedter.calendar.JDateChooser dcNgaySinh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jspKH;
    private javax.swing.JLabel lblKhachHang;
    private javax.swing.JPanel pnThongTin;
    private javax.swing.JTable tbKhachHang;
    private javax.swing.JTable tbLichSu;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtDiemDauVao;
    private javax.swing.JTextField txtDiemToiDa;
    private javax.swing.JTextField txtLopDangHoc;
    private javax.swing.JTextField txtMa;
    private javax.swing.JTextField txtSdt;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTimKH;
    // End of variables declaration//GEN-END:variables
}
