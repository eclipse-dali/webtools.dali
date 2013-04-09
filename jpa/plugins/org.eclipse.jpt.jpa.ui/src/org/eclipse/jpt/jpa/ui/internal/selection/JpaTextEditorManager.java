/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.selection;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.jface.viewers.IPostSelectionProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jpt.common.core.utility.TextRange;
import org.eclipse.jpt.common.ui.internal.PropertyAdapter;
import org.eclipse.jpt.common.ui.internal.jface.SelectionChangedAdapter;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.jpt.common.utility.internal.model.value.DoublePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.internal.transformer.AbstractTransformer;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeAdapter;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.JpaFile;
import org.eclipse.jpt.jpa.core.JpaStructureNode;
import org.eclipse.jpt.jpa.ui.JpaFileModel;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.selection.JpaEditorManager;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPropertyListener;
import org.eclipse.ui.texteditor.ITextEditor;

/**
 * JPA editor manager for a {@link ITextEditor text editor}.
 * <p>
 * If the JPA selection model changes, set the text editor's selection
 * accordingly. If the {@link #textEditor text editor}'s selection changes,
 * forward the corresponding JPA selection to the {@link #jpaSelectionModel
 * JPA selection model}.
 */
class JpaTextEditorManager
	implements JpaEditorManager
{
	/**
	 * The manager's text editor
	 */
	private final ITextEditor textEditor;

	/**
	 * Listen for the {@link #textEditor text editor}'s input to change.
	 * This can happen if the text editor is re-usable.
	 */
	private final IPropertyListener textEditorInputListener = new TextEditorInputListener();

	/**
	 * Listen for the {@link #textEditor text editor}'s selection to change.
	 */
	private final ISelectionChangedListener textEditorSelectionListener = new TextEditorSelectionListener();

	private final ModifiablePropertyValueModel<IFile> fileModel = new SimplePropertyValueModel<IFile>();

	/**
	 * We use the JPA file to calculate the JPA selection.
	 * We update the JPA file model's file model whenever the text editor's
	 * file changes.
	 */
	private final PropertyValueModel<JpaFile> jpaFileModel;

	/**
	 * Listen for the model or ourselves (via the file model) to change the
	 * JPA file.
	 */
	private final PropertyChangeListener jpaFileListener = new JpaFileListener();

	/**
	 * We update the JPA selection model whenever the text editor's selection
	 * changes.
	 */
	private final ModifiablePropertyValueModel<JpaStructureNode> jpaSelectionModel = new SimplePropertyValueModel<JpaStructureNode>();

	/**
	 * Listen for other views to change the JPA selection.
	 */
	private final PropertyChangeListener jpaSelectionListener = new JpaSelectionListener();


	// ********** constructor **********

	/* CU private */ JpaTextEditorManager(ITextEditor textEditor) {
		super();
		this.textEditor = textEditor;
		this.textEditor.addPropertyListener(this.textEditorInputListener);
		IPostSelectionProvider selProvider = this.getTextEditorSelectionProvider();
		if (selProvider != null) {
			selProvider.addPostSelectionChangedListener(this.textEditorSelectionListener);
		}

		this.jpaFileModel = this.buildJpaFileModel();
		this.jpaFileModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.jpaFileListener);

		this.jpaSelectionModel.addPropertyChangeListener(PropertyValueModel.VALUE, this.jpaSelectionListener);

		// this will trigger a new JPA file
		// which will trigger us to set the JPA selection
		this.setFileModel();
	}

	/**
	 * The <em>public</em> JPA selection model will not allow clients to set the
	 * JPA selection to <code>null</code>. Only the editor manager can set the
	 * selection to <code>null</code>. A client may set the JPA selection to
	 * <code>null</code> when it does not have appropriate input. It is OK
	 * for the <em>client's</em> JPA selection be <code>null</code>; but the
	 * editor manager's JPA selection is the <em>master</em> JPA selection for
	 * the workbench page/window and drives the JPA selection for potentially
	 * multiple clients. As a result, clients can only modify the editor
	 * manager's JPA selection to be some non-<code>null</code> value.
	 */

	// ********** JPA file **********

	public PropertyValueModel<JpaFile> getJpaFileModel() {
		return this.jpaFileModel;
	}

	private PropertyValueModel<JpaFile> buildJpaFileModel() {
		return new DoublePropertyValueModel<JpaFile>(this.buildJpaFileModelModel());
	}

	private PropertyValueModel<PropertyValueModel<JpaFile>> buildJpaFileModelModel() {
		return new TransformationPropertyValueModel<IFile, PropertyValueModel<JpaFile>>(this.fileModel, JPA_FILE_MODEL_TRANSFORMER);
	}

	private static final Transformer<IFile, PropertyValueModel<JpaFile>> JPA_FILE_MODEL_TRANSFORMER = new JpaFileModelTransformer();

	/* CU private */ static class JpaFileModelTransformer
		extends AbstractTransformer<IFile, PropertyValueModel<JpaFile>>
	{
		@Override
		protected PropertyValueModel<JpaFile> transform_(IFile file) {
			return (JpaFileModel) file.getAdapter(JpaFileModel.class);
		}
	}

	/* CU private */ class JpaFileListener
			extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			JpaTextEditorManager.this.jpaFileChanged();
		}
	}

	/**
	 * Dispatch to the UI thread.
	 */
	/* CU private */ void jpaFileChanged() {
		this.execute(new JpaFileChangedRunnable());
	}

	/* CU private */ class JpaFileChangedRunnable
			implements Runnable
	{
		public void run() {
			JpaTextEditorManager.this.jpaFileChanged_();
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this);
		}
	}

	/**
	 * Pre-condition: executing on the UI thread.
	 */
	/* CU private */ void jpaFileChanged_() {
		this.jpaSelectionModel.setValue(this.getTextEditorJpaSelection());
	}


	// ********** JPA selection **********

	public ModifiablePropertyValueModel<JpaStructureNode> getJpaSelectionModel() {
		return this.jpaSelectionModel;
	}

	/* CU private */ class JpaSelectionListener
			extends PropertyChangeAdapter
	{
		@Override
		public void propertyChanged(PropertyChangeEvent event) {
			JpaTextEditorManager.this.setTextEditorJpaSelection((JpaStructureNode) event.getNewValue());
		}
	}

	/* CU private */ void setJpaSelection(ISelection selection) {
		this.jpaSelectionModel.setValue(this.getTextEditorJpaSelection(selection));
	}


	// ********** text editor selection **********

	/* CU private */ class TextEditorSelectionListener
			extends SelectionChangedAdapter
	{
		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			//If the focus is in the JPA Details view we do not want to handle
			//TextEditor selection events. When in the Details View we are not
			//listening for java change events so our cached text ranges are not updated.
			//We do not want the selection to change while editing in the details view
			//or the details view can be changed out from under us.
			//AbstractJavaPersistentType.getStructureNode(int) will cause a
			//synchronizeWithJavaSource() to be run, and we don't want that to happen
			//when we have focus.
			if (JptJpaUiPlugin.instance().getFocusIsNonDali()) {
				JpaTextEditorManager.this.setJpaSelection(event.getSelection());
			}
		}
	}

	/**
	 * Dispatch to the UI thread.
	 */
	/* CU private */ void setTextEditorJpaSelection(JpaStructureNode selection) {
		this.execute(new SetTextEditorSelectionRunnable(selection));
	}

	/* CU private */ class SetTextEditorSelectionRunnable
			implements Runnable
	{
		private final JpaStructureNode selection;

		SetTextEditorSelectionRunnable(JpaStructureNode selection) {
			super();
			this.selection = selection;
		}

		public void run() {
			JpaTextEditorManager.this.setTextEditorJpaSelection_(this.selection);
		}

		@Override
		public String toString() {
			return ObjectTools.toString(this, this.selection);
		}
	}

	/**
	 * Pre-condition: executing on the UI thread.
	 * If the new JPA selection is not <code>null</code> and it is different
	 * from the text editor's current JPA selection, modify the text editor's
	 * selection.
	 */
	/* CU private */ void setTextEditorJpaSelection_(JpaStructureNode selection) {
		if ((selection != null) && (selection != this.getTextEditorJpaSelection())) {
			this.setTextEditorSelection(selection.getSelectionTextRange());
		}
	}

	private void setTextEditorSelection(TextRange textRange) {
		if (textRange != null) {
			// no need for a disposed check...
			this.textEditor.selectAndReveal(textRange.getOffset(), textRange.getLength());
		}
	}

	/**
	 * Return the JPA selection corresponding to the text editor's current
	 * selection.
	 * Pre-condition: executing on the UI thread.
	 */
	private JpaStructureNode getTextEditorJpaSelection() {
		// the selection provider can be null if the text editor was disposed
		// before we get a chance to [asynchronously] handle the event
		// (or if the text editor does not have a *post* selection provider)
		IPostSelectionProvider selProvider = this.getTextEditorSelectionProvider();
		return (selProvider == null) ? null : this.getTextEditorJpaSelection(selProvider.getSelection());
	}

	private JpaStructureNode getTextEditorJpaSelection(ISelection selection) {
		return (selection instanceof ITextSelection) ? this.getTextEditorJpaSelection((ITextSelection) selection) : null;
	}

	private JpaStructureNode getTextEditorJpaSelection(ITextSelection selection) {
		JpaFile jpaFile = this.jpaFileModel.getValue();
		return (jpaFile == null) ? null : jpaFile.getStructureNode(selection.getOffset());
	}


	// ********** text editor input **********

	/* CU private */ class TextEditorInputListener
			extends PropertyAdapter
	{
		@Override
		public void propertyChanged(Object source, int propertyID) {
			if (propertyID == IEditorPart.PROP_INPUT) {
				JpaTextEditorManager.this.setFileModel();
			}
		}
	}


	// ********** misc **********

	public IEditorPart getEditor() {
		return this.textEditor;
	}

	/* CU private */ void setFileModel() {
		this.fileModel.setValue(this.getTextEditorFile());
	}

	private IFile getTextEditorFile() {
		IEditorInput input = this.textEditor.getEditorInput();
		return (input instanceof IFileEditorInput) ? ((IFileEditorInput) input).getFile() : null;
	}

	private IPostSelectionProvider getTextEditorSelectionProvider() {
		ISelectionProvider selProvider = this.textEditor.getSelectionProvider();
		return (selProvider instanceof IPostSelectionProvider) ? (IPostSelectionProvider) selProvider : null;
	}

	private void execute(Runnable runnable) {
		SWTUtil.execute(this.textEditor.getSite().getShell().getDisplay(), runnable);
	}

	public void dispose() {
		this.jpaFileModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.jpaFileListener);
		this.jpaSelectionModel.removePropertyChangeListener(PropertyValueModel.VALUE, this.jpaSelectionListener);
		IPostSelectionProvider selProvider = this.getTextEditorSelectionProvider();
		if (selProvider != null) {
			selProvider.removePostSelectionChangedListener(this.textEditorSelectionListener);
		}
		this.textEditor.removePropertyListener(this.textEditorInputListener);
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.textEditor);
	}
}
