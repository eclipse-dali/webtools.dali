/*******************************************************************************
 * Copyright (c) 2005, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

/**
 * Generation Type
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
public enum GenerationType {

	TABLE(
			org.eclipse.jpt.core.resource.java.GenerationType.TABLE,
			org.eclipse.jpt.core.resource.orm.GenerationType.TABLE
		),
	SEQUENCE(
			org.eclipse.jpt.core.resource.java.GenerationType.SEQUENCE,
			org.eclipse.jpt.core.resource.orm.GenerationType.SEQUENCE
		),
	IDENTITY(
			org.eclipse.jpt.core.resource.java.GenerationType.IDENTITY,
			org.eclipse.jpt.core.resource.orm.GenerationType.IDENTITY
		),
	AUTO(
			org.eclipse.jpt.core.resource.java.GenerationType.AUTO,
			org.eclipse.jpt.core.resource.orm.GenerationType.AUTO
		);


	private org.eclipse.jpt.core.resource.java.GenerationType javaGenerationType;
	private org.eclipse.jpt.core.resource.orm.GenerationType ormGenerationType;

	GenerationType(org.eclipse.jpt.core.resource.java.GenerationType javaGenerationType, org.eclipse.jpt.core.resource.orm.GenerationType ormGenerationType) {
		if (javaGenerationType == null) {
			throw new NullPointerException();
		}
		if (ormGenerationType == null) {
			throw new NullPointerException();
		}
		this.javaGenerationType = javaGenerationType;
		this.ormGenerationType = ormGenerationType;
	}

	public org.eclipse.jpt.core.resource.java.GenerationType getJavaGenerationType() {
		return this.javaGenerationType;
	}

	public org.eclipse.jpt.core.resource.orm.GenerationType getOrmGenerationType() {
		return this.ormGenerationType;
	}


	// ********** static methods **********

	public static GenerationType fromJavaResourceModel(org.eclipse.jpt.core.resource.java.GenerationType javaGenerationType) {
		return (javaGenerationType == null) ? null : fromJavaResourceModel_(javaGenerationType);
	}

	private static GenerationType fromJavaResourceModel_(org.eclipse.jpt.core.resource.java.GenerationType javaGenerationType) {
		for (GenerationType generationType : GenerationType.values()) {
			if (generationType.getJavaGenerationType() == javaGenerationType) {
				return generationType;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.core.resource.java.GenerationType toJavaResourceModel(GenerationType generationType) {
		return (generationType == null) ? null : generationType.getJavaGenerationType();
	}

	public static GenerationType fromOrmResourceModel(org.eclipse.jpt.core.resource.orm.GenerationType ormGenerationType) {
		return (ormGenerationType == null) ? null : fromOrmResourceModel_(ormGenerationType);
	}

	private static GenerationType fromOrmResourceModel_(org.eclipse.jpt.core.resource.orm.GenerationType ormGenerationType) {
		for (GenerationType generationType : GenerationType.values()) {
			if (generationType.getOrmGenerationType() == ormGenerationType) {
				return generationType;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.core.resource.orm.GenerationType toOrmResourceModel(GenerationType generationType) {
		return (generationType == null) ? null : generationType.getOrmGenerationType();
	}

}
