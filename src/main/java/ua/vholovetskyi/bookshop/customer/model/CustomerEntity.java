package ua.vholovetskyi.bookshop.customer.model;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author Volodymyr Holovetskyi
 * @version 1.0
 * @since 2024-04-5
 */
@Getter
@Setter
@ToString
@Table(name = "customer")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_seq_gen")
    @SequenceGenerator(name = "customer_seq_gen", sequenceName = "customer_id_seq", allocationSize = 1)
    @Column(name = "cust_id", nullable = false)
    private Long id;
    @Column(nullable = false, length = 70)
    private String firstName;
    @Column(nullable = false, length = 70)
    private String lastName;
    @Column(nullable = false, length = 70, unique = true)
    private String email;
    @Column(nullable = false, length = 16)
    private String phone;
    @Column(nullable = false, length = 80)
    private String street;
    @Column(nullable = false, length = 70)
    private String city;
    @Column(nullable = false, length = 10)
    private String zipCode;

    public boolean equalEmails(String anotherEmail) {
        return email.equalsIgnoreCase(anotherEmail);
    }

    public CustomerEntity updateFields(CustomerEntity customer) {
        if (customer.getId() != null) {
            this.id = customer.getId();
        }
        if (customer.getFirstName() != null) {
            this.firstName = customer.getFirstName();
        }
        if (customer.getLastName() != null) {
            this.lastName = customer.getLastName();
        }
        if (customer.getEmail() != null) {
            this.email = customer.getEmail();
        }
        if (customer.getPhone() != null) {
            this.phone = customer.getPhone();
        }
        if (customer.getStreet() != null) {
            this.street = customer.getStreet();
        }
        if (customer.getCity() != null) {
            this.city = customer.getCity();
        }
        if (customer.zipCode != null) {
            this.zipCode = customer.getZipCode();
        }
        return this;
    }
}
