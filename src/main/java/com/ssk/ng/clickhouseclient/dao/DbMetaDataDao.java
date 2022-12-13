package com.ssk.ng.clickhouseclient.dao;

import com.ssk.ng.clickhouseclient.model.Database;

import java.sql.SQLException;
import java.util.List;

public interface DbMetaDataDao {
    List<Database> getDatabases() throws SQLException;
}
