package com.project.love_data.model.resource;

import com.project.love_data.model.service.Location;
import com.project.love_data.model.user.User;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table (name = "image")
@ToString
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Image {
    @Id
    @Column(name = "img_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = org.hibernate.annotations.CascadeType.ALL)
    private Long img_no;

//    @Column(name = "user_no", nullable = false)
//    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_no")
//    private User user;

    @Column(name = "user_no", nullable = false)
    private Long user_no;

//    @Column(name = "loc_no", nullable = true)
//    @ManyToOne(targetEntity = Location.class, fetch = FetchType.LAZY)
//    @JoinColumn(name = "loc_no")
//    private Location location;

    @Column(name = "loc_no", nullable = true)
    private Long loc_no;

    // @Todo 코스, 리뷰 칼럼 추가

    @Column(name = "img_url", nullable = false, length = 200)
    private String img_url;

    @Column(name = "img_uuid", nullable = false, length = 45, unique = true)
    private String img_uuid;
}
