package com.vito16.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import io.codearte.jfairy.producer.person.Person;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VitoPerson implements Serializable {

    @ExcelProperty(value = "姓")
    private  String firstName;

    @ExcelProperty(value = "名")
    private  String lastName;

    @ExcelProperty(value = "邮箱")
    private  String email;

    @ExcelProperty(value = "用户名")
    private  String username;

    @ExcelProperty(value = "密码")
    private  String password;

    @ExcelProperty(value = "手机")
    private  String telephoneNumber;

//    @ExcelProperty(value = "生日",converter = DateTimeConverter.class)
//    @DateTimeFormat("yyyy年MM月dd日")
//    private  DateTime dateOfBirth;

}
