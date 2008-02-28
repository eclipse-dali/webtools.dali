/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public enum AccessType {

	FIELD,
	PROPERTY;


	public static AccessType fromJavaResourceModel(org.eclipse.jpt.core.resource.java.AccessType javaAccessType) {
		if (javaAccessType == null) {
			return null;
		}
		switch (javaAccessType) {
			case FIELD:
				return FIELD;
			case PROPERTY:
				return PROPERTY;
			default:
				throw new IllegalArgumentException("unknown access type: " + javaAccessType);
		}
	}
	
	public static AccessType fromXmlResourceModel(org.eclipse.jpt.core.resource.orm.AccessType ormAccessType) {
		if (ormAccessType == null) {
			return null;
		}
		switch (ormAccessType) {
			case FIELD:
				return FIELD;
			case PROPERTY:
				return PROPERTY;
			default:
				throw new IllegalArgumentException("unknown access type: " + ormAccessType);
		}
	}
	
	public static org.eclipse.jpt.core.resource.orm.AccessType toXmlResourceModel(AccessType accessType) {
		if (accessType == null) {
			return null;
		}
		switch (accessType) {
			case FIELD:
				return org.eclipse.jpt.core.resource.orm.AccessType.FIELD;
			case PROPERTY:
				return org.eclipse.jpt.core.resource.orm.AccessType.PROPERTY;
			default:
				throw new IllegalArgumentException("unknown access type: " + accessType);
		}
	}

}
