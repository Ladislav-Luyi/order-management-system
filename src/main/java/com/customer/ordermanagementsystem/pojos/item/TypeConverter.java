package com.customer.ordermanagementsystem.pojos.item;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TypeConverter  implements AttributeConverter<Type, String> {

    @Override
    public String convertToDatabaseColumn(Type type) {
        return type.getShortName();
    }

    @Override
    public Type convertToEntityAttribute(String dbData) {
        return Type.fromShortName(dbData);
    }

}
