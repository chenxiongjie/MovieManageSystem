
import dao.ConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author caster
 */
public class Database {
    
    private Connection conn;
    
    public void init () {
        
        try {
            this.conn = ConnectionFactory.getConnection();
            this.createTablesIfIfNotExists();
            ConnectionFactory.close(null, null, this.conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Boolean tableExists(String tableName) throws SQLException {
        ResultSet rs = this.conn.getMetaData().getTables(null, null, tableName, null);
        
        if (rs.next()) {
            return true;
        }
        
        return false;
    }
    
    public void createTablesIfIfNotExists() throws SQLException {
        if (! this.tableExists("movies")) {
            System.out.println("movies 表不存在，正在创建。。。");
            ConnectionFactory.queryReturnBoolean(this.createMoviesTableString());
            System.out.println("movies 表创建完成。");
        }

        if (! this.tableExists("permissions")) {
            System.out.println("permissions 表不存在，正在创建。。。");
            ConnectionFactory.queryReturnBoolean(this.createPermissionsTableString());
            System.out.println("permissions 表创建完成。");
        }
        
        if (! this.tableExists("roles")) {
            System.out.println("roles 表不存在，正在创建。。。");
            ConnectionFactory.queryReturnBoolean(this.createRolesTableString());
            System.out.println("roles 表创建完成。");
        }

        if (! this.tableExists("role_permissions")) {
            System.out.println("role_permissions 表不存在，正在创建。。。");
            ConnectionFactory.queryReturnBoolean(this.createRolePermissionsTableString());
            System.out.println("role_permissions 表创建完成。");
        }

        if (! this.tableExists("rooms")) {
            System.out.println("rooms 表不存在，正在创建。。。");
            ConnectionFactory.queryReturnBoolean(this.createRoomsTableString());
            System.out.println("rooms 表创建完成。");
        }

        if (! this.tableExists("showings")) {
            System.out.println("showings 表不存在，正在创建。。。");
            ConnectionFactory.queryReturnBoolean(this.createShowingsTableString());
            System.out.println("showings 表创建完成。");
        }

        if (! this.tableExists("users")) {
            System.out.println("users 表不存在，正在创建。。。");
            ConnectionFactory.queryReturnBoolean(this.createUsersTableString());
            System.out.println("users 表创建完成。");
        }

        if (! this.tableExists("user_roles")) {
            System.out.println("user_roles 表不存在，正在创建。。。");
            ConnectionFactory.queryReturnBoolean(this.createUserRolesTableString());
            System.out.println("user_roles 表创建完成。");
        }
    }
    
    private String createMoviesTableString () {
        return "CREATE TABLE `movies` (" +
            "`id` int(11) unsigned NOT NULL AUTO_INCREMENT," +
            "`name` varchar(255) NOT NULL UNIQUE COMMENT '电影名'," +
            "`director` varchar(255) NOT NULL COMMENT '导演'," +
            "`starring` varchar(255) NOT NULL COMMENT '主演'," +
            "`cover` varchar(2083) NOT NULL COMMENT '封面'," +
            "`type` varchar(255) NOT NULL COMMENT '类型'," +
            "`price` decimal(9,2) NOT NULL COMMENT '电影票价'," +
            "PRIMARY KEY (`id`)" +
        ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
    }

    private String createPermissionsTableString () {
        return "CREATE TABLE `permissions` (" +
            "`id` int(11) unsigned NOT NULL AUTO_INCREMENT," +
            "`name` varchar(255) NOT NULL UNIQUE COMMENT '权限名'," +
            "PRIMARY KEY (`id`)" +
        ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
    }

    private String createRolePermissionsTableString () {
        return  "CREATE TABLE `role_permissions` (" +
            "`id` int(11) unsigned NOT NULL AUTO_INCREMENT," +
            "`role_id` int(11) unsigned NOT NULL COMMENT '角色 id'," +
            "`permission_id` int(10) unsigned NOT NULL COMMENT '权限 id'," +
            "PRIMARY KEY (`id`)" +
        ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
    }

    private String createRolesTableString () {
        return  "CREATE TABLE `roles` (" +
            "`id` int(11) unsigned NOT NULL AUTO_INCREMENT," +
            "`name` varchar(255) NOT NULL UNIQUE COMMENT '角色名'," +
            "PRIMARY KEY (`id`)" +
        ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
    }

    private String createRoomsTableString () {
        return "CREATE TABLE `rooms` (" +
            "`id` int(11) unsigned NOT NULL AUTO_INCREMENT," +
            "`name` varchar(255) NOT NULL UNIQUE COMMENT '放映厅名字'," +
            "`seats` int(11) unsigned NOT NULL COMMENT '座位数'," +
            "PRIMARY KEY (`id`)" +
        ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
    }

    private String createShowingsTableString () {
        return "CREATE TABLE `showings` (" +
            "`id` int(11) unsigned NOT NULL AUTO_INCREMENT," +
            "`date` timestamp NOT NULL COMMENT '时间'," +
            "`room_id` int(11) unsigned NOT NULL COMMENT '放映厅 id'," +
            "`movie_id` int(11) unsigned NOT NULL COMMENT '电影 id'," +
            "PRIMARY KEY (`id`)" +
        ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
    }

    private String createUserRolesTableString () {
        return "CREATE TABLE `user_roles` (" +
            "`id` int(11) unsigned NOT NULL AUTO_INCREMENT," +
            "`user_id` int(11) unsigned NOT NULL COMMENT '用户 id'," +
            "`role_id` int(11) unsigned NOT NULL COMMENT '角色 id'," +
            "PRIMARY KEY (`id`)" +
        ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
    }

    private String createUsersTableString () {
        return
        "CREATE TABLE `users` (" +
            "`id` int(11) unsigned NOT NULL AUTO_INCREMENT," +
            "`email` varchar(255) NOT NULL UNIQUE COMMENT '邮箱'," +
            "`password` varchar(255) NOT NULL COMMENT '密码'," +
            "`name` varchar(255) NOT NULL UNIQUE COMMENT '用户名'," +
            "`phone` varchar(255) NOT NULL UNIQUE COMMENT '联系方式'," +
            "`address` varchar(255) NOT NULL DEFAULT '' COMMENT '地址'," +
            "PRIMARY KEY (`id`)" +
        ") ENGINE=InnoDB DEFAULT CHARSET=utf8;";
    }
}
