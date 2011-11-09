/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the 
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0 
 * which accompanies this distribution. 
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at 
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpql;

import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;

/**
 * This class was removed and then added back as a stub to provide compatibility with Dali 3.0.
 * It is a legacy class that should be removed in 3.2 stream for Juno.
 * The concrete implementation that is wrapping the design-time representation of a persistence unit.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
public final class JpaPersistenceUnit extends JpaManagedTypeProvider {

	/**
	 * Creates a new <code>JpaPersistenceUnit</code>.
	 *
	 * @param jpaProject The project that gives access to the application's metadata
	 * @param persistentUnit The design-time persistence unit
	 */
	public JpaPersistenceUnit(JpaProject jpaProject, PersistenceUnit persistentUnit) {
		super(jpaProject, persistentUnit);
	}

}