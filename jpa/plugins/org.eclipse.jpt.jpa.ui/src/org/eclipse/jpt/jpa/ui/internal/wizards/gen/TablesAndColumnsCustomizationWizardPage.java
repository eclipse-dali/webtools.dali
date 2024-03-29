/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.ui.internal.wizards.gen;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.internal.ui.wizards.dialogfields.LayoutUtil;
import org.eclipse.jdt.ui.wizards.NewTypeWizardPage;
import org.eclipse.jface.dialogs.IMessageProvider;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.common.ui.internal.WorkbenchTools;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.gen.internal.ORMGenColumn;
import org.eclipse.jpt.jpa.gen.internal.ORMGenCustomizer;
import org.eclipse.jpt.jpa.gen.internal.ORMGenTable;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.internal.JpaHelpContextIds;
import org.eclipse.jpt.jpa.ui.wizards.gen.JptJpaUiWizardsEntityGenMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class TablesAndColumnsCustomizationWizardPage extends NewTypeWizardPage {

	private JpaProject jpaProject;

	private TreeViewer tableColumnTreeViewer;
	
	protected Composite detailPanel ;
	protected StackLayout detailPanelStatckLayout;
	protected Composite tableGenDetatilGroup;
	protected ColumnGenPanel columnGenPanel;
	protected Composite columnGenDetatilGroup;
	protected TableGenPanel tableGenPanel;
	protected ORMGenTable selectedTable;
	
	private ORMGenCustomizer customizer;

	protected final ResourceManager resourceManager;
	
	public TablesAndColumnsCustomizationWizardPage(JpaProject jpaProject, ResourceManager resourceManager) {
		super(true, "TablesAndColumnsCustomizationWizardPage"); //$NON-NLS-1$
		this.jpaProject = jpaProject;
		this.resourceManager = resourceManager;
		setTitle( JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_TABLES_AND_COLUMNS_PAGE_TITLE );
		setMessage( JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_TABLES_AND_COLUMNS_PAGE_DESC);
	}

	// -------- Initialization ---------
	/**
	 * The wizard owning this page is responsible for calling this method with the
	 * current selection. The selection is used to initialize the fields of the wizard 
	 * page.
	 * 
	 * @param selection used to initialize the fields
	 */
	public void init(IStructuredSelection selection) {
		if ( jpaProject != null ) {
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
		WorkbenchTools.setHelp(composite, JpaHelpContextIds.GENERATE_ENTITIES_WIZARD_CUSTOMIZE_INDIVIDUAL_ENTITIES);

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
		label.setText( JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_TABLES_AND_COLUMNS_PAGE_LABEL_TABLE_AND_COLUMNS );
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

	@Override
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
	
	protected void updateColumnGenDetail(ORMGenColumn column) {
		if(columnGenDetatilGroup==null){
			columnGenDetatilGroup = new Composite(detailPanel, SWT.NONE);
			GridLayout gridLayout = new GridLayout();
			gridLayout.numColumns = 4;
			columnGenDetatilGroup.setLayout(gridLayout);
			this.columnGenPanel = new ColumnGenPanel(columnGenDetatilGroup, 4, getCustomizer() , this, false);
		}
		columnGenPanel.setColumn(column);
		this.detailPanelStatckLayout.topControl = columnGenDetatilGroup;
		this.detailPanel.layout();		
		detailPanel.getParent().layout();
	}

	protected void updateTabelGenDetail(ORMGenTable table) {
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
		tableGenPanel.updateControls();
		
		this.detailPanelStatckLayout.topControl = tableGenDetatilGroup;
		this.detailPanel.layout();		

		String baseClass = StringTools.isBlank(table.getExtends()) ? "" : table.getExtends();
		setSuperClass(baseClass, true);			
		
		
		setSuperInterfaces(table.getImplements(), true);
		
		detailPanel.getParent().layout();
	}
	
	protected void createDomainJavaClassesPropertiesGroup(Composite composite, int columns) {
		Group parent = new Group( composite, SWT.NONE);
		parent.setText( JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_DOMAIN_JAVA_CLASS );
		parent.setLayout(new GridLayout(columns, false));
		SWTUtil.fillColumns( parent, columns);

		createSuperClassControls(parent, columns);
		createSuperInterfacesControls(parent, columns);

		//Resize supper class text width to fill the parent group.
		//Have to do it indirectly since fSuperClassDialogField is private in super class.
		Control[] controls = parent.getChildren();
		if( controls.length>1 && controls[1] instanceof Text ){
			Text text = (Text)(parent.getChildren()[1]);
			LayoutUtil.setWidthHint(text, getMaxFieldWidth());
			LayoutUtil.setHorizontalGrabbing(text);
		}		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.ui.wizards.NewTypeWizardPage#superClassChanged()
	 */
	@Override
	protected IStatus superClassChanged() {
		IStatus status = super.superClassChanged();
		String baseClass = getSuperClass();
		if(baseClass!=null && this.selectedTable!=null ){
			String oldBaseClass = this.selectedTable.getExtends();
			if( !baseClass.equals(oldBaseClass))
				this.selectedTable.setExtends(baseClass);
		}
		return status; 
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.ui.wizards.NewTypeWizardPage#superInterfacesChanged()
	 */
	@Override
	protected IStatus superInterfacesChanged() {
		IStatus status = super.superInterfacesChanged();
		List<String> superInterfaces = getSuperInterfaces();
		if (this.selectedTable != null) {
			this.selectedTable.setImplements(superInterfaces);
		}
		return status;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.ui.wizards.NewTypeWizardPage#addSuperInterface(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean addSuperInterface(String superInterface) {
		super.addSuperInterface(superInterface);
		List interfaces = getSuperInterfaces();
		if(this.selectedTable!=null)
			this.selectedTable.setImplements(interfaces);
		return true;
	}
	
	@Override
	protected void handleFieldChanged(String fieldName) {
		super.handleFieldChanged(fieldName);
		if( this.fSuperClassStatus.matches(IStatus.ERROR)){
			updateStatus(fSuperClassStatus);
		}else{
			setMessage("", IMessageProvider.NONE);
			setErrorMessage(null);
		}
		
	}	
	
	protected ORMGenCustomizer getCustomizer(){
		return this.getWizard().getCustomizer();
	}
	
	@Override
	public GenerateEntitiesFromSchemaWizard getWizard() {
		return (GenerateEntitiesFromSchemaWizard) super.getWizard();
	}

	public boolean isDynamic() {
		return false;
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
				for( ORMGenColumn col : columns){
					if( col.isForeignKey() )
						continue;
					if( col.isPrimaryKey() ){
						ret.add(0,col );
					}else{
						ret.add(col);
					}
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

		@Override
		public Image getImage(Object element) {
			if( element instanceof ORMGenTable ){
				return resourceManager.createImage(JptJpaUiImages.ENTITY_GEN_TABLE);
			}
			if( element instanceof ORMGenColumn ){
				ORMGenColumn col = ( ORMGenColumn)element;
				return col.isPrimaryKey() ?
						resourceManager.createImage(JptJpaUiImages.ENTITY_GEN_KEY_COLUMN) :
						resourceManager.createImage(JptJpaUiImages.ENTITY_GEN_COLUMN);
			}
			return null;
		}
		

		@Override
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
    public final void performHelp() {
        WorkbenchTools.displayHelp(this.getWizard().getHelpContextID());
    }
}
