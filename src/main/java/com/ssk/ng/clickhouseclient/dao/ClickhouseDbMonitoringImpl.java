package com.ssk.ng.clickhouseclient.dao;

import com.clickhouse.jdbc.ClickHouseConnection;
import com.ssk.ng.clickhouseclient.model.ClickhouseDbTableSize;
import com.ssk.ng.clickhouseclient.model.ClickhouseSystemDisk;
import com.ssk.ng.clickhouseclient.web.session.ClickhouseDataSourceProvider;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClickhouseDbMonitoringImpl implements ClickhouseDbMonitoring {

    private static final String SYSTEM_DISKS = "SELECT * FROM system.disks";
    private static final String SYSTEM_PARTS_SQL = "SELECT " +
            "        table, " +
            "        database, " +
            "        sum(bytes) AS size, " +
            "        sum(rows) AS rows, " +
            "        min(min_time) AS min_time, " +
            "        max(max_time) AS max_time, " +
            "        toUInt32((max_time - min_time) / 86400) AS days, " +
            "        size / ((max_time - min_time) / 86400) AS avgDailySize " +
            "    FROM system.parts " +
            "    WHERE active " +
            "    GROUP BY table, database " +
            "    ORDER BY rows DESC";

    private static final String DATABASE_SYSTEM_PARTS_SQL = "SELECT " +
            "        table, " +
            "        database, " +
            "        sum(bytes) AS size, " +
            "        sum(rows) AS rows, " +
            "        min(min_time) AS min_time, " +
            "        max(max_time) AS max_time, " +
            "        toUInt32((max_time - min_time) / 86400) AS days, " +
            "        size / ((max_time - min_time) / 86400) AS avgDailySize " +
            "    FROM system.parts " +
            "    WHERE database = ? " +
            "    GROUP BY table, database " +
            "    ORDER BY rows DESC";

    private final ClickhouseDataSourceProvider provider;

    public ClickhouseDbMonitoringImpl(ClickhouseDataSourceProvider provider) {
        this.provider = provider;
    }

    @Override
    public List<ClickhouseDbTableSize> getAllSystemParts() {
        List<ClickhouseDbTableSize> rows = new ArrayList<>();
        try (ClickHouseConnection connection = provider.getClickHouseDataSource().getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SYSTEM_PARTS_SQL);
            try (ResultSet rs = ps.executeQuery()) {
                extractClickhouseDbTableSizeData(rows, rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    @Override
    public List<ClickhouseDbTableSize> getDatabaseParts(String database) {
        List<ClickhouseDbTableSize> rows = new ArrayList<>();
        try (ClickHouseConnection connection = provider.getClickHouseDataSource().getConnection()) {
            PreparedStatement ps = connection.prepareStatement(DATABASE_SYSTEM_PARTS_SQL);
            ps.setString(1, database);
            try (ResultSet rs = ps.executeQuery()) {
                extractClickhouseDbTableSizeData(rows, rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }

    @Override
    public List<ClickhouseSystemDisk> getSystemDisks() {
        List<ClickhouseSystemDisk> rows = new ArrayList<>();
        try (ClickHouseConnection connection = provider.getClickHouseDataSource().getConnection()) {
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
                    rs.getDate("min_time"),
                    rs.getDate("max_time"),
                    rs.getLong("days"),
                    rs.getLong("avgDailySize")
            ));
        }
    }

}
