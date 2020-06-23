import common.Common;
import ui.MainUI;

public class Main {
    public static void main(String[] args) {
        start();
    }

    public static MainUI start() {
        MainUI ui = new MainUI(Common.TITLE);
        ui.init();
        return ui;
    }
}
