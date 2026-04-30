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
 *******************************************************************************/
package org.eclipse.jpt.jpa.core.resource.java;

/**
 * Jakarta Persistence 3.2 Java-related constants.
 *
 * @since 3.2
 */
@SuppressWarnings("nls")
public interface JPA3_2 extends JPA3_1 {

	// ====================================================================
	// JPA 3.2 new annotations
	// ====================================================================

	String CHECK_CONSTRAINT = PACKAGE_ + "CheckConstraint";
		String CHECK_CONSTRAINT__NAME = "name";
		String CHECK_CONSTRAINT__CONSTRAINT = "constraint";
		String CHECK_CONSTRAINT__OPTIONS = "options";

	String ENUMERATED_VALUE = PACKAGE_ + "EnumeratedValue";


	// ====================================================================
	// JPA 3.2 element additions to existing annotations
	// ====================================================================

	// @Column
	String COLUMN__OPTIONS = "options";
	String COLUMN__SECOND_PRECISION = "secondPrecision";
	String COLUMN__CHECK = "check";
	String COLUMN__COMMENT = "comment";

	// @Table
	String TABLE__CHECK = "check";
	String TABLE__COMMENT = "comment";
	String TABLE__OPTIONS = "options";

	// @SecondaryTable
	String SECONDARY_TABLE__CHECK = "check";
	String SECONDARY_TABLE__COMMENT = "comment";
	String SECONDARY_TABLE__OPTIONS = "options";

	// @JoinColumn
	String JOIN_COLUMN__CHECK = "check";
	String JOIN_COLUMN__COMMENT = "comment";
	String JOIN_COLUMN__OPTIONS = "options";

	// @JoinTable
	String JOIN_TABLE__CHECK = "check";
	String JOIN_TABLE__COMMENT = "comment";
	String JOIN_TABLE__OPTIONS = "options";

	// @CollectionTable
	String COLLECTION_TABLE__CHECK = "check";
	String COLLECTION_TABLE__COMMENT = "comment";
	String COLLECTION_TABLE__OPTIONS = "options";

	// @Index
	String INDEX__OPTIONS = "options";

	// @UniqueConstraint
	String UNIQUE_CONSTRAINT__OPTIONS = "options";

	// @ForeignKey
	String FOREIGN_KEY__OPTIONS = "options";

	// @SequenceGenerator
	String SEQUENCE_GENERATOR__OPTIONS = "options";

	// @TableGenerator
	String TABLE_GENERATOR__OPTIONS = "options";

	// @NamedNativeQuery
	String NAMED_NATIVE_QUERY__ENTITIES = "entities";
	String NAMED_NATIVE_QUERY__CLASSES = "classes";
	String NAMED_NATIVE_QUERY__COLUMNS = "columns";

	// @EntityResult
	String ENTITY_RESULT__LOCK_MODE = "lockMode";
}
