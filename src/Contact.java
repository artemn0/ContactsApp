import java.util.ArrayList;

public class Contact {
    private String name;
    private final ArrayList<String> phoneNumbers = new ArrayList<>();

    Contact() {
        this.name = "unknown";
    }

    Contact(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ArrayList<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public int getPhoneNumbersCount() {
        return phoneNumbers.size();
    }

    public String getPhoneNumberByIndex(int index) {
        return phoneNumbers.get(index);
    }

    public void addPhoneNumber(String number) {
        phoneNumbers.add(number);
    }

    public void setPhoneNumber(int i, String s) {
        phoneNumbers.set(i, s);
    }

    public void removeNumber(int i) {
        phoneNumbers.remove(i);
    }
}
