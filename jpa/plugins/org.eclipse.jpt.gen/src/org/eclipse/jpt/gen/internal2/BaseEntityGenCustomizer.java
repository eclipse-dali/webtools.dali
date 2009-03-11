/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal2;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.gen.internal2.util.DTPUtil;
import org.eclipse.jpt.gen.internal2.util.StringUtil;

/**
 * Default implementation of ORMGenCustomizer according to JPA specification 
 * for entity generation.
 * 
 * This is used to retrieve/change the properties from the wizard
 * and is also passed as a context object to Velocity.
 */
public class BaseEntityGenCustomizer extends ORMGenCustomizer implements java.io.Serializable
{
	private final static long serialVersionUID = 1;

	/*mapping kinds*/
	private static final String BASIC_MAPPING_KIND = "basic";
	private static final String ID_MAPPING_KIND = "id";
	private static final String VERSION_MAPPING_KIND = "version";

	
	/*the strings for generator names. These appear in a combo box 
	 * and used by the Velocity template processing.*/
	private static final String AUTO_GENERATOR = "auto";
	private static final String NONE_GENERATOR = "none";
	private static final String IDENTITY_GENERATOR = "identity";
	private static final String SEQUENCE_GENERATOR = "sequence";
	private static final String TABLE_GENERATOR = "table";
	
	public BaseEntityGenCustomizer(){}
	
	public void init(File file, Schema schema) {
		super.init(file, schema);
	}
	
	//-----------------------------------------
	//------ ORMGenCustomizer methods
	//-----------------------------------------
	public List<String> getAllIdGenerators() {
		List<String> result = new java.util.ArrayList<String>(5);
		/*add in the order in which they would appear in the combo*/
		result.add(AUTO_GENERATOR);
		result.add(IDENTITY_GENERATOR);
		result.add(SEQUENCE_GENERATOR);
		result.add(TABLE_GENERATOR);
		result.add(NONE_GENERATOR);
		return result;
	}
	public String getNoIdGenerator() {
		return NONE_GENERATOR;
	}
	public String getIdentityIdGenerator() {
		return IDENTITY_GENERATOR;
	}
	public Set<String> getSequenceIdGenerators() {
		Set<String> result = new java.util.HashSet<String>(3);
		result.add(AUTO_GENERATOR); 
		result.add(SEQUENCE_GENERATOR);
		
		return result;
	}
	public String getPropertyTypeFromColumn( Column column) {
		return DTPUtil.getJavaType( getSchema(), column );
	}
	public String[] getAllPropertyTypes() {
		/*
		 * Java primitive types, wrapper of the primitive types
		 * , java.lang.String, java.math.BigInteger,
		 * java.math.BigDecimal, java.util.Date, java.util.Calendar, java.sql.Date,
		 * java.sql.Time, java.sql.Timestamp, byte[], Byte[], char[], Character[],
		 * enums, and any other type that implements Serializable.
		 * */
		//return in the order that will be used in the combo
		return new String[] {
				"boolean"
				, "Boolean"
				, "byte"
				, "Byte"
				, "byte[]"
				, "char"
				, "char[]"
				, "Character"
				, "Character[]"
				, "double"
				, "Double"
				, "float"
				, "Float"
				, "int"
				, "Integer"
				, "long"
				, "Long"
				, "Object"
				, "short"
				, "Short"
				, "String"
				
				, java.math.BigDecimal.class.getName()
				, java.math.BigInteger.class.getName()
				, java.util.Calendar.class.getName()
				, java.util.Date.class.getName()
				, java.sql.Date.class.getName()
				, java.sql.Time.class.getName()
				, java.sql.Timestamp.class.getName()				
				};
	}
	public String[] getAllMappingKinds() {
		return new String[] {
				BASIC_MAPPING_KIND
				, ID_MAPPING_KIND
				, VERSION_MAPPING_KIND
		};
	}
	/* (non-Javadoc)
	 */
	public String getBasicMappingKind() {
		return BASIC_MAPPING_KIND;
	}
	/* (non-Javadoc)
	 */
	public String getIdMappingKind() {
		return ID_MAPPING_KIND;
	}
	public boolean editCascade(AssociationRole role) {
		return false;
	}

	protected boolean manySideIsAssociationOwner() {
		return true;
	}

	//-----------------------------------------
	//---- Velocity templates methods
	//-----------------------------------------
	/**
	 * Returns the cascades annotation member value, or empty string 
	 * if none.
	 * Empty string is returned instead of null because Velocity does not like null 
	 * when used in #set.
	 */
	public String genCascades(AssociationRole role) {
		List<String> cascades = StringUtil.strToList(role.getCascade(), ',', true/*trim*/);
		if (cascades == null) {
			return "";
		}
		StringBuffer buffer = new StringBuffer("{");
		for (int i = 0, n = cascades.size(); i < n; ++i) {
			String cascade = (String)cascades.get(i);
			String enumStr;
			if (cascade.equals(TagNames.ALL_CASCADE)) {
				enumStr = "CascadeType.ALL";
			} else if (cascade.equals(TagNames.PERSIST_CASCADE)) {
				enumStr = "CascadeType.PERSIST";
			} else if (cascade.equals(TagNames.MERGE_CASCADE)) {
				enumStr = "CascadeType.MERGE";
			} else if (cascade.equals(TagNames.REMOVE_CASCADE)) {
				enumStr = "CascadeType.REMOVE";
			} else {
				assert(cascade.equals(TagNames.REFRESH_CASCADE));
				enumStr = "CascadeType.REFRESH";
			}
			if (i != 0) {
				buffer.append(", ");
			}
			buffer.append(enumStr);
		}
		buffer.append('}');
		return buffer.toString();
	}
	/**
	 * Returns the fetch type annotation member value, or empty string 
	 * if none.
	 * Empty string is returned instead of null because Velocity does not like null 
	 * when used in #set.
	 */
	public String genFetch(ORMGenTable table) {
		String fetch = table.getDefaultFetch();
		if (fetch == null) {
			return "";
		} else if (fetch.equals(ORMGenTable.LAZY_FETCH)) {
			return "FetchType.LAZY";
		} else {
			return "FetchType.EAGER";
		}
	}
}
