package practice;

import java.util.Scanner;
import java.util.TreeSet;

public class Main {

    public static final String WRONG_INPUT_MSG = "Неверный формат ввода";
    public static final String SUCCESS_MSG = "Контакт сохранен!";

    public static void main(String[] args) {

        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Введите номер, имя или команду:");
            String input = scanner.nextLine();
            if (input.equals("0")) {
                break;
            }
            Command command = phoneBook.parseCommand(input);
            switch (command) {
                case LIST: {
                    System.out.println(phoneBook.getAllContacts());
                    break;
                }
                case NAME: {
                    String name = input;
                    TreeSet<String> phones = new TreeSet<>(phoneBook.getContactByName(name));
                    if (!phones.isEmpty()) {
                        System.out.println(phones);
                    } else {
                        System.out.println("Такого имени в телефонной книге нет.\n" +
                                "Введите номер телефона для абонента " + name);
                        String phone = scanner.nextLine();
                        phone = phoneBook.phoneFormat(phone);
                        phoneBook.addContact(phone, name);
                    }
                    break;
                }
                case PHONE: {
                    String phone = phoneBook.phoneFormat(input);
                    String name = phoneBook.getContactByPhone(phone);
                    if (!name.isEmpty()) {
                        System.out.println(name);
                    } else {
                        System.out.println("Такого номера нет в телефонной книге.\n" +
                                "Введите имя абонента для номера " + phone);
                        name = scanner.nextLine();
                        phoneBook.addContact(phone, name);
                    }
                    break;
                }
                case WRONG: {
                    System.out.println(WRONG_INPUT_MSG);
                    break;
                }

            }
        }
    }
}