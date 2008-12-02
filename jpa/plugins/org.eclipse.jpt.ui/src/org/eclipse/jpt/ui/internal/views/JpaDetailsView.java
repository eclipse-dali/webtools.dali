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
import org.eclipse.core.resources.IFile;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.utility.PlatformUtilities;
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

	//TODO this is crap, a Map of Maps of Maps. Needs to be done differently, the factory/platform should handle caching instead
	// key1 platform id
	// key2 content type id
	// key3 structure node type
	// value Composite page
	private Map<String, Map<String, Map<String, JpaDetailsPage<? extends JpaStructureNode>>>> detailsPages;

	/**
	 * Creates a new <code>JpaDetailsView</code>.
	 */
	public JpaDetailsView() {
		super(JptUiMessages.JpaDetailsView_viewNotAvailable);
	}

	@Override
	protected void initialize() {
		super.initialize();

		this.currentSelection = JpaSelection.NULL_SELECTION;
		this.detailsPages     = new HashMap<String, Map<String, Map<String, JpaDetailsPage<? extends JpaStructureNode>>>>();
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
			structureNode,
			getWidgetFactory()
		);

		if (page != null) {
			String platformId = structureNode.getJpaProject().getJpaPlatform().getId();
			this.detailsPages.get(platformId);
			Map<String, Map<String, JpaDetailsPage<? extends JpaStructureNode>>> platformDetailsPages = this.detailsPages.get(platformId);
			if (platformDetailsPages == null) {
				platformDetailsPages = new HashMap<String, Map<String, JpaDetailsPage<? extends JpaStructureNode>>>();
				this.detailsPages.put(platformId, platformDetailsPages);
			}
			//not sure how this couldn't be a file, at least not if we are in JpaDetailsView trying to view it
			IFile file = (IFile) structureNode.getResource();
			//also, don't see how the contentType can be null, so not checking, can't have a JpaStructureNode without a contentType
			String contentTypeId = PlatformUtilities.getContentType(file).getId();
			Map<String, JpaDetailsPage<? extends JpaStructureNode>> contentTypeDetailsPages = platformDetailsPages.get(contentTypeId);
			if (contentTypeDetailsPages == null) {
				contentTypeDetailsPages = new HashMap<String, JpaDetailsPage<? extends JpaStructureNode>>();
				platformDetailsPages.put(contentTypeId, contentTypeDetailsPages);
			}
			contentTypeDetailsPages.put(id, page);
		}

		return page;
	}

	@Override
	public void dispose() {

		this.detailsPages.clear();

		this.currentSelection = JpaSelection.NULL_SELECTION;
		this.currentPage = null;

		super.dispose();
	}

	protected String getContentTypeId(JpaStructureNode structureNode) {
		//not sure how this couldn't be a file, at least not if we are in JpaDetailsView trying to view it
		IFile file = (IFile) structureNode.getResource();
		//also, don't see how the contentType can be null, so not checking, can't have a JpaStructureNode without a contentType
		return PlatformUtilities.getContentType(file).getId();
	}
	
	private JpaDetailsPage<? extends JpaStructureNode> getDetailsPage(JpaStructureNode structureNode) {
		String platformId = structureNode.getJpaProject().getJpaPlatform().getId();
		if (this.detailsPages.containsKey(platformId)) {
			Map<String, Map<String, JpaDetailsPage<? extends JpaStructureNode>>> platformDetailsPages = this.detailsPages.get(platformId);
			String contentTypeId = getContentTypeId(structureNode);
			if (platformDetailsPages.containsKey(contentTypeId)) {
				Map<String, JpaDetailsPage<? extends JpaStructureNode>> contentTypeDetailsPages = platformDetailsPages.get(contentTypeId);	
				if (contentTypeDetailsPages.containsKey(structureNode.getId())) {
					JpaDetailsPage<? extends JpaStructureNode> page =  contentTypeDetailsPages.get(structureNode.getId());
		
					if ((page != null) &&
							(page.getControl().isDisposed())) {
						platformDetailsPages.remove(structureNode.getId());
					}
					else {
						return page;
					}
				}
			}
		}
		return buildDetailsPage(structureNode);
	}

	private JpaDetailsProvider getDetailsProvider(JpaStructureNode structureNode) {
		String platformId = structureNode.getJpaProject().getJpaPlatform().getId();
		JpaPlatformUi jpaPlatformUI = JpaPlatformUiRegistry.instance().getJpaPlatformUi(platformId);
		return jpaPlatformUI.getDetailsProvider(structureNode);
	}

	public JpaSelection getSelection() {
		return this.currentSelection;
	}

	private void log(String message) {
		if (Tracing.booleanDebugOption(Tracing.UI_DETAILS_VIEW)) {
			Tracing.log(message);
		}
	}

	@Override
	public void select(JpaSelection jpaSelection) {
		if (jpaSelection.equals(this.currentSelection)) {
			return;
		}

		this.currentSelection = jpaSelection;

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
		if (this.currentPage != null) {
			try {
				log("JpaDetailsView.setCurrentPage() : disposing of current page");

				this.currentPage.setSubject(null);
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
				newPage.setSubject(this.currentSelection.getSelectedNode());
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
		if (newPage != null && this.currentPage == newPage) {
			return;
		}
		this.currentPage = newPage;

		// Show new page
		if (page == null) {
			showDefaultPage();
		}
		else {
			showPage(page.getControl());
		}
	}
}