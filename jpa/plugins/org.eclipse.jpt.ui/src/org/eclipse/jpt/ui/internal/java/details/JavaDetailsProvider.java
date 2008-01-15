/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.java.details;

import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.context.base.IPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsPage;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsProvider;
import org.eclipse.jpt.utility.internal.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

/**
 * This provider is responsible for creating the <code>IJpaDetailsPage</code>
 * when the information comes from the Java source file.
 *
 * @version 2.0
 * @since 1.0
 */
public class JavaDetailsProvider
	implements IJpaDetailsProvider
{
	/**
	 * Creates a new <code>JavaDetailsProvider</code>.
	 */
	public JavaDetailsProvider() {
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

//		if (contentNodeId.equals(IJavaContentNodes.PERSISTENT_TYPE_ID)) {
		if (contentNodeId instanceof IJavaPersistentType) {
			return new JavaPersistentTypeDetailsPage(
				buildPersistenceTypeHolder(subjectHolder),
				parentComposite,
				widgetFactory);
		}

//		if (contentNodeId.equals(IJavaContentNodes.PERSISTENT_ATTRIBUTE_ID)) {
		if (contentNodeId instanceof IJavaPersistentAttribute) {
			return new JavaPersistentAttributeDetailsPage(
				buildPersistenceAttributeHolder(subjectHolder),
				parentComposite,
				widgetFactory
			);
		}

		return null;
	}

	private PropertyValueModel<IPersistentAttribute> buildPersistenceAttributeHolder(PropertyValueModel<IJpaContextNode> subjectHolder) {
		return new TransformationPropertyValueModel<IJpaContextNode, IPersistentAttribute>(subjectHolder) {
			@Override
			protected IPersistentAttribute transform_(IJpaContextNode value) {
				return (value instanceof IPersistentAttribute) ? (IPersistentAttribute) value : null;
			}
		};
	}

	private PropertyValueModel<IJavaPersistentType> buildPersistenceTypeHolder(PropertyValueModel<IJpaContextNode> subjectHolder) {
		return new TransformationPropertyValueModel<IJpaContextNode, IJavaPersistentType>(subjectHolder) {
			@Override
			protected IJavaPersistentType transform_(IJpaContextNode value) {
				return (value instanceof IJavaPersistentType) ? (IJavaPersistentType) value : null;
			}
		};
	}
}