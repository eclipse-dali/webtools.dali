/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.details;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jpt.core.internal.context.base.AccessType;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.context.orm.XmlTypeMapping;
import org.eclipse.jpt.core.internal.resource.orm.OrmPackage;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;
import org.eclipse.jpt.ui.internal.details.PersistentTypeDetailsPage;
import org.eclipse.jpt.ui.internal.java.details.ITypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.EmbeddableUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.EntityUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.MappedSuperclassUiProvider;
import org.eclipse.jpt.ui.internal.xml.JptUiXmlMessages;
import org.eclipse.jpt.ui.internal.xml.details.AccessTypeComboViewer.AccessHolder;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class XmlPersistentTypeDetailsPage extends PersistentTypeDetailsPage<XmlPersistentType>
{
	private AccessTypeComboViewer accessComboViewer;

	private XmlJavaClassChooser javaClassChooser;

	private MetaDataCompleteComboViewer metadataCompleteComboViewer;

	//Storing these here instead of querying IJpaPlatformUI, because the orm.xml schema
	//is not extensible.  We only need to support extensibility for java
	private List<ITypeMappingUiProvider> xmlTypeMappingUiProviders;

	public XmlPersistentTypeDetailsPage(Composite parent, TabbedPropertySheetWidgetFactory widgetFactory) {
		super(parent, widgetFactory);
	}

	protected void addXmlTypeMappingUiProvidersTo(Collection<ITypeMappingUiProvider> providers) {
		providers.add(EntityUiProvider.instance());
		providers.add(MappedSuperclassUiProvider.instance());
		providers.add(EmbeddableUiProvider.instance());
	}

	private Label buildMetadataCompleteLabel(Composite parent ) {
		return getWidgetFactory().createLabel(parent, JptUiXmlMessages.PersistentTypePage_MetadataCompleteLabel);
	}

	@Override
	public void dispose() {
		this.javaClassChooser.dispose();
		this.metadataCompleteComboViewer.dispose();
		this.accessComboViewer.dispose();
		super.dispose();
	}

	@Override
	protected void doPopulate() {
		super.doPopulate();
		if (subject() == null) {
			this.javaClassChooser.populate(null);
			this.metadataCompleteComboViewer.populate(null);
			this.accessComboViewer.populate(new MyAccessHolder(null));
		}
		else {
			XmlTypeMapping<? extends TypeMapping> mapping = subject().getMapping();
			this.javaClassChooser.populate();
			this.metadataCompleteComboViewer.populate(mapping);
			this.accessComboViewer.populate(new MyAccessHolder(mapping));
		}
	}

	@Override
	protected void initializeLayout(Composite composite) {
		composite.setLayout(new GridLayout(2, false));

		GridData gridData;

		CommonWidgets.buildJavaClassLabel(composite, getWidgetFactory());

		this.javaClassChooser = CommonWidgets.buildJavaClassChooser(composite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.javaClassChooser.getControl().setLayoutData(gridData);

		buildTypeMappingLabel(composite);

		ComboViewer typeMappingCombo = buildTypeMappingCombo(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		typeMappingCombo.getCombo().setLayoutData(gridData);

		buildMetadataCompleteLabel(composite);
		this.metadataCompleteComboViewer = new MetaDataCompleteComboViewer(composite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.metadataCompleteComboViewer.getControl().setLayoutData(gridData);

		CommonWidgets.buildAccessLabel(composite, getWidgetFactory());
		this.accessComboViewer = CommonWidgets.buildAccessTypeComboViewer(composite, getWidgetFactory());
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.BEGINNING;
		gridData.grabExcessHorizontalSpace = true;
		this.accessComboViewer.getControl().setLayoutData(gridData);

		PageBook typeMappingPageBook = buildTypeMappingPageBook(composite);
		gridData = new GridData();
		gridData.horizontalAlignment = SWT.FILL;
		gridData.verticalAlignment = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 2;
		typeMappingPageBook.setLayoutData(gridData);
	}

	@Override
	public ListIterator<ITypeMappingUiProvider> typeMappingUiProviders() {
		if (this.xmlTypeMappingUiProviders == null) {
			this.xmlTypeMappingUiProviders = new ArrayList<ITypeMappingUiProvider>();
			this.addXmlTypeMappingUiProvidersTo(this.xmlTypeMappingUiProviders);
		}
		return new CloneListIterator<ITypeMappingUiProvider>(this.xmlTypeMappingUiProviders);
	}


	private class MyAccessHolder implements AccessHolder<XmlTypeMapping<? extends TypeMapping>> {

		private XmlTypeMapping<? extends TypeMapping> xmlTypeMapping;
		MyAccessHolder(XmlTypeMapping<? extends TypeMapping> xmlTypeMapping) {
			super();
			this.xmlTypeMapping = xmlTypeMapping;
		}
		public Class<XmlTypeMapping> featureClass() {
			return XmlTypeMapping.class;
		}

		public int featureId() {
			return OrmPackage.XML_TYPE_MAPPING__SPECIFIED_ACCESS;
		}

		public AccessType getAccess() {
			return xmlTypeMapping.getSpecifiedAccess();
		}

		public void setAccess(AccessType accessType) {
			xmlTypeMapping.setSpecifiedAccess(accessType);
		}

		public XmlTypeMapping<? extends TypeMapping> wrappedObject() {
			return this.xmlTypeMapping;
		}
	}
}