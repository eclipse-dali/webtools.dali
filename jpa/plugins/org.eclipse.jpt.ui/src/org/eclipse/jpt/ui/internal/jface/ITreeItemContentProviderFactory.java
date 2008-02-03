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
package org.eclipse.jpt.ui.internal.jface;

/**
 * Extension of {@link IItemContentProviderFactory} that extends functionality
 * for tree content
 */
public interface ITreeItemContentProviderFactory extends IItemContentProviderFactory
{
	public ITreeItemContentProvider buildItemContentProvider(Object item, 
			DelegatingContentAndLabelProvider contentAndLabelProvider);
}
