package test;

public class Record {
    private String text;
    private String date;

    public Record(String textString, String dateString) {
        text = textString;
        date = dateString;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
