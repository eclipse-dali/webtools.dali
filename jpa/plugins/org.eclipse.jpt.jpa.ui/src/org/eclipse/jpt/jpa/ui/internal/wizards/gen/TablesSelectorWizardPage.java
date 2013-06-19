/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.ui.internal.wizards.gen;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.eclipse.core.resources.WorkspaceJob;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
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
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.common.ui.JptCommonUiImages;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.common.ui.internal.swt.widgets.DisplayTools;
import org.eclipse.jpt.common.ui.internal.swt.widgets.TableTools;
import org.eclipse.jpt.common.ui.internal.widgets.TableLayoutComposite;
import org.eclipse.jpt.common.utility.internal.StringMatcher;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.gen.internal.ORMGenCustomizer;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.wizards.gen.JptJpaUiWizardsEntityGenMessages;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class TablesSelectorWizardPage extends WizardPage {

	private static final int TABLE_COLUMN_INDEX = 0;
	
	private JpaProject jpaProject;
	private Schema schema;
	private ORMGenCustomizer customizer;
	private boolean updatePersistenceXml = true;
	private boolean isDynamic;

	private DatabaseGroup databaseGroup;
	private CheckboxTableViewer tableTable;
	private Button updatePersistenceXmlCheckBox;
	private Button refreshTablesButton;
	private Button selectAllButton;
	private Button deselectAllButton;
	private Text searchText;
	private TableFilter filter;

	private WorkspaceJob fetchTablesJob;
	protected final ResourceManager resourceManager;

	// ********** constructors **********
	
	public TablesSelectorWizardPage(JpaProject jpaProject, ResourceManager resourceManager, boolean isDynamic) {
		super("TablesSelectorWizardPage"); //$NON-NLS-1$
		
		this.jpaProject = jpaProject;
		this.resourceManager = resourceManager;
		this.schema = jpaProject.getDefaultDbSchema();
		this.isDynamic = isDynamic;
		this.setTitle(JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_SELECT_TABLE );
		this.setMessage(JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_CHOOSE_ENTITY_TABLE );
	}

	// ********** IDialogPage implementation  **********
	
	public void createControl(Composite parent) {
		this.initializeDialogUnits(parent);

		this.setPageComplete(true);
		this.setControl(this.buildTopLevelControl(parent));
	}

	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		
		this.doStatusUpdate();
		this.updateButtons();
	}

	@Override
	public void dispose() {
		if(this.databaseGroup != null) {
			this.databaseGroup.dispose();
		}
		super.dispose();
	}

    @Override
    public final void performHelp() {
    	WorkbenchTools.displayHelp(this.getWizard().getHelpContextID());
    }
    
	@Override
	public GenerateEntitiesFromSchemaWizard getWizard() {
		return (GenerateEntitiesFromSchemaWizard) super.getWizard();
	}

	// ********** IWizardPage implementation  **********

	@Override
	public IWizardPage getPreviousPage() {
		IWizardPage previousPage = super.getPreviousPage();
		if(previousPage instanceof PromptJPAProjectWizardPage)
			//Prevent going back to the PromptJPAProjectWizardPage
			//if JPA project already selected
			return previousPage.getPreviousPage();
		else
			return previousPage;
	}

	// ********** internal methods **********

	private Composite buildTopLevelControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		int nColumns= 3;
		GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		composite.setLayout(layout);
		WorkbenchTools.setHelp(composite, JpaHelpContextIds.GENERATE_ENTITIES_WIZARD_SELECT_TABLES);

		this.databaseGroup = this.createDatabaseGroup(composite, 400);

		this.createTablesSelectionControl(composite, nColumns);

		if (!isDynamic) {
			this.updatePersistenceXmlCheckBox = this.buildUpdatePersistenceXmlCheckBox(composite);
			this.fillColumns(this.updatePersistenceXmlCheckBox, 3);
		}
		
		//Filler column
		new Label( composite, SWT.NONE);
		//Restore defaults button
		this.buildRestoreDefaultsButton(composite);

		this.updateSelectionState(this.databaseGroup.getSelectedSchema());
		WorkbenchTools.setHelp(this.tableTable.getControl(), JpaHelpContextIds.DIALOG_GENERATE_ENTITIES_TABLES);
		return composite;
	}

	private void createTablesSelectionControl(Composite parent, int columns) {
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.BEGINNING;
		
		Label tableLabel = new Label(parent, SWT.NONE);
		tableLabel.setLayoutData(gridData);
		tableLabel.setText(JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_TABLES );
		
		this.searchText = this.buildSearchText(parent);

		// build two empty labels to align the components
		new Label(parent, SWT.NONE);
		new Label(parent, SWT.NONE);
		
		TableLayoutComposite layout = new TableLayoutComposite(parent, SWT.NONE);
		this.addColumnLayoutData(layout);

		filter = new TableFilter();
		this.tableTable = this.buildCheckboxTableViewer(this.buildTable(layout));
		this.tableTable.addFilter(filter);

		this.createButtonComposite(parent);
		this.initTablesSelectionControl(this.possibleTables());		
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

		this.selectAllButton = this.buildSelectAllButton(buttonComposite);
		this.deselectAllButton = this.buildDeselectAllButton(buttonComposite);
		this.refreshTablesButton = this.buildRefreshTablesButton(buttonComposite);
	}
	
	private DatabaseGroup createDatabaseGroup(Composite parent, int widthHint) {
		DatabaseGroup dbGroup = new DatabaseGroup(this.getContainer(), this.jpaProject, parent, this.resourceManager, widthHint);
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
					updateTablesListViewer(Collections.EMPTY_LIST);
				} 
				else {
					// store the *identifier* in the JPA project, since it gets put in Java annotations
					jpaProject.setUserOverrideDefaultSchema(schema.getIdentifier());
					setSchema(schema);
					updateButtons();
					updateSelectionState(schema);
				}
				doStatusUpdate();
			}
		}
		dbGroup.addListener(new DatabasePageListener());
		dbGroup.init();
		return dbGroup;
	}

	// ********** private methods **********

	private Schema getSchema() {
		return this.schema;
	}

	private void setSchema(Schema s) {
		this.schema = s;
	}

	private boolean connectionIsActive() {
		return this.databaseGroup.connectionIsActive();
	}

	private Collection<Table> getTables(Schema schema) {
		if(this.fetchTablesJobIsRunning()) {
			return Collections.<Table> emptyList();
		}
		return CollectionTools.collection(schema.getTables());
	}

	private Collection<Table> possibleTables() {
		Schema schema = this.getSchema();
		if(schema != null && schema.getName() != null) {
			return this.getTables(schema);
		}
		return Collections.<Table> emptyList();
	}

	private void updateButtons() {
		if(this.selectAllButton != null) {
			this.selectAllButton.setEnabled(this.connectionIsActive());
		}
		if(this.refreshTablesButton != null) {
			this.refreshTablesButton.setEnabled(this.connectionIsActive());
		}
		if(this.deselectAllButton != null) {
			this.deselectAllButton.setEnabled(this.connectionIsActive());
		}
	}
	
	private boolean updatePersistenceXml() {
		return this.updatePersistenceXml;
	}

	private void setShouldUpdatePersistenceXml(boolean updatePersistenceXml){
		this.updatePersistenceXml = updatePersistenceXml;
		this.doStatusUpdate();
	}
	
	private void restoreUpdatePersistenceXmlDefault() {
		this.updatePersistenceXmlCheckBox.setSelection(true);
		this.setShouldUpdatePersistenceXml(true);
	}

	private void selectAllTables() {
		this.tableTable.setAllChecked(true);
		this.doStatusUpdate();
	}

	private void deselectAllTables() {
		this.tableTable.setAllChecked(false);
		this.doStatusUpdate();
	}

	private void refreshTables() {
		this.schema.refresh();
		this.updateSelectionState(this.databaseGroup.getSelectedSchema());
	}

	private void initTablesSelectionControl(Collection<Table> possibleTables) {
		this.tableTable.setInput(possibleTables);
	}

	// ********** UI components **********
	
	private org.eclipse.swt.widgets.Table buildTable(Composite parent) {
		org.eclipse.swt.widgets.Table table = new org.eclipse.swt.widgets.Table(parent, SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER | SWT.CHECK);

		TableColumn tableNameColumn = new TableColumn(table, SWT.NONE, TABLE_COLUMN_INDEX);
		tableNameColumn.setText(JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_TABLE_COLUMN);
		tableNameColumn.setResizable(true);

		table.addKeyListener(this.buildTableKeyListener());
		
		GridData gridData= new GridData(GridData.FILL_BOTH);
		gridData.heightHint= TableTools.calculateHeightHint(table, 20);
		gridData.widthHint = 250;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true ;
		
		Color backgroundColor = new Color(parent.getDisplay(), 255, 0,0);
		parent.setLayoutData(gridData);
		parent.setBackground(backgroundColor);
		backgroundColor.dispose();
		
		return table;
	}
	
	private CheckboxTableViewer buildCheckboxTableViewer(org.eclipse.swt.widgets.Table parent) {
		CheckboxTableViewer tableViewer = new CheckboxTableViewer(parent);
		tableViewer.setUseHashlookup(true);
		
		tableViewer.setLabelProvider(this.buildTableTableLabelProvider());
		tableViewer.setContentProvider(this.buildTableTableContentProvider());
		
		tableViewer.setSorter(this.buildTableViewerSorter());
		tableViewer.addPostSelectionChangedListener(this.buildTableSelectionChangedListener());
		
		return tableViewer;
	}
	
	private ViewerSorter buildTableViewerSorter() {
		return new ViewerSorter() {
			@Override
			public int compare(Viewer viewer, Object e1, Object e2) {
				return ((Table) e1).getName().compareTo(((Table) e2).getName());
			}
		};
	}
	
	private Button buildUpdatePersistenceXmlCheckBox(Composite parent) {
		Button checkBox = new Button(parent, SWT.CHECK);
		checkBox.setText(JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_UPDATE_PERSISTENCE_XML );
		checkBox.setSelection(this.updatePersistenceXml());
		checkBox.addSelectionListener(this.buildUpdatePersistenceXmlSelectionListener());
		
		return checkBox;
	}

	private Button buildRestoreDefaultsButton(Composite parent) {
		Button button = new Button(parent, SWT.PUSH);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.END;
		button.setLayoutData(gridData);
		button.setText(JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_RESTORE_DEFAULTS);
		button.addSelectionListener(this.buildRestoreDefaultsSelectionListener());
		
		return button;
	}

	private Button buildSelectAllButton(Composite parent) {
		Button button = this.buildSelectionButton(parent,
			JptJpaUiMessages.GENERAL_SELECT_ALL,
			this.resourceManager.createImage(JptCommonUiImages.SELECT_ALL_BUTTON));
		
		button.addSelectionListener(this.buildSelectAllSelectionListener());
		return button;
	}

	private Button buildDeselectAllButton(Composite parent) {
		Button button = this.buildSelectionButton(parent,
			JptJpaUiMessages.GENERAL_DESELECT_ALL,
			this.resourceManager.createImage(JptCommonUiImages.DESELECT_ALL_BUTTON));
		
		button.addSelectionListener(this.buildDeselectAllSelectionListener());
		return button;
	}

	private Button buildRefreshTablesButton(Composite parent) {
		Button button = this.buildSelectionButton(parent,
			JptJpaUiMessages.GENERAL_REFRESH,
			this.resourceManager.createImage(JptCommonUiImages.REFRESH_BUTTON));
		
		button.addSelectionListener(this.buildRefreshTablesSelectionListener());
		return button;
	}

	private Button buildSelectionButton(Composite parent, String toolTipText, Image buttonImage) {
		Button button = new Button(parent, SWT.PUSH);
		button.setToolTipText(toolTipText);
		button.setImage(buttonImage);
		GridData gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		button.setLayoutData(gridData);
		return button;
	}
	
	private void addColumnLayoutData(TableLayoutComposite layout) {
		layout.addColumnData(new ColumnWeightData(50, true));
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

	private IBaseLabelProvider buildTableTableLabelProvider() {
		return new TableTableLabelProvider();
	}

	private IContentProvider buildTableTableContentProvider() {
		return new TableTableContentProvider();
	}

	private Text buildSearchText(Composite parent) {
		GridData gridData = new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL);
		Text text = new Text(parent, SWT.BORDER | SWT.SEARCH | SWT.ICON_SEARCH | SWT.ICON_CANCEL);
		text.setMessage(JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_TABLE_SEARCH);
		text.setLayoutData(gridData);
		text.addSelectionListener(this.buildClearSearchTextSelectionListener());
		text.addKeyListener(this.buildSearchTextKeyListener());
		return text;
	}

	// ********** listeners callbacks **********

	private void handleTablesListSelectionChanged(SelectionChangedEvent event) {
		this.doStatusUpdate();
	}

	// ********** listeners **********
	
	private KeyAdapter buildTableKeyListener() {
		return new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.keyCode == SWT.F2 && e.stateMask == SWT.NONE) {
					editEntityNameIfPossible();
					e.doit= false;
				}
			}
		};
	}
	
	private ISelectionChangedListener buildTableSelectionChangedListener() {
		return new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				handleTablesListSelectionChanged(event);
			}
		};
	}
	
	private SelectionListener buildUpdatePersistenceXmlSelectionListener() {
		return new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}

			public void widgetSelected(SelectionEvent e) {
				setShouldUpdatePersistenceXml(updatePersistenceXmlCheckBox.getSelection());
			}
		};
	}
	
	private SelectionListener buildRestoreDefaultsSelectionListener() {
		return new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
			
			public void widgetSelected(SelectionEvent e) {
				if(customizer != null && customizer.getFile() != null ){
					if(customizer.getFile().exists() ){
						customizer.getFile().delete();
					}
					deselectAllTables();
					restoreUpdatePersistenceXmlDefault();
				}
			}
		};
	}
	
	private SelectionListener buildSelectAllSelectionListener() {
		return new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}

			public void widgetSelected(SelectionEvent e) {
				selectAllTables();
			}
		};
	}
	
	private SelectionListener buildDeselectAllSelectionListener() {
		return new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}

			public void widgetSelected(SelectionEvent e) {
				deselectAllTables();
			}
		};
	}
	
	private SelectionListener buildRefreshTablesSelectionListener() {
		return new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}

			public void widgetSelected(SelectionEvent e) {
				refreshTables();
			}
		};
	}

	private SelectionListener buildClearSearchTextSelectionListener() {
		return new SelectionAdapter() {
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				filter.setPattern("");
				tableTable.refresh();
			}
		};
	}

	private KeyListener buildSearchTextKeyListener() {
		return new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent event) {
				filter.setPattern(searchText.getText());
				tableTable.refresh();
			}
		};
	}

	// ********** table behaviors **********

	public boolean hasTablesSelected() {
		return (this.tableTable != null) ? (this.getSelectedTables().size() > 0) : false;
	}
	
	private void editEntityNameIfPossible() {
		Object[] selected = ((IStructuredSelection) this.tableTable.getSelection()).toArray();
		if (selected.length != 1) {
			return;
		}
	}
	
	private Collection<Table> getSelectedTables() {
		ArrayList<Table> selectedTables = new ArrayList<Table>();
		for (Object selectedTable : this.tableTable.getCheckedElements())
			selectedTables.add((Table) selectedTable);
		return selectedTables;
	}

	private void updateTablesListViewer(Collection<Table> possibleTables) {
		if(this.tableTable != null) {
			this.initTablesSelectionControl(possibleTables);
		}
	}

	private boolean tableInitialized() {
		return (this.tableTable != null) && (this.tableTable.getTable().getItemCount() > 0);
	}

	// ********** fetch tables **********

	private boolean fetchTablesJobIsRunning() {
		return this.fetchTablesJob != null;
	}

	private WorkspaceJob buildFetchTablesJob(final Schema schema) {
		final Collection<Table> tables = new ArrayList<Table>();

		WorkspaceJob workspaceJob = new WorkspaceJob(JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_GET_TABLES_JOB_NAME) {

			@Override
			public IStatus runInWorkspace(IProgressMonitor monitor) {
				if(monitor.isCanceled()) {
					return Status.CANCEL_STATUS;
				}
				SubMonitor subMonitor = SubMonitor.convert(monitor, 
					JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_GET_TABLES_TASK_NAME, 75);
				try {
					subMonitor.beginTask(schema.getContainer().getName(), 100);
					subMonitor.subTask(
						NLS.bind(
							JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_GET_TABLES_SUB_TASK_NAME, 
							schema.getName()));
					subMonitor.worked(20);

					tables.addAll(CollectionTools.collection(schema.getTables()));
					
					subMonitor.worked(95);
				}
				catch(OperationCanceledException e) {
					return Status.CANCEL_STATUS;
				}
				finally {
					subMonitor.done();
				}
				return Status.OK_STATUS;
			}
		};

		workspaceJob.addJobChangeListener(new JobChangeAdapter() {
			@Override
			public void done(final IJobChangeEvent event) {
				
				 DisplayTools.asyncExec(new Runnable() {
		               public void run() {
		            	   updateTablesListViewer(tables);
		               }
				 });
				event.getJob().removeJobChangeListener(this);
				fetchTablesJob = null;
			}
		});
		return workspaceJob;
	}

	// ********** updates **********
	
	/**
	 * Update the status line and the OK button according to the given status
	 */
	private void doStatusUpdate() {
		if( ! this.hasTablesSelected()) {
			this.setPageComplete(false);
		}
		else {
			this.setPageComplete(true);
			try{
				this.getContainer().run(false, false, new IRunnableWithProgress() {
					public void run( final IProgressMonitor monitor ) 
				    	throws InvocationTargetException, InterruptedException
				    {
						monitor.beginTask(JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_TABLE_SELECT_PAGE_STATUS_UPDATE_TASK_NAME, 10);
				
						Collection<Table> ret = TablesSelectorWizardPage.this.getSelectedTables();
						ArrayList<String> tableNames = new ArrayList<String>();
						for(Table t : ret) {
							tableNames.add(t.getName());
						}
						Schema schema = getSchema();
						if(schema == null) {
							return;
						}
						customizer.setSchema(schema);
						customizer.setTableNames(tableNames);
						customizer.setUpdatePersistenceXml(updatePersistenceXml);
						monitor.done();
				    }
				});
			} 
			catch (Exception e) {
				JptJpaUiPlugin.instance().logError(e);
			}
		}
	}

	private void updateSelectionState(final Schema schema) {
		if(schema ==null) {
			return;
		}
		this.jpaProject.setUserOverrideDefaultSchema(schema.getIdentifier());

		if( ! this.fetchTablesJobIsRunning() && ! this.tableInitialized()) {
			this.fetchTablesJob = this.buildFetchTablesJob(schema);
			this.fetchTablesJob.schedule();
		}
		else {
			this.updateTablesListViewer(this.getTables(schema));
		}

		//Create the ORMGenCustomizer
		this.customizer = this.getWizard().createORMGenCustomizer(schema);

		if(this.tableTable!=null && this.customizer != null) {
			this.restoreWizardState();
		}
		this.doStatusUpdate();
	}

	private boolean restoreWizardState() {
		boolean pageComplete = false;
		if (this.updatePersistenceXmlCheckBox != null){
			this.updatePersistenceXmlCheckBox.setSelection(this.customizer.updatePersistenceXml());
		}
		List<String> preSelectedTableNames = this.customizer.getTableNames();
		if(preSelectedTableNames!=null && preSelectedTableNames.size()>0) {
			Set<String> set = new HashSet<String>();
			for(String s : preSelectedTableNames){
				set.add(s);
			}
	        TableItem[] items = this.tableTable.getTable().getItems();
	        for(int i = 0; i < items.length; ++i) {
	            TableItem item = items[i];
	            org.eclipse.jpt.jpa.db.Table element = (org.eclipse.jpt.jpa.db.Table)item.getData();
	            if(element != null) {
	                boolean check = set.contains(element.getName());
	                // only set if different, to avoid flicker
	                if(item.getChecked() != check) {
	                    item.setChecked(check);
	                    pageComplete = true;
	                }
	            }
	        }
		}
		return pageComplete;
	}

	// ********** inner classes **********
	
	private class TableTableLabelProvider extends LabelProvider implements ITableLabelProvider {

		// ********** constructors **********
		
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
			if(element == null) {
				return null;
			}
			switch(columnIndex) {
				case TABLE_COLUMN_INDEX:
					return ((Table) element).getName();
			}
			throw new IllegalArgumentException("invalid column index: " + columnIndex);// $NON-NLS-1$
		}
	}


	private class TableTableContentProvider implements IStructuredContentProvider {

		// ********** constructors **********
		
		TableTableContentProvider() {
			super();
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}

		public void dispose() {}

		public Object[] getElements(Object inputElement) {
			return ((Collection<?>) inputElement).toArray();
		}
	}

	private class TableFilter extends ViewerFilter {

		private String pattern;
		private StringMatcher matcher;
		private static final String ALL = "*"; //$NON-NLS-1$

		/**
		 * @see org.eclipse.jface.viewers.ViewerFilter#select(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
		 */
		@Override
		public boolean select (Viewer viewer, Object parentElement, Object element) {
			Table table = (Table) element;
			if (pattern == null || pattern.length() == 0) {
				return true;
			}
			// if a table is previously selected, it will show up 
			// in the viewer along with the list of tables filtered out
			if (tableTable.getChecked(table)) {
				return true;
			}
			if (matcher.match(table.getName())) {
				return true;
			}
			return false;
		}

		/**
		 * Set the pattern to filter out tables for the table viewer
		 * <p>
		 * The following characters have special meaning:
		 *   ? => any character
		 *   * => any string
		 * </p>
		 * @param newPattern the new search pattern
		 */
		protected void setPattern(String newPattern) {
			if (newPattern == null || newPattern.trim().length() == 0) {
				matcher = new StringMatcher(ALL, true, false);
			} else {
				pattern = newPattern + ALL; 
				matcher = new StringMatcher(pattern, true, false);
			}
		}
	}
}
