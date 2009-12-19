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
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.core.context.AssociationOverride;
import org.eclipse.jpt.core.context.AssociationOverrideContainer;
import org.eclipse.jpt.core.context.AttributeOverride;
import org.eclipse.jpt.core.context.AttributeOverrideContainer;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.context.MappingFile;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.UniqueConstraint;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaBaseColumn;
import org.eclipse.jpt.core.context.java.JavaBaseJoinColumn;
import org.eclipse.jpt.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.core.context.java.JavaCascade;
import org.eclipse.jpt.core.context.java.JavaColumn;
import org.eclipse.jpt.core.context.java.JavaConverter;
import org.eclipse.jpt.core.context.java.JavaDiscriminatorColumn;
import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.core.context.java.JavaEntity;
import org.eclipse.jpt.core.context.java.JavaEnumeratedConverter;
import org.eclipse.jpt.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.core.context.java.JavaIdMapping;
import org.eclipse.jpt.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.core.context.java.JavaJoinTable;
import org.eclipse.jpt.core.context.java.JavaJoinTableJoiningStrategy;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaLobConverter;
import org.eclipse.jpt.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.core.context.java.JavaNamedNativeQuery;
import org.eclipse.jpt.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaOrderable;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.context.java.JavaQuery;
import org.eclipse.jpt.core.context.java.JavaQueryContainer;
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
import org.eclipse.jpt.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;

/**
 * Use a JPA factory to build any core (e.g. {@link JpaProject})
 * model object or any Java (e.g. {@link JavaEntity}), ORM (e.g.
 * {@link EntityMappings}), or persistence (e.g. {@link PersistenceUnit})
 * context model objects.
 * <p>
 * Assumes a base JPA project context structure 
 * corresponding to the JPA spec:
 * <pre>
 *     RootContext
 *      |- persistence.xml
 *          |- persistence unit(s)
 *               |- mapping file(s)  (e.g. orm.xml)
 *               |   |- persistent type mapping(s)  (e.g. Entity)
 *               |       |- persistent attribute mapping(s)  (e.g. Basic)
 *               |- persistent type mapping(s)
 * </pre>
 *   ... and associated objects.
 *<p> 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @see org.eclipse.jpt.core.internal.jpa1.GenericJpaFactory
 */
public interface JpaFactory 
{
	// ********** Core Model **********
	
	/**
	 * Construct a JpaProject for the specified config, to be
	 * added to the specified JPA project. Return null if unable to create
	 * the JPA file (e.g. the content type is unrecognized).
	 */
	JpaProject buildJpaProject(JpaProject.Config config);
	
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


	// ********** XML Context Model **********

	PersistenceXml buildPersistenceXml(JpaRootContextNode parent, JpaXmlResource resource);

	MappingFile buildMappingFile(MappingFileRef parent, JpaXmlResource resource);


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
	
	JavaGeneratorContainer buildJavaGeneratorContainer(JavaJpaContextNode parent);

	JavaIdMapping buildJavaIdMapping(JavaPersistentAttribute parent);
	
	JavaManyToManyMapping buildJavaManyToManyMapping(JavaPersistentAttribute parent);
	
	JavaManyToOneMapping buildJavaManyToOneMapping(JavaPersistentAttribute parent);
	
	JavaOneToManyMapping buildJavaOneToManyMapping(JavaPersistentAttribute parent);
	
	JavaOneToOneMapping buildJavaOneToOneMapping(JavaPersistentAttribute parent);
	
	JavaTransientMapping buildJavaTransientMapping(JavaPersistentAttribute parent);
	
	JavaVersionMapping buildJavaVersionMapping(JavaPersistentAttribute parent);
	
	JavaAttributeMapping buildJavaNullAttributeMapping(JavaPersistentAttribute parent);
	
	JavaTable buildJavaTable(JavaEntity parent);
	
	JavaJoinTable buildJavaJoinTable(JavaJoinTableJoiningStrategy parent);
	
	JavaCascade buildJavaCascade(JavaRelationshipMapping parent);
	
	JavaColumn buildJavaColumn(JavaJpaContextNode parent, JavaBaseColumn.Owner owner);
	
	JavaDiscriminatorColumn buildJavaDiscriminatorColumn(JavaEntity parent, JavaDiscriminatorColumn.Owner owner);
	
	JavaJoinColumn buildJavaJoinColumn(JavaJpaContextNode parent, JavaJoinColumn.Owner owner);
	
	JavaSecondaryTable buildJavaSecondaryTable(JavaEntity parent);
	
	JavaSequenceGenerator buildJavaSequenceGenerator(JavaJpaContextNode parent);
	
	JavaTableGenerator buildJavaTableGenerator(JavaJpaContextNode parent);
	
	JavaGeneratedValue buildJavaGeneratedValue(JavaIdMapping parent);
	
	JavaPrimaryKeyJoinColumn buildJavaPrimaryKeyJoinColumn(JavaJpaContextNode parent, JavaBaseJoinColumn.Owner owner);
	
	JavaAttributeOverrideContainer buildJavaAttributeOverrideContainer(JavaJpaContextNode parent, AttributeOverrideContainer.Owner owner);

	JavaAttributeOverride buildJavaAttributeOverride(JavaAttributeOverrideContainer parent, AttributeOverride.Owner owner);
	
	JavaAssociationOverrideContainer buildJavaAssociationOverrideContainer(JavaEntity parent, AssociationOverrideContainer.Owner owner);

	JavaAssociationOverride buildJavaAssociationOverride(JavaAssociationOverrideContainer parent, AssociationOverride.Owner owner);
	
	JavaAssociationOverrideRelationshipReference buildJavaAssociationOverrideRelationshipReference(JavaAssociationOverride parent);
	
	JavaQueryContainer buildJavaQueryContainer(JavaJpaContextNode parent);

	JavaNamedQuery buildJavaNamedQuery(JavaJpaContextNode parent);
	
	JavaNamedNativeQuery buildJavaNamedNativeQuery(JavaJpaContextNode parent);
	
	JavaQueryHint buildJavaQueryHint(JavaQuery parent);
	
	JavaUniqueConstraint buildJavaUniqueConstraint(JavaJpaContextNode parent, UniqueConstraint.Owner owner);
	
	JavaEnumeratedConverter buildJavaEnumeratedConverter(JavaAttributeMapping parent, JavaResourcePersistentAttribute jrpa);
	
	JavaTemporalConverter buildJavaTemporalConverter(JavaAttributeMapping parent, JavaResourcePersistentAttribute jrpa);
	
	JavaLobConverter buildJavaLobConverter(JavaAttributeMapping parent, JavaResourcePersistentAttribute jrpa);

	JavaConverter buildJavaNullConverter(JavaAttributeMapping parent);
	
	JavaOrderable buildJavaOrderable(JavaAttributeMapping parent);
	
	AssociationOverrideAnnotation buildJavaVirtualAssociationOverrideAnnotation(JavaResourcePersistentMember jrpm, String name, JoiningStrategy joiningStrategy);
	
}
