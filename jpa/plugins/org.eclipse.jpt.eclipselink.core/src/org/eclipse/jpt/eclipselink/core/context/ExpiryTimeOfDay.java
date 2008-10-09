/*******************************************************************************
 * Copyright (c) 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.context;

import org.eclipse.jpt.core.context.JpaContextNode;

/**
 * Corresponds to a TimeOfDay resource model object
 * 
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 2.1
 * @since 2.1
 */
public interface ExpiryTimeOfDay extends JpaContextNode
{
	
	Integer getHour();
	void setHour(Integer hour);
		String HOUR_PROPERTY = "hourProperty"; //$NON-NLS-1$

	Integer getMinute();
	void setMinute(Integer minute);
		String MINUTE_PROPERTY = "minuteProperty"; //$NON-NLS-1$
	
	Integer getSecond();
	void setSecond(Integer second);
		String SECOND_PROPERTY = "secondProperty"; //$NON-NLS-1$
	
	Integer getMillisecond();
	void setMillisecond(Integer millisecond);
		String MILLISECOND_PROPERTY = "millisecondProperty"; //$NON-NLS-1$

}
