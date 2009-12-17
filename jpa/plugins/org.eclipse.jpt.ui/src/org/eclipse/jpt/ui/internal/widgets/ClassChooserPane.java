/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.widgets;

import java.util.Collections;
import java.util.List;

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
import org.eclipse.jdt.internal.ui.wizards.NewClassCreationWizard;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.ui.wizards.NewClassWizardPage;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.utility.internal.ClassTools;
import org.eclipse.jpt.utility.model.Model;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.forms.widgets.Hyperlink;
import org.eclipse.ui.progress.IProgressService;

/**
 * This chooser allows the user to choose a type when browsing and it adds code
 * completion support to the text field, which is the main component.
 * <p>
 * Here the layout of this pane:
 * <pre>
 * -----------------------------------------------------------------------------
 * |        ---------------------------------------------------- ------------- |
 * | Label: | I                                                | | Browse... | |
 * |        ---------------------------------------------------- ------------- |
 * -----------------------------------------------------------------------------</pre>
 *
 * @version 2.0
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class ClassChooserPane<T extends Model> extends ChooserPane<T>
{
	/**
	 * The code completion manager.
	 */
	protected JavaTypeCompletionProcessor javaTypeCompletionProcessor;

	/**
	 * Creates a new <code>ClassChooserPane</code>.
	 *
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 */
	public ClassChooserPane(Pane<? extends T> parentPane,
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
	public ClassChooserPane(Pane<?> parentPane,
	                        PropertyValueModel<? extends T> subjectHolder,
	                        Composite parent) {

		super(parentPane, subjectHolder, parent);
	}

	@Override
	protected Control addLeftControl(Composite container) {
		if( ! this.allowTypeCreation()) {
			return this.addLabel(container, this.getLabelText());
		}
		Hyperlink labelLink = this.addHyperlink(container,
			this.getLabelText(),
			this.buildHyperLinkAction()
		);
		return labelLink;
	}

	private Runnable buildHyperLinkAction() {
		return new Runnable() {
			public void run() {
				ClassChooserPane.this.hyperLinkSelected();
			}
		};
	}

	protected void hyperLinkSelected() {
		IType type = getType();
		if (type != null) {
			openEditor(type);	
		}
		else if (allowTypeCreation()){
			createType();
		}
	}
	
	protected IType getType() {
		if (getClassName() == null) {
			return null;
		}
		IType type = null;
		try {
			type = getJpaProject().getJavaProject().findType(getClassName().replace('$', '.'));
		}
		catch (JavaModelException e) {
			JptUiPlugin.log(e);
		}
		return type;
	}
	
	protected void createType() {
		NewClassWizardPage newClassWizardPage = new NewClassWizardPage();
		newClassWizardPage.setSuperClass(getSuperclassName(), true);
		newClassWizardPage.setSuperInterfaces(getSuperInterfaceNames(), true);
		newClassWizardPage.setPackageFragmentRoot(getPackageFragmentRoot(), true);
		if (getClassName() != null) {
			newClassWizardPage.setTypeName(ClassTools.shortNameForClassNamed(getClassName()), true);
			String packageName = ClassTools.packageNameForClassNamed(getClassName());
			if (packageName != null) {
				newClassWizardPage.setPackageFragment(getPackageFragmentRoot().getPackageFragment(packageName), true);
			}
		}
		NewClassCreationWizard wizard = new NewClassCreationWizard(newClassWizardPage, false);
		wizard.init(PlatformUI.getWorkbench(), new StructuredSelection(getJpaProject().getProject()));//TODO StructuredSelection
		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		int dResult = dialog.open();
		if (dResult == Window.OK) {
			String className = (newClassWizardPage.getCreatedType()).getFullyQualifiedName(getEnclosingTypeSeparator());
			setClassName(className);
		}
	}
	
	protected abstract void setClassName(String className);
	
	protected char getEnclosingTypeSeparator() {
		return '$';
	}
	
	/**
	 * Override this to set a superclass in the New Class wizard.  If no class is chosen, 
	 * clicking the hyperlink label will open the new class wizard.
	 */
	protected String getSuperclassName() {
		return "";
	}
	
	/**
	 * Override this to set a super interface in the New Class wizard.  If no class is chosen, 
	 * clicking the hyperlink label will open the new class wizard.
	 * @see getSuperInterfaceName
	 */
	protected List<String> getSuperInterfaceNames() {
		return getSuperInterfaceName() != null ? Collections.singletonList(getSuperInterfaceName()) : Collections.<String>emptyList();
	}
	
	/**
	 * Override this to set a super interface in the New Class wizard.  If no class is chosen, 
	 * clicking the hyperlink label will open the new class wizard.
	 */
	protected String getSuperInterfaceName() {
		return null;
	}
	
	/**
	 * Override this if it does not make sense to allow the user to create a new type.
	 * This will determine whether clicking the hyperlink opens the New Class wizard
	 * @return
	 */
	protected boolean allowTypeCreation() {
		return true;
	}
	
	protected void openEditor(IType type) {
		IJavaElement javaElement = type.getParent();
		try {
			JavaUI.openInEditor(javaElement, true, true);
		}
		catch (JavaModelException e) {
			JptUiPlugin.log(e);
		}
		catch (PartInitException e) {
			JptUiPlugin.log(e);
		}
	}

	protected abstract JpaProject getJpaProject();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected final Runnable buildBrowseAction() {
		return new Runnable() {
			public void run() {
				promptType();
			}
		};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Control addMainControl(Composite container) {
		Composite subPane = addSubPane(container);
		Text text = addText(subPane, buildTextHolder());

		Image image = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_CONTENT_PROPOSAL).getImage();
		GridData data = new GridData(GridData.FILL_HORIZONTAL);
		data.horizontalIndent = image.getBounds().width;
		text.setLayoutData(data);

		ControlContentAssistHelper.createTextContentAssistant(
			text,
			javaTypeCompletionProcessor
		);

		return subPane;
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

		IPackageFragmentRoot root = getPackageFragmentRoot();

		if (root == null) {
			return null;
		}

		IJavaElement[] elements = new IJavaElement[] { root.getJavaProject() };
		IJavaSearchScope scope = SearchEngine.createJavaSearchScope(elements);
		IProgressService service = PlatformUI.getWorkbench().getProgressService();
		SelectionDialog typeSelectionDialog;

		try {
			typeSelectionDialog = JavaUI.createTypeDialog(
				getShell(),
				service,
				scope,
				getTypeDialogStyle(),
				false,
				getClassName() != null ? ClassTools.shortNameForClassNamed(getClassName()) : ""
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

	protected int getTypeDialogStyle() {
		return IJavaElementSearchConstants.CONSIDER_CLASSES;
	}
	
	/**
	 * Returns the class name from its subject.
	 *
	 * @return The class name or <code>null</code> if none is defined
	 */
	protected abstract String getClassName();

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doPopulate() {
		super.doPopulate();
		updatePackageFragment();
	}

	/**
	 * {@inheritDoc}
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
	protected IPackageFragmentRoot getPackageFragmentRoot() {
		IProject project = getJpaProject().getProject();
		IJavaProject root = JavaCore.create(project);

		try {
			return root.getAllPackageFragmentRoots()[0];
		}
		catch (JavaModelException e) {
			JptUiPlugin.log(e);
		}
		return null;
	}

	/**
	 * The browse button was clicked, its action invokes this action which should
	 * prompt the user to select a class and set it.
	 */
	protected void promptType() {
		IType type = this.chooseType();

		if (type != null) {
			String className = type.getFullyQualifiedName(getEnclosingTypeSeparator());
			setClassName(className);
		}
	}
	
	private void updatePackageFragment() {

		if (getSubject() != null) {
			IPackageFragmentRoot root = getPackageFragmentRoot();

			if (root != null) {
				javaTypeCompletionProcessor.setPackageFragment(root.getPackageFragment(""));
				return;
			}
		}

		javaTypeCompletionProcessor.setPackageFragment(null);
	}
}