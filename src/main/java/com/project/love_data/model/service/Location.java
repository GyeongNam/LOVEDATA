package com.project.love_data.model.service;

import com.project.love_data.model.base.TimeEntity;
import com.project.love_data.model.resource.Image;
import com.sun.istack.NotNull;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "location")
@ToString
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class Location extends TimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @NotNull
    private Long loc_no;

    @Column(name = "loc_name", nullable = false, length = 40, unique = true)
    private String loc_name;

    @Column(name = "user_no", nullable = false)
    private Long user_no;

    @Column(name = "roadaddr", length = 50, nullable = false)
    private String roadAddr;

    @Column(name = "addrdetail", length = 30, nullable = false)
    private String addrDetail;

    @Column(name = "sido", length = 15, nullable = false)
    private String siDo;

    @Column(name = "sigungu", length = 8, nullable = false)
    private String siGunGu;

    @Column(name = "info", length = 150, nullable = false)
    private String info;

    @ElementCollection(fetch = FetchType.LAZY)
    @JoinColumn(name = "loc_no")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @Builder.Default
    private Set<String> tagSet = new HashSet<>();

    @OneToMany(mappedBy = "Location", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Image> imgList = new ArrayList<>();

    @Column(name = "likecount", nullable = false, columnDefinition = "bigint default 0")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Builder.Default
    private Long likeCount = Long.valueOf(0);
    
    // Todo 여기에 댓글 칼럼도 추가

    public void addLocImg(Image img) {
        imgList.add(img);
    }

    public void addLocTag(String str) {
        LocationTag tag = null;
        String temp = "";

        temp = str.replaceAll(" ", "_").toUpperCase(Locale.ROOT);

        tag = LocationTag.valueOf(temp);

        if (tag == null) {
            log.warn("Location Entity에 tag를 추가하는 과정에서 오류 발생");
            return;
        }

        tagSet.add(tag.name());
    }
}
