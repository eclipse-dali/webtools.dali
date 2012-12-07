/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
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
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProviderFactory;
import org.eclipse.jpt.common.ui.jface.ItemLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemStructuredContentProvider;
import org.eclipse.jpt.common.ui.jface.StructuredStateProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.RunnableAdapter;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;

/**
 * This provider maintains caches of item content and label providers, each
 * keyed by item. This allows the providers to listen to the items and update
 * the viewer as necessary.
 * <p>
 * <strong>NB:</strong> This class, if used as a label provider should typically
 * be used also as a content provider for the same viewer. Otherwise, the item
 * label providers will not be disposed until the viewer is disposed; because
 * the item label providers are disposed (here) as their associated items are
 * disposed by their item content providers, which are listening to the items.
 * This is only a problem if the items in the tree can be removed, thus leaking
 * stale item label providers in the manager's cache. The default behavior is
 * to disallow using the manager as only a label provider (see
 * {@link #checkViewer()}).
 * 
 * @see ItemStructuredContentProvider
 * @see ItemExtendedLabelProvider
 */
public abstract class AbstractItemStructuredStateProviderManager<V extends StructuredViewer, CP extends ItemStructuredContentProvider>
	extends BaseLabelProvider
	implements StructuredStateProvider, ItemStructuredContentProvider.Manager, ItemExtendedLabelProvider.Manager
{
	/**
	 * May be <code>null</code>.
	 */
	protected final ItemExtendedLabelProviderFactory itemLabelProviderFactory;

	protected final HashMap<Object, CP> itemContentProviders = new HashMap<Object, CP>();

	protected final HashMap<Object, ItemExtendedLabelProvider> itemLabelProviders = new HashMap<Object, ItemExtendedLabelProvider>();

	/**
	 * Never <code>null</code>.
	 */
	protected final ResourceManager resourceManager;

	protected volatile V viewer;


	protected AbstractItemStructuredStateProviderManager(ItemExtendedLabelProviderFactory itemLabelProviderFactory, ResourceManager resourceManager) {
		super();
		this.itemLabelProviderFactory = itemLabelProviderFactory;
		if (resourceManager == null) {
			throw new NullPointerException();
		}
		this.resourceManager = resourceManager;
	}


	// ********** content provider **********

	@SuppressWarnings("unchecked")
	public synchronized void inputChanged(Viewer v, Object oldInput, Object newInput) {
		if (oldInput != newInput) {
			this.disposeProviders();
		}
		this.viewer = (V) v;
	}

	public Object[] getElements(Object inputElement) {
		CP provider = this.getItemContentProvider(inputElement);
		return (provider == null) ? ObjectTools.EMPTY_OBJECT_ARRAY : provider.getElements();
	}


	// ********** label provider **********

	public Image getImage(Object element) {
		ItemLabelProvider provider = this.getItemLabelProvider(element);
		return (provider == null) ? null : provider.getImage();
	}

	public String getText(Object element) {
		ItemLabelProvider provider = this.getItemLabelProvider(element);
		return (provider == null) ? null : provider.getText();
	}

	public String getDescription(Object element) {
		ItemExtendedLabelProvider provider = this.getItemLabelProvider(element);
		return (provider == null) ? null : provider.getDescription();
	}


	// ********** item provider caches **********

	protected synchronized CP getItemContentProvider(Object item) {
		CP provider = this.itemContentProviders.get(item);
		if (provider == null) {
			if ( ! this.itemContentProviders.containsKey(item)) {  // null is an allowed value
				provider = this.buildItemContentProvider(item);
				this.itemContentProviders.put(item, provider);
			}
		}
		return provider;
	}

	protected abstract CP buildItemContentProvider(Object item);

	protected synchronized ItemExtendedLabelProvider getItemLabelProvider(Object item) {
		ItemExtendedLabelProvider provider = this.itemLabelProviders.get(item);
		if (provider == null) {
			if ( ! this.itemLabelProviders.containsKey(item)) {  // null is an allowed value
				provider = this.buildItemLabelProvider(item);
				this.itemLabelProviders.put(item, provider);
			}
		}
		return provider;
	}

	protected ItemExtendedLabelProvider buildItemLabelProvider(Object item) {
		this.checkViewer();
		return (this.itemLabelProviderFactory == null) ? null : this.itemLabelProviderFactory.buildProvider(item, this);
	}

	/**
	 * The viewer passes itself to its content provider; so it will be
	 * initialized by the time we get here if this provider is the
	 * viewer's content provider.
	 */
	protected void checkViewer() {
		if (this.viewer == null) {
			throw new IllegalStateException("This provider must be used as a content provider *as well as* a label provider."); //$NON-NLS-1$
		}
	}


	// ********** update elements **********

	/**
	 * Dispatch to the UI thread.
	 */
	public void updateElements(Object inputElement) {
		this.execute(new UpdateElementsRunnable(inputElement));
	}

	/* CU private */ class UpdateElementsRunnable
		extends RunnableAdapter
	{
		private final Object inputElement;
		UpdateElementsRunnable(Object inputElement) {
			super();
			this.inputElement = inputElement;
		}
		@Override
		public void run() {
			AbstractItemStructuredStateProviderManager.this.updateElements_(this.inputElement);
		}
	}

	/**
	 * Update the specified item's elements.
	 */
	/* CU private */ void updateElements_(Object inputElement) {
		if (this.viewerIsAlive()) {
			this.viewer.refresh(inputElement);
		}
	}


	// ********** update label **********

	/**
	 * Dispatch to the UI thread.
	 */
	public void updateLabel(Object item) {
		this.execute(new UpdateLabelRunnable(item));
	}

	/* CU private */ class UpdateLabelRunnable
		extends RunnableAdapter
	{
		private final Object item;
		UpdateLabelRunnable(Object item) {
			super();
			this.item = item;
		}
		@Override
		public void run() {
			AbstractItemStructuredStateProviderManager.this.updateLabel_(this.item);
		}
	}

	/**
	 * Update the specified item's label.
	 */
	/* CU private */ void updateLabel_(Object item) {
		if (this.viewerIsAlive()) {
			this.fireLabelProviderChanged(new LabelProviderChangedEvent(this, item));
		}
	}


	// ********** update description **********

	public void updateDescription(Object item) {
		// NOP - currently there is no way affect the status bar;
		// it is updated when the viewer's selection changes
	}


	// ********** misc **********

	public ResourceManager getResourceManager() {
		return this.resourceManager;
	}

	protected void execute(Runnable runnable) {
		SWTUtil.execute(this.viewer, runnable);
	}

	protected boolean viewerIsAlive() {
		Control control = (this.viewer == null) ? null : this.viewer.getControl();
		return (control != null) && ! control.isDisposed();
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}


	// ********** dispose **********

	/**
	 * Disposes resource manager and all item providers.
	 */
	@Override
	public synchronized void dispose() {
		this.disposeProviders();
		this.resourceManager.dispose();
		super.dispose();
	}

	protected synchronized void disposeProviders() {
		// coded this way because the item providers will call back to this
		// manager to dispose their children when they are disposed
		while ( ! this.itemContentProviders.isEmpty()) {
			this.dispose_(this.itemContentProviders.keySet().iterator().next());
		}
		// dispose the label providers for any items that did not have a content provider;
		// although that is most likely a bug, it is allowed and handled
		while (! this.itemLabelProviders.isEmpty()) {
			this.dispose_(this.itemLabelProviders.keySet().iterator().next());
		}
	}

	/**
	 * Dispose the specified item's content and label providers.
	 */
	public synchronized void dispose(Object item) {
		this.dispose_(item);
	}

	/**
	 * Pre-condition: synchronized
	 */
	private void dispose_(Object item) {
		ItemStructuredContentProvider icp = this.itemContentProviders.remove(item);
		if (icp != null) {
			icp.dispose();
		}
		ItemLabelProvider ilp = this.itemLabelProviders.remove(item);
		if (ilp != null) {
			ilp.dispose();
		}
	}
}
