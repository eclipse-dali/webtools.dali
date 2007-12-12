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

import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.context.base.ITransientMapping;
import org.eclipse.jpt.core.internal.resource.orm.AttributeMapping;
import org.eclipse.jpt.core.internal.resource.orm.Basic;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.core.internal.resource.orm.Transient;
import org.eclipse.jpt.core.internal.resource.orm.TypeMapping;


public class XmlTransientMapping extends XmlAttributeMapping implements ITransientMapping
{
	protected Transient transientResource;
	
	protected XmlTransientMapping(XmlPersistentAttribute parent) {
		super(parent);
	}

	@Override
	protected void initializeOn(XmlAttributeMapping newMapping) {
		newMapping.initializeFromXmlTransientMapping(this);
	}

	@Override
	public int xmlSequence() {
		return 8;
	}

	public String getKey() {
		return IMappingKeys.TRANSIENT_ATTRIBUTE_MAPPING_KEY;
	}

	@Override
	public AttributeMapping addToResourceModel(TypeMapping typeMapping) {
		Basic basic = OrmFactory.eINSTANCE.createBasic();
		if (typeMapping.getAttributes() == null) {
			typeMapping.setAttributes(OrmFactory.eINSTANCE.createAttributes());
		}
		typeMapping.getAttributes().getBasics().add(basic);
		return basic;
	}
	
	@Override
	public void removeFromResourceModel(TypeMapping typeMapping) {
		typeMapping.getAttributes().getTransients().remove(this.transientResource);
		if (typeMapping.getAttributes().isAllFeaturesUnset()) {
			typeMapping.setAttributes(null);
		}
	}
	
	public void initialize(Transient transientResource) {
		this.transientResource = transientResource;
	}
	
	public void update(Transient transientResource) {
		this.transientResource = transientResource;
	}
}
