package com.gps.api.rbac.datascope;

import com.gps.db.datascope.PermissionAop;
import net.sf.jsqlparser.statement.select.AllColumns;
import net.sf.jsqlparser.statement.select.AllTableColumns;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItemVisitor;

public class SelectItemVisitorImpl implements SelectItemVisitor {

    private PermissionAop permissionAop;
    public SelectItemVisitorImpl(PermissionAop permissionAop) {
        this.permissionAop = permissionAop;
    }

    @Override
    public void visit(AllColumns allColumns) {
    }

    @Override
    public void visit(AllTableColumns allTableColumns) {
    }

    @Override
    public void visit(SelectExpressionItem selectExpressionItem) {
        selectExpressionItem.getExpression().accept(new ExpressionVisitorImpl(permissionAop));
    }

}
