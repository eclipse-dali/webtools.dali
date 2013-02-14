/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.resource.orm.v2_1.XmlConverter_2_1;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.XmlNamedConverter;

public final class OrmEclipseLinkEntityMappingsConverterContainer
	extends AbstractOrmEclipseLinkConverterContainerImpl
{

	public OrmEclipseLinkEntityMappingsConverterContainer(JpaContextNode parent, Owner owner,  XmlConverterContainer xmlConverterContainer) {
		super(parent, owner, xmlConverterContainer);
	}

	/**
	 * Return those XmlConverters that have a name, otherwise the converters are
	 * JPA 2.1 converters
	 */
	@Override
	protected List<XmlConverter_2_1> getNamedXmlConverters() {
		if (this.isJpa2_1Compatible()) {
			ArrayList<XmlConverter_2_1> xmlConverters = new ArrayList<XmlConverter_2_1>();
			for (XmlConverter_2_1 xmlConverter : this.xmlConverterContainer.getConverters()) {
				if (((XmlNamedConverter) xmlConverter).getName() != null){
					xmlConverters.add(xmlConverter);
				}
			}
		
			return xmlConverters;
		}
		return super.getNamedXmlConverters();
	}
}
