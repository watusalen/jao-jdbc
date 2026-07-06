package model.dao.factory;

import model.dao.SellerDAO;
import model.dao.implementation.SellerJDBC;

public class DAOFactory {
    public static SellerDAO createSellerDAO() {
        return new SellerJDBC();
    }
}