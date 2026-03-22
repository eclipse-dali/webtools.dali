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
package org.eclipse.jpt.jpa.core.tests.internal.jpa3_0;

import junit.framework.TestCase;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.AssociationOverrideAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.MapKeyJoinColumnAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2.resource.java.NamedQueryAnnotationDefinition2_0;
import org.eclipse.jpt.jpa.core.internal.jpa2_1.resource.java.NamedStoredProcedureQueryAnnotationDefinition2_1;
import org.eclipse.jpt.jpa.core.internal.resource.java.AttributeOverrideAnnotationDefinition;
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
import org.eclipse.jpt.jpa.core.internal.resource.java.JoinTableAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.LobAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.ManyToManyAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.ManyToOneAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.MapKeyAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.MappedSuperclassAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.NamedNativeQueryAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.OneToManyAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.OneToOneAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.OrderByAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.PrimaryKeyJoinColumnAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.SecondaryTableAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.SequenceGeneratorAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.TableAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.TableGeneratorAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.TemporalAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.TransientAnnotationDefinition;
import org.eclipse.jpt.jpa.core.internal.resource.java.VersionAnnotationDefinition;
import org.eclipse.jpt.jpa.core.jpa2.resource.java.JPA2_0;
import org.eclipse.jpt.jpa.core.jpa2_1.resource.java.JPA2_1;
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JPA3_0;

/**
 * Direct tests for the {@code instance(String jpaPackage)} factory pattern on
 * all annotation definition classes migrated to support both
 * {@code javax.persistence} and {@code jakarta.persistence}.
 * <p>
 * Each definition class must:
 * <ol>
 *   <li>Return the {@code javax.persistence} FQN from the no-arg
 *       {@code instance()} (the legacy default).</li>
 *   <li>Return the corresponding {@code jakarta.persistence} FQN when called
 *       with {@link JPA#JAKARTA_PACKAGE}.</li>
 *   <li>Return the <em>same singleton</em> from {@code instance(JAVAX_PACKAGE)}
 *       as from {@code instance()}.</li>
 * </ol>
 * The nestable definitions additionally expose a container annotation name that
 * must follow the same rules.
 */
@SuppressWarnings("nls")
public class AnnotationDefinitionJakartaTests extends TestCase {

	public AnnotationDefinitionJakartaTests(String name) {
		super(name);
	}


	// ====================================================================
	// Non-nestable annotation definition factories (28 definitions)
	// ====================================================================

	// ---- Basic ----

	public void testBasicDefaultIsJavax() {
		assertEquals(JPA.BASIC, BasicAnnotationDefinition.instance().getAnnotationName());
	}

	public void testBasicJakartaName() {
		assertEquals(JPA3_0.BASIC,
				BasicAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testBasicJavaxInstanceIsSingleton() {
		assertSame(BasicAnnotationDefinition.instance(),
				BasicAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- Column ----

	public void testColumnDefaultIsJavax() {
		assertEquals(JPA.COLUMN, ColumnAnnotationDefinition.instance().getAnnotationName());
	}

	public void testColumnJakartaName() {
		assertEquals(JPA3_0.COLUMN,
				ColumnAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testColumnJavaxInstanceIsSingleton() {
		assertSame(ColumnAnnotationDefinition.instance(),
				ColumnAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- DiscriminatorColumn ----

	public void testDiscriminatorColumnDefaultIsJavax() {
		assertEquals(JPA.DISCRIMINATOR_COLUMN,
				DiscriminatorColumnAnnotationDefinition.instance().getAnnotationName());
	}

	public void testDiscriminatorColumnJakartaName() {
		assertEquals(JPA3_0.DISCRIMINATOR_COLUMN,
				DiscriminatorColumnAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testDiscriminatorColumnJavaxInstanceIsSingleton() {
		assertSame(DiscriminatorColumnAnnotationDefinition.instance(),
				DiscriminatorColumnAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- DiscriminatorValue ----

	public void testDiscriminatorValueDefaultIsJavax() {
		assertEquals(JPA.DISCRIMINATOR_VALUE,
				DiscriminatorValueAnnotationDefinition.instance().getAnnotationName());
	}

	public void testDiscriminatorValueJakartaName() {
		assertEquals(JPA3_0.DISCRIMINATOR_VALUE,
				DiscriminatorValueAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testDiscriminatorValueJavaxInstanceIsSingleton() {
		assertSame(DiscriminatorValueAnnotationDefinition.instance(),
				DiscriminatorValueAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- Embeddable ----

	public void testEmbeddableDefaultIsJavax() {
		assertEquals(JPA.EMBEDDABLE,
				EmbeddableAnnotationDefinition.instance().getAnnotationName());
	}

	public void testEmbeddableJakartaName() {
		assertEquals(JPA3_0.EMBEDDABLE,
				EmbeddableAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testEmbeddableJavaxInstanceIsSingleton() {
		assertSame(EmbeddableAnnotationDefinition.instance(),
				EmbeddableAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- Embedded ----

	public void testEmbeddedDefaultIsJavax() {
		assertEquals(JPA.EMBEDDED,
				EmbeddedAnnotationDefinition.instance().getAnnotationName());
	}

	public void testEmbeddedJakartaName() {
		assertEquals(JPA3_0.EMBEDDED,
				EmbeddedAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testEmbeddedJavaxInstanceIsSingleton() {
		assertSame(EmbeddedAnnotationDefinition.instance(),
				EmbeddedAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- EmbeddedId ----

	public void testEmbeddedIdDefaultIsJavax() {
		assertEquals(JPA.EMBEDDED_ID,
				EmbeddedIdAnnotationDefinition.instance().getAnnotationName());
	}

	public void testEmbeddedIdJakartaName() {
		assertEquals(JPA3_0.EMBEDDED_ID,
				EmbeddedIdAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testEmbeddedIdJavaxInstanceIsSingleton() {
		assertSame(EmbeddedIdAnnotationDefinition.instance(),
				EmbeddedIdAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- Entity ----

	public void testEntityDefaultIsJavax() {
		assertEquals(JPA.ENTITY,
				EntityAnnotationDefinition.instance().getAnnotationName());
	}

	public void testEntityJakartaName() {
		assertEquals(JPA3_0.ENTITY,
				EntityAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testEntityJavaxInstanceIsSingleton() {
		assertSame(EntityAnnotationDefinition.instance(),
				EntityAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- Enumerated ----

	public void testEnumeratedDefaultIsJavax() {
		assertEquals(JPA.ENUMERATED,
				EnumeratedAnnotationDefinition.instance().getAnnotationName());
	}

	public void testEnumeratedJakartaName() {
		assertEquals(JPA3_0.ENUMERATED,
				EnumeratedAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testEnumeratedJavaxInstanceIsSingleton() {
		assertSame(EnumeratedAnnotationDefinition.instance(),
				EnumeratedAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- GeneratedValue ----

	public void testGeneratedValueDefaultIsJavax() {
		assertEquals(JPA.GENERATED_VALUE,
				GeneratedValueAnnotationDefinition.instance().getAnnotationName());
	}

	public void testGeneratedValueJakartaName() {
		assertEquals(JPA3_0.GENERATED_VALUE,
				GeneratedValueAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testGeneratedValueJavaxInstanceIsSingleton() {
		assertSame(GeneratedValueAnnotationDefinition.instance(),
				GeneratedValueAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- Id ----

	public void testIdDefaultIsJavax() {
		assertEquals(JPA.ID,
				IdAnnotationDefinition.instance().getAnnotationName());
	}

	public void testIdJakartaName() {
		assertEquals(JPA3_0.ID,
				IdAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testIdJavaxInstanceIsSingleton() {
		assertSame(IdAnnotationDefinition.instance(),
				IdAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- IdClass ----

	public void testIdClassDefaultIsJavax() {
		assertEquals(JPA.ID_CLASS,
				IdClassAnnotationDefinition.instance().getAnnotationName());
	}

	public void testIdClassJakartaName() {
		assertEquals(JPA3_0.ID_CLASS,
				IdClassAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testIdClassJavaxInstanceIsSingleton() {
		assertSame(IdClassAnnotationDefinition.instance(),
				IdClassAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- Inheritance ----

	public void testInheritanceDefaultIsJavax() {
		assertEquals(JPA.INHERITANCE,
				InheritanceAnnotationDefinition.instance().getAnnotationName());
	}

	public void testInheritanceJakartaName() {
		assertEquals(JPA3_0.INHERITANCE,
				InheritanceAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testInheritanceJavaxInstanceIsSingleton() {
		assertSame(InheritanceAnnotationDefinition.instance(),
				InheritanceAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- JoinTable ----

	public void testJoinTableDefaultIsJavax() {
		assertEquals(JPA.JOIN_TABLE,
				JoinTableAnnotationDefinition.instance().getAnnotationName());
	}

	public void testJoinTableJakartaName() {
		assertEquals(JPA3_0.JOIN_TABLE,
				JoinTableAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testJoinTableJavaxInstanceIsSingleton() {
		assertSame(JoinTableAnnotationDefinition.instance(),
				JoinTableAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- Lob ----

	public void testLobDefaultIsJavax() {
		assertEquals(JPA.LOB,
				LobAnnotationDefinition.instance().getAnnotationName());
	}

	public void testLobJakartaName() {
		assertEquals(JPA3_0.LOB,
				LobAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testLobJavaxInstanceIsSingleton() {
		assertSame(LobAnnotationDefinition.instance(),
				LobAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- ManyToMany ----

	public void testManyToManyDefaultIsJavax() {
		assertEquals(JPA.MANY_TO_MANY,
				ManyToManyAnnotationDefinition.instance().getAnnotationName());
	}

	public void testManyToManyJakartaName() {
		assertEquals(JPA3_0.MANY_TO_MANY,
				ManyToManyAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testManyToManyJavaxInstanceIsSingleton() {
		assertSame(ManyToManyAnnotationDefinition.instance(),
				ManyToManyAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- ManyToOne ----

	public void testManyToOneDefaultIsJavax() {
		assertEquals(JPA.MANY_TO_ONE,
				ManyToOneAnnotationDefinition.instance().getAnnotationName());
	}

	public void testManyToOneJakartaName() {
		assertEquals(JPA3_0.MANY_TO_ONE,
				ManyToOneAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testManyToOneJavaxInstanceIsSingleton() {
		assertSame(ManyToOneAnnotationDefinition.instance(),
				ManyToOneAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- MapKey ----

	public void testMapKeyDefaultIsJavax() {
		assertEquals(JPA.MAP_KEY,
				MapKeyAnnotationDefinition.instance().getAnnotationName());
	}

	public void testMapKeyJakartaName() {
		assertEquals(JPA3_0.MAP_KEY,
				MapKeyAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testMapKeyJavaxInstanceIsSingleton() {
		assertSame(MapKeyAnnotationDefinition.instance(),
				MapKeyAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- MappedSuperclass ----

	public void testMappedSuperclassDefaultIsJavax() {
		assertEquals(JPA.MAPPED_SUPERCLASS,
				MappedSuperclassAnnotationDefinition.instance().getAnnotationName());
	}

	public void testMappedSuperclassJakartaName() {
		assertEquals(JPA3_0.MAPPED_SUPERCLASS,
				MappedSuperclassAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testMappedSuperclassJavaxInstanceIsSingleton() {
		assertSame(MappedSuperclassAnnotationDefinition.instance(),
				MappedSuperclassAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- OneToMany ----

	public void testOneToManyDefaultIsJavax() {
		assertEquals(JPA.ONE_TO_MANY,
				OneToManyAnnotationDefinition.instance().getAnnotationName());
	}

	public void testOneToManyJakartaName() {
		assertEquals(JPA3_0.ONE_TO_MANY,
				OneToManyAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testOneToManyJavaxInstanceIsSingleton() {
		assertSame(OneToManyAnnotationDefinition.instance(),
				OneToManyAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- OneToOne ----

	public void testOneToOneDefaultIsJavax() {
		assertEquals(JPA.ONE_TO_ONE,
				OneToOneAnnotationDefinition.instance().getAnnotationName());
	}

	public void testOneToOneJakartaName() {
		assertEquals(JPA3_0.ONE_TO_ONE,
				OneToOneAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testOneToOneJavaxInstanceIsSingleton() {
		assertSame(OneToOneAnnotationDefinition.instance(),
				OneToOneAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- OrderBy ----

	public void testOrderByDefaultIsJavax() {
		assertEquals(JPA.ORDER_BY,
				OrderByAnnotationDefinition.instance().getAnnotationName());
	}

	public void testOrderByJakartaName() {
		assertEquals(JPA3_0.ORDER_BY,
				OrderByAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testOrderByJavaxInstanceIsSingleton() {
		assertSame(OrderByAnnotationDefinition.instance(),
				OrderByAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- SequenceGenerator (was broken — field missing) ----

	public void testSequenceGeneratorDefaultIsJavax() {
		assertEquals(JPA.SEQUENCE_GENERATOR,
				SequenceGeneratorAnnotationDefinition.instance().getAnnotationName());
	}

	public void testSequenceGeneratorJakartaName() {
		assertEquals(JPA3_0.SEQUENCE_GENERATOR,
				SequenceGeneratorAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testSequenceGeneratorJakartaNameStartsWithJakarta() {
		assertTrue(SequenceGeneratorAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE)
				.getAnnotationName().startsWith(JPA.JAKARTA_PACKAGE));
	}

	public void testSequenceGeneratorDefaultDoesNotStartWithJakarta() {
		assertFalse(SequenceGeneratorAnnotationDefinition.instance()
				.getAnnotationName().startsWith(JPA.JAKARTA_PACKAGE));
	}

	public void testSequenceGeneratorJavaxInstanceIsSingleton() {
		assertSame(SequenceGeneratorAnnotationDefinition.instance(),
				SequenceGeneratorAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}

	public void testSequenceGeneratorJakartaInstanceIsNotSingleton() {
		assertNotSame(SequenceGeneratorAnnotationDefinition.instance(),
				SequenceGeneratorAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE));
	}


	// ---- Table ----

	public void testTableDefaultIsJavax() {
		assertEquals(JPA.TABLE,
				TableAnnotationDefinition.instance().getAnnotationName());
	}

	public void testTableJakartaName() {
		assertEquals(JPA3_0.TABLE,
				TableAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testTableJavaxInstanceIsSingleton() {
		assertSame(TableAnnotationDefinition.instance(),
				TableAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- TableGenerator ----

	public void testTableGeneratorDefaultIsJavax() {
		assertEquals(JPA.TABLE_GENERATOR,
				TableGeneratorAnnotationDefinition.instance().getAnnotationName());
	}

	public void testTableGeneratorJakartaName() {
		assertEquals(JPA3_0.TABLE_GENERATOR,
				TableGeneratorAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testTableGeneratorJavaxInstanceIsSingleton() {
		assertSame(TableGeneratorAnnotationDefinition.instance(),
				TableGeneratorAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- Temporal ----

	public void testTemporalDefaultIsJavax() {
		assertEquals(JPA.TEMPORAL,
				TemporalAnnotationDefinition.instance().getAnnotationName());
	}

	public void testTemporalJakartaName() {
		assertEquals(JPA3_0.TEMPORAL,
				TemporalAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testTemporalJavaxInstanceIsSingleton() {
		assertSame(TemporalAnnotationDefinition.instance(),
				TemporalAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- Transient ----

	public void testTransientDefaultIsJavax() {
		assertEquals(JPA.TRANSIENT,
				TransientAnnotationDefinition.instance().getAnnotationName());
	}

	public void testTransientJakartaName() {
		assertEquals(JPA3_0.TRANSIENT,
				TransientAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testTransientJavaxInstanceIsSingleton() {
		assertSame(TransientAnnotationDefinition.instance(),
				TransientAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- Version ----

	public void testVersionDefaultIsJavax() {
		assertEquals(JPA.VERSION,
				VersionAnnotationDefinition.instance().getAnnotationName());
	}

	public void testVersionJakartaName() {
		assertEquals(JPA3_0.VERSION,
				VersionAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getAnnotationName());
	}

	public void testVersionJavaxInstanceIsSingleton() {
		assertSame(VersionAnnotationDefinition.instance(),
				VersionAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ====================================================================
	// Nestable annotation definition factories (9 definitions)
	// ====================================================================

	// ---- JoinColumn (nestable) ----

	public void testJoinColumnDefaultNestableIsJavax() {
		assertEquals(JPA.JOIN_COLUMN,
				JoinColumnAnnotationDefinition.instance().getNestableAnnotationName());
	}

	public void testJoinColumnDefaultContainerIsJavax() {
		assertEquals(JPA.JOIN_COLUMNS,
				JoinColumnAnnotationDefinition.instance().getContainerAnnotationName());
	}

	public void testJoinColumnJakartaNestableName() {
		assertEquals(JPA3_0.JOIN_COLUMN,
				JoinColumnAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getNestableAnnotationName());
	}

	public void testJoinColumnJakartaContainerName() {
		assertEquals(JPA3_0.JOIN_COLUMNS,
				JoinColumnAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getContainerAnnotationName());
	}

	public void testJoinColumnJavaxInstanceIsSingleton() {
		assertSame(JoinColumnAnnotationDefinition.instance(),
				JoinColumnAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- AttributeOverride (nestable) ----

	public void testAttributeOverrideDefaultNestableIsJavax() {
		assertEquals(JPA.ATTRIBUTE_OVERRIDE,
				AttributeOverrideAnnotationDefinition.instance().getNestableAnnotationName());
	}

	public void testAttributeOverrideDefaultContainerIsJavax() {
		assertEquals(JPA.ATTRIBUTE_OVERRIDES,
				AttributeOverrideAnnotationDefinition.instance().getContainerAnnotationName());
	}

	public void testAttributeOverrideJakartaNestableName() {
		assertEquals(JPA3_0.ATTRIBUTE_OVERRIDE,
				AttributeOverrideAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getNestableAnnotationName());
	}

	public void testAttributeOverrideJakartaContainerName() {
		assertEquals(JPA3_0.ATTRIBUTE_OVERRIDES,
				AttributeOverrideAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getContainerAnnotationName());
	}

	public void testAttributeOverrideJavaxInstanceIsSingleton() {
		assertSame(AttributeOverrideAnnotationDefinition.instance(),
				AttributeOverrideAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- NamedNativeQuery (nestable) ----

	public void testNamedNativeQueryDefaultNestableIsJavax() {
		assertEquals(JPA.NAMED_NATIVE_QUERY,
				NamedNativeQueryAnnotationDefinition.instance().getNestableAnnotationName());
	}

	public void testNamedNativeQueryDefaultContainerIsJavax() {
		assertEquals(JPA.NAMED_NATIVE_QUERIES,
				NamedNativeQueryAnnotationDefinition.instance().getContainerAnnotationName());
	}

	public void testNamedNativeQueryJakartaNestableName() {
		assertEquals(JPA3_0.NAMED_NATIVE_QUERY,
				NamedNativeQueryAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getNestableAnnotationName());
	}

	public void testNamedNativeQueryJakartaContainerName() {
		assertEquals(JPA3_0.NAMED_NATIVE_QUERIES,
				NamedNativeQueryAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getContainerAnnotationName());
	}

	public void testNamedNativeQueryJavaxInstanceIsSingleton() {
		assertSame(NamedNativeQueryAnnotationDefinition.instance(),
				NamedNativeQueryAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- PrimaryKeyJoinColumn (nestable) ----

	public void testPrimaryKeyJoinColumnDefaultNestableIsJavax() {
		assertEquals(JPA.PRIMARY_KEY_JOIN_COLUMN,
				PrimaryKeyJoinColumnAnnotationDefinition.instance().getNestableAnnotationName());
	}

	public void testPrimaryKeyJoinColumnDefaultContainerIsJavax() {
		assertEquals(JPA.PRIMARY_KEY_JOIN_COLUMNS,
				PrimaryKeyJoinColumnAnnotationDefinition.instance().getContainerAnnotationName());
	}

	public void testPrimaryKeyJoinColumnJakartaNestableName() {
		assertEquals(JPA3_0.PRIMARY_KEY_JOIN_COLUMN,
				PrimaryKeyJoinColumnAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getNestableAnnotationName());
	}

	public void testPrimaryKeyJoinColumnJakartaContainerName() {
		assertEquals(JPA3_0.PRIMARY_KEY_JOIN_COLUMNS,
				PrimaryKeyJoinColumnAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getContainerAnnotationName());
	}

	public void testPrimaryKeyJoinColumnJavaxInstanceIsSingleton() {
		assertSame(PrimaryKeyJoinColumnAnnotationDefinition.instance(),
				PrimaryKeyJoinColumnAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- SecondaryTable (nestable) ----

	public void testSecondaryTableDefaultNestableIsJavax() {
		assertEquals(JPA.SECONDARY_TABLE,
				SecondaryTableAnnotationDefinition.instance().getNestableAnnotationName());
	}

	public void testSecondaryTableDefaultContainerIsJavax() {
		assertEquals(JPA.SECONDARY_TABLES,
				SecondaryTableAnnotationDefinition.instance().getContainerAnnotationName());
	}

	public void testSecondaryTableJakartaNestableName() {
		assertEquals(JPA3_0.SECONDARY_TABLE,
				SecondaryTableAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getNestableAnnotationName());
	}

	public void testSecondaryTableJakartaContainerName() {
		assertEquals(JPA3_0.SECONDARY_TABLES,
				SecondaryTableAnnotationDefinition.instance(JPA.JAKARTA_PACKAGE).getContainerAnnotationName());
	}

	public void testSecondaryTableJavaxInstanceIsSingleton() {
		assertSame(SecondaryTableAnnotationDefinition.instance(),
				SecondaryTableAnnotationDefinition.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- AssociationOverride 2.0 (nestable) ----

	public void testAssociationOverrideDefaultNestableIsJavax() {
		assertEquals(JPA.ASSOCIATION_OVERRIDE,
				AssociationOverrideAnnotationDefinition2_0.instance().getNestableAnnotationName());
	}

	public void testAssociationOverrideDefaultContainerIsJavax() {
		assertEquals(JPA.ASSOCIATION_OVERRIDES,
				AssociationOverrideAnnotationDefinition2_0.instance().getContainerAnnotationName());
	}

	public void testAssociationOverrideJakartaNestableName() {
		assertEquals(JPA3_0.ASSOCIATION_OVERRIDE,
				AssociationOverrideAnnotationDefinition2_0.instance(JPA.JAKARTA_PACKAGE).getNestableAnnotationName());
	}

	public void testAssociationOverrideJakartaContainerName() {
		assertEquals(JPA3_0.ASSOCIATION_OVERRIDES,
				AssociationOverrideAnnotationDefinition2_0.instance(JPA.JAKARTA_PACKAGE).getContainerAnnotationName());
	}

	public void testAssociationOverrideJavaxInstanceIsSingleton() {
		assertSame(AssociationOverrideAnnotationDefinition2_0.instance(),
				AssociationOverrideAnnotationDefinition2_0.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- MapKeyJoinColumn 2.0 (nestable) ----

	public void testMapKeyJoinColumnDefaultNestableIsJavax() {
		assertEquals(JPA2_0.MAP_KEY_JOIN_COLUMN,
				MapKeyJoinColumnAnnotationDefinition2_0.instance().getNestableAnnotationName());
	}

	public void testMapKeyJoinColumnDefaultContainerIsJavax() {
		assertEquals(JPA2_0.MAP_KEY_JOIN_COLUMNS,
				MapKeyJoinColumnAnnotationDefinition2_0.instance().getContainerAnnotationName());
	}

	public void testMapKeyJoinColumnJakartaNestableName() {
		assertEquals(JPA3_0.MAP_KEY_JOIN_COLUMN,
				MapKeyJoinColumnAnnotationDefinition2_0.instance(JPA.JAKARTA_PACKAGE).getNestableAnnotationName());
	}

	public void testMapKeyJoinColumnJakartaContainerName() {
		assertEquals(JPA3_0.MAP_KEY_JOIN_COLUMNS,
				MapKeyJoinColumnAnnotationDefinition2_0.instance(JPA.JAKARTA_PACKAGE).getContainerAnnotationName());
	}

	public void testMapKeyJoinColumnJavaxInstanceIsSingleton() {
		assertSame(MapKeyJoinColumnAnnotationDefinition2_0.instance(),
				MapKeyJoinColumnAnnotationDefinition2_0.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- NamedQuery 2.0 (nestable) ----

	public void testNamedQueryDefaultNestableIsJavax() {
		assertEquals(JPA.NAMED_QUERY,
				NamedQueryAnnotationDefinition2_0.instance().getNestableAnnotationName());
	}

	public void testNamedQueryDefaultContainerIsJavax() {
		assertEquals(JPA.NAMED_QUERIES,
				NamedQueryAnnotationDefinition2_0.instance().getContainerAnnotationName());
	}

	public void testNamedQueryJakartaNestableName() {
		assertEquals(JPA3_0.NAMED_QUERY,
				NamedQueryAnnotationDefinition2_0.instance(JPA.JAKARTA_PACKAGE).getNestableAnnotationName());
	}

	public void testNamedQueryJakartaContainerName() {
		assertEquals(JPA3_0.NAMED_QUERIES,
				NamedQueryAnnotationDefinition2_0.instance(JPA.JAKARTA_PACKAGE).getContainerAnnotationName());
	}

	public void testNamedQueryJavaxInstanceIsSingleton() {
		assertSame(NamedQueryAnnotationDefinition2_0.instance(),
				NamedQueryAnnotationDefinition2_0.instance(JPA.JAVAX_PACKAGE));
	}


	// ---- NamedStoredProcedureQuery 2.1 (nestable) ----

	public void testNamedStoredProcedureQueryDefaultNestableIsJavax() {
		assertEquals(JPA2_1.NAMED_STORED_PROCEDURE_QUERY,
				NamedStoredProcedureQueryAnnotationDefinition2_1.instance().getNestableAnnotationName());
	}

	public void testNamedStoredProcedureQueryDefaultContainerIsJavax() {
		assertEquals(JPA2_1.NAMED_STORED_PROCEDURE_QUERIES,
				NamedStoredProcedureQueryAnnotationDefinition2_1.instance().getContainerAnnotationName());
	}

	public void testNamedStoredProcedureQueryJakartaNestableName() {
		assertEquals(JPA3_0.NAMED_STORED_PROCEDURE_QUERY,
				NamedStoredProcedureQueryAnnotationDefinition2_1.instance(JPA.JAKARTA_PACKAGE).getNestableAnnotationName());
	}

	public void testNamedStoredProcedureQueryJakartaContainerName() {
		assertEquals(JPA3_0.NAMED_STORED_PROCEDURE_QUERIES,
				NamedStoredProcedureQueryAnnotationDefinition2_1.instance(JPA.JAKARTA_PACKAGE).getContainerAnnotationName());
	}

	public void testNamedStoredProcedureQueryJavaxInstanceIsSingleton() {
		assertSame(NamedStoredProcedureQueryAnnotationDefinition2_1.instance(),
				NamedStoredProcedureQueryAnnotationDefinition2_1.instance(JPA.JAVAX_PACKAGE));
	}
}
