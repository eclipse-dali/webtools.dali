/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.resource.orm.XmlAttributeOverride;
import org.eclipse.jpt.core.resource.orm.XmlColumn;
import org.eclipse.jpt.core.resource.xml.AbstractJpaEObject;

public class VirtualXmlAttributeOverride extends AbstractJpaEObject implements XmlAttributeOverride
{

	protected String name;
	
	protected final XmlColumn column;

	protected VirtualXmlAttributeOverride(String name, XmlColumn xmlColumn) {
		super();
		this.name = name;
		this.column = xmlColumn;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(@SuppressWarnings("unused") String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}


	public XmlColumn getColumn() {
		return this.column;
	}

	public void setColumn(@SuppressWarnings("unused") XmlColumn value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
}
