/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.selection;

import org.eclipse.core.resources.IFile;
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
	extends AbstractSelectionParticipant 
{
	private ITextEditor editor;
	
	private IPropertyListener editorInputListener;
	
	private ISelectionChangedListener editorSelectionListener;
	
	private Selection currentSelection;
	
	private boolean suppressNotification = false;
	
	
	public TextEditorSelectionParticipant(ISelectionManager theSelectionManager, ITextEditor theEditor) {
		super(theSelectionManager);
		editor = theEditor;
		editorInputListener = new EditorInputListener();
		editor.addPropertyListener(editorInputListener);
		editorSelectionListener = new EditorSelectionListener();
		((IPostSelectionProvider) editor.getSelectionProvider()).addPostSelectionChangedListener(editorSelectionListener);
		currentSelection = calculateSelection();
	}
	
	private void editorInputChanged() {
		Selection newSelection = calculateSelection();
		
		if (! newSelection.equals(currentSelection)) {
			currentSelection = newSelection;
			
			if (! suppressNotification) {
				selectionManager.select(newSelection);
			}
		}
	}
	
	private void editorSelectionChanged(SelectionChangedEvent event) {
		Selection newSelection = calculateSelection();
		
		if (! newSelection.equals(currentSelection)) {
			currentSelection = newSelection;
			
			// bug 188344 - won't actively change selection manager selection if 
			// a "JPA" view is the active (and presumably selecting) view
			if (editor.getEditorSite().getPage().getActivePart() instanceof AbstractJpaView) {
				return;
			}
			
			if (! suppressNotification) {
				selectionManager.select(newSelection);
			}
		}
	}
	
	private Selection calculateSelection() {
		if (editor == null) {
			return Selection.NULL_SELECTION;
		}
		
		ISelection selection = editor.getSelectionProvider().getSelection();
		IJpaFile persistenceFile = persistenceFileFor(editor.getEditorInput());
		
		if ((persistenceFile == null)
			|| (! (selection instanceof ITextSelection))) {
			return Selection.NULL_SELECTION;
		}
		
		IJpaContentNode selectedNode = persistenceFile.getContentNode(((ITextSelection) selection).getOffset());
		if (selectedNode == null) {
			return Selection.NULL_SELECTION;
		}
		return new Selection(selectedNode);
	}
	
	public Selection getSelection() {
		return currentSelection;
	}
	
	public IJpaFile persistenceFileFor(IEditorInput input) {
		IFile file = null;
		if (input instanceof IFileEditorInput) {
			IFileEditorInput fileInput = (IFileEditorInput) input;
			file = fileInput.getFile();
		}
		if (file == null) {
			return null;
		}
		return JptCorePlugin.getJpaFile(file);
	}
		
	public void selectionChanged(SelectionEvent evt) {
		Selection newSelection = evt.getSelection();
		
		if ((getSelection().equals(newSelection))
			|| (newSelection == Selection.NULL_SELECTION)) {
			return;
		}
		
		suppressNotification = true;
		IJpaContentNode selectedNode = newSelection.getSelectedNode();
		
		ITextRange textRange = selectedNode.selectionTextRange();
		if (textRange != null) {
			this.editor.selectAndReveal(textRange.getOffset(), textRange.getLength());
		}
		suppressNotification = false;
	}
	
	public boolean disposeOnHide() {
		return true;
	}
	
	public void dispose() {
		this.editor.removePropertyListener(this.editorInputListener);
		((IPostSelectionProvider) this.editor.getSelectionProvider()).removePostSelectionChangedListener(editorSelectionListener);
	}
	
	
	private class EditorInputListener
		implements IPropertyListener
	{
		public void propertyChanged(Object source, int propId) {
			if ((editor == source) && (propId == IEditorPart.PROP_INPUT)) {
				editorInputChanged();
			}
		}
	}
	
	
	private class EditorSelectionListener
		implements ISelectionChangedListener 
	{
		public void selectionChanged(SelectionChangedEvent event) {
			editorSelectionChanged(event);
		}
	}
}
