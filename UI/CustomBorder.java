/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.geom.Rectangle2D;
import javax.swing.border.AbstractBorder;

public class CustomBorder extends AbstractBorder{

    public static class BorderPiece {

        public Color color;
        public int height;  //Chỉ chiều dài của border 

        public BorderPiece() {
            this.height = 1;
        }

        public BorderPiece(int height) {
            this.height = height;
        }

        public BorderPiece(Color color, int height) {
            this.color = color;
            this.height = height;
        }
    }

    private BorderPiece topPiece = null;
    private BorderPiece rightPiece = null;
    private BorderPiece bottomPiece = null;
    private BorderPiece leftPiece = null;
    
    public CustomBorder() {
        super();
    }

    public CustomBorder(int width) {
        this(width, width, width, width);
    }

    public CustomBorder(int widthTopBottom, int widthRightLeft) {
        this(widthTopBottom, widthRightLeft, widthTopBottom, widthRightLeft);
    }

    public CustomBorder(int widthTopPiece, int widthRightPiece, int widthBottomPiece, int widthLeftPiece) {
        super();
        if (widthTopPiece <= 0) {
            this.topPiece = null;
        } else {
            this.topPiece = new BorderPiece(widthTopPiece);
        }
        if (widthRightPiece <= 0) {
            this.rightPiece = null;
        } else {
            this.rightPiece = new BorderPiece(widthRightPiece);
        }
        if (widthBottomPiece <= 0) {
            this.bottomPiece = null;
        } else {
            this.bottomPiece = new BorderPiece(widthBottomPiece);
        }
        if (widthLeftPiece <= 0) {
            this.leftPiece = null;
        } else {
            this.leftPiece = new BorderPiece(widthLeftPiece);
        }
    }

    public CustomBorder(BorderPiece topPiece, BorderPiece rightPiece, BorderPiece bottomPiece, BorderPiece leftPiece) {
        super();
        this.topPiece = topPiece;
        this.rightPiece = rightPiece;
        this.bottomPiece = bottomPiece;
        this.leftPiece = leftPiece;
    }
    
    public static final Color WHITE = new Color(0, 0, 0, 0);
    
    public static final Color THONGKE = new Color(0,204,106);
    public static final Color KHACHHANG = new Color(255,0,151);
    public static final Color LOPHOC = new Color(96,60,186);
    public static final Color CHUONGTRINHHOC = new Color(229, 20, 0);
    public static final Color TAIKHOAN = new Color(255,196,13);
    public static final Color TKB = new Color(250, 104, 0);
    
    public void setColor(Color newColor) {
        if (this.topPiece != null) {
            this.topPiece.color = newColor;
        }
        if (this.rightPiece != null) {
            this.rightPiece.color = newColor;
        }
        if (this.bottomPiece != null) {
            this.bottomPiece.color = newColor;
        }
        if (this.leftPiece != null) {
            this.leftPiece.color = newColor;
        }
    }

    @Override
    public Insets getBorderInsets(Component c, Insets insets) {
        Insets newInsets = super.getBorderInsets(c, insets);
        if (this.topPiece != null) {
            newInsets.top = this.topPiece.height;
        }
        if (this.rightPiece != null) {
            newInsets.right = this.rightPiece.height;
        }
        if (this.bottomPiece != null) {
            newInsets.bottom = this.bottomPiece.height;
        }
        if (this.leftPiece != null) {
            newInsets.left = this.leftPiece.height;
        }
        return newInsets;
    }

    @Override
    public void paintBorder(Component component, Graphics graphics, int x, int y, int width, int height) {
        super.paintBorder(component, graphics, x, y, width, height);

        Graphics2D graphics2D = null;
        if (graphics instanceof Graphics2D) {
            graphics2D = (Graphics2D) graphics;

            //top border
            if (this.topPiece != null) {
                graphics2D.setColor(this.topPiece.color);
                int newX = x;
                int newY = y;
                int newWidth = width;
                int newHeight = this.topPiece.height;
                graphics2D.fill(new Rectangle2D.Double(newX, newY, newWidth, newHeight));
            }

            //right border
            if (this.rightPiece != null) {
                graphics2D.setColor(this.rightPiece.color);
                int newX = x + width - this.rightPiece.height;
                int newY = y;
                int newWidth = this.rightPiece.height;
                int newHeight = height;
                graphics2D.fill(new Rectangle2D.Double(newX, newY, newWidth, newHeight));
            }

            //bottom border
            if (this.bottomPiece != null) {
                graphics2D.setColor(this.bottomPiece.color);
                int newX = x;
                int newY = y + height - this.bottomPiece.height;
                int newWidth = width;
                int newHeight = this.bottomPiece.height;
                graphics2D.fill(new Rectangle2D.Double(newX, newY, newWidth, newHeight));
            }

            //left border
            if (this.leftPiece != null) {
                graphics2D.setColor(this.leftPiece.color);
                int newX = x;
                int newY = y;
                int newWidth = this.leftPiece.height;
                int newHeight = height;
                graphics2D.fill(new Rectangle2D.Double(newX, newY, newWidth, newHeight));
            }
        }
    }

    public BorderPiece getTopPiece() {
        return topPiece;
    }

    public void setTopPiece(BorderPiece topPiece) {
        this.topPiece = topPiece;
    }

    public BorderPiece getRightPiece() {
        return rightPiece;
    }

    public void setRightPiece(BorderPiece rightPiece) {
        this.rightPiece = rightPiece;
    }

    public BorderPiece getBottomPiece() {
        return bottomPiece;
    }

    public void setBottomPiece(BorderPiece bottomPiece) {
        this.bottomPiece = bottomPiece;
    }

    public BorderPiece getLeftPiece() {
        return leftPiece;
    }

    public void setLeftPiece(BorderPiece leftPiece) {
        this.leftPiece = leftPiece;
    }

}
