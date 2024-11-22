package Version2.src.Utils;

public class Pagination {
    private int currentPage;
    private final int pageSize;

    public Pagination(int pageSize) {
        this.pageSize = pageSize;
        this.currentPage = 1;
    }

    public int getOffset() {
        return (currentPage - 1) * pageSize;
    }

    public void nextPage() {
        currentPage++;
    }

    public void prevPage() {
        if (currentPage > 1) {
            currentPage--;
        }
    }

    public int getCurrentPage() {
        return currentPage;
    }
}

