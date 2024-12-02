package Version2.src.Model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Calendar {
    private LocalDate currentDate;
    private Map<LocalDate, Note> notes;

    public Calendar(LocalDate currentDate) {
        this.currentDate = currentDate;
        this.notes = new HashMap<>();
    }

    public void addNote(LocalDate date, String noteContent) {
        notes.put(date, new Note(date, noteContent));
    }

    public void updateNote(LocalDate date, String newContent) {
        if (notes.containsKey(date)) {
            notes.get(date).setContent(newContent);
        }
    }

    public Note getNoteForDate(LocalDate date) {
        return notes.get(date);
    }

    public void removeNote(LocalDate date) {
        notes.remove(date);
    }

    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void changeMonth(int increment) {
        currentDate = currentDate.plusMonths(increment);
    }

    public void changeYear(int increment) {
        currentDate = currentDate.plusYears(increment);
    }

    public int getDayOfWeekForFirstDayOfMonth() {
        return LocalDate.of(currentDate.getYear(), currentDate.getMonth(), 1).getDayOfWeek().getValue();
    }

    public int getDaysInMonth() {
        return currentDate.lengthOfMonth();
    }

    public void setCurrentDate(LocalDate currentDate) {
        this.currentDate = currentDate;
    }
}
