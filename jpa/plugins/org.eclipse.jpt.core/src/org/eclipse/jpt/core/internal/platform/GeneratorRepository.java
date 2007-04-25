/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.eclipse.jpt.core.internal.mappings.IGenerator;

/**
 * For now we are not going to handle duplicates.  The spec says that it is undefined
 * when you have multiple generators with the same name.  A generator with the 
 * same name in an orm.xml file overrides one in the java.  Duplicates will just
 * be handled in a last in wins fashion. 
 * 
 * At some point we could handle duplicates that aren't overrides with a warning. It
 * is difficult to tell the difference between an override and a duplicate so 
 * right now we are not handling it.
 */
public class GeneratorRepository implements IGeneratorRepository
{
	
	private Map<String, IGenerator> generators;
	
	public GeneratorRepository() {
		super();
		this.generators = new HashMap<String, IGenerator>();
	}
	
	/**
	 * Add the given IGenerator to the Map of generators. If there is
	 * already a generator with the given name it will be replaced in the map
	 * by the new one.
	 * @param generator
	 */
	public void addGenerator(IGenerator generator) {
		this.generators.put(generator.getName(), generator);
	}
	
	public IGenerator generator(String name) {
		return this.generators.get(name);
	}

	public Iterator<String> generatorNames() {
		return this.generators.keySet().iterator();
	}
}
