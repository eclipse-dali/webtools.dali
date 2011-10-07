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
 * corresponds to EclipseLink org.eclipse.persistence.config.CacheIsolationType
 * which was added in EclipseLink 2.2
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
public enum EclipseLinkCacheIsolationType2_2 {

	SHARED,
	PROTECTED,
	ISOLATED;
	

	public static EclipseLinkCacheIsolationType2_2 fromJavaResourceModel(org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheIsolationType2_2 javaCacheIsolationType) {
		if (javaCacheIsolationType == null) {
			return null;
		}
		switch (javaCacheIsolationType) {
			case SHARED:
				return SHARED;
			case PROTECTED:
				return PROTECTED;
			case ISOLATED:
				return ISOLATED;
			default:
				throw new IllegalArgumentException("unknown cache isolation type: " + javaCacheIsolationType); //$NON-NLS-1$
		}
	}

	public static org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheIsolationType2_2 toJavaResourceModel(EclipseLinkCacheIsolationType2_2 cacheIsolationType) {
		if (cacheIsolationType == null) {
			return null;
		}
		switch (cacheIsolationType) {
			case SHARED:
				return org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheIsolationType2_2.SHARED;
			case PROTECTED:
				return org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheIsolationType2_2.PROTECTED;
			case ISOLATED:
				return org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheIsolationType2_2.ISOLATED;
			default:
				throw new IllegalArgumentException("unknown cache isolation type: " + cacheIsolationType); //$NON-NLS-1$
		}
	}
	

	public static EclipseLinkCacheIsolationType2_2 fromOrmResourceModel(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.CacheIsolationType ormCacheIsolationType) {
		if (ormCacheIsolationType == null) {
			return null;
		}
		switch (ormCacheIsolationType) {
			case SHARED:
				return SHARED;
			case PROTECTED:
				return PROTECTED;
			case ISOLATED:
				return ISOLATED;
			default:
				throw new IllegalArgumentException("unknown cache isolation type: " + ormCacheIsolationType); //$NON-NLS-1$
		}
	}
	
	public static org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.CacheIsolationType toOrmResourceModel(EclipseLinkCacheIsolationType2_2 cacheIsolationType) {
		if (cacheIsolationType == null) {
			return null;
		}
		switch (cacheIsolationType) {
			case SHARED:
				return org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.CacheIsolationType.SHARED;
			case PROTECTED:
				return org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.CacheIsolationType.PROTECTED;
			case ISOLATED:
				return org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2.CacheIsolationType.ISOLATED;
			default:
				throw new IllegalArgumentException("unknown cache isolation type: " + cacheIsolationType); //$NON-NLS-1$
		}
	}

}
