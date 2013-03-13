/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.common.ui.jface.ItemExtendedLabelProvider;
import org.eclipse.jpt.common.ui.jface.ItemTreeContentProvider;
import org.eclipse.jpt.common.ui.jface.TreeStateProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonContentProvider;

/**
 * This content provider delegates to a {@link TreeStateProvider} that is
 * shared with a partner {@link NavigatorLabelProvider label provider}.
 * <p>
 * Concrete subclasses of this provider can be used as the content provider
 * in extensions of <code>org.eclipse.ui.navigator.navigatorContent</code>.
 */
public abstract class NavigatorContentProvider
	implements ICommonContentProvider
{
	protected volatile ResourceManager resourceManager;
	protected volatile ItemTreeStateProviderManager delegate;


	/**
	 * Must use zero-argument constructor because the provider is instantiated
	 * by the Navigator Content extension point
	 * (<code>org.eclipse.ui.navigator.navigatorContent</code>).
	 */
	protected NavigatorContentProvider() {
		super();
	}

	/**
	 * Return a <em>local</em> resource manager to be used, typically, by the
	 * item label providers to retrieve {@link org.eclipse.swt.graphics.Image}s.
	 * The returned resource manager will be disposed when the provider is
	 * {@link #dispose() disposed}.
	 */
	protected abstract ResourceManager buildResourceManager();

	/**
	 * The content provider is initialized first; so we build the delegate
	 * here.
	 */
	public void init(ICommonContentExtensionSite config) {
		this.resourceManager = this.buildResourceManager();
		this.delegate = this.buildDelegate();
	}

	protected ItemTreeStateProviderManager buildDelegate() {
		return new ItemTreeStateProviderManager(
				this.buildItemContentProviderFactory(),
				this.buildItemLabelProviderFactory(),
				this.resourceManager
			);
	}

	/**
	 * Return a factory that will build item content providers for the
	 * appropriate items in the navigator.
	 */
	protected abstract ItemTreeContentProvider.Factory buildItemContentProviderFactory();

	/**
	 * Return a factory that will build item label providers for the
	 * appropriate items in the navigator.
	 */
	protected abstract ItemExtendedLabelProvider.Factory buildItemLabelProviderFactory();

	public void inputChanged(Viewer commonViewer, Object oldInput, Object newInput) {
		this.delegate.inputChanged(commonViewer, oldInput, newInput);
	}

	public Object[] getElements(Object inputElement) {
		// never called?
		return this.delegate.getElements(inputElement);
	}

	public Object getParent(Object element) {
		return this.delegate.getParent(element);
	}

	public boolean hasChildren(Object element) {
		return this.hasChildren_(element) || this.delegate.hasChildren(element);
	}

	/**
	 * @see #getChildren_(Object)
	 */
	protected abstract boolean hasChildren_(Object element);

	public Object[] getChildren(Object element) {
		Object[] children = this.getChildren_(element);
		return (children != null) ? children : this.delegate.getChildren(element);
	}

	/**
	 * Return the specified element's children. This method handles
	 * any element that is <em>not</em> handled by the {@link #delegate} but is
	 * the parent of element(s) that <em>are</em> handled by the
	 * {@link #delegate} (i.e. any third-party element that is to hold the
	 * provider's elements; e.g. a project). Return <code>null</code> if the
	 * element is to be handled by the {@link #delegate}.
	 * <p>
	 * We provide this hook for elements that are to be displayed even if their
	 * corresponding model does not yet exist.
	 * @see #hasChildren_(Object)
	 */
	protected abstract Object[] getChildren_(Object element);

	public void saveState(IMemento memento) {
		// TODO
	}

	public void restoreState(IMemento memento) {
		// TODO
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}

	public void dispose() {
		this.delegate.dispose();
		this.resourceManager.dispose();
	}

	/**
	 * @see NavigatorLabelProvider#getDelegate()
	 */
	TreeStateProvider getDelegate() {
		return this.delegate;
	}
}
