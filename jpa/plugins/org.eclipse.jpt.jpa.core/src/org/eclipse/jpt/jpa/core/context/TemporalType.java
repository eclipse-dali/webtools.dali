/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * Temporal Type
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
public enum TemporalType {

	DATE(
			org.eclipse.jpt.jpa.core.resource.java.TemporalType.DATE,
			org.eclipse.jpt.jpa.core.resource.orm.TemporalType.DATE
		),
	TIME(
			org.eclipse.jpt.jpa.core.resource.java.TemporalType.TIME,
			org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIME
		),
	TIMESTAMP(
			org.eclipse.jpt.jpa.core.resource.java.TemporalType.TIMESTAMP,
			org.eclipse.jpt.jpa.core.resource.orm.TemporalType.TIMESTAMP
		);


	private org.eclipse.jpt.jpa.core.resource.java.TemporalType javaTemporalType;
	private org.eclipse.jpt.jpa.core.resource.orm.TemporalType ormTemporalType;

	TemporalType(org.eclipse.jpt.jpa.core.resource.java.TemporalType javaTemporalType, org.eclipse.jpt.jpa.core.resource.orm.TemporalType ormTemporalType) {
		if (javaTemporalType == null) {
			throw new NullPointerException();
		}
		if (ormTemporalType == null) {
			throw new NullPointerException();
		}
		this.javaTemporalType = javaTemporalType;
		this.ormTemporalType = ormTemporalType;
	}

	public org.eclipse.jpt.jpa.core.resource.java.TemporalType getJavaTemporalType() {
		return this.javaTemporalType;
	}

	public org.eclipse.jpt.jpa.core.resource.orm.TemporalType getOrmTemporalType() {
		return this.ormTemporalType;
	}


	// ********** static methods **********

	public static TemporalType fromJavaResourceModel(org.eclipse.jpt.jpa.core.resource.java.TemporalType javaTemporalType) {
		return (javaTemporalType == null) ? null : fromJavaResourceModel_(javaTemporalType);
	}

	private static TemporalType fromJavaResourceModel_(org.eclipse.jpt.jpa.core.resource.java.TemporalType javaTemporalType) {
		for (TemporalType temporalType : TemporalType.values()) {
			if (temporalType.getJavaTemporalType() == javaTemporalType) {
				return temporalType;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.jpa.core.resource.java.TemporalType toJavaResourceModel(TemporalType temporalType) {
		return (temporalType == null) ? null : temporalType.getJavaTemporalType();
	}

	public static TemporalType fromOrmResourceModel(org.eclipse.jpt.jpa.core.resource.orm.TemporalType ormTemporalType) {
		return (ormTemporalType == null) ? null : fromOrmResourceModel_(ormTemporalType);
	}

	private static TemporalType fromOrmResourceModel_(org.eclipse.jpt.jpa.core.resource.orm.TemporalType ormTemporalType) {
		for (TemporalType temporalType : TemporalType.values()) {
			if (temporalType.getOrmTemporalType() == ormTemporalType) {
				return temporalType;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.jpa.core.resource.orm.TemporalType toOrmResourceModel(TemporalType temporalType) {
		return (temporalType == null) ? null : temporalType.getOrmTemporalType();
	}

}
