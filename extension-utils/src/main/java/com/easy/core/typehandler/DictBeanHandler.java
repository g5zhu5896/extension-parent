package com.easy.core.typehandler;


import com.easy.core.dict.bean.IDictBean;
import com.easy.core.dict.service.IDictService;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * mybatis 枚举类型处理器
 * 请扫描IDictBean 的子类进行注册handler
 *
 * @author zzz
 */
public class DictBeanHandler<T extends IDictBean> extends BaseTypeHandler<IDictBean> {


    private Class<T> type;

    public DictBeanHandler(Class<T> type) {
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, IDictBean parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getValue().toString());
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return convert(rs.getString(columnName));
    }

    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return convert(rs.getString(columnIndex));
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return convert(cs.getString(columnIndex));
    }

    private T convert(String value) {
        return (T) IDictService.getInstance().getDictBean(value, type);
    }
}
