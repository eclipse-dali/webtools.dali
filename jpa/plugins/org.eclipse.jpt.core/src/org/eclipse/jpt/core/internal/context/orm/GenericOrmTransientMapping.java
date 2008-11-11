/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.core.resource.orm.AbstractXmlTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlTransient;


public class GenericOrmTransientMapping extends AbstractOrmAttributeMapping<XmlTransient> implements OrmTransientMapping
{
	
	public GenericOrmTransientMapping(OrmPersistentAttribute parent) {
		super(parent);
	}

	public void initializeOn(OrmAttributeMapping newMapping) {
		newMapping.initializeFromOrmTransientMapping(this);
	}

	public int getXmlSequence() {
		return 90;
	}

	public String getKey() {
		return MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY;
	}

	public XmlTransient addToResourceModel(AbstractXmlTypeMapping typeMapping) {
		XmlTransient transientResource = OrmFactory.eINSTANCE.createXmlTransientImpl();
		getPersistentAttribute().initialize(transientResource);
		typeMapping.getAttributes().getTransients().add(transientResource);
		return transientResource;
	}
	
	public void removeFromResourceModel(AbstractXmlTypeMapping typeMapping) {
		typeMapping.getAttributes().getTransients().remove(this.resourceAttributeMapping);
	}
}
