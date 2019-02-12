/*******************************************************************************
 * Copyright (c) 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
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
	 * Any validation message with this severity will be ignored and no
	 * marker produced.
	 * <p>
	 * Value: {@value}
	 * @see IMessage#getSeverity()
	 */
	int IGNORE_SEVERITY = 8;

	/**
	 * This value is returned for any validation severity preference that has
	 * not been specified.
	 * <p>
	 * Value: {@value}
	 * @see IMessage#getSeverity()
	 */
	int UNSET_SEVERITY_PREFERENCE = -1;

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
	 * Return the message's default severity (i.e. the severity of the message in
	 * the absence of a user-specified preference).
	 * @see IMessage#getSeverity()
	 */
	int getDefaultSeverity();

	/**
	 * Set the message's default severity (i.e. the severity of the message in
	 * the absence of a user-specified preference).
	 * @see IMessage#getSeverity()
	 */
	void setDefaultSeverity(int severity);

	/**
	 * Build a validation message with the specified target resource, text
	 * range, and arguments to be bound to the message template.
	 */
	IMessage buildValidationMessage(IResource target, TextRange textRange, Object... args);
}
