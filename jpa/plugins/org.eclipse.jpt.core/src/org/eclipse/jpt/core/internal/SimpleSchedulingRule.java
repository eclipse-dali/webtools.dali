/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal;

import org.eclipse.core.runtime.jobs.ISchedulingRule;

/**
 * A job scheduling rule that conflicts only with itself.
 */
public final class SimpleSchedulingRule
	implements ISchedulingRule
{

	// singleton
	private static final SimpleSchedulingRule INSTANCE = new SimpleSchedulingRule();

	/**
	 * Return the singleton.
	 */
	public static ISchedulingRule instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private SimpleSchedulingRule() {
		super();
	}

	public boolean contains(ISchedulingRule rule) {
		return rule == this;
	}

	public boolean isConflicting(ISchedulingRule rule) {
		return rule == this;
	}

}
