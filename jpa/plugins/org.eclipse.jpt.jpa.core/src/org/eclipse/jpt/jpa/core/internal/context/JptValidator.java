/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context;

import java.util.List;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public interface JptValidator
{
	/**
	 * Return whether the client should continue validating.
	 */
	boolean validate(List<IMessage> messages, IReporter reporter);


	final class Null
		implements JptValidator
	{
		private static final JptValidator INSTANCE = new Null();
		public static JptValidator instance() {
			return INSTANCE;
		}
		// ensure single instance
		private Null() {
			super();
		}
		public boolean validate(List<IMessage> messages, IReporter reporter) {
			return true;  // continue validating
		}
		@Override
		public String toString() {
			return StringTools.buildSingletonToString(this);
		}
	}
}
