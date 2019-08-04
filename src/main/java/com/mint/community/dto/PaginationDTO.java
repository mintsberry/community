package com.mint.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PaginationDTO {
    private List<?> data;
    private boolean hasPre;
    private boolean showFirstPage;
    private boolean hasNext;
    private boolean showEndPage;
    private int currentPage;
    private int countPage;
    private List<Integer> pages = new ArrayList<>();

    public void setPagination() {
    }

    public void setPagination(int count, int page, int size, List<?> list) {
        this.data = list;
        this.countPage = count % size == 0 ? count / size : count / size + 1;
        if (page < 1){
            page = 1;
        } else if (page > countPage){
            page = countPage;
        }
        this.currentPage = page;
        if (this.currentPage != countPage){
            hasNext = true;
        } else {
            hasNext = false;
        }
        if (this.currentPage != 1){
            hasPre = true;
        } else {
            hasPre = false;
        }
        if (this.currentPage > 4){
            showFirstPage = true;
        } else {
            showFirstPage = false;
        }
        if (this.currentPage < countPage - 3){
            showEndPage = true;
        } else {
            showEndPage = false;
        }
        this.pages.add(currentPage);
        for (int i = 1; i <= 3; i++){
            int prePage = currentPage - i;
            if (prePage >= 1){
                pages.add(0, prePage);
            }
        }
        for (int i = 1; i <= 3; i++){
            int prePage = currentPage + i;
            if (prePage <= countPage){
                pages.add(prePage);
            }
        }
    }
}
