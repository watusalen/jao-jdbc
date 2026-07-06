package model.dao.factory;

import db.DataBase;
import model.dao.SellerDAO;
import model.dao.implementation.SellerJDBC;

public class DAOFactory {
    public static SellerDAO createSellerDAO() {
        return new SellerJDBC(DataBase.getConnection());
    }
}