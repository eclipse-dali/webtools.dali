/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

/**
 * Access Type
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public enum AccessType {

	FIELD(
			org.eclipse.jpt.core.resource.java.AccessType.FIELD,
			org.eclipse.jpt.core.resource.orm.AccessType.FIELD
		),
	PROPERTY(
			org.eclipse.jpt.core.resource.java.AccessType.PROPERTY,
			org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY
		);


	private org.eclipse.jpt.core.resource.java.AccessType javaAccessType;
	private org.eclipse.jpt.core.resource.orm.AccessType ormAccessType;

	AccessType(org.eclipse.jpt.core.resource.java.AccessType javaAccessType, org.eclipse.jpt.core.resource.orm.AccessType ormAccessType) {
		if (javaAccessType == null) {
			throw new NullPointerException();
		}
		if (ormAccessType == null) {
			throw new NullPointerException();
		}
		this.javaAccessType = javaAccessType;
		this.ormAccessType = ormAccessType;
	}

	public org.eclipse.jpt.core.resource.java.AccessType getJavaAccessType() {
		return this.javaAccessType;
	}

	public org.eclipse.jpt.core.resource.orm.AccessType getOrmAccessType() {
		return this.ormAccessType;
	}


	// ********** static methods **********

	public static AccessType fromJavaResourceModel(org.eclipse.jpt.core.resource.java.AccessType javaAccessType) {
		return (javaAccessType == null) ? null : fromJavaResourceModel_(javaAccessType);
	}

	private static AccessType fromJavaResourceModel_(org.eclipse.jpt.core.resource.java.AccessType javaAccessType) {
		for (AccessType accessType : AccessType.values()) {
			if (accessType.getJavaAccessType() == javaAccessType) {
				return accessType;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.core.resource.java.AccessType toJavaResourceModel(AccessType accessType) {
		return (accessType == null) ? null : accessType.getJavaAccessType();
	}

	public static AccessType fromOrmResourceModel(org.eclipse.jpt.core.resource.orm.AccessType ormAccessType) {
		return (ormAccessType == null) ? null : fromOrmResourceModel_(ormAccessType);
	}

	private static AccessType fromOrmResourceModel_(org.eclipse.jpt.core.resource.orm.AccessType ormAccessType) {
		for (AccessType accessType : AccessType.values()) {
			if (accessType.getOrmAccessType() == ormAccessType) {
				return accessType;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.core.resource.orm.AccessType toOrmResourceModel(AccessType accessType) {
		return (accessType == null) ? null : accessType.getOrmAccessType();
	}

}
