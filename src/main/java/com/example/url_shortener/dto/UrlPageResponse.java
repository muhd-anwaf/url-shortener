package com.example.url_shortener.dto;

import java.util.List;

public class UrlPageResponse {

    private List<UrlStatsResponse> contents;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    public UrlPageResponse(List<UrlStatsResponse> contents, int page, int size,
                           long totalElements, int totalPages) {
        this.contents = contents;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public List<UrlStatsResponse> getContents() {
        return contents;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
