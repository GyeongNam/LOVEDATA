package com.project.love_data.businessLogic.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Service
@Log4j2
public class ControllerScriptUtils {

    public void init(HttpServletResponse response) {
        response.setContentType("text/html; charset=euc-kr");
        response.setCharacterEncoding("euc-kr");
    }

    public void alert(HttpServletResponse response, String alertText) throws IOException {
        init(response);
        PrintWriter out = response.getWriter();
        out.println("<script>alert('" + alertText + "');</script> ");
        out.flush();
    }

    public void alertAndMovePage(HttpServletResponse response, String alertText, String nextPage)
            throws IOException {
        init(response);
        PrintWriter out = response.getWriter();
        out.println("<script>alert('" + alertText + "'); location.href='" + nextPage + "';</script> ");
        out.flush();
    }

    public void alertAndBackPage(HttpServletResponse response, String alertText) throws IOException {
        init(response);
        PrintWriter out = response.getWriter();
        out.println("<script>alert('" + alertText + "'); history.go(-1);</script>");
        out.flush();
    }

    public void alertPageout(HttpServletResponse response, String alertText) throws IOException {
        init(response);
        PrintWriter out = response.getWriter();
        out.println("<script>alert('" + alertText + "'); window.close();</script>");
        out.flush();
    }

    public void Nextpage(HttpServletResponse response, String Nextpage) throws IOException {
        init(response);
        PrintWriter out = response.getWriter();
        out.println("<script>location.href='" + Nextpage + "';</script>");
        out.flush();
    }
}
