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
 * Discriminator Type
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
public enum DiscriminatorType {

	STRING(
			org.eclipse.jpt.core.resource.java.DiscriminatorType.STRING,
			org.eclipse.jpt.core.resource.orm.DiscriminatorType.STRING
		),
	CHAR(
			org.eclipse.jpt.core.resource.java.DiscriminatorType.CHAR,
			org.eclipse.jpt.core.resource.orm.DiscriminatorType.CHAR
		),
	INTEGER(
			org.eclipse.jpt.core.resource.java.DiscriminatorType.INTEGER,
			org.eclipse.jpt.core.resource.orm.DiscriminatorType.INTEGER
		);


	private org.eclipse.jpt.core.resource.java.DiscriminatorType javaDiscriminatorType;
	private org.eclipse.jpt.core.resource.orm.DiscriminatorType ormDiscriminatorType;

	DiscriminatorType(org.eclipse.jpt.core.resource.java.DiscriminatorType javaDiscriminatorType, org.eclipse.jpt.core.resource.orm.DiscriminatorType ormDiscriminatorType) {
		if (javaDiscriminatorType == null) {
			throw new NullPointerException();
		}
		if (ormDiscriminatorType == null) {
			throw new NullPointerException();
		}
		this.javaDiscriminatorType = javaDiscriminatorType;
		this.ormDiscriminatorType = ormDiscriminatorType;
	}

	public org.eclipse.jpt.core.resource.java.DiscriminatorType getJavaDiscriminatorType() {
		return this.javaDiscriminatorType;
	}

	public org.eclipse.jpt.core.resource.orm.DiscriminatorType getOrmDiscriminatorType() {
		return this.ormDiscriminatorType;
	}


	// ********** static methods **********

	public static DiscriminatorType fromJavaResourceModel(org.eclipse.jpt.core.resource.java.DiscriminatorType javaDiscriminatorType) {
		return (javaDiscriminatorType == null) ? null : fromJavaResourceModel_(javaDiscriminatorType);
	}

	private static DiscriminatorType fromJavaResourceModel_(org.eclipse.jpt.core.resource.java.DiscriminatorType javaDiscriminatorType) {
		for (DiscriminatorType discriminatorType : DiscriminatorType.values()) {
			if (discriminatorType.getJavaDiscriminatorType() == javaDiscriminatorType) {
				return discriminatorType;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.core.resource.java.DiscriminatorType toJavaResourceModel(DiscriminatorType discriminatorType) {
		return (discriminatorType == null) ? null : discriminatorType.getJavaDiscriminatorType();
	}

	public static DiscriminatorType fromOrmResourceModel(org.eclipse.jpt.core.resource.orm.DiscriminatorType ormDiscriminatorType) {
		return (ormDiscriminatorType == null) ? null : fromOrmResourceModel_(ormDiscriminatorType);
	}

	private static DiscriminatorType fromOrmResourceModel_(org.eclipse.jpt.core.resource.orm.DiscriminatorType ormDiscriminatorType) {
		for (DiscriminatorType discriminatorType : DiscriminatorType.values()) {
			if (discriminatorType.getOrmDiscriminatorType() == ormDiscriminatorType) {
				return discriminatorType;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.core.resource.orm.DiscriminatorType toOrmResourceModel(DiscriminatorType discriminatorType) {
		return (discriminatorType == null) ? null : discriminatorType.getOrmDiscriminatorType();
	}

}
