/*******************************************************************************
 *  Copyright (c) 2008 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.jface;

/**
 * Factory interface used to describe how to build {@link ItemContentProvider}s
 * for a {@link DelegatingContentAndLabelProvider}
 */
public interface ItemContentProviderFactory
{
	ItemContentProvider buildItemContentProvider(Object item, 
			DelegatingContentAndLabelProvider contentAndLabelProvider);
}
