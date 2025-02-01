package ef.models;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import java.util.UUID;

@Entity
public class Rounds {

    @Id
    @GeneratedValue(generator = "uuid2")  // Definicja generatora UUID
    @GenericGenerator(name = "uuid2", strategy = "uuid2")  // Generator UUID2
    @Column(name = "id", columnDefinition = "BINARY(16)")  // Przechowywanie UUID jako BINARY(16)
    private UUID id;

//    @Column(name = "user_id")
//    private UUID userId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "id")
    private User user;

    @Column(name = "balance")
    private double balance;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}
