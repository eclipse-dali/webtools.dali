/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.widgets;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.ControlContentAssistHelper;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.JavaTypeCompletionProcessor;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.progress.IProgressService;

/**
 * This chooser allows the user to choose a type when browsing and it adds code
 * completion support to the text field, which is the main component.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |       !---------------------------------------------------- ------------- |
 * | Label: | I                                                | | Browse... | |
 * |        ---------------------------------------------------- ------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class ClassChooserPane<T extends Model> extends AbstractChooserPane<T>
{
	/**
	 * The code completion manager.
	 */
	private JavaTypeCompletionProcessor javaTypeCompletionProcessor;

	/**
	 * Creates a new <code>ClassChooserPane</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public ClassChooserPane(AbstractPane<? extends T> parentPane,
	                        Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>ClassChooserPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public ClassChooserPane(AbstractPane<?> parentPane,
	                        PropertyValueModel<? extends T> subjectHolder,
	                        Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected final Runnable buildBrowseAction() {
		return new Runnable() {
			public void run() {
				promptType();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected Control buildMainControl(Composite container) {

		WritablePropertyValueModel<String> textHolder = buildTextHolder();

		textHolder.addPropertyChangeListener(
			PropertyValueModel.VALUE,
			buildTextChangeListener()
		);

		Text text = buildText(container, textHolder);

		ControlContentAssistHelper.createTextContentAssistant(
			text,
			javaTypeCompletionProcessor
		);

		return text;
	}

	private PropertyChangeListener buildTextChangeListener() {
		return new SWTPropertyChangeListenerWrapper(buildTextChangeListener_());
	}

	private PropertyChangeListener buildTextChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				if (subject() != null) {
					IPackageFragmentRoot root = packageFragmentRoot();

					if (root != null) {
						javaTypeCompletionProcessor.setPackageFragment(root.getPackageFragment(""));
					}
				}
			}
		};
	}

	/**
	 * Creates the value holder of the subject's property.
	 *
	 * @return The holder of the class name
	 */
	protected abstract WritablePropertyValueModel<String> buildTextHolder();

	/**
	 * Prompts the user the Open Type dialog.
	 *
	 * @return Either the selected type or <code>null</code> if the user
	 * cancelled the dialog
	 */
	protected IType chooseType() {

		IPackageFragmentRoot root = packageFragmentRoot();

		if (root == null) {
			return null;
		}

		IJavaElement[] elements = new IJavaElement[] { root.getJavaProject() };
		IJavaSearchScope scope = SearchEngine.createJavaSearchScope(elements);
		IProgressService service = PlatformUI.getWorkbench().getProgressService();
		SelectionDialog typeSelectionDialog;

		try {
			typeSelectionDialog = JavaUI.createTypeDialog(
				shell(),
				service,
				scope,
				IJavaElementSearchConstants.CONSIDER_CLASSES,
				false,
				className() != null ? ClassTools.shortNameForClassNamed(className()) : ""
			);
		}
		catch (JavaModelException e) {
			JptUiPlugin.log(e);
			return null;
		}

		typeSelectionDialog.setTitle(JptUiMessages.ClassChooserPane_dialogTitle);
		typeSelectionDialog.setMessage(JptUiMessages.ClassChooserPane_dialogMessage);

		if (typeSelectionDialog.open() == Window.OK) {
			return (IType) typeSelectionDialog.getResult()[0];
		}

		return null;
	}

	/**
	 * Returns the class name from its subject.
	 *
	 * @return The class name or <code>null</code> if none is defined
	 */
	protected abstract String className();

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();

		// TODO bug 156185 - when this is fixed there should be api for this
		this.javaTypeCompletionProcessor = new JavaTypeCompletionProcessor(false, false);
	}

	/**
	 * Retrieves the ??
	 *
	 * @return Either the root of the package fragment or <code>null</code> if it
	 * can't be retrieved
	 */
	protected abstract IPackageFragmentRoot packageFragmentRoot();

	/**
	 * The browse button was clicked, its action invokes this action which should
	 * prompt the user to select a class and set it.
	 */
	protected abstract void promptType();
}
