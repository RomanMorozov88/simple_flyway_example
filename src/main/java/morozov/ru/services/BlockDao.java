package morozov.ru.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import morozov.ru.models.Block;
import morozov.ru.models.LocalDateBlock;
import morozov.ru.models.TextBlock;

@Repository
public class BlockDao {
	
	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public BlockDao(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public Integer saveBlock(Block block) {
		String sql = "insert into blocks (id_pack, name, type_code) "
				+ "values(:idPack, :name, :typeCode);";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("idPack", block.getIdPack());
		parameters.addValue("name", block.getName());
		parameters.addValue("typeCode", block.getTypeCode());
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql, parameters, generatedKeyHolder, new String[] { "id" });
		return generatedKeyHolder.getKey().intValue();
	}
	
	public void updateBlock(Block block) {
		String sql = "update blocks "
				+ "set name = :name "
				+ "where id = :idBlock;";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("idBlock", block.getId());
		parameters.addValue("name", block.getName());
		jdbcTemplate.update(sql, parameters);
	}

	public void deleteBlock(int idBlock) {
		String sql = "delete from blocks "
				+ "where id = :idBlock;";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("idBlock", idBlock);
		jdbcTemplate.update(sql, parameters);
	}

	public void saveTextBlock(TextBlock block) {
		String sql = "insert into text_blocks (id, text) "
				+ "values(:idBlock, :text);";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("idBlock", block.getId());
		parameters.addValue("text", block.getText());
		jdbcTemplate.update(sql, parameters);
	}
	
	public void updateTextBlock(TextBlock block) {
		String sql = "update text_blocks "
				+ "set text = :text "
				+ "where id = :idBlock;";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("idBlock", block.getId());
		parameters.addValue("text", block.getText());
		jdbcTemplate.update(sql, parameters);
	}
	
	public void saveLocalDateBlock(LocalDateBlock block) {
		String sql = "insert into local_date_blocks "
				+ "(id, first_date, second_date) "
				+ "values(:idBlock, :fDate, :sDate);";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("idBlock", block.getId());
		parameters.addValue("fDate", block.getFirstDate());
		parameters.addValue("sDate", block.getSecondDate());
		jdbcTemplate.update(sql, parameters);
	}
	
	public void updateLocalDateBlock(LocalDateBlock block) {
		String sql = "update local_date_blocks " 
					+ "set first_date = :fDate, " 
					+ "second_date = :sDate "
					+ "where id = :idBlock;";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("idBlock", block.getId());
		parameters.addValue("fDate", block.getFirstDate());
		parameters.addValue("sDate", block.getSecondDate());
		jdbcTemplate.update(sql, parameters);
	}
	
	public List<Map<String, Object>> getBlocksByIdPack(int idPack) {
        String sql = "select b.id, b.id_pack, b.name, b.type_code, "
        		+ "tb.text, ldb.first_date, ldb.second_date "
        		+ "from blocks as b "
        		+ "left join text_blocks as tb on tb.id = b.id "
        		+ "left join local_date_blocks as ldb on ldb.id = b.id "
        		+ "where b.id_pack = :idPack;";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("idPack", idPack);
        return jdbcTemplate.queryForList(sql, parameters);
    }

}
