package UI.ChungChi;

import BUS.BCrypt;
import BUS.bus_ChungChi;
import DTO.dto_ChungChi;
import UI.DangNhap.UI_DangNhap;
import UI.pnXacNhanMatKhau;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class UI_ChungChi extends javax.swing.JDialog {

    /**
     * Creates new form UI_ChungChi
     */
    public UI_ChungChi() {
        this.setModal(true);
        initComponents();
        giaoDienBanDau();
    }

    // BIẾN TỰ ĐỊNH NGHĨA
    private boolean isCapNhat;
    String img;
    DefaultTableModel dtmChungChi;
    ArrayList<dto_ChungChi> dsChungChi;

    // HÀM XÓA CHỨNG CHỈ
    public void xoaChungChi() {
        
        dto_ChungChi selected = duLieuDuocChon();

        if (selected != null) {
            pnXacNhanMatKhau pn = new pnXacNhanMatKhau();
            
            int luaChon = JOptionPane.showConfirmDialog(null, pn, "Xác Minh Người Dùng", JOptionPane.OK_CANCEL_OPTION);

            if (luaChon == 0) {
                
                String matKhauNhap = pn.getMatKhau();
                boolean ketQua = kiemTraMatKhau(matKhauNhap);

                if (ketQua == true) {
                    int rs = new bus_ChungChi().xoaChungChi(selected.getMaCc());

                    if (rs == 0) {
                        JOptionPane.showMessageDialog(null, "Lỗi");
                    } else {
                        giaoDienBanDau();
                        JOptionPane.showMessageDialog(null, "Đã Xóa");
                    }
                }
                else
                    JOptionPane.showMessageDialog(null, "Mật Khẩu Không Chính Xác");
            }
        }
    }
    
    // HÀM XÁC MINH NGƯỜI D
    public boolean kiemTraMatKhau(String matKhauNhap){
        String mk = UI_DangNhap.layMatKhauDangNhap();
        
        boolean kq = BCrypt.checkpw(matKhauNhap, mk);
        return kq;
    }
    
    // HÀM CẬP NHẬT CHỨNG CHỈ
    public void capNhatChungChi() {
        dto_ChungChi cc = new dto_ChungChi();

        dto_ChungChi selected = duLieuDuocChon();

        cc = layThongTinNhap();

        cc.setMaCc(selected.getMaCc());

        if (cc != null) {
            int kq = new bus_ChungChi().capNhatChungChi(cc);

            if (kq == 0) {
                JOptionPane.showMessageDialog(null, "Lỗi");

            } else {

                giaoDienBanDau();
                JOptionPane.showMessageDialog(null, "Đã cập nhật");
            }
        }
    }

    // HÀM THÊM CHỨNG CHỈ
    public void themChungChi() {
        dto_ChungChi cc = new dto_ChungChi();

        cc = layThongTinNhap();

        if (cc != null) {
            int kq = new bus_ChungChi().themChungChi(cc);

            if (kq == 0) {
                JOptionPane.showMessageDialog(null, "Lỗi");
            } else {

                giaoDienBanDau();
                JOptionPane.showMessageDialog(null, "Hoàn Tất");
            }
        }
    }

    // HÀM LẤY THÔNG TIN NHẬP Ở KHUNG BÊN PHẢI
    public dto_ChungChi layThongTinNhap() {

        dto_ChungChi cc = null;

        String ten = txtTenChungChi.getText();
        String strDiemToiDa = txtDiemToiDa.getText();
        String noiDung = txtNoiDung.getText();

        int kq = kiemTra(ten, strDiemToiDa, noiDung);

        if (kq == 0) {
            JOptionPane.showMessageDialog(null, "Chưa nhập đủ thông tin");
        } else if (kq == 1) {
            JOptionPane.showMessageDialog(null, "Điểm nhập vào không hợp lệ");
        } else {

            cc = new dto_ChungChi();
            Float diemToiDa = Float.parseFloat(strDiemToiDa);

            cc.setTenCc(ten);
            cc.setDiemToiDa(diemToiDa);
            cc.setNoiDung(noiDung);

            if (img == null) {
                cc.setSrcImg("");
            } else {
                cc.setSrcImg(img);
            }
        }

        return cc;
    }

    // HÀM KIỂM TRA DỮ LIỆU NHẬP VÀO
    public int kiemTra(String ten, String strDiemToiDa, String noiDung) {

        if (ten.isEmpty() || strDiemToiDa.isEmpty() || noiDung.isEmpty()) {
            return 0; // dữ liệu chưa điền đủ
        }
        Float diemToiDa;
        try {

            diemToiDa = Float.parseFloat(strDiemToiDa);
        } catch (Exception e) {

            e.printStackTrace();
            return 1;//Số nhập vào không đúng
        }

        if (diemToiDa >= 1000) {
            return 1;
        }

        return 2;
    }

    // HÀM RESET THÔNG TIN CHI TIẾT CHO KHUNG BÊN PHẢI
    public void xoaThongTinChiTiet() {
        txtTenChungChi.setText("");
        txtDiemToiDa.setText("");
        txtNoiDung.setText("");
        lblImg.setIcon(null);
    }

    // HÀM HIỂN THỊ THÔNG TIN CHI TIẾT CHO DỮ LIỆU ĐƯỢC CHỌN
    public void xemThongTinChiTiet(dto_ChungChi cc) {
        xemMode();
        txtTenChungChi.setText(cc.getTenCc());
        txtDiemToiDa.setText(cc.getDiemToiDa() + "");
        txtNoiDung.setText(cc.getNoiDung());

        ImageIcon img = new ImageIcon(cc.getSrcImg());

        lblImg.setIcon(img);

    }

    // HÀM LẤY ĐỐI TƯỢNG ĐƯỢC CHỌN TRONG BẢNG
    public dto_ChungChi duLieuDuocChon() {
        int row = tbChungChi.getSelectedRow();

        dto_ChungChi cc = null;

        if (row >= 0) {
            cc = new dto_ChungChi();
            cc = dsChungChi.get(row);
        } else {
            JOptionPane.showMessageDialog(null, "Chưa chọn dữ liệu");
        }
        return cc;
    }

    // HÀM THIẾT LẬP GIAO DIỆN BAN ĐẦU
    public void giaoDienBanDau() {
        setupTable();
        taoMoiMode();

        xoaThongTinChiTiet();

        this.img = "";
        dsChungChi = new ArrayList<dto_ChungChi>();
        dsChungChi = new bus_ChungChi().layDsChungChi();
        reloadTable(dsChungChi);

    }

    // HÀM CÀI CHẾ ĐỘ XEM THÔNG TIN
    public void xemMode() {
        this.isCapNhat = false;
        lblMode.setText("Thông Tin");
        btnXacNhan.setVisible(false);
        btnChooseFile.setVisible(false);
    }

    // HÀM CÀI CHẾ ĐỘ TẠO MỚI
    public void taoMoiMode() {
        this.isCapNhat = false;
        lblMode.setText("Tạo Mới");
        btnXacNhan.setText("TẠO MỚI");
        btnXacNhan.setVisible(true);
        btnChooseFile.setVisible(true);
        xoaThongTinChiTiet();

    }

    // HÀM CÀI CHẾ ĐỘ CẬP NHẬT
    public void capNhatMode() {
        this.isCapNhat = true;

        lblMode.setText("Cập Nhật");
        btnXacNhan.setText("CẬP NHẬT");
        btnXacNhan.setVisible(true);
        btnChooseFile.setVisible(true);
    }

    // HÀM LOAD DỮ LIỆU LÊN BẢNG
    public void reloadTable(ArrayList<dto_ChungChi> dsCc) {

        dsChungChi = new ArrayList<dto_ChungChi>();
        dsChungChi = dsCc;

        int stt = 0;

        dtmChungChi.setRowCount(0);

        for (dto_ChungChi cc : dsChungChi) {

            stt++;
            Vector<Object> vc = new Vector<Object>();

            vc.add(stt);
            vc.add(cc.getMaCc());
            vc.add(cc.getTenCc());
            vc.add(cc.getDiemToiDa());
            vc.add(cc.getNoiDung());

            dtmChungChi.addRow(vc);
        }

    }
    // HÀM TẠO BẢNG

    public void setupTable() {
        dtmChungChi = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        dtmChungChi.addColumn("STT");
        dtmChungChi.addColumn("Mã Chứng Chỉ");
        dtmChungChi.addColumn("Tên Chứng Chỉ");
        dtmChungChi.addColumn("Điểm Tối Đa");
        dtmChungChi.addColumn("Nội Dung");

        tbChungChi.setModel(dtmChungChi);

        tbChungChi.getColumnModel().getColumn(0).setMaxWidth(50);
        tbChungChi.getColumnModel().getColumn(1).setMinWidth(120);
        tbChungChi.getColumnModel().getColumn(1).setMaxWidth(120);
        tbChungChi.getColumnModel().getColumn(2).setMinWidth(150);
        tbChungChi.getColumnModel().getColumn(2).setMaxWidth(150);
        tbChungChi.getColumnModel().getColumn(3).setMinWidth(100);
        tbChungChi.getColumnModel().getColumn(3).setMaxWidth(100);

        tbChungChi.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        tbChungChi.getTableHeader().setOpaque(false);
        tbChungChi.getTableHeader().setForeground(new Color(0, 0, 0));
        tbChungChi.setSelectionBackground(new Color(0, 64, 128));

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnChuongTtrinh = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbChungChi = new javax.swing.JTable();
        btnThemChungChi = new javax.swing.JButton();
        btnCapNhatChungChi = new javax.swing.JButton();
        btnXoaChungChi = new javax.swing.JButton();
        pnThem = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtTenChungChi = new javax.swing.JTextField();
        txtDiemToiDa = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtNoiDung = new javax.swing.JTextArea();
        lblImg = new javax.swing.JLabel();
        btnChooseFile = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btnXacNhan = new javax.swing.JButton();
        lblMode = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Quản Lý Chứng Chỉ");

        pnChuongTtrinh.setBackground(new java.awt.Color(230, 245, 255));
        pnChuongTtrinh.setMinimumSize(new java.awt.Dimension(1350, 729));
        pnChuongTtrinh.setPreferredSize(new java.awt.Dimension(1350, 729));

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        tbChungChi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        tbChungChi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tbChungChi.setMaximumSize(new java.awt.Dimension(2147483647, 10000));
        tbChungChi.setRowHeight(30);
        tbChungChi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbChungChiMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tbChungChiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                tbChungChiMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbChungChiMousePressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbChungChi);

        btnThemChungChi.setBackground(new java.awt.Color(230, 245, 255));
        btnThemChungChi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThemChungChi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/taomoi.png"))); // NOI18N
        btnThemChungChi.setToolTipText("Tạo Mới");
        btnThemChungChi.setContentAreaFilled(false);
        btnThemChungChi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThemChungChi.setFocusable(false);
        btnThemChungChi.setOpaque(true);
        btnThemChungChi.setPreferredSize(new java.awt.Dimension(183, 40));
        btnThemChungChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemChungChiActionPerformed(evt);
            }
        });

        btnCapNhatChungChi.setBackground(new java.awt.Color(230, 245, 255));
        btnCapNhatChungChi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnCapNhatChungChi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/capnhat.png"))); // NOI18N
        btnCapNhatChungChi.setToolTipText("Tạo Mới");
        btnCapNhatChungChi.setContentAreaFilled(false);
        btnCapNhatChungChi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCapNhatChungChi.setFocusable(false);
        btnCapNhatChungChi.setOpaque(true);
        btnCapNhatChungChi.setPreferredSize(new java.awt.Dimension(183, 40));
        btnCapNhatChungChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatChungChiActionPerformed(evt);
            }
        });

        btnXoaChungChi.setBackground(new java.awt.Color(230, 245, 255));
        btnXoaChungChi.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnXoaChungChi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/xoa.png"))); // NOI18N
        btnXoaChungChi.setToolTipText("Tạo Mới");
        btnXoaChungChi.setContentAreaFilled(false);
        btnXoaChungChi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnXoaChungChi.setFocusable(false);
        btnXoaChungChi.setOpaque(true);
        btnXoaChungChi.setPreferredSize(new java.awt.Dimension(183, 40));
        btnXoaChungChi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaChungChiActionPerformed(evt);
            }
        });

        pnThem.setBackground(new java.awt.Color(230, 245, 255));
        pnThem.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 102)));
        pnThem.setMaximumSize(new java.awt.Dimension(391, 640));
        pnThem.setMinimumSize(new java.awt.Dimension(391, 640));
        pnThem.setOpaque(false);
        pnThem.setRequestFocusEnabled(false);
        pnThem.setVerifyInputWhenFocusTarget(false);

        jLabel2.setText("Tên Chứng Chỉ");

        txtTenChungChi.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        txtDiemToiDa.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDiemToiDa.setForeground(new java.awt.Color(0, 102, 153));
        txtDiemToiDa.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        txtNoiDung.setColumns(20);
        txtNoiDung.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        txtNoiDung.setRows(5);
        jScrollPane2.setViewportView(txtNoiDung);

        lblImg.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImg.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        lblImg.setMaximumSize(new java.awt.Dimension(343, 200));
        lblImg.setPreferredSize(new java.awt.Dimension(300, 200));
        lblImg.setRequestFocusEnabled(false);
        lblImg.setVerifyInputWhenFocusTarget(false);

        btnChooseFile.setBackground(new java.awt.Color(230, 245, 255));
        btnChooseFile.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/folder.png"))); // NOI18N
        btnChooseFile.setText("...");
        btnChooseFile.setBorderPainted(false);
        btnChooseFile.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnChooseFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChooseFileActionPerformed(evt);
            }
        });

        jLabel7.setText("Ảnh Đại Diện");

        jLabel3.setText("Điểm Tối Đa");

        jLabel4.setText("Nội Dung");

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

        lblMode.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lblMode.setForeground(new java.awt.Color(0, 0, 102));
        lblMode.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMode.setText("Tạo Mới");
        lblMode.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 1, new java.awt.Color(51, 0, 102)));

        javax.swing.GroupLayout pnThemLayout = new javax.swing.GroupLayout(pnThem);
        pnThem.setLayout(pnThemLayout);
        pnThemLayout.setHorizontalGroup(
            pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThemLayout.createSequentialGroup()
                .addContainerGap(62, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChooseFile)
                .addGap(449, 449, 449))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThemLayout.createSequentialGroup()
                .addComponent(lblMode, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(pnThemLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnThemLayout.createSequentialGroup()
                        .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtTenChungChi, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(29, 29, 29)
                        .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(txtDiemToiDa, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnThemLayout.createSequentialGroup()
                        .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblImg, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))
                        .addContainerGap())))
        );
        pnThemLayout.setVerticalGroup(
            pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnThemLayout.createSequentialGroup()
                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMode, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDiemToiDa, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTenChungChi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnThemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnChooseFile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblImg, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnChuongTtrinhLayout = new javax.swing.GroupLayout(pnChuongTtrinh);
        pnChuongTtrinh.setLayout(pnChuongTtrinhLayout);
        pnChuongTtrinhLayout.setHorizontalGroup(
            pnChuongTtrinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnChuongTtrinhLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(pnChuongTtrinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnChuongTtrinhLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(pnThem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnChuongTtrinhLayout.createSequentialGroup()
                        .addComponent(btnXoaChungChi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnCapNhatChungChi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnThemChungChi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        pnChuongTtrinhLayout.setVerticalGroup(
            pnChuongTtrinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnChuongTtrinhLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnChuongTtrinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnCapNhatChungChi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnXoaChungChi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnThemChungChi, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnChuongTtrinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnThem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnChuongTtrinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnChuongTtrinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(1366, 768));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemChungChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemChungChiActionPerformed
        taoMoiMode();

    }//GEN-LAST:event_btnThemChungChiActionPerformed

    private void btnCapNhatChungChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatChungChiActionPerformed

        dto_ChungChi selected = duLieuDuocChon();

        if (selected != null) {
            xemThongTinChiTiet(selected);
            capNhatMode();
        }


    }//GEN-LAST:event_btnCapNhatChungChiActionPerformed

    private void btnXoaChungChiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaChungChiActionPerformed

        xoaChungChi();

    }//GEN-LAST:event_btnXoaChungChiActionPerformed

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        if (isCapNhat == true) {
            capNhatChungChi();
        } else {
            themChungChi();
        }
    }//GEN-LAST:event_btnXacNhanActionPerformed

    private void tbChungChiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbChungChiMouseClicked


    }//GEN-LAST:event_tbChungChiMouseClicked

    private void tbChungChiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbChungChiMouseEntered

    }//GEN-LAST:event_tbChungChiMouseEntered

    private void tbChungChiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbChungChiMouseExited

    }//GEN-LAST:event_tbChungChiMouseExited

    private void tbChungChiMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbChungChiMousePressed
        int row = tbChungChi.getSelectedRow();

        dto_ChungChi cc = duLieuDuocChon();

        xemThongTinChiTiet(cc);
    }//GEN-LAST:event_tbChungChiMousePressed

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
            java.util.logging.Logger.getLogger(UI_ChungChi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI_ChungChi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI_ChungChi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI_ChungChi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new UI_ChungChi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhatChungChi;
    private javax.swing.JButton btnChooseFile;
    private javax.swing.JButton btnThemChungChi;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JButton btnXoaChungChi;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblImg;
    private javax.swing.JLabel lblMode;
    private javax.swing.JPanel pnChuongTtrinh;
    private javax.swing.JPanel pnThem;
    private javax.swing.JTable tbChungChi;
    private javax.swing.JTextField txtDiemToiDa;
    private javax.swing.JTextArea txtNoiDung;
    private javax.swing.JTextField txtTenChungChi;
    // End of variables declaration//GEN-END:variables
}
