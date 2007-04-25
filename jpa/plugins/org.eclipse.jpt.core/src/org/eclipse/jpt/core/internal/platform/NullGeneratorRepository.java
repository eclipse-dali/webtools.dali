/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.Iterator;
import org.eclipse.jpt.core.internal.mappings.IGenerator;
import org.eclipse.jpt.utility.internal.iterators.EmptyIterator;

public class NullGeneratorRepository implements IGeneratorRepository
{
	// singleton
	private static final NullGeneratorRepository INSTANCE = new NullGeneratorRepository();

	/**
	 * Return the singleton.
	 */
	public static NullGeneratorRepository instance() {
		return INSTANCE;
	}

	/**
	 * Ensure non-instantiability.
	 */
	private NullGeneratorRepository() {
		super();
	}
	
	public IGenerator generator(String name) {
		return null;
	}

	public Iterator<String> generatorNames() {
		return EmptyIterator.instance();
	}
}
