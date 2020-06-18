package com.gps.api.rbac.datascope;


import com.gps.api.rbac.datascope.cond.TableCondition;
import com.gps.api.rbac.datascope.cond.UserConditionUtils;
import com.gps.db.datascope.PermissionAop;
import lombok.Getter;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.Between;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.GreaterThanEquals;
import net.sf.jsqlparser.expression.operators.relational.InExpression;
import net.sf.jsqlparser.expression.operators.relational.IsNullExpression;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.LikeExpression;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.expression.operators.relational.MinorThanEquals;
import net.sf.jsqlparser.expression.operators.relational.NotEqualsTo;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.LateralSubSelect;
import net.sf.jsqlparser.statement.select.ParenthesisFromItem;
import net.sf.jsqlparser.statement.select.SubJoin;
import net.sf.jsqlparser.statement.select.SubSelect;
import net.sf.jsqlparser.statement.select.TableFunction;
import net.sf.jsqlparser.statement.select.ValuesList;

import java.util.ArrayList;
import java.util.List;

@Getter
public class FromItemVisitorImpl implements FromItemVisitor {

    // 声明增强条件
    private Expression enhancedCondition;
    private PermissionAop permissionAop;

    public FromItemVisitorImpl(PermissionAop permissionAop) {
        this.permissionAop = permissionAop;
    }

//    // 定义需要数据隔离的表
//    private static HashSet<String> interceptTableSet = Sets.newHashSet();
//
//    static {
//        interceptTableSet.add("DEVICE_STATUS");//商品管理数据隔离; 同时item 表中新增  company_id 字段
//        interceptTableSet.add("POSITIONS");
//        interceptTableSet.add("DEVICE");
//    }

    // FROM 表名 <----主要的就是这个，判断用户对这个表有没有权限
    @Override
    public void visit(Table tableName) {
        //判断该表是否是需要操作的表
        if (isActionTable(tableName.getFullyQualifiedName())) {
//            根据表名获取该用户对于该表的限制条件
            List<TableCondition> test = UserConditionUtils.getTableCondition(tableName, permissionAop);
            //If the TableConditionList is exist
            if (test != null) { //增强sql
                for (TableCondition tableCondition : test) {
                    Expression operator = null;
                    // 声明表达式数组
                    String cond = tableCondition.getOperator();
                    Column column = new Column(new Table(tableName.getAlias() != null ? tableName.getAlias().getName() : tableName.getFullyQualifiedName()),
                            tableCondition.getFieldName());
                    // 如果操作符是between
                    if ("between".equalsIgnoreCase(cond) || "not between".equalsIgnoreCase(cond)) {
                        //expressions = new Expression[] { new LongValue(tableCondition.getFieldName()),new LongValue(tableCondition.getOperator()),new LongValue(tableCondition.getFieldValue()) };
                    } else if ("is null".equalsIgnoreCase(cond) || "is not null".equalsIgnoreCase(cond)) {
                        // 如果操作符是 is null或者是is not null的时候
                        //expressions = new Expression[] { new LongValue(	tableCondition.getFieldName()) };
                    } else if ("in".equalsIgnoreCase(cond) || "not in".equalsIgnoreCase(cond)) {
                        Expression[] expressions = new Expression[]{column};
                        List<Expression> aa = new ArrayList<>();
                        List ls = (List) tableCondition.getFieldValue();
                        for (Object o : ls) {
                            aa.add(new LongValue((Long) o));
                        }
                        ExpressionList el = new ExpressionList(aa);
                        operator = this.getOperator(tableCondition.getOperator(), expressions, el);
                    } else {
                        Expression[] expressions = null;
                        String c = tableCondition.getFieldName();
                        // 其他情况,也就是最常用的情况，比如where   1 = 1
                        if ("1".equals(c)) {
                            Object v = tableCondition.getFieldValue();
                            if (v instanceof String) {
                                expressions = new Expression[] {
                                        new LongValue(c),
                                        new LongValue((String)v)
                                };
                            } else if (v instanceof Long) {
                                expressions = new Expression[] {
                                        new LongValue(c),
                                        new LongValue((Long)v)
                                };
                            }
                        } else {
                            expressions = new Expression[]{column, new StringValue((String) tableCondition.getFieldValue())};
                        }
                        // 根据运算符对原始数据进行拼接
                        operator = this.getOperator(tableCondition.getOperator(), expressions, null);
                    }
                    if (operator != null) {
                        if (this.enhancedCondition != null) {
                            enhancedCondition = new AndExpression(enhancedCondition, operator);
                        } else {
                            enhancedCondition = operator;
                        }
                    }
                }
            }
        }
    }

    // FROM 子查询
    @Override
    public void visit(SubSelect subSelect) {
        // 如果是子查询的话返回到select接口实现类
        subSelect.getSelectBody().accept(new SelectVisitorImpl(permissionAop));
    }

    // FROM subjoin
    @Override
    public void visit(SubJoin subjoin) {
        subjoin.getLeft().accept(new FromItemVisitorImpl(permissionAop));
        subjoin.getJoinList().get(0).getRightItem().accept(new FromItemVisitorImpl(permissionAop));
    }

    // FROM 横向子查询
    @Override
    public void visit(LateralSubSelect lateralSubSelect) {
        lateralSubSelect.getSubSelect().getSelectBody().accept(new SelectVisitorImpl(permissionAop));
    }

    // FROM value列表
    @Override
    public void visit(ValuesList valuesList) {
    }

    // FROM tableFunction
    @Override
    public void visit(TableFunction tableFunction) {
    }

    @Override
    public void visit(ParenthesisFromItem aThis) {

    }

    // 将字符串类型的运算符转换成数据库运算语句
    private Expression getOperator(String op, Expression[] exp, ItemsList listexp) {
        if ("=".equals(op)) {
            EqualsTo eq = new EqualsTo();
            eq.setLeftExpression(exp[0]);
            eq.setRightExpression(exp[1]);
            return eq;
        } else if (">".equals(op)) {
            GreaterThan gt = new GreaterThan();
            gt.setLeftExpression(exp[0]);
            gt.setRightExpression(exp[1]);
            return gt;
        } else if (">=".equals(op)) {
            GreaterThanEquals geq = new GreaterThanEquals();
            geq.setLeftExpression(exp[0]);
            geq.setRightExpression(exp[1]);
            return geq;
        } else if ("<".equals(op)) {
            MinorThan mt = new MinorThan();
            mt.setLeftExpression(exp[0]);
            mt.setRightExpression(exp[1]);
            return mt;
        } else if ("<=".equals(op)) {
            MinorThanEquals leq = new MinorThanEquals();
            leq.setLeftExpression(exp[0]);
            leq.setRightExpression(exp[1]);
            return leq;
        } else if ("<>".equals(op)) {
            NotEqualsTo neq = new NotEqualsTo();
            neq.setLeftExpression(exp[0]);
            neq.setRightExpression(exp[1]);
            return neq;
        } else if ("in".equals(op)) {
            InExpression in = new InExpression();
            in.setNot(false);
            in.setLeftExpression(exp[0]);
            in.setRightItemsList(listexp);
            return in;
        } else if ("not in".equals(op)) {
            InExpression in = new InExpression();
            in.setNot(true);
            in.setLeftExpression(exp[0]);
            in.setRightItemsList(listexp);
            return in;
        } else if ("is null".equalsIgnoreCase(op)) {
            IsNullExpression isNull = new IsNullExpression();
            isNull.setNot(false);
            isNull.setLeftExpression(exp[0]);
            return isNull;
        } else if ("is not null".equalsIgnoreCase(op)) {
            IsNullExpression isNull = new IsNullExpression();
            isNull.setNot(true);
            isNull.setLeftExpression(exp[0]);
            return isNull;
        } else if ("like".equalsIgnoreCase(op)) {
            LikeExpression like = new LikeExpression();
            like.setNot(false);
            like.setLeftExpression(exp[0]);
            like.setRightExpression(exp[1]);
            return like;
        } else if ("not like".equalsIgnoreCase(op)) {
            LikeExpression nlike = new LikeExpression();
            nlike.setNot(true);
            nlike.setLeftExpression(exp[0]);
            nlike.setRightExpression(exp[1]);
            return nlike;
        } else if ("between".equalsIgnoreCase(op)) {
            Between bt = new Between();
            bt.setNot(false);
            bt.setLeftExpression(exp[0]);
            bt.setBetweenExpressionStart(exp[1]);
            bt.setBetweenExpressionEnd(exp[2]);
            return bt;
        } else if ("not between".equalsIgnoreCase(op)) {
            Between bt = new Between();
            bt.setNot(true);
            bt.setLeftExpression(exp[0]);
            bt.setBetweenExpressionStart(exp[1]);
            bt.setBetweenExpressionEnd(exp[2]);
            return bt;
        } else {
            // 如果没有该运算符对应的语句
            return null;
        }

    }

    // 判断传入的table是否是要进行操作的table
    private boolean isActionTable(String tableName) {
        // 默认为操作
        boolean flag = true;
        // 无需操作的表的表名
        List<String> tableNames = new ArrayList<>();

        // 由于sql可能格式不规范可能表名会存在小写，故全部转换成大写,最上面的方法一样
        if (tableNames.contains(tableName.toUpperCase())) {
            // 如果表名在过滤条件中则将flag改为flase
            flag = false;
        }
//        if (interceptTableSet.contains(tableName.toUpperCase())) {
//            flag = true;
//        }
        return flag;
    }

}
