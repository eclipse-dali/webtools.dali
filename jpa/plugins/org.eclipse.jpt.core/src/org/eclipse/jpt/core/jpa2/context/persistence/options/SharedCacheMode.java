/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.core.jpa2.context.persistence.options;

import org.eclipse.jpt.core.resource.persistence.v2_0.XmlPersistenceUnitCachingType_2_0;

/**
 *  SharedCacheMode
 */
public enum SharedCacheMode 
{
	ALL,
	NONE,
	ENABLE_SELECTIVE,
	DISABLE_SELECTIVE,
	UNSPECIFIED; 
	

	public static SharedCacheMode fromXmlResourceModel(XmlPersistenceUnitCachingType_2_0 sharedCacheMode) {
		if (sharedCacheMode == null) {
			return null;
		}
		switch (sharedCacheMode) {
			case ALL:
				return ALL;
			case NONE:
				return NONE;
			case ENABLE_SELECTIVE:
				return ENABLE_SELECTIVE;
			case DISABLE_SELECTIVE:
				return DISABLE_SELECTIVE;
			case UNSPECIFIED:
				return UNSPECIFIED;
			default:
				throw new IllegalArgumentException("unknown validation mode: " + sharedCacheMode); //$NON-NLS-1$
		}
	}
	
	public static XmlPersistenceUnitCachingType_2_0 toXmlResourceModel(SharedCacheMode sharedCacheMode) {
		if (sharedCacheMode == null) {
			return null;
		}
		switch (sharedCacheMode) {
			case ALL:
				return XmlPersistenceUnitCachingType_2_0.ALL;
			case NONE:
				return XmlPersistenceUnitCachingType_2_0.NONE;
			case ENABLE_SELECTIVE:
				return XmlPersistenceUnitCachingType_2_0.ENABLE_SELECTIVE;
			case DISABLE_SELECTIVE:
				return XmlPersistenceUnitCachingType_2_0.DISABLE_SELECTIVE;
			case UNSPECIFIED:
				return XmlPersistenceUnitCachingType_2_0.UNSPECIFIED;
			default:
				throw new IllegalArgumentException("unknown shared cache mode: " + sharedCacheMode); //$NON-NLS-1$
		}
	}
}