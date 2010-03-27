/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.ui.internal.wizards.gen;

import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaModel;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.internal.ui.wizards.NewWizardMessages;
import org.eclipse.jdt.internal.ui.wizards.TypedElementSelectionValidator;
import org.eclipse.jdt.internal.ui.wizards.TypedViewerFilter;
import org.eclipse.jdt.ui.JavaElementComparator;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jdt.ui.StandardJavaElementContentProvider;
import org.eclipse.jdt.ui.wizards.NewTypeWizardPage;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.window.Window;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.internal.utility.jdt.JDTTools;
import org.eclipse.jpt.gen.internal.ORMGenCustomizer;
import org.eclipse.jpt.gen.internal.ORMGenTable;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.ElementTreeSelectionDialog;
import org.eclipse.ui.help.IWorkbenchHelpSystem;

/**
 * A wizard page allowing the entry of the default table generation 
 * properties (Java classes package, base class, etc).
 * These properties apply to all tables unless explicitly overridden (in the table generation page).
 * 
 * @author Danny Ju
 */
public class DefaultTableGenerationWizardPage extends NewTypeWizardPage {

	private JpaProject jpaProject;

	/*the instance used to get/set the default properties.*/
	private ORMGenTable defaultsTable;

	private ORMGenCustomizer customizer;
	
	private TableGenPanel defaultTableGenPanel ;

	protected DefaultTableGenerationWizardPage(JpaProject jpaProject) {
		super(true, "DefaultTableGenerationWizardPage"); //$NON-NLS-1$
		this.jpaProject = jpaProject;
		setTitle(JptUiEntityGenMessages.GenerateEntitiesWizard_defaultTablePage_title);
		setDescription( JptUiEntityGenMessages.GenerateEntitiesWizard_defaultTablePage_desc);
	}
	
	
	// -------- Initialization ---------
	/**
	 * The wizard owning this page is responsible for calling this method with the
	 * current selection. The selection is used to initialize the fields of the wizard 
	 * page.
	 * 
	 * @param selection used to initialize the fields
	 */
	void init(IStructuredSelection selection) {
		IJavaElement jelem = null;
		if ( selection!=null && selection.getFirstElement() instanceof IJavaElement ) {
			jelem = (IJavaElement) selection.getFirstElement();
		}else{
			jelem = this.jpaProject.getJavaProject();			
		}
		if( jelem !=null ){
			initContainerPage(jelem);
			initTypePage(jelem);
		}
	}
	
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		Composite composite = new Composite(parent, SWT.NULL);
		int nColumns= 4		;
		GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		composite.setLayout(layout);
		this.getHelpSystem().setHelp(composite, JpaHelpContextIds.GENERATE_ENTITIES_WIZARD_CUSTOMIZE_DEFAULT_ENTITY_GENERATION);

		//Create entity access, collection type, etc 
		defaultTableGenPanel = new TableGenPanel(composite, 4, true, this);
		
		createDomainJavaClassesPropertiesGroup(composite, 4);
		setControl(composite);

		
		this.setPageComplete( true );
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			ORMGenCustomizer customizer = getCustomizer();
			//If user changed the connection or schema
			if ( this.customizer != customizer ) {
				this.customizer = customizer; 
				ORMGenTable newTable;
				newTable = getCustomizer().createGenTable(null);
				this.defaultsTable = newTable;
				defaultTableGenPanel.setORMGenTable(newTable);

				//set the super class and implemented interfaces value
				String baseClass = defaultsTable.getExtends() == null ?"" : defaultsTable.getExtends();
				setSuperClass(baseClass, true);
				setSuperInterfaces(defaultsTable.getImplements(), true);
				IPackageFragmentRoot root = getSourceFolder( defaultsTable.getSourceFolder());
				String initPackageName = this.getPackageText();
				if( initPackageName.length()==0 ){
					setPackageName( root, defaultsTable.getPackage() );
				}
				setPackageFragmentRoot(root, true/*canBeModified*/);
			}
		}
	}
	
	//search for the source folder with the given name or return the first
	//source folder if not found.
	private IPackageFragmentRoot getSourceFolder(String srcFolder) {
		IPackageFragmentRoot packageFragmentRoot = null;
		srcFolder = '/' + srcFolder;
		IJavaProject javaProject = this.jpaProject.getJavaProject();

		for (IPackageFragmentRoot root : JDTTools.getJavaSourceFolders(javaProject)) {
			//Save the first source root in case we don't find one that matches the saved value
			if (packageFragmentRoot == null) {
				packageFragmentRoot = root;
			}
			//check for alternative source root that matches the saved value
			if (root.getPath().toString().equals(srcFolder)){
				packageFragmentRoot = root;
				break;
			}
		}
		return packageFragmentRoot;
	}
	
	private void setPackageName(IPackageFragmentRoot packageFragmentRoot, String packageName) {
		if( packageName == null || packageName.length() == 0 || packageFragmentRoot==null) {
			return;
		}
		IPackageFragment packageFragment = packageFragmentRoot.getPackageFragment(packageName);
		setPackageFragment(packageFragment, true/*canBeModified*/);
	}


	protected void createDomainJavaClassesPropertiesGroup(Composite composite, int columns) {
		Group parent = new Group( composite, SWT.NONE);
		parent.setText( JptUiEntityGenMessages.GenerateEntitiesWizard_defaultTablePage_domainJavaClass);
		parent.setLayout(new GridLayout( columns, false));
		GridData layoutData = new GridData();
		layoutData.horizontalSpan = columns;
		layoutData.verticalAlignment = SWT.FILL;
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = true;
		parent.setLayoutData(layoutData);
		
		//default Java package name only available for default table generation
		createPackageControls(parent, columns);
		createContainerControls(parent, columns);
		createSuperClassControls(parent, columns);
		createSuperInterfacesControls(parent, columns);
	}
	
	@Override
	protected IStatus packageChanged() {
		IStatus status = super.packageChanged(); 
		IPackageFragment packageFragment = getPackageFragment();
		//String srcFolder = packageFragment.getPath().toPortableString();
		if (defaultsTable != null && !status.matches(IStatus.ERROR)) {
			defaultsTable.setPackage(packageFragment.getElementName());
		}
		return status;
	}	

	@Override
	protected IStatus superClassChanged() {
		IStatus status = super.superClassChanged();
		String baseClass = getSuperClass();
		if (baseClass != null && defaultsTable != null && !status.matches(IStatus.ERROR)) {
			String oldBaseClass = defaultsTable.getExtends();
			if ( !baseClass.equals(oldBaseClass)) {
				defaultsTable.setExtends(baseClass);
			}
		}
		return status; 
	}	
	@Override
	protected IStatus containerChanged() {
		IStatus status = super.containerChanged();
		String srcFolder = getPackageFragmentRootText();
		if( !status.matches(IStatus.ERROR) ){
			if (defaultsTable != null ) {
				defaultsTable.setSourceFolder( srcFolder );
			}
		}
		return status;
	}

	/** Override to allow select source folder in current project only
	 * @see org.eclipse.jdt.ui.wizards.NewContainerWizardPage#chooseContainer()
	 */
	@Override
	protected IPackageFragmentRoot chooseContainer() {
		Class<?>[] acceptedClasses = new Class[] { IPackageFragmentRoot.class, IJavaProject.class };
		TypedElementSelectionValidator validator= new TypedElementSelectionValidator(acceptedClasses, false) {
			@Override
			public boolean isSelectedValid(Object element) {
				try {
					if (element instanceof IJavaProject) {
						IJavaProject jproject= (IJavaProject)element;
						IPath path= jproject.getProject().getFullPath();
						return (jproject.findPackageFragmentRoot(path) != null);
					} else if (element instanceof IPackageFragmentRoot) {
						return JDTTools.packageFragmentRootIsSourceFolder((IPackageFragmentRoot) element);
					}
					return true;
				} catch (JavaModelException e) {
					JptUiPlugin.log(e); // just log, no UI in validation
				}
				return false;
			}
		};

		acceptedClasses= new Class[] { IJavaModel.class, IPackageFragmentRoot.class, IJavaProject.class };
		ViewerFilter filter= new TypedViewerFilter(acceptedClasses) {
			@Override
			public boolean select(Viewer viewer, Object parent, Object element) {
				if (element instanceof IPackageFragmentRoot) {
					return JDTTools.packageFragmentRootIsSourceFolder((IPackageFragmentRoot) element);
				}
				return super.select(viewer, parent, element);
			}
		};

		StandardJavaElementContentProvider provider= new StandardJavaElementContentProvider();
		ILabelProvider labelProvider= new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_DEFAULT);
		ElementTreeSelectionDialog dialog= new ElementTreeSelectionDialog(getShell(), labelProvider, provider);
		dialog.setValidator(validator);
		dialog.setComparator(new JavaElementComparator());
		dialog.setTitle(NewWizardMessages.NewContainerWizardPage_ChooseSourceContainerDialog_title);
		dialog.setMessage(NewWizardMessages.NewContainerWizardPage_ChooseSourceContainerDialog_description);
		dialog.addFilter(filter);
		dialog.setInput(jpaProject.getJavaProject());
		dialog.setInitialSelection(getPackageFragmentRoot());
		dialog.setHelpAvailable(false);

		if (dialog.open() == Window.OK) {
			Object element= dialog.getFirstResult();
			if (element instanceof IJavaProject) {
				IJavaProject jproject= (IJavaProject)element;
				return jproject.getPackageFragmentRoot(jproject.getProject());
			} else if (element instanceof IPackageFragmentRoot) {
				return (IPackageFragmentRoot)element;
			}
			return null;
		}
		return null;
	}
	
	@Override
	protected void handleFieldChanged(String fieldName) {
		super.handleFieldChanged(fieldName);
		if (this.fContainerStatus.matches(IStatus.ERROR)) {
			updateStatus(fContainerStatus);
		}else if( ! this.fPackageStatus.matches(IStatus.OK) ) {
			updateStatus(fPackageStatus);
		} else if (this.fSuperClassStatus.matches(IStatus.ERROR)) {
			updateStatus(fSuperClassStatus);
		} else {
			updateStatus(Status.OK_STATUS);
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	protected IStatus superInterfacesChanged() {
		IStatus ret = super.superInterfacesChanged();
		if ( ret.isOK() ) {
			List interfaces = getSuperInterfaces();
			if(defaultsTable!=null)
				defaultsTable.setImplements(interfaces);
		}
		return ret;
	}	

	private ORMGenCustomizer getCustomizer() {
		GenerateEntitiesFromSchemaWizard wizard = (GenerateEntitiesFromSchemaWizard) this.getWizard();
		return wizard.getCustomizer();
	}	

    @Override
    public final void performHelp() {
        this.getHelpSystem().displayHelp( GenerateEntitiesFromSchemaWizard.HELP_CONTEXT_ID );
    }
    
	protected final IWorkbenchHelpSystem getHelpSystem() {
		return PlatformUI.getWorkbench().getHelpSystem();
	}
}

