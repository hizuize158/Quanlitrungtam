package UI.TaiKhoan;

import BUS.BCrypt;
import BUS.bus_TaiKhoan;
import DTO.dto_TaiKhoan;
import UI.DangNhap.UI_DangNhap;

import UI.pnXacNhanMatKhau;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFileChooser;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class UI_TaiKhoan extends javax.swing.JPanel {

    /**
     * Creates new form UI_TaiKhoan
     */
    public UI_TaiKhoan() {
        initComponents();
        giaoDienBanDau();
    }

    // BIẾN TỰ ĐỊNH NGHĨA
    private boolean isCapNhat;
    private boolean isDoiMatKhau;
    private String img;
    private DefaultTableModel dtmTaiKhoan;
    private ArrayList<dto_TaiKhoan> dsTaiKhoan;
    private DefaultComboBoxModel dfcDsLoai;

    
    // HÀM HIỂN THỊ DANH SÁCH TÌM KIẾM 
    public void hienThiDsTimKiem(String text){
        ArrayList<dto_TaiKhoan> dsTimKiem = new bus_TaiKhoan().layDsTimKiem(text);
        
        reloadTable(dsTimKiem);
    }
    
    // HÀM XÓA TÀI KHOẢN
    public void xoaTaiKhoan() {
        dto_TaiKhoan tkSelected = duLieuDuocChon();

        if (tkSelected != null) {
            pnXacNhanMatKhau pn = new pnXacNhanMatKhau();
            
            int luaChon = JOptionPane.showConfirmDialog(null, pn, "Xác Minh Người Dùng", JOptionPane.OK_CANCEL_OPTION);
            
            if(luaChon == 0){
                
                String matKhauNhap = pn.getMatKhau();
                boolean ketQua = kiemTraMatKhau(matKhauNhap);
                
                if (ketQua == true) {

                    int rs = new bus_TaiKhoan().xoaTaiKhoan(tkSelected);

                    if (rs == 0)
                        JOptionPane.showMessageDialog(null, "Lỗi");
                    
                    else {
                        giaoDienBanDau();
                        JOptionPane.showMessageDialog(null, "Đã Xóa");
                    }
                } 
                else
                    JOptionPane.showMessageDialog(null, "Mật Khẩu Không Chính Xác");
            }
        }
    }
    
    // HÀM XÁC MINH NGƯỜI DÙNG
    public boolean kiemTraMatKhau(String matKhauNhap){
        
        String mk = UI_DangNhap.layMatKhauDangNhap();
        boolean kq = BCrypt.checkpw(matKhauNhap, mk);
        
        return kq;
    }
    // HÀM CẬP NHẬT TÀI KHOẢN
    public void capNhatTaiKhoan() {
        dto_TaiKhoan tk = new dto_TaiKhoan();

        dto_TaiKhoan tkSelected = duLieuDuocChon();

        tk = layThongTinNhap();

        tk.setMa(tkSelected.getMa());

        kiemTraMatKhau(tk, tkSelected);

        if (tk != null) {

            int kq = new bus_TaiKhoan().capNhatTaiKhoan(tk, isDoiMatKhau);
            if (kq == 0) {
                JOptionPane.showMessageDialog(null, "Lỗi");

            } else {
                
                giaoDienBanDau();
                JOptionPane.showMessageDialog(null, "Đã cập nhật");
            }

        }
    }

    // KIỂM TRA NGƯỜI DÙNG CÓ NHẬP MẬT KHẨU MỚI HAY KHÔNG
    public void kiemTraMatKhau(dto_TaiKhoan tkMoi, dto_TaiKhoan tkCu) {

        if (tkMoi.getMatKhau().equalsIgnoreCase(tkCu.getMatKhau()) == true) {
            this.isDoiMatKhau = false;
        }
    }

    // HÀM THÊM TÀI KHOẢN
    public void themTaiKhoan() {
        dto_TaiKhoan tk = new dto_TaiKhoan();
        tk = layThongTinNhap();

        if (tk != null) {

            int kq = new bus_TaiKhoan().themTaiKhoan(tk);

            if (kq == 0) {
                JOptionPane.showMessageDialog(null, "Lỗi");
            } else {
                giaoDienBanDau();
                JOptionPane.showMessageDialog(null, "Hoàn Tất");
            }
        }
    }

    //HÀM HIỂN THỊ THÔNG TIN DỮ LIỆU ĐƯỢC CHỌN QUA KHUNG BÊN PHẢI
    public void xemThongTinChiTiet(dto_TaiKhoan tk) {

        xemMode();

        txtTen.setText(tk.getHoTen());
        txtsdt.setText(tk.getSdt());
        txtTenDangNhap.setText(tk.getTenDangNhap());
        txtMatKhau.setText(tk.getMatKhau());

        cbLoai.setSelectedIndex(tk.getLoai() - 1);

        ImageIcon imgIcon = new ImageIcon(tk.getSrcImg());
        lblImg.setIcon(imgIcon);
    }

    // HÀM LẤY THÔNG TIN NHẬP Ở KHUNG BÊN PHẢI
    public dto_TaiKhoan layThongTinNhap() {

        dto_TaiKhoan tk = null;

        String ten = txtTen.getText();
        String strSdt = txtsdt.getText();
        String tenDangNhap = txtTenDangNhap.getText();
        String matKhau = txtMatKhau.getText();
        int loai;

        int dongDuocChon = cbLoai.getSelectedIndex();
        if (dongDuocChon == 0) {
            loai = 1;
        } else if (dongDuocChon == 1) {
            loai = 2;
        } else if (dongDuocChon == 2) {
            loai = 3;
        } else {
            loai = 0;
        }

        int kqkt = kiemTra(ten, strSdt, tenDangNhap, matKhau, loai);

        if (kqkt == 0) {
            JOptionPane.showMessageDialog(null, "Chưa nhập đủ thông tin");
        } else if (kqkt == 1) {
            JOptionPane.showMessageDialog(null, "Số điện thoại không hợp lệ");
        } else if (kqkt == 2) {
            JOptionPane.showMessageDialog(null, "Tên đăng nhập đã tồn tại");
        } else if (kqkt == 3) {

            tk = new dto_TaiKhoan();

            tk.setHoTen(ten);
            tk.setSdt(strSdt);
            tk.setLoai(loai);
            tk.setTenDangNhap(tenDangNhap);
            tk.setMatKhau(matKhau);

            if (img == null) {
                tk.setSrcImg("");
            } else {
                tk.setSrcImg(img);
            }
        }

        return tk;
    }

    // HÀM KIỂM TRA DỮ LIỆU NHẬP VÀO
    public int kiemTra(String ten, String strSdt, String tenDangNhap, String matKhau, int loai) {

        if (ten.isEmpty() || strSdt.isEmpty() || tenDangNhap.isEmpty() || matKhau.isEmpty() || loai == 0) {
            return 0; // dữ liệu chưa nhập đủ
        }
        long sdt;
        try {
            sdt = Long.parseLong(strSdt);
        } catch (Exception ex) {
            ex.printStackTrace();
            return 1; // số điện thoại nhập không đúng
        }

        if (isCapNhat == false) {
            if (kiemTraTenDangNhap(tenDangNhap) == true) {
                return 2;
            }
        }

        return 3;
    }

    // HÀM KIỂM TRA TÊN ĐĂNG NHẬP ĐÃ TỒN TẠI HAY CHƯA
    public boolean kiemTraTenDangNhap(String tenDangNhap) {

        for (dto_TaiKhoan tk : dsTaiKhoan) {
            if (tk.getTenDangNhap().equalsIgnoreCase(tenDangNhap)) {
                return true;
            }
        }
        return false;
    }

    // HÀM LẤY ĐỐI TƯỢNG ĐƯỢC CHỌN TRONG BẢNG
    public dto_TaiKhoan duLieuDuocChon() {
        int row = tbTaiKhoan.getSelectedRow();

        dto_TaiKhoan tk = null;

        if (row >= 0) {
            tk = new dto_TaiKhoan();
            tk = dsTaiKhoan.get(row);
        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn dữ liệu");
        }
        return tk;
    }

    // HÀM THIẾT LẬP GIAO DIỆN BAN ĐẦU
    public void giaoDienBanDau() {
        setupTable();
        taoMoiMode();
        
        img = "";
        lblImg.setIcon(new ImageIcon(img));
        dsTaiKhoan = new ArrayList<dto_TaiKhoan>();
        dsTaiKhoan = new bus_TaiKhoan().layDsTaiKhoan();
        
        reloadTable(dsTaiKhoan);

        dfcDsLoai = new DefaultComboBoxModel();
        dfcDsLoai.addElement("Quản Lý");
        dfcDsLoai.addElement("Ghi Danh");
        dfcDsLoai.addElement("Học Vụ");
        cbLoai.setModel(dfcDsLoai);

        xoaThongTinChiTiet();
    }

    // HÀM XÓA THÔNG TIN CHI TIÊT CHO KHUNG BÊN PHẢI
    public void xoaThongTinChiTiet() {
        txtTen.setText("");
        txtsdt.setText("");
        txtTenDangNhap.setText("");
        txtMatKhau.setText("");
        cbLoai.setSelectedIndex(-1);
        img ="";
    }

    // HÀM CÀI CHẾ ĐỘ XEM THÔNG TIN
    public void xemMode() {
        this.isCapNhat = false;
        this.isDoiMatKhau = false;

        lblMode.setText("Thông Tin");
        btnXacNhan.setVisible(false);
        btnChooseFile.setVisible(false);
        txtTenDangNhap.enable(false);
        txtMatKhau.enable(false);
        btnDoiMatKhau.setVisible(false);
    }

    // HÀM CÀI CHẾ ĐỘ TẠO MỚI
    public void taoMoiMode() {
        this.isCapNhat = false;
        this.isDoiMatKhau = false;

        lblMode.setText("Tạo Mới");
        btnXacNhan.setText("TẠO MỚI");

        btnXacNhan.setVisible(true);
        btnChooseFile.setVisible(true);
        xoaThongTinChiTiet();
        btnDoiMatKhau.setVisible(false);
        txtTenDangNhap.enable(true);
        txtMatKhau.enable(true);
        lblImg.setIcon(new ImageIcon(img));
    }

    // HÀM CÀI CHẾ ĐỘ CẬP NHẬT
    public void capNhatMode() {
        this.isCapNhat = true;

        lblMode.setText("Cập Nhật");
        btnXacNhan.setText("CẬP NHẬT");
        btnXacNhan.setVisible(true);
        btnChooseFile.setVisible(true);
        txtTenDangNhap.enable(false);
        txtMatKhau.enable(false);
        btnDoiMatKhau.setVisible(true);
    }

    // HÀM LOAD DỮ LIỆU LÊN BẢNG
    public void reloadTable(ArrayList<dto_TaiKhoan> dsTk) {

        int stt = 0;

        dtmTaiKhoan.setRowCount(0);

        for (dto_TaiKhoan tk : dsTk) {

            stt++;
            Vector<Object> vc = new Vector<Object>();

            vc.add(stt);
            vc.add(tk.getMa());
            vc.add(tk.getHoTen());
            vc.add(tk.getSdt());

            int loai = tk.getLoai();
            if (loai == 1) {
                vc.add("Quản Lý");
            } else if (loai == 2) {
                vc.add("Ghi Danh");
            } else {
                vc.add("Hoc Vu");
            }

            vc.add(tk.getTenDangNhap());
            vc.add(tk.getMatKhau());

            dtmTaiKhoan.addRow(vc);
        }

    }

    // HÀM TẠO BẢNG
    public void setupTable() {
        dtmTaiKhoan = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        dtmTaiKhoan.addColumn("STT");
        dtmTaiKhoan.addColumn("Mã TK");
        dtmTaiKhoan.addColumn("Tên Người Dùng");
        dtmTaiKhoan.addColumn("Điện Thoại");
        dtmTaiKhoan.addColumn("Loại TK");
        dtmTaiKhoan.addColumn("Tên Đăng Nhập");
        dtmTaiKhoan.addColumn("Mật Khẩu");

        tbTaiKhoan.setModel(dtmTaiKhoan);

        tbTaiKhoan.getColumnModel().getColumn(0).setMaxWidth(50);
        tbTaiKhoan.getColumnModel().getColumn(1).setMinWidth(80);
        tbTaiKhoan.getColumnModel().getColumn(1).setMaxWidth(80);
        tbTaiKhoan.getColumnModel().getColumn(2).setMinWidth(150);
        tbTaiKhoan.getColumnModel().getColumn(2).setMaxWidth(150);
        tbTaiKhoan.getColumnModel().getColumn(3).setMinWidth(100);
        tbTaiKhoan.getColumnModel().getColumn(3).setMaxWidth(100);
        tbTaiKhoan.getColumnModel().getColumn(4).setMaxWidth(80);
        tbTaiKhoan.getColumnModel().getColumn(4).setMinWidth(80);
        tbTaiKhoan.getColumnModel().getColumn(5).setMinWidth(150);
        tbTaiKhoan.getColumnModel().getColumn(5).setMaxWidth(150);

        tbTaiKhoan.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        tbTaiKhoan.getTableHeader().setOpaque(false);
        tbTaiKhoan.getTableHeader().setForeground(new Color(0, 0, 0));
        tbTaiKhoan.setSelectionBackground(new Color(0, 64, 128));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnCapNhatTK = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtTimTaiKhoan = new javax.swing.JTextField();
        btnThemTK = new javax.swing.JButton();
        jspKH = new javax.swing.JScrollPane();
        tbTaiKhoan = new javax.swing.JTable();
        pnThem = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtTen = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtsdt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        lblImg = new javax.swing.JLabel();
        btnChooseFile = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        cbLoai = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtTenDangNhap = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();
        btnDoiMatKhau = new javax.swing.JButton();
        btnXacNhan = new javax.swing.JButton();
        btnXoaTK = new javax.swing.JButton();
        lblMode = new javax.swing.JLabel();

        setBackground(new java.awt.Color(230, 245, 255));
        setMinimumSize(new java.awt.Dimension(1350, 634));
        setPreferredSize(new java.awt.Dimension(1350, 634));

        btnCapNhatTK.setBackground(new java.awt.Color(230, 245, 255));
        btnCapNhatTK.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCapNhatTK.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhatTK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/capnhat.png"))); // NOI18N
        btnCapNhatTK.setToolTipText("Cập Nhật");
        btnCapNhatTK.setContentAreaFilled(false);
        btnCapNhatTK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCapNhatTK.setFocusable(false);
        btnCapNhatTK.setMaximumSize(new java.awt.Dimension(129, 49));
        btnCapNhatTK.setMinimumSize(new java.awt.Dimension(129, 49));
        btnCapNhatTK.setOpaque(true);
        btnCapNhatTK.setPreferredSize(new java.awt.Dimension(129, 49));
        btnCapNhatTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatTKActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Tìm Kiếm");

        txtTimTaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtTimTaiKhoan.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtTimTaiKhoan.setToolTipText("Tìm theo mã, họ tên, điện thoại, tên đăng nhập.");
        txtTimTaiKhoan.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(153, 153, 153)));
        txtTimTaiKhoan.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtTimTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimTaiKhoanActionPerformed(evt);
            }
        });
        txtTimTaiKhoan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtTimTaiKhoanKeyReleased(evt);
            }
        });

        btnThemTK.setBackground(new java.awt.Color(230, 245, 255));
        btnThemTK.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemTK.setForeground(new java.awt.Color(255, 255, 255));
        btnThemTK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/taomoi.png"))); // NOI18N
        btnThemTK.setToolTipText("Tạo Mới");
        btnThemTK.setContentAreaFilled(false);
        btnThemTK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThemTK.setFocusable(false);
        btnThemTK.setMaximumSize(new java.awt.Dimension(217, 60));
        btnThemTK.setMinimumSize(new java.awt.Dimension(217, 60));
        btnThemTK.setOpaque(true);
        btnThemTK.setPreferredSize(new java.awt.Dimension(209, 30));
        btnThemTK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnThemTKMousePressed(evt);
            }
        });
        btnThemTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemTKActionPerformed(evt);
            }
        });

        jspKH.setBackground(new java.awt.Color(255, 255, 255));
        jspKH.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jspKH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jspKH.setPreferredSize(new java.awt.Dimension(469, 1000));
        jspKH.setViewportView(null);

        tbTaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbTaiKhoan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã KH", "Họ Tên", "Ngày Sinh", "Giới Tính", "Điện Thoại", "Địa Chỉ", "Trạng Thái", "Điểm Đầu Vào"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbTaiKhoan.setFocusable(false);
        tbTaiKhoan.setRowHeight(30);
        tbTaiKhoan.setRowMargin(5);
        tbTaiKhoan.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tbTaiKhoan.setShowGrid(true);
        tbTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbTaiKhoanMousePressed(evt);
            }
        });
        jspKH.setViewportView(tbTaiKhoan);

        pnThem.setBackground(new java.awt.Color(255, 255, 255));
        pnThem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 51, 102), 2));
        pnThem.setPreferredSize(new java.awt.Dimension(366, 600));

        jLabel2.setText("Tên Người Dùng");

        txtTen.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        jLabel3.setText("Số Điện Thoại");

        txtsdt.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtsdt.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel4.setText("Loại Người Dùng");

        lblImg.setBackground(new java.awt.Color(204, 204, 204));
        lblImg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImg.setPreferredSize(new java.awt.Dimension(300, 150));

        btnChooseFile.setBackground(new java.awt.Color(230, 245, 255));
        btnChooseFile.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnChooseFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/folder.png"))); // NOI18N
        btnChooseFile.setText(". . . ");
        btnChooseFile.setBorderPainted(false);
        btnChooseFile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnChooseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseFileActionPerformed(evt);
            }
        });

        jLabel7.setText("Ảnh Đại Diện");
        jLabel7.setRequestFocusEnabled(false);
        jLabel7.setVerifyInputWhenFocusTarget(false);

        cbLoai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Tên Đăng Nhập");

        jLabel6.setText("Mật Khẩu");

        btnDoiMatKhau.setBackground(new java.awt.Color(0, 102, 153));
        btnDoiMatKhau.setForeground(new java.awt.Color(255, 255, 255));
        btnDoiMatKhau.setText("Đổi Mật Khẩu");
        btnDoiMatKhau.setBorderPainted(false);
        btnDoiMatKhau.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDoiMatKhau.setVerifyInputWhenFocusTarget(false);
        btnDoiMatKhau.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                btnDoiMatKhauMousePressed(evt);
            }
        });
        btnDoiMatKhau.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMatKhauActionPerformed(evt);
            }
        });

        btnXacNhan.setBackground(new java.awt.Color(0, 102, 153));
        btnXacNhan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXacNhan.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhan.setText("Tạo Mới");
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

        javax.swing.GroupLayout pnThemLayout = new javax.swing.GroupLayout(pnThem);
        pnThem.setLayout(pnThemLayout);
        pnThemLayout.setHorizontalGroup(
            pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThemLayout.createSequentialGroup()
                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnThemLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(txtTen)
                            .addGroup(pnThemLayout.createSequentialGroup()
                                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtsdt, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5)
                                    .addGroup(pnThemLayout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnChooseFile, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel3))
                                .addGap(27, 27, 27)
                                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6)
                                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4)
                                    .addComponent(btnDoiMatKhau)))
                            .addComponent(lblImg, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnThemLayout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnThemLayout.setVerticalGroup(
            pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThemLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtTen, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addGap(8, 8, 8)
                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtsdt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbLoai, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnChooseFile, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDoiMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblImg, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnXoaTK.setBackground(new java.awt.Color(230, 245, 255));
        btnXoaTK.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoaTK.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaTK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/xoa.png"))); // NOI18N
        btnXoaTK.setToolTipText("Xóa");
        btnXoaTK.setContentAreaFilled(false);
        btnXoaTK.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoaTK.setFocusable(false);
        btnXoaTK.setMaximumSize(new java.awt.Dimension(129, 49));
        btnXoaTK.setMinimumSize(new java.awt.Dimension(129, 49));
        btnXoaTK.setOpaque(true);
        btnXoaTK.setPreferredSize(new java.awt.Dimension(129, 49));
        btnXoaTK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaTKActionPerformed(evt);
            }
        });

        lblMode.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMode.setForeground(new java.awt.Color(0, 0, 102));
        lblMode.setText("Tạo Mới");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jspKH, javax.swing.GroupLayout.PREFERRED_SIZE, 940, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtTimTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThemTK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnCapNhatTK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(btnXoaTK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(lblMode)
                        .addGap(166, 166, 166))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnXoaTK, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTimTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnThemTK, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCapNhatTK, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnThem, javax.swing.GroupLayout.DEFAULT_SIZE, 555, Short.MAX_VALUE)
                    .addComponent(jspKH, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(24, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCapNhatTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatTKActionPerformed
        if (tbTaiKhoan.getSelectedRow() < 0)
            JOptionPane.showMessageDialog(null, "Chưa chọn Tài Khoản muốn cập nhật");

        else
            capNhatMode();
    }//GEN-LAST:event_btnCapNhatTKActionPerformed

    private void txtTimTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimTaiKhoanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTimTaiKhoanActionPerformed

    private void btnThemTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemTKActionPerformed
        taoMoiMode();
    }//GEN-LAST:event_btnThemTKActionPerformed

    private void btnXoaTKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaTKActionPerformed
        dto_TaiKhoan tkSelected = duLieuDuocChon();
        
        if(tkSelected.getMa() == UI_DangNhap.layMaNguoiDung()){
            JOptionPane.showMessageDialog(null, "Không thể xóa tài khoản của chính mình");
        }
        else
            xoaTaiKhoan();
        
    }//GEN-LAST:event_btnXoaTKActionPerformed

    private void btnChooseFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChooseFileActionPerformed

        JFileChooser fileChooser = new JFileChooser();

        int returnValue = fileChooser.showOpenDialog(null);

        img = "";

        //Neu file duoc chon
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            img = selectedFile.getPath();

            lblImg.setIcon(new ImageIcon(img));
        }

    }//GEN-LAST:event_btnChooseFileActionPerformed

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        if (isCapNhat == true) {

            capNhatTaiKhoan();

        } else {
            themTaiKhoan();
        }
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void tbTaiKhoanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbTaiKhoanMousePressed

        int row = tbTaiKhoan.getSelectedRow();
        dto_TaiKhoan tk = duLieuDuocChon();
        xemThongTinChiTiet(tk);
    }//GEN-LAST:event_tbTaiKhoanMousePressed

    private void btnThemTKMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemTKMousePressed

    }//GEN-LAST:event_btnThemTKMousePressed

    private void btnDoiMatKhauActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMatKhauActionPerformed

        if (this.isDoiMatKhau == false) {
            pnXacNhanMatKhau pn = new pnXacNhanMatKhau();
            
            int luaChon = JOptionPane.showConfirmDialog(null, pn, "Xác Minh Người Dùng", JOptionPane.OK_CANCEL_OPTION);
            
            if (luaChon == 0) {
                
                String matKhauNhap = pn.getMatKhau();
                boolean ketQua = kiemTraMatKhau(matKhauNhap);
                
                if (ketQua == true) {
                    txtMatKhau.enable(true);
                    txtMatKhau.setText("");
                    btnDoiMatKhau.setText("Hủy Đổi Mật Khẩu");
                    this.isDoiMatKhau = true;

                } 
                else
                    JOptionPane.showMessageDialog(null, "Mật Khẩu Không Chính Xác");
            }
        } else {
            btnDoiMatKhau.setText("Đổi Mật Khẩu");
            this.isDoiMatKhau = false;
            txtMatKhau.enable(false);
            txtMatKhau.setText(duLieuDuocChon().getMatKhau());
        }


    }//GEN-LAST:event_btnDoiMatKhauActionPerformed

    private void btnDoiMatKhauMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDoiMatKhauMousePressed

    }//GEN-LAST:event_btnDoiMatKhauMousePressed

    private void txtTimTaiKhoanKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimTaiKhoanKeyReleased
       String text = txtTimTaiKhoan.getText();
       
       if(text.isEmpty() == true){
           reloadTable(dsTaiKhoan);
       }
       else{
           hienThiDsTimKiem(text);
       }
    }//GEN-LAST:event_txtTimTaiKhoanKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhatTK;
    private javax.swing.JButton btnChooseFile;
    private javax.swing.JButton btnDoiMatKhau;
    private javax.swing.JButton btnThemTK;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JButton btnXoaTK;
    private javax.swing.JComboBox<String> cbLoai;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jspKH;
    private javax.swing.JLabel lblImg;
    private javax.swing.JLabel lblMode;
    private javax.swing.JPanel pnThem;
    private javax.swing.JTable tbTaiKhoan;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtTen;
    private javax.swing.JTextField txtTenDangNhap;
    private javax.swing.JTextField txtTimTaiKhoan;
    private javax.swing.JTextField txtsdt;
    // End of variables declaration//GEN-END:variables
}
