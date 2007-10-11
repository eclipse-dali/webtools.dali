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
package org.eclipse.jpt.ui.internal.navigator;

import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jpt.core.internal.context.IContextModel;
import org.eclipse.jpt.ui.internal.JptUiPlugin;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;
import org.eclipse.ui.navigator.IDescriptionProvider;
import org.eclipse.ui.navigator.IMementoAware;

public class JpaLabelProvider
	implements ICommonLabelProvider
{
	public JpaLabelProvider() {
		super();
	}
	
	/**
	 * @see ICommonLabelProvider#init(ICommonContentExtensionSite)
	 */
	public void init(ICommonContentExtensionSite config) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @see IBaseLabelProvider#dispose()
	 */
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @see IBaseLabelProvider#addListener(ILabelProviderListener)
	 */
	public void addListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @see IBaseLabelProvider#removeListener(ILabelProviderListener)
	 */
	public void removeListener(ILabelProviderListener listener) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @see ILabelProvider#getImage(Object)
	 */
	public Image getImage(Object element) {
		if (element instanceof IContextModel) {
			return JptUiPlugin.getPlugin().getImage("full/obj16/jpa_content");
		}
		
		return null;
	}
	
	/**
	 * @see ILabelProvider#getText(Object)
	 */
	public String getText(Object element) {
		if (element instanceof IContextModel) {
			return "JPA Content";
		}
		
		return null;
	}
	
	/**
	 * @see IDescriptionProvider#getDescription(Object)
	 */
	public String getDescription(Object anElement) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * @see IBaseLabelProvider#isLabelProperty(Object, String)
	 */
	public boolean isLabelProperty(Object element, String property) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * @see IMementoAware#restoreState(IMemento)
	 */
	public void restoreState(IMemento memento) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * @see IMementoAware#saveState(IMemento)
	 */
	public void saveState(IMemento memento) {
		// TODO Auto-generated method stub
		
	}
}
