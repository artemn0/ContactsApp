import java.util.Arrays;
import java.util.Scanner;

/**
 * Simple contacts app
 * @author Artem Novykov
 * @version 1.1
 */

public class ContactsApp {

    enum UiMode {UiModeCLI, UiModeGUI}

    public static void main(String[] args) {
        UiMode uiMode = UiMode.UiModeCLI;

        if(args.length > 0) {
            if(args[0].equals("--cli")) {
                // already assigned
                // uiMode = UiMode.UiModeCLI;
            }
            else if(args[0].equals("--gui")) {
                uiMode = UiMode.UiModeGUI;
            }
            else{
                System.err.println("Unknown command line args: " + Arrays.toString(args));
                System.exit(1);
            }
        }

        ContactsStorage storage = ContactsStorage.getInstance();
        if(!storage.load()) {
            System.out.println("Something went wrong while file reading: " + ContactsStorage.STORAGE_FILE);
        }

        Showable ui = null;
        switch(uiMode) {
            case UiModeCLI: ui = new CommandLineUI(storage); break;
            case UiModeGUI: ui = new GraphicalUI(storage); break;
            default:
                System.err.println("Unknown UiMode value: " + uiMode);
                System.exit(1);
        }
        ui.show();
    }

}
