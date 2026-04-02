package com.engineer.batchprocessing.dto;

public class UseSearchrRequest {

    private Integer pageNo = 0;
    private Integer pageSize = 5;
    private String sortBy = "userId";
    private String direction = "ASC";
    private String search;

    public
    Integer getPageNo () {
        return pageNo;
    }

    public
    void setPageNo (Integer pageNo) {
        this.pageNo = pageNo;
    }

    public
    Integer getPageSize () {
        return pageSize;
    }

    public
    void setPageSize (Integer pageSize) {
        this.pageSize = pageSize;
    }

    public
    String getSortBy () {
        return sortBy;
    }

    public
    void setSortBy (String sortBy) {
        this.sortBy = sortBy;
    }

    public
    String getDirection () {
        return direction;
    }

    public
    void setDirection (String direction) {
        this.direction = direction;
    }

    public
    String getSearch () {
        return search;
    }

    public
    void setSearch (String search) {
        this.search = search;
    }
}
