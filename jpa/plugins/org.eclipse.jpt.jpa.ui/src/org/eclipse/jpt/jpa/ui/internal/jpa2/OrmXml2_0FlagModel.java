/*******************************************************************************
 * Copyright (c) 2011, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2;

import org.eclipse.jpt.common.core.JptResourceTypeReference;
import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.internal.jpa2.context.orm.GenericOrmXmlDefinition2_0;

public class OrmXml2_0FlagModel<R extends JptResourceTypeReference>
	extends TransformationPropertyValueModel<R, Boolean>
{
	public OrmXml2_0FlagModel(PropertyValueModel<? extends R> valueModel) {
		super(valueModel);
	}

	@Override
	protected Boolean transform_(R ref) {
		return Boolean.valueOf(ref.getResourceType().isKindOf(GenericOrmXmlDefinition2_0.instance().getResourceType()));
	}
}
