/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.structure;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;

public interface IJpaStructureProvider 
{

	String fileContentType();
	
	/**
	 * Build an outline content provider.
	 */
	ITreeContentProvider buildContentProvider();
	
	/**
	 * Build an outline label provider.
	 */
	ILabelProvider buildLabelProvider();
	
	void dispose();
	
}
