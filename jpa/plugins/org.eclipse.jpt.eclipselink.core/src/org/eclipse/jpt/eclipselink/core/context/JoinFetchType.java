/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context;

import org.eclipse.jpt.eclipselink.core.resource.orm.XmlJoinFetchType;

/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public enum JoinFetchType {

	INNER,
	OUTER;
	

	public static JoinFetchType fromJavaResourceModel(org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType javaJoinFetchType) {
		if (javaJoinFetchType == null) {
			return null;
		}
		switch (javaJoinFetchType) {
			case INNER:
				return INNER;
			case OUTER:
				return OUTER;
			default:
				throw new IllegalArgumentException("unknown join fetch type: " + javaJoinFetchType); //$NON-NLS-1$
		}
	}

	public static org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType toJavaResourceModel(JoinFetchType joinFetchType) {
		if (joinFetchType == null) {
			return null;
		}
		switch (joinFetchType) {
			case INNER:
				return org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.INNER;
			case OUTER:
				return org.eclipse.jpt.eclipselink.core.resource.java.JoinFetchType.OUTER;
			default:
				throw new IllegalArgumentException("unknown join fetch type: " + joinFetchType); //$NON-NLS-1$
		}
	}
	

	public static JoinFetchType fromOrmResourceModel(XmlJoinFetchType ormJoinFetchType) {
		if (ormJoinFetchType == null) {
			return null;
		}
		switch (ormJoinFetchType) {
			case INNER:
				return INNER;
			case OUTER:
				return OUTER;
			default:
				throw new IllegalArgumentException("unknown join fetch type: " + ormJoinFetchType); //$NON-NLS-1$
		}
	}
	
	public static XmlJoinFetchType toOrmResourceModel(JoinFetchType fetchType) {
		if (fetchType == null) {
			return null;
		}
		switch (fetchType) {
			case INNER:
				return XmlJoinFetchType.INNER;
			case OUTER:
				return XmlJoinFetchType.OUTER;
			default:
				throw new IllegalArgumentException("unknown join fetch type: " + fetchType); //$NON-NLS-1$
		}
	}

}
