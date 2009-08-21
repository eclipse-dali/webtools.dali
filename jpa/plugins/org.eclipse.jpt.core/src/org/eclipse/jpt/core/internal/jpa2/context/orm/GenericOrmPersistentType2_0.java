/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.orm;

import java.util.Iterator;

import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmPersistentType;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.resource.orm.Orm2_0Factory;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlAttributeMapping;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;

public class GenericOrmPersistentType2_0
	extends AbstractOrmPersistentType
{
	
	public GenericOrmPersistentType2_0(EntityMappings parent, XmlTypeMapping resourceMapping) {
		super(parent, resourceMapping);
	}
	
	@Override
	protected JpaFactory2_0 getJpaFactory() {
		return (JpaFactory2_0)  super.getJpaFactory();
	}
	
	@Override
	protected Attributes createResourceAttributes() {
		return Orm2_0Factory.eINSTANCE.createAttributes();
	}
	
	@Override
	protected OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentAttribute.Owner owner, org.eclipse.jpt.core.resource.orm.XmlAttributeMapping resourceMapping) {
		return getJpaFactory().buildOrmPersistentAttribute2_0(this, owner, (XmlAttributeMapping) resourceMapping);
	}
	
	@Override
	protected AccessType getAccess(OrmPersistentAttribute ormPersistentAttribute) {
		return ormPersistentAttribute.getAccess();
	}
	
	@Override
	protected Iterator<JavaResourcePersistentAttribute> javaPersistentAttributes(JavaResourcePersistentType resourcePersistentType) {
		AccessType access = getSpecifiedAccess();
		if (access == null && !getMapping().isMetadataComplete()) {
			access = getJavaPersistentType().getSpecifiedAccess();
		}
		return access == null
			? super.javaPersistentAttributes(resourcePersistentType)
			: resourcePersistentType.persistableAttributes(AccessType.toJavaResourceModel(access));
	}

}
