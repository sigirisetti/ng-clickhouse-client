package com.ssk.ng.clickhouseclient.dao;

import com.ssk.ng.clickhouseclient.model.Column;
import com.ssk.ng.clickhouseclient.model.Table;
import ru.yandex.clickhouse.ClickHouseConnection;
import ru.yandex.clickhouse.ClickHouseDataSource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TableDaoImpl {

    private final String SELECT_TABLE_DATA_SQL = "SELECT %s FROM %s.%s LIMIT 1000";

    private final ClickHouseDataSource dataSource;

    public TableDaoImpl(ClickHouseDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<List<Object>> getTableData(Table table) {
        List<List<Object>> tableData = new ArrayList<>(1000);
        try (ClickHouseConnection connection = dataSource.getConnection()) {

            String cols = table.getColumn().stream().map(c -> c.getName()).collect(Collectors.joining(", "));

            String sql = String.format(SELECT_TABLE_DATA_SQL, cols, table.getDatabase(), table.getName());


            System.out.println("Firing sql : " + sql);
            PreparedStatement ps = connection.prepareStatement(sql);
            int n = table.getColumn().size();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    List<Object> row = new ArrayList<>(n);
                    tableData.add(row);
                    for (Column c : table.getColumn()) {
                        if (c.getType().startsWith("Array")) {
                            row.add(Arrays.stream((Object[])rs.getArray(c.getName()).getArray())
                                    .filter(Objects::nonNull)
                                    .map(String::valueOf).collect(Collectors.joining(", ")));
                        } else {
                            row.add(rs.getObject(c.getName()));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tableData;
    }
}
