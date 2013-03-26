/*******************************************************************************
 * Copyright (c) 2012, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2_1;

import java.util.ArrayList;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.common.utility.internal.collection.CollectionTools;
import org.eclipse.jpt.jpa.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.AccessAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.AssociationOverrideAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.CacheableAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.CollectionTableAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.ElementCollectionAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.GeneratedAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.MapKeyClassAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.MapKeyColumnAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.MapKeyEnumeratedAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.MapKeyJoinColumnAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.MapKeyJoinColumnsAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.MapKeyTemporalAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.MapsIdAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.NamedQueryAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.OrderColumnAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.SequenceGeneratorAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.StaticMetamodelAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.resource.java.ConverterAnnotationDefinition2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.resource.java.NamedStoredProcedureQueriesAnnotationDefinition2_1;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.resource.java.NamedStoredProcedureQueryAnnotationDefinition2_1;
import org.eclipse.jpt.jpa.core.internal.resource.java.AssociationOverridesAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.AttributeOverrideAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.AttributeOverridesAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.BasicAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.ColumnAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.DiscriminatorColumnAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.DiscriminatorValueAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.EmbeddableAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.EmbeddedAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.EmbeddedIdAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.EntityAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.EnumeratedAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.GeneratedValueAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.IdAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.IdClassAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.InheritanceAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.JoinColumnAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.JoinColumnsAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.JoinTableAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.LobAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.ManyToManyAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.ManyToOneAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.MapKeyAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.MappedSuperclassAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.NamedNativeQueriesAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.NamedNativeQueryAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.NamedQueriesAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.OneToManyAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.OneToOneAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.OrderByAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.PrimaryKeyJoinColumnAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.PrimaryKeyJoinColumnsAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.SecondaryTableAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.SecondaryTablesAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.TableAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.TableGeneratorAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.TemporalAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.TransientAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.VersionAnnotationDefinition;

public class GenericJpaAnnotationDefinitionProvider2_1
	extends AbstractJpaAnnotationDefinitionProvider
{
	// singleton
	private static final JpaAnnotationDefinitionProvider INSTANCE = new GenericJpaAnnotationDefinitionProvider2_1();

	/**
	 * Return the singleton
	 */
	public static JpaAnnotationDefinitionProvider instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private GenericJpaAnnotationDefinitionProvider2_1() {
		super();
	}

	@Override
	protected void addAnnotationDefinitionsTo(ArrayList<AnnotationDefinition> definitions) {
		CollectionTools.addAll(definitions, ANNOTATION_DEFINITIONS);
	}

	protected static final AnnotationDefinition[] ANNOTATION_DEFINITIONS = new AnnotationDefinition[] {
		AttributeOverridesAnnotationDefinition.instance(),
		AssociationOverridesAnnotationDefinition.instance(),
		AccessAnnotationDefinition2_0.instance(),
		BasicAnnotationDefinition.instance(),
		CacheableAnnotationDefinition2_0.instance(),
		CollectionTableAnnotationDefinition2_0.instance(),
		ColumnAnnotationDefinition.instance(),
		ConverterAnnotationDefinition2_1.instance(),
		DiscriminatorColumnAnnotationDefinition.instance(),
		DiscriminatorValueAnnotationDefinition.instance(),
		ElementCollectionAnnotationDefinition2_0.instance(),
		EmbeddableAnnotationDefinition.instance(),
		EmbeddedAnnotationDefinition.instance(),
		EmbeddedIdAnnotationDefinition.instance(),
		EntityAnnotationDefinition.instance(),
		EnumeratedAnnotationDefinition.instance(),
		GeneratedAnnotationDefinition2_0.instance(),
		GeneratedValueAnnotationDefinition.instance(),
		IdAnnotationDefinition.instance(),
		IdClassAnnotationDefinition.instance(),
		InheritanceAnnotationDefinition.instance(),
		JoinColumnsAnnotationDefinition.instance(),
		JoinTableAnnotationDefinition.instance(),
		LobAnnotationDefinition.instance(),
		ManyToManyAnnotationDefinition.instance(),
		ManyToOneAnnotationDefinition.instance(),
		MappedSuperclassAnnotationDefinition.instance(),
		MapsIdAnnotationDefinition2_0.instance(),
		MapKeyAnnotationDefinition.instance(),
		MapKeyClassAnnotationDefinition2_0.instance(),
		MapKeyColumnAnnotationDefinition2_0.instance(),
		MapKeyEnumeratedAnnotationDefinition2_0.instance(),
		MapKeyJoinColumnsAnnotationDefinition2_0.instance(),
		MapKeyTemporalAnnotationDefinition2_0.instance(),
		NamedNativeQueriesAnnotationDefinition.instance(),
		NamedQueriesAnnotationDefinition.instance(),
		NamedStoredProcedureQueriesAnnotationDefinition2_1.instance(),
		OneToManyAnnotationDefinition.instance(),
		OneToOneAnnotationDefinition.instance(),
		OrderByAnnotationDefinition.instance(),
		OrderColumnAnnotationDefinition2_0.instance(),
		PrimaryKeyJoinColumnsAnnotationDefinition.instance(),
		SecondaryTablesAnnotationDefinition.instance(),
		SequenceGeneratorAnnotationDefinition2_0.instance(),
		StaticMetamodelAnnotationDefinition2_0.instance(),
		TableAnnotationDefinition.instance(),
		TableGeneratorAnnotationDefinition.instance(),
		TemporalAnnotationDefinition.instance(),
		TransientAnnotationDefinition.instance(),
		VersionAnnotationDefinition.instance()
	};

	@Override
	protected void addNestableAnnotationDefinitionsTo(ArrayList<NestableAnnotationDefinition> definitions) {
		CollectionTools.addAll(definitions, NESTABLE_ANNOTATION_DEFINITIONS);
	}

	protected static final NestableAnnotationDefinition[] NESTABLE_ANNOTATION_DEFINITIONS = new NestableAnnotationDefinition[] {
		AssociationOverrideAnnotationDefinition2_0.instance(),
		AttributeOverrideAnnotationDefinition.instance(),
		JoinColumnAnnotationDefinition.instance(),
		MapKeyJoinColumnAnnotationDefinition2_0.instance(),
		NamedNativeQueryAnnotationDefinition.instance(),
		NamedQueryAnnotationDefinition2_0.instance(),
		NamedStoredProcedureQueryAnnotationDefinition2_1.instance(),
		PrimaryKeyJoinColumnAnnotationDefinition.instance(),
		SecondaryTableAnnotationDefinition.instance()
	};
}
