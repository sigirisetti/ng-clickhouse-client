package com.ssk.ng.clickhouseclient.dao;

import com.clickhouse.jdbc.ClickHouseConnection;
import com.ssk.ng.clickhouseclient.model.Column;
import com.ssk.ng.clickhouseclient.model.Database;
import com.ssk.ng.clickhouseclient.model.Table;
import com.ssk.ng.clickhouseclient.web.session.ClickhouseDataSourceProvider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbMetaDataDaoImpl implements DbMetaDataDao {


    private final ClickhouseDataSourceProvider provider;

    public DbMetaDataDaoImpl(ClickhouseDataSourceProvider provider) {
        this.provider = provider;
    }


    @Override
    public List<Database> getDatabases() throws SQLException {
        List<Database> dbs = new ArrayList<>();
        try (ClickHouseConnection connection = provider.getClickHouseDataSource().getConnection()) {
             PreparedStatement ps = connection.prepareStatement("show databases");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    dbs.add(new Database(rs.getString("name")));
                }
            }

            populateTables(dbs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dbs;
    }

    private void populateTables(List<Database> dbs) {
        for (Database db : dbs) {
            populateTables(db);
        }
    }

    private void populateTables(Database db) {
        List<Table> tables = new ArrayList<>();
            try (ClickHouseConnection connection = provider.getClickHouseDataSource().getConnection()) {
            String sql = String.format("show tables from %s", db.getName());
            PreparedStatement ps = connection.prepareStatement(sql);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    tables.add(new Table(db.getName(), rs.getString("name")));
                }
            }
            db.setTables(tables);
            populateTableColumns(db, tables);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void populateTableColumns(Database db, List<Table> tables) {
        for (Table t : tables) {
            populateTableColumns(db, t);
        }
    }

    private void populateTableColumns(Database db, Table t) {
        List<Column> cols = new ArrayList<>();
            try (ClickHouseConnection connection = provider.getClickHouseDataSource().getConnection()) {
            String sql = String.format("describe %s.%s", db.getName(), t.getName());
            PreparedStatement ps = connection.prepareStatement(sql);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cols.add(new Column(rs.getString("name"), rs.getString("type")));
                }
            }
            t.setColumn(cols);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
