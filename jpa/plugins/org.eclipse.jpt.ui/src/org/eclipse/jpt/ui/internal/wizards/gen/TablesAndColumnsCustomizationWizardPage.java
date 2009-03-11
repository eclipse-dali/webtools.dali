/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.ui.internal.wizards.gen;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.ui.wizards.NewTypeWizardPage;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.gen.internal2.ORMGenColumn;
import org.eclipse.jpt.gen.internal2.ORMGenCustomizer;
import org.eclipse.jpt.gen.internal2.ORMGenTable;
import org.eclipse.jpt.gen.internal2.util.DTPUtil;
import org.eclipse.jpt.ui.CommonImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.PlatformUI;

public class TablesAndColumnsCustomizationWizardPage extends NewTypeWizardPage {

	@SuppressWarnings("unused")
	private JpaProject jpaProject;

	private TreeViewer tableColumnTreeViewer ;
	
	private Composite detailPanel ;
	private StackLayout detailPanelStatckLayout ;
	private Composite tableGenDetatilGroup;
	private ColumnGenPanel columnGenPanel;
	private Composite columnGenDetatilGroup;
	private TableGenPanel tableGenPanel;
	private ORMGenTable selectedTable;
	
	private ORMGenCustomizer customizer;
	
	protected TablesAndColumnsCustomizationWizardPage(JpaProject jpaProject ) {
		super(true, "TablesAndColumnsCustomizationWizardPage"); //$NON-NLS-1$
		this.jpaProject = jpaProject;
		setTitle( JptUiEntityGenMessages.GenerateEntitiesWizard_tablesAndColumnsPage_title );
		setMessage( JptUiEntityGenMessages.GenerateEntitiesWizard_tablesAndColumnsPage_desc);
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
		int nColumns= 1	;
		GridLayout layout = new GridLayout();
		layout.numColumns = nColumns;
		composite.setLayout(layout);
		//PlatformUI.getWorkbench().getHelpSystem().setHelp(composite, JpaHelpContextIds.DIALOG_GENERATE_ENTITIES);

		createTableAndColumnsListPanel(composite, 1);
		
		SWTUtil.createLabel( composite, 1, ""); //$NON-NLS-1$

		SWTUtil.createSeparator(composite, 1);
		
		createGenerateDetailGroup(composite, 1);

		setControl(composite);
		this.setPageComplete( true );
	}
	
	/**
	 * A panel with JFace TreeViewer showing tables and columns to be generated into JPA entities
	 * 
	 * @param parent
	 * @param columns
	 */
	private void createTableAndColumnsListPanel(Composite parent, int columns) {
		Label label = new Label(parent, columns );
		label.setText( JptUiEntityGenMessages.GenerateEntitiesWizard_tablesAndColumnsPage_labelTableAndColumns );
		SWTUtil.fillColumns( label , columns);
		
		GridData data = new GridData();
		data.horizontalSpan = columns;
		data.verticalAlignment = SWT.FILL;
		data.horizontalAlignment = SWT.FILL;
		data.grabExcessHorizontalSpace = true;
		data.heightHint = 200;
		data.grabExcessVerticalSpace = true;
		
		tableColumnTreeViewer = new TreeViewer(parent);
		tableColumnTreeViewer.getTree().setLayoutData( data);		
		tableColumnTreeViewer.setContentProvider(new TableColumnTreeContentProvider());
		tableColumnTreeViewer.setLabelProvider(new TableColumnTreeLabelProvider());
		
		tableColumnTreeViewer.addSelectionChangedListener( new ISelectionChangedListener(){
			public void selectionChanged(SelectionChangedEvent event) {
				updateDetailPanel(event.getSelection());
			}

		});
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if(visible){
			ORMGenCustomizer customizer = getCustomizer();
			//If user changed the connection or schema
			if( this.customizer != customizer ){
				this.customizer = customizer; 
				tableColumnTreeViewer.setInput( customizer );
			}else{
				tableColumnTreeViewer.refresh();
			}
			List<String> tableNames = this.customizer.getTableNames();
			
			//Select the first table
			ORMGenTable ormGenTable = this.customizer.getTable(tableNames.get(0));
			updateTabelGenDetail( ormGenTable );
		}
	}


	private void updateDetailPanel(ISelection selection) {
		TreeSelection ts = (TreeSelection)selection;
		Object selectedObject = ts.getFirstElement();
		if( selectedObject instanceof ORMGenTable ){
			updateTabelGenDetail( (ORMGenTable)selectedObject );
		}else if( selectedObject instanceof ORMGenColumn ){
			updateColumnGenDetail( (ORMGenColumn)selectedObject );
		}
	}			
	
	private void updateColumnGenDetail(ORMGenColumn column) {
		if(columnGenDetatilGroup==null){
			columnGenDetatilGroup = new Composite(detailPanel, SWT.NONE);
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 4;
			columnGenDetatilGroup.setLayout(gridLayout);
			this.columnGenPanel = new ColumnGenPanel(columnGenDetatilGroup, 4, getCustomizer() , this );
		}
		columnGenPanel.setColumn(column);
		this.detailPanelStatckLayout.topControl = columnGenDetatilGroup;
		this.detailPanel.layout();		
		detailPanel.getParent().layout();
	}

	private void updateTabelGenDetail(ORMGenTable table) {
		this.selectedTable = table;
		if(tableGenDetatilGroup==null){
			tableGenDetatilGroup = new Composite(detailPanel, SWT.NONE);
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 4;
			tableGenDetatilGroup.setLayout(gridLayout);
			
			this.tableGenPanel = new TableGenPanel(tableGenDetatilGroup, 4 , false, this );
			createDomainJavaClassesPropertiesGroup(tableGenDetatilGroup, 4 );
		}
		tableGenPanel.setORMGenTable(table);
		
		this.detailPanelStatckLayout.topControl = tableGenDetatilGroup;
		this.detailPanel.layout();		

		String baseClass = table.getExtends();
		if( baseClass!= null )
			setSuperClass(baseClass, true);
		
		setSuperInterfaces( table.getImplements(), true);
		
		detailPanel.getParent().layout();
	}
	
	protected void createDomainJavaClassesPropertiesGroup(Composite composite, int columns) {
		Group parent = new Group( composite, SWT.NONE);
		parent.setText( JptUiEntityGenMessages.GenerateEntitiesWizard_defaultTablePage_domainJavaClass );
		parent.setLayout(new GridLayout(columns, false));
		SWTUtil.fillColumns( parent, columns);

		createSuperClassControls(parent, columns);
		createSuperInterfacesControls(parent, columns);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.ui.wizards.NewTypeWizardPage#superClassChanged()
	 */
	protected IStatus superClassChanged() {
		IStatus status = super.superClassChanged();
		String baseClass = getSuperClass();
		if(baseClass!=null && this.selectedTable!=null ){
			String oldBaseClass = this.selectedTable.getExtends();
			if( !oldBaseClass.equals(baseClass ))
				this.selectedTable.setExtends(baseClass);
		}
		return status; 
	}	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.ui.wizards.NewTypeWizardPage#addSuperInterface(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public boolean addSuperInterface(String superInterface) {
		super.addSuperInterface(superInterface);
		List interfaces = getSuperInterfaces();
		if(this.selectedTable!=null)
			this.selectedTable.setImplements(interfaces);
		return true;
	}
	
	protected void handleFieldChanged(String fieldName) {
		super.handleFieldChanged(fieldName);
		if( this.fSuperClassStatus.matches(IStatus.ERROR)){
			updateStatus(fSuperClassStatus);
		}else{
			setMessage("", IMessageProvider.NONE);
			setErrorMessage(null);
		}
		
	}	
	
	private ORMGenCustomizer getCustomizer(){
		GenerateEntitiesFromSchemaWizard wizard = (GenerateEntitiesFromSchemaWizard) this.getWizard();
		return wizard.getCustomizer();
	}
	/**
	 * Content provider, and label provider for the DB Table/Column TreeViewer
	 *
	 */
	class TableColumnTreeContentProvider implements ITreeContentProvider {
		public Object[] getElements(Object inputElement) {
			if( inputElement instanceof ORMGenCustomizer ){
				ORMGenCustomizer input = (ORMGenCustomizer )inputElement;
				List<String> tableNameList = input.getGenTableNames();
				List<ORMGenTable> ret = new ArrayList<ORMGenTable>();
				for(String t : tableNameList){
					ORMGenTable ormGenTable = getCustomizer().getTable( t );
					ret.add( ormGenTable );
				}
				return ret.toArray();
			}
			return new Object[]{};
		}
		public Object[] getChildren(Object parentElement) {
			if( parentElement instanceof ORMGenTable ){
				ORMGenTable table = (ORMGenTable) parentElement;
				List<ORMGenColumn> columns = table.getColumns();
				List<ORMGenColumn> ret = new ArrayList<ORMGenColumn>();
				boolean isCompositePk = DTPUtil.getPrimaryKeyColumnNames(table.getDbTable()).size()>1;
				for( ORMGenColumn col : columns){
					if( col.isForeignKey() )
						continue;
					if( col.isPrimaryKey() && isCompositePk ){
						continue;
					}
					ret.add(col);
				}
				return ret.toArray();
			}
			return new Object[]{};
		}
		public Object getParent(Object element) { 
			if( element instanceof ORMGenColumn){
				return null;
			}
			return null;
		}
		public boolean hasChildren(Object element) {
			return( element instanceof ORMGenTable );
		}
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}
		public void dispose() {}
	}

	class TableColumnTreeLabelProvider extends LabelProvider{

		public Image getImage(Object element) {
			if( element instanceof ORMGenTable ){
				return CommonImages.createImage( CommonImages.TABLE_IMAGE );
			}else 	if( element instanceof ORMGenColumn ){
				ORMGenColumn col = ( ORMGenColumn)element;
				if( col.isPrimaryKey() )
					return CommonImages.createImage( CommonImages.COLUMN_KEY_IMAGE);
				return CommonImages.createImage( CommonImages.COLUMN_IMAGE);
			}
			return null;
		}
		

		public String getText(Object element) {
			if( element instanceof ORMGenTable ){
				return ((ORMGenTable)element).getName();
			}else 			if( element instanceof ORMGenColumn ){
				return ((ORMGenColumn)element).getName();
			}
			return super.getText(element);
		}		
	}
	
	private void createGenerateDetailGroup(Composite parent, int columns) {
		detailPanel = new Composite(parent, SWT.NONE);
		SWTUtil.fillColumns( detailPanel, columns);
		  
		detailPanelStatckLayout = new StackLayout();
		detailPanel.setLayout(detailPanelStatckLayout);
		  
		Composite emptyPanel = new Composite(detailPanel, SWT.NONE);
		emptyPanel.setLayoutData(new GridData());

		detailPanelStatckLayout.topControl = emptyPanel;
		detailPanel.layout();
	}

    @Override
    public final void performHelp() 
    {
        PlatformUI.getWorkbench().getHelpSystem().displayHelp( GenerateEntitiesFromSchemaWizard.HELP_CONTEXT_ID );
    }
}
