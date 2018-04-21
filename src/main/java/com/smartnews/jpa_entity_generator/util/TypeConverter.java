package com.smartnews.jpa_entity_generator.util;

import java.sql.Types;

/**
 * Utility to convert SQL types to Java types.
 */
public class TypeConverter {

    private TypeConverter() {
    }

    public static String toJavaType(int typeCode) {
        switch (typeCode) {
            case Types.ARRAY:
                return "Array";
            case Types.BIGINT:
                return "Long";
            // case Types.BINARY:
            case Types.BIT:
                // return "Boolean";
                return "boolean";
            case Types.BLOB:
                return "Blob";
            case Types.BOOLEAN:
                return "Boolean";
            case Types.CHAR:
                return "String";
            case Types.CLOB:
                return "Clob";
            // case Types.DATALINK:
            case Types.DATE:
                return "Date";
            case Types.DECIMAL:
                return "java.math.BigDecimal";
            // case Types.DISTINCT:
            case Types.DOUBLE:
                return "Double";
            case Types.FLOAT:
                return "Float";
            case Types.INTEGER:
                return "Integer";
            // case Types.JAVA_OBJECT:
            // case Types.LONGNVARCHAR:
            // case Types.LONGVARBINARY:
            case Types.LONGVARCHAR:
                return "String";
            // case Types.NCHAR:
            // case Types.NCLOB:
            // case Types.NULL:
            case Types.NUMERIC:
                return "BigDecimal";
            // case Types.NVARCHAR:
            // case Types.OTHER:
            case Types.REAL:
                return "Float";
            case Types.REF:
                return "Ref";
            // case Types.REF_CURSOR:
            // case Types.ROWID:
            case Types.SMALLINT:
                return "Short";
            // case Types.SQLXML:
            case Types.STRUCT:
                return "Struct";
            case Types.TIME:
                return "Time";
            case Types.TIME_WITH_TIMEZONE:
                return "Time";
            case Types.TIMESTAMP:
                return "Timestamp";
            case Types.TIMESTAMP_WITH_TIMEZONE:
                return "Timestamp";
            case Types.TINYINT:
                return "Byte";
            // case Types.VARBINARY:
            case Types.VARCHAR:
                return "String";
            default:
                return "String";
        }
    }

}
