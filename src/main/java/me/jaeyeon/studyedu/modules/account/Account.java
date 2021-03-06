package me.jaeyeon.studyedu.modules.account;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity @Builder @AllArgsConstructor @NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter @EqualsAndHashCode(of = "id")
public class Account {

    @Id @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String nickname;
    private String password;

    private boolean emailVerified;
    private String emailCheckToken;
    private LocalDateTime emailCheckTokenGeneratedAt;
    private LocalDateTime joinedAt;

    private String bio;
    private String occupation;
    private String location;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String profileImage;

    public void generateEmailCheckToken() {
        this.emailCheckToken = UUID.randomUUID().toString();
        this.emailCheckTokenGeneratedAt = LocalDateTime.now();
    }

    public void completeSignUp() {
        this.emailVerified = true;
        this.joinedAt = LocalDateTime.now();
    }

    public boolean isValidToken(String token) {
        return this.emailCheckToken.equals(token);
    }

    public boolean canSendConfirmEmail() {
        return this.emailCheckTokenGeneratedAt.isBefore(LocalDateTime.now().minusSeconds(1));
    }
}
