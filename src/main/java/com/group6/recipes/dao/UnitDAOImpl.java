package com.group6.recipes.dao;

import com.group6.recipes.model.Category;
import com.group6.recipes.model.Unit;
import com.group6.recipes.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UnitDAOImpl implements UnitDAO {

    @Override
    public void addUnit(Unit unit) {

    }

    @Override
    public Unit getUnitById(int unitId) {
        return null;
    }

    @Override
    public List<Unit> getAllUnits() throws SQLException {
        List<Unit> list = new ArrayList<>();
        String sql = "SELECT * FROM units";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Unit unit = new Unit();
                unit.setUnitId(rs.getInt("unit_id"));
                unit.setUnitName(rs.getString("unit_name"));
                list.add(unit);
            }
        }
        return list;
    }

    @Override
    public void updateUnit(Unit unit) {

    }

    @Override
    public void deleteUnit(int unitId) {

    }
}
