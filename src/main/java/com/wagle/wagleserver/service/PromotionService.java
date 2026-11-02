package com.wagle.wagleserver.service;

import com.wagle.wagleserver.entity.Promotion;
import com.wagle.wagleserver.repository.PromotionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PromotionService {

    private final PromotionRepository promotionRepository;

    public PromotionService(PromotionRepository promotionRepository) {
        this.promotionRepository = promotionRepository;
    }

    public Promotion createPromotion(Promotion promotion) {
        return promotionRepository.save(promotion);
    }

    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }

    public Optional<Promotion> getPromotion(Long promotionId) {
        return promotionRepository.findById(promotionId);
    }

    public void deletePromotion(Long promotionId) {
        promotionRepository.deleteById(promotionId);
    }
}