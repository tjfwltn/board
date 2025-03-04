package com.example.board.controller;

import com.example.board.dto.RecommendRequest;
import com.example.board.service.RecommendService;
import org.springframework.web.bind.annotation.*;

@RestController
public class RecommendController {
    private final RecommendService recommendService;

    public RecommendController(RecommendService recommendService) {
        this.recommendService = recommendService;
    }

    @PostMapping("/recommend/{postId}")
    public String recommend(@PathVariable Long postId, @RequestBody RecommendRequest recommendRequest) {
        return recommendService.recommend(postId, recommendRequest);
    }

    @DeleteMapping("/recommend/{postId}")
    public String cancel(@PathVariable Long postId, @RequestBody RecommendRequest recommendRequest) {
        return recommendService.delete(postId, recommendRequest);
    }

}
