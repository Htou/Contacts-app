package contacts;

import java.util.ArrayList;

class PhoneBook {
    private ArrayList<Record> recordList = new ArrayList();

    public PhoneBook() {
    }

    public PhoneBook(Record record) {
        this.recordList.add(record);
    }

    public ArrayList getRecordList() {
        return this.recordList;
    }

    public int getRecordListSize() {
        return this.recordList.size();
    }

    public void addRecord(Record record) {
        this.recordList.add(record);
    }

    public void removeRecord(int recordIndex) {
        this.recordList.remove(recordIndex);
    }

}