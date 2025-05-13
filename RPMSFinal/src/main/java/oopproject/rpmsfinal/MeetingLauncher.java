package oopproject.rpmsfinal;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class MeetingLauncher {

    private final String defaultMeetingURL = "https://meet.google.com/pzo-mfgu-yhi";

    // Launches the meeting using the default browser
    public boolean launchMeeting() {
        return launchMeeting(defaultMeetingURL);
    }

    // Launches a meeting with a custom URL
    public boolean launchMeeting(String meetingURL) {
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
}
