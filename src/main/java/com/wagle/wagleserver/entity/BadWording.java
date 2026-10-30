package com.wagle.wagleserver.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class BadWording {

    @Id
    @Column(name = "log_id")
    private Long id;

    @Column(name = "message_id")
    private Long messageId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "detacted_word")
    private String detactedWord;

    private String action;

    @Column(name = "createad_at")
    private LocalDateTime createdAt;

}