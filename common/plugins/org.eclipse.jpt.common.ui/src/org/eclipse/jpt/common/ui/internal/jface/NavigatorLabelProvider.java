/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.jface;

import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.LabelProviderChangedEvent;
import org.eclipse.jpt.common.ui.internal.jface.LabelProviderAdapter;
import org.eclipse.jpt.common.ui.jface.TreeStateProvider;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.navigator.ICommonContentExtensionSite;
import org.eclipse.ui.navigator.ICommonLabelProvider;

/**
 * @see NavigatorContentProvider
 * <p>
 * This provider can be used as the label provider
 * in extensions of <code>org.eclipse.ui.navigator.navigatorContent</code>.
 */
public class NavigatorLabelProvider
	extends BaseLabelProvider
	implements ICommonLabelProvider
{
	/**
	 * "Partner" content provider that holds the tree
	 * state provider.
	 */
	private NavigatorContentProvider contentProvider;

	/**
	 * Listen to the delegate provider and forward any events.
	 */
	private DelegateListener delegateListener;


	public NavigatorLabelProvider() {
		super();
	}

	/**
	 * @see #dispose()
	 */
	public void init(ICommonContentExtensionSite config) {
		this.contentProvider = (NavigatorContentProvider) config.getExtension().getContentProvider();
		this.delegateListener = new DelegateListener();
		this.getDelegate().addListener(this.delegateListener);
	}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return this.getDelegate().isLabelProperty(element, property);
	}

	public Image getImage(Object element) {
		return this.getDelegate().getImage(element);
	}

	public String getText(Object element) {
		return this.getDelegate().getText(element);
	}

	public String getDescription(Object element) {
		return this.getDelegate().getDescription(element);
	}

	public void saveState(IMemento memento) {
		// TODO
	}

	public void restoreState(IMemento memento) {
		// TODO
	}

	private TreeStateProvider getDelegate() {
		return this.contentProvider.getDelegate();
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}

	/**
	 * NB: The delegate will remove our listener when it is disposed
	 * in {@link NavigatorContentProvider#dispose()}. :-(
	 * @see BaseLabelProvider#dispose()
	 */
	@Override
	public void dispose() {
		super.dispose();
	}


	// ********** delegate listener **********

	/* CU private */ class DelegateListener
		extends LabelProviderAdapter
	{
		@Override
		public void labelProviderChanged(LabelProviderChangedEvent event) {
			NavigatorLabelProvider.this.labelProviderChanged(event);
		}
	}

	/* CU private */ void labelProviderChanged(LabelProviderChangedEvent event) {
		// forward the event with *this* provider as the source
		this.fireLabelProviderChanged(new LabelProviderChangedEvent(this, event.getElements()));
	}
}
