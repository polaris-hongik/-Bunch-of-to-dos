package project_todo;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

public class Main{
    public static void main(String[] args) throws ParseException {
    	TrayIconHandler.registerTrayIcon(Toolkit.getDefaultToolkit().getImage("img/ÇÒÀÏ¹¶Ä¡.ico")
    			, "Bunch-of-Todo is running now",
    			new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
		});
    	Data data = new Data();
        new Mainpage("ÇÒÀÏ¹¶Ä¡",data);
    }
}