/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/   
package org.eclipse.jpt.ui.internal.wizards;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.internal.ui.util.SWTUtil;
import org.eclipse.jdt.internal.ui.util.TableLayoutComposite;
import org.eclipse.jdt.ui.wizards.NewTypeWizardPage;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jpt.db.internal.Table;
import org.eclipse.jpt.gen.internal.EntityGenerator;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.PlatformUI;

// TODO determine name collisions
class GenerateEntitiesWizardPage extends NewTypeWizardPage {

	CheckboxTableViewer tableTable;

	private boolean convertToCamelCase = true;
	private boolean fieldAccessType = true;
	private String collectionTypeName = Set.class.getName();
	private int fieldVisibility = EntityGenerator.Config.PRIVATE;
	private int methodVisibility = EntityGenerator.Config.PUBLIC;
	private boolean generateGettersAndSetters = true;
	private boolean generateDefaultConstructor = true;
	private boolean serializable = true;
	private boolean generateSerialVersionUID = true;
	private boolean generateEmbeddedIdForCompoundPK = true;
	private Map overrideEntityNames;
	
	static final String[] TABLE_TABLE_COLUMN_PROPERTIES = { "table", "entityName" };
	private static final int TABLE_COLUMN_INDEX = 0;
	private static final int ENTITY_NAME_COLUMN_INDEX = 1;
	

	GenerateEntitiesWizardPage() {
		super(true, "Generate Entities"); //$NON-NLS-1$
		setTitle(JptUiMessages.GenerateEntitiesWizardPage_generateEntities);
		setMessage(JptUiMessages.GenerateEntitiesWizardPage_chooseEntityTable);
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
		IJavaElement jelem= getInitialJavaElement(selection);
		initContainerPage(jelem);
		initTypePage(jelem);
		doStatusUpdate();
	}

	public void createControl(Composite parent) {
		Composite composite = new Composite(parent, SWT.NULL);
		int nColumns= 4;
		GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		composite.setLayout(layout);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, IJpaHelpContextIds.DIALOG_GENERATE_ENTITIES);
		
		createContainerControls(composite, nColumns);	
		createPackageControls(composite, nColumns);	
		
		Group tablesGroup = new Group(composite, SWT.SHADOW_ETCHED_IN);
		tablesGroup.setLayout(new GridLayout(2, false));
		tablesGroup.setText(JptUiMessages.GenerateEntitiesWizardPage_tables);
		GridData data = new GridData();
		data.horizontalSpan = 4;
		data.verticalAlignment = SWT.FILL;
		data.horizontalAlignment = SWT.FILL;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		tablesGroup.setLayoutData(data);
		
		createTablesSelectionControl(tablesGroup);
		createButtonComposite(tablesGroup);
		
		GenerateEntitiesWizard generateEntitiesWizard = ((GenerateEntitiesWizard)this.getWizard());
		Collection possibleTables = generateEntitiesWizard.getPossibleTables();
		initTablesSelectionControl(possibleTables);
		
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this.tableTable.getControl(), IJpaHelpContextIds.DIALOG_GENERATE_ENTITIES_TABLES);
		
		setControl(composite);
		this.setPageComplete( false);
	}

	private void selectAllTables(){
		this.tableTable.setAllChecked(true);
		doStatusUpdate();
	}
	
	private void deselectAllTables(){
		this.tableTable.setAllChecked(false);
		doStatusUpdate();
	}
	
	private void initTablesSelectionControl(Collection possibleTables) {
		this.overrideEntityNames = new HashMap(possibleTables.size());
		this.tableTable.setInput(possibleTables);
	}

	private void createTablesSelectionControl(Composite parent) {
		TableLayoutComposite layout= new TableLayoutComposite(parent, SWT.NONE);
		addColumnLayoutData(layout);
		
		final org.eclipse.swt.widgets.Table table = new org.eclipse.swt.widgets.Table(layout, SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER | SWT.CHECK);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableNameColumn = new TableColumn(table, SWT.NONE, TABLE_COLUMN_INDEX);
		tableNameColumn.setText("Table");
		tableNameColumn.setResizable(true);

		TableColumn entityNameColumn = new TableColumn(table, SWT.NONE, ENTITY_NAME_COLUMN_INDEX);
		entityNameColumn.setText("Entity Name");
		entityNameColumn.setResizable(true);
		
		GridData gd= new GridData(GridData.FILL_BOTH);
		gd.heightHint= SWTUtil.getTableHeightHint(table, 20);
		gd.widthHint = 600;
		layout.setLayoutData(gd);

		this.tableTable = new CheckboxTableViewer(table);
		this.tableTable.setUseHashlookup(true);
		this.tableTable.setLabelProvider(this.buildTableTableLabelProvider());
		this.tableTable.setContentProvider(this.buildTableTableContentProvider());
		this.tableTable.setSorter(new ViewerSorter() {
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
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.F2 && e.stateMask == SWT.NONE) {
					editEntityNameIfPossible();
					e.doit= false;
				}
			}
		});
		
		this.addCellEditors();
	}
	
	private void createButtonComposite(Group tablesGroup){
		
		Composite buttonComposite = new Composite(tablesGroup, SWT.NULL);
		GridLayout buttonLayout = new GridLayout(1, false);
		buttonComposite.setLayout(buttonLayout);
		GridData data =  new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.BEGINNING;
		buttonComposite.setLayoutData(data);
		
		Button selectAllButton = new Button(buttonComposite, SWT.PUSH);
		selectAllButton.setText("Select All");
		GridData gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		selectAllButton.setLayoutData(gridData);
		selectAllButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}
		
			public void widgetSelected(SelectionEvent e) {
				selectAllTables();
				
			}
		});
		
		Button deselectAllButton = new Button(buttonComposite, SWT.PUSH);
		deselectAllButton.setText("Deselect All");
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		deselectAllButton.setLayoutData(gridData);
		deselectAllButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}
		
			public void widgetSelected(SelectionEvent e) {
				deselectAllTables();
			}
		});
	}
	
	
	private void addColumnLayoutData(TableLayoutComposite layout) {
		layout.addColumnData(new ColumnWeightData(50, true));
		layout.addColumnData(new ColumnWeightData(50, true));
	}

	void editEntityNameIfPossible(){
		Object[] selected = ((IStructuredSelection) this.tableTable.getSelection()).toArray();
		if (selected.length != 1) {
			return;
		}
		this.tableTable.editElement(selected[0], ENTITY_NAME_COLUMN_INDEX);
	}
	
	private void addCellEditors() {
		this.tableTable.setColumnProperties(TABLE_TABLE_COLUMN_PROPERTIES);
		
		TextCellEditor[] editors = new TextCellEditor[TABLE_TABLE_COLUMN_PROPERTIES.length];
		editors[ENTITY_NAME_COLUMN_INDEX]= new TextCellEditor(this.tableTable.getTable(), SWT.SINGLE);
		
		this.tableTable.setCellEditors(editors);
		this.tableTable.setCellModifier(this.buildTableTableCellModifier());
	}

	void handleTablesListSelectionChanged(SelectionChangedEvent event) {
		this.setPageComplete(true);
		if ( ! this.hasTablesSelected()) {
			this.setPageComplete(false);
		}
	}
	
	private IBaseLabelProvider buildTableTableLabelProvider() {
		return new TableTableLabelProvider();
	}
	
	private IContentProvider buildTableTableContentProvider() {
		return new TableTableContentProvider();
	}
	
	private ICellModifier buildTableTableCellModifier() {
		return new TableTableCellModifier();
	}
	
	Collection getSelectedTables() {
		return Arrays.asList(this.tableTable.getCheckedElements());
	}
	
	private boolean hasTablesSelected() {
		return (this.tableTable != null) ? (this.getSelectedTables().size() > 0) : false;
	}
	
	void updateTablesListViewer(Collection possibleTables) {
		if (this.tableTable != null) {
			this.initTablesSelectionControl(possibleTables);
		}
	}

	private void doStatusUpdate() {
		// status of all used components
		IStatus[] status= new IStatus[] {
			fContainerStatus,
			fPackageStatus
		};
		// the mode severe status will be displayed and the OK button enabled/disabled.
		this.updateStatus(status);
	}
	
	/**
	 * Update the status line and the OK button according to the given status
	 */
	protected void updateStatus(IStatus status) {
		super.updateStatus(status);
		if (this.isPageComplete() && ! this.hasTablesSelected()) {
			this.setPageComplete(false);
		}
	}

	String entityName(Table table) {
		String overrideEntityName = (String) this.overrideEntityNames.get(table);
		return (overrideEntityName != null) ? overrideEntityName : this.defaultEntityName(table);
	}

	private String defaultEntityName(Table table) {
		String entityName = table.shortJavaClassName();
		if (this.convertToCamelCase) {
			entityName = StringTools.convertUnderscoresToCamelCase(entityName);
		}
		return entityName;
	}

	void setOverrideEntityName(Table table, String name) {
		if (table.shortJavaClassName().equals(name)) {
			this.overrideEntityNames.remove(table);
		} else {
			this.overrideEntityNames.put(table, name);
		}
	}

	boolean convertToCamelCase() {
		return this.convertToCamelCase;
	}
	private void setConvertToCamelCase(boolean convertToCamelCase) {
		// TODO re-calculate the default entity names if this changes
		this.convertToCamelCase = convertToCamelCase;
	}

	boolean fieldAccessType() {
		return this.fieldAccessType;
	}
	private void setFieldAccessType(boolean fieldAccessType) {
		this.fieldAccessType = fieldAccessType;
	}

	String getCollectionTypeName() {
		return this.collectionTypeName;
	}
	private void setCollectionTypeName(String collectionTypeName) {
		this.collectionTypeName = collectionTypeName;
	}

	int getFieldVisibility() {
		return this.fieldVisibility;
	}
	private void setFieldVisibility(int fieldVisibility) {
		this.fieldVisibility = fieldVisibility;
	}

	int getMethodVisibility() {
		return this.methodVisibility;
	}
	private void setMethodVisibility(int methodVisibility) {
		this.methodVisibility = methodVisibility;
	}

	boolean generateGettersAndSetters() {
		return this.generateGettersAndSetters;
	}
	private void setGenerateGettersAndSetters(boolean generateGettersAndSetters) {
		this.generateGettersAndSetters = generateGettersAndSetters;
	}

	boolean generateDefaultConstructor() {
		return this.generateDefaultConstructor;
	}
	private void setGenerateDefaultConstructor(boolean generateDefaultConstructor) {
		this.generateDefaultConstructor = generateDefaultConstructor;
	}

	boolean serializable() {
		return this.serializable;
	}
	private void setSerializable(boolean serializable) {
		this.serializable = serializable;
	}

	boolean generateSerialVersionUID() {
		return this.generateSerialVersionUID;
	}
	private void setGenerateSerialVersionUID(boolean generateSerialVersionUID) {
		this.generateSerialVersionUID = generateSerialVersionUID;
	}

	boolean generateEmbeddedIdForCompoundPK() {
		return this.generateEmbeddedIdForCompoundPK;
	}
	private void setGenerateEmbeddedIdForCompoundPK(boolean generateEmbeddedIdForCompoundPK) {
		this.generateEmbeddedIdForCompoundPK = generateEmbeddedIdForCompoundPK;
	}

	/**
	 * key = table
	 * value = override entity name
	 */
	Map getOverrideEntityNames() {
		return this.overrideEntityNames;
	}


	// ********** inner classes **********

	private class TableTableLabelProvider extends LabelProvider implements ITableLabelProvider {

		TableTableLabelProvider() {
			super();
		}

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

				case ENTITY_NAME_COLUMN_INDEX:
					return GenerateEntitiesWizardPage.this.entityName((Table) element);
			}
			throw new IllegalArgumentException("invalid column index: " + columnIndex);
		}

	}


	private class TableTableContentProvider implements IStructuredContentProvider {

		TableTableContentProvider() {
			super();
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// do nothing
		}
	
		public void dispose() {
			// do nothing
		}
	
		public Object[] getElements(Object inputElement) {
			return ((Collection) inputElement).toArray();
		}

	}


	private class TableTableCellModifier implements ICellModifier {

		TableTableCellModifier() {
			super();
		}

		public boolean canModify(Object element, String property) {
			return property.equals(TABLE_TABLE_COLUMN_PROPERTIES[ENTITY_NAME_COLUMN_INDEX]);
		}

		public Object getValue(Object element, String property) {
			if (property.equals(TABLE_TABLE_COLUMN_PROPERTIES[ENTITY_NAME_COLUMN_INDEX])) {
				return GenerateEntitiesWizardPage.this.entityName((Table) element);
			}
			return null;
		}

		public void modify(Object element, String property, Object value) {
			if (element instanceof TableItem) {
				element= ((TableItem) element).getData();
			}
			if ( ! (element instanceof Table)) {
				return;
			}

			boolean unchanged = false;
			Table table = (Table) element;
			if (property.equals(TABLE_TABLE_COLUMN_PROPERTIES[ENTITY_NAME_COLUMN_INDEX])) {
				unchanged = GenerateEntitiesWizardPage.this.entityName(table).equals(value);
				GenerateEntitiesWizardPage.this.setOverrideEntityName(table, (String) value);
			}
			if (! unchanged) {
				GenerateEntitiesWizardPage.this.tableTable.update(table, new String[] { property });
			}
		}

	}

}
