/***********************************************************************
 * Copyright (c) 2008 by SAP AG, Walldorf. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     SAP AG - initial API and implementation
 *     Dimiter Dimitrov, d.dimitrov@sap.com - initial API and implementation
 ***********************************************************************/
package org.eclipse.jpt.ui.internal.wizards.entity.data.model;

import java.util.Arrays;
import java.util.List;

public class EntityRow {

	private static final String DOT = ".";
	private boolean key = false;
	private String name = "";
	private String type = "";
	private String fqnTypeName = "";
	private boolean isSimpleType = false;

	private final static String[] PK_TYPES = {"int", "long", "short", "char", "boolean", "byte", "double", "float", 
		"java.lang.String", "java.sql.Date", "java.util.Date", "java.lang.Integer", "java.lang.Long", "java.lang.Short",
		"java.lang.Character", "java.lang.Boolean", "java.lang.Byte", "java.lang.Double", "java.lang.Float"};
	
	private final static List<String> VALID_PK_TYPES = Arrays.asList(PK_TYPES);
	
	
	/**
	 * Constructs <code>EntityColumn</code>.
	 */
	public EntityRow() {
		super();
	}

	
	/**
	 * 
	 * Constructs <code>EntityColumn</code> with the following parameters
	 * 
	 * @param fqnTypeName - fully qualified name of the entity field type
	 * @param name - name of the entity field
	 * @param isKey - flag which indicates whether the entity field is primary key or part of composite primary key
	 */
	public EntityRow(String fqnTypeName, String name, boolean isKey) {
		super();
		this.fqnTypeName = type;
		this.name = name;
		this.key = isKey;
		if (fqnTypeName.indexOf(DOT) == -1) {
			type = fqnTypeName;
			isSimpleType = true;
		} else {
			type = getSimpleName(fqnTypeName);
		}
	}
	
	
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

	/**
	 * Sets the fully qualified type name of the entity field
	 * 
	 * @param fqnTypeName
	 */
	public void setFqnTypeName(String fqnTypeName) {
		this.fqnTypeName = fqnTypeName;
		if (fqnTypeName.indexOf(DOT) == -1) {
			setType(fqnTypeName);
			setSimpleType(true);
		} else {
			setType(getSimpleName(fqnTypeName));
		}
		
	}

	/**
	 * @return is the type of the entity field is primitive type
	 */
	public boolean isSimpleType() {
		return isSimpleType;
	}

	/**
	 * Sets the flag which indicate the type of the entity field as primitive type
	 * 
	 * @param isSimpleType
	 */
	public void setSimpleType(boolean isSimpleType) {
		this.isSimpleType = isSimpleType;
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
