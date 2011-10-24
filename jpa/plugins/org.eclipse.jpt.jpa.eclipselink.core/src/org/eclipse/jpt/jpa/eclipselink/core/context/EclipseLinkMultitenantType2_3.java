/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context;


/**
 * 
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.1
 * @since 3.1
 */
public enum EclipseLinkMultitenantType2_3 {

	SINGLE_TABLE,
	TABLE_PER_TENANT,
	VPD;


	public static EclipseLinkMultitenantType2_3 fromJavaResourceModel(org.eclipse.jpt.jpa.eclipselink.core.resource.java.MultitenantType2_3 javaMultitenantType) {
		if (javaMultitenantType == null) {
			return null;
		}
		switch (javaMultitenantType) {
			case SINGLE_TABLE:
				return SINGLE_TABLE;
			case TABLE_PER_TENANT:
				return TABLE_PER_TENANT;
			case VPD:
				return VPD;
			default:
				throw new IllegalArgumentException("unknown multitenant type: " + javaMultitenantType); //$NON-NLS-1$
		}
	}

	public static org.eclipse.jpt.jpa.eclipselink.core.resource.java.MultitenantType2_3 toJavaResourceModel(EclipseLinkMultitenantType2_3 multitenantType) {
		if (multitenantType == null) {
			return null;
		}
		switch (multitenantType) {
			case SINGLE_TABLE:
				return org.eclipse.jpt.jpa.eclipselink.core.resource.java.MultitenantType2_3.SINGLE_TABLE;
			case TABLE_PER_TENANT:
				return org.eclipse.jpt.jpa.eclipselink.core.resource.java.MultitenantType2_3.TABLE_PER_TENANT;
			case VPD:
				return org.eclipse.jpt.jpa.eclipselink.core.resource.java.MultitenantType2_3.VPD;
			default:
				throw new IllegalArgumentException("unknown multitenant type: " + multitenantType); //$NON-NLS-1$
		}
	}

	public static EclipseLinkMultitenantType2_3 fromOrmResourceModel(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.MultitenantType ormMultitenantType) {
		if (ormMultitenantType == null) {
			return null;
		}
		switch (ormMultitenantType) {
			case SINGLE_TABLE:
				return SINGLE_TABLE;
			case TABLE_PER_TENANT:
				return TABLE_PER_TENANT;
			case VPD:
				return VPD;
			default:
				throw new IllegalArgumentException("unknown multitenant type: " + ormMultitenantType); //$NON-NLS-1$
		}
	}

	public static org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.MultitenantType toOrmResourceModel(EclipseLinkMultitenantType2_3 multitenantType) {
		if (multitenantType == null) {
			return null;
		}
		switch (multitenantType) {
			case SINGLE_TABLE:
				return org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.MultitenantType.SINGLE_TABLE;
			case TABLE_PER_TENANT:
				return org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.MultitenantType.TABLE_PER_TENANT;
			case VPD:
				return org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_3.MultitenantType.VPD;
			default:
				throw new IllegalArgumentException("unknown multitenant type: " + multitenantType); //$NON-NLS-1$
		}
	}

}
