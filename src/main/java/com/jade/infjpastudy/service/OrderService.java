package com.jade.infjpastudy.service;

import com.jade.infjpastudy.domain.Delivery;
import com.jade.infjpastudy.domain.Member;
import com.jade.infjpastudy.domain.Order;
import com.jade.infjpastudy.domain.OrderItem;
import com.jade.infjpastudy.domain.item.Item;
import com.jade.infjpastudy.repository.ItemRepository;
import com.jade.infjpastudy.repository.MemberRepository;
import com.jade.infjpastudy.repository.OrderRepository;
import com.jade.infjpastudy.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }

}
