/*******************************************************************************
 * Copyright (c) 2006, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal;

import org.eclipse.jpt.jpa.db.Sequence;

/**
 * Wrap a DTP Sequence
 */
final class DTPSequenceWrapper
	extends DTPDatabaseObjectWrapper<DTPSchemaWrapper, org.eclipse.datatools.modelbase.sql.schema.Sequence>
	implements Sequence
{
	DTPSequenceWrapper(DTPSchemaWrapper schema, org.eclipse.datatools.modelbase.sql.schema.Sequence dtpSequence) {
		super(schema, dtpSequence);
	}


	// ********** DTPDatabaseObjectWrapper implementation **********

	@Override
	synchronized void catalogObjectChanged() {
		super.catalogObjectChanged();
		this.getConnectionProfile().sequenceChanged(this);
	}


	// ********** Sequence implementation **********

	public DTPSchemaWrapper getSchema() {
		return this.parent;
	}


	// ********** internal methods **********

	boolean wraps(org.eclipse.datatools.modelbase.sql.schema.Sequence sequence) {
		return this.dtpObject == sequence;
	}

	@Override
	void clear() {
		// no state to clear
	}
}
