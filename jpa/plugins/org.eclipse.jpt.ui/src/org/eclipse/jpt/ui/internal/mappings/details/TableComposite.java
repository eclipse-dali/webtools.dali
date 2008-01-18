/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.jpt.core.internal.context.base.ITable;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JptUiMappingsMessages;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

//TODO repopulate this panel based on the Entity table changing
public class TableComposite extends BaseJpaComposite<ITable>
{
	private TableCombo tableCombo;
	private CatalogCombo catalogCombo;
	private SchemaCombo schemaCombo;

	public TableComposite(PropertyValueModel<? extends ITable> subjectHolder,
	                      Composite parent,
	                      TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite composite) {
		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		composite.setLayout(layout);

		Group columnGroup = getWidgetFactory().createGroup(composite, JptUiMappingsMessages.TableComposite_tableSection);
		layout = new GridLayout();
		layout.marginHeight = 0;
		columnGroup.setLayout(layout);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace= true;
		columnGroup.setLayoutData(gridData);

		//created this composite because combos as direct children of a Group do not have a border, no clue why
		Composite intermediaryComposite = getWidgetFactory().createComposite(columnGroup);
		layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		intermediaryComposite.setLayout(layout);

		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace= true;
		intermediaryComposite.setLayoutData(gridData);

		CommonWidgets.buildTableLabel(intermediaryComposite, getWidgetFactory());

		this.tableCombo = new TableCombo(getSubjectHolder(), intermediaryComposite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.tableCombo.getCombo().setLayoutData(gridData);
		helpSystem.setHelp(tableCombo.getCombo(), IJpaHelpContextIds.ENTITY_TABLE);

		CommonWidgets.buildCatalogLabel(intermediaryComposite, getWidgetFactory());
		this.catalogCombo = new CatalogCombo(getSubjectHolder(), intermediaryComposite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.catalogCombo.getCombo().setLayoutData(gridData);
		helpSystem.setHelp(catalogCombo.getCombo(), IJpaHelpContextIds.ENTITY_CATALOG);

		CommonWidgets.buildSchemaLabel(intermediaryComposite, getWidgetFactory());
		this.schemaCombo = new SchemaCombo(getSubjectHolder(), intermediaryComposite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.schemaCombo.getCombo().setLayoutData(gridData);
		helpSystem.setHelp(schemaCombo.getCombo(), IJpaHelpContextIds.ENTITY_SCHEMA);
	}

	@Override
	protected void engageListeners() {
	}

	@Override
	protected void disengageListeners() {
	}

	@Override
	public void doPopulate() {
		this.tableCombo.populate();
		this.catalogCombo.populate();
		this.schemaCombo.populate();
	}

	@Override
	public void dispose() {
		this.catalogCombo.dispose();
		this.schemaCombo.dispose();
		this.tableCombo.dispose();
		super.dispose();
	}
}