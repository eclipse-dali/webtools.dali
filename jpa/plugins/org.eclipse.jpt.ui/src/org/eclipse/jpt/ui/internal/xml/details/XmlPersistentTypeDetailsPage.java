/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved. This
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
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
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
import org.eclipse.jpt.ui.internal.xml.details.AccessTypeComposite.AccessHolder;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class XmlPersistentTypeDetailsPage extends PersistentTypeDetailsPage<XmlPersistentType>
{
	private AccessTypeComposite accessComboViewer;

	private XmlJavaClassChooser javaClassChooser;

	private MetaDataCompleteComboViewer metadataCompleteComboViewer;

	//Storing these here instead of querying IJpaPlatformUI, because the orm.xml schema
	//is not extensible.  We only need to support extensibility for java
	private List<ITypeMappingUiProvider<? extends ITypeMapping>> xmlTypeMappingUiProviders;

	public XmlPersistentTypeDetailsPage(PropertyValueModel<? extends XmlPersistentType> subjectHolder,
	                                    Composite parent,
	                                    TabbedPropertySheetWidgetFactory widgetFactory) {

		super(subjectHolder, parent, widgetFactory);
	}

	protected void addXmlTypeMappingUiProvidersTo(Collection<ITypeMappingUiProvider<? extends ITypeMapping>> providers) {
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

		this.javaClassChooser.populate();
		this.metadataCompleteComboViewer.populate();
		this.accessComboViewer.populate();
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
		this.accessComboViewer = CommonWidgets.buildAccessTypeComboViewer(buildAccessTypeHolder(), composite, getWidgetFactory());
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

	private PropertyValueModel<? extends AccessTypeComposite.AccessHolder<XmlTypeMapping<? extends TypeMapping>>> buildAccessTypeHolder() {
		return new TransformationPropertyValueModel<XmlPersistentType, AccessTypeComposite.AccessHolder<XmlTypeMapping<? extends TypeMapping>>>(
			getSubjectHolder()) {

			@Override
			protected AccessHolder<XmlTypeMapping<? extends TypeMapping>> transform(XmlPersistentType value) {
				XmlTypeMapping<? extends TypeMapping> mapping = subject().getMapping();
				return new MyAccessHolder(mapping);
			}
		};
	}

	@Override
	public ListIterator<ITypeMappingUiProvider<? extends ITypeMapping>> typeMappingUiProviders() {
		if (this.xmlTypeMappingUiProviders == null) {
			this.xmlTypeMappingUiProviders = new ArrayList<ITypeMappingUiProvider<? extends ITypeMapping>>();
			this.addXmlTypeMappingUiProvidersTo(this.xmlTypeMappingUiProviders);
		}
		return new CloneListIterator<ITypeMappingUiProvider<? extends ITypeMapping>>(this.xmlTypeMappingUiProviders);
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