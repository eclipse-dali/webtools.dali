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
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.common.ui.internal.plugin.JptCommonUiPlugin;
import org.eclipse.jpt.common.ui.internal.swt.widgets.DisplayTools;
import org.eclipse.jpt.common.ui.jface.ItemContentProvider;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemStructuredContentProvider;
import org.eclipse.jpt.common.ui.jface.StructuredStateProvider;
import org.eclipse.jpt.common.utility.ExceptionHandler;
import org.eclipse.jpt.common.utility.internal.ListenerList;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.swt.graphics.Image;
import com.ibm.icu.text.MessageFormat;

/**
 * This provider maintains an input element content provider and a cache of item
 * label providers, each keyed by item. This allows the providers to listen to
 * the items and update the viewer as necessary.
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
abstract class AbstractItemStructuredStateProviderManager<V extends StructuredViewer, CP extends ItemContentProvider, CPF extends ItemStructuredContentProvider.Factory>
	implements StructuredStateProvider,
				ItemStructuredContentProvider.Manager,
				ItemExtendedLabelProvider.Manager
{
	/* private-protected */ V viewer;

	private Object input;

	/* private-protected */ final CPF itemContentProviderFactory;

	private ItemStructuredContentProvider inputContentProvider;

	private final ItemExtendedLabelProvider.Factory itemLabelProviderFactory;

	/**
	 * This maps <em>every</em> item in the view to its label provider
	 * (<em>including</em> the {@link #input} item).
	 */
	private final HashMap<Object, ItemExtendedLabelProvider> itemLabelProviders = new HashMap<Object, ItemExtendedLabelProvider>();

	private final ResourceManager resourceManager;

	private final ListenerList<ILabelProviderListener> listenerList = new ListenerList<ILabelProviderListener>(ILabelProviderListener.class);
	private final ExceptionHandler exceptionHandler;

	/* private-protected */ final static JptCommonUiPlugin PLUG_IN = JptCommonUiPlugin.instance();


	/* private-protected */ AbstractItemStructuredStateProviderManager(
			CPF itemContentProviderFactory,
			ItemExtendedLabelProvider.Factory itemLabelProviderFactory,
			ResourceManager resourceManager,
			ExceptionHandler exceptionHandler
	) {
		super();
		if (itemContentProviderFactory == null) {
			throw new NullPointerException();
		}
		this.itemContentProviderFactory = itemContentProviderFactory;
		if (itemLabelProviderFactory == null) {
			throw new NullPointerException();
		}
		this.itemLabelProviderFactory = itemLabelProviderFactory;
		if (resourceManager == null) {
			throw new NullPointerException();
		}
		this.resourceManager = resourceManager;
		if (exceptionHandler == null) {
			throw new NullPointerException();
		}
		this.exceptionHandler = exceptionHandler;
	}


	// ********** structured content provider **********

	/**
	 * <em>API Commentary:</em> The viewer and the original input should be
	 * passed to the provider's constructor; and this method should be passed
	 * <em>only</em> the new input.
	 */
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		@SuppressWarnings("unchecked")
		V temp = (V) v;
		this.viewer = temp;
		if (oldInput != newInput) {
			if (this.inputContentProvider != null) {
				this.disposeInputProviders();
			}
			this.input = newInput;
			if (newInput != null) {
				// build a label provider for the input because the viewer might
				// use the input's label for a title etc. (e.g. the CommonViewer
				// stores the input's label text on the frame list to enable
				// "Back" and "Forward" navigation)
				this.addLabelProvider(newInput);
				this.inputContentProvider = this.itemContentProviderFactory.buildProvider(newInput, this);
				this.addAll(newInput, this.inputContentProvider.getElements());
			}
		}
	}

	/**
	 * Assume the input element passed to this method
	 * is the same object as the "new input" passed (earlier) to
	 * {@link #inputChanged(Viewer, Object, Object)},
	 * and it is not <code>null</code>.
	 * <p>
	 * <em>API Commentary:</em> The input element should be passed to the
	 * provider's constructor and {@link #inputChanged(Viewer, Object, Object)};
	 * and this method should have <em>no</em> parameters.
	 */
	public Object[] getElements(Object inputElement) {
		if (inputElement != this.input) {
			String msg = MessageFormat.format("Mismatched input: this.input: '{0}' vs. inputElement: '{1}'", this.input, inputElement); //$NON-NLS-1$
			IllegalArgumentException ex = new IllegalArgumentException(msg);
			if (PLUG_IN != null) {
				PLUG_IN.logError(ex);
			} else {
				ex.printStackTrace();
			}
		}
		return this.inputContentProvider.getElements();
	}


	// ********** label provider **********

	public Image getImage(Object element) {
		return this.getItemLabelProvider(element).getImage();
	}

	public String getText(Object element) {
		return this.getItemLabelProvider(element).getText();
	}

	public String getDescription(Object element) {
		return this.getItemLabelProvider(element).getDescription();
	}

	/**
	 * <strong>NB:</strong> We have a bug if this method ever returns
	 * <code>null</code>.
	 */
	private ItemExtendedLabelProvider getItemLabelProvider(Object item) {
		this.checkViewer();
		return this.itemLabelProviders.get(item);
	}

	/**
	 * The viewer passes itself to its content provider
	 * (via {@link #inputChanged(Viewer, Object, Object)}); so the manager's
	 * {@link #viewer} will be initialized by the time we get here if the
	 * manager is also the viewer's content provider.
	 */
	private void checkViewer() {
		if (this.viewer == null) {
			String msg = "This manager must be both the viewer's content provider *and* the viewer's label provider."; //$NON-NLS-1$
			IllegalStateException ex = new IllegalStateException(msg);
			if (PLUG_IN != null) {
				PLUG_IN.logError(ex);
			} else {
				ex.printStackTrace();
			}
		}
	}


	// ********** elements changed **********

	/**
	 * If the {@link #input} has changed out from under us, ignore the "event".
	 */
	public void elementsChanged(Object parent, Iterable<?> addedElements, Iterable<?> removedElements) {
		this.checkUIThread();
		if (parent == this.input) {
			this.elementsChanged_(parent, addedElements, removedElements);
		}
	}

	private void elementsChanged_(Object parent, Iterable<?> addedElements, Iterable<?> removedElements) {
		this.addAll(parent, addedElements);
		this.removeAll(removedElements);
		this.viewer.refresh(false);  // not much else we can do here...
	}

	/**
	 * @see #addAll(Object, Iterable)
	 */
	/* private-protected */ void addAll(Object parent, Object[] items) {
		for (Object item : items) {
			this.add(parent, item);
		}
	}

	/**
	 * Add the specified items' content and label providers.
	 * <p>
	 * Pre-condition: Executing on the UI thread.
	 */
	/* private-protected */ void addAll(Object parent, Iterable<?> items) {
		for (Object item : items) {
			this.add(parent, item);
		}
	}

	/**
	 * Add the specified item's content and label providers.
	 * <p>
	 * Pre-condition: Executing on the UI thread.
	 */
	/* private-protected */ void add(@SuppressWarnings("unused") Object parent, Object item) {
		this.addLabelProvider(item);
	}

	private void addLabelProvider(Object item) {
		this.itemLabelProviders.put(item, this.itemLabelProviderFactory.buildProvider(item, this));
	}

	/**
	 * @see #removeAll(Iterable)
	 */
	/* private-protected */ void removeAll(Object[] items) {
		for (Object item : items) {
			this.remove(item);
		}
	}

	/**
	 * Dispose the specified items' content and label providers.
	 * <p>
	 * Pre-condition: Executing on the UI thread.
	 */
	/* private-protected */ void removeAll(Iterable<?> items) {
		for (Object item : items) {
			this.remove(item);
		}
	}

	/**
	 * Dispose the specified item's content and label providers.
	 * <p>
	 * Pre-condition: Executing on the UI thread.
	 */
	/* private-protected */ void remove(Object item) {
		this.removeLabelProvider(item);
	}

	private void removeLabelProvider(Object item) {
		this.itemLabelProviders.get(item).dispose();
		this.itemLabelProviders.remove(item);
	}


	// ********** label changed **********

	/**
	 * If the specified item has been removed from under us, ignore the "event".
	 */
	public void labelChanged(Object item) {
		this.checkUIThread();
		if (this.getItemLabelProvider(item) != null) {
			this.labelChanged_(item);
		}
	}

	private void labelChanged_(Object item) {
		this.fireLabelProviderChanged(new LabelProviderChangedEvent(this, item));
	}


	// ********** description changed **********

	/**
	 * If the specified item has been removed from under us, ignore the "event".
	 */
	public void descriptionChanged(Object item) {
		this.checkUIThread();
		if (this.getItemLabelProvider(item) != null) {
			this.descriptionChanged_(item);
		}
	}

	private void descriptionChanged_(@SuppressWarnings("unused") Object item) {
		// NOP - currently there is no way affect the status bar;
		// it is updated when the viewer's selection changes
	}


	// ********** listeners **********

	public void addListener(ILabelProviderListener listener) {
		this.listenerList.add(listener);
	}

	public void removeListener(ILabelProviderListener listener) {
		this.listenerList.remove(listener);
	}

	public boolean isLabelProperty(Object element, String property) {
		return true;
	}

	/* private-protected */ void fireLabelProviderChanged(LabelProviderChangedEvent event) {
		for (ILabelProviderListener listener : this.listenerList) {
			try {
				listener.labelProviderChanged(event);
			} catch (Throwable t) {
				this.exceptionHandler.handleException(t);
			}
		}
	}

	// ********** dispose **********

	/**
	 * Typically, the provider's input is set to <code>null</code> (via
	 * {@link #inputChanged(Viewer, Object, Object) inputChanged(...)}) <em>before</em> the
	 * provider is disposed
	 * (see {@link org.eclipse.jface.viewers.ContentViewer#handleDispose(org.eclipse.swt.events.DisposeEvent)
	 * ContentViewer.handleDispose(...)}).
	 * But {@link org.eclipse.ui.navigator.CommonViewer CommonViewer}
	 * disposes its {@link org.eclipse.ui.navigator.INavigatorContentService
	 * content service} (and its delegate content providers) <em>before</em> the input
	 * is set to <code>null</code>
	 * (see {@link org.eclipse.ui.navigator.CommonViewer#handleDispose(org.eclipse.swt.events.DisposeEvent)
	 * CommonViewer.handleDispose(...)}).
	 * This is <em>intentional</em>;
	 * see the comment in {@link org.eclipse.ui.internal.navigator.NavigatorContentService#updateService(Viewer, Object, Object)
	 * NavigatorContentService.updateService(...)}.
	 */
	public void dispose() {
		if (this.inputContentProvider != null) {
			this.disposeInputProviders();
			this.input = null;
		}
		// the input is set to null before the content and label providers are disposed,
		// so there is no need to dispose the item providers here - they are already gone...
		if ( ! this.itemLabelProviders.isEmpty()) {
			String msg = MessageFormat.format("Not all item label providers were disposed: {0}", this.itemLabelProviders); //$NON-NLS-1$
			IllegalStateException ex = new IllegalStateException(msg);
			if (PLUG_IN != null) {
				PLUG_IN.logError(ex);
			} else {
				ex.printStackTrace();
			}
		}
	}

	private void disposeInputProviders() {
		this.removeAll(this.inputContentProvider.getElements());
		this.inputContentProvider.dispose();
		this.inputContentProvider = null;
		this.removeLabelProvider(this.input);
	}


	// ********** misc **********

	public StructuredViewer getViewer() {
		return this.viewer;
	}

	public ResourceManager getResourceManager() {
		return this.resourceManager;
	}

	/* private-protected */ void checkUIThread() {
		DisplayTools.checkUIThread(this.viewer);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
