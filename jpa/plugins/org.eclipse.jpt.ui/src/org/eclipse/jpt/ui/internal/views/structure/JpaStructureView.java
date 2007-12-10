/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.views.structure;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.selection.IJpaSelection;
import org.eclipse.jpt.ui.internal.structure.IJpaStructureProvider;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.MessagePage;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PageBookView;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class JpaStructureView extends PageBookView
{
	private TabbedPropertySheetWidgetFactory widgetFactory = 
			new TabbedPropertySheetWidgetFactory();
	
	
	public JpaStructureView() {
		super();
	}
	
	
	@Override
	protected boolean isImportant(IWorkbenchPart part) {
		return part instanceof IEditorPart;
	}
	
	@Override
	protected IWorkbenchPart getBootstrapPart() {
		IWorkbenchPage page = getSite().getPage();
        if (page != null) {
			return page.getActiveEditor();
		}

        return null;
	}
	
	@Override
	protected IPage createDefaultPage(PageBook book) {
		MessagePage page = new MessagePage();
        initPage(page);
        page.createControl(book);
        page.setMessage(JptUiMessages.JpaStructureView_structureNotAvailable);
        return page;
	}
	
	@Override
	protected PageRec doCreatePage(IWorkbenchPart part) {
		IJpaStructureProvider structureProvider = 
				(IJpaStructureProvider) part.getAdapter(IJpaStructureProvider.class);
		if (structureProvider != null) {
			JpaStructurePage page = new JpaStructurePage(structureProvider);
			page.createControl(getPageBook());
			return new PageRec(part, page);
		}
		return null;
	}
	
	@Override
	protected void doDestroyPage(IWorkbenchPart part, PageRec pageRecord) {
		JpaStructurePage page = (JpaStructurePage) pageRecord.page;
        page.dispose();
        pageRecord.dispose();
	}
	
	public IJpaSelection getSelection() {
		if (getCurrentPage() != getDefaultPage()) {
			return ((JpaStructurePage) getCurrentPage()).getSelection();
		}
		else {
			return IJpaSelection.NULL_SELECTION;
		}
	}
	
	public void select(IJpaSelection newSelection) {
		// correct page should be shown
		if (getCurrentPage() != getDefaultPage()) {
			((JpaStructurePage) getCurrentPage()).select(newSelection);
		}
	}
	
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		getSelectionProvider().addSelectionChangedListener(listener);
	}
	
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		getSelectionProvider().removeSelectionChangedListener(listener);
	}
}
