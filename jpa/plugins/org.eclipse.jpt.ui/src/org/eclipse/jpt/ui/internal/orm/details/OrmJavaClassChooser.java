/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.orm.details;

import java.util.Collection;
import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.ControlContentAssistHelper;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.JavaTypeCompletionProcessor;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.orm.AbstractTypeMapping;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.orm.JptUiOrmMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.progress.IProgressService;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |             ----------------------------------------------- ------------- |
 * | Java Class: | I                                           | | Browse... | |
 * |             ----------------------------------------------- ------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * TODO possibly help the user and if they have chosen a package at the
 * entity-mappings level only insert the class name in the xml file if they
 * choose a class from the package.
 * Not sure if this should be driven by the UI or by ui api in the model
 *
 * @see OrmTypeMapping
 * @see OrmPersistentTypeDetailsPage - The parent container
 *
 * @version 2.0
 * @since 1.0
 */
@SuppressWarnings("nls")
public class OrmJavaClassChooser extends AbstractFormPane<OrmTypeMapping<?>> {

	private JavaTypeCompletionProcessor javaTypeCompletionProcessor;
	private Text text;

	/**
	 * Creates a new <code>XmlJavaClassChooser</code>.
	 *
	 * @param parentPane The parent controller of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 */
	public OrmJavaClassChooser(AbstractFormPane<?> parentPane,
	                           PropertyValueModel<? extends OrmTypeMapping<? extends AbstractTypeMapping>> subjectHolder,
	                           Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	/**
	 * Creates a new <code>XmlJavaClassChooser</code>.
	 *
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public OrmJavaClassChooser(PropertyValueModel<? extends OrmTypeMapping<? extends AbstractTypeMapping>> subjectHolder,
	                           Composite parent,
	                           TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void addPropertyNames(Collection<String> propertyNames) {
		super.addPropertyNames(propertyNames);
		propertyNames.add(OrmTypeMapping.CLASS_PROPERTY);
	}

	private void browseType() {

		IType type = chooseType();

		if (type != null) {

			setPopulating(true);

			try {
				String className = type.getFullyQualifiedName();
				text.setText(className);
				subject().setClass(className);
			}
			finally {
				setPopulating(false);
			}
		}
	}

	private Button buildBrowseButton(Composite container) {
		return buildButton(
			container,
			JptUiOrmMessages.OrmJavaClassChooser_browse,
			buildBrowseButtonAction()
		);
	}

	private Runnable buildBrowseButtonAction() {
		return new Runnable() {
			public void run() {
				browseType();
			}
		};
	}

	private ModifyListener buildPackageTextModifyListener() {
		return new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				if (!isPopulating()) {
					Text text = (Text) e.widget;
					textChanged(text.getText());
				}
			}
		};
	}

	private IType chooseType() {

		IPackageFragmentRoot root = getPackageFragmentRoot();

		if (root == null) {
			return null;
		}

		IJavaElement[] elements= new IJavaElement[] { root.getJavaProject() };
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
				text.getText()
			);
		}
		catch (JavaModelException e) {
			JptUiPlugin.log(e);
			return null;
		}

		typeSelectionDialog.setTitle(JptUiOrmMessages.OrmJavaClassChooser_XmlJavaClassDialog_title);
		typeSelectionDialog.setMessage(JptUiOrmMessages.OrmJavaClassChooser_XmlJavaClassDialog_message);

		if (typeSelectionDialog.open() == Window.OK) {
			return (IType) typeSelectionDialog.getResult()[0];
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
			JptUiOrmMessages.PersistentTypePage_javaClassLabel,
			buildPackageTextModifyListener(),
			buildBrowseButton(container)
		);

		//TODO bug 156185 - when this is fixed there should be api for this
		this.javaTypeCompletionProcessor = new JavaTypeCompletionProcessor(false, false);
		ControlContentAssistHelper.createTextContentAssistant(this.text,  this.javaTypeCompletionProcessor);
	}

	private void populateText() {

		OrmTypeMapping<?> subject = subject();
		text.setText("");

		if (subject == null) {
			return;
		}

		IPackageFragmentRoot root = getPackageFragmentRoot();

		if (root != null) {
			 javaTypeCompletionProcessor.setPackageFragment(root.getPackageFragment(""));
		}

		String javaClass = subject.getClass_();

		if (javaClass == null) {
			javaClass = "";
		}

		text.setText(javaClass);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void propertyChanged(String propertyName) {
		super.propertyChanged(propertyName);

		if (propertyName == OrmTypeMapping.CLASS_PROPERTY) {
			populateText();
		}
	}

	private void textChanged(String text) {

		setPopulating(true);

		try {
			subject().setClass(text);
		}
		finally {
			setPopulating(false);
		}
	}
}