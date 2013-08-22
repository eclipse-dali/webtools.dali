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

import org.eclipse.core.resources.IProject;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.jpa.annotate.mapping.ColumnAttributes;
import org.eclipse.jpt.jpa.annotate.mapping.EntityPropertyElem;
import org.eclipse.jpt.jpa.annotate.util.AnnotateMappingUtil;
import org.eclipse.jpt.jpa.core.resource.orm.JPA;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
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
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class DbColumnAnnotationCtl
{
	private ResourceManager resourceManager;
	private EntityPropertyElem entityProp;
	private Table table;
	private IProject project;
	private Text colNameText;
	private Button colNameBrowseBtn;
	private Combo nullableCombo;
	private Button uniqueChkbox;
	private Text lengthText;
	private Text precisionText;
	private Text scaleText;
	private Combo insertableCombo;
	private Combo updatableCombo;
	private boolean isNumeric;
	private boolean isTimeDataType;
	private boolean isDateDataType;
	private boolean isBasicMapping;

	public DbColumnAnnotationCtl(ResourceManager resourceManager, EntityPropertyElem entityProp,
			Table table, IProject project)
	{
		this.resourceManager = resourceManager;
		this.entityProp = entityProp;
		this.table = table;
		this.project = project;
	}

	Composite createColumnGroup(Composite parent, int style)
	{
		Group colGroup = new Group(parent, SWT.NONE);
		colGroup.setText(JptJpaUiMakePersistentMessages.COL_ANNOTATION_GROUP_DESC);
		GridLayout layout = new GridLayout(3, false);
		colGroup.setLayout(layout);
		colGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// Column Name
		AssociationAnnotationWizard.createLabel(colGroup, 1, 
				JptJpaUiMakePersistentMessages.COLUMN_NAME, 
				-1);
		colNameText = AssociationAnnotationWizard.createText(colGroup, true, 1, SWT.BORDER);
		colNameBrowseBtn = AssociationAnnotationWizard.createImageButton(
				colGroup, resourceManager.createImage(JptJpaUiImages.LIST_OF_VALUES), 1, SWT.NONE, 
				JptJpaUiMakePersistentMessages.BROWSE);
		
		// unique check box if it's basic annotation
		isBasicMapping = entityProp.getTagName().equals(JPA.BASIC);
		if (isBasicMapping)
		{
			AssociationAnnotationWizard.createLabel(colGroup, 1, 
					JptJpaUiMakePersistentMessages.UNIQUE, 
					-1);
			uniqueChkbox = AssociationAnnotationWizard.createButton(colGroup, 2, 
					null, -1, SWT.CHECK);
		}

		// nullable
		AssociationAnnotationWizard.createLabel(colGroup, 1, 
				JptJpaUiMakePersistentMessages.NULLABLE, 
				-1);
		nullableCombo = AssociationAnnotationWizard.createCombo(colGroup, true, 1, 
				SWT.BORDER | SWT.READ_ONLY | SWT.DROP_DOWN, -1);
		new Label(colGroup, SWT.NULL);
				
		// insertable and updatable for basic mapping
		if (isBasicMapping)
		{
			AssociationAnnotationWizard.createLabel(colGroup, 1, 
					JptJpaUiMakePersistentMessages.INSERTABLE, 
					-1);
			insertableCombo = AssociationAnnotationWizard.createCombo(colGroup, true, 1, 
					SWT.BORDER | SWT.READ_ONLY | SWT.DROP_DOWN, -1);
			new Label(colGroup, SWT.NULL);
			
			AssociationAnnotationWizard.createLabel(colGroup, 1, 
					JptJpaUiMakePersistentMessages.UPDATABLE, 
					-1);
			updatableCombo = AssociationAnnotationWizard.createCombo(colGroup, true, 1, 
					SWT.BORDER | SWT.READ_ONLY | SWT.DROP_DOWN, -1);
			new Label(colGroup, SWT.NULL);			
		}

		isNumeric = AnnotateMappingUtil.isNumeric(entityProp.getPropertyType());
		isTimeDataType = AnnotateMappingUtil.isTime(entityProp.getPropertyType());
		try 
		{
			isDateDataType = AnnotateMappingUtil.isDate(entityProp.getPropertyType(), project);
		} catch (JavaModelException je) 
		{
			JptJpaUiPlugin.instance().logError(je);
		}
		if (isNumeric) 
		{
			// precision and scale if the filed is numeric
			AssociationAnnotationWizard.createLabel(colGroup, 1, 
					JptJpaUiMakePersistentMessages.PRECISION, 
					-1);
			precisionText = AssociationAnnotationWizard.createText(colGroup, true, 1, SWT.BORDER);
			new Label(colGroup, SWT.NULL);
			
			AssociationAnnotationWizard.createLabel(colGroup, 1, 
					JptJpaUiMakePersistentMessages.SCALE, 
					-1);
			scaleText = AssociationAnnotationWizard.createText(colGroup, true, 1, SWT.BORDER);
			new Label(colGroup, SWT.NULL);
		}
		else if (isTimeDataType) 
		{
			// precision if the filed is a time data type
			AssociationAnnotationWizard.createLabel(colGroup, 1, 
					JptJpaUiMakePersistentMessages.PRECISION, 
					-1);
			precisionText = AssociationAnnotationWizard.createText(colGroup, true, 1, SWT.BORDER);
			new Label(colGroup, SWT.NULL);
		}
		else if (!isDateDataType) 
		{
			// length if the field is not numeric or time data type or date data type
			AssociationAnnotationWizard.createLabel(colGroup, 1, 
					JptJpaUiMakePersistentMessages.LENGTH, 
					-1);
			lengthText = AssociationAnnotationWizard.createText(colGroup, true, 1, SWT.BORDER);	
			new Label(colGroup, SWT.NULL);
		}
		return colGroup;
	}
	
	void initFields()
	{
		String[] boolComboVals = new String[]{"true", "false"}; 
		nullableCombo.setItems(boolComboVals);
		if (isBasicMapping)
		{
			insertableCombo.setItems(boolComboVals);
			updatableCombo.setItems(boolComboVals);
		}
		ColumnAttributes colAttrs = entityProp.getColumnAnnotationAttrs();
		if (colAttrs != null)
		{
			// column name
			if (colAttrs.getName() != null)
				colNameText.setText(colAttrs.getName());			
			// unique
			if (colAttrs.isSetUnique() && isBasicMapping)
				uniqueChkbox.setSelection(colAttrs.isUnique());
			// nullable
			if (colAttrs.isSetNullable()) 
			{
				if (colAttrs.isNullable())
					nullableCombo.select(0);
				else
					nullableCombo.select(1);
			}
			if (isNumeric) 
			{
				// precision and scale
				if (colAttrs.isSetPrecision())
				{
					precisionText.setText(String.valueOf(colAttrs.getPrecision()));
				}
				if (colAttrs.isSetScale())
				{
					scaleText.setText(String.valueOf(colAttrs.getScale()));
				}
			}
			else if (isTimeDataType) 
			{
				// precision
				if (colAttrs.isSetPrecision())
				{
					precisionText.setText(String.valueOf(colAttrs.getPrecision()));
				}
			}
			else if (!isDateDataType) 
			{
				// length
				if (colAttrs.isSetLength())
				{
					lengthText.setText(String.valueOf(colAttrs.getLength()));
				}
			}
		
			if (isBasicMapping)
			{
				// insertable
				if (colAttrs.isSetInsertable()) 
				{
					if (colAttrs.isInsertable())
						insertableCombo.select(0);
					else
						insertableCombo.select(1);
				}
				// updatable
				if (colAttrs.isSetUpdatable()) 
				{
					if (colAttrs.isUpdatable())
						updatableCombo.select(0);
					else
						updatableCombo.select(1);
				}
			}
		}		
	}
	
	void addListeners()
	{
		colNameText.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				handleColNameChange();
			}			
		});
		colNameBrowseBtn.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{			
				chooseColumn();
			}
		});
		if (isBasicMapping)
		{
			uniqueChkbox.addSelectionListener(new SelectionAdapter()
			{
				@Override
				public void widgetSelected(SelectionEvent e)
				{			
					handleUniqueChange();
				}
			});
		}
		nullableCombo.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{			
				handleNullableChange();
			}
		});
		if (lengthText != null)
		{			
			lengthText.addModifyListener(new ModifyListener()
			{
				public void modifyText(ModifyEvent e)
				{
					handleLengthChange();
				}			
			});
		}
		if (precisionText != null)
		{			
			precisionText.addModifyListener(new ModifyListener()
			{
				public void modifyText(ModifyEvent e)
				{
					handlePrecisionChange();
				}			
			});
		}
		if (scaleText != null)
		{			
			scaleText.addModifyListener(new ModifyListener()
			{
				public void modifyText(ModifyEvent e)
				{
					handleScaleChange();
				}			
			});
		}
		if (isBasicMapping)
		{
			insertableCombo.addSelectionListener(new SelectionAdapter()
			{
				@Override
				public void widgetSelected(SelectionEvent e)
				{			
					handleInsertableChange();
				}
			});
			updatableCombo.addSelectionListener(new SelectionAdapter()
			{
				@Override
				public void widgetSelected(SelectionEvent e)
				{			
					handleUpdatableChange();
				}
			});			
		}
	}

	private void handleColNameChange()
	{
		ColumnAttributes colAttrs = entityProp.getColumnAnnotationAttrs();
		entityProp.setDBColumn(table.getColumnNamed(colNameText.getText()));
		if (colAttrs == null)
		{
			colAttrs = new ColumnAttributes();
			colAttrs.setName(colNameText.getText());
			entityProp.setColumnAnnotationAttrs(colAttrs);
		}
		else 
		{
			colAttrs.setName(colNameText.getText());
		}
		
	}
	private void chooseColumn()
	{		
		SelectColumnDialog dlg = new SelectColumnDialog(Display.getDefault().getActiveShell(),
				table, entityProp.getPropertyName());
		if (dlg.open() == Dialog.OK)
        {
			colNameText.setText(dlg.getSelectedColumn());
        }
	}
	
	private void handleUniqueChange()
	{
		boolean isUnique = uniqueChkbox.getSelection();
		ColumnAttributes colAttrs = entityProp.getColumnAnnotationAttrs();		
		if (colAttrs == null)
		{
			colAttrs = new ColumnAttributes();
			colAttrs.setUnique(isUnique);
			entityProp.setColumnAnnotationAttrs(colAttrs);
		}
		else 
		{
			colAttrs.setUnique(isUnique);
		}
	}

	private void handleNullableChange()
	{
		int index = nullableCombo.getSelectionIndex();
		ColumnAttributes colAttrs = entityProp.getColumnAnnotationAttrs();		
		boolean nullable = index == 0 ? true : false;
		if (colAttrs == null)
		{
			colAttrs = new ColumnAttributes();
			colAttrs.setNullable(nullable);
			entityProp.setColumnAnnotationAttrs(colAttrs);
		}
		else 
		{
			colAttrs.setNullable(nullable);
		}
	}
	
	private void handleLengthChange()
	{
		// TODO validate
		ColumnAttributes colAttrs = entityProp.getColumnAnnotationAttrs();		
		if (colAttrs == null)
		{
			colAttrs = new ColumnAttributes();
			colAttrs.setLength(lengthText.getText());
			entityProp.setColumnAnnotationAttrs(colAttrs);
		}
		else 
		{
			colAttrs.setLength(lengthText.getText());
		}
	}

	private void handleScaleChange()
	{
		// TODO validate
		ColumnAttributes colAttrs = entityProp.getColumnAnnotationAttrs();		
		if (colAttrs == null)
		{
			colAttrs = new ColumnAttributes();
			colAttrs.setScale(scaleText.getText());
			entityProp.setColumnAnnotationAttrs(colAttrs);
		}
		else 
		{
			colAttrs.setScale(scaleText.getText());
		}		
	}

	private void handlePrecisionChange()
	{
		// TODO validate
		ColumnAttributes colAttrs = entityProp.getColumnAnnotationAttrs();		
		if (colAttrs == null)
		{
			colAttrs = new ColumnAttributes();
			colAttrs.setPrecision(precisionText.getText());
			entityProp.setColumnAnnotationAttrs(colAttrs);
		}
		else 
		{
			colAttrs.setPrecision(precisionText.getText());
		}		
	}
	
	private void handleInsertableChange()
	{
		int index = insertableCombo.getSelectionIndex();
		ColumnAttributes colAttrs = entityProp.getColumnAnnotationAttrs();		
		boolean insertable = index == 0 ? true : false;
		if (colAttrs == null)
		{
			colAttrs = new ColumnAttributes();
			colAttrs.setInsertable(insertable);
			entityProp.setColumnAnnotationAttrs(colAttrs);
		}
		else 
		{
			colAttrs.setInsertable(insertable);
		}
	}

	private void handleUpdatableChange()
	{
		int index = updatableCombo.getSelectionIndex();
		ColumnAttributes colAttrs = entityProp.getColumnAnnotationAttrs();		
		boolean updatable = index == 0 ? true : false;
		if (colAttrs == null)
		{
			colAttrs = new ColumnAttributes();
			colAttrs.setUpdatable(updatable);
			entityProp.setColumnAnnotationAttrs(colAttrs);
		}
		else 
		{
			colAttrs.setUpdatable(updatable);
		}
	}
}
