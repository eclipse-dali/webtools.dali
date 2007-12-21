/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.base;

public interface ICascade extends IJpaContextNode
{

	boolean isAll();
	void setAll(boolean value);
		String ALL_PROPERTY = "allProperty";

	boolean isPersist();
	void setPersist(boolean value);
		String PERSIST_PROPERTY = "persistProperty";

	boolean isMerge();
	void setMerge(boolean value);
		String MERGE_PROPERTY = "mergeProperty";

	boolean isRemove();
	void setRemove(boolean value);
		String REMOVE_PROPERTY = "removeProperty";

	boolean isRefresh();
	void setRefresh(boolean value);
		String REFRESH_PROPERTY = "refreshProperty";
}
