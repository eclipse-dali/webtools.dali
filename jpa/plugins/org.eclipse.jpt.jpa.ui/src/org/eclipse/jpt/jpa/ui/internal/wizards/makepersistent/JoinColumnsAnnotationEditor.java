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
import java.util.List;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jpt.common.ui.internal.util.SWTUtil;
import org.eclipse.jpt.jpa.annotate.mapping.ColumnAttributes;
import org.eclipse.jpt.jpa.annotate.mapping.EntityRefPropertyElem;
import org.eclipse.jpt.jpa.annotate.mapping.JoinTableAttributes;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.forms.events.HyperlinkEvent;
import org.eclipse.ui.forms.events.IHyperlinkListener;
import org.eclipse.ui.forms.widgets.Hyperlink;

public class JoinColumnsAnnotationEditor
{
	private ResourceManager resourceManager;
	private EntityRefPropertyElem refElem;
	private boolean isInverseJoin;
	private Table table;
	private Table refTable;
	private JoinPropertiesPage joinPropsPage;
	private Label noJoinLabel;
	private Hyperlink actionLink;
	
	public JoinColumnsAnnotationEditor(ResourceManager resourceManager, EntityRefPropertyElem refElem, boolean isInverseJoin,
			Table table, Table refTable, JoinPropertiesPage joinPropsPage)
	{
		this.resourceManager = resourceManager;
		assert refElem != null;
		this.isInverseJoin = isInverseJoin;
		this.refElem = refElem;
		this.table = table;
		this.refTable = refTable;
		this.joinPropsPage = joinPropsPage;
	}
	
	private List<ColumnAttributes> getEntityJoinColumns()
	{
		List<ColumnAttributes> joinColumns = new ArrayList<ColumnAttributes>();
		List<ColumnAttributes> srcJoinColumns = null;
		JoinTableAttributes joinTable = refElem.getJoinTable();
		if (joinTable != null)
		{
			if (isInverseJoin)
				srcJoinColumns = joinTable.getInverseJoinColumns();
			else
				srcJoinColumns = joinTable.getJoinColumns();
		}
		else
		{
			srcJoinColumns = refElem.getJoinColumns();
		}
		for (ColumnAttributes srcColAttrs : srcJoinColumns)
		{
			joinColumns.add(new ColumnAttributes(srcColAttrs));
		}
		return joinColumns;
	}
	
	public Control createJoinColumnsControl(Composite parent)
	{
		if (table == null)
			return null; // could happen when the join table is not specified yet
		if (refTable == null)
		{
			if (refElem.isManyToMany())
			{
				// do not show anything because the message would look stupid here
				return null; 
			}
			Label label = new Label(parent, SWT.NONE);
			label.setText(JptJpaUiMakePersistentMessages.REF_TABLE_NOT_SPECIFIED);
			return label;
		}
		
		final Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gl = new GridLayout(1, false);
		composite.setLayout(gl);
		
		List<ColumnAttributes> joinColumns = getEntityJoinColumns();
		
		String actionLabel;
		final boolean add;
		if (joinColumns == null || joinColumns.isEmpty())
		{
			noJoinLabel = new Label(composite, SWT.NONE);
			String desc = String.format(
					JptJpaUiMakePersistentMessages.NO_JOIN_COLUMN_LABEL, 
					table.getName(), refTable.getName());
			noJoinLabel.setText(desc);
			actionLabel = JptJpaUiMakePersistentMessages.ADD_JOIN_COLUMN;
			add = true;
		}
		else
		{
			Label joinColumnTableLabel = new Label(composite, SWT.NONE);
			String tableDesc = String.format(
					JptJpaUiMakePersistentMessages.JOIN_COLUMN_TABLE_DESC, 
					table.getName(), refTable.getName());
			joinColumnTableLabel.setText(tableDesc);
			actionLabel = JptJpaUiMakePersistentMessages.EDIT_JOIN_COLUMNS;			
			TableViewer joinColumnsTable = createJoinColumnTable(composite, joinColumns, (400 - 60) / 2);
			add = false;
			GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
			int ncols = joinColumns.size() > 2 ? 2 : 1;
			//gd2.heightHint = SWTUtil.getTableHeightHint(joinColumnsTable.getTable(), ncols);
			gd2.heightHint = 60;
			joinColumnsTable.getTable().setLayoutData(gd2);
		}
		actionLink = new Hyperlink(composite, SWT.NONE);
		actionLink.setForeground(ColorConstants.blue);
		actionLink.setText(actionLabel);
		actionLink.setUnderlined(true);
		actionLink.addHyperlinkListener(new IHyperlinkListener()
		{
			public void linkActivated(HyperlinkEvent e) 
			{
				Display display = Display.getDefault();
				if (display.isDisposed()) return;				
				display.asyncExec(new Runnable() 
				{
					public void run() 
					{				
						if (add)
							addJoinColumn();
						else
							editJoinColumns();
					}
				});
			}	
			
			public void linkEntered(HyperlinkEvent e) {}
			public void linkExited(HyperlinkEvent e) {}
		});
		
		composite.addDisposeListener(new DisposeListener()
		{
			public void widgetDisposed(DisposeEvent e)
			{
				Control[] children = composite.getChildren();
				for (Control child : children)
					child.dispose();
			}
		});
		return composite;
	}
	
	public void setEnable(boolean enabled)
	{
		if (actionLink != null)
		{
			actionLink.setEnabled(enabled);
			if (enabled)
				actionLink.setForeground(ColorConstants.blue);
			else
				actionLink.setForeground(ColorConstants.gray);
		}
		if (noJoinLabel != null)
			noJoinLabel.setEnabled(enabled);		
	}
	
	
	private TableViewer createJoinColumnTable(Composite parent, 
			List<ColumnAttributes> joinColumns, int colWidth)			
	{
		org.eclipse.swt.widgets.Table joinColumnsTable = 
			new org.eclipse.swt.widgets.Table(parent, 
					SWT.BORDER | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.SINGLE);
		joinColumnsTable.setLinesVisible(true);
		joinColumnsTable.setHeaderVisible(true);
				
		TableColumn colNameCol = new TableColumn(joinColumnsTable, SWT.NONE);
		colNameCol.setWidth(colWidth);
		colNameCol.setText(table.getName());
		colNameCol.setResizable(true);
		TableColumn refColNameCol = new TableColumn(joinColumnsTable, SWT.NONE);
		refColNameCol.setWidth(colWidth);
		refColNameCol.setText(refTable.getName());
		refColNameCol.setResizable(true);
		
		TableViewer tableViewer = new TableViewer(joinColumnsTable);
		tableViewer.setContentProvider(new JoinColumnsContentProvider());
		tableViewer.setLabelProvider(new JoinColumnsLabelProvider());
		tableViewer.setInput(joinColumns.toArray(new ColumnAttributes[0]));
		return tableViewer;
	}
	
	private final class JoinColumnsContentProvider implements IStructuredContentProvider
	{

		public Object[] getElements(Object inputElement)
		{
			if (inputElement instanceof ColumnAttributes[])
			{				
				return (ColumnAttributes[])inputElement;
			}
			return null;
		}

		public void dispose()
		{
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput)
		{
		}
		
	}
	
	private final class JoinColumnsLabelProvider extends LabelProvider implements ITableLabelProvider
	{
		public Image getColumnImage(Object element, int columnIndex)
		{
			return null;
		}

		public String getColumnText(Object element, int columnIndex)
		{
			ColumnAttributes colAttrs = (ColumnAttributes)element;
			String colText = null;
			
			switch (columnIndex)
			{
				case 0:
				{
					colText = colAttrs.getName();
					break;
				}
				case 1:
				{
					colText = colAttrs.getReferencedColumnName();
				}
			}			
			return colText;
		}
	}
	
	private void addJoinColumn()
	{
		AddJoinColumnDlg dlg = new AddJoinColumnDlg(Display.getDefault().getActiveShell(), resourceManager,
				table, refTable, refElem);
		if (dlg.open() == Dialog.OK)
        {
			ColumnAttributes joinCol = dlg.getNewJoinColumn();
			JoinTableAttributes joinTable = refElem.getJoinTable();
			if (joinTable != null)
			{
				if (isInverseJoin)
					joinTable.addInverseJoinColumn(joinCol);
				else
					joinTable.addJoinColumn(joinCol);
			}
			else
			{
				refElem.addJoinColumn(joinCol);
			}
			joinPropsPage.refreshJoinProperties();
        }
		
	}
	
	private void editJoinColumns()
	{
		List<ColumnAttributes> joinCols = getEntityJoinColumns();
		EditJoinColumnsDialog dlg = new EditJoinColumnsDialog(Display.getDefault().getActiveShell(),
				table, refTable, joinCols);
		if (dlg.open() == Dialog.OK)
        {
			joinCols = dlg.getJoinColumns();
			JoinTableAttributes joinTable = refElem.getJoinTable();
			if (joinTable != null)
			{
				if (isInverseJoin)
					joinTable.setInverseJoinColumns(joinCols);
				else
					joinTable.setJoinColumns(joinCols);
			}
			else
			{
				refElem.setJoinColumns(joinCols);
			}
					
			joinPropsPage.refreshJoinProperties();
        }
	}
			
	private class EditJoinColumnsDialog extends Dialog
	{
		private Table table;
		private Table refTable;
		private List<ColumnAttributes> joinColumns;
		private TableViewer joinColumnTable;
		private Button addButton;
		private Button removeButton;
				
		public EditJoinColumnsDialog(Shell shell, Table table, Table refTable,
				List<ColumnAttributes> joinColumns)
		{
			super(shell);
			this.table = table;
			this.refTable = refTable;
			this.joinColumns = new ArrayList<ColumnAttributes>();
			if (joinColumns != null)
				this.joinColumns.addAll(joinColumns);
		}

		@Override
		protected void configureShell(Shell newShell) 
		{
			newShell.setText(JptJpaUiMakePersistentMessages.EDIT_JOIN_COLUMNS); 
			super.configureShell(newShell);
		}

		@Override
		protected Control createDialogArea(Composite parent) 
		{
			initializeDialogUnits(parent);
			Composite composite = (Composite)super.createDialogArea(parent);
			GridLayout gl = new GridLayout(2, false);
			gl.marginWidth = 10;
			gl.marginHeight = 10;
			gl.verticalSpacing = 10;
			composite.setLayout(gl);

			Label label = new Label(composite, SWT.NONE);
			String desc = String.format(JptJpaUiMakePersistentMessages.EDIT_JOIN_COLUMNS_DESC, 
					table.getName(), refTable.getName());
			label.setText(desc);
			GridData gd = new GridData();
			gd.horizontalSpan = 2;
			label.setLayoutData(gd);
			
			joinColumnTable = createJoinColumnTable(composite, joinColumns, 200);
			GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
			gd2.heightHint = SWTUtil.getTableHeightHint(joinColumnTable.getTable(), 5);
			joinColumnTable.getTable().setLayoutData(gd2);
			joinColumnTable.addSelectionChangedListener(new ISelectionChangedListener()
			{
				public void selectionChanged(SelectionChangedEvent event)
				{
					updateButtons();
				}
			});
			
			createAddRemoveButtonComposite(composite);
			updateButtons();
			return composite;
		}
		
		@Override
		protected boolean isResizable() 
		{
			return true;
		}
		
		public List<ColumnAttributes> getJoinColumns()
		{
			return joinColumns;
		}
		
		private void createAddRemoveButtonComposite(Composite parent) 
		{
			//Add and Remove JoinColumns buttons
			Composite buttonComposite = new Composite(parent, SWT.NULL);
			GridLayout buttonLayout = new GridLayout(1, false);
			buttonComposite.setLayout(buttonLayout);
			GridData data =  new GridData();
			data.horizontalAlignment = GridData.FILL;
			data.verticalAlignment = GridData.BEGINNING;
			buttonComposite.setLayoutData(data);
			
			addButton = new Button(buttonComposite, SWT.PUSH);
			addButton.setText( JptJpaUiMakePersistentMessages.ADD );
			GridData gridData =  new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			addButton.setLayoutData(gridData);
			addButton.addSelectionListener(new SelectionAdapter() 
			{			
				public void widgetSelected(SelectionEvent e) 
				{
					AddJoinColumnDlg dlg = new AddJoinColumnDlg(Display.getDefault().getActiveShell(), resourceManager,
							table, refTable, refElem);
					if (dlg.open() == Dialog.OK)
			        {
						ColumnAttributes joinCol = dlg.getNewJoinColumn();
						joinColumns.add(joinCol);
						joinColumnTable.add(joinCol);
			        }
				}
			});
			
			removeButton = new Button(buttonComposite, SWT.PUSH);
			removeButton.setText( JptJpaUiMakePersistentMessages.REMOVE );
			gridData =  new GridData();
			gridData.horizontalAlignment = GridData.FILL;
			removeButton.setLayoutData(gridData);
			removeButton.addSelectionListener(new SelectionAdapter() 
			{
				public void widgetSelected(SelectionEvent e) 
				{
					StructuredSelection selection = (StructuredSelection)joinColumnTable.getSelection();
					if( selection.isEmpty())
						return;
					ColumnAttributes selectedCol = (ColumnAttributes)selection.getFirstElement();

					//Update TableViewer model
					joinColumns.remove(selectedCol);
					joinColumnTable.remove(selectedCol);
				}
			});
			
			addButton.setFocus();		
		}
		
		private void updateButtons()
		{
			if (joinColumnTable.getSelection().isEmpty())
				removeButton.setEnabled(false);
			else
				removeButton.setEnabled(true);
		}
	}
	
}
