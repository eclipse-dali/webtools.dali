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


public interface SequenceGenerator extends Generator
{
	/**
	 * Corresponds to the sequenceName element of the SequenceGenerator annotation.
	 * Returns null if the sequenceName element does not exist in java.  If no other memberValuePairs exist
	 * the SequenceGenerator annotation will be removed as well.
	 */
	String getSequenceName();
	
	/**
	 * Corresponds to the sequenceName element of the SequenceGenerator annotation.
	 * Set to null to remove the sequenceName element.  If no other memberValuePairs exist
	 * the SequenceGenerator annotation will be removed as well.
	 */
	void setSequenceName(String sequenceName);

}
