/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context.java;

import java.util.ListIterator;
import org.eclipse.jpt.core.internal.context.base.IQuery;

public interface IJavaQuery extends IQuery, IJavaJpaContextNode
{

	@SuppressWarnings("unchecked")
	ListIterator<IJavaQueryHint> hints();
	
	IJavaQueryHint addHint(int index);
}
