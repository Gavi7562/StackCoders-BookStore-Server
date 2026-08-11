package com.stackcoders.bookstore.service;

import com.stackcoders.bookstore.dto.response.BusinessAnalyticsResponse;

public interface BusinessAnalyticsService {

    BusinessAnalyticsResponse getDailyAnalytics(String date);

    BusinessAnalyticsResponse getMonthlyAnalytics(int month, int year);

    BusinessAnalyticsResponse getYearlyAnalytics(int year);

    BusinessAnalyticsResponse getOverallAnalytics();
}
