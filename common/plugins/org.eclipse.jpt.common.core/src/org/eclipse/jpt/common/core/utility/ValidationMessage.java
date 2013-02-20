/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core.utility;

import org.eclipse.core.resources.IResource;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

/**
 * A validation message 
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 */
public interface ValidationMessage {
	/**
	 * Return the message's ID. This is the name of the static field that
	 * holds the message.
	 */
	String getID();

	/**
	 * Return the message's description.
	 */
	String getDescription();

	/**
	 * Build a validation message with the specified default
	 * severity (i.e. the severity of the message in the absence of a
	 * user-specified preference), target resource, text range, and arguments
	 * to be bound to the message template.
	 * @see IMessage#getSeverity()
	 */
	IMessage buildValidationMessage(IResource target, TextRange textRange, int defaultSeverity, Object... args);
}
