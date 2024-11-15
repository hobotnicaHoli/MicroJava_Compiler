// generated with ast extension for cup
// version 0.8
// 26/7/2024 17:2:35


package rs.ac.bg.etf.pp1.ast;

public class Statement11 extends Statement {

    private For For;
    private StatementOpt1 StatementOpt1;
    private StatementOpt2 StatementOpt2;
    private StatementOpt1 StatementOpt11;
    private Statement Statement;

    public Statement11 (For For, StatementOpt1 StatementOpt1, StatementOpt2 StatementOpt2, StatementOpt1 StatementOpt11, Statement Statement) {
        this.For=For;
        if(For!=null) For.setParent(this);
        this.StatementOpt1=StatementOpt1;
        if(StatementOpt1!=null) StatementOpt1.setParent(this);
        this.StatementOpt2=StatementOpt2;
        if(StatementOpt2!=null) StatementOpt2.setParent(this);
        this.StatementOpt11=StatementOpt11;
        if(StatementOpt11!=null) StatementOpt11.setParent(this);
        this.Statement=Statement;
        if(Statement!=null) Statement.setParent(this);
    }

    public For getFor() {
        return For;
    }

    public void setFor(For For) {
        this.For=For;
    }

    public StatementOpt1 getStatementOpt1() {
        return StatementOpt1;
    }

    public void setStatementOpt1(StatementOpt1 StatementOpt1) {
        this.StatementOpt1=StatementOpt1;
    }

    public StatementOpt2 getStatementOpt2() {
        return StatementOpt2;
    }

    public void setStatementOpt2(StatementOpt2 StatementOpt2) {
        this.StatementOpt2=StatementOpt2;
    }

    public StatementOpt1 getStatementOpt11() {
        return StatementOpt11;
    }

    public void setStatementOpt11(StatementOpt1 StatementOpt11) {
        this.StatementOpt11=StatementOpt11;
    }

    public Statement getStatement() {
        return Statement;
    }

    public void setStatement(Statement Statement) {
        this.Statement=Statement;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(For!=null) For.accept(visitor);
        if(StatementOpt1!=null) StatementOpt1.accept(visitor);
        if(StatementOpt2!=null) StatementOpt2.accept(visitor);
        if(StatementOpt11!=null) StatementOpt11.accept(visitor);
        if(Statement!=null) Statement.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(For!=null) For.traverseTopDown(visitor);
        if(StatementOpt1!=null) StatementOpt1.traverseTopDown(visitor);
        if(StatementOpt2!=null) StatementOpt2.traverseTopDown(visitor);
        if(StatementOpt11!=null) StatementOpt11.traverseTopDown(visitor);
        if(Statement!=null) Statement.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(For!=null) For.traverseBottomUp(visitor);
        if(StatementOpt1!=null) StatementOpt1.traverseBottomUp(visitor);
        if(StatementOpt2!=null) StatementOpt2.traverseBottomUp(visitor);
        if(StatementOpt11!=null) StatementOpt11.traverseBottomUp(visitor);
        if(Statement!=null) Statement.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement11(\n");

        if(For!=null)
            buffer.append(For.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementOpt1!=null)
            buffer.append(StatementOpt1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementOpt2!=null)
            buffer.append(StatementOpt2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(StatementOpt11!=null)
            buffer.append(StatementOpt11.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Statement!=null)
            buffer.append(Statement.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement11]");
        return buffer.toString();
    }
}
