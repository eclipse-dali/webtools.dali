/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.details;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.resource.common.JpaEObject;
import org.eclipse.jpt.core.internal.resource.orm.OrmPackage;
import org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.internal.resource.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.ui.internal.IJpaHelpContextIds;
import org.eclipse.jpt.ui.internal.details.BaseJpaController;
import org.eclipse.jpt.ui.internal.mappings.details.StringWithDefaultChooser;
import org.eclipse.jpt.ui.internal.mappings.details.StringWithDefaultChooser.StringHolder;
import org.eclipse.jpt.ui.internal.xml.JptUiXmlMessages;
import org.eclipse.jpt.ui.internal.xml.details.AccessTypeComboViewer.AccessHolder;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.help.IWorkbenchHelpSystem;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class PersistenceUnitMetadataSection extends BaseJpaController<PersistenceUnitMetadata>
{

	private AccessTypeComboViewer accessComboViewer;
	private CascadePersistCheckBox cascadePersistCheckBox;
	private Section section;
	private StringWithDefaultChooser xmlCatalogChooser;
	private XmlMappingMetadataCompleteCheckBox xmlMappingMetadataCompleteCheckBox;
	private StringWithDefaultChooser xmlSchemaChooser;

	public PersistenceUnitMetadataSection(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, widgetFactory);
	}

	private CascadePersistCheckBox buildCascadePersistCheckBox(Composite parent) {
		return new CascadePersistCheckBox(parent, getWidgetFactory());
	}

	@Override
	protected void buildWidget(Composite parent) {
		IWorkbenchHelpSystem helpSystem = PlatformUI.getWorkbench().getHelpSystem();
	    this.section = getWidgetFactory().createSection(parent, SWT.FLAT | ExpandableComposite.TWISTIE | ExpandableComposite.TITLE_BAR);
	    this.section.setText(JptUiXmlMessages.XMLEntityMappingsPage_PersistenceUnitSection);

		Composite persistenceUnitComposite = getWidgetFactory().createComposite(this.section);
		this.section.setClient(persistenceUnitComposite);

		GridLayout layout = new GridLayout(2, false);
		layout.marginWidth = 1;
		persistenceUnitComposite.setLayout(layout);


	    this.xmlMappingMetadataCompleteCheckBox = buildXmlMappingMetadataCompleteCheckBox(persistenceUnitComposite);
		GridData gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		this.xmlMappingMetadataCompleteCheckBox.getControl().setLayoutData(gridData);
		helpSystem.setHelp(xmlMappingMetadataCompleteCheckBox.getControl(), IJpaHelpContextIds.ENTITY_ORM_XML);

		CommonWidgets.buildSchemaLabel(persistenceUnitComposite, getWidgetFactory());

		this.xmlSchemaChooser = CommonWidgets.buildSchemaChooser(persistenceUnitComposite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.xmlSchemaChooser.getCombo().setLayoutData(gridData);
		helpSystem.setHelp(xmlSchemaChooser.getControl(), IJpaHelpContextIds.ENTITY_ORM_SCHEMA);

		CommonWidgets.buildCatalogLabel(persistenceUnitComposite, getWidgetFactory());

		this.xmlCatalogChooser = CommonWidgets.buildCatalogChooser(persistenceUnitComposite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.xmlCatalogChooser.getCombo().setLayoutData(gridData);
		helpSystem.setHelp(xmlCatalogChooser.getControl(), IJpaHelpContextIds.ENTITY_ORM_CATALOG);

		CommonWidgets.buildAccessLabel(persistenceUnitComposite, getWidgetFactory());

		this.accessComboViewer = CommonWidgets.buildAccessTypeComboViewer(persistenceUnitComposite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.accessComboViewer.getControl().setLayoutData(gridData);
		helpSystem.setHelp(accessComboViewer.getControl(), IJpaHelpContextIds.ENTITY_ORM_ACCESS);


	    this.cascadePersistCheckBox = buildCascadePersistCheckBox(persistenceUnitComposite);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalSpan = 2;
		this.cascadePersistCheckBox.getControl().setLayoutData(gridData);
		helpSystem.setHelp(cascadePersistCheckBox.getControl(), IJpaHelpContextIds.ENTITY_ORM_CASCADE);

	}

	private XmlMappingMetadataCompleteCheckBox buildXmlMappingMetadataCompleteCheckBox(Composite parent) {
		return new XmlMappingMetadataCompleteCheckBox(parent, getWidgetFactory());
	}

	@Override
	protected void disengageListeners() {
	}

	@Override
	public void dispose() {
		this.xmlMappingMetadataCompleteCheckBox.dispose();
		this.xmlSchemaChooser.dispose();
		this.xmlCatalogChooser.dispose();
		this.accessComboViewer.dispose();
		this.cascadePersistCheckBox.dispose();
		super.dispose();
	}

	@Override
	protected void doPopulate() {
		if (subject() != null) {
			this.accessComboViewer.populate(new MyAccessHolder(subject().getPersistenceUnitDefaults()));
			this.xmlSchemaChooser.populate(new SchemaHolder(subject().getPersistenceUnitDefaults()));
			this.xmlCatalogChooser.populate(new CatalogHolder(subject().getPersistenceUnitDefaults()));
			this.cascadePersistCheckBox.populate(subject().getPersistenceUnitDefaults());
		}
		else {
			this.accessComboViewer.populate(new MyAccessHolder(null));
			this.xmlSchemaChooser.populate(new SchemaHolder(null));
			this.xmlCatalogChooser.populate(new CatalogHolder(null));
			this.cascadePersistCheckBox.populate(null);
		}
	}

	@Override
	protected void engageListeners() {
	}

	@Override
	public Control getControl() {
		return getSection();
	}

	public Section getSection() {
		return this.section;
	}

	private class CatalogHolder extends JpaEObject implements StringHolder {
		private PersistenceUnitDefaults persistenceUnitDefaults;
		CatalogHolder(PersistenceUnitDefaults persistenceUnitDefaults) {
			super();
			this.persistenceUnitDefaults = persistenceUnitDefaults;
		}

		public int defaultFeatureId() {
			throw new UnsupportedOperationException();
		}

		public String defaultItem() {
			return JptUiXmlMessages.PersistenceUnitMetadataSection_CatalogDefault;
		}

		public Class featureClass() {
			return PersistenceUnitDefaults.class;
		}

		public int featureId() {
			return OrmPackage.PERSISTENCE_UNIT_DEFAULTS__CATALOG;
		}

		public String getString() {
			return this.persistenceUnitDefaults.getCatalog();
		}

		public void setString(String string) {
			this.persistenceUnitDefaults.setCatalog(string);
		}

		public boolean supportsDefault() {
			return false;
		}

		public EObject wrappedObject() {
			return this.persistenceUnitDefaults;
		}
	}

	private class MyAccessHolder extends JpaEObject implements AccessHolder{

		private PersistenceUnitDefaultsInternal persistenceUnitDefaults;
		MyAccessHolder(PersistenceUnitDefaults persistenceUnitDefaultsInternal) {
			super();
			this.persistenceUnitDefaults = (PersistenceUnitDefaultsInternal) persistenceUnitDefaultsInternal;
		}
		public Class featureClass() {
			return PersistenceUnitDefaults.class;
		}

		public int featureId() {
			return OrmPackage.PERSISTENCE_UNIT_DEFAULTS__ACCESS;
		}

		public AccessType getAccess() {
			return persistenceUnitDefaults.getAccess();
		}

		public void setAccess(AccessType accessType) {
			persistenceUnitDefaults.setAccess(accessType);
		}

		public EObject wrappedObject() {
			return this.persistenceUnitDefaults;
		}
	}

	private class SchemaHolder extends JpaEObject implements StringHolder {
		private PersistenceUnitDefaults persistenceUnitDefaults;
		SchemaHolder(PersistenceUnitDefaults persistenceUnitDefaults) {
			super();
			this.persistenceUnitDefaults = persistenceUnitDefaults;
		}

		public int defaultFeatureId() {
			throw new UnsupportedOperationException();
		}

		public String defaultItem() {
			return JptUiXmlMessages.PersistenceUnitMetadataSection_SchemaDefault;
		}

		public Class featureClass() {
			return PersistenceUnitDefaults.class;
		}

		public int featureId() {
			return OrmPackage.PERSISTENCE_UNIT_DEFAULTS__SCHEMA;
		}

		public String getString() {
			return this.persistenceUnitDefaults.getSchema();
		}

		public void setString(String newSchema) {
			this.persistenceUnitDefaults.setSchema(newSchema);
		}

		public boolean supportsDefault() {
			return false;
		}

		public EObject wrappedObject() {
			return this.persistenceUnitDefaults;
		}
	}
}