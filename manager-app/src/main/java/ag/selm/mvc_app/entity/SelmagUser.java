package ag.selm.mvc_app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "t_user") // Указываем имя таблицы в базе данных
public class SelmagUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоматическая генерация ID
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true, nullable = false, length = 50) // Уникальное имя пользователя
    private String username;

    @Column(name = "password_hash", nullable = false) // Хэш пароля
    private String passwordHash;

    @ManyToMany
    @JoinTable(name = "t_user_authority",
    joinColumns = @JoinColumn(name = "id_user"),
    inverseJoinColumns = @JoinColumn(name = "id_authority"))
    private List<Authority> authorities = new LinkedList<>();
}