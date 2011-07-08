/*******************************************************************************
 * Copyright (c) 2007, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.content.IContentType;
import org.eclipse.jpt.common.core.JptResourceModel;
import org.eclipse.jpt.jpa.core.context.JpaRootContextNode;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.ReadOnlyJoinTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.ReadOnlyUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinTable;
import org.eclipse.jpt.jpa.core.context.java.JavaJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaLobConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOrderable;
import org.eclipse.jpt.jpa.core.context.java.JavaOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryHint;
import org.eclipse.jpt.jpa.core.context.java.JavaReadOnlyBaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaReadOnlyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSecondaryTable;
import org.eclipse.jpt.jpa.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaTable;
import org.eclipse.jpt.jpa.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaTemporalConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaTransientMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinTable;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualJoinTableRelationshipStrategy;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EnumeratedAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.core.resource.java.JoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.LobAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.MappedSuperclassAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.QueryHintAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TemporalAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.UniqueConstraintAnnotation;
import org.eclipse.jpt.jpa.core.resource.xml.JpaXmlResource;

/**
 * Use a JPA factory to build any core (e.g. {@link JpaProject})
 * model object or any Java (e.g. {@link JavaEntity}), ORM (e.g.
 * {@link EntityMappings}), or persistence (e.g. {@link PersistenceUnit})
 * context model objects.
 * <p>
 * Assumes a base JPA project context structure 
 * corresponding to the JPA spec:<ul>
 * <li>RootContext<ul>
 *     <li>persistence.xml<ul>
 *         <li>persistence unit(s)<ul>
 *             <li>mapping file(s)  (e.g. <code>orm.xml</code>)<ul>
 *                 <li>type mapping(s)  (e.g. Entity)<ul>
 *                     <li>attribute mapping(s)  (e.g. Basic)
 *                 </ul>
 *             </ul>
 *             <li>type mapping(s)
 *         </ul>
 *     </ul>
 * </ul>
 * </ul>
 *   ... and associated objects.
 *<p> 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 *
 * @see org.eclipse.jpt.jpa.core.internal.jpa1.GenericJpaFactory
 * 
 * @version 2.3
 * @since 2.0
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
	JpaFile buildJpaFile(JpaProject jpaProject, IFile file, IContentType contentType, JptResourceModel resourceModel);
	
	
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
	
	JavaEntity buildJavaEntity(JavaPersistentType parent, EntityAnnotation entityAnnotation);
	
	JavaMappedSuperclass buildJavaMappedSuperclass(JavaPersistentType parent, MappedSuperclassAnnotation mappedSuperclassAnnotation);
	
	JavaEmbeddable buildJavaEmbeddable(JavaPersistentType parent, EmbeddableAnnotation embeddableAnnotation);
	
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
	
	JavaGeneratorContainer buildJavaGeneratorContainer(JavaJpaContextNode parent, JavaGeneratorContainer.Owner owner);

	JavaTable buildJavaTable(JavaEntity parent, Table.Owner owner);
	
	JavaJoinTable buildJavaJoinTable(JavaJoinTableRelationshipStrategy parent, Table.Owner owner);
	
	JavaVirtualJoinTable buildJavaVirtualJoinTable(JavaVirtualJoinTableRelationshipStrategy parent, ReadOnlyTable.Owner owner, ReadOnlyJoinTable overriddenTable);
	
	JavaColumn buildJavaColumn(JavaJpaContextNode parent, JavaColumn.Owner owner);
	
	JavaVirtualColumn buildJavaVirtualColumn(JavaJpaContextNode parent, JavaVirtualColumn.Owner owner);

	JavaDiscriminatorColumn buildJavaDiscriminatorColumn(JavaEntity parent, JavaDiscriminatorColumn.Owner owner);
	
	JavaJoinColumn buildJavaJoinColumn(JavaJpaContextNode parent, JavaReadOnlyJoinColumn.Owner owner, JoinColumnAnnotation joinColumnAnnotation);
	
	JavaVirtualJoinColumn buildJavaVirtualJoinColumn(JavaJpaContextNode parent, JavaReadOnlyJoinColumn.Owner owner, ReadOnlyJoinColumn overriddenColumn);
	
	JavaSecondaryTable buildJavaSecondaryTable(JavaEntity parent, Table.Owner owner, SecondaryTableAnnotation tableAnnotation);
	
	JavaSequenceGenerator buildJavaSequenceGenerator(JavaJpaContextNode parent, SequenceGeneratorAnnotation sequenceGeneratorAnnotation);
	
	JavaTableGenerator buildJavaTableGenerator(JavaJpaContextNode parent, TableGeneratorAnnotation tableGeneratorAnnotation);
	
	JavaGeneratedValue buildJavaGeneratedValue(JavaIdMapping parent, GeneratedValueAnnotation generatedValueAnnotation);
	
	JavaPrimaryKeyJoinColumn buildJavaPrimaryKeyJoinColumn(JavaJpaContextNode parent, JavaReadOnlyBaseJoinColumn.Owner owner, PrimaryKeyJoinColumnAnnotation pkJoinColumnAnnotation);
	
	JavaAttributeOverrideContainer buildJavaAttributeOverrideContainer(JavaJpaContextNode parent, JavaAttributeOverrideContainer.Owner owner);

	JavaAttributeOverride buildJavaAttributeOverride(JavaAttributeOverrideContainer parent, AttributeOverrideAnnotation annotation);
	
	JavaVirtualAttributeOverride buildJavaVirtualAttributeOverride(JavaAttributeOverrideContainer parent, String name);
	
	JavaAssociationOverrideContainer buildJavaAssociationOverrideContainer(JavaJpaContextNode parent, JavaAssociationOverrideContainer.Owner owner);

	JavaAssociationOverride buildJavaAssociationOverride(JavaAssociationOverrideContainer parent, AssociationOverrideAnnotation annotation);
	
	JavaVirtualAssociationOverride buildJavaVirtualAssociationOverride(JavaAssociationOverrideContainer parent, String name);
	
	JavaOverrideRelationship buildJavaOverrideRelationship(JavaAssociationOverride parent);
	
	JavaVirtualOverrideRelationship buildJavaVirtualOverrideRelationship(JavaVirtualAssociationOverride parent);
	
	JavaQueryContainer buildJavaQueryContainer(JavaJpaContextNode parent, JavaQueryContainer.Owner owner);

	JavaNamedQuery buildJavaNamedQuery(JavaJpaContextNode parent, NamedQueryAnnotation namedQueryAnnotation);
	
	JavaNamedNativeQuery buildJavaNamedNativeQuery(JavaJpaContextNode parent, NamedNativeQueryAnnotation namedNativeQueryAnnotation);
	
	JavaQueryHint buildJavaQueryHint(JavaQuery parent, QueryHintAnnotation queryHintAnnotation);
	
	JavaUniqueConstraint buildJavaUniqueConstraint(JavaJpaContextNode parent, UniqueConstraint.Owner owner, UniqueConstraintAnnotation constraintAnnotation);
	
	JavaVirtualUniqueConstraint buildJavaVirtualUniqueConstraint(JavaJpaContextNode parent, ReadOnlyUniqueConstraint overriddenUniqueConstraint);
	
	JavaEnumeratedConverter buildJavaEnumeratedConverter(JavaAttributeMapping parent, EnumeratedAnnotation annotation);
	
	JavaTemporalConverter buildJavaTemporalConverter(JavaAttributeMapping parent, TemporalAnnotation annotation);
	
	JavaLobConverter buildJavaLobConverter(JavaAttributeMapping parent, LobAnnotation annotation);

	JavaOrderable buildJavaOrderable(JavaAttributeMapping parent);
}
