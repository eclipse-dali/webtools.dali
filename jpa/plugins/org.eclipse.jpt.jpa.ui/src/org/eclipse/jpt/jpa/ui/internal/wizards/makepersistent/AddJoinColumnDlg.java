/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/

package org.eclipse.jpt.jpa.ui.internal.wizards.makepersistent;


import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.jpa.annotate.mapping.ColumnAttributes;
import org.eclipse.jpt.jpa.annotate.mapping.EntityRefPropertyElem;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AddJoinColumnDlg extends Dialog
{
	private Table table;
	private Table refTable;
	private Text colNameText;
	private Button colNameBrowseBtn;
	private Button uniqueChkbox;
	private Combo nullableCombo;
	private Combo insertableCombo;
	private Combo updatableCombo;
	private Text refColNameText;
	private Button refColNameBrowseBtn;
	private ColumnAttributes colAttrs;
	private ResourceManager resourceManager;
	private EntityRefPropertyElem entityRefElem;
	
	public AddJoinColumnDlg(Shell shell, ResourceManager resourceManager, Table table, Table refTable,
			EntityRefPropertyElem entityRefElem)
	{
		super(shell);
		this.table = table;
		this.refTable = refTable;
		this.resourceManager = resourceManager;
		this.entityRefElem = entityRefElem;
		colAttrs = new ColumnAttributes();
	}

	@Override
	protected void configureShell(Shell newShell) 
	{
		newShell.setText(JptJpaUiMakePersistentMessages.ADD_JOIN_COLUMN_DLG_TITLE); 
		super.configureShell(newShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) 
	{
		initializeDialogUnits(parent);
		
		Composite composite = (Composite)super.createDialogArea(parent);
		GridLayout gl = new GridLayout(3, false);
		composite.setLayout(gl);
		GridData gd = new GridData();
		gd.widthHint = 300;
		composite.setLayoutData(gd);

		// Column name
		AssociationAnnotationWizard.createLabel(composite, 1, 
				JptJpaUiMakePersistentMessages.COLUMN_NAME,
				-1);
		colNameText = AssociationAnnotationWizard.createText(composite, true, 1, SWT.BORDER);
		colNameBrowseBtn = AssociationAnnotationWizard.createImageButton(
				composite, resourceManager.createImage(JptJpaUiImages.LIST_OF_VALUES), 1, SWT.NONE,
				JptJpaUiMakePersistentMessages.BROWSE);
		// unique
		AssociationAnnotationWizard.createLabel(composite, 1, 
				JptJpaUiMakePersistentMessages.UNIQUE, 
				-1);
		uniqueChkbox = AssociationAnnotationWizard.createButton(composite, 2, 
				null, -1, SWT.CHECK);	
		
		String[] booleanVals = new String[] {"true", "false"};
		// nullable
		AssociationAnnotationWizard.createLabel(composite, 1, 
				JptJpaUiMakePersistentMessages.NULLABLE, 
				-1);
		nullableCombo = AssociationAnnotationWizard.createCombo(composite, true, 1, 
				SWT.BORDER | SWT.READ_ONLY | SWT.DROP_DOWN, -1);
		nullableCombo.setItems(booleanVals);
		new Label(composite, SWT.NULL);
		
		// insertable
		AssociationAnnotationWizard.createLabel(composite, 1, 
				JptJpaUiMakePersistentMessages.INSERTABLE, 
				-1);
		insertableCombo = AssociationAnnotationWizard.createCombo(composite, true, 1, 
				SWT.BORDER | SWT.READ_ONLY | SWT.DROP_DOWN, -1);
		insertableCombo.setItems(booleanVals);
		new Label(composite, SWT.NULL);
		
		// updatable
		AssociationAnnotationWizard.createLabel(composite, 1, 
				JptJpaUiMakePersistentMessages.UPDATABLE, 
				-1);
		updatableCombo = AssociationAnnotationWizard.createCombo(composite, true, 1, 
				SWT.BORDER | SWT.READ_ONLY | SWT.DROP_DOWN, -1);
		updatableCombo.setItems(booleanVals);
		new Label(composite, SWT.NULL);
		
		// reference column name
		// Column name
		AssociationAnnotationWizard.createLabel(composite, 1, 
				JptJpaUiMakePersistentMessages.REF_COLUMN_NAME,
				-1);
		refColNameText = AssociationAnnotationWizard.createText(composite, true, 1, SWT.BORDER);
		refColNameBrowseBtn = AssociationAnnotationWizard.createImageButton(
				composite, resourceManager.createImage(JptJpaUiImages.LIST_OF_VALUES), 1, SWT.NONE,
				JptJpaUiMakePersistentMessages.BROWSE);
		
		addListeners();
		return composite;
	}
	
	@Override
	protected boolean isResizable() 
	{
		return true;
	}
	
	private void addListeners()
	{
		colNameText.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				colAttrs.setName(colNameText.getText());
			}			
		});
		colNameBrowseBtn.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{			
				SelectColumnDialog dlg = new SelectColumnDialog(Display.getDefault().getActiveShell(),
						table, entityRefElem.getPropertyName());
				if (dlg.open() == Dialog.OK)
		        {
					colNameText.setText(dlg.getSelectedColumn());
		        }
			}
		});
		uniqueChkbox.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{			
				colAttrs.setUnique(uniqueChkbox.getSelection());
			}
		});
		nullableCombo.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{			
				colAttrs.setNullable(nullableCombo.getSelectionIndex() == 0);
			}
		});
		insertableCombo.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{			
				colAttrs.setInsertable(insertableCombo.getSelectionIndex() == 0);
			}
		});
		updatableCombo.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{			
				colAttrs.setUpdatable(updatableCombo.getSelectionIndex() == 0);
			}
		});			
		refColNameText.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				colAttrs.setReferencedColumnName(refColNameText.getText());
			}			
		});
		refColNameBrowseBtn.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{			
				SelectColumnDialog dlg = new SelectColumnDialog(Display.getDefault().getActiveShell(),
						refTable, entityRefElem.getPropertyName());
				if (dlg.open() == Dialog.OK)
		        {
					refColNameText.setText(dlg.getSelectedColumn());
		        }
			}
		});		
	}
	
	public ColumnAttributes getNewJoinColumn()
	{
		return colAttrs;
	}
}
