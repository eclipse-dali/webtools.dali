/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.TransientMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.resource.orm.AbstractTypeMapping;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.jpt.core.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlTransient;


public class GenericOrmTransientMapping extends AbstractOrmAttributeMapping<XmlTransient> implements TransientMapping
{
	
	protected GenericOrmTransientMapping(OrmPersistentAttribute parent) {
		super(parent);
	}

	@Override
	protected void initializeOn(AbstractOrmAttributeMapping<? extends XmlAttributeMapping> newMapping) {
		newMapping.initializeFromXmlTransientMapping(this);
	}

	@Override
	public int xmlSequence() {
		return 8;
	}

	public String getKey() {
		return MappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	public XmlTransient addToResourceModel(AbstractTypeMapping typeMapping) {
		XmlTransient transientResource = OrmFactory.eINSTANCE.createTransientImpl();
		persistentAttribute().initialize(transientResource);
		typeMapping.getAttributes().getTransients().add(transientResource);
		return transientResource;
	}
	
	@Override
	public void removeFromResourceModel(AbstractTypeMapping typeMapping) {
		typeMapping.getAttributes().getTransients().remove(this.attributeMapping());
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}
	
	@Override
	public void initialize(XmlTransient transientResource) {
		super.initialize(transientResource);
	}
	
	@Override
	public void update(XmlTransient transientResource) {
		super.update(transientResource);
	}
}
