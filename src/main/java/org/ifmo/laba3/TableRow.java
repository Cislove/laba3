package org.ifmo.laba3;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name="records")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Named("tableRow")
@ApplicationScoped
public class TableRow implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Embedded
    private Point point;
    @Column(name="in_area", nullable = false)
    private boolean inArea;
    @Column(name="start_time", nullable = false)
    private LocalDateTime startTime;
    @Column(name="processed_time", nullable = false)
    private long processedTime;

    public TableRow(Point point, boolean inArea, LocalDateTime startTime) {
        this.point = point;
        this.inArea = inArea;
        this.startTime = startTime;
        this.processedTime = Duration.between(startTime, LocalDateTime.now()).toMillis();
    }
}
