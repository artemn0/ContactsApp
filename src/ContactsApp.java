import java.util.Scanner;

/**
 * Simple contacts app
 * @author Artem Novykov
 * @version 1.1
 */

public class ContactsApp {

    private static Scanner scanner;
    private static ContactsStorage storage;

    public static void main(String[] args) {
        scanner = new Scanner(System.in);
        storage = ContactsStorage.getInstance();
        if(!storage.load()) {
            System.out.println("Something went wrong while file reading: " + ContactsStorage.STORAGE_FILE);
        }
        ContactsApp app = new ContactsApp();
        app.showMenu();
    }

    /**
     * This method shows text interface
     */
    private void showMenu() {
        int choose;
        while(true) {
            System.out.println("1. Show contacts");
            System.out.println("2. Add new contact");
            System.out.println("3. Edit contact name or number");
            System.out.println("4. Delete contact or contact number");
            System.out.println("5. Save changes");
            System.out.println("6. Quit");
            System.out.print("Your choice: ");
            choose = scanner.nextInt();
            scanner.nextLine();
            switch(choose) {
                case 1: showContacts(); break;
                case 2: addContact(); break;
                case 3: edit(); break;
                case 4: delete(); break;
                case 5: System.out.println(storage.store() ?
                        "All contacts was stored successfully." : "There is some storing error.");
                    break;
                case 6: System.exit(0);
                default: System.out.println("Incorrect choice!");
            }
        }
    }

    /**
     *
     * @param s string for parsing
     * @return first element is number of contact, second unnecessary element is a number of phone number in table
     */
    private String[] getParts(String s) {
        String[] parts = s.split("\\.");
        if(parts.length < 1 || parts.length > 2) {
            System.out.println("Wrong format!");
            return null;
        }

        int contactN = Integer.parseInt(parts[0])-1;
        if(contactN < 0 || contactN >= storage.size()) {
            System.out.println("Unknown contact number: " + s);
            return null;
        }

        if(parts.length == 2) {
            Contact c = storage.get(contactN);
            int phoneN = Integer.parseInt(parts[1])-1;
            if(phoneN < 0 || phoneN >= c.getPhoneNumbersCount()) {
                System.out.println("Unknown number of a contact number: " + s);
                return null;
            }
        }

        return parts;
    }

    /**
     * This method asks user for number of contact or phone number and assists to change it
     */
    private void edit() {
        System.out.print("Please enter contact number (e.g. 1) or number of phone number (e.g. 1.1): ");
        String pointer = scanner.nextLine();
        String[] parts = getParts(pointer);
        if(parts == null) return;

        int contactN = Integer.parseInt(parts[0])-1;
        Contact c = storage.get(contactN);
        if(parts.length == 1) {
            System.out.println("Current contact name: " + c.getName());
            System.out.print("Enter a new contact name: ");
            String name = scanner.nextLine();
            c.setName(name);
            System.out.println("Contact name was changed.");
        } else if(parts.length == 2) {
            int phoneN = Integer.parseInt(parts[1])-1;
            System.out.println("Current phone number: " + c.getPhoneNumberByIndex(phoneN));
            System.out.print("Enter a new phone number: ");
            String number = scanner.nextLine();
            storage.get(contactN).setPhoneNumber(phoneN, number);
            System.out.println("Phone number was changed successfully.");
        }
    }

    /**
     * This method asks user for number of contact or phone number and deletes it
     */
    private void delete() {
        System.out.print("Please enter contact number (e.g. 1) or number of phone number (e.g. 1.1): ");
        String pointer = scanner.nextLine();
        String[] parts = getParts(pointer);
        if(parts == null) return;

        int contactN = Integer.parseInt(parts[0])-1;
        if(parts.length == 1) {
            storage.remove(contactN);
            System.out.println("Contact was removed successfully!");
        } else if(parts.length == 2) {
            Contact c = storage.get(contactN);
            int phoneN = Integer.parseInt(parts[1])-1;
            c.removeNumber(phoneN);
            System.out.println("Phone number was removed successfully!");
        }
    }

    /**
     * This method shows contact list
     */
    private void showContacts() {
        String firstNumber;
        String filler = "*".repeat(75);
        int phoneNumbersCount;
        if(storage.size() > 0) {
            System.out.println(filler);
            System.out.printf("* %-3s %-30s * %-3s %-30s *\n", "#", "name", "#", "phone numbers");
            System.out.println(filler);
            Contact c;
            for(int i = 0; i < storage.size(); i++) {
                c = storage.get(i);
                phoneNumbersCount = c.getPhoneNumbersCount();
                firstNumber = (phoneNumbersCount > 0) ? c.getPhoneNumberByIndex(0) : "no phone numbers";
                System.out.printf("* %-3d %-30s * %-3d %-30s *\n", i+1, c.getName(), 1, firstNumber);

                for(int j = 1; j < phoneNumbersCount; j++) {
                    System.out.printf("* %-3s %-30s * %-3d %-30s *\n", " ", "", j+1, c.getPhoneNumberByIndex(j));
                }
                System.out.println(filler);
            }
        }
        else{
            System.out.println("There are no contacts.");
        }
    }

    /**
     * This method asks user for contact details and adds contact info to storage
     */
    private void addContact() {
        Contact contact = new Contact();
        String temp;

        System.out.print("Contact name: ");
        temp = scanner.nextLine();
        contact.setName(temp);

        int i = 1;
        while(true) {
            System.out.print("Please enter a phone number #" + i + " (or press ENTER only instead): ");
            i++;
            temp = scanner.nextLine();
            if( temp.equals("") ) {
                break;
            } else{
                contact.addPhoneNumber(temp);
            }
        }

        storage.add(contact);
    }

}
