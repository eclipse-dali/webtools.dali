/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.common.core;

import java.util.EventListener;
import org.eclipse.wst.common.internal.emf.resource.TranslatorResourceImpl;

/**
 * The listener is notified whenever anything in the JPA resource model changes.
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.3
 * @since 2.2
 */
public interface JptResourceModelListener
	extends EventListener
{
	void resourceModelChanged(JptResourceModel jpaResourceModel);
	
	/**
	 * Modified resource is closed without saving
	 * @see TranslatorResourceImpl#isReverting()
	 */
	void resourceModelReverted(JptResourceModel jpaResourceModel);
	
	void resourceModelUnloaded(JptResourceModel jpaResourceModel);
}
