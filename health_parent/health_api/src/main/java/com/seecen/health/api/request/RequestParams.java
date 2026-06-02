package com.seecen.health.api.request;


import lombok.Data;

import java.util.List;
//检查组信息和检查项id集合
@Data
public class RequestParams<T> {
    private T data ;

    private List<Integer> ids;
}
