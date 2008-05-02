/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.views.structure;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.ui.JpaPlatformUi;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.selection.JpaSelection;
import org.eclipse.jpt.ui.internal.selection.JpaSelectionManager;
import org.eclipse.jpt.ui.internal.selection.SelectionManagerFactory;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.MessagePage;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PageBookView;

public class JpaStructureView extends PageBookView
{
	public JpaStructureView() {
		super();
	}
	
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		
		JpaSelectionManager selectionManager =
			SelectionManagerFactory.getSelectionManager(getViewSite().getWorkbenchWindow());
		
		selectionManager.register(this);
		select(selectionManager.getCurrentSelection());
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
		JpaStructureProvider structureProvider = 
			structureProvider(part);
		if (structureProvider != null) {
			JpaStructurePage page = new JpaStructurePage(this, structureProvider);
			initPage(page);
			page.createControl(getPageBook());
			return new PageRec(part, page);
		}
		return null;
	}
	
	private JpaStructureProvider structureProvider(IWorkbenchPart part) {
		JpaFile jpaFile = 
			(JpaFile) part.getAdapter(JpaFile.class);
		
		if (jpaFile == null) {
			return null;
		}
		
		JpaPlatformUi platformUi = JptUiPlugin.getPlugin().jpaPlatformUi(jpaFile.getJpaProject().getJpaPlatform());
		return platformUi.buildStructureProvider(jpaFile);
	}
	
	@Override
	protected void doDestroyPage(IWorkbenchPart part, PageRec pageRecord) {
		JpaStructurePage page = (JpaStructurePage) pageRecord.page;
		removeSelectionChangedListener(page);
        page.dispose();
        pageRecord.dispose();
	}
	
	public JpaSelection getSelection() {
		if (getCurrentPage() != getDefaultPage()) {
			return ((JpaStructurePage) getCurrentPage()).getSelection();
		}
		else {
			return JpaSelection.NULL_SELECTION;
		}
	}
	
	public void select(JpaSelection newSelection) {
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
