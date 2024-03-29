/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.orm;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.JpaContextModel;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.EclipseLinkOrmTypeConverter;

/**
 * EclipseLink <code>orm.xml</code> converter container
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.3
 * @since 2.1
 */
public interface EclipseLinkOrmConverterContainer
	extends EclipseLinkConverterContainer, TypeRefactoringParticipant
{
	ListIterable<EclipseLinkOrmCustomConverter> getCustomConverters();
	EclipseLinkOrmCustomConverter addCustomConverter(String name, int index);
	EclipseLinkOrmCustomConverter addCustomConverter(String name);

	ListIterable<EclipseLinkOrmObjectTypeConverter> getObjectTypeConverters();
	EclipseLinkOrmObjectTypeConverter addObjectTypeConverter(String name, int index);
	EclipseLinkOrmObjectTypeConverter addObjectTypeConverter(String name);
	
	ListIterable<EclipseLinkOrmStructConverter> getStructConverters();
	EclipseLinkOrmStructConverter addStructConverter(String name, int index);
	EclipseLinkOrmStructConverter addStructConverter(String name);

	ListIterable<EclipseLinkOrmTypeConverter> getTypeConverters();
	EclipseLinkOrmTypeConverter addTypeConverter(String name, int index);
	EclipseLinkOrmTypeConverter addTypeConverter(String name);


	// ********** parent interface **********

	interface Parent
		extends JpaContextModel
	{
		/**
		 * Return the maximum number converters the parent allows the
		 * converter container to hold.
		 */
		int getMaximumAllowedConverters();
	}
}
