/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.views;

import java.util.HashMap;
import java.util.Map;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.ui.internal.IJpaPlatformUi;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.JptUiPlugin;
import org.eclipse.jpt.ui.internal.Tracing;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsPage;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsProvider;
import org.eclipse.jpt.ui.internal.platform.JpaPlatformUiRegistry;
import org.eclipse.jpt.ui.internal.selection.IJpaSelection;
import org.eclipse.jpt.ui.internal.selection.JpaSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

/**
 * The JPA view that shows the details a mapping (either type or attribute).
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public class JpaDetailsView extends AbstractJpaView
{
	/**
	 * The current <code>IJpaDetailsPage</code> that was retrieve based on the
	 * current selection.
	 */
	private IJpaDetailsPage<IJpaContextNode> currentPage;

	/**
	 * The current selection used to show the right <code>IJpaDetailsPage</code>.
	 */
	private IJpaSelection currentSelection;

	/**
	 * key: Object content node id,  value: Composite page.
	 */
	private Map<Object, IJpaDetailsPage<? extends IJpaContextNode>> detailsPages;

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

		Composite container = getWidgetFactory().createComposite(getPageBook());
		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		IJpaDetailsPage<? extends IJpaContextNode> page = detailsProvider.buildDetailsPage(
			container,
			contextNode,
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

		detailsPages.clear();

		currentSelection = JpaSelection.NULL_SELECTION;
		currentPage = null;

		super.dispose();
	}

	private IJpaDetailsPage<? extends IJpaContextNode> getDetailsPage(IJpaContextNode contextNode) {
		//TODO commented out the caching of the details provider for the time being,
		//someone should probably revist the possibility of caching
//		String id = contextNode.jpaProject().jpaPlatform().getId();
//
//		if (detailsPages.containsKey(id)) {
//			IJpaDetailsPage<? extends IJpaContextNode> page = detailsPages.get(id);
//
//			if ((page != null) && page.getControl().isDisposed()) {
//				detailsPages.remove(id);
//			}
//			else {
//				return page;
//			}
//		}
//
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
	}

	private void log(String message) {
		if (Tracing.booleanDebugOption(Tracing.UI_DETAILS_VIEW)) {
			Tracing.log(message);
		}
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
	private void setCurrentPage(IJpaDetailsPage<? extends IJpaContextNode> page) {

		// Unpopulate old page
		if (currentPage != null) {
			try {
				log("JpaDetailsView.setCurrentPage() : disposing of current page");

				currentPage.setSubject(null);
			}
			catch (Exception e) {
				JptUiPlugin.log(e);
			}
		}

		IJpaDetailsPage<IJpaContextNode> newPage = (IJpaDetailsPage<IJpaContextNode>) page;

		// Populate new page
		if (page != null) {
			try {
				log("JpaDetailsView.setCurrentPage() : populating new page");
				newPage.setSubject(currentSelection.getSelectedNode());
			}
			catch (Exception e) {
				// Show error page
				page = null;
				JptUiPlugin.log(e);
			}
		}
		else {
			log("JpaDetailsView.setCurrentPage() : No page to populate");
		}

		currentPage = newPage;

		// Show new page
		if (page == null) {
			showDefaultPage();
		}
		else {
			showPage(page.getControl());
		}
	}
}