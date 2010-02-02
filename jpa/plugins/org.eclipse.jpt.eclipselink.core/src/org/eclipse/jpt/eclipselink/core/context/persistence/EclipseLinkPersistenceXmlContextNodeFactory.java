/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context.persistence;

import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.context.persistence.PersistenceUnitProperties;
import org.eclipse.jpt.core.context.persistence.PersistenceXmlContextNodeFactory;

public interface EclipseLinkPersistenceXmlContextNodeFactory extends PersistenceXmlContextNodeFactory
{	

	PersistenceUnitProperties buildLogging(PersistenceUnit parent);

}
