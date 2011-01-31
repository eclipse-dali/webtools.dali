/*******************************************************************************
 * Copyright (c) 2008, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * A job scheduling rule that conflicts only with itself.
 */
public final class SimpleSchedulingRule
	implements ISchedulingRule
{
	public SimpleSchedulingRule() {
		super();
	}

	public boolean contains(ISchedulingRule rule) {
		return rule == this;
	}

	public boolean isConflicting(ISchedulingRule rule) {
		return rule == this;
	}

	@Override
	public String toString() {
		return StringTools.buildToStringFor(this);
	}

}
