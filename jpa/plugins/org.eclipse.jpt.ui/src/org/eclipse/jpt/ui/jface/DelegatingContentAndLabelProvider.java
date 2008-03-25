/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.jface;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

/**
 * Implementation of {@link IStructuredContentProvider} and {@link ILabelProvider} that 
 * maintains a collection (Map, actually) of {@link ItemContentProvider} 
 * delegates that perform the function of providing content and label information
 * for each represented item
 * 
 * NB: This class, if used as a label provider *MUST* be used as a content provider
 * for the same viewer.  It may be used as a content provider with a different
 * label provider, however.
 */
public abstract class DelegatingContentAndLabelProvider extends BaseLabelProvider
	implements IStructuredContentProvider, ILabelProvider
{
	private final ItemContentProviderFactory itemContentProviderFactory;
	
	private final ItemLabelProviderFactory itemLabelProviderFactory;
	
	private final Map<Object, ItemContentProvider> itemContentProviders;
	
	private final Map<Object, ItemLabelProvider> itemLabelProviders;
	
	StructuredViewer viewer;
	
	
	protected DelegatingContentAndLabelProvider(
			ItemContentProviderFactory itemContentProviderFactory) {
		this(itemContentProviderFactory, null);
	}
	
	protected DelegatingContentAndLabelProvider(
			ItemContentProviderFactory itemContentProviderFactory,
			ItemLabelProviderFactory itemLabelProviderFactory) {
		super();
		this.itemContentProviderFactory = itemContentProviderFactory;
		this.itemLabelProviderFactory = itemLabelProviderFactory;
		this.itemContentProviders = new HashMap<Object, ItemContentProvider>();
		this.itemLabelProviders = new HashMap<Object, ItemLabelProvider>();
	}
	
	
	protected ItemContentProvider itemContentProvider(Object item) {
		ItemContentProvider itemContentProvider = itemContentProviders.get(item);
		if (itemContentProvider != null) {
			return itemContentProvider;
		}
		itemContentProvider = itemContentProviderFactory.buildItemContentProvider(item, this);
		if (itemContentProvider == null) {
			return null;
		}
		itemContentProviders.put(item, itemContentProvider);
		return itemContentProvider;
	}
	
	protected ItemLabelProvider itemLabelProvider(Object item) {
		if (viewer == null) {
			throw new IllegalStateException(
					"This provider must be used as a content" +
					"provider *as well as* a label provider.");
		}
		ItemLabelProvider itemLabelProvider = itemLabelProviders.get(item);
		if (itemLabelProvider != null) {
			return itemLabelProvider;
		}
		itemLabelProvider = itemLabelProviderFactory.buildItemLabelProvider(item, this);
		if (itemLabelProvider == null) {
			return null;
		}
		itemLabelProviders.put(item, itemLabelProvider);
		return itemLabelProvider;
	}
	
	
	public Object[] getElements(Object inputElement) {
		return itemContentProvider(inputElement).getElements();
	}
	
	public Image getImage(Object element) {
		ItemLabelProvider provider = itemLabelProvider(element);
		return (provider == null) ? null :provider.image();
	}
	
	public String getText(Object element) {
		ItemLabelProvider provider = itemLabelProvider(element);
		return (provider == null) ? null : provider.text();
	}
	
	/**
	 * Disposes all items
	 */
	@Override
	public void dispose() {
		// coded this way to allow some item providers to dispose of their child 
		// elements without disrupting the entire process
		while (! itemContentProviders.isEmpty()) {
			dispose(itemContentProviders.keySet().iterator().next());
		}
		// this catches any items that weren't disposed from the content providers,
		// though there most likely won't be any items represented here that 
		// haven't already been disposed
		while (! itemLabelProviders.isEmpty()) {
			dispose(itemLabelProviders.keySet().iterator().next());
		}
		super.dispose();
	}
	
	/**
	 * Disposes item
	 */
	public void dispose(Object item) {
		if (itemContentProviders.containsKey(item)) {
			itemContentProviders.get(item).dispose();
			itemContentProviders.remove(item);
		}
		if (itemLabelProviders.containsKey(item)) {
			itemLabelProviders.get(item).dispose();
			itemLabelProviders.remove(item);
		}
	}
	
	public void inputChanged(Viewer structuredViewer, Object oldInput, Object newInput) {
		if (oldInput != newInput) {
			dispose();
		}
		this.viewer = (StructuredViewer) structuredViewer;
	}
	
	/**
	 * Update the content for the given item
	 */
	public void updateContent(final Object item) {
		Runnable runnable = new Runnable() {
			public void run() {
				if (viewer != null && viewer.getControl() != null && !viewer.getControl().isDisposed()) {
					viewer.refresh(item);
				}
			}
		};
		viewer.getControl().getDisplay().asyncExec(runnable);
	}
	
	// open up visibility a bit for inner classes
	@Override
	protected void fireLabelProviderChanged(LabelProviderChangedEvent event) {
		super.fireLabelProviderChanged(event);
	}
	
	/**
	 * Update the label for the given item
	 */
	public void updateLabel(final Object item) {
		Runnable runnable = new Runnable() {
			public void run() {
				if (viewer != null && viewer.getControl() != null && !viewer.getControl().isDisposed()) {
					fireLabelProviderChanged(new LabelProviderChangedEvent(DelegatingContentAndLabelProvider.this, item));
				}
			}
		};
		viewer.getControl().getDisplay().asyncExec(runnable);
	}
}
