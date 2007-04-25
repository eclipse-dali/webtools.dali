/*******************************************************************************
 *  Copyright (c) 2006 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.ui.internal.xml.details;

import org.eclipse.jpt.core.internal.JpaCorePlugin;
import org.eclipse.jpt.core.internal.content.orm.IXmlContentNodes;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsPage;
import org.eclipse.jpt.ui.internal.details.IJpaDetailsProvider;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.views.properties.tabbed.TabbedPropertySheetWidgetFactory;

public class XmlDetailsProvider 
	implements IJpaDetailsProvider 
{
	public XmlDetailsProvider() {
		super();
	}
	
	public String fileContentType() {
		return JpaCorePlugin.ORM_XML_CONTENT_TYPE;
	}
	
	public IJpaDetailsPage buildDetailsPage(
			Composite parentComposite, Object contentNodeId, TabbedPropertySheetWidgetFactory widgetFactory) {
		if (contentNodeId.equals(IXmlContentNodes.ENTITY_MAPPINGS_ID)) {
			return new XmlEntityMappingsDetailsPage(parentComposite, widgetFactory);
		}
		else if (contentNodeId.equals(IXmlContentNodes.PERSISTENT_TYPE_ID)) {
			return new XmlPersistentTypeDetailsPage(parentComposite, widgetFactory);
		}
		else if (contentNodeId.equals(IXmlContentNodes.PERSISTENT_ATTRIBUTE_ID)) {
			return new XmlPersistentAttributeDetailsPage(parentComposite, widgetFactory);
		}
		
		return null;
	}
	
	public void dispose() {
		// no op ... for now
	}
}
