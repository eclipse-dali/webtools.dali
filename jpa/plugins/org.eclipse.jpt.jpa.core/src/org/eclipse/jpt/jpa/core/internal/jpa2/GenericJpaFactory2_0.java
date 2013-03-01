/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2;

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jpa.core.JpaDataSource;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaProject.Config;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.NamedColumn;
import org.eclipse.jpt.jpa.core.context.Orderable;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.SpecifiedTable;
import org.eclipse.jpt.jpa.core.context.java.JavaAttributeMapping;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.jpa.core.context.java.JavaGeneratorContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.java.JavaSequenceGenerator;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedColumn;
import org.eclipse.jpt.jpa.core.context.java.JavaSpecifiedPersistentAttribute;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaFactory;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaColumn;
import org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJavaOrderable;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaCacheable2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaCollectionTable2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaEmbeddable2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaOrderColumn2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaOrphanRemoval2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaPersistentType2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.java.GenericJavaSequenceGenerator2_0;
import org.eclipse.jpt.jpa.core.jpa2.JpaFactory2_0;
import org.eclipse.jpt.jpa.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelSourceType;
import org.eclipse.jpt.jpa.core.jpa2.context.Orderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovalMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCacheableReference2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOrderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSpecifiedOrderColumn2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.SequenceGenerator2_0Annotation;
import org.eclipse.jpt.jpa.core.resource.java.EmbeddableAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.SequenceGeneratorAnnotation;
import org.eclipse.jpt.jpa.db.DatabaseIdentifierAdapter;


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
	public JpaProject buildJpaProject(Config config) {
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
	public JavaPersistentType buildJavaPersistentType(PersistentType.Parent owner, JavaResourceType jrt) {
		return new GenericJavaPersistentType2_0(owner, jrt);
	}

	@Override
	public JavaEmbeddable buildJavaEmbeddable(JavaPersistentType parent, EmbeddableAnnotation embeddableAnnotation) {
		return new GenericJavaEmbeddable2_0(parent, embeddableAnnotation);
	}

	@Override
	public JavaSequenceGenerator buildJavaSequenceGenerator(JavaGeneratorContainer parent, SequenceGeneratorAnnotation annotation) {
		return new GenericJavaSequenceGenerator2_0(parent, (SequenceGenerator2_0Annotation) annotation);
	}

	public JavaDerivedIdentity2_0 buildJavaDerivedIdentity(JavaSingleRelationshipMapping2_0 parent) {
		return new GenericJavaDerivedIdentity2_0(parent);
	}

	public JavaElementCollectionMapping2_0 buildJavaElementCollectionMapping2_0(JavaSpecifiedPersistentAttribute parent) {
		return new GenericJavaElementCollectionMapping2_0(parent);
	}

	public Cacheable2_0 buildJavaCacheable(JavaCacheableReference2_0 parent) {
		return new GenericJavaCacheable2_0(parent);
	}

	public OrphanRemovable2_0 buildJavaOrphanRemoval(OrphanRemovalMapping2_0 parent) {
		return new GenericJavaOrphanRemoval2_0(parent);
	}

	public JavaCollectionTable2_0 buildJavaCollectionTable(JavaElementCollectionMapping2_0 parent, SpecifiedTable.Owner owner) {
		return new GenericJavaCollectionTable2_0(parent, owner);
	}

	public JavaSpecifiedOrderColumn2_0 buildJavaOrderColumn(JavaOrderable2_0 parent, NamedColumn.Owner owner) {
		return new GenericJavaOrderColumn2_0(parent, owner);
	}

	public JavaSpecifiedColumn buildJavaMapKeyColumn(JpaContextModel parent, JavaSpecifiedColumn.Owner owner) {
		return new GenericJavaColumn(parent, owner);
	}

	public JavaOrderable2_0 buildJavaOrderable(Orderable2_0.ParentAdapter<JavaAttributeMapping> parentAdapter) {
		return new GenericJavaOrderable(parentAdapter);
	}

	@Override
	public Orderable buildJavaOrderable(JavaAttributeMapping parent) {
		throw new UnsupportedOperationException();
	}
}
