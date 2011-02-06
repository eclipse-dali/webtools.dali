/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.views.structure;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jpt.jpa.ui.internal.selection.JpaSelection;
import org.eclipse.jpt.jpa.ui.internal.selection.JpaSelectionManager;
import org.eclipse.jpt.jpa.ui.internal.selection.SelectionManagerFactory;
import org.eclipse.jpt.jpa.ui.structure.JpaStructureProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.MessagePage;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PageBookView;

public class JpaStructureView
	extends PageBookView
{
	public JpaStructureView() {
		super();
	}
	
	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		
		JpaSelectionManager selectionManager =
			SelectionManagerFactory.getSelectionManager(this.getViewSite().getWorkbenchWindow());
		
		selectionManager.register(this);
		this.select(selectionManager.getCurrentSelection());
	}
	
	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		// do same thing as partActivated, which will check to see if the 
		// part is an editor, in which case, we want to show the right page
		partActivated(part);
	}
	
	@Override
	protected boolean isImportant(IWorkbenchPart part) {
		return part instanceof IEditorPart;
	}
	
	@Override
	protected IWorkbenchPart getBootstrapPart() {
		IWorkbenchPage page = this.getSite().getPage();
        return (page == null) ? null : page.getActiveEditor();
	}
	
	@Override
	protected IPage createDefaultPage(PageBook book) {
		MessagePage page = new MessagePage();
        this.initPage(page);
        page.createControl(book);
        page.setMessage(JptUiMessages.JpaStructureView_structureNotAvailable);
        return page;
	}
	
	@Override
	protected PageRec doCreatePage(IWorkbenchPart part) {
		// use the platform adapter service so the structure view can be
		// associated with any IEditorPart that has an associated adapter
		// factory that can give us the JPA file associated with the editor part
		// @see org.eclipse.jpt.jpa.ui.internal.EditorPartAdapterFactory
		JpaFile jpaFile = (JpaFile) part.getAdapter(JpaFile.class);
		if (jpaFile == null) {
			return null;
		}

		JpaStructureProvider structureProvider = this.getStructureProvider(jpaFile);
		if (structureProvider == null) {
			return null;
		}

		JpaStructurePage page = new JpaStructurePage(this, jpaFile, structureProvider);
		this.initPage(page);
		page.createControl(this.getPageBook());
		return new PageRec(part, page);
	}
	
	private JpaStructureProvider getStructureProvider(JpaFile jpaFile) {
		return this.getPlatformUi(jpaFile).getStructureProvider(jpaFile);
	}
	
	private JpaPlatformUi getPlatformUi(JpaFile jpaFile) {
		return JptJpaUiPlugin.instance().getJpaPlatformUi(jpaFile.getJpaProject().getJpaPlatform());
	}
	
	@Override
	protected void doDestroyPage(IWorkbenchPart part, PageRec pageRecord) {
		JpaStructurePage page = (JpaStructurePage) pageRecord.page;
        page.dispose();
        pageRecord.dispose();
	}
	
	public JpaSelection getJpaSelection() {
		if (this.getCurrentPage() != this.getDefaultPage()) {
			return ((JpaStructurePage) this.getCurrentPage()).getJpaSelection();
		}
		return JpaSelection.NULL_SELECTION;
	}
	
	public void select(JpaSelection newSelection) {
		// correct page should be shown
		if (this.getCurrentPage() != this.getDefaultPage()) {
			((JpaStructurePage) this.getCurrentPage()).select(newSelection);
		}
	}
	
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		this.getSelectionProvider().addSelectionChangedListener(listener);
	}
	
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		this.getSelectionProvider().removeSelectionChangedListener(listener);
	}

}
