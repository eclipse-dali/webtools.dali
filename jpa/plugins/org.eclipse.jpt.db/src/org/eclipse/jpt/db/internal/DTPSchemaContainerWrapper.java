/*******************************************************************************
 * Copyright (c) 2006, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;
import org.eclipse.jpt.utility.internal.StringTools;
import org.eclipse.jpt.utility.internal.iterators.ArrayIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;

/**
 * Coalesce behavior for a schema container (i.e. database or catalog).
 */
abstract class DTPSchemaContainerWrapper
	extends DTPWrapper
	implements SchemaContainer
{
	// lazy-initialized
	private DTPSchemaWrapper[] schemata;


	// ********** constructor **********

	DTPSchemaContainerWrapper(ConnectionProfileHolder connectionProfileHolder) {
		super(connectionProfileHolder);
	}


	// ********** DTPWrapper implementation **********

	@Override
	synchronized void catalogObjectChanged(int eventType) {
		// clear stuff so it will be rebuilt
		this.dispose_();
	}


	// ********** abstract methods **********

	abstract DTPDatabaseWrapper database();

	abstract boolean isCaseSensitive();

	abstract List<org.eclipse.datatools.modelbase.sql.schema.Schema> dtpSchemata();


	// ********** schemata **********

	public Iterator<Schema> schemata() {
		return new ArrayIterator<Schema>(this.schemata_());
	}

	private Iterator<DTPSchemaWrapper> schemaWrappers() {
		return new ArrayIterator<DTPSchemaWrapper>(this.schemata_());
	}

	synchronized DTPSchemaWrapper[] schemata_() {
		if (this.schemata == null) {
			this.schemata = this.buildSchemata();
		}
		return this.schemata;
	}

	private DTPSchemaWrapper[] buildSchemata() {
		List<org.eclipse.datatools.modelbase.sql.schema.Schema> dtpSchemata = this.dtpSchemata();
		DTPSchemaWrapper[] result = new DTPSchemaWrapper[dtpSchemata.size()];
		for (int i = result.length; i-- > 0;) {
			result[i] = new DTPSchemaWrapper(this, dtpSchemata.get(i));
		}
		return result;
	}

	public int schemataSize() {
		return this.schemata_().length;
	}

	public Iterator<String> schemaNames() {
		return new TransformationIterator<DTPSchemaWrapper, String>(this.schemaWrappers()) {
			@Override
			protected String transform(DTPSchemaWrapper schema) {
				 return schema.name();
			}
		};
	}

	public boolean containsSchemaNamed(String name) {
		return this.schemaNamed(name) != null;
	}

	public DTPSchemaWrapper schemaNamed(String name) {
		return this.isCaseSensitive() ? this.schemaNamedCaseSensitive(name) : this.schemaNamedIgnoreCase(name);
	}

	private DTPSchemaWrapper schemaNamedCaseSensitive(String name) {
		for (Iterator<DTPSchemaWrapper> stream = this.schemaWrappers(); stream.hasNext(); ) {
			DTPSchemaWrapper schema = stream.next();
			if (schema.name().equals(name)) {
				return schema;
			}
		}
		return null;
	}
	
	private DTPSchemaWrapper schemaNamedIgnoreCase(String name) {
		for (Iterator<DTPSchemaWrapper> stream = this.schemaWrappers(); stream.hasNext(); ) {
			DTPSchemaWrapper schema = stream.next();
			if (StringTools.stringsAreEqualIgnoreCase(schema.name(), name)) {
				return schema;
			}
		}
		return null;
	}

	/**
	 * return the schema for the specified DTP schema
	 */
	DTPSchemaWrapper schema(org.eclipse.datatools.modelbase.sql.schema.Schema dtpSchema) {
		for (Iterator<DTPSchemaWrapper> stream = this.schemaWrappers(); stream.hasNext(); ) {
			DTPSchemaWrapper schema = stream.next();
			if (schema.wraps(dtpSchema)) {
				return schema;
			}
		}
		throw new IllegalArgumentException("invalid DTP schema: " + dtpSchema);  //$NON-NLS-1$
	}


	// ********** disposal **********

	@Override
	synchronized void dispose() {
		this.dispose_();
		super.dispose();
	}

	void dispose_() {
		this.disposeSchemata();
	}

	private void disposeSchemata() {
		if (this.schemata != null) {
			for (DTPSchemaWrapper schema : this.schemata) {
				schema.dispose();
			}
			this.schemata = null;
		}
	}

}
