package com.pm.notifications.service;

import com.pm.notifications.model.NotificationInfo;

public interface RabbitMqListener {

     void handlerFinishEnroll(NotificationInfo notificationInfo);
}
