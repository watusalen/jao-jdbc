package application;

import model.dao.SellerDAO;
import model.dao.factory.DAOFactory;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {

        SellerDAO sellerDAO = DAOFactory.createSellerDAO();

        System.out.println("TESTE 1: Seller sellerDAOfinById()");
        Seller seller = sellerDAO.findById(3);
        System.out.println(seller + "\n");

        System.out.println("TESTE 2: Seller sellerDAOfinByDepartment()");
        List<Seller> list = sellerDAO.findByDepartment(new Department(2, null));
        for (Seller s : list) {
            System.out.println(s);
        }
        System.out.println();

        System.out.println("TESTE 3: Seller sellerDAOfindAll()");
        list = sellerDAO.findAll();
        for (Seller s : list) {
            System.out.println(s);
        }
        System.out.println();

        System.out.println("TESTE 4: Seller sellerDAOinsert()");
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, new Department(2, null));
        sellerDAO.insert(newSeller);
        if (newSeller.getId() != null) {
            System.out.println("Inserted! New id = " + newSeller.getId());
        }
        System.out.println();

        System.out.println("TESTE 5: Seller sellerDAOupdate()");
        seller = sellerDAO.findById(1);
        System.out.print("Before update: " + seller + ". After update: ");
        seller.setName("Martha Uaine");
        sellerDAO.update(seller);
        System.out.println(seller);
        System.out.println();

        System.out.println("TESTE 6: Seller sellerDAO.delete()");
        System.out.println("Enter id for delete test:");
        Scanner sc = new Scanner(System.in);
        int id = sc.nextInt();
        sellerDAO.deleteById(id);
        System.out.println("Delete completed.");
    }
}