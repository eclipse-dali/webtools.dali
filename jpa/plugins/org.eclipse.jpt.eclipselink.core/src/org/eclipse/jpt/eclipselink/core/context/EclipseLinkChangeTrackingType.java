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

import org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingType;


/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */
public enum EclipseLinkChangeTrackingType {

	ATTRIBUTE,
	OBJECT,
	DEFERRED,
	AUTO;
	

	public static EclipseLinkChangeTrackingType fromJavaResourceModel(org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType javaChangeTrackingType) {
		if (javaChangeTrackingType == null) {
			return null;
		}
		switch (javaChangeTrackingType) {
			case ATTRIBUTE:
				return ATTRIBUTE;
			case OBJECT:
				return OBJECT;
			case DEFERRED:
				return DEFERRED;
			case AUTO:
				return AUTO;
			default:
				throw new IllegalArgumentException("unknown change tracking type: " + javaChangeTrackingType); //$NON-NLS-1$
		}
	}

	public static org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType toJavaResourceModel(EclipseLinkChangeTrackingType changeTrackingType) {
		if (changeTrackingType == null) {
			return null;
		}
		switch (changeTrackingType) {
			case ATTRIBUTE:
				return org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.ATTRIBUTE;
			case OBJECT:
				return org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.OBJECT;
			case DEFERRED:
				return org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.DEFERRED;
			case AUTO:
				return org.eclipse.jpt.eclipselink.core.resource.java.ChangeTrackingType.AUTO;
			default:
				throw new IllegalArgumentException("unknown change tracking type: " + changeTrackingType); //$NON-NLS-1$
		}
	}
	
	public static EclipseLinkChangeTrackingType fromOrmResourceModel(XmlChangeTrackingType ormChangeTrackingType) {
		if (ormChangeTrackingType == null) {
			return null;
		}
		switch (ormChangeTrackingType) {
			case ATTRIBUTE:
				return ATTRIBUTE;
			case OBJECT:
				return OBJECT;
			case DEFERRED:
				return DEFERRED;
			case AUTO:
				return AUTO;
			default:
				throw new IllegalArgumentException("unknown change tracking type: " + ormChangeTrackingType); //$NON-NLS-1$
		}
	}
	
	public static XmlChangeTrackingType toOrmResourceModel(EclipseLinkChangeTrackingType changeTrackingType) {
		if (changeTrackingType == null) {
			return null;
		}
		switch (changeTrackingType) {
			case ATTRIBUTE:
				return XmlChangeTrackingType.ATTRIBUTE;
			case OBJECT:
				return XmlChangeTrackingType.OBJECT;
			case DEFERRED:
				return XmlChangeTrackingType.DEFERRED;
			case AUTO:
				return XmlChangeTrackingType.AUTO;
			default:
				throw new IllegalArgumentException("unknown change tracking type: " + changeTrackingType); //$NON-NLS-1$
		}
	}
}
