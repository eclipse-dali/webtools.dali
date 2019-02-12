/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm;

import org.eclipse.emf.ecore.EFactory;
import org.eclipse.jpt.jpa.core.internal.context.orm.AbstractOrmXmlDefinition;
import org.eclipse.jpt.jpa.eclipselink.core.resource.orm.EclipseLinkOrmFactory;

public abstract class EclipseLinkAbstractOrmXmlDefinition
	extends AbstractOrmXmlDefinition
{
	protected EclipseLinkAbstractOrmXmlDefinition() {
		super();
	}

	public EFactory getResourceModelFactory() {
		return EclipseLinkOrmFactory.eINSTANCE;
	}
}
