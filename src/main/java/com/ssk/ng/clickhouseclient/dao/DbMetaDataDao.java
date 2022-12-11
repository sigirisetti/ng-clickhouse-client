package com.ssk.ng.clickhouseclient.dao;

import com.ssk.ng.clickhouseclient.db.Column;
import com.ssk.ng.clickhouseclient.db.Database;
import com.ssk.ng.clickhouseclient.db.Table;
import com.ssk.ng.clickhouseclient.model.ClickhouseDbTableSize;
import com.ssk.ng.clickhouseclient.model.ClickhouseSystemDisk;
import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickHouseDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbMetaDataDao {

    private static final String SYSTEM_DISKS = "SELECT * FROM system.disks";
    private static final String SYSTEM_PARTS_SQL = "SELECT " +
            "        table, " +
            "        database, " +
            "        sum(bytes) AS size, " +
            "        sum(rows) AS rows, " +
            "        min(min_time) AS min_time, " +
            "        max(max_time) AS max_time, " +
            "        toUInt32((max_time - min_time) / 86400) AS days, " +
            "        size / ((max_time - min_time) / 86400) AS avgDaySize " +
            "    FROM system.parts " +
            "    WHERE active " +
            "    GROUP BY table " +
            "    ORDER BY rows DESC";

    private static final String DATABASE_SYSTEM_PARTS_SQL = "SELECT " +
            "        table, " +
            "        database, " +
            "        sum(bytes) AS size, " +
            "        sum(rows) AS rows, " +
            "        min(min_time) AS min_time, " +
            "        max(max_time) AS max_time, " +
            "        toUInt32((max_time - min_time) / 86400) AS days, " +
            "        size / ((max_time - min_time) / 86400) AS avgDaySize " +
            "    FROM system.parts " +
            "    WHERE database = ? " +
            "    GROUP BY table " +
            "    ORDER BY rows DESC";

    private final ClickHouseDataSource dataSource;

    public DbMetaDataDao(ClickHouseDataSource dataSource) {
        this.dataSource = dataSource;
    }

    private List<ClickhouseDbTableSize> getAllSystemParts() {
        List<ClickhouseDbTableSize> rows = new ArrayList<>();
        try (ClickHouseConnection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SYSTEM_PARTS_SQL);
            try (ResultSet rs = ps.executeQuery()) {
                extractClickhouseDbTableSizeData(rows, rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    private List<ClickhouseSystemDisk> getSystemDisks() {
        List<ClickhouseSystemDisk> rows = new ArrayList<>();
        try (ClickHouseConnection connection = dataSource.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SYSTEM_DISKS);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rows.add(new ClickhouseSystemDisk(
                            rs.getString("name"),
                            rs.getString("path"),
                            rs.getLong("free_space"),
                            rs.getLong("total_space"),
                            rs.getLong("keep_free_space"),
                            rs.getString("type"))
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    private static void extractClickhouseDbTableSizeData(List<ClickhouseDbTableSize> rows, ResultSet rs) throws SQLException {
        while (rs.next()) {
            rows.add(new ClickhouseDbTableSize(
                    rs.getString("table"),
                    rs.getString("database"),
                    rs.getLong("size"),
                    rs.getLong("rows"),
                    rs.getDate("min_date"),
                    rs.getDate("max_date"),
                    rs.getLong("days"),
                    rs.getLong("avgDaySize")
            ));
        }
    }

    public List<Database> getDatabases() {
        List<Database> dbs = new ArrayList<>();
        try (ClickHouseConnection connection = dataSource.getConnection();
             PreparedStatement ps = connection.prepareStatement("show databases")) {
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
        try (ClickHouseConnection connection = dataSource.getConnection()) {
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
        try (ClickHouseConnection connection = dataSource.getConnection()) {
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
