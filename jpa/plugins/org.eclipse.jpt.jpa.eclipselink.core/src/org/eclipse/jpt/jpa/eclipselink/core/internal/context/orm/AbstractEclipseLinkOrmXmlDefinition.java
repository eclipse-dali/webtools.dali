/*******************************************************************************
 * Copyright (c) 2008, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;

public abstract class AbstractEclipseLinkOrmXmlDefinition
	extends AbstractOrmXmlDefinition
{
	protected AbstractEclipseLinkOrmXmlDefinition() {
		super();
	}

	public EFactory getResourceModelFactory() {
		return EclipseLinkOrmFactory.eINSTANCE;
	}

}
