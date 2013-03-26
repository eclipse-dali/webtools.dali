/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context;

import java.util.List;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public final class NullJpaValidator
	implements JpaValidator
{
	private static final JpaValidator INSTANCE = new NullJpaValidator();

	public static JpaValidator instance() {
		return INSTANCE;
	}

	// ensure single instance
	private NullJpaValidator() {
		super();
	}

	public boolean validate(List<IMessage> messages, IReporter reporter) {
		return true;  // continue validating
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
