/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.internal.utility.JavaProjectTools;
import org.eclipse.jpt.common.ui.JptCommonUiMessages;
import org.eclipse.jpt.common.ui.internal.listeners.SWTPropertyChangeListenerWrapper;
import org.eclipse.jpt.common.ui.internal.plugin.JptCommonUiPlugin;
import org.eclipse.jpt.common.utility.internal.ClassNameTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.model.Model;
import org.eclipse.jpt.common.utility.model.event.PropertyChangeEvent;
import org.eclipse.jpt.common.utility.model.listener.PropertyChangeListener;
import org.eclipse.jpt.common.utility.model.value.ModifiablePropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
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
 * -----------------------------------------------------------------------
 * |  ---------------------------------------------------- ------------- |
 * |  | I                                                | | Browse... | |
 * |  ---------------------------------------------------- ------------- |
 * -----------------------------------------------------------------------</pre>
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
	 * @param parentPane The parent pane of this one
	 * @param parent The parent container
	 * @param hyperlink include a Hyperlink widget to select/or create a Type
	 */
	public ClassChooserPane(Pane<? extends T> parentPane,
        Composite parent,
        Hyperlink hyperlink) {

		super(parentPane, parent);
		initialize(hyperlink);
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

	/**
	 * Creates a new <code>ClassChooserPane</code>.
	 *
	 * @param parentPane The parent container of this one
	 * @param subjectHolder The holder of this pane's subject
	 * @param parent The parent container
	 * @param hyperlink include a Hyperlink widget to select/or create a Type
	 */
	public ClassChooserPane(Pane<?> parentPane,
        PropertyValueModel<? extends T> subjectHolder,
        Composite parent,
        Hyperlink hyperlink) {

		super(parentPane, subjectHolder, parent);
		initialize(hyperlink);
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
	                        PropertyValueModel<Boolean> enabledModel,
	                        Composite parent) {

		super(parentPane, subjectHolder, enabledModel, parent);
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
	                        PropertyValueModel<Boolean> enabledModel,
	                        Composite parent,
	                        Hyperlink hyperlink) {

		this(parentPane, subjectHolder, enabledModel, parent);
		this.initialize(hyperlink);
	}

	@Override
	protected void initialize() {
		super.initialize();

		// TODO bug 156185 - when this is fixed there should be api for this
		this.javaTypeCompletionProcessor = buildJavaTypeCompletionProcessor();

		this.subjectChangeListener = this.buildSubjectChangeListener();
		this.getSubjectHolder().addPropertyChangeListener(PropertyValueModel.VALUE, this.subjectChangeListener);

		this.classChooserSubjectChanged(getSubject());
	}

	protected void initialize(Hyperlink hyperlink) {
		final Runnable hyperLinkAction = this.buildHyperLinkAction();
		hyperlink.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {

				Hyperlink hyperLink = (Hyperlink) e.widget;

				if (hyperLink.isEnabled()) {
					hyperLinkAction.run();
				}
			}
		});
	}

	protected JavaTypeCompletionProcessor buildJavaTypeCompletionProcessor() {
		return new JavaTypeCompletionProcessor(false, false);
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

	private Runnable buildHyperLinkAction() {
		return new Runnable() {
			public void run() {
				ClassChooserPane.this.hyperLinkSelected();
			}
		};
	}

	protected void hyperLinkSelected() {
		IType type = resolveJdtType();
		if (type != null) {
			openInEditor(type);	
		}
		else {
			createType();
		}
	}
	
	protected IType resolveJdtType() {
		String className = this.getFullyQualifiedClassName();
		if (className == null) {
			return null;
		}
		return JavaProjectTools.findType(this.getJavaProject(), className);
	}
	
	protected void createType() {
		StructuredSelection selection = new StructuredSelection(getJavaProject().getProject());

		NewClassWizardPage newClassWizardPage = new NewClassWizardPage();
		newClassWizardPage.init(selection);
		newClassWizardPage.setSuperClass(getSuperclassName(), true);
		newClassWizardPage.setSuperInterfaces(getSuperInterfaceNames(), true);
		String qualifiedClassName = this.getFullyQualifiedClassName();
		if (!StringTools.isBlank(qualifiedClassName)) {
			newClassWizardPage.setTypeName(ClassNameTools.simpleName(qualifiedClassName), true);
			String packageName = ClassNameTools.packageName(qualifiedClassName);
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
	
	protected void openInEditor(IType type) {
		try {
			JavaUI.openInEditor(type, true, true);
		}
		catch (JavaModelException e) {
			JptCommonUiPlugin.instance().logError(e);
		}
		catch (PartInitException e) {
			JptCommonUiPlugin.instance().logError(e);
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
		Text text = addText(container, buildTextHolder(), getHelpId());

		ControlContentAssistHelper.createTextContentAssistant(
			text,
			javaTypeCompletionProcessor
		);

		return text;
	}

	/**
	 * Creates the value holder of the subject's property.
	 *
	 * @return The holder of the class name
	 */
	protected abstract ModifiablePropertyValueModel<String> buildTextHolder();

	/**
	 * Prompts the user the Open Type dialog.
	 *
	 * @return Either the selected type or <code>null</code> if the user
	 * cancelled the dialog
	 */
	protected IType chooseType() {
		SelectionDialog dialog;
		try {
			dialog = JavaUI.createTypeDialog(
					getShell(),
					PlatformUI.getWorkbench().getProgressService(),
					SearchEngine.createJavaSearchScope(new IJavaElement[] { getJavaProject() }),
					getTypeDialogStyle(),
					false,
					StringTools.isBlank(getClassName()) ? StringTools.EMPTY_STRING : ClassNameTools.simpleName(getClassName())
				);
		} catch (JavaModelException ex) {
			JptCommonUiPlugin.instance().logError(ex);
			return null;
		}

		dialog.setTitle(JptCommonUiMessages.CLASS_CHOOSER_PANE__DIALOG_TITLE);
		dialog.setMessage(JptCommonUiMessages.CLASS_CHOOSER_PANE__DIALOG_MESSAGE);

		return (dialog.open() == Window.OK) ? (IType) dialog.getResult()[0] : null;
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
	 * Return the fully qualified class name ('.' qualification)
	 * Override this method if getClassName() does not return the fully qualified name
	 */
	protected String getFullyQualifiedClassName() {
		return this.getClassName() == null ? null : this.getClassName().replace('$', '.');
	}

	protected IPackageFragmentRoot getFirstJavaSourceFolder() {
		Iterator<IPackageFragmentRoot> i = JavaProjectTools.getSourceFolders(getJavaProject()).iterator();
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
		try {
			return this.getJavaProject().getPackageFragmentRoots()[0];
		} catch (JavaModelException ex) {
			JptCommonUiPlugin.instance().logError(ex);
			return null;
		}
	}

	@Override
	public void controlDisposed() {
		this.getSubjectHolder().removePropertyChangeListener(PropertyValueModel.VALUE, this.subjectChangeListener);
		super.controlDisposed();
	}
}