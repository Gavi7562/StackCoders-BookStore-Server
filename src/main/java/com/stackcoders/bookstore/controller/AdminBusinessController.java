package com.stackcoders.bookstore.controller;

import com.stackcoders.bookstore.dto.response.ApiResponse;
import com.stackcoders.bookstore.dto.response.BusinessAnalyticsResponse;
import com.stackcoders.bookstore.service.BusinessAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/business")
@RequiredArgsConstructor
public class AdminBusinessController {

    private final BusinessAnalyticsService analyticsService;

    @GetMapping("/daily")
    public ResponseEntity<ApiResponse<BusinessAnalyticsResponse>> getDaily(@RequestParam String date) {
        return ResponseEntity
                .ok(ApiResponse.success("Daily analytics retrieved", analyticsService.getDailyAnalytics(date)));
    }

    @GetMapping("/monthly")
    public ResponseEntity<ApiResponse<BusinessAnalyticsResponse>> getMonthly(@RequestParam int month,
            @RequestParam int year) {
        return ResponseEntity.ok(
                ApiResponse.success("Monthly analytics retrieved", analyticsService.getMonthlyAnalytics(month, year)));
    }

    @GetMapping("/yearly")
    public ResponseEntity<ApiResponse<BusinessAnalyticsResponse>> getYearly(@RequestParam int year) {
        return ResponseEntity
                .ok(ApiResponse.success("Yearly analytics retrieved", analyticsService.getYearlyAnalytics(year)));
    }

    @GetMapping("/overall")
    public ResponseEntity<ApiResponse<BusinessAnalyticsResponse>> getOverall() {
        return ResponseEntity
                .ok(ApiResponse.success("Overall analytics retrieved", analyticsService.getOverallAnalytics()));
    }
}
