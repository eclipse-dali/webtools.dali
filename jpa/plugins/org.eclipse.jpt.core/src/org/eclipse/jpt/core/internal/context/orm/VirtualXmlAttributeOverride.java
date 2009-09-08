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

public class VirtualXmlAttributeOverride extends XmlAttributeOverride
{

	public VirtualXmlAttributeOverride(String name, XmlColumn xmlColumn) {
		super();
		this.name = name;
		this.column = xmlColumn;
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}


	@Override
	public XmlColumn getColumn() {
		return this.column;
	}

	@Override
	public void setColumn(XmlColumn value) {
		throw new UnsupportedOperationException("cannot set values on a virtual mapping"); //$NON-NLS-1$
	}
}
