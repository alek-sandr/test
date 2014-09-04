package test.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "COMMENTS")
public class Comment {
    @Id
    @GeneratedValue(generator = "increment")
    @GenericGenerator(name = "increment", strategy = "increment")
    private Long id;

    @Column(name = "CONTENT", nullable = false)
    @Type(type = "text")
    private String content;

    @Column(name = "COMMENT_DATE", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @OneToOne
    @JoinColumn(name = "AUTHOR", nullable = false)
    private User author;

    @OneToOne
    @JoinColumn(name = "RECORD_ID", nullable = false)
    private Record record;

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }
}
