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
		Composite parent,
		Object contentNodeId,
		TabbedPropertySheetWidgetFactory widgetFactory) {

		if (contentNodeId instanceof EntityMappings) {
			return new XmlEntityMappingsDetailsPage(parent, widgetFactory);
		}

		if (contentNodeId instanceof XmlPersistentType) {
			return new XmlPersistentTypeDetailsPage(parent, widgetFactory);
		}

		if (contentNodeId instanceof XmlPersistentAttribute) {
			return new XmlPersistentAttributeDetailsPage(parent, widgetFactory);
		}

		return null;
	}
}