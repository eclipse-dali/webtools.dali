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
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.core.utility.TextRange;

/**
 * VirtualEmbedded is an implementation of Embedded used when there is 
 * no tag in the orm.xml and an underlying javaEmbeddedMapping exists.
 */
public class VirtualXmlEmbedded extends AbstractJpaEObject implements XmlEmbedded
{
	JavaEmbeddedMapping javaEmbeddedMapping;

	protected boolean metadataComplete;

	protected EList<XmlAttributeOverride> virtualAttributeOverrides;
	
	protected OrmTypeMapping ormTypeMapping;
	
	public VirtualXmlEmbedded(OrmTypeMapping ormTypeMapping, JavaEmbeddedMapping javaEmbeddedMapping, boolean metadataComplete) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaEmbeddedMapping = javaEmbeddedMapping;
		this.metadataComplete = metadataComplete;
		this.initializeAttributeOverrides(javaEmbeddedMapping);
	}
	
	protected void initializeAttributeOverrides(JavaEmbeddedMapping javaEmbeddedMapping) {
		this.virtualAttributeOverrides = new BasicEList<XmlAttributeOverride>();
		ListIterator<JavaAttributeOverride> javaAttributesOverrides;
		if (this.metadataComplete) {
			javaAttributesOverrides = javaEmbeddedMapping.virtualAttributeOverrides();
		}
		else {
			javaAttributesOverrides = javaEmbeddedMapping.attributeOverrides();			
		}
		
		while (javaAttributesOverrides.hasNext()) {
			this.virtualAttributeOverrides.add(new VirtualXmlAttributeOverride(this.ormTypeMapping, javaAttributesOverrides.next(), this.metadataComplete));
		}
	}
	
	public String getName() {
		return this.javaEmbeddedMapping.persistentAttribute().getName();
	}

	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}


	public EList<XmlAttributeOverride> getAttributeOverrides() {
		return this.virtualAttributeOverrides;
	}

	public void update(JavaEmbeddedMapping javaEmbeddedMapping) {
		this.javaEmbeddedMapping = javaEmbeddedMapping;
		this.updateAttributeOverrides(javaEmbeddedMapping);
	}
	
	protected void updateAttributeOverrides(JavaEmbeddedMapping javaEmbeddedMapping) {
		ListIterator<JavaAttributeOverride> javaAttributesOverrides;
		ListIterator<XmlAttributeOverride> virtualAttributeOverrides = this.virtualAttributeOverrides.listIterator();
		if (this.metadataComplete) {
			javaAttributesOverrides = javaEmbeddedMapping.virtualAttributeOverrides();
		}
		else {
			javaAttributesOverrides = javaEmbeddedMapping.attributeOverrides();			
		}
		
		while (javaAttributesOverrides.hasNext()) {
			JavaAttributeOverride javaAttributeOverride = javaAttributesOverrides.next();
			if (virtualAttributeOverrides.hasNext()) {
				VirtualXmlAttributeOverride virtualAttributeOverride = (VirtualXmlAttributeOverride) virtualAttributeOverrides.next();
				virtualAttributeOverride.update(javaAttributeOverride);
			}
			else {
				this.virtualAttributeOverrides.add(new VirtualXmlAttributeOverride(this.ormTypeMapping, javaAttributeOverride, this.metadataComplete));
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
