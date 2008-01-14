/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import java.util.ListIterator;
import org.eclipse.jpt.core.internal.context.base.IJpaContextNode;
import org.eclipse.jpt.core.internal.context.base.INamedQuery;
import org.eclipse.jpt.core.internal.resource.orm.NamedQuery;


public class XmlNamedQuery extends AbstractXmlQuery<NamedQuery> implements INamedQuery
{

	protected XmlNamedQuery(IJpaContextNode parent) {
		super(parent);
	}


	@Override
	@SuppressWarnings("unchecked")
	public ListIterator<XmlQueryHint> hints() {
		return super.hints();
	}
}
