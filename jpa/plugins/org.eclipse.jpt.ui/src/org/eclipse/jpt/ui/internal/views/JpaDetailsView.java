/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved. This
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
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.ui.internal.IJpaPlatformUi;
import org.eclipse.jpt.ui.internal.JpaUiMessages;
import org.eclipse.jpt.ui.internal.PlatformRegistry;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsPage;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsProvider;
import org.eclipse.jpt.ui.internal.selection.Selection;
import org.eclipse.jpt.utility.internal.iterators.CloneIterator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

public class JpaDetailsView extends AbstractJpaView 
{	
	private Selection currentSelection;
	
	private IJpaDetailsPage currentPage;
	
	/* key: String file content id,  value: IJpaDetailsProvider */
	private Map<String, IJpaDetailsProvider> detailsProviders;
	
	/* key: Object content node id,  value: Composite page */
	private Map<Object, IJpaDetailsPage> detailsPages;
	
	public JpaDetailsView() {
		super(JpaUiMessages.JpaDetailsView_viewNotAvailable);
		this.currentSelection = Selection.NULL_SELECTION;
		this.detailsProviders = new HashMap<String, IJpaDetailsProvider>();
		this.detailsPages = new HashMap<Object, IJpaDetailsPage>();
	}
	
	
	public Selection getSelection() {
		return currentSelection;
	}
	
	public void select(Selection newSelection) {
		if (newSelection.equals(currentSelection)) {
			return;
		}
		
		currentSelection = newSelection;
		
		if (newSelection != Selection.NULL_SELECTION) {
			IJpaContentNode newNode = newSelection.getSelectedNode();
			IJpaDetailsPage newPage = getDetailsPage(newNode);
			setCurrentPage(newPage);
		}
		else if (currentSelection != Selection.NULL_SELECTION) {
			setCurrentPage(null);
		}
	}
	
	private IJpaDetailsPage getDetailsPage(IJpaContentNode contentNode) {
		if (detailsPages.containsKey(contentNode.getId())) {
			IJpaDetailsPage page =  detailsPages.get(contentNode.getId());
			
			if ((page != null) &&
					(page.getControl().isDisposed())) {
				detailsPages.remove(contentNode.getId());
			}
			else {
				return page;
			}
		}
		
		return buildDetailsPage(contentNode);
	}
	
	private IJpaDetailsPage buildDetailsPage(IJpaContentNode contentNode) {
		IJpaDetailsProvider detailsProvider =
					getDetailsProvider(contentNode);
			
		if (detailsProvider == null) {
			return null;
		}
		Composite parentComposite = getWidgetFactory().createComposite(pageBook, SWT.NONE);
		parentComposite.setLayout(new FillLayout(SWT.VERTICAL));
		IJpaDetailsPage page = 
			detailsProvider.buildDetailsPage(parentComposite, contentNode.getId(), getWidgetFactory());
		
		if (page != null) {
			detailsPages.put(contentNode.getId(), page);
		}
		
		return page;
	}
	
	private IJpaDetailsProvider getDetailsProvider(IJpaContentNode contentNode) {
		String contentId = contentNode.getJpaFile().getContentId();
		IJpaDetailsProvider provider = detailsProviders.get(contentId);
		
		if (provider == null) {
			String platformId = contentNode.getJpaProject().getPlatform().getId();
			IJpaPlatformUi jpaPlatformUI = PlatformRegistry.INSTANCE.getJpaPlatform(platformId);
			for (IJpaDetailsProvider p : jpaPlatformUI.detailsProviders()) {
				if (p.fileContentType().equals(contentId)) {
					provider = p;
					break;
				}
			}
			
			//TODO this view and the detailsProviders Map is not created on a per project basis.
			//the detailsProviders and their fileContentTypes could overlap across project, this would cause problems with storing this map.
			
			if (provider != null) {
				detailsProviders.put(contentId, provider);
			}
		}
		
		return provider;	
	}
	
	private void setCurrentPage(IJpaDetailsPage newPage) {
		// depopulate old page
		if ((currentPage != null) && (currentPage != newPage)) {
			currentPage.populate(null);
		}
		
		// populate new page
		if (newPage != null) {
			newPage.populate(currentSelection.getSelectedNode());
		}
		
		currentPage = newPage;
		
		// show new page
		if (newPage == null) {
			showDefaultPage();
		}
		else {
			pageBook.showPage(newPage.getControl().getParent());
		}
	}
	
	public void dispose() {
		for (Iterator<String> stream = new CloneIterator<String>(detailsProviders.keySet()); stream.hasNext(); ) {
			String key = stream.next();
			IJpaDetailsProvider provider = detailsProviders.remove(key);
			provider.dispose();
		}
		
		for (Iterator<Object> stream = new CloneIterator<Object>(detailsPages.keySet()); stream.hasNext(); ) {
			Object key = stream.next();
			IJpaDetailsPage detailsPage = detailsPages.remove(key);
			detailsPage.dispose();
		}
		
		currentSelection = Selection.NULL_SELECTION;
		currentPage = null;

		super.dispose();
	}
}