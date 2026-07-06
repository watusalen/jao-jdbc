package model.dao.implementation;

import db.DataBase;
import db.DataBaseException;
import model.dao.SellerDAO;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerJDBC implements SellerDAO {

    Connection connection;

    public SellerJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller seller) {
        PreparedStatement pst = null;

        try {
            pst = connection.prepareStatement(
                    "INSERT INTO seller "
                            + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
                            + "VALUES "
                            + "(?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            pst.setString(1, seller.getName());
            pst.setString(2, seller.getEmail());
            pst.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            pst.setDouble(4, seller.getBaseSalary());
            pst.setInt(5, seller.getDepartment().getId());

            int rows = pst.executeUpdate();

            if (rows > 0) {
                ResultSet rs = pst.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    seller.setId(id);
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
    public void update(Seller seller) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = connection.prepareStatement(
                    "SELECT seller.*,department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE seller.Id = ?"
            );

            pst.setInt(1, id);

            rs = pst.executeQuery();

            if (rs.next()) {
                Department department = instantiateDepartment(rs);
                return instantiateSeller(rs, department);
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
    public List<Seller> findAll() {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = connection.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "ORDER BY Name"
            );

            rs = pst.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller = instantiateSeller(rs, dep);
                list.add(seller);
            }

            return list;
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } finally {
            DataBase.closeStatement(pst);
            DataBase.closeResultSet(rs);
        }
    }

    public List<Seller> findByDepartment(Department department) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = connection.prepareStatement(
                    "SELECT seller.*, department.Name as DepName "
                            + "FROM seller INNER JOIN department "
                            + "ON seller.DepartmentId = department.Id "
                            + "WHERE seller.DepartmentId = ? "
                            + "ORDER BY Name"
            );

            pst.setInt(1, department.getId());

            rs = pst.executeQuery();

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            while (rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));

                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    map.put(rs.getInt("DepartmentId"), dep);
                }

                Seller seller = instantiateSeller(rs, dep);
                list.add(seller);
            }

            return list;
        } catch (SQLException e) {
            throw new DataBaseException(e.getMessage());
        } finally {
            DataBase.closeStatement(pst);
            DataBase.closeResultSet(rs);
        }
    }

    private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setEmail(rs.getString("Email"));
        seller.setBaseSalary(rs.getDouble("BaseSalary"));
        seller.setBirthDate(rs.getDate("BirthDate"));
        seller.setDepartment(department);
        return seller;
    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setId(rs.getInt("DepartmentId"));
        department.setName(rs.getString("DepName"));
        return department;
    }
}