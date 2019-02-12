/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.JpaPlatform;

/**
 * (Extensible) context model "enum" corresponding to:<ul>
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
 * @version 3.2
 * @since 2.0
 */
public class AccessType {

	public static final AccessType FIELD = new AccessType(
			org.eclipse.jpt.jpa.core.resource.java.AccessType.FIELD,
			org.eclipse.jpt.jpa.core.resource.orm.AccessType.FIELD,
			"Field" //$NON-NLS-1$
		);
	public static final AccessType PROPERTY = new AccessType(
			org.eclipse.jpt.jpa.core.resource.java.AccessType.PROPERTY,
			org.eclipse.jpt.jpa.core.resource.orm.AccessType.PROPERTY,
			"Property" //$NON-NLS-1$
		);


	protected final org.eclipse.jpt.jpa.core.resource.java.AccessType javaAccessType;
	protected final String ormAccessType;
	protected final String displayString;

	protected AccessType(org.eclipse.jpt.jpa.core.resource.java.AccessType javaAccessType, String ormAccessType, String displayString) {
		if ((ormAccessType == null) || (displayString == null)) {
			throw new NullPointerException();
		}
		this.javaAccessType = javaAccessType;
		this.ormAccessType = ormAccessType;
		this.displayString = displayString;
	}

	public org.eclipse.jpt.jpa.core.resource.java.AccessType getJavaAccessType() {
		return this.javaAccessType;
	}

	public String getOrmAccessType() {
		return this.ormAccessType;
	}

	public String getDisplayString() {
		return this.displayString;
	}

	@Override
	public String toString() {
		return this.displayString;
	}


	// ********** static methods **********

	public static AccessType fromJavaResourceModel(org.eclipse.jpt.jpa.core.resource.java.AccessType javaAccessType, JpaPlatform jpaPlatform, JptResourceType resourceType) {
		return (javaAccessType == null) ? null : fromJavaResourceModel_(javaAccessType, jpaPlatform, resourceType);
	}

	private static AccessType fromJavaResourceModel_(org.eclipse.jpt.jpa.core.resource.java.AccessType javaAccessType, JpaPlatform jpaPlatform, JptResourceType resourceType) {
		for (AccessType accessType : jpaPlatform.getJpaVariation().getSupportedAccessTypes(resourceType)) {
			if (accessType.getJavaAccessType() == javaAccessType) {
				return accessType;
			}
		}
		return null;
	}

	public static org.eclipse.jpt.jpa.core.resource.java.AccessType toJavaResourceModel(AccessType accessType) {
		return (accessType == null) ? null : accessType.getJavaAccessType();
	}

	public static AccessType fromOrmResourceModel(String ormAccessType, JpaPlatform jpaPlatform, JptResourceType resourceType) {
		return (ormAccessType == null) ? null : fromOrmResourceModel_(ormAccessType, jpaPlatform, resourceType);
	}

	private static AccessType fromOrmResourceModel_(String ormAccessType, JpaPlatform jpaPlatform, JptResourceType resourceType) {
		for (AccessType accessType : jpaPlatform.getJpaVariation().getSupportedAccessTypes(resourceType)) {
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
