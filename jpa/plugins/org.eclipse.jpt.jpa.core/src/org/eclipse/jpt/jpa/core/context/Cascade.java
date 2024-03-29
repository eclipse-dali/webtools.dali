/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

/**
 * JPA cascade (persist, merge, remove, refresh)
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.2
 * @since 2.0
 */
public interface Cascade
	extends JpaContextModel
{
	boolean isAll();
	void setAll(boolean value);
		String ALL_PROPERTY = "all"; //$NON-NLS-1$
	
	boolean isPersist();
	void setPersist(boolean value);
		String PERSIST_PROPERTY = "persist"; //$NON-NLS-1$
	
	boolean isMerge();
	void setMerge(boolean value);
		String MERGE_PROPERTY = "merge"; //$NON-NLS-1$
	
	boolean isRemove();
	void setRemove(boolean value);
		String REMOVE_PROPERTY = "remove"; //$NON-NLS-1$
	
	boolean isRefresh();
	void setRefresh(boolean value);
		String REFRESH_PROPERTY = "refresh"; //$NON-NLS-1$
}
