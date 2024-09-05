package com.sparta.onboarding.domain.comment;

import com.sparta.onboarding.common.Timestamped;
import com.sparta.onboarding.domain.schedule.Schedule;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    public Comment(String comment, String username, Schedule schedule) {
        this.comment = comment;
        this.username = username;
        this.schedule = schedule;
    }

    public void updateComment(String comment) {
        this.comment = comment;
    }
}
