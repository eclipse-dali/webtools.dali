/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.platform;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jpt.core.JpaProject;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.JpaRootContextNode;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaManyToOneMapping;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.core.context.persistence.JarFileRef;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceXml;
import org.eclipse.jpt.core.internal.jpa2.GenericJpaProject2_0;
import org.eclipse.jpt.core.internal.jpa2.GenericPersistentTypeStaticMetamodelSynchronizer;
import org.eclipse.jpt.core.internal.jpa2.GenericStaticMetamodelSynchronizer;
import org.eclipse.jpt.core.internal.jpa2.context.GenericRootContextNode2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaAssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaEmbeddable2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaManyToOneMapping2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaOneToOneMapping2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaPersistentAttribute2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaPersistentType2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaSequenceGenerator2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.VirtualAssociationOverride2_0Annotation;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.GenericClassRef2_0;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.GenericJarFileRef2_0;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.GenericMappingFileRef2_0;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.GenericPersistence2_0;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.GenericPersistenceUnit2_0;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.GenericPersistenceXml2_0;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.ImpliedMappingFileRef2_0;
import org.eclipse.jpt.core.internal.platform.AbstractJpaFactory;
import org.eclipse.jpt.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.core.jpa2.PersistentTypeStaticMetamodelSynchronizer;
import org.eclipse.jpt.core.jpa2.StaticMetamodelSynchronizer;
import org.eclipse.jpt.core.jpa2.context.JpaRootContextNode2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.ClassRef2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.MappingFileRef2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.Persistence2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceXml2_0;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.persistence.XmlJarFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlJavaClassRef;
import org.eclipse.jpt.core.resource.persistence.XmlMappingFileRef;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;


/**
 * Central class that allows extenders to easily replace implementations of
 * various Dali interfaces.
 */
public class GenericJpaFactory2_0
	extends AbstractJpaFactory
	implements JpaFactory2_0
{
	
	public GenericJpaFactory2_0() {
		super();
	}
		
	// ********** Core Model **********

	@Override
	public JpaProject2_0 buildJpaProject(JpaProject.Config config) throws CoreException {
		return new GenericJpaProject2_0(config);
	}
	
	public StaticMetamodelSynchronizer buildStaticMetamodelSynchronizer(JpaProject2_0 jpaProject) {
		return new GenericStaticMetamodelSynchronizer(jpaProject);
	}
	
	public PersistentTypeStaticMetamodelSynchronizer buildPersistentTypeStaticMetamodelSynchronizer(StaticMetamodelSynchronizer staticMetamodelSynchronizer, PersistentType persistentType) {
		return new GenericPersistentTypeStaticMetamodelSynchronizer(staticMetamodelSynchronizer, persistentType);
	}
	
	
	// ********** Context Nodes **********
	
	@Override
	public JpaRootContextNode2_0 buildRootContextNode(JpaProject parent) {
		return new GenericRootContextNode2_0((JpaProject2_0) parent);
	}

	
	// ********** Persistence Context Model **********
	
	@Override
	public PersistenceXml2_0 buildPersistenceXml(JpaRootContextNode parent, JpaXmlResource resource) {
		return new GenericPersistenceXml2_0((JpaRootContextNode2_0) parent, resource);
	}
	
	@Override
	public Persistence2_0 buildPersistence(PersistenceXml parent, XmlPersistence xmlPersistence) {
		return new GenericPersistence2_0((PersistenceXml2_0) parent, xmlPersistence);
	}
	
	@Override
	public PersistenceUnit2_0 buildPersistenceUnit(Persistence parent, XmlPersistenceUnit xmlPersistenceUnit) {
		return new GenericPersistenceUnit2_0((Persistence2_0) parent, xmlPersistenceUnit);
	}
	
	@Override
	public MappingFileRef2_0 buildMappingFileRef(PersistenceUnit parent, XmlMappingFileRef xmlMappingFileRef) {
		return new GenericMappingFileRef2_0((PersistenceUnit2_0) parent, xmlMappingFileRef);
	}
	
	@Override
	public MappingFileRef2_0 buildImpliedMappingFileRef(PersistenceUnit parent) {
		return new ImpliedMappingFileRef2_0((PersistenceUnit2_0) parent, JptCorePlugin.DEFAULT_ORM_XML_FILE_PATH );
	}
	
	@Override
	public ClassRef2_0 buildClassRef(PersistenceUnit parent, XmlJavaClassRef classRef) {
		return new GenericClassRef2_0((PersistenceUnit2_0) parent, classRef);
	}
	
	@Override
	public ClassRef2_0 buildClassRef(PersistenceUnit parent, String className) {
		return new GenericClassRef2_0((PersistenceUnit2_0) parent, className);
	}
	
	@Override
	public JarFileRef buildJarFileRef(PersistenceUnit parent, XmlJarFileRef xmlJarFileRef) {
		return new GenericJarFileRef2_0((PersistenceUnit2_0) parent, xmlJarFileRef);
	}
	
	
	// ********** Java Context Model **********
	
	@Override
	public JavaPersistentType buildJavaPersistentType(PersistentType.Owner owner, JavaResourcePersistentType jrpt) {
		return new GenericJavaPersistentType2_0(owner, jrpt);
	}
	
	@Override
	public JavaPersistentAttribute buildJavaPersistentAttribute(PersistentType parent, JavaResourcePersistentAttribute jrpa) {
		return new GenericJavaPersistentAttribute2_0(parent, jrpa);
	}
	
	@Override
	public JavaEmbeddable buildJavaEmbeddable(JavaPersistentType parent) {
		return new GenericJavaEmbeddable2_0(parent);
	}
	
	@Override
	public JavaManyToOneMapping buildJavaManyToOneMapping(JavaPersistentAttribute parent) {
		return new GenericJavaManyToOneMapping2_0(parent);
	}
	
	@Override
	public JavaOneToOneMapping buildJavaOneToOneMapping(JavaPersistentAttribute parent) {
		return new GenericJavaOneToOneMapping2_0(parent);
	}

	@Override
	public JavaSequenceGenerator buildJavaSequenceGenerator(JavaJpaContextNode parent) {
		return new GenericJavaSequenceGenerator2_0(parent);
	}
	
	@Override
	public JavaAssociationOverrideRelationshipReference buildJavaAssociationOverrideRelationshipReference(JavaAssociationOverride parent) {
		return new GenericJavaAssociationOverrideRelationshipReference2_0(parent);
	}
	
	@Override
	public AssociationOverrideAnnotation buildJavaVirtualAssociationOverrideAnnotation(JavaResourcePersistentType jrpt, String name, JoiningStrategy joiningStrategy) {
		return new VirtualAssociationOverride2_0Annotation(jrpt, name, joiningStrategy);
	}

}
