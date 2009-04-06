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
 * Fetch Type
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public enum FetchType {

	EAGER(
			org.eclipse.jpt.core.resource.java.FetchType.EAGER,
			org.eclipse.jpt.core.resource.orm.FetchType.EAGER
		),
	LAZY(
			org.eclipse.jpt.core.resource.java.FetchType.LAZY,
			org.eclipse.jpt.core.resource.orm.FetchType.LAZY
		);


	private org.eclipse.jpt.core.resource.java.FetchType javaFetchType;
	private org.eclipse.jpt.core.resource.orm.FetchType ormFetchType;

	FetchType(org.eclipse.jpt.core.resource.java.FetchType javaFetchType, org.eclipse.jpt.core.resource.orm.FetchType ormFetchType) {
		if (javaFetchType == null) {
			throw new NullPointerException();
		}
		if (ormFetchType == null) {
			throw new NullPointerException();
		}
		this.javaFetchType = javaFetchType;
		this.ormFetchType = ormFetchType;
	}

	public org.eclipse.jpt.core.resource.java.FetchType getJavaFetchType() {
		return this.javaFetchType;
	}

	public org.eclipse.jpt.core.resource.orm.FetchType getOrmFetchType() {
		return this.ormFetchType;
	}


	// ********** static methods **********

	public static FetchType fromJavaResourceModel(org.eclipse.jpt.core.resource.java.FetchType javaFetchType) {
		return (javaFetchType == null) ? null : fromJavaResourceModel_(javaFetchType);
	}

	private static FetchType fromJavaResourceModel_(org.eclipse.jpt.core.resource.java.FetchType javaFetchType) {
		for (FetchType fetchType : FetchType.values()) {
			if (fetchType.getJavaFetchType() == javaFetchType) {
				return fetchType;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.core.resource.java.FetchType toJavaResourceModel(FetchType fetchType) {
		return (fetchType == null) ? null : fetchType.getJavaFetchType();
	}

	public static FetchType fromOrmResourceModel(org.eclipse.jpt.core.resource.orm.FetchType ormFetchType) {
		return (ormFetchType == null) ? null : fromOrmResourceModel_(ormFetchType);
	}

	private static FetchType fromOrmResourceModel_(org.eclipse.jpt.core.resource.orm.FetchType ormFetchType) {
		for (FetchType fetchType : FetchType.values()) {
			if (fetchType.getOrmFetchType() == ormFetchType) {
				return fetchType;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.core.resource.orm.FetchType toOrmResourceModel(FetchType fetchType) {
		return (fetchType == null) ? null : fetchType.getOrmFetchType();
	}

}
