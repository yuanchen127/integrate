package org.ike.integrate.tool;

import javax.sql.RowSet;
import java.sql.SQLException;

@FunctionalInterface
public interface FillSQLServerBulk<R> {
    void fill(RowSet rowSet, R resource) throws SQLException;
}
