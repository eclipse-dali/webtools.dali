/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal;

import org.eclipse.jpt.jpa.core.JpaEntityGeneratorDatabaseAnnotationNameBuilder;
import org.eclipse.jpt.jpa.db.Column;
import org.eclipse.jpt.jpa.db.ForeignKey;
import org.eclipse.jpt.jpa.db.Table;

/**
 * Singleton that delegates to the db object passed in.
 */
public final class GenericEntityGeneratorDatabaseAnnotationNameBuilder
	implements JpaEntityGeneratorDatabaseAnnotationNameBuilder
{
	public static final JpaEntityGeneratorDatabaseAnnotationNameBuilder INSTANCE
				= new GenericEntityGeneratorDatabaseAnnotationNameBuilder();

	public static JpaEntityGeneratorDatabaseAnnotationNameBuilder instance() {
		return INSTANCE;
	}

	// ensure single instance
	private GenericEntityGeneratorDatabaseAnnotationNameBuilder() {
		super();
	}

	public String buildTableAnnotationName(String entityName, Table table) {
		return table.getIdentifier(entityName);
	}

	public String buildColumnAnnotationName(String attributeName, Column column) {
		return column.getIdentifier(attributeName);
	}

	public String buildJoinColumnAnnotationName(String attributeName, ForeignKey foreignKey) {
		return foreignKey.getJoinColumnAnnotationIdentifier(attributeName);
	}

	public String buildJoinColumnAnnotationName(Column column) {
		return column.getIdentifier();
	}

	public String buildJoinTableAnnotationName(Table table) {
		return table.getIdentifier();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
