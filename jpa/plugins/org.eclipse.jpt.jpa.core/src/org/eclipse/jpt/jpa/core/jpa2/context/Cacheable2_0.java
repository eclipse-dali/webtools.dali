/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpa2.context;

import org.eclipse.jpt.jpa.core.context.JpaContextModel;

/**
 * cacheable
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
public interface Cacheable2_0
	extends JpaContextModel
{
	boolean isCacheable();
	
	Boolean getSpecifiedCacheable();
	void setSpecifiedCacheable(Boolean cacheable);
		String SPECIFIED_CACHEABLE_PROPERTY = "specifiedCacheable"; //$NON-NLS-1$
	
	boolean isDefaultCacheable();
		String DEFAULT_CACHEABLE_PROPERTY = "defaultCacheable"; //$NON-NLS-1$
}
