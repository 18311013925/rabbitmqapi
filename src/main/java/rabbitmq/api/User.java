package rabbitmq.api;

/**
 * @Auther: lizhi
 * @Date: 2018/9/21 16:33
 * @Description:
 */
public class User {
    private int id;
    private String name;
    private String passwd;
    private String sex;
    private String address;

    public User() {
    }

    public User(User target) {
        this.id = target.id;
        this.name = target.name;
        this.passwd = target.passwd;
        this.sex = target.sex;
        this.address = target.address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPasswd() {
        return passwd;
    }

    public String getSex() {
        return sex;
    }

    public String getAddress() {
        return address;
    }

    public static class Builder {
        private User target;

        public Builder() {
            target = new User();
        }

        public Builder name(String name) {
            target.name = name;
            return this;
        }

        public Builder passwd(String passwd) {
            target.passwd = passwd;
            return this;
        }

        public Builder sex(String sex) {
            target.sex = sex;
            return this;
        }

        public Builder address(String address) {
            target.address = address;
            return this;
        }

        public User build() {
            return new User(target);
        }

    }
}
