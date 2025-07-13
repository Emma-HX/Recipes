package com.group6.recipes.dao;

import com.group6.recipes.model.Unit;

import java.sql.SQLException;
import java.util.List;

public interface UnitDAO {
    public void addUnit(Unit unit) ;
    public Unit getUnitById(int unitId) ;
    public List<Unit> getAllUnits() throws SQLException;
    public void updateUnit(Unit unit) ;
    public void deleteUnit(int unitId) ;
} 