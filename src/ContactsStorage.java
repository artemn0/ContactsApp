import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class ContactsStorage {
    private final ArrayList<Contact> storage = new ArrayList<>();
    private static ContactsStorage instance;
    public static final String STORAGE_FILE = "db.txt";
    enum FileReadingStage {READ_NAME, READ_NUMBERS}

    public static ContactsStorage getInstance() {
        if(instance == null) {
            instance = new ContactsStorage();
        }
        return instance;
    }

    public void add(Contact contact) {
        storage.add(contact);
    }

    public Contact get(int i) {
        return storage.get(i);
    }

    public int size() {
        return storage.size();
    }

    public void remove(int i) {
        storage.remove(i);
    }

    public void set(int i, Contact c) {
        storage.set(i, c);
    }

    public boolean store() {
        boolean result = true;

        try(PrintWriter pw = new PrintWriter(STORAGE_FILE, StandardCharsets.UTF_8)) {
            int i;
            for(Contact c : storage) {
                pw.println(c.getName());
                for(i = 0; i < c.getPhoneNumbersCount(); i++) {
                    pw.println(c.getPhoneNumberByIndex(i));
                }
                pw.println("");
            }
        }
        catch(IOException e) {
            result = false;
        }

        return result;
    }

    public boolean load() {
        boolean result = true;

        try(Scanner in = new Scanner(Path.of(STORAGE_FILE), StandardCharsets.UTF_8)) {
            Contact c = null;
            FileReadingStage mode = FileReadingStage.READ_NAME;
            String temp;
            while(in.hasNextLine()) {
                if(c == null) {
                    c = new Contact();
                }

                if(mode == FileReadingStage.READ_NAME) {
                    c.setName(in.nextLine());
                    mode = FileReadingStage.READ_NUMBERS;
                }
                else{
                    temp = in.nextLine();
                    if(temp.equals("")) {
                        mode = FileReadingStage.READ_NAME;
                        add(c);
                        c = null;
                    } else{
                        c.addPhoneNumber(temp);
                    }
                }
            }

            if(c != null) {
                add(c);
            }
        }
        catch(IOException e) {
            result = false;
        }

        return result;
    }
}
