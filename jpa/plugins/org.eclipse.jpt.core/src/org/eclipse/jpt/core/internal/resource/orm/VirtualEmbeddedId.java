/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm;

import java.util.ListIterator;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jpt.core.internal.context.java.IJavaAttributeOverride;
import org.eclipse.jpt.core.internal.context.java.IJavaEmbeddedIdMapping;
import org.eclipse.jpt.core.internal.resource.common.JpaEObject;

/**
 * VirtualEmbeddedId is an implementation of EmbeddedId used when there is 
 * no tag in the orm.xml and an underlying javaEmbeddedIdMapping exists.
 */
public class VirtualEmbeddedId extends JpaEObject implements EmbeddedId
{
	IJavaEmbeddedIdMapping javaEmbeddedIdMapping;

	protected boolean metadataComplete;

	protected EList<AttributeOverride> virtualAttributeOverrides;
	

	public VirtualEmbeddedId(IJavaEmbeddedIdMapping javaEmbeddedIdMapping, boolean metadataComplete) {
		super();
		this.javaEmbeddedIdMapping = javaEmbeddedIdMapping;
		this.metadataComplete = metadataComplete;
		this.initializeAttributeOverrides(javaEmbeddedIdMapping);
	}
	
	protected void initializeAttributeOverrides(IJavaEmbeddedIdMapping javaEmbeddedIdMapping) {
		this.virtualAttributeOverrides = new BasicEList<AttributeOverride>();
		ListIterator<IJavaAttributeOverride> javaAttributesOverrides;
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


	public EList<AttributeOverride> getAttributeOverrides() {
		return this.virtualAttributeOverrides;
	}

	public void update(IJavaEmbeddedIdMapping javaEmbeddedIdMapping) {
		this.javaEmbeddedIdMapping = javaEmbeddedIdMapping;
		this.updateAttributeOverrides(javaEmbeddedIdMapping);
	}
	
	protected void updateAttributeOverrides(IJavaEmbeddedIdMapping javaEmbeddedIdMapping) {
		ListIterator<IJavaAttributeOverride> javaAttributesOverrides;
		ListIterator<AttributeOverride> virtualAttributeOverrides = this.virtualAttributeOverrides.listIterator();
		if (this.metadataComplete) {
			javaAttributesOverrides = this.javaEmbeddedIdMapping.defaultAttributeOverrides();
		}
		else {
			javaAttributesOverrides = this.javaEmbeddedIdMapping.attributeOverrides();			
		}
		
		while (javaAttributesOverrides.hasNext()) {
			IJavaAttributeOverride javaAttributeOverride = javaAttributesOverrides.next();
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

}
