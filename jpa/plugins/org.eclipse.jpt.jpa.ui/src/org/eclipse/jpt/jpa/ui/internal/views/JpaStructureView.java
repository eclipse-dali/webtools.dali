/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.views;

import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.common.core.internal.utility.PlatformTools;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.jpa.ui.JpaWorkbench;
import org.eclipse.jpt.jpa.ui.internal.JptUiMessages;
import org.eclipse.jpt.jpa.ui.selection.JpaEditorManager;
import org.eclipse.jpt.jpa.ui.selection.JpaViewManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.IPage;
import org.eclipse.ui.part.Page;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.part.PageBookView;

/**
 * The JPA structure view is a page book view that maintains a
 * {@link JpaStructurePage JPA structure page}
 * per editor and a default page when the editor does not contain JPA
 * content.
 * <p>
 * See <code>org.eclipse.jpt.jpa.ui/plugin.xml</code>.
 */
public class JpaStructureView
	extends PageBookView
{
	/**
	 * The manager is created when the view's control is
	 * {@link #createPartControl(Composite) created}
	 * and disposed, if necessary, when the view is
	 * {@link #dispose() disposed}.
	 */
	private volatile Manager manager;

	/**
	 * The resource manager is created when the view's control is
	 * {@link #createPartControl(Composite) created}
	 * and disposed, if necessary, when the view is
	 * {@link #dispose() disposed}.
	 */
	private volatile ResourceManager resourceManager;


	public JpaStructureView() {
		super();
	}

	@Override
	public void createPartControl(Composite parent) {
		this.manager = this.buildManager();
		this.resourceManager = this.buildResourceManager();
		super.createPartControl(parent);
	}

	private Manager buildManager() {
		JpaViewManager.PageManager pageManager = this.getPageManager();
		return (pageManager == null) ? null : new Manager(pageManager);
	}

	/**
	 * Go to the singleton in the sky.
	 * <p>
	 * <strong>NB:</strong> This will trigger the creation of the appropriate
	 * page manager if it does not already exist.
	 */
	private JpaViewManager.PageManager getPageManager() {
		return (JpaViewManager.PageManager) this.getAdapter(JpaViewManager.PageManager.class);
	}

	private ResourceManager buildResourceManager() {
		JpaWorkbench jpaWorkbench = this.getJpaWorkbench();
		return (jpaWorkbench != null) ? jpaWorkbench.buildLocalResourceManager() : new LocalResourceManager(JFaceResources.getResources(this.getWorkbench().getDisplay()));
	}

	private JpaWorkbench getJpaWorkbench() {
		return PlatformTools.getAdapter(this.getWorkbench(), JpaWorkbench.class);
	}

	private IWorkbench getWorkbench() {
		return this.getSite().getWorkbenchWindow().getWorkbench();
	}

	@Override
	protected IPage createDefaultPage(PageBook book) {
		DefaultPage page = new DefaultPage();
        this.initPage(page);
        page.createControl(book);
        return page;
	}

	/**
	 * Start with the currently active editor.
	 */
	@Override
	protected IWorkbenchPart getBootstrapPart() {
		IWorkbenchPage page = this.getSite().getPage();
        return (page == null) ? null : page.getActiveEditor();
	}

	/**
	 * Behave the same way as {@link #partActivated(IWorkbenchPart)};
	 * i.e. {@link #isImportant(IWorkbenchPart) check whether the specified
	 * part is an editor} and display the appropriate page if it is.
	 */
	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
		super.partBroughtToTop(part);
		this.partActivated(part);
	}

	/**
	 * Display the appropriate page if an editor is
	 * {@link #partActivated(IWorkbenchPart) activated}
	 * or {@link #partBroughtToTop(IWorkbenchPart) brought to the top}.
	 */
	@Override
	protected boolean isImportant(IWorkbenchPart part) {
		return part instanceof IEditorPart;
	}

	/**
	 * The specified part is an editor (see {@link #isImportant(IWorkbenchPart)}.
	 */
	@Override
	protected PageRec doCreatePage(IWorkbenchPart part) {
		IEditorPart editor = (IEditorPart) part;
		if (this.manager == null) {
			return null;
		}
		JpaEditorManager editorManager = this.manager.getEditorManager(editor);
		if (editorManager == null) {
			// if there is no editor manager corresponding to the current
			// editor return null so the default page is displayed
			return null;
		}
		JpaStructurePage page = new JpaStructurePage(this, editorManager, this.resourceManager);
		this.initPage(page);
		page.createControl(this.getPageBook());
		return new PageRec(editor, page);
	}

	@Override
	protected void doDestroyPage(IWorkbenchPart part, PageRec pageRecord) {
		JpaStructurePage page = (JpaStructurePage) pageRecord.page;
        page.dispose();
        pageRecord.dispose();
	}

	@Override
	public void dispose() {
		super.dispose();
		if (this.manager != null) {
			this.manager.dispose();
		}
		if (this.resourceManager != null) {
			this.resourceManager.dispose();
		}
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this);
	}


	// ********** JPA view manager **********

	/**
	 * Adapter to the view's page manager.
	 */
	/* CU private */ class Manager
		implements JpaViewManager
	{
		/**
		 * The manager for the structure view's workbench page.
		 */
		private final JpaViewManager.PageManager pageManager;


		Manager(JpaViewManager.PageManager pageManager) {
			super();
			if (pageManager == null) {
				throw new NullPointerException();  // shouldn't happen...
			}
			this.pageManager = pageManager;
			this.pageManager.addViewManager(this);
		}

		public IViewPart getView() {
			return JpaStructureView.this;
		}

		JpaEditorManager getEditorManager(IEditorPart editor) {
			return this.pageManager.getEditorManager(editor);
		}

		void dispose() {
			this.pageManager.removeViewManager(this);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}


	// ********** default page **********

	/**
	 * Simply display a message stating the JPA structure is not available.
	 * @see org.eclipse.ui.part.MessagePage
	 */
	/* CU private */ static class DefaultPage
		extends Page
	{
		private /* virtually final */ Composite composite;

		DefaultPage() {
			super();
		}

		@Override
		public void createControl(Composite parent) {
			// a composite will put some margins around the label
			this.composite = new Composite(parent, SWT.NULL);
			this.composite.setLayout(new FillLayout());
			Label label = new Label(this.composite, SWT.LEFT | SWT.TOP | SWT.WRAP);
			label.setText(JptUiMessages.JpaStructureView_structureNotAvailable);
		}

		@Override
		public Control getControl() {
			return this.composite;
		}

		@Override
		public void setFocus() {
			this.composite.setFocus();
		}
	}
}
