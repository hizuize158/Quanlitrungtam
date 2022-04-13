package UI.LopHoc;

import BUS.bus_LopHoc;
import DTO.dto_GiaoVien;
import DTO.dto_Lich;
import DTO.dto_LopHoc;
import DTO.dto_Phong;
import DTO.dto_Thu;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FormLichHoc extends javax.swing.JDialog {

    private dto_LopHoc lop;

    /**
     * Creates new form FormLichHoc
     */
    public FormLichHoc() {
        initComponents();
    }

    public FormLichHoc(dto_LopHoc lopChon) {
        this.setModal(true);
        initComponents();
        setGiaoDienBanDau();

        this.lop = new dto_LopHoc();
        this.lop = lopChon;
    }

    // BIẾN TỰ ĐỊNH NGHĨA
    Font fontLblSelected = new Font("Tahoma", Font.BOLD, 11);
    Font fontLblNotSelected = new Font("Tahoma", Font.PLAIN, 11);
    Color colorSelected = new Color(153, 0, 0);
    Color colorNotSelected = new Color(0, 0, 0);

    dto_Thu t2;
    dto_Thu t3;
    dto_Thu t4;
    dto_Thu t5;
    dto_Thu t6;
    dto_Thu t7;
    dto_Thu Cn;

    DefaultTableModel dtmNgayNghi;
    ArrayList<dto_GiaoVien> dsGiaoVien;
    ArrayList<dto_Phong> dsPhong;
    ArrayList<dto_Lich> dsLichHeThong;
    ArrayList<Date> dsNgayNghi;
    ArrayList<dto_Thu> dsThu;

    Date dBatDau;
    int soBuoiHoc;

    // HÀM THÊM DS LỊCH TẠO VÀO CSDL
    public void themLich() {

        int kqNhap = layThongTinNhap();

        if (kqNhap == 1) {
            JOptionPane.showMessageDialog(null, "Thời gian học không hợp lệ hoặc chưa nhập đủ thông tin !\nVui lòng kiểm tra lại");
        } else if (kqNhap == 2) {
            JOptionPane.showMessageDialog(null, "Lịch học lớp mới phải được tính từ ngày hôm nay");
        } else {
            ArrayList<dto_Lich> dsLich = new ArrayList<dto_Lich>();

            dsLich = lichHocChiTiet(dsThu);

            if (dsLich != null) {

                if (lop.getDsLich() != null) {
                    new bus_LopHoc().xoaDsLich(lop.getMaLop());
                }

                int kq = new bus_LopHoc().themDsLich(dsLich);

                if (kq == 1) {

                    this.lop.setNgayBd(dsLich.get(0).getTgBd());
                    this.lop.setNgayKt(dsLich.get(dsLich.size() - 1).getTgKt());
                    this.lop.setSoBuoi(this.soBuoiHoc);

                    int kqCapNhat = new bus_LopHoc().capNhapThoiGianHoc(this.lop);

                    if (kqCapNhat < 0) {
                        JOptionPane.showMessageDialog(null, "Cập nhật thời gian học cho lớp không thành công");
                    } else {
                        UI_LopHoc.reloadDuLieuLop();
                    }

                    this.dispose();
                    JOptionPane.showMessageDialog(null, "Cập Nhật Lịch Hoàn Tất");

                    lop.setDsLich(new bus_LopHoc().layDsLichTheoLop(lop.getMaLop()));
                    UI_LopHoc.reloadTableLich(lop);
                } else {
                    JOptionPane.showMessageDialog(null, "Lỗi Cơ Sở Dữ Liệu");
                }
            }
        }
        this.soBuoiHoc = 0;
    }

    // XÓA NGÀY NGHỈ
    public void xoaNgayNghi() {

        int dong = tbNgayNghi.getSelectedRow();

        if (dong > -1) {

            dsNgayNghi.remove(dong);
            reloadTable();
        } else {
            JOptionPane.showMessageDialog(null, "Chưa Chọn Ngày Nghỉ Hoặc Lịch Học Không Hợp Lệ");
        }
    }

    // THÊM NGÀY NGHỈ
    public void themNgayNghi(Date d) {
        dsNgayNghi.add(d);
        reloadTable();
    }

    // HÀM LẤY THÔNG TIN NHẬP
    public int layThongTinNhap() {

        dsThu = new ArrayList<dto_Thu>();
        ArrayList<dto_Lich> dsLich = new ArrayList<dto_Lich>();

        String strBuoiHoc = txtBuoiHoc.getText();

        if (strBuoiHoc.isEmpty()) {
            return 1;
        }

        try {
            this.soBuoiHoc = Integer.parseInt(strBuoiHoc);
        } catch (Exception ex) {
            ex.printStackTrace();
            return 1;
        }

        if (ckT2.isSelected()) {
            t2 = setThongTinThu(2, gioBdT2, phutBdT2, gioKtT2, phutKtT2, cbGvT2, cbPhongT2);
            if (t2 == null) {
                return 1;
            }

            dsThu.add(t2);
        }
        if (ckT3.isSelected()) {
            t3 = setThongTinThu(3, gioBdT3, phutBdT3, gioKtT3, phutKtT3, cbGvT3, cbPhongT3);
            if (t3 == null) {
                return 1;
            }
            dsThu.add(t3);
        }
        if (ckT4.isSelected()) {
            t4 = setThongTinThu(4, gioBdT4, phutBdT4, gioKtT4, phutKtT4, cbGvT4, cbPhongT4);

            if (t4 == null) {
                return 1;
            }
            dsThu.add(t4);
        }
        if (ckT5.isSelected()) {
            t5 = setThongTinThu(5, gioBdT5, phutBdT5, gioKtT5, phutKtT5, cbGvT5, cbPhongT5);

            if (t5 == null) {
                return 1;
            }
            dsThu.add(t5);
        }
        if (ckT6.isSelected()) {
            t6 = setThongTinThu(6, gioBdT6, phutBdT6, gioKtT6, phutKtT6, cbGvT6, cbPhongT6);

            if (t6 == null) {
                return 1;
            }
            dsThu.add(t6);
        }
        if (ckT7.isSelected()) {
            t7 = setThongTinThu(7, gioBdT7, phutBdT7, gioKtT7, phutKtT7, cbGvT7, cbPhongT7);

            if (t7 == null) {
                return 1;
            }
            dsThu.add(t7);
        }
        if (ckCn.isSelected()) {
            Cn = setThongTinThu(1, gioBdCn, phutBdCn, gioKtCn, phutKtCn, cbGvCn, cbPhongCn);
            if (Cn == null) {
                return 1;
            }
            dsThu.add(Cn);
        }

        dBatDau = dcTuNgay.getDate();

        if (dBatDau == null) {
            return 1;
        }

        dBatDau.setHours(23);
        dBatDau.setMinutes(59);
        Date d = new Date();
        d.setHours(0);
        d.setMinutes(0);
        d.setSeconds(0);

        int kq = d.compareTo(dBatDau);
        if (d.after(dBatDau)) {
            return 2;
        }

        return 0;
    }

    // HÀM TÍNH TỪNG NGÀY HỌC CHI TIẾT
    public ArrayList<dto_Lich> lichHocChiTiet(ArrayList<dto_Thu> dsThu) {

        ArrayList<dto_Lich> dsLich = new ArrayList<dto_Lich>();

        dto_Lich lich = null;
        int demBuoiHoc = 0;

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.dBatDau);

        boolean trungNgayNghi = false;
        int trungGvPhong = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        while (demBuoiHoc < this.soBuoiHoc && trungGvPhong == 0) {

            // set trùng ngày nghỉ về lại false.
            trungNgayNghi = false;

            // lấy ngày hiện tại trong calendar ra.
            Date dCalendar = calendar.getTime();

            // kiểm tra xem ngày trong calendar có trùng với ngày nghỉ hay không
            for (Date d : dsNgayNghi) {
                if (d.getYear() == dCalendar.getYear() && d.getMonth() == dCalendar.getMonth() && d.getDate() == dCalendar.getDate()) {
                    trungNgayNghi = true;
                    break;
                }
            }

            // nếu không trùng vào ngày nghỉ thì bắt đầu xét thứ của ngày trong Calendar xem có trùng với thứ nào của lịch học không
            if (trungNgayNghi == false) {

                // kiểm tra ngày trong Calendar có Thứ trùng với Thứ nào trong lịch học không.
                // nếu trùng thì đánh dấu ngày đó là ngày học - tạo lịch ngày mới và thêm vào ds. Còn không thì thoát vòng lặp và tăng ngày lên
                for (dto_Thu thu : dsThu) {

                    if (calendar.get(calendar.DAY_OF_WEEK) == thu.getDayOfWeek()) {

                        lich = new dto_Lich();
                        lich.setMaLop(this.lop.getMaLop());
                        lich.setMaGv(thu.getGiaoVien().getMaGv());
                        lich.setMaPhong(thu.getPhong().getMaPhong());

                        String strD = sdf.format(dCalendar);

                        lich.setTgBd(thu.layTgBd(strD));
                        lich.setTgKt(thu.layTgKt(strD));

                        // kiểm tra lịch có bị trùng giáo viên và phòng học không
                        for (dto_Lich lichCu : dsLichHeThong) {

                            if (lichCu.getMaLop() != this.lop.getMaLop()) {
                                trungGvPhong = ktTrungGvPhong(lich, lichCu);

                                // bị trùng giáo viên.
                                if (trungGvPhong == 1) {

                                    String mess = "Lịch ";

                                    if (thu.getDayOfWeek() == 1) {
                                        mess += "CHỦ NHẬT ";
                                    } else {
                                        mess += "THỨ " + thu.getDayOfWeek() + " ";
                                    }

                                    mess += "trùng giờ dạy của GIÁO VIÊN tại:"
                                            + "\n\nLớp: " + lichCu.getMaLop()
                                            + "\n\nThời Gian: ";
                                    mess += lichCu.getNgayGioBd() + " đến " + lichCu.getNgayGioKt();

                                    JOptionPane.showMessageDialog(null, mess);

                                    return null;
                                } // bị trùng phòng
                                else if (trungGvPhong == 2) {

                                    String mess = "Lịch ";
                                    if (thu.getDayOfWeek() == 1) {
                                        mess += "CHỦ NHẬT ";
                                    } else {
                                        mess += "THỨ " + thu.getDayOfWeek() + " ";
                                    }

                                    mess += "trùng PHÒNG HỌC tại:"
                                            + "\n\nLớp: " + lichCu.getMaLop()
                                            + "\n\nThời Gian: ";
                                    mess += lichCu.getNgayGioBd() + " đến " + lichCu.getNgayGioKt();
                                    JOptionPane.showMessageDialog(null, mess);
                                    return null;

                                }
                            }

                        }
                        dsLich.add(lich);
                        demBuoiHoc++;
                    }

                }
            }

            calendar.add(Calendar.DATE, 1); // tăng ngày lên 1 - chuyển đến ngày tiếp theo

        }

        return dsLich;
    }

    // HÀM KIỂM TRA XEM GIÁO VIÊN VÀ PHÒNG HỌC TRONG NGÀY TẠO ĐÓ CÓ TRÙNG VỚI TRONG HỆ THỐNG KHÔNG
    public int ktTrungGvPhong(dto_Lich lichMoi, dto_Lich lichCu) {
        Date tgbdMoi = lichMoi.getTgBd();
        Date tgktMoi = lichMoi.getTgKt();
        Date tgbdCu = lichCu.getTgBd();
        Date tgktCu = lichCu.getTgKt();

        // nếu hai ngày này là 1
        if (tgbdMoi.getYear() == tgbdCu.getYear() && tgbdMoi.getMonth() == tgbdCu.getMonth() && tgbdMoi.getDate() == tgbdCu.getDate()) {

            //kiểm tra xem có trùng mã giáo viên không?
            if (lichMoi.getMaGv() == lichCu.getMaGv()) {

                int gioBdMoi = tgbdMoi.getHours();
                int gioKtMoi = tgbdMoi.getHours();
                int gioBdCu = tgbdCu.getHours();
                int gioKtCu = tgktCu.getHours();
                //nếu trùng mã giáo viên thì kiểm tra xem giờ học của lịch mới mà có giáo viên đó có nằm ngoài khung giờ mà giáo viên đó đang dạy ở lịch cũ hay không
                if (gioBdMoi >= gioBdCu && gioBdMoi <= gioKtCu || gioKtMoi >= gioBdCu && gioKtMoi <= gioKtCu) {
                    return 1; // trùng giờ dạy giáo viên
                }
            }

            // nếu không trùng mã giáo viên thì kiểm tra mã phòng học xem có bị trùng phòng học không
            if (lichMoi.getMaPhong() == lichCu.getMaPhong()) {

                // nếu trùng mã phòng thì kiểm tra 2 khung giờ học có bị trùng phòng học không
                int gioBdMoi = tgbdMoi.getHours();
                int gioKtMoi = tgbdMoi.getHours();
                int gioBdCu = tgbdCu.getHours();
                int gioKtCu = tgktCu.getHours();

                if (gioBdMoi >= gioBdCu && gioBdMoi <= gioKtCu || gioKtMoi >= gioBdCu && gioKtMoi <= gioKtCu) {
                    return 2; // trùng giờ dạy giáo viên               
                }
            }
        }
        return 0; // hợp lệ
    }

    // HÀM CÀI ĐẶT GIÁ TRỊ CHO THỨ 2-3-4-5-6-7-CN
    public dto_Thu setThongTinThu(int dayOfWeek, JComboBox cbGioBd, JComboBox cbPhutBd, JComboBox cbGioKt, JComboBox cbPhutKt, JComboBox cbGv, JComboBox cbPhong) {

        int gioBd = Integer.parseInt((String) cbGioBd.getSelectedItem());
        int phutBd = Integer.parseInt((String) cbPhutBd.getSelectedItem());
        int gioKt = Integer.parseInt((String) cbGioKt.getSelectedItem());
        int phutKt = Integer.parseInt((String) cbPhutKt.getSelectedItem());

        if (gioBd >= gioKt) {
            return null;
        }

        dto_GiaoVien gv = this.dsGiaoVien.get(cbGv.getSelectedIndex());
        if (gv == null) {
            return null;
        }

        dto_Phong phong = this.dsPhong.get(cbPhong.getSelectedIndex());

        if (phong == null) {
            return null;
        }

        dto_Thu thu = new dto_Thu(dayOfWeek, gioBd, phutBd, gioKt, phutKt, gv, phong);
        return thu;
    }

    // HÀM RELOAD TABLE
    public void reloadTable() {
        dtmNgayNghi.setRowCount(0);

        SimpleDateFormat sdf = new SimpleDateFormat("dd - MM - yyyy");
        Calendar calendar = Calendar.getInstance();

        int stt = 0;

        for (Date d : dsNgayNghi) {

            stt++;
            calendar.setTime(d);
            int dayOfWeek = calendar.get(calendar.DAY_OF_WEEK);

            Vector<Object> vc = new Vector<Object>();
            vc.add(stt);

            if (dayOfWeek == 1) {
                vc.add("CN");
            } else {
                vc.add(dayOfWeek);
            }

            vc.add(sdf.format(d));

            dtmNgayNghi.addRow(vc);
        }
    }

    // HÀM SET GIAO DIỆN BAN ĐẦU
    public void setGiaoDienBanDau() {
        setupTable();
        pnHai.setVisible(false);
        pnBa.setVisible(false);
        pnTu.setVisible(false);
        pnNam.setVisible(false);
        pnSau.setVisible(false);
        pnBay.setVisible(false);
        pnCn.setVisible(false);

        dsGiaoVien = new ArrayList<dto_GiaoVien>();
        dsGiaoVien = new bus_LopHoc().layDsGiaoVien();

        dsPhong = new ArrayList<dto_Phong>();
        dsPhong = new bus_LopHoc().layDsPhong();

        setComboBoxChonLich(cbGvT2, cbPhongT2);
        setComboBoxChonLich(cbGvT3, cbPhongT3);
        setComboBoxChonLich(cbGvT4, cbPhongT4);
        setComboBoxChonLich(cbGvT5, cbPhongT5);
        setComboBoxChonLich(cbGvT6, cbPhongT6);
        setComboBoxChonLich(cbGvT7, cbPhongT7);
        setComboBoxChonLich(cbGvCn, cbPhongCn);

        dsNgayNghi = new ArrayList<Date>();

        this.dsLichHeThong = new ArrayList<dto_Lich>();
        this.dsLichHeThong = new bus_LopHoc().layDsLichNgay();
    }

    // HÀM SET GIAO DIỆN CHO KHU VỰC THỨ
    public void setComboBoxChonLich(JComboBox cbGv, JComboBox cbPhong) {

        cbGv.removeAllItems();
        for (dto_GiaoVien gv : this.dsGiaoVien) {
            cbGv.addItem(gv.getTenGv());
        }

        cbPhong.removeAllItems();
        for (dto_Phong phong : this.dsPhong) {
            cbPhong.addItem(phong.getTenPhong());
        }
    }

    // HÀM SETUP BẢNG NGÀY NGHỈ
    public void setupTable() {
        dtmNgayNghi = new DefaultTableModel();

        dtmNgayNghi.addColumn("STT");
        dtmNgayNghi.addColumn("Thứ");
        dtmNgayNghi.addColumn("Ngày Nghỉ");

        tbNgayNghi.setModel(dtmNgayNghi);

        tbNgayNghi.getColumnModel().getColumn(0).setMaxWidth(80);

        tbNgayNghi.getTableHeader().setFont(new Font("Tahoma", Font.BOLD, 12));
        tbNgayNghi.getTableHeader().setOpaque(false);
        tbNgayNghi.getTableHeader().setForeground(new Color(0, 0, 0));
        tbNgayNghi.setSelectionBackground(new Color(0, 64, 128));
    }

    // HÀM THAY ĐỔI MÀU SẮC CHO CK BOX KHI ĐƯỢC CHỌN
    public void duocChon(JCheckBox ck) {
        ck.setFont(fontLblSelected);
        ck.setForeground(colorSelected);
    }

    public void khongDuocChon(JCheckBox ck) {
        ck.setFont(fontLblNotSelected);
        ck.setForeground(colorNotSelected);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnTong = new javax.swing.JPanel();
        scLichHoc = new javax.swing.JScrollPane();
        pnLich = new javax.swing.JPanel();
        ckT2 = new javax.swing.JCheckBox();
        ckT3 = new javax.swing.JCheckBox();
        ckT4 = new javax.swing.JCheckBox();
        ckT5 = new javax.swing.JCheckBox();
        ckT7 = new javax.swing.JCheckBox();
        ckT6 = new javax.swing.JCheckBox();
        ckCn = new javax.swing.JCheckBox();
        pnHai = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        gioBdT2 = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        phutBdT2 = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        gioKtT2 = new javax.swing.JComboBox<>();
        jLabel29 = new javax.swing.JLabel();
        phutKtT2 = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        cbGvT2 = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        cbPhongT2 = new javax.swing.JComboBox<>();
        pnBa = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        gioBdT3 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        phutBdT3 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        gioKtT3 = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        phutKtT3 = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        cbGvT3 = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        cbPhongT3 = new javax.swing.JComboBox<>();
        pnTu = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        gioBdT4 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        phutBdT4 = new javax.swing.JComboBox<>();
        jLabel19 = new javax.swing.JLabel();
        gioKtT4 = new javax.swing.JComboBox<>();
        jLabel20 = new javax.swing.JLabel();
        phutKtT4 = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        cbGvT4 = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        cbPhongT4 = new javax.swing.JComboBox<>();
        pnNam = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        gioBdT5 = new javax.swing.JComboBox<>();
        jLabel45 = new javax.swing.JLabel();
        phutBdT5 = new javax.swing.JComboBox<>();
        jLabel46 = new javax.swing.JLabel();
        gioKtT5 = new javax.swing.JComboBox<>();
        jLabel47 = new javax.swing.JLabel();
        phutKtT5 = new javax.swing.JComboBox<>();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        cbGvT5 = new javax.swing.JComboBox<>();
        jLabel50 = new javax.swing.JLabel();
        cbPhongT5 = new javax.swing.JComboBox<>();
        pnSau = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        gioBdT6 = new javax.swing.JComboBox<>();
        jLabel36 = new javax.swing.JLabel();
        phutBdT6 = new javax.swing.JComboBox<>();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        cbGvT6 = new javax.swing.JComboBox<>();
        jLabel41 = new javax.swing.JLabel();
        cbPhongT6 = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        gioKtT6 = new javax.swing.JComboBox<>();
        phutKtT6 = new javax.swing.JComboBox<>();
        jLabel69 = new javax.swing.JLabel();
        pnBay = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        gioBdT7 = new javax.swing.JComboBox<>();
        jLabel54 = new javax.swing.JLabel();
        phutBdT7 = new javax.swing.JComboBox<>();
        jLabel55 = new javax.swing.JLabel();
        gioKtT7 = new javax.swing.JComboBox<>();
        jLabel56 = new javax.swing.JLabel();
        phutKtT7 = new javax.swing.JComboBox<>();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        cbGvT7 = new javax.swing.JComboBox<>();
        jLabel59 = new javax.swing.JLabel();
        cbPhongT7 = new javax.swing.JComboBox<>();
        pnCn = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        gioBdCn = new javax.swing.JComboBox<>();
        jLabel63 = new javax.swing.JLabel();
        phutBdCn = new javax.swing.JComboBox<>();
        jLabel64 = new javax.swing.JLabel();
        gioKtCn = new javax.swing.JComboBox<>();
        jLabel65 = new javax.swing.JLabel();
        phutKtCn = new javax.swing.JComboBox<>();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        cbGvCn = new javax.swing.JComboBox<>();
        jLabel68 = new javax.swing.JLabel();
        cbPhongCn = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbNgayNghi = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        dateNgayNghi = new com.toedter.calendar.JCalendar();
        btnThemNgayNghi = new javax.swing.JButton();
        btnXacNhan = new javax.swing.JButton();
        btnXoaNgay1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        dcTuNgay = new com.toedter.calendar.JDateChooser();
        jLabel70 = new javax.swing.JLabel();
        txtBuoiHoc = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Lịch Học");
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(1350, 729));

        pnTong.setBackground(new java.awt.Color(230, 245, 255));
        pnTong.setMinimumSize(new java.awt.Dimension(1350, 729));
        pnTong.setPreferredSize(new java.awt.Dimension(1350, 729));

        pnLich.setBackground(new java.awt.Color(255, 255, 255));
        pnLich.setPreferredSize(new java.awt.Dimension(589, 900));

        ckT2.setBackground(new java.awt.Color(255, 255, 255));
        ckT2.setText("Thứ 2");
        ckT2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckT2ActionPerformed(evt);
            }
        });

        ckT3.setBackground(new java.awt.Color(255, 255, 255));
        ckT3.setText("Thứ 3");
        ckT3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckT3ActionPerformed(evt);
            }
        });

        ckT4.setBackground(new java.awt.Color(255, 255, 255));
        ckT4.setText("Thứ 4");
        ckT4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckT4ActionPerformed(evt);
            }
        });

        ckT5.setBackground(new java.awt.Color(255, 255, 255));
        ckT5.setText("Thứ 5");
        ckT5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckT5ActionPerformed(evt);
            }
        });

        ckT7.setBackground(new java.awt.Color(255, 255, 255));
        ckT7.setText("Thứ 7");
        ckT7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckT7ActionPerformed(evt);
            }
        });

        ckT6.setBackground(new java.awt.Color(255, 255, 255));
        ckT6.setText("Thứ 6");
        ckT6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckT6ActionPerformed(evt);
            }
        });

        ckCn.setBackground(new java.awt.Color(255, 255, 255));
        ckCn.setText("CN");
        ckCn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckCnActionPerformed(evt);
            }
        });

        pnHai.setBackground(new java.awt.Color(230, 245, 255));

        jLabel25.setText("Giờ Học:");

        gioBdT2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21" }));

        jLabel27.setText("giờ");

        phutBdT2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));

        jLabel28.setText("đến");

        gioKtT2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21" }));

        jLabel29.setText("giờ");

        phutKtT2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));

        jLabel30.setText("từ");

        jLabel31.setText("Giáo Viên");

        cbGvT2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel32.setText("Phòng");

        cbPhongT2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnHaiLayout = new javax.swing.GroupLayout(pnHai);
        pnHai.setLayout(pnHaiLayout);
        pnHaiLayout.setHorizontalGroup(
            pnHaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnHaiLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnHaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addGroup(pnHaiLayout.createSequentialGroup()
                        .addGroup(pnHaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel28)
                            .addComponent(jLabel30))
                        .addGap(18, 18, 18)
                        .addGroup(pnHaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gioBdT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gioKtT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnHaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnHaiLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel24)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel26))
                            .addGroup(pnHaiLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(pnHaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnHaiLayout.createSequentialGroup()
                                        .addComponent(jLabel29)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(phutKtT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnHaiLayout.createSequentialGroup()
                                        .addComponent(jLabel27)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(phutBdT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(36, 36, 36)
                .addGroup(pnHaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbGvT2, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnHaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbPhongT2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32))
                .addContainerGap())
        );
        pnHaiLayout.setVerticalGroup(
            pnHaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnHaiLayout.createSequentialGroup()
                .addGroup(pnHaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnHaiLayout.createSequentialGroup()
                        .addGroup(pnHaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addGroup(pnHaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32))
                        .addGap(10, 10, 10)
                        .addGroup(pnHaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(phutBdT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27)
                            .addComponent(gioBdT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbGvT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbPhongT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)))
                    .addGroup(pnHaiLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel25)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnHaiLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(gioKtT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(phutKtT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnBa.setBackground(new java.awt.Color(230, 245, 255));

        jLabel7.setText("Giờ Học:");

        gioBdT3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21" }));

        jLabel8.setText("giờ");

        phutBdT3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));

        jLabel10.setText("đến");

        gioKtT3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21" }));

        jLabel11.setText("giờ");

        phutKtT3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));

        jLabel12.setText("từ");

        jLabel13.setText("Giáo Viên");

        cbGvT3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel14.setText("Phòng");

        cbPhongT3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnBaLayout = new javax.swing.GroupLayout(pnBa);
        pnBa.setLayout(pnBaLayout);
        pnBaLayout.setHorizontalGroup(
            pnBaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnBaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnBaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(pnBaLayout.createSequentialGroup()
                        .addGroup(pnBaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel12))
                        .addGap(18, 18, 18)
                        .addGroup(pnBaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gioBdT3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gioKtT3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnBaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnBaLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel6)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel9))
                            .addGroup(pnBaLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(pnBaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnBaLayout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(phutKtT3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnBaLayout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(phutBdT3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(36, 36, 36)
                .addGroup(pnBaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbGvT3, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnBaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbPhongT3, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addContainerGap())
        );
        pnBaLayout.setVerticalGroup(
            pnBaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnBaLayout.createSequentialGroup()
                .addGroup(pnBaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnBaLayout.createSequentialGroup()
                        .addGroup(pnBaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnBaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14))
                        .addGap(10, 10, 10)
                        .addGroup(pnBaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(phutBdT3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(gioBdT3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbGvT3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbPhongT3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12)))
                    .addGroup(pnBaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnBaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(gioKtT3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(phutKtT3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnTu.setBackground(new java.awt.Color(230, 245, 255));

        jLabel16.setText("Giờ Học:");

        gioBdT4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21" }));

        jLabel18.setText("giờ");

        phutBdT4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));

        jLabel19.setText("đến");

        gioKtT4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21" }));

        jLabel20.setText("giờ");

        phutKtT4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));

        jLabel21.setText("từ");

        jLabel22.setText("Giáo Viên");

        cbGvT4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel23.setText("Phòng");

        cbPhongT4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnTuLayout = new javax.swing.GroupLayout(pnTu);
        pnTu.setLayout(pnTuLayout);
        pnTuLayout.setHorizontalGroup(
            pnTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTuLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addGroup(pnTuLayout.createSequentialGroup()
                        .addGroup(pnTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19)
                            .addComponent(jLabel21))
                        .addGap(18, 18, 18)
                        .addGroup(pnTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gioBdT4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gioKtT4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnTuLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel15)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel17))
                            .addGroup(pnTuLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(pnTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnTuLayout.createSequentialGroup()
                                        .addComponent(jLabel20)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(phutKtT4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnTuLayout.createSequentialGroup()
                                        .addComponent(jLabel18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(phutBdT4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(36, 36, 36)
                .addGroup(pnTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbGvT4, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbPhongT4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23))
                .addContainerGap())
        );
        pnTuLayout.setVerticalGroup(
            pnTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTuLayout.createSequentialGroup()
                .addGroup(pnTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnTuLayout.createSequentialGroup()
                        .addGroup(pnTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jLabel23))
                        .addGap(10, 10, 10)
                        .addGroup(pnTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(phutBdT4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18)
                            .addComponent(gioBdT4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbGvT4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbPhongT4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21)))
                    .addGroup(pnTuLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel16)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnTuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(gioKtT4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel20)
                    .addComponent(phutKtT4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnNam.setBackground(new java.awt.Color(230, 245, 255));

        jLabel43.setText("Giờ Học:");

        gioBdT5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21" }));

        jLabel45.setText("giờ");

        phutBdT5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));

        jLabel46.setText("đến");

        gioKtT5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21" }));

        jLabel47.setText("giờ");

        phutKtT5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));

        jLabel48.setText("từ");

        jLabel49.setText("Giáo Viên");

        cbGvT5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel50.setText("Phòng");

        cbPhongT5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnNamLayout = new javax.swing.GroupLayout(pnNam);
        pnNam.setLayout(pnNamLayout);
        pnNamLayout.setHorizontalGroup(
            pnNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnNamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43)
                    .addGroup(pnNamLayout.createSequentialGroup()
                        .addGroup(pnNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel46)
                            .addComponent(jLabel48))
                        .addGap(18, 18, 18)
                        .addGroup(pnNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gioBdT5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gioKtT5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnNamLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel42)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel44))
                            .addGroup(pnNamLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(pnNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnNamLayout.createSequentialGroup()
                                        .addComponent(jLabel47)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(phutKtT5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnNamLayout.createSequentialGroup()
                                        .addComponent(jLabel45)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(phutBdT5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(36, 36, 36)
                .addGroup(pnNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbGvT5, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbPhongT5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50))
                .addContainerGap())
        );
        pnNamLayout.setVerticalGroup(
            pnNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnNamLayout.createSequentialGroup()
                .addGroup(pnNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnNamLayout.createSequentialGroup()
                        .addGroup(pnNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42)
                            .addComponent(jLabel44))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel49)
                            .addComponent(jLabel50))
                        .addGap(10, 10, 10)
                        .addGroup(pnNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(phutBdT5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel45)
                            .addComponent(gioBdT5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbGvT5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbPhongT5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel48)))
                    .addGroup(pnNamLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel43)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnNamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(gioKtT5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47)
                    .addComponent(phutKtT5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnSau.setBackground(new java.awt.Color(230, 245, 255));

        jLabel34.setText("Giờ Học:");

        gioBdT6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21" }));

        jLabel36.setText("giờ");

        phutBdT6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));

        jLabel37.setText("đến");

        jLabel38.setText("giờ");

        jLabel39.setText("từ");

        jLabel40.setText("Giáo Viên");

        cbGvT6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel41.setText("Phòng");

        cbPhongT6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setText("đến");

        gioKtT6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21" }));

        phutKtT6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));

        jLabel69.setText("giờ");

        javax.swing.GroupLayout pnSauLayout = new javax.swing.GroupLayout(pnSau);
        pnSau.setLayout(pnSauLayout);
        pnSauLayout.setHorizontalGroup(
            pnSauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSauLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnSauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel34)
                    .addGroup(pnSauLayout.createSequentialGroup()
                        .addGroup(pnSauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37)
                            .addComponent(jLabel39))
                        .addGroup(pnSauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnSauLayout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(jLabel33)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel35))
                            .addGroup(pnSauLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(gioBdT6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel36)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(phutBdT6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnSauLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(gioKtT6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnSauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel38)
                                    .addGroup(pnSauLayout.createSequentialGroup()
                                        .addComponent(jLabel69)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(phutKtT6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(pnSauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel40)
                    .addComponent(cbGvT6, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(pnSauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbPhongT6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel41))
                .addContainerGap())
        );
        pnSauLayout.setVerticalGroup(
            pnSauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnSauLayout.createSequentialGroup()
                .addGroup(pnSauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnSauLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel34))
                    .addGroup(pnSauLayout.createSequentialGroup()
                        .addGroup(pnSauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel33)
                            .addComponent(jLabel35))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnSauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel40)
                            .addComponent(jLabel41))
                        .addGap(10, 10, 10)
                        .addGroup(pnSauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(phutBdT6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel36)
                            .addComponent(cbGvT6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbPhongT6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel39)
                            .addComponent(gioBdT6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(6, 6, 6)
                .addGroup(pnSauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnSauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(phutKtT6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel69)
                        .addComponent(gioKtT6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4))
                .addGap(381, 381, 381)
                .addGroup(pnSauLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel37)
                    .addComponent(jLabel38)))
        );

        pnBay.setBackground(new java.awt.Color(230, 245, 255));

        jLabel52.setText("Giờ Học:");

        gioBdT7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21" }));

        jLabel54.setText("giờ");

        phutBdT7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));

        jLabel55.setText("đến");

        gioKtT7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21" }));

        jLabel56.setText("giờ");

        phutKtT7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));

        jLabel57.setText("từ");

        jLabel58.setText("Giáo Viên");

        cbGvT7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel59.setText("Phòng");

        cbPhongT7.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnBayLayout = new javax.swing.GroupLayout(pnBay);
        pnBay.setLayout(pnBayLayout);
        pnBayLayout.setHorizontalGroup(
            pnBayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnBayLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnBayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel52)
                    .addGroup(pnBayLayout.createSequentialGroup()
                        .addGroup(pnBayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel55)
                            .addComponent(jLabel57))
                        .addGap(18, 18, 18)
                        .addGroup(pnBayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gioBdT7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gioKtT7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnBayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnBayLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel51)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel53))
                            .addGroup(pnBayLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(pnBayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnBayLayout.createSequentialGroup()
                                        .addComponent(jLabel56)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(phutKtT7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnBayLayout.createSequentialGroup()
                                        .addComponent(jLabel54)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(phutBdT7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(36, 36, 36)
                .addGroup(pnBayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbGvT7, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel58))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnBayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbPhongT7, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel59))
                .addContainerGap())
        );
        pnBayLayout.setVerticalGroup(
            pnBayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnBayLayout.createSequentialGroup()
                .addGroup(pnBayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnBayLayout.createSequentialGroup()
                        .addGroup(pnBayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel51)
                            .addComponent(jLabel53))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnBayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel58)
                            .addComponent(jLabel59))
                        .addGap(10, 10, 10)
                        .addGroup(pnBayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(phutBdT7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel54)
                            .addComponent(gioBdT7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbGvT7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbPhongT7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel57)))
                    .addGroup(pnBayLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel52)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnBayLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel55)
                    .addComponent(gioKtT7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel56)
                    .addComponent(phutKtT7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnCn.setBackground(new java.awt.Color(230, 245, 255));

        jLabel61.setText("Giờ Học:");

        gioBdCn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21" }));

        jLabel63.setText("giờ");

        phutBdCn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));

        jLabel64.setText("đến");

        gioKtCn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21" }));

        jLabel65.setText("giờ");

        phutKtCn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "00", "5", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55" }));

        jLabel66.setText("từ");

        jLabel67.setText("Giáo Viên");

        cbGvCn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel68.setText("Phòng");

        cbPhongCn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnCnLayout = new javax.swing.GroupLayout(pnCn);
        pnCn.setLayout(pnCnLayout);
        pnCnLayout.setHorizontalGroup(
            pnCnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCnLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnCnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel61)
                    .addGroup(pnCnLayout.createSequentialGroup()
                        .addGroup(pnCnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel64)
                            .addComponent(jLabel66))
                        .addGap(18, 18, 18)
                        .addGroup(pnCnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(gioBdCn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(gioKtCn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(pnCnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnCnLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jLabel60)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel62))
                            .addGroup(pnCnLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addGroup(pnCnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnCnLayout.createSequentialGroup()
                                        .addComponent(jLabel65)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(phutKtCn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(pnCnLayout.createSequentialGroup()
                                        .addComponent(jLabel63)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(phutBdCn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addGap(36, 36, 36)
                .addGroup(pnCnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbGvCn, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel67))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnCnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbPhongCn, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel68))
                .addContainerGap())
        );
        pnCnLayout.setVerticalGroup(
            pnCnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnCnLayout.createSequentialGroup()
                .addGroup(pnCnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnCnLayout.createSequentialGroup()
                        .addGroup(pnCnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel60)
                            .addComponent(jLabel62))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnCnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel67)
                            .addComponent(jLabel68))
                        .addGap(10, 10, 10)
                        .addGroup(pnCnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(phutBdCn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel63)
                            .addComponent(gioBdCn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbGvCn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbPhongCn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel66)))
                    .addGroup(pnCnLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel61)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnCnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel64)
                    .addComponent(gioKtCn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel65)
                    .addComponent(phutKtCn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnLichLayout = new javax.swing.GroupLayout(pnLich);
        pnLich.setLayout(pnLichLayout);
        pnLichLayout.setHorizontalGroup(
            pnLichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnLichLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnLichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnLichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(ckT4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ckT5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ckT6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ckT7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ckCn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(ckT2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ckT3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnLichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnTu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnBa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnSau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnBay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnCn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnHai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        pnLichLayout.setVerticalGroup(
            pnLichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnLichLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnLichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ckT2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnHai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnLichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ckT3, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnBa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(pnLichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ckT4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnTu, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(pnLichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ckT5, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pnNam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnLichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnSau, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ckT6, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnLichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pnBay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ckT7, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(pnLichLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnCn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ckCn, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(76, Short.MAX_VALUE))
        );

        scLichHoc.setViewportView(pnLich);

        jLabel1.setForeground(new java.awt.Color(0, 0, 102));
        jLabel1.setText("Chọn Ngày Học Trong Tuần");

        tbNgayNghi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Thứ", "Ngày"
            }
        ));
        tbNgayNghi.setRowHeight(30);
        jScrollPane1.setViewportView(tbNgayNghi);

        jLabel2.setForeground(new java.awt.Color(0, 0, 102));
        jLabel2.setText("Chọn Ngày Nghỉ");

        dateNgayNghi.setBackground(new java.awt.Color(255, 255, 255));
        dateNgayNghi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnThemNgayNghi.setBackground(new java.awt.Color(0, 102, 153));
        btnThemNgayNghi.setForeground(new java.awt.Color(255, 255, 255));
        btnThemNgayNghi.setText("Thêm Ngày Nghỉ");
        btnThemNgayNghi.setBorderPainted(false);
        btnThemNgayNghi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemNgayNghiActionPerformed(evt);
            }
        });

        btnXacNhan.setBackground(new java.awt.Color(0, 102, 153));
        btnXacNhan.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnXacNhan.setForeground(new java.awt.Color(255, 255, 255));
        btnXacNhan.setText("Xác Nhận");
        btnXacNhan.setBorderPainted(false);
        btnXacNhan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXacNhanActionPerformed(evt);
            }
        });

        btnXoaNgay1.setBackground(new java.awt.Color(0, 102, 153));
        btnXoaNgay1.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaNgay1.setText("Xóa Ngày Nghỉ");
        btnXoaNgay1.setBorderPainted(false);
        btnXoaNgay1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaNgay1ActionPerformed(evt);
            }
        });

        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jLabel3.setText("Danh Sách Ngày Nghỉ");

        jLabel5.setForeground(new java.awt.Color(51, 0, 102));
        jLabel5.setText("Tạo Lịch Từ Ngày");

        jLabel70.setForeground(new java.awt.Color(51, 0, 102));
        jLabel70.setText("Số Buổi Học");

        txtBuoiHoc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtBuoiHoc.setForeground(new java.awt.Color(153, 0, 51));
        txtBuoiHoc.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout pnTongLayout = new javax.swing.GroupLayout(pnTong);
        pnTong.setLayout(pnTongLayout);
        pnTongLayout.setHorizontalGroup(
            pnTongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnTongLayout.createSequentialGroup()
                .addGap(372, 372, 372)
                .addComponent(btnXoaNgay1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(240, 240, 240))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnTongLayout.createSequentialGroup()
                .addGroup(pnTongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnTongLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnTongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnTongLayout.createSequentialGroup()
                                .addGroup(pnTongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnTongLayout.createSequentialGroup()
                                        .addComponent(jLabel5)
                                        .addGap(0, 98, Short.MAX_VALUE))
                                    .addComponent(dcTuNgay, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(28, 28, 28))
                            .addGroup(pnTongLayout.createSequentialGroup()
                                .addGroup(pnTongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel70, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtBuoiHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(pnTongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(dateNgayNghi, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(40, 40, 40))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnTongLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnThemNgayNghi)
                        .addGap(199, 199, 199)))
                .addGroup(pnTongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scLichHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(34, 34, 34))
        );
        pnTongLayout.setVerticalGroup(
            pnTongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnTongLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(pnTongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel5)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnTongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnTongLayout.createSequentialGroup()
                        .addComponent(scLichHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(btnXacNhan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6))
                    .addGroup(pnTongLayout.createSequentialGroup()
                        .addGroup(pnTongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnTongLayout.createSequentialGroup()
                                .addComponent(dcTuNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(42, 42, 42)
                                .addComponent(jLabel70)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtBuoiHoc, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(dateNgayNghi, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnThemNgayNghi)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnXoaNgay1)
                        .addGap(23, 23, 23))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pnTong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnTong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(1366, 768));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void ckCnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckCnActionPerformed

        if (ckCn.isSelected()) {
            pnCn.setVisible(true);
            duocChon(ckCn);
        } else {
            pnCn.setVisible(false);
            khongDuocChon(ckCn);
        }

    }//GEN-LAST:event_ckCnActionPerformed

    private void ckT6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckT6ActionPerformed

        if (ckT6.isSelected()) {
            pnSau.setVisible(true);
            duocChon(ckT6);
        } else {
            pnSau.setVisible(false);
            khongDuocChon(ckT6);
        }
    }//GEN-LAST:event_ckT6ActionPerformed

    private void ckT7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckT7ActionPerformed

        if (ckT7.isSelected()) {
            pnBay.setVisible(true);
            duocChon(ckT7);
        } else {
            pnBay.setVisible(false);
            khongDuocChon(ckT7);
        }
    }//GEN-LAST:event_ckT7ActionPerformed

    private void ckT5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckT5ActionPerformed

        if (ckT5.isSelected()) {
            pnNam.setVisible(true);
            duocChon(ckT5);
        } else {
            pnNam.setVisible(false);
            khongDuocChon(ckT5);
        }
    }//GEN-LAST:event_ckT5ActionPerformed

    private void ckT4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckT4ActionPerformed

        if (ckT4.isSelected()) {
            pnTu.setVisible(true);
            duocChon(ckT4);
        } else {
            pnTu.setVisible(false);
            khongDuocChon(ckT4);
        }
    }//GEN-LAST:event_ckT4ActionPerformed

    private void ckT3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckT3ActionPerformed

        if (ckT3.isSelected()) {
            pnBa.setVisible(true);
            duocChon(ckT3);
        } else {
            pnBa.setVisible(false);
            khongDuocChon(ckT3);
        }
    }//GEN-LAST:event_ckT3ActionPerformed

    private void ckT2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckT2ActionPerformed

        if (ckT2.isSelected()) {
            pnHai.setVisible(true);
            duocChon(ckT2);
        } else {
            pnHai.setVisible(false);
            khongDuocChon(ckT2);
        }
    }//GEN-LAST:event_ckT2ActionPerformed

    private void btnThemNgayNghiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemNgayNghiActionPerformed

        Date d = new Date();
        d = dateNgayNghi.getDate();

        d.setHours(0);
        d.setMinutes(0);
        d.setSeconds(0);

        themNgayNghi(d);
    }//GEN-LAST:event_btnThemNgayNghiActionPerformed

    private void btnXoaNgay1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaNgay1ActionPerformed
        xoaNgayNghi();
    }//GEN-LAST:event_btnXoaNgay1ActionPerformed

    private void btnXacNhanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXacNhanActionPerformed
        themLich();
    }//GEN-LAST:event_btnXacNhanActionPerformed

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
            java.util.logging.Logger.getLogger(FormLichHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormLichHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormLichHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormLichHoc.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormLichHoc().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnThemNgayNghi;
    private javax.swing.JButton btnXacNhan;
    private javax.swing.JButton btnXoaNgay1;
    private javax.swing.JComboBox<String> cbGvCn;
    private javax.swing.JComboBox<String> cbGvT2;
    private javax.swing.JComboBox<String> cbGvT3;
    private javax.swing.JComboBox<String> cbGvT4;
    private javax.swing.JComboBox<String> cbGvT5;
    private javax.swing.JComboBox<String> cbGvT6;
    private javax.swing.JComboBox<String> cbGvT7;
    private javax.swing.JComboBox<String> cbPhongCn;
    private javax.swing.JComboBox<String> cbPhongT2;
    private javax.swing.JComboBox<String> cbPhongT3;
    private javax.swing.JComboBox<String> cbPhongT4;
    private javax.swing.JComboBox<String> cbPhongT5;
    private javax.swing.JComboBox<String> cbPhongT6;
    private javax.swing.JComboBox<String> cbPhongT7;
    private javax.swing.JCheckBox ckCn;
    private javax.swing.JCheckBox ckT2;
    private javax.swing.JCheckBox ckT3;
    private javax.swing.JCheckBox ckT4;
    private javax.swing.JCheckBox ckT5;
    private javax.swing.JCheckBox ckT6;
    private javax.swing.JCheckBox ckT7;
    private com.toedter.calendar.JCalendar dateNgayNghi;
    private com.toedter.calendar.JDateChooser dcTuNgay;
    private javax.swing.JComboBox<String> gioBdCn;
    private javax.swing.JComboBox<String> gioBdT2;
    private javax.swing.JComboBox<String> gioBdT3;
    private javax.swing.JComboBox<String> gioBdT4;
    private javax.swing.JComboBox<String> gioBdT5;
    private javax.swing.JComboBox<String> gioBdT6;
    private javax.swing.JComboBox<String> gioBdT7;
    private javax.swing.JComboBox<String> gioKtCn;
    private javax.swing.JComboBox<String> gioKtT2;
    private javax.swing.JComboBox<String> gioKtT3;
    private javax.swing.JComboBox<String> gioKtT4;
    private javax.swing.JComboBox<String> gioKtT5;
    private javax.swing.JComboBox<String> gioKtT6;
    private javax.swing.JComboBox<String> gioKtT7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> phutBdCn;
    private javax.swing.JComboBox<String> phutBdT2;
    private javax.swing.JComboBox<String> phutBdT3;
    private javax.swing.JComboBox<String> phutBdT4;
    private javax.swing.JComboBox<String> phutBdT5;
    private javax.swing.JComboBox<String> phutBdT6;
    private javax.swing.JComboBox<String> phutBdT7;
    private javax.swing.JComboBox<String> phutKtCn;
    private javax.swing.JComboBox<String> phutKtT2;
    private javax.swing.JComboBox<String> phutKtT3;
    private javax.swing.JComboBox<String> phutKtT4;
    private javax.swing.JComboBox<String> phutKtT5;
    private javax.swing.JComboBox<String> phutKtT6;
    private javax.swing.JComboBox<String> phutKtT7;
    private javax.swing.JPanel pnBa;
    private javax.swing.JPanel pnBay;
    private javax.swing.JPanel pnCn;
    private javax.swing.JPanel pnHai;
    private javax.swing.JPanel pnLich;
    private javax.swing.JPanel pnNam;
    private javax.swing.JPanel pnSau;
    private javax.swing.JPanel pnTong;
    private javax.swing.JPanel pnTu;
    private javax.swing.JScrollPane scLichHoc;
    private javax.swing.JTable tbNgayNghi;
    private javax.swing.JTextField txtBuoiHoc;
    // End of variables declaration//GEN-END:variables

}
