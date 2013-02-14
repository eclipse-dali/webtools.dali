/*******************************************************************************
 * Copyright (c) 2008, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.orm;

import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;
import org.eclipse.jpt.jpa.eclipselink.core.context.EclipseLinkConverterContainer;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkCustomConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkObjectTypeConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkStructConverter;
import org.eclipse.jpt.jpa.eclipselink.core.internal.context.orm.OrmEclipseLinkTypeConverter;

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
public interface OrmEclipseLinkConverterContainer
	extends EclipseLinkConverterContainer, TypeRefactoringParticipant
{

	ListIterable<OrmEclipseLinkCustomConverter> getCustomConverters();
	OrmEclipseLinkCustomConverter addCustomConverter(String name, int index);
	OrmEclipseLinkCustomConverter addCustomConverter(String name);

	ListIterable<OrmEclipseLinkObjectTypeConverter> getObjectTypeConverters();
	OrmEclipseLinkObjectTypeConverter addObjectTypeConverter(String name, int index);
	OrmEclipseLinkObjectTypeConverter addObjectTypeConverter(String name);
	
	ListIterable<OrmEclipseLinkStructConverter> getStructConverters();
	OrmEclipseLinkStructConverter addStructConverter(String name, int index);
	OrmEclipseLinkStructConverter addStructConverter(String name);

	ListIterable<OrmEclipseLinkTypeConverter> getTypeConverters();
	OrmEclipseLinkTypeConverter addTypeConverter(String name, int index);
	OrmEclipseLinkTypeConverter addTypeConverter(String name);


	interface Owner {
		int getNumberSupportedConverters();
	}
}
