package com.project.love_data.model.resource;

import com.project.love_data.model.base.TimeEntity;
import com.project.love_data.model.service.Location;
import com.project.love_data.model.user.User;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.util.Lazy;

import javax.persistence.*;

@Entity
@Table (name = "loc_image")
@ToString (exclude = "location")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationImage extends TimeEntity {
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

    @Column(name = "img_idx", nullable = false)
    @Builder.Default
    private Long idx = 0L;

    @Column(name = "user_no", nullable = false)
    private Long user_no;

//    @Column(name = "loc_no", nullable = true)
//    @ManyToOne(targetEntity = Location.class, fetch = FetchType.LAZY)
//    @JoinColumn(name = "loc_no")
//    private Location location;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Location location;

    @Column(name = "img_url", nullable = false, length = 200)
    private String img_url;

    @Column(name = "img_uuid", nullable = false, length = 45)
    private String img_uuid;

    @Column(name = "is_deleted", nullable = false)
    @Builder.Default
    private boolean is_deleted = false;
}
