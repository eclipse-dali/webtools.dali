/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ListIterator;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.TextRange;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddedId;

/**
 * VirtualEmbeddedId is an implementation of EmbeddedId used when there is 
 * no tag in the orm.xml and an underlying javaEmbeddedIdMapping exists.
 */
public class VirtualEmbeddedId extends AbstractJpaEObject implements XmlEmbeddedId
{
	JavaEmbeddedIdMapping javaEmbeddedIdMapping;

	protected boolean metadataComplete;

	protected EList<XmlAttributeOverride> virtualAttributeOverrides;
	

	public VirtualEmbeddedId(JavaEmbeddedIdMapping javaEmbeddedIdMapping, boolean metadataComplete) {
		super();
		this.javaEmbeddedIdMapping = javaEmbeddedIdMapping;
		this.metadataComplete = metadataComplete;
		this.initializeAttributeOverrides(javaEmbeddedIdMapping);
	}
	
	protected void initializeAttributeOverrides(JavaEmbeddedIdMapping javaEmbeddedIdMapping) {
		this.virtualAttributeOverrides = new BasicEList<XmlAttributeOverride>();
		ListIterator<JavaAttributeOverride> javaAttributesOverrides;
		if (this.metadataComplete) {
			javaAttributesOverrides = this.javaEmbeddedIdMapping.defaultAttributeOverrides();
		}
		else {
			javaAttributesOverrides = this.javaEmbeddedIdMapping.attributeOverrides();			
		}
		
		while (javaAttributesOverrides.hasNext()) {
			this.virtualAttributeOverrides.add(new VirtualAttributeOverride(javaAttributesOverrides.next(), this.metadataComplete));
		}
	}
	
	public String getName() {
		return this.javaEmbeddedIdMapping.persistentAttribute().getName();
	}

	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}


	public EList<XmlAttributeOverride> getAttributeOverrides() {
		return this.virtualAttributeOverrides;
	}

	public void update(JavaEmbeddedIdMapping javaEmbeddedIdMapping) {
		this.javaEmbeddedIdMapping = javaEmbeddedIdMapping;
		this.updateAttributeOverrides(javaEmbeddedIdMapping);
	}
	
	protected void updateAttributeOverrides(JavaEmbeddedIdMapping javaEmbeddedIdMapping) {
		ListIterator<JavaAttributeOverride> javaAttributesOverrides;
		ListIterator<XmlAttributeOverride> virtualAttributeOverrides = this.virtualAttributeOverrides.listIterator();
		if (this.metadataComplete) {
			javaAttributesOverrides = this.javaEmbeddedIdMapping.defaultAttributeOverrides();
		}
		else {
			javaAttributesOverrides = this.javaEmbeddedIdMapping.attributeOverrides();			
		}
		
		while (javaAttributesOverrides.hasNext()) {
			JavaAttributeOverride javaAttributeOverride = javaAttributesOverrides.next();
			if (virtualAttributeOverrides.hasNext()) {
				VirtualAttributeOverride virtualAttributeOverride = (VirtualAttributeOverride) virtualAttributeOverrides.next();
				virtualAttributeOverride.update(javaAttributeOverride);
			}
			else {
				this.virtualAttributeOverrides.add(new VirtualAttributeOverride(javaAttributeOverride, this.metadataComplete));
			}
		}
		
		while(virtualAttributeOverrides.hasNext()) {
			this.virtualAttributeOverrides.remove(virtualAttributeOverrides.next());
		}
	}
	
	public TextRange nameTextRange() {
		return null;
	}

}
