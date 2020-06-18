package com.gps.api.rbac.datascope;

import com.gps.db.datascope.PermissionAop;
import net.sf.jsqlparser.expression.AllComparisonExpression;
import net.sf.jsqlparser.expression.AnalyticExpression;
import net.sf.jsqlparser.expression.AnyComparisonExpression;
import net.sf.jsqlparser.expression.ArrayExpression;
import net.sf.jsqlparser.expression.BinaryExpression;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.CastExpression;
import net.sf.jsqlparser.expression.CollateExpression;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;
import net.sf.jsqlparser.expression.DateValue;
import net.sf.jsqlparser.expression.DoubleValue;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.ExtractExpression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.HexValue;
import net.sf.jsqlparser.expression.IntervalExpression;
import net.sf.jsqlparser.expression.JdbcNamedParameter;
import net.sf.jsqlparser.expression.JdbcParameter;
import net.sf.jsqlparser.expression.JsonExpression;
import net.sf.jsqlparser.expression.KeepExpression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.MySQLGroupConcat;
import net.sf.jsqlparser.expression.NextValExpression;
import net.sf.jsqlparser.expression.NotExpression;
import net.sf.jsqlparser.expression.NullValue;
import net.sf.jsqlparser.expression.NumericBind;
import net.sf.jsqlparser.expression.OracleHierarchicalExpression;
import net.sf.jsqlparser.expression.OracleHint;
import net.sf.jsqlparser.expression.Parenthesis;
import net.sf.jsqlparser.expression.RowConstructor;
import net.sf.jsqlparser.expression.SignedExpression;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.TimeKeyExpression;
import net.sf.jsqlparser.expression.TimeValue;
import net.sf.jsqlparser.expression.TimestampValue;
import net.sf.jsqlparser.expression.UserVariable;
import net.sf.jsqlparser.expression.ValueListExpression;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.operators.arithmetic.Addition;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseAnd;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseLeftShift;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseOr;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseRightShift;
import net.sf.jsqlparser.expression.operators.arithmetic.BitwiseXor;
import net.sf.jsqlparser.expression.operators.arithmetic.Concat;
import net.sf.jsqlparser.expression.operators.arithmetic.Division;
import net.sf.jsqlparser.expression.operators.arithmetic.IntegerDivision;
import net.sf.jsqlparser.expression.operators.arithmetic.Modulo;
import net.sf.jsqlparser.expression.operators.arithmetic.Multiplication;
import net.sf.jsqlparser.expression.operators.arithmetic.Subtraction;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExistsExpression;
import net.sf.jsqlparser.expression.operators.relational.FullTextSearch;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsBooleanExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.JsonOperator;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.Matches;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.expression.operators.relational.RegExpMatchOperator;
import net.sf.jsqlparser.expression.operators.relational.RegExpMySQLOperator;
import net.sf.jsqlparser.expression.operators.relational.SimilarToExpression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.WithItem;

public class ExpressionVisitorImpl implements ExpressionVisitor {

    private PermissionAop permissionAop;
    public ExpressionVisitorImpl(PermissionAop permissionAop) {
        this.permissionAop = permissionAop;
    }

    // 单表达式
    @Override
    public void visit(SignedExpression signedExpression) {
        signedExpression.accept(new ExpressionVisitorImpl(permissionAop));
    }

    // jdbc参数
    @Override
    public void visit(JdbcParameter jdbcParameter) {
    }

    // jdbc参数
    @Override
    public void visit(JdbcNamedParameter jdbcNamedParameter) {
    }

    //
    @Override
    public void visit(Parenthesis parenthesis) {
        parenthesis.getExpression().accept(new ExpressionVisitorImpl(permissionAop));
    }

    // between
    @Override
    public void visit(Between between) {
        between.getLeftExpression().accept(new ExpressionVisitorImpl(permissionAop));
        between.getBetweenExpressionStart().accept(new ExpressionVisitorImpl(permissionAop));
        between.getBetweenExpressionEnd().accept(new ExpressionVisitorImpl(permissionAop));
    }

    // in表达式
    @Override
    public void visit(InExpression inExpression) {
        if (inExpression.getLeftExpression() != null) {
            inExpression.getLeftExpression().accept(new ExpressionVisitorImpl(permissionAop));
        } else if (inExpression.getLeftItemsList() != null) {
//            inExpression.getLeftItemsList().accept(new ItemsListVisitorImpl());
        }
//        inExpression.getRightItemsList().accept(new ItemsListVisitorImpl());
    }

    @Override
    public void visit(FullTextSearch fullTextSearch) {

    }

    // 子查询
    @Override
    public void visit(SubSelect subSelect) {
        if (subSelect.getWithItemsList() != null) {
            for (WithItem withItem : subSelect.getWithItemsList()) {
                withItem.accept(new SelectVisitorImpl(permissionAop));
            }
        }
        subSelect.getSelectBody().accept(new SelectVisitorImpl(permissionAop));
    }

    // exist
    @Override
    public void visit(ExistsExpression existsExpression) {
        existsExpression.getRightExpression().accept(new ExpressionVisitorImpl(permissionAop));
    }

    // allComparisonExpression??
    @Override
    public void visit(AllComparisonExpression allComparisonExpression) {
        allComparisonExpression.getSubSelect().getSelectBody().accept(new SelectVisitorImpl(permissionAop));
    }

    // anyComparisonExpression??
    @Override
    public void visit(AnyComparisonExpression anyComparisonExpression) {
        anyComparisonExpression.getSubSelect().getSelectBody().accept(new SelectVisitorImpl(permissionAop));
    }

    // oexpr??
    @Override
    public void visit(OracleHierarchicalExpression oexpr) {
        if (oexpr.getStartExpression() != null) {
            oexpr.getStartExpression().accept(this);
        }

        if (oexpr.getConnectExpression() != null) {
            oexpr.getConnectExpression().accept(this);
        }
    }

    // rowConstructor?
    @Override
    public void visit(RowConstructor rowConstructor) {
        for (Expression expr : rowConstructor.getExprList().getExpressions()) {
            expr.accept(this);
        }
    }

    // cast
    @Override
    public void visit(CastExpression cast) {
        cast.getLeftExpression().accept(new ExpressionVisitorImpl(permissionAop));
    }

    // 加法
    @Override
    public void visit(Addition addition) {
        visitBinaryExpression(addition);
    }

    // 除法
    @Override
    public void visit(Division division) {
        visitBinaryExpression(division);
    }

    @Override
    public void visit(IntegerDivision division) {

    }

    // 乘法
    @Override
    public void visit(Multiplication multiplication) {
        visitBinaryExpression(multiplication);
    }

    // 减法
    @Override
    public void visit(Subtraction subtraction) {
        visitBinaryExpression(subtraction);
    }

    // and表达式
    @Override
    public void visit(AndExpression andExpression) {
        visitBinaryExpression(andExpression);
    }

    // or表达式
    @Override
    public void visit(OrExpression orExpression) {
        visitBinaryExpression(orExpression);
    }

    // 等式
    @Override
    public void visit(EqualsTo equalsTo) {
        visitBinaryExpression(equalsTo);
    }

    // 大于
    @Override
    public void visit(GreaterThan greaterThan) {
        visitBinaryExpression(greaterThan);
    }

    // 大于等于
    @Override
    public void visit(GreaterThanEquals greaterThanEquals) {
        visitBinaryExpression(greaterThanEquals);
    }

    // like表达式
    @Override
    public void visit(LikeExpression likeExpression) {
        visitBinaryExpression(likeExpression);
    }

    // 小于
    @Override
    public void visit(MinorThan minorThan) {
        visitBinaryExpression(minorThan);
    }

    // 小于等于
    @Override
    public void visit(MinorThanEquals minorThanEquals) {
        visitBinaryExpression(minorThanEquals);
    }

    // 不等于
    @Override
    public void visit(NotEqualsTo notEqualsTo) {
        visitBinaryExpression(notEqualsTo);
    }

    // concat
    @Override
    public void visit(Concat concat) {
        visitBinaryExpression(concat);
    }

    // matches?
    @Override
    public void visit(Matches matches) {
        visitBinaryExpression(matches);
    }

    // bitwiseAnd位运算?
    @Override
    public void visit(BitwiseAnd bitwiseAnd) {
        visitBinaryExpression(bitwiseAnd);
    }

    // bitwiseOr?
    @Override
    public void visit(BitwiseOr bitwiseOr) {
        visitBinaryExpression(bitwiseOr);
    }

    // bitwiseXor?
    @Override
    public void visit(BitwiseXor bitwiseXor) {
        visitBinaryExpression(bitwiseXor);
    }

    // 取模运算modulo?
    @Override
    public void visit(Modulo modulo) {
        visitBinaryExpression(modulo);
    }

    // rexp??
    @Override
    public void visit(RegExpMatchOperator rexpr) {
        visitBinaryExpression(rexpr);
    }

    // regExpMySQLOperator??
    @Override
    public void visit(RegExpMySQLOperator regExpMySQLOperator) {
        visitBinaryExpression(regExpMySQLOperator);
    }

    // 二元表达式
    public void visitBinaryExpression(BinaryExpression binaryExpression) {
        binaryExpression.getLeftExpression().accept(new ExpressionVisitorImpl(permissionAop));
        binaryExpression.getRightExpression().accept(new ExpressionVisitorImpl(permissionAop));
    }

    // -------------------------下面都是没用到的-----------------------------------

    // aexpr??
    @Override
    public void visit(AnalyticExpression aexpr) {
    }

    // wgexpr??
//    @Override
//    public void visit(WithinGroupExpression wgexpr) {
//    }

    // eexpr??
    @Override
    public void visit(ExtractExpression eexpr) {
    }

    // iexpr??
    @Override
    public void visit(IntervalExpression iexpr) {
    }

    // jsonExpr??
    @Override
    public void visit(JsonExpression jsonExpr) {
    }

    @Override
    public void visit(JsonOperator jsonExpr) {

    }

    // hint?
    @Override
    public void visit(OracleHint hint) {
    }

    // timeKeyExpression?
    @Override
    public void visit(TimeKeyExpression timeKeyExpression) {
    }

    // caseExpression?
    @Override
    public void visit(CaseExpression caseExpression) {
    }

    // when?
    @Override
    public void visit(WhenClause whenClause) {
    }

    // var??
    @Override
    public void visit(UserVariable var) {
    }

    // bind?
    @Override
    public void visit(NumericBind bind) {
    }

    // aexpr?
    @Override
    public void visit(KeepExpression aexpr) {
    }

    // groupConcat?
    @Override
    public void visit(MySQLGroupConcat groupConcat) {
    }

    @Override
    public void visit(ValueListExpression valueList) {

    }

    // table列
    @Override
    public void visit(Column tableColumn) {
    }

    // double类型值
    @Override
    public void visit(DoubleValue doubleValue) {
    }

    // long类型值
    @Override
    public void visit(LongValue longValue) {
    }

    // 16进制类型值
    @Override
    public void visit(HexValue hexValue) {
    }

    // date类型值
    @Override
    public void visit(DateValue dateValue) {
    }

    // time类型值
    @Override
    public void visit(TimeValue timeValue) {
    }

    // 时间戳类型值
    @Override
    public void visit(TimestampValue timestampValue) {
    }

    @Override
    public void visit(BitwiseRightShift aThis) {

    }

    @Override
    public void visit(BitwiseLeftShift aThis) {

    }

    // 空值
    @Override
    public void visit(NullValue nullValue) {
    }

    // 方法
    @Override
    public void visit(Function function) {
    }

    // 字符串类型值
    @Override
    public void visit(StringValue stringValue) {
    }

    // is null表达式
    @Override
    public void visit(IsNullExpression isNullExpression) {
    }

    @Override
    public void visit(IsBooleanExpression isBooleanExpression) {

    }

    // literal?
    @Override
    public void visit(DateTimeLiteralExpression literal) {
    }

    @Override
    public void visit(NotExpression aThis) {

    }

    @Override
    public void visit(NextValExpression aThis) {

    }

    @Override
    public void visit(CollateExpression aThis) {

    }

    @Override
    public void visit(SimilarToExpression aThis) {

    }

    @Override
    public void visit(ArrayExpression aThis) {

    }
}
