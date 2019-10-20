package com.vito16.excel.web;

import com.alibaba.excel.EasyExcel;
import com.vito16.excel.ExcelExpoter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@RequestMapping
@Controller
public class TestController {

    @Autowired
    ExcelExpoter excelExpoter;

    @GetMapping("abc")
    @ResponseBody
    public void genExcel(HttpServletResponse response){
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=demo.xlsx");
        excelExpoter.dynamicHeadWrite(response);
    }

}
