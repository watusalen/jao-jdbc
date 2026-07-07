package model.dao.factory;

import db.DataBase;
import model.dao.DepartmentDAO;
import model.dao.SellerDAO;
import model.dao.implementation.DepartmentJDBC;
import model.dao.implementation.SellerJDBC;

public class DAOFactory {
    public static SellerDAO createSellerDAO() {
        return new SellerJDBC(DataBase.getConnection());
    }

    public static DepartmentDAO createDepartmentDAO() {
        return new DepartmentJDBC(DataBase.getConnection());
    }
}