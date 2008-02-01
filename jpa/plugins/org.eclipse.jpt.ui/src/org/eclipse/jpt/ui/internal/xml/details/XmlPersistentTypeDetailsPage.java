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
import org.eclipse.jpt.core.internal.context.base.ITypeMapping;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.context.orm.XmlTypeMapping;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;
import org.eclipse.jpt.ui.internal.JptUiMessages;
import org.eclipse.jpt.ui.internal.details.PersistentTypeDetailsPage;
import org.eclipse.jpt.ui.internal.java.details.ITypeMappingUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.EmbeddableUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.EntityUiProvider;
import org.eclipse.jpt.ui.internal.java.mappings.properties.MappedSuperclassUiProvider;
import org.eclipse.jpt.ui.internal.xml.JptUiXmlMessages;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.PageBook;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * The default implementation of the details page used for the XML persistent
 * attribute.
 *
 * @see XmlPersistentType
 *
 * @version 2.0
 * @since 2.0
 */
public class XmlPersistentTypeDetailsPage extends PersistentTypeDetailsPage<XmlPersistentType>
{
	/**
	 * Storing these here instead of querying IJpaPlatformUI, because the orm.xml
	 * schema is not extensible. We only need to support extensibility for java.
	 */
	private List<ITypeMappingUiProvider<? extends ITypeMapping>> xmlTypeMappingUiProviders;

	/**
	 * Creates a new <code>XmlPersistentTypeDetailsPage</code>.
	 *
	 * @param parent The parent container
	 * @param widgetFactory The factory used to create various common widgets
	 */
	public XmlPersistentTypeDetailsPage(Composite parent,
	                                    TabbedPropertySheetWidgetFactory widgetFactory) {

		super(parent, widgetFactory);
	}

	protected void addXmlTypeMappingUiProvidersTo(Collection<ITypeMappingUiProvider<? extends ITypeMapping>> providers) {
		providers.add(EntityUiProvider.instance());
		providers.add(MappedSuperclassUiProvider.instance());
		providers.add(EmbeddableUiProvider.instance());
	}

	private PropertyValueModel<XmlTypeMapping<? extends TypeMapping>> buildMappingHolder() {
		return new TransformationPropertyValueModel<XmlPersistentType, XmlTypeMapping<? extends TypeMapping>>(getSubjectHolder()) {
			@Override
			protected XmlTypeMapping<? extends TypeMapping> transform_(XmlPersistentType value) {
				return value.getMapping();
			}
		};
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	protected void initializeLayout(Composite container) {

		// Java class widgets
		XmlJavaClassChooser xmlJavaClassChooser =
			new XmlJavaClassChooser(this, container);

		buildLabeledComposite(
			container,
			JptUiXmlMessages.PersistentTypePage_javaClassLabel,
			xmlJavaClassChooser.getControl()
		);

		// Type Mapping widgets
		ComboViewer typeMappingCombo = buildTypeMappingCombo(container);

		buildLabeledComposite(
			container,
			JptUiMessages.PersistentTypePage_mapAs,
			typeMappingCombo.getControl().getParent()
		);

		// Metadata complete widget
		MetaDataCompleteComboViewer metadataCompleteComboViewer =
			new MetaDataCompleteComboViewer(this, buildMappingHolder(), container);

		buildLabeledComposite(
			container,
			JptUiXmlMessages.PersistentTypePage_MetadataCompleteLabel,
			metadataCompleteComboViewer.getControl()
		);

		// Access widgets
		new AccessTypeComposite(this, buildMappingHolder(), container);

		// Type mapping pane
		PageBook typeMappingPageBook = buildTypeMappingPageBook(container);

		GridData gridData = new GridData();
		gridData.horizontalAlignment       = SWT.FILL;
		gridData.verticalAlignment         = SWT.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace   = true;

		typeMappingPageBook.setLayoutData(gridData);
	}

	/*
	 * (non-Javadoc)
	 */
	@Override
	public ListIterator<ITypeMappingUiProvider<? extends ITypeMapping>> typeMappingUiProviders() {
		if (this.xmlTypeMappingUiProviders == null) {
			this.xmlTypeMappingUiProviders = new ArrayList<ITypeMappingUiProvider<? extends ITypeMapping>>();
			this.addXmlTypeMappingUiProvidersTo(this.xmlTypeMappingUiProviders);
		}
		return new CloneListIterator<ITypeMappingUiProvider<? extends ITypeMapping>>(this.xmlTypeMappingUiProviders);
	}
}