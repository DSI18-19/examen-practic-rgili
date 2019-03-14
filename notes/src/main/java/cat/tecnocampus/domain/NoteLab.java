package cat.tecnocampus.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by roure on 8/09/2017.
 */

public class NoteLab {

    @NotNull
    private String title;
    private String content;

    private LocalDateTime dateCreation;

    private LocalDateTime dateEdit;

    private String owner;
    private long id;

    public NoteLab() {
    }

    private NoteLab(NoteLabBuilder builder) {
        id = builder.id;
        title = builder.title;
        content = builder.content;
        dateCreation = builder.dateCreation;
        dateEdit = builder.dateEdit;
        owner = builder.owner;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getDateEdit() {
        return dateEdit;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setDateEdit(LocalDateTime dateEdit) {
        this.dateEdit = dateEdit;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String toString(){
        return "NoteLab: "+this.title+", Content: "+ this.content;
    }

    private String buildKey(NoteLab noteLab) {
        return noteLab.getTitle() + noteLab.getDateCreation();
    }

    public static class NoteLabBuilder {
        private final String title;
        private final String content;
        private final String owner;

        private LocalDateTime dateCreation;
        private LocalDateTime dateEdit;
        private long id;

        public NoteLabBuilder(String title, String contennt, String owner) {
            this.title = title;
            this.content = contennt;
            this.owner = owner;
        }

        public NoteLabBuilder dateCreation(LocalDateTime dateCreation) {
            this.dateCreation = dateCreation;
            return this;
        }

        public NoteLabBuilder dateEdit(LocalDateTime dateEdit) {
            this.dateEdit = dateEdit;
            return this;
        }

        public NoteLabBuilder id(long id) {
            this.id = id;
            return this;
        }

        public NoteLab build() {
            return new NoteLab(this);
        }
    }
}
