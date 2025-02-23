package model;

public class User {

    private String id;
    private String studentNo;
    private String lastName;
    private String firstName;
    private String middleName;
    private String emailAddress;
    private String course;
    private String address;
    private String sex;
    private String contactNo;
    private int age;
    private String section;

    // Constructor
    public User(String id, String studentNo, String lastName, String firstName, String middleName,
            String emailAddress, String course, String address, String sex, String contactNo,
            int age, String section) {
        this.id = id;
        this.studentNo = studentNo;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.emailAddress = emailAddress;
        this.course = course;
        this.address = address;
        this.sex = sex;
        this.contactNo = contactNo;
        this.age = age;
        this.section = section;
    }

    // Getters and Setters (all fields)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public String toString() {
        return "User{"
                + "id='" + id + '\''
                + ", studentNo='" + studentNo + '\''
                + ", lastName='" + lastName + '\''
                + ", firstName='" + firstName + '\''
                + ", middleName='" + middleName + '\''
                + ", emailAddress='" + emailAddress + '\''
                + ", course='" + course + '\''
                + ", address='" + address + '\''
                + ", sex='" + sex + '\''
                + ", contactNo='" + contactNo + '\''
                + ", age=" + age
                + ", section='" + section + '\''
                + '}';
    }
}
