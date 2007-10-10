/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.resource.java;


public interface GeneratedValue extends JavaResource
{	
	/**
	 * Corresponds to the strategy element of the GeneratedValue annotation.
	 * Returns null if the strategy element does not exist in java.
	 */
	GenerationType getStrategy();
	
	/**
	 * Corresponds to the strategy element of the GeneratedValue annotation.
	 * Set to null to remove the strategy element.
	 */
	void setStrategy(GenerationType strategy);
	
	/**
	 * Corresponds to the generator element of the GeneratedValue annotation.
	 * Returns null if the generator element does not exist in java.
	 */
	String getGenerator();
	
	/**
	 * Corresponds to the generator element of the GeneratedValue annotation.
	 * Set to null to remove the generator element.
	 */
	void setGenerator(String generator);

}
