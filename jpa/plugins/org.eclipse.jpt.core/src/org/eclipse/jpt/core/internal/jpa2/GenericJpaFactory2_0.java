/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2;

import org.eclipse.jpt.core.context.JoiningStrategy;
import org.eclipse.jpt.core.context.PersistentType;
import org.eclipse.jpt.core.context.AssociationOverrideContainer.Owner;
import org.eclipse.jpt.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.core.context.java.JavaAssociationOverrideRelationshipReference;
import org.eclipse.jpt.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.core.context.java.JavaPersistentType;
import org.eclipse.jpt.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.core.context.persistence.Persistence;
import org.eclipse.jpt.core.internal.AbstractJpaFactory;
import org.eclipse.jpt.core.internal.jpa1.context.java.GenericJavaAssociationOverrideContainer;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaAssociationOverrideRelationshipReference2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaDerivedId2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaEmbeddable2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaPersistentAttribute2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaPersistentType2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.GenericJavaSequenceGenerator2_0;
import org.eclipse.jpt.core.internal.jpa2.context.java.VirtualAssociationOverride2_0Annotation;
import org.eclipse.jpt.core.internal.jpa2.context.orm.GenericOrmDerivedId2_0;
import org.eclipse.jpt.core.internal.jpa2.context.persistence.GenericPersistenceUnit2_0;
import org.eclipse.jpt.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.core.jpa2.PersistentTypeStaticMetamodelSynchronizer;
import org.eclipse.jpt.core.jpa2.StaticMetamodelSynchronizer;
import org.eclipse.jpt.core.jpa2.context.java.JavaDerivedId2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaEmbeddedMapping2_0;
import org.eclipse.jpt.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmDerivedId2_0;
import org.eclipse.jpt.core.jpa2.context.orm.OrmSingleRelationshipMapping2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.Persistence2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.jpa2.resource.orm.XmlDerivedId;
import org.eclipse.jpt.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentAttribute;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentMember;
import org.eclipse.jpt.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.core.resource.persistence.XmlPersistenceUnit;


/**
 * Central class that allows extenders to easily replace implementations of
 * various Dali interfaces.
 */
public class GenericJpaFactory2_0
	extends AbstractJpaFactory
{
	
	public GenericJpaFactory2_0() {
		super();
	}
		
	// ********** Core Model **********
	
	@Override
	public StaticMetamodelSynchronizer buildStaticMetamodelSynchronizer(JpaProject2_0 jpaProject) {
		return new GenericStaticMetamodelSynchronizer(jpaProject);
	}
	
	@Override
	public PersistentTypeStaticMetamodelSynchronizer buildPersistentTypeStaticMetamodelSynchronizer(StaticMetamodelSynchronizer staticMetamodelSynchronizer, PersistentType persistentType) {
		return new GenericPersistentTypeStaticMetamodelSynchronizer(staticMetamodelSynchronizer, persistentType);
	}
	
	
	// ********** Persistence Context Model **********
	
	@Override
	public PersistenceUnit2_0 buildPersistenceUnit(Persistence parent, XmlPersistenceUnit xmlPersistenceUnit) {
		return new GenericPersistenceUnit2_0((Persistence2_0) parent, xmlPersistenceUnit);
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
	public JavaSequenceGenerator buildJavaSequenceGenerator(JavaJpaContextNode parent) {
		return new GenericJavaSequenceGenerator2_0(parent);
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

	
	// ********** Orm Context Model **********
	
	public OrmDerivedId2_0 buildOrmDerivedId(OrmSingleRelationshipMapping2_0 parent, XmlDerivedId resource) {
		return new GenericOrmDerivedId2_0(parent, resource);
	}

}
