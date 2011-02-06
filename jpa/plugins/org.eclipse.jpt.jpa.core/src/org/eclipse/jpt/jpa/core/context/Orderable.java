/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * Multi-valued (1:m, m:m) relationship mappings support ordering.
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
public interface Orderable
	extends JpaContextNode
{
	AttributeMapping getParent();

	String getSpecifiedOrderBy();
	void setSpecifiedOrderBy(String orderBy);
		String SPECIFIED_ORDER_BY_PROPERTY = "specifiedOrderBy"; //$NON-NLS-1$
	
	boolean isNoOrdering();
	void setNoOrdering(boolean noOrdering);
		String NO_ORDERING_PROPERTY = "noOrdering"; //$NON-NLS-1$
	
	boolean isPkOrdering();
	void setPkOrdering(boolean pkOrdering);
		String PK_ORDERING_PROPERTY = "pkOrdering"; //$NON-NLS-1$
	
	boolean isCustomOrdering();
	void setCustomOrdering(boolean customOrdering);
		String CUSTOM_ORDERING_PROPERTY = "customOrdering"; //$NON-NLS-1$
}
