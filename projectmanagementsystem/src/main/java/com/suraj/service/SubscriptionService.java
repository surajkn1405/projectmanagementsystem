package com.suraj.service;

import com.suraj.modal.PlaneType;
import com.suraj.modal.Subscription;
import com.suraj.modal.User;

public interface SubscriptionService {

    Subscription createSubscription(User user);

    Subscription getUserSubscription(Long userId);

    Subscription updateSubscription(Long userId, PlaneType planeType);

    boolean isValid(Subscription subscription);
}
