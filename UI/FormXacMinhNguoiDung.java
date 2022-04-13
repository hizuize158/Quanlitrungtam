/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;
import BUS.BCrypt;
import UI.DangNhap.UI_DangNhap;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class FormXacMinhNguoiDung {
    private String matKhauNhap;
    private int luaChon;
    
    public int getLuaChon(){
        return this.luaChon;
    }
    public FormXacMinhNguoiDung() {
        pnXacNhanMatKhau pn = new pnXacNhanMatKhau();
        this.luaChon = JOptionPane.showConfirmDialog(null, pn, "Xác Minh Người Dùng", JOptionPane.OK_CANCEL_OPTION);
        
        if (this.luaChon == 0) {  
            this.matKhauNhap = pn.getMatKhau();
        }
    }
    
    // HÀM XÁC MINH NGƯỜI DÙNG
    public boolean kiemTraMatKhau() {
        String mk = UI_DangNhap.layMatKhauDangNhap(); // lấy thông tin tài khoản người đang sử dụng
        
        boolean kq = BCrypt.checkpw(this.matKhauNhap, mk); // so sánh chuỗi nhập vào và chuỗi mã hóa
        return kq;
    }
}
