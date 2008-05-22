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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.resource.common.AbstractJpaEObject;
import org.eclipse.jpt.core.resource.orm.OrmPackage;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.jpt.utility.internal.CollectionTools;

/**
 * VirtualEmbeddedId is an implementation of EmbeddedId used when there is 
 * no tag in the orm.xml and an underlying javaEmbeddedIdMapping exists.
 */
public class VirtualXmlEmbeddedId extends AbstractJpaEObject implements XmlEmbeddedId
{
	JavaEmbeddedIdMapping javaEmbeddedIdMapping;

	protected boolean metadataComplete;
	
	protected OrmTypeMapping ormTypeMapping;
	
	public VirtualXmlEmbeddedId(OrmTypeMapping ormTypeMapping, JavaEmbeddedIdMapping javaEmbeddedIdMapping, boolean metadataComplete) {
		super();
		this.ormTypeMapping = ormTypeMapping;
		this.javaEmbeddedIdMapping = javaEmbeddedIdMapping;
		this.metadataComplete = metadataComplete;
	}
	
	public String getName() {
		return this.javaEmbeddedIdMapping.getPersistentAttribute().getName();
	}

	public void setName(String newName) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping");
	}

	public EList<XmlAttributeOverride> getAttributeOverrides() {
		EList<XmlAttributeOverride> attributeOverrides = new EObjectContainmentEList<XmlAttributeOverride>(XmlAttributeOverride.class, this, OrmPackage.XML_EMBEDDED_ID__ATTRIBUTE_OVERRIDES);
		ListIterator<JavaAttributeOverride> javaAttributeOverrides;
		if (!this.metadataComplete) {
			javaAttributeOverrides = this.javaEmbeddedIdMapping.attributeOverrides();
		}
		else {
			javaAttributeOverrides = this.javaEmbeddedIdMapping.virtualAttributeOverrides();
		}
		for (JavaAttributeOverride javaAttributeOverride : CollectionTools.iterable(javaAttributeOverrides)) {
			XmlColumn xmlColumn = new VirtualXmlColumn(this.ormTypeMapping, javaAttributeOverride.getColumn(), this.metadataComplete);
			XmlAttributeOverride xmlAttributeOverride = new VirtualXmlAttributeOverride(javaAttributeOverride.getName(), xmlColumn);
			attributeOverrides.add(xmlAttributeOverride);
		}
		return attributeOverrides;
	}

	public void update(JavaEmbeddedIdMapping javaEmbeddedIdMapping) {
		this.javaEmbeddedIdMapping = javaEmbeddedIdMapping;
	}

	public TextRange getNameTextRange() {
		return null;
	}
}
