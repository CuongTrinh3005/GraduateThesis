package com.example.onlinephoneshop.entity;

import com.example.onlinephoneshop.model.AuditModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import org.hibernate.annotations.Parameter;
import org.hibernate.validator.constraints.Length;

import java.util.Set;

@Entity
@Table(name = "products")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends AuditModel {
    @Id
    @Column
    @GeneratedValue(generator = "phoneid-generator")
    @GenericGenerator(name = "phoneid-generator",
            parameters = @Parameter(name = "prefix", value = "PD"),
            strategy = "com.example.onlinephoneshop.generators.PhoneIdGenerator")
    private String productId;

    @Column(columnDefinition = "nvarchar", length = 200)
    @Length(max = 200)
//    @NotBlank
    private String productName;

    @Column
    @DecimalMin(value = "0", message = "Price must be not under 0")
    private Float unitPrice;

    @Column
    @DecimalMin(value = "0", message = "Quantity must be not under 0")
    private Long quantity;

    @Column
    @DecimalMin(value = "0", message = "Discount must be not under 0%")
    @DecimalMax(value = "0.7", message = "Discount must be not over 70%")
    private Float discount;

    @Lob
    @Column(columnDefinition = "BLOB")
    private byte[] image;

    @Column(columnDefinition = "ntext")
    private String description;

    @Column(columnDefinition = "ntext")
    private String specification;

    @Column
    @DecimalMin(value = "0", message = "No. view must be not under 0")
    private Long viewCount;

    @Column
    private Boolean special;

    @Column
    private Boolean available;

    @Column
    @DecimalMin(value = "0", message ="Number of month in warranty must greater than 0")
    private Integer warranty;

    @Column
    private Float label;

    // Specify entity relationships
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "manufacturerId")
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<OrderDetails> orderDetails;

    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<Rating> ratings;
}
