/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
 * @version 2.1
 * @since 2.1
 */
public enum EclipseLinkCacheCoordinationType {

	SEND_OBJECT_CHANGES,
	INVALIDATE_CHANGED_OBJECTS,
	SEND_NEW_OBJECTS_WITH_CHANGES,
	NONE;
	

	public static EclipseLinkCacheCoordinationType fromJavaResourceModel(org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheCoordinationType javaCacheCoordinationType) {
		if (javaCacheCoordinationType == null) {
			return null;
		}
		switch (javaCacheCoordinationType) {
			case SEND_OBJECT_CHANGES:
				return SEND_OBJECT_CHANGES;
			case INVALIDATE_CHANGED_OBJECTS:
				return INVALIDATE_CHANGED_OBJECTS;
			case SEND_NEW_OBJECTS_WITH_CHANGES:
				return SEND_NEW_OBJECTS_WITH_CHANGES;
			case NONE:
				return NONE;
			default:
				throw new IllegalArgumentException("unknown cache coordination type: " + javaCacheCoordinationType); //$NON-NLS-1$
		}
	}

	public static org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheCoordinationType toJavaResourceModel(EclipseLinkCacheCoordinationType cacheCoordinationType) {
		if (cacheCoordinationType == null) {
			return null;
		}
		switch (cacheCoordinationType) {
			case SEND_OBJECT_CHANGES:
				return org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheCoordinationType.SEND_OBJECT_CHANGES;
			case INVALIDATE_CHANGED_OBJECTS:
				return org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS;
			case SEND_NEW_OBJECTS_WITH_CHANGES:
				return org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES;
			case NONE:
				return org.eclipse.jpt.jpa.eclipselink.core.resource.java.CacheCoordinationType.NONE;
			default:
				throw new IllegalArgumentException("unknown cache coordination type: " + cacheCoordinationType); //$NON-NLS-1$
		}
	}
	

	public static EclipseLinkCacheCoordinationType fromOrmResourceModel(org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheCoordinationType ormCacheCoordinationType) {
		if (ormCacheCoordinationType == null) {
			return null;
		}
		switch (ormCacheCoordinationType) {
			case SEND_OBJECT_CHANGES:
				return SEND_OBJECT_CHANGES;
			case INVALIDATE_CHANGED_OBJECTS:
				return INVALIDATE_CHANGED_OBJECTS;
			case SEND_NEW_OBJECTS_WITH_CHANGES:
				return SEND_NEW_OBJECTS_WITH_CHANGES;
			case NONE:
				return NONE;
			default:
				throw new IllegalArgumentException("unknown cache coordination type: " + ormCacheCoordinationType); //$NON-NLS-1$
		}
	}
	
	public static org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheCoordinationType toOrmResourceModel(EclipseLinkCacheCoordinationType cacheCoordinationType) {
		if (cacheCoordinationType == null) {
			return null;
		}
		switch (cacheCoordinationType) {
			case SEND_OBJECT_CHANGES:
				return org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheCoordinationType.SEND_OBJECT_CHANGES;
			case INVALIDATE_CHANGED_OBJECTS:
				return org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheCoordinationType.INVALIDATE_CHANGED_OBJECTS;
			case SEND_NEW_OBJECTS_WITH_CHANGES:
				return org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheCoordinationType.SEND_NEW_OBJECTS_WITH_CHANGES;
			case NONE:
				return org.eclipse.jpt.jpa.eclipselink.core.resource.orm.CacheCoordinationType.NONE;
			default:
				throw new IllegalArgumentException("unknown cache coordination type: " + cacheCoordinationType); //$NON-NLS-1$
		}
	}

}
