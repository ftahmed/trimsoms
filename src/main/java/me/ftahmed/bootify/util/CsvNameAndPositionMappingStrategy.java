package me.ftahmed.bootify.util;

import com.opencsv.bean.*;
import com.opencsv.exceptions.*;

public class CsvNameAndPositionMappingStrategy<T> extends ColumnPositionMappingStrategy<T> {

    private final String EMPTY_STRING = "";

    @Override
    public String[] generateHeader(T bean) throws CsvRequiredFieldEmptyException {

        String[] headersAsPerFieldName = getFieldMap().generateHeader(bean); // header name based on field name

        String[] header = new String[headersAsPerFieldName.length];

        for (int i = 0; i <= headersAsPerFieldName.length - 1; i++) {

            BeanField beanField = findField(i);

            String columnHeaderName = extractHeaderName(beanField); // header name based on @CsvBindByName annotation

            if (columnHeaderName.isEmpty()) // No @CsvBindByName is present
                columnHeaderName = headersAsPerFieldName[i]; // defaults to header name based on field name

            header[i] = columnHeaderName;
        }

        headerIndex.initializeHeaderIndex(header);

        return header;
    }

    private String extractHeaderName(final BeanField beanField) {
        if (beanField == null || beanField.getField() == null || beanField.getField().getDeclaredAnnotationsByType(CsvBindByName.class).length == 0) {
            return EMPTY_STRING;
        }

        final CsvBindByName bindByNameAnnotation = beanField.getField().getDeclaredAnnotationsByType(CsvBindByName.class)[0];
        return bindByNameAnnotation.column();
    }
}