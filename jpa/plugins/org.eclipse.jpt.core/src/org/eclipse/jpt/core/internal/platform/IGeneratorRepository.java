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

public interface IGeneratorRepository
{
	/**
	 * Return all generator names for this persistence unit.
	 * 1 namespace per persistence unit for all generators in xml and java
	 * @return
	 */
	Iterator<String> generatorNames();
	
	/**
	 * Return the Generator with the given name
	 * @param name
	 * @return
	 */
	IGenerator generator(String name);
}
