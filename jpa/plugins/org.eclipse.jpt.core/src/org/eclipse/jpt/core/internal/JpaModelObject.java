/*******************************************************************************
 *  Copyright (c) 2007 Oracle. 
 *  All rights reserved.  This program and the accompanying materials 
 *  are made available under the terms of the Eclipse Public License v1.0 
 *  which accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.jpt.utility.internal.model.AbstractModel;

public abstract class JpaModelObject extends AbstractModel
	implements IJpaModelObject
{
	// For now, make this simply an object, but it should eventually be a JpaModelObject
	private Object parent;
	
	
	public JpaModelObject(Object theParent) {
		super();
		parent = theParent;
		initialize(parent);
	}
	
	protected void initialize(Object parent) {
		// do nothing by default
	}
	
	/**
	 * @see IJpaModelObject#getParent()
	 */
	public Object getParent() {
		return parent;
	}
}
