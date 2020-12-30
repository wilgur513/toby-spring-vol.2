package chapter2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.IncorrectResultSetColumnCountException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/chapter2/dataSource.xml")
public class SimpleJdbcTemplateTest {
    @Autowired
    DataSource dataSource;
    SimpleJdbcTemplate template;

    @Before
    public void setUp() throws Exception {
        template = new SimpleJdbcTemplate(dataSource);
        template.update("delete from member");
    }

    @Test
    public void simpleJdbcTemplate(){
        assertThat(template, is(notNullValue()));
    }

    @Test
    public void updateUsingVarargs() {
        int result = template.update("insert into member(id, name, point) values(?, ?, ?)", 1, "spring", 1.5);
        assertThat(result, is(1));
        verify();
    }

    @Test
    public void updateUsingMap() {
        Map map = new HashMap();
        map.put("id", 1);
        map.put("name", "spring");
        map.put("point", 1.5);
        int result = template.update("insert into member(id, name, point) values(:id, :name, :point)", map);
        assertThat(result, is(1));
        verify();
    }

    @Test
    public void updateUsingSqlParameterSource() {
        Member member = new Member(1, "spring", 1.5);
        int result = template.update("insert into member(id, name, point) values(:id, :name, :point)",
                new BeanPropertySqlParameterSource(member));
        assertThat(result, is(1));
        verify();
    }

    @Test
    public void updateUsingMapSqlParameterSource() {
        int result = template.update("insert into member(id, name, point) values(:id, :name, :point)",
                new MapSqlParameterSource()
                        .addValue("id", 1)
                        .addValue("name", "spring")
                        .addValue("point", 1.5));
        assertThat(result, is(1));
        verify();
    }

    @Test
    public void queryForInt() {
        insert(1, "spring", 1.5);
        insert(2, "framework", 2.5);

        int count = template.queryForInt("select count(*) from member");
        assertThat(count, is(2));

        int id = template.queryForInt("select id from member where name=?", "spring");
        assertThat(id, is(1));

        id = template.queryForInt("select id from member where name = :name",
                new MapSqlParameterSource().addValue("name", "framework"));
        assertThat(id, is(2));

        Member member = new Member(1, "spring", 1.5);
        id = template.queryForInt("select id from member where name = :name",
                new BeanPropertySqlParameterSource(member));
        assertThat(id, is(1));
    }

    @Test(expected = IncorrectResultSetColumnCountException.class)
    public void queryForIntMultiColumn() {
        insert(1, "spring", 1.5);
        template.queryForInt("select id, count(*) from member where id='1'");
    }

    @Test
    public void queryForObject() {
        insert(1, "spring", 1.5);

        Map map = new HashMap();
        map.put("id", 1);
        String name = template.queryForObject("select name from member where id = :id", String.class, map);
        assertThat(name, is("spring"));
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void queryForObjectEmptyResult() {
        template.queryForObject("select name from member where id='1'", String.class);
    }

    @Test(expected = IncorrectResultSetColumnCountException.class)
    public void queryForObjectMultiColumn() {
        insert(1, "spring", 1.5);
        template.queryForObject("select name, id from member where id = '1'", String.class);
    }

    @Test
    public void queryForObjectWithRowMapper() {
        insert(1, "spring", 1.5);
        Member member = template.queryForObject("select * from member where id='1'", new RowMapper<Member>() {
            @Override
            public Member mapRow(ResultSet resultSet, int i) throws SQLException {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                double point = resultSet.getDouble("point");

                return new Member(id, name, point);
            }
        });

        assertThat(member.id, is(1));
        assertThat(member.name, is("spring"));
        assertThat(member.point, is(1.5));
    }

    @Test
    public void queryForObjectWithBeanPropertyRowMapper() {
        insert(1, "spring", 1.5);
        Member member = template.queryForObject("select * from member where id ='1'",
                new BeanPropertyRowMapper<>(Member.class));

        assertThat(member.id, is(1));
        assertThat(member.name, is("spring"));
        assertThat(member.point, is(1.5));
    }

    @Test(expected = IncorrectResultSizeDataAccessException.class)
    public void queryForObjectMultiEntity() {
        insert(1, "spring", 1.5);
        insert(2, "framework", 2.5);
        template.queryForObject("select * from member", new BeanPropertyRowMapper<>(Member.class));
    }

    @Test
    public void query() {
        List<Member> list = template.query("select * from member", new BeanPropertyRowMapper<>(Member.class));
        assertThat(list.size(), is(0));

        insert(1, "spring", 1.5);
        insert(2, "framework", 2.5);
        list = template.query("select * from member", new BeanPropertyRowMapper<>(Member.class));
        assertThat(list.size(), is(2));

    }

    @Test
    public void queryForMap() {
        insert(1, "spring", 1.5);
        Map<String, Object> map = template.queryForMap("select * from member where id = '1'");
        assertThat(map.get("id"), is(1));
        assertThat(map.get("name"), is("spring"));
        assertThat(map.get("point"), is(1.5));
    }

    @Test(expected = IncorrectResultSizeDataAccessException.class)
    public void queryForMapMultiColumn() {
        insert(1, "spring", 1.5);
        insert(2, "framework", 2.5);
        template.queryForMap("select * from member");
    }

    @Test
    public void queryForList() {
        List<Map<String, Object>> list = template.queryForList("select * from member");
        assertThat(list.size(), is(0));

        insert(1, "spring", 1.5);
        insert(2, "framework", 2.5);
        list = template.queryForList("select * from member");

        assertThat(list.size(), is(2));
        assertThat(list.get(0).get("id"), is(1));
        assertThat(list.get(0).get("name"), is("spring"));
        assertThat(list.get(0).get("point"), is(1.5));

        assertThat(list.get(1).get("id"), is(2));
        assertThat(list.get(1).get("name"), is("framework"));
        assertThat(list.get(1).get("point"), is(2.5));
    }

    @Test
    public void batchUpdateWithMap() {
        Map map1 = new HashMap();
        Map map2 = new HashMap();

        map1.put("id", 1);
        map1.put("name", "spring");
        map1.put("point", 1.5);

        map2.put("id", 2);
        map2.put("name", "framework");
        map2.put("point", 2.5);

        template.batchUpdate("insert into member(id, name, point) values(:id, :name, :point)", new Map[]{map1, map2});

        List<Member> list = template.query("select * from member", new BeanPropertyRowMapper<>(Member.class));
        assertThat(list.size(), is(2));

        assertThat(list.get(0).id, is(1));
        assertThat(list.get(0).name, is("spring"));
        assertThat(list.get(0).point, is(1.5));

        assertThat(list.get(1).id, is(2));
        assertThat(list.get(1).name, is("framework"));
        assertThat(list.get(1).point, is(2.5));
    }

    @Test
    public void updateBatchWithSqlParameterSource() {
        template.batchUpdate("insert into member(id, name, point) values(:id, :name, :point)",
                new SqlParameterSource[]{
                        new MapSqlParameterSource()
                                .addValue("id", 1)
                                .addValue("name", "spring")
                                .addValue("point", 1.5),
                        new BeanPropertySqlParameterSource(new Member(2, "framework", 2.5))
                });

        List<Member> list = template.query("select * from member", new BeanPropertyRowMapper<>(Member.class));
        assertThat(list.size(), is(2));

        assertThat(list.get(0).id, is(1));
        assertThat(list.get(0).name, is("spring"));
        assertThat(list.get(0).point, is(1.5));

        assertThat(list.get(1).id, is(2));
        assertThat(list.get(1).name, is("framework"));
        assertThat(list.get(1).point, is(2.5));
    }

    @Test
    public void updateBatchWithListArgs() {
        List<Object[]> args = new ArrayList<>();
        args.add(new Object[]{1, "spring", 1.5});
        args.add(new Object[]{2, "framework", 2.5});

        template.batchUpdate("insert into member(id, name, point) values(?, ?, ?)", args);

        List<Member> list = template.query("select * from member", new BeanPropertyRowMapper<>(Member.class));
        assertThat(list.size(), is(2));

        assertThat(list.get(0).id, is(1));
        assertThat(list.get(0).name, is("spring"));
        assertThat(list.get(0).point, is(1.5));

        assertThat(list.get(1).id, is(2));
        assertThat(list.get(1).name, is("framework"));
        assertThat(list.get(1).point, is(2.5));
    }

    private void insert(int id, String name, double point){
        template.update("insert into member(id, name, point) values(?, ?, ?)", id, name, point);
    }

    private void verify(){
        Member member = template.queryForObject("select * from member", (ResultSet rs, int i) -> {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double point = rs.getDouble("point");

            return new Member(id, name, point);
        });

        assertThat(member.id, is(1));
        assertThat(member.name, is("spring"));
        assertThat(member.point, is(1.5));
    }
}
