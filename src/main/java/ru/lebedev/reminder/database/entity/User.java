package ru.lebedev.reminder.database.entity;

import jakarta.persistence.Column;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "telegram")
	private String telegram;

	@Column(name = "chat_id")
	private Long chatId;
	
	@OneToMany(mappedBy = "user", orphanRemoval = true)
	@Builder.Default
	private List<Reminder> reminders = new ArrayList<>();
}
