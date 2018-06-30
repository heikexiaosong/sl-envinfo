package com.gavel.monitor.persistence.repository;

import com.gavel.monitor.persistence.entity.EnvInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EnvinfoJdbcRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public EnvInfo findById(String id) {
        try {
            return jdbcTemplate.queryForObject("select * from SL_ENVINFO where id= ? ", new Object[] { id },
                    new BeanPropertyRowMapper<EnvInfo>(EnvInfo.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public int deleteById(String id) {
        return jdbcTemplate.update("delete from SL_ENVINFO where id=?", new Object[] { id });
    }

    public int insert(EnvInfo envInfo ) {
        return jdbcTemplate.update("insert into SL_ENVINFO (id, name, code, type, theDate, data) " + "values(?, ?, ?, ?, ?, ?)",
                new Object[] { envInfo .getId(), envInfo .getName(), envInfo .getCode(), envInfo .getType(), envInfo.getThedate(), envInfo.getData() });
    }

    public int update(EnvInfo envInfo ) {
        return jdbcTemplate.update("update SL_ENVINFO  set name = ?, code = ?, type = ?, theDate = ?,  data = ?  where id = ?",
                new Object[] { envInfo .getName(), envInfo .getCode(), envInfo .getType(), envInfo.getThedate(), envInfo.getData(), envInfo .getId() });
    }


    class EnvInfoRowMapper implements RowMapper<EnvInfo> {

        @Override
        public EnvInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
            EnvInfo envInfo = new EnvInfo();
            envInfo.setId(rs.getString("id"));
            envInfo.setName(rs.getString("name"));
            envInfo.setCode(rs.getString("code"));
            envInfo.setType(rs.getString("type"));
            envInfo.setThedate(rs.getTimestamp("theDate"));
            envInfo.setData(rs.getString("data"));
            return envInfo;
        }

    }

    public List<EnvInfo> findAll() {
        return jdbcTemplate.query("select * from SL_ENVINFO", new EnvInfoRowMapper());
    }


}
