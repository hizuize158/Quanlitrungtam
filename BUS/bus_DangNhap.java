package BUS;

import DAL.dal_TaiKhoan;
import DTO.dto_DangNhap;
import DTO.dto_TaiKhoan;
import UI.UI_Main;

public class bus_DangNhap {

    // HÀM ĐĂNG NHẬP
    public dto_TaiKhoan dangNhap(dto_TaiKhoan tkNhap) {

        dto_TaiKhoan tk = new dal_TaiKhoan().dangNhap(tkNhap);

        if (tk == null) 
            return null;
            
        else {

            boolean ktmk = kiemTraMatKhau(tkNhap.getMatKhau(), tk.getMatKhau());

            if (ktmk == true) {
                
                UI_Main ui_main = new UI_Main();
                ui_main.setThongTinDangNhap(tk);

                if (tk.getLoai() == 2)
                    ui_main.showGhiDanh();
                
                else if (tk.getLoai() == 3)
                    ui_main.showHocVu();

                ui_main.setVisible(true);
                
                return tk;
            }
            return null;

        }
    }

    // HÀM BĂM MẬT KHẨU NHẬP VÀO VÀ KIỂM TRA VỚI MÃ BRYPT LÁY TỪ DB
    public boolean kiemTraMatKhau(String matKhauNhap, String hash) {
        return BCrypt.checkpw(matKhauNhap, hash);
    }

}
