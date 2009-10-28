/*******************************************************************************
* Copyright (c) 2009 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.v2_0;

import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.AssociationOverrideContainer.Owner;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaOneToManyMapping;
import org.eclipse.jpt.core.context.java.JavaOneToOneMapping;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaAssociationOverrideContainer;
import org.eclipse.jpt.core.internal.jpa2.GenericMetamodelSynchronizer;
import org.eclipse.jpt.core.internal.jpa2.GenericPersistentTypeMetamodelSynchronizer;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaAssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaCacheable2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaDerivedId2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaMapsId2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaPersistentType2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaSequenceGenerator2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.VirtualAssociationOverride2_0Annotation;
import org.eclipse.jpt.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.core.jpa2.MetamodelSynchronizer;
import org.eclipse.jpt.core.jpa2.PersistentTypeMetamodelSynchronizer;
import org.eclipse.jpt.core.jpa2.context.java.JavaCacheable2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaCacheableHolder2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaDerivedId2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaEmbeddedMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaMapsId2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.context.java.JavaEclipseLinkOneToManyMapping2_0;
import org.eclipse.jpt.eclipselink.core.internal.v2_0.context.java.JavaEclipseLinkOneToOneMapping2_0;

/**
 *  EclipseLink2_0JpaFactory
 */
public class EclipseLink2_0JpaFactory
	extends EclipseLinkJpaFactory
{
	protected EclipseLink2_0JpaFactory() {
		super();
	}
	
	// ********** Core Model **********
	
	@Override
	public MetamodelSynchronizer buildMetamodelSynchronizer(JpaProject2_0 jpaProject) {
		return new GenericMetamodelSynchronizer(jpaProject);
	}
	
	@Override
	public PersistentTypeMetamodelSynchronizer buildPersistentTypeMetamodelSynchronizer(PersistentTypeMetamodelSynchronizer.Owner owner, PersistentType persistentType) {
		return new GenericPersistentTypeMetamodelSynchronizer(owner, persistentType);
	}
	
	// ********** Java Context Model **********
	@Override
	public JavaPersistentType buildJavaPersistentType(PersistentType.Owner owner, JavaResourcePersistentType jrpt) {
		return new GenericJavaPersistentType2_0(owner, jrpt);
	}
	
	//The 2.0 JPA spec supports association overrides on an embedded mapping while the 1.0 spec did not
	@Override
	public JavaAssociationOverrideContainer buildJavaAssociationOverrideContainer(JavaEmbeddedMapping2_0 parent, Owner owner) {
		return new GenericJavaAssociationOverrideContainer(parent, owner);
	}
	
	@Override
	public JavaAssociationOverrideRelationshipReference buildJavaAssociationOverrideRelationshipReference(JavaAssociationOverride parent) {
		return new GenericJavaAssociationOverrideRelationshipReference2_0(parent);
	}
	
	@Override
	public AssociationOverrideAnnotation buildJavaVirtualAssociationOverrideAnnotation(JavaResourcePersistentMember jrpm, String name, JoiningStrategy joiningStrategy) {
		return new VirtualAssociationOverride2_0Annotation(jrpm, name, joiningStrategy);
	}
	
	@Override
	public JavaDerivedId2_0 buildJavaDerivedId(JavaSingleRelationshipMapping2_0 parent) {
		return new GenericJavaDerivedId2_0(parent);
	}
	
	@Override
	public JavaElementCollectionMapping2_0 buildJavaElementCollectionMapping2_0(JavaPersistentAttribute parent) {
		return new GenericJavaElementCollectionMapping2_0(parent);
	}
	
	@Override
	public JavaMapsId2_0 buildJavaMapsId(JavaSingleRelationshipMapping2_0 parent) {
		return new GenericJavaMapsId2_0(parent);
	}
	
	@Override
	public JavaSequenceGenerator buildJavaSequenceGenerator(JavaJpaContextNode parent) {
		return new GenericJavaSequenceGenerator2_0(parent);
	}
	
	@Override
	public JavaCacheable2_0 buildJavaCacheable(JavaCacheableHolder2_0 parent) {
		return new GenericJavaCacheable2_0(parent);
	}
	
	@Override
	public JavaOneToManyMapping buildJavaOneToManyMapping(JavaPersistentAttribute parent) {
		return new JavaEclipseLinkOneToManyMapping2_0(parent);
	}
	
	@Override
	public JavaOneToOneMapping buildJavaOneToOneMapping(JavaPersistentAttribute parent) {
		return new JavaEclipseLinkOneToOneMapping2_0(parent);
	}
}