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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.dialogs.IPageChangedListener;
import org.eclipse.jface.dialogs.PageChangedEvent;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.IBaseLabelProvider;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.Table;
import org.eclipse.jpt.gen.internal.Association;
import org.eclipse.jpt.gen.internal.ORMGenCustomizer;
import org.eclipse.jpt.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.ui.internal.util.SWTUtil;
import org.eclipse.jpt.ui.internal.util.TableLayoutComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;


public class JoinColumnsPage extends NewAssociationWizardPage {

	private Label joinColumnsDescLabel1;
	/*the table containing the association columns between table1 and table2
	 * , or table1 and join table if many to many*/
	private TableViewer joinColumnsTable1;
	private ArrayList<SimpleJoin> tableDataModel1 = new ArrayList<SimpleJoin>();  
	private Composite tablesGroup1;
	
	private Label joinColumnsDescLabel2;
	/*the table containing the association columns between join table and table2
	 * if many to many*/
	private TableViewer joinColumnsTable2;
	private ArrayList<SimpleJoin> tableDataModel2 = new ArrayList<SimpleJoin>();  
	private Composite tablesGroup2;
	
	static final String[] JOINCOLUMNS_TABLE_COLUMN_PROPERTIES = { "referrerColumn", "referencedColumn" };
	
	private static final int JOINCOLUMN1_COLUMN_INDEX = 0;
	private static final int JOINCOLUMN2_COLUMN_INDEX = 1;

	protected JoinColumnsPage(ORMGenCustomizer customizer ) {
		super(customizer, "JoinColumnsPage");
		setTitle( JptUiEntityGenMessages.GenerateEntitiesWizard_newAssoc_colsPage_title);
		setDescription(JptUiEntityGenMessages.GenerateEntitiesWizard_newAssoc_colsPage_desc);
	}

	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		composite.setLayout(layout);
		this.getHelpSystem().setHelp(composite, JpaHelpContextIds.GENERATE_ENTITIES_WIZARD_JOIN_COLUMNS);

		tablesGroup1 = new Composite(composite, SWT.SHADOW_ETCHED_IN);
		tablesGroup1.setLayoutData(new GridData());
		tablesGroup1.setLayout(new GridLayout(2, false));
		createJoinColumnsTableControl1(tablesGroup1);

		//createMtmJoinColumnsTable2(composite);
		
		setControl(composite);
		this.setPageComplete( false);
		
		((WizardDialog)getContainer()).addPageChangedListener(new IPageChangedListener(){
			public void pageChanged(PageChangedEvent event) {
				if( event.getSelectedPage() == JoinColumnsPage.this ){
					((Composite)JoinColumnsPage.this.getControl()).getParent().layout() ;		
					
				}
			}			
		});
	}

	private void createMtmJoinColumnsTable2(Composite composite) {
		tablesGroup2 = new Composite(composite, SWT.SHADOW_ETCHED_IN);
		tablesGroup2.setLayoutData(new GridData());
		tablesGroup2.setLayout(new GridLayout(2, false));
		createJoinColumnsTableControl2(tablesGroup2);
	}

	/**
	 * Update wizard page UI with new table names
	 */
	public void updateWithNewTables() {
		String cardinality = this.getCardinality() ;
		if( Association.MANY_TO_MANY.equals( cardinality ) ){
			updateWithMtmTables();
		}else{
			updateWithOtmTables();
		}
	}

	/**
	 * Update Wizard UI with a single TableViewer with columns from the two associated database tables
	 */
	public void updateWithOtmTables() {
		TableColumn[] columns = joinColumnsTable1.getTable().getColumns();
		String table1Name = this.getReferrerTableName() ;
		String table2Name = this.getReferencedTableName() ;
		
		if( table1Name ==null || table2Name == null )
			return;
		
		columns[0].setText( table1Name );
		columns[1].setText( table2Name );

		//Hide the Join column table 2
		if( tablesGroup2 !=null )
			tablesGroup2.setVisible(false);
		
		tableDataModel1.clear();
		joinColumnsTable1.refresh();
		
		String msg = String.format(JptUiEntityGenMessages.GenerateEntitiesWizard_newAssoc_colsPage_label, table1Name, table2Name); 
		joinColumnsDescLabel1.setText(msg);
		joinColumnsDescLabel1.setToolTipText( msg );
		tablesGroup1.layout();
		
		String[] referrerColumnValues = getTableColumns(table1Name);
		String[] referencedColumnValues = getTableColumns(table2Name);
		
		updateCellEditors(joinColumnsTable1, referrerColumnValues, referencedColumnValues);		

		
		((Composite)this.getControl()).layout() ;
	}

	/**
	 * Update Wizard UI with a two TableViewers with the first with columns from table1 to the MTM join table
	 * and the second one with columns from the MTM join table to table2
	 */
	public void updateWithMtmTables() {
		TableColumn[] columns = joinColumnsTable1.getTable().getColumns();
		String table1Name = this.getReferrerTableName() ;
		String table2Name = this.getReferencedTableName() ;
		String joinTableName = this.getJoinTableName() ;
		if( table1Name==null || table2Name==null || joinTableName==null ){
			return;
		}
		if( tablesGroup2 == null ){
			createMtmJoinColumnsTable2( tablesGroup1.getParent());
		} 

		columns[0].setText( table1Name==null?"":table1Name );
		columns[1].setText( table2Name==null?"":joinTableName );
		
		//Update join column TableViewer 1
		tableDataModel1.clear();
		joinColumnsTable1.refresh();
		
		String msg = String.format(JptUiEntityGenMessages.GenerateEntitiesWizard_newAssoc_colsPage_label, table1Name, joinTableName); 
		joinColumnsDescLabel1.setText(msg);
		joinColumnsDescLabel1.setToolTipText( msg );
		String[] referrerColumnValues = getTableColumns(table1Name);
		String[] referencedColumnValues = getTableColumns(joinTableName);
		
		updateCellEditors(joinColumnsTable1, referrerColumnValues, referencedColumnValues );

		//Update join column TableViewer 2
		columns = joinColumnsTable2.getTable().getColumns();
		columns[0].setText( joinTableName==null?"":joinTableName );
		columns[1].setText( table2Name==null?"":table2Name );
		tablesGroup1.layout();

		tableDataModel2.clear();
		joinColumnsTable2.refresh();
		msg = String.format(JptUiEntityGenMessages.GenerateEntitiesWizard_newAssoc_colsPage_label, joinTableName, table2Name); 
		joinColumnsDescLabel2.setText(msg);
		joinColumnsDescLabel2.setToolTipText( msg );
		referrerColumnValues = getTableColumns(joinTableName);
		referencedColumnValues = getTableColumns(table2Name);
		updateCellEditors(joinColumnsTable2, referrerColumnValues, referencedColumnValues );
		
		tablesGroup2.layout();

		//Show the Join column TableViewer 2
		tablesGroup2.setVisible(true);
		
		
		((Composite)this.getControl()).layout(new Control[]{this.tablesGroup1, this.tablesGroup2});		
	}
	
	
	private void createAddRemoveButtonComposite(Composite tablesGroup, final TableViewer joinColumnsTable,  final ArrayList<SimpleJoin> tableDataModel) {
		//Add and Remove JoinColumns buttons
		Composite buttonComposite = new Composite(tablesGroup, SWT.NULL);
		GridLayout buttonLayout = new GridLayout(1, false);
		buttonComposite.setLayout(buttonLayout);
		GridData data =  new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.BEGINNING;
		buttonComposite.setLayoutData(data);
		
		Button addButton = new Button(buttonComposite, SWT.PUSH);
		addButton.setText( JptUiEntityGenMessages.add );
		GridData gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		addButton.setLayoutData(gridData);
		addButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
		
			@SuppressWarnings("unchecked")
			public void widgetSelected(SelectionEvent e) {
				
				SimpleJoin join = getDefaultNewJoin(joinColumnsTable);
				tableDataModel.add(join);
				joinColumnsTable.refresh();
				
				//Update Wizard model
				TreeMap<String, String> joins = null;
				if( joinColumnsTable == joinColumnsTable1 ){
					joins = (TreeMap<String, String>)getWizardDataModel().get(NewAssociationWizard.ASSOCIATION_JOIN_COLUMNS1 );
				}else{
					joins = (TreeMap<String, String>)getWizardDataModel().get(NewAssociationWizard.ASSOCIATION_JOIN_COLUMNS2 );
				}
				joins.put( join.foreignKey, join.primaryKey);
				
				updatePageComplete();
			}
		});
		
		Button removeButton = new Button(buttonComposite, SWT.PUSH);
		removeButton.setText( JptUiEntityGenMessages.remove );
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		removeButton.setLayoutData(gridData);
		removeButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {}
		
			@SuppressWarnings("unchecked")
			public void widgetSelected(SelectionEvent e) {
				StructuredSelection selection = (StructuredSelection)joinColumnsTable.getSelection();
				if( selection.isEmpty())
					return;
				SimpleJoin join = (SimpleJoin)selection.getFirstElement();

				//Update TableViewer model
				tableDataModel.remove( join );
				//Update Wizard model
				
				TreeMap<String, String> joins = null;
				if( joinColumnsTable == joinColumnsTable1 ){
					joins = (TreeMap<String, String>)getWizardDataModel().get(NewAssociationWizard.ASSOCIATION_JOIN_COLUMNS1 );
				}else{
					joins = (TreeMap<String, String>)getWizardDataModel().get(NewAssociationWizard.ASSOCIATION_JOIN_COLUMNS2 );
				}
				joins.remove(join.foreignKey);

				joinColumnsTable.refresh();
			}
		});
		
		addButton.setFocus();
		
	}

	protected SimpleJoin getDefaultNewJoin(TableViewer joinColumnsTable) {
		String table1Name = "";
		String table2Name = "";

		TreeMap<String, String> existingJoins = null;
		if( joinColumnsTable == this.joinColumnsTable1 ){
			existingJoins = (TreeMap<String, String>)getWizardDataModel().get(NewAssociationWizard.ASSOCIATION_JOIN_COLUMNS1 );
			if( this.getJoinTableName() == null) {
				table1Name = this.getReferrerTableName();
				table2Name = this.getReferencedTableName() ;
			}else{
				table1Name = this.getReferrerTableName();
				table2Name = this.getJoinTableName()  ;
			}
		}else{
			existingJoins = (TreeMap<String, String>)getWizardDataModel().get(NewAssociationWizard.ASSOCIATION_JOIN_COLUMNS2 );
			table1Name = this.getJoinTableName();
			table2Name = this.getReferencedTableName() ;
		}
		//find next available column
		String[] table1ColumnValues = getTableColumns(table1Name);
		String nextCol1 = "";
		for( String s: table1ColumnValues ){
			if( !existingJoins.keySet().contains(s)){
				nextCol1 = s;
				break;
			}
		}
		
		String[] table2ColumnValues = getTableColumns(table2Name);
		String nextCol2 = "";
		for( String s: table2ColumnValues ){
			if( !existingJoins.values().contains(s)){
				nextCol2 = s;
				break;
			}
		}
		return new SimpleJoin( nextCol1, nextCol2);
	}

	public boolean canFlipToNextPage() {
		return isPageComplete();
	}
	
	public void updatePageComplete() {
		boolean ret = tableDataModel1.size()> 0 ;
		setPageComplete( ret );
	}	
	
	private Label createLabel(Composite container, int span, String text) {
		Label label = new Label(container, SWT.NONE);
		label.setText(text);
		GridData gd = new GridData();
		gd.horizontalSpan = span;
		label.setLayoutData(gd);
		return label;
	}

	private void createJoinColumnsTableControl1(Composite tablesGroup) {
		joinColumnsDescLabel1 = createLabel(tablesGroup, 2, JptUiEntityGenMessages.GenerateEntitiesWizard_newAssoc_colsPage_label );
		joinColumnsTable1 = createJoinColumnsTableControl(tablesGroup, this.tableDataModel1);
		createAddRemoveButtonComposite(tablesGroup, joinColumnsTable1, tableDataModel1);
	}
	
	private void createJoinColumnsTableControl2(Composite tablesGroup) {
		joinColumnsDescLabel2 = createLabel(tablesGroup, 2, JptUiEntityGenMessages.GenerateEntitiesWizard_newAssoc_colsPage_label );
		joinColumnsTable2 = createJoinColumnsTableControl(tablesGroup, this.tableDataModel2);
		createAddRemoveButtonComposite(tablesGroup, joinColumnsTable2, tableDataModel2);
	}
	
	private TableViewer createJoinColumnsTableControl(Composite parent, ArrayList<SimpleJoin> tableDataModel ){	
	
		TableLayoutComposite layout= new TableLayoutComposite(parent, SWT.NONE);
		addColumnLayoutData(layout);
		
		final org.eclipse.swt.widgets.Table table = new org.eclipse.swt.widgets.Table(layout, SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER );
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn referrerColumn = new TableColumn(table, SWT.NONE, JOINCOLUMN1_COLUMN_INDEX);
		referrerColumn.setText("%table1");
		referrerColumn.setResizable(true);

		TableColumn referencedColumn = new TableColumn(table, SWT.NONE, JOINCOLUMN2_COLUMN_INDEX);
		referencedColumn.setText("%table2");
		referencedColumn.setResizable(true);
		
		GridData gd= new GridData(GridData.FILL_BOTH);
		gd.heightHint= SWTUtil.getTableHeightHint(table, 3);
		gd.widthHint = 300;
		layout.setLayoutData(gd);

		TableViewer newJoinColumnsTable = new TableViewer(table);
		newJoinColumnsTable.setUseHashlookup(true);
		newJoinColumnsTable.setLabelProvider(this.buildTableTableLabelProvider());
		newJoinColumnsTable.setContentProvider(this.buildTableTableContentProvider());
		newJoinColumnsTable.setSorter(new ViewerSorter() {
			public int compare(Viewer viewer, Object e1, Object e2) {
				return ((SimpleJoin) e1).foreignKey.compareTo(((SimpleJoin) e2).foreignKey);
			}
		});
		
		newJoinColumnsTable.addPostSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				//handleTablesListSelectionChanged(event);
			}
		});
		populateTableDataModel();
		newJoinColumnsTable.setInput( tableDataModel );
		return newJoinColumnsTable;
	}

	@SuppressWarnings("unchecked")
	public void populateTableDataModel(){
		HashMap<String, Object> dataModel = (HashMap<String, Object> )getWizardDataModel();
		TreeMap<String, String> joinColumns = (TreeMap<String, String>)dataModel.get(NewAssociationWizard.ASSOCIATION_JOIN_COLUMNS1);
		if( joinColumns!= null ){
			for( String referrerColumn : joinColumns.keySet() ){
				tableDataModel1.add(new SimpleJoin(referrerColumn, joinColumns.get(referrerColumn) ));
			}
		}
		
		TreeMap<String, String> joinColumns2 = (TreeMap<String, String>)dataModel.get(NewAssociationWizard.ASSOCIATION_JOIN_COLUMNS2);
		if( joinColumns2!= null ){
			for( String referrerColumn : joinColumns2.keySet() ){
				tableDataModel2.add(new SimpleJoin(referrerColumn, joinColumns2.get(referrerColumn) ));
			}
		}
		
	}
	
	private IContentProvider buildTableTableContentProvider() {
		return new JoinColumnsContentProvider();
	}

	
	private IBaseLabelProvider buildTableTableLabelProvider() {
		return new JoinColumnsTableLabelProvider();
	}
	
	
	private void addColumnLayoutData(TableLayoutComposite layout) {
		layout.addColumnData(new ColumnWeightData(50, true));
		layout.addColumnData(new ColumnWeightData(50, true));
	}
	
	
	private void updateCellEditors(TableViewer joinColumnsTable, String[] referrerColumnValues, String[] referencedColumnValues ){
		joinColumnsTable.setColumnProperties(JOINCOLUMNS_TABLE_COLUMN_PROPERTIES);
		ComboBoxCellEditor[] editors = new ComboBoxCellEditor[JOINCOLUMNS_TABLE_COLUMN_PROPERTIES.length];

		editors[JOINCOLUMN1_COLUMN_INDEX]= new ComboBoxCellEditor(joinColumnsTable.getTable(), referrerColumnValues, SWT.SINGLE);
		editors[JOINCOLUMN2_COLUMN_INDEX]= new ComboBoxCellEditor(joinColumnsTable.getTable(), referencedColumnValues, SWT.SINGLE);
		
		joinColumnsTable.setCellEditors(editors);
		joinColumnsTable.setCellModifier(this.buildTableTableCellModifier(joinColumnsTable, referrerColumnValues, referencedColumnValues ));
	}

	public String[] getTableColumns(String tableName){
		Schema schema = (Schema)getWizardDataModel().get(NewAssociationWizard.ASSOCIATION_SCHEMA);
		Table table = schema.getTableNamed(tableName);
		List<String> list = new ArrayList<String>();
		for (Column column : table.getColumns()) {
			list.add(column.getName());
		}
		String[] ret = new String[list.size()];
		list.toArray(ret);
		return ret;
	}
	
	private ICellModifier buildTableTableCellModifier(TableViewer joinColumnsTable, String[] referrerColumnValues, String[] referencedColumnValues) {
		return new JoinColumnsCellModifier(joinColumnsTable, referrerColumnValues, referencedColumnValues);
	}	
	
	/**
	 * A ContentProvider translates the SimpleJoin list into a Collection for display 
	 *
	 */
	private class JoinColumnsContentProvider implements IStructuredContentProvider {

		JoinColumnsContentProvider() {
			super();
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}	
		public void dispose() {}
		public Object[] getElements(Object inputElement) {
			return ((Collection<?>) inputElement).toArray();
		}
	}
	
	/**
	 * Simple value object used as model backing the JFace table 
	 *
	 */
	private class SimpleJoin {
		public SimpleJoin(String foreignKey, String primaryKey) {
			this.foreignKey = foreignKey;
			this.primaryKey = primaryKey; 
		}
		public String foreignKey;
		public String primaryKey;
		
		public String toString(){
			return "["+ this.foreignKey + " = " + this.primaryKey + "]";
		}
	}
	
	/**
	 *	A CellModifier to update the join columns in the wizard data model
	 */
	private class JoinColumnsCellModifier implements ICellModifier {
		private TableViewer joinColumnsTable;
		private String[] referrerColumnValues;
		private String[] referencedColumnValues;
		JoinColumnsCellModifier(TableViewer joinColumnsTable, String[] referrerColumnValues, String[] referencedColumnValues) {
			super();
			this.joinColumnsTable = joinColumnsTable;
			this.referrerColumnValues = referrerColumnValues;
			this.referencedColumnValues = referencedColumnValues;
		}

		public boolean canModify(Object element, String property) {
			return true;
		}

		@SuppressWarnings("unchecked")
		public Object getValue(Object element, String property) {
//			SimpleJoin join = (SimpleJoin) element;
//			if (property.equals(JOINCOLUMNS_TABLE_COLUMN_PROPERTIES[JOINCOLUMN2_COLUMN_INDEX])) {
//				return join.primaryKey;
//			}
//			return join.foreignKey;
			// returnt the index of the value in the ComboxCellEditor
			ArrayList<SimpleJoin> tableDataModel = (ArrayList<SimpleJoin>) joinColumnsTable.getInput();
			for(int i=0; i< tableDataModel.size(); i ++ ){
				if( tableDataModel.get(i) == element )
					return new Integer(i);
			}
			return new Integer(0);
			
		}

		/** 
		 * element is the selected TableItem
		 * value is the selected item index in the comboCellEditor 
		 */
		@SuppressWarnings("unchecked")
		public void modify(Object element, String property, Object value) {
			if ( ! (element instanceof TableItem)) {
				return;
			}
			Integer index = (Integer)value;
			TableItem item = (TableItem)element;
			boolean unchanged = false;
			SimpleJoin join = (SimpleJoin) item.getData();
			if (property.equals(JOINCOLUMNS_TABLE_COLUMN_PROPERTIES[JOINCOLUMN1_COLUMN_INDEX])) {
				unchanged = join.foreignKey.equals( referrerColumnValues[ index.intValue() ] );
				if (! unchanged) {
					
					//update the wizard datamodel
					TreeMap<String, String> joins = null;
					if( joinColumnsTable == joinColumnsTable1 ){
						joins = (TreeMap<String, String>)getWizardDataModel().get(NewAssociationWizard.ASSOCIATION_JOIN_COLUMNS1 );
					}else{
						joins = (TreeMap<String, String>)getWizardDataModel().get(NewAssociationWizard.ASSOCIATION_JOIN_COLUMNS2 );
					}
					joins.remove(join.foreignKey);
					joins.put(referrerColumnValues[ index.intValue() ], join.primaryKey);

					//Update the TableViewer model
					join.foreignKey = referrerColumnValues[ index.intValue()];
					joinColumnsTable.refresh();
				}
				return;
			}
			
			if (property.equals(JOINCOLUMNS_TABLE_COLUMN_PROPERTIES[JOINCOLUMN2_COLUMN_INDEX])) {
				unchanged = join.primaryKey.equals( referencedColumnValues[ index.intValue()] ) ;
				if (! unchanged) {
					//Update the TableViewer model
					join.primaryKey = referencedColumnValues[ index.intValue()] ;
					
					//Update wizard data model
					TreeMap<String, String> joins = null;
					if( joinColumnsTable == joinColumnsTable1 ){
						joins = (TreeMap<String, String>)getWizardDataModel().get(NewAssociationWizard.ASSOCIATION_JOIN_COLUMNS1 );
					}else{
						joins = (TreeMap<String, String>)getWizardDataModel().get(NewAssociationWizard.ASSOCIATION_JOIN_COLUMNS2 );
					}
					joins.put(join.foreignKey, join.primaryKey);

					joinColumnsTable.refresh();
				}
			}
			
			
		}

	}

	/**
	 * A table label provider to return the join column names for display
	 *
	 */
	private final class JoinColumnsTableLabelProvider extends LabelProvider implements ITableLabelProvider {
		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}
		public String getColumnText(Object element, int columnIndex) {
			if( !(element instanceof SimpleJoin) )
				return null;
			switch (columnIndex) {
	            case 0:
	            	return ((SimpleJoin)element).foreignKey;
	            case 1:
	            	return ((SimpleJoin)element).primaryKey;
	            default:
	            	Assert.isTrue(false);
	            	return null;
            }
		}
		public String getText(Object element) {
		    return getColumnText(element, 0); // needed to make the sorter work
		}
	}
	
}
