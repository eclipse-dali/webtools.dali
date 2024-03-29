/*******************************************************************************
 * Copyright (c) 2005, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * Inheritance Type
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public enum InheritanceType {

	SINGLE_TABLE(
			org.eclipse.jpt.jpa.core.resource.java.InheritanceType.SINGLE_TABLE,
			org.eclipse.jpt.jpa.core.resource.orm.InheritanceType.SINGLE_TABLE
		),
	JOINED(
			org.eclipse.jpt.jpa.core.resource.java.InheritanceType.JOINED,
			org.eclipse.jpt.jpa.core.resource.orm.InheritanceType.JOINED
		),
	TABLE_PER_CLASS(
			org.eclipse.jpt.jpa.core.resource.java.InheritanceType.TABLE_PER_CLASS,
			org.eclipse.jpt.jpa.core.resource.orm.InheritanceType.TABLE_PER_CLASS
		);


	private org.eclipse.jpt.jpa.core.resource.java.InheritanceType javaInheritanceType;
	private org.eclipse.jpt.jpa.core.resource.orm.InheritanceType ormInheritanceType;

	InheritanceType(org.eclipse.jpt.jpa.core.resource.java.InheritanceType javaInheritanceType, org.eclipse.jpt.jpa.core.resource.orm.InheritanceType ormInheritanceType) {
		if (javaInheritanceType == null) {
			throw new NullPointerException();
		}
		if (ormInheritanceType == null) {
			throw new NullPointerException();
		}
		this.javaInheritanceType = javaInheritanceType;
		this.ormInheritanceType = ormInheritanceType;
	}

	public org.eclipse.jpt.jpa.core.resource.java.InheritanceType getJavaInheritanceType() {
		return this.javaInheritanceType;
	}

	public org.eclipse.jpt.jpa.core.resource.orm.InheritanceType getOrmInheritanceType() {
		return this.ormInheritanceType;
	}


	// ********** static methods **********

	public static InheritanceType fromJavaResourceModel(org.eclipse.jpt.jpa.core.resource.java.InheritanceType javaInheritanceType) {
		return (javaInheritanceType == null) ? null : fromJavaResourceModel_(javaInheritanceType);
	}

	private static InheritanceType fromJavaResourceModel_(org.eclipse.jpt.jpa.core.resource.java.InheritanceType javaInheritanceType) {
		for (InheritanceType inheritanceType : InheritanceType.values()) {
			if (inheritanceType.getJavaInheritanceType() == javaInheritanceType) {
				return inheritanceType;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.jpa.core.resource.java.InheritanceType toJavaResourceModel(InheritanceType inheritanceType) {
		return (inheritanceType == null) ? null : inheritanceType.getJavaInheritanceType();
	}

	public static InheritanceType fromOrmResourceModel(org.eclipse.jpt.jpa.core.resource.orm.InheritanceType ormInheritanceType) {
		return (ormInheritanceType == null) ? null : fromOrmResourceModel_(ormInheritanceType);
	}

	private static InheritanceType fromOrmResourceModel_(org.eclipse.jpt.jpa.core.resource.orm.InheritanceType ormInheritanceType) {
		for (InheritanceType inheritanceType : InheritanceType.values()) {
			if (inheritanceType.getOrmInheritanceType() == ormInheritanceType) {
				return inheritanceType;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.jpa.core.resource.orm.InheritanceType toOrmResourceModel(InheritanceType inheritanceType) {
		return (inheritanceType == null) ? null : inheritanceType.getOrmInheritanceType();
	}

}
