package UI.LopHoc.ChiTietLopHoc;

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
 * @author ThinkPro
 */
public class UI_ChiTietLop extends javax.swing.JDialog {

    /**
     * Creates new form FormLichSuLH
     */
    private dto_LopHoc lopHoc;

    public UI_ChiTietLop() {
        initComponents();
    }

    public UI_ChiTietLop(dto_LopHoc lopHoc) {

        initComponents();

        this.lopHoc = new dto_LopHoc();
        this.lopHoc = lopHoc;

        giaoDienBanDau();
        reloadDuLieu();
    }

    // BIẾN TỰ ĐỊNH NGHĨA
    private DefaultTableModel dtmDanhSach;
    private ArrayList<dto_kqht> dsKqht;
    private bus_kqht bus_kqht = new bus_kqht();
    private dto_kqht khDuocChon;
    boolean tinhNghe = true;
    boolean tinhNoi = true;
    boolean tinhDoc = true;
    boolean tinhViet = true;

    // HÀM XÓA LỚP ĐANG HỌC
    public void xoaKhachHangKhoiLop() {

        layKhachHangDuocChon();

        if (this.khDuocChon != null) {

            int luaChon = JOptionPane.showConfirmDialog(null, "Bạn muốn xóa khách hàng này ra khỏi lớp ?\nNhấn Yes để tiếp tục", "Xác Nhận Xóa", JOptionPane.YES_NO_OPTION);

            if (luaChon == 0) {
                int kq = new bus_kqht().xoaKhachHangKhoiLop(khDuocChon);

                if (kq > 0) {
                    JOptionPane.showMessageDialog(null, "Đã xóa khách hàng khỏi lớp");
                    reloadDuLieu();
                } else {
                    JOptionPane.showMessageDialog(null, "Lỗi");
                }
            }

        }
    }

    // HÀM CẬP NHẬT KQHT
    public void capNhatKqht() {

        dto_kqht kqht = new dto_kqht();
        kqht = layThongTinNhap();

        if (kqht != null) {
            layKhachHangDuocChon();

            kqht.setMaKh(this.khDuocChon.getMaKh());
            kqht.setMaLop(this.khDuocChon.getMaLop());

            int kq = new bus_kqht().capNhatKqht(kqht);

            if (kq > 0) {

                giaoDienBanDau();
                JOptionPane.showMessageDialog(null, "Đã cập nhật điểm");
                reloadDuLieu();
            } else {
                JOptionPane.showMessageDialog(null, "Lỗi");
            }

        }
    }

    // HÀM SET GIAO DIỆN BAN ĐẦU
    public void giaoDienBanDau() {
        setupTable();
        hienThiThongTinLop();

        if (this.lopHoc.getCt().getTinhDoc() == 0) {
            txtDoc.setVisible(false);
            lblDoc.setVisible(false);
            tinhDoc = false;
        } else {
            txtDoc.setEditable(true);
        }

        if (this.lopHoc.getCt().getTinhNghe() == 0) {
            txtNghe.setVisible(false);
            lblNghe.setVisible(false);
            tinhNghe = false;
        } else {
            txtNghe.setEditable(true);
        }

        if (this.lopHoc.getCt().getTinhNoi() == 0) {
            txtNoi.setVisible(false);
            lblNoi.setVisible(false);
            tinhNoi = false;
        } else {
            txtNoi.setEditable(true);
        }

        if (this.lopHoc.getCt().getTinhViet() == 0) {
            txtViet.setVisible(false);
            lblViet.setVisible(false);
            tinhViet = false;
        } else {
            txtViet.setEditable(true);
        }

        btnXacNhan.setVisible(false);
    }

    // HÀM LẤY THÔNG TIN NHẬP VÀO
    public dto_kqht layThongTinNhap() {

        dto_kqht kqht = null;

        String strNghe = txtNghe.getText();
        String strNoi = txtNoi.getText();
        String strDoc = txtDoc.getText();
        String strViet = txtViet.getText();

        float nghe = 0;
        float noi = 0;
        float doc = 0;
        float viet = 0;

        try {
            if (tinhNghe == true) {
                nghe = Float.parseFloat(strNghe);
            }

            if (tinhNoi == true) {
                noi = Float.parseFloat(strNoi);
            }

            if (tinhDoc == true) {
                doc = Float.parseFloat(strDoc);
            }

            if (tinhViet == true) {
                viet = Float.parseFloat(strViet);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Điểm nhập vào không hợp lệ");
            return null;
        }

        float tong = 0;

        if (this.lopHoc.getCt().getCachTinhDiem() == 1) {
            if (tinhNghe == true) {
                tong += nghe;
            }

            if (tinhNoi == true) {
                tong += noi;
            }

            if (tinhDoc == true) {
                tong += doc;
            }

            if (tinhViet == true) {
                tong += viet;
            }

        } else {

            int size = 0;

            if (tinhNghe == true) {
                tong += nghe;
                size++;
            }

            if (tinhNoi == true) {
                tong += noi;
                size++;
            }

            if (tinhDoc == true) {
                tong += doc;
                size++;
            }

            if (tinhViet == true) {
                tong += viet;
                size++;
            }

            tong /= size;
            tong = Math.round(tong);
        }

        if (tong > this.lopHoc.getCt().getChungChi().getDiemToiDa()) {
            JOptionPane.showMessageDialog(null, "Tổng điểm thành phần không thể lớn hơn điểm tối đa của chứng chỉ");
            return null;
        }

        kqht = new dto_kqht();

        if (tinhNghe == true) {
            kqht.setNghe(nghe);
        }
        if (tinhNoi == true) {
            kqht.setNoi(noi);
        }
        if (tinhDoc == true) {
            kqht.setDoc(doc);
        }
        if (tinhViet == true) {
            kqht.setViet(viet);
        }

        kqht.setTong(tong);

        return kqht;
    }

    // HÀM SET GIAO DIỆN CẬP NHẬT
    public void giaoDienCapNhat() {
        lblKqht.setText("Cập Nhật Điểm");
        btnXacNhan.setVisible(true);
    }

    // HÀM SET GIAO DIỆN CHI TIẾT ĐIỂM
    public void giaoDienBangDiem() {
        layKhachHangDuocChon();

        lblKqht.setText("Thông Tin");
        btnXacNhan.setVisible(false);

        txtMaKh.setText(this.khDuocChon.getMaKh() + "");
        txtTenKh.setText(this.khDuocChon.getKh().getTenKh());

        if (this.khDuocChon.getTong() == -1) {
            txtTongDiem.setText("");
        } else {
            txtTongDiem.setText(this.khDuocChon.getTong() + "");
        }

        if (this.khDuocChon.getNghe() == -1) {
            txtNghe.setText("");
        } else {
            txtNghe.setText(this.khDuocChon.getNghe() + "");
        }

        if (this.khDuocChon.getNoi() == -1) {
            txtNoi.setText("");
        } else {
            txtNoi.setText(this.khDuocChon.getNoi() + "");
        }

        if (this.khDuocChon.getDoc() == -1) {
            txtDoc.setText("");
        } else {
            txtDoc.setText(this.khDuocChon.getDoc() + "");
        }

        if (this.khDuocChon.getViet() == -1) {
            txtViet.setText("");
        } else {
            txtViet.setText(this.khDuocChon.getViet() + "");
        }
    }

    // HÀM LẤY KHÁCH HÀNG ĐƯỢC CHON
    public void layKhachHangDuocChon() {

        int row = tbDanhSach.getSelectedRow();

        if (row > -1) {

            this.khDuocChon = new dto_kqht();
            this.khDuocChon = this.dsKqht.get(row);
        }
    }

    // HÀM HIỂN THỊ THÔNG TIN LỚP HỌC
    public void hienThiThongTinLop() {

        lblMaLop.setText(this.lopHoc.getMaLop() + "");
        lblTenLop.setText(this.lopHoc.getTenLop());
        lblChuongTrinh.setText(lopHoc.getCt().getTenCt());
        lblNgayBd.setText(lopHoc.layNgayBd() + "");
        lblNgayKt.setText(lopHoc.layNgayKt() + "");
        lblDiemDauVao.setText(lopHoc.getCt().getDiemDauVao() + "");
        lblDiemDauRa.setText(lopHoc.getCt().getDiemDauRa() + "");
        lblChungChi.setText(this.lopHoc.getCt().getChungChi().getTenCc());
        lblDiemToiDa.setText(this.lopHoc.getCt().getChungChi().getDiemToiDa() + "");

        this.setTitle(this.lopHoc.getTenLop() + " - " + this.lopHoc.getMaLop());

    }

    // HÀM RELOAD DỮ LIỆU
    public void reloadDuLieu() {

        this.dsKqht = bus_kqht.layDsKqht(this.lopHoc.getMaLop());
        this.dsKqht = bus_kqht.layKhachHang(this.dsKqht);
        reloadTable(this.dsKqht);
    }

    // HÀM RELOAD TABLE
    public void reloadTable(ArrayList<dto_kqht> ds) {

        int stt = 0;
        this.dsKqht = ds;

        dtmDanhSach.setRowCount(0);

        for (dto_kqht kqht : ds) {

            stt++;
            Vector<Object> vc = new Vector<Object>();
            dto_KhachHang kh = kqht.getKh();

            vc.add(stt);
            vc.add(kqht.getMaKh());
            vc.add(kh.getTenKh());

            if (kh.getGioiTinh() == 1) {
                vc.add("Nam");
            } else {
                vc.add("Nữ");
            }

            vc.add(kh.layNgaySinh());
            vc.add(kh.getSdt());

            if (kqht.getNghe() == -1) {
                vc.add("");
            } else {
                vc.add(kqht.getNghe());
            }

            if (kqht.getNoi() == -1) {
                vc.add("");
            } else {
                vc.add(kqht.getNoi());
            }

            if (kqht.getDoc() == -1) {
                vc.add("");
            } else {
                vc.add(kqht.getDoc());
            }

            if (kqht.getViet() == -1) {
                vc.add("");
            } else {
                vc.add(kqht.getViet());
            }

            if (kqht.getTong() == -1) {
                vc.add("");
            } else {
                vc.add(kqht.getTong());
            }

            dtmDanhSach.addRow(vc);
        }
    }

    // HÀM TẠO BẢNG KHÁCH HÀNG
    public void setupTable() {
        dtmDanhSach = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        dtmDanhSach.addColumn("STT");
        dtmDanhSach.addColumn("Mã KH");
        dtmDanhSach.addColumn("Họ Tên");
        dtmDanhSach.addColumn("Giới Tính");
        dtmDanhSach.addColumn("Ngày Sinh");
        dtmDanhSach.addColumn("Điện Thoại");
        dtmDanhSach.addColumn("Nghe");
        dtmDanhSach.addColumn("Nói");
        dtmDanhSach.addColumn("Đọc");
        dtmDanhSach.addColumn("Viết");
        dtmDanhSach.addColumn("Tổng");

        tbDanhSach.setModel(dtmDanhSach);

        tbDanhSach.getColumnModel().getColumn(0).setMaxWidth(50);
        tbDanhSach.getColumnModel().getColumn(1).setMaxWidth(80);
        tbDanhSach.getColumnModel().getColumn(2).setMinWidth(150);
        tbDanhSach.getColumnModel().getColumn(2).setMaxWidth(200);
        tbDanhSach.getColumnModel().getColumn(2).setMaxWidth(80);
        tbDanhSach.getColumnModel().getColumn(4).setMinWidth(100);
        tbDanhSach.getColumnModel().getColumn(4).setMaxWidth(100);
        tbDanhSach.getColumnModel().getColumn(5).setMaxWidth(100);
        tbDanhSach.getColumnModel().getColumn(5).setMinWidth(100);

        tbDanhSach.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        tbDanhSach.getTableHeader().setOpaque(false);
        tbDanhSach.getTableHeader().setForeground(new Color(0, 0, 0));
        tbDanhSach.setSelectionBackground(new Color(0, 64, 128));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        pnLop = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jspLH = new javax.swing.JScrollPane();
        tbDanhSach = new javax.swing.JTable();
        btnXoa = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        lblKqht = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtMaKh = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTenKh = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        btnXacNhan = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        lblNghe = new javax.swing.JLabel();
        txtDoc = new javax.swing.JTextField();
        lblViet = new javax.swing.JLabel();
        lblNoi = new javax.swing.JLabel();
        lblDoc = new javax.swing.JLabel();
        txtNghe = new javax.swing.JTextField();
        txtNoi = new javax.swing.JTextField();
        txtViet = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        txtTongDiem = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        lblMaLop = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblTenLop = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblChuongTrinh = new javax.swing.JLabel();
        lblNgayKt = new javax.swing.JLabel();
        lblNgayBd = new javax.swing.JLabel();
        lblDiemDauVao = new javax.swing.JLabel();
        lblDiemDauRa = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lblChungChi = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblDiemToiDa = new javax.swing.JLabel();
        btnCapNhat = new javax.swing.JButton();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CHI TIẾT LỚP HỌC");
        setResizable(false);

        pnLop.setBackground(new java.awt.Color(230, 245, 255));
        pnLop.setMinimumSize(new java.awt.Dimension(1350, 729));
        pnLop.setPreferredSize(new java.awt.Dimension(1350, 729));

        jPanel1.setBackground(new java.awt.Color(230, 245, 255));

        jspLH.setBackground(new java.awt.Color(255, 255, 255));
        jspLH.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jspLH.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jspLH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jspLH.setPreferredSize(new java.awt.Dimension(450, 400));
        jspLH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jspLHMousePressed(evt);
            }
        });

        tbDanhSach.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tbDanhSach.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã KH", "Tên Khách Hàng", "Giới Tính", "Ngày Sinh", "Điện Thoại", "Nghe", "Nói", "Đọc", "Viết", "TB"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true, false, true, true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbDanhSach.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        tbDanhSach.setDoubleBuffered(true);
        tbDanhSach.setFocusable(false);
        tbDanhSach.setRowHeight(30);
        tbDanhSach.setSelectionBackground(new java.awt.Color(232, 57, 95));
        tbDanhSach.getTableHeader().setReorderingAllowed(false);
        tbDanhSach.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbDanhSachMousePressed(evt);
            }
        });
        jspLH.setViewportView(tbDanhSach);
        if (tbDanhSach.getColumnModel().getColumnCount() > 0) {
            tbDanhSach.getColumnModel().getColumn(0).setMaxWidth(50);
            tbDanhSach.getColumnModel().getColumn(1).setMaxWidth(100);
            tbDanhSach.getColumnModel().getColumn(2).setMinWidth(150);
            tbDanhSach.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        btnXoa.setBackground(new java.awt.Color(230, 245, 255));
        btnXoa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/xoa.png"))); // NOI18N
        btnXoa.setContentAreaFilled(false);
        btnXoa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoa.setFocusable(false);
        btnXoa.setMaximumSize(new java.awt.Dimension(129, 49));
        btnXoa.setMinimumSize(new java.awt.Dimension(129, 49));
        btnXoa.setOpaque(true);
        btnXoa.setPreferredSize(new java.awt.Dimension(129, 49));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnThem.setBackground(new java.awt.Color(230, 245, 255));
        btnThem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/taomoi.png"))); // NOI18N
        btnThem.setToolTipText("Thêm Khách Hàng Vào Lớp");
        btnThem.setContentAreaFilled(false);
        btnThem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThem.setFocusable(false);
        btnThem.setOpaque(true);
        btnThem.setPreferredSize(new java.awt.Dimension(183, 40));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jspLH, javax.swing.GroupLayout.PREFERRED_SIZE, 878, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jspLH, javax.swing.GroupLayout.PREFERRED_SIZE, 675, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(250, 250, 250));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 0, 0, new java.awt.Color(0, 0, 0)));

        lblKqht.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblKqht.setForeground(new java.awt.Color(0, 0, 102));
        lblKqht.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblKqht.setText("Kết Quả Học Tập");
        lblKqht.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(51, 0, 102)));

        jLabel4.setText("Mã KH");

        txtMaKh.setEditable(false);

        jLabel5.setText("Tên KH");

        txtTenKh.setEditable(false);

        btnXacNhan.setBackground(new java.awt.Color(0, 102, 153));
        btnXacNhan.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhan.setText("Xác Nhận");
        btnXacNhan.setBorderPainted(false);
        btnXacNhan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });

        jPanel5.setBackground(new java.awt.Color(250, 250, 250));

        lblNghe.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblNghe.setForeground(new java.awt.Color(51, 0, 102));
        lblNghe.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNghe.setText("NGHE");

        txtDoc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDoc.setForeground(new java.awt.Color(0, 51, 102));
        txtDoc.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        lblViet.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblViet.setForeground(new java.awt.Color(51, 0, 102));
        lblViet.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblViet.setText("VIẾT");

        lblNoi.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblNoi.setForeground(new java.awt.Color(51, 0, 102));
        lblNoi.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNoi.setText("NÓI");

        lblDoc.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        lblDoc.setForeground(new java.awt.Color(51, 0, 102));
        lblDoc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDoc.setText("ĐỌC");

        txtNghe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNghe.setForeground(new java.awt.Color(0, 51, 102));
        txtNghe.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtNoi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtNoi.setForeground(new java.awt.Color(102, 0, 0));
        txtNoi.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txtNoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNoiActionPerformed(evt);
            }
        });

        txtViet.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtViet.setForeground(new java.awt.Color(102, 0, 0));
        txtViet.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNghe, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNghe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(40, 40, 40)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtNoi, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNoi, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblViet, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtViet, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap(48, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNghe)
                    .addComponent(lblNoi)
                    .addComponent(lblDoc)
                    .addComponent(lblViet))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNghe, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNoi, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDoc, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtViet, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40))
        );

        jLabel18.setText("Tổng Điểm");

        txtTongDiem.setEditable(false);
        txtTongDiem.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtTongDiem.setForeground(new java.awt.Color(153, 0, 51));
        txtTongDiem.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(lblKqht, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXacNhan))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel18)
                                .addGap(18, 18, 18)
                                .addComponent(txtTongDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4))
                                .addGap(35, 35, 35)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtTenKh, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtMaKh, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblKqht, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtMaKh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtTenKh, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(19, 19, 19)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTongDiem, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(18, 18, 18)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(173, 173, 173)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(250, 250, 250));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 1, 1, 0, new java.awt.Color(51, 0, 102)));

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setText("Mã Lớp:");

        lblMaLop.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblMaLop.setText("55555");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Lớp:");

        lblTenLop.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblTenLop.setText("IELTS 5.0 J12");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setText("Chương Trình:");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setText("Ngày KT:");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setText("Ngày BĐ:");

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel16.setText("Điểm Đâu Ra:");

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel17.setText("Điểm Đầu Vào:");

        lblChuongTrinh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblChuongTrinh.setText("IELTS 5.0 J12");

        lblNgayKt.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNgayKt.setText("15/06/2020");

        lblNgayBd.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblNgayBd.setText("15/06/2020");

        lblDiemDauVao.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDiemDauVao.setText("500");

        lblDiemDauRa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblDiemDauRa.setText("700");

        jLabel1.setText("Chứng Chỉ:");

        lblChungChi.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblChungChi.setText("IELTS");

        jLabel2.setText("Điểm Tối Đa:");

        lblDiemToiDa.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblDiemToiDa.setText("9.0");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblMaLop, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblTenLop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel15)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblNgayBd))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblDiemDauVao))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblChungChi, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(38, 38, 38)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblDiemToiDa))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addGap(18, 18, 18)
                                .addComponent(lblDiemDauRa))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblNgayKt)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addGap(18, 18, 18)
                        .addComponent(lblChuongTrinh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblMaLop)
                    .addComponent(jLabel3)
                    .addComponent(lblTenLop))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(lblChuongTrinh))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblChungChi)
                    .addComponent(jLabel2)
                    .addComponent(lblDiemToiDa))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(lblNgayBd)
                    .addComponent(jLabel14)
                    .addComponent(lblNgayKt))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(lblDiemDauVao)
                    .addComponent(jLabel16)
                    .addComponent(lblDiemDauRa))
                .addGap(22, 22, 22))
        );

        btnCapNhat.setBackground(new java.awt.Color(230, 245, 255));
        btnCapNhat.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCapNhat.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/capnhat.png"))); // NOI18N
        btnCapNhat.setToolTipText("Cập Nhật Điểm");
        btnCapNhat.setContentAreaFilled(false);
        btnCapNhat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCapNhat.setFocusable(false);
        btnCapNhat.setOpaque(true);
        btnCapNhat.setPreferredSize(new java.awt.Dimension(183, 40));
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnLopLayout = new javax.swing.GroupLayout(pnLop);
        pnLop.setLayout(pnLopLayout);
        pnLopLayout.setHorizontalGroup(
            pnLopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnLopLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(54, 54, 54)
                .addGroup(pnLopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        pnLopLayout.setVerticalGroup(
            pnLopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnLopLayout.createSequentialGroup()
                .addGroup(pnLopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnLopLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCapNhat, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnLop, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnLop, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1366, 768));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        xoaKhachHangKhoiLop();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        giaoDienCapNhat();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        new FormThemKhachHang(this.lopHoc).show();
        reloadDuLieu();
    }//GEN-LAST:event_btnThemActionPerformed

    private void jspLHMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jspLHMousePressed

    }//GEN-LAST:event_jspLHMousePressed

    private void tbDanhSachMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbDanhSachMousePressed
        giaoDienBangDiem();
    }//GEN-LAST:event_tbDanhSachMousePressed

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        capNhatKqht();
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void txtNoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNoiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNoiActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UI_ChiTietLop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI_ChiTietLop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI_ChiTietLop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI_ChiTietLop.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UI_ChiTietLop().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JButton btnXoa;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JList<String> jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jspLH;
    private javax.swing.JLabel lblChungChi;
    private javax.swing.JLabel lblChuongTrinh;
    private javax.swing.JLabel lblDiemDauRa;
    private javax.swing.JLabel lblDiemDauVao;
    private javax.swing.JLabel lblDiemToiDa;
    private javax.swing.JLabel lblDoc;
    private javax.swing.JLabel lblKqht;
    private javax.swing.JLabel lblMaLop;
    private javax.swing.JLabel lblNgayBd;
    private javax.swing.JLabel lblNgayKt;
    private javax.swing.JLabel lblNghe;
    private javax.swing.JLabel lblNoi;
    private javax.swing.JLabel lblTenLop;
    private javax.swing.JLabel lblViet;
    private javax.swing.JPanel pnLop;
    private javax.swing.JTable tbDanhSach;
    private javax.swing.JTextField txtDoc;
    private javax.swing.JTextField txtMaKh;
    private javax.swing.JTextField txtNghe;
    private javax.swing.JTextField txtNoi;
    private javax.swing.JTextField txtTenKh;
    private javax.swing.JTextField txtTongDiem;
    private javax.swing.JTextField txtViet;
    // End of variables declaration//GEN-END:variables
}
