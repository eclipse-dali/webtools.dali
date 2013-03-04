/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
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
import org.eclipse.jpt.common.core.resource.java.JavaResourceField;
import org.eclipse.jpt.common.core.resource.java.JavaResourceMethod;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.core.resource.xml.JptXmlResource;
import org.eclipse.jpt.jpa.core.context.BaseJoinColumn;
import org.eclipse.jpt.jpa.core.context.Converter;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.JoinTable;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.JpaContextModelRoot;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.Orderable;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.SpecifiedUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.UniqueConstraint;
import org.eclipse.jpt.jpa.core.context.VirtualColumn;
import org.eclipse.jpt.jpa.core.context.VirtualJoinColumn;
import org.eclipse.jpt.jpa.core.context.VirtualJoinTable;
import org.eclipse.jpt.jpa.core.context.VirtualOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.VirtualUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.Accessor;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaBaseEnumeratedConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaBaseTemporalConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaBasicMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddedIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddedMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEntity;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratedValue;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaIdMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaLobConverter;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaMappedSuperclass;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedNativeQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaQueryHint;
import org.eclipse.jpt.jpa.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedAttributeOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedDiscriminatorColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedJoinTable;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedOverrideRelationship;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPrimaryKeyJoinColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedSecondaryTable;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedTable;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedUniqueConstraint;
import org.eclipse.jpt.jpa.core.context.java.JavaTable;
import org.eclipse.jpt.jpa.core.context.java.JavaTableGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaTransientMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaTypeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaVersionMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAttributeOverride;
import org.eclipse.jpt.jpa.core.context.orm.EntityMappings;
import org.eclipse.jpt.jpa.core.context.persistence.MappingFileRef;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.jpa.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.AttributeOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.BaseEnumeratedAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.BaseTemporalAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.CompleteJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.EntityAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.GeneratedValueAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.LobAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.MappedSuperclassAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NamedNativeQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NamedQueryAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.PrimaryKeyJoinColumnAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.QueryHintAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SecondaryTableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.TableGeneratorAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.UniqueConstraintAnnotation;

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
 * @version 3.3
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
	 * Build a (/an updated) context model root for the
	 * specified JPA project.
	 * The root will be built once, but updated many times.
	 * @see org.eclipse.jpt.jpa.core.internal.AbstractJpaProject#update(org.eclipse.core.runtime.IProgressMonitor)
	 */
	JpaContextModelRoot buildContextModelRoot(JpaProject jpaProject);


	// ********** XML Context Model **********

	PersistenceXml buildPersistenceXml(JpaContextModelRoot parent, JptXmlResource resource);

	MappingFile buildMappingFile(MappingFileRef parent, Object resourceMappingFile);


	// ********** Java Context Model **********
	
	JavaPersistentType buildJavaPersistentType(PersistentType.Parent parent, JavaResourceType jrt);
	
	JavaEntity buildJavaEntity(JavaPersistentType parent, EntityAnnotation entityAnnotation);
	
	JavaMappedSuperclass buildJavaMappedSuperclass(JavaPersistentType parent, MappedSuperclassAnnotation mappedSuperclassAnnotation);
	
	JavaEmbeddable buildJavaEmbeddable(JavaPersistentType parent, EmbeddableAnnotation embeddableAnnotation);
	
	JavaTypeMapping buildJavaNullTypeMapping(JavaPersistentType parent);

	JavaSpecifiedPersistentAttribute buildJavaPersistentAttribute(PersistentType parent, Accessor accessor);
	
	JavaSpecifiedPersistentAttribute buildJavaPersistentField(PersistentType parent, JavaResourceField resourceField);
	
	JavaSpecifiedPersistentAttribute buildJavaPersistentProperty(PersistentType parent, JavaResourceMethod resourceGetter, JavaResourceMethod resourceSetter);
	
	JavaBasicMapping buildJavaBasicMapping(JavaSpecifiedPersistentAttribute parent);
	
	JavaEmbeddedIdMapping buildJavaEmbeddedIdMapping(JavaSpecifiedPersistentAttribute parent);
	
	JavaEmbeddedMapping buildJavaEmbeddedMapping(JavaSpecifiedPersistentAttribute parent);
	
	JavaIdMapping buildJavaIdMapping(JavaSpecifiedPersistentAttribute parent);
	
	JavaManyToManyMapping buildJavaManyToManyMapping(JavaSpecifiedPersistentAttribute parent);
	
	JavaManyToOneMapping buildJavaManyToOneMapping(JavaSpecifiedPersistentAttribute parent);
	
	JavaOneToManyMapping buildJavaOneToManyMapping(JavaSpecifiedPersistentAttribute parent);
	
	JavaOneToOneMapping buildJavaOneToOneMapping(JavaSpecifiedPersistentAttribute parent);
	
	JavaTransientMapping buildJavaTransientMapping(JavaSpecifiedPersistentAttribute parent);
	
	JavaVersionMapping buildJavaVersionMapping(JavaSpecifiedPersistentAttribute parent);
	
	JavaAttributeMapping buildJavaNullAttributeMapping(JavaSpecifiedPersistentAttribute parent);
	
	JavaGeneratorContainer buildJavaGeneratorContainer(JavaGeneratorContainer.Parent parentAdapter);

	JavaSpecifiedTable buildJavaTable(JavaTable.ParentAdapter parentAdapter);
	
	JavaSpecifiedJoinTable buildJavaJoinTable(JavaSpecifiedJoinTable.ParentAdapter parentAdapter);
	
	VirtualJoinTable buildJavaVirtualJoinTable(VirtualJoinTable.ParentAdapter parentAdapter, JoinTable overriddenTable);
	
	JavaSpecifiedColumn buildJavaColumn(JavaSpecifiedColumn.ParentAdapter parentAdapter);
	
	VirtualColumn buildJavaVirtualColumn(VirtualColumn.ParentAdapter parentAdapter);

	JavaSpecifiedDiscriminatorColumn buildJavaDiscriminatorColumn(JavaSpecifiedDiscriminatorColumn.ParentAdapter parentAdapter);
	
	JavaSpecifiedJoinColumn buildJavaJoinColumn(JoinColumn.ParentAdapter parentAdapter, CompleteJoinColumnAnnotation joinColumnAnnotation);
	
	VirtualJoinColumn buildJavaVirtualJoinColumn(JoinColumn.ParentAdapter parentAdapter, JoinColumn overriddenColumn);
	
	JavaSpecifiedSecondaryTable buildJavaSecondaryTable(JavaSpecifiedSecondaryTable.ParentAdapter parentAdapter, SecondaryTableAnnotation tableAnnotation);
	
	JavaSequenceGenerator buildJavaSequenceGenerator(JavaGeneratorContainer parent, SequenceGeneratorAnnotation sequenceGeneratorAnnotation);
	
	JavaTableGenerator buildJavaTableGenerator(JavaGeneratorContainer parent, TableGeneratorAnnotation tableGeneratorAnnotation);
	
	JavaGeneratedValue buildJavaGeneratedValue(JavaAttributeMapping parent, GeneratedValueAnnotation generatedValueAnnotation);
	
	JavaSpecifiedPrimaryKeyJoinColumn buildJavaPrimaryKeyJoinColumn(BaseJoinColumn.ParentAdapter parentAdapter, PrimaryKeyJoinColumnAnnotation pkJoinColumnAnnotation);
	
	JavaAttributeOverrideContainer buildJavaAttributeOverrideContainer(JavaAttributeOverrideContainer.ParentAdapter parentAdapter);

	JavaSpecifiedAttributeOverride buildJavaAttributeOverride(JavaAttributeOverrideContainer parent, AttributeOverrideAnnotation annotation);
	
	JavaVirtualAttributeOverride buildJavaVirtualAttributeOverride(JavaAttributeOverrideContainer parent, String name);
	
	JavaAssociationOverrideContainer buildJavaAssociationOverrideContainer(JavaAssociationOverrideContainer.ParentAdapter parentAdapter);

	JavaSpecifiedAssociationOverride buildJavaAssociationOverride(JavaAssociationOverrideContainer parent, AssociationOverrideAnnotation annotation);
	
	JavaVirtualAssociationOverride buildJavaVirtualAssociationOverride(JavaAssociationOverrideContainer parent, String name);
	
	JavaSpecifiedOverrideRelationship buildJavaOverrideRelationship(JavaSpecifiedAssociationOverride parent);
	
	VirtualOverrideRelationship buildJavaVirtualOverrideRelationship(JavaVirtualAssociationOverride parent);
	
	JavaQueryContainer buildJavaQueryContainer(JavaQueryContainer.Parent owner);

	JavaNamedQuery buildJavaNamedQuery(JavaQueryContainer parent, NamedQueryAnnotation namedQueryAnnotation);
	
	JavaNamedNativeQuery buildJavaNamedNativeQuery(JavaQueryContainer parent, NamedNativeQueryAnnotation namedNativeQueryAnnotation);
	
	JavaQueryHint buildJavaQueryHint(JavaQuery parent, QueryHintAnnotation queryHintAnnotation);
	
	JavaSpecifiedUniqueConstraint buildJavaUniqueConstraint(SpecifiedUniqueConstraint.Parent parent, UniqueConstraintAnnotation constraintAnnotation);
	
	VirtualUniqueConstraint buildJavaVirtualUniqueConstraint(JpaContextModel parent, UniqueConstraint overriddenUniqueConstraint);
	
	JavaBaseEnumeratedConverter buildJavaBaseEnumeratedConverter(Converter.ParentAdapter<JavaAttributeMapping> parentAdapter, BaseEnumeratedAnnotation annotation);
	
	JavaBaseTemporalConverter buildJavaBaseTemporalConverter(Converter.ParentAdapter<JavaAttributeMapping> parentAdapter, BaseTemporalAnnotation annotation);
	
	JavaLobConverter buildJavaLobConverter(Converter.ParentAdapter<JavaAttributeMapping> parentAdapter, LobAnnotation annotation);

	Orderable buildJavaOrderable(JavaAttributeMapping parent);
}
