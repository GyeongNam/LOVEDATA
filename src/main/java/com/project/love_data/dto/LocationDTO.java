package com.project.love_data.dto;

import com.project.love_data.model.resource.LocationImage;
import com.project.love_data.model.service.Comment;
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
    private String fullRoadAddr;
    private String roadAddr;
    private String addrDetail;
    private String zipNo;
    private String siDo;
    private String siGunGu;
    private String info;

    @Builder.Default
    private LocalDateTime regDate = LocalDateTime.now();
    @Builder.Default
    private LocalDateTime modDate = LocalDateTime.now();
    @Builder.Default
    private String loc_uuid = UUID.randomUUID().toString();
    @Builder.Default
    private Set<String> tagSet = new HashSet<>();
    @Builder.Default
    private int likeCount = 0;
    @Builder.Default
    private int viewCount = 0;
    @Builder.Default
    private List<LocationImage> imgList = new ArrayList<>();
    @Builder.Default
    private List<Comment> cmtList = new ArrayList<>();
    @Builder.Default
    private int liveCmtCount = 0;
    @Builder.Default
    private String thumbnail = "";
    @Builder.Default
    private boolean is_deleted = false;
    @Builder.Default
    private boolean is_reported = false;

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
                        .imgSet(new HashSet<>(this.getImgList()))
                        .cmtSet(new HashSet<>(getCmtList()))
                        .thumbnail(this.getThumbnail())
                        .build());
        imgList.add(img);
    }

    public void addImg(List<LocationImage> img) {
        for (LocationImage locationImage : img) {
            addImg(locationImage);
        }
    }

    public void addCmt(Comment cmt) {
        cmt.setCmtIdx((long) imgList.size());
        cmt.setLocation(
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
                        .imgSet(new HashSet<>(this.getImgList()))
                        .cmtSet(new HashSet<>(getCmtList()))
                        .thumbnail(this.getThumbnail())
                        .build());
        cmtList.add(cmt);
    }

    public void addCmt(List<Comment> cmt) {
        for (Comment item : cmt) {
            addCmt(item);
        }
    }

    public String longToString(Long item) {

        if (item == null) {
            return null;
        }

        return String.valueOf(item);
    }

    public String printImgURLS() {
        StringBuilder sb = new StringBuilder();

        if(imgList == null) {
            return null;
        }

        for (LocationImage img : imgList) {
            sb.append(img.getImg_url());
            sb.append("_");
        }

        return sb.toString().substring(0, sb.toString().length()-1);
    }
    //CHOI

    public String myreviewload(String loc_name) {

        return "loc_name";
    }
}
