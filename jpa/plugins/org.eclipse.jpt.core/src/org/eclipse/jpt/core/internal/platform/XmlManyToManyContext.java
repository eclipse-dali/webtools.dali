/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.platform;


public class XmlManyToManyContext extends XmlMultiRelationshipMappingContext
{
	public XmlManyToManyContext(IContext parentContext, XmlManyToMany mapping) {
		super(parentContext, mapping);
	}
	
	protected XmlManyToMany getManyToMany() {
		return (XmlManyToMany) attributeMapping();
	}
}
