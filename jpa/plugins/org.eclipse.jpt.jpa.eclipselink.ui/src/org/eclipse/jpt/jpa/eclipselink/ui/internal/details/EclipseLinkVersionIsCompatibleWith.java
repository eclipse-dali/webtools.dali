/*******************************************************************************
 * Copyright (c) 2011, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.eclipselink.ui.internal.details;

import org.eclipse.jpt.common.utility.internal.predicate.CriterionPredicate;
import org.eclipse.jpt.jpa.core.JpaModel;
import org.eclipse.jpt.jpa.eclipselink.core.internal.EclipseLinkJpaPlatformFactory.EclipseLinkJpaPlatformVersion;

/**
 * A predicate that returns whether a JPA model's EclipseLink version compatible
 * with the configured version.
 * @see EclipseLinkJpaPlatformVersion#isCompatibleWithEclipseLinkVersion(String)
 */
public class EclipseLinkVersionIsCompatibleWith
	extends CriterionPredicate<JpaModel, String>
{
	public EclipseLinkVersionIsCompatibleWith(String version) {
		super(version);
		if (version == null) {
			throw new NullPointerException();
		}
	}

	public boolean evaluate(JpaModel jpaModel) {
		EclipseLinkJpaPlatformVersion jpaVersion = (EclipseLinkJpaPlatformVersion) jpaModel.getJpaPlatform().getJpaVersion();
		return jpaVersion.isCompatibleWithEclipseLinkVersion(this.criterion);
	}
}
