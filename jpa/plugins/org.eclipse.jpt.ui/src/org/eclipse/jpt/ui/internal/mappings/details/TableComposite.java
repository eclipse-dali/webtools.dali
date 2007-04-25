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
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JpaUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.StringWithDefaultChooser.StringHolder;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

//TODO repopulate this panel based on the Entity table changing
public class TableComposite extends BaseJpaComposite
{
	
	private ITable table;
		
	protected TableCombo tableCombo;
	
	private StringWithDefaultChooser catalogChooser;
	private StringWithDefaultChooser schemaChooser;

	
	public TableComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, commandStack, widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite composite) {
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
		PlatformUI.getWorkbench().getHelpSystem().setHelp(tableCombo.getCombo(), IJpaHelpContextIds.ENTITY_TABLE);

		CommonWidgets.buildCatalogLabel(composite, getWidgetFactory());
		this.catalogChooser = CommonWidgets.buildStringWithDefaultChooser(composite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.catalogChooser.getCombo().setLayoutData(gridData);
	
		CommonWidgets.buildSchemaLabel(composite, getWidgetFactory());
		this.schemaChooser = CommonWidgets.buildStringWithDefaultChooser(composite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.schemaChooser.getCombo().setLayoutData(gridData);
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
		this.catalogChooser.populate(new TableCatalogHolder(this.table));
		this.schemaChooser.populate(new TableSchemaHolder(this.table));
	}
	
	public void doPopulate() {
		this.tableCombo.populate();
		this.catalogChooser.populate();
		this.schemaChooser.populate();
	}
	
	@Override
	public void dispose() {
		this.catalogChooser.dispose();
		this.schemaChooser.dispose();
		this.tableCombo.dispose();
		super.dispose();
	}
		
	private abstract class TableHolder extends EObjectImpl implements StringHolder {
		private ITable table;
		
		TableHolder(ITable table) {
			super();
			this.table = table;
		}
		
		public Class featureClass() {
			return ITable.class;
		}
		
		public boolean supportsDefault() {
			return true;
		}
		
		public EObject wrappedObject() {
			return this.table;
		}
		
		public ITable getTable() {
			return this.table;
		}
	}
	
	private class TableCatalogHolder extends TableHolder implements StringHolder {
		
		TableCatalogHolder(ITable table) {
			super(table);
		}
			
		public int featureId() {
			return JpaCoreMappingsPackage.ITABLE__SPECIFIED_CATALOG;
		}
		
		public int defaultFeatureId() {
			return JpaCoreMappingsPackage.ITABLE__DEFAULT_CATALOG;
		}
		
		public String defaultItem() {
			String defaultCatalog = getTable().getDefaultCatalog();
			if (defaultCatalog != null) {
				return NLS.bind(JpaUiMappingsMessages.EntityComposite_tableDefault, defaultCatalog);
			}
			return JpaUiMappingsMessages.EntityComposite_tableNoDefaultSpecified;
		}
		
		public String getString() {
			return getTable().getSpecifiedCatalog();
		}
		
		public void setString(String newName) {
			getTable().setSpecifiedCatalog(newName);
		}
	}
	
	private class TableSchemaHolder extends TableHolder implements StringHolder {
		
		TableSchemaHolder(ITable table) {
			super(table);
		}
			
		public int featureId() {
			return JpaCoreMappingsPackage.ITABLE__SPECIFIED_SCHEMA;
		}
		
		public int defaultFeatureId() {
			return JpaCoreMappingsPackage.ITABLE__DEFAULT_SCHEMA;
		}
		
		public String defaultItem() {
			String defaultSchema = getTable().getDefaultSchema();
			if (defaultSchema != null) {
				return NLS.bind(JpaUiMappingsMessages.EntityComposite_tableDefault, defaultSchema);
			}
			return JpaUiMappingsMessages.EntityComposite_tableNoDefaultSpecified;
		}
		
		public String getString() {
			return getTable().getSpecifiedSchema();
		}
		
		public void setString(String newName) {
			getTable().setSpecifiedSchema(newName);
		}
	}

}
