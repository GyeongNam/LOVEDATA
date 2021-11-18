package com.project.love_data.model.service;

import com.project.love_data.model.base.TimeEntity;
import com.project.love_data.model.*;
import com.project.love_data.model.resource.LocationImage;
import lombok.*;
import lombok.extern.log4j.Log4j2;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "location")
@ToString(exclude = "imgSet")
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
    private Long loc_no;

    @Column(name = "loc_name", nullable = false, length = 40)
    private String loc_name;

    @Column(name = "user_no", nullable = false)
    private Long user_no;

    @Column(name = "loc_uuid", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @Builder.Default
    private String loc_uuid = UUID.randomUUID().toString();

    @Column(name = "tel", nullable = false)
    private String tel;

    @Column(name = "fullroadaddr", length = 90, nullable = false)
    private String fullRoadAddr;

    @Column(name = "roadaddr", length = 50, nullable = false)
    private String roadAddr;

    @Column(name = "addrdetail", length = 30, nullable = false)
    private String addrDetail;

    @Column(name = "zipno", length = 10, nullable = false)
    private String zipNo;

    @Column(name = "sido", length = 15, nullable = false)
    private String siDo;

    @Column(name = "sigungu", length = 8, nullable = false)
    private String siGunGu;

    @Column(name = "info", length = 150, nullable = false)
    private String info;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "loc_no")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @Builder.Default
    private Set<String> tagSet = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "loc_no")
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @Builder.Default
    private Set<LocationImage> imgSet = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "location")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    @Builder.Default
    private Set<Comment> cmtSet = new HashSet<>();

    @Column(name = "likecount", nullable = false, columnDefinition = "int default 0")
    @Builder.Default
    private int likeCount = 0;

    @Column(name = "viewcount", nullable = false, columnDefinition = "int default 0")
    @Builder.Default
    private int viewCount = 0;

    @Column(name = "thumbnail", nullable = false)
    @Builder.Default
    private String thumbnail = "";

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean is_deleted = false;

    @Column(name = "is_reported", nullable = false)
    @Builder.Default
    private boolean is_reported = false;

    // Todo 여기에 댓글 칼럼도 추가

    public void addLocTag(String str) {
        LocationTag tag = null;
        String temp = "";

        temp = str.replaceAll(" ", "_").toUpperCase(Locale.ROOT);

        tag = LocationTag.valueOf(temp);

        tagSet.add(tag.name());
    }

    public String getFullAddr() {
        return this.roadAddr + this.addrDetail;
    }

    public void addImg(LocationImage img) {
        img.setIdx((long) imgSet.size());
        img.setLocation(this);
        imgSet.add(img);
    }

    public void addImg(List<LocationImage> img) {
        for (LocationImage locationImage : img) {
            addImg(locationImage);
        }
    }

    public void addCmt(Comment cmt) {
        cmt.setCmtIdx((long) cmtSet.size());
        cmt.setLocation(this);
        cmtSet.add(cmt);
    }

    public void addCmt(List<Comment> cmt) {
        for (Comment item : cmt) {
            addCmt(item);
        }
    }
}
