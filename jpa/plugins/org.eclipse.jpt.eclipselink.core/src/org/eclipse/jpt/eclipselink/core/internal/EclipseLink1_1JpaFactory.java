/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal;

import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.PersistentType.Owner;
import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;
import org.eclipse.jpt.eclipselink.core.internal.context.java.EclipseLink1_1JavaPersistentTypeImpl;
import org.eclipse.jpt.eclipselink.core.internal.context.orm.EclipseLink1_1OrmXml;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.java.EclipseLinkJavaEmbeddable_1_1;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.orm.EclipseLinkOrmEmbeddable_1_1;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddable;

public class EclipseLink1_1JpaFactory
	extends EclipseLinkJpaFactory
{
	protected EclipseLink1_1JpaFactory() {
		super();
	}
	
	
	// **************** Context Nodes ******************************************
	
	@Override
	public MappingFile buildEclipseLinkMappingFile(MappingFileRef parent, JpaXmlResource resource) {
		return this.buildEclipseLinkOrmXml(parent, resource);
	}
	
	public MappingFile buildEclipseLink1_1MappingFile(MappingFileRef parent, JpaXmlResource resource) {
		return this.buildEclipseLink1_1OrmXml(parent, resource);
	}
	
	protected EclipseLink1_1OrmXml buildEclipseLink1_1OrmXml(MappingFileRef parent, JpaXmlResource resource) {
		return new EclipseLink1_1OrmXml(parent, resource);
	}
	
	
	// **************** EclipseLink-specific ORM Context Model *****************
	
	
	@Override
	public OrmEmbeddable buildEclipseLinkOrmEmbeddable(OrmPersistentType type, XmlEmbeddable resourceMapping) {
		return new EclipseLinkOrmEmbeddable_1_1(type, resourceMapping);
	}
	
	
	// **************** Java Context Model *************************************
	
	@Override
	public JavaPersistentType buildJavaPersistentType(Owner owner, JavaResourcePersistentType jrpt) {
		return new EclipseLink1_1JavaPersistentTypeImpl(owner, jrpt);
	}
	
	@Override
	public JavaEmbeddable buildJavaEmbeddable(JavaPersistentType parent) {
		return new EclipseLinkJavaEmbeddable_1_1(parent);
	}
	
//	
//	public EntityMappings buildEclipseLink1_1EntityMappings(EclipseLink1_1OrmXml parent, XmlEntityMappings xmlEntityMappings) {
//		EclipseLink1_1EntityMappingsImpl entityMappings = new EclipseLink1_1EntityMappingsImpl(parent);
//		entityMappings.initialize(xmlEntityMappings);
//		return entityMappings;
//	}
//
//	public OrmPersistentType buildEclipseLink1_1OrmPersistentType(EclipseLinkEntityMappings parent, String mappingKey) {
//		return new EclipseLink1_1OrmPersistentType(parent, mappingKey);
//	}
//
//	public EclipseLink1_1OrmPersistentAttribute buildEclipseLink1_1OrmPersistentAttribute(OrmPersistentType parent, OrmPersistentAttribute.Owner owner, String mappingKey) {
//		return new EclipseLink1_1OrmPersistentAttributeImpl(parent, owner, mappingKey);
//	}

}
