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
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.jpa.annotate.mapping.EntityRefPropertyElem;
import org.eclipse.jpt.jpa.annotate.mapping.JoinStrategy;
import org.eclipse.jpt.jpa.annotate.mapping.JoinTableAttributes;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Text;

public class OneToOneJoinPropsPage extends JoinPropertiesPage
{
	private Button joinColumnRadio;
	private Button pkJoinColumnRadio;
	private Button mappedByRadio;
	private Text mappedByText;
	private Button mappedByBrowseBtn;
	private Button joinTableRadio;
	private Composite joinColumnGroup;
	private Composite joinTableGroup;
	private Text joinTableText;
	private Button joinTableBtn;	
	private Control joinColumnsCtl;
	private Control joinColumnsCtl2, inverseJoinColumnsCtl;
	private JoinColumnsAnnotationEditor joinColEditor;	
	
	public OneToOneJoinPropsPage(PersistenceUnit persistenceUnit, ResourceManager resourceManager, IProject project, String javaClass, 
			Schema schema, Table table, Table inverseTable, EntityRefPropertyElem refElem)
	{
		super(persistenceUnit, resourceManager, project, javaClass, schema, table, inverseTable, refElem);
		joinColEditor = 
			new JoinColumnsAnnotationEditor(resourceManager, refElem, false, table, inverseTable, this);			
	}
	
	@Override
	public Composite createJoinProps(Composite parent)
	{
		Composite group = new Composite(parent, SWT.NONE);
		GridLayout gl = new GridLayout(3, false);
		group.setLayout(gl);
		GridData gd = new GridData(GridData.FILL_HORIZONTAL);
		gd.widthHint = 400;
		gd.heightHint = AssociationAnnotationWizard.JOIN_PROP_GROUP_HEIGHT;
		group.setLayoutData(gd);
		
		mappedByRadio = AssociationAnnotationWizard.createButton(group, 1,  
				JptJpaUiMakePersistentMessages.PROPS_MAPPING_PAGE_MAPPEDBY,
				-1, SWT.RADIO); 
		mappedByText = AssociationAnnotationWizard.createText(group, true, 1, SWT.BORDER);
		mappedByBrowseBtn = AssociationAnnotationWizard.createImageButton(
				group, resourceManager.createImage(JptJpaUiImages.LIST_OF_VALUES), 1, SWT.NONE,
				JptJpaUiMakePersistentMessages.BROWSE);
		pkJoinColumnRadio = AssociationAnnotationWizard.createButton(group, 3,  
					JptJpaUiMakePersistentMessages.PK_JOIN_COLUMNS_LABEL,
					-1, SWT.RADIO); 		
			
		joinColumnRadio = AssociationAnnotationWizard.createButton(group, 3,  
				JptJpaUiMakePersistentMessages.JOIN_COLUMNS_LABEL,
				-1, SWT.RADIO); 
		
		// Join columns ctl parent
		joinColumnGroup = new Composite(group, SWT.NONE);
		GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
		gd2.horizontalSpan = 3;
		gd2.widthHint = 400;
		joinColumnGroup.setLayoutData(gd2);
		GridLayout gl2 = new GridLayout(1, false);
		gl2.marginWidth = gl2.marginHeight = 0;
		joinColumnGroup.setLayout(gl2);
		
		if (!isJpa1_0Project())
		{
			joinTableRadio = AssociationAnnotationWizard.createButton(group, 1,  
					JptJpaUiMakePersistentMessages.PROPS_MAPPING_PAGE_JOIN_TABLE,
					-1, SWT.RADIO); 
			joinTableText = AssociationAnnotationWizard.createText(group, true, 1, SWT.BORDER);
			Image listImage = resourceManager.createImage(JptJpaUiImages.LIST_OF_VALUES);
			joinTableBtn = AssociationAnnotationWizard.createImageButton(
					group, listImage, 1, SWT.NONE,
					JptJpaUiMakePersistentMessages.BROWSE);
			// Join Table ctl parent
			joinTableGroup = new Composite(group, SWT.NONE);
			GridData gd3 = new GridData(GridData.FILL_HORIZONTAL);
			gd3.horizontalSpan = 3;
			gd3.widthHint = 400;
			gd3.heightHint = 280;
			joinTableGroup.setLayoutData(gd3);
			GridLayout gl3 = new GridLayout(1, false);
			gl3.marginWidth = gl3.marginHeight = 0;
			joinTableGroup.setLayout(gl3);			
		}		
		return group;
	}

	@Override
	protected void initFields() 
	{
		String mappedBy = refElem.getMappedBy();
		if (mappedBy != null)
		{
			refElem.setJoinStrategy(JoinStrategy.MAPPED_BY);
			mappedByRadio.setSelection(true);
			mappedByText.setText(mappedBy);
			mappedByText.setEnabled(true);
			mappedByBrowseBtn.setEnabled(true);
			pkJoinColumnRadio.setSelection(false);
			joinColumnRadio.setSelection(false);
			joinColEditor.setEnable(false);
			if (!isJpa1_0Project())
			{
				joinTableRadio.setSelection(false);
				joinTableText.setEnabled(false);
				joinTableBtn.setEnabled(false);
			}
		}
		else
		{
			refElem.setJoinStrategy(JoinStrategy.JOIN_COLUMNS);
			mappedByRadio.setSelection(false);			
			mappedByText.setText("");
			mappedByText.setEnabled(false);
			mappedByBrowseBtn.setEnabled(false);
			pkJoinColumnRadio.setSelection(false);
			joinColumnRadio.setSelection(true);
			joinColEditor.setEnable(true);
			if (!isJpa1_0Project())
			{
				joinTableRadio.setSelection(false);
				joinTableText.setEnabled(false);
				joinTableBtn.setEnabled(false);				
			}			
		}
	}
	
	@Override
	protected void refreshJoinProperties()
	{
		if (joinColumnsCtl != null)
		{
			joinColumnsCtl.dispose();
			joinColumnsCtl = null;
		}
		
		Composite joinProps = getJoinPropsComposite();
		if (refElem.getJoinStrategy() == JoinStrategy.JOIN_COLUMNS)
		{
			Composite parent = joinColumnGroup != null ? joinColumnGroup : joinProps;
			joinColumnsCtl = joinColEditor.createJoinColumnsControl(parent);
			if (joinColumnsCtl != null)
			{
				GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
				gd2.grabExcessHorizontalSpace = true;
				joinColumnsCtl.setLayoutData(gd2);
				parent.pack();
				parent.layout();
			}
		}
		if (!isJpa1_0Project())
		{
			refreshJoinTableGroup();
		}
		
		joinProps.pack();
		joinProps.layout();
	}
	
	private void refreshJoinTableGroup()
	{
		if (joinColumnsCtl2 != null)
		{
			joinColumnsCtl2.dispose();
			joinColumnsCtl2 = null;
		}
		if (inverseJoinColumnsCtl != null)
		{
			inverseJoinColumnsCtl.dispose();
			inverseJoinColumnsCtl = null;
		}
		if (refElem.getJoinStrategy() != JoinStrategy.JOIN_TABLE)
		{
			return;
		}
		String joinTableName = getJoinTableName();
		Table joinTable = null;
		if (joinTableName != null)
			joinTable = schema.getTableNamed(joinTableName);
		
		JoinColumnsAnnotationEditor joinColEditor = 
				new JoinColumnsAnnotationEditor(resourceManager, refElem, false, joinTable, table, this);
		joinColumnsCtl2 = joinColEditor.createJoinColumnsControl(joinTableGroup);
		if (joinColumnsCtl2 != null)
		{
			GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
			gd2.grabExcessHorizontalSpace = true;
			joinColumnsCtl2.setLayoutData(gd2);
			joinTableGroup.pack();
			joinTableGroup.layout();
		}
		
		JoinColumnsAnnotationEditor inverseJoinColEditor = 
				new JoinColumnsAnnotationEditor(resourceManager, refElem, true, joinTable, inverseTable, this);		
				
		inverseJoinColumnsCtl = inverseJoinColEditor.createJoinColumnsControl(joinTableGroup);
		if (inverseJoinColumnsCtl != null)
		{
			GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
			gd2.grabExcessHorizontalSpace = true;
			inverseJoinColumnsCtl.setLayoutData(gd2);
			joinTableGroup.pack();
			joinTableGroup.layout();
		}

	}
	
	@Override
	protected void addListeners()
	{
		super.addListeners();
		pkJoinColumnRadio.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{			
				handleJoinPropsChange();
			}
		});
		joinColumnRadio.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{			
				handleJoinPropsChange();
			}
		});
	
		if (!isJpa1_0Project())
		{
			joinTableRadio.addSelectionListener(new SelectionAdapter()
			{
				@Override
				public void widgetSelected(SelectionEvent e)
				{			
					handleJoinPropsChange();
				}
			});	
			joinTableText.addModifyListener(new ModifyListener()
			{
				public void modifyText(ModifyEvent e)
				{
					handleJoinTableChange(joinTableText);
				}			
			});
			joinTableBtn.addSelectionListener(new SelectionAdapter()
			{
				@Override
				public void widgetSelected(SelectionEvent e)
				{			
					chooseJoinTable(joinTableText);
				}
			});
			
		}
		
		mappedByText.addModifyListener(new ModifyListener()
		{
			public void modifyText(ModifyEvent e)
			{
				handleMappedByChange(mappedByText);
			}			
		});
		mappedByBrowseBtn.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{			
				chooseMappedBy(mappedByText);
			}
		});
		
	}	
	
	private void handleJoinPropsChange()
	{
		if (mappedByRadio.getSelection())
		{
			refElem.setJoinStrategy(JoinStrategy.MAPPED_BY);
			refElem.removeAllJoinColumns();
			refElem.removeJoinTable();
			refElem.removeAllPKJoinColumns();
			refreshJoinProperties();
			
			mappedByText.setEnabled(true);
			mappedByBrowseBtn.setEnabled(true);
			pkJoinColumnRadio.setSelection(false);
			joinColumnRadio.setSelection(false);
			if (!isJpa1_0Project())
			{
				joinTableRadio.setSelection(false);
				joinTableText.setText("");
				joinTableText.setEnabled(false);
				joinTableBtn.setEnabled(false);
			}
		}
		else if (pkJoinColumnRadio.getSelection())
		{
			refElem.setJoinStrategy(JoinStrategy.PRIMARY_KEY_JOIN_COLUMNS);
			refElem.removeMappedBy();
			refElem.removeAllJoinColumns();
			refElem.removeJoinTable();
			refreshJoinProperties();
			
			mappedByRadio.setSelection(false);
			mappedByText.setText("");
			mappedByText.setEnabled(false);
			mappedByBrowseBtn.setEnabled(false);
			
			joinColumnRadio.setSelection(false);
			if (!isJpa1_0Project())
			{
				joinTableRadio.setSelection(false);
				joinTableText.setText("");
				joinTableText.setEnabled(false);
				joinTableBtn.setEnabled(false);
			}			
		}
		else if (joinColumnRadio.getSelection())
		{
			refElem.setJoinStrategy(JoinStrategy.JOIN_COLUMNS);
			refElem.removeMappedBy();
			refElem.removeAllPKJoinColumns();
			refElem.removeJoinTable();
			refreshJoinProperties();
			
			mappedByRadio.setSelection(false);
			mappedByText.setText("");
			mappedByText.setEnabled(false);
			mappedByBrowseBtn.setEnabled(false);
			
			pkJoinColumnRadio.setSelection(false);
			if (!isJpa1_0Project())
			{
				joinTableRadio.setSelection(false);
				joinTableText.setText("");
				joinTableText.setEnabled(false);
				joinTableBtn.setEnabled(false);
			}			
		}
		else if (!isJpa1_0Project() && joinTableRadio.getSelection())
		{
			refElem.setJoinStrategy(JoinStrategy.JOIN_TABLE);
			refElem.removeAllJoinColumns();	
			refElem.removeMappedBy();
			refElem.removeAllPKJoinColumns();
			JoinTableAttributes joinTblAttr = refElem.getJoinTable();
			if (joinTblAttr == null)
			{
				joinTblAttr = new JoinTableAttributes();
				refElem.setJoinTable(joinTblAttr);
			}
			refreshJoinProperties();
					
			mappedByRadio.setSelection(false);
			mappedByText.setText("");
			mappedByText.setEnabled(false);
			mappedByBrowseBtn.setEnabled(false);
			
			joinTableText.setEnabled(true);
			joinTableBtn.setEnabled(true);
			joinColumnRadio.setSelection(false);
		}
		else
		{
			refElem.setJoinStrategy(JoinStrategy.JOIN_COLUMNS);
			refElem.removeJoinTable();
			refElem.removeMappedBy();
			refreshJoinProperties();
			
			mappedByRadio.setSelection(false);
			mappedByText.setText("");
			mappedByText.setEnabled(false);
			mappedByBrowseBtn.setEnabled(false);
			
			joinTableText.setText("");
			joinTableText.setEnabled(false);
			joinTableBtn.setEnabled(false);
			joinTableRadio.setSelection(false);
		}		
		
	}
	
}
