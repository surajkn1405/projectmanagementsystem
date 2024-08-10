package com.suraj.service;

import com.suraj.modal.PlaneType;
import com.suraj.modal.Subscription;
import com.suraj.modal.User;
import com.suraj.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class SubscriptionServiceImpl implements SubscriptionService{

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserService userService;


    @Override
    public Subscription createSubscription(User user) {
        Subscription subscription = new Subscription();;
        subscription.setUser(user);
        subscription.setSubscriptionStartDate(LocalDate.now());
        subscription.setSubscriptionEndtDate(LocalDate.now().plusMonths(12));
        subscription.setIsValid(true);
        subscription.setPlaneType(PlaneType.FREE);

        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription getUserSubscription(Long userId) {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        if (!isValid(subscription)){{
            subscription.setPlaneType(PlaneType.FREE);
            subscription.setSubscriptionEndtDate(LocalDate.now().plusMonths(12));
            subscription.setSubscriptionStartDate(LocalDate.now());
        }}
        return subscriptionRepository.save(subscription);
    }

    @Override
    public Subscription updateSubscription(Long userId, PlaneType planeType) {
        Subscription subscription = subscriptionRepository.findByUserId(userId);
        subscription.setPlaneType(planeType);
        subscription.setSubscriptionStartDate(LocalDate.now());
        if(planeType.equals(PlaneType.ANNUALLY)){
            subscription.setSubscriptionEndtDate(LocalDate.now().plusMonths(12));
        }else {
            subscription.setSubscriptionEndtDate(LocalDate.now().plusMonths(1));
        }
        return subscriptionRepository.save(subscription);
    }

    @Override
    public boolean isValid(Subscription subscription) {
        if(subscription.getPlaneType().equals(PlaneType.FREE)){
            return true;
        }
        LocalDate endDate = subscription.getSubscriptionEndtDate();
        LocalDate currentDate = LocalDate.now();
        return endDate.isAfter(currentDate) || endDate.isEqual(currentDate);
    }
}
