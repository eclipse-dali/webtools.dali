/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.persistence;

import java.util.Iterator;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.persistence.AbstractPersistence;
import org.eclipse.jpt.core.jpa2.context.persistence.Persistence2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceUnit2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceXml2_0;
import org.eclipse.jpt.core.resource.persistence.XmlPersistence;

public class GenericPersistence2_0
	extends AbstractPersistence
	implements Persistence2_0
{
	public GenericPersistence2_0(PersistenceXml2_0 parent, XmlPersistence xmlPersistence) {
		super(parent, xmlPersistence);
	}

	@Override
	public PersistenceXml2_0 getParent() {
		return (PersistenceXml2_0) super.getParent();
	}

	@Override
	public PersistenceUnit2_0 addPersistenceUnit() {
		return (PersistenceUnit2_0) super.addPersistenceUnit();
	}

	@Override
	public PersistenceUnit2_0 addPersistenceUnit(int index) {
		return (PersistenceUnit2_0) super.addPersistenceUnit(index);
	}

	public void synchronizeStaticMetaModel() {
		for (Iterator<PersistenceUnit> stream = this.persistenceUnits(); stream.hasNext(); ) {
			((PersistenceUnit2_0) stream.next()).synchronizeStaticMetaModel();
		}
	}

}
