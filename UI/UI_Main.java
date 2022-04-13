/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import DTO.dto_TaiKhoan;
import UI.ChuongTrinh.UI_ChuongTrinh;
import UI.LopHoc.UI_LopHoc;
import UI.KhachHang.UI_KhachHang;
import UI.DangNhap.UI_DangNhap;
import UI.GV_Phong.UI_GV_Phong;
import UI.TaiKhoan.UI_TaiKhoan;
import UI.TaiKhoan.pnDoiMatKhau;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JButton;

public final class UI_Main extends javax.swing.JFrame {

    public UI_Main() {
        initComponents();
        setResizable(false);
        setAllTabButtonColor("thongke");
        hienThiNgay();

    }
    //Biến tự định nghĩa
    private UI_LopHoc ui_lop = new UI_LopHoc();
    private UI_KhachHang ui_kh = new UI_KhachHang();
    private UI_ChuongTrinh ui_ct = new UI_ChuongTrinh();
    private UI_TaiKhoan ui_tk = new UI_TaiKhoan();
    private UI_GV_Phong ui_gv_phong = new UI_GV_Phong();

    // HÀM THAY ĐỔI MÀU
    public void changeTabButtonColor(JButton button, Color currentColor, Color hoverColor) {

        CustomBorder customBorder = new CustomBorder(null, null, new CustomBorder.BorderPiece(3), null);
        button.setBorder(customBorder);

        ((CustomBorder) button.getBorder()).setColor(currentColor);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                ((CustomBorder) button.getBorder()).setColor(currentColor);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                ((CustomBorder) button.getBorder()).setColor(hoverColor);
            }
        });
    }

    // HÀM SET MÀU CHO TỪNG BUTTON
    public void setAllTabButtonColor(String name) {
        if (name.equals("thongke")) {
            this.changeTabButtonColor(btnChuongTrinh, CustomBorder.WHITE, CustomBorder.CHUONGTRINHHOC);
            this.changeTabButtonColor(btnKhachHang, CustomBorder.WHITE, CustomBorder.KHACHHANG);
            this.changeTabButtonColor(btnLopHoc, CustomBorder.WHITE, CustomBorder.LOPHOC);
            this.changeTabButtonColor(btnTaiKhoan, CustomBorder.WHITE, CustomBorder.TAIKHOAN);
            this.changeTabButtonColor(btnThongKe, CustomBorder.THONGKE, CustomBorder.THONGKE);
            this.changeTabButtonColor(btnGiaoVienPhong, CustomBorder.WHITE, CustomBorder.TKB);
        } else if (name.equals("khachhang")) {
            this.changeTabButtonColor(btnChuongTrinh, CustomBorder.WHITE, CustomBorder.CHUONGTRINHHOC);
            this.changeTabButtonColor(btnKhachHang, CustomBorder.KHACHHANG, CustomBorder.KHACHHANG);
            this.changeTabButtonColor(btnLopHoc, CustomBorder.WHITE, CustomBorder.LOPHOC);
            this.changeTabButtonColor(btnTaiKhoan, CustomBorder.WHITE, CustomBorder.TAIKHOAN);
            this.changeTabButtonColor(btnThongKe, CustomBorder.WHITE, CustomBorder.THONGKE);
            this.changeTabButtonColor(btnGiaoVienPhong, CustomBorder.WHITE, CustomBorder.TKB);

        } else if (name.equals("lophoc")) {
            this.changeTabButtonColor(btnChuongTrinh, CustomBorder.WHITE, CustomBorder.CHUONGTRINHHOC);
            this.changeTabButtonColor(btnKhachHang, CustomBorder.WHITE, CustomBorder.KHACHHANG);
            this.changeTabButtonColor(btnLopHoc, CustomBorder.LOPHOC, CustomBorder.LOPHOC);
            this.changeTabButtonColor(btnTaiKhoan, CustomBorder.WHITE, CustomBorder.TAIKHOAN);
            this.changeTabButtonColor(btnThongKe, CustomBorder.WHITE, CustomBorder.THONGKE);
            this.changeTabButtonColor(btnGiaoVienPhong, CustomBorder.WHITE, CustomBorder.TKB);
        } else if (name.equals("chuongtrinh")) {
            this.changeTabButtonColor(btnChuongTrinh, CustomBorder.CHUONGTRINHHOC, CustomBorder.CHUONGTRINHHOC);
            this.changeTabButtonColor(btnKhachHang, CustomBorder.WHITE, CustomBorder.KHACHHANG);
            this.changeTabButtonColor(btnLopHoc, CustomBorder.WHITE, CustomBorder.LOPHOC);
            this.changeTabButtonColor(btnTaiKhoan, CustomBorder.WHITE, CustomBorder.TAIKHOAN);
            this.changeTabButtonColor(btnThongKe, CustomBorder.WHITE, CustomBorder.THONGKE);
            this.changeTabButtonColor(btnGiaoVienPhong, CustomBorder.WHITE, CustomBorder.TKB);
        } else if (name.equals("taikhoan")) {
            this.changeTabButtonColor(btnChuongTrinh, CustomBorder.WHITE, CustomBorder.CHUONGTRINHHOC);
            this.changeTabButtonColor(btnKhachHang, CustomBorder.WHITE, CustomBorder.KHACHHANG);
            this.changeTabButtonColor(btnLopHoc, CustomBorder.WHITE, CustomBorder.LOPHOC);
            this.changeTabButtonColor(btnTaiKhoan, CustomBorder.TAIKHOAN, CustomBorder.TAIKHOAN);
            this.changeTabButtonColor(btnThongKe, CustomBorder.WHITE, CustomBorder.THONGKE);
            this.changeTabButtonColor(btnGiaoVienPhong, CustomBorder.WHITE, CustomBorder.TKB);
        } else if (name.equals("gv_phong")) {
            this.changeTabButtonColor(btnChuongTrinh, CustomBorder.WHITE, CustomBorder.CHUONGTRINHHOC);
            this.changeTabButtonColor(btnKhachHang, CustomBorder.WHITE, CustomBorder.KHACHHANG);
            this.changeTabButtonColor(btnLopHoc, CustomBorder.WHITE, CustomBorder.LOPHOC);
            this.changeTabButtonColor(btnTaiKhoan, CustomBorder.WHITE, CustomBorder.TAIKHOAN);
            this.changeTabButtonColor(btnThongKe, CustomBorder.WHITE, CustomBorder.THONGKE);
            this.changeTabButtonColor(btnGiaoVienPhong, CustomBorder.WHITE, CustomBorder.THONGKE);
            this.changeTabButtonColor(btnGiaoVienPhong, CustomBorder.TKB, CustomBorder.TKB);
        }

    }

    // HÀM HIỂN THỊ NGÀY HIỆN TẠI
    public void hienThiNgay() {

        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        lblNgay.setText(sdf.format(date));
    }

    //method giao diện cho nhân viên ghi danh 
    public void showGhiDanh() {
        ui_lop.hideBtnLop();
        ui_ct.hideBtnCt();
    }

    //method giao diện cho nhân viên học vụ
    public void showHocVu() {
        ui_kh.hideBtnKh();
        ui_ct.hideBtnCt();
    }

    //method hiển thị thông tin người dùng bên góc phải phía trên UI_Main
    public void setThongTinDangNhap(dto_TaiKhoan tk) {
        
        String chucVu = "";
        
        if(tk.getLoai() == 1)
            chucVu = "Quản Lý";
        else if(tk.getLoai() == 2)
            chucVu = "Ghi Danh";
        else
            chucVu = "Học vụ";
        
        txtThongTinDangNhap.setText(tk.getMa() + " | " +chucVu + " | " +  tk.getHoTen());
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
        btnThongKe = new javax.swing.JButton();
        btnKhachHang = new javax.swing.JButton();
        btnLopHoc = new javax.swing.JButton();
        btnTaiKhoan = new javax.swing.JButton();
        txtThongTinDangNhap = new javax.swing.JTextField();
        pnBody = new javax.swing.JPanel();
        pnThongKe = new javax.swing.JPanel();
        btnChuongTrinh = new javax.swing.JButton();
        lblNgay = new javax.swing.JLabel();
        btnGiaoVienPhong = new javax.swing.JButton();
        btnDangXuat = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản Lý Trung Tâm Anh Ngữ");
        setMinimumSize(new java.awt.Dimension(1366, 768));
        setSize(new java.awt.Dimension(1366, 768));

        jPanel1.setBackground(new java.awt.Color(230, 245, 255));
        jPanel1.setMinimumSize(new java.awt.Dimension(1350, 729));
        jPanel1.setPreferredSize(new java.awt.Dimension(1350, 729));

        btnThongKe.setBackground(new java.awt.Color(153, 255, 153));
        btnThongKe.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnThongKe.setForeground(new java.awt.Color(0, 0, 51));
        btnThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/thongke_new.png"))); // NOI18N
        btnThongKe.setText("THỐNG KÊ");
        btnThongKe.setContentAreaFilled(false);
        btnThongKe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnThongKe.setFocusable(false);
        btnThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeActionPerformed(evt);
            }
        });

        btnKhachHang.setBackground(new java.awt.Color(153, 255, 153));
        btnKhachHang.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnKhachHang.setForeground(new java.awt.Color(0, 0, 51));
        btnKhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/kh.png"))); // NOI18N
        btnKhachHang.setText("KHÁCH HÀNG");
        btnKhachHang.setContentAreaFilled(false);
        btnKhachHang.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnKhachHang.setFocusable(false);
        btnKhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhachHangActionPerformed(evt);
            }
        });

        btnLopHoc.setBackground(new java.awt.Color(153, 255, 153));
        btnLopHoc.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnLopHoc.setForeground(new java.awt.Color(0, 0, 51));
        btnLopHoc.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/classroom.png"))); // NOI18N
        btnLopHoc.setText("LỚP HỌC");
        btnLopHoc.setContentAreaFilled(false);
        btnLopHoc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLopHoc.setFocusable(false);
        btnLopHoc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLopHocActionPerformed(evt);
            }
        });

        btnTaiKhoan.setBackground(new java.awt.Color(153, 255, 153));
        btnTaiKhoan.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnTaiKhoan.setForeground(new java.awt.Color(0, 0, 51));
        btnTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/lock1.png"))); // NOI18N
        btnTaiKhoan.setText("TÀI KHOẢN");
        btnTaiKhoan.setContentAreaFilled(false);
        btnTaiKhoan.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnTaiKhoan.setFocusable(false);
        btnTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaiKhoanActionPerformed(evt);
            }
        });

        txtThongTinDangNhap.setEditable(false);
        txtThongTinDangNhap.setBackground(new java.awt.Color(230, 245, 255));
        txtThongTinDangNhap.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        txtThongTinDangNhap.setText("12323 | Nguyễn Thị Huyền Trang");
        txtThongTinDangNhap.setToolTipText("Thông Tin Người Dùng");
        txtThongTinDangNhap.setBorder(null);
        txtThongTinDangNhap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtThongTinDangNhapActionPerformed(evt);
            }
        });

        pnBody.setBackground(new java.awt.Color(204, 255, 255));
        pnBody.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 0, 0, new java.awt.Color(0, 51, 102)));
        pnBody.setMinimumSize(new java.awt.Dimension(1350, 660));
        pnBody.setPreferredSize(new java.awt.Dimension(1350, 634));
        pnBody.setLayout(new java.awt.CardLayout());

        pnThongKe.setBackground(new java.awt.Color(255, 255, 255));
        pnThongKe.setMinimumSize(new java.awt.Dimension(1350, 643));
        pnThongKe.setPreferredSize(new java.awt.Dimension(1350, 660));

        javax.swing.GroupLayout pnThongKeLayout = new javax.swing.GroupLayout(pnThongKe);
        pnThongKe.setLayout(pnThongKeLayout);
        pnThongKeLayout.setHorizontalGroup(
            pnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1350, Short.MAX_VALUE)
        );
        pnThongKeLayout.setVerticalGroup(
            pnThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 643, Short.MAX_VALUE)
        );

        pnBody.add(pnThongKe, "card4");

        btnChuongTrinh.setBackground(new java.awt.Color(153, 255, 153));
        btnChuongTrinh.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnChuongTrinh.setForeground(new java.awt.Color(0, 0, 51));
        btnChuongTrinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/book.png"))); // NOI18N
        btnChuongTrinh.setText("CHƯƠNG TRÌNH");
        btnChuongTrinh.setBorder(null);
        btnChuongTrinh.setContentAreaFilled(false);
        btnChuongTrinh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnChuongTrinh.setFocusable(false);
        btnChuongTrinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChuongTrinhActionPerformed(evt);
            }
        });

        lblNgay.setText("Ngày");

        btnGiaoVienPhong.setBackground(new java.awt.Color(153, 255, 153));
        btnGiaoVienPhong.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnGiaoVienPhong.setForeground(new java.awt.Color(0, 0, 51));
        btnGiaoVienPhong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/teacher.png"))); // NOI18N
        btnGiaoVienPhong.setText("GIÁO VIÊN & PHÒNG");
        btnGiaoVienPhong.setBorder(null);
        btnGiaoVienPhong.setContentAreaFilled(false);
        btnGiaoVienPhong.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGiaoVienPhong.setFocusable(false);
        btnGiaoVienPhong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGiaoVienPhongActionPerformed(evt);
            }
        });

        btnDangXuat.setBackground(new java.awt.Color(230, 245, 255));
        btnDangXuat.setForeground(new java.awt.Color(255, 255, 255));
        btnDangXuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dangxuat.png"))); // NOI18N
        btnDangXuat.setToolTipText("Đăng Xuất");
        btnDangXuat.setBorder(null);
        btnDangXuat.setBorderPainted(false);
        btnDangXuat.setContentAreaFilled(false);
        btnDangXuat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDangXuat.setDefaultCapable(false);
        btnDangXuat.setFocusPainted(false);
        btnDangXuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangXuatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(txtThongTinDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnBody, javax.swing.GroupLayout.PREFERRED_SIZE, 1350, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(btnDangXuat)
                .addGap(60, 60, 60)
                .addComponent(btnThongKe)
                .addGap(80, 80, 80)
                .addComponent(btnKhachHang)
                .addGap(80, 80, 80)
                .addComponent(btnLopHoc)
                .addGap(80, 80, 80)
                .addComponent(btnGiaoVienPhong)
                .addGap(80, 80, 80)
                .addComponent(btnChuongTrinh)
                .addGap(80, 80, 80)
                .addComponent(btnTaiKhoan)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnKhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLopHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnGiaoVienPhong, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnChuongTrinh, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnDangXuat))
                .addGap(10, 10, 10)
                .addComponent(pnBody, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtThongTinDangNhap, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(1366, 768));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtThongTinDangNhapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtThongTinDangNhapActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtThongTinDangNhapActionPerformed

    private void btnTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaiKhoanActionPerformed
        setAllTabButtonColor("taikhoan");

        pnBody.removeAll();
        pnBody.repaint();
        pnBody.revalidate();
        
        
        if (UI_DangNhap.layTaiKhoanDangNhap().getLoai() == 1) {
            pnBody.add(new UI_TaiKhoan());
        } else {
            pnBody.add(new pnDoiMatKhau());
        }
        
        pnBody.add(new UI_TaiKhoan());
        pnBody.repaint();
        pnBody.revalidate();
    }//GEN-LAST:event_btnTaiKhoanActionPerformed

    private void btnLopHocActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLopHocActionPerformed
        setAllTabButtonColor("lophoc");

        pnBody.removeAll();
        pnBody.repaint();
        pnBody.revalidate();

        if (UI_DangNhap.layTaiKhoanDangNhap().getLoai() == 2) {
            pnBody.add(new UI_LopHoc(true));
        } else {
            pnBody.add(new UI_LopHoc());
        }
        pnBody.repaint();
        pnBody.revalidate();
    }//GEN-LAST:event_btnLopHocActionPerformed

    private void btnKhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhachHangActionPerformed
        setAllTabButtonColor("khachhang");

        pnBody.removeAll();
        pnBody.repaint();
        pnBody.revalidate();

        if (UI_DangNhap.layTaiKhoanDangNhap().getLoai() == 3) {
            pnBody.add(new UI_KhachHang(true));
        } else {
            pnBody.add(new UI_KhachHang());
        }
        pnBody.add(new UI_KhachHang());
        pnBody.repaint();
        pnBody.revalidate();
    }//GEN-LAST:event_btnKhachHangActionPerformed

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeActionPerformed
        setAllTabButtonColor("thongke");

        pnBody.removeAll();
        pnBody.repaint();
        pnBody.add(pnThongKe);
        pnBody.repaint();
        pnBody.revalidate();
    }//GEN-LAST:event_btnThongKeActionPerformed

    private void btnChuongTrinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChuongTrinhActionPerformed
        setAllTabButtonColor("chuongtrinh");
        pnBody.removeAll();
        pnBody.repaint();
        if (UI_DangNhap.layTaiKhoanDangNhap().getLoai() != 1) {
            pnBody.add(new UI_ChuongTrinh(true));
        } else {
            pnBody.add(new UI_ChuongTrinh());
        }
        pnBody.add(new UI_ChuongTrinh());
        pnBody.repaint();
        pnBody.revalidate();
    }//GEN-LAST:event_btnChuongTrinhActionPerformed

    private void btnGiaoVienPhongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGiaoVienPhongActionPerformed
        setAllTabButtonColor("gv_phong");

        pnBody.removeAll();
        pnBody.repaint();

        if (UI_DangNhap.layTaiKhoanDangNhap().getLoai() != 1) {
            pnBody.add(new UI_GV_Phong(true));
        } else {
            pnBody.add(new UI_GV_Phong());
        }
        pnBody.add(new UI_GV_Phong());
        pnBody.repaint();
        pnBody.revalidate();
    }//GEN-LAST:event_btnGiaoVienPhongActionPerformed

    private void btnDangXuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangXuatActionPerformed
        dispose();
        new UI_DangNhap().show();
    }//GEN-LAST:event_btnDangXuatActionPerformed

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(UI_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(UI_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(UI_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(UI_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
            @Override
            public void run() {
                new UI_Main().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChuongTrinh;
    private javax.swing.JButton btnDangXuat;
    private javax.swing.JButton btnGiaoVienPhong;
    private javax.swing.JButton btnKhachHang;
    private javax.swing.JButton btnLopHoc;
    private javax.swing.JButton btnTaiKhoan;
    private javax.swing.JButton btnThongKe;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblNgay;
    private javax.swing.JPanel pnBody;
    private javax.swing.JPanel pnThongKe;
    private javax.swing.JTextField txtThongTinDangNhap;
    // End of variables declaration//GEN-END:variables
}
