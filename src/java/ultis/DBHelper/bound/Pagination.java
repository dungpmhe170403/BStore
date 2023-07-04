package ultis.DBHelper.bound;

import model.helper.type.FilterProduct;

import java.util.ArrayList;
import java.util.HashMap;

public class Pagination<T> {
    private final int PAGE_SIZE = 15;
    public int showPageLinks = 3;
    public Integer currentPage;
    public Integer prevPage;
    public Integer nextPage;
    ArrayList<T> data;
    public int totalItems;
    public int totalPages;

    public HashMap<FilterProduct, String> filterLabel;
// db helper

    public int offset;
    public int limit;

    public Pagination(Integer currentPage, HashMap<FilterProduct, String> filterLabel) {
        this.currentPage = currentPage;
        offset = (currentPage - 1) * PAGE_SIZE;
        limit = PAGE_SIZE;
        this.filterLabel = filterLabel;
    }

    public void calculateTotalPages() {
        this.totalPages = (int) Math.ceil((double) totalItems / PAGE_SIZE);
        calculatePagination();
    }

    public void calculatePagination() {
        this.prevPage = currentPage - 1 == 0 ? null : currentPage - 1;
        if (showPageLinks + currentPage - 1 >= totalPages) {
            this.nextPage = currentPage + 1 > totalPages + 1 ? null : currentPage + 1;
            this.showPageLinks = 3;
        } else {
            this.nextPage = showPageLinks + currentPage;
        }
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }

    public ArrayList<T> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "PAGE_SIZE=" + PAGE_SIZE +
                ", showPageLinks=" + showPageLinks +
                ", currentPage=" + currentPage +
                ", prevPage=" + prevPage +
                ", nextPage=" + nextPage +
                ", data=" + data +
                ", totalItems=" + totalItems +
                ", totalPages=" + totalPages +
                ", filterLabel=" + filterLabel +
                ", offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}
