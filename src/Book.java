import java.io.Serializable;

public class Book implements Serializable {
    private String ID;
    private String name;
    private String category;
    private String des;
    private String author;
    private String year;

    public Book(String ID, String name, String category, String des, String author, String year) {
        this.ID = ID;
        this.name = name;
        this.category = category;
        this.des = des;
        this.author = author;
        this.year = year;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}
