package practice;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class PhoneBook {

    private Map<String, String> phoneMap = new TreeMap<>();

    private final String regexIsNumber = "[\\d()+-]+";
    private final String regexIsWord = "[A-Za-zА-яЁё ]+";

    public Command parseCommand(String command) {
        if (command.matches(regexIsNumber)) {
            return Command.PHONE;
        }
        if (command.equals("LIST")) {
            return Command.LIST;
        }
        if (command.matches(regexIsWord)) {
            return Command.NAME;
        }
        return Command.WRONG;
    }

    public boolean nameCheck(String name) {
        return name.matches(regexIsWord);
    }

    public String phoneFormat(String phone) {
        String regexNotNumberExclude = "\\D";
        String result = phone.replaceAll(regexNotNumberExclude, "");
        if (!isPhoneCorrect(result)) {
            return Main.WRONG_INPUT_MSG;
        }
        if (result.length() == 10) {
            return "7" + result;
        }
        return result.replaceAll("^8", "7");
    }

    private boolean isPhoneCorrect(String phone) {
        String regexCorrectNumber = "^(7|8)?[\\d]{10}$";
        return phone.matches(regexCorrectNumber);
    }

    public void addContact(String phone, String name) {
        if (nameCheck(name) && isPhoneCorrect(phone)) {
            String previousName = phoneMap.put(phone, name);
            System.out.println(Main.SUCCESS_MSG);
            if (previousName != null) {
                updateContacts(previousName, name);
            }
        } else {
            System.out.println(Main.WRONG_INPUT_MSG);
        }
    }

    private void updateContacts(String previousName, String name) {
        TreeSet<String> contactsToUpdate = new TreeSet<>(setOfContacts(previousName));
        for (String phone : contactsToUpdate) {
            phoneMap.put(phone, name);
        }
    }

    private TreeSet<String> setOfContacts(String name) {
        TreeSet<String> phoneSet = new TreeSet<>();
        for (Map.Entry<String, String> entry : phoneMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (value.equals(name)) {
                phoneSet.add(key);
            }
        }
        return phoneSet;
    }

    public String getContactByPhone(String phone) {
        String result = "";
        if (phoneMap.containsKey(phone)) {
            result = phoneMap.get(phone) + " - " + phone;
        }
        return result;
    }

    public Set<String> getContactByName(String name) {
        TreeSet<String> contactByName = new TreeSet<>();
        if (phoneMap.containsValue(name)) {
            String contact = name + " - " + getStringFromSet(setOfContacts(name));
            contactByName.add(contact);
        }
        return contactByName;

    }

    public Set<String> getAllContacts() {
        TreeSet<String> allContacts = new TreeSet<>();
        for (String name : phoneMap.values()) {
            TreeSet<String> contacts = new TreeSet<>(setOfContacts(name));
            allContacts.add(name + " - " + getStringFromSet(contacts));
        }
        return allContacts;
    }

    private String getStringFromSet(TreeSet<String> treeSet) {
        String result = treeSet.toString();
        return result.substring(1, result.length() - 1);
    }
}