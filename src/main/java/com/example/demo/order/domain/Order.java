package com.example.demo.order.domain;

import com.example.demo.member.domain.Member;
import com.example.demo.store.domain.Spot;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ono;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spno", nullable = false)
    private Spot spot;

    @Column(nullable = false)
    private int totalAmount;

    @Column(nullable = false)
    private int totalPrice;

    @CreatedDate
    @Builder.Default
    private LocalDateTime createtime = LocalDateTime.now();

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime pickupdate = LocalDateTime.now();

    @Builder.Default
    @Column(nullable = false, columnDefinition = "varchar(50) default 'PREPARING'")
    private OrderStatus status = OrderStatus.PREPARING;

    @Builder.Default
    @Column(nullable = false)
    private boolean delFlag = false;

    // QR 코드 파일명 필드
    @Column(nullable = true)
    private String qrCodePath;

    @Column(nullable = true)
    private LocalDateTime statusChangeTime;

    public void changeStatus(OrderStatus newStatus) {
        validateStatusChange(newStatus);
        this.status = newStatus;
        this.statusChangeTime = LocalDateTime.now();
    }

    public void validateStatusChange(OrderStatus newStatus) {
        if (this.status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Cancelled orders cannot be modified.");
        }
        if (this.status == OrderStatus.PREPARING && newStatus != OrderStatus.APPROVED) {
            throw new IllegalArgumentException("Invalid status transition from PREPARING.");
        }
    }
}