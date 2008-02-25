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

import java.util.HashMap;
import java.util.Map;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.platform.JpaPlatform;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

/**
 * This extension of navigator label provider delegates to the platform UI
 * (see the org.eclipse.jpt.ui.jpaPlatform extension point) for navigator labels.
 * 
 * This label provider provides a label for the root "JPA Content" node provided
 * by the content provider (see {@link JpaNavigatorContentProvider}) and delegates
 * to the label provider returned by the platform UI implementation for labels
 * for children of the "JPA Content" node (or for any other sub-node).
 */
public class JpaNavigatorLabelProvider extends LabelProvider
	implements ICommonLabelProvider
{
	/**
	 * Exactly *one* of these is created for each view that utilizes it.  Therefore, 
	 * as we delegate to the platform UI for each project, we should maintain the 
	 * same multiplicity.  That is, if there is a delegate for each platform UI, we 
	 * should maintain *one* delegate for each view.
	 * 
	 * Key: platform id,  Value: delegate label provider
	 */
	private Map<String, ICommonLabelProvider> delegateLabelProviders;
	
	private ILabelProviderListener delegateLabelProviderListener;
	
	
	public JpaNavigatorLabelProvider() {
		super();
		delegateLabelProviders = new HashMap<String, ICommonLabelProvider>();
		delegateLabelProviderListener = new DelegateLabelProviderListener();
	}
	
	
	// **************** IBaseLabelProvider implementation **********************
	
	public void addListener(ILabelProviderListener listener) {
		super.addListener(listener);
	}
	
	public void removeListener(ILabelProviderListener listener) {
		super.removeListener(listener);
	}
	
	public boolean isLabelProperty(Object element, String property) {
		ICommonLabelProvider delegate = getDelegate(element);
		
		if (delegate != null) {
			return delegate.isLabelProperty(element, property);
		}
		
		return super.isLabelProperty(element, property);
	}
	
	public void dispose() {
		super.dispose();
		for (ICommonLabelProvider delegate : delegateLabelProviders.values()) {
			delegate.dispose();
		}
		delegateLabelProviders.clear();
	}
	
	
	// **************** ILabelProvider implementation **************************
	
	public Image getImage(Object element) {
		ICommonLabelProvider delegate = getDelegate(element);
		
		if (delegate != null) {
			return delegate.getImage(element);
		}
		
		return super.getImage(element);
	}
	
	public String getText(Object element) {
		ICommonLabelProvider delegate = getDelegate(element);
		
		if (delegate != null) {
			return delegate.getText(element);
		}
		
		return super.getText(element);
	}
	
	
	// **************** IDescriptionProvider implementation ********************
	
	public String getDescription(Object element) {
		ICommonLabelProvider delegate = getDelegate(element);
		
		if (delegate != null) {
			return delegate.getDescription(element);
		}
		
		// no "super" implementation - default to getText(..) for description
		return super.getText(element);
	}
	
	
	// **************** IMementoAware implementation ***************************
	
	public void saveState(IMemento memento) {
		for (ICommonLabelProvider delegate : delegateLabelProviders.values()) {
			delegate.saveState(memento);
		}
	}
	
	public void restoreState(IMemento memento) {
		for (ICommonLabelProvider delegate : delegateLabelProviders.values()) {
			delegate.restoreState(memento);
		}
	}
	
	
	// **************** ICommonLabelProvider implementation ********************
	
	public void init(ICommonContentExtensionSite config) {
		for (ICommonLabelProvider delegate : delegateLabelProviders.values()) {
			delegate.init(config);
		}
	}
	
	
	// *************** internal ************************************************
	
	private ICommonLabelProvider getDelegate(Object element) {
		if (! (element instanceof IAdaptable)) {
			return null;
		}
		
		JpaContextNode contextNode = (JpaContextNode) ((IAdaptable) element).getAdapter(JpaContextNode.class);
		
		if (contextNode == null) {
			return null;
		}
		
		JpaPlatform platform = contextNode.jpaProject().jpaPlatform();
		JpaPlatformUi platformUi = JptUiPlugin.getPlugin().jpaPlatformUi(platform);
		
		ICommonLabelProvider delegate = delegateLabelProviders.get(platform.getId());
		
		if (delegate == null && platform != null && ! delegateLabelProviders.containsKey(platform.getId())) {
			delegate = platformUi.buildNavigatorLabelProvider();
			delegate.addListener(delegateLabelProviderListener);
			delegateLabelProviders.put(platform.getId(), delegate);
		}
		
		return delegate;
	}
	
	
	private class DelegateLabelProviderListener 
		implements ILabelProviderListener
	{
		public void labelProviderChanged(LabelProviderChangedEvent event) {
			fireLabelProviderChanged(event);
		}
	}
}
