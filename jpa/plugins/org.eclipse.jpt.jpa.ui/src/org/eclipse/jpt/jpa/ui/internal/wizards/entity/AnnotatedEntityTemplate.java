package org.eclipse.jpt.jpa.ui.internal.wizards.entity;

import org.eclipse.jpt.jpa.ui.internal.wizards.entity.data.model.*;
import java.util.*;

public class AnnotatedEntityTemplate
{
  protected static String nl;
  public static synchronized AnnotatedEntityTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    AnnotatedEntityTemplate result = new AnnotatedEntityTemplate();
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
  protected final String TEXT_7 = NL + " *" + NL + " */";
  protected final String TEXT_8 = NL;
  protected final String TEXT_9 = "(name=\"";
  protected final String TEXT_10 = "\")";
  protected final String TEXT_11 = NL + "@Table(name=\"";
  protected final String TEXT_12 = "\")";
  protected final String TEXT_13 = NL;
  protected final String TEXT_14 = NL + "@IdClass(";
  protected final String TEXT_15 = ".class)";
  protected final String TEXT_16 = NL + "public class ";
  protected final String TEXT_17 = " extends ";
  protected final String TEXT_18 = " implements ";
  protected final String TEXT_19 = ", ";
  protected final String TEXT_20 = " {" + NL + "" + NL + "\t";
  protected final String TEXT_21 = "   " + NL + "\t@Id";
  protected final String TEXT_22 = NL + "\tprivate ";
  protected final String TEXT_23 = " ";
  protected final String TEXT_24 = ";";
  protected final String TEXT_25 = NL + "\tprivate static final long serialVersionUID = 1L;" + NL + "" + NL + "\tpublic ";
  protected final String TEXT_26 = "() {" + NL + "\t\tsuper();" + NL + "\t}";
  protected final String TEXT_27 = "   " + NL + "\t@Id ";
  protected final String TEXT_28 = "   " + NL + "\tpublic ";
  protected final String TEXT_29 = " get";
  protected final String TEXT_30 = "() {" + NL + "\t\treturn this.";
  protected final String TEXT_31 = ";" + NL + "\t}" + NL + "" + NL + "\tpublic void set";
  protected final String TEXT_32 = "(";
  protected final String TEXT_33 = " ";
  protected final String TEXT_34 = ") {" + NL + "\t\tthis.";
  protected final String TEXT_35 = " = ";
  protected final String TEXT_36 = ";" + NL + "\t}";
  protected final String TEXT_37 = NL + "   " + NL + "}";
  protected final String TEXT_38 = NL;

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
    stringBuffer.append(TEXT_8);
    stringBuffer.append(model.getArtifactType());
    String ENTITY_NAME = model.getEntityName();
if (model.isEntityNameSet()) {
    stringBuffer.append(TEXT_9);
    stringBuffer.append(ENTITY_NAME);
    stringBuffer.append(TEXT_10);
    }
    if (model.isTableNameSet()) {
    stringBuffer.append(TEXT_11);
    stringBuffer.append(model.getTableName());
    stringBuffer.append(TEXT_12);
    }
    stringBuffer.append(TEXT_13);
    stringBuffer.append(model.getInheritanceStrategy());
    if (model.isCompositePK()) {
    stringBuffer.append(TEXT_14);
    stringBuffer.append(model.getIdClassName());
    stringBuffer.append(TEXT_15);
    }
    stringBuffer.append(TEXT_16);
    stringBuffer.append(model.getClassName());
    String superClass = model.getSuperclassName();
	if (! "".equals(superClass)) {
    stringBuffer.append(TEXT_17);
    stringBuffer.append(superClass);
    }
    
	List<String> interfaces = model.getInterfaces(); 
	if (interfaces.size()>0) {
    stringBuffer.append(TEXT_18);
     }
	for (int i=0; i<interfaces.size(); i++) {
 		String INTERFACE = (String) interfaces.get(i);
		if (i>0) { 
    stringBuffer.append(TEXT_19);
    }
    stringBuffer.append(INTERFACE);
    }
    stringBuffer.append(TEXT_20);
     List<EntityRow> fields = model.getEntityFields();
	List<String> pkFields = model.getPKFields(); 
 	for (EntityRow entity : fields) {
		String NAME = entity.getName();
		if (pkFields.contains(NAME) && model.isFieldAccess()) {
    
    stringBuffer.append(TEXT_21);
    }
    stringBuffer.append(TEXT_22);
    stringBuffer.append(entity.getType());
    stringBuffer.append(TEXT_23);
    stringBuffer.append(entity.getName());
    stringBuffer.append(TEXT_24);
    }
    stringBuffer.append(TEXT_25);
    stringBuffer.append(model.getClassName());
    stringBuffer.append(TEXT_26);
    
	fields = model.getEntityFields();
	if (fields != null) for (int i=0; i<fields.size(); i++) {
		EntityRow field = (EntityRow) fields.get(i);
		String TYPE = field.getType();
		String NAME = field.getName();
		String METHOD = NAME.substring(0,1).toUpperCase() + NAME.substring(1);
	if (pkFields.contains(NAME) && !model.isFieldAccess()) {
    
    stringBuffer.append(TEXT_27);
    }
    stringBuffer.append(TEXT_28);
    stringBuffer.append(TYPE);
    stringBuffer.append(TEXT_29);
    stringBuffer.append(METHOD);
    stringBuffer.append(TEXT_30);
    stringBuffer.append(NAME);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(METHOD);
    stringBuffer.append(TEXT_32);
    stringBuffer.append(TYPE);
    stringBuffer.append(TEXT_33);
    stringBuffer.append(NAME);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(NAME);
    stringBuffer.append(TEXT_35);
    stringBuffer.append(NAME);
    stringBuffer.append(TEXT_36);
    }
    stringBuffer.append(TEXT_37);
    stringBuffer.append(TEXT_38);
    return stringBuffer.toString();
  }
}
