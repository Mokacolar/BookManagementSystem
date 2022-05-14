package com.example.bms.Repository;

import com.example.bms.controller.BookForm;
import com.example.bms.domain.Book;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcBookRepository implements BookRepository {
    private final DataSource dataSource;
    public JdbcBookRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Book> findAll() {
        String sql = "select * from book";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Book> books = new ArrayList<>();
            while(rs.next()) {  //불러올 테이블 컬럼 목록
                Book book = new Book();
                book.setId(rs.getLong("id"));
                book.setName(rs.getString("name"));
                book.setPublisher(rs.getString("publisher"));
                book.setPrice(rs.getInt("price"));
                books.add(book);
            }
            return books;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }

    public Book save (Book book){
        String sql = "insert into book(name,publisher,price) value(?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, book.getName());
            pstmt.setString(2, book.getPublisher());
            pstmt.setInt(3, book.getPrice());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            List<Book> books = new ArrayList<>();
            if(rs.next()) {
                book.setId(rs.getLong(1));
            }
            else{
                throw new SQLException("id 조회 실패");
            }
            return book;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    public List<Book> findCond(BookForm form){

        String sql = "select * from book where 1";
        if(form.getName() != "")
            sql = sql + " AND name like '%" + form.getName() + "%' ";
        if(form.getPublisher() != "")
            sql = sql + " AND publisher like '%" + form.getPublisher() + "%' ";
        if(form.getPrice() != null)
            sql = sql + " AND price >= " + form.getPrice();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Book> books = new ArrayList<>();
            while(rs.next()) {
                Book book = new Book();
                book.setId(rs.getLong("id"));
                book.setName(rs.getString("name"));
                book.setPublisher(rs.getString("publisher"));
                book.setPrice(rs.getInt("price"));
                books.add(book);
            }
            return books;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
}
