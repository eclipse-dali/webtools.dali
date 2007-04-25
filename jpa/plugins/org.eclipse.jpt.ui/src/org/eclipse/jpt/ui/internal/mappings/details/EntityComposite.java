/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved. This
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
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.ITable;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.ui.internal.details.BaseJpaComposite;
import org.eclipse.jpt.ui.internal.mappings.JpaUiMappingsMessages;
import org.eclipse.jpt.ui.internal.mappings.details.StringWithDefaultChooser.StringHolder;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class EntityComposite extends BaseJpaComposite
{
	private IEntity entity;
	
	private EntityNameCombo entityNameCombo;
	
	private TableComposite tableComposite;
	private InheritanceComposite inheritanceComposite;
	private OverridesComposite attributeOverridesComposite;
	
	public EntityComposite(Composite parent, CommandStack commandStack, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, SWT.NULL, commandStack, widgetFactory);
	}
	
	@Override
	protected void initializeLayout(Composite composite) {
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		composite.setLayout(layout);
		
		Control generalControl = buildGeneralComposite(composite);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		generalControl.setLayoutData(gridData);

		Control inheritanceControl = buildInheritanceComposite(composite);
	    gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		inheritanceControl.setLayoutData(gridData);
	}
	
	private Control buildGeneralComposite(Composite composite) {
//		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
		
		Composite generalComposite = getWidgetFactory().createComposite(composite);
		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 0;
		generalComposite.setLayout(layout);	
				
		CommonWidgets.buildEntityNameLabel(generalComposite, getWidgetFactory());
		
		this.entityNameCombo = 
	    	CommonWidgets.buildEntityNameCombo(generalComposite, this.commandStack, getWidgetFactory());
	    GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		this.entityNameCombo.getCombo().setLayoutData(gridData);
		
		
		this.tableComposite = new TableComposite(generalComposite, this.commandStack, getWidgetFactory());
	    gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		this.tableComposite.getControl().setLayoutData(gridData);
		
		this.attributeOverridesComposite = new OverridesComposite(generalComposite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalSpan = 2;
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		this.attributeOverridesComposite.getControl().setLayoutData(gridData);

		return generalComposite;
	}
	
	private Control buildInheritanceComposite(Composite composite) {
	    Section section = getWidgetFactory().createSection(composite, SWT.FLAT | ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR);
	    section.setText(JpaUiMappingsMessages.EntityComposite_inheritance);

		Composite inheritanceClient = getWidgetFactory().createComposite(section);
		section.setClient(inheritanceClient);
		
		GridLayout layout = new GridLayout();
		layout.marginWidth = 0;
		inheritanceClient.setLayout(layout);

		this.inheritanceComposite = new InheritanceComposite(inheritanceClient, this.commandStack, getWidgetFactory());
		GridData gridData = new GridData();
		gridData.horizontalAlignment = GridData.FILL;
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		this.inheritanceComposite.getControl().setLayoutData(gridData);
		
		return section;
	}
	
	//TODO talk to JavaEditor people about what we can do to hook in TabbedProperties for the JavaEditor
	
	public void doPopulate(EObject obj) {
		this.entity = (IEntity) obj;
		this.entityNameCombo.populate(obj);
		this.attributeOverridesComposite.populate(obj);
		this.inheritanceComposite.populate(obj);
		if (this.entity != null) {
			this.tableComposite.populate(this.entity.getTable());
		}
		else {
			this.tableComposite.populate(null);					
		}
	}
	
	public void doPopulate() {
		this.entityNameCombo.populate();
		this.tableComposite.populate();
		this.attributeOverridesComposite.populate();
		this.inheritanceComposite.populate();
	}
	
	protected void engageListeners() {
	}
	
	protected void disengageListeners() {
	}
	
	@Override
	public void dispose() {
		this.entityNameCombo.dispose();
		this.tableComposite.dispose();
		this.attributeOverridesComposite.dispose();
		this.inheritanceComposite.dispose();
		super.dispose();
	}
	
	protected IEntity getEntity() {
		return this.entity;
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
	
	private class TableNameHolder extends TableHolder implements StringHolder {
		
		TableNameHolder(ITable table) {
			super(table);
		}
			
		public int featureId() {
			return JpaCoreMappingsPackage.ITABLE__SPECIFIED_NAME;
		}
		
		public int defaultFeatureId() {
			return JpaCoreMappingsPackage.ITABLE__DEFAULT_NAME;
		}
		
		public String defaultItem() {
			String defaultName = getTable().getDefaultName();
			if (defaultName != null) {
				return NLS.bind(JpaUiMappingsMessages.EntityComposite_tableDefault, defaultName);
			}
			return JpaUiMappingsMessages.EntityComposite_tableNoDefaultSpecified;
		}
		
		public String getString() {
			return getTable().getSpecifiedName();
		}
		
		public void setString(String newName) {
			getTable().setSpecifiedName(newName);
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