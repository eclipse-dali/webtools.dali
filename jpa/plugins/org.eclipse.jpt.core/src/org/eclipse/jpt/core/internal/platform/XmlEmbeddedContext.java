/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbedded;
import org.eclipse.jpt.core.internal.content.orm.OrmFactory;
import org.eclipse.jpt.core.internal.content.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.internal.content.orm.XmlEmbedded;
import org.eclipse.jpt.core.internal.content.orm.XmlTypeMapping;
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.IEmbedded;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class XmlEmbeddedContext
	extends XmlAttributeContext
{
	private Collection<AttributeOverrideContext> attributeOverrideContexts;

	public XmlEmbeddedContext(IContext parentContext, XmlEmbedded mapping) {
		super(parentContext, mapping);
		this.attributeOverrideContexts = buildAttributeOverrideContexts();
	}
	
	protected Collection<AttributeOverrideContext> buildAttributeOverrideContexts() {
		Collection<AttributeOverrideContext> contexts = new ArrayList<AttributeOverrideContext>();
		for (IAttributeOverride attributeOverride : getEmbedded().getAttributeOverrides()) {
			contexts.add(new AttributeOverrideContext(this, attributeOverride));
		}
		
		return contexts;
	}
	
	@Override
	public void refreshDefaults(DefaultsContext parentDefaults) {
		super.refreshDefaults(parentDefaults);
		refreshDefaultAttributeOverrides();
		for (AttributeOverrideContext context : this.attributeOverrideContexts) {
			context.refreshDefaults(parentDefaults);
		}
	}

	
	protected void refreshDefaultAttributeOverrides() {
		for (Iterator<String> i = getEmbedded().allOverridableAttributeNames(); i.hasNext(); ) {
			String override = i.next();
			if (!getEmbedded().containsAttributeOverride(override)) {
				XmlAttributeOverride attributeOverride = OrmFactory.eINSTANCE.createXmlAttributeOverride(new IEmbedded.AttributeOverrideOwner(getEmbedded()));
				attributeOverride.setName(override);
				getEmbedded().getDefaultAttributeOverrides().add(attributeOverride);
			}
		}
		
		Collection<String> attributeNames = CollectionTools.collection(getEmbedded().allOverridableAttributeNames());
	
		//remove any default mappings that are not included in the attributeNames collection
		Collection<IAttributeOverride> overridesToRemove = new ArrayList<IAttributeOverride>();
		for (IAttributeOverride attributeOverride : getEmbedded().getDefaultAttributeOverrides()) {
			if (!attributeNames.contains(attributeOverride.getName())
				|| getEmbedded().containsSpecifiedAttributeOverride(attributeOverride.getName())) {
				overridesToRemove.add(attributeOverride);
			}
		}
		
		getEmbedded().getDefaultAttributeOverrides().removeAll(overridesToRemove);
	}

	protected XmlTypeMapping getXmlTypeMapping() {
		return (XmlTypeMapping) getEmbedded().getPersistentAttribute().typeMapping();
	}
	
	private IEmbedded getEmbedded() {
		return (IEmbedded) attributeMapping();
	}
	
	protected JavaEmbedded getJavaEmbedded() {
		IAttributeMapping javaAttributeMapping = javaAttributeMapping();
		if (javaAttributeMapping != null
				&& javaAttributeMapping.getKey() == IMappingKeys.EMBEDDED_ATTRIBUTE_MAPPING_KEY) {
			return (JavaEmbedded) javaAttributeMapping;
		}
		return null;
	}
	
	@Override
	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		
		for (AttributeOverrideContext aoContext : attributeOverrideContexts) {
			aoContext.addToMessages(messages);
		}
	}
}
