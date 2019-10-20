package com.vito16.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.google.common.base.Objects;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import io.codearte.jfairy.producer.person.PersonProperties;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Component
@Slf4j
public class ExcelExpoter {

    public void abc(){
        List<VitoPerson> datas = getDataRows();

        String fileName = System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去读，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        // 如果这里想使用03 则 传入excelType参数即可
        EasyExcel.write(fileName, VitoPerson.class).sheet("模板").doWrite(datas);
        log.info("测试输出路径：{}",fileName);
    }

    private List<VitoPerson> getDataRows() {
        Fairy fairy = Fairy.create();
        List<VitoPerson> datas = Lists.newArrayList();
        for(int i = 0;i<10;i++){
            Person person = fairy.person();
            log.info("名字:{}",person.getFirstName());
            log.info("邮箱:{}",person.getEmail());
            log.info("手机:{}",person.getTelephoneNumber());

            VitoPerson vp = new VitoPerson();
            BeanUtils.copyProperties(person,vp);
            datas.add(vp);
        }
        return datas;
    }

    public void dynamicHeadWrite(HttpServletResponse response){
        List<VitoPerson> datas = getDataRows();

        try {
            //自定义列
            List<String> filterList = Lists.newArrayList("password","lastName","firstName");

            //生成自定义头
            List<List<String>> heads = buildHead(filterList,VitoPerson.class);

            //生成自定义数据
            List<List<String>> rowDate  = buildData(filterList,VitoPerson.class,datas);

            EasyExcel.write(response.getOutputStream()).head(heads).sheet("动态生成").doWrite(rowDate);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 生成表格头
     * @param filterList
     * @param clazz
     * @return
     */
    public List<List<String>> buildHead(List<String> filterList,Class clazz){
        List<List<String>> list = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for(String filterHead:filterList){
            for(Field field:fields){
                if(filterHead.equalsIgnoreCase(field.getName())){
                    log.info("筛选完成.{} - {}",filterHead,field.getName());
                    ExcelProperty excelProperty = field.getAnnotationsByType(ExcelProperty.class)[0];
                    String title = excelProperty.value()[0];
                    list.add(Lists.newArrayList(title));
                }
            }
        }
        return list;
    }

    /**
     * 生成表格数据（筛选+排序）
     * @param filterList
     * @param clazz
     * @param rows
     * @return
     */
    public  List<List<String>> buildData(List<String> filterList,Class clazz, Collection rows){
        List<List<String>> list = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        Iterator iterable = rows.iterator();
        while (iterable.hasNext()){
            List<String> rowDate = Lists.newArrayList();
            for(String filterHead:filterList){
                for(Field field:fields){
                    if(filterHead.equalsIgnoreCase(field.getName())){
                        log.info("筛选完成.{} - {}",filterHead,field.getName());
                        ExcelProperty excelProperty = field.getAnnotationsByType(ExcelProperty.class)[0];

                        try {
                            field.setAccessible(true);
                            Object obj = field.get(field.getType());
                            rowDate.add(obj.toString());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                        continue;
                    }
                }
            }
            list.add(rowDate);
        }
        return list;
    }


    private List<List<String>> head() {
        List<List<String>> list = new ArrayList<List<String>>();

        List<String> head0 = new ArrayList<String>();
        head0.add("名字");

        List<String> head1 = new ArrayList<String>();
        head1.add("数字");

        List<String> head2 = new ArrayList<String>();
        head2.add("日期");

        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }
}
