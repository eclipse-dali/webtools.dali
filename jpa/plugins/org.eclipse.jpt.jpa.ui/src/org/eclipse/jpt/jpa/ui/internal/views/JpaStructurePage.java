/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.views;

import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jpt.common.ui.internal.jface.AbstractSelectionProvider;
import org.eclipse.jpt.common.ui.internal.jface.ItemTreeStateProviderManager;
import org.eclipse.jpt.common.ui.internal.jface.NullItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.ui.internal.jface.SelectionChangedAdapter;
import org.eclipse.jpt.common.ui.internal.jface.SimpleMessageTreeContent;
import org.eclipse.jpt.common.ui.internal.swt.widgets.DisplayTools;
import org.eclipse.jpt.common.ui.jface.ItemTreeStateProviderFactoryProvider;
import org.eclipse.jpt.common.ui.jface.TreeStateProvider;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.ui.JpaPlatformUi;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.selection.JpaEditorManager;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.navigator.IDescriptionProvider;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.Page;

/**
 * The {@link JpaStructureView JPA structure view} is a page book view that
 * maintains a JPA structure page per editor.
 */
public class JpaStructurePage
	extends Page
{
	/**
	 * The structure page's parent page book.
	 * Never <code>null</code>.
	 */
	private final JpaStructureView structureView;

	/**
	 * The manager corresponding to the editor
	 * the structure page is associated with.
	 * Never <code>null</code>.
	 */
	private final JpaEditorManager editorManager;

	/**
	 * Listen for the model or the editor manager to change the
	 * JPA file.
	 */
	private final PropertyChangeListener jpaFileListener = new JpaFileListener();

	/**
	 * Listen for others to change the JPA selection.
	 */
	private final PropertyChangeListener jpaSelectionListener = new JpaSelectionListener();

	/**
	 * Adapt the tree selection provider to the page's selection provider,
	 * which is adapted to the (page book) view's selection provider.
	 */
	private final SelectionProvider selectionProvider = new SelectionProvider();

	/**
	 * The page's main control.
	 */
	private /* virtually final */ Composite control;

	/**
	 * The page's tree viewer.
	 */
	private /* virtually final */ TreeViewer treeViewer;

	/**
	 * Listen for changes to the tree's selection.
	 */
	private final ISelectionChangedListener treePostSelectionListener = new TreePostSelectionChangedListener();

	/**
	 * Resource manager passed to tree state provider.
	 */
	private final ResourceManager resourceManager;


	public JpaStructurePage(JpaStructureView structureView, JpaEditorManager editorManager, ResourceManager resourceManager) {
		super();
		this.structureView = structureView;
		this.editorManager = editorManager;
		// we build a local resource manager because these pages come and go
		// with their corresponding editors
		this.resourceManager = new LocalResourceManager(resourceManager);
	}

	@Override
	public void init(IPageSite pageSite) {
		super.init(pageSite);
		pageSite.setSelectionProvider(this.selectionProvider);
	}

	// TODO use problem decorator
	@Override
	public void createControl(Composite parent) {
		this.control = new Composite(parent, SWT.NULL);
		this.control.setLayout(new FillLayout());
		this.treeViewer = new TreeViewer(this.control, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		this.treeViewer.setAutoExpandLevel(2);
		this.setTreeViewerJpaFile(this.getEditorJpaFile());
		this.engageListeners();
		this.initializeContextMenu();
	}

	private void engageListeners() {
		this.getEditorJpaFileModel().addPropertyChangeListener(PropertyValueModel.VALUE, this.jpaFileListener);
		this.getEditorJpaSelectionModel().addPropertyChangeListener(PropertyValueModel.VALUE, this.jpaSelectionListener);
		this.treeViewer.addSelectionChangedListener(this.selectionProvider);
		this.treeViewer.addPostSelectionChangedListener(this.treePostSelectionListener);
	}

	@Override
	public void dispose() {
		this.resourceManager.dispose();
		this.disengageListeners();
		super.dispose();
	}

	private void disengageListeners() {
		this.treeViewer.removePostSelectionChangedListener(this.treePostSelectionListener);
		this.treeViewer.removeSelectionChangedListener(this.selectionProvider);
		this.getEditorJpaSelectionModel().removePropertyChangeListener(PropertyValueModel.VALUE, this.jpaSelectionListener);
		this.getEditorJpaFileModel().removePropertyChangeListener(PropertyValueModel.VALUE, this.jpaFileListener);
	}


	// ********** context menu **********

	/**
	 * A dynamic menu manager is required to support action contributions.
	 */
	private void initializeContextMenu() {
		MenuManager menuManager = new MenuManager();
		menuManager.setRemoveAllWhenShown(true);
		menuManager.addMenuListener(new MenuListener());  // no need to remove listener(?)
		Tree tree = this.treeViewer.getTree();
		tree.setMenu(menuManager.createContextMenu(tree));
		this.structureView.getSite().registerContextMenu(menuManager, this.treeViewer);
	}

	/* CU private */ class MenuListener
		implements IMenuListener
	{
		public void menuAboutToShow(IMenuManager menuManager) {
			// Delegates to the action group using the viewer's selection as the action context.
			menuManager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
		}
		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}


	// ********** editor JPA file **********

	private JpaFile getEditorJpaFile() {
		return this.getEditorJpaFileModel().getValue();
	}

	private PropertyValueModel<JpaFile> getEditorJpaFileModel() {
		return this.editorManager.getJpaFileModel();
	}

	/* CU private */ class JpaFileListener
			extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			JpaStructurePage.this.setTreeViewerInput((JpaFile) event.getNewValue());
		}
	}


	// ********** tree input **********

	/* CU private */ void setTreeViewerInput(JpaFile jpaFile) {
		this.execute(new SetTreeViewerInputRunnable(jpaFile));
	}

	/* CU private */ class SetTreeViewerInputRunnable
			implements Runnable
	{
		private final JpaFile jpaFile;

		SetTreeViewerInputRunnable(JpaFile jpaFile) {
			super();
			this.jpaFile = jpaFile;
		}

		public void run() {
			JpaStructurePage.this.setTreeViewerInput_(this.jpaFile);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.jpaFile);
		}
	}

	/**
	 * Pre-condition: executing on the UI thread.
	 */
	/* CU private */ void setTreeViewerInput_(JpaFile jpaFile) {
		// not sure why TreeViewer.setInput(...) requires a dispose check,
		// while TreeViewer.setSelection(...) does not...
		if (this.treeViewer.getControl().isDisposed()) {
			return;
		}

		// clear the tree before we change the providers
		this.treeViewer.setInput(null);
		this.setTreeViewerJpaFile(jpaFile);
	}

	private void setTreeViewerJpaFile(JpaFile jpaFile) {
		if (jpaFile == null || jpaFile.getResourceModel().getResourceType() == null) {
			this.setTreeViewerMessage(JptJpaUiMessages.JpaStructureView_structureNotAvailable);
		} 
		else if (! jpaFile.getJpaProject().getJpaPlatform().supportsResourceType(jpaFile.getResourceModel().getResourceType())) {
			this.setTreeViewerMessage(this.buildMissingStructureProviderMessage(jpaFile));
		} 
		else {
			this.setTreeViewerJpaFile(jpaFile, this.getFactoryProvider(jpaFile));
			this.setTreeViewerJpaSelection_(this.getEditorJpaSelection());
		}
	}

	private ItemTreeStateProviderFactoryProvider getFactoryProvider(JpaFile jpaFile) {
		JpaPlatformUi ui = this.getPlatformUi(jpaFile);
		return (ui != null) ? ui.getStructureViewFactoryProvider(jpaFile) : NullItemTreeStateProviderFactoryProvider.instance();
	}

	private JpaPlatformUi getPlatformUi(JpaFile jpaFile) {
		return (JpaPlatformUi) jpaFile.getJpaProject().getJpaPlatform().getAdapter(JpaPlatformUi.class);
	}

	private String buildMissingStructureProviderMessage(JpaFile jpaFile) {
		return NLS.bind(JptJpaUiMessages.JpaStructureView_structureProviderNotAvailable, jpaFile.getResourceModel().getResourceType());
	}

	private void setTreeViewerJpaFile(JpaFile jpaFile, ItemTreeStateProviderFactoryProvider factoryProvider) {
		TreeStateProvider provider = this.buildStateProvider(factoryProvider);
		this.treeViewer.setContentProvider(provider);
		this.treeViewer.setLabelProvider(provider);
		this.treeViewer.setInput(jpaFile);
	}

	private TreeStateProvider buildStateProvider(ItemTreeStateProviderFactoryProvider factoryProvider) {
		return new ItemTreeStateProviderManager(
				factoryProvider.getItemContentProviderFactory(),
				factoryProvider.getItemLabelProviderFactory(),
				this.resourceManager
			);
	}

	private void setTreeViewerMessage(String message) {
		this.treeViewer.setContentProvider(SimpleMessageTreeContent.contentProvider());
		this.treeViewer.setLabelProvider(SimpleMessageTreeContent.labelProvider());
		this.treeViewer.setInput(new SimpleMessageTreeContent(message));
	}


	// ********** editor JPA selection **********

	private JpaStructureNode getEditorJpaSelection() {
		return this.getEditorJpaSelectionModel().getValue();
	}

	private void setEditorJpaSelection(JpaStructureNode selection) {
		this.getEditorJpaSelectionModel().setValue(selection);
	}

	private ModifiablePropertyValueModel<JpaStructureNode> getEditorJpaSelectionModel() {
		return this.editorManager.getJpaSelectionModel();
	}

	/* CU private */ class JpaSelectionListener
			extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			JpaStructurePage.this.setTreeViewerJpaSelection((JpaStructureNode) event.getNewValue());
		}
	}


	// ********** tree selection **********

	/**
	 * If the new JPA selection is different
	 * from the tree's current JPA selection, modify the tree's selection.
	 */
	/* CU private */ void setTreeViewerJpaSelection(JpaStructureNode selection) {
		this.execute(new SetTreeViewerSelectionRunnable(selection));
	}

	/* CU private */ class SetTreeViewerSelectionRunnable
			implements Runnable
	{
		private final JpaStructureNode selection;

		SetTreeViewerSelectionRunnable(JpaStructureNode selection) {
			super();
			this.selection = selection;
		}

		public void run() {
			JpaStructurePage.this.setTreeViewerJpaSelection_(this.selection);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.selection);
		}
	}

	/**
	 * Pre-condition: executing on the UI thread.
	 */
	/* CU private */ void setTreeViewerJpaSelection_(JpaStructureNode selection) {
		if (selection != this.getTreeViewerJpaSelection()) {
			this.setTreeViewerSelection((selection == null) ? null : new StructuredSelection(selection));
		}
	}

	/* CU private */ void setTreeViewerSelection(ISelection selection) {
		if (this.treeViewer != null) {
			this.treeViewer.setSelection(selection, true);  // true => reveal
		}
	}

	private JpaStructureNode getTreeViewerJpaSelection() {
		return this.convertToJpaSelection_(this.getTreeViewerSelection());
	}

	/**
	 * Return a tree selection that is either empty or contains
	 * {@link JpaStructureNode JPA structure nodes}.
	 */
	/* CU private */ IStructuredSelection getTreeViewerSelection() {
		return (this.treeViewer == null) ?
				TreeSelection.EMPTY :
				this.filterSelection((ITreeSelection) this.treeViewer.getSelection());
	}

	/* CU private */ IStructuredSelection filterSelection(IStructuredSelection selection) {
		// the selection could hold a message
		return ((selection.size() > 0) && (selection.getFirstElement() instanceof JpaStructureNode)) ?
				selection :
				TreeSelection.EMPTY;
	}

	private JpaStructureNode convertToJpaSelection(IStructuredSelection selection) {
		return this.convertToJpaSelection_(this.filterSelection(selection));
	}

	private JpaStructureNode convertToJpaSelection_(IStructuredSelection filteredSelection) {
		return (filteredSelection.size() != 1) ? null : (JpaStructureNode) filteredSelection.getFirstElement();
	}


	// ********** tree post selection **********

	/**
	 * "Post" listeners are notified with a bit of a delay when the selection
	 * is modified via the keyboard (as opposed to the mouse).
	 */
	/* CU private */ class TreePostSelectionChangedListener
		extends SelectionChangedAdapter
	{
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			JpaStructurePage.this.treePostSelectionChanged(event.getSelection());
		}
	}

	/**
	 * <ul>
	 * <li>Forward the new selection to the editor manager
	 *     (and on to other JPA views).
	 * <li>Update the status bar.
	 * </ul>
	 */
	/* CU private */ void treePostSelectionChanged(ISelection selection) {
		this.setEditorJpaSelection(this.convertToJpaSelection(selection));
		this.updateStatusLine(selection);
	}

	private JpaStructureNode convertToJpaSelection(ISelection selection) {
		return (selection instanceof IStructuredSelection) ?
				this.convertToJpaSelection((IStructuredSelection) selection) :
				null;
	}


	// ********** status line **********

	private void updateStatusLine(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			this.updateStatusLine((IStructuredSelection) selection);
		} else {
			this.getStatusLineManager().setMessage(""); //$NON-NLS-1$
		}
	}

	private void updateStatusLine(IStructuredSelection selection) {
		if (selection.isEmpty()) {
			this.getStatusLineManager().setMessage(""); //$NON-NLS-1$
		} else {
			if (selection.size() > 1) {
				Integer size = Integer.valueOf(selection.size());
				String msg = NLS.bind(JptJpaUiMessages.JpaStructureView_numItemsSelected, size);
				this.getStatusLineManager().setMessage(msg);
			} else {
				Object first = selection.getFirstElement();
				ILabelProvider labelProvider = (ILabelProvider) this.treeViewer.getLabelProvider();
				Image image = labelProvider.getImage(first);
				String msg = labelProvider.getText(first);
				if (first instanceof JpaStructureNode) {
					msg = ((IDescriptionProvider) labelProvider).getDescription(first);
				}
				this.getStatusLineManager().setMessage(image, msg);
			}
		}
	}

	private IStatusLineManager getStatusLineManager() {
		return this.getSite().getActionBars().getStatusLineManager();
	}


	// ********** selection provider **********

	/**
	 * Selection provider - adapt the structure page's
	 * {@link #treeViewer tree viewer} for the page book view.
	 * The selection is {@link JpaStructurePage#filterSelection(IStructuredSelection) filtered}.
	 */
	private class SelectionProvider
		extends AbstractSelectionProvider
		implements ISelectionChangedListener
	{
		SelectionProvider() {
			super(JptJpaUiPlugin.instance().getExceptionHandler());
		}

		/**
		 * This method is called by the page site.
		 */
		public ISelection getSelection() {
			return JpaStructurePage.this.getTreeViewerSelection();
		}

		/**
		 * This method is called by the page site.
		 */
		public void setSelection(ISelection selection) {
			JpaStructurePage.this.setTreeViewerSelection(selection);
		}

		/**
		 * This method is called by the tree viewer.
		 */
		public void selectionChanged(SelectionChangedEvent event) {
			// convert the tree view event into a selection provider event
			this.fireSelectionChanged(this.filterSelection(event.getSelection()));
		}

		private ISelection filterSelection(ISelection selection) {
			return (selection instanceof IStructuredSelection) ?
					JpaStructurePage.this.filterSelection((IStructuredSelection) selection) :
					selection;  // shouldn't happen? since the event is coming from the tree viewer
		}
	}


	// ********** misc **********

	@Override
	public Control getControl() {
		return this.control;
	}

	@Override
	public void setFocus() {
		this.control.setFocus();
	}

	private void execute(Runnable runnable) {
		DisplayTools.execute(this.getSite().getShell().getDisplay(), runnable);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.getEditorJpaFile());
	}
}
