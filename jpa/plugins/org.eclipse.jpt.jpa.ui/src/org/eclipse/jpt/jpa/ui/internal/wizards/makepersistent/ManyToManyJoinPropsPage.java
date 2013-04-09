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

public class ManyToManyJoinPropsPage extends JoinPropertiesPage
{
	private Text mappedByText;
	private Button mappedByBrowseBtn;
	private Button joinTableRadio;
	private Text joinTableText;
	private Button joinTableBtn;
	private Button mappedByRadio;
	private Control joinColumnsCtl;
	private Control inverseJoinColumnsCtl;
	
	public ManyToManyJoinPropsPage(PersistenceUnit persistenceUnit, ResourceManager resourceManager, IProject project, String javaClass, 
			Schema schema, Table table, Table inverseTable, EntityRefPropertyElem refElem)
	{
		super(persistenceUnit, resourceManager, project, javaClass, schema, table, inverseTable, refElem);
	}

	@Override
	public Composite createJoinProps(Composite parent)
	{		
		Composite group = new Composite(parent, SWT.NONE);
		GridLayout gl = new GridLayout(3, false);
		group.setLayout(gl);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, false);
		gd.widthHint = 400;
		gd.heightHint = AssociationAnnotationWizard.JOIN_PROP_GROUP_HEIGHT;
		group.setLayoutData(gd);
		
		mappedByRadio = AssociationAnnotationWizard.createButton(group, 1,  
				JptJpaUiMakePersistentMessages.PROPS_MAPPING_PAGE_MAPPEDBY,
				-1, SWT.RADIO); 
		mappedByText = AssociationAnnotationWizard.createText(group, true, 1, SWT.BORDER);
		Image listImage = resourceManager.createImage(JptJpaUiImages.LIST_OF_VALUES);
		mappedByBrowseBtn = AssociationAnnotationWizard.createImageButton(
				group, listImage, 1, SWT.NONE,
				JptJpaUiMakePersistentMessages.BROWSE);
		
		joinTableRadio = AssociationAnnotationWizard.createButton(group, 1,  
				JptJpaUiMakePersistentMessages.PROPS_MAPPING_PAGE_JOIN_TABLE,
				-1, SWT.RADIO); 
		joinTableText = AssociationAnnotationWizard.createText(group, true, 1, SWT.BORDER);
		joinTableBtn = AssociationAnnotationWizard.createImageButton(
				group, listImage, 1, SWT.NONE,
				JptJpaUiMakePersistentMessages.BROWSE);
		return group;
	}

	@Override
	protected void initFields()
	{
		String mappedBy = refElem.getMappedBy();
		if (mappedBy != null)
		{
			mappedByRadio.setSelection(true);
			mappedByText.setEnabled(true);
			mappedByBrowseBtn.setEnabled(true);
			mappedByText.setText(mappedBy);
			joinTableRadio.setSelection(false);
			joinTableText.setEnabled(false);
			joinTableBtn.setEnabled(false);
		}
		else
		{
			mappedByRadio.setSelection(false);
			mappedByText.setEnabled(false);
			mappedByBrowseBtn.setEnabled(false);
			joinTableRadio.setSelection(true);
			joinTableText.setEnabled(true);
			joinTableBtn.setEnabled(true);			
			if (refElem.getJoinTable() != null)
				joinTableText.setText(refElem.getJoinTable().getTableName());
		}
	}			
	
	@Override
	protected void addListeners()
	{
		super.addListeners();
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
		
		mappedByRadio.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{			
				handleJoinPropsChange();
			}
		});		
	}
	
	private void handleJoinPropsChange()
	{
		if (joinTableRadio.getSelection())
		{
			refElem.setJoinStrategy(JoinStrategy.JOIN_TABLE);
			refElem.removeMappedBy();
			joinTableText.setEnabled(true);
			joinTableBtn.setEnabled(true);			
			mappedByText.setText("");
			mappedByText.setEnabled(false);
			mappedByBrowseBtn.setEnabled(false);
		}
		else
		{
			refElem.setJoinStrategy(JoinStrategy.MAPPED_BY);
			refElem.removeJoinTable();
			joinTableText.setText("");
			joinTableText.setEnabled(false);
			joinTableBtn.setEnabled(false);
			mappedByText.setEnabled(true);
			mappedByBrowseBtn.setEnabled(true);			
			refreshJoinProperties();
		}
	}
		
	@Override
	protected void refreshJoinProperties()
	{
		if (joinColumnsCtl != null)
		{
			joinColumnsCtl.dispose();
		}
		if (inverseJoinColumnsCtl != null)
		{
			inverseJoinColumnsCtl.dispose();
		}
		String joinTableName = getJoinTableName();
		Table joinTable = null;
		if (joinTableName != null)
			joinTable = schema.getTableNamed(joinTableName);
		
		JoinColumnsAnnotationEditor joinColEditor = 
				new JoinColumnsAnnotationEditor(resourceManager, refElem, false, joinTable, table, this);
		Composite joinProps = getJoinPropsComposite();
		joinColumnsCtl = joinColEditor.createJoinColumnsControl(joinProps);
		if (joinColumnsCtl != null)
		{
			GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
			gd2.horizontalSpan = 3;
			gd2.widthHint = 400;
			joinColumnsCtl.setLayoutData(gd2);
			joinProps.pack();
			joinProps.layout();
		}
		
		JoinColumnsAnnotationEditor inverseJoinColEditor = 
				new JoinColumnsAnnotationEditor(resourceManager, refElem, true, joinTable, inverseTable, this);		
				
		inverseJoinColumnsCtl = inverseJoinColEditor.createJoinColumnsControl(joinProps);
		if (inverseJoinColumnsCtl != null)
		{
			GridData gd2 = new GridData(GridData.FILL_HORIZONTAL);
			gd2.horizontalSpan = 3;
			gd2.widthHint = 400;
			inverseJoinColumnsCtl.setLayoutData(gd2);
			joinProps.pack();
			joinProps.layout();
		}

	}
	
}
