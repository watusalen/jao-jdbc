package model.dao.implementation;

import model.dao.SellerDAO;
import model.entities.Seller;

import java.util.List;

public class SellerJDBC implements SellerDAO {
    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Seller findById(Integer id) {
        return null;
    }

    @Override
    public List<Seller> findAll() {
        return List.of();
    }
}