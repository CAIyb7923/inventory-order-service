package com.example.inventoryorderservice.common;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageResult<T> {
    private List<T> records;
    private long total;
    private int page;
    private int size;
    private int totalPages;

    public PageResult() {

    }

    public PageResult(List<T> records, long total, int page, int size, int totalPages) {
        this.records = records;
        this.total = total;
        this.page = page;
        this.size = size;
        this.totalPages = totalPages;
    }

    public static <T> PageResult<T> of(Page<T> page) {
        return new PageResult<>(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber() + 1,
                page.getSize(),
                page.getTotalPages()
        );
    }

    public List<T> getRecords() {
        return records;
    }

    public void setRecords(List<T> records) {
        this.records = records;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
