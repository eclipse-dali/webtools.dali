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

import org.eclipse.jpt.core.internal.IJpaFactory;
import org.eclipse.jpt.core.internal.context.base.IAbstractJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IAssociationOverride;
import org.eclipse.jpt.core.internal.context.base.IAttributeOverride;
import org.eclipse.jpt.core.internal.context.base.IBaseJpaContent;
import org.eclipse.jpt.core.internal.context.base.IJoinColumn;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.context.base.INamedColumn;
import org.eclipse.jpt.core.internal.context.java.IJavaAssociationOverride;
import org.eclipse.jpt.core.internal.context.java.IJavaAttributeMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaAttributeOverride;
import org.eclipse.jpt.core.internal.context.java.IJavaBasicMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaColumn;
import org.eclipse.jpt.core.internal.context.java.IJavaDiscriminatorColumn;
import org.eclipse.jpt.core.internal.context.java.IJavaEmbeddable;
import org.eclipse.jpt.core.internal.context.java.IJavaEmbeddedIdMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaEmbeddedMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaEntity;
import org.eclipse.jpt.core.internal.context.java.IJavaGeneratedValue;
import org.eclipse.jpt.core.internal.context.java.IJavaIdMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaJoinColumn;
import org.eclipse.jpt.core.internal.context.java.IJavaJoinTable;
import org.eclipse.jpt.core.internal.context.java.IJavaJpaContextNode;
import org.eclipse.jpt.core.internal.context.java.IJavaManyToManyMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaManyToOneMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaMappedSuperclass;
import org.eclipse.jpt.core.internal.context.java.IJavaNamedNativeQuery;
import org.eclipse.jpt.core.internal.context.java.IJavaNamedQuery;
import org.eclipse.jpt.core.internal.context.java.IJavaOneToManyMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaOneToOneMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentAttribute;
import org.eclipse.jpt.core.internal.context.java.IJavaPersistentType;
import org.eclipse.jpt.core.internal.context.java.IJavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.context.java.IJavaQuery;
import org.eclipse.jpt.core.internal.context.java.IJavaQueryHint;
import org.eclipse.jpt.core.internal.context.java.IJavaRelationshipMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaSecondaryTable;
import org.eclipse.jpt.core.internal.context.java.IJavaSequenceGenerator;
import org.eclipse.jpt.core.internal.context.java.IJavaTable;
import org.eclipse.jpt.core.internal.context.java.IJavaTableGenerator;
import org.eclipse.jpt.core.internal.context.java.IJavaTransientMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaTypeMapping;
import org.eclipse.jpt.core.internal.context.java.IJavaVersionMapping;
import org.eclipse.jpt.core.internal.context.orm.EntityMappings;
import org.eclipse.jpt.core.internal.context.orm.OrmXml;
import org.eclipse.jpt.core.internal.context.orm.PersistenceUnitDefaults;
import org.eclipse.jpt.core.internal.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.internal.context.orm.XmlEmbeddable;
import org.eclipse.jpt.core.internal.context.orm.XmlEntity;
import org.eclipse.jpt.core.internal.context.orm.XmlMappedSuperclass;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentAttribute;
import org.eclipse.jpt.core.internal.context.orm.XmlPersistentType;
import org.eclipse.jpt.core.internal.context.persistence.IClassRef;
import org.eclipse.jpt.core.internal.context.persistence.IMappingFileRef;
import org.eclipse.jpt.core.internal.context.persistence.IPersistence;
import org.eclipse.jpt.core.internal.context.persistence.IPersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.IPersistenceXml;
import org.eclipse.jpt.core.internal.context.persistence.IProperty;

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
public interface IJpaBaseContextFactory extends IJpaFactory
{
	IPersistenceXml createPersistenceXml(IBaseJpaContent parent);
	
	IPersistence createPersistence(IPersistenceXml parent);
	
	OrmXml createOrmXml(IMappingFileRef parent);

	EntityMappings createEntityMappings(OrmXml parent);
	
	PersistenceUnitMetadata createPersistenceUnitMetadata(EntityMappings parent);
	
	PersistenceUnitDefaults createPersistenceUnitDefaults(PersistenceUnitMetadata parent);
	
	IPersistenceUnit createPersistenceUnit(IPersistence parent);
	
	IMappingFileRef createMappingFileRef(IPersistenceUnit parent);
	
	IClassRef createClassRef(IPersistenceUnit parent);
	
	IProperty createProperty(IPersistenceUnit parent);
	
	IJavaPersistentType createJavaPersistentType(IJpaContextNode parent);
	
	IJavaPersistentAttribute createJavaPersistentAttribute(IJavaPersistentType parent);

	IJavaTypeMapping createJavaNullTypeMapping(IJavaPersistentType parent);

	IJavaEntity createJavaEntity(IJavaPersistentType parent);
	
	IJavaMappedSuperclass createJavaMappedSuperclass(IJavaPersistentType parent);
	
	IJavaEmbeddable createJavaEmbeddable(IJavaPersistentType parent);
	
	IJavaTable createJavaTable(IJavaEntity parent);
	
	IJavaJoinTable createJavaJoinTable(IJavaRelationshipMapping parent);
	
	IJavaColumn createJavaColumn(IJavaJpaContextNode parent, IJavaColumn.Owner owner);

	IJavaDiscriminatorColumn createJavaDiscriminatorColumn(IJavaEntity parent, INamedColumn.Owner owner);
	
	IJavaJoinColumn createJavaJoinColumn(IJavaJpaContextNode parent, IJoinColumn.Owner owner);

	IJavaSecondaryTable createJavaSecondaryTable(IJavaEntity parent);
	
	IJavaBasicMapping createJavaBasicMapping(IJavaPersistentAttribute parent);
	
	IJavaEmbeddedIdMapping createJavaEmbeddedIdMapping(IJavaPersistentAttribute parent);
	
	IJavaEmbeddedMapping createJavaEmbeddedMapping(IJavaPersistentAttribute parent);
	
	IJavaIdMapping createJavaIdMapping(IJavaPersistentAttribute parent);
	
	IJavaManyToManyMapping createJavaManyToManyMapping(IJavaPersistentAttribute parent);
	
	IJavaManyToOneMapping createJavaManyToOneMapping(IJavaPersistentAttribute parent);
	
	IJavaOneToManyMapping createJavaOneToManyMapping(IJavaPersistentAttribute parent);
	
	IJavaOneToOneMapping createJavaOneToOneMapping(IJavaPersistentAttribute parent);

	IJavaTransientMapping createJavaTransientMapping(IJavaPersistentAttribute parent);
	
	IJavaVersionMapping createJavaVersionMapping(IJavaPersistentAttribute parent);
	
	IJavaAttributeMapping createJavaNullAttributeMapping(IJavaPersistentAttribute parent);
	
	IJavaSequenceGenerator createJavaSequenceGenerator(IJavaJpaContextNode parent);
	
	IJavaTableGenerator createJavaTableGenerator(IJavaJpaContextNode parent);
	
	IJavaGeneratedValue createJavaGeneratedValue(IJavaAttributeMapping parent);
	
	IJavaPrimaryKeyJoinColumn createJavaPrimaryKeyJoinColumn(IJavaJpaContextNode parent, IAbstractJoinColumn.Owner owner);
	
	IJavaAttributeOverride createJavaAttributeOverride(IJavaJpaContextNode parent, IAttributeOverride.Owner owner);
	
	IJavaAssociationOverride createJavaAssociationOverride(IJavaJpaContextNode parent, IAssociationOverride.Owner owner);

	IJavaNamedQuery createJavaNamedQuery(IJavaJpaContextNode parent);
	
	IJavaNamedNativeQuery createJavaNamedNativeQuery(IJavaJpaContextNode parent);
	
	IJavaQueryHint createJavaQueryHint(IJavaQuery<?> parent);
	
	//TODO need an interface??
	XmlPersistentType createXmlPersistentType(EntityMappings parent, String mappingKey);
	
	XmlEntity createXmlEntity(XmlPersistentType parent);
	
	XmlMappedSuperclass createXmlMappedSuperclass(XmlPersistentType parent);
	
	XmlEmbeddable createXmlEmbeddable(XmlPersistentType parent);
	
	XmlPersistentAttribute createXmlPersistentAttribute(XmlPersistentType parent, String mappingKey);

}
