package ui;

import common.Common;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FontManagerUI {
    private static final long serialVersionUID = -37011351219515242L;

    private JLabel currentFontDescJLabel;
    private JLabel currentFontJLabel;
    private JLabel descJlabel;
    private JSeparator line;
    private JComboBox<String> fontJComboBox;


    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    String fontNames[] = ge.getAvailableFontFamilyNames();

    public static String FONT_TYPE = Common.FONT_TYPE;
    public static int FONT_SIZE = Common.FONT_SIZE;


    private void verticalGroupLayout(GroupLayout layout) {
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(currentFontJLabel)
                                        .addComponent(fontJComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)).addGap(26, 26, 26)
                        .addComponent(line, GroupLayout.PREFERRED_SIZE, 11, GroupLayout.PREFERRED_SIZE).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(descJlabel).addGap(18, 18, 18).addComponent(currentFontDescJLabel).addContainerGap(47, Short.MAX_VALUE)));
    }

    private void horizontalGroupLayout(GroupLayout layout) {
        layout.setHorizontalGroup(layout
                .createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(
                                        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(currentFontDescJLabel)
                                                .addComponent(descJlabel)
                                                .addGroup(
                                                        layout.createSequentialGroup().addComponent(currentFontJLabel).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(fontJComboBox, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createSequentialGroup().addComponent(line, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE).addGap(0, 0, Short.MAX_VALUE)));
    }

}
