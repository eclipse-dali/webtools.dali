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
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.WritablePropertyValueModel;
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
	 * The holder of the current selection, which is the
	 * <code>IJpaDetailsPage</code>'s subject.
	 */
	private WritablePropertyValueModel<IJpaContextNode> subjectHolder;

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

		Composite parentComposite = getWidgetFactory().createComposite(getPageBook());
		parentComposite.setLayout(new FillLayout(SWT.HORIZONTAL));

		IJpaDetailsPage<? extends IJpaContextNode> page = detailsProvider.buildDetailsPage(
			subjectHolder,
			parentComposite,
			contextNode,
			getWidgetFactory()
		);

		if (page != null) {
			detailsPages.put(id, page);
		}

		return page;
	}

	private WritablePropertyValueModel<IJpaContextNode> buildSubjectHolder() {
		return new SimplePropertyValueModel<IJpaContextNode>();
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public void dispose() {

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

		String platformId = contextNode.jpaProject().jpaPlatform().getId();
		IJpaPlatformUi jpaPlatformUI = JpaPlatformUiRegistry.instance().jpaPlatform(platformId);
		return jpaPlatformUI.detailsProvider(contextNode);

		//TODO this view and the detailsProviders Map is not created on a per project basis.
		//the detailsProviders and their fileContentTypes could overlap across project, this would cause problems with storing this map.
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
		this.detailsPages     = new HashMap<Object, IJpaDetailsPage<? extends IJpaContextNode>>();
		this.subjectHolder    = buildSubjectHolder();
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
	 * Changes the current page and shows the given page.
	 *
	 * @param newPage The new page to display
	 */
	@SuppressWarnings("unchecked")
	private void setCurrentPage(IJpaDetailsPage<? extends IJpaContextNode> newPage) {

		// Depopulate old page
		if ((currentPage != null) && (currentPage != newPage)) {
			try {
				currentPage.dispose();
				subjectHolder.setValue(null);
			}
			finally {
				// Log the exception
			}
		}

		// Populate new page
		if (newPage != null) {
			try {
				// TODO: Tweak the IJpaDetailsPage to only listen to the subject change
				IJpaDetailsPage<IJpaContextNode> page = (IJpaDetailsPage<IJpaContextNode>) newPage;
				subjectHolder.setValue(currentSelection.getSelectedNode());
				page.populate();
			}
			finally {
				// Show error page
			}
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