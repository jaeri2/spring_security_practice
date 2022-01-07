package com.example.securitypracitce.domain.entity;

import com.sun.tools.jconsole.JConsoleContext;
import lombok.*;

import javax.persistence.*;

@ToString(exclude = {"user"})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Board extends BaseEntity{

    @Column(name = "board_id")
    @Id @GeneratedValue
    private Long id;

    private String title;

    private String contents;

    private String writer;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public void changeBoard(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
