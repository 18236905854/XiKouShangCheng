package com.xk.mall.model.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

@Entity(indexes = {
        @Index(value = "name,age, id DESC", unique = true)
})
public class UserBean {
    @NotNull
    private int id;
    @NotNull
    private String name;
    @NotNull
    private int age;
@Generated(hash = 643440480)
public UserBean(int id, @NotNull String name, int age) {
    this.id = id;
    this.name = name;
    this.age = age;
}

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Generated(hash = 1203313951)
public UserBean() {
}
public int getId() {
    return this.id;
}
public void setId(int id) {
    this.id = id;
}
public String getName() {
    return this.name;
}
public void setName(String name) {
    this.name = name;
}
public int getAge() {
    return this.age;
}
public void setAge(int age) {
    this.age = age;
}
}
