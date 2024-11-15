// generated with ast extension for cup
// version 0.8
// 26/7/2024 17:2:35


package rs.ac.bg.etf.pp1.ast;

public class Statement2 extends Statement {

    private IFOpt1 IFOpt1;

    public Statement2 (IFOpt1 IFOpt1) {
        this.IFOpt1=IFOpt1;
        if(IFOpt1!=null) IFOpt1.setParent(this);
    }

    public IFOpt1 getIFOpt1() {
        return IFOpt1;
    }

    public void setIFOpt1(IFOpt1 IFOpt1) {
        this.IFOpt1=IFOpt1;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IFOpt1!=null) IFOpt1.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IFOpt1!=null) IFOpt1.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IFOpt1!=null) IFOpt1.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement2(\n");

        if(IFOpt1!=null)
            buffer.append(IFOpt1.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement2]");
        return buffer.toString();
    }
}
