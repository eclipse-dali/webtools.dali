/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal;

import java.text.Collator;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObject;
import org.eclipse.datatools.connectivity.sqm.core.rte.ICatalogObjectListener;

/**
 *  Wrap a DTP Sequence
 */
public final class Sequence extends DTPWrapper {
	private final Schema schema;
	private final org.eclipse.datatools.modelbase.sql.schema.Sequence dtpSequence;
	private ICatalogObjectListener sequenceListener;

	Sequence( Schema schema, org.eclipse.datatools.modelbase.sql.schema.Sequence dtpSequence) {
		super();
		this.schema = schema;
		this.dtpSequence = dtpSequence;
		this.initialize();
	}

	// ********** behavior **********

	private void initialize() {
		if( this.connectionIsOnline()) {
			this.sequenceListener = this.buildSequenceListener();
			this.addCatalogObjectListener(( ICatalogObject) this.dtpSequence, this.sequenceListener);
		}
	}
	
	protected boolean connectionIsOnline() {
		return this.schema.connectionIsOnline();
	}
	
	private ICatalogObjectListener buildSequenceListener() {
       return new ICatalogObjectListener() {
    	    public void notifyChanged( final ICatalogObject sequence, final int eventType) {
//				TODO
//    			if( sequence == Sequence.this.dtpSequence) {	    	    	
//    				Sequence.this.schema.sequenceChanged( Sequence.this, eventType);
//    			}
    	    }
        };
    }


	// ********** queries **********

	protected void dispose() {
		
		this.removeCatalogObjectListener(( ICatalogObject) this.dtpSequence, this.sequenceListener);
	}

	public String getName() {
		return this.dtpSequence.getName();
	}

	public int compareTo( Object o) {
		return Collator.getInstance().compare( this.getName(), (( Sequence)o).getName());
	}
}
