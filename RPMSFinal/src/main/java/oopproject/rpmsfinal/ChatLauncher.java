package oopproject.rpmsfinal;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ChatLauncher {

    private final String defaultMeetingURL = "https://chat.google.com/room/AAQAv2hWDtk?cls=7";

    // Launches the meeting using the default browser
    public boolean launchChat() {
        return launchChat(defaultMeetingURL);
    }

    // Launches a meeting with a custom URL
    public boolean launchChat(String meetingURL) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(meetingURL));
                return true;
            } else {
                System.err.println("Desktop is not supported on this platform.");
                return false;
            }
        } catch (IOException | URISyntaxException e) {
            System.err.println("Failed to open browser: " + e.getMessage());
            return false;
        }
    }

    // You can also include additional helper methods if needed
}
