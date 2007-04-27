/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.mappings.details;

import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

//TODO repopulate this panel based on the Entity table changing
public class TableComposite extends BaseJpaComposite
{
	private ITable table;
	
	protected TableCombo tableCombo;
	
	protected CatalogCombo catalogCombo;
	protected SchemaCombo schemaCombo;
	
	public TableComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, commandStack, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite composite) {
		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		composite.setLayout(layout);	
		
		CommonWidgets.buildTableLabel(composite, getWidgetFactory());
		
		this.tableCombo = new TableCombo(composite, this.commandStack, getWidgetFactory());
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.tableCombo.getCombo().setLayoutData(gridData);
		helpSystem.setHelp(tableCombo.getCombo(), IJpaHelpContextIds.ENTITY_TABLE);

		CommonWidgets.buildCatalogLabel(composite, getWidgetFactory());
		this.catalogCombo = new CatalogCombo(composite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.catalogCombo.getCombo().setLayoutData(gridData);
		helpSystem.setHelp(catalogCombo.getCombo(), IJpaHelpContextIds.ENTITY_CATALOG);
	
		CommonWidgets.buildSchemaLabel(composite, getWidgetFactory());
		this.schemaCombo = new SchemaCombo(composite, this.commandStack, getWidgetFactory());
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
	
	public void doPopulate(EObject obj) {
		this.table = (ITable) obj;
		this.tableCombo.populate(this.table);
		this.catalogCombo.populate(this.table);
		this.schemaCombo.populate(this.table);
	}
	
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
