package application;

import model.dao.SellerDAO;
import model.dao.factory.DAOFactory;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public class Program {
    public static void main(String[] args) {

        SellerDAO sellerDAO = DAOFactory.createSellerDAO();

        System.out.println("TESTE 1: Seller obj.finById()");
        Seller seller = sellerDAO.findById(3);
        System.out.println(seller + "\n");

        System.out.println("TESTE 2: Seller obj.finByDepartment()");
        List<Seller> list = sellerDAO.findByDepartment(new Department(2, null));
        for(Seller s : list){
            System.out.println(s);
        }
        System.out.println();

        System.out.println("TESTE 3: Seller obj.findAll()");
        list = sellerDAO.findAll();
        for(Seller s : list){
            System.out.println(s);
        }
        System.out.println();


    }
}