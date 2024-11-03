package com.webtech.schoolfeedingsystemtracker.model;

import java.util.List;

public class PageObject {

    private List<User> content; // List of users
    private int totalPages; // Total number of pages
    private long totalElements; // Total number of elements
    private int number; // Current page number
    private int size; // Size of the page (number of elements per page)
    private String sort; // Field for sorting

    // Full constructor with sorting
    public PageObject(List<User> content, int totalPages, long totalElements, int number, int size, String sort) {
        this.content = content;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.number = number;
        this.size = size;
        this.sort = sort;
    }

    // Overloaded constructor without sorting
    public PageObject(List<User> content, int totalPages, long totalElements, int number, int size) {
        this(content, totalPages, totalElements, number, size, ""); // default sort as empty string
    }

    // Getter and Setter methods
    public List<User> getContent() {
        return content;
    }

    public void setContent(List<User> content) {
        this.content = content;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    // Pagination helper methods
    public boolean hasPrevious() {
        return number > 0;
    }

    public boolean hasNext() {
        return number + 1 < totalPages;
    }
}
