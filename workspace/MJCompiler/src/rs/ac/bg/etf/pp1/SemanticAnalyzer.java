package rs.ac.bg.etf.pp1;

import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;

public class SemanticAnalyzer extends VisitorAdaptor {

	
	boolean returnhappened = false;
	int printCallCount = 0;
	int varDeclCount = 0;
	int forLevel = 0;
	Struct currentMethod = null;
	Type currType = null;
	Collection<Obj> reqParams = null;
	ArrayList<Struct> passedParams = null;
	ArrayList<Struct> listTypes = null;
	boolean errorDetected = false;
	int nVars = 0;
	
	private Struct boolType;
	
	public SemanticAnalyzer() {
		boolType = new Struct(Struct.Bool);
		Tab.currentScope().addToLocals(new Obj(Obj.Type, "bool", boolType));
	}
	
	Logger log = Logger.getLogger(getClass());

	public void report_error(String message, SyntaxNode info) {
		errorDetected = true;
		StringBuilder msg = new StringBuilder(message);
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.error(msg.toString());
	}

	public void report_info(String message, SyntaxNode info) {
		StringBuilder msg = new StringBuilder(message); 
		int line = (info == null) ? 0: info.getLine();
		if (line != 0)
			msg.append (" na liniji ").append(line);
		log.info(msg.toString());
	}
	
	public boolean checkParams(int level) {
		boolean ret = true;
		if(level != passedParams.size()) {
			return false;
		}
		Iterator<Obj> iterator = reqParams.iterator();
		int i = 0;
		while(iterator.hasNext() && i < passedParams.size()) {
			if (!iterator.next().getType().assignableTo(passedParams.get(i)))
				return false;
			i++;
		}
		return ret;
	}

	public void visit(ProgramName programName) {
		programName.obj = Tab.insert(Obj.Prog, programName.getProgName(), Tab.noType);
		Tab.openScope();
		report_info("Deklarisan program "+ programName.getProgName() , programName);
	}
	
	public void visit(Program program) {
		nVars = Tab.currentScope.getnVars();
		Tab.chainLocalSymbols(program.getProgramName().obj);
		Tab.closeScope();
		report_info("Zavrsen program "+ program.getProgramName().getProgName(), program);
	}
	
	public void visit(ConstDecl constDecl) {
		Obj object = Tab.find(constDecl.getConstName());
		if(object != Tab.noObj) {
			//promenljiva je vec deklarisana
			report_error("Greska na liniji "+ constDecl.getLine()+" : promenljiva sa nazivom "+ constDecl.getConstName()+" je vec deklarisana", null);
			constDecl.obj = Tab.noObj;
			return;
		}
		
		if(currType.struct != constDecl.getConstOption().struct) {
			//dodeljen tip se ne poklapa sa Const vrednoscu (npr char 5)
			report_error("Greska na liniji "+ constDecl.getLine()+" : promenljiva sa nazivom "+ constDecl.getConstName()+" ne odgovara navedenom tipu", null);
			constDecl.obj = Tab.noObj;
			return;
		}		
		
		constDecl.obj = Tab.insert(Obj.Con, constDecl.getConstName(), constDecl.getConstOption().struct);
		ConstOption constopt = constDecl.getConstOption();
		
		if(constopt instanceof ConstOption1) {
			//its number
			ConstOption1 constNumber = (ConstOption1)constopt;
			constDecl.obj.setAdr(constNumber.getNVal());
			report_info("Deklarisana konstanta "+ constDecl.getConstName() + " jednaka "+ constNumber.getNVal(), constDecl);
			return;
		}
		if(constopt instanceof ConstOption2) {
			//its char
			ConstOption2 constChar = (ConstOption2)constopt;
			constDecl.obj.setAdr(constChar.getCVal());
			report_info("Deklarisana konstanta "+ constDecl.getConstName() + " jednaka "+ constChar.getCVal(), constDecl);
			return;
		}
		if(constopt instanceof ConstOption3) {
			//its bool
			ConstOption3 constBool = (ConstOption3)constopt;
			constDecl.obj.setAdr(constBool.getBVal() ? 1 : 0);
			report_info("Deklarisana konstanta "+ constDecl.getConstName() + " jednaka "+ constBool.getBVal(), constDecl);
			return;
		}
		
		
	}
	
	public void visit(Const constelem) {
		Obj object = Tab.find(constelem.getConstName());
		if(object != Tab.noObj) {
			//promenljiva je vec deklarisana
			report_error("Greska na liniji "+ constelem.getLine()+" : promenljiva sa nazivom "+ constelem.getConstName()+" je vec deklarisana", null);
			constelem.obj = Tab.noObj;
			return;
		}
		
		if(currType.struct != constelem.getConstOption().struct) {
			//dodeljen tip se ne poklapa sa Const vrednoscu (npr char 5)
			report_error("Greska na liniji "+ constelem.getLine()+" : promenljiva sa nazivom "+ constelem.getConstName()+" ne odgovara navedenom tipu", null);
			constelem.obj = Tab.noObj;
			return;
		}		
		
		constelem.obj = Tab.insert(Obj.Con, constelem.getConstName(), constelem.getConstOption().struct);
		ConstOption constopt = constelem.getConstOption();
		
		if(constopt instanceof ConstOption1) {
			//its number
			ConstOption1 constNumber = (ConstOption1)constopt;
			constelem.obj.setAdr(constNumber.getNVal());
			report_info("Deklarisana konstanta "+ constelem.getConstName() + " jednaka "+ constNumber.getNVal(), constelem);
			return;
		}
		if(constopt instanceof ConstOption2) {
			//its char
			ConstOption2 constChar = (ConstOption2)constopt;
			constelem.obj.setAdr(constChar.getCVal());
			report_info("Deklarisana konstanta "+ constelem.getConstName() + " jednaka "+ constChar.getCVal(), constelem);
			return;
		}
		if(constopt instanceof ConstOption3) {
			//its bool
			ConstOption3 constBool = (ConstOption3)constopt;
			constelem.obj.setAdr(constBool.getBVal() ? 1 : 0);
			report_info("Deklarisana konstanta "+ constelem.getConstName() + " jednaka "+ constBool.getBVal(), constelem);
			return;
		}
	}
	
	public void visit(ConstOption1 constOption1) {
		//const option with NUMBER
		constOption1.struct = Tab.intType;
	}
	
	public void visit(ConstOption2 constOption2) {
		//const option with CHAR
		constOption2.struct = Tab.charType;
	}
	
	public void visit(ConstOption3 constOption3) {
		//const option with BOOL
		constOption3.struct = boolType;
	}
	
	public void visit(Type type) {
		String typename = type.getTypeName();
		
		Obj obj = Tab.find(typename);
		
		currType = type;
		
		if(obj == Tab.noObj) {
			//ako ovaj tip ne postoji u tabeli simbola vec naveden 
			report_error("Greska na liniji "+ type.getLine()+" : tip "+ typename +" ne postoji", type);
			type.struct = Tab.noType;
			currType.struct = Tab.noType;
			return;
		}
		
		if(obj.getKind() != Obj.Type) {
			//ovaj objekat postoji u tabeli ali ne kao tip
			report_error("Greska na liniji "+ type.getLine()+" : naziv "+ typename +" ne predstavlja tip", type);
			type.struct = Tab.noType;
			currType.struct = Tab.noType;
			return;
		}
		report_info("Deklarisan tip "+ type.getTypeName(), type);
		
		//dodeljuje se strukturi zvanican tip kom odgovara
		type.struct = obj.getType();
	}
	
	public void visit(VarDecl varDecl) {
		String varName = varDecl.getVarName();
		if(Tab.currentScope.findSymbol(varName) != null) {
			//postoji u ovom scope-u vec promenljiva sa datim imenom, to ne sme
			report_error("Greska na liniji "+ varDecl.getLine()+" : varijabla "+ varName +" je vec deklarisana u ovom scope-u", null);
			varDecl.obj = Tab.noObj;
			return;
		}
		
		VarPossibility vp = varDecl.getVarPossibility();
		if(vp instanceof VarPoss1) {
			//niz je + pravi se nova struktura za novi kompleksni tip - Array
			varDecl.obj = Tab.insert(Obj.Var, varName, new Struct(Struct.Array, currType.struct));
		}
		else if(vp instanceof VarPoss3) {
			//MATRICA
			varDecl.obj = Tab.insert(Obj.Var, varName, new Struct(Struct.Array, new Struct(Struct.Array, currType.struct)));
		}
		else {
			//nije niz
			varDecl.obj = Tab.insert(Obj.Var, varName, currType.struct);
		}
		report_info("Deklarisana varijabla "+ varDecl.getVarName(), varDecl);		
	}
	
	public void visit(VarElem varElem) {
		String varName = varElem.getVarName();
		if(Tab.currentScope.findSymbol(varName) != null) {
			//postoji u ovom scope-u vec promenljiva sa datim imenom, to ne sme
			report_error("Greska na liniji "+ varElem.getLine()+" : varijabla "+ varName +" je vec deklarisana u ovom scope-u", null);
			varElem.obj = Tab.noObj;
			return;
		}
		
		VarPossibility vp = varElem.getVarPossibility();
		if(vp instanceof VarPoss1) {
			//niz je + pravi se nova struktura za novi kompleksni tip - Array
			varElem.obj = Tab.insert(Obj.Var, varName, new Struct(Struct.Array, currType.struct));
		}
		else if(vp instanceof VarPoss3) {
			//MATRICA
			varElem.obj = Tab.insert(Obj.Var, varName, new Struct(Struct.Array, new Struct(Struct.Array, currType.struct)));
		}
		else {
			//nije niz
			varElem.obj = Tab.insert(Obj.Var, varName, currType.struct);
		}
		report_info("Deklarisana varijabla "+ varElem.getVarName(), varElem);
	}
	
	public void visit(MethodName methodName) {
		String name = methodName.getMethodName();
		Obj obj = Tab.currentScope.findSymbol(name);
		
		if(obj != null) {
			//postoji vec simbol sa datim imenom u okviru istog scope-a
			report_error("Greska na liniji "+ methodName.getLine()+" : naziv metode "+ name +" vec postoji u ovom scope-u", null);
			methodName.obj = Tab.noObj;
			return;
		}
		
		methodName.obj = Tab.insert(Obj.Meth, name, currentMethod);
		Tab.openScope();
		report_info("Deklarisana metoda pocetak: "+ methodName.getMethodName(), methodName);
		
	}
	
	public void visit(MethodDeclaration methodDeclaration) {
		
		methodDeclaration.obj = methodDeclaration.getMethodName().obj;
		methodDeclaration.obj.setLevel(nVars);
		Tab.chainLocalSymbols(methodDeclaration.obj);
		Tab.closeScope();
		nVars = 0;
		currentMethod = null;
		//ako nije bio nijedan return do sada + metoda nije void -- greska
		MethodOption mo = methodDeclaration.getMethodOption();
		if(mo instanceof MethodOption1) {
			if(returnhappened == false) {
				report_error("Greska: Za metodu koja nije tipa VOID nije pozvana instrukcija return", mo);
				return;
			}
		}
		report_info("Deklarisana metoda kraj:  "+ methodDeclaration.getMethodName().getMethodName(), methodDeclaration);

	}
	
	public void visit(MethodOption1 methodOpt1) {
		//odredjeni Type
		currentMethod = methodOpt1.getType().struct;
		methodOpt1.struct = methodOpt1.getType().struct;
		report_info("Deklarisana metoda je odredjenog tipa: "+ methodOpt1.getType().getTypeName(), methodOpt1);

	}
	
	public void visit(MethodOption2 methodOpt2) {
		//void
		currentMethod = Tab.noType;
		methodOpt2.struct = Tab.noType;
		report_info("Deklarisana metoda je tipa void: ", methodOpt2);

	}
	
	public void visit(FormPars formPars) {
		//ubacuje jedan parametar i ima na kraju poziv rekurzivne liste
		nVars++;
		report_info("Nvars: "+nVars, formPars);
		FormParsVarPossibility vp = formPars.getFormParsVarPossibility();
		if(vp instanceof FormParsVar1) {
			//niz je + pravi se nova struktura za novi kompleksni tip - Array
			formPars.obj = Tab.insert(Obj.Var, formPars.getParamName(), new Struct(Struct.Array, formPars.getType().struct));
		}
		else {
			//nije niz
			formPars.obj = Tab.insert(Obj.Var, formPars.getParamName(), formPars.getType().struct);
		}
		report_info("Deklarisan parametar metode: "+ formPars.getParamName(), formPars);

	}
	
	
	public void visit(FormPar formPar) {
		//samo ubacuje jedan parametar (lista ovo poziva)
		nVars++;
		report_info("Nvars: "+nVars, formPar);
		FormParsVarPossibility vp = formPar.getFormParsVarPossibility();
		if(vp instanceof FormParsVar1) {
			//niz je + pravi se nova struktura za novi kompleksni tip - Array
			formPar.obj = Tab.insert(Obj.Var, formPar.getParamName(), new Struct(Struct.Array, formPar.getType().struct));
		}
		else {
			//nije niz
			formPar.obj = Tab.insert(Obj.Var, formPar.getParamName(), formPar.getType().struct);
		}
		report_info("Deklarisan parametar metode: "+ formPar.getParamName(), formPar);

	}
	
	public void visit(For forstatopen) {
		forLevel++;
		report_info("Otvorena for petlja: ", forstatopen);

	}
	
	public void visit(Statement11 forstatclose) {
		forLevel--;
		report_info("Zatvorena for petlja: ", forstatclose);

	}
	
	public void visit(Statement4 breakstatement) {
		if(forLevel == 0) {
			report_error("Greska na liniji "+ breakstatement.getLine()+" : break nije moguc jer for nije trenutno otvoren", breakstatement);
		}
		report_info("Deklarisana break naredba: ", breakstatement);
	}
	
	public void visit(Statement5 continuestatement) {
		if(forLevel == 0) {
			report_error("Greska na liniji "+ continuestatement.getLine()+" : continue nije moguc jer for nije trenutno otvoren", continuestatement);
		}
		report_info("Deklarisana continue naredba: ", continuestatement);

	}
	
	public void visit(Statement6 returnsemistatement) {
		if(currentMethod != Tab.noType) {
			//trenutna metoda zahteva neku povratnu vrednost
			report_error("Greska na liniji "+ returnsemistatement.getLine()+" : metoda zahteva povratnu vrednost koja nije navedena", returnsemistatement);
		}
		returnhappened = true;
		report_info("Deklarisana return naredba: ", returnsemistatement);

	}
	
	public void visit(Statement7 returnexprsemistatement) {
		//potreban nam je Expr za ovo
		Struct returnType = returnexprsemistatement.getExpr().struct;
		if(!returnType.compatibleWith(currentMethod)) {
			//tip povratnog Expr mora biti kompatibilan sa zadatim tipom funkcije
			report_error("Greska na liniji "+ returnexprsemistatement.getLine()+" : navedena povratna vrednost nije odgovarajuceg tipa", null);
			returnexprsemistatement.obj = Tab.noObj;
			return;
		}
		returnhappened = true;
		report_info("Deklarisan return expr : ", returnexprsemistatement);

	}
	
	public void visit(Statement8 readstatement) {
		//potreban nam je Designator za ovo
		Obj des = readstatement.getDesignator().obj;
		//designator mora biti promenljiva ili element niza
		if(des.getKind() != Obj.Var && des.getKind() != Obj.Elem) {
			report_error("Greska na liniji "+ readstatement.getLine()+" : read se koristi samo za promenljivu ili element niza", null);
			return;
		}
		//designator mora biti tipa int, char ili bool
		if(des.getType() != Tab.intType && des.getType() != Tab.charType && des.getType() != boolType) {
			report_error("Greska na liniji "+ readstatement.getLine()+" : read se koristi samo za tip int, char ili bool", null);
			return;
		}
		report_info("Read promenljive: "+des.getName(), readstatement);
		
	}
	
	public void visit(Statement9 printwithoutcomma) {
		//print (expr);
		Struct expr = printwithoutcomma.getExpr().struct;
		if(expr != boolType && expr != Tab.intType && expr != Tab.charType) {
			report_error("Greska na liniji "+ printwithoutcomma.getLine()+" : print se koristi samo za tip int, char ili bool", null);
			return;	
		}
		report_info("Print promenljive: ", printwithoutcomma);

	}
	
	public void visit(Statement10 printwithcomma) {
		//print(expr, numconst);
		Struct expr = printwithcomma.getExpr().struct;
		if(expr != boolType && expr != Tab.intType && expr != Tab.charType) {
			report_error("Greska na liniji "+ printwithcomma.getLine()+" : print se koristi samo za tip int, char ili bool", null);
			return;	
		}
		report_info("Print promenljive: ", printwithcomma);

	}
	
	public void visit(IFOpt1 ifelse1) {
		Condition con = ifelse1.getCondition();
		if(con.struct != boolType) {
			report_error("Greska na liniji "+ ifelse1.getLine()+" : condition mora biti bool", null);
			ifelse1.obj = Tab.noObj;
			return;	
		}
		
		report_info("If else opcija ", con);
		
	}
	
	public void visit(IFOpt2 if2) {
		Condition con = if2.getCondition();
		if(con.struct != boolType) {
			report_error("Greska na liniji "+ if2.getLine()+" : condition mora biti bool", null);
			if2.obj = Tab.noObj;
			return;	
		}
		
		report_info("If opcija", con);
		
	}

	public void visit(CondFact1 cf1) {
		//Expr
		
		if(cf1.getExpr().struct != boolType) {
			report_error("Greska na liniji "+ cf1.getLine()+" : condfact mora biti boolean", null);
			return;
		}
		cf1.struct = boolType;
	}
	
	public void visit(CondFact2 cf2) {
		//Expr relop expr
		//condition mora biti tipa bool
		if(!cf2.getExpr().struct.compatibleWith(cf2.getExpr1().struct)) {
			report_error("Greska na liniji "+ cf2.getLine()+" : expr1 nije kompatibilan sa expr (izrazi moraju da budu kompatibilni)", null);
			
			return;
		}
		Relop relop = cf2.getRelop();
		if(cf2.getExpr().struct.getKind() == Struct.Array) {
			if(!(relop instanceof DoubleEqual) && !(relop instanceof NotEqual)) {
				report_error("Greska na liniji "+ cf2.getLine()+" : operacija mora biti == ili != uz niz", null);
				
				return;
			}
		}
		
		cf2.struct = boolType;
	}
	
	public void visit(CondTerm condterm) {
		//condfact condfactlist
		if(condterm.getCondFact().struct != boolType) {
			report_error("Greska na liniji "+ condterm.getLine()+" : condfact mora biti boolean", null);
			
			return;
		}
		condterm.struct = boolType;
	}
	
	public void visit(CondFacts cflist) {
		//&& condfact condfactlist | nada
		if(cflist.getCondFact().struct != boolType) {
			report_error("Greska na liniji "+ cflist.getLine()+" : condfact mora biti boolean", null);
			
			return;
		}
		cflist.struct = boolType;
	}
	
	public void visit(CondTerms ctlist) {
		//|| conterm condtermlist | nada
		if(ctlist.getCondTerm().struct != boolType) {
			report_error("Greska na liniji "+ ctlist.getLine()+" : condterm mora biti boolean", null);
			
			return;
		}
		ctlist.struct = boolType;
	}
	
	public void visit(Condition cond) {
		//condterm condtermlist
		if(cond.getCondTerm().struct != boolType) {
			report_error("Greska na liniji "+ cond.getLine()+" : condfact mora biti boolean", null);
			
			return;
		}
		cond.struct = boolType;
	}

	
	
	public void visit(Factor1 desfactor) {
		//designator
		Obj desObj = desfactor.getDesignator().obj;
		String desName = desObj.getName();
		
		
		if(desObj.getKind() == Obj.Meth) {
			report_error("Greska na liniji "+ desfactor.getLine()+" : pozvan je naziv metode bez ()", null);
			desObj = Tab.noObj;
			return;		
		}
		desfactor.struct = desObj.getType();
	}
	
	public void visit(Factor2 methodfactornoparams) {
		//designator ()
		Obj desObj = methodfactornoparams.getDesignator().obj;
		if(desObj.getKind() != Obj.Meth) {
			report_error("Greska na liniji "+ methodfactornoparams.getLine()+" : pozivom metode je pozvano nesto sto nije metoda", null);
			desObj = Tab.noObj;
			return;	
		}
		int level = desObj.getLevel();
		report_info("Level: "+level, methodfactornoparams);
		if(level > 0) {
			report_error("Greska na liniji "+ methodfactornoparams.getLine()+" : metoda koja prima argumente je pozvana bez argumenata", null);
			desObj = Tab.noObj;
			return;	
		}
		methodfactornoparams.struct = desObj.getType();
	}
	
	public void visit(Factor3 methodfactorparams) {
		Obj desObj = methodfactorparams.getDesignator().obj;
		if(desObj.getKind() != Obj.Meth) {
			report_error("Greska na liniji "+ methodfactorparams.getLine()+" : pozivom metode je pozvano nesto sto nije metoda", null);
			desObj = Tab.noObj;
			return;	
		}
		reqParams = desObj.getLocalSymbols();
		int level = desObj.getLevel();
		boolean ret = checkParams(level);
		if(ret == false) {
			report_error("Greska na liniji "+ methodfactorparams.getLine()+" : prosledjeni parametri se ne poklapaju sa argumentima koje metoda prima", null);
			desObj = Tab.noObj;
			return;	
		}
		passedParams = null;
		reqParams = null;
		methodfactorparams.struct = desObj.getType();
	}

	
	
	public void visit(FactoruNm numfactor) {
		numfactor.struct = Tab.intType;
		report_info("Deklarisan int factor : "+ numfactor.getNVal(), numfactor);
	}
	
	public void visit(FactorChar charfactor) {
		charfactor.struct = Tab.charType;
		report_info("Deklarisan char factor : "+ charfactor.getCVal(), charfactor);

	}
	
	public void visit(FactorBool boolfactor) {

		boolfactor.struct = boolType;
		report_info("Deklarisan bool factor : "+ boolfactor.getBVal(), boolfactor);

	}
	
	public void visit(Factor7 newfactor) {
		
		if(newfactor.getExpr().struct != Tab.intType) {
			report_error("Greska na liniji "+ newfactor.getLine()+" : new se koristi samo za niz i mora u [] imati int", null);
			return;
		}
		//pravi se novi niz
		newfactor.struct = new Struct(Struct.Array, newfactor.getType().struct);
		
	}
	
	public void visit(Factor9 newfactormatrix) {
		//NEW Type LUPAR Expr RUPAR LUPAR Expr RUPAR
		if(newfactormatrix.getExpr().struct != Tab.intType) {
			report_error("Greska na liniji "+ newfactormatrix.getLine()+" : new se koristi samo za niz/matricu i mora u [][] imati int", null);
			return;
		}
		if(newfactormatrix.getExpr().struct != Tab.intType) {
			report_error("Greska na liniji "+ newfactormatrix.getLine()+" : new se koristi samo za niz/matricu i mora u [][] imati int", null);
			return;
		}
		//pravljenje matrice tj novog tipa
		newfactormatrix.struct = new Struct(Struct.Array, new Struct(Struct.Array, newfactormatrix.getType().struct));
		report_info("NEW Matrica: type: " + newfactormatrix.getType().getTypeName() + " kind: " + newfactormatrix.struct.getElemType().getElemType().getKind(), newfactormatrix);
	}
	
	public void visit(Factor8 lparenexprrparen) {
		lparenexprrparen.struct = lparenexprrparen.getExpr().struct;
	}
	public void visit(Term term) {
		//factor mulopfactorlist
		Struct factorStruct = term.getFactor().struct;

		MulopFactorList mfl = term.getMulopFactorList();
		if(mfl instanceof MulopFactors) {
			//znaci ima ih vise od jednog
			if(factorStruct != Tab.intType) {
				report_error("Greska na liniji "+ term.getLine()+" : factor mora biti int", null);
				
				return;	
			}
		}

		term.struct = term.getFactor().struct;

	}
	
	public void visit(MulopFactors mf) {
		Factor f = mf.getFactor();
		if(f.struct != Tab.intType) {
			report_error("Greska na liniji "+ mf.getLine()+" : factor mora biti int", null);
			
			return;
		}
		mf.struct = f.struct;

	}
	
	public void visit(Expr2 minusterm) {
		Term t = minusterm.getTerm();
		if(t.struct != Tab.intType) {
			report_error("Greska na liniji "+ minusterm.getLine()+" : term mora biti int", null);
			
			return;			
		}
		minusterm.struct = minusterm.getTerm().struct;
	}
	
	public void visit(AddopTerms at) {
			Term t = at.getTerm();
			if(t.struct != Tab.intType) {
				report_error("Greska na liniji "+ at.getLine()+" : term mora biti int", null);
				
				return;
			}
			at.struct = t.struct;

	}
	
	public void visit(Expr1 termwithoutminus) {
		AddopTermList at = termwithoutminus.getAddopTermList();
		if(at instanceof AddopTerms) {
			Term t = termwithoutminus.getTerm();
			if(t.struct != Tab.intType) {
				report_error("Greska na liniji "+ at.getLine()+" : term mora biti int", null);
				
				return;
			}
		}
		termwithoutminus.struct = termwithoutminus.getTerm().struct;
	}
	
	public void visit(DesignatorName desName) {
		Obj obj = Tab.find(desName.getDesName());
		
		if(obj == Tab.noObj) {
			report_error("Greska na liniji "+ desName.getLine()+"promenljiva: "+desName.getDesName()+" nije definisana.", null);
			obj = Tab.noObj;
			desName.obj = Tab.noObj;
			return;
		}
		desName.obj = obj;
	}
	
	public void visit(Designator designator) {
		String name = designator.getDesignatorName().getDesName();
		Obj obj = Tab.find(name);
		
		if(obj == Tab.noObj) {
			report_error("Greska na liniji "+ designator.getLine()+"promenljiva: "+name+" nije definisana.", null);
			obj = Tab.noObj;
			designator.obj = Tab.noObj;
			return;
		}
		
		if(designator.getIdentOrExprList() instanceof NoIdentOrExpr) {
			//nije ni niz ni matrica
			designator.obj = obj;
		}
		
		else if(designator.getIdentOrExprList() instanceof ExpressMatrix) {
			//matrica je
			//ExpressMatrix em = designator.getIdentOrExprList();
			ExpressMatrix em = (ExpressMatrix) designator.getIdentOrExprList();
			//treba proveriti da li designator je matrica
			if(!(obj.getType().getKind() == Struct.Array && obj.getType().getElemType().getKind() == Struct.Array)) {
				report_error("Greska na liniji "+ designator.getLine()+" : Promenljiva nije tipa matrice", null);
				designator.obj = Tab.noObj;
				return;
			}
			if(em.getExpr().struct != Tab.intType) {
				report_error("Greska na liniji "+ designator.getLine()+" : Expression nije tipa int", null);
				designator.obj = Tab.noObj;
				return;
			}
			if(em.getExpr1().struct != Tab.intType) {
				report_error("Greska na liniji "+ designator.getLine()+" : Expression nije tipa int", null);
				designator.obj = Tab.noObj;
				return;
			}
			designator.obj = new Obj(Obj.Elem, obj.getName(), obj.getType().getElemType().getElemType());
		}
		else {
			//niz je
			if(obj.getType().getKind() != Struct.Array) {
				report_error("Greska na liniji "+ designator.getLine()+" : Promenljiva nije tipa niza", null);
				designator.obj = Tab.noObj;
				return;
			}
			Express expr = (Express) designator.getIdentOrExprList();
			if(expr.getExpr().struct != Tab.intType) {
				report_error("Greska na liniji "+ designator.getLine()+" : Expression nije tipa int", null);
				designator.obj = Tab.noObj;
				return;
			}
			designator.obj = new Obj(Obj.Elem, obj.getName(), obj.getType().getElemType());
		}
	}
	public void visit(DesignStmt1 desstmtsimple) {
		DesStmtOptions dso = desstmtsimple.getDesStmtOptions();
		if(dso instanceof DesStmtOpt4) {
			//designator plus plus
			Obj des = desstmtsimple.getDesignator().obj;
			
			if(des.getKind() != Obj.Var && des.getKind() != Obj.Elem && !(des.getKind() == Obj.Elem && des.getType().getKind() == Struct.Array)) {
				//ako nije ni var, ni field, ni elem onda je greska
				report_error("Greska na liniji "+ desstmtsimple.getLine()+" : designator nije ni var ni field ni elem", null);
				
				return;
			}
			if(des.getType() != Tab.intType) {
				report_error("Greska na liniji "+ desstmtsimple.getLine()+" : designator nije int ", null);
				return;
			}
		}
		else if(dso instanceof DesStmtOpt5) {
			//designator minus minus
			Obj des = desstmtsimple.getDesignator().obj;
			if(des.getKind() != Obj.Var && des.getKind() != Obj.Fld && des.getKind() != Obj.Elem) {
				//ako nije ni var, ni field, ni elem onda je greska
				report_error("Greska na liniji "+ desstmtsimple.getLine()+" : designator nije ni var ni field ni elem", null);
				
				return;
			}
			if(des.getType() != Tab.intType) {
				report_error("Greska na liniji "+ desstmtsimple.getLine()+" : designator nije int ", null);
				return;
			}
		}
		else if(dso instanceof DesStmtOpt1) {
			//designator assignop expr
			Obj des = desstmtsimple.getDesignator().obj;
			//moze biti i matrica
			if(des.getKind() != Obj.Var && des.getKind() != Obj.Elem) {
				//ako nije ni var, ni field, ni elem onda je greska
				report_error("Greska na liniji "+ desstmtsimple.getLine()+" : designator nije ni var ni field ni elem", null);
				des = Tab.noObj;
				return;
			}
			//provera da li je expr kompatibilan sa designator
			Struct exprstruct = ((DesStmtOpt1) dso).getExpr().struct;
			if(exprstruct != null && !exprstruct.compatibleWith(des.getType())) {
				report_error("Greska na liniji "+ desstmtsimple.getLine()+" : expression nije kompatibilan sa designatorom", null);
				
				return;
			}
		}
		else if(dso instanceof DesStmtOpt2) {
			//designator LPAREN RPAREN
			//poziva se metoda ali bez argumenata
			Obj desObj = desstmtsimple.getDesignator().obj;
			Obj obj = Tab.find(desObj.getName());
			if(obj == Tab.noObj) {
				report_error("Greska na liniji "+ desstmtsimple.getLine()+" : metoda nije prethodno deklarisana", null);
				desObj = Tab.noObj;
				obj = Tab.noObj;
				return;
			}
			if(desObj.getKind() != Obj.Meth) {
				report_error("Greska na liniji "+ desstmtsimple.getLine()+" : simbol nije deklarisan kao metoda", null);
				desObj = Tab.noObj;
				obj = Tab.noObj;
				return;
			}
			//proverava koliko ima parametara
			if(desObj.getLevel() > 0) {
				report_error("Greska na liniji "+ desstmtsimple.getLine()+" : metoda je deklarisana kao metoda sa argumentime, a nista nije prosledjeno", null);
				desObj = Tab.noObj;
				obj = Tab.noObj;
				return;
			}
		}
		else if(dso instanceof DesStmtOpt3) {
			//designator LPAREN actpars RPAREN
			//ovde ima argumenata
			Obj desObj = desstmtsimple.getDesignator().obj;
			Obj obj = Tab.find(desObj.getName());
			if(obj == Tab.noObj) {
				report_error("Greska na liniji "+ desstmtsimple.getLine()+" : metoda nije prethodno deklarisana", null);
				desObj = Tab.noObj;
				obj = Tab.noObj;
				return;
			}
			if(desObj.getKind() != Obj.Meth) {
				report_error("Greska na liniji "+ desstmtsimple.getLine()+" : simbol nije deklarisan kao metoda", null);
				desObj = Tab.noObj;
				obj = Tab.noObj;
				return;
			}
			int level = desObj.getLevel();
			reqParams = desObj.getLocalSymbols();
			boolean ret = checkParams(level);
			if(ret != true) {
				report_error("Greska na liniji "+ desstmtsimple.getLine()+" : prosledjeni argumenti se ne poklapaju sa trazenim parametrima", null);
				desObj = Tab.noObj;
				obj = Tab.noObj;
				return;
			}
			passedParams = null;
			reqParams = null;
		}
	}
	
	public void visit(DesignStmt2 ds2) {
		//LUPAR DesignListRight MUL Designator RUPAR EQUAL Prolazak
		Obj desObj1 = ds2.getDesignator().obj;
		if(desObj1.getType().getKind() != Struct.Array) {
			report_error("Greska na liniji "+ ds2.getLine()+" : designator mora biti Array", null);
			desObj1 = Tab.noObj;
			return;
		}
		Obj desObj2 = ds2.getDesignator1().obj;
		if(desObj2.getType().getKind() != Struct.Array) {
			report_error("Greska na liniji "+ ds2.getLine()+" : designator mora biti Array", null);
			desObj2 = Tab.noObj;
			return;
		}
		if(desObj1.getType().getElemType() != desObj2.getType().getElemType()) {
			report_error("Greska na liniji "+ ds2.getLine()+" : Des pretposlednji i poslednji moraju da budu istog tipa", null);
			desObj2 = Tab.noObj;
			desObj1 = Tab.noObj;
			return;
		}
		//treba obici listu i proveriti sve sa desObj2.getType().getElemType()
		int i = 0;
		while(i < listTypes.size()) {
			if(listTypes.get(i) != desObj2.getType().getElemType()) {
				report_error("Greska na liniji "+ ds2.getLine()+" : Des u listi i poslednji moraju da budu istog tipa", null);
				desObj2 = Tab.noObj;
				desObj1 = Tab.noObj;
				return;
			}
			i++;
		}
		
	}
	
	public void visit(DesignatorRights dr) {
		//designator comma designatorrights
		Obj des = dr.getDesignator().obj;
		if(des.getType().getKind() == Struct.Array) {
			report_error("Greska na liniji "+ dr.getLine()+" : Des u listi ne sme biti niz", null);
			des = Tab.noObj;
			return;
		}
		if(listTypes == null) {
			listTypes = new ArrayList<>();
		}
		listTypes.add(des.getType());
	}
	
	
	public void visit(ActPars ap) {
		if(passedParams == null) {
			passedParams = new ArrayList<>();
		}
		passedParams.add(ap.getExpr().struct);
	}
	public void visit(Exprs exprs) {
		if(passedParams == null) {
			passedParams = new ArrayList<>();
		}
		passedParams.add(exprs.getExpr().struct);
	}
	
}
	

