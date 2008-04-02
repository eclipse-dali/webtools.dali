/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.ui.internal.schema.generation;

import org.eclipse.jpt.eclipselink.core.internal.context.schema.generation.SchemaGeneration;
import org.eclipse.jpt.eclipselink.ui.internal.EclipseLinkUiMessages;
import org.eclipse.jpt.ui.internal.widgets.AbstractFormPane;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

/**
 * EclipseLinkSchemaGenerationComposite
 */
public class EclipseLinkSchemaGenerationComposite
	extends AbstractFormPane<SchemaGeneration>
{
	public EclipseLinkSchemaGenerationComposite(
					AbstractFormPane<SchemaGeneration> subjectHolder, 
					Composite container) {
		super(subjectHolder, container, false);
	}

	@Override
	protected void initializeLayout(Composite parent) {
		Section section = getWidgetFactory().createSection(parent, SWT.FLAT | ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		section.setText(EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_sectionTitle);
		section.setDescription(EclipseLinkUiMessages.PersistenceXmlSchemaGenerationTab_sectionDescription);
		Composite composite = getWidgetFactory().createComposite(section);
		composite.setLayout(new GridLayout(1, false));
		section.setClient(composite);
		this.updateGridData(composite);
		this.updateGridData(composite.getParent());
		
		// DDL Generation Type:
		new DdlGenerationTypeComposite(this, composite);
		// Output Mode:
		new OutputModeComposite(this, composite);
		
		// Create DDL File Name:
		new CreateDdlFileNameComposite(this, composite);
		// Drop DDL File Name:
		new DropDdlFileNameComposite(this, composite);
		
		return;
	}

	@Override
	protected void doPopulate() {
		super.doPopulate();
	}

	private void updateGridData(Composite container) {
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		container.setLayoutData(gridData);
	}
}
