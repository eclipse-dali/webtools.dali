/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;


public class XmlManyToOneContext
	extends XmlSingleRelationshipMappingContext
{
	public XmlManyToOneContext(IContext parentContext, XmlManyToOne mapping) {
		super(parentContext, mapping);
	}
	
	//ManyToOne mapping is always the owning side
	protected boolean isOwningSide() {
		return true;
	}
}
