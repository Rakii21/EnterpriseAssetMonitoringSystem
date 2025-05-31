
package com.example.EnterpriseAssetMonitoringSystem.controller;

import com.example.EnterpriseAssetMonitoringSystem.entity.UptimeLog;
import com.example.EnterpriseAssetMonitoringSystem.uptimeLogTest.UptimeLogService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name="Uptime Log")
@RestController
@RequestMapping("/api/uptime")
@RequiredArgsConstructor
public class UptimeLogController {

    private final UptimeLogService uptimeService;

//    @PostMapping("/log")
//    public UptimeLog logUptime(@RequestBody @Valid UptimeLogDTO dto, @RequestParam Long userId) {
//        return uptimeService.logStatus(dto, userId);
//    }

    @GetMapping("/asset/{assetId}")
    public List<UptimeLog> getLogsByAsset(@PathVariable Long assetId) {
        return uptimeService.getLogsByAsset(assetId);
 }
}