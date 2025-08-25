package com.jade.infjpastudy.repository;

import com.jade.infjpastudy.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
