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
import org.eclipse.jpt.core.internal.context.java.IJavaEmbeddedMapping;
import org.eclipse.jpt.core.internal.resource.common.JpaEObject;

/**
 * VirtualEmbedded is an implementation of Embedded used when there is 
 * no tag in the orm.xml and an underlying javaEmbeddedMapping exists.
 */
public class VirtualEmbedded extends JpaEObject implements Embedded
{
	IJavaEmbeddedMapping javaEmbeddedMapping;

	protected boolean metadataComplete;

	protected EList<AttributeOverride> virtualAttributeOverrides;
	

	public VirtualEmbedded(IJavaEmbeddedMapping javaEmbeddedMapping, boolean metadataComplete) {
		super();
		this.javaEmbeddedMapping = javaEmbeddedMapping;
		this.metadataComplete = metadataComplete;
		this.initializeAttributeOverrides(javaEmbeddedMapping);
	}
	
	protected void initializeAttributeOverrides(IJavaEmbeddedMapping javaEmbeddedMapping) {
		this.virtualAttributeOverrides = new BasicEList<AttributeOverride>();
		ListIterator<IJavaAttributeOverride> javaAttributesOverrides;
		if (this.metadataComplete) {
			javaAttributesOverrides = this.javaEmbeddedMapping.defaultAttributeOverrides();
		}
		else {
			javaAttributesOverrides = this.javaEmbeddedMapping.attributeOverrides();			
		}
		
		while (javaAttributesOverrides.hasNext()) {
			this.virtualAttributeOverrides.add(new VirtualAttributeOverride(javaAttributesOverrides.next(), this.metadataComplete));
		}
	}
	
	public String getName() {
		return this.javaEmbeddedMapping.persistentAttribute().getName();
	}

	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}


	public EList<AttributeOverride> getAttributeOverrides() {
		return this.virtualAttributeOverrides;
	}

	public void update(IJavaEmbeddedMapping javaEmbeddedMapping) {
		this.javaEmbeddedMapping = javaEmbeddedMapping;
		this.updateAttributeOverrides(javaEmbeddedMapping);
	}
	
	protected void updateAttributeOverrides(IJavaEmbeddedMapping javaEmbeddedMapping) {
		ListIterator<IJavaAttributeOverride> javaAttributesOverrides;
		ListIterator<AttributeOverride> virtualAttributeOverrides = this.virtualAttributeOverrides.listIterator();
		if (this.metadataComplete) {
			javaAttributesOverrides = this.javaEmbeddedMapping.defaultAttributeOverrides();
		}
		else {
			javaAttributesOverrides = this.javaEmbeddedMapping.attributeOverrides();			
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
