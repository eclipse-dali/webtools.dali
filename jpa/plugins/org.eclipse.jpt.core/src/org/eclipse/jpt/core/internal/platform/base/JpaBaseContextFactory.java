/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform.base;

import org.eclipse.jpt.core.JpaFactory;
import org.eclipse.jpt.core.context.AbstractJoinColumn;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.IBaseJpaContent;
import org.eclipse.jpt.core.context.JoinColumn;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.NamedColumn;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaDiscriminatorColumn;
import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinTable;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.core.context.java.JavaNamedNativeQuery;
import org.eclipse.jpt.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaQuery;
import org.eclipse.jpt.core.context.java.JavaQueryHint;
import org.eclipse.jpt.core.context.java.JavaRelationshipMapping;
import org.eclipse.jpt.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.core.context.java.JavaTable;
import org.eclipse.jpt.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.core.context.java.JavaTransientMapping;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEmbeddable;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmEntity;
import org.eclipse.jpt.core.internal.context.orm.GenericOrmMappedSuperclass;
import org.eclipse.jpt.core.internal.context.orm.OrmPersistentAttribute;

/**
 * An IJpaFactory that also assumes a base JPA project context structure 
 * corresponding to the JPA spec:
 * 
 * 	RootContent
 * 	 |- persistence.xml
 * 	     |- persistence unit(s)
 *           |- mapping file(s)  (e.g. orm.xml)
 *           |   |- persistent type mapping(s)  (e.g. Entity)
 *           |        |- persistent attribute mapping(s)  (e.g. Basic)
 *           |- persistent type mapping(s)
 *   
 *   ... and associated objects.
 */
public interface JpaBaseContextFactory extends JpaFactory
{
	PersistenceXml buildPersistenceXml(IBaseJpaContent parent);
	
	Persistence buildPersistence(PersistenceXml parent);
	
	OrmXml buildOrmXml(MappingFileRef parent);

	EntityMappings buildEntityMappings(OrmXml parent);
	
	PersistenceUnitMetadata buildPersistenceUnitMetadata(EntityMappings parent);
	
	PersistenceUnitDefaults buildPersistenceUnitDefaults(PersistenceUnitMetadata parent);
	
	PersistenceUnit buildPersistenceUnit(Persistence parent);
	
	MappingFileRef buildMappingFileRef(PersistenceUnit parent);
	
	ClassRef buildClassRef(PersistenceUnit parent);
	
	Property buildProperty(PersistenceUnit parent);
	
	JavaPersistentType buildJavaPersistentType(JpaContextNode parent);
	
	JavaPersistentAttribute buildJavaPersistentAttribute(JavaPersistentType parent);

	JavaTypeMapping buildJavaNullTypeMapping(JavaPersistentType parent);

	JavaEntity buildJavaEntity(JavaPersistentType parent);
	
	JavaMappedSuperclass buildJavaMappedSuperclass(JavaPersistentType parent);
	
	JavaEmbeddable buildJavaEmbeddable(JavaPersistentType parent);
	
	JavaTable buildJavaTable(JavaEntity parent);
	
	JavaJoinTable buildJavaJoinTable(JavaRelationshipMapping parent);
	
	JavaColumn buildJavaColumn(JavaJpaContextNode parent, JavaColumn.Owner owner);

	JavaDiscriminatorColumn buildJavaDiscriminatorColumn(JavaEntity parent, NamedColumn.Owner owner);
	
	JavaJoinColumn buildJavaJoinColumn(JavaJpaContextNode parent, JoinColumn.Owner owner);

	JavaSecondaryTable buildJavaSecondaryTable(JavaEntity parent);
	
	JavaBasicMapping buildJavaBasicMapping(JavaPersistentAttribute parent);
	
	JavaEmbeddedIdMapping buildJavaEmbeddedIdMapping(JavaPersistentAttribute parent);
	
	JavaEmbeddedMapping buildJavaEmbeddedMapping(JavaPersistentAttribute parent);
	
	JavaIdMapping buildJavaIdMapping(JavaPersistentAttribute parent);
	
	JavaManyToManyMapping buildJavaManyToManyMapping(JavaPersistentAttribute parent);
	
	JavaManyToOneMapping buildJavaManyToOneMapping(JavaPersistentAttribute parent);
	
	JavaOneToManyMapping buildJavaOneToManyMapping(JavaPersistentAttribute parent);
	
	JavaOneToOneMapping buildJavaOneToOneMapping(JavaPersistentAttribute parent);

	JavaTransientMapping buildJavaTransientMapping(JavaPersistentAttribute parent);
	
	JavaVersionMapping buildJavaVersionMapping(JavaPersistentAttribute parent);
	
	JavaAttributeMapping buildJavaNullAttributeMapping(JavaPersistentAttribute parent);
	
	JavaSequenceGenerator buildJavaSequenceGenerator(JavaJpaContextNode parent);
	
	JavaTableGenerator buildJavaTableGenerator(JavaJpaContextNode parent);
	
	JavaGeneratedValue buildJavaGeneratedValue(JavaAttributeMapping parent);
	
	JavaPrimaryKeyJoinColumn buildJavaPrimaryKeyJoinColumn(JavaJpaContextNode parent, AbstractJoinColumn.Owner owner);
	
	JavaAttributeOverride buildJavaAttributeOverride(JavaJpaContextNode parent, AttributeOverride.Owner owner);
	
	JavaAssociationOverride buildJavaAssociationOverride(JavaJpaContextNode parent, AssociationOverride.Owner owner);

	JavaNamedQuery buildJavaNamedQuery(JavaJpaContextNode parent);
	
	JavaNamedNativeQuery buildJavaNamedNativeQuery(JavaJpaContextNode parent);
	
	JavaQueryHint buildJavaQueryHint(JavaQuery<?> parent);
	
	OrmPersistentType buildOrmPersistentType(EntityMappings parent, String mappingKey);
	
	GenericOrmEntity buildXmlEntity(OrmPersistentType parent);
	
	GenericOrmMappedSuperclass buildXmlMappedSuperclass(OrmPersistentType parent);
	
	GenericOrmEmbeddable buildXmlEmbeddable(OrmPersistentType parent);
	
	OrmPersistentAttribute buildXmlPersistentAttribute(OrmPersistentType parent, String mappingKey);

}
