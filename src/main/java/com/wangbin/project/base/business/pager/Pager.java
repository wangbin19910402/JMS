package com.wangbin.project.base.business.pager;

import com.wangbin.project.web.SearchInfo;

import java.util.Collections;
import java.util.List;

/**
 *
 *
 * @author wangbin
 **/
public class Pager<T> {

    private Integer pageNum;//总页数

    private Integer pageSize;//分页大小

    private Integer pageIndex;//第几页

    private Integer itemSize;//总数

    private SearchInfo searchInfo;//搜索参数

    private List<T> items = Collections.EMPTY_LIST;//返回结果list

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    /**
     * 方法重新计算总页数
     * @param pageSum 数据总数
     * @param pageSize 每页大小
     */
    public void setPageNum(Integer pageSum,Integer pageSize){
        this.pageNum = pageSum%pageSize == 0 ? pageSum/pageSize : pageSum/pageSize +1;
        if (this.pageNum == 0) {
            this.pageNum = 1;
        }
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getItemSize() {
        return itemSize;
    }

    public void setItemSize(Integer itemSize) {
        this.itemSize = itemSize;
    }

    public SearchInfo getSearchInfo() {
        return searchInfo;
    }

    public void setSearchInfo(SearchInfo searchInfo) {
        this.searchInfo = searchInfo;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

}
