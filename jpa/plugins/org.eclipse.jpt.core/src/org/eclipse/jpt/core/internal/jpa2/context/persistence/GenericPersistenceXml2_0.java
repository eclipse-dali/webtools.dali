/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context.persistence;

import org.eclipse.jpt.core.internal.context.persistence.AbstractPersistenceXml;
import org.eclipse.jpt.core.jpa2.context.JpaRootContextNode2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.Persistence2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceXml2_0;
import org.eclipse.jpt.core.resource.xml.JpaXmlResource;

public class GenericPersistenceXml2_0
	extends AbstractPersistenceXml
	implements PersistenceXml2_0
{
	public GenericPersistenceXml2_0(JpaRootContextNode2_0 parent, JpaXmlResource resource) {
		super(parent, resource);
	}
	
	@Override
	public Persistence2_0 addPersistence() {
		return (Persistence2_0) super.addPersistence();
	}

	@Override
	public Persistence2_0 getPersistence() {
		return (Persistence2_0) super.getPersistence();
	}

	public void synchronizeStaticMetaModel() {
		Persistence2_0 p = this.getPersistence();
		if (p != null) {
			p.synchronizeStaticMetaModel();
		}
	}

}
