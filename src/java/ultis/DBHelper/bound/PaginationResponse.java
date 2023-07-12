package ultis.DBHelper.bound;

import model.helper.type.FilterProduct;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
// class giup ben view nhan dc respon 1 cach phu hop gom links va trang hien tai
public class PaginationResponse<T> {
    private String pathUrl;
    public ArrayList<String> links;
    private Pagination<T> pagination;
    public Integer currentPage;
    public ArrayList<T> responseData;

    public PaginationResponse(String pathUrl, Pagination<T> pagination) {
        currentPage = pagination.currentPage;
        this.pathUrl = pathUrl;
        this.pagination = pagination;
        this.links = new ArrayList<>();
        int start = pagination.currentPage;
        int end = pagination.currentPage + pagination.showPageLinks;
        if (end > pagination.totalPages) {
            end = end - pagination.showPageLinks + 1;
            start = end - pagination.showPageLinks - 1 > 0 ? end - pagination.showPageLinks - 1 : end - 1;
            pagination.prevPage = start - 1 == 0 ? null : start - 1;
            pagination.nextPage = null;
        }
        if (pagination.prevPage != null) {
            this.links.add(generateHtml(pagination.prevPage, "Prev"));
        }
        for (int i = start; i < end; i++) {
            this.links.add(generateHtml(i, null));
        }
        if (pagination.nextPage != null) {
            this.links.add(generateHtml(pagination.nextPage, "Next"));
        }
        responseData = pagination.getData();
    }

    private String generateHtml(Integer page, String label) {
        String activeClass = Objects.equals(page, pagination.currentPage) ? "page-active" : "";
        String text = label != null ? label : page.toString();
        return String.format("<a href='%s?page=%d%s' class='%s'>%s</a>", pathUrl, page, generateUrlParam(), activeClass, text);
    }

    private String generateUrlParam() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<FilterProduct, String> entry : pagination.filterLabel.entrySet()) {
            sb.append("&").append(entry.getKey().toString().toLowerCase()).append("=").append(entry.getValue());
        }
        return sb.toString();
    }
}
