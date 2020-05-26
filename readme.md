## 项目结构

```
- src
    - main
        - java
            - dao # 数据访问层（实现数据的增删改查）
            - views # GUI 页面
```



## Dao

### 构建 Dao

```java
class UserDao extends Dao<UserDao> {
  private static String table = "users";
  
  @Column(type = "Number")
  private Number id;
  
  @Column(type = "String")
  private String name;
  
	public UserDao () {
    super(table);
  }
  
  public Number getId() {
    return id;
  }
  
  public void setId(Number id) {
    this.id = id;
  }
  
  public String getName() {
    return name;
  }
  
  public void setName(String name) {
    this.name = name;
  }
}
```



### 使用方法

#### 查

```java
// 获取所有实例
ArrayList<UserDao> users = new UserDao().get();

/**
 * 获取符合条件的实例
 * @method where(String column, String operator, Object value)
 * @method orWhere(String column, String operator, Object value)
 */
ArrayList<UserDao> users = new UserDao()
        .where("name", "=", "Demo")
        .orWhere("id", ">", 1)
        .get();

/**
 * 按顺序获取符合条件的实例
 * @method order(String column, String order)
 */
ArrayList<UserDao> users = new UserDao()
  			.order("id", "desc")
        .get();

/**
 * 获取分页数据
 * @method where(String column, String operator, Object value)
 * @method orWhere(String column, String operator, Object value)
 * @method paginate(int page, int paginate)
 */
ArrayList<UserDao> users = new UserDao()
        .where("name", "=", "Demo")
        .orWhere("id", ">", 1)
        .paginate(1, 10)
  			.get();
```

#### 增、改

```java
UserDao user = new UserDao();
user.setName("Demo");
user.setEmail("demo@example.com");
user.setPassword("password");
user.setPhone("1234567");
user.setAddress("Kyoto");
/**
 * 新增实例
 * @return 若成功返回 Builder， 否则返回 null
 */
user.create();

UserDao user = new UserDao().where("id", "=", 1);
user.setName("Example");
user.setEmail("demo@example.com");
user.setPassword("password");
user.setPhone("1234567");
user.setAddress("Kyoto");
/**
 * 更新实例
 * @return 若成功返回 Builder， 否则返回 null
 */
user.update();

/**
 * 保存实例，当数据不存在时，新增；存在时，更新。
 * @return 若成功返回 Builder， 否则返回 null
 */
user.save();
```



#### 删

```java
UserDao user = (UserDao) new UserDao().first();
/**
 * 删除实例
 * @return Boolean
 */
user.delete();
```

