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
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.details.JpaDetailsPage;
import org.eclipse.jpt.ui.details.JpaDetailsProvider;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.Tracing;
import org.eclipse.jpt.ui.internal.platform.JpaPlatformUiRegistry;
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
	private JpaDetailsPage<JpaStructureNode> currentPage;

	/**
	 * The current selection used to show the right <code>IJpaDetailsPage</code>.
	 */
	private JpaSelection currentSelection;

	/**
	 * key: Object content node id,  value: Composite page.
	 */
	private Map<Object, JpaDetailsPage<? extends JpaStructureNode>> detailsPages;

	/**
	 * Creates a new <code>JpaDetailsView</code>.
	 */
	public JpaDetailsView() {
		super(JptUiMessages.JpaDetailsView_viewNotAvailable);
	}

	private JpaDetailsPage<? extends JpaStructureNode> buildDetailsPage(JpaStructureNode structureNode) {
		JpaDetailsProvider detailsProvider = getDetailsProvider(structureNode);

		if (detailsProvider == null) {
			return null;
		}

		String id = structureNode.getId();

		Composite container = getWidgetFactory().createComposite(getPageBook());
		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		JpaDetailsPage<? extends JpaStructureNode> page = detailsProvider.buildDetailsPage(
			container,
			id,
			getWidgetFactory()
		);

		if (page != null) {
			this.detailsPages.put(id, page);
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

	private JpaDetailsPage<? extends JpaStructureNode> getDetailsPage(JpaStructureNode structureNode) {
		if (detailsPages.containsKey(structureNode.getId())) {
			JpaDetailsPage<? extends JpaStructureNode> page =  detailsPages.get(structureNode.getId());

			if ((page != null) &&
					(page.getControl().isDisposed())) {
				detailsPages.remove(structureNode.getId());
			}
			else {
				return page;
			}
		}
		return buildDetailsPage(structureNode);
	}

	private JpaDetailsProvider getDetailsProvider(JpaStructureNode structureNode) {

		String platformId = structureNode.getJpaProject().getJpaPlatform().getId();
		JpaPlatformUi jpaPlatformUI = JpaPlatformUiRegistry.instance().getJpaPlatformUi(platformId);
		return jpaPlatformUI.getDetailsProvider(structureNode);

		//TODO this view and the detailsProviders Map is not created on a per project basis.
		//the detailsProviders and their fileContentTypes could overlap across project, this would cause problems with storing this map.
	}

	public JpaSelection getSelection() {
		return currentSelection;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();

		this.currentSelection = JpaSelection.NULL_SELECTION;
		this.detailsPages     = new HashMap<Object, JpaDetailsPage<? extends JpaStructureNode>>();
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
	public void select(JpaSelection jpaSelection) {
		if (jpaSelection.equals(currentSelection)) {
			return;
		}

		currentSelection = jpaSelection;

		if (jpaSelection != JpaSelection.NULL_SELECTION) {
			JpaStructureNode newNode = jpaSelection.getSelectedNode();
			JpaDetailsPage<? extends JpaStructureNode> newPage = getDetailsPage(newNode);
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
	private void setCurrentPage(JpaDetailsPage<? extends JpaStructureNode> page) {

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

		JpaDetailsPage<JpaStructureNode> newPage = (JpaDetailsPage<JpaStructureNode>) page;

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

		//no need to show the page again if it is still the same
		if (newPage != null && currentPage == newPage) {
			return;
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