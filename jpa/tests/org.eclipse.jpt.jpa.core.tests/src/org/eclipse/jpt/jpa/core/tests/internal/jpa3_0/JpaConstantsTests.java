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
import org.eclipse.jpt.jpa.core.resource.java.JPA;
import org.eclipse.jpt.jpa.core.resource.java.JPA3_0;

/**
 * Verifies that {@link JPA} and {@link JPA3_0} expose the correct package
 * prefix constants and annotation FQN constants for all JPA versions.
 */
@SuppressWarnings("nls")
public class JpaConstantsTests extends TestCase {

	public JpaConstantsTests(String name) {
		super(name);
	}


	// ---- JPA (javax) package constants ----

	public void testJpaPackageIsJavax() {
		assertEquals("javax.persistence", JPA.PACKAGE);
	}

	public void testJavaxPackageConstant() {
		assertEquals("javax.persistence", JPA.JAVAX_PACKAGE);
	}

	public void testJakartaPackageConstant() {
		assertEquals("jakarta.persistence", JPA.JAKARTA_PACKAGE);
	}

	public void testJpaPackageAndJakartaPackageDiffer() {
		assertFalse(JPA.JAVAX_PACKAGE.equals(JPA.JAKARTA_PACKAGE));
	}


	// ---- JPA3_0 (jakarta) package constants ----

	public void testJpa3PackageIsJakarta() {
		assertEquals("jakarta.persistence", JPA3_0.PACKAGE);
	}

	public void testJpa3PackageDiffersFromJpaPackage() {
		assertFalse(JPA.PACKAGE.equals(JPA3_0.PACKAGE));
	}

	public void testJpa3PackageTrailingUnderscore() {
		assertEquals("jakarta.persistence.", JPA3_0.PACKAGE_);
	}


	// ---- JPA 1.0 annotation FQNs in JPA3_0 ----

	public void testJpa3Entity() {
		assertEquals("jakarta.persistence.Entity", JPA3_0.ENTITY);
	}

	public void testJpa3Basic() {
		assertEquals("jakarta.persistence.Basic", JPA3_0.BASIC);
	}

	public void testJpa3Column() {
		assertEquals("jakarta.persistence.Column", JPA3_0.COLUMN);
	}

	public void testJpa3Table() {
		assertEquals("jakarta.persistence.Table", JPA3_0.TABLE);
	}

	public void testJpa3Id() {
		assertEquals("jakarta.persistence.Id", JPA3_0.ID);
	}

	public void testJpa3ManyToOne() {
		assertEquals("jakarta.persistence.ManyToOne", JPA3_0.MANY_TO_ONE);
	}

	public void testJpa3OneToMany() {
		assertEquals("jakarta.persistence.OneToMany", JPA3_0.ONE_TO_MANY);
	}

	public void testJpa3MappedSuperclass() {
		assertEquals("jakarta.persistence.MappedSuperclass", JPA3_0.MAPPED_SUPERCLASS);
	}

	public void testJpa3Embeddable() {
		assertEquals("jakarta.persistence.Embeddable", JPA3_0.EMBEDDABLE);
	}

	public void testJpa3SequenceGenerator() {
		assertEquals("jakarta.persistence.SequenceGenerator", JPA3_0.SEQUENCE_GENERATOR);
	}

	public void testJpa3NamedQuery() {
		assertEquals("jakarta.persistence.NamedQuery", JPA3_0.NAMED_QUERY);
	}

	public void testJpa3JoinColumn() {
		assertEquals("jakarta.persistence.JoinColumn", JPA3_0.JOIN_COLUMN);
	}

	// JPA 1.0 enum: CascadeType
	public void testJpa3CascadeType() {
		assertEquals("jakarta.persistence.CascadeType", JPA3_0.CASCADE_TYPE);
	}

	public void testJpa3GenerationType() {
		assertEquals("jakarta.persistence.GenerationType", JPA3_0.GENERATION_TYPE);
	}


	// ---- JPA 2.0 annotation FQNs in JPA3_0 ----

	public void testJpa3Access() {
		assertEquals("jakarta.persistence.Access", JPA3_0.ACCESS);
	}

	public void testJpa3Cacheable() {
		assertEquals("jakarta.persistence.Cacheable", JPA3_0.CACHEABLE);
	}

	public void testJpa3ElementCollection() {
		assertEquals("jakarta.persistence.ElementCollection", JPA3_0.ELEMENT_COLLECTION);
	}

	public void testJpa3CollectionTable() {
		assertEquals("jakarta.persistence.CollectionTable", JPA3_0.COLLECTION_TABLE);
	}

	public void testJpa3MapKeyColumn() {
		assertEquals("jakarta.persistence.MapKeyColumn", JPA3_0.MAP_KEY_COLUMN);
	}

	public void testJpa3MapKeyJoinColumn() {
		assertEquals("jakarta.persistence.MapKeyJoinColumn", JPA3_0.MAP_KEY_JOIN_COLUMN);
	}

	public void testJpa3MapKeyClass() {
		assertEquals("jakarta.persistence.MapKeyClass", JPA3_0.MAP_KEY_CLASS);
	}

	public void testJpa3MapKeyTemporal() {
		assertEquals("jakarta.persistence.MapKeyTemporal", JPA3_0.MAP_KEY_TEMPORAL);
	}

	public void testJpa3MapKeyEnumerated() {
		assertEquals("jakarta.persistence.MapKeyEnumerated", JPA3_0.MAP_KEY_ENUMERATED);
	}

	public void testJpa3MapsId() {
		assertEquals("jakarta.persistence.MapsId", JPA3_0.MAPS_ID);
	}

	public void testJpa3OrderColumn() {
		assertEquals("jakarta.persistence.OrderColumn", JPA3_0.ORDER_COLUMN);
	}

	// JPA 2.0 enum: AccessType
	public void testJpa3AccessType() {
		assertEquals("jakarta.persistence.AccessType", JPA3_0.ACCESS_TYPE);
	}

	// JPA 2.0 enum: LockModeType
	public void testJpa3LockModeType() {
		assertEquals("jakarta.persistence.LockModeType", JPA3_0.LOCK_MODE_TYPE);
	}

	// JPA 2.0 metamodel
	public void testJpa3StaticMetamodel() {
		assertEquals("jakarta.persistence.metamodel.StaticMetamodel", JPA3_0.STATIC_METAMODEL);
	}


	// ---- JPA 2.1 annotation FQNs in JPA3_0 ----

	public void testJpa3Convert() {
		assertEquals("jakarta.persistence.Convert", JPA3_0.CONVERT);
	}

	public void testJpa3Converter() {
		assertEquals("jakarta.persistence.Converter", JPA3_0.CONVERTER);
	}

	public void testJpa3NamedEntityGraph() {
		assertEquals("jakarta.persistence.NamedEntityGraph", JPA3_0.NAMED_ENTITY_GRAPH);
	}

	public void testJpa3ForeignKey() {
		assertEquals("jakarta.persistence.ForeignKey", JPA3_0.FOREIGN_KEY);
	}

	public void testJpa3Index() {
		assertEquals("jakarta.persistence.Index", JPA3_0.INDEX);
	}

	public void testJpa3NamedStoredProcedureQuery() {
		assertEquals("jakarta.persistence.NamedStoredProcedureQuery", JPA3_0.NAMED_STORED_PROCEDURE_QUERY);
	}

	public void testJpa3ParameterMode() {
		assertEquals("jakarta.persistence.ParameterMode", JPA3_0.PARAMETER_MODE);
	}

	public void testJpa3ConstraintMode() {
		assertEquals("jakarta.persistence.ConstraintMode", JPA3_0.CONSTRAINT_MODE);
	}


	// ---- JPA 3.1 constants in JPA3_0 ----

	public void testJpa31GenerationTypeUuid() {
		assertEquals("jakarta.persistence.GenerationType.UUID", JPA3_0.GENERATION_TYPE__UUID);
	}


	// ---- consistency: every JPA3_0 annotation FQN starts with jakarta ----

	public void testEntityDoesNotStartWithJavax() {
		assertFalse(JPA3_0.ENTITY.startsWith(JPA.JAVAX_PACKAGE));
	}

	public void testEntityStartsWithJakarta() {
		assertTrue(JPA3_0.ENTITY.startsWith(JPA.JAKARTA_PACKAGE));
	}

	public void testElementCollectionDoesNotStartWithJavax() {
		assertFalse(JPA3_0.ELEMENT_COLLECTION.startsWith(JPA.JAVAX_PACKAGE));
	}

	public void testConvertDoesNotStartWithJavax() {
		assertFalse(JPA3_0.CONVERT.startsWith(JPA.JAVAX_PACKAGE));
	}
}
