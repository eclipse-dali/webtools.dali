/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.selection;

import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.IJpaFile;
import org.eclipse.jpt.core.internal.ITextRange;
import org.eclipse.jpt.core.internal.JptCorePlugin;
import org.eclipse.jpt.ui.internal.views.AbstractJpaView;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.texteditor.ITextEditor;

public class TextEditorSelectionParticipant 
	implements ISelectionParticipant 
{
	private final ISelectionManager selectionManager;

	final ITextEditor textEditor;
	
	private final IPropertyListener editorInputListener;
	
	private final ISelectionChangedListener editorSelectionListener;
	
	private Selection currentSelection;
	
	private boolean forwardSelection = true;  // TODO this just smells wrong  ~bjv
	
	
	public TextEditorSelectionParticipant(ISelectionManager selectionManager, ITextEditor textEditor) {
		super();
		this.selectionManager = selectionManager;
		this.textEditor = textEditor;
		this.editorInputListener = new EditorInputListener();
		this.textEditor.addPropertyListener(this.editorInputListener);
		this.editorSelectionListener = new EditorSelectionListener();
		this.postSelectionProvider().addPostSelectionChangedListener(this.editorSelectionListener);
		this.currentSelection = this.calculateSelection();
	}

	// ********** ISelectionParticipant implementation **********

	public Selection getSelection() {
		return this.currentSelection;
	}
	
	public void selectionChanged(SelectionEvent evt) {
		Selection newSelection = evt.getSelection();
		
		if ((newSelection == Selection.NULL_SELECTION)
			|| newSelection.equals(this.currentSelection)) {
			return;
		}
		
		this.forwardSelection = false;
		ITextRange textRange = newSelection.getSelectedNode().selectionTextRange();
		if (textRange != null) {
			this.textEditor.selectAndReveal(textRange.getOffset(), textRange.getLength());
		}
		this.forwardSelection = true;
	}
	
	public boolean disposeOnHide() {
		return true;
	}
	
	public void dispose() {
		this.textEditor.removePropertyListener(this.editorInputListener);
		this.postSelectionProvider().removePostSelectionChangedListener(this.editorSelectionListener);
	}


	// ********** internal methods **********

	private Selection calculateSelection() {
		ISelection selection = this.textEditor.getSelectionProvider().getSelection();
		if (! (selection instanceof ITextSelection)) {
			return Selection.NULL_SELECTION;
		}

		IJpaFile persistenceFile = this.persistenceFile();
		if (persistenceFile == null) {
			return Selection.NULL_SELECTION;
		}

		IJpaContentNode selectedNode = persistenceFile.getContentNode(((ITextSelection) selection).getOffset());
		if (selectedNode == null) {
			return Selection.NULL_SELECTION;
		}

		return new Selection(selectedNode);
	}
	
	private IJpaFile persistenceFile() {
		IEditorInput input = this.textEditor.getEditorInput();
		if ( ! (input instanceof IFileEditorInput)) {
			return null;
		}
		return JptCorePlugin.getJpaFile(((IFileEditorInput) input).getFile());
	}
		
	private IPostSelectionProvider postSelectionProvider() {
		return (IPostSelectionProvider) this.textEditor.getSelectionProvider();
	}


	// ********** listener callbacks **********

	void editorInputChanged() {
		Selection newSelection = this.calculateSelection();
		if (newSelection.equals(this.currentSelection)) {
			return;
		}
		this.currentSelection = newSelection;
		if (this.forwardSelection) {
			this.selectionManager.select(newSelection);
		}
	}
	
	void editorSelectionChanged(SelectionChangedEvent event) {
		Selection newSelection = this.calculateSelection();
		if (newSelection.equals(this.currentSelection)) {
			return;
		}
		this.currentSelection = newSelection;

		// bug 188344 - won't actively change selection manager selection if 
		// a "JPA" view is the active (and presumably selecting) view
		if (this.textEditor.getEditorSite().getPage().getActivePart() instanceof AbstractJpaView) {
			return;
		}

		if (this.forwardSelection) {
			this.selectionManager.select(newSelection);
		}
	}
	

	// ********** listeners **********

	private class EditorInputListener implements IPropertyListener {
		EditorInputListener() {
			super();
		}
		public void propertyChanged(Object source, int propId) {
			if ((source == TextEditorSelectionParticipant.this.textEditor)
						&& (propId == IEditorPart.PROP_INPUT)) {
				TextEditorSelectionParticipant.this.editorInputChanged();
			}
		}
	}
	
	
	private class EditorSelectionListener implements ISelectionChangedListener {
		EditorSelectionListener() {
			super();
		}
		public void selectionChanged(SelectionChangedEvent event) {
			TextEditorSelectionParticipant.this.editorSelectionChanged(event);
		}
	}

}
