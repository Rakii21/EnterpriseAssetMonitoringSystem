package com.example.EnterpriseAssetMonitoringSystem.controller;

import com.example.EnterpriseAssetMonitoringSystem.dto.UptimeLogdto;
import com.example.EnterpriseAssetMonitoringSystem.entity.UptimeLog;
import com.example.EnterpriseAssetMonitoringSystem.service.UptimeLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//Controller for viewing the uptime long entry
@RestController
@RequestMapping("/api/uptime")
@RequiredArgsConstructor
public class UptimeLogController {
    private final UptimeLogService uptimelogService;

    //UP and DOWN status for a specific asset
    @PostMapping("/log")
    public UptimeLog logUptime(@RequestBody @Valid UptimeLogdto dto, @RequestParam Long userId) {
        return uptimelogService.logStatus(dto, userId);
    }
    // Get all logs for that asset
    @GetMapping("/api/uptime/asset/{id}")
    public List<UptimeLog> getLogsByAsset(@PathVariable Long assetid){
        return uptimelogService.getLogsByAsset(assetid);
    }
}
