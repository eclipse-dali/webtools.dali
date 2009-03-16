/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.ui.internal.wizards.gen;

import java.util.List;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.ui.wizards.NewTypeWizardPage;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.gen.internal2.ORMGenCustomizer;
import org.eclipse.jpt.gen.internal2.ORMGenTable;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;

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
		if( jpaProject != null ){
			IJavaElement jelem = this.jpaProject.getJavaProject();
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
		//PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, JpaHelpContextIds.DIALOG_GENERATE_ENTITIES);

		//Create entity access, collection type, etc 
		defaultTableGenPanel = new TableGenPanel(composite, 4, true , this);
		
		createDomainJavaClassesPropertiesGroup(composite, 4);
		setControl(composite);

		
		this.setPageComplete( true );
		
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if(visible){
			ORMGenCustomizer customizer = getCustomizer();
			//If user changed the connection or schema
			if( this.customizer != customizer ){
				this.customizer = customizer; 
				ORMGenTable newTable;
				newTable = getCustomizer().createGenTable(null);
				this.defaultsTable = newTable;
				defaultTableGenPanel.setORMGenTable(newTable);

				//set the super class and implemented interfaces value
				String baseClass = defaultsTable.getExtends()==null?"":defaultsTable.getExtends();
				setSuperClass(baseClass, true);
				setSuperInterfaces(defaultsTable.getImplements(), true);
				
				setPackageName( defaultsTable.getPackage() );
				

			}
		}
	}	
	
	private void setPackageName(String packageName) {
		if( packageName==null )
			return;
		// Copied from org.eclipse.pde.internal.ui.editor.plugin.JavaAttributeWizardPage
		try {
			IPackageFragmentRoot srcEntryDft = null;
			IJavaProject javaProject = jpaProject.getJavaProject();
			IPackageFragmentRoot[] roots = javaProject.getPackageFragmentRoots();
			for (int i = 0; i < roots.length; i++) {
				if (roots[i].getKind() == IPackageFragmentRoot.K_SOURCE) {
					srcEntryDft = roots[i];
					break;
				}
			}
			IPackageFragmentRoot packageFragmentRoot;
			if (srcEntryDft != null)
				packageFragmentRoot = srcEntryDft;
			else {
				packageFragmentRoot = javaProject.getPackageFragmentRoot(javaProject.getResource());
			}
			IFolder packageFolder = javaProject.getProject().getFolder(packageName);
			IPackageFragment packageFragment = packageFragmentRoot.getPackageFragment(packageFolder.getProjectRelativePath().toOSString());
			setPackageFragment(packageFragment, true/*canBeModified*/);
		} catch (CoreException e) {
			JptUiPlugin.log(e);
		}
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
		createSuperClassControls(parent, columns);
		createSuperInterfacesControls(parent, columns);

	}
	
	protected IStatus packageChanged() {
		IStatus status = super.packageChanged(); 
		IPackageFragment packageFragment = getPackageFragment();
		String srcFolder = packageFragment.getPath().toPortableString();
		if(defaultsTable!=null && !status.matches(IStatus.ERROR)){
			defaultsTable.setPackage(srcFolder, packageFragment.getElementName());
		}
		return status;
	}	

	protected IStatus superClassChanged() {
		IStatus status = super.superClassChanged();
		String baseClass = getSuperClass();
		if(baseClass!=null && defaultsTable!=null && !status.matches(IStatus.ERROR)){
			String oldBaseClass = defaultsTable.getExtends();
			if( !baseClass.equals(oldBaseClass))
				defaultsTable.setExtends(baseClass);
		}
		return status; 
	}	
	
	protected void handleFieldChanged(String fieldName) {
		super.handleFieldChanged(fieldName);
		if( this.fPackageStatus.matches(IStatus.ERROR) ){
			updateStatus(fPackageStatus);
		}else if( this.fSuperClassStatus.matches(IStatus.ERROR)){
			updateStatus(fSuperClassStatus);
		}else{
			setMessage("", IMessageProvider.NONE);
			setErrorMessage(null);
			updateStatus(fPackageStatus);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected IStatus superInterfacesChanged() {
		IStatus ret = super.superInterfacesChanged();
		if( ret.isOK() ){
			List interfaces = getSuperInterfaces();
			if(defaultsTable!=null)
				defaultsTable.setImplements(interfaces);
		}
		return ret;
	}	

	private ORMGenCustomizer getCustomizer(){
		GenerateEntitiesFromSchemaWizard wizard = (GenerateEntitiesFromSchemaWizard) this.getWizard();
		return wizard.getCustomizer();
	}	

    @Override
    public final void performHelp() 
    {
        PlatformUI.getWorkbench().getHelpSystem().displayHelp( GenerateEntitiesFromSchemaWizard.HELP_CONTEXT_ID );
    }
}

