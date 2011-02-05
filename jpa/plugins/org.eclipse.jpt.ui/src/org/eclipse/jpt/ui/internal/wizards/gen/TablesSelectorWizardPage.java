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

import java.lang.reflect.InvocationTargetException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.ui.internal.util.TableLayoutComposite;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.db.ConnectionProfile;
import org.eclipse.jpt.db.JptDbPlugin;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.gen.internal.ORMGenCustomizer;
import org.eclipse.jpt.ui.JptUiPlugin;
import org.eclipse.jpt.ui.internal.ImageRepository;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;

class TablesSelectorWizardPage extends WizardPage{

	private static final int TABLE_COLUMN_INDEX = 0;
	private JpaProject jpaProject;
	private Schema schema = null;
	private ORMGenCustomizer customizer = null;
	private boolean updatePersistenceXml = true;

	private DatabaseGroup databaseGroup;
	private CheckboxTableViewer tableTable;
	private Button updatePersistenceXmlCheckBox;

	protected final ResourceManager resourceManager;
	
	TablesSelectorWizardPage(JpaProject jpaProject, ResourceManager resourceManager) {
		super("TablesSelectorWizardPage"); //$NON-NLS-1$
		this.jpaProject = jpaProject;
		this.resourceManager = resourceManager;
		this.schema = jpaProject.getDefaultDbSchema();
		setTitle(JptUiEntityGenMessages.GenerateEntitiesWizard_tableSelectPage_selectTable );
		setMessage(JptUiEntityGenMessages.GenerateEntitiesWizard_tableSelectPage_chooseEntityTable );
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		doStatusUpdate();
	}

	// -------- Provide access to wrapped DTP connection related classes ---------
	ConnectionProfile getProjectConnectionProfile() {
		String profileName = this.jpaProject.getDataSource().getConnectionProfileName();
		return this.connectionProfileNamed(profileName);
	}

	ConnectionProfile connectionProfileNamed(String profileName) {
		return JptDbPlugin.getConnectionProfileFactory().buildConnectionProfile(profileName);
	}

	Schema getSchema(){
		return this.schema;
	}

	void setSchema(Schema s){
		this.schema = s;
	}

	private Collection<Table> possibleTables() {
		Schema schema = this.getSchema();
		if (schema != null && schema.getName() != null) {
			return CollectionTools.collection(schema.getTables());
		}
		return Collections.<Table> emptyList();
	}

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);

		Composite composite = new Composite(parent, SWT.NULL);
		int nColumns= 3;
		GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		composite.setLayout(layout);
		this.getHelpSystem().setHelp(composite, JpaHelpContextIds.GENERATE_ENTITIES_WIZARD_SELECT_TABLES);

		this.databaseGroup = createDatabaseGroup(composite, 400);

		createTablesSelectionControl(composite, nColumns);

		
		this.updatePersistenceXmlCheckBox = new Button(composite, SWT.CHECK);
		this.updatePersistenceXmlCheckBox.setText(JptUiEntityGenMessages.GenerateEntitiesWizard_tableSelectPage_updatePersistenceXml );
		this.updatePersistenceXmlCheckBox.setSelection(updatePersistenceXml());
		this.updatePersistenceXmlCheckBox.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}

			public void widgetSelected(SelectionEvent e) {
				setShouldUpdatePersistenceXml(updatePersistenceXmlCheckBox.getSelection());
			}

		});
		fillColumns( this.updatePersistenceXmlCheckBox, 3);


		//Filler column
		new Label( composite, SWT.NONE);
		//Restore default button
		final Button restoreBtn = new Button(composite, SWT.PUSH );
		restoreBtn.setText( JptUiEntityGenMessages.GenerateEntitiesWizard_tableSelectPage_Restore_Defaults );
		restoreBtn.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			public void widgetSelected(SelectionEvent e) {
				if( customizer!=null && customizer.getFile()!=null ){
					if( customizer.getFile().exists() ){
						customizer.getFile().delete();
					}
					deselectAllTables();
					restoreUpdatePersistenceXmlDefault();
				}
			}

		});
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.END;
		restoreBtn.setLayoutData(gridData);
		
		this.updateSelectionState(databaseGroup.getSelectedSchema());
		this.getHelpSystem().setHelp(this.tableTable.getControl(), JpaHelpContextIds.DIALOG_GENERATE_ENTITIES_TABLES);
		this.setControl(composite);

		this.setPageComplete(true);
	}
	
	private void restoreUpdatePersistenceXmlDefault(){
		updatePersistenceXmlCheckBox.setSelection(true);
		setShouldUpdatePersistenceXml(true);
	}

	@Override
	public void dispose() {
		if (this.databaseGroup != null)
			this.databaseGroup.dispose();
		super.dispose();
	}

	@Override
	public IWizardPage getPreviousPage() {
		IWizardPage prevPage = super.getPreviousPage();
		if (prevPage instanceof PromptJPAProjectWizardPage)
			//Prevent going back to the PromptJPAProjectWizardPage
			//if JPA project already selected
			return prevPage.getPreviousPage();
		else
			return prevPage;
	}

	private DatabaseGroup createDatabaseGroup(Composite parent, int widthHint) {
		DatabaseGroup dbGroup = new DatabaseGroup(this.getContainer(), jpaProject, parent, resourceManager, widthHint);
		/**
		 * listen for when the Database Connection changes its selected schema
		 * so we can keep the page in synch
		 */
		class DatabasePageListener implements DatabaseGroup.Listener {
			public void selectedConnectionProfileChanged(ConnectionProfile connectionProfile) {
				jpaProject.getDataSource().setConnectionProfileName(connectionProfile.getName());
			}
			@SuppressWarnings("unchecked")
			public void selectedSchemaChanged(Schema schema) {
				if (schema==null) {
					updateTablesListViewer(Collections.EMPTY_LIST );
				} else {
					// store the *identifier* in the JPA project, since it gets put in Java annotations
					jpaProject.setUserOverrideDefaultSchema(schema.getIdentifier());
					setSchema( schema );
					updateSelectionState(schema);
				}
				doStatusUpdate();
			}
		}
		dbGroup.addListener(new DatabasePageListener());
		dbGroup.init();
		return dbGroup;
	}

	private boolean updatePersistenceXml() {
		return this.updatePersistenceXml;
	}

	private void setShouldUpdatePersistenceXml(boolean updatePersistenceXml){
		this.updatePersistenceXml = updatePersistenceXml;
		doStatusUpdate();
	}

	private void selectAllTables(){
		this.tableTable.setAllChecked(true);
		doStatusUpdate();
	}

	private void deselectAllTables(){
		this.tableTable.setAllChecked(false);
		doStatusUpdate();
	}

	private void initTablesSelectionControl(Collection<Table> possibleTables) {
		this.tableTable.setInput(possibleTables);
	}

	private void createTablesSelectionControl(Composite parent, int columns) {
		Label tableLabel = new Label(parent, SWT.NONE);
		GridData gd= new GridData();
		gd.horizontalAlignment = GridData.FILL;
		gd.verticalAlignment = GridData.BEGINNING;
		tableLabel.setLayoutData( gd );
		tableLabel.setText( JptUiEntityGenMessages.GenerateEntitiesWizard_tableSelectPage_tables );
		
		TableLayoutComposite layout= new TableLayoutComposite(parent, SWT.NONE);
		addColumnLayoutData(layout);

		final org.eclipse.swt.widgets.Table table = new org.eclipse.swt.widgets.Table(layout, SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER | SWT.CHECK);

		TableColumn tableNameColumn = new TableColumn(table, SWT.NONE, TABLE_COLUMN_INDEX);
		tableNameColumn.setText(JptUiEntityGenMessages.GenerateEntitiesWizard_tableSelectPage_tableColumn );
		tableNameColumn.setResizable(true);

		gd= new GridData(GridData.FILL_BOTH);
		gd.heightHint= SWTUtil.getTableHeightHint(table, 20);
		gd.widthHint = 250;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true ;
		layout.setLayoutData(gd);
		Color backgroundColor = new Color( Display.getDefault(), 255, 0,0);
		layout.setBackground(backgroundColor);
		backgroundColor.dispose();

		this.tableTable = new CheckboxTableViewer(table);
		this.tableTable.setUseHashlookup(true);
		this.tableTable.setLabelProvider(this.buildTableTableLabelProvider());
		this.tableTable.setContentProvider(this.buildTableTableContentProvider());
		this.tableTable.setSorter(new ViewerSorter() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				return ((Table) e1).getName().compareTo(((Table) e2).getName());
			}
		});

		this.tableTable.addPostSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				handleTablesListSelectionChanged(event);
			}
		});

		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.F2 && e.stateMask == SWT.NONE) {
					editEntityNameIfPossible();
					e.doit= false;
				}
			}
		});

		createButtonComposite(parent);
		initTablesSelectionControl(possibleTables());		
	}

	private void createButtonComposite(Composite parent){

		Composite buttonComposite = new Composite(parent, SWT.NULL);
		GridLayout buttonLayout = new GridLayout(1, false);
		buttonLayout.marginHeight = 0;
		buttonLayout.marginWidth = 0;
		
		buttonComposite.setLayout(buttonLayout);
		GridData data =  new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.BEGINNING;
		buttonComposite.setLayoutData(data);

		Button selectAllButton = new Button(buttonComposite, SWT.PUSH);
		selectAllButton.setToolTipText(JptUiMessages.General_selectAll);
		selectAllButton.setImage( ImageRepository.getSelectAllButtonImage(this.resourceManager)  );
		GridData gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		selectAllButton.setLayoutData(gridData);
		selectAllButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}

			public void widgetSelected(SelectionEvent e) {
				selectAllTables();
			}
		});

		Button deselectAllButton = new Button(buttonComposite, SWT.PUSH);
		deselectAllButton.setToolTipText(JptUiMessages.General_deselectAll);
		deselectAllButton.setImage( ImageRepository.getDeselectAllButtonImage(this.resourceManager) );
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		deselectAllButton.setLayoutData(gridData);
		deselectAllButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}

			public void widgetSelected(SelectionEvent e) {
				deselectAllTables();
			}
		});
	}


	private void addColumnLayoutData(TableLayoutComposite layout) {
		layout.addColumnData(new ColumnWeightData(50, true));
	}

	void editEntityNameIfPossible(){
		Object[] selected = ((IStructuredSelection) this.tableTable.getSelection()).toArray();
		if (selected.length != 1) {
			return;
		}
	}

	void handleTablesListSelectionChanged(SelectionChangedEvent event) {
		doStatusUpdate();
	}

	private IBaseLabelProvider buildTableTableLabelProvider() {
		return new TableTableLabelProvider();
	}

	private IContentProvider buildTableTableContentProvider() {
		return new TableTableContentProvider();
	}
	
	public Schema getDefaultSchema() {
		return this.jpaProject.getDefaultDbSchema() ;
	}
	
	Collection<Table> getSelectedTables() {
		ArrayList<Table> selectedTables = new ArrayList<Table>();
		for (Object selectedTable : this.tableTable.getCheckedElements())
			selectedTables.add((Table) selectedTable);
		return selectedTables;
	}

	private boolean hasTablesSelected() {
		return (this.tableTable != null) ? (this.getSelectedTables().size() > 0) : false;
	}

	void updateTablesListViewer(Collection<Table> possibleTables) {
		if (this.tableTable != null) {
			this.initTablesSelectionControl(possibleTables);
		}
	}

	/**
	 * Update the status line and the OK button according to the given status
	 */
	protected void doStatusUpdate() {
		if ( ! this.hasTablesSelected()) {
			this.setPageComplete(false);
		}else{
			setPageComplete(true);
			try{
				getContainer().run(false, false, new IRunnableWithProgress(){
					public void run( final IProgressMonitor monitor ) 
				    	throws InvocationTargetException, InterruptedException
				    {
						monitor.beginTask("Updating", 10);
				
						Collection<Table> ret = getSelectedTables();
						ArrayList<String> tableNames = new ArrayList<String>();
						for( Table t : ret ){
							tableNames.add(t.getName());
						}
						Schema schema = getSchema();
						if( schema == null ){
							return ;
						}
						customizer.setSchema(schema);
						customizer.setTableNames(tableNames);
						customizer.setUpdatePersistenceXml(updatePersistenceXml);
						monitor.done();
				    }
				});
			} catch (Exception e) {
				JptUiPlugin.log(e);
			}
				
		}
	}

	// ********** inner classes **********
	private class TableTableLabelProvider extends LabelProvider implements ITableLabelProvider {

		TableTableLabelProvider() {
			super();
		}

		@Override
		public String getText(Object element) {
			return ((Table) element).getName();
		}

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			if (element == null) {
				return null;
			}
			switch (columnIndex) {
				case TABLE_COLUMN_INDEX:
					return ((Table) element).getName();

			}
			throw new IllegalArgumentException("invalid column index: " + columnIndex);// $NON-NLS-1$
		}

	}


	private class TableTableContentProvider implements IStructuredContentProvider {

		TableTableContentProvider() {
			super();
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {	}

		public void dispose() {}

		public Object[] getElements(Object inputElement) {
			return ((Collection<?>) inputElement).toArray();
		}

	}

	private void updateSelectionState(final Schema schema) {
		if(schema ==null)
			return;
		this.jpaProject.setUserOverrideDefaultSchema( schema.getIdentifier());
		
		updateTablesListViewer(CollectionTools.collection(schema.getTables()));

		//Create the ORMGenCustomizer
		GenerateEntitiesFromSchemaWizard wizard = (GenerateEntitiesFromSchemaWizard) getWizard();
		customizer = wizard.createORMGenCustomizer( schema );

		if( this.tableTable!=null && this.updatePersistenceXmlCheckBox!=null && customizer != null  ){
			restoreWizardState();
		}
        doStatusUpdate();

	}

	private boolean restoreWizardState(){
		boolean pageComplete = false;
		this.updatePersistenceXmlCheckBox.setSelection(this.customizer.updatePersistenceXml());
		List<String> preSelectedTableNames = this.customizer.getTableNames();
		if(preSelectedTableNames!=null && preSelectedTableNames.size()>0) {
			Set<String> set = new HashSet<String>();
			for(String s : preSelectedTableNames ){
				set.add(s);
			}
	        TableItem[] items = this.tableTable.getTable().getItems();
	        for (int i = 0; i < items.length; ++i) {
	            TableItem item = items[i];
	            org.eclipse.jpt.db.Table element = (org.eclipse.jpt.db.Table)item.getData();
	            if (element != null) {
	                boolean check = set.contains(element.getName());
	                // only set if different, to avoid flicker
	                if (item.getChecked() != check) {
	                    item.setChecked(check);
	                    pageComplete = true;
	                }
	            }
	        }
		}
		return pageComplete;
	}


	/**
	 * Set the layoutData of the input control to occupy specified number of columns
	 * @param c
	 * @param columns
	 */
	private void fillColumns(Control c, int columns){
		GridData layoutData = new GridData();
		layoutData.horizontalSpan = columns;
		layoutData.verticalAlignment = SWT.FILL;
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = false;
		c.setLayoutData(layoutData);
		return ;
	}

    @Override
    public final void performHelp() 
    {
    	this.getHelpSystem().displayHelp( GenerateEntitiesFromSchemaWizard.HELP_CONTEXT_ID );
    }
    
	protected final IWorkbenchHelpSystem getHelpSystem() {
		return PlatformUI.getWorkbench().getHelpSystem();
	}
}
