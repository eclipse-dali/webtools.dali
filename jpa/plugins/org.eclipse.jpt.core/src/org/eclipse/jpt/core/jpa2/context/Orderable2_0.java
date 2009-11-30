/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.jpa2.context;

import org.eclipse.jpt.core.context.Orderable;

/**
 * Multi-valued (1:m, m:m) relationship mappings support ordering.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface Orderable2_0
	extends Orderable
{
	
	boolean isOrderColumnOrdering();
	void setOrderColumnOrdering(boolean orderColumn);
		String ORDER_COLUMN_ORDERING_PROPERTY = "orderColumnOrdering"; //$NON-NLS-1$
	
	OrderColumn2_0 getOrderColumn();

}
