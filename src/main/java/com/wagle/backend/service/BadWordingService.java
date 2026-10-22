package com.wagle.backend.service;

import com.wagle.backend.entity.BadWording;
import com.wagle.backend.repository.BadWordingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BadWordingService {

    private final BadWordingRepository badWordingRepository;

    public BadWordingService(BadWordingRepository badWordingRepository) {
        this.badWordingRepository = badWordingRepository;
    }

    public BadWording createBadWording(BadWording badWording) {
        return badWordingRepository.save(badWording);
    }

    public List<BadWording> getAllBadWordings() {
        return badWordingRepository.findAll();
    }

    public Optional<BadWording> getBadWording(Long id) {
        return badWordingRepository.findById(id);
    }

    public void deleteBadWording(Long id) {
        badWordingRepository.deleteById(id);
    }
}