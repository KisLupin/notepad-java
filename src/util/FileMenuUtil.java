package util;

import common.Common;
import ui.MainUI;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class FileMenuUtil extends MainUI{
    private static final long serialVersionUID = 1L;


    public FileMenuUtil(String title) {
        super(title);
    }

    public static void news(MainUI mainUI) {

    }

    public static void save(MainUI mainUI) {
    }

    public static void saveAs(MainUI mainUI) {

    }

    public void exit(MainUI mainUI) {

    }

    public void open(MainUI mainUI) {
        if (!Common.EMPTY.equals(filePath)) {
            if (savedText.equals(textArea.getText())) {
                openOperation(mainUI);
            } else {
                confirmOpen(mainUI);
            }
        } else {
            if (Common.EMPTY.equals(textArea.getText())) {
                openOperation(mainUI);
            } else {
                confirmOpen(mainUI);
            }
        }
    }

    private void confirmOpen(MainUI mainUI) {
        int option = JOptionPane.showConfirmDialog(
                FileMenuUtil.this,
                Common.DO_YOU_WANT_TO_SAVE_CHANGES, Common.CONFIM_EXIT,
                JOptionPane.YES_NO_CANCEL_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            save(mainUI);
            openOperation(mainUI);
        } else if (option == JOptionPane.NO_OPTION) {
            openOperation(mainUI);
        } else if (option == JOptionPane.CANCEL_OPTION) {
            textArea.setFocusable(true);
        }
    }

    private static void openOperation(MainUI mainUI) {
        String path;
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter;
        filter = new FileNameExtensionFilter(Common.TXT_FILE, Common.TXT);
        chooser.setFileFilter(filter);
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setDialogTitle(Common.OPEN);
        int ret = chooser.showOpenDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            path = chooser.getSelectedFile().getAbsolutePath();
            String name = chooser.getSelectedFile().getName();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), Common.GB2312));
                StringBuffer buffer = new StringBuffer();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append(Common.NEW_LINE);
                }
                reader.close();
                textArea.setText(String.valueOf(buffer));
                mainUI.setTitle(name + Common.NOTEPAD_NOTEPAD);
                savedText = textArea.getText();
                mainUI.setSaved(true);
                filePath = path;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void readProperties(MainUI mainUI) {
    }
}
