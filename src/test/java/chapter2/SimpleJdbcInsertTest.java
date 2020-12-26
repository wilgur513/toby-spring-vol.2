package chapter2;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

import java.util.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/chapter2/dataSource.xml")
public class SimpleJdbcInsertTest {
    public static class Member{
        int id;
        String name;
        double point;

        public Member() {
        }

        public Member(int id, String name, double point) {
            this.id = id;
            this.name = name;
            this.point = point;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getPoint() {
            return point;
        }

        public void setPoint(double point) {
            this.point = point;
        }
    }
    SimpleJdbcTemplate template;
    SimpleJdbcInsert insert;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        template = new SimpleJdbcTemplate(dataSource);
        insert = new SimpleJdbcInsert(dataSource).withTableName("member");
    }

    @Before
    public void setUp() throws Exception {
        template.update("delete from member");
    }

    @Test
    public void simpleJdbcInsert() {
        assertThat(insert, is(notNullValue()));
    }

    @Test
    public void executeWithMap() {
        Map map = new HashMap();
        map.put("id", 1);
        map.put("name", "spring");
        map.put("point", 1.5);
        insert.execute(map);

        verify(1, "spring", 1.5);
    }

    @Test
    public void executeWithBeanPropertySqlParameterSource() {
        Member member = new Member(1, "spring", 1.5);
        insert.execute(new BeanPropertySqlParameterSource(member));
        verify(1, "spring", 1.5);
    }

    @Test
    public void executeWithMapSqlParameterSource() {
        insert.execute(new MapSqlParameterSource()
                .addValue("id", 1)
                .addValue("name", "spring")
                .addValue("point", 1.5));
        verify(1, "spring", 1.5);
    }

    private void verify(int id, String name, double point) {
        Member member = template.queryForObject("select * from member where id = ?", new BeanPropertyRowMapper<>(Member.class), id);
        assertThat(member.name, is(name));
        assertThat(member.point, is(point));
    }
}
