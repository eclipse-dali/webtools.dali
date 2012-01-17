/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * Context model corresponding to:<ul>
 * <li>the XML resource model
 * {@link org.eclipse.jpt.jpa.core.resource.orm.AccessType},
 * which corresponds to the <code>access</code> element in the
 * <code>orm.xml</code> file.
 * <li>the Java resource model {@link org.eclipse.jpt.jpa.core.resource.java.AccessType}
 * which corresponds to the <code>javax.persistence.Access</code> annotation.
 * </ul>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public enum AccessType {

	FIELD(
			org.eclipse.jpt.jpa.core.resource.java.AccessType.FIELD,
			org.eclipse.jpt.jpa.core.resource.orm.AccessType.FIELD
		),
	PROPERTY(
			org.eclipse.jpt.jpa.core.resource.java.AccessType.PROPERTY,
			org.eclipse.jpt.jpa.core.resource.orm.AccessType.PROPERTY
		);


	private org.eclipse.jpt.jpa.core.resource.java.AccessType javaAccessType;
	private String ormAccessType;

	AccessType(org.eclipse.jpt.jpa.core.resource.java.AccessType javaAccessType, String ormAccessType) {
		if (javaAccessType == null) {
			throw new NullPointerException();
		}
		if (ormAccessType == null) {
			throw new NullPointerException();
		}
		this.javaAccessType = javaAccessType;
		this.ormAccessType = ormAccessType;
	}

	public org.eclipse.jpt.jpa.core.resource.java.AccessType getJavaAccessType() {
		return this.javaAccessType;
	}

	public String getOrmAccessType() {
		return this.ormAccessType;
	}


	// ********** static methods **********

	public static AccessType fromJavaResourceModel(org.eclipse.jpt.jpa.core.resource.java.AccessType javaAccessType) {
		return (javaAccessType == null) ? null : fromJavaResourceModel_(javaAccessType);
	}

	private static AccessType fromJavaResourceModel_(org.eclipse.jpt.jpa.core.resource.java.AccessType javaAccessType) {
		for (AccessType accessType : AccessType.values()) {
			if (accessType.getJavaAccessType() == javaAccessType) {
				return accessType;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.jpa.core.resource.java.AccessType toJavaResourceModel(AccessType accessType) {
		return (accessType == null) ? null : accessType.getJavaAccessType();
	}

	public static AccessType fromOrmResourceModel(String ormAccessType) {
		return (ormAccessType == null) ? null : fromOrmResourceModel_(ormAccessType);
	}

	private static AccessType fromOrmResourceModel_(String ormAccessType) {
		for (AccessType accessType : AccessType.values()) {
			if (accessType.getOrmAccessType().equals(ormAccessType)) {
				return accessType;
			}
		}
		return null;
	}

	public static String toOrmResourceModel(AccessType accessType) {
		return (accessType == null) ? null : accessType.getOrmAccessType();
	}

}
