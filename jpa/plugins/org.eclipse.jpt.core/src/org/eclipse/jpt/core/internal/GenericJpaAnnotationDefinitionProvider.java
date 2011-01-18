/*******************************************************************************
 * Copyright (c) 2007, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import java.util.ArrayList;
import org.eclipse.jpt.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.core.internal.resource.java.AssociationOverrideAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.AssociationOverridesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverrideAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverridesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.BasicAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.ColumnAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorColumnAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorValueAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.EmbeddableAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.EmbeddedAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.EmbeddedIdAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.EntityAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.EnumeratedAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.GeneratedValueAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.IdAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.IdClassAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.InheritanceAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.JoinColumnAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.JoinColumnsAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.JoinTableAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.LobAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.ManyToManyAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.ManyToOneAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.MapKeyAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.MappedSuperclassAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.NamedNativeQueriesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.NamedNativeQueryAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.NamedQueriesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.NamedQueryAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.OneToManyAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.OneToOneAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.OrderByAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumnAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumnsAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTableAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTablesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.SequenceGeneratorAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.TableAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.TableGeneratorAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.TemporalAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.TransientAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.VersionAnnotationDefinition;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.utility.internal.CollectionTools;

public class GenericJpaAnnotationDefinitionProvider
	extends AbstractJpaAnnotationDefinitionProvider
{
	// singleton
	private static final JpaAnnotationDefinitionProvider INSTANCE = new GenericJpaAnnotationDefinitionProvider();

	/**
	 * Return the singleton
	 */
	public static JpaAnnotationDefinitionProvider instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private GenericJpaAnnotationDefinitionProvider() {
		super();
	}

	@Override
	protected void addTypeAnnotationDefinitionsTo(ArrayList<AnnotationDefinition> definitions) {
		CollectionTools.addAll(definitions, TYPE_ANNOTATION_DEFINITIONS);
	}

	protected static final AnnotationDefinition[] TYPE_ANNOTATION_DEFINITIONS = new AnnotationDefinition[] {
		AssociationOverrideAnnotationDefinition.instance(),
		AssociationOverridesAnnotationDefinition.instance(),
		AttributeOverrideAnnotationDefinition.instance(),
		AttributeOverridesAnnotationDefinition.instance(),
		DiscriminatorColumnAnnotationDefinition.instance(),
		DiscriminatorValueAnnotationDefinition.instance(),
		EmbeddableAnnotationDefinition.instance(),
		EntityAnnotationDefinition.instance(),
		IdClassAnnotationDefinition.instance(),
		InheritanceAnnotationDefinition.instance(),
		MappedSuperclassAnnotationDefinition.instance(),
		NamedQueryAnnotationDefinition.instance(),
		NamedQueriesAnnotationDefinition.instance(),
		NamedNativeQueryAnnotationDefinition.instance(),
		NamedNativeQueriesAnnotationDefinition.instance(),
		PrimaryKeyJoinColumnAnnotationDefinition.instance(),
		PrimaryKeyJoinColumnsAnnotationDefinition.instance(),
		SecondaryTableAnnotationDefinition.instance(),
		SecondaryTablesAnnotationDefinition.instance(),
		SequenceGeneratorAnnotationDefinition.instance(),
		TableAnnotationDefinition.instance(),
		TableGeneratorAnnotationDefinition.instance()
	};

	@Override
	protected void addTypeMappingAnnotationDefinitionsTo(ArrayList<AnnotationDefinition> definitions) {
		CollectionTools.addAll(definitions, TYPE_MAPPING_ANNOTATION_DEFINITIONS);
	}

	protected static final AnnotationDefinition[] TYPE_MAPPING_ANNOTATION_DEFINITIONS = new AnnotationDefinition[] {
		EmbeddableAnnotationDefinition.instance(),
		EntityAnnotationDefinition.instance(),
		MappedSuperclassAnnotationDefinition.instance()
	};

	@Override
	protected void addAttributeAnnotationDefinitionsTo(ArrayList<AnnotationDefinition> definitions) {
		CollectionTools.addAll(definitions, ATTRIBUTE_ANNOTATION_DEFINITIONS);
	}

	protected static final AnnotationDefinition[] ATTRIBUTE_ANNOTATION_DEFINITIONS = new AnnotationDefinition[] {
		AssociationOverrideAnnotationDefinition.instance(),
		AssociationOverridesAnnotationDefinition.instance(),
		AttributeOverrideAnnotationDefinition.instance(),
		AttributeOverridesAnnotationDefinition.instance(),
		BasicAnnotationDefinition.instance(),
		ColumnAnnotationDefinition.instance(),
		EmbeddedAnnotationDefinition.instance(),
		EmbeddedIdAnnotationDefinition.instance(),
		EnumeratedAnnotationDefinition.instance(),
		GeneratedValueAnnotationDefinition.instance(),
		IdAnnotationDefinition.instance(),
		JoinColumnAnnotationDefinition.instance(),
		JoinColumnsAnnotationDefinition.instance(),
		JoinTableAnnotationDefinition.instance(),
		LobAnnotationDefinition.instance(),
		ManyToManyAnnotationDefinition.instance(),
		ManyToOneAnnotationDefinition.instance(),
		MapKeyAnnotationDefinition.instance(),
		OneToManyAnnotationDefinition.instance(),
		OneToOneAnnotationDefinition.instance(),
		OrderByAnnotationDefinition.instance(),
		PrimaryKeyJoinColumnAnnotationDefinition.instance(),
		PrimaryKeyJoinColumnsAnnotationDefinition.instance(),
		SequenceGeneratorAnnotationDefinition.instance(),
		TableGeneratorAnnotationDefinition.instance(),
		TemporalAnnotationDefinition.instance(),
		TransientAnnotationDefinition.instance(),
		VersionAnnotationDefinition.instance()
	};

	@Override
	protected void addPackageAnnotationDefinitionsTo(ArrayList<AnnotationDefinition> definitions) {
		// no package annotations
	}
}
