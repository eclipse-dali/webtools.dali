/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import org.eclipse.jpt.core.EntityGeneratorDatabaseAnnotationNameBuilder;
import org.eclipse.jpt.db.Column;
import org.eclipse.jpt.db.ForeignKey;
import org.eclipse.jpt.db.Table;

/**
 * Singleton that delegates to the db object passed in.
 */
public final class GenericEntityGeneratorDatabaseAnnotationNameBuilder
	implements EntityGeneratorDatabaseAnnotationNameBuilder
{
	public static final EntityGeneratorDatabaseAnnotationNameBuilder INSTANCE
				= new GenericEntityGeneratorDatabaseAnnotationNameBuilder();

	public static EntityGeneratorDatabaseAnnotationNameBuilder instance() {
		return INSTANCE;
	}

	// ensure single instance
	private GenericEntityGeneratorDatabaseAnnotationNameBuilder() {
		super();
	}

	public String buildTableAnnotationName(String entityName, Table table) {
		return table.getAnnotationIdentifier(entityName);
	}

	public String buildColumnAnnotationName(String attributeName, Column column) {
		return column.getAnnotationIdentifier(attributeName);
	}

	public String buildJoinColumnAnnotationName(String attributeName, ForeignKey foreignKey) {
		return foreignKey.getJoinColumnAnnotationIdentifier(attributeName);
	}

	public String buildJoinColumnAnnotationName(Column column) {
		return column.getAnnotationIdentifier();
	}

	public String buildJoinTableAnnotationName(Table table) {
		return table.getAnnotationIdentifier();
	}

}
