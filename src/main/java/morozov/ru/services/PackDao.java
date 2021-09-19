package morozov.ru.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import morozov.ru.models.Pack;

@Repository
public class PackDao {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public PackDao(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public Integer savePack(Pack pack) {
		String sql = "insert into packs (name) values(:name);";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("name", pack.getName());
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql, parameters, generatedKeyHolder, new String[] { "id" });
		return generatedKeyHolder.getKey().intValue();
	}

	public void updatePack(Pack pack) {
		String sql = "update packs " + "set name = :name " + "where id = :idPack;";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("idPack", pack.getId());
		parameters.addValue("name", pack.getName());
		jdbcTemplate.update(sql, parameters);
	}

	public void deletePack(int idPack) {
		String sql = "delete from packs " 
					+ "where id = :idPack;";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("idPack", idPack);
		jdbcTemplate.update(sql, parameters);
	}

	public Pack getPackById(int idPack) {
		String sql = "select * from packs " 
					+ "where id = :idPack;";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("idPack", idPack);
		try {
			return jdbcTemplate.queryForObject(
					sql, 
					parameters,
					(rs, rowNum) -> new Pack(rs.getInt("id"), rs.getString("name"))
					);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	public List<Pack> getPacks() {
		String sql = "select * from packs;";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		return jdbcTemplate.query(
				sql, 
				parameters, 
				(rs, rowNum) -> new Pack(rs.getInt("id"), rs.getString("name"))
				);
	}

}
