/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
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
import org.eclipse.jpt.jpa.core.JpaModel;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory2_4;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory.EclipseLinkJpaPlatformVersion;

/**
 * Flag indicating whether the JPA project supports EclipseLink 2.4
 */
public class EclipseLink2_4ProjectFlagModel<T extends JpaModel>
	extends TransformationPropertyValueModel<T, Boolean>
{
	public EclipseLink2_4ProjectFlagModel(PropertyValueModel<? extends T> jpaProjectModel) { 
		super(jpaProjectModel);
	}

	@Override
	protected Boolean transform_(T jpaModel) {
		EclipseLinkJpaPlatformVersion jpaVersion = (EclipseLinkJpaPlatformVersion) jpaModel.getJpaPlatform().getJpaVersion();
		return Boolean.valueOf(jpaVersion.isCompatibleWithEclipseLinkVersion(EclipseLinkJpaPlatformFactory2_4.VERSION));
	}
}
