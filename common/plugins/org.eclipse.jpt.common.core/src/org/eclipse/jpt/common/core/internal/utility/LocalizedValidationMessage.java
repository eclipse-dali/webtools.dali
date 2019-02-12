/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility;

import java.util.Locale;
import org.eclipse.core.resources.IResource;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.wst.validation.internal.core.Message;

/**
 * The {@link Message default validation message} expects to bind its text when
 * it is queried via the various <code>getText(...)</code> methods.
 * This implementation expects the text to be already bound at construction
 * time.
 */
public class LocalizedValidationMessage
	extends Message
{
	private final String localizedMessage;


	public LocalizedValidationMessage(int severity, String id, IResource target, String localizedMessage) {
		// super(bundleName, severity, id, parameters, target)
		super(null, severity, id, null, target);
		this.localizedMessage = localizedMessage;
	}

	@Override
	public String getText() {
		return this.localizedMessage;
	}

	@Override
	public String getText(ClassLoader cl) {
		return this.localizedMessage;
	}

	@Override
	public String getText(Locale l) {
		return this.localizedMessage;
	}

	@Override
	public String getText(Locale l, ClassLoader cl) {
		return this.localizedMessage;
	}

	@Override
	public String toString() {
		return ObjectTools.toString(this, this.localizedMessage);
	}
}
