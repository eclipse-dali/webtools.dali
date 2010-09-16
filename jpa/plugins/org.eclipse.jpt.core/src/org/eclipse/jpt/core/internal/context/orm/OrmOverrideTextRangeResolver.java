/*******************************************************************************
 *  Copyright (c) 2010  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.context.orm;

import org.eclipse.jpt.core.context.orm.OrmOverride;
import org.eclipse.jpt.core.internal.context.OverrideTextRangeResolver;
import org.eclipse.jpt.core.utility.TextRange;

public class OrmOverrideTextRangeResolver
	implements OverrideTextRangeResolver
{
	protected final OrmOverride ormOverride;

	public OrmOverrideTextRangeResolver(OrmOverride ormOverride) {
		this.ormOverride = ormOverride;
	}

	protected OrmOverride getOverride() {
		return this.ormOverride;
	}

	public TextRange getNameTextRange() {
		return this.ormOverride.getNameTextRange();
	}
}
