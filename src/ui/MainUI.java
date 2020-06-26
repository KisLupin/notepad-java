package ui;

import common.Common;
import util.FileMenuUtil;

import javax.swing.*;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.*;

public class MainUI extends JFrame implements ActionListener {
        private static final long serialVersionUID = 1L;
        public static JTextArea textArea;
        private JMenuBar menuBar;
        private JSeparator line;
        private JPopupMenu textAreaPopup;
        private JScrollPane textScroll;
        // Menus
        private JMenu file, edit, format, view, help, viewHelp, source;
        //menu item
        private JMenuItem news, open, save, saveAs, properties, exit;
        //edit item
        private JMenuItem undo, copy, paste, cut, find, findNext, replace, selectAll, timeDate;
        //popup item
        private JMenuItem popUndo, popCopy, popPaste, popCut, popSelectAll, popTimeDate;

    // PopupMenu
        public static UndoManager undoManager;

        public static String filePath = Common.EMPTY;
        boolean saved = false;
        public static boolean lineWrap = true;
        // Default position is (0, 0)
        public static int pointX = 0;
        public static int pointY = 0;
        public static String savedText = Common.EMPTY;
        public static int fontNum = Common.FONT_NUM;
        public static int fontSizeNum = Common.FONT_SIZE_NUM;
        public static int fontStyleNum = Common.FONT_STYLE_NUM;
        public static String findWhat = Common.EMPTY;
        private Font textAreaFont;

        private void setMainUIXY() {
            pointX = getMainUIX();
            pointY = getMainUIY();
        }

        private int getMainUIY() {
            return (int) getLocation().getY();
        }

        private int getMainUIX() {
            return (int) getLocation().getX();
        }

        public MainUI(String title) {
            super(title);
            setTitle(title);
        }

        public void init() {
            initMenu();
            initText();
            this.setResizable(true);
            this.setBounds(new Rectangle(150, 100, 800, 550));
            this.setVisible(true);
            addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    FileMenuUtil file = new FileMenuUtil(Common.EMPTY);
                    file.exit(MainUI.this);
                }
            });
        }

        private void initMenu(){
            menuBar();
            menuFile();
            menuEdit();
            setJMenuBar(menuBar);
            initTextAreaPopupMenu();
        }

        private void menuBar(){
            menuBar = new JMenuBar();
        }

        private void menuFile(){
            file = new JMenu(Common.FILE);

            news = new JMenuItem(Common.NEW);
            news.addActionListener(this);
            news.setAccelerator(KeyStroke.getKeyStroke(Common.N, InputEvent.CTRL_DOWN_MASK));
            file.add(news);

            open = new JMenuItem(Common.OPEN);
            open.addActionListener(this);
            open.setAccelerator(KeyStroke.getKeyStroke(Common.O, InputEvent.CTRL_DOWN_MASK));
            file.add(open);

            save = new JMenuItem(Common.SAVE);
            save.addActionListener(this);
            save.setAccelerator(KeyStroke.getKeyStroke(Common.S, InputEvent.CTRL_DOWN_MASK));
            file.add(save);

            saveAs = new JMenuItem(Common.SAVE_AS);
            saveAs.addActionListener(this);
            saveAs.setAccelerator(KeyStroke.getKeyStroke(Common.S, InputEvent.CTRL_DOWN_MASK + InputEvent.SHIFT_DOWN_MASK));
            file.add(saveAs);

            line = new JSeparator();
            file.add(line);

            properties = new JMenuItem(Common.PROPERTIES);
            properties.addActionListener(this);
            file.add(properties);

            line = new JSeparator();
            file.add(line);

            exit = new JMenuItem(Common.EXIT);
            exit.addActionListener(this);
            file.add(exit);

            menuBar.add(file);
        }

        private void menuEdit(){
            //undo, copy, paste, cut, find, findNext, replace, selectAll, timeDate
            edit = new JMenu(Common.EDIT);

            undo = new JMenuItem(Common.UNDO);
            undo.addActionListener(this);
            undo.setAccelerator(KeyStroke.getKeyStroke(Common.Z, InputEvent.CTRL_DOWN_MASK));
            edit.add(undo);

            line = new JSeparator();
            edit.add(line);

            cut = new JMenuItem(Common.CUT);
            cut.addActionListener(this);
            cut.setAccelerator(KeyStroke.getKeyStroke(Common.X, InputEvent.CTRL_MASK));
            edit.add(cut);

            copy = new JMenuItem(Common.COPY);
            copy.addActionListener(this);
            copy.setAccelerator(KeyStroke.getKeyStroke(Common.C, InputEvent.CTRL_MASK));
            edit.add(copy);

            paste = new JMenuItem(Common.PASTE);
            paste.addActionListener(this);
            paste.setAccelerator(KeyStroke.getKeyStroke(Common.V, InputEvent.CTRL_MASK));
            edit.add(paste);

            line = new JSeparator();
            edit.add(line);

            find = new JMenuItem(Common.FIND);
            find.addActionListener(this);
            find.setAccelerator(KeyStroke.getKeyStroke(Common.F, InputEvent.CTRL_MASK));
            edit.add(find);

            findNext = new JMenuItem(Common.FIND_NEXT);
            findNext.addActionListener(this);
            findNext.setAccelerator(KeyStroke.getKeyStroke(Common.F, InputEvent.CTRL_MASK + InputEvent.SHIFT_MASK));
            edit.add(findNext);

            replace = new JMenuItem(Common.REPLACE);
            replace.addActionListener(this);
            replace.setAccelerator(KeyStroke.getKeyStroke(Common.H, InputEvent.CTRL_MASK));
            edit.add(replace);

            line = new JSeparator();
            edit.add(line);

            selectAll = new JMenuItem(Common.SELECT_ALL);
            selectAll.addActionListener(this);
            selectAll.setAccelerator(KeyStroke.getKeyStroke(Common.A, InputEvent.CTRL_MASK));
            edit.add(selectAll);

            timeDate = new JMenuItem(Common.TIME_DATE);
            timeDate.addActionListener(this);
            timeDate.setAccelerator(KeyStroke.getKeyStroke(Common.T, InputEvent.CTRL_MASK));
            edit.add(timeDate);

            menuBar.add(edit);
        }

        private void initText(){
            textArea = new JTextArea(Common.EMPTY);
            textArea.setLineWrap(true);
            lineWrap = true;
            textAreaFont = new Font(FontManagerUI.FONT_TYPE, fontStyleNum,FontManagerUI.FONT_SIZE);
            textArea.setFont(textAreaFont);
            textArea.add(textAreaPopup);
            initUndoManager();
            //addUndoable edit listener
            textArea.getDocument().addUndoableEditListener(e -> undoManager.addEdit(e.getEdit()));
            // add caret listener
            textArea.addCaretListener(e ->{
                if (savedText != null && textArea.getText() != null){
                    if (savedText.equals(textArea.getText())){
                        setSaved(true);
                    }else {
                        setSaved(false);
                    }
                }
                textArea.setFocusable(true);
                setDisabledMenuAtCreating(true);
            });

            textArea.addMouseMotionListener(new MouseMotionListener() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    isSelectedText();
                }
                @Override
                public void mouseMoved(MouseEvent e) {
                    isSelectedText();
                }
            });

            textArea.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                }

                @Override
                public void mousePressed(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3){
                        isSelectedText();
                        textAreaPopup.show(textArea, e.getX(), e.getY());
                    }
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.getButton() == MouseEvent.BUTTON3){
                        isSelectedText();
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            textScroll = new JScrollPane(textArea);
            this.add((textScroll));
        }

        private void setDisabledMenuAtCreating(boolean b) {
            undo.setEnabled(b);
            popUndo.setEnabled(b);
            cut.setEnabled(b);
            popCut.setEnabled(b);
            copy.setEnabled(b);
            popCopy.setEnabled(b);
            find.setEnabled(b);
            findNext.setEnabled(b);
        }

        private void setSaved(boolean b) {
            this.saved = b;
        }

        private void initUndoManager() {
            undoManager = new UndoManager();
        }

        private void initTextAreaPopupMenugit (){
            textAreaPopup = new JPopupMenu();

            popUndo = new JMenuItem(Common.UNDO);
            popUndo.addActionListener(this);
            textAreaPopup.add(popUndo);

            line = new JSeparator();
            textAreaPopup.add(line);

            popCut = new JMenuItem(Common.CUT);
            popCut.addActionListener(this);
            textAreaPopup.add(popCut);

            popCopy = new JMenuItem(Common.COPY);
            popCopy.addActionListener(this);
            textAreaPopup.add(popCopy);

            popPaste = new JMenuItem(Common.PASTE);
            popPaste.addActionListener(this);
            textAreaPopup.add(popPaste);

            line = new JSeparator();
            textAreaPopup.add(line);

            popSelectAll = new JMenuItem(Common.SELECT_ALL);
            popSelectAll.addActionListener(this);
            textAreaPopup.add(popSelectAll);

            popTimeDate = new JMenuItem(Common.TIME_DATE);
            popTimeDate.addActionListener(this);
            textAreaPopup.add(popTimeDate);
        }

        private void isSelectedText(){
            textArea.setFocusable(true);
            String text = textArea.getSelectedText();
            if(null != text){
                setDisabledMenuAtSelecting(true);
            }else{
                setDisabledMenuAtSelecting(false);
            }
        }

        private void setDisabledMenuAtSelecting(boolean b) {
            cut.setEnabled(b);
            popCut.setEnabled(b);
            copy.setEnabled(b);
            popCopy.setEnabled(b);
        }

        private void actionForFileItem(ActionEvent e){
            if (e.getSource() == news) {
                FileMenuUtil.news(MainUI.this);
            }else if (e.getSource() == open){
                var file = new FileMenuUtil(Common.EMPTY);
                file.open(MainUI.this);
            }
        }

        private void actionForEditItem(ActionEvent e){
            if (e.getSource() == undo){
                EditMenuUtil.undo();
            }else if (e.getSource() == copy){
                EditMenuUtil.copy();
            }
        }

        @Override
        public void actionPerformed(ActionEvent e) {
                actionForFileItem(e);
                actionForEditItem(e);
        }
}
