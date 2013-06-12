/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import java.util.HashMap;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jpt.common.ui.internal.plugin.JptCommonUiPlugin;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.TreeStateProvider;
import org.eclipse.jpt.common.utility.ExceptionHandler;
import com.ibm.icu.text.MessageFormat;

/**
 * Add a cache of item content providers (for branch and leaf items).
 * 
 * @see AbstractItemStructuredStateProviderManager
 * @see ItemTreeContentProvider
 */
public class ItemTreeStateProviderManager
	extends AbstractItemStructuredStateProviderManager<TreeViewer, ItemTreeContentProvider, ItemTreeContentProvider.Factory>
	implements TreeStateProvider,
				ItemTreeContentProvider.Manager
{
	private final HashMap<Object, ItemTreeContentProvider> itemContentProviders = new HashMap<Object, ItemTreeContentProvider>();


	public ItemTreeStateProviderManager(ItemTreeContentProvider.Factory itemContentProviderFactory, ResourceManager resourceManager) {
		this(itemContentProviderFactory, NullItemExtendedLabelProviderFactory.instance(), resourceManager);
	}

	public ItemTreeStateProviderManager(
			ItemTreeContentProvider.Factory itemContentProviderFactory,
			ItemExtendedLabelProvider.Factory itemLabelProviderFactory,
			ResourceManager resourceManager
	) {
		this(itemContentProviderFactory, itemLabelProviderFactory, resourceManager, JptCommonUiPlugin.exceptionHandler());
	}

	public ItemTreeStateProviderManager(
			ItemTreeContentProvider.Factory itemContentProviderFactory,
			ItemExtendedLabelProvider.Factory itemLabelProviderFactory,
			ResourceManager resourceManager,
			ExceptionHandler exceptionHandler
	) {
		super(itemContentProviderFactory, itemLabelProviderFactory, resourceManager, exceptionHandler);
	}


	// ********** tree content provider **********

	public boolean hasChildren(Object element) {
		return this.getItemContentProvider(element).hasChildren();
	}

	public Object[] getChildren(Object parentElement) {
		return this.getItemContentProvider(parentElement).getChildren();
	}

	/**
	 * We need a <code>null</code> check here because
	 * {@link org.eclipse.jface.viewers.StructuredViewer#preservingSelection(Runnable, boolean)}
	 * will call this method with a "dead" object in an attempt to save the
	 * selection when the view is updated.
	 */
	public Object getParent(Object element) {
		ItemTreeContentProvider provider = this.getItemContentProvider(element);
		return (provider == null) ? null : provider.getParent();
	}

	/**
	 * <strong>NB:</strong> We have a bug if this method ever returns
	 * <code>null</code> unexpectedly.
	 */
	private ItemTreeContentProvider getItemContentProvider(Object item) {
		return this.itemContentProviders.get(item);
	}


	// ********** children changed **********

	/**
	 * If the specified item has been removed from under us, ignore the "event".
	 */
	public void childrenChanged(Object item, Iterable<?> addedChildren, Iterable<?> removedChildren) {
		this.checkUIThread();
		if (this.getItemContentProvider(item) != null) {
			this.childrenChanged_(item, addedChildren, removedChildren);
		}
	}

	private void childrenChanged_(Object item, Iterable<?> addedChildren, Iterable<?> removedChildren) {
		this.addAll(item, addedChildren);
		this.removeAll(removedChildren);
		this.viewer.refresh(item, false);
	}

	@Override
	/* private-protected */ void add(Object parent, Object item) {
		super.add(parent, item);
		ItemTreeContentProvider provider = this.itemContentProviderFactory.buildProvider(item, parent, this);
		this.itemContentProviders.put(item, provider);
		this.addAll(item, provider.getChildren());  // recurse over descendants
	}

	@Override
	/* private-protected */ void remove(Object item) {
		ItemTreeContentProvider provider = this.itemContentProviders.get(item);
		this.removeAll(provider.getChildren());  // recurse over descendants
		provider.dispose();
		this.itemContentProviders.remove(item);
		super.remove(item);
	}


	// ********** dispose **********

	@Override
	public void dispose() {
		super.dispose();
		if ( ! this.itemContentProviders.isEmpty()) {
			String msg = MessageFormat.format("Not all item content providers were disposed: {0}", this.itemContentProviders); //$NON-NLS-1$
			IllegalStateException ex = new IllegalStateException(msg);
			if (PLUG_IN != null) {
				PLUG_IN.logError(ex);
			} else {
				ex.printStackTrace();
			}
		}
	}
}
