/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.views;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.ui.internal.IJpaPlatformUi;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsPage;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsProvider;
import org.eclipse.jpt.ui.internal.platform.JpaPlatformUiRegistry;
import org.eclipse.jpt.ui.internal.selection.IJpaSelection;
import org.eclipse.jpt.ui.internal.selection.JpaSelection;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * The JPA view that shows the details of ... TODO
 *
 * @version 2.0
 * @since 1.0
 */
public class JpaDetailsView extends AbstractJpaView
{
	/**
	 * The current <code>IJpaDetailsPage</code> that was retrieve based on the
	 * current selection.
	 */
	private IJpaDetailsPage<? extends IJpaContextNode> currentPage;

	/**
	 * The current selection used to show the right <code>IJpaDetailsPage</code>.
	 */
	private IJpaSelection currentSelection;

	/**
	 * key: Object content node id,  value: Composite page.
	 */
	private Map<Object, IJpaDetailsPage<? extends IJpaContextNode>> detailsPages;

	/**
	 * Key: String file content id,  value: IJpaDetailsProvider.
	 */
	private Map<String, IJpaDetailsProvider> detailsProviders;

	/**
	 * Creates a new <code>JpaDetailsView</code>.
	 */
	public JpaDetailsView() {
		super(JptUiMessages.JpaDetailsView_viewNotAvailable);
	}

	private IJpaDetailsPage<? extends IJpaContextNode> buildDetailsPage(IJpaContextNode contextNode) {
		IJpaDetailsProvider detailsProvider = getDetailsProvider(contextNode);

		if (detailsProvider == null) {
			return null;
		}

		String id = contextNode.jpaProject().jpaPlatform().getId();

		Composite parentComposite = getWidgetFactory().createComposite(getPageBook(), SWT.NONE);
		parentComposite.setLayout(new FillLayout(SWT.VERTICAL));

		IJpaDetailsPage<? extends IJpaContextNode> page = detailsProvider.buildDetailsPage(
			parentComposite,
			id,
			getWidgetFactory()
		);

		if (page != null) {
			detailsPages.put(id, page);
		}

		return page;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void dispose() {
		for (Iterator<String> stream = new CloneIterator<String>(detailsProviders.keySet()); stream.hasNext(); ) {
			String key = stream.next();
			IJpaDetailsProvider provider = detailsProviders.remove(key);
			provider.dispose();
		}

		for (Iterator<Object> stream = new CloneIterator<Object>(detailsPages.keySet()); stream.hasNext(); ) {
			Object key = stream.next();
			IJpaDetailsPage<? extends IJpaContextNode> detailsPage = detailsPages.remove(key);
			detailsPage.dispose();
		}

		currentSelection = JpaSelection.NULL_SELECTION;
		currentPage = null;

		super.dispose();
	}

	private IJpaDetailsPage<? extends IJpaContextNode> getDetailsPage(IJpaContextNode contextNode) {
		String id = contextNode.jpaProject().jpaPlatform().getId();

		if (detailsPages.containsKey(id)) {
			IJpaDetailsPage<? extends IJpaContextNode> page = detailsPages.get(id);

			if ((page != null) && page.getControl().isDisposed()) {
				detailsPages.remove(id);
			}
			else {
				return page;
			}
		}

		return buildDetailsPage(contextNode);
	}

	private IJpaDetailsProvider getDetailsProvider(IJpaContextNode contextNode) {
		String contentId = contextNode.getJpaFile().getContentId();
		IJpaDetailsProvider provider = detailsProviders.get(contentId);

		if (provider == null) {
			String platformId = contextNode.jpaProject().jpaPlatform().getId();
			IJpaPlatformUi jpaPlatformUI = JpaPlatformUiRegistry.instance().jpaPlatform(platformId);
			provider = jpaPlatformUI.detailsProvider(contentId);

			//TODO this view and the detailsProviders Map is not created on a per project basis.
			//the detailsProviders and their fileContentTypes could overlap across project, this would cause problems with storing this map.

			if (provider != null) {
				detailsProviders.put(contentId, provider);
			}
		}

		return provider;
	}

	public IJpaSelection getSelection() {
		return currentSelection;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();
		this.currentSelection = IJpaSelection.NULL_SELECTION;
		this.detailsProviders = new HashMap<String, IJpaDetailsProvider>();
		this.detailsPages = new HashMap<Object, IJpaDetailsPage<? extends IJpaContextNode>>();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void select(IJpaSelection jpaSelection) {
		if (jpaSelection.equals(currentSelection)) {
			return;
		}

		currentSelection = jpaSelection;

		if (jpaSelection != IJpaSelection.NULL_SELECTION) {
			IJpaContextNode newNode = jpaSelection.getSelectedNode();
			IJpaDetailsPage<? extends IJpaContextNode> newPage = getDetailsPage(newNode);
			setCurrentPage(newPage);
		}
		else {
			setCurrentPage(null);
		}
	}

	/**
	 * Changes the current page and show the given page.
	 *
	 * @param newPage The new page to display
	 */
	@SuppressWarnings("unchecked")
	private void setCurrentPage(IJpaDetailsPage<? extends IJpaContextNode> newPage) {
		// Depopulate old page
		if ((currentPage != null) && (currentPage != newPage)) {
			currentPage.populate(null);
		}

		// Populate new page
		if (newPage != null) {
			IJpaDetailsPage<IJpaContextNode> page = (IJpaDetailsPage<IJpaContextNode>) newPage;
			page.populate(currentSelection.getSelectedNode());
		}

		currentPage = newPage;

		// Show new page
		if (newPage == null) {
			showDefaultPage();
		}
		else {
			showPage(newPage.getControl().getParent());
		}
	}
}