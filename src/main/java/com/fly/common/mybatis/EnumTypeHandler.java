//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.fly.common.mybatis;



import com.fly.common.model.EnumValue;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EnumTypeHandler extends BaseTypeHandler<EnumValue> {
    private final EnumValue[] enums;

    public EnumTypeHandler(Class<EnumValue> type) {
        assert type != null;

        this.enums = (EnumValue[])type.getEnumConstants();
    }

    public void setNonNullParameter(PreparedStatement preparedStatement, int i, EnumValue enumValue, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, enumValue.getValue());
    }

    public EnumValue getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return this.handleResult(resultSet.getInt(s));
    }

    public EnumValue getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return this.handleResult(resultSet.getInt(i));
    }

    public EnumValue getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return this.handleResult(callableStatement.getInt(i));
    }

    private EnumValue handleResult(int val) {
        EnumValue[] var2 = this.enums;
        int var3 = var2.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            EnumValue v = var2[var4];
            if(v.getValue() == val) {
                return v;
            }
        }

        return null;
    }
}
