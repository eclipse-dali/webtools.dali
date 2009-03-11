/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.gen.internal2;

/**
 * TODO: merge with JPA class ?
 * The JPA mapping tag and attribute names.
 * 
 */
public class TagNames
{
	public static final String BASIC_TAG = "basic";
	public static final String CASCADE_TAG = "cascade";
	public static final String COLUMN_TAG = "column";
	public static final String EMBEDDED_TAG = "embedded";
	public static final String EMBEDDED_ID_TAG = "embedded-id";
	public static final String GENERATED_VALUE_TAG = "generated-value";
	public static final String ID_TAG = "id";
	public static final String ID_CLASS_TAG = "id";
	public static final String JOIN_COLUMN_TAG = "join-column";
	public static final String INVERSE_JOIN_COLUMN_TAG = "inverse-join-column";
	public static final String LOB_TAG = "lob";
	public static final String MANY_TO_MANY_TAG = "many-to-many";
	public static final String MANY_TO_ONE_TAG = "many-to-one";
	public static final String MAPPED_BY_TAG = "mapped-by";
	public static final String ONE_TO_MANY_TAG = "one-to-many";
	public static final String ONE_TO_ONE_TAG = "one-to-one";
	public static final String PK_JOIN_COLUMN_TAG = "primary-key-join-column";
	public static final String TABLE_TAG = "table";
	public static final String VERSION_TAG = "version";
	public static final String JOIN_TABLE_TAG = "join-table";

	/*cascade tags*/
	public static final String ALL_CASCADE = "all";
	public static final String PERSIST_CASCADE = "persist";
	public static final String MERGE_CASCADE = "merge";
	public static final String REMOVE_CASCADE = "remove";
	public static final String REFRESH_CASCADE = "refresh";
}
