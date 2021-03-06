package vatsuvaksi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import vatsuvaksi.domainClasses.contact;
import vatsuvaksi.rowMapper.contactRowMapper;

public class contactDAOimplementation extends BaseDAO implements ContactDAO {
	public void save(contact c) {
        String sql = "INSERT INTO contact(idUser, name, phone, email, address, remark) VALUES(:idUser, :name, :phone, :email, :address, :remark)";
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("idUser", c.getIdUser());
        map.put("name", c.getName());
        map.put("phone", c.getPhone());
        map.put("email", c.getEmail());
        map.put("address", c.getAddress());
        map.put("remark", c.getRemark());
        SqlParameterSource ps = new MapSqlParameterSource(map); // This maps the value of the string to the query where : is placed 
        KeyHolder kh = new GeneratedKeyHolder(); // This binds the value which is autoIncremented and generated by the Databases ;
        getNamedParameterJdbcTemplate().update(sql, ps, kh);
        c.setContactId(kh.getKey().intValue());
    }

	public void update(contact c) {
        String sql = "UPDATE contact SET name=:name, phone=:phone, email=:email, address=:address, remark=:remark WHERE contactId=:contactId";
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("contactId", c.getContactId());
        map.put("name", c.getName());
        map.put("phone", c.getPhone());
        map.put("email", c.getEmail());
        map.put("address", c.getAddress());
        map.put("remark", c.getRemark());
        getNamedParameterJdbcTemplate().update(sql, map);
    }


	public void delete(contact c) {
		
	  @SuppressWarnings("deprecation")
	Integer i=new Integer(c.getContactId());
	  deleteById(i);
	}

	public void deleteById(Integer contactId) {
        String sql = "DELETE FROM contact WHERE contactId=?";
        getJdbcTemplate().update(sql, contactId);
    }


	public contact findById(Integer contactId) {
        String sql = "SELECT contactId, idUser, name, phone, email, address, remark FROM contact WHERE contactId=?";
        return getJdbcTemplate().queryForObject(sql, new contactRowMapper(), contactId);
    }

	public List<contact> findAll() {
        String sql = "SELECT contactId, idUser, name, phone, email, address, remark FROM contact";
        return getJdbcTemplate().query(sql, new contactRowMapper());
    }

	public List<contact> findByProperty(String propName, Object propValue) {
        String sql = "SELECT contactId, idUser, name, phone, email, address, remark FROM contact WHERE "+propName+"=?";
        return getJdbcTemplate().query(sql, new contactRowMapper(), propValue);
    }

}
