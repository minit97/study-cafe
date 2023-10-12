package com.example.studyCafe.api.board.model;

import com.example.studyCafe.api.auth.model.User;
import com.example.studyCafe.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    private String title;

    @Lob    // java에선 string이지만 DB에서 long text
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public BoardEditor.BoardEditorBuilder toEditor() {
        return BoardEditor.builder()
                .title(title)
                .content(content);
    }

    @Builder
    public Board(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }

    public void edit(BoardEditor boardEditor) {
        this.title = boardEditor.getTitle();
        this.content = boardEditor.getContent();
    }
}
