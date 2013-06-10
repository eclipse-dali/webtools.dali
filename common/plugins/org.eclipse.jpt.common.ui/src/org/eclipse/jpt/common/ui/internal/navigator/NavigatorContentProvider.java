/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.navigator;

import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.common.ui.internal.jface.ItemTreeStateProviderManager;
import org.eclipse.jpt.common.ui.jface.ExtendedLabelProvider;
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
	protected ResourceManager resourceManager;
	protected TreeStateProvider delegate;


	/**
	 * Must use zero-argument constructor because the provider is instantiated
	 * by the Navigator Content extension point
	 * (<code>org.eclipse.ui.navigator.navigatorContent</code>).
	 */
	protected NavigatorContentProvider() {
		super();
	}

	/**
	 * The content provider is initialized first; so we build the delegate
	 * here.
	 */
	public void init(ICommonContentExtensionSite config) {
		this.resourceManager = this.buildResourceManager();
		this.delegate = this.buildDelegate();
	}

	/**
	 * Return a <em>local</em> resource manager to be used, typically, by the
	 * item label providers to retrieve {@link org.eclipse.swt.graphics.Image images}s.
	 * The returned resource manager will be disposed when the provider is
	 * {@link #dispose() disposed}.
	 */
	protected abstract ResourceManager buildResourceManager();

	protected TreeStateProvider buildDelegate() {
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

	/**
	 * By default, this method is passed the common viewer and the workspace root.
	 * But the common viewer will later change the input when the user invokes
	 * the "Go Into" command.
	 */
	public void inputChanged(Viewer commonViewer, Object oldInput, Object newInput) {
		this.delegate.inputChanged(commonViewer, oldInput, newInput);
	}

	/**
	 * @see #inputChanged(Viewer, Object, Object)
	 */
	public Object[] getElements(Object inputElement) {
		return this.delegate.getElements(inputElement);
	}

	public Object getParent(Object element) {
		return this.delegate.getParent(element);
	}

	public boolean hasChildren(Object element) {
		return this.delegate.hasChildren(element);
	}

	public Object[] getChildren(Object element) {
		return this.delegate.getChildren(element);
	}

	public void saveState(IMemento memento) {
		// NOP
	}

	public void restoreState(IMemento memento) {
		// NOP
	}

	public void dispose() {
		this.delegate.dispose();
		this.resourceManager.dispose();
	}

	/**
	 * @see NavigatorLabelProvider#getDelegate()
	 */
	ExtendedLabelProvider getDelegate() {
		return this.delegate;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}
}
