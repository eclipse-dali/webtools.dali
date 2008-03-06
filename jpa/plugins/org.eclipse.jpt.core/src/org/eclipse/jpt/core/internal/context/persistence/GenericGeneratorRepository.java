/*******************************************************************************
 *  Copyright (c) 2008  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.eclipse.jpt.core.context.Generator;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.context.AbstractJpaContextNode;
import org.eclipse.jpt.utility.internal.iterators.CloneListIterator;

/**
 * This is currently just an aggregated class.  No public interface, no change
 * notification.  Its only purpose is to provide a convenient place to collate 
 * the list of all generators defined in the persistence unit and add
 * validation support.
 */
public class GenericGeneratorRepository extends AbstractJpaContextNode
{
	protected final List<Generator> generators;
	
	GenericGeneratorRepository(PersistenceUnit persistenceUnit) {
		super(persistenceUnit);
		this.generators = new ArrayList<Generator>();
	}
	
	
	public void clear() {
		generators.clear();
	}
	
	public void add(Generator generator) {
		generators.add(generator);
	}
	
	ListIterator<Generator> allGenerators() {
		return new CloneListIterator<Generator>(this.generators);
	}
}
