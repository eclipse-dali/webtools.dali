/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.views.structure;

import java.util.Iterator;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jpt.core.JpaFile;
import org.eclipse.jpt.core.JpaModel;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JpaStructureNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.selection.DefaultJpaSelection;
import org.eclipse.jpt.ui.internal.selection.JpaSelection;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.jface.DelegatingContentAndLabelProvider;
import org.eclipse.jpt.ui.structure.JpaStructureProvider;
import org.eclipse.jpt.utility.model.event.CollectionChangeEvent;
import org.eclipse.jpt.utility.model.listener.CollectionChangeListener;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.Page;

public class JpaStructurePage 
	extends Page
	implements ISelectionProvider
{
	private final JpaStructureView jpaStructureView;
	
	private JpaFile jpaFile;
	
	private final IFile file;
	
	private JpaProject jpaProject;
	
	private final JpaStructureProvider structureProvider;
	
	private Composite control;
	
	private DelegatingContentAndLabelProvider contentAndLabelProvider;
	
	private TreeViewer viewer;
	
	private final ListenerList selectionChangedListenerList;
	
	private final ISelectionChangedListener treeSelectionListener;
	
	private final ISelectionChangedListener treePostSelectionListener;
	
	private final CollectionChangeListener projectsListener;
	
	private final CollectionChangeListener jpaFilesListener;
	
	public JpaStructurePage(
			JpaStructureView jpaStructureView, 
			JpaFile jpaFile, 
			JpaStructureProvider structureProvider) {
		this.jpaStructureView = jpaStructureView;
		this.jpaFile = jpaFile;
		this.jpaProject = jpaFile.getJpaProject();
		this.file = jpaFile.getFile();
		this.structureProvider = structureProvider;
		this.selectionChangedListenerList = new ListenerList();
		this.treeSelectionListener = new TreeSelectionChangedListener();
		this.treePostSelectionListener = new TreePostSelectionChangedListener();
		this.projectsListener = buildProjectsListener();
		this.jpaFilesListener = buildJpaFilesListener();
	}
	
	private CollectionChangeListener buildProjectsListener() {
		return new CollectionChangeListener(){
		
			public void itemsRemoved(CollectionChangeEvent event) {
				JpaStructurePage.this.projectsRemoved(event);
			}
		
			public void itemsAdded(CollectionChangeEvent event) {
				JpaStructurePage.this.projectsAdded(event);
			}
		
			public void collectionCleared(CollectionChangeEvent event) {
				JpaStructurePage.this.projectsCleared(event);
			}
		
			public void collectionChanged(CollectionChangeEvent event) {
				JpaStructurePage.this.projectsChanged(event);
			}
		};
	}

	private void projectsRemoved(CollectionChangeEvent event) {
		for (Iterator<?> i = event.items(); i.hasNext();) {
			JpaProject project = (JpaProject) i.next();
			if (project.getProject() == JpaStructurePage.this.file.getProject()) {
				setJpaProject(null);
				break;
			}
		}
	}

	private void projectsAdded(CollectionChangeEvent event) {
		for (Iterator<?> i = event.items(); i.hasNext();) {
			JpaProject jpaProject = (JpaProject) i.next();
			if (jpaProject.getProject() == JpaStructurePage.this.file.getProject()) {
				setJpaProject(jpaProject);
				break;
			}
		}
	}

	private void projectsCleared(CollectionChangeEvent event) {
		setJpaProject(null);
	}
	
	private void projectsChanged(CollectionChangeEvent event) {
		setJpaProject(JptCorePlugin.getJpaProject(this.file.getProject()));
	}
	
	private CollectionChangeListener buildJpaFilesListener() {
		return new CollectionChangeListener(){
		
			public void itemsRemoved(CollectionChangeEvent event) {
				JpaStructurePage.this.jpaFilesRemoved(event);
			}
		
			public void itemsAdded(CollectionChangeEvent event) {
				JpaStructurePage.this.jpaFilesAdded(event);
			}
		
			public void collectionCleared(CollectionChangeEvent event) {
				JpaStructurePage.this.jpaFilesCleared(event);
			}
		
			public void collectionChanged(CollectionChangeEvent event) {
				JpaStructurePage.this.jpaFilesChanged(event);
			}
		};
	}
	
	private void jpaFilesRemoved(CollectionChangeEvent event) {
		for (Iterator<?> i = event.items(); i.hasNext();) {
			JpaFile jpaFile = (JpaFile) i.next();
			if (jpaFile == JpaStructurePage.this.jpaFile) {
				setJpaFile(null);
				break;
			}
		}
	}

	private void jpaFilesAdded(CollectionChangeEvent event) {
		for (Iterator<?> i = event.items(); i.hasNext();) {
			JpaFile jpaFile = (JpaFile) i.next();
			if (jpaFile.getFile() == JpaStructurePage.this.file) {
				setJpaFile(jpaFile);
				break;
			}
		}
	}

	private void jpaFilesCleared(CollectionChangeEvent event) {
		setJpaFile(null);
	}

	private void jpaFilesChanged(CollectionChangeEvent event) {
		setJpaFile(this.jpaProject.getJpaFile(this.file));
	}

	private void setJpaProject(JpaProject jpaProject) {
		if (this.jpaProject == jpaProject) {
			return;
		}
		if (this.jpaProject != null) {
			this.jpaProject.removeCollectionChangeListener(JpaProject.JPA_FILES_COLLECTION, this.jpaFilesListener);
		}
		this.jpaProject = jpaProject;
		if (this.jpaProject != null) {
			this.jpaProject.addCollectionChangeListener(JpaProject.JPA_FILES_COLLECTION, this.jpaFilesListener);
			setJpaFile(this.jpaProject.getJpaFile(JpaStructurePage.this.file));
		}
		else {
			setJpaFile(null);
		}
	}
	
	private void setJpaFile(JpaFile jpaFile) {
		if (this.jpaFile == jpaFile) {
			return;
		}
		this.jpaFile = jpaFile;
		SWTUtil.asyncExec(new Runnable(){						
			public void run() {
				JpaStructurePage.this.viewer.setInput(JpaStructurePage.this.jpaFile);
			}
		});
	}
	
	@Override
	public void init(IPageSite pageSite) {
		super.init(pageSite);
		pageSite.setSelectionProvider(this);
	}
	
	@Override
	public void createControl(Composite parent) {
		control = new Composite(parent, SWT.NULL);
		control.setLayout(new FillLayout());		
		viewer = new TreeViewer(control, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setAutoExpandLevel(2);
		DelegatingTreeContentAndLabelProvider contentAndLabelProvider
			= new DelegatingTreeContentAndLabelProvider(
				structureProvider.getTreeItemContentProviderFactory(),
				structureProvider.getItemLabelProviderFactory());
		this.contentAndLabelProvider = contentAndLabelProvider;
		viewer.setContentProvider(contentAndLabelProvider);
		// TODO Use problem decorator
		viewer.setLabelProvider(contentAndLabelProvider);
		this.viewer.setInput(this.jpaFile);
		engageListeners();
		initContextMenu();
	}
	
	protected void engageListeners() {
		this.viewer.addSelectionChangedListener(this.treeSelectionListener);
		this.viewer.addPostSelectionChangedListener(this.treePostSelectionListener);
		this.jpaProject.addCollectionChangeListener(JpaProject.JPA_FILES_COLLECTION, this.jpaFilesListener);
		JptCorePlugin.getJpaModel().addCollectionChangeListener(JpaModel.JPA_PROJECTS_COLLECTION, this.projectsListener);
	}
	
	@Override
	public void dispose() {
		disengageListeners();
		super.dispose();
	}
	
	protected void disengageListeners() {
		JptCorePlugin.getJpaModel().removeCollectionChangeListener(JpaModel.JPA_PROJECTS_COLLECTION, this.projectsListener);
		if (this.jpaProject != null) {
			this.jpaProject.removeCollectionChangeListener(JpaProject.JPA_FILES_COLLECTION, this.jpaFilesListener);
		}
		this.viewer.removePostSelectionChangedListener(this.treePostSelectionListener);
		this.viewer.removeSelectionChangedListener(this.treeSelectionListener);
	}
	
    protected void initContextMenu() {
        // Create dynamic menu mgr.  Dynamic is currently required to
        // support action contributions.
        MenuManager mgr = new MenuManager();
        mgr.setRemoveAllWhenShown(true);
        mgr.addMenuListener(new IMenuListener() {
            public void menuAboutToShow(IMenuManager menuManager) {
                JpaStructurePage.this.fillContextMenu(menuManager);
            }
        });
        Menu menu = mgr.createContextMenu(viewer.getControl());
        viewer.getControl().setMenu(menu);
        this.jpaStructureView.getSite().registerContextMenu(mgr, viewer);
    }	
	
    /**
     * Called when the context menu is about to open.
     * Delegates to the action group using the viewer's selection as the action context.
     * @since 2.0
     */
    protected void fillContextMenu(IMenuManager manager) {
        manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
    }
	
	@Override
	public Control getControl() {
		return control;
	}
	
	@Override
	public void setFocus() {
		control.setFocus();
	}
	
	
	
	void select(JpaSelection selection) {
		if (selection.isEmpty()) {
			viewer.setSelection(StructuredSelection.EMPTY);
		}
		else {
			viewer.setSelection(new StructuredSelection(selection.getSelectedNode()), true);
		}
	}
	
	
	// **************** ISelectionProvider impl ********************************
	
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListenerList.add(listener);
	}
	
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListenerList.remove(listener);
	}
	
	public ITreeSelection getSelection() {
		return (ITreeSelection) viewer.getSelection();
	}
	
	public JpaSelection getJpaSelection() {
		ITreeSelection viewerSelection = getSelection();
		
		if (viewerSelection.isEmpty() || viewerSelection.size() > 1) {
			return JpaSelection.NULL_SELECTION;
		}
		return new DefaultJpaSelection((JpaStructureNode) viewerSelection.getFirstElement());
	}
	
	
	public void setSelection(ISelection selection) {
		if (viewer != null) {
			viewer.setSelection(selection);
		}
	}
	
	/*
	 * relays tree selection event to listeners of this page
	 */
	protected void fireSelectionChanged(ISelection selection) {
		// create an event
		final SelectionChangedEvent event = 
				new SelectionChangedEvent(this, selection);
		
		// fire the event
		Object[] listeners = selectionChangedListenerList.getListeners();
		for (int i = 0; i < listeners.length; ++i) {
			final ISelectionChangedListener l = (ISelectionChangedListener) listeners[i];
			SafeRunner.run(
					new SafeRunnable() {
						public void run() {
							l.selectionChanged(event);
						}
					});
        }
    }
	
	protected void updateStatusBar(ISelection selection) {
		IStatusLineManager statusLineManager = getSite().getActionBars().getStatusLineManager();
		if (! (selection instanceof IStructuredSelection) || selection.isEmpty()) {
			statusLineManager.setMessage("");
			return;
		}
		IStructuredSelection sselection = (IStructuredSelection) selection;
		if (sselection.size() > 1) {
			statusLineManager.setMessage(NLS.bind(JptUiMessages.JpaStructureView_numItemsSelected, sselection.size()));
		}
		else {
			Object selObj = sselection.getFirstElement();
			statusLineManager.setMessage(
				this.contentAndLabelProvider.getImage(selObj), 
				this.contentAndLabelProvider.getDescription(selObj));
		}
	}
	
	
	private class TreeSelectionChangedListener
		implements ISelectionChangedListener
	{
		public void selectionChanged(SelectionChangedEvent event) {
			JpaStructurePage.this.fireSelectionChanged(event.getSelection());
		}
	}
	
	
	private class TreePostSelectionChangedListener
		implements ISelectionChangedListener
	{
		public void selectionChanged(SelectionChangedEvent event) {
			JpaStructurePage.this.updateStatusBar(event.getSelection());
		}
	}
}