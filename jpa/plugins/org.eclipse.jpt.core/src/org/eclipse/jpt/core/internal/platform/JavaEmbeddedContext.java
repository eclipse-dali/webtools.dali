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
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.IEmbedded;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public class JavaEmbeddedContext extends JavaAttributeContext
{
	private Collection<AttributeOverrideContext> attributeOverrideContexts;
	
	public JavaEmbeddedContext(IContext parentContext, JavaEmbedded javaEmbedded) {
		super(parentContext, javaEmbedded);
		this.attributeOverrideContexts = buildAttributeOverrideContexts();
	}
	
	protected Collection<AttributeOverrideContext> buildAttributeOverrideContexts() {
		Collection<AttributeOverrideContext> contexts = new ArrayList<AttributeOverrideContext>();
		for (IAttributeOverride attributeOverride : getEmbedded().getAttributeOverrides()) {
			contexts.add(new AttributeOverrideContext(this, attributeOverride));
		}
		
		return contexts;
	}
	
	private IEmbedded getEmbedded() {
		return (IEmbedded) getMapping();
	}
	
	protected void refreshDefaultsInternal(DefaultsContext defaultsContext, IProgressMonitor monitor) {
		super.refreshDefaultsInternal(defaultsContext, monitor);
		refreshDefaultAttributeOverrides();
		for (AttributeOverrideContext context : this.attributeOverrideContexts) {
			context.refreshDefaults(defaultsContext, monitor);
		}
	}
	
	protected void refreshDefaultAttributeOverrides() {
		for (Iterator<String> i = getEmbedded().allOverridableAttributeNames(); i.hasNext(); ) {
			String override = i.next();
			if (!getEmbedded().containsAttributeOverride(override)) {
				JavaAttributeOverride attributeOverride = JpaJavaMappingsFactory.eINSTANCE.createJavaAttributeOverride(new IEmbedded.AttributeOverrideOwner(getEmbedded()), getEmbedded().getPersistentAttribute().getAttribute());
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

	public void addToMessages(List<IMessage> messages) {
		super.addToMessages(messages);
		
		for (AttributeOverrideContext aoContext : attributeOverrideContexts) {
			aoContext.addToMessages(messages);
		}
	}
}
