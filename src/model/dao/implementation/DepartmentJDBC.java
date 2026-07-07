package model.dao.implementation;

import db.DataBase;
import db.DataBaseException;
import db.DataBaseIntegrityException;
import model.dao.DepartmentDAO;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentJDBC implements DepartmentDAO {
    Connection connection;

    public DepartmentJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Department department) {
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(
                    "INSERT INTO department " +
                    "(Name) " +
                    "VALUES " +
                    "(?)",
                    Statement.RETURN_GENERATED_KEYS
            );
            pst.setString(1, department.getName());
            int rows = pst.executeUpdate();
            if (rows > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    department.setId(id);
                }
                DataBase.closeResultSet(rs);
            } else {
                throw new DataBaseException("Unexpected error! No rows affected.");
            }
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } finally {
            DataBase.closeStatement(pst);
        }
    }

    @Override
    public void update(Department department) {
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(
                    "UPDATE department " +
                    "SET Name = ? " +
                    "WHERE Id = ?",
                    Statement.RETURN_GENERATED_KEYS
            );
            pst.setString(1, department.getName());
            pst.setInt(2, department.getId());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } finally {
            DataBase.closeStatement(pst);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement(
                    "DELETE FROM department " +
                    "WHERE Id = ?"
            );
            pst.setInt(1, id);
            int rows = pst.executeUpdate();
            if (rows < 1) {
                throw new DataBaseException("ID not found!");
            }
        } catch (SQLException e) {
            throw new DataBaseIntegrityException(e.getMessage());
        } finally {
            DataBase.closeStatement(pst);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = connection.prepareStatement(
                    "SELECT * " +
                    "FROM department " +
                    "WHERE department.Id = ?"
            );
            pst.setInt(1, id);
            rs = pst.executeQuery();
            if (rs.next()) {
                return instantiateDepartment(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } finally {
            DataBase.closeStatement(pst);
            DataBase.closeResultSet(rs);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = connection.prepareStatement(
                    "SELECT * " +
                    "FROM department " +
                    "ORDER BY Name"
            );
            rs = pst.executeQuery();
            List<Department> list = new ArrayList<>();
            while (rs.next()) {
                list.add(instantiateDepartment(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } finally {
            DataBase.closeStatement(pst);
            DataBase.closeResultSet(rs);
        }
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        return new Department(rs.getInt("Id"), rs.getString("Name"));
    }
}