package com.project.love_data.dto;

import com.project.love_data.model.resource.Image;
import com.project.love_data.model.service.Location;
import com.project.love_data.model.service.LocationTag;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LocationDTO {
    private Long loc_no;
    private String loc_name;
    private Long user_no;
    private String tel;
    private String roadAddr;
    private String addrDetail;
    private String zipNo;
    private String siDo;
    private String siGunGu;
    private String info;
    private LocalDateTime regDate;
    private LocalDateTime modDate;

    @Builder.Default
    private String loc_uuid = UUID.randomUUID().toString();
    @Builder.Default
    private Set<String> tagSet = new HashSet<>();
    @Builder.Default
    private Long likeCount = 0L;
    @Builder.Default
    private Long viewCount = 0L;
    @Builder.Default
    private List<Image> imgList = new ArrayList<>();

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

    public void addImg(Image img) {
        img.setIdx((long) imgList.size());
        img.setLocation(
                Location.builder()
                        .loc_no(this.getLoc_no())
                        .loc_name(this.getLoc_name())
                        .loc_uuid(this.getLoc_uuid())
                        .user_no(this.getUser_no())
                        .roadAddr(this.getRoadAddr())
                        .addrDetail(this.getAddrDetail())
                        .siDo(this.getSiDo())
                        .siGunGu(this.getSiGunGu())
                        .info(this.getInfo())
                        .tel(this.getTel())
                        .zipNo(this.getZipNo())
                        .tagSet(this.getTagSet())
                        .imgList(this.getImgList())
                        .build());
        imgList.add(img);
    }

    public void addImg(List<Image> img) {
        for (Image image : img) {
            addImg(image);
        }
    }
}
