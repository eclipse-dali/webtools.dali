/*******************************************************************************
 * Copyright (c) 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.ui.internal.jpa2;

import org.eclipse.jpt.core.JpaNode;
import org.eclipse.jpt.core.JptCorePlugin;
import org.eclipse.jpt.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;

/**
 * Flag indicating whether the JPA project supports JPA 2.0.
 */
public class Jpa2_0ProjectFlagModel<T extends JpaNode>
	extends TransformationPropertyValueModel<T, Boolean>
{
	public Jpa2_0ProjectFlagModel(PropertyValueModel<T> jpaProjectModel) { 
		super(jpaProjectModel);
	}

	@Override
	protected Boolean transform_(T value) {
		return Boolean.valueOf(JptCorePlugin.nodeIsJpa2_0Compatible(value));
	}
}
