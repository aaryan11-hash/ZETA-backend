package com.tier4.backend.web.Domain;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "pot")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
public class Pot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Double currentAmount;
    private Double amount;
    // {mm/dd/hh} format

    @Column(updatable = true)
    private String timeLeft;
    private String totalTime;
    private Boolean autoDeduct;

    @Column(nullable = true)
    private Double weight;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
