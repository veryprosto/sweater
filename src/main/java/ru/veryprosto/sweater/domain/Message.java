package ru.veryprosto.sweater.domain;

import ru.veryprosto.sweater.domain.User;

import javax.persistence.*;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)//спринг сам будет определять как генерировать это Id
    private Integer id;

    private String text;
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)//означает что каждый раз когда мы получаем message мы сразу хотим получать инфу про автора, а не делать запрос в БД, как в случае с LAZY
    @JoinColumn(name = "user_id")
    private User author;

    private String filename;

    public Message() {//в ентити должен быть ОБЯЗАТЕЛЬНО конструктор по умолчанию
    }

    public Message(String text, String tag, User author) {
        this.text = text;
        this.tag = tag;
        this.author = author;
    }

    public String getAuthorName(){
        return author!=null ? author.getUsername(): "<none>";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
