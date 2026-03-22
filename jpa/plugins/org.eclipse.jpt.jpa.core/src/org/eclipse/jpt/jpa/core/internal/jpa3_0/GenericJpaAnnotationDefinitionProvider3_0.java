/*******************************************************************************
 * Copyright (c) 2026 Lakshminarayana Nekkanti. All rights reserved.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Lakshminarayana Nekkanti - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa3_0;

import java.util.ArrayList;
import org.eclipse.jpt.common.core.resource.java.AnnotationDefinition;
import org.eclipse.jpt.common.core.resource.java.NestableAnnotationDefinition;
import org.eclipse.jpt.jpa.core.JpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaAnnotationDefinitionProvider;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.GenericJpaAnnotationDefinitionProvider2_1;
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
import org.eclipse.jpt.jpa.core.internal.resource.java.JakartaAnnotationDefinitionAdapter;
import org.eclipse.jpt.jpa.core.internal.resource.java.JakartaNestableAnnotationDefinitionAdapter;
import org.eclipse.jpt.jpa.core.internal.resource.java.JoinColumnAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.JoinTableAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.NamedNativeQueryAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.PrimaryKeyJoinColumnAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.SecondaryTableAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.AssociationOverrideAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.MapKeyJoinColumnAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.NamedQueryAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.resource.java.NamedStoredProcedureQueryAnnotationDefinition2_1;
import org.eclipse.jpt.jpa.core.internal.resource.java.AttributeOverrideAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.LobAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.ManyToManyAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.ManyToOneAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.MapKeyAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.MappedSuperclassAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.OneToManyAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.OneToOneAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.OrderByAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.SequenceGeneratorAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.TableAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.TableGeneratorAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.TemporalAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.TransientAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.VersionAnnotationDefinition;
import org.eclipse.jpt.jpa.core.resource.java.JPA;

/**
 * JPA 3.0 annotation definition provider.
 * <p>
 * Extends the JPA 2.1 definition set by replacing every
 * <code>javax.persistence.*</code> annotation name with its
 * <code>jakarta.persistence.*</code> equivalent, so that the JPA 3.0+ platform
 * correctly recognises annotations in projects that use the Jakarta namespace.
 * <p>
 * The full definition list is obtained through the public API of
 * {@link GenericJpaAnnotationDefinitionProvider2_1#instance()} so that the
 * protected arrays in that class remain encapsulated.
 * <p>
 * <b>Implementation note:</b> Annotations whose source/binary implementations
 * have a fully package-aware constructor (e.g. {@code @Entity} via
 * {@link EntityAnnotationDefinition#instance(String)}) are handled with the
 * proper factory so that AST attribute extraction also uses the correct FQN.
 * All other definitions are adapted via {@link JakartaAnnotationDefinitionAdapter}
 * which translates the name for recognition purposes only.  Update each
 * definition to be fully package-aware (following the EntityAnnotationDefinition
 * pattern) and replace its adapter entry here as the migration proceeds.
 */
public class GenericJpaAnnotationDefinitionProvider3_0
	extends AbstractJpaAnnotationDefinitionProvider
{
	// singleton
	private static final JpaAnnotationDefinitionProvider INSTANCE =
			new GenericJpaAnnotationDefinitionProvider3_0();

	public static JpaAnnotationDefinitionProvider instance() {
		return INSTANCE;
	}

	private GenericJpaAnnotationDefinitionProvider3_0() {
		super();
	}

	@Override
	protected void addAnnotationDefinitionsTo(ArrayList<AnnotationDefinition> definitions) {
		// Obtain definitions through the public API to avoid accessing
		// protected fields across package boundaries.
		for (AnnotationDefinition def :
				GenericJpaAnnotationDefinitionProvider2_1.instance().getAnnotationDefinitions()) {
			definitions.add(toJakarta(def));
		}
	}

	@Override
	protected void addNestableAnnotationDefinitionsTo(
			ArrayList<NestableAnnotationDefinition> definitions) {
		for (NestableAnnotationDefinition def :
				GenericJpaAnnotationDefinitionProvider2_1.instance()
						.getNestableAnnotationDefinitions()) {
			definitions.add(toJakartaNestable(def));
		}
	}

	/**
	 * Returns the jakarta-equivalent of the given nestable annotation definition.
	 * Definitions with a fully package-aware {@code instance(String jpaPackage)}
	 * factory are dispatched directly; all others fall back to
	 * {@link JakartaNestableAnnotationDefinitionAdapter} (name-translation only).
	 */
	private static NestableAnnotationDefinition toJakartaNestable(NestableAnnotationDefinition def) {
		if (def == AssociationOverrideAnnotationDefinition2_0.instance()) {
			return AssociationOverrideAnnotationDefinition2_0.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == AttributeOverrideAnnotationDefinition.instance()) {
			return AttributeOverrideAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == JoinColumnAnnotationDefinition.instance()) {
			return JoinColumnAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == MapKeyJoinColumnAnnotationDefinition2_0.instance()) {
			return MapKeyJoinColumnAnnotationDefinition2_0.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == NamedNativeQueryAnnotationDefinition.instance()) {
			return NamedNativeQueryAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == NamedQueryAnnotationDefinition2_0.instance()) {
			return NamedQueryAnnotationDefinition2_0.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == NamedStoredProcedureQueryAnnotationDefinition2_1.instance()) {
			return NamedStoredProcedureQueryAnnotationDefinition2_1.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == PrimaryKeyJoinColumnAnnotationDefinition.instance()) {
			return PrimaryKeyJoinColumnAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == SecondaryTableAnnotationDefinition.instance()) {
			return SecondaryTableAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		// Fallback for any unrecognized definition
		return new JakartaNestableAnnotationDefinitionAdapter(def);
	}

	/**
	 * Returns the jakarta-equivalent of the given annotation definition.
	 * <p>
	 * Definitions with a fully package-aware {@code instance(String jpaPackage)}
	 * factory are dispatched to that factory so that the underlying
	 * source/binary annotation also uses the correct FQN when reading
	 * annotation attributes from the AST.
	 * <p>
	 * Any definition that has not yet been migrated to the factory pattern
	 * falls through to the {@link JakartaAnnotationDefinitionAdapter} fallback,
	 * which translates the annotation name for recognition purposes only.
	 */
	private static AnnotationDefinition toJakarta(AnnotationDefinition def) {
		// JPA 1.0 core annotations
		if (def == BasicAnnotationDefinition.instance()) {
			return BasicAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == ColumnAnnotationDefinition.instance()) {
			return ColumnAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == DiscriminatorColumnAnnotationDefinition.instance()) {
			return DiscriminatorColumnAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == DiscriminatorValueAnnotationDefinition.instance()) {
			return DiscriminatorValueAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == EmbeddableAnnotationDefinition.instance()) {
			return EmbeddableAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == EmbeddedAnnotationDefinition.instance()) {
			return EmbeddedAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == EmbeddedIdAnnotationDefinition.instance()) {
			return EmbeddedIdAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == EntityAnnotationDefinition.instance()) {
			return EntityAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == EnumeratedAnnotationDefinition.instance()) {
			return EnumeratedAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == GeneratedValueAnnotationDefinition.instance()) {
			return GeneratedValueAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == IdAnnotationDefinition.instance()) {
			return IdAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == IdClassAnnotationDefinition.instance()) {
			return IdClassAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == InheritanceAnnotationDefinition.instance()) {
			return InheritanceAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == JoinTableAnnotationDefinition.instance()) {
			return JoinTableAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == LobAnnotationDefinition.instance()) {
			return LobAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == ManyToManyAnnotationDefinition.instance()) {
			return ManyToManyAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == ManyToOneAnnotationDefinition.instance()) {
			return ManyToOneAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == MapKeyAnnotationDefinition.instance()) {
			return MapKeyAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == MappedSuperclassAnnotationDefinition.instance()) {
			return MappedSuperclassAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == OneToManyAnnotationDefinition.instance()) {
			return OneToManyAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == OneToOneAnnotationDefinition.instance()) {
			return OneToOneAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == OrderByAnnotationDefinition.instance()) {
			return OrderByAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == SequenceGeneratorAnnotationDefinition.instance()) {
			return SequenceGeneratorAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == TableAnnotationDefinition.instance()) {
			return TableAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == TableGeneratorAnnotationDefinition.instance()) {
			return TableGeneratorAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == TemporalAnnotationDefinition.instance()) {
			return TemporalAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == TransientAnnotationDefinition.instance()) {
			return TransientAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		if (def == VersionAnnotationDefinition.instance()) {
			return VersionAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE);
		}
		// Fallback: translate the annotation name for recognition purposes only.
		// (Handles JPA 2.x-specific definitions not yet migrated to the factory pattern.)
		String name = def.getAnnotationName();
		if (name != null && name.startsWith(JPA.JAVAX_PACKAGE)) {
			return new JakartaAnnotationDefinitionAdapter(def);
		}
		return def;
	}
}
