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

import java.util.Iterator;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.TypeMapping;

/**
 * The concrete implementation that is wrapping the design-time representation of a mapping file.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
public class JpaMappingFile extends JpaManagedTypeProvider {

	/**
	 * Creates a new <code>JpaMappingFile</code>.
	 * 
	 * @param jpaProject The project that gives access to the application's metadata
	 * @param persistentTypeContainer The design-time provider of managed types
	 */
	public JpaMappingFile(JpaProject jpaProject, MappingFile persistentTypeContainer) {
		super(jpaProject, persistentTypeContainer);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	JpaEntity buildEntity(TypeMapping mappedClass) {
		return new JpaOrmEntity(this, (Entity) mappedClass);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected MappingFile getPersistentTypeContainer() {
		return (MappingFile) super.getPersistentTypeContainer();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Iterator<? extends PersistentType> persistenceTypes() {
		return getPersistentTypeContainer().getPersistentTypes().iterator();
	}
}