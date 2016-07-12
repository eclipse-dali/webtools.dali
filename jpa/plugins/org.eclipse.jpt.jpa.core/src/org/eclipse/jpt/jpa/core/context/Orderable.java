/*******************************************************************************
 * Copyright (c) 2006, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;

/**
 * Multi-valued (1:m, m:m) relationship mappings support ordering.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.4
 * @since 2.3
 */
public interface Orderable
		extends JpaContextModel {
	
	AttributeMapping getParent();
	
	
	// ***** no ordering *****
	
	/**
	 * String associated with changes to the "noOrdering" property
	 */
	String NO_ORDERING_PROPERTY = "noOrdering"; //$NON-NLS-1$
	
	/**
	 * Will have no ordering if no other metadata is present
	 */
	boolean isNoOrdering();
	
	/**
	 * Will set noOrdering to true (will remove all other metadata)
	 */
	void setNoOrdering();
	
	
	// ***** order by *****
	
	/**
	 * String associated with changes to the "orderByOrdering" property
	 */
	String ORDER_BY_ORDERING_PROPERTY = "orderByOrdering"; //$NON-NLS-1$
	
	/**
	 * If true, will have orderBy metadata that takes precedence over other metadata
	 */
	boolean isOrderByOrdering();
	
	/**
	 * Will set orderByOrdering to true 
	 * (will remove all other metadata, and will set orderBy to null)
	 */
	void setOrderByOrdering();
	
	/**
	 * Return the orderBy object.
	 * This will never be null.
	 */
	OrderBy getOrderBy();
		Transformer<Orderable, OrderBy> ORDER_BY_TRANSFORMER = new OrderByTransformer();
	class OrderByTransformer
		extends TransformerAdapter<Orderable, OrderBy>
	{
		@Override
		public OrderBy transform(Orderable orderable) {
			return orderable.getOrderBy();
		}
	}
}
