/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.details;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.resource.common.JpaEObject;
import org.eclipse.jpt.core.internal.resource.orm.OrmPackage;
import org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaDetailsPage;
import org.eclipse.jpt.ui.internal.mappings.details.StringWithDefaultChooser;
import org.eclipse.jpt.ui.internal.mappings.details.StringWithDefaultChooser.StringHolder;
import org.eclipse.jpt.ui.internal.xml.JptUiXmlMessages;
import org.eclipse.jpt.ui.internal.xml.details.AccessTypeComposite.AccessHolder;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class XmlEntityMappingsDetailsPage extends BaseJpaDetailsPage<EntityMappings>
{
	private AccessTypeComposite accessComboViewer;
	private PersistenceUnitMetadataSection persistenceUnitMetadataSection;
	private StringWithDefaultChooser xmlCatalogChooser;
	private XmlPackageChooser xmlPackageChooser;
	private StringWithDefaultChooser xmlSchemaChooser;

	public XmlEntityMappingsDetailsPage(PropertyValueModel<? extends EntityMappings> subjectHolder,
	                                    Composite parent,
	                                    TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	@Override
	protected void disengageListeners() {
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
	protected void doPopulate() {
		super.doPopulate();
		this.xmlPackageChooser.populate();
		this.xmlSchemaChooser.populate();
		this.xmlCatalogChooser.populate();
		this.accessComboViewer.populate();
		this.persistenceUnitMetadataSection.populate();
	}

	@Override
	protected void engageListeners() {
	}

	@Override
	protected void initializeLayout(Composite composite) {
		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
		GridLayout layout = new GridLayout(2, false);
		layout.horizontalSpacing = 6;
		composite.setLayout(layout);

		GridData gridData;

		CommonWidgets.buildPackageLabel(composite, getWidgetFactory());

		this.xmlPackageChooser = CommonWidgets.buildXmlPackageChooser(getSubjectHolder(), composite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.xmlPackageChooser.getControl().setLayoutData(gridData);
		helpSystem.setHelp(xmlPackageChooser.getControl(), IJpaHelpContextIds.ENTITY_ORM_PACKAGE);


		CommonWidgets.buildSchemaLabel(composite, getWidgetFactory());

		this.xmlSchemaChooser = CommonWidgets.buildSchemaChooser(getSubjectHolder(), composite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.xmlSchemaChooser.getCombo().setLayoutData(gridData);
		helpSystem.setHelp(xmlSchemaChooser.getControl(), IJpaHelpContextIds.ENTITY_ORM_SCHEMA);


		CommonWidgets.buildCatalogLabel(composite, getWidgetFactory());

		this.xmlCatalogChooser = CommonWidgets.buildCatalogChooser(getSubjectHolder(), composite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.xmlCatalogChooser.getCombo().setLayoutData(gridData);
		helpSystem.setHelp(xmlCatalogChooser.getControl(), IJpaHelpContextIds.ENTITY_ORM_CATALOG);


		CommonWidgets.buildAccessLabel(composite, getWidgetFactory());

		this.accessComboViewer = CommonWidgets.buildAccessTypeComboViewer(getSubjectHolder(), composite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.accessComboViewer.getControl().setLayoutData(gridData);
		helpSystem.setHelp(accessComboViewer.getControl(), IJpaHelpContextIds.ENTITY_ORM_ACCESS);

		this.persistenceUnitMetadataSection = new PersistenceUnitMetadataSection(buildPersistentUnitMetadaHolder(), composite, getWidgetFactory());

		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		this.persistenceUnitMetadataSection.getSection().setLayoutData(gridData);
	}


	private PropertyValueModel<PersistenceUnitMetadata> buildPersistentUnitMetadaHolder() {
		return new TransformationPropertyValueModel<EntityMappings, PersistenceUnitMetadata>(getSubjectHolder()) {
			@Override
			protected PersistenceUnitMetadata transform_(EntityMappings value) {
				return value.getPersistenceUnitMetadata();
			}
		};
	}


	private class CatalogHolder extends JpaEObject implements StringHolder {
		private EntityMappingsInternal entityMappings;
		CatalogHolder(EntityMappings entityMappings) {
			super();
			this.entityMappings = (EntityMappingsInternal) entityMappings;
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

		public Class featureClass() {
			return EntityMappingsInternal.class;
		}

		public int featureId() {
			return OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_CATALOG;
		}

		public String getString() {
			return this.entityMappings.getSpecifiedCatalog();
		}

		public void setString(String newCatalog) {
			this.entityMappings.setSpecifiedCatalog(newCatalog);
		}

		public boolean supportsDefault() {
			return true;
		}

		public EObject wrappedObject() {
			return this.entityMappings;
		}
	}

	private class MyAccessHolder extends JpaEObject implements AccessHolder{

		private EntityMappingsInternal entityMappings;
		MyAccessHolder(EntityMappings entityMappings) {
			super();
			this.entityMappings = (EntityMappingsInternal) entityMappings;
		}
		public Class featureClass() {
			return EntityMappingsInternal.class;
		}

		public int featureId() {
			return OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_ACCESS;
		}

		public AccessType getAccess() {
			return entityMappings.getSpecifiedAccess();
		}

		public void setAccess(AccessType accessType) {
			entityMappings.setSpecifiedAccess(accessType);
		}

		public EObject wrappedObject() {
			return this.entityMappings;
		}

	}

	private class SchemaHolder extends JpaEObject implements StringHolder {
		private EntityMappingsInternal entityMappings;
		SchemaHolder(EntityMappings entityMappings) {
			super();
			this.entityMappings = (EntityMappingsInternal) entityMappings;
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

		public Class featureClass() {
			return EntityMappingsInternal.class;
		}

		public int featureId() {
			return OrmPackage.ENTITY_MAPPINGS_INTERNAL__SPECIFIED_SCHEMA;
		}

		public String getString() {
			return this.entityMappings.getSpecifiedSchema();
		}

		public void setString(String newSchema) {
			this.entityMappings.setSpecifiedSchema(newSchema);
		}

		public boolean supportsDefault() {
			return true;
		}

		public EObject wrappedObject() {
			return this.entityMappings;
		}
	}
}
