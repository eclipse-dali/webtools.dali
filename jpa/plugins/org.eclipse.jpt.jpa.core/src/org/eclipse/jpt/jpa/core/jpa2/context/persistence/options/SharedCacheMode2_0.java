/*******************************************************************************
* Copyright (c) 2009, 2013 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License 2.0, which accompanies this distribution
* and is available at https://www.eclipse.org/legal/epl-2.0/.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context.persistence.options;

import org.eclipse.jpt.jpa.core.resource.persistence.v2_0.XmlPersistenceUnitCachingType_2_0;

/**
 * Context model corresponding to the XML resource model
 * {@link XmlPersistenceUnitCachingType_2_0},
 * which corresponds to the <code>shared-cache-mode</code> element in the
 * <code>persistence.xml</code> file.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.3
 */
public enum SharedCacheMode2_0 
{
	ALL,
	NONE,
	ENABLE_SELECTIVE,
	DISABLE_SELECTIVE,
	UNSPECIFIED; 
	

	public static SharedCacheMode2_0 fromXmlResourceModel(XmlPersistenceUnitCachingType_2_0 sharedCacheMode) {
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
	
	public static XmlPersistenceUnitCachingType_2_0 toXmlResourceModel(SharedCacheMode2_0 sharedCacheMode) {
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