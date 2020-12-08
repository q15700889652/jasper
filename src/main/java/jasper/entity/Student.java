package jasper.entity;

public class Student {
    private String NAME;
    private String INFO;
 
    public Student() {
    }
 
    public Student(String NAME, String INFO) {
        this.NAME = NAME;
        this.INFO = INFO;
    }
 
    public String getNAME() {
        return NAME;
    }
 
    public void setNAME(String NAME) {
        this.NAME = NAME;
    }
 
    public String getINFO() {
        return INFO;
    }
 
    public void setINFO(String INFO) {
        this.INFO = INFO;
    }
}
