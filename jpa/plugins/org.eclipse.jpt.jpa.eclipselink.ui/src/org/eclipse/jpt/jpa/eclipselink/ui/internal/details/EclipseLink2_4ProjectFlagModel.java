/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JpaNode;
import org.eclipse.jpt.jpa.eclipselink.core.JptJpaEclipseLinkCorePlugin;

/**
 * Flag indicating whether the JPA project supports EclipseLink 2.4
 */
public class EclipseLink2_4ProjectFlagModel<T extends JpaNode>
	extends TransformationPropertyValueModel<T, Boolean>
{
	public EclipseLink2_4ProjectFlagModel(PropertyValueModel<T> jpaProjectModel) { 
		super(jpaProjectModel);
	}

	@Override
	protected Boolean transform_(T value) {
		return Boolean.valueOf(JptJpaEclipseLinkCorePlugin.nodeIsEclipseLink2_4Compatible(value));
	}
}
