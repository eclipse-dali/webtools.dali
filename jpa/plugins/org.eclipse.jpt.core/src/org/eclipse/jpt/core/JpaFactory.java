/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.XmlContextNode;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaBaseJoinColumn;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaDiscriminatorColumn;
import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaEnumeratedConverter;
import org.eclipse.jpt.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinTable;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaLobConverter;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.core.context.java.JavaNamedColumn;
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
import org.eclipse.jpt.core.context.java.JavaTemporalConverter;
import org.eclipse.jpt.core.context.java.JavaTransientMapping;
import org.eclipse.jpt.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmAssociationOverride;
import org.eclipse.jpt.core.context.orm.OrmAttributeMapping;
import org.eclipse.jpt.core.context.orm.OrmAttributeOverride;
import org.eclipse.jpt.core.context.orm.OrmBaseJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmBasicMapping;
import org.eclipse.jpt.core.context.orm.OrmColumn;
import org.eclipse.jpt.core.context.orm.OrmDiscriminatorColumn;
import org.eclipse.jpt.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedIdMapping;
import org.eclipse.jpt.core.context.orm.OrmEmbeddedMapping;
import org.eclipse.jpt.core.context.orm.OrmEntity;
import org.eclipse.jpt.core.context.orm.OrmGeneratedValue;
import org.eclipse.jpt.core.context.orm.OrmIdMapping;
import org.eclipse.jpt.core.context.orm.OrmJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmJoinTable;
import org.eclipse.jpt.core.context.orm.OrmManyToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmManyToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmMappedSuperclass;
import org.eclipse.jpt.core.context.orm.OrmNamedColumn;
import org.eclipse.jpt.core.context.orm.OrmNamedNativeQuery;
import org.eclipse.jpt.core.context.orm.OrmNamedQuery;
import org.eclipse.jpt.core.context.orm.OrmOneToManyMapping;
import org.eclipse.jpt.core.context.orm.OrmOneToOneMapping;
import org.eclipse.jpt.core.context.orm.OrmPersistenceUnitDefaults;
import org.eclipse.jpt.core.context.orm.OrmPersistentAttribute;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.context.orm.OrmPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.orm.OrmQuery;
import org.eclipse.jpt.core.context.orm.OrmQueryHint;
import org.eclipse.jpt.core.context.orm.OrmRelationshipMapping;
import org.eclipse.jpt.core.context.orm.OrmSecondaryTable;
import org.eclipse.jpt.core.context.orm.OrmSequenceGenerator;
import org.eclipse.jpt.core.context.orm.OrmTable;
import org.eclipse.jpt.core.context.orm.OrmTableGenerator;
import org.eclipse.jpt.core.context.orm.OrmTransientMapping;
import org.eclipse.jpt.core.context.orm.OrmTypeMapping;
import org.eclipse.jpt.core.context.orm.OrmUniqueConstraint;
import org.eclipse.jpt.core.context.orm.OrmVersionMapping;
import org.eclipse.jpt.core.context.orm.OrmXml;
import org.eclipse.jpt.core.context.orm.PersistenceUnitMetadata;
import org.eclipse.jpt.core.context.persistence.ClassRef;
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.context.persistence.Property;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.orm.OrmXmlResource;
import org.eclipse.jpt.core.resource.orm.XmlAssociationOverride;
import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlBasic;
import org.eclipse.jpt.core.resource.orm.XmlEmbedded;
import org.eclipse.jpt.core.resource.orm.XmlEmbeddedId;
import org.eclipse.jpt.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.core.resource.orm.XmlGeneratedValue;
import org.eclipse.jpt.core.resource.orm.XmlId;
import org.eclipse.jpt.core.resource.orm.XmlJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlManyToMany;
import org.eclipse.jpt.core.resource.orm.XmlManyToOne;
import org.eclipse.jpt.core.resource.orm.XmlNamedNativeQuery;
import org.eclipse.jpt.core.resource.orm.XmlNamedQuery;
import org.eclipse.jpt.core.resource.orm.XmlNullAttributeMapping;
import org.eclipse.jpt.core.resource.orm.XmlOneToMany;
import org.eclipse.jpt.core.resource.orm.XmlOneToOne;
import org.eclipse.jpt.core.resource.orm.XmlPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.resource.orm.XmlQueryHint;
import org.eclipse.jpt.core.resource.orm.XmlSecondaryTable;
import org.eclipse.jpt.core.resource.orm.XmlSequenceGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTableGenerator;
import org.eclipse.jpt.core.resource.orm.XmlTransient;
import org.eclipse.jpt.core.resource.orm.XmlUniqueConstraint;
import org.eclipse.jpt.core.resource.orm.XmlVersion;
import org.eclipse.jpt.core.resource.persistence.PersistenceXmlResource;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.resource.persistence.XmlProperty;

/**
 * Use JpaFactory to build any core (e.g. JpaProject) model object or any
 * java (e.g. JavaEntity), orm (e.g. EntityMappings), or 
 * persistence (e.g. PersistenceUnit) context model objects.
 * 
 * Assumes a base JPA project context structure 
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
 *
 * @see org.eclipse.jpt.core.internal.platform.GenericJpaFactory
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface JpaFactory 
{
	// ********** Core Model **********
	
	/**
	 * Construct a JpaProject for the specified config, to be
	 * added to the specified JPA project. Return null if unable to create
	 * the JPA file (e.g. the content type is unrecognized).
	 */
	JpaProject buildJpaProject(JpaProject.Config config) throws CoreException;
	
	JpaDataSource buildJpaDataSource(JpaProject jpaProject, String connectionProfileName);

	/**
	 * Construct a JPA file for the specified JPA project, file, content type,
	 * and resource model.
	 */
	JpaFile buildJpaFile(JpaProject jpaProject, IFile file, IContentType contentType, JpaResourceModel resourceModel);
	
	
	// ********** Context Nodes **********
	
	/**
	 * Build a (/an updated) root context node to be associated with the given 
	 * JPA project.
	 * The root context node will be built once, but updated many times.
	 * @see JpaProject#update(org.eclipse.core.runtime.IProgressMonitor)
	 */
	JpaRootContextNode buildRootContextNode(JpaProject jpaProject);

	MappingFile buildMappingFile(MappingFileRef parent, OrmXmlResource resource);
	
	
	// ********** Persistence Context Model **********
	
	PersistenceXml buildPersistenceXml(JpaRootContextNode parent, PersistenceXmlResource persistenceResource);
	
	Persistence buildPersistence(PersistenceXml parent, XmlPersistence resourcePersistence);
	
	PersistenceUnit buildPersistenceUnit(Persistence parent, XmlPersistenceUnit resourcePersistenceUnit);
	
	/**
	 * xmlMappingFileRef is allowed to be null, this would be used for the implied mapping file ref
	 */
	MappingFileRef buildMappingFileRef(PersistenceUnit parent, XmlMappingFileRef xmlMappingFileRef);
	
	ClassRef buildClassRef(PersistenceUnit parent, XmlJavaClassRef xmlClassRef);
	
	ClassRef buildClassRef(PersistenceUnit parent, String className);
	
	Property buildProperty(PersistenceUnit parent, XmlProperty property);
	
	
	// ********** ORM Context Model **********
	
	OrmXml buildOrmXml(MappingFileRef parent, OrmXmlResource ormResource);
	
	EntityMappings buildEntityMappings(OrmXml parent, XmlEntityMappings entityMappings);
	
	PersistenceUnitMetadata buildPersistenceUnitMetadata(EntityMappings parent, XmlEntityMappings entityMappings);
	
	OrmPersistenceUnitDefaults buildPersistenceUnitDefaults(PersistenceUnitMetadata parent, XmlEntityMappings entityMappings);
	
	OrmPersistentType buildOrmPersistentType(EntityMappings parent, String mappingKey);
	
	OrmEntity buildOrmEntity(OrmPersistentType parent);
	
	OrmMappedSuperclass buildOrmMappedSuperclass(OrmPersistentType parent);
	
	OrmEmbeddable buildOrmEmbeddable(OrmPersistentType parent);
	
	OrmPersistentAttribute buildOrmPersistentAttribute(OrmPersistentType parent, OrmPersistentAttribute.Owner owner, String mappingKey);
	
	OrmTable buildOrmTable(OrmEntity parent);
	
	OrmSecondaryTable buildOrmSecondaryTable(OrmEntity parent, XmlSecondaryTable xmlSecondaryTable);
	
	OrmPrimaryKeyJoinColumn buildOrmPrimaryKeyJoinColumn(XmlContextNode parent, OrmBaseJoinColumn.Owner owner, XmlPrimaryKeyJoinColumn resourcePkJoinColumn);
	
	OrmJoinTable buildOrmJoinTable(OrmRelationshipMapping parent);
	
	OrmJoinColumn buildOrmJoinColumn(XmlContextNode parent, OrmJoinColumn.Owner owner, XmlJoinColumn resourceJoinColumn);
	
	OrmAttributeOverride buildOrmAttributeOverride(XmlContextNode parent, AttributeOverride.Owner owner, XmlAttributeOverride xmlAttributeOverride);
	
	OrmAssociationOverride buildOrmAssociationOverride(XmlContextNode parent, AssociationOverride.Owner owner, XmlAssociationOverride associationOverride);
	
	OrmDiscriminatorColumn buildOrmDiscriminatorColumn(OrmEntity parent, OrmNamedColumn.Owner owner);
	
	OrmColumn buildOrmColumn(XmlContextNode parent, OrmColumn.Owner owner);
	
	OrmGeneratedValue buildOrmGeneratedValue(XmlContextNode parent, XmlGeneratedValue resourceGeneratedValue);
	
	OrmSequenceGenerator buildOrmSequenceGenerator(XmlContextNode parent, XmlSequenceGenerator resourceSequenceGenerator);
	
	OrmTableGenerator buildOrmTableGenerator(XmlContextNode parent, XmlTableGenerator resourceTableGenerator);
	
	OrmNamedNativeQuery buildOrmNamedNativeQuery(XmlContextNode parent, XmlNamedNativeQuery resourceNamedQuery);
	
	OrmNamedQuery buildOrmNamedQuery(XmlContextNode parent, XmlNamedQuery resourceNamedQuery);
	
	OrmQueryHint buildOrmQueryHint(OrmQuery parent, XmlQueryHint resourceQueryhint);
	
	OrmBasicMapping buildOrmBasicMapping(OrmPersistentAttribute parent);
	
	OrmManyToManyMapping buildOrmManyToManyMapping(OrmPersistentAttribute parent);
	
	OrmOneToManyMapping buildOrmOneToManyMapping(OrmPersistentAttribute parent);
	
	OrmManyToOneMapping buildOrmManyToOneMapping(OrmPersistentAttribute parent);
	
	OrmOneToOneMapping buildOrmOneToOneMapping(OrmPersistentAttribute parent);
	
	OrmEmbeddedIdMapping buildOrmEmbeddedIdMapping(OrmPersistentAttribute parent);
	
	OrmEmbeddedMapping buildOrmEmbeddedMapping(OrmPersistentAttribute parent);
	
	OrmIdMapping buildOrmIdMapping(OrmPersistentAttribute parent);
	
	OrmTransientMapping buildOrmTransientMapping(OrmPersistentAttribute parent);
	
	OrmVersionMapping buildOrmVersionMapping(OrmPersistentAttribute parent);
	
	OrmAttributeMapping buildOrmNullAttributeMapping(OrmPersistentAttribute parent);
	
	OrmUniqueConstraint buildOrmUniqueConstraint(XmlContextNode parent, UniqueConstraint.Owner owner, XmlUniqueConstraint resourceUniqueConstraint);
	
	
	// ********** ORM Virtual Resource Model **********
	
	XmlBasic buildVirtualXmlBasic(OrmTypeMapping ormTypeMapping, JavaBasicMapping javaBasicMapping);
	
	XmlEmbeddedId buildVirtualXmlEmbeddedId(OrmTypeMapping ormTypeMapping, JavaEmbeddedIdMapping javaEmbeddedIdMapping);
	
	XmlEmbedded buildVirtualXmlEmbedded(OrmTypeMapping ormTypeMapping, JavaEmbeddedMapping javaEmbeddedMapping);
	
	XmlId buildVirtualXmlId(OrmTypeMapping ormTypeMapping, JavaIdMapping javaIdMapping);
	
	XmlManyToOne buildVirtualXmlManyToOne(OrmTypeMapping ormTypeMapping, JavaManyToOneMapping javaManyToOneMapping);
	
	XmlManyToMany buildVirtualXmlManyToMany(OrmTypeMapping ormTypeMapping, JavaManyToManyMapping javaManyToManyMapping);
	
	XmlOneToMany buildVirtualXmlOneToMany(OrmTypeMapping ormTypeMapping, JavaOneToManyMapping javaOneToManyMapping);
	
	XmlOneToOne buildVirtualXmlOneToOne(OrmTypeMapping ormTypeMapping, JavaOneToOneMapping javaOneToOneMapping);
	
	XmlTransient buildVirtualXmlTransient(OrmTypeMapping ormTypeMapping, JavaTransientMapping javaTransientMapping);
	
	XmlVersion buildVirtualXmlVersion(OrmTypeMapping ormTypeMapping, JavaVersionMapping javaVersionMapping);
	
	XmlNullAttributeMapping buildVirtualXmlNullAttributeMapping(OrmTypeMapping ormTypeMapping, JavaAttributeMapping javaAttributeMapping);
	
	
	// ********** Java Context Model **********
	
	JavaPersistentType buildJavaPersistentType(PersistentType.Owner owner, JavaResourcePersistentType jrpt);
	
	JavaEntity buildJavaEntity(JavaPersistentType parent);
	
	JavaMappedSuperclass buildJavaMappedSuperclass(JavaPersistentType parent);
	
	JavaEmbeddable buildJavaEmbeddable(JavaPersistentType parent);
	
	JavaTypeMapping buildJavaNullTypeMapping(JavaPersistentType parent);
	
	JavaPersistentAttribute buildJavaPersistentAttribute(PersistentType parent, JavaResourcePersistentAttribute jrpa);
	
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
	
	JavaTable buildJavaTable(JavaEntity parent);
	
	JavaJoinTable buildJavaJoinTable(JavaRelationshipMapping parent);
	
	JavaColumn buildJavaColumn(JavaJpaContextNode parent, JavaColumn.Owner owner);
	
	JavaDiscriminatorColumn buildJavaDiscriminatorColumn(JavaEntity parent, JavaNamedColumn.Owner owner);
	
	JavaJoinColumn buildJavaJoinColumn(JavaJpaContextNode parent, JavaJoinColumn.Owner owner);
	
	JavaSecondaryTable buildJavaSecondaryTable(JavaEntity parent);
	
	JavaSequenceGenerator buildJavaSequenceGenerator(JavaJpaContextNode parent);
	
	JavaTableGenerator buildJavaTableGenerator(JavaJpaContextNode parent);
	
	JavaGeneratedValue buildJavaGeneratedValue(JavaIdMapping parent);
	
	JavaPrimaryKeyJoinColumn buildJavaPrimaryKeyJoinColumn(JavaJpaContextNode parent, JavaBaseJoinColumn.Owner owner);
	
	JavaAttributeOverride buildJavaAttributeOverride(JavaJpaContextNode parent, AttributeOverride.Owner owner);
	
	JavaAssociationOverride buildJavaAssociationOverride(JavaJpaContextNode parent, AssociationOverride.Owner owner);
	
	JavaNamedQuery buildJavaNamedQuery(JavaJpaContextNode parent);
	
	JavaNamedNativeQuery buildJavaNamedNativeQuery(JavaJpaContextNode parent);
	
	JavaQueryHint buildJavaQueryHint(JavaQuery parent);
	
	JavaUniqueConstraint buildJavaUniqueConstraint(JavaJpaContextNode parent, UniqueConstraint.Owner owner);
	
	JavaEnumeratedConverter buildJavaEnumeratedConverter(JavaAttributeMapping parent, JavaResourcePersistentAttribute jrpa);
	
	JavaTemporalConverter buildJavaTemporalConverter(JavaAttributeMapping parent, JavaResourcePersistentAttribute jrpa);
	
	JavaLobConverter buildJavaLobConverter(JavaAttributeMapping parent, JavaResourcePersistentAttribute jrpa);

}
