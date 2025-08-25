package com.jade.infjpastudy.repository;

import com.jade.infjpastudy.domain.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> cq = cb.createQuery(Order.class);
        Root<Order> root = cq.from(Order.class);
        Join<Object, Object> m = root.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();
        if(orderSearch.getOrderStatus() != null) {
            criteria.add(cb.equal(m.get("status"), orderSearch.getOrderStatus()));
        }

        if(orderSearch.getMemberName() != null) {
            criteria.add(cb.equal(m.get("memberName"), orderSearch.getMemberName()));
        }


        cq.where(criteria.toArray(new Predicate[criteria.size()]));
        TypedQuery<Order> query = em.createQuery(cq);
        return query.getResultList();

    }
}
