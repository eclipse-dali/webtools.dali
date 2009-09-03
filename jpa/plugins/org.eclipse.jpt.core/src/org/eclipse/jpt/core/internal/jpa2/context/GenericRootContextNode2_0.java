/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.jpa2.context;

import org.eclipse.jpt.core.internal.context.AbstractRootContextNode;
import org.eclipse.jpt.core.jpa2.JpaProject2_0;
import org.eclipse.jpt.core.jpa2.context.JpaRootContextNode2_0;
import org.eclipse.jpt.core.jpa2.context.persistence.PersistenceXml2_0;

public class GenericRootContextNode2_0
	extends AbstractRootContextNode
	implements JpaRootContextNode2_0
{
	public GenericRootContextNode2_0(JpaProject2_0 jpaProject) {
		super(jpaProject);
	}

	@Override
	public PersistenceXml2_0 getPersistenceXml() {
		return (PersistenceXml2_0) super.getPersistenceXml();
	}

	public void synchronizeStaticMetamodel() {
		PersistenceXml2_0 p_xml = this.getPersistenceXml();
		if (p_xml != null) {
			p_xml.synchronizeStaticMetamodel();
		}
	}

}
