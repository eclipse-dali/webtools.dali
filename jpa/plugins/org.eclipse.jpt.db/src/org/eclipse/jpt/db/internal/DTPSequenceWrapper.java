/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import org.eclipse.jpt.db.Sequence;

/**
 *  Wrap a DTP Sequence
 */
final class DTPSequenceWrapper
	extends DTPDatabaseObjectWrapper
	implements Sequence
{
	/** the wrapped DTP sequence */
	private final org.eclipse.datatools.modelbase.sql.schema.Sequence dtpSequence;


	// ********** constructor **********

	DTPSequenceWrapper(DTPSchemaWrapper schema, org.eclipse.datatools.modelbase.sql.schema.Sequence dtpSequence) {
		super(schema, dtpSequence);
		this.dtpSequence = dtpSequence;
	}


	// ********** DTPWrapper implementation **********

	@Override
	synchronized void catalogObjectChanged() {
		super.catalogObjectChanged();
		this.getConnectionProfile().sequenceChanged(this);
	}


	// ********** Sequence implementation **********

	public String getName() {
		return this.dtpSequence.getName();
	}

	public DTPSchemaWrapper getSchema() {
		return (DTPSchemaWrapper) this.getParent();
	}


	// ********** internal methods **********

	boolean wraps(org.eclipse.datatools.modelbase.sql.schema.Sequence sequence) {
		return this.dtpSequence == sequence;
	}

	@Override
	void clear() {
		// no state to clear
	}

}
