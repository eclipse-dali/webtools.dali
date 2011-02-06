/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa2;

import java.util.ArrayList;
import org.eclipse.jpt.common.utility.internal.CollectionTools;
import org.eclipse.jpt.jpa.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.Access2_0AnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.AssociationOverride2_0AnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.AssociationOverrides2_0AnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.Cacheable2_0AnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.CollectionTable2_0AnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.ElementCollection2_0AnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.MapKeyClass2_0AnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.MapKeyColumn2_0AnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.MapKeyEnumerated2_0AnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.MapKeyJoinColumn2_0AnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.MapKeyJoinColumns2_0AnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.MapKeyTemporal2_0AnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.MapsId2_0AnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.NamedQueries2_0AnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.NamedQuery2_0AnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.OrderColumn2_0AnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.SequenceGenerator2_0AnnotationDefinition;
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
import org.eclipse.jpt.jpa.core.resource.java.AnnotationDefinition;

/**
 * Support for existing JPA 1.0 annotations, new JPA 2.0 annotations, and
 * augmented support for annotations changed from 1.0 to 2.0
 */
public class Generic2_0JpaAnnotationDefinitionProvider
	extends AbstractJpaAnnotationDefinitionProvider
{
	// singleton
	private static final JpaAnnotationDefinitionProvider INSTANCE = new Generic2_0JpaAnnotationDefinitionProvider();

	/**
	 * Return the singleton
	 */
	public static JpaAnnotationDefinitionProvider instance() {
		return INSTANCE;
	}


	/**
	 * Enforce singleton usage
	 */
	private Generic2_0JpaAnnotationDefinitionProvider() {
		super();
	}

	@Override
	protected void addTypeAnnotationDefinitionsTo(ArrayList<AnnotationDefinition> definitions) {
		CollectionTools.addAll(definitions, TYPE_ANNOTATION_DEFINITIONS);
	}

	protected static final AnnotationDefinition[] TYPE_ANNOTATION_DEFINITIONS = new AnnotationDefinition[] {
		Access2_0AnnotationDefinition.instance(),
		AssociationOverride2_0AnnotationDefinition.instance(),
		AssociationOverrides2_0AnnotationDefinition.instance(),
		AttributeOverrideAnnotationDefinition.instance(),
		AttributeOverridesAnnotationDefinition.instance(),
		Cacheable2_0AnnotationDefinition.instance(),
		DiscriminatorColumnAnnotationDefinition.instance(),
		DiscriminatorValueAnnotationDefinition.instance(),
		EmbeddableAnnotationDefinition.instance(),
		EntityAnnotationDefinition.instance(),
		IdClassAnnotationDefinition.instance(),
		InheritanceAnnotationDefinition.instance(),
		MappedSuperclassAnnotationDefinition.instance(),
		NamedQuery2_0AnnotationDefinition.instance(),
		NamedQueries2_0AnnotationDefinition.instance(),
		NamedNativeQueryAnnotationDefinition.instance(),
		NamedNativeQueriesAnnotationDefinition.instance(),
		PrimaryKeyJoinColumnAnnotationDefinition.instance(),
		PrimaryKeyJoinColumnsAnnotationDefinition.instance(),
		SecondaryTableAnnotationDefinition.instance(),
		SecondaryTablesAnnotationDefinition.instance(),
		SequenceGenerator2_0AnnotationDefinition.instance(),
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
		Access2_0AnnotationDefinition.instance(),
		AssociationOverride2_0AnnotationDefinition.instance(),
		AssociationOverrides2_0AnnotationDefinition.instance(),
		AttributeOverrideAnnotationDefinition.instance(),
		AttributeOverridesAnnotationDefinition.instance(),
		BasicAnnotationDefinition.instance(),
		CollectionTable2_0AnnotationDefinition.instance(),
		ColumnAnnotationDefinition.instance(),
		ElementCollection2_0AnnotationDefinition.instance(),
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
		MapsId2_0AnnotationDefinition.instance(),
		MapKeyAnnotationDefinition.instance(),
		MapKeyClass2_0AnnotationDefinition.instance(),
		MapKeyColumn2_0AnnotationDefinition.instance(),
		MapKeyEnumerated2_0AnnotationDefinition.instance(),
		MapKeyJoinColumn2_0AnnotationDefinition.instance(),
		MapKeyJoinColumns2_0AnnotationDefinition.instance(),
		MapKeyTemporal2_0AnnotationDefinition.instance(),
		OneToManyAnnotationDefinition.instance(),
		OneToOneAnnotationDefinition.instance(),
		OrderByAnnotationDefinition.instance(),
		OrderColumn2_0AnnotationDefinition.instance(),
		PrimaryKeyJoinColumnAnnotationDefinition.instance(),
		PrimaryKeyJoinColumnsAnnotationDefinition.instance(),
		SequenceGenerator2_0AnnotationDefinition.instance(),
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
