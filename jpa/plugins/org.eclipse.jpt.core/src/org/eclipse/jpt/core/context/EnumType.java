/*******************************************************************************
 * Copyright (c) 2005, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

/**
 * Enum Type
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public enum EnumType {

	ORDINAL(
			org.eclipse.jpt.core.resource.java.EnumType.ORDINAL,
			org.eclipse.jpt.core.resource.orm.EnumType.ORDINAL
		),
	STRING(
			org.eclipse.jpt.core.resource.java.EnumType.STRING,
			org.eclipse.jpt.core.resource.orm.EnumType.STRING
		);


	private org.eclipse.jpt.core.resource.java.EnumType javaEnumType;
	private org.eclipse.jpt.core.resource.orm.EnumType ormEnumType;

	EnumType(org.eclipse.jpt.core.resource.java.EnumType javaEnumType, org.eclipse.jpt.core.resource.orm.EnumType ormEnumType) {
		if (javaEnumType == null) {
			throw new NullPointerException();
		}
		if (ormEnumType == null) {
			throw new NullPointerException();
		}
		this.javaEnumType = javaEnumType;
		this.ormEnumType = ormEnumType;
	}

	public org.eclipse.jpt.core.resource.java.EnumType getJavaEnumType() {
		return this.javaEnumType;
	}

	public org.eclipse.jpt.core.resource.orm.EnumType getOrmEnumType() {
		return this.ormEnumType;
	}


	// ********** static methods **********

	public static EnumType fromJavaResourceModel(org.eclipse.jpt.core.resource.java.EnumType javaEnumType) {
		return (javaEnumType == null) ? null : fromJavaResourceModel_(javaEnumType);
	}

	private static EnumType fromJavaResourceModel_(org.eclipse.jpt.core.resource.java.EnumType javaEnumType) {
		for (EnumType enumType : EnumType.values()) {
			if (enumType.getJavaEnumType() == javaEnumType) {
				return enumType;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.core.resource.java.EnumType toJavaResourceModel(EnumType enumType) {
		return (enumType == null) ? null : enumType.getJavaEnumType();
	}

	public static EnumType fromOrmResourceModel(org.eclipse.jpt.core.resource.orm.EnumType ormEnumType) {
		return (ormEnumType == null) ? null : fromOrmResourceModel_(ormEnumType);
	}

	private static EnumType fromOrmResourceModel_(org.eclipse.jpt.core.resource.orm.EnumType ormEnumType) {
		for (EnumType enumType : EnumType.values()) {
			if (enumType.getOrmEnumType() == ormEnumType) {
				return enumType;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.core.resource.orm.EnumType toOrmResourceModel(EnumType enumType) {
		return (enumType == null) ? null : enumType.getOrmEnumType();
	}

}
