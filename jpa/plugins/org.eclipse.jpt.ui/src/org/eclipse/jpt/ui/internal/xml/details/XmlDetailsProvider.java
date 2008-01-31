/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.details;

import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsPage;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsProvider;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * This provider is responsible for creating the <code>IJpaDetailsPage</code>
 * when the information comes from the XML file (either from the persistence
 * configuration or from the Mappings Descriptor).
 *
 * @version 2.0
 * @since 1.0
 */
public class XmlDetailsProvider
	implements IJpaDetailsProvider
{
	/**
	 * Creates a new <code>XmlDetailsProvider</code>.
	 */
	public XmlDetailsProvider() {
		super();
	}

	/*
	 * (non-Javadoc)
	 */
	public IJpaDetailsPage<? extends IJpaContextNode> buildDetailsPage(
		PropertyValueModel<IJpaContextNode> subjectHolder,
		Composite parentComposite,
		Object contentNodeId,
		TabbedPropertySheetWidgetFactory widgetFactory) {

//		if (contentNodeId.equals(IXmlContentNodes.ENTITY_MAPPINGS_ID)) {
		if (contentNodeId instanceof EntityMappings) {

			return new XmlEntityMappingsDetailsPage(
				buildEntityMappingsHolder(subjectHolder),
				parentComposite,
				widgetFactory
			);
		}

//		if (contentNodeId.equals(IXmlContentNodes.PERSISTENT_TYPE_ID)) {
		if (contentNodeId instanceof XmlPersistentType) {
			return new XmlPersistentTypeDetailsPage(
				buildPersistentTypeHolder(subjectHolder),
				parentComposite,
				widgetFactory);
		}

//		if (contentNodeId.equals(IXmlContentNodes.PERSISTENT_ATTRIBUTE_ID)) {
		if (contentNodeId instanceof XmlPersistentAttribute) {
			return new XmlPersistentAttributeDetailsPage(
				buildPersistentAttributeHolder(subjectHolder),
				parentComposite,
				widgetFactory
			);
		}

		return null;
	}

	private PropertyValueModel<EntityMappings> buildEntityMappingsHolder(PropertyValueModel<IJpaContextNode> subjectHolder) {
		return new TransformationPropertyValueModel<IJpaContextNode, EntityMappings>(subjectHolder) {
			@Override
			protected EntityMappings transform_(IJpaContextNode value) {
				return (value instanceof EntityMappings) ? (EntityMappings) value : null;
			}
		};
	}

	private PropertyValueModel<XmlPersistentAttribute> buildPersistentAttributeHolder(PropertyValueModel<IJpaContextNode> subjectHolder) {
		return new TransformationPropertyValueModel<IJpaContextNode, XmlPersistentAttribute>(subjectHolder) {
			@Override
			protected XmlPersistentAttribute transform_(IJpaContextNode value) {
				return (value instanceof XmlPersistentAttribute) ? (XmlPersistentAttribute) value : null;
			}
		};
	}

	private PropertyValueModel<XmlPersistentType> buildPersistentTypeHolder(PropertyValueModel<IJpaContextNode> subjectHolder) {
		return new TransformationPropertyValueModel<IJpaContextNode, XmlPersistentType>(subjectHolder) {
			@Override
			protected XmlPersistentType transform_(IJpaContextNode value) {
				return (value instanceof XmlPersistentType) ? (XmlPersistentType) value : null;
			}
		};
	}
}