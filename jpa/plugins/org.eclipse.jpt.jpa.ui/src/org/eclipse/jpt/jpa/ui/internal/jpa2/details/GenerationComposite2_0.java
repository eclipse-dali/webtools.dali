/*******************************************************************************
 * Copyright (c) 2009, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpa2.details;

import org.eclipse.jpt.common.ui.internal.widgets.Pane;
import org.eclipse.jpt.common.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.jpa.core.context.GeneratorContainer;
import org.eclipse.jpt.jpa.ui.internal.details.GenerationComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

/**
 *  Generation2_0Composite
 */
public class GenerationComposite2_0 extends GenerationComposite
{
	public GenerationComposite2_0(
		Pane<?> parentPane, 
		PropertyValueModel<? extends GeneratorContainer> subjectHolder,
		Composite parent) {

			super(parentPane, subjectHolder, parent);
	}

	@Override
	protected Control addSequenceGeneratorComposite(Composite container) {
		return new SequenceGeneratorComposite2_0(
			this,
			this.buildSequenceGeneratorHolder(),
			container,
			this.buildSequenceGeneratorBuilder()
		).getControl();
	}
}
