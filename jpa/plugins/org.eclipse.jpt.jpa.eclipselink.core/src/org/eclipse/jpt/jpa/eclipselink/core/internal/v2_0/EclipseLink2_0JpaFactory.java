/*******************************************************************************
* Copyright (c) 2009, 2010 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0;

import org.eclipse.jpt.jpa.core.JpaDataSource;
import org.eclipse.jpt.jpa.core.JpaProject.Config;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaNamedQuery;
import org.eclipse.jpt.jpa.core.context.java.JavaOrderable;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentAttribute;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaColumn;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaOrderable;
import org.eclipse.jpt.jpa.core.internal.jpa2.GenericJpaDatabaseIdentifierAdapter;
import org.eclipse.jpt.jpa.core.internal.jpa2.GenericMetamodelSynchronizer;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaCacheable2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaCollectionTable2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaNamedQuery2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaOrderColumn2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaOrphanRemoval2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaPersistentType2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaSequenceGenerator2_0;
import org.eclipse.jpt.jpa.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelSourceType;
import org.eclipse.jpt.jpa.core.jpa2.context.Orderable2_0.Owner;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCacheableHolder2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaEmbeddedMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOrderColumn2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOrderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOrphanRemovable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOrphanRemovalHolder2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.NamedQuery2_0Annotation;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.SequenceGenerator2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.JavaResourcePersistentType;
import org.eclipse.jpt.jpa.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.jpa.db.DatabaseIdentifierAdapter;
import org.eclipse.jpt.jpa.eclipselink.core.EclipseLinkJpaProject;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaFactory;
import org.eclipse.jpt.jpa.eclipselink.core.internal.v2_0.context.java.JavaEclipseLinkElementCollectionMapping2_0;

/**
 *  EclipseLink 2.0 factory
 */
public class EclipseLink2_0JpaFactory
	extends EclipseLinkJpaFactory
	implements JpaFactory2_0
{
	public EclipseLink2_0JpaFactory() {
		super();
	}
	
	
	// ********** Core Model **********
	
	@Override
	public EclipseLinkJpaProject buildJpaProject(Config config) {
		if ( ! (config instanceof JpaProject2_0.Config)) {
			throw new IllegalArgumentException("config must be 2.0-compatible: " + config); //$NON-NLS-1$
		}
		return super.buildJpaProject(config);
	}
	
	public MetamodelSourceType.Synchronizer buildMetamodelSynchronizer(MetamodelSourceType sourceType) {
		return new GenericMetamodelSynchronizer(sourceType);
	}
	
	public DatabaseIdentifierAdapter buildDatabaseIdentifierAdapter(JpaDataSource dataSource) {
		return new GenericJpaDatabaseIdentifierAdapter(dataSource);
	}
	

	// ********** Java Context Model **********

	@Override
	public JavaPersistentType buildJavaPersistentType(PersistentType.Owner owner, JavaResourcePersistentType jrpt) {
		return new GenericJavaPersistentType2_0(owner, jrpt);
	}
	
	@Override
	public JavaSequenceGenerator buildJavaSequenceGenerator(JavaJpaContextNode parent, SequenceGeneratorAnnotation annotation) {
		return new GenericJavaSequenceGenerator2_0(parent, (SequenceGenerator2_0Annotation) annotation);
	}

	//The 2.0 JPA spec supports association overrides on an embedded mapping while the 1.0 spec did not
	public JavaAssociationOverrideContainer buildJavaAssociationOverrideContainer(JavaEmbeddedMapping2_0 parent, JavaAssociationOverrideContainer.Owner owner) {
		return new GenericJavaAssociationOverrideContainer(parent, owner);
	}
	
	public JavaDerivedIdentity2_0 buildJavaDerivedIdentity(JavaSingleRelationshipMapping2_0 parent) {
		return new GenericJavaDerivedIdentity2_0(parent);
	}
	
	public JavaElementCollectionMapping2_0 buildJavaElementCollectionMapping2_0(JavaPersistentAttribute parent) {
		return new JavaEclipseLinkElementCollectionMapping2_0(parent);
	}
	
	public JavaCacheable2_0 buildJavaCacheable(JavaCacheableHolder2_0 parent) {
		return new GenericJavaCacheable2_0(parent);
	}
	
	public JavaOrphanRemovable2_0 buildJavaOrphanRemoval(JavaOrphanRemovalHolder2_0 parent) {
		return new GenericJavaOrphanRemoval2_0(parent);
	}

	public JavaNamedQuery buildJavaNamedQuery(JavaJpaContextNode parent, NamedQuery2_0Annotation annotation) {
		return new GenericJavaNamedQuery2_0(parent, annotation);
	}

	public JavaCollectionTable2_0 buildJavaCollectionTable(JavaElementCollectionMapping2_0 parent, Table.Owner owner) {
		return new GenericJavaCollectionTable2_0(parent, owner);
	}

	public JavaOrderColumn2_0 buildJavaOrderColumn(JavaOrderable2_0 parent, JavaNamedColumn.Owner owner) {
		return new GenericJavaOrderColumn2_0(parent, owner);
	}

	public JavaColumn buildJavaMapKeyColumn(JavaJpaContextNode parent, JavaColumn.Owner owner) {
		return new GenericJavaColumn(parent, owner);
	}

	public JavaOrderable2_0 buildJavaOrderable(JavaAttributeMapping parent, Owner owner) {
		return new GenericJavaOrderable(parent, owner);
	}

	@Override
	public JavaOrderable buildJavaOrderable(JavaAttributeMapping parent) {
		throw new UnsupportedOperationException();
	}
}
