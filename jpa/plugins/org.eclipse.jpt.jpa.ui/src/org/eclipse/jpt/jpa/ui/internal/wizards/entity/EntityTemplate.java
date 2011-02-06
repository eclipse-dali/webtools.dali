package org.eclipse.jpt.jpa.ui.internal.wizards.entity;

import org.eclipse.jpt.jpa.ui.internal.wizards.entity.data.model.*;
import java.util.*;

public class EntityTemplate
{
  protected static String nl;
  public static synchronized EntityTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    EntityTemplate result = new EntityTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";";
  protected final String TEXT_3 = NL;
  protected final String TEXT_4 = NL + "import ";
  protected final String TEXT_5 = ";";
  protected final String TEXT_6 = NL + NL + "/**" + NL + " * Entity implementation class for Entity: ";
  protected final String TEXT_7 = NL + " *" + NL + " */" + NL + "public class ";
  protected final String TEXT_8 = " extends ";
  protected final String TEXT_9 = " implements ";
  protected final String TEXT_10 = ", ";
  protected final String TEXT_11 = " {" + NL + "" + NL + "\t";
  protected final String TEXT_12 = " " + NL + "\tprivate ";
  protected final String TEXT_13 = " ";
  protected final String TEXT_14 = ";";
  protected final String TEXT_15 = NL + "\tprivate static final long serialVersionUID = 1L;\t" + NL + "\tpublic ";
  protected final String TEXT_16 = "() {" + NL + "\t\tsuper();" + NL + "\t} " + NL + "\t";
  protected final String TEXT_17 = "   " + NL + "\tpublic ";
  protected final String TEXT_18 = " get";
  protected final String TEXT_19 = "() {" + NL + " \t\treturn this.";
  protected final String TEXT_20 = ";" + NL + "\t}" + NL + "" + NL + "\tpublic void set";
  protected final String TEXT_21 = "(";
  protected final String TEXT_22 = " ";
  protected final String TEXT_23 = ") {" + NL + "\t\tthis.";
  protected final String TEXT_24 = " = ";
  protected final String TEXT_25 = ";" + NL + "\t}" + NL + "\t";
  protected final String TEXT_26 = NL + "   " + NL + "}";
  protected final String TEXT_27 = NL;

  public String generate(Object argument)
  {
    final StringBuffer stringBuffer = new StringBuffer();
     CreateEntityTemplateModel model = (CreateEntityTemplateModel) argument; 
if (model.getJavaPackageName()!=null && model.getJavaPackageName()!="") { 
    stringBuffer.append(TEXT_1);
    stringBuffer.append(model.getJavaPackageName());
    stringBuffer.append(TEXT_2);
    }
    stringBuffer.append(TEXT_3);
     Collection<String> imports = model.getImports(false);
for (String anImport : imports) { 
    stringBuffer.append(TEXT_4);
    stringBuffer.append(anImport);
    stringBuffer.append(TEXT_5);
     } 
    stringBuffer.append(TEXT_6);
    stringBuffer.append(model.getEntityName());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(model.getClassName());
    String superClass = model.getSuperclassName();
	if (! "".equals(superClass)) {
    stringBuffer.append(TEXT_8);
    stringBuffer.append(superClass);
    }
    
	List<String> interfaces = model.getInterfaces(); 
	if (interfaces.size()>0) {
    stringBuffer.append(TEXT_9);
     }
	for (int i=0; i<interfaces.size(); i++) {
		String INTERFACE = (String) interfaces.get(i);
		if (i>0) { 
    stringBuffer.append(TEXT_10);
    }
    stringBuffer.append(INTERFACE);
    }
    stringBuffer.append(TEXT_11);
     List<EntityRow> fields = model.getEntityFields(); 
	for (EntityRow entity : fields) {     
	
    stringBuffer.append(TEXT_12);
    stringBuffer.append(entity.getType());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(entity.getName());
    stringBuffer.append(TEXT_14);
    }
    stringBuffer.append(TEXT_15);
    stringBuffer.append(model.getClassName());
    stringBuffer.append(TEXT_16);
    
	fields = model.getEntityFields();
	if (fields != null) for (int i=0; i<fields.size(); i++) {
		EntityRow field = (EntityRow) fields.get(i);
		String TYPE = field.getType();
		String NAME = field.getName();
		String METHOD = NAME.substring(0,1).toUpperCase() + NAME.substring(1);
	
    stringBuffer.append(TEXT_17);
    stringBuffer.append(TYPE);
    stringBuffer.append(TEXT_18);
    stringBuffer.append(METHOD);
    stringBuffer.append(TEXT_19);
    stringBuffer.append(NAME);
    stringBuffer.append(TEXT_20);
    stringBuffer.append(METHOD);
    stringBuffer.append(TEXT_21);
    stringBuffer.append(TYPE);
    stringBuffer.append(TEXT_22);
    stringBuffer.append(NAME);
    stringBuffer.append(TEXT_23);
    stringBuffer.append(NAME);
    stringBuffer.append(TEXT_24);
    stringBuffer.append(NAME);
    stringBuffer.append(TEXT_25);
    }
    stringBuffer.append(TEXT_26);
    stringBuffer.append(TEXT_27);
    return stringBuffer.toString();
  }
}
