package com.group6.recipes.dao;

import com.group6.recipes.model.Notification;
import java.util.List;

public interface NotificationDAO {
    public void addNotification(Notification notification);
    public List<Notification> getNotificationsByUser(int userId) ;
    public void markAsRead(int notificationId) ;
    public void deleteNotification(int notificationId) ;
} 