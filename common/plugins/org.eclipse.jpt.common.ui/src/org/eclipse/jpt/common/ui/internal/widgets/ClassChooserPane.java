/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.ui.internal.widgets;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.search.IJavaSearchScope;
import org.eclipse.jdt.core.search.SearchEngine;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.ControlContentAssistHelper;
import org.eclipse.jdt.internal.ui.refactoring.contentassist.JavaTypeCompletionProcessor;
import org.eclipse.jdt.internal.ui.wizards.NewClassCreationWizard;
import org.eclipse.jdt.ui.IJavaElementSearchConstants;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.ui.wizards.NewClassWizardPage;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.common.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.common.ui.JptCommonUiPlugin;
import org.eclipse.jpt.common.ui.internal.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.common.utility.internal.ClassName;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.WritablePropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.SelectionDialog;
import org.eclipse.ui.forms.widgets.Hyperlink;

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
 * @version 2.3
 * @since 2.0
 */
@SuppressWarnings("nls")
public abstract class ClassChooserPane<T extends Model> extends ChooserPane<T>
{
	/**
	 * The code completion manager.
	 */
	protected JavaTypeCompletionProcessor javaTypeCompletionProcessor;

	private PropertyChangeListener subjectChangeListener;

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
	protected void initialize() {
		super.initialize();

		// TODO bug 156185 - when this is fixed there should be api for this
		this.javaTypeCompletionProcessor = new JavaTypeCompletionProcessor(false, false);

		this.subjectChangeListener = this.buildSubjectChangeListener();
		this.getSubjectHolder().addPropertyChangeListener(PropertyValueModel.VALUE, this.subjectChangeListener);

		this.classChooserSubjectChanged(getSubject());
	}

	private PropertyChangeListener buildSubjectChangeListener() {
		return new SWTPropertyChangeListenerWrapper(this.buildSubjectChangeListener_());
	}

	private PropertyChangeListener buildSubjectChangeListener_() {
		return new PropertyChangeListener() {
			@SuppressWarnings("unchecked")
			public void propertyChanged(PropertyChangeEvent e) {
				ClassChooserPane.this.classChooserSubjectChanged((T) e.getNewValue());
			}
		};
	}

	protected void classChooserSubjectChanged(T newSubject) {
		IPackageFragment packageFragment = null;
		if (newSubject != null) {
			IPackageFragmentRoot root = getPackageFragmentRoot();
			if (root != null) {
				packageFragment = root.getPackageFragment("");
			}
		}
		this.javaTypeCompletionProcessor.setPackageFragment(packageFragment);
	}

	@Override
	protected Control addLeftControl(Composite container) {
		if( ! this.allowTypeCreation()) {
			return super.addLeftControl(container);
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
			openInEditor(type);	
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
			type = getJavaProject().findType(getClassName().replace('$', '.'));
		}
		catch (JavaModelException e) {
			JptCommonUiPlugin.log(e);
		}
		return type;
	}
	
	protected void createType() {
		StructuredSelection selection = new StructuredSelection(getJavaProject().getProject());

		NewClassWizardPage newClassWizardPage = new NewClassWizardPage();
		newClassWizardPage.init(selection);
		newClassWizardPage.setSuperClass(getSuperclassName(), true);
		newClassWizardPage.setSuperInterfaces(getSuperInterfaceNames(), true);
		if (!StringTools.stringIsEmpty(getClassName())) {
			newClassWizardPage.setTypeName(ClassName.getSimpleName(getClassName()), true);
			String packageName = ClassName.getPackageName(getClassName());
			newClassWizardPage.setPackageFragment(getFirstJavaSourceFolder().getPackageFragment(packageName), true);
		}
		NewClassCreationWizard wizard = new NewClassCreationWizard(newClassWizardPage, false);
		wizard.init(PlatformUI.getWorkbench(), selection);

		WizardDialog dialog = new WizardDialog(getShell(), wizard);
		dialog.create();
		int dResult = dialog.open();
		if (dResult == Window.OK) {
			String className = (newClassWizardPage.getCreatedType()).getFullyQualifiedName(getEnclosingTypeSeparator());
			setClassName(className);
		}
	}
	
	protected abstract void setClassName(String className);
	
	/**
	 * Override this to change the enclosing type separator
	 */
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
	
	protected void openInEditor(IType type) {
		IJavaElement javaElement = type.getParent();
		try {
			JavaUI.openInEditor(javaElement, true, true);
		}
		catch (JavaModelException e) {
			JptCommonUiPlugin.log(e);
		}
		catch (PartInitException e) {
			JptCommonUiPlugin.log(e);
		}
	}

	protected abstract IJavaProject getJavaProject();
	
	@Override
	protected final Runnable buildBrowseAction() {
		return new Runnable() {
			public void run() {
				promptType();
			}
		};
	}

	@Override
	protected Control addMainControl(Composite container) {
		Composite subPane = addSubPane(container);
		Text text = addText(subPane, buildTextHolder());

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
		IJavaElement[] elements = new IJavaElement[] { getJavaProject() };
		IJavaSearchScope scope = SearchEngine.createJavaSearchScope(elements);
		SelectionDialog typeSelectionDialog;

		try {
			typeSelectionDialog = JavaUI.createTypeDialog(
				getShell(),
				PlatformUI.getWorkbench().getProgressService(),
				scope,
				getTypeDialogStyle(),
				false,
				StringTools.stringIsEmpty(getClassName()) ? "" : ClassName.getSimpleName(getClassName())
			);
		}
		catch (JavaModelException e) {
			JptCommonUiPlugin.log(e);
			return null;
		}

		typeSelectionDialog.setTitle(JptCommonUiMessages.ClassChooserPane_dialogTitle);
		typeSelectionDialog.setMessage(JptCommonUiMessages.ClassChooserPane_dialogMessage);

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

	protected IPackageFragmentRoot getFirstJavaSourceFolder() {
		Iterator<IPackageFragmentRoot> i = JDTTools.getJavaSourceFolders(getJavaProject()).iterator();
		return i.hasNext() ? i.next() : null;
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

	protected IPackageFragmentRoot getPackageFragmentRoot() {
		return JDTTools.getCodeCompletionContextRoot(getJavaProject());
	}

	@Override
	public void dispose() {
		this.getSubjectHolder().removePropertyChangeListener(PropertyValueModel.VALUE, this.subjectChangeListener);
		super.dispose();
	}
}