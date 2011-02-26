/*******************************************************************************
* Copyright (c) 2010, 2011 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.dbws.eclipselink.ui.internal.wizards.gen;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.ui.internal.util.TableLayoutComposite;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.dbws.eclipselink.ui.JptDbwsUiPlugin;
import org.eclipse.jpt.dbws.eclipselink.ui.internal.JptDbwsUiIcons;
import org.eclipse.jpt.dbws.eclipselink.ui.internal.JptDbwsUiMessages;
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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

/**
 *  JdbcDriversWizardPage
 */
public class JdbcDriverWizardPage extends WizardPage
{
	private final String storedDriverClasspathId;
	private ArrayList<String> jarPaths;
	
	public static String BINDINGS_FILE_FILTER = "*.jar;*.zip";   //$NON-NLS-1$
	// dialog store id constants
	private static final String PAGE_ID = "DRIVER_CLASSPATH_ID";	//$NON-NLS-1$
	private static final String PATH_SEPARATOR = System.getProperty("path.separator");	//$NON-NLS-1$

	// ********** constructor **********

	protected JdbcDriverWizardPage(String wizardName) {
		super("Jdbc Driver"); //$NON-NLS-1$

		this.initialize();
		
		this.jarPaths = new ArrayList<String>();
		this.storedDriverClasspathId = wizardName + "." + PAGE_ID; //$NON-NLS-1$
	}

	protected void initialize() {
		this.setTitle(JptDbwsUiMessages.JdbcDriverWizardPage_title);
		this.setDescription(JptDbwsUiMessages.JdbcDriverWizardPage_desc);
		this.setImageDescriptor(JptDbwsUiPlugin.getImageDescriptor(JptDbwsUiIcons.DBWS_GEN_WIZ_BANNER));
	}

	// ********** intra-wizard methods **********

	public void finish() {
		this.saveWidgetValues();
	}

	public String getDriverJarList() {
		return this.buildDriverJarListString();
	}

	// ********** internal methods **********

	/**
	 *	Initializes the JAR package from last used wizard page values.
	 */
	private void initializeJarPaths() {
		IDialogSettings settings= this.getDialogSettings();
		if(settings != null) {
			// destination
			String[] driverPaths = settings.getArray(this.storedDriverClasspathId);
			if(driverPaths == null) {
				return; // ie.- no settings stored
			}
			this.jarPaths = new ArrayList<String>();
			
			CollectionTools.addAll(this.jarPaths, driverPaths);
		}
	}
	
	private void saveWidgetValues() {
		IDialogSettings settings= this.getDialogSettings();

		if(settings != null) {
			String[] driverPaths = this.jarPaths.toArray(new String[0]);
			settings.put(this.storedDriverClasspathId, driverPaths);
		}
	}

	private String buildDriverJarListString() {
		if(this.jarPaths.isEmpty()) {
			return StringTools.EMPTY_STRING;
		}
		StringBuffer result = new StringBuffer();
		for(String jar : this.jarPaths) {
			result.append(jar).append(PATH_SEPARATOR);
		}
		result.deleteCharAt(result.length() - 1);	// remove last separator
		return result.toString();
	}
	
	// ********** UI components **********

	public void createControl(Composite parent) {
		this.initializeJarPaths();
		this.setPageComplete(true);
		this.setControl(this.buildTopLevelControl(parent));
	}

	private Control buildTopLevelControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		composite.setLayout(new GridLayout());

		new JarsGroup(composite);
		
		return composite;
	}

	// ********** private methods **********

	private void removeJarFile(String filePath) {
		this.jarPaths.remove(filePath);
	}
	
	private void addJarFile(String filePath) {
		if( ! this.jarPaths.contains(filePath)) {
			this.jarPaths.add(filePath);
		}
	}
	
	// ********** JarsGroup class **********

	private class JarsGroup {

		
		// ********** constructor **********

		private JarsGroup(Composite parent) {
			super();
			Composite composite = new Composite(parent, SWT.NONE);
			GridLayout layout = new GridLayout(2, false); //must be 4 for the package controls
			layout.marginHeight = 0;
			layout.marginWidth = 0;
			composite.setLayout(layout);
			composite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
			// TODO PlatformUI.getWorkbench().getHelpSystem().setHelp(this.group, HELP_CONTEXT_ID);

			this.buildLabel(composite, 2, JptDbwsUiMessages.JdbcDriverWizardPage_driverFiles);
			
			this.buildDriverFilesTable(composite);
		}
	    
		// ********** intra-wizard methods **********
	    
		public IJavaProject getJavaProject() {
			IWizard wizard = getWizard();
	    	
			if( ! (wizard instanceof DbwsGeneratorWizard)) {
				throw new NullPointerException();
			}
			return ((DbwsGeneratorWizard)wizard).getJavaProject();
		}

		private ArrayList<String> getJarPaths() {
			return jarPaths;
		}

		// ********** private methods **********

		private TableViewer buildDriverFilesTable(Composite parent) {
			
			TableViewer tableViewer = this.buildTableViewer(parent, this.getJarPaths());
			
			this.buildAddRemoveButtons(parent, tableViewer);
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
			gridData.horizontalSpan = 1;
			gridData.heightHint= SWTUtil.getTableHeightHint(table, 7);
			tableLayout.setLayoutData(gridData);
			
			TableViewer tableViewer = new TableViewer(table);
			tableViewer.setUseHashlookup(true);
			tableViewer.setLabelProvider(this.buildLabelProvider());
			tableViewer.setContentProvider(this.buildContentProvider());
			
			tableViewer.setInput(tableDataModel);
			tableViewer.refresh();
			return tableViewer;
		}
		
		private void buildAddRemoveButtons(Composite parent, final TableViewer tableViewer) {
			
			Composite buttonComposite = new Composite(parent, SWT.NULL);
			GridLayout buttonLayout = new GridLayout(1, false);
			buttonComposite.setLayout(buttonLayout);
			GridData gridData =  new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.verticalAlignment = GridData.BEGINNING;
			buttonComposite.setLayoutData(gridData);
			// Add buttons
			Button addButton = new Button(buttonComposite, SWT.PUSH);
			addButton.setText(JptDbwsUiMessages.JdbcDriverWizardPage_addButton);
			gridData = new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace= true;
			addButton.setLayoutData(gridData);
			addButton.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {}

				public void widgetSelected(SelectionEvent e) {

					ArrayList<String> jarFiles = promptJarFiles();
					for(String jarFile : jarFiles) {
						addJarFile(jarFile);
					}
					tableViewer.refresh();
				}
			});
			// Remove buttons
			Button removeButton = new Button(buttonComposite, SWT.PUSH);
			removeButton.setText(JptDbwsUiMessages.JdbcDriverWizardPage_removeButton);
			gridData =  new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			gridData.grabExcessHorizontalSpace= true;
			removeButton.setLayoutData(gridData);
			removeButton.addSelectionListener(new SelectionListener() {
				public void widgetDefaultSelected(SelectionEvent e) {}
				
				public void widgetSelected(SelectionEvent e) {
					StructuredSelection selection = (StructuredSelection)tableViewer.getSelection();
					if(selection.isEmpty()) {
						return;
					}
					String jarFile = (String)selection.getFirstElement();
					removeJarFile(jarFile);
					
					tableViewer.refresh();
				}
			});
			addButton.setFocus();
		}

		private IBaseLabelProvider buildLabelProvider() {
			return new TableLabelProvider();
		}
		
		private IContentProvider buildContentProvider() {
			return new TableContentProvider();
		}

		private ArrayList<String> promptJarFiles() {
			String projectPath= this.getJavaProject().getProject().getLocation().toString();

			FileDialog dialog = new FileDialog(getShell(), SWT.MULTI);
			dialog.setText(JptDbwsUiMessages.JdbcDriverWizardPage_chooseADriverFile);
			dialog.setFilterPath(projectPath);
			dialog.setFilterExtensions(new String[] {BINDINGS_FILE_FILTER});

			dialog.open();
			String path = dialog.getFilterPath();
			String[] fileNames = dialog.getFileNames();
			ArrayList<String> results = new ArrayList<String>(fileNames.length);
			for(String fileName : fileNames) {
				results.add(path + File.separator + fileName);
			}
			return results;
		}

		private void addColumnsData(TableLayoutComposite layout) {
			layout.addColumnData(new ColumnWeightData(50, true));
		}

		/**
		 * Build and return a label
		 */
		private Label buildLabel(Composite parent, int span, String text) {
			Label label = new Label(parent, SWT.NONE);
			label.setText(text);
			GridData gd = new GridData();
			gd.horizontalSpan = span;
			label.setLayoutData(gd);
			return label;
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

}