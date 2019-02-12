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

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.jpa.annotate.mapping.AnnotationAttrConverter;
import org.eclipse.jpt.jpa.annotate.mapping.EntityPropertyElem;
import org.eclipse.jpt.jpa.annotate.mapping.GeneratedValueAttributes;
import org.eclipse.jpt.jpa.annotate.mapping.IdEntityPropertyElement;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class IdAnnotationDialog extends Dialog
{
	private ResourceManager resourceManager;
	private EntityPropertyElem entityProp;
	private Table table;
	private IProject project;
	private Text propNameText;
	private Text propTypeText;
	private Combo genStrategyCombo;
	private DbColumnAnnotationCtl columnGroupCtl;
	
	public IdAnnotationDialog(Shell parentShell, ResourceManager resourceManager, Table table, EntityPropertyElem entityProp, IProject project)
	{
		super(parentShell);
		this.resourceManager = resourceManager;
		this.table = table;
		this.entityProp = entityProp;
		this.project = project;
	}

	@Override
	protected void configureShell(Shell newShell) 
	{
		newShell.setText(JptJpaUiMakePersistentMessages.ID_ANNOTATION_DLG_TITLE); 
		super.configureShell(newShell);
	}
	
	@Override
	protected Control createDialogArea(Composite parent) 
	{
		Composite composite = (Composite)super.createDialogArea(parent);
		GridLayout gl = new GridLayout(1, false);
		gl.verticalSpacing = 10;
		gl.marginHeight = 10;
		gl.marginWidth = 10;
		composite.setLayout(gl);
		GridData gd = new GridData();
		gd.widthHint = 400;
		composite.setLayoutData(gd);
		createIdGroup(composite);
		columnGroupCtl = new DbColumnAnnotationCtl(resourceManager, entityProp, table, project);
		columnGroupCtl.createColumnGroup(composite, SWT.NONE);
		initFields();
		addListeners();
		return composite;
	}
	
	private Group createIdGroup(Composite parent)
	{
		Group idGroup = new Group(parent, SWT.NONE);
		idGroup.setText(JptJpaUiMakePersistentMessages.ID_ANNOTATION_GROUP_DESC);
		GridLayout layout = new GridLayout(2, false);
		idGroup.setLayout(layout);
		idGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// Property Name
		AssociationAnnotationWizard.createLabel(idGroup, 1, 
				JptJpaUiMakePersistentMessages.PROPERTY_NAME_LABEL,
				-1); 
		propNameText = AssociationAnnotationWizard.createText(idGroup, 1, true, null, 
				SWT.BORDER | SWT.READ_ONLY);

		// Property type
		AssociationAnnotationWizard.createLabel(idGroup, 1, 
				JptJpaUiMakePersistentMessages.PROPERTY_TYPE_LABEL,
				-1);
		propTypeText = AssociationAnnotationWizard.createText(idGroup, 1, true, null, 
				SWT.BORDER | SWT.READ_ONLY);
		
		// Generation Strategy
		AssociationAnnotationWizard.createLabel(idGroup, 1, 
				JptJpaUiMakePersistentMessages.GENERATION_STRATEGY, 
				-1);
		genStrategyCombo = AssociationAnnotationWizard.createCombo(idGroup, true, 1, 
				SWT.BORDER | SWT.READ_ONLY | SWT.DROP_DOWN, -1);
		return idGroup;
	}
		
	private void initFields()
	{
		propNameText.setText(entityProp.getPropertyName());
		propTypeText.setText(entityProp.getPropertyType());
		
		String[] strategies = AnnotationAttrConverter.getTagEnumStringValues(AnnotationAttrConverter.GENERATION_STRATEGY);
		genStrategyCombo.setItems(strategies);
		assert entityProp instanceof IdEntityPropertyElement;
		IdEntityPropertyElement idProp = (IdEntityPropertyElement)entityProp;
		GeneratedValueAttributes genAttrs = idProp.getGeneratedValueAttrs();		
		if (genAttrs != null)
		{
			String strategy = genAttrs.getStrategy();
			if ( strategy!= null)
			{
				int index = genStrategyCombo.indexOf(strategy);
				assert index != -1;
				genStrategyCombo.select(index);
			}
		}
		columnGroupCtl.initFields();		
	}
	
	private void addListeners()
	{
		genStrategyCombo.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{			
				handleGenStrategyChange();
			}
		});
		
		columnGroupCtl.addListeners();
	}
	
	private void handleGenStrategyChange()
	{
		String newStrategy = genStrategyCombo.getItem(genStrategyCombo.getSelectionIndex());
		assert entityProp instanceof IdEntityPropertyElement;
		IdEntityPropertyElement idProp = (IdEntityPropertyElement)entityProp;
		GeneratedValueAttributes genAttrs = idProp.getGeneratedValueAttrs();		
		if (genAttrs == null)
		{
			genAttrs = new GeneratedValueAttributes();
			genAttrs.setStrategy(newStrategy);
			idProp.setGeneratedValueAttrs(genAttrs);
		}
		else 
		{
			genAttrs.setStrategy(newStrategy);
		}
	}
	
}
