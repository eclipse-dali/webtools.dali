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
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.jpa.annotate.mapping.EntityRefPropertyElem;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

public class EntityMappingPage extends WizardPage
{
	protected static final int JOIN_PROPS_GROUP_MIN_HEIGHT = 400;
	private PersistenceUnit persistenceUnit;
	private ResourceManager resourceManager;
	protected IProject project;
	protected EntityRefPropertyElem refElem;
	protected IWizardPage nextPage;
	private MappingAnnotationCtl mappingCtl;
	private Group mappingGroup;
	private Text targetEntityText;
	private Button targetEntityBrowseBtn;
	private Text orderByText;
	private Button orderByBrowseBtn;
	
	public EntityMappingPage(PersistenceUnit persistenceUnit, ResourceManager resourceManager, IProject project,  EntityRefPropertyElem refElem, IWizardPage nextPage)
	{
		super("Mapping Properties Page"); //$NON-NLS-1$
		this.persistenceUnit = persistenceUnit;
		this.resourceManager = resourceManager;
		this.project = project;
		this.refElem = refElem;
		this.nextPage = nextPage;
		setTitle(JptJpaUiMakePersistentMessages.MAPPING_PAGE_TITLE);
		setMessage(JptJpaUiMakePersistentMessages.MAPPING_PAGE_DESC);			
	}
	
	public void createControl(Composite parent)
	{
		initializeDialogUnits(parent);
		
		Composite composite = new Composite(parent, SWT.NONE);
		GridLayout gl = new GridLayout(1, true);
		composite.setLayout(gl);
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.widthHint = 400;
		composite.setLayoutData(gd);
		
		mappingCtl = new MappingAnnotationCtl(resourceManager, refElem);		
		mappingGroup = (Group)mappingCtl.createMappingPropGroup(composite, SWT.NONE);
		createTargetEntityGroup(composite);
		initFields();
		addListeners();
		setControl(composite);
	}

	@Override
	public void setVisible(boolean visible) 
	{
		super.setVisible(visible);
		if (visible)
		{
			if (refElem.isOneToMany())
				mappingGroup.setText(JptJpaUiMakePersistentMessages.ONE_TO_MANY_PROP_DESC);
			else if (refElem.isManyToMany())
				mappingGroup.setText(JptJpaUiMakePersistentMessages.MANY_TO_MANY_PROP_DESC);
			else if (refElem.isManyToOne())
				mappingGroup.setText(JptJpaUiMakePersistentMessages.MANY_TO_ONE_PROP_DESC);
			else if (refElem.isOneToOne())
				mappingGroup.setText(JptJpaUiMakePersistentMessages.ONE_TO_ONE_PROP_DESC);
		}
	}
	
	@Override
	public IWizardPage getNextPage()
	{
		return nextPage;
	}
	
	protected Group createTargetEntityGroup(Composite parent)
	{
		Group targetEntityGroup = new Group(parent, SWT.NONE);
		targetEntityGroup.setText(JptJpaUiMakePersistentMessages.TARGET_ENTITY_GROUP_DESC);
		GridLayout gl = new GridLayout(3, false);
		targetEntityGroup.setLayout(gl);
		targetEntityGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// target entity
		AssociationAnnotationWizard.createLabel(targetEntityGroup, 1, 
				JptJpaUiMakePersistentMessages.TARGET_ENTITY_GROUP_LABEL,
				-1); 
		targetEntityText = AssociationAnnotationWizard.createText(targetEntityGroup, true, 1, SWT.BORDER);
		targetEntityBrowseBtn = AssociationAnnotationWizard.createImageButton(
				targetEntityGroup, resourceManager.createImage(JptJpaUiImages.LIST_OF_VALUES), 1, SWT.NONE,
				JptJpaUiMakePersistentMessages.BROWSE);
		// order by
		AssociationAnnotationWizard.createLabel(targetEntityGroup, 1, 
				JptJpaUiMakePersistentMessages.ORDER_BY_LABEL,
				-1); 
		orderByText = AssociationAnnotationWizard.createText(targetEntityGroup, true, 1, SWT.BORDER);
		orderByBrowseBtn = AssociationAnnotationWizard.createImageButton(
				targetEntityGroup, resourceManager.createImage(JptJpaUiImages.LIST_OF_VALUES), 1, SWT.NONE,
				JptJpaUiMakePersistentMessages.BROWSE);
				
		return targetEntityGroup;
	}
	
	protected PersistenceUnit getPersistenceUnit()
	{
		return this.persistenceUnit;
	}
	
	protected void initFields()
	{
		mappingCtl.initFields();
		if (refElem.getRefEntityClassName() != null && targetEntityText != null)
			targetEntityText.setText(refElem.getRefEntityClassName());
		if (refElem.getOrderBy() != null && orderByText != null)
			orderByText.setText(refElem.getOrderBy());		
	}

	protected void addListeners()
	{
		mappingCtl.addListeners();
		if (targetEntityText != null)
		{
			targetEntityText.addModifyListener(new ModifyListener()
			{
				public void modifyText(ModifyEvent e)
				{
					refElem.setRefEntityClassName(targetEntityText.getText());
				}
			});
		}
		if (targetEntityBrowseBtn != null)
		{
			targetEntityBrowseBtn.addSelectionListener(new SelectionAdapter()
			{
	            @Override
	            public void widgetSelected( SelectionEvent e )
	            {
	            	String selectedClass = OrmUiUtil.selectJavaClass(project);
	            	if (selectedClass != null)
	            		targetEntityText.setText(selectedClass);
	            }
			});			
		}
		
		if (orderByBrowseBtn != null)
		{
			orderByBrowseBtn.addSelectionListener(new SelectionAdapter()
			{
	            @Override
	            public void widgetSelected( SelectionEvent e )
	            {
	            	if (refElem.getRefEntityClassName() == null)
	            	{
	            		AssociationAnnotationWizard.displayNoTargetEntityError(refElem.getPropertyName());
	            		return;
	            	}
	        		SelectOrderByDialog dlg = new SelectOrderByDialog(Display.getDefault().getActiveShell(),
	        				resourceManager, project, refElem.getRefEntityClassName(), refElem.getOrderBy());
	        		if (dlg.open() == Dialog.OK)
	                {
	        			String orderBy = dlg.getOrderByDisplayStr();
	        			if (orderBy != null)
	        				orderByText.setText(orderBy);
	        			else
	        				orderByText.setText("");
	                }	            	
	            }
			});			
		}
		
		if (orderByText != null)
		{
			orderByText.addModifyListener(new ModifyListener()
			{
				public void modifyText(ModifyEvent e)
				{
					if (orderByText.getText().length() != 0)
						refElem.setOrderBy(orderByText.getText());
					else
						refElem.setOrderBy(null);
				}
			});
		}
		
	}
		
}
