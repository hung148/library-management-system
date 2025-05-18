package com.main.respository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.main.entity.Admin;
import com.main.entity.Member;

public class LibraryDAO {
    
    // add, remove, get, update admin
    public static void addAdmin(String email, String password, String username, String name, double balance) {
        String sql = "INSERT INTO users (email, password, username, name, type, status, balance) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try(Connection conn = DBInitiailzer.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, username);
            pstmt.setString(4, name);
            pstmt.setString(5, "admin");
            pstmt.setString(6, "normal");
            pstmt.setDouble(7, balance);
            pstmt.executeUpdate();
            System.out.println("Admin added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Admin getAdminById(int id) {
        String sql = "SELECT * FROM users WHERE id = ? AND type = 'admin'";
        try (Connection conn = DBInitiailzer.connect(); 
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setEmail(rs.getString("email"));
                admin.setUsername(rs.getString("username"));
                admin.setName(rs.getString("name"));
                admin.setPassword(rs.getString("password"));
                admin.setType(rs.getString("type"));
                admin.setStatus(rs.getString("status"));
                admin.setBalance(rs.getDouble("balance"));
                return admin;
            } else {
                System.out.println("admin not found");
            } 
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Admin getAdminByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ? AND type = 'admin'";
        try (Connection conn = DBInitiailzer.connect(); 
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setEmail(rs.getString("email"));
                admin.setUsername(rs.getString("username"));
                admin.setName(rs.getString("name"));
                admin.setPassword(rs.getString("password"));
                admin.setType(rs.getString("type"));
                admin.setStatus(rs.getString("status"));
                admin.setBalance(rs.getDouble("balance"));
                return admin;
            } else {
                System.out.println("admin not found");
            } 
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Admin[] getAdminList() {
        String sql = "SELECT * FROM users WHERE type = 'admin'";
        List<Admin> adminList = new ArrayList<>();
        
        try (Connection conn = DBInitiailzer.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Admin admin = new Admin();
                admin.setId(rs.getInt("id"));
                admin.setEmail(rs.getString("email"));
                admin.setUsername(rs.getString("username"));
                admin.setName(rs.getString("name"));
                admin.setPassword(rs.getString("password"));
                admin.setType(rs.getString("type"));
                admin.setStatus(rs.getString("status"));
                admin.setBalance(rs.getDouble("balance"));
                adminList.add(admin);
            }
            System.out.println("Retrieve admin list sucessfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminList.toArray(new Admin[0]);
    }

    public static void updateAdmin(int id, Admin admin) {
        String sql = "UPDATE users SET email = ?, password = ?, username = ?, name = ?, type = ?, status = ?, balance = ? WHERE id = ? AND type = 'admin'";

        try (Connection conn = DBInitiailzer.connect(); 
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, admin.getEmail());
                pstmt.setString(2, admin.getPassword());
                pstmt.setString(3, admin.getUsername());
                pstmt.setString(4, admin.getName());
                pstmt.setString(5, admin.getType());
                pstmt.setString(6, admin.getStatus());
                pstmt.setInt(7, admin.getId());
                pstmt.executeUpdate();
                System.out.println("Update admin successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateAdmin(String email, Admin admin) {
        String sql = "UPDATE users SET email = ?, password = ?, username = ?, name = ?, type = ?, status = ?, balance = ? WHERE email = ? AND type = 'admin'";

        try (Connection conn = DBInitiailzer.connect(); 
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, admin.getEmail());
                pstmt.setString(2, admin.getPassword());
                pstmt.setString(3, admin.getUsername());
                pstmt.setString(4, admin.getName());
                pstmt.setString(5, admin.getType());
                pstmt.setString(6, admin.getStatus());
                pstmt.setString(7, email);
                pstmt.executeUpdate();
                System.out.println("Update admin successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeAdmin(int id) {
        String sql = "DELETE FROM users WHERE id = ? AND type = 'admin'";
        try (Connection conn = DBInitiailzer.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Admin is removed");
            } else {
                System.out.println("No admin found with id " + id + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeAdmin(String email) {
        String sql = "DELETE FROM users WHERE email = ? AND type = 'admin'";
        try (Connection conn = DBInitiailzer.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Admin is removed");
            } else {
                System.out.println("No admin found with email " + email + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // add, remove, get, update member
    public static void addMember(String email, String password, String username, String name, double balance) {
        String sql = "INSERT INTO users (email, password, username, name, type, status, balance) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try(Connection conn = DBInitiailzer.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, password);
            pstmt.setString(3, username);
            pstmt.setString(4, name);
            pstmt.setString(5, "member");
            pstmt.setString(6, "normal");
            pstmt.setDouble(7, balance);
            pstmt.executeUpdate();
            System.out.println("Member added successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Member getMemberById(int id) {
        String sql = "SELECT * FROM users WHERE id = ? AND type = 'member'";
        try (Connection conn = DBInitiailzer.connect(); 
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setEmail(rs.getString("email"));
                member.setUsername(rs.getString("username"));
                member.setName(rs.getString("name"));
                member.setPassword(rs.getString("password"));
                member.setType(rs.getString("type"));
                member.setStatus(rs.getString("status"));
                member.setBalance(rs.getDouble("balance"));
                return member;
            } else {
                System.out.println("member not found");
            } 
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Member getMemberByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ? AND type = 'member'";
        try (Connection conn = DBInitiailzer.connect(); 
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setEmail(rs.getString("email"));
                member.setUsername(rs.getString("username"));
                member.setName(rs.getString("name"));
                member.setPassword(rs.getString("password"));
                member.setType(rs.getString("type"));
                member.setStatus(rs.getString("status"));
                member.setBalance(rs.getDouble("balance"));
                return member;
            } else {
                System.out.println("member not found");
            } 
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Member[] getMemberList() {
        String sql = "SELECT * FROM users WHERE type = 'member'";
        List<Member> memberList = new ArrayList<>();
        
        try (Connection conn = DBInitiailzer.connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Member member = new Member();
                member.setId(rs.getInt("id"));
                member.setEmail(rs.getString("email"));
                member.setUsername(rs.getString("username"));
                member.setName(rs.getString("name"));
                member.setPassword(rs.getString("password"));
                member.setType(rs.getString("type"));
                member.setStatus(rs.getString("status"));
                member.setBalance(rs.getDouble("balance"));
                memberList.add(member);
            }
            System.out.println("Retrieve meber list sucessfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return memberList.toArray(new Member[0]);
    }

    public static void updateMember(int id, Member member) {
        String sql = "UPDATE users SET email = ?, password = ?, username = ?, name = ?, type = ?, status = ?, balance = ? WHERE id = ? AND type = 'member'";

        try (Connection conn = DBInitiailzer.connect(); 
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, member.getEmail());
                pstmt.setString(2, member.getPassword());
                pstmt.setString(3, member.getUsername());
                pstmt.setString(4, member.getName());
                pstmt.setString(5, member.getType());
                pstmt.setString(6, member.getStatus());
                pstmt.setInt(7, member.getId());
                pstmt.executeUpdate();
                System.out.println("Update member successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateMember(String email, Member member) {
        String sql = "UPDATE users SET email = ?, password = ?, username = ?, name = ?, type = ?, status = ?, balance = ? WHERE email = ? AND type = 'member'";

        try (Connection conn = DBInitiailzer.connect(); 
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, member.getEmail());
                pstmt.setString(2, member.getPassword());
                pstmt.setString(3, member.getUsername());
                pstmt.setString(4, member.getName());
                pstmt.setString(5, member.getType());
                pstmt.setString(6, member.getStatus());
                pstmt.setString(7, email);
                pstmt.executeUpdate();
                System.out.println("Update member successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeMember(int id) {
        String sql = "DELETE FROM users WHERE id = ? AND type = 'member'";
        try (Connection conn = DBInitiailzer.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Member is removed");
            } else {
                System.out.println("No member found with id " + id + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void removeMember(String email) {
        String sql = "DELETE FROM users WHERE email = ? AND type = 'member'";
        try (Connection conn = DBInitiailzer.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Member is removed");
            } else {
                System.out.println("No member found with email " + email + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // add, remove, get, update book
}
