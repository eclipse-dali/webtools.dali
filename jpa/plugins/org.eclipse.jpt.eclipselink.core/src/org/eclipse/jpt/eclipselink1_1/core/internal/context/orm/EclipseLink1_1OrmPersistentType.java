/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink1_1.core.internal.context.orm;

import java.util.Iterator;
import org.eclipse.jpt.core.context.AccessType;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmPersistentType;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.Attributes;
import org.eclipse.jpt.core.resource.orm.XmlTypeMapping;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLinkEntityMappings;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.jpt.eclipselink1_1.core.internal.EclipseLink1_1JpaFactory;
import org.eclipse.jpt.eclipselink1_1.core.resource.orm.XmlAttributeMapping;

public class EclipseLink1_1OrmPersistentType extends AbstractOrmPersistentType
{
	
	public EclipseLink1_1OrmPersistentType(EclipseLinkEntityMappings parent, XmlTypeMapping resourceMapping) {
		super(parent, resourceMapping);
	}
	
	@Override
	protected EclipseLink1_1JpaFactory getJpaFactory() {
		return (EclipseLink1_1JpaFactory)  super.getJpaFactory();
	}
	
	@Override
	protected Attributes createResourceAttributes() {
		return EclipseLinkOrmFactory.eINSTANCE.createAttributes();
	}
	
	@Override
	protected OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentAttribute.Owner owner, org.eclipse.jpt.core.resource.orm.XmlAttributeMapping resourceMapping) {
		return getJpaFactory().buildEclipseLink1_1OrmPersistentAttribute(this, owner, (XmlAttributeMapping) resourceMapping);
	}
	
	@Override
	protected AccessType getAccess(OrmPersistentAttribute ormPersistentAttribute) {
		return ((EclipseLink1_1OrmPersistentAttribute) ormPersistentAttribute).getAccess();
	}
	
	@Override
	protected Iterator<JavaResourcePersistentAttribute> javaPersistentAttributes(JavaResourcePersistentType resourcePersistentType) {
		AccessType specifiedAccess = getSpecifiedAccess();
		if (specifiedAccess == null && !getMapping().isMetadataComplete()) {
			specifiedAccess = getJavaPersistentType().getSpecifiedAccess();
		}
		return specifiedAccess == null
			? super.javaPersistentAttributes(resourcePersistentType)
			: resourcePersistentType.persistableAttributes(AccessType.toJavaResourceModel(specifiedAccess));
	}

}
