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
import java.util.Collection;
import java.util.List;
import java.util.StringTokenizer;

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.Flags;
import org.eclipse.jdt.core.IField;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.common.ui.JptCommonUiImages;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.common.ui.internal.util.TableLayoutComposite;
import org.eclipse.jpt.jpa.annotate.mapping.EntityRefPropertyElem;
import org.eclipse.jpt.jpa.annotate.util.AnnotateMappingUtil;
import org.eclipse.jpt.jpa.ui.JptJpaUiMessages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class SelectOrderByDialog extends Dialog
{
	private IProject project;
	private String fqRefClassName;
	private CheckboxTableViewer propertiesTable;
	private List<PropertyOrder> initialOrderBys;
	private List<PropertyOrder> finalOrderBys;
	
	private static final String[] PROPERTY_TABLE_COLUMN_PROPERTIES = { "property", "order" };
	private static final int PROPERTY_COLUMN_INDEX = 0;
	private static final int ORDER_COLUMN_INDEX = 1;
	private static final String[] ORDER_NAMES = {EntityRefPropertyElem.ASCENDING,
		EntityRefPropertyElem.DESCENDING};
	private ResourceManager resourceManager;
	
	public SelectOrderByDialog(Shell shell, ResourceManager resourceManager, IProject project, String fqRefClassName, String orderBy)
	{
		super(shell);
		this.resourceManager = resourceManager;
		this.project = project;
		this.fqRefClassName = fqRefClassName;
		initOrderBys(orderBy);
	}

	@Override
	protected void configureShell(Shell newShell) 
	{
		newShell.setText(JptJpaUiMakePersistentMessages.ORDER_BY_TITLE); 
		super.configureShell(newShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) 
	{
		Composite composite = (Composite)super.createDialogArea(parent);
		GridLayout gl = new GridLayout(1, false);
		composite.setLayout(gl);
		Label label = new Label(composite, SWT.NONE);
		String desc = String.format(JptJpaUiMakePersistentMessages.ORDER_BY_DESC, 
				AnnotateMappingUtil.getClassName(fqRefClassName));
		label.setText(desc);
		
		Composite tablesGroup = new Composite(composite, SWT.NONE);
		tablesGroup.setLayout(new GridLayout(2, false));
		GridData data = new GridData();
		data.verticalAlignment = SWT.FILL;
		data.horizontalAlignment = SWT.FILL;
		data.grabExcessHorizontalSpace = true;
		data.grabExcessVerticalSpace = true;
		tablesGroup.setLayoutData(data);
		
		createPropertiesTable(tablesGroup);
		createButtonComposite(tablesGroup);
		
		return composite;
	}
	
	public String getOrderByDisplayStr()
	{
		StringBuffer buf = new StringBuffer();
		boolean first = true;
		for (PropertyOrder propOrder : finalOrderBys)
		{
			if (propOrder.included)
			{
				if (!first)
					buf.append(", ");
				else
					first = false;
				buf.append(propOrder.propertyName);
				if (propOrder.order.equals(EntityRefPropertyElem.DESCENDING))
					buf.append(" DESC");
			}
		}
		if (buf.length() > 0)
			return buf.toString();
		else
			return null;
	}
	private void createPropertiesTable(Composite parent)
	{		
		TableLayoutComposite layout= new TableLayoutComposite(parent, SWT.NONE);
		addColumnLayoutData(layout);
		
		final org.eclipse.swt.widgets.Table table = new org.eclipse.swt.widgets.Table(layout, SWT.H_SCROLL | SWT.V_SCROLL | SWT.SINGLE | SWT.FULL_SELECTION | SWT.BORDER | SWT.CHECK);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tableNameColumn = new TableColumn(table, SWT.NONE, PROPERTY_COLUMN_INDEX);
		tableNameColumn.setText(JptJpaUiMakePersistentMessages.SELECT_ORDERBY_DIALOG_PROPERTY);
		tableNameColumn.setResizable(true);

		TableColumn entityNameColumn = new TableColumn(table, SWT.NONE, ORDER_COLUMN_INDEX);
		entityNameColumn.setText(JptJpaUiMakePersistentMessages.SELECT_ORDERBY_DIALOG_ORDER);
		entityNameColumn.setResizable(true);
		
		GridData gd= new GridData(GridData.FILL_BOTH);
		gd.heightHint= SWTUtil.getTableHeightHint(table, 10);
		gd.widthHint = 400;
		layout.setLayoutData(gd);

		this.propertiesTable = new CheckboxTableViewer(table);
		this.propertiesTable.setUseHashlookup(true);
		this.propertiesTable.setLabelProvider(new PropertyTableLabelProvider());
		this.propertiesTable.setContentProvider(new PropertyTableContentProvider());
		
		this.propertiesTable.addPostSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				handlePropertiesSelectionChanged(event);
			}
		});
		
		table.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.F2 && e.stateMask == SWT.NONE) {
					//editEntityNameIfPossible();
					e.doit= false;
				}
			}
		});
		
		this.addCellEditors();
		initPropertiesTable();
	}
	
	private void addColumnLayoutData(TableLayoutComposite layout) 
	{
		layout.addColumnData(new ColumnWeightData(50, true));
		layout.addColumnData(new ColumnWeightData(50, true));
	}
	
	private void createButtonComposite(Composite tablesGroup)
	{
		Composite buttonComposite = new Composite(tablesGroup, SWT.NULL);
		GridLayout buttonLayout = new GridLayout(1, false);
		buttonComposite.setLayout(buttonLayout);
		GridData data =  new GridData();
		data.horizontalAlignment = GridData.FILL;
		data.verticalAlignment = GridData.BEGINNING;
		buttonComposite.setLayoutData(data);
		
		Button selectAllButton = new Button(buttonComposite, SWT.PUSH);
		selectAllButton.setImage(resourceManager.createImage(JptCommonUiImages.SELECT_ALL_BUTTON));		
		selectAllButton.setToolTipText(JptJpaUiMessages.General_selectAll);
		GridData gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		selectAllButton.setLayoutData(gridData);
		selectAllButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}
		
			public void widgetSelected(SelectionEvent e) {
				selectAllProperties();				
			}
		});
		
		Button deselectAllButton = new Button(buttonComposite, SWT.PUSH);
		deselectAllButton.setImage(resourceManager.createImage(JptCommonUiImages.DESELECT_ALL_BUTTON));		
		deselectAllButton.setToolTipText(JptJpaUiMessages.General_deselectAll);
		gridData =  new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		deselectAllButton.setLayoutData(gridData);
		deselectAllButton.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
				// do nothing
			}
		
			public void widgetSelected(SelectionEvent e) {
				deselectAllProperties();
			}
		});
		
	}
		
	private void syncPropertiesSelection()
	{
		int index = 0;
		for (PropertyOrder propOrder : finalOrderBys)
		{
			if (propOrder.included != propertiesTable.getChecked(propOrder))
			{
				propOrder.included = !propOrder.included;
				if (propOrder.included)
				{
					propOrder.order = EntityRefPropertyElem.ASCENDING;					
				}
				propertiesTable.update(propOrder, null);
			}
			index++;
		}	
	}
	
	private void selectAllProperties()
	{
		propertiesTable.setAllChecked(true);
		syncPropertiesSelection();
	}
	
	private void deselectAllProperties()
	{
		propertiesTable.setAllChecked(false);
		syncPropertiesSelection();
	}

	private void handlePropertiesSelectionChanged(SelectionChangedEvent event)
	{
		syncPropertiesSelection();
	}

	private void initPropertiesTable()
	{
		IType refType = null;
		finalOrderBys = new ArrayList<PropertyOrder>();
		
		try
		{
			refType = AnnotateMappingUtil.getType(fqRefClassName, project);
			IField[] fields = refType.getFields();
			for (IField field : fields)
			{
				// filter out static/inherited fields
				if (Flags.isStatic(field.getFlags()) || Flags.isSuper(field.getFlags()))
					continue;
				
				String typeStr = AnnotateMappingUtil.getFieldType(field, refType);
				if (AnnotateMappingUtil.isString(typeStr) || 
						AnnotateMappingUtil.isNumeric(typeStr) || 
						AnnotateMappingUtil.isDate(typeStr, project) ||
						AnnotateMappingUtil.isBoolean(typeStr))
				{					
					PropertyOrder propertyOrder = new PropertyOrder(field.getElementName(), EntityRefPropertyElem.ASCENDING);
					if (initialOrderBys.contains(propertyOrder))
					{
						PropertyOrder another = initialOrderBys.get(initialOrderBys.indexOf(propertyOrder));
						propertyOrder.included = true;
						propertyOrder.order = another.order; 
					}
					finalOrderBys.add(propertyOrder);
				}				
			}
			propertiesTable.setInput(finalOrderBys);
			for (PropertyOrder propOrder : finalOrderBys)
			{
				propertiesTable.setChecked(propOrder, propOrder.included);
				propertiesTable.update(propOrder, null);
			}			
		}
		catch (JavaModelException je)
		{
			JptJpaUiPlugin.instance().logError(je);
		}
		
	}
	
	private void addCellEditors() 
	{
		this.propertiesTable.setColumnProperties(PROPERTY_TABLE_COLUMN_PROPERTIES);
		
		CellEditor[] editors = new CellEditor[PROPERTY_TABLE_COLUMN_PROPERTIES.length];
		editors[ORDER_COLUMN_INDEX] = new ComboBoxCellEditor(this.propertiesTable.getTable(), 
								ORDER_NAMES, SWT.READ_ONLY | SWT.DROP_DOWN);
		
		this.propertiesTable.setCellEditors(editors);
		this.propertiesTable.setCellModifier(new PropertyOrderCellModifier());
	}
	
	private void initOrderBys(String orderBy)
	{
		initialOrderBys = new ArrayList<PropertyOrder>();
		if (orderBy != null && orderBy.length() > 0)
		{
			StringTokenizer tok = new StringTokenizer(orderBy, ",");
			while (tok.hasMoreTokens())
			{
				String str = tok.nextToken();
				if (str.charAt(0) == ' ')
					str = str.substring(1);
				String propName = null;
				String propOrder = null;
				PropertyOrder orderObj = null;
				int index = str.indexOf(' ');
				if (index != -1)
				{
					propName = str.substring(0, index);
					String temp = str.substring(index + 1);
					propOrder = temp.equals("DESC") ? EntityRefPropertyElem.DESCENDING : 
						EntityRefPropertyElem.ASCENDING;
					orderObj = new PropertyOrder(propName, propOrder);
				}
				else
				{
					propName = str;
					orderObj = new PropertyOrder(propName, EntityRefPropertyElem.ASCENDING);
				}
				initialOrderBys.add(orderObj);
			}
		}
	}
	
	// inner classes
	private class PropertyOrder
	{
		public String propertyName;
		public String order;
		public boolean included;
		
		public PropertyOrder(String propertyName, String order)
		{
			this(propertyName, order, false);
		}
		
		public PropertyOrder(String propertyName, String order, boolean included)
		{
			this.propertyName = propertyName;
			this.order = order;
			this.included = included;
		}
		
		@Override
		public boolean equals(Object another)
		{
			if (!(another instanceof PropertyOrder))
				return false;
			return propertyName.equals(((PropertyOrder)another).propertyName);
		}
		
		@Override
		public String toString()
		{
			return "["+ this.propertyName + " order " + this.order + "]";
		}
		
	}
	
	private class PropertyTableLabelProvider extends LabelProvider implements ITableLabelProvider 
	{
		PropertyTableLabelProvider() 
		{
			super();
		}

		public Image getColumnImage(Object element, int columnIndex) 
		{
			return null;
		}

		public String getColumnText(Object element, int columnIndex) 
		{
			if (element == null) {
				return null;
			}
			switch (columnIndex) 
			{
				case PROPERTY_COLUMN_INDEX:
					return ((PropertyOrder) element).propertyName;

				case ORDER_COLUMN_INDEX:
				{
					if (propertiesTable.getChecked(element))
						return ((PropertyOrder) element).order;
					else
						return null;
				}
			}
			throw new IllegalArgumentException("invalid column index: " + columnIndex);
		}

	}

	private class PropertyTableContentProvider implements IStructuredContentProvider 
	{
		PropertyTableContentProvider() 
		{
			super();
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// do nothing
		}
	
		public void dispose() {
			// do nothing
		}
	
		public Object[] getElements(Object inputElement) 
		{
			return ((Collection<?>) inputElement).toArray();
		}
	}
	
	/**
	 *	A CellModifier to update the property orders
	 */
	private class PropertyOrderCellModifier implements ICellModifier 
	{
		private CCombo orderCombo;
		public PropertyOrderCellModifier() 
		{
			super();
			CellEditor cellEditor = propertiesTable.getCellEditors()[ORDER_COLUMN_INDEX];
			assert cellEditor instanceof ComboBoxCellEditor;
			ComboBoxCellEditor comboEditor = (ComboBoxCellEditor)cellEditor;
			orderCombo = (CCombo)comboEditor.getControl();
			
		}

		public boolean canModify(Object element, String property) 
		{
			// order column can be modified if the element is selected
			if (property.equals(PROPERTY_TABLE_COLUMN_PROPERTIES[ORDER_COLUMN_INDEX]) &&
					propertiesTable.getChecked(element))
				return true;
			else
				return false;
		}

		public Object getValue(Object element, String property) 
		{
			// return the index of the value in the table model
			if (property.equals(PROPERTY_TABLE_COLUMN_PROPERTIES[ORDER_COLUMN_INDEX]))
			{
				return finalOrderBys.indexOf(element);
			}
			return new Integer(0);			
		}

		/** 
		 * element is the selected TableItem
		 * value is the selected item index in the comboCellEditor 
		 */
		public void modify(Object element, String property, Object value) 
		{
			if ( ! (element instanceof TableItem)) 
			{
				return;
			}
			Integer index = (Integer)value;
			TableItem item = (TableItem)element;
			PropertyOrder propertyOrder = (PropertyOrder)item.getData();
			String newOrder = orderCombo.getItem(index);
			propertyOrder.order = newOrder;
			propertiesTable.refresh();
		}

	}
	
}
