// generated with ast extension for cup
// version 0.8
// 26/7/2024 17:2:35


package rs.ac.bg.etf.pp1.ast;

public class Statement3 extends Statement {

    private IFOpt2 IFOpt2;

    public Statement3 (IFOpt2 IFOpt2) {
        this.IFOpt2=IFOpt2;
        if(IFOpt2!=null) IFOpt2.setParent(this);
    }

    public IFOpt2 getIFOpt2() {
        return IFOpt2;
    }

    public void setIFOpt2(IFOpt2 IFOpt2) {
        this.IFOpt2=IFOpt2;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IFOpt2!=null) IFOpt2.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IFOpt2!=null) IFOpt2.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IFOpt2!=null) IFOpt2.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Statement3(\n");

        if(IFOpt2!=null)
            buffer.append(IFOpt2.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Statement3]");
        return buffer.toString();
    }
}
