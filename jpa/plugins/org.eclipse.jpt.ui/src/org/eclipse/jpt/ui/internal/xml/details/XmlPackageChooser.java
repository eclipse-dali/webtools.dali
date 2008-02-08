/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.details;

import java.util.Collection;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.ControlContentAssistHelper;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.JavaPackageCompletionProcessor;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.core.internal.context.orm.EntityMappings;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.JptUiPlugin;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.ui.internal.xml.JptUiXmlMessages;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.SelectionDialog;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |          -------------------------------------------------- ------------- |
 * | Package: | I                                              | | Browse... | |
 * |          -------------------------------------------------- ------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @see EntityMappings
 * @see XmlEntityMappingsDetailsPage - The parent container
 *
 * @version 2.0
 * @since 2.0
 */
public class XmlPackageChooser extends AbstractFormPane<EntityMappings>
{
	private IContentAssistProcessor contentAssistProcessor;
	private Text text;

	/**
	 * Creates a new <code>XmlPackageChooser</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param parent The parent container
	 */
	public XmlPackageChooser(AbstractFormPane<? extends EntityMappings> parentPane,
	                         Composite parent) {

		super(parentPane, parent);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(EntityMappings.PACKAGE_PROPERTY);
	}

	private void browsePackage() {

		IPackageFragment packageFragment = choosePackage();

		if (packageFragment != null) {

			setPopulating(true);

			try {
				String packageName = packageFragment.getElementName();
				text.setText(packageName);
				subject().setPackage(packageName);
			}
			finally {
				setPopulating(false);
			}
		}
	}

	private Button buildBrowseButton(Composite container) {
		return buildButton(
			container,
			JptUiXmlMessages.XmlJavaClassChooser_browse,
			buildBrowseButtonAction()
		);
	}

	private Runnable buildBrowseButtonAction() {
		return new Runnable() {
			public void run() {
				browsePackage();
			}
		};
	}

	private ModifyListener buildPackageModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (!isPopulating()) {
					Text text = (Text) e.widget;
					textChanged(text.getText());
				}
			}
		};
	}

	/**
	 * Opens a selection dialog that allows to select a package.
	 *
	 * @return returns the selected package or <code>null</code> if the dialog has been canceled.
	 * The caller typically sets the result to the package input field.
	 * <p>
	 * Clients can override this method if they want to offer a different dialog.
	 * </p>
	 *
	 * @since 3.2
	 */
	protected IPackageFragment choosePackage() {
		SelectionDialog selectionDialog;

		try {
			selectionDialog = JavaUI.createPackageDialog(
				shell(),
				getPackageFragmentRoot()
			);
		}
		catch (JavaModelException e) {
			JptUiPlugin.log(e);
			return null;
		}

		selectionDialog.setTitle(JptUiXmlMessages.XmlPackageChooser_PackageDialog_title);
		selectionDialog.setMessage(JptUiXmlMessages.XmlPackageChooser_PackageDialog_message);
		selectionDialog.setHelpAvailable(false);
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
	protected void doPopulate() {
		super.doPopulate();
		populateText();
	}

	/**
	 * Returns the package fragment corresponding to the current input.
	 *
	 * @return a package fragment or <code>null</code> if the input
	 * could not be resolved.
	 */
	public IPackageFragment getPackageFragment() {
		String packageString = this.text.getText();
		return getPackageFragmentRoot().getPackageFragment(packageString);
	}

	private IPackageFragmentRoot getPackageFragmentRoot() {

		IProject project = subject().jpaProject().project();
		IJavaProject root = JavaCore.create(project);

		try {
			return root.getAllPackageFragmentRoots()[0];
		}
		catch (JavaModelException e) {
			JptUiPlugin.log(e);
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		text = buildLabeledText(
			container,
			JptUiXmlMessages.XmlEntityMappingsDetailsPage_package,
			buildPackageModifyListener(),
			buildBrowseButton(container),
			IJpaHelpContextIds.ENTITY_ORM_PACKAGE
		);

		contentAssistProcessor = new JavaPackageCompletionProcessor(
			new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_ROOT)
		);

		ControlContentAssistHelper.createTextContentAssistant(
			text,
			contentAssistProcessor
		);
	}

	private void populateText() {

		EntityMappings subject = subject();
		text.setText("");

		if (subject == null) {
			return;
		}

		IPackageFragmentRoot root = getPackageFragmentRoot();

		if (root != null) {
			((JavaPackageCompletionProcessor) contentAssistProcessor).setPackageFragmentRoot(root);
		}

		String packageName = subject.getPackage();

		if (packageName == null) {
			packageName = "";
		}

		text.setText(packageName);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == EntityMappings.PACKAGE_PROPERTY) {
			populateText();
		}
	}

	private void textChanged(String text) {

		setPopulating(true);

		try {
			subject().setPackage(text);
		}
		finally {
			setPopulating(false);
		}
	}
}