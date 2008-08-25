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

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.ControlContentAssistHelper;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.JavaPackageCompletionProcessor;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * This chooser allows the user to choose a package when browsing and it adds
 * code completion support to the text field, which is the main component.
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
public abstract class PackageChooserPane<T extends Model> extends ChooserPane<T>
{
	/**
	 * The code completion manager.
	 */
	private JavaPackageCompletionProcessor javaPackageCompletionProcessor;

	/**
	 * Creates a new <code>PackageChooserPane</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public PackageChooserPane(Pane<? extends T> parentPane,
	                          Composite parent) {

		super(parentPane, parent);
	}

	/**
	 * Creates a new <code>PackageChooserPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public PackageChooserPane(Pane<?> parentPane,
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
				promptPackage();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected Control addMainControl(Composite container) {

		WritablePropertyValueModel<String> textHolder = buildTextHolder();

		textHolder.addPropertyChangeListener(
			PropertyValueModel.VALUE,
			buildTextChangeListener()
		);

		Text text = addText(container, textHolder);

		ControlContentAssistHelper.createTextContentAssistant(
			text,
			javaPackageCompletionProcessor
		);

		return text;
	}

	private PropertyChangeListener buildTextChangeListener() {
		return new SWTPropertyChangeListenerWrapper(buildTextChangeListener_());
	}

	private PropertyChangeListener buildTextChangeListener_() {
		return new PropertyChangeListener() {
			public void propertyChanged(PropertyChangeEvent e) {
				if (getSubject() != null) {
					IPackageFragmentRoot root = getPackageFragmentRoot();

					if (root != null) {
						javaPackageCompletionProcessor.setPackageFragmentRoot(root);
					}
				}
			}
		};
	}

	/**
	 * Creates the value holder of the subject's property.
	 *
	 * @return The holder of the package name
	 */
	protected abstract WritablePropertyValueModel<String> buildTextHolder();

	/**
	 * Prompts the user the Open Package dialog.
	 *
	 * @return Either the selected package or <code>null</code> if the user
	 * cancelled the dialog
	 */
	protected IPackageFragment choosePackage() {

		SelectionDialog selectionDialog;

		try {
			selectionDialog = JavaUI.createPackageDialog(
				getShell(),
				getPackageFragmentRoot()
			);
		}
		catch (JavaModelException e) {
			JptUiPlugin.log(e);
			return null;
		}

		selectionDialog.setTitle(JptUiMessages.ClassChooserPane_dialogTitle);
		selectionDialog.setMessage(JptUiMessages.ClassChooserPane_dialogMessage);

		IPackageFragment pack = getPackageFragment();

		if (pack != null) {
			selectionDialog.setInitialSelections(new Object[] { pack });
		}

		if (selectionDialog.open() == Window.OK) {
			return (IPackageFragment) selectionDialog.getResult()[0];
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initialize() {
		super.initialize();

		// TODO bug 156185 - when this is fixed there should be api for this
		this.javaPackageCompletionProcessor = new JavaPackageCompletionProcessor(
			new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_ROOT)
		);
	}

	private IPackageFragment getPackageFragment() {
		String packageName = getPackageName();

		if (packageName == null) {
			return null;
		}

		return getPackageFragmentRoot().getPackageFragment(packageName);
	}

	/**
	 * Retrieves the ??
	 *
	 * @return Either the root of the package fragment or <code>null</code> if it
	 * can't be retrieved
	 */
	protected abstract IPackageFragmentRoot getPackageFragmentRoot();

	/**
	 * Returns the package name from its subject.
	 *
	 * @return The package name or <code>null</code> if none is defined
	 */
	protected abstract String getPackageName();

	/**
	 * The browse button was clicked, its action invokes this action which should
	 * prompt the user to select a package and set it.
	 */
	protected abstract void promptPackage();
}
