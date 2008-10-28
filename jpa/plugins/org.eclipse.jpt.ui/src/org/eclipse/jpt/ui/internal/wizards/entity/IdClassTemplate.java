package org.eclipse.jpt.ui.internal.wizards.entity;

import org.eclipse.jpt.ui.internal.wizards.entity.data.model.*;
import java.util.*;

public class IdClassTemplate
{
  protected static String nl;
  public static synchronized IdClassTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    IdClassTemplate result = new IdClassTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "package ";
  protected final String TEXT_2 = ";";
  protected final String TEXT_3 = NL;
  protected final String TEXT_4 = NL + "import ";
  protected final String TEXT_5 = ";";
  protected final String TEXT_6 = NL + NL + "/**" + NL + " * ID class for entity: ";
  protected final String TEXT_7 = NL + " *" + NL + " */ " + NL + "public class ";
  protected final String TEXT_8 = " ";
  protected final String TEXT_9 = " implements ";
  protected final String TEXT_10 = ", ";
  protected final String TEXT_11 = " {   " + NL + "   " + NL + "\t";
  protected final String TEXT_12 = "         " + NL + "\tprivate ";
  protected final String TEXT_13 = " ";
  protected final String TEXT_14 = ";";
  protected final String TEXT_15 = NL + "\tprivate static final long serialVersionUID = 1L;" + NL + "" + NL + "\tpublic ";
  protected final String TEXT_16 = "() {}" + NL + "" + NL + "\t";
  protected final String TEXT_17 = NL + NL + "\tpublic ";
  protected final String TEXT_18 = " get";
  protected final String TEXT_19 = "() {" + NL + "\t\treturn this.";
  protected final String TEXT_20 = ";" + NL + "\t}" + NL + "" + NL + "\tpublic void set";
  protected final String TEXT_21 = "(";
  protected final String TEXT_22 = " ";
  protected final String TEXT_23 = ") {" + NL + "\t\tthis.";
  protected final String TEXT_24 = " = ";
  protected final String TEXT_25 = ";" + NL + "\t}" + NL + "\t";
  protected final String TEXT_26 = NL + "   " + NL + "\t/*" + NL + "\t * @see java.lang.Object#equals(Object)" + NL + "\t */\t" + NL + "\tpublic boolean equals(Object o) {" + NL + "\t\tif (o == this) {" + NL + "\t\t\treturn true;" + NL + "\t\t}" + NL + "\t\tif (!(o instanceof ";
  protected final String TEXT_27 = ")) {" + NL + "\t\t\treturn false;" + NL + "\t\t}" + NL + "\t\t";
  protected final String TEXT_28 = " other = (";
  protected final String TEXT_29 = ") o;" + NL + "\t\treturn true";
  protected final String TEXT_30 = NL + "\t\t\t&& ";
  protected final String TEXT_31 = "() == other.";
  protected final String TEXT_32 = "()";
  protected final String TEXT_33 = NL + "\t\t\t&& (Double.doubleToLongBits(";
  protected final String TEXT_34 = "()) == Double.doubleToLongBits(other.";
  protected final String TEXT_35 = "()))";
  protected final String TEXT_36 = NL + "\t\t\t&& (Float.floatToIntBits(";
  protected final String TEXT_37 = "()) == Float.floatToIntBits(other.";
  protected final String TEXT_38 = "()))";
  protected final String TEXT_39 = NL + "\t\t\t&& (";
  protected final String TEXT_40 = "() == null ? other.";
  protected final String TEXT_41 = "() == null : ";
  protected final String TEXT_42 = "().equals(other.";
  protected final String TEXT_43 = "()))";
  protected final String TEXT_44 = ";" + NL + "\t}" + NL + "\t" + NL + "\t/*\t " + NL + "\t * @see java.lang.Object#hashCode()" + NL + "\t */\t" + NL + "\tpublic int hashCode() {" + NL + "\t\tfinal int prime = 31;" + NL + "\t\tint result = 1;";
  protected final String TEXT_45 = NL + "\t\tresult = prime * result + (";
  protected final String TEXT_46 = "() ? 1 : 0);";
  protected final String TEXT_47 = NL + "\t\tresult = prime * result + ";
  protected final String TEXT_48 = "();";
  protected final String TEXT_49 = NL + "\t\tresult = prime * result + ((int) ";
  protected final String TEXT_50 = "());";
  protected final String TEXT_51 = NL + "\t\tresult = prime * result + ((int) (";
  protected final String TEXT_52 = "() ^ (";
  protected final String TEXT_53 = "() >>> 32)));";
  protected final String TEXT_54 = NL + "\t\tresult = prime * result + ((int) (Double.doubleToLongBits(";
  protected final String TEXT_55 = "() ) ^ (Double.doubleToLongBits(";
  protected final String TEXT_56 = "()) >>> 32)));";
  protected final String TEXT_57 = NL + "\t\tresult = prime * result + Float.floatToIntBits(";
  protected final String TEXT_58 = "());";
  protected final String TEXT_59 = NL + "\t\tresult = prime * result + (";
  protected final String TEXT_60 = "() == null ? 0 : ";
  protected final String TEXT_61 = "().hashCode());";
  protected final String TEXT_62 = NL + "\t\treturn result;" + NL + "\t}" + NL + "   " + NL + "   " + NL + "}";
  protected final String TEXT_63 = NL;

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
     Collection<String> imports = model.getImports(true);
for (String anImport : imports) { 
    stringBuffer.append(TEXT_4);
    stringBuffer.append(anImport);
    stringBuffer.append(TEXT_5);
     } 
    stringBuffer.append(TEXT_6);
    stringBuffer.append(model.getClassName());
    stringBuffer.append(TEXT_7);
    stringBuffer.append(model.getIdClassName());
    stringBuffer.append(TEXT_8);
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
	List<String> pkFields = model.getPKFields(); 
	for (int i=0; i<fields.size(); i++) {
		EntityRow entity = (EntityRow) fields.get(i);
		if (!pkFields.contains(entity.getName())) {
			continue;
		}
	
    stringBuffer.append(TEXT_12);
    stringBuffer.append(entity.getType());
    stringBuffer.append(TEXT_13);
    stringBuffer.append(entity.getName());
    stringBuffer.append(TEXT_14);
    }
    stringBuffer.append(TEXT_15);
    stringBuffer.append(model.getIdClassName());
    stringBuffer.append(TEXT_16);
    
	fields = model.getEntityFields();
	if (fields != null) for (int i=0; i<fields.size(); i++) {
		EntityRow field = (EntityRow) fields.get(i);
		String TYPE = field.getType();
		String NAME = field.getName();
		if (!pkFields.contains(NAME)) {
			continue;
		} 		
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
    stringBuffer.append(model.getIdClassName());
    stringBuffer.append(TEXT_27);
    stringBuffer.append(model.getIdClassName());
    stringBuffer.append(TEXT_28);
    stringBuffer.append(model.getIdClassName());
    stringBuffer.append(TEXT_29);
     if (fields != null) for (int i=0; i<fields.size(); i++) {
	EntityRow field = (EntityRow) fields.get(i); 
	String NAME = field.getName();
	if (!pkFields.contains(NAME)) {
       	continue;
    }
     	String TYPE = field.getType(); 
	String GET_METHOD = "get" + NAME.substring(0,1).toUpperCase() + NAME.substring(1); 
    	if (TYPE.equals("boolean") || TYPE.equals("byte") || TYPE.equals("char") || TYPE.equals("short") || TYPE.equals("int") || TYPE.equals("long")) { 
    stringBuffer.append(TEXT_30);
    stringBuffer.append(GET_METHOD);
    stringBuffer.append(TEXT_31);
    stringBuffer.append(GET_METHOD);
    stringBuffer.append(TEXT_32);
     	} else if (TYPE.equals("double")) { 
    stringBuffer.append(TEXT_33);
    stringBuffer.append(GET_METHOD);
    stringBuffer.append(TEXT_34);
    stringBuffer.append(GET_METHOD);
    stringBuffer.append(TEXT_35);
     	} else if (TYPE.equals("float")) { 
    stringBuffer.append(TEXT_36);
    stringBuffer.append(GET_METHOD);
    stringBuffer.append(TEXT_37);
    stringBuffer.append(GET_METHOD);
    stringBuffer.append(TEXT_38);
     	} else { 
    stringBuffer.append(TEXT_39);
    stringBuffer.append(GET_METHOD);
    stringBuffer.append(TEXT_40);
    stringBuffer.append(GET_METHOD);
    stringBuffer.append(TEXT_41);
    stringBuffer.append(GET_METHOD);
    stringBuffer.append(TEXT_42);
    stringBuffer.append(GET_METHOD);
    stringBuffer.append(TEXT_43);
     	} 
     } 
    stringBuffer.append(TEXT_44);
     if (fields != null) for (int i=0; i<fields.size(); i++) { 
	EntityRow field = (EntityRow) fields.get(i);
	String NAME = field.getName();
	if (!pkFields.contains(NAME)) {
       	continue;
    }
     	String TYPE = field.getType(); 
	String GET_METHOD = "get" + NAME.substring(0,1).toUpperCase() + NAME.substring(1); 
    	if (TYPE.equals("boolean")) { 
    stringBuffer.append(TEXT_45);
    stringBuffer.append(GET_METHOD);
    stringBuffer.append(TEXT_46);
     	} else if (TYPE.equals("int")) { 
    stringBuffer.append(TEXT_47);
    stringBuffer.append(GET_METHOD);
    stringBuffer.append(TEXT_48);
     	} else if (TYPE.equals("byte") || TYPE.equals("char") || TYPE.equals("short")) { 
    stringBuffer.append(TEXT_49);
    stringBuffer.append(GET_METHOD);
    stringBuffer.append(TEXT_50);
     	} else if (TYPE.equals("long")) { 
    stringBuffer.append(TEXT_51);
    stringBuffer.append(GET_METHOD);
    stringBuffer.append(TEXT_52);
    stringBuffer.append(GET_METHOD);
    stringBuffer.append(TEXT_53);
     	} else if (TYPE.equals("double")) { 
    stringBuffer.append(TEXT_54);
    stringBuffer.append(GET_METHOD);
    stringBuffer.append(TEXT_55);
    stringBuffer.append(GET_METHOD);
    stringBuffer.append(TEXT_56);
     	} else if (TYPE.equals("float")) { 
    stringBuffer.append(TEXT_57);
    stringBuffer.append(GET_METHOD);
    stringBuffer.append(TEXT_58);
     	} else { 
    stringBuffer.append(TEXT_59);
    stringBuffer.append(GET_METHOD);
    stringBuffer.append(TEXT_60);
    stringBuffer.append(GET_METHOD);
    stringBuffer.append(TEXT_61);
     	} 
     } 
    stringBuffer.append(TEXT_62);
    stringBuffer.append(TEXT_63);
    return stringBuffer.toString();
  }
}
