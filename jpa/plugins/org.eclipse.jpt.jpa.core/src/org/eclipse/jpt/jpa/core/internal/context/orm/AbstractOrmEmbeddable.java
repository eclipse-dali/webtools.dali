/*******************************************************************************
 * Copyright (c) 2006, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context.orm;

import org.eclipse.jpt.common.utility.internal.iterables.EmptyIterable;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.java.JavaEmbeddable;
import org.eclipse.jpt.jpa.core.context.java.JavaPersistentType;
import org.eclipse.jpt.jpa.core.context.orm.OrmEmbeddable;
import org.eclipse.jpt.jpa.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;

/**
 * <code>orm.xml</code> embeddable type mapping
 */
public abstract class AbstractOrmEmbeddable<X extends XmlEmbeddable>
	extends AbstractOrmTypeMapping<X>
	implements OrmEmbeddable
{
	protected AbstractOrmEmbeddable(OrmPersistentType parent, X resourceMapping) {
		super(parent, resourceMapping);
	}


	// ********** key **********

	public String getKey() {
		return MappingKeys.EMBEDDABLE_TYPE_MAPPING_KEY;
	}


	// ********** id class **********

	public JavaPersistentType getIdClass() {
		return null;
	}


	// ********** tables **********

	public Iterable<ReadOnlyTable> getAssociatedTables() {
		return EmptyIterable.instance();
	}

	public Iterable<ReadOnlyTable> getAllAssociatedTables() {
		return EmptyIterable.instance();
	}

	public Iterable<String> getAllAssociatedTableNames() {
		return EmptyIterable.instance();
	}

	public boolean tableNameIsInvalid(String tableName) {
		return false;
	}


	// ********** Java **********

	@Override
	public JavaEmbeddable getJavaTypeMapping() {
		return (JavaEmbeddable) super.getJavaTypeMapping();
	}

	@Override
	public JavaEmbeddable getJavaTypeMappingForDefaults() {
		return (JavaEmbeddable) super.getJavaTypeMappingForDefaults();
	}


	// ********** entity mappings **********

	public int getXmlSequence() {
		return 2;
	}

	public void addXmlTypeMappingTo(XmlEntityMappings entityMappings) {
		entityMappings.getEmbeddables().add(this.xmlTypeMapping);
	}

	public void removeXmlTypeMappingFrom(XmlEntityMappings entityMappings) {
		entityMappings.getEmbeddables().remove(this.xmlTypeMapping);
	}


	// ********** validation **********

	@Override
	public boolean validatesAgainstDatabase() {
		return false;
	}
}
