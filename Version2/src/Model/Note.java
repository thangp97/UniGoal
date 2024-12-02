package Version2.src.Model;

import java.time.LocalDate;

public class Note {
    private LocalDate date;
    private String content;

    public Note(LocalDate date, String content) {
        this.date = date;
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return " - " + content;
    }
}
