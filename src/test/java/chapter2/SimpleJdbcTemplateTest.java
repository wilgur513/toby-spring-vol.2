package chapter2;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/chapter2/dataSource.xml")
public class SimpleJdbcTemplateTest {
    static class Member{
        int id;
        String name;
        double point;

        public Member(){

        }

        public Member(int id, String name, double point) {
            this.id = id;
            this.name = name;
            this.point = point;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setPoint(double point) {
            this.point = point;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public double getPoint() {
            return point;
        }
    }

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
