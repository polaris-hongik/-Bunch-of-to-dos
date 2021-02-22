package project_todo;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;

public class TrayIconHandler {
	private static TrayIcon trayicon;
	
	public static void registerTrayIcon(Image image, String toolTip, ActionListener action) {
		if (SystemTray.isSupported()) {
			if (trayicon != null) {
				trayicon = null;
			}
			trayicon = new TrayIcon(image);
			trayicon.setImageAutoSize(true);

			if (toolTip != null) {
				trayicon.setToolTip(toolTip);
			}

			if (action != null) {
				trayicon.addActionListener(action);
			}

			try {
				for (TrayIcon registeredTrayIcon : SystemTray.getSystemTray().getTrayIcons()) {
					SystemTray.getSystemTray().remove(registeredTrayIcon);
				}

				SystemTray.getSystemTray().add(trayicon);
			} catch (AWTException e) {
				//LOGGER.error("I got catch an error during add system tray !", e);
			}
		} else {
			//LOGGER.error("System tray is not supported !");
		}
	}
}
