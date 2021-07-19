package com.project.love_data.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Log4j2
@Controller
public class PopupController {
    @RequestMapping("/popup/jusoPopup")
    public String popup_juso() {
        return "/popup/jusoPopup";
    }

    @RequestMapping("/popup/locationSearchPopup")
    public String popupLocationSearch() {
        return "/popup/locationSearchPopup";
    }
}
