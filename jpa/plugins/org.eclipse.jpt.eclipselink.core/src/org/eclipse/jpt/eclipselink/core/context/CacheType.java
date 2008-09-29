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
public enum CacheType {

	SOFT_WEAK,
	HARD_WEAK,
	WEAK,
	SOFT,
	FULL,
	CACHE,
	NONE;
	

	public static CacheType fromJavaResourceModel(org.eclipse.jpt.eclipselink.core.resource.java.CacheType javaCacheType) {
		if (javaCacheType == null) {
			return null;
		}
		switch (javaCacheType) {
			case FULL:
				return FULL;
			case WEAK:
				return WEAK;
			case SOFT:
				return SOFT;
			case SOFT_WEAK:
				return SOFT_WEAK;
			case HARD_WEAK:
				return HARD_WEAK;
			case CACHE:
				return CACHE;
			case NONE:
				return NONE;
			default:
				throw new IllegalArgumentException("unknown cache type: " + javaCacheType); //$NON-NLS-1$
		}
	}

	public static org.eclipse.jpt.eclipselink.core.resource.java.CacheType toJavaResourceModel(CacheType cacheType) {
		if (cacheType == null) {
			return null;
		}
		switch (cacheType) {
			case FULL:
				return org.eclipse.jpt.eclipselink.core.resource.java.CacheType.FULL;
			case WEAK:
				return org.eclipse.jpt.eclipselink.core.resource.java.CacheType.WEAK;
			case SOFT:
				return org.eclipse.jpt.eclipselink.core.resource.java.CacheType.SOFT;
			case SOFT_WEAK:
				return org.eclipse.jpt.eclipselink.core.resource.java.CacheType.SOFT_WEAK;
			case HARD_WEAK:
				return org.eclipse.jpt.eclipselink.core.resource.java.CacheType.HARD_WEAK;
			case CACHE:
				return org.eclipse.jpt.eclipselink.core.resource.java.CacheType.CACHE;
			case NONE:
				return org.eclipse.jpt.eclipselink.core.resource.java.CacheType.NONE;
			default:
				throw new IllegalArgumentException("unknown cache type: " + cacheType); //$NON-NLS-1$
		}
	}
	

//	public static CacheType fromOrmResourceModel(org.eclipse.jpt.core.resource.orm.FetchType ormCacheType) {
//		if (ormCacheType == null) {
//			return null;
//		}
//		switch (ormCacheType) {
//			case FULL:
//				return FULL;
//			case WEAK:
//				return WEAK;
//			case SOFT:
//				return SOFT;
//			case SOFT_WEAK:
//				return SOFT_WEAK;
//			case HARD_WEAK:
//				return HARD_WEAK;
//			case CACHE:
//				return CACHE;
//			case NONE:
//				return NONE;
//			default:
//				throw new IllegalArgumentException("unknown cache type: " + ormCacheType);
//		}
//	}
//	
//	public static org.eclipse.jpt.core.resource.orm.FetchType toOrmResourceModel(CacheType cacheType) {
//		if (cacheType == null) {
//			return null;
//		}
//		switch (cacheType) {
//			case FULL:
//				return org.eclipse.jpt.core.resource.orm.FetchType.FULL;
//			case WEAK:
//				return org.eclipse.jpt.core.resource.orm.FetchType.WEAK;
//			case SOFT:
//				return org.eclipse.jpt.core.resource.orm.FetchType.SOFT;
//			case SOFT_WEAK:
//				return org.eclipse.jpt.core.resource.orm.FetchType.SOFT_WEAK;
//			case HARD_WEAK:
//				return org.eclipse.jpt.core.resource.orm.FetchType.HARD_WEAK;
//			case CACHE:
//				return org.eclipse.jpt.core.resource.orm.FetchType.CACHE;
//			case NONE:
//				return org.eclipse.jpt.core.resource.orm.FetchType.NONE;
//			default:
//				throw new IllegalArgumentException("unknown cache type: " + cacheType);
//		}
//	}

}
