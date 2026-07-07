package application;

import model.dao.DepartmentDAO;
import model.dao.SellerDAO;
import model.dao.factory.DAOFactory;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        SellerDAO sellerDAO = DAOFactory.createSellerDAO();

        System.out.println("TESTE 1: sellerDAO sellerDAO.finById()");
        Seller seller = sellerDAO.findById(3);
        System.out.println(seller + "\n");

        System.out.println("TESTE 2: sellerDAO sellerDAOfinByDepartment()");
        List<Seller> list = sellerDAO.findByDepartment(new Department(2, null));
        for (Seller s : list) {
            System.out.println(s);
        }
        System.out.println();

        System.out.println("TESTE 3: sellerDAO sellerDAO.findAll()");
        list = sellerDAO.findAll();
        for (Seller s : list) {
            System.out.println(s);
        }
        System.out.println();

        System.out.println("TESTE 4: sellerDAO sellerDAO.insert()");
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, new Department(2, null));
        sellerDAO.insert(newSeller);
        if (newSeller.getId() != null) {
            System.out.println("Inserted! New id = " + newSeller.getId());
        }
        System.out.println();

        System.out.println("TESTE 5: sellerDAO sellerDAO.update()");
        seller = sellerDAO.findById(1);
        System.out.print("Before update: " + seller + ". After update: ");
        seller.setName("Martha Uaine");
        sellerDAO.update(seller);
        System.out.println(seller);
        System.out.println();

        System.out.println("TESTE 6: sellerDAO sellerDAO.delete()");
        System.out.println("Enter id for delete test:");
        int id = sc.nextInt();
        sellerDAO.deleteById(id);
        System.out.println("Delete completed.");

        DepartmentDAO departmentDAO = DAOFactory.createDepartmentDAO();

        System.out.println("TESTE 1: departmentDAO departmentDAO.finById()");
        Department department = departmentDAO.findById(2);
        System.out.println(department + "\n");

        System.out.println("TESTE 2: departmentDAO departmentDAO.findAll()");
        List<Department> list2 = departmentDAO.findAll();
        for (Department dp : list2) {
            System.out.println(dp);
        }
        System.out.println();

        System.out.println("TESTE 3: departmentDAO departmentDAO.insert()");
        Department newDepartment = new Department(null, "Beauty");
        departmentDAO.insert(newDepartment);
        if (newDepartment.getId() != null) {
            System.out.println("Inserted! New id = " + newDepartment.getId());
        }
        System.out.println();

        System.out.println("TESTE 4: departmentDAO departmentDAO.update()");
        department = departmentDAO.findById(1);
        System.out.print("Before update: " + department + ". After update: ");
        department.setName("We Pink!");
        departmentDAO.update(department);
        System.out.println(department);
        System.out.println();

        System.out.println("TESTE 5: departmentDAO departentDAO.delete()");
        System.out.println("Enter id for delete test:");
        id = sc.nextInt();
        departmentDAO.deleteById(id);
        System.out.println("Delete completed.");
    }
}