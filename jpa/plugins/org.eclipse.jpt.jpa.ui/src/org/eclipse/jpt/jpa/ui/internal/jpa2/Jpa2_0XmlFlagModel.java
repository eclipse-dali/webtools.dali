/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2;

import org.eclipse.jpt.common.utility.internal.model.value.TransformationPropertyValueModel;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.JptJpaCorePlugin;
import org.eclipse.jpt.jpa.core.context.XmlContextNode;

public class Jpa2_0XmlFlagModel<T extends XmlContextNode> extends
		TransformationPropertyValueModel<T, Boolean> {

	public Jpa2_0XmlFlagModel(PropertyValueModel<T> valueModel) {
		super(valueModel);
	}

	@Override
	protected Boolean transform_(T value) {
		return Boolean.valueOf(JptJpaCorePlugin.nodeIsXml2_0Compatible(value));
	}
}