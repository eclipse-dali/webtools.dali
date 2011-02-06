/***********************************************************************
 * Copyright (c) 2008, 2009 by SAP AG, Walldorf. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     SAP AG - initial API and implementation
 *     Dimiter Dimitrov, d.dimitrov@sap.com - initial API and implementation
 ***********************************************************************/
package org.eclipse.jpt.jpa.ui.internal.wizards.entity.data.model;

import java.util.Arrays;
import java.util.List;

public class EntityRow {

	private static final String DOT = ".";
	private static final char BRACKET_SQUARE = '[';
	private static final char BRACKET_ANGULAR = '<';
	private static final String PACKAGE_JAVA_LANG = "java.lang.";
	private boolean key = false;
	private String name = "";
	private String type = "";
	private String fqnTypeName = "";

	private final static String[] PK_TYPES = {"int", "long", "short", "char", "boolean", "byte", "double", "float", 
		"java.lang.String", "java.sql.Date", "java.util.Date", "java.lang.Integer", "java.lang.Long", "java.lang.Short",
		"java.lang.Character", "java.lang.Boolean", "java.lang.Byte", "java.lang.Double", "java.lang.Float"};
	
	private final static String[] PK_TYPES_SHORT = { "String", "Integer", "Long", "Short", "Character", "Boolean", 
		 "Byte", "Double", "Float" };

	private final static List<String> VALID_PK_TYPES = Arrays.asList(PK_TYPES);
	private final static List<String> VALID_PK_TYPES_SHORT = Arrays.asList(PK_TYPES_SHORT);
	
	/**
	 * @return whether the presented entity field is primary key or part of composite primary key
	 */
	public boolean isKey() {
		return key;
	}

	/**
	 * Sets the presented entity field to be primary key (or part of composite primari key)
	 * 
	 * @param key 
	 */
	public void setKey(boolean key) {
		this.key = key;
	}
		
	/**
	 * Check whether the type of the entity is allowed to be primary key.
	 * The limitation in the current implementation is that Embedded PK are not checked
	 * @return whether the type of field could be used as primary key
	 */
	public boolean couldBeKey() {
		boolean result = false;
		result = VALID_PK_TYPES.contains(getFqnTypeName());		
		return result;
	}

	/**
	 * @return the name of the entity field
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of the presented entity field
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type (as a simple name) of the entity field
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the type (as a simple name) of the entity field
	 * 
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the type (as fully qualified name) of the entity field
	 */
	public String getFqnTypeName() {
		return fqnTypeName;
	}
	
	private String removeSpaces(String str) {
		str = str.trim();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (!Character.isWhitespace(c)) 
				sb.append(c);
		}
		return sb.toString();
	}
	
	private String getBasicFQN(String fqn) {
		String res;
		int bsIndex = fqn.indexOf(BRACKET_SQUARE);
		int baIndex = fqn.indexOf(BRACKET_ANGULAR);
		if (bsIndex == -1) {
			if (baIndex == -1) res = fqn;
			else res = fqn.substring(0, baIndex);
		} else {
			if (baIndex == -1) res = fqn.substring(0, bsIndex);
			else res = fqn.substring(0, Math.max(bsIndex, baIndex));
		}
		return res;
	}

	/**
	 * Sets the fully qualified type name of the entity field
	 * 
	 * @param fqnTypeName
	 */
	public void setFqnTypeName(String fqnTypeName) {
		fqnTypeName = removeSpaces(fqnTypeName);
		String fqnBasicTypeName = getBasicFQN(fqnTypeName);
		if (fqnBasicTypeName.indexOf(DOT) == -1) {
			if (VALID_PK_TYPES_SHORT.contains(fqnBasicTypeName)) {
				this.fqnTypeName = PACKAGE_JAVA_LANG + fqnTypeName;
				setType(fqnTypeName);
			} else {
				this.fqnTypeName = fqnTypeName;
				setType(fqnTypeName);
			}			
		} else {
			this.fqnTypeName = fqnTypeName;
			setType(getSimpleName(fqnTypeName));
		}		
	}

	/**
	 * @return whether the type of the entity field is boolean. The information could be used 
	 * when the name of getter should be constructed
	 */
	public boolean isBoolean() {
		return "boolean".equals(getType());
	}
	
	/**
	 * For internal purpose only
	 * Convert fully qualified name to the simple one
	 * @param fullyName
	 * @return the simple name form the fully qualified name parameter(last segment)
	 */
	private String getSimpleName(String fullyName) {
		return fullyName.substring(fullyName.lastIndexOf(DOT) + 1);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((fqnTypeName == null) ? 0 : fqnTypeName.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * Implement equals, depending from name of the entity field and its type.
	 * The type is presented from the fully qualified name
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final EntityRow other = (EntityRow) obj;
		if (fqnTypeName == null) {
			if (other.fqnTypeName != null)
				return false;
		} else if (!fqnTypeName.equals(other.fqnTypeName))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}
