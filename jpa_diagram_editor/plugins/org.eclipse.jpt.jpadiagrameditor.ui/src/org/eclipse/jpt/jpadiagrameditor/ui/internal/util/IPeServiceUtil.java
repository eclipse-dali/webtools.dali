/*******************************************************************************
 * <copyright>
 *
 * Copyright (c) 2005, 2011 SAP AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *    Kiril Mitov - initial API, implementation and documentation
 *
 * </copyright>
 *
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.util;

import org.eclipse.graphiti.mm.Property;
import org.eclipse.graphiti.mm.PropertyContainer;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;

public interface IPeServiceUtil {

	public FreeFormConnection createFreeFormConnection(Diagram diagram);
	
	public String getPropertyValue(PropertyContainer propertyContainer, String key);
	
	public Property getProperty(PropertyContainer propertyContainer, String key);
	
	public boolean removeProperty(PropertyContainer propertyContainer, String key); 
		
	public void setPropertyValue(PropertyContainer propertyContainer, String key, String value);

}
