/*******************************************************************************
 * Copyright (c) 2011, 2019 IBM Corporation and others.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.jpadiagrameditor.ui.internal.feature;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.IMoveConnectionDecoratorContext;
import org.eclipse.graphiti.features.impl.DefaultMoveConnectionDecoratorFeature;

public class JPAMoveConnectionDecoratorFeature extends
		DefaultMoveConnectionDecoratorFeature {

	public JPAMoveConnectionDecoratorFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	public boolean canExecute(IContext context) {
		return false;
	}
	
	@Override
	public boolean canMoveConnectionDecorator(
			IMoveConnectionDecoratorContext context) {
		return false;
	}
	
	@Override
	public boolean isAvailable(IContext context) {
		return false;
	}	

}
