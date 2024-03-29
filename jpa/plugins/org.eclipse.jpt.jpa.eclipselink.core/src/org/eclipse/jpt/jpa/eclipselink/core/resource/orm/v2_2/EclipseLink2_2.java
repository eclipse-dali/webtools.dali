/*******************************************************************************
 * Copyright (c) 2010, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_2;

import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLink;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.v2_1.EclipseLink2_1;

@SuppressWarnings("nls")
public interface EclipseLink2_2
	extends EclipseLink2_1
{
	String SCHEMA_NAMESPACE = EclipseLink.SCHEMA_NAMESPACE;
	String SCHEMA_LOCATION = "http://www.eclipse.org/eclipselink/xsds/eclipselink_orm_2_2.xsd";
	String SCHEMA_VERSION	= "2.2";
	
	
	// EclipseLink 2.2 specific nodes
	String ADDITIONAL_CRITERIA = "additional-criteria";
	String CACHE__ISOLATION = "isolation";
	String CASCADE_ON_DELETE = "cascade-on-delete";
	String CREATION_SUFFIX = "creation-suffix";
	String CRITERIA = "criteria";
	String HASH_PARTITIONING = "hash-partitioning";
	String INDEX = "index";
		String INDEX__COLUMN_NAME = "column-name";
		String INDEX__NAME = "name";
		String INDEX__CATALOG = "catalog";
		String INDEX__SCHEMA = "schema";
		String INDEX__TABLE = "table";
		String INDEX__UNIQUE = "unique";
	String NONCACHEABLE = "noncacheable";
	String PARTITIONING = "partitioning";
	String PARTITIONING_GROUP__PARTITIONED = "partitioned";
	String PINNED_PARTITIONING = "pinned-partitioning";
	String RANGE_PARTITIONING = "range-partitioning";
	String REPLICATION_PARTITIONING = "replication-partitioning";
	String ROUND_ROBIN_PARTITIONING = "round-robin-partitioning";
	String UNION_PARTITIONING = "union-partitioning";
	String VALUE_PARTITIONING = "value-partitioning";
}
