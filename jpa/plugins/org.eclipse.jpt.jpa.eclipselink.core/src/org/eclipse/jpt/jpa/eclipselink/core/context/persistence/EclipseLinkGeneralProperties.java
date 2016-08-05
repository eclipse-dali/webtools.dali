/*******************************************************************************
* Copyright (c) 2008, 2016 Oracle. All rights reserved.
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0, which accompanies this distribution
* and is available at http://www.eclipse.org/legal/epl-v10.html.
* 
* Contributors:
*     Oracle - initial API and implementation
*******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.core.context.persistence;

import org.eclipse.jpt.common.utility.closure.BiClosure;
import org.eclipse.jpt.common.utility.internal.closure.BiClosureAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnitProperties;

/**
 *  GeneralProperties
 */
public interface EclipseLinkGeneralProperties
	extends PersistenceUnitProperties
{

	Boolean getDefaultExcludeEclipselinkOrm();
	Boolean getExcludeEclipselinkOrm();
	void setExcludeEclipselinkOrm(Boolean newExcludeEclipselinkOrm);
		static final String EXCLUDE_ECLIPSELINK_ORM_PROPERTY = "excludeEclipselinkOrm"; //$NON-NLS-1$
		// EclipseLink key string
		static final String ECLIPSELINK_EXCLUDE_ECLIPSELINK_ORM = "eclipselink.exclude-eclipselink-orm"; //$NON-NLS-1$
		static final Boolean DEFAULT_EXCLUDE_ECLIPSELINK_ORM = Boolean.FALSE;
		Transformer<EclipseLinkGeneralProperties, Boolean> EXCLUDE_ECLIPSELINK_ORM_TRANSFORMER = new ExcludeEclipselinkOrmTransformer();
		class ExcludeEclipselinkOrmTransformer
			extends TransformerAdapter<EclipseLinkGeneralProperties, Boolean>
		{
			@Override
			public Boolean transform(EclipseLinkGeneralProperties model) {
				return model.getExcludeEclipselinkOrm();
			}
		}
	
		BiClosure<EclipseLinkGeneralProperties, Boolean> SET_EXCLUDE_ECLIPSELINK_ORM_CLOSURE = new SetExcludeEclipselinkOrmClosure();
		class SetExcludeEclipselinkOrmClosure
			extends BiClosureAdapter<EclipseLinkGeneralProperties, Boolean>
		{
			@Override
			public void execute(EclipseLinkGeneralProperties model, Boolean value) {
				model.setExcludeEclipselinkOrm(value);
			}
		}
}
