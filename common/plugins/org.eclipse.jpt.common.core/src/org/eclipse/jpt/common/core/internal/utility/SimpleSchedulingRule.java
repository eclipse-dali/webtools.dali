/*******************************************************************************
 * Copyright (c) 2008, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import java.io.Serializable;
import org.eclipse.core.runtime.jobs.ISchedulingRule;
import org.eclipse.jpt.common.utility.internal.ObjectTools;

/**
 * A job scheduling rule that conflicts only with itself.
 */
public final class SimpleSchedulingRule
	implements ISchedulingRule, Serializable
{
	private static final long serialVersionUID = 1L;

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
		return ObjectTools.toString(this);
	}
}
