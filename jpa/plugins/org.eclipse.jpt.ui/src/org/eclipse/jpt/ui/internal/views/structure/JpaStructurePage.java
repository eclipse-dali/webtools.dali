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

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ITreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.ui.internal.jface.NullLabelProvider;
import org.eclipse.jpt.ui.internal.jface.NullTreeContentProvider;
import org.eclipse.jpt.ui.internal.selection.IJpaSelection;
import org.eclipse.jpt.ui.internal.selection.JpaSelection;
import org.eclipse.jpt.ui.internal.structure.IJpaStructureProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.part.Page;

public class JpaStructurePage extends Page
{
	private IJpaStructureProvider structureProvider;
	
	private Composite control;
	
	private TreeViewer viewer;
	
	
	public JpaStructurePage(IJpaStructureProvider structureProvider) {
		this.structureProvider = structureProvider;
	}
	
	
	@Override
	public void createControl(Composite parent) {
		control = new Composite(parent, SWT.NULL);
		control.setLayout(new FillLayout());		
		viewer = new TreeViewer(control, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		viewer.setAutoExpandLevel(2);
		viewer.setContentProvider(structureProvider.buildContentProvider());
		viewer.setLabelProvider(structureProvider.buildLabelProvider());
		viewer.setInput(structureProvider.getInput());
		initContextMenu();
	}
	
	protected void initContextMenu() {
		// TODO
//        // Create dynamic menu mgr.  Dynamic is currently required to
//        // support action contributions.
//        MenuManager mgr = new MenuManager();
//        mgr.setRemoveAllWhenShown(true);
//        mgr.addMenuListener(new IMenuListener() {
//            public void menuAboutToShow(IMenuManager mgr) {
//                fillContextMenu(mgr);
//            }
//        });
//        Menu menu = mgr.createContextMenu(viewer.getControl());
//        viewer.getControl().setMenu(menu);
//        // TODO - what is the ID??
//        getSite().registerContextMenu("id", mgr, viewer);
//       	        
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
	
	IJpaSelection getSelection() {
		ITreeSelection viewerSelection = (ITreeSelection) viewer.getSelection();
		
		if (viewerSelection.isEmpty() || viewerSelection.size() > 1) {
			return IJpaSelection.NULL_SELECTION;
		}
		else {
			return new JpaSelection((IJpaContextNode) viewerSelection.getFirstElement());
		}
	}
	
	void select(IJpaSelection selection) {
		// TODO
	//			// note: checks for null and equals() selection have already been performed
	//			
	//			if (selection.equals(IJpaSelection.NULL_SELECTION)) {
	//				clearViewer();
	//				return;
	//			}
	//			
	//			IJpaSelection currentSelection = getSelection();
	//			IJpaContentNode newNode = selection.getSelectedNode();
	//			IJpaFile newFile = newNode.getJpaFile();
	//			IJpaContentNode currentNode = 
	//				(currentSelection == IJpaSelection.NULL_SELECTION) ?
	//						null : getSelection().getSelectedNode();
	//			IJpaFile currentFile = 
	//				(currentNode == null) ? 
	//						null : currentNode.getJpaFile();
	//			
	//			if (newFile.equals(currentFile)) {
	//				viewer.setSelection(new StructuredSelection(newNode), true);
	//			}
	//			else if (currentFile != null &&  newFile.getContentId().equals(currentFile.getContentId())) {
	//				viewer.setInput(newFile.getContent());
	//				viewer.setSelection(new StructuredSelection(newNode), true);
	//			}
	//			else {
	//				// new content type
	//				// replace composite and set selection of tree
	//				IJpaStructureProvider provider = getStructureProvider(newNode);
	//				
	//				if (provider == null) {
	//					clearViewer();
	//				}
	//				else {
	//					viewer.setContentProvider(provider.buildContentProvider());
	//					viewer.setLabelProvider(provider.buildLabelProvider());
	//					viewer.setInput(newFile.getContent());
	//				}
	//			}
	}
			
	private void clearViewer() {
		viewer.setContentProvider(NullTreeContentProvider.INSTANCE);
		viewer.setLabelProvider(NullLabelProvider.INSTANCE);
		viewer.setInput(null);
	}
}
