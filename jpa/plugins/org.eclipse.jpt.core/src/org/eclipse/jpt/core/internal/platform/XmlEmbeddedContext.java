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
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.core.internal.IAttributeMapping;
import org.eclipse.jpt.core.internal.IJpaPlatform;
import org.eclipse.jpt.core.internal.IMappingKeys;
import org.eclipse.jpt.core.internal.content.java.mappings.JavaEmbedded;
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.IEmbedded;
import org.eclipse.jpt.core.internal.platform.XmlAttributeOverrideContext.ParentContext;
import org.eclipse.jpt.core.internal.resource.orm.OrmFactory;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class XmlEmbeddedContext
	extends XmlAttributeContext
{
	private Collection<XmlAttributeOverrideContext> attributeOverrideContexts;

	public XmlEmbeddedContext(IContext parentContext, XmlEmbedded mapping) {
		super(parentContext, mapping);
		this.attributeOverrideContexts = buildAttributeOverrideContexts();
	}
	
	protected Collection<XmlAttributeOverrideContext> buildAttributeOverrideContexts() {
		Collection<XmlAttributeOverrideContext> contexts = new ArrayList<XmlAttributeOverrideContext>();
		for (IAttributeOverride attributeOverride : getEmbedded().getAttributeOverrides()) {
			contexts.add(new XmlAttributeOverrideContext(buildParentContext(), attributeOverride));
		}
		
		return contexts;
	}
	private ParentContext buildParentContext() {
		return new XmlAttributeOverrideContext.ParentContext() {
			public void refreshDefaults(DefaultsContext defaults, IProgressMonitor monitor) {
				XmlEmbeddedContext.this.refreshDefaults(defaults, monitor);
			}
			public IJpaPlatform getPlatform() {
				return XmlEmbeddedContext.this.getPlatform();
			}
			public IContext getParentContext() {
				return XmlEmbeddedContext.this.getParentContext();
			}
			public void addToMessages(List<IMessage> messages) {
				XmlEmbeddedContext.this.addToMessages(messages);
			}
			public IAttributeOverride javaAttributeOverride(String overrideName) {
				if (getEmbedded().isVirtual()) {
					return getJavaEmbedded().attributeOverrideNamed(overrideName);
				}
				//if the xml mapping is specified, then it is as if no annotations are 
				//specified on the java mapping, so return null
				return null;
			}
		};
	}
	
	@Override
	public void refreshDefaults(DefaultsContext parentDefaults, IProgressMonitor monitor) {
		super.refreshDefaults(parentDefaults, monitor);
		refreshDefaultAttributeOverrides();
		for (XmlAttributeOverrideContext context : this.attributeOverrideContexts) {
			context.refreshDefaults(parentDefaults, monitor);
		}
	}

	
	protected void refreshDefaultAttributeOverrides() {
		for (Iterator<String> i = getEmbedded().allOverridableAttributeNames(); i.hasNext(); ) {
			String override = i.next();
			if (!getEmbedded().containsAttributeOverride(override)) {
				XmlAttributeOverride attributeOverride = OrmFactory.eINSTANCE.createXmlAttributeOverride(new IEmbedded.AttributeOverrideOwner(getEmbedded()));
				getEmbedded().getDefaultAttributeOverrides().add(attributeOverride);
				attributeOverride.setName(override);
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
		return getEmbedded().getPersistentAttribute().typeMapping();
	}
	
	private XmlEmbedded getEmbedded() {
		return (XmlEmbedded) attributeMapping();
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
		
		for (XmlAttributeOverrideContext aoContext : this.attributeOverrideContexts) {
			aoContext.addToMessages(messages);
		}
	}
}
