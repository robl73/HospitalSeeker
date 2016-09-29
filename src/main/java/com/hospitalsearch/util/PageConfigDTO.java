package com.hospitalsearch.util;

public class PageConfigDTO {

    private String type;
    private Integer itemsPerPage;
    private Integer currentPage;
    private String currentSearchQuery;

    public PageConfigDTO() {

    }

    public PageConfigDTO(String type, Integer itemsPerPage, Integer currentPage, String currentSearchQuery) {
        this.type = type;
        this.itemsPerPage = itemsPerPage;
        this.currentPage = currentPage;
        this.currentSearchQuery = currentSearchQuery;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public void setCurrentSearchQuery(String currentSearchQuery) {
        this.currentSearchQuery = currentSearchQuery;
    }

    public String getCurrentSearchQuery() {
        return currentSearchQuery;
    }

    @Override
    public String toString() {
        return "PageConfigDTO{" +
                "type='" + type + '\'' +
                ", itemsPerPage=" + itemsPerPage +
                ", currentPage=" + currentPage +
                ", currentSearchQuery='" + currentSearchQuery + '\'' +
                '}';
    }
}
