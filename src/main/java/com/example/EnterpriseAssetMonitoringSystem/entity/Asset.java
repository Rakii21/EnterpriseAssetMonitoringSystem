package com.example.EnterpriseAssetMonitoringSystem.entity;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asset {
    @Id // Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatically generate Primary Key value
    private Long id;
    private String name;
    private String type;
    private String location;
    private Double thresholdTemp;
    private Double thresholdPressure;
    @ManyToOne // Define a many-to-one relationship with the User entity
    @JoinColumn(name="assigned_to") // Specify the foreign key column name in the database
    private User assignedTo; // User to whom the asset is assigned
}
