/*******************************************************************************
 * Copyright (c) 2009, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence;

import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractJarFileRef;
import org.eclipse.jpt.jpa.core.resource.persistence.XmlJarFileRef;

/**
 * Context JAR file reference (from the persistence unit)
 */
public class GenericJarFileRef 
	extends AbstractJarFileRef
{
	public GenericJarFileRef(PersistenceUnit parent, XmlJarFileRef xmlJarFileRef) {
		super(parent, xmlJarFileRef);
	}

}
