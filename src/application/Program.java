package application;

import model.dao.SellerDAO;
import model.dao.factory.DAOFactory;
import model.entities.Seller;

public class Program {
    public static void main(String[] args) {

        SellerDAO sellerDAO = DAOFactory.createSellerDAO();

        Seller seller = sellerDAO.findById(3);

        System.out.println(seller);
    }
}