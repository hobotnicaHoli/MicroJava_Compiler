// generated with ast extension for cup
// version 0.8
// 26/7/2024 17:2:35


package rs.ac.bg.etf.pp1.ast;

public class MethodDeclarations extends MethodDeclarationList {

    private MethodDeclaration MethodDeclaration;
    private MethodDeclarationList MethodDeclarationList;

    public MethodDeclarations (MethodDeclaration MethodDeclaration, MethodDeclarationList MethodDeclarationList) {
        this.MethodDeclaration=MethodDeclaration;
        if(MethodDeclaration!=null) MethodDeclaration.setParent(this);
        this.MethodDeclarationList=MethodDeclarationList;
        if(MethodDeclarationList!=null) MethodDeclarationList.setParent(this);
    }

    public MethodDeclaration getMethodDeclaration() {
        return MethodDeclaration;
    }

    public void setMethodDeclaration(MethodDeclaration MethodDeclaration) {
        this.MethodDeclaration=MethodDeclaration;
    }

    public MethodDeclarationList getMethodDeclarationList() {
        return MethodDeclarationList;
    }

    public void setMethodDeclarationList(MethodDeclarationList MethodDeclarationList) {
        this.MethodDeclarationList=MethodDeclarationList;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MethodDeclaration!=null) MethodDeclaration.accept(visitor);
        if(MethodDeclarationList!=null) MethodDeclarationList.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MethodDeclaration!=null) MethodDeclaration.traverseTopDown(visitor);
        if(MethodDeclarationList!=null) MethodDeclarationList.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MethodDeclaration!=null) MethodDeclaration.traverseBottomUp(visitor);
        if(MethodDeclarationList!=null) MethodDeclarationList.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MethodDeclarations(\n");

        if(MethodDeclaration!=null)
            buffer.append(MethodDeclaration.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MethodDeclarationList!=null)
            buffer.append(MethodDeclarationList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MethodDeclarations]");
        return buffer.toString();
    }
}
