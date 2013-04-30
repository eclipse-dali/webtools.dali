/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.ui.internal.wizards.makepersistent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.Signature;
import org.eclipse.jdt.ui.JavaElementLabelProvider;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ComboBoxViewerCellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.common.utility.internal.ClassNameTools;
import org.eclipse.jpt.common.utility.internal.iterator.IteratorTools;
import org.eclipse.jpt.jpa.annotate.util.DefaultTableUtil;
import org.eclipse.jpt.jpa.annotate.util.AnnotateMappingUtil;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.ConnectionProfile;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.jpt.jpa.ui.internal.wizards.gen.DatabaseGroup;
import org.eclipse.jpt.jpa.ui.internal.wizards.makepersistent.JpaMakePersistentWizard.TypeComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class ClassMappingPage extends WizardPage 
{
	/* CU private */ final JpaProject jpaProject;
	/* CU private */ final ResourceManager resourceManager;
	private JavaClassMapping[] javaClassMappings;
	private JpaMakePersistentWizardPage makePersistentPage;
	private DatabaseGroup databaseGroup;
	private Schema schema;
	private List<String> tableNames;
	private TableViewer classMappingTableViewer;
	private final Set<Listener> listeners = Collections.synchronizedSet(new HashSet<Listener>());
	private static final int TYPE_COLUMN_WIDTH = 200;
	private static final int DATABASE_TABLE_COLUMN_WIDTH = 200;
	private static final int PRIMARY_KEY_PROPERTY_COLUMN_WIDTH = 200;
	private static final int MAPPING_TABLE_HEIGHT = 180;
	
	
	public ClassMappingPage(JpaProject proj, JavaClassMapping[] javaClassMappings,
			ResourceManager resourceManager, JpaMakePersistentWizardPage makePersistentPage)
	{
		super("Class Mapping Properties"); //$NON-NLS-1$
		this.resourceManager = resourceManager;
		this.jpaProject = proj;
		this.javaClassMappings = javaClassMappings;
		this.makePersistentPage = makePersistentPage;
		this.tableNames = new ArrayList<String>();
		setTitle(JptJpaUiMakePersistentMessages.CLASS_MAPPING_PAGE_TITLE);
		setMessage(JptJpaUiMakePersistentMessages.CLASS_MAPPING_PAGE_DESC);	
		
		this.makePersistentPage.addListener(new JpaMakePersistentWizardPage.Listener() 
		{			
			public void mappingTypeChanged(JavaClassMapping javaClassMapping)
			{
				handleMappingTypeChange(javaClassMapping);
			}
		});
		
	}

	public void createControl(Composite parent) 
	{
		this.initializeDialogUnits(parent);
		
		Composite composite = new Composite(parent, SWT.NULL);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		composite.setLayout(layout);

		this.databaseGroup = this.createDatabaseGroup(composite, -1);
		
		// Create class - db table mapping table
		Composite comp = new Composite( composite , SWT.NONE );
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		gridData.horizontalSpan = 3;
		comp.setLayoutData(gridData);
		GridLayout gridLayout = new GridLayout(1, false);
		comp.setLayout(gridLayout);
		
		this.classMappingTableViewer = new TableViewer(comp, SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		this.classMappingTableViewer.getTable().setLinesVisible(true);
		this.classMappingTableViewer.getTable().setHeaderVisible(true);
		this.classMappingTableViewer.setContentProvider(new ClassMappingContentProvider());
		this.classMappingTableViewer.setComparator(new TypeComparator());
		this.createTypeTableColumn();
		this.createDBTableColumn();
		this.createPrimaryKeyPropertyColumn();
		this.classMappingTableViewer.setInput(this.javaClassMappings);

		GridData data = new GridData(SWT.FILL, SWT.FILL, true, true);
		data.widthHint = TYPE_COLUMN_WIDTH + DATABASE_TABLE_COLUMN_WIDTH + PRIMARY_KEY_PROPERTY_COLUMN_WIDTH;
		data.heightHint = MAPPING_TABLE_HEIGHT;
		this.classMappingTableViewer.getTable().setLayoutData(data);	

		this.setControl(composite);
	}

	@Override
	public void dispose() {
		if(this.databaseGroup != null) {
			this.databaseGroup.dispose();
		}
		super.dispose();
	}
	
	public JavaClassMapping[] getJavaClassMappings()
	{
		return this.javaClassMappings;
	}
	
	public Schema getSchema()
	{
		return this.schema;
	}

	private void setSchema(Schema s) {
		this.schema = s;
	}
	
	private DatabaseGroup createDatabaseGroup(Composite parent, int widthHint) {	
		DatabaseGroup dbGroup = new DatabaseGroup(this.getContainer(), this.jpaProject, parent, resourceManager, widthHint);
		/**
		 * listen for when the Database Connection changes its selected schema
		 * so we can keep the page in synch
		 */
		class DatabasePageListener implements DatabaseGroup.Listener {
			public void selectedConnectionProfileChanged(ConnectionProfile connectionProfile) {
				ClassMappingPage.this.jpaProject.getDataSource().setConnectionProfileName(connectionProfile.getName());
			}

			public void selectedSchemaChanged(Schema schema) {
				ClassMappingPage.this.setSchema(schema);
				ClassMappingPage.this.updateTableNames();
			}
		}
		dbGroup.addListener(new DatabasePageListener());
		dbGroup.init();
		return dbGroup;
	}
	
	protected void createTypeTableColumn() 
	{
		TableViewerColumn column = new TableViewerColumn(this.classMappingTableViewer, SWT.NONE);
		column.getColumn().setWidth(TYPE_COLUMN_WIDTH);
		column.getColumn().setText(JptJpaUiMakePersistentMessages.CLASS_MAPPING_PAGE_TYPE_TABLE_COLUMN);
		column.setLabelProvider(new TypeColumnLabelProvider());
	}
	
	protected void createDBTableColumn() 
	{
		TableViewerColumn column;
		column = new TableViewerColumn(this.classMappingTableViewer, SWT.NONE);
		column.getColumn().setWidth(DATABASE_TABLE_COLUMN_WIDTH);
		column.getColumn().setText(JptJpaUiMakePersistentMessages.CLASS_MAPPING_PAGE_MAPPING_TABLE_COLUMN);

		column.setEditingSupport(new EditingSupport(this.classMappingTableViewer) {
			@Override
			protected Object getValue(Object element) 
			{
				return ((JavaClassMapping)element).getDBTable();
			}

			@Override
			protected void setValue(Object element, Object value) {
				((JavaClassMapping) element).setDBTable((String)value);
				syncPKProp((JavaClassMapping) element);
				getViewer().update(element, null);
				fireClassMappingChanged((JavaClassMapping) element);
			}
		
			@Override
			protected CellEditor getCellEditor(Object element) {
				final ComboBoxViewerCellEditor comboCellEditor =
					new ComboBoxViewerCellEditor((Composite) ClassMappingPage.this.classMappingTableViewer.getControl());

				comboCellEditor.setLabelProvider(buildDBTableComboCellEditorLabelProvider());
				comboCellEditor.setContentProvider(buildDBTableComboCellEditorContentProvider());
				comboCellEditor.setInput(ClassMappingPage.this.tableNames);
				return comboCellEditor;
			}
			
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		});
		
		column.setLabelProvider(new DBTableColumnLabelProvider());
	}
	
	protected void createPrimaryKeyPropertyColumn()
	{
		TableViewerColumn column;
		column = new TableViewerColumn(this.classMappingTableViewer, SWT.NONE);
		column.getColumn().setWidth(PRIMARY_KEY_PROPERTY_COLUMN_WIDTH);
		column.getColumn().setText(JptJpaUiMakePersistentMessages.CLASS_MAPPING_PAGE_PRIMARY_KEY_PROPERTY_COLUMN);

		column.setEditingSupport(new EditingSupport(this.classMappingTableViewer) {
			@Override
			protected Object getValue(Object element) 
			{
				return ((JavaClassMapping)element).getPrimaryKeyProperty();
			}

			@Override
			protected void setValue(Object element, Object value) {
				((JavaClassMapping) element).setPrimaryKeyProperty((String)value);
				getViewer().update(element, null);
				fireClassMappingChanged((JavaClassMapping) element);
			}
		
			@Override
			protected CellEditor getCellEditor(Object element) {
				final ComboBoxViewerCellEditor comboCellEditor =
					new ComboBoxViewerCellEditor((Composite) ClassMappingPage.this.classMappingTableViewer.getControl());

				comboCellEditor.setLabelProvider(buildDBTableComboCellEditorLabelProvider());
				comboCellEditor.setContentProvider(buildPrimaryKeyComboCellEditorContentProvider());
				JavaClassMapping mapping = (JavaClassMapping)element;
				comboCellEditor.setInput(getPKPropertyNames(mapping.getJDTType()));
				return comboCellEditor;
			}
			
			@Override
			protected boolean canEdit(Object element) {
				return true;
			}
		});
		
		column.setLabelProvider(new PrimaryKeyPropertyColumnLabelProvider());	
		
	}
	
	public void addListener(Listener listener) 
	{
		if( ! this.listeners.add(listener)) {
			throw new IllegalArgumentException("duplicate listener: " + listener); //$NON-NLS-1$
		}
	}

	public void removeListener(Listener listener) 
	{
		if( ! this.listeners.remove(listener)) {
			throw new IllegalArgumentException("missing listener: " + listener); //$NON-NLS-1$
		}
	}

	private Iterator<Listener> listeners() {
		return IteratorTools.clone(this.listeners);
	}

	private void fireClassMappingChanged(JavaClassMapping javaClassMapping) 
	{
		for(Iterator<Listener> stream = this.listeners(); stream.hasNext(); ) 
		{
			stream.next().classMappingChanged(javaClassMapping);
		}
	}
	
	private void syncPKProp(JavaClassMapping javaClassMapping)
	{
		if (javaClassMapping.getDBTable() != null)
		{
			String pkProp = computeDefaultPKProp(javaClassMapping.getDBTable(), javaClassMapping.getJDTType());
			if (pkProp != null) 
			{
				javaClassMapping.setPrimaryKeyProperty(pkProp);
			}
		}
	}
	
	private String computeDefaultPKProp(String tableName, IType jdtType)
	{
		Table table = getSchema().getTableNamed(tableName);
		if (table == null)
			return null;
		if (table.getPrimaryKeyColumnsSize() != 1)
			return null;
		Column pkCol = table.getPrimaryKeyColumn();
		String pkPropName = AnnotateMappingUtil.dbNameToJavaName(pkCol.getName());
		List<String> pkPropNames = getPKPropertyNames(jdtType);
		for (String temp : pkPropNames)
		{
			if (temp.equalsIgnoreCase(pkPropName))
				return temp;
		}
		return null;
	}
	
	private String computeDefaultTableName(String fqClassName)
	{
		if (this.schema != null)
		{
			Table table = DefaultTableUtil.findTable(schema, fqClassName);
			if (table != null)
			{
				return table.getName();
			}
		}
		return null;		
	}
	
	/**
	 * Returns the property names that could be primary keys
	 */
	private List<String> getPKPropertyNames(IType jdtType)
	{
		List<String> props = new ArrayList<String>();
		try 
		{
			IField[] fields = jdtType.getFields();
			for (IField field : fields)
			{
				// filter out static/inherited fields
				if (Flags.isSuper(field.getFlags()) || Flags.isStatic(field.getFlags()))
					continue;
				String typeStr = field.getTypeSignature();
				typeStr = Signature.toString(typeStr);
				if (!ClassNameTools.isPrimitive(typeStr))
					typeStr = AnnotateMappingUtil.resolveType(typeStr, jdtType);
				if (AnnotateMappingUtil.isString(typeStr) || 
						AnnotateMappingUtil.isNumeric(typeStr) || 
						AnnotateMappingUtil.isDate(typeStr, this.jpaProject.getProject()))
				{
					props.add(field.getElementName());
				}
			}
		}
		catch (JavaModelException je)
		{
			JptJpaUiPlugin.instance().logError(je);
		}
		return props;
	}
	
	private void updateTableNames()
	{
		tableNames.clear();
		if (schema != null)
		{
			for( Table table : schema.getTables() )
			{
				tableNames.add(table.getName());
			}
			for (JavaClassMapping javaClassMapping : javaClassMappings)
			{
				if (javaClassMapping.getMappingKey().equals(MappingKeys.ENTITY_TYPE_MAPPING_KEY)) 
				{
					String tableName = computeDefaultTableName(javaClassMapping.getName());
					if (tableName != null)
					{
						javaClassMapping.setDBTable(tableName);
						syncPKProp(javaClassMapping);
						fireClassMappingChanged(javaClassMapping);
					}
				}
			}
		}
		if (this.classMappingTableViewer != null)
			this.classMappingTableViewer.refresh(true);
	}
	
	private void handleMappingTypeChange(JavaClassMapping javaClassMapping)
	{
		this.classMappingTableViewer.getTable().clearAll();
		this.classMappingTableViewer.refresh(true);
		fireClassMappingChanged(javaClassMapping);
	}
	
	/**
	 * Allows clients to listen for changes to the java class mappings
	 */
	public interface Listener extends EventListener
	{
		void classMappingChanged(JavaClassMapping javaClassMapping);
	}
	
	//The ComboBoxViewerCellEditor does not support the image, so no reason to implement getImage(Object)
	protected ColumnLabelProvider buildDBTableComboCellEditorLabelProvider() {
		return new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				return (String)element;
			}
		};
	}
	
	protected IStructuredContentProvider buildDBTableComboCellEditorContentProvider() {
		return new IStructuredContentProvider() {
			public Object[] getElements(Object inputElement) {
				return ((List<String>)inputElement).toArray();				
			}

			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				//do nothing
			}

			public void dispose() {
				//do nothing
			}
		};
	}
	
	protected IStructuredContentProvider buildPrimaryKeyComboCellEditorContentProvider() {
		return new IStructuredContentProvider() {
			public Object[] getElements(Object inputElement) {
				return ((List<String>)inputElement).toArray();				
			}

			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
				//do nothing
			}

			public void dispose() {
				//do nothing
			}
		};
	}
		
	private final class ClassMappingContentProvider implements IStructuredContentProvider
	{
		public Object[] getElements(Object inputElement)
		{
			List<JavaClassMapping> entityMappings = new ArrayList<JavaClassMapping>();
			JavaClassMapping[] javaClassMappings = (JavaClassMapping[]) inputElement;
			for (JavaClassMapping javaClassMapping : javaClassMappings)
			{
				if (javaClassMapping.getMappingKey().equals(MappingKeys.ENTITY_TYPE_MAPPING_KEY)) 
				{
					entityMappings.add(javaClassMapping);
				}
			}
			return entityMappings.toArray();
		}

		public void dispose(){}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
		{
		}
		
	}
	
	private final class TypeColumnLabelProvider extends ColumnLabelProvider
	{
		private final JavaElementLabelProvider javaElementLabelProvider = 
			new JavaElementLabelProvider(JavaElementLabelProvider.SHOW_POST_QUALIFIED
										| JavaElementLabelProvider.SHOW_SMALL_ICONS
										| JavaElementLabelProvider.SHOW_OVERLAY_ICONS);

		@Override
		public String getText(Object element) {
			return this.javaElementLabelProvider.getText(((JavaClassMapping) element).getJDTType());
		}

		@Override
		public Image getImage(Object element) {
			return this.javaElementLabelProvider.getImage(((JavaClassMapping) element).getJDTType());
		}
	}
	
	private class DBTableColumnLabelProvider
		extends ColumnLabelProvider
	{
		@Override
		public String getText(Object element) {
			JavaClassMapping mapping = (JavaClassMapping)element;
			return mapping.getDBTable();
		}
	
		@Override
		public Image getImage(Object element) {
			JavaClassMapping mapping = (JavaClassMapping)element;
			return (mapping.getDBTable() == null) ? null : this.getResourceManager().createImage(JptJpaUiImages.TABLE);
		}
	
		private ResourceManager getResourceManager() {
			return ClassMappingPage.this.resourceManager;
		}
	}
	
	private class PrimaryKeyPropertyColumnLabelProvider
		extends ColumnLabelProvider
	{
		@Override
		public String getText(Object element) {
			JavaClassMapping mapping = (JavaClassMapping)element;
			return mapping.getPrimaryKeyProperty();
		}
	
		@Override
		public Image getImage(Object element) {
			JavaClassMapping mapping = (JavaClassMapping)element;
			return (mapping.getPrimaryKeyProperty() == null) ? null : this.getResourceManager().createImage(JptJpaUiImages.KEY);
		}
	
		private ResourceManager getResourceManager() {
			return ClassMappingPage.this.resourceManager;
		}
	}
}
