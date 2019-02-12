/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.wizards.gen;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.jpt.jpa.ui.internal.wizards.gen.TableGenPanel;
import org.eclipse.jpt.jpa.ui.wizards.gen.JptJpaUiWizardsEntityGenMessages;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;


public class EclipseLinkDynamicTableGenPanel
	extends TableGenPanel
{
	public EclipseLinkDynamicTableGenPanel(Composite parent, int columns , boolean isDefaultTable, WizardPage wizardPage){
		super(parent, columns, isDefaultTable, wizardPage);
	}

	@Override
	protected void createTableMappingPropertiesGroup(Composite composite, int columns) {
		Group parent = new Group(composite, SWT.NONE );
		parent.setText( JptJpaUiWizardsEntityGenMessages.GENERATE_ENTITIES_WIZARD_DEFAULT_TABLE_PAGE_TABLE_MAPPING);
		parent.setLayout(new GridLayout(columns, false));
		GridData layoutData = new GridData();
		layoutData.horizontalSpan = columns;
		layoutData.verticalAlignment = SWT.FILL;
		layoutData.horizontalAlignment = SWT.FILL;
		layoutData.grabExcessHorizontalSpace = true;
		layoutData.grabExcessVerticalSpace = false;
		parent.setLayoutData(layoutData);
		
		createClassNameControl(parent, columns);
		
		createIdGeneratorControls(parent, columns);
		
		//AssociationFetch and CollectionType only available for default table generation
		if ( isDefaultTable ) {
			createAssociationFetchControls(parent, columns);
			createCollectionTypeControls(parent, columns);
		}
	}
	
	@Override
	protected void updateAccessControls(){
		//do nothing, not applicable for Dynamic
	}
	
	@Override
	protected void updateGenerateOptionalAnnotationControls(){
		//do nothing, not applicable for Dynamic
	}
}
