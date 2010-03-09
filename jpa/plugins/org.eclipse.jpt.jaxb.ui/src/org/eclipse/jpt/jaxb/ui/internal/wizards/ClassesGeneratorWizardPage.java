/*******************************************************************************
* Copyright (c) 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jaxb.ui.internal.wizards;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.ui.wizards.NewTypeWizardPage;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.jaxb.core.internal.ClassesGenerator;
import org.eclipse.jpt.jaxb.ui.internal.JptJaxbUiMessages;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.util.TableLayoutComposite;
import org.eclipse.jpt.utility.internal.ArrayTools;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.osgi.framework.Bundle;

/**
 *  ClassesGeneratorWizardPage
 */
public class ClassesGeneratorWizardPage extends NewTypeWizardPage {
	static public String JPT_JAXB_ECLIPSELINK_UI_PLUGIN_ID = "org.eclipse.jpt.eclipselink.jaxb.ui";   //$NON-NLS-1$

	private final JpaProject jpaProject;

	private SettingsGroup settingsGroup;
	
	private String targetFolder;
	private String targetPackage;
	
	private Button usesMoxyCheckBox;
	private boolean usesMoxy;

	// ********** constructor **********
	
	public ClassesGeneratorWizardPage(JpaProject jpaProject, String xmlSchemaName) {
		super(true, "Classes Generator"); //$NON-NLS-1$
		if (jpaProject == null) {
			throw new NullPointerException();
		}
		this.jpaProject = jpaProject;
		// default usesMoxy to true only when JPT EclipseLink bundle exists and MOXy is on the classpath
		this.usesMoxy = (jptEclipseLinkJaxbBundleExists() && moxyIsOnClasspath()); 
		
		this.setTitle(NLS.bind(JptJaxbUiMessages.ClassesGeneratorWizardPage_title, xmlSchemaName));
		this.setDescription(JptJaxbUiMessages.ClassesGeneratorWizardPage_desc);
	}

	// ********** UI components **********

	public void createControl(Composite parent) {
		this.setPageComplete(false);
		this.setControl(this.buildTopLevelControl(parent));
		
		initContainerPage(this.jpaProject.getJavaProject());
	}

	private Control buildTopLevelControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());
		
		this.settingsGroup = new SettingsGroup(composite);

		// add checkbox only if jpt.eclipselink.jaxb plugin is available
		// and EclipseLink MOXy is not on the classpath
		if(jptEclipseLinkJaxbBundleExists() && ! moxyIsOnClasspath()) {
			this.usesMoxyCheckBox = this.buildUsesMoxyCheckBox(composite);
		}
		
		Dialog.applyDialogFont(parent);
		return composite;
	}
	
	private Button buildUsesMoxyCheckBox(Composite parent) {

		 Button checkBox = new Button(parent, SWT.CHECK);
		checkBox.setText(JptJaxbUiMessages.ClassesGeneratorWizardPage_usesMoxyImplementation);
		checkBox.setSelection(this.usesMoxy());
		checkBox.addSelectionListener(this.buildUsesMoxySelectionListener());
		
		return checkBox;
	}
	
	private SelectionListener buildUsesMoxySelectionListener() {
		return new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent event) {
				this.widgetSelected(event);
			}
			
			public void widgetSelected(SelectionEvent event) {
				setUsesMoxy(usesMoxyCheckBox.getSelection());
				validateProjectClasspath();
			}
		};
	}

	// ********** intra-wizard methods **********
	
	protected String getTargetFolder() {
		return this.targetFolder;
	}

	protected String getTargetPackage() {
		return this.targetPackage;
	}

	protected String getCatalog() {
		return this.settingsGroup.getCatalog();
	}

	protected String[] getBindingsFileNames() {
		return this.settingsGroup.getBindingsFileNames();
	}
	
	protected Boolean usesMoxy() {
		return this.usesMoxy;
	}
	
	protected void setUsesMoxy(boolean usesMoxy){
		this.usesMoxy = usesMoxy;
	}

	// ********** internal methods **********
	
	private boolean jptEclipseLinkJaxbBundleExists() {
		return (this.getJptEclipseLinkJaxbBundle() != null);
	}
	
	private Bundle getJptEclipseLinkJaxbBundle() {
		return Platform.getBundle(JPT_JAXB_ECLIPSELINK_UI_PLUGIN_ID);	// Cannot reference directly EL plugin.
	}
	
	private void validateProjectClasspath() {
		//this line will suppress the "default package" warning (which doesn't really apply here
		//as the JAXB gen uses an org.example.schemaName package by default) and will clear the classpath warnings when necessary
		setMessage(null);
		
		if ( ! this.genericJaxbIsOnClasspath()) {
			this.displayWarning(JptJaxbUiMessages.ClassesGeneratorWizardPage_jaxbLibrariesNotAvailable);
		}
		else if (this.usesMoxy() && ! this.moxyIsOnClasspath()) {
			//this message is being truncated by the wizard width in some cases
			this.displayWarning(JptJaxbUiMessages.ClassesGeneratorWizardPage_moxyLibrariesNotAvailable);
		}

		//this code will intelligently remove our classpath warnings when they are present but no longer apply (as an alternative 
		//to setting the message to null continuously as is currently done)
//		else if( this.getMessage() != null){
//			if (this.getMessage().equals(JptJaxbUiMessages.ClassesGeneratorWizardPage_jaxbLibrariesNotAvailable) ||
//					this.getMessage().equals(JptJaxbUiMessages.ClassesGeneratorWizardPage_moxyLibrariesNotAvailable)) { 
//				setMessage(null);
//			}
//		}
	}
	
	private void displayWarning(String message) {
		this.setMessage(message, WARNING);
	}

	/**
	 * Test if the Jaxb compiler is on the classpath.
	 */
	private boolean genericJaxbIsOnClasspath() {
		try {
			String className = ClassesGenerator.JAXB_GENERIC_GEN_CLASS;
			IType genClass = this.jpaProject.getJavaProject().findType(className);
			return (genClass != null);
		} 
		catch (JavaModelException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * Test if the EclipseLink Jaxb compiler is on the classpath.
	 */
	private boolean moxyIsOnClasspath() {
		try {
			String className = ClassesGenerator.JAXB_ECLIPSELINK_GEN_CLASS;
			IType genClass = this.jpaProject.getJavaProject().findType(className);
			return (genClass != null);
		} 
		catch (JavaModelException e) {
			throw new RuntimeException(e);
		}
	}
	
	// ********** overrides **********
	

	
	@Override
	protected IStatus packageChanged() {
		IStatus status = super.packageChanged(); 
		IPackageFragment packageFragment = getPackageFragment();
		if (!status.matches(IStatus.ERROR)) {
			this.targetPackage = packageFragment.getElementName();
		}
		return status;
	}			
	
	@Override
	protected IStatus containerChanged() {
		IStatus status = super.containerChanged();
		String srcFolder = getPackageFragmentRootText();
		if( !status.matches(IStatus.ERROR) ){
				this.targetFolder = srcFolder.substring(srcFolder.indexOf("/") + 1);
		}
		return status;
	}
	
	@Override
	protected void handleFieldChanged(String fieldName) {
		super.handleFieldChanged(fieldName);
		if (this.fContainerStatus.matches(IStatus.ERROR)) {
			updateStatus(fContainerStatus);
		}else if( ! this.fPackageStatus.matches(IStatus.OK) ) {
			updateStatus(fPackageStatus);
		} else {
			updateStatus(Status.OK_STATUS);
		}
		validateProjectClasspath();
	}
	
	/**
	 * Override setVisible to insure that our more important warning
	 * message about classpath problems is displayed to the user first.
	 */
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		validateProjectClasspath();
	}
	
    @Override
    public final void performHelp() {
        //TODO We need a help ID for JAXB Generation
    }
	
	// ********** SettingsGroup class **********

	private class SettingsGroup {

		private final Text catalogText;

		private final ArrayList<String> bindingsFileNames;
		
		// ********** constructor **********

		private SettingsGroup(Composite composite) {
			super();
			Group group = new Group(composite, SWT.NONE);
			group.setLayout(new GridLayout(4, false));  // false = do not make columns equal width
			group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			group.setText(JptJaxbUiMessages.ClassesGeneratorWizardPage_settingsGroupTitle);
			// TODO PlatformUI.getWorkbench().getHelpSystem().setHelp(this.group, JpaHelpContextIds.XXX);

			// Source folder
			createContainerControls(group, 4);
			
			// Package
			createPackageControls(group, 4);
			
			// Catalog
			this.buildLabel(group, 1, JptJaxbUiMessages.ClassesGeneratorWizardPage_catalog);
			this.catalogText = this.buildText(group);
			
			// Bindings files
			this.bindingsFileNames = new ArrayList<String>();
			this.buildLabel(group, 1, JptJaxbUiMessages.ClassesGeneratorWizardPage_bindingsFiles);
			this.buildBindingsFileTable(group);
		}

		// ********** intra-wizard methods **********

		protected String getCatalog() {
			return this.catalogText.getText();
		}

		protected String[] getBindingsFileNames() {
			return ArrayTools.array(this.bindingsFileNames.iterator(), new String[0]);
		}
		
		// ********** UI components **********

		private Label buildLabel(Composite parent, int span, String text) {
			Label label = new Label(parent, SWT.NONE);
			label.setText(text);
			GridData gd = new GridData();
			gd.horizontalSpan = span;
			label.setLayoutData(gd);
			return label;
		}

		private Text buildText(Composite parent) {
			
			Text text = new Text(parent, SWT.BORDER);
			text.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			//Filler columns
			new Label(parent, SWT.NONE);
			new Label(parent, SWT.NONE);
			return text;
		}

		private TableViewer buildBindingsFileTable(Composite parent) {
			
			TableViewer tableViewer = this.buildTableViewer(parent, this.bindingsFileNames);
			
			this.buildAddRemoveButtons(parent, tableViewer, this.bindingsFileNames);
			return tableViewer;
		}
		
		private TableViewer buildTableViewer(Composite parent, ArrayList<String> tableDataModel) {	
		
			TableLayoutComposite tableLayout = new TableLayoutComposite(parent, SWT.NONE);
			this.addColumnsData(tableLayout);
		
			final Table table = new Table(tableLayout, SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER);
			table.setLinesVisible(false);
			
			TableColumn column = new TableColumn(table, SWT.NONE, 0);
			column.setResizable(true);
		
			GridData gridData= new GridData(GridData.FILL_BOTH);
			gridData.heightHint= SWTUtil.getTableHeightHint(table, 3);
			gridData.widthHint = 300;
			tableLayout.setLayoutData(gridData);
		
			TableViewer tableViewer = new TableViewer(table);
			tableViewer.setUseHashlookup(true);
			tableViewer.setLabelProvider(this.buildLabelProvider());
			tableViewer.setContentProvider(this.buildContentProvider());
			
			tableViewer.setInput(tableDataModel);
			return tableViewer;
		}
		
		private void buildAddRemoveButtons(Composite parent, final TableViewer tableViewer, final ArrayList<String> tableDataModel) {
		
			Composite buttonComposite = new Composite(parent, SWT.NULL);
			GridLayout buttonLayout = new GridLayout(1, false);
			buttonComposite.setLayout(buttonLayout);
			GridData gridData =  new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.verticalAlignment = GridData.BEGINNING;
			buttonComposite.setLayoutData(gridData);
			// Add buttons
			Button addButton = new Button(buttonComposite, SWT.PUSH);
			addButton.setText(JptJaxbUiMessages.ClassesGeneratorWizardPage_addButton);
			gridData =  new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			addButton.setLayoutData(gridData);
			addButton.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {}
			
				public void widgetSelected(SelectionEvent e) {

					String fileName = promptFile();
					if( ! StringTools.stringIsEmpty(fileName)) {
						
						tableDataModel.add(makeRelativeToProjectPath(fileName));
						tableViewer.refresh();
					}
				}
			});
			// Remove buttons
			Button removeButton = new Button(buttonComposite, SWT.PUSH);
			removeButton.setText(JptJaxbUiMessages.ClassesGeneratorWizardPage_removeButton);
			gridData =  new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			removeButton.setLayoutData(gridData);
			removeButton.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {}
			
				public void widgetSelected(SelectionEvent e) {
					StructuredSelection selection = (StructuredSelection)tableViewer.getSelection();
					if(selection.isEmpty()) {
						return;
					}
					String bindingsFileName = (String)selection.getFirstElement();
					removeBindingsFileName(bindingsFileName);
		
					tableViewer.refresh();
				}
			});
			addButton.setFocus();
		}

		// ********** internal methods **********

		private String makeRelativeToProjectPath(String filePath) {
			Path path = new Path(filePath);
			IPath relativePath = path.makeRelativeTo(jpaProject.getProject().getLocation());
			return relativePath.toOSString();
		}
		
		private void removeBindingsFileName(String bindingsName) {
			String name = this.getBindingsFileName(bindingsName);
			this.bindingsFileNames.remove(name);
		}
		
		private String getBindingsFileName(String bindingsName) {
			for(String name: this.bindingsFileNames) {
				if(name.equals(bindingsName)) {
					return name;
				}
			}
			return null;
		}

		private IBaseLabelProvider buildLabelProvider() {
			return new TableLabelProvider();
		}
		
		private IContentProvider buildContentProvider() {
			return new TableContentProvider();
		}

		/**
		 * The Add button was clicked, its action invokes this action which should
		 * prompt the user to select a file and return it.
		 */
		private String promptFile() {
			String projectPath= jpaProject.getProject().getLocation().toString();

			FileDialog dialog = new FileDialog(getShell());
			dialog.setText(JptJaxbUiMessages.ClassesGeneratorWizardPage_chooseABindingsFile);
			dialog.setFilterPath(projectPath);
			dialog.setFilterExtensions(new String[] {"*.xjb"});   //$NON-NLS-1$
			String filePath = dialog.open();
			
			return (filePath != null) ? filePath : null;
		}
		
		private void addColumnsData(TableLayoutComposite layout) {
			layout.addColumnData(new ColumnWeightData(50, true));
		}
		
	}

	// ********** inner class **********
	private class TableLabelProvider extends LabelProvider implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
		
		public String getColumnText(Object element, int columnIndex) {
			return (String)element;
		}
	}
	
	private class TableContentProvider implements IStructuredContentProvider {

		TableContentProvider() {
			super();
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
		
		public void dispose() {}
		
		public Object[] getElements(Object inputElement) {
			return ((Collection<?>) inputElement).toArray();
		}
	}
	
}