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

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.jpt.jpa.annotate.mapping.AnnotationAttrConverter;
import org.eclipse.jpt.jpa.annotate.mapping.AnnotationAttribute;
import org.eclipse.jpt.jpa.annotate.mapping.AnnotationAttributeNames;
import org.eclipse.jpt.jpa.annotate.mapping.EntityRefPropertyElem;
import org.eclipse.jpt.jpa.ui.JptJpaUiImages;
import org.eclipse.swt.SWT;
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

public class MappingAnnotationCtl
{
	private EntityRefPropertyElem refElem;
	private Text propNameText;
	private Text propTypeText;
	private Text cascadeText;
	private Button cascadeBrowseBtn;
	private Combo fetchTypeCombo;
	private ResourceManager resourceManager;
	
	public MappingAnnotationCtl(ResourceManager resourceManager, EntityRefPropertyElem refElem)
	{
		this.refElem = refElem;
		this.resourceManager = resourceManager;
	}
	
	Composite createMappingPropGroup(Composite parent, int style)
	{
		Group mappingGroup = new Group(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		mappingGroup.setLayout(layout);
		mappingGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// Property Name
		AssociationAnnotationWizard.createLabel(mappingGroup, 1, 
				JptJpaUiMakePersistentMessages.PROPERTY_NAME_LABEL, -1);
		propNameText = AssociationAnnotationWizard.createText(mappingGroup, 1, true, null, 
				SWT.BORDER | SWT.READ_ONLY);
		new Label(mappingGroup, SWT.NULL);
		// Property type
		AssociationAnnotationWizard.createLabel(mappingGroup, 1, 
				JptJpaUiMakePersistentMessages.PROPERTY_TYPE_LABEL, -1);
		propTypeText = AssociationAnnotationWizard.createText(mappingGroup, 1, true, null, 
				SWT.BORDER | SWT.READ_ONLY);
		new Label(mappingGroup, SWT.NULL);
		// cascade
		AssociationAnnotationWizard.createLabel(mappingGroup, 1, 
				JptJpaUiMakePersistentMessages.CASCADE, -1); 
		cascadeText = AssociationAnnotationWizard.createText(mappingGroup, 1, true, null, 
				SWT.BORDER | SWT.READ_ONLY);
		cascadeBrowseBtn = AssociationAnnotationWizard.createImageButton(
				mappingGroup, resourceManager.createImage(JptJpaUiImages.LIST_OF_VALUES), 1, SWT.NONE,
				JptJpaUiMakePersistentMessages.BROWSE);
		// Fetch type
		AssociationAnnotationWizard.createLabel(mappingGroup, 1, 
				JptJpaUiMakePersistentMessages.FETCH_TYPE,
				-1); 
		fetchTypeCombo = AssociationAnnotationWizard.createCombo(mappingGroup, true, 1, 
				SWT.BORDER | SWT.READ_ONLY | SWT.DROP_DOWN, -1);
		new Label(mappingGroup, SWT.NULL);
		return mappingGroup;
	}
	
	void initFields()
	{
		propNameText.setText(refElem.getPropertyName());
		propTypeText.setText(refElem.getPropertyType());
		String cascadeStr = getCascadeDisplayStr(refElem.getAllCascades());
		cascadeText.setText(cascadeStr);
		fetchTypeCombo.setItems(
				AnnotationAttrConverter.getTagEnumStringValues(AnnotationAttrConverter.FETCH));
		AnnotationAttribute fetchAttr = 
			refElem.getAnnotationAttribute(AnnotationAttributeNames.FETCH);
		if (fetchAttr != null)
		{
			fetchTypeCombo.select(fetchTypeCombo.indexOf(fetchAttr.attrValue));
		}		
	}
	
	void addListeners()
	{
		cascadeBrowseBtn.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{			
				chooseCascade();
			}
		});
		fetchTypeCombo.addSelectionListener(new SelectionAdapter()
		{
			@Override
			public void widgetSelected(SelectionEvent e)
			{			
				handleFetchTypeChange();
			}
		});
		
	}
	
	private String getCascadeDisplayStr(List<String> cascades)
	{
		assert cascades != null;
		if (cascades.isEmpty())
			return ""; //$NON-NLS-1$
		
		StringBuffer cascadeBuf = new StringBuffer();		
		for (int i = 0; i < cascades.size(); i++)
		{
			if (i != 0)
				cascadeBuf.append(", "); //$NON-NLS-1$
			cascadeBuf.append(cascades.get(i));
		}
		return cascadeBuf.toString();
	}
	
	private void chooseCascade()
	{
		SelectCascadeDialog dlg = new SelectCascadeDialog(Display.getDefault().getActiveShell(),
				refElem.getAllCascades());
		if (dlg.open() == Dialog.OK)
	    {
			List<String> cascades = dlg.getAllCascades();
			refElem.setCascades(cascades);
			cascadeText.setText(getCascadeDisplayStr(cascades));
	    }
	}
	
	private void handleFetchTypeChange()
	{
		int index = fetchTypeCombo.getSelectionIndex();
		String newFetchType = fetchTypeCombo.getItem(index);
		AnnotationAttribute fetchAttr = 
			new AnnotationAttribute(
					AnnotationAttributeNames.FETCH, AnnotationAttrConverter.FETCH, newFetchType);
		
		refElem.setAnnotationAttr(fetchAttr);
	}
}
