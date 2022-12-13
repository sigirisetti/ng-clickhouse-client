package com.ssk.ng.clickhouseclient.dao;

import com.ssk.ng.clickhouseclient.model.Table;

import java.util.List;

public interface TableDao {
    List<List<Object>> getTableData(Table table);
}
