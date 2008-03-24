/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.context.persistence;

import java.util.List;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.utility.TextRange;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;

public interface PersistenceJpaContextNode extends JpaContextNode
{
	// **************** validation **************************************

	/**
	 * Add to the list of current validation messages
	 */
	void addToMessages(List<IMessage> messages);

	TextRange validationTextRange();

}
