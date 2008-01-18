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

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.swt.graphics.Image;

/**
 * Implementation of {@link ILabelProvider} that maintains a collection 
 * (Map, actually) of {@link IItemLabelProvider} delegates that perform
 * the function of providing label information for each element
 */
public class DelegatingLabelProvider extends BaseLabelProvider
	implements ILabelProvider
{
	private final IItemLabelProviderFactory itemLabelProviderFactory;
	
	private final Map<Object, IItemLabelProvider> itemLabelProviders;
	
	
	public DelegatingLabelProvider(IItemLabelProviderFactory itemLabelProviderFactory) {
		super();
		this.itemLabelProviderFactory = itemLabelProviderFactory;
		this.itemLabelProviders = new HashMap<Object, IItemLabelProvider>();
	}
	
	
	public Image getImage(Object element) {
		return itemLabelProvider(element).image();
	}
	
	public String getText(Object element) {
		return itemLabelProvider(element).text();
	}
	
	protected IItemLabelProvider itemLabelProvider(Object element) {
		IItemLabelProvider itemLabelProvider = itemLabelProviders.get(element);
		if (itemLabelProvider != null) {
			return itemLabelProvider;
		}
		itemLabelProvider = itemLabelProviderFactory.buildItemLabelProvider(element, this);
		if (itemLabelProvider == null) {
			return null;
		}
		itemLabelProviders.put(element, itemLabelProvider);
		return itemLabelProvider;
	}
	
	/**
	 * Disposes all elements
	 */
	public void dispose() {
		// coded this way to allow some item providers to dispose of their child 
		// elements without disrupting the entire process
		while (! itemLabelProviders.isEmpty()) {
			dispose(itemLabelProviders.keySet().iterator().next());
		}
	}
	
	/**
	 * Disposes element
	 */
	protected void dispose(Object element) {
		if (itemLabelProviders.containsKey(element)) {
			itemLabelProviders.get(element).dispose();
			itemLabelProviders.remove(element);
		}
	}
	
	/**
	 * Fire an event to update the label for the given element
	 */
	public void updateLabel(Object element) {
		fireLabelProviderChanged(new LabelProviderChangedEvent(this, element));
	}
}
