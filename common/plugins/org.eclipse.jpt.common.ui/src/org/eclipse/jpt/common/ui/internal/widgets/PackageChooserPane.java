/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.ControlContentAssistHelper;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.JavaPackageCompletionProcessor;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.common.ui.internal.plugin.JptCommonUiPlugin;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
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

	private PropertyChangeListener subjectChangeListener;

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

	@Override
	protected void initialize() {
		super.initialize();

		// TODO bug 156185 - when this is fixed there should be api for this
		this.javaPackageCompletionProcessor = new JavaPackageCompletionProcessor(
			new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_ROOT)
		);
		this.subjectChangeListener = this.buildSubjectChangeListener();
		this.getSubjectHolder().addPropertyChangeListener(PropertyValueModel.VALUE, this.subjectChangeListener);
		this.packageChooserSubjectChanged(getSubject());
	}

	private PropertyChangeListener buildSubjectChangeListener() {
		return new SWTPropertyChangeListenerWrapper(this.buildSubjectChangeListener_());
	}

	private PropertyChangeListener buildSubjectChangeListener_() {
		return new PropertyChangeListener() {
			@SuppressWarnings("unchecked")
			public void propertyChanged(PropertyChangeEvent e) {
				PackageChooserPane.this.packageChooserSubjectChanged((T) e.getNewValue());
			}
		};
	}

	protected void packageChooserSubjectChanged(T newSubject) {
		IPackageFragmentRoot root = null;
		if (newSubject != null) {
			root = getPackageFragmentRoot();
		}
		this.javaPackageCompletionProcessor.setPackageFragmentRoot(root);
	}

	@Override
	protected final Runnable buildBrowseAction() {
		return new Runnable() {
			public void run() {
				promptPackage();
			}
		};
	}

	@Override
	protected Control addMainControl(Composite container) {
		Text text = addText(container, buildTextHolder(), getHelpId());

		Image image = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL).getImage();
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = image.getBounds().width;
		text.setLayoutData(data);

		ControlContentAssistHelper.createTextContentAssistant(
			text,
			javaPackageCompletionProcessor
		);

		return text;
	}

	/**
	 * Creates the value holder of the subject's property.
	 *
	 * @return The holder of the package name
	 */
	protected abstract ModifiablePropertyValueModel<String> buildTextHolder();

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
			JptCommonUiPlugin.instance().logError(e);
			return null;
		}

		selectionDialog.setTitle(JptCommonUiMessages.PackageChooserPane_dialogTitle);
		selectionDialog.setMessage(JptCommonUiMessages.PackageChooserPane_dialogMessage);

		IPackageFragment pack = getPackageFragment();

		if (pack != null) {
			selectionDialog.setInitialSelections(new Object[] { pack });
		}

		if (selectionDialog.open() == Window.OK) {
			return (IPackageFragment) selectionDialog.getResult()[0];
		}

		return null;
	}

	protected abstract IJavaProject getJavaProject();

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
	protected void promptPackage() {
		IPackageFragment packageFragment = choosePackage();

		if (packageFragment != null) {
			String packageName = packageFragment.getElementName();
			this.setPackageName(packageName);
		}
	}
	
	protected abstract void setPackageName(String packageName);

	private IPackageFragment getPackageFragment() {
		String packageName = getPackageName();

		if (packageName == null) {
			return null;
		}

		return getPackageFragmentRoot().getPackageFragment(packageName);
	}

	protected IPackageFragmentRoot getPackageFragmentRoot() {
		try {
			return this.getJavaProject().getPackageFragmentRoots()[0];
		} catch (JavaModelException ex) {
			JptCommonUiPlugin.instance().logError(ex);
			return null;
		}
	}

	@Override
	public void dispose() {
		this.getSubjectHolder().removePropertyChangeListener(PropertyValueModel.VALUE, this.subjectChangeListener);
		super.dispose();
	}
}
