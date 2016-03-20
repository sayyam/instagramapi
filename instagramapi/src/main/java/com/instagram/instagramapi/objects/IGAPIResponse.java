package com.instagram.instagramapi.objects;

import java.io.Serializable;

/**
 * Created by Sayyam on 3/18/16.
 */
public class IGAPIResponse implements Serializable {

    IGMeta meta;
    IGPagInfo pagination;
    Object data;

    public IGMeta getMeta() {
        return meta;
    }

    public void setMeta(IGMeta meta) {
        this.meta = meta;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public IGPagInfo getPagination() {
        return pagination;
    }

    public void setPagination(IGPagInfo pagination) {
        this.pagination = pagination;
    }
}
