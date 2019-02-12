/*******************************************************************************
 * Copyright (c) 2009, 2016 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.jpa.core.JpaDataSource;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.JpaProject.Config;
import org.eclipse.jpt.jpa.core.context.Orderable;
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
import org.eclipse.jpt.jpa.core.jpa2.context.MetamodelSourceType2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.OrphanRemovalMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCacheableReference2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaCollectionTable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaDerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaElementCollectionMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaOrderable2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSingleRelationshipMapping2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.java.JavaSpecifiedOrderColumn2_0;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.SequenceGeneratorAnnotation2_0;
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
	public JpaProject buildJpaProject(Config config, IProgressMonitor monitor) {
		if ( ! (config instanceof JpaProject2_0.Config)) {
			throw new IllegalArgumentException("config must be 2.0-compatible: " + config); //$NON-NLS-1$
		}
		return super.buildJpaProject(config, monitor);
	}

	public MetamodelSourceType2_0.Synchronizer buildMetamodelSynchronizer(MetamodelSourceType2_0 sourceType) {
		return new GenericMetamodelSynchronizer2_0(sourceType);
	}

	public DatabaseIdentifierAdapter buildDatabaseIdentifierAdapter(JpaDataSource dataSource) {
		return new GenericJpaDatabaseIdentifierAdapter2_0(dataSource);
	}


	// ********** Java Context Model **********

	@Override
	public JavaPersistentType buildJavaPersistentType(JavaPersistentType.Parent parent, JavaResourceType jrt) {
		return new GenericJavaPersistentType2_0(parent, jrt);
	}

	@Override
	public JavaEmbeddable buildJavaEmbeddable(JavaPersistentType parent, EmbeddableAnnotation embeddableAnnotation) {
		return new GenericJavaEmbeddable2_0(parent, embeddableAnnotation);
	}

	@Override
	public JavaSequenceGenerator buildJavaSequenceGenerator(JavaGeneratorContainer parent, SequenceGeneratorAnnotation annotation) {
		return new GenericJavaSequenceGenerator2_0(parent, (SequenceGeneratorAnnotation2_0) annotation);
	}

	public JavaDerivedIdentity2_0 buildJavaDerivedIdentity(JavaSingleRelationshipMapping2_0 parent) {
		return new GenericJavaDerivedIdentity2_0(parent);
	}

	public JavaElementCollectionMapping2_0 buildJavaElementCollectionMapping(JavaSpecifiedPersistentAttribute parent) {
		return new GenericJavaElementCollectionMapping2_0(parent);
	}

	public Cacheable2_0 buildJavaCacheable(JavaCacheableReference2_0 parent) {
		return new GenericJavaCacheable2_0(parent);
	}

	public OrphanRemovable2_0 buildJavaOrphanRemoval(OrphanRemovalMapping2_0 parent) {
		return new GenericJavaOrphanRemoval2_0(parent);
	}

	public JavaCollectionTable2_0 buildJavaCollectionTable(JavaCollectionTable2_0.ParentAdapter parentAdapter) {
		return new GenericJavaCollectionTable2_0(parentAdapter);
	}

	public JavaSpecifiedOrderColumn2_0 buildJavaOrderColumn(JavaSpecifiedOrderColumn2_0.ParentAdapter parentAdapter) {
		return new GenericJavaOrderColumn2_0(parentAdapter);
	}

	public JavaSpecifiedColumn buildJavaMapKeyColumn(JavaSpecifiedColumn.ParentAdapter parentAdapter) {
		return new GenericJavaColumn(parentAdapter);
	}

	public JavaOrderable2_0 buildJavaOrderable(JavaOrderable2_0.ParentAdapter parentAdapter) {
		return new GenericJavaOrderable(parentAdapter);
	}

	@Override
	public Orderable buildJavaOrderable(JavaAttributeMapping parent) {
		throw new UnsupportedOperationException();
	}
}
