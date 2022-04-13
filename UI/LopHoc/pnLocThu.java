/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI.LopHoc;

import BUS.bus_LopHoc;
import DTO.dto_Lich;
import DTO.dto_LopHoc;
import java.util.ArrayList;

/**
 *
 * @author USER
 */
public class pnLocThu extends javax.swing.JPanel {

    /**
     * Creates new form pnLocThu
     */
    private ArrayList<dto_LopHoc> dsLop;
    private ArrayList<dto_LopHoc> dsLopPhuHop;
    private ArrayList<Integer> dsCheck;

    public pnLocThu() {
        initComponents();
        this.dsLop = new ArrayList<dto_LopHoc>();
        this.dsLop = new bus_LopHoc().layDsLop(true);
        
    }
    // HÀM HIỂN THỊ LỚP ĐÃ LỌC
    public void hienThiLopDaLoc(){
        boolean isEmpty = layThongTin();
        
        if(isEmpty == false){
            
           layDanhSachPhuHop();
           UI_LopHoc.reloadTableLop(dsLopPhuHop);
        }
        else
            UI_LopHoc.reloadTableLop(dsLop);
    }

    // HÀM LẤY THÔNG TIN CÁC CHECK BOX
    public boolean layThongTin() {
        dsCheck = new ArrayList<Integer>();
        
        boolean isEmpty = true;
        if (ckT2.isSelected()) {
            
            isEmpty =false;
            dsCheck.add(2);
        }
        if (ckT3.isSelected()) {
            isEmpty =false;
            dsCheck.add(3);
        }

        if (ckT4.isSelected()) {
            isEmpty =false;
            dsCheck.add(4);
        }
        if (ckT5.isSelected()) {
            isEmpty =false;
            dsCheck.add(5);
        }
        if (ckT6.isSelected()) {
            isEmpty =false;
            dsCheck.add(6);
        }
        if (ckT7.isSelected()) {
            isEmpty =false;
            dsCheck.add(7);
        }
        if (ckCn.isSelected()) {
            isEmpty =false;
            dsCheck.add(1);
        }
        
        return isEmpty;
        
    }
    
    // HÀM KIỂM TRA DANH SÁCH LỚP NHỮNG LỚP CÓ THỨ ĐÚNG VỚI YÊU CẦU
    public void layDanhSachPhuHop(){
        
        dsLopPhuHop = new ArrayList<dto_LopHoc>();
        
        int size = this.dsLop.size();
        
        for(int i = 0; i<size; i++){
            
            this.dsLop.get(i).setDsLich(new bus_LopHoc().layDsLichTheoLop(this.dsLop.get(i).getMaLop()));
        }
        
        this.dsLop = new bus_LopHoc().layChuongTrinh(dsLop);
        boolean timDuoc;
        
        // duyệt từng lớp đang có
        for(dto_LopHoc lop : dsLop){
            
            timDuoc = false;
            
            //lấy ds những ngày học của lớp đó
            ArrayList<dto_Lich> dsLich = lop.getDsLich();
            
            //kiểm tra từng ngày trong lớp xem có thứ trùng với thứ đã check không
            for(dto_Lich lich : dsLich){
                
                //lấy ra thứ của lịch
                int dayOfWeek = lich.getThu();
                
                // kiểm tra xem thứ của lịch có thuộc thứ cần lọc không
                for(int thuTrongTuan: dsCheck){
                    
                    if(thuTrongTuan == dayOfWeek){
                        timDuoc = true;
                        dsLopPhuHop.add(lop);
                        break;
                    }
                }
                
                if(timDuoc == true)
                    break;
            } 
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ckT2 = new javax.swing.JCheckBox();
        ckT4 = new javax.swing.JCheckBox();
        ckT3 = new javax.swing.JCheckBox();
        ckT5 = new javax.swing.JCheckBox();
        ckT6 = new javax.swing.JCheckBox();
        ckT7 = new javax.swing.JCheckBox();
        ckCn = new javax.swing.JCheckBox();

        setBackground(new java.awt.Color(230, 245, 255));
        setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(204, 204, 204)));
        setMinimumSize(new java.awt.Dimension(346, 30));
        setPreferredSize(new java.awt.Dimension(346, 30));

        ckT2.setBackground(new java.awt.Color(230, 245, 255));
        ckT2.setText("T2");
        ckT2.setMinimumSize(new java.awt.Dimension(37, 25));
        ckT2.setPreferredSize(new java.awt.Dimension(37, 25));
        ckT2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckT2ActionPerformed(evt);
            }
        });

        ckT4.setBackground(new java.awt.Color(230, 245, 255));
        ckT4.setText("T4");
        ckT4.setMinimumSize(new java.awt.Dimension(37, 25));
        ckT4.setPreferredSize(new java.awt.Dimension(37, 25));
        ckT4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckT4ActionPerformed(evt);
            }
        });

        ckT3.setBackground(new java.awt.Color(230, 245, 255));
        ckT3.setText("T3");
        ckT3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckT3ActionPerformed(evt);
            }
        });

        ckT5.setBackground(new java.awt.Color(230, 245, 255));
        ckT5.setText("T5");
        ckT5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckT5ActionPerformed(evt);
            }
        });

        ckT6.setBackground(new java.awt.Color(230, 245, 255));
        ckT6.setText("T6");
        ckT6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckT6ActionPerformed(evt);
            }
        });

        ckT7.setBackground(new java.awt.Color(230, 245, 255));
        ckT7.setText("T7");
        ckT7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckT7ActionPerformed(evt);
            }
        });

        ckCn.setBackground(new java.awt.Color(230, 245, 255));
        ckCn.setText("CN");
        ckCn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ckCnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(ckT2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(ckT3)
                .addGap(15, 15, 15)
                .addComponent(ckT4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15)
                .addComponent(ckT5)
                .addGap(15, 15, 15)
                .addComponent(ckT6)
                .addGap(12, 12, 12)
                .addComponent(ckT7, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                .addComponent(ckCn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ckT2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ckT3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ckT5)
                    .addComponent(ckT6)
                    .addComponent(ckT7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ckCn, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ckT4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void ckT5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckT5ActionPerformed
        hienThiLopDaLoc();
    }//GEN-LAST:event_ckT5ActionPerformed

    private void ckT2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckT2ActionPerformed
        hienThiLopDaLoc();
    }//GEN-LAST:event_ckT2ActionPerformed

    private void ckT3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckT3ActionPerformed
        hienThiLopDaLoc();
    }//GEN-LAST:event_ckT3ActionPerformed

    private void ckT4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckT4ActionPerformed
        hienThiLopDaLoc();
    }//GEN-LAST:event_ckT4ActionPerformed

    private void ckT6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckT6ActionPerformed
        hienThiLopDaLoc();
    }//GEN-LAST:event_ckT6ActionPerformed

    private void ckT7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckT7ActionPerformed
        hienThiLopDaLoc();
    }//GEN-LAST:event_ckT7ActionPerformed

    private void ckCnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ckCnActionPerformed
       hienThiLopDaLoc();
    }//GEN-LAST:event_ckCnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox ckCn;
    private javax.swing.JCheckBox ckT2;
    private javax.swing.JCheckBox ckT3;
    private javax.swing.JCheckBox ckT4;
    private javax.swing.JCheckBox ckT5;
    private javax.swing.JCheckBox ckT6;
    private javax.swing.JCheckBox ckT7;
    // End of variables declaration//GEN-END:variables
}