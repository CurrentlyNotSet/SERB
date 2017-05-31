/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.util;

import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.table.DefaultTableCellRenderer;
import parker.serb.Global;

/**
 *
 * @author User
 */
public class ImageIconRenderer extends DefaultTableCellRenderer {

    JLabel lbl = new JLabel();

    ImageIcon icon = new ImageIcon(getClass().getResource("/file-icon.png"));

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {

        if (!value.equals("")) {
            lbl.setIcon(icon);
        } else {
            lbl.setIcon(null);
        }

        if (!isSelected) {
            lbl.setBackground(row % 2 == 0 ? Color.WHITE : Global.ALTERNATE_ROW_COLOR);
        } else {
            lbl.setBackground(table.getSelectionBackground());
        }

        lbl.setOpaque(true);
        lbl.setText("");
        lbl.setHorizontalAlignment(CENTER);
        return lbl;
    }

}
