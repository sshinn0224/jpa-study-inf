package com.jade.infjpastudy.controller;

import com.jade.infjpastudy.domain.Member;
import com.jade.infjpastudy.domain.Order;
import com.jade.infjpastudy.domain.item.Item;
import com.jade.infjpastudy.repository.OrderSearch;
import com.jade.infjpastudy.service.ItemService;
import com.jade.infjpastudy.service.MemberService;
import com.jade.infjpastudy.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findAll();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "order/orderForm";
    }

    @PostMapping("/order")
    public String create(@RequestParam("memberId") Long memberId,
                         @RequestParam("itemId") Long itemId,
                         @RequestParam("count") int count) {

        orderService.order(memberId, itemId, count);

        return "redirect:/orders";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);

        return "redirect:/orders";
    }
}
