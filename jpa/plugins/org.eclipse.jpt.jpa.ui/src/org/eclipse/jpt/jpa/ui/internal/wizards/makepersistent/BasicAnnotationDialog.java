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
import org.eclipse.jpt.jpa.annotate.mapping.AnnotationAttrConverter;
import org.eclipse.jpt.jpa.annotate.mapping.BasicEntityPropertyElem;
import org.eclipse.jpt.jpa.annotate.mapping.EntityPropertyElem;
import org.eclipse.jpt.jpa.annotate.util.AnnotateMappingUtil;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.jpt.jpa.ui.internal.plugin.JptJpaUiPlugin;
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

public class BasicAnnotationDialog extends Dialog
{
	private ResourceManager resourceManager;
	private IProject project;
	private Table table;
	private EntityPropertyElem entityProp;
	private DbColumnAnnotationCtl columnGroupCtl;
	private Text propNameText;
	private Text propTypeText;
	private Combo temporalCombo;
	
	public BasicAnnotationDialog(Shell parentShell, ResourceManager resourceManager, IProject project,
			Table table, EntityPropertyElem entityProp)
	{
		super(parentShell);
		this.resourceManager = resourceManager;
		this.project = project;
		this.table = table;
		this.entityProp = entityProp;
	}
	
	@Override
	protected void configureShell(Shell newShell) 
	{
		newShell.setText(JptJpaUiMakePersistentMessages.BASIC_ANNOTATION_DLG_TITLE); 
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
		createBasicGroup(composite);
		columnGroupCtl = new DbColumnAnnotationCtl(resourceManager, entityProp, table);
		columnGroupCtl.createColumnGroup(composite, SWT.NONE);
		initFields();
		addListeners();
		return composite;
	}
	
	private Group createBasicGroup(Composite parent)
	{
		Group basicGroup = new Group(parent, SWT.NONE);
		basicGroup.setText(JptJpaUiMakePersistentMessages.BASIC_ANNOTATION_GROUP_DESC);
		GridLayout layout = new GridLayout(2, false);
		basicGroup.setLayout(layout);
		basicGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		// Property Name
		AssociationAnnotationWizard.createLabel(basicGroup, 1, 
				JptJpaUiMakePersistentMessages.PROPERTY_NAME_LABEL, 
				-1); 
		propNameText = AssociationAnnotationWizard.createText(basicGroup, 1, true, null, 
				SWT.BORDER | SWT.READ_ONLY);

		// Property type
		AssociationAnnotationWizard.createLabel(basicGroup, 1, 
				JptJpaUiMakePersistentMessages.PROPERTY_TYPE_LABEL,
				-1); 
		propTypeText = AssociationAnnotationWizard.createText(basicGroup, 1, true, null, 
				SWT.BORDER | SWT.READ_ONLY);
		
		// temporal combo if the field is date/time
		try
		{
			if (AnnotateMappingUtil.isDate(entityProp.getPropertyType(), project))
			{
				AssociationAnnotationWizard.createLabel(basicGroup, 1, 
						JptJpaUiMakePersistentMessages.TEMPORAL,
						-1); 
				temporalCombo = AssociationAnnotationWizard.createCombo(basicGroup, true, 1, 
						SWT.BORDER | SWT.READ_ONLY | SWT.DROP_DOWN, -1);
			}
		}
		catch (JavaModelException je)
		{
			JptJpaUiPlugin.instance().logError(je);
		}
		return basicGroup;
	}
	
	private void initFields()
	{
		propNameText.setText(entityProp.getPropertyName());
		propTypeText.setText(entityProp.getPropertyType());
		if (temporalCombo != null)
		{
			String[] temporals = AnnotationAttrConverter.getTagEnumStringValues(AnnotationAttrConverter.TEMPORAL);
			temporalCombo.setItems(temporals);
			assert entityProp instanceof BasicEntityPropertyElem;
			BasicEntityPropertyElem basicProp = (BasicEntityPropertyElem)entityProp;
			String temporalType = basicProp.getTemporalType();		
			if (temporalType != null)
			{
				int index = temporalCombo.indexOf(temporalType);
				assert index != -1;
				temporalCombo.select(index);
			}
		}
		columnGroupCtl.initFields();		
	}
	
	private void addListeners()
	{
		if (temporalCombo != null)
		{
			temporalCombo.addSelectionListener(new SelectionAdapter()
			{
				@Override
				public void widgetSelected(SelectionEvent e)
				{			
					handleTemporalTypeChange();
				}
			});
		}
		columnGroupCtl.addListeners();
	}
	
	private void handleTemporalTypeChange()
	{
		String newTemporalType = temporalCombo.getItem(temporalCombo.getSelectionIndex());
		assert entityProp instanceof BasicEntityPropertyElem;
		BasicEntityPropertyElem basicProp = (BasicEntityPropertyElem)entityProp;
		basicProp.setTemporalType(newTemporalType);
	}
}
