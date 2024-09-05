package com.sparta.onboarding.domain.comment;

import com.sparta.onboarding.domain.schedule.Schedule;
import com.sparta.onboarding.common.Timestamped;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comment")
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false, name = "createdAt")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "scheduleId", nullable = false)
    private Schedule schedule;

    public Comment(String content, String username, Schedule schedule) {
        this.content = content;
        this.username = username;
        this.createdAt = LocalDateTime.now();
        this.schedule = schedule;
    }

    public void update(String content) {
        this.content = content;
    }
}