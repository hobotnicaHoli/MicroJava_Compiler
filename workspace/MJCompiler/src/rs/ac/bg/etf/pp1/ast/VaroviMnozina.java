// generated with ast extension for cup
// version 0.8
// 26/7/2024 17:2:35


package rs.ac.bg.etf.pp1.ast;

public class VaroviMnozina extends VarList {

    private VarElem VarElem;
    private VarList VarList;

    public VaroviMnozina (VarElem VarElem, VarList VarList) {
        this.VarElem=VarElem;
        if(VarElem!=null) VarElem.setParent(this);
        this.VarList=VarList;
        if(VarList!=null) VarList.setParent(this);
    }

    public VarElem getVarElem() {
        return VarElem;
    }

    public void setVarElem(VarElem VarElem) {
        this.VarElem=VarElem;
    }

    public VarList getVarList() {
        return VarList;
    }

    public void setVarList(VarList VarList) {
        this.VarList=VarList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(VarElem!=null) VarElem.accept(visitor);
        if(VarList!=null) VarList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(VarElem!=null) VarElem.traverseTopDown(visitor);
        if(VarList!=null) VarList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(VarElem!=null) VarElem.traverseBottomUp(visitor);
        if(VarList!=null) VarList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("VaroviMnozina(\n");

        if(VarElem!=null)
            buffer.append(VarElem.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(VarList!=null)
            buffer.append(VarList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [VaroviMnozina]");
        return buffer.toString();
    }
}
