/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa1.context.orm;

import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmAttributeMapping;
import org.eclipse.jpt.core.jpa2.context.MetamodelField;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;

/**
 * This will be used in cases where Java-supported attribute mapping types
 * are not supported by the particular version of the <code>orm.xml</code>
 * file. For example, EclipseLink supports variable 1:1 mappings, but the
 * generic <code>orm.xml</code> file does not.
 */
public class UnsupportedOrmAttributeMapping 
	extends AbstractOrmAttributeMapping<XmlNullAttributeMapping>
{
	public UnsupportedOrmAttributeMapping(OrmPersistentAttribute parent, XmlNullAttributeMapping xmlMapping) {
		super(parent, xmlMapping);
	}

	public String getKey() {
		//this ends up returning the java attribute mapping key
		return this.xmlAttributeMapping.getMappingKey();
	}
	
	public int getXmlSequence() {
		return -1;
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmAttributeMapping(this);
	}

	public void addXmlAttributeMappingTo(Attributes xmlAttributes) {
		throw new UnsupportedOperationException();
	}

	public void removeXmlAttributeMappingFrom(Attributes xmlAttributes) {
		throw new UnsupportedOperationException();
	}


	// ********** metamodel **********  

	@Override
	public MetamodelField getMetamodelField() {
		return null;
	}
}
