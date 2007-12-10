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
package org.eclipse.jpt.ui.internal.structure;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.swt.graphics.Image;

public class JavaStructureLabelProvider implements ILabelProvider
{
	public Image getImage(Object element) {
		return null;
	}
	
	public String getText(Object element) {
		if (element instanceof IJavaPersistentType) {
			return ((IJavaPersistentType) element).getName();
		}
		
		if (element instanceof IJavaPersistentAttribute) {
			return ((IJavaPersistentAttribute) element).getName();
		}
		
		return element.toString();
	}
	
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public void dispose() {
	// TODO Auto-generated method stub
	}
	
	public void addListener(ILabelProviderListener listener) {
	// TODO Auto-generated method stub
	}
	
	public void removeListener(ILabelProviderListener listener) {
	// TODO Auto-generated method stub
	}
}
