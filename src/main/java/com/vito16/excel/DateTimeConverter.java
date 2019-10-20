package com.vito16.excel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import org.joda.time.DateTime;

/**
 * @author vito
 */
public class DateTimeConverter implements Converter<DateTime> {
    @Override
    public Class supportJavaTypeKey() {
        return DateTime.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }

    @Override
    public DateTime convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {
            return new DateTime();
        } else {
            return new DateTime();
        }
    }

    @Override
    public CellData convertToExcelData(DateTime value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
        if (contentProperty == null || contentProperty.getDateTimeFormatProperty() == null) {

            return new CellData(value.toString());
        } else {
            return new CellData(value.toString());
        }
    }
}
