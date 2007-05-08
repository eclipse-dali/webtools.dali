/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.details;

import org.eclipse.emf.common.command.BasicCommandStack;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.AccessType;
import org.eclipse.jpt.core.internal.IJpaContentNode;
import org.eclipse.jpt.core.internal.XmlEObject;
import org.eclipse.jpt.core.internal.content.orm.EntityMappings;
import org.eclipse.jpt.core.internal.content.orm.EntityMappingsInternal;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaDetailsPage;
import org.eclipse.jpt.ui.internal.mappings.details.StringWithDefaultChooser;
import org.eclipse.jpt.ui.internal.mappings.details.StringWithDefaultChooser.StringHolder;
import org.eclipse.jpt.ui.internal.xml.JptUiXmlMessages;
import org.eclipse.jpt.ui.internal.xml.details.AccessTypeComboViewer.AccessHolder;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class XmlEntityMappingsDetailsPage extends BaseJpaDetailsPage
{
	private EntityMappings entityMappings;

	private XmlPackageChooser xmlPackageChooser;
	
	private StringWithDefaultChooser xmlSchemaChooser;
	
	private StringWithDefaultChooser xmlCatalogChooser;
	
	private AccessTypeComboViewer accessComboViewer;
	
	private PersistenceUnitMetadataSection persistenceUnitMetadataSection;
	
	public XmlEntityMappingsDetailsPage(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, new BasicCommandStack(), widgetFactory);
	}

	@Override
	protected void initializeLayout(Composite composite) {
		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
		GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing = 6;
		composite.setLayout(layout);
		
		GridData gridData;

		CommonWidgets.buildPackageLabel(composite, getWidgetFactory());
		
		this.xmlPackageChooser = CommonWidgets.buildXmlPackageChooser(composite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.xmlPackageChooser.getControl().setLayoutData(gridData);
		helpSystem.setHelp(xmlPackageChooser.getControl(), IJpaHelpContextIds.ENTITY_ORM_PACKAGE);

		
		CommonWidgets.buildSchemaLabel(composite, getWidgetFactory());
		
		this.xmlSchemaChooser = CommonWidgets.buildSchemaChooser(composite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.xmlSchemaChooser.getCombo().setLayoutData(gridData);
		helpSystem.setHelp(xmlSchemaChooser.getControl(), IJpaHelpContextIds.ENTITY_ORM_SCHEMA);

		
		CommonWidgets.buildCatalogLabel(composite, getWidgetFactory());
		
		this.xmlCatalogChooser = CommonWidgets.buildCatalogChooser(composite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.xmlCatalogChooser.getCombo().setLayoutData(gridData);
		helpSystem.setHelp(xmlCatalogChooser.getControl(), IJpaHelpContextIds.ENTITY_ORM_CATALOG);
		
		
		CommonWidgets.buildAccessLabel(composite, getWidgetFactory());
		
		this.accessComboViewer = CommonWidgets.buildAccessTypeComboViewer(composite, this.commandStack, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.accessComboViewer.getControl().setLayoutData(gridData);
		helpSystem.setHelp(accessComboViewer.getControl(), IJpaHelpContextIds.ENTITY_ORM_ACCESS);
		
		this.persistenceUnitMetadataSection = new PersistenceUnitMetadataSection(composite, this.commandStack, getWidgetFactory());
		
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		this.persistenceUnitMetadataSection.getSection().setLayoutData(gridData);
	}

	
	@Override
	protected void doPopulate(IJpaContentNode contentNode) {
		this.entityMappings = (EntityMappings) contentNode;
		this.xmlPackageChooser.populate(this.entityMappings);
		this.xmlSchemaChooser.populate(new SchemaHolder(this.entityMappings));
		this.xmlCatalogChooser.populate(new CatalogHolder(this.entityMappings));
		this.accessComboViewer.populate(new MyAccessHolder(this.entityMappings));	
		if (this.entityMappings != null) {
			this.persistenceUnitMetadataSection.populate(this.entityMappings.getPersistenceUnitMetadata());
		}
		else {
			this.persistenceUnitMetadataSection.populate(null);
		}
	}

	@Override
	protected void doPopulate() {
		this.xmlPackageChooser.populate();
		this.xmlSchemaChooser.populate();
		this.xmlCatalogChooser.populate();
		this.accessComboViewer.populate();
		this.persistenceUnitMetadataSection.populate();
	}
	
	@Override
	public void dispose() {
		this.xmlPackageChooser.dispose();
		this.xmlSchemaChooser.dispose();
		this.xmlCatalogChooser.dispose();
		this.accessComboViewer.dispose();
		this.persistenceUnitMetadataSection.dispose();
		super.dispose();
	}
	
	@Override
	protected void engageListeners() {
	}
	
	@Override
	protected void disengageListeners() {
	}

	
	private class MyAccessHolder extends XmlEObject implements AccessHolder{
		
		private EntityMappingsInternal entityMappings;
		MyAccessHolder(EntityMappings entityMappings) {
			super();
			this.entityMappings = (EntityMappingsInternal) entityMappings;
		}
		public void setAccess(AccessType accessType) {
			entityMappings.setSpecifiedAccess(accessType);
		}
	
		public AccessType getAccess() {
			return entityMappings.getSpecifiedAccess();
		}
		
		public Class featureClass() {
			return EntityMappingsInternal.class;
		}
		
		public int featureId() {
			return OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_ACCESS;
		}
		
		public EObject wrappedObject() {
			return this.entityMappings;
		}
		
	}
	
	private class SchemaHolder extends XmlEObject implements StringHolder {
		private EntityMappingsInternal entityMappings;
		SchemaHolder(EntityMappings entityMappings) {
			super();
			this.entityMappings = (EntityMappingsInternal) entityMappings;
		}
		
		public Class featureClass() {
			return EntityMappingsInternal.class;
		}
		
		public int featureId() {
			return OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_SCHEMA;
		}
		
		public boolean supportsDefault() {
			return true;
		}
		
		public int defaultFeatureId() {
			return OrmPackage.ENTITY_MAPPINGS_INTERNAL__DEFAULT_SCHEMA;
		}
		
		public String defaultItem() {
			String defaultSchema = this.entityMappings.getDefaultSchema();
			if (defaultSchema != null) {
				return NLS.bind(JptUiXmlMessages.XMLEntityMappingsPage_SchemaDefault, defaultSchema);
			}
			return JptUiXmlMessages.XMLEntityMappingsPage_SchemaNoDefaultSpecified;
		}
		
		public String getString() {
			return this.entityMappings.getSpecifiedSchema();
		}
		
		public void setString(String newSchema) {
			this.entityMappings.setSpecifiedSchema(newSchema);
		}
		
		public EObject wrappedObject() {
			return this.entityMappings;
		}
	}
	
	private class CatalogHolder extends XmlEObject implements StringHolder {
		private EntityMappingsInternal entityMappings;
		CatalogHolder(EntityMappings entityMappings) {
			super();
			this.entityMappings = (EntityMappingsInternal) entityMappings;
		}
		
		public Class featureClass() {
			return EntityMappingsInternal.class;
		}
		
		public int featureId() {
			return OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_CATALOG;
		}
		
		public boolean supportsDefault() {
			return true;
		}
		
		public int defaultFeatureId() {
			return OrmPackage.ENTITY_MAPPINGS_INTERNAL__DEFAULT_CATALOG;
		}
		
		public String defaultItem() {
			String defaultCatalog = this.entityMappings.getDefaultCatalog();
			if (defaultCatalog != null) {
				return NLS.bind(JptUiXmlMessages.XMLEntityMappingsPage_CatalogDefault, defaultCatalog);
			}
			return JptUiXmlMessages.XMLEntityMappingsPage_CatalogNoDefaultSpecified;
		}
		
		public String getString() {
			return this.entityMappings.getSpecifiedCatalog();
		}
		
		public void setString(String newCatalog) {
			this.entityMappings.setSpecifiedCatalog(newCatalog);
		}
		
		public EObject wrappedObject() {
			return this.entityMappings;
		}
	}
}
