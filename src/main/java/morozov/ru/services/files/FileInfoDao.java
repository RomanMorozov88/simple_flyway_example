package morozov.ru.services.files;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import morozov.ru.models.FileInfo;

@Repository
public class FileInfoDao {
	
	private final NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	public FileInfoDao(NamedParameterJdbcTemplate jdbcTemplate) {
		super();
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public int save(FileInfo fileInfo) {
		String sql = "insert into file_infos "
				+ "(name, first_file_path, second_file_path) "
				+ "values(:name, :firstFilePath, :secondFilePath);";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("name", fileInfo.getName());
		parameters.addValue("firstFilePath", fileInfo.getFirstFilePath());
		parameters.addValue("secondFilePath", fileInfo.getSecondFilePath());
		GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql, parameters, generatedKeyHolder, new String[] { "id" });
		return generatedKeyHolder.getKey().intValue();
	}
	
	public FileInfo getFileInfoById(int idFileInfo) {
		String sql = "select * from file_infos "
				+ "where id = :idFileInfo;";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("idFileInfo", idFileInfo);
		try {
			return jdbcTemplate.queryForObject(
					sql, 
					parameters,
					(rs, rowNum) -> new FileInfo(
							rs.getInt("id"), 
							rs.getString("name"),
							rs.getString("first_file_path"),
							rs.getString("second_file_path")
							)
					);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	List<FileInfo> getFileInfos() {
		String sql = "select * from file_infos;";
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		return jdbcTemplate.query(
				sql, 
				parameters, 
				(rs, rowNum) -> new FileInfo(
						rs.getInt("id"), 
						rs.getString("name"),
						rs.getString("first_file_path"),
						rs.getString("second_file_path")
						)
				);
	}
	
	void deleteFileInfo(int idFileInfo) {
		String sql = "delete file_infos " 
				+ "where id = :id;";
	MapSqlParameterSource parameters = new MapSqlParameterSource();
	parameters.addValue("id", idFileInfo);
	jdbcTemplate.update(sql, parameters);
	}

}
