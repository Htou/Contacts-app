package contacts;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Contacts {
    private PhoneBook phoneBook;
    private ArrayList<Record> recordList;

    private boolean exit;

    private static Scanner input;

    public Contacts() {
        this.phoneBook = new PhoneBook();
        this.recordList = phoneBook.getRecordList();

        this.exit = false;

        input = new Scanner(System.in);
    }


    private void exit() {
        this.exit = true;
    }



    public void runMainMenu() {
        while (!exit) {
            String choice = getMainMenuInput();

            if (choice.matches("add|remove|edit|count|list|exit")) {
                preformMainMenuAction(choice);
            } else {
                System.out.println("Your input: " + choice + ", is incorrect");
            }

        }
    }

    private String getMainMenuInput() {
        System.out.println("Enter action (add, remove, edit, count, list, exit):");
        String choice = input.nextLine();

        return choice;
    }


    //beginning of add contacts menu
    private void addContactMenu() {
        String name = getNameInput();
        String surName = getSurnameInput();
        String phoneNumber = getPhoneNumberInput();

        int recordId = phoneBook.getRecordListSize() + 1;

        Record record = createRecord(recordId, name, surName, phoneNumber);

        addRecordToPhoneBook(record);
    }


    private String getNameInput() {
        System.out.println("Enter the name of the person:");
        String nameInput = input.nextLine();


//        Matcher namePattern = Pattern
//                .compile("([a-z]*)", Pattern.CASE_INSENSITIVE)
//                .matcher(nameInput);
//
//        if (namePattern.find()) {
//            name = namePattern.group(1);
//        }

        return nameInput;
    }

    private String getSurnameInput() {
        System.out.println("Enter the surname of the person");
        String surNameInput = input.nextLine();


//        Matcher namePattern = Pattern
//                .compile("([a-z]*)", Pattern.CASE_INSENSITIVE)
//                .matcher(surNameInput);
//
//        if (namePattern.find()) {
//            surName = namePattern.group(1);
//        }


        return surNameInput;
    }

    private String getPhoneNumberInput() {
        System.out.println("Enter the number:");

        String phoneNumberInput = input.nextLine();
        String phoneNumber = "";

        String stringNumberPattern = "^\\+?[0-9]{0,3}[- ]?([a-z0-9]{2,3})?([- ][a-z0-9]{2,4})?([- ][0-9]{2,4})?$"
                                   + "|^\\([0-9]{0,3}\\)([- ][0-9]{0,3})?([- ][0-9]{0,4})?$"
                                   + "|^[0-9]{0,3}[- ]\\([0-9]{0,3}\\)([- ][0-9]{0,3})?([- ][0-9]{0,4})?$"
                                   + "|^\\+\\([a-z]+\\)$";


        Matcher numberPattern = Pattern
                .compile(stringNumberPattern, Pattern.CASE_INSENSITIVE)
                .matcher(phoneNumberInput);

        if (numberPattern.find()) {
            phoneNumber = phoneNumberInput;
        } else {
            System.out.println("Wrong number format!");
        }

        return phoneNumber;
    }


    private Record createRecord(int recordId, String firstName, String lastName, String number) {
        Record record = new Record(recordId, firstName, lastName, number);

        return record;
    }

    private void addRecordToPhoneBook(Record record) {
        phoneBook.addRecord(record);

        System.out.println("The record added.");
    }
    //end of contacts menu

    private void removeRecord() {

        if (recordList.isEmpty()) {
            System.out.println("No records to remove!");
        } else {
            listRecords();

            int recordIndex = getSelectedRecordIdInput();
            phoneBook.removeRecord(recordIndex);

            for (int i = recordIndex; i < recordList.size(); i++) {
                int recordId = recordList.get(i).getRecordId();
                recordList.get(recordIndex).setRecordId(recordId - 1);
            }

            System.out.println("The record removed!");
        }
    }

    //beginning of edit record menu
    private void editRecordMenu() {
        if (recordList.isEmpty()) {
            System.out.println("No records to edit!");
        } else {
            listRecords();

            int recordIndex = getSelectedRecordIdInput();

            if (recordIndex < 0 || recordIndex > recordList.size()) {
                System.out.println("Record does not exist");
            } else {
                String fieldChoice = selectField();

                if (fieldChoice.matches("name|surname|number")) {
                    preformEditRecordAction(recordIndex, fieldChoice);

                    System.out.println("The record updated!");
                } else {
                    System.out.println("Your input: " + fieldChoice + ", is incorrect");
                }
            }
        }
    }

    private int getSelectedRecordIdInput() {
        System.out.println("Select a record:");

        int recordIdChoice = Integer.parseInt(input.nextLine());
        int recordIndex = recordIdChoice -1;

        if (recordIndex < 0) {
            recordIndex = 0;
        }

        return recordIndex;
    }

    private String selectField() {
        System.out.println("Select a field (name, surname, number)");

        String fieldChoice = input.nextLine();

        return fieldChoice;
    }

    private void editRecordName(int recordIndex) {
        String editedName = getNameInput();

        recordList.get(recordIndex).setName(editedName);
    }

    private void editRecordSurname(int recordIndex) {
        String editedSurname = getSurnameInput();

        recordList.get(recordIndex).setSurname(editedSurname);
    }

    private void editRecordNumber(int recordIndex) {
        String editedPhoneNumber = getPhoneNumberInput();

        recordList.get(recordIndex).setPhoneNumber(editedPhoneNumber);
    }

    public void preformEditRecordAction(int recordIndex, String fieldChoice) {
        switch (fieldChoice) {
            case "name": {
                editRecordName(recordIndex);
                break;
            }
            case "surname": {
                editRecordSurname(recordIndex);
                break;
            }
            case "number": {
                editRecordNumber(recordIndex);
                break;
            }
            default: {
                System.out.println("unknown error");
                break;
            }
        }
    }
    // end of edit record menu

    private void countRecords() {
        int recordSize = phoneBook.getRecordListSize();

        System.out.println("The phonebook has " + recordSize + " records.");
    }

    // begining list record
    public void listRecords() {

        for (int recordIndex = 0; recordIndex < recordList.size(); recordIndex++) {
            int recordId = recordList.get(recordIndex).getRecordId();
            String name = recordList.get(recordIndex).getName();
            String surName = recordList.get(recordIndex).getSurName();
            String checkedPhoneNumber = hasNumber(recordIndex);

            System.out.println(recordId + "." + " " + name + " " + surName + ", " + checkedPhoneNumber);
        }
    }


    private String hasNumber(int index) {
        String phoneNumber = recordList.get(index).getPhoneNumber();
        String hasNoNumber = "[no number]";

        if (phoneNumber.equals("")) {
            return hasNoNumber;
        }

        return recordList.get(index).getPhoneNumber();
    }
    // end of listRecord

    public void preformMainMenuAction(String choice) {
        switch (choice) {
            case "add": {
                addContactMenu();
                break;
            }
            case "remove": {
                removeRecord();
                break;
            }
            case "edit": {
                editRecordMenu();
                break;
            }
            case "count": {
                countRecords();
                break;
            }
            case "list": {
                listRecords();
                break;
            }
            case "exit": {
                exit();
                break;
            }
            default: {
                System.out.println("unknown error");
                break;
            }
        }
    }

}