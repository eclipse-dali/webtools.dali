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

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jpt.core.internal.IJpaStructureNode;
import org.eclipse.jpt.ui.internal.jface.DelegatingTreeContentAndLabelProvider;
import org.eclipse.jpt.ui.internal.selection.IJpaSelection;
import org.eclipse.jpt.ui.internal.selection.JpaSelection;
import org.eclipse.jpt.ui.internal.structure.IJpaStructureProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.part.Page;

public class JpaStructurePage extends Page
	implements ISelectionProvider, ISelectionChangedListener
{
	private IJpaStructureProvider structureProvider;
	
	private ListenerList selectionChangedListeners;
	
	private Composite control;
	
	private TreeViewer viewer;
	
	private JpaStructureView jpaStructureView;
	
	public JpaStructurePage(JpaStructureView jpaStructureView, IJpaStructureProvider structureProvider) {
		this.jpaStructureView = jpaStructureView;
		this.structureProvider = structureProvider;
		this.selectionChangedListeners = new ListenerList();
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
				structureProvider.treeItemContentProviderFactory(),
				structureProvider.itemLabelProviderFactory());
		viewer.setContentProvider(contentAndLabelProvider);
		// TODO Use problem decorator
		viewer.setLabelProvider(contentAndLabelProvider);
		viewer.setInput(structureProvider.getInput());
		viewer.addSelectionChangedListener(this);
		initContextMenu();
	}
	
	@Override
	public void dispose() {
		viewer.removeSelectionChangedListener(this);
		structureProvider.dispose();
		super.dispose();
	}
	
	//TODO this isn't really working.  our jpa actions appear, but along with a bunch of other actions!!
    protected void initContextMenu() {
        // Create dynamic menu mgr.  Dynamic is currently required to
        // support action contributions.
        MenuManager mgr = new MenuManager();
        mgr.setRemoveAllWhenShown(true);
        mgr.addMenuListener(new IMenuListener() {
            public void menuAboutToShow(IMenuManager mgr) {
                fillContextMenu(mgr);
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
	
	
	
	void select(IJpaSelection selection) {
		if (selection.isEmpty()) {
			viewer.setSelection(StructuredSelection.EMPTY);
		}
		else {
			viewer.setSelection(new StructuredSelection(selection.getSelectedNode()), true);
		}
	}
	
	
	// **************** ISelectionProvider impl ********************************
	
	public void addSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.add(listener);
	}
	
	public void removeSelectionChangedListener(ISelectionChangedListener listener) {
		selectionChangedListeners.remove(listener);
	}
	
	public IJpaSelection getSelection() {
		ITreeSelection viewerSelection = (ITreeSelection) viewer.getSelection();
		
		if (viewerSelection.isEmpty() || viewerSelection.size() > 1) {
			return IJpaSelection.NULL_SELECTION;
		}
		return new JpaSelection((IJpaStructureNode) viewerSelection.getFirstElement());
	}
	
	public void setSelection(ISelection selection) {
		if (viewer != null) {
			viewer.setSelection(selection);
		}
	}
	
	
	
	// **************** ISelectionChangedListener impl *************************
	
	public void selectionChanged(SelectionChangedEvent event) {
		fireSelectionChanged(event.getSelection());
	}
	
	protected void fireSelectionChanged(ISelection selection) {
		// create an event
		final SelectionChangedEvent event = 
				new SelectionChangedEvent(this, selection);
		
		// fire the event
		Object[] listeners = selectionChangedListeners.getListeners();
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
}