package contacts;

class Record {
    private int recordId;
    private String name;
    private String surName;
    private String phoneNumber;

    Record(int recordId, String name, String surName, String phoneNumber) {
        this.recordId = recordId;
        this.name = name;
        this.surName = surName;
        this.phoneNumber = phoneNumber;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getRecordId() {
        return this.recordId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setSurname(String surName) {
        this.surName = surName;
    }

    public String getSurName() {
        return this.surName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}