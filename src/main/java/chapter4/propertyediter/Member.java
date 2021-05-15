package chapter4.propertyediter;

public class Member {
    public int id;
    public int age;

    public void setId(int id) {
        this.id = id;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", age=" + age +
                '}';
    }
}